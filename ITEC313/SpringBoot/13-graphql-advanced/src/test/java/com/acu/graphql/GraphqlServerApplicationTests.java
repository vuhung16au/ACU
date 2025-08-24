package com.acu.graphql;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "spring.graphql.graphiql.enabled=false")
class GraphqlServerApplicationTests {

    @Test
    void contextLoads() {
    }

}
