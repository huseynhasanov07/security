package com.example.security.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;


@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorResponse {
    final LocalDateTime timestamp = LocalDateTime.now();
    String url;
    String message;
    Integer statusCode;
    List<String> validationMessages;
}
