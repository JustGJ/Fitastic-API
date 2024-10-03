package com.fitastic.exception;

import com.fitastic.dto.ErrorMessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles EntityAlreadyExistsException and returns an HTTP 409 Conflict response.
     * This is typically thrown when trying to create an entity that already exists, such as
     * when registering a user with an email that is already in use.
     *
     * @param ex The exception to handle.
     * @return A ResponseEntity with an error message and HTTP 409 status.
     */
    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ErrorMessageDTO> handleEntityAlreadyExistsException(EntityAlreadyExistsException ex) {
        ErrorMessageDTO error = new ErrorMessageDTO(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    /**
     * Handles NoSuchElementException and returns an HTTP 404 Not Found response.
     * This is thrown when trying to access an entity that does not exist, such as
     * when fetching a resource by ID that is not found in the database.
     *
     * @param ex The exception to handle.
     * @return A ResponseEntity with an error message and HTTP 404 status.
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorMessageDTO> handleEntityNotFoundException(NoSuchElementException ex) {
        ErrorMessageDTO error = new ErrorMessageDTO(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles AccessDeniedException and returns an HTTP 403 Forbidden response.
     * This is typically thrown when a user attempts to access a resource for which they do not
     * have the appropriate permissions.
     *
     * @param ex The exception to handle.
     * @return A ResponseEntity with a generic error message and HTTP 403 status.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessageDTO> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorMessageDTO error = new ErrorMessageDTO("You do not have permission to access this resource");
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    /**
     * Handles all other generic exceptions and returns an HTTP 500 Internal Server Error response.
     * This handler acts as a fallback for any unhandled exceptions that may occur in the application.
     *
     * @param ex The exception to handle.
     * @return A ResponseEntity with an error message and HTTP 500 status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageDTO> handleGenericException(Exception ex) {
        ErrorMessageDTO error = new ErrorMessageDTO(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}