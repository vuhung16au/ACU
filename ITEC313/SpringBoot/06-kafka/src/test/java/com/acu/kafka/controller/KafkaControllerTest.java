package com.acu.kafka.controller;

import com.acu.kafka.model.Message;
import com.acu.kafka.model.MessageEntity;
import com.acu.kafka.service.KafkaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import com.acu.kafka.config.TestConfig;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@EmbeddedKafka(partitions = 1, topics = {"messages-topic"})
@ActiveProfiles("test")
@Import(TestConfig.class)
class KafkaControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private KafkaService kafkaService;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        // Setup default mock behavior
        when(kafkaService.sendMessage(any(Message.class))).thenReturn(CompletableFuture.completedFuture(null));
    }

    @Test
    void testSendMessage_Success() throws Exception {
        // Given
        Message message = new Message("Test message", "TestSender", Message.MessageType.INFO);
        message.setId(UUID.randomUUID().toString());
        message.setTimestamp(LocalDateTime.now());

        // When & Then
        mockMvc.perform(post("/api/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(message)))
                .andExpect(status().isOk())
                .andExpect(content().string("Message sent successfully"));
    }

    @Test
    void testSendMessage_InvalidMessage() throws Exception {
        // Given
        Message message = new Message(); // Invalid message without required fields

        // When & Then
        mockMvc.perform(post("/api/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(message)))
                .andExpect(status().isOk()); // Controller doesn't validate fields, so it returns 200
    }

    @Test
    void testGetMessages_Success() throws Exception {
        // Given
        Message message1 = new Message("Test message 1", "Sender1", Message.MessageType.INFO);
        Message message2 = new Message("Test message 2", "Sender2", Message.MessageType.WARNING);
        List<Message> messages = Arrays.asList(message1, message2);

        // Mock the service
        when(kafkaService.getReceivedMessages()).thenReturn(messages);

        // When & Then
        mockMvc.perform(get("/api/messages"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].content").value("Test message 1"))
                .andExpect(jsonPath("$[1].content").value("Test message 2"));
    }

    @Test
    void testGetMessagesFromDatabase_Success() throws Exception {
        // Given
        MessageEntity entity1 = new MessageEntity();
        entity1.setId(UUID.randomUUID());
        entity1.setContent("DB message 1");
        entity1.setSender("DBSender1");
        entity1.setMessageType(MessageEntity.MessageType.INFO);
        entity1.setTimestamp(LocalDateTime.now());

        MessageEntity entity2 = new MessageEntity();
        entity2.setId(UUID.randomUUID());
        entity2.setContent("DB message 2");
        entity2.setSender("DBSender2");
        entity2.setMessageType(MessageEntity.MessageType.WARNING);
        entity2.setTimestamp(LocalDateTime.now());

        List<MessageEntity> entities = Arrays.asList(entity1, entity2);

        // Mock the service
        when(kafkaService.getMessagesFromDatabase()).thenReturn(entities);

        // When & Then
        mockMvc.perform(get("/api/messages/database"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].content").value("DB message 1"))
                .andExpect(jsonPath("$[1].content").value("DB message 2"));
    }

    @Test
    void testGetMessagesBySender_Success() throws Exception {
        // Given
        MessageEntity entity = new MessageEntity();
        entity.setId(UUID.randomUUID());
        entity.setContent("Sender specific message");
        entity.setSender("TestSender");
        entity.setMessageType(MessageEntity.MessageType.INFO);
        entity.setTimestamp(LocalDateTime.now());

        List<MessageEntity> entities = Arrays.asList(entity);

        // Mock the service
        when(kafkaService.getMessagesBySender("TestSender")).thenReturn(entities);

        // When & Then
        mockMvc.perform(get("/api/messages/sender/TestSender"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].sender").value("TestSender"));
    }

    @Test
    void testGetMessagesByType_Success() throws Exception {
        // Given
        MessageEntity entity = new MessageEntity();
        entity.setId(UUID.randomUUID());
        entity.setContent("Info message");
        entity.setSender("TestSender");
        entity.setMessageType(MessageEntity.MessageType.INFO);
        entity.setTimestamp(LocalDateTime.now());

        List<MessageEntity> entities = Arrays.asList(entity);

        // Mock the service
        when(kafkaService.getMessagesByType(MessageEntity.MessageType.INFO)).thenReturn(entities);

        // When & Then
        mockMvc.perform(get("/api/messages/type/info"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].messageType").value("INFO"));
    }

    @Test
    void testGetMessagesByType_InvalidType() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/messages/type/invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testClearMessages_Success() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/messages"))
                .andExpect(status().isOk())
                .andExpect(content().string("Messages cleared successfully"));
    }
}
