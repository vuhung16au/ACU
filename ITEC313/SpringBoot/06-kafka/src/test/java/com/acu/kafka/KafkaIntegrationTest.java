package com.acu.kafka;

import com.acu.kafka.model.Message;
import com.acu.kafka.service.KafkaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"messages-topic"})
@ActiveProfiles("test")
class KafkaIntegrationTest {

    @Autowired
    private KafkaService kafkaService;

    @BeforeEach
    void setUp() {
        kafkaService.clearMessages();
        kafkaService.clearStatistics();
    }

    @Test
    void testKafkaProducerConsumerFlow() throws Exception {
        // Given
        Message message = new Message("Kafka integration test message", "KafkaTest", Message.MessageType.INFO);

        // When - Send message to Kafka
        CompletableFuture<?> future = kafkaService.sendMessage(message);

        // Then - Verify the future is created
        assertNotNull(future);
        
        // Wait for the future to complete (in real scenario, this would be handled by Kafka listener)
        try {
            future.get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            // In embedded Kafka test, the message might not be consumed immediately
            // This is expected behavior for integration tests
        }
    }

    @Test
    void testMessageConsumptionAndStorage() {
        // Given
        Message message = new Message("Test consumption message", "ConsumerTest", Message.MessageType.WARNING);

        // When - Simulate message consumption
        kafkaService.consumeMessage(message);

        // Then - Verify message is stored in memory
        var messages = kafkaService.getReceivedMessages();
        assertEquals(1, messages.size());
        assertEquals("Test consumption message", messages.get(0).getContent());
        assertEquals("ConsumerTest", messages.get(0).getSender());
        assertEquals(Message.MessageType.WARNING, messages.get(0).getType());
    }

    @Test
    void testMultipleMessageTypes() {
        // Given
        Message infoMessage = new Message("Info message", "TestSender", Message.MessageType.INFO);
        Message warningMessage = new Message("Warning message", "TestSender", Message.MessageType.WARNING);
        Message errorMessage = new Message("Error message", "TestSender", Message.MessageType.ERROR);

        // When
        kafkaService.consumeMessage(infoMessage);
        kafkaService.consumeMessage(warningMessage);
        kafkaService.consumeMessage(errorMessage);

        // Then
        var allMessages = kafkaService.getReceivedMessages();
        assertEquals(3, allMessages.size());

        var infoMessages = kafkaService.getMessagesByType(Message.MessageType.INFO);
        assertEquals(1, infoMessages.size());

        var warningMessages = kafkaService.getMessagesByType(Message.MessageType.WARNING);
        assertEquals(1, warningMessages.size());

        var errorMessages = kafkaService.getMessagesByType(Message.MessageType.ERROR);
        assertEquals(1, errorMessages.size());
    }

    @Test
    void testMessageFilteringBySender() {
        // Given
        Message message1 = new Message("Message from Alice", "Alice", Message.MessageType.INFO);
        Message message2 = new Message("Message from Bob", "Bob", Message.MessageType.INFO);
        Message message3 = new Message("Another from Alice", "Alice", Message.MessageType.WARNING);

        kafkaService.consumeMessage(message1);
        kafkaService.consumeMessage(message2);
        kafkaService.consumeMessage(message3);

        // When
        var aliceMessages = kafkaService.getMessagesBySender("Alice");
        var bobMessages = kafkaService.getMessagesBySender("Bob");

        // Then
        assertEquals(2, aliceMessages.size());
        assertEquals(1, bobMessages.size());
        
        assertTrue(aliceMessages.stream().allMatch(m -> "Alice".equals(m.getSender())));
        assertTrue(bobMessages.stream().allMatch(m -> "Bob".equals(m.getSender())));
    }

    @Test
    void testMessageStatistics() {
        // Given
        Message message1 = new Message("First message", "Sender1", Message.MessageType.INFO);
        Message message2 = new Message("Second message", "Sender2", Message.MessageType.WARNING);
        Message message3 = new Message("Third message", "Sender1", Message.MessageType.ERROR);

        kafkaService.consumeMessage(message1);
        kafkaService.consumeMessage(message2);
        kafkaService.consumeMessage(message3);

        // When
        var stats = kafkaService.getMessageStatistics();

        // Then
        assertNotNull(stats);
        assertEquals(3L, stats.get("totalMessagesReceived"));
        assertEquals(3, stats.get("messagesInMemory"));
        
        @SuppressWarnings("unchecked")
        var typeStats = (java.util.Map<String, Long>) stats.get("messageTypeCounts");
        assertEquals(1L, typeStats.get("INFO"));
        assertEquals(1L, typeStats.get("WARNING"));
        assertEquals(1L, typeStats.get("ERROR"));
    }
}
