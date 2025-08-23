package com.acu.kafka.service;

import com.acu.kafka.config.TestConfig;
import com.acu.kafka.model.EmailLogEntity;
import com.acu.kafka.repository.EmailLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestConfig.class)
@Transactional
class EmailServiceIntegrationTest {

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailLogRepository emailLogRepository;

    @BeforeEach
    void setUp() {
        emailLogRepository.deleteAll();
    }

    @Test
    void testSendSimpleEmail_LogsToDatabase() {
        // Given
        String to = "test@example.com";
        String subject = "Test Subject";
        String text = "Test email content";

        // When
        emailService.sendSimpleEmail(to, subject, text);

        // Then
        List<EmailLogEntity> logs = emailLogRepository.findAll();
        assertEquals(1, logs.size());
        
        EmailLogEntity log = logs.get(0);
        assertEquals(to, log.getRecipient());
        assertEquals(subject, log.getSubject());
        assertEquals("simple", log.getTemplateName());
        assertEquals(EmailLogEntity.EmailStatus.SENT, log.getStatus());
        assertNull(log.getErrorMessage());
    }

    @Test
    void testSendTemplatedEmail_LogsToDatabase() {
        // Given
        String to = "templated@example.com";
        String subject = "Templated Subject";
        String templateName = "welcome-email";

        // When
        emailService.sendTemplatedEmail(to, subject, templateName, null);

        // Then
        List<EmailLogEntity> logs = emailLogRepository.findAll();
        assertEquals(1, logs.size());
        
        EmailLogEntity log = logs.get(0);
        assertEquals(to, log.getRecipient());
        assertEquals(subject, log.getSubject());
        assertEquals(templateName, log.getTemplateName());
        assertEquals(EmailLogEntity.EmailStatus.SENT, log.getStatus());
        assertNull(log.getErrorMessage());
    }

    @Test
    void testSendWelcomeEmail_LogsToDatabase() {
        // Given
        String to = "welcome@example.com";
        String username = "TestUser";

        // When
        emailService.sendWelcomeEmail(to, username);

        // Then
        List<EmailLogEntity> logs = emailLogRepository.findAll();
        assertEquals(1, logs.size());
        
        EmailLogEntity log = logs.get(0);
        assertEquals(to, log.getRecipient());
        assertEquals("Welcome to Spring Boot Kafka", log.getSubject());
        assertEquals("welcome-email", log.getTemplateName());
        assertEquals(EmailLogEntity.EmailStatus.SENT, log.getStatus());
    }

    @Test
    void testSendNotificationEmail_LogsToDatabase() {
        // Given
        String to = "notification@example.com";
        String notificationType = "INFO";
        String message = "Test notification message";

        // When
        emailService.sendNotificationEmail(to, notificationType, message);

        // Then
        List<EmailLogEntity> logs = emailLogRepository.findAll();
        assertEquals(1, logs.size());
        
        EmailLogEntity log = logs.get(0);
        assertEquals(to, log.getRecipient());
        assertEquals("Notification: " + notificationType, log.getSubject());
        assertEquals("notification-email", log.getTemplateName());
        assertEquals(EmailLogEntity.EmailStatus.SENT, log.getStatus());
    }

    @Test
    void testGetEmailLogs() {
        // Given
        emailService.sendSimpleEmail("test1@example.com", "Subject 1", "Content 1");
        emailService.sendSimpleEmail("test2@example.com", "Subject 2", "Content 2");

        // When
        List<EmailLogEntity> logs = emailService.getEmailLogs();

        // Then
        assertEquals(2, logs.size());
    }

    @Test
    void testGetEmailLogsByRecipient() {
        // Given
        String recipient = "test@example.com";
        emailService.sendSimpleEmail(recipient, "Subject 1", "Content 1");
        emailService.sendSimpleEmail(recipient, "Subject 2", "Content 2");
        emailService.sendSimpleEmail("other@example.com", "Subject 3", "Content 3");

        // When
        List<EmailLogEntity> logs = emailService.getEmailLogsByRecipient(recipient);

        // Then
        assertEquals(2, logs.size());
        logs.forEach(log -> assertEquals(recipient, log.getRecipient()));
    }

    @Test
    void testGetEmailLogsByStatus() {
        // Given
        emailService.sendSimpleEmail("test1@example.com", "Subject 1", "Content 1");
        emailService.sendSimpleEmail("test2@example.com", "Subject 2", "Content 2");

        // When
        List<EmailLogEntity> logs = emailService.getEmailLogsByStatus(EmailLogEntity.EmailStatus.SENT);

        // Then
        assertEquals(2, logs.size());
        logs.forEach(log -> assertEquals(EmailLogEntity.EmailStatus.SENT, log.getStatus()));
    }
}
