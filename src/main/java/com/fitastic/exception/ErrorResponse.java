package com.fitastic.exception;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor

public class ErrorResponse {

    private String message;
    private String errorCode;
    private List<String> details;
}
