package com.acu.kafka.controller;

import com.acu.kafka.model.Message;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@EmbeddedKafka(partitions = 1, topics = {"messages-topic"})
@ActiveProfiles("test")
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
        
        // Setup default mock behavior for Kafka service
        when(kafkaService.sendMessage(any(Message.class))).thenReturn(CompletableFuture.completedFuture(null));
    }

    @Test
    void testSendMessage_Success() throws Exception {
        // Given
        Message message = new Message("Test Kafka message", "TestSender", Message.MessageType.INFO);
        message.setId(UUID.randomUUID().toString());
        message.setTimestamp(LocalDateTime.now());

        // When & Then
        mockMvc.perform(post("/api/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(message)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Message sent successfully"));
    }

    @Test
    void testGetMessages_Success() throws Exception {
        // Given
        Message message1 = new Message("Kafka message 1", "Sender1", Message.MessageType.INFO);
        Message message2 = new Message("Kafka message 2", "Sender2", Message.MessageType.WARNING);
        List<Message> messages = Arrays.asList(message1, message2);

        when(kafkaService.getReceivedMessages()).thenReturn(messages);

        // When & Then
        mockMvc.perform(get("/api/messages"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].content").value("Kafka message 1"))
                .andExpect(jsonPath("$[1].content").value("Kafka message 2"));
    }

    @Test
    void testGetMessagesBySender_Success() throws Exception {
        // Given
        Message message = new Message("Sender specific message", "TestSender", Message.MessageType.INFO);
        List<Message> messages = Arrays.asList(message);

        when(kafkaService.getMessagesBySender("TestSender")).thenReturn(messages);

        // When & Then
        mockMvc.perform(get("/api/messages/sender/TestSender"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sender").value("TestSender"));
    }

    @Test
    void testGetMessagesByType_Success() throws Exception {
        // Given
        Message message = new Message("Info message", "TestSender", Message.MessageType.INFO);
        List<Message> messages = Arrays.asList(message);

        when(kafkaService.getMessagesByType(Message.MessageType.INFO)).thenReturn(messages);

        // When & Then
        mockMvc.perform(get("/api/messages/type/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("INFO"));
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
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Messages cleared successfully"));
    }

    @Test
    void testHealthCheck() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/messages/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.service").value("Kafka Message Service"));
    }
}
