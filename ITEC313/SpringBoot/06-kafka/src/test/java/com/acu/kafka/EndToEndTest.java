package com.acu.kafka;

import com.acu.kafka.config.TestConfig;
import com.acu.kafka.controller.EmailController;
import com.acu.kafka.model.Message;
import com.acu.kafka.model.MessageEntity;
import com.acu.kafka.repository.EmailLogRepository;
import com.acu.kafka.repository.MessageRepository;
import com.acu.kafka.repository.ScheduledTaskRepository;
import com.acu.kafka.service.KafkaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@EmbeddedKafka(partitions = 1, topics = {"messages-topic"})
@ActiveProfiles("test")
@Transactional
@Import(TestConfig.class)
class EndToEndTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private EmailLogRepository emailLogRepository;

    @Autowired
    private ScheduledTaskRepository scheduledTaskRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        messageRepository.deleteAll();
        emailLogRepository.deleteAll();
        scheduledTaskRepository.deleteAll();
    }

    @Test
    void testCompleteMessageFlow() throws Exception {
        // 1. Send a message via REST API
        Message message = new Message("End-to-end test message", "E2ETest", Message.MessageType.INFO);
        message.setId(UUID.randomUUID().toString());
        message.setTimestamp(LocalDateTime.now());

        mockMvc.perform(post("/api/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(message)))
                .andExpect(status().isOk())
                .andExpect(content().string("Message sent successfully"));

        // 2. Simulate message consumption (which would normally happen via Kafka listener)
        kafkaService.consumeMessage(message);

        // 3. Verify message is saved to database
        List<MessageEntity> savedMessages = messageRepository.findAll();
        assertEquals(1, savedMessages.size());
        
        MessageEntity savedMessage = savedMessages.get(0);
        assertEquals("End-to-end test message", savedMessage.getContent());
        assertEquals("E2ETest", savedMessage.getSender());
        assertEquals(MessageEntity.MessageType.INFO, savedMessage.getMessageType());

        // 4. Verify message can be retrieved via REST API
        mockMvc.perform(get("/api/messages/database"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].content").value("End-to-end test message"));
    }

    @Test
    void testCompleteEmailFlow() throws Exception {
        // 1. Send an email via REST API
        EmailController.EmailRequest emailRequest = new EmailController.EmailRequest();
        emailRequest.setTo("e2e@example.com");
        emailRequest.setSubject("E2E Test Subject");
        emailRequest.setText("E2E test email content");

        mockMvc.perform(post("/api/email/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emailRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Email sent successfully"));

        // 2. Verify email log is saved to database
        List<com.acu.kafka.model.EmailLogEntity> emailLogs = emailLogRepository.findAll();
        assertEquals(1, emailLogs.size());
        
        com.acu.kafka.model.EmailLogEntity emailLog = emailLogs.get(0);
        assertEquals("e2e@example.com", emailLog.getRecipient());
        assertEquals("E2E Test Subject", emailLog.getSubject());
        assertEquals("simple", emailLog.getTemplateName());
        assertEquals(com.acu.kafka.model.EmailLogEntity.EmailStatus.SENT, emailLog.getStatus());

        // 3. Verify email logs can be retrieved via REST API
        mockMvc.perform(get("/api/email/logs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].recipient").value("e2e@example.com"));
    }

    @Test
    void testCompleteSchedulerFlow() throws Exception {
        // 1. Check scheduler status
        mockMvc.perform(get("/api/scheduler/status"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.status").value("running"));

        // 2. Reset counters
        mockMvc.perform(post("/api/scheduler/reset"))
                .andExpect(status().isOk())
                .andExpect(content().string("Counters reset successfully"));

        // 3. Verify scheduler tasks can be retrieved
        mockMvc.perform(get("/api/scheduler/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testMessageFilteringEndToEnd() throws Exception {
        // 1. Create multiple messages with different senders and types
        Message message1 = new Message("Message from Alice", "Alice", Message.MessageType.INFO);
        Message message2 = new Message("Message from Bob", "Bob", Message.MessageType.WARNING);
        Message message3 = new Message("Another message from Alice", "Alice", Message.MessageType.SUCCESS);

        kafkaService.consumeMessage(message1);
        kafkaService.consumeMessage(message2);
        kafkaService.consumeMessage(message3);

        // 2. Test filtering by sender
        mockMvc.perform(get("/api/messages/sender/Alice"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].sender").value("Alice"))
                .andExpect(jsonPath("$[1].sender").value("Alice"));

        // 3. Test filtering by type
        mockMvc.perform(get("/api/messages/type/info"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].messageType").value("INFO"));
    }

    @Test
    void testEmailFilteringEndToEnd() throws Exception {
        // 1. Create multiple email logs
        com.acu.kafka.model.EmailLogEntity log1 = new com.acu.kafka.model.EmailLogEntity();
        log1.setRecipient("user1@example.com");
        log1.setSubject("Subject 1");
        log1.setStatus(com.acu.kafka.model.EmailLogEntity.EmailStatus.SENT);
        emailLogRepository.save(log1);

        com.acu.kafka.model.EmailLogEntity log2 = new com.acu.kafka.model.EmailLogEntity();
        log2.setRecipient("user2@example.com");
        log2.setSubject("Subject 2");
        log2.setStatus(com.acu.kafka.model.EmailLogEntity.EmailStatus.FAILED);
        emailLogRepository.save(log2);

        // 2. Test filtering by recipient
        mockMvc.perform(get("/api/email/logs/recipient/user1@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].recipient").value("user1@example.com"));

        // 3. Test filtering by status
        mockMvc.perform(get("/api/email/logs/status/sent"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].status").value("SENT"));
    }

    @Test
    void testErrorHandling() throws Exception {
        // Test invalid message type
        mockMvc.perform(get("/api/messages/type/invalid"))
                .andExpect(status().isBadRequest());

        // Test invalid email status
        mockMvc.perform(get("/api/email/logs/status/invalid"))
                .andExpect(status().isBadRequest());

        // Test invalid task status
        mockMvc.perform(get("/api/scheduler/tasks/status/invalid"))
                .andExpect(status().isBadRequest());
    }
}
