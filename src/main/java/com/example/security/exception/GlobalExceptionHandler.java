package com.example.security.exception;

import com.example.security.model.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    private static final String UNEXPECTED_EXCEPTION_CODE = "INTERNAL SERVER ERROR";
    private static final String UNEXPECTED_EXCEPTION_MESSAGE = "UNEXPECTED EXCEPTION";

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

    } // kohneyazdigim


//ingress

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ExceptionResponse handle(Exception ex) {
        log.error("Exception: ", ex);
        return new ExceptionResponse(UNEXPECTED_EXCEPTION_CODE, UNEXPECTED_EXCEPTION_MESSAGE);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ExceptionResponse handle(NotFoundException ex) {
        log.error("NotFoundException: ", ex);
        return new ExceptionResponse(ex.getCode(), ex.getMessage());
    }


//    @ExceptionHandler(CustomFeignException.class)
//    public ResponseEntity<ExceptionResponse> handle(CustomFeignException ex) {
//        log.error("CustomFeignException: ", ex,getMessage());
//        return ResponseEntity.status(ex.getStatus()).body(
//                ExceptionResponse.builder()
//                        .message(ex.getMessage())
//                        .code(ex.getCode())
//                        .build
//        );
//    }


}
