package com.acu.redis.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class RedisDemoApplicationUnitTest {

    private Receiver receiver;

    @BeforeEach
    void setUp() {
        receiver = new Receiver();
    }

    @Test
    void testReceiverInitialState() {
        // Test that receiver starts with count 0
        assertEquals(0, receiver.getCount());
    }

    @Test
    void testReceiverCanReceiveMessage() {
        // Test that receiver can process a message
        int initialCount = receiver.getCount();
        receiver.receiveMessage("Test message");
        assertEquals(initialCount + 1, receiver.getCount());
    }

    @Test
    void testReceiverCanReceiveMultipleMessages() {
        // Test that receiver can process multiple messages
        receiver.receiveMessage("First message");
        receiver.receiveMessage("Second message");
        receiver.receiveMessage("Third message");
        assertEquals(3, receiver.getCount());
    }
}
