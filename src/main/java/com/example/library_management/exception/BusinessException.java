package com.example.library_management.exception;

import com.example.library_management.utils.ResponseCodeHandler;
import lombok.Data;

@Data
public class BusinessException extends RuntimeException {
    private final int errorCode;
    private final String errorMessage;

    public BusinessException(String message) {
        super();
        this.errorCode = 0;
        this.errorMessage = ResponseCodeHandler.getMessage(message) != null ? ResponseCodeHandler.getMessage(message) : message;
    }
    public BusinessException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

}

