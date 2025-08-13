package com.acu.quickstart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot Quick Start Application
 * 
 * This is the main entry point for the Spring Boot application.
 * The @SpringBootApplication annotation enables:
 * - Auto-configuration based on classpath
 * - Component scanning in the current package
 * - Ability to define extra configuration on the application class
 */
@SpringBootApplication
public class QuickStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuickStartApplication.class, args);
    }
}
