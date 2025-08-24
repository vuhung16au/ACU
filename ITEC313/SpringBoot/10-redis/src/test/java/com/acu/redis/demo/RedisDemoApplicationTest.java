package com.acu.redis.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.redis.host=localhost",
    "spring.redis.port=6379"
})
class RedisDemoApplicationTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private Receiver receiver;

    @Test
    void testContextLoads() {
        // This test verifies that the Spring context loads successfully
        assertNotNull(stringRedisTemplate);
        assertNotNull(receiver);
    }

    @Test
    void testReceiverCanReceiveMessage() {
        // Test that receiver can process a message
        int initialCount = receiver.getCount();
        receiver.receiveMessage("Test message");
        assertEquals(initialCount + 1, receiver.getCount());
    }
}
