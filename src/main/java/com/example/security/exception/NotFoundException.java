package com.example.security.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;


@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotFoundException extends RuntimeException {

    String code;

    public NotFoundException(String message, String code) {
        super(message);
        this.code = code;
    }
}
