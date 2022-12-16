package com.readysetsoftware.creditassessmentapi.data.payload.response;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

public class ApiError {
    private HttpStatus status;
    private String message;
    private List<String> errors;
    private ZonedDateTime timestamp;

    public ApiError(HttpStatus status, String message, List<String> errors, ZonedDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.timestamp = timestamp;
    }

    public ApiError(HttpStatus status, String message, String error, ZonedDateTime timestamp) {
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
        this.timestamp = timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }
    public ZonedDateTime getTimestamp() {
        return timestamp;
    }


}
