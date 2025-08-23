package com.acu.kafka.service;

import com.acu.kafka.config.TestConfig;
import com.acu.kafka.model.Message;
import com.acu.kafka.model.MessageEntity;
import com.acu.kafka.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"messages-topic"})
@ActiveProfiles("test")
@Import(TestConfig.class)
class KafkaServiceIntegrationTest {

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    private MessageRepository messageRepository;

    @BeforeEach
    void setUp() {
        messageRepository.deleteAll();
    }

    @Test
    void testSendMessage_Success() {
        // Given
        Message message = new Message("Test integration message", "IntegrationTest", Message.MessageType.INFO);
        message.setId(UUID.randomUUID().toString());
        message.setTimestamp(LocalDateTime.now());

        // When
        var future = kafkaService.sendMessage(message);

        // Then
        assertNotNull(future);
        // Note: In a real test, you might want to wait for the future to complete
        // and verify the message was actually sent to Kafka
    }

    @Test
    void testConsumeMessage_SavesToDatabase() {
        // Given
        Message message = new Message("Database test message", "DBTest", Message.MessageType.SUCCESS);
        message.setId(UUID.randomUUID().toString());
        message.setTimestamp(LocalDateTime.now());

        // When
        kafkaService.consumeMessage(message);

        // Then
        List<MessageEntity> savedMessages = messageRepository.findAll();
        assertEquals(1, savedMessages.size());
        
        MessageEntity savedMessage = savedMessages.get(0);
        assertEquals("Database test message", savedMessage.getContent());
        assertEquals("DBTest", savedMessage.getSender());
        assertEquals(MessageEntity.MessageType.SUCCESS, savedMessage.getMessageType());
        assertEquals("messages-topic", savedMessage.getKafkaTopic());
    }

    @Test
    void testGetMessagesFromDatabase() {
        // Given
        MessageEntity entity1 = new MessageEntity();
        entity1.setContent("Test message 1");
        entity1.setSender("TestSender1");
        entity1.setMessageType(MessageEntity.MessageType.INFO);
        entity1.setTimestamp(LocalDateTime.now());
        messageRepository.save(entity1);

        MessageEntity entity2 = new MessageEntity();
        entity2.setContent("Test message 2");
        entity2.setSender("TestSender2");
        entity2.setMessageType(MessageEntity.MessageType.WARNING);
        entity2.setTimestamp(LocalDateTime.now());
        messageRepository.save(entity2);

        // When
        List<MessageEntity> messages = kafkaService.getMessagesFromDatabase();

        // Then
        assertEquals(2, messages.size());
        assertTrue(messages.stream().anyMatch(m -> m.getContent().equals("Test message 1")));
        assertTrue(messages.stream().anyMatch(m -> m.getContent().equals("Test message 2")));
    }

    @Test
    void testGetMessagesBySender() {
        // Given
        MessageEntity entity1 = new MessageEntity();
        entity1.setContent("Sender specific message");
        entity1.setSender("SpecificSender");
        entity1.setMessageType(MessageEntity.MessageType.INFO);
        entity1.setTimestamp(LocalDateTime.now());
        messageRepository.save(entity1);

        MessageEntity entity2 = new MessageEntity();
        entity2.setContent("Other sender message");
        entity2.setSender("OtherSender");
        entity2.setMessageType(MessageEntity.MessageType.WARNING);
        entity2.setTimestamp(LocalDateTime.now());
        messageRepository.save(entity2);

        // When
        List<MessageEntity> messages = kafkaService.getMessagesBySender("SpecificSender");

        // Then
        assertEquals(1, messages.size());
        assertEquals("SpecificSender", messages.get(0).getSender());
        assertEquals("Sender specific message", messages.get(0).getContent());
    }

    @Test
    void testGetMessagesByType() {
        // Given
        MessageEntity entity1 = new MessageEntity();
        entity1.setContent("Info message");
        entity1.setSender("TestSender");
        entity1.setMessageType(MessageEntity.MessageType.INFO);
        entity1.setTimestamp(LocalDateTime.now());
        messageRepository.save(entity1);

        MessageEntity entity2 = new MessageEntity();
        entity2.setContent("Warning message");
        entity2.setSender("TestSender");
        entity2.setMessageType(MessageEntity.MessageType.WARNING);
        entity2.setTimestamp(LocalDateTime.now());
        messageRepository.save(entity2);

        // When
        List<MessageEntity> messages = kafkaService.getMessagesByType(MessageEntity.MessageType.INFO);

        // Then
        assertEquals(1, messages.size());
        assertEquals(MessageEntity.MessageType.INFO, messages.get(0).getMessageType());
        assertEquals("Info message", messages.get(0).getContent());
    }

    
}
