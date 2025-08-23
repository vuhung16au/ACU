package com.acu.kafka.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public class Message {
    private String id;
    private String content;
    private String sender;
    private String recipient;
    private String topic;
    private Integer priority;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    
    private MessageType type;
    private MessageStatus status;

    public enum MessageType {
        INFO("Information message"),
        WARNING("Warning message"), 
        ERROR("Error message"), 
        SUCCESS("Success message"),
        DEBUG("Debug message"),
        ALERT("Alert message");

        private final String description;

        MessageType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum MessageStatus {
        PENDING("Pending"),
        SENT("Sent"),
        DELIVERED("Delivered"),
        READ("Read"),
        FAILED("Failed");

        private final String description;

        MessageStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    // Default constructor
    public Message() {
        this.id = UUID.randomUUID().toString();
        this.timestamp = LocalDateTime.now();
        this.status = MessageStatus.PENDING;
        this.priority = 1;
    }

    // Constructor with parameters
    public Message(String content, String sender, MessageType type) {
        this();
        this.content = content;
        this.sender = sender;
        this.type = type;
    }

    // Constructor with all parameters
    public Message(String content, String sender, String recipient, String topic, 
                   Integer priority, MessageType type) {
        this();
        this.content = content;
        this.sender = sender;
        this.recipient = recipient;
        this.topic = topic;
        this.priority = priority;
        this.type = type;
    }

    // Static factory methods for common message types
    public static Message info(String content, String sender) {
        return new Message(content, sender, MessageType.INFO);
    }

    public static Message warning(String content, String sender) {
        return new Message(content, sender, MessageType.WARNING);
    }

    public static Message error(String content, String sender) {
        return new Message(content, sender, MessageType.ERROR);
    }

    public static Message success(String content, String sender) {
        return new Message(content, sender, MessageType.SUCCESS);
    }

    public static Message alert(String content, String sender, String recipient) {
        Message message = new Message(content, sender, MessageType.ALERT);
        message.setRecipient(recipient);
        message.setPriority(5); // High priority for alerts
        return message;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    // Utility methods
    public boolean isHighPriority() {
        return priority != null && priority >= 4;
    }

    public boolean isAlert() {
        return MessageType.ALERT.equals(type);
    }

    public boolean isError() {
        return MessageType.ERROR.equals(type);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                ", topic='" + topic + '\'' +
                ", priority=" + priority +
                ", timestamp=" + timestamp +
                ", type=" + type +
                ", status=" + status +
                '}';
    }
}
