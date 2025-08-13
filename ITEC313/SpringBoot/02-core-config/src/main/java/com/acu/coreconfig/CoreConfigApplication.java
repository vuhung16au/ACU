package com.acu.coreconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application class for Day 2: Core Concepts
 * 
 * @SpringBootApplication combines:
 * - @Configuration: Marks this class as a source of bean definitions
 * - @EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath
 * - @ComponentScan: Tells Spring to look for other components in this package
 */
@SpringBootApplication
public class CoreConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreConfigApplication.class, args);
    }
}
