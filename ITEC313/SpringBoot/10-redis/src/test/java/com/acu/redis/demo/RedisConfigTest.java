package com.acu.redis.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.redis.host=localhost",
    "spring.redis.port=6379"
})
class RedisConfigTest {

    @Autowired
    private RedisConfig redisConfig;

    @Test
    void testRedisConfigBeansAreCreated() {
        assertNotNull(redisConfig);
    }

    @Test
    void testReceiverBeanIsCreated() {
        Receiver receiver = redisConfig.receiver();
        assertNotNull(receiver);
        assertEquals(0, receiver.getCount());
    }

    @Test
    void testMessageListenerAdapterIsCreated() {
        Receiver receiver = redisConfig.receiver();
        var listenerAdapter = redisConfig.listenerAdapter(receiver);
        assertNotNull(listenerAdapter);
    }
}
