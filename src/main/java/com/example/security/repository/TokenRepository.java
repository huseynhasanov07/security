package com.example.security.repository;

import com.example.security.token.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<Token, Integer> {

    void deleteByAccessToken(String accessToken);
    Optional<Token> findByAccessToken(String accessToken);

}
