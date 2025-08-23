package com.acu.kafka.service;

import com.acu.kafka.model.EmailLogEntity;
import com.acu.kafka.repository.EmailLogRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;

import jakarta.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @MockBean
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailLogRepository emailLogRepository;

    @Test
    void testSendSimpleEmail_LogsToDatabase() {
        // Given
        String to = "test@example.com";
        String subject = "Test Subject";
        String text = "Test message content";

        // Mock the mail sender to do nothing (avoid actual email sending)
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

        // When
        emailService.sendSimpleEmail(to, subject, text);

        // Then
        // Verify that the email was logged to database
        EmailLogEntity emailLog = emailLogRepository.findTopByOrderBySentAtDesc().orElse(null);
        assertNotNull(emailLog);
        assertEquals(to, emailLog.getRecipient());
        assertEquals(subject, emailLog.getSubject());
        assertEquals(EmailLogEntity.EmailStatus.SENT, emailLog.getStatus());
        
        // Verify that send method was called
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void testSendWelcomeEmail_LogsToDatabase() {
        // Given
        String to = "welcome@example.com";
        String name = "John Doe";

        // Mock the mail sender to handle MimeMessage
        MimeMessage mockMimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mockMimeMessage);
        doNothing().when(javaMailSender).send(any(MimeMessage.class));

        // When
        emailService.sendWelcomeEmail(to, name);

        // Then
        // Verify that the email was logged to database
        EmailLogEntity emailLog = emailLogRepository.findTopByOrderBySentAtDesc().orElse(null);
        assertNotNull(emailLog);
        assertEquals(to, emailLog.getRecipient());
        assertTrue(emailLog.getSubject().contains("Welcome"));
        assertEquals(EmailLogEntity.EmailStatus.SENT, emailLog.getStatus());
        
        // Verify that send method was called
        verify(javaMailSender, times(1)).send(any(MimeMessage.class));
    }

    @Test
    void testSendNotificationEmail_LogsToDatabase() {
        // Given
        String to = "notification@example.com";
        String title = "Important Notification";
        String message = "This is an important message";

        // Mock the mail sender to handle MimeMessage
        MimeMessage mockMimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mockMimeMessage);
        doNothing().when(javaMailSender).send(any(MimeMessage.class));

        // When
        emailService.sendNotificationEmail(to, title, message);

        // Then
        // Verify that the email was logged to database
        EmailLogEntity emailLog = emailLogRepository.findTopByOrderBySentAtDesc().orElse(null);
        assertNotNull(emailLog);
        assertEquals(to, emailLog.getRecipient());
        assertTrue(emailLog.getSubject().contains("Notification"));
        assertEquals(EmailLogEntity.EmailStatus.SENT, emailLog.getStatus());
        
        // Verify that send method was called
        verify(javaMailSender, times(1)).send(any(MimeMessage.class));
    }

    @Test
    void testEmailService_HandlesFailure() {
        // Given
        String to = "failure@example.com";
        String subject = "Test Subject";
        String text = "Test message content";

        // Mock the mail sender to throw an exception
        doThrow(new RuntimeException("Mail server error")).when(javaMailSender).send(any(SimpleMailMessage.class));

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            emailService.sendSimpleEmail(to, subject, text);
        });

        // Verify that the failure was logged to database
        EmailLogEntity emailLog = emailLogRepository.findTopByOrderBySentAtDesc().orElse(null);
        assertNotNull(emailLog);
        assertEquals(to, emailLog.getRecipient());
        assertEquals(EmailLogEntity.EmailStatus.FAILED, emailLog.getStatus());
    }
}
