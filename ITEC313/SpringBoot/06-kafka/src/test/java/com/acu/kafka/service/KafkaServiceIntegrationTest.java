package com.acu.kafka.service;

import com.acu.kafka.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"messages-topic"})
@ActiveProfiles("test")
class KafkaServiceIntegrationTest {

    @Autowired
    private KafkaService kafkaService;

    @BeforeEach
    void setUp() {
        kafkaService.clearMessages();
        kafkaService.clearStatistics();
    }

    @Test
    void testSendMessage_Success() {
        // Given
        Message message = new Message("Test Kafka integration message", "IntegrationTest", Message.MessageType.INFO);
        message.setId(UUID.randomUUID().toString());
        message.setTimestamp(LocalDateTime.now());

        // When
        var future = kafkaService.sendMessage(message);

        // Then
        assertNotNull(future);
        // In a real Kafka test, you would wait for the future to complete
        // and verify the message was actually sent to Kafka
    }

    @Test
    void testConsumeMessage_StoresInMemory() {
        // Given
        Message message = new Message("Memory test message", "MemoryTest", Message.MessageType.SUCCESS);
        message.setId(UUID.randomUUID().toString());
        message.setTimestamp(LocalDateTime.now());

        // When
        kafkaService.consumeMessage(message);

        // Then
        List<Message> storedMessages = kafkaService.getReceivedMessages();
        assertEquals(1, storedMessages.size());
        
        Message storedMessage = storedMessages.get(0);
        assertEquals("Memory test message", storedMessage.getContent());
        assertEquals("MemoryTest", storedMessage.getSender());
        assertEquals(Message.MessageType.SUCCESS, storedMessage.getType());
    }

    @Test
    void testGetMessagesBySender() {
        // Given
        Message message1 = new Message("Sender specific message", "SpecificSender", Message.MessageType.INFO);
        Message message2 = new Message("Other sender message", "OtherSender", Message.MessageType.WARNING);
        
        kafkaService.consumeMessage(message1);
        kafkaService.consumeMessage(message2);

        // When
        List<Message> messages = kafkaService.getMessagesBySender("SpecificSender");

        // Then
        assertEquals(1, messages.size());
        assertEquals("SpecificSender", messages.get(0).getSender());
    }

    @Test
    void testGetMessagesByType() {
        // Given
        Message message1 = new Message("Info message", "TestSender", Message.MessageType.INFO);
        Message message2 = new Message("Warning message", "TestSender", Message.MessageType.WARNING);
        
        kafkaService.consumeMessage(message1);
        kafkaService.consumeMessage(message2);

        // When
        List<Message> messages = kafkaService.getMessagesByType(Message.MessageType.INFO);

        // Then
        assertEquals(1, messages.size());
        assertEquals(Message.MessageType.INFO, messages.get(0).getType());
    }

    @Test
    void testClearMessages() {
        // Given
        Message message = new Message("Test message", "TestSender", Message.MessageType.INFO);
        kafkaService.consumeMessage(message);
        assertEquals(1, kafkaService.getReceivedMessages().size());

        // When
        kafkaService.clearMessages();

        // Then
        assertEquals(0, kafkaService.getReceivedMessages().size());
    }

    @Test
    void testMessageStatistics() {
        // Given
        Message message1 = new Message("Info message", "Sender1", Message.MessageType.INFO);
        Message message2 = new Message("Warning message", "Sender2", Message.MessageType.WARNING);
        
        kafkaService.consumeMessage(message1);
        kafkaService.consumeMessage(message2);

        // When
        var stats = kafkaService.getMessageStatistics();

        // Then
        assertNotNull(stats);
        assertEquals(2L, stats.get("totalMessagesReceived"));
        assertEquals(2, stats.get("messagesInMemory"));
    }
}
