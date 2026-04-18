package com.jbryan98.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex) {
        return new ResponseEntity<>(
                ErrorResponse
                        .builder()
                        .title("Authentication Failed")
                        .error("INVALID_CREDENTIALS")
                        .detail(ex.getMessage())
                        .build(),
                HttpStatus.UNAUTHORIZED
        );
    }
}
