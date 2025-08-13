package com.acu.coreconfig.controller;

import com.acu.coreconfig.service.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST Controller demonstrating @RestController and @RequestMapping annotations
 * 
 * This class shows how to:
 * - Use @RestController for REST API endpoints
 * - Use @RequestMapping for base path mapping
 * - Use @GetMapping, @PostMapping for HTTP methods
 * - Use @PathVariable and @RequestParam for parameter binding
 * - Inject services using @Autowired
 */
@RestController
@RequestMapping("/api/config")
public class ConfigController {

    private static final Logger logger = LoggerFactory.getLogger(ConfigController.class);

    private final ConfigService configService;

    @Autowired
    public ConfigController(ConfigService configService) {
        this.configService = configService;
        logger.info("ConfigController initialized");
    }

    /**
     * GET /api/config - Get all configuration
     */
    @GetMapping
    public Map<String, Object> getConfig() {
        logger.info("GET /api/config - Retrieving application configuration");
        configService.logConfiguration();
        return configService.getAppConfig();
    }

    /**
     * GET /api/config/timestamp - Get current timestamp
     */
    @GetMapping("/timestamp")
    public Map<String, String> getTimestamp() {
        logger.debug("GET /api/config/timestamp - Retrieving current timestamp");
        return Map.of(
            "timestamp", configService.getCurrentTimestamp(),
            "shortTimestamp", configService.getShortTimestamp()
        );
    }

    /**
     * GET /api/config/timestamp/{format} - Get timestamp with specific format
     */
    @GetMapping("/timestamp/{format}")
    public Map<String, String> getTimestampWithFormat(@PathVariable String format) {
        logger.debug("GET /api/config/timestamp/{} - Retrieving timestamp with format", format);
        
        String timestamp;
        if ("short".equalsIgnoreCase(format)) {
            timestamp = configService.getShortTimestamp();
        } else {
            timestamp = configService.getCurrentTimestamp();
        }
        
        return Map.of(
            "format", format,
            "timestamp", timestamp
        );
    }

    /**
     * POST /api/config/log - Trigger configuration logging
     */
    @PostMapping("/log")
    public Map<String, String> triggerLogging(@RequestParam(defaultValue = "info") String level) {
        logger.info("POST /api/config/log - Triggering configuration logging with level: {}", level);
        
        configService.logConfiguration();
        
        return Map.of(
            "message", "Configuration logged successfully",
            "level", level,
            "timestamp", configService.getCurrentTimestamp()
        );
    }

    /**
     * GET /api/config/health - Health check endpoint
     */
    @GetMapping("/health")
    public Map<String, Object> health() {
        logger.trace("GET /api/config/health - Health check");
        return Map.of(
            "status", "UP",
            "service", "Config Service",
            "timestamp", configService.getCurrentTimestamp()
        );
    }
}
