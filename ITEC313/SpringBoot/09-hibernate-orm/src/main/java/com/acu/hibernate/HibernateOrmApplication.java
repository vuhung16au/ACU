package com.acu.hibernate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;

@SpringBootApplication(exclude = {WebMvcAutoConfiguration.class})
public class HibernateOrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(HibernateOrmApplication.class, args);
    }
}
