package com.acu.kafka.controller;

import com.acu.kafka.model.EmailLogEntity;
import com.acu.kafka.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import com.acu.kafka.config.TestConfig;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@Import(TestConfig.class)
class EmailControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private EmailService emailService;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        // Setup default mock behavior
        doNothing().when(emailService).sendSimpleEmail(anyString(), anyString(), anyString());
        doNothing().when(emailService).sendWelcomeEmail(anyString(), anyString());
        doNothing().when(emailService).sendNotificationEmail(anyString(), anyString(), anyString());
    }

    @Test
    void testSendEmail_Success() throws Exception {
        // Given
        EmailController.EmailRequest request = new EmailController.EmailRequest();
        request.setTo("test@example.com");
        request.setSubject("Test Subject");
        request.setText("Test email content");

        // When & Then
        mockMvc.perform(post("/api/email/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Email sent successfully"));
    }

    @Test
    void testSendEmail_InvalidRequest() throws Exception {
        // Given
        EmailController.EmailRequest request = new EmailController.EmailRequest();
        // Missing required fields

        // When & Then
        mockMvc.perform(post("/api/email/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk()); // Controller doesn't validate fields, so it returns 200
    }

    @Test
    void testSendWelcomeEmail_Success() throws Exception {
        // Given
        EmailController.WelcomeEmailRequest request = new EmailController.WelcomeEmailRequest();
        request.setTo("welcome@example.com");
        request.setUsername("TestUser");

        // When & Then
        mockMvc.perform(post("/api/email/send-welcome")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome email sent successfully"));
    }

    @Test
    void testSendNotificationEmail_Success() throws Exception {
        // Given
        EmailController.NotificationEmailRequest request = new EmailController.NotificationEmailRequest();
        request.setTo("notify@example.com");
        request.setType("INFO");
        request.setMessage("Test notification message");

        // When & Then
        mockMvc.perform(post("/api/email/send-notification")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Notification email sent successfully"));
    }

    @Test
    void testGetAvailableTemplates_Success() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/email/templates"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").value("welcome-email"))
                .andExpect(jsonPath("$[1]").value("notification-email"));
    }

    @Test
    void testGetEmailLogs_Success() throws Exception {
        // Given
        EmailLogEntity log1 = new EmailLogEntity();
        log1.setId(UUID.randomUUID());
        log1.setRecipient("test1@example.com");
        log1.setSubject("Test Subject 1");
        log1.setStatus(EmailLogEntity.EmailStatus.SENT);
        log1.setSentAt(LocalDateTime.now());

        EmailLogEntity log2 = new EmailLogEntity();
        log2.setId(UUID.randomUUID());
        log2.setRecipient("test2@example.com");
        log2.setSubject("Test Subject 2");
        log2.setStatus(EmailLogEntity.EmailStatus.FAILED);
        log2.setSentAt(LocalDateTime.now());

        List<EmailLogEntity> logs = Arrays.asList(log1, log2);

        // Mock the service
        when(emailService.getEmailLogs()).thenReturn(logs);

        // When & Then
        mockMvc.perform(get("/api/email/logs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].recipient").value("test1@example.com"))
                .andExpect(jsonPath("$[1].recipient").value("test2@example.com"));
    }

    @Test
    void testGetEmailLogsByRecipient_Success() throws Exception {
        // Given
        EmailLogEntity log = new EmailLogEntity();
        log.setId(UUID.randomUUID());
        log.setRecipient("specific@example.com");
        log.setSubject("Specific Subject");
        log.setStatus(EmailLogEntity.EmailStatus.SENT);
        log.setSentAt(LocalDateTime.now());

        List<EmailLogEntity> logs = Arrays.asList(log);

        // Mock the service
        when(emailService.getEmailLogsByRecipient("specific@example.com")).thenReturn(logs);

        // When & Then
        mockMvc.perform(get("/api/email/logs/recipient/specific@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].recipient").value("specific@example.com"));
    }

    @Test
    void testGetEmailLogsByStatus_Success() throws Exception {
        // Given
        EmailLogEntity log = new EmailLogEntity();
        log.setId(UUID.randomUUID());
        log.setRecipient("status@example.com");
        log.setSubject("Status Subject");
        log.setStatus(EmailLogEntity.EmailStatus.SENT);
        log.setSentAt(LocalDateTime.now());

        List<EmailLogEntity> logs = Arrays.asList(log);

        // Mock the service
        when(emailService.getEmailLogsByStatus(EmailLogEntity.EmailStatus.SENT)).thenReturn(logs);

        // When & Then
        mockMvc.perform(get("/api/email/logs/status/SENT"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].status").value("SENT"));
    }
}
