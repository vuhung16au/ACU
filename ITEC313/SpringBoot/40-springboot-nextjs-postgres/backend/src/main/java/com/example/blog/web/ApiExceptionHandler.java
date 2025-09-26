package com.example.blog.web;

import com.example.blog.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler {
    private ResponseEntity<Object> body(HttpStatus status, String error, String message, String path) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("timestamp", Instant.now().toString());
        m.put("status", status.value());
        m.put("error", error);
        m.put("message", message);
        m.put("path", path);
        return ResponseEntity.status(status).body(m);
    }

    @ExceptionHandler(BlogService.ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(BlogService.ResourceNotFoundException ex, ServletWebRequest req) {
        return body(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), req.getRequest().getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidation(MethodArgumentNotValidException ex, ServletWebRequest req) {
        String msg = ex.getBindingResult().getAllErrors().stream().findFirst().map(e -> e.getDefaultMessage()).orElse("Validation error");
        return body(HttpStatus.BAD_REQUEST, "Bad Request", msg, req.getRequest().getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOther(Exception ex, ServletWebRequest req) {
        return body(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex.getMessage(), req.getRequest().getRequestURI());
    }
}


