package com.acu.coreconfig.service;

import com.acu.coreconfig.config.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Service class demonstrating @Service annotation and dependency injection
 * 
 * This class shows how to:
 * - Use @Service annotation for business logic
 * - Inject dependencies using @Autowired
 * - Use @Value for simple property injection
 * - Use @Qualifier for specific bean selection
 */
@Service
public class ConfigService {

    private static final Logger logger = LoggerFactory.getLogger(ConfigService.class);

    private final AppProperties appProperties;
    private final DateTimeFormatter dateTimeFormatter;
    private final DateTimeFormatter shortDateTimeFormatter;

    @Value("${server.port:8080}")
    private int serverPort;

    @Value("${spring.application.name:core-config}")
    private String applicationName;

    /**
     * Constructor injection demonstrating @Autowired
     */
    @Autowired
    public ConfigService(AppProperties appProperties,
                        DateTimeFormatter dateTimeFormatter,
                        @Qualifier("shortDateTimeFormatter") DateTimeFormatter shortDateTimeFormatter) {
        this.appProperties = appProperties;
        this.dateTimeFormatter = dateTimeFormatter;
        this.shortDateTimeFormatter = shortDateTimeFormatter;
        
        logger.info("ConfigService initialized with app name: {}", appProperties.getName());
    }

    /**
     * Get application configuration as a map
     */
    public Map<String, Object> getAppConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("name", appProperties.getName());
        config.put("environment", appProperties.getEnvironment());
        config.put("maxConnections", appProperties.getMaxConnections());
        config.put("debugEnabled", appProperties.isDebugEnabled());
        config.put("allowedOrigins", appProperties.getAllowedOrigins());
        config.put("serverPort", serverPort);
        config.put("applicationName", applicationName);
        config.put("timestamp", getCurrentTimestamp());
        
        logger.debug("Retrieved app configuration: {}", config);
        return config;
    }

    /**
     * Get current timestamp using the primary DateTimeFormatter
     */
    public String getCurrentTimestamp() {
        String timestamp = LocalDateTime.now().format(dateTimeFormatter);
        logger.trace("Generated timestamp: {}", timestamp);
        return timestamp;
    }

    /**
     * Get current timestamp using the short format DateTimeFormatter
     */
    public String getShortTimestamp() {
        String timestamp = LocalDateTime.now().format(shortDateTimeFormatter);
        logger.trace("Generated short timestamp: {}", timestamp);
        return timestamp;
    }

    /**
     * Log configuration details at different levels
     */
    public void logConfiguration() {
        logger.info("Application: {} running on port: {}", appProperties.getName(), serverPort);
        logger.debug("Environment: {}, Max Connections: {}", 
                    appProperties.getEnvironment(), appProperties.getMaxConnections());
        logger.trace("Debug enabled: {}, Allowed origins: {}", 
                    appProperties.isDebugEnabled(), appProperties.getAllowedOrigins());
    }
}
