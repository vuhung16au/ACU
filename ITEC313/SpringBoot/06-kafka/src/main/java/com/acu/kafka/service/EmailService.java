package com.acu.kafka.service;

import com.acu.kafka.model.EmailLogEntity;
import com.acu.kafka.repository.EmailLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;
    
    @Autowired
    private EmailLogRepository emailLogRepository;

    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        
        try {
            mailSender.send(message);
            logger.info("Simple email sent successfully to: {}", to);
            
            // Log successful email
            EmailLogEntity emailLog = new EmailLogEntity(to, subject, "simple");
            emailLogRepository.save(emailLog);
        } catch (Exception e) {
            logger.error("Failed to send simple email to: {}", to, e);
            
            // Log failed email
            EmailLogEntity emailLog = new EmailLogEntity(to, subject, "simple", e.getMessage());
            emailLogRepository.save(emailLog);
            
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public void sendTemplatedEmail(String to, String subject, String templateName, Map<String, Object> variables) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setTo(to);
            helper.setSubject(subject);
            
            Context context = new Context();
            if (variables != null) {
                variables.forEach(context::setVariable);
            }
            
            String htmlContent = templateEngine.process(templateName, context);
            helper.setText(htmlContent, true);
            
            mailSender.send(message);
            logger.info("Templated email sent successfully to: {}", to);
            
            // Log successful email
            EmailLogEntity emailLog = new EmailLogEntity(to, subject, templateName);
            emailLogRepository.save(emailLog);
        } catch (MessagingException e) {
            logger.error("Failed to send templated email to: {}", to, e);
            
            // Log failed email
            EmailLogEntity emailLog = new EmailLogEntity(to, subject, templateName, e.getMessage());
            emailLogRepository.save(emailLog);
            
            throw new RuntimeException("Failed to send templated email", e);
        }
    }

    public void sendWelcomeEmail(String to, String username) {
        Map<String, Object> variables = Map.of(
            "username", username,
            "welcomeMessage", "Welcome to our Spring Boot Kafka application!"
        );
        
        sendTemplatedEmail(to, "Welcome to Spring Boot Kafka", "welcome-email", variables);
    }

    public void sendNotificationEmail(String to, String notificationType, String message) {
        Map<String, Object> variables = Map.of(
            "notificationType", notificationType,
            "message", message,
            "timestamp", java.time.LocalDateTime.now()
        );
        
        sendTemplatedEmail(to, "Notification: " + notificationType, "notification-email", variables);
    }
    
    public List<EmailLogEntity> getEmailLogs() {
        return emailLogRepository.findAll();
    }
    
    public List<EmailLogEntity> getEmailLogsByRecipient(String recipient) {
        return emailLogRepository.findByRecipientOrderBySentAtDesc(recipient);
    }
    
    public List<EmailLogEntity> getEmailLogsByStatus(EmailLogEntity.EmailStatus status) {
        return emailLogRepository.findByStatusOrderBySentAtDesc(status);
    }
}
