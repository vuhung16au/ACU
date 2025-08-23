package com.acu.datajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Main Spring Boot Application for Day 5: Data JPA and Database Integration
 * 
 * This application demonstrates:
 * - JPA entity management
 * - Database integration with PostgreSQL
 * - Repository pattern implementation
 * - Auditing capabilities
 * - REST API development
 */
@SpringBootApplication
@EntityScan("com.acu.datajpa.entity")
@EnableJpaRepositories("com.acu.datajpa.repository")
@EnableJpaAuditing
public class DataJpaApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(DataJpaApplication.class, args);
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter();
    }
}
