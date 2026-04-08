package com.category.server.globalHandleException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalErrorException {

    private final Logger logger = LoggerFactory.getLogger(GlobalErrorException.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> methodHandleNotValidException(MethodArgumentNotValidException ex) {
        Map<String, Object> fieldErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        });

        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", 400);
        response.put("status", false);
        response.put("message", "Validation failed");
        response.put("requiredFields", fieldErrors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(NoHandlerFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("errorMessage", "Request url is not found");
        error.put("path", ex.getRequestURL());
        error.put("statusCode", ex.getStatusCode());
        error.put("status", false);
        logger.error("Error from global NoHandlerFoundException " + ex);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("errorMessage", "Request url is not found");
        error.put("notFoundMethod", ex.getMethod());
        error.put("statusCode", ex.getStatusCode());
        error.put("status", false);
        logger.error("Error from global HttpRequestMethodNotSupportedException " + ex);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> IllegalArgumentException(IllegalArgumentException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("errorMessage", ex.getMessage());
        error.put("statusCode", HttpStatus.NOT_FOUND);
        error.put("status", false);
        logger.error("Error from global IllegalArgumentException " + ex);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, Object>> handleCustom(CustomException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", ex.getCode());
        response.put("status", false);
        response.put("message", ex.getMessage());
        response.put("data", null);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> notReadableExpection(HttpMessageNotReadableException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("statusCode", 400);
        error.put("status", false);
        error.put("message", ex.getMessage());
        error.put("data", null);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAll(Exception ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", 500);
        response.put("status", false);
        response.put("message", ex.getMessage());
        response.put("data", null);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
