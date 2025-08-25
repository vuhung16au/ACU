package com.acu.vaadin;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:postgresql://localhost:5432/postgres",
    "spring.datasource.driver-class-name=org.postgresql.Driver",
    "spring.datasource.username=postgres",
    "spring.datasource.password=postgres",
    "spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect"
})
public class MainViewTests {

    @Test
    public void contextLoads() {
        // Test that the Spring context loads successfully
    }
}
