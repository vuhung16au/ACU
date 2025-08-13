package com.acu.kafka.model;

import java.time.LocalDateTime;

public class Message {
    private String id;
    private String content;
    private String sender;
    private LocalDateTime timestamp;
    private MessageType type;

    public enum MessageType {
        INFO, WARNING, ERROR, SUCCESS
    }

    // Default constructor
    public Message() {
        this.timestamp = LocalDateTime.now();
    }

    // Constructor with parameters
    public Message(String content, String sender, MessageType type) {
        this();
        this.content = content;
        this.sender = sender;
        this.type = type;
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

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", timestamp=" + timestamp +
                ", type=" + type +
                '}';
    }
}
