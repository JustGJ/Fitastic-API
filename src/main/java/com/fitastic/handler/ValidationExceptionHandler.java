package com.fitastic.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for validation errors.
 * This class handles all exceptions related to method argument validation,
 * specifically targeting field-level validation failures.
 */
@RestControllerAdvice
public class ValidationExceptionHandler {

    /**
     * Handles exceptions triggered by validation errors in request bodies.
     *
     * @param ex the exception that contains validation error details
     * @return a {@link ResponseEntity} containing a map of field names and their respective error messages
     *         along with an HTTP 400 Bad Request status code
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
