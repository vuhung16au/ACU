package com.acu.sse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SseApplicationTests {
    @Autowired
    private TestRestTemplate rest;

    @Test
    void healthEndpointWorks() {
        ResponseEntity<Map> res = rest.getForEntity("/api/sse/health", Map.class);
        assertThat(res.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(res.getBody()).containsEntry("status", "UP");
    }
}


