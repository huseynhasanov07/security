package com.example.security.exception;

import com.example.security.model.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyFoundException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse finAlreadyFoundException(UserAlreadyFoundException ex, HttpServletRequest
            request) {

        return ErrorResponse
                .builder()
                .url(request.getRequestURI())
                .message(ex.getMessage())
                .statusCode(BAD_REQUEST.value())
                .build();

    }


}
