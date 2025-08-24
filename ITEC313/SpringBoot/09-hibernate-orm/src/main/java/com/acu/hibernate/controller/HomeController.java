package com.acu.hibernate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to Hibernate ORM Demo API");
        response.put("version", "1.0.0");
        response.put("description", "A Spring Boot application demonstrating Hibernate ORM with PostgreSQL");
        
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("authors", "/api/authors");
        endpoints.put("health", "/actuator/health");
        endpoints.put("info", "/actuator/info");
        endpoints.put("metrics", "/actuator/metrics");
        
        response.put("available_endpoints", endpoints);
        response.put("documentation", "Use the endpoints above to interact with the API");
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Application is running successfully");
        return ResponseEntity.ok(response);
    }
}
