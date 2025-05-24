package com.example.addressbook.dto;

import java.util.Map;

public class ErrorResponseDto {
    private String error;
    private String message;
    private Map<String, String> details;

    public ErrorResponseDto(String error, String message, Map<String, String> details) {
        this.error = error;
        this.message = message;
        this.details = details;
    }

    // Getters
    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    // Setters
    public void setError(String error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }
}