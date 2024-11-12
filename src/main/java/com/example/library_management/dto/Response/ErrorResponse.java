package com.example.library_management.dto.Response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private String errorMessage;
    private LocalDateTime timestamp;

    public ErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
        this.timestamp = LocalDateTime.now();
    }
}