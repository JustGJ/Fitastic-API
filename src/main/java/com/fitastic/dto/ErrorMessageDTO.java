package com.fitastic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Class representing the error details that will be returned in the error response.
 */
@Data
@AllArgsConstructor
public class ErrorMessageDTO {

    private String message;
}