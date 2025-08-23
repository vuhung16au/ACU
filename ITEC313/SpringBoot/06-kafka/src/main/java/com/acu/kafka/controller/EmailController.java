package com.acu.kafka.controller;

import com.acu.kafka.model.EmailLogEntity;
import com.acu.kafka.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest request) {
        try {
            emailService.sendSimpleEmail(request.getTo(), request.getSubject(), request.getText());
            return ResponseEntity.ok("Email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Failed to send email: " + e.getMessage());
        }
    }

    @PostMapping("/send-welcome")
    public ResponseEntity<String> sendWelcomeEmail(@RequestBody WelcomeEmailRequest request) {
        try {
            emailService.sendWelcomeEmail(request.getTo(), request.getUsername());
            return ResponseEntity.ok("Welcome email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Failed to send welcome email: " + e.getMessage());
        }
    }

    @PostMapping("/send-notification")
    public ResponseEntity<String> sendNotificationEmail(@RequestBody NotificationEmailRequest request) {
        try {
            emailService.sendNotificationEmail(request.getTo(), request.getType(), request.getMessage());
            return ResponseEntity.ok("Notification email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Failed to send notification email: " + e.getMessage());
        }
    }

    @GetMapping("/templates")
    public ResponseEntity<List<String>> getAvailableTemplates() {
        List<String> templates = List.of("welcome-email", "notification-email");
        return ResponseEntity.ok(templates);
    }
    
    @GetMapping("/logs")
    public ResponseEntity<List<EmailLogEntity>> getEmailLogs() {
        List<EmailLogEntity> logs = emailService.getEmailLogs();
        return ResponseEntity.ok(logs);
    }
    
    @GetMapping("/logs/recipient/{recipient}")
    public ResponseEntity<List<EmailLogEntity>> getEmailLogsByRecipient(@PathVariable String recipient) {
        List<EmailLogEntity> logs = emailService.getEmailLogsByRecipient(recipient);
        return ResponseEntity.ok(logs);
    }
    
    @GetMapping("/logs/status/{status}")
    public ResponseEntity<List<EmailLogEntity>> getEmailLogsByStatus(@PathVariable String status) {
        try {
            EmailLogEntity.EmailStatus emailStatus = EmailLogEntity.EmailStatus.valueOf(status.toUpperCase());
            List<EmailLogEntity> logs = emailService.getEmailLogsByStatus(emailStatus);
            return ResponseEntity.ok(logs);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Request DTOs
    public static class EmailRequest {
        private String to;
        private String subject;
        private String text;

        // Getters and Setters
        public String getTo() { return to; }
        public void setTo(String to) { this.to = to; }
        public String getSubject() { return subject; }
        public void setSubject(String subject) { this.subject = subject; }
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
    }

    public static class WelcomeEmailRequest {
        private String to;
        private String username;

        // Getters and Setters
        public String getTo() { return to; }
        public void setTo(String to) { this.to = to; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
    }

    public static class NotificationEmailRequest {
        private String to;
        private String type;
        private String message;

        // Getters and Setters
        public String getTo() { return to; }
        public void setTo(String to) { this.to = to; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}
