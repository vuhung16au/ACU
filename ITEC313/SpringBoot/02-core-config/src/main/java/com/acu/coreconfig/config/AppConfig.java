package com.acu.coreconfig.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Configuration class demonstrating @Configuration and @Bean annotations
 * 
 * This class shows how to:
 * - Define custom beans using @Bean annotation
 * - Use @Primary to specify the preferred bean when multiple candidates exist
 * - Configure application-specific beans
 */
@Configuration
public class AppConfig {

    /**
     * Creates a DateTimeFormatter bean for consistent date formatting across the application
     * 
     * @return DateTimeFormatter configured for ISO format
     */
    @Bean
    @Primary
    public DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Creates an alternative DateTimeFormatter for different formatting needs
     * 
     * @return DateTimeFormatter configured for short format
     */
    @Bean("shortDateTimeFormatter")
    public DateTimeFormatter shortDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
    }

    /**
     * Creates a utility bean for getting current timestamp
     * 
     * @return Current LocalDateTime
     */
    @Bean
    public LocalDateTime currentTime() {
        return LocalDateTime.now();
    }
}
