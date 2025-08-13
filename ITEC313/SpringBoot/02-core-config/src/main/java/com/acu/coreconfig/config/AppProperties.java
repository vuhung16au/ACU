package com.acu.coreconfig.config;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * Configuration Properties class demonstrating @ConfigurationProperties
 * 
 * This class shows how to:
 * - Bind external configuration to Java objects
 * - Use validation annotations for configuration validation
 * - Organize related configuration properties
 */
@Component
@ConfigurationProperties(prefix = "app")
@Validated
public class AppProperties {

    @NotBlank(message = "Application name is required")
    private String name = "Core Config Demo";

    @NotBlank(message = "Environment is required")
    private String environment = "dev";

    @Min(value = 1, message = "Max connections must be at least 1")
    @Max(value = 100, message = "Max connections cannot exceed 100")
    private int maxConnections = 10;

    private boolean debugEnabled = false;

    private String[] allowedOrigins = {"http://localhost:3000", "http://localhost:8080"};

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public void setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

    public String[] getAllowedOrigins() {
        return allowedOrigins;
    }

    public void setAllowedOrigins(String[] allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }
}
