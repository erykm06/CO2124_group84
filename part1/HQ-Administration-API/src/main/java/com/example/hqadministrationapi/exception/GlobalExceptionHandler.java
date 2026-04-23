package com.example.hqadministrationapi.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> notFound(EntityNotFoundException ex) {
        HashMap<String, String> body = new HashMap<>();
        body.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(NotEligibleException.class)
    public ResponseEntity<Map<String, String>> notEligible(NotEligibleException ex) {
        HashMap<String, String> body = new HashMap<>();
        body.put("error", ex.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validation(MethodArgumentNotValidException ex) {
        StringBuilder message = new StringBuilder();
        for (var fieldError : ex.getBindingResult().getFieldErrors()) {
            if (message.length() > 0) message.append("; ");
            String detail = fieldError.getDefaultMessage() == null ? "invalid" : fieldError.getDefaultMessage();
            message.append(fieldError.getField()).append(" ").append(detail);
        }

        HashMap<String, String> body = new HashMap<>();
        body.put("error", message.toString());
        return ResponseEntity.badRequest().body(body);
    }
}