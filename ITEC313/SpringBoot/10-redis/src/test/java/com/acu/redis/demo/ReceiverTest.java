package com.acu.redis.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReceiverTest {

    private Receiver receiver;

    @BeforeEach
    void setUp() {
        receiver = new Receiver();
    }

    @Test
    void testInitialCountIsZero() {
        assertEquals(0, receiver.getCount());
    }

    @Test
    void testReceiveMessageIncrementsCount() {
        receiver.receiveMessage("Test message");
        assertEquals(1, receiver.getCount());
    }

    @Test
    void testMultipleMessagesIncrementCount() {
        receiver.receiveMessage("First message");
        receiver.receiveMessage("Second message");
        receiver.receiveMessage("Third message");
        assertEquals(3, receiver.getCount());
    }

    @Test
    void testReceiveMessageWithNullMessage() {
        receiver.receiveMessage(null);
        assertEquals(1, receiver.getCount());
    }

    @Test
    void testReceiveMessageWithEmptyMessage() {
        receiver.receiveMessage("");
        assertEquals(1, receiver.getCount());
    }
}
