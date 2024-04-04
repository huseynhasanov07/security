package com.example.security.service;

import com.example.security.exception.UserAlreadyFoundException;
import com.example.security.model.*;
import com.example.security.repository.TokenRepository;
import com.example.security.repository.UserRepository;
import com.example.security.token.Token;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService service;
    private final AuthenticationManager manager;
    private final TokenRepository tokenRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        if (repository.findByEmail(request.getEmail()).isPresent())
            throw new UserAlreadyFoundException("User already found with fin : " + request.getEmail());
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role(Role.ROLE_ADMIN)
                .build();
        var savedUser = repository.save(user);
        var jwtToken = service.generateToken(user);
        var refreshToken = service.generateRefreshToken(user);
        var token = Token.builder().id(savedUser.getId()).accessToken(jwtToken).build();
        saveUserToken(token);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void revokeAllUserTokens(Integer userId) {
        tokenRepository.deleteById(userId);
    }

    private void saveUserToken(Token token) {
        tokenRepository.save(token);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getFin(),
                request.getPassword())
        );
        var user = repository.findByEmail(request.getFin()).orElseThrow();
        revokeAllUserTokens(user.getId());
        var jwtToken = service.generateToken(user);
        var refreshToken = service.generateRefreshToken(user);
        var token = Token.builder().id(user.getId()).accessToken(jwtToken).build();
        saveUserToken(token);
        var tempToken = tokenRepository.findByAccessToken(jwtToken);
        System.out.println(tempToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = service.extractFin(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail).orElseThrow();
            if (service.isTokenValid(refreshToken, user)) {
                var accessToken = service.generateToken(user);
                revokeAllUserTokens(user.getId());
                saveUserToken(Token.builder().id(user.getId()).accessToken(accessToken).build());
                var authResponse = AuthenticationResponse
                        .builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
