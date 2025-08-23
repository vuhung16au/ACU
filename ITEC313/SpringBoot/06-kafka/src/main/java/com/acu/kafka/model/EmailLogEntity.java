package com.acu.kafka.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "email_logs")
public class EmailLogEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID id;
    
    @Column(name = "recipient", nullable = false)
    private String recipient;
    
    @Column(name = "subject", nullable = false)
    private String subject;
    
    @Column(name = "template_name")
    private String templateName;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EmailStatus status;
    
    @Column(name = "sent_at")
    private LocalDateTime sentAt;
    
    @Column(name = "error_message")
    private String errorMessage;
    
    public enum EmailStatus {
        SENT, FAILED, PENDING
    }
    
    // Default constructor
    public EmailLogEntity() {
        this.sentAt = LocalDateTime.now();
    }
    
    // Constructor for successful email
    public EmailLogEntity(String recipient, String subject, String templateName) {
        this();
        this.recipient = recipient;
        this.subject = subject;
        this.templateName = templateName;
        this.status = EmailStatus.SENT;
    }
    
    // Constructor for failed email
    public EmailLogEntity(String recipient, String subject, String templateName, String errorMessage) {
        this();
        this.recipient = recipient;
        this.subject = subject;
        this.templateName = templateName;
        this.status = EmailStatus.FAILED;
        this.errorMessage = errorMessage;
    }
    
    // Getters and Setters
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public String getRecipient() {
        return recipient;
    }
    
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public String getTemplateName() {
        return templateName;
    }
    
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
    
    public EmailStatus getStatus() {
        return status;
    }
    
    public void setStatus(EmailStatus status) {
        this.status = status;
    }
    
    public LocalDateTime getSentAt() {
        return sentAt;
    }
    
    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    @Override
    public String toString() {
        return "EmailLogEntity{" +
                "id=" + id +
                ", recipient='" + recipient + '\'' +
                ", subject='" + subject + '\'' +
                ", templateName='" + templateName + '\'' +
                ", status=" + status +
                ", sentAt=" + sentAt +
                '}';
    }
}
