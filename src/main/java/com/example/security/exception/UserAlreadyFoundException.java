package com.example.security.exception;

public class UserAlreadyFoundException extends RuntimeException {
    public UserAlreadyFoundException(String message) {
        super(message);
    }

    public UserAlreadyFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyFoundException(Throwable cause) {
        super(cause);
    }
}
