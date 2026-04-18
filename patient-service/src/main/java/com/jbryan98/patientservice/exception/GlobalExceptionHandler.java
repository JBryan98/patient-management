package com.jbryan98.patientservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        var fields = new HashMap<String, List<String>>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            fields.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(errorMessage);
        });
        return new ResponseEntity<>(
                ErrorResponse
                        .builder()
                        .title("Validation Failed")
                        .error("METHOD_ARGUMENT_NOT_VALID")
                        .validationErrors(fields)
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        log.warn("Email address already exists: {}", ex.getMessage());
        return new ResponseEntity<>(
                ErrorResponse
                        .builder()
                        .title("Email Already Exists")
                        .error("EMAIL_ALREADY_EXISTS")
                        .detail(ex.getMessage())
                        .build(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePatientNotFoundException(PatientNotFoundException ex) {
        log.warn("Patient not found: {}", ex.getMessage());
        return new ResponseEntity<>(
                ErrorResponse
                        .builder()
                        .title("Patient Not Found")
                        .error("PATIENT_NOT_FOUND")
                        .detail(ex.getMessage())
                        .build(),
                HttpStatus.NOT_FOUND
        );
    }
}