package com.fitastic.handler;

import com.fitastic.dto.ErrorMessageDTO;
import com.fitastic.exception.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    /**
     * Handles InvalidCredentialsException and returns an HTTP 401 Unauthorized response.
     *
     * @param ex The exception to handle.
     * @return A ResponseEntity with an error message and HTTP 401 status.
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorMessageDTO> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        ErrorMessageDTO error = new ErrorMessageDTO(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
}
