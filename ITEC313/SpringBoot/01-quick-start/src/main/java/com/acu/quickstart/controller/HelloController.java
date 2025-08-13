package com.acu.quickstart.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple REST Controller to demonstrate Spring Boot Web functionality
 */
@RestController
public class HelloController {

    /**
     * Basic hello endpoint
     */
    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring Boot!";
    }

    /**
     * Hello endpoint with customizable name parameter
     */
    @GetMapping("/hello/name")
    public Map<String, Object> helloWithName(@RequestParam(defaultValue = "World") String name) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hello " + name + "!");
        response.put("timestamp", LocalDateTime.now());
        response.put("application", "01-quick-start");
        return response;
    }

    /**
     * Health check endpoint (in addition to actuator health)
     */
    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now());
        health.put("application", "01-quick-start");
        return health;
    }
}
