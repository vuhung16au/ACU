package com.acu.datajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Main Spring Boot application class for Day 5: Data JPA and Database Integration
 * 
 * This application demonstrates:
 * - Spring Data JPA with Hibernate
 * - Database integration with H2 (dev) and MySQL (prod)
 * - Entity relationships and associations
 * - Repository pattern with query methods
 * - Database migrations with Flyway
 * - Transaction management
 */
@SpringBootApplication
@EnableJpaAuditing
public class DataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataJpaApplication.class, args);
    }
}
