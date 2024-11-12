package com.example.library_management.exception;

import com.example.library_management.dto.Response.ErrorResponse;
import com.example.library_management.dto.Response.GenericResponse;
import com.example.library_management.dto.Response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Response<ErrorResponse>> handleBusinessException(BusinessException ex) {
        logger.error("BusinessException with code {}: {}", ex.getErrorCode(), ex.getMessage());
        GenericResponse<ErrorResponse> genericResponse = new GenericResponse<>();
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        Response<ErrorResponse> response = genericResponse.createErrorResponse(errorResponse, ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Response<ErrorResponse>> handleGlobalException(Exception ex) {
        logger.error("Unexpected error: ", ex);
        GenericResponse<ErrorResponse> genericResponse = new GenericResponse<>();
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        Response<ErrorResponse> response = genericResponse.createErrorResponse(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
