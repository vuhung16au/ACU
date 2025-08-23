package com.acu.kafka.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "messages")
public class MessageEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID id;
    
    @Column(name = "content", nullable = false)
    private String content;
    
    @Column(name = "sender", nullable = false)
    private String sender;
    
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "message_type", nullable = false)
    private MessageType messageType;
    
    @Column(name = "kafka_topic")
    private String kafkaTopic;
    
    @Column(name = "kafka_partition")
    private Integer kafkaPartition;
    
    @Column(name = "kafka_offset")
    private Long kafkaOffset;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    public enum MessageType {
        INFO, WARNING, ERROR, SUCCESS
    }
    
    // Default constructor
    public MessageEntity() {
        this.timestamp = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
    
    // Constructor from Message model
    public MessageEntity(Message message) {
        this();
        this.content = message.getContent();
        this.sender = message.getSender();
        this.timestamp = message.getTimestamp();
        this.messageType = MessageType.valueOf(message.getType().name());
    }
    
    // Getters and Setters
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
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
    
    public MessageType getMessageType() {
        return messageType;
    }
    
    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
    
    public String getKafkaTopic() {
        return kafkaTopic;
    }
    
    public void setKafkaTopic(String kafkaTopic) {
        this.kafkaTopic = kafkaTopic;
    }
    
    public Integer getKafkaPartition() {
        return kafkaPartition;
    }
    
    public void setKafkaPartition(Integer kafkaPartition) {
        this.kafkaPartition = kafkaPartition;
    }
    
    public Long getKafkaOffset() {
        return kafkaOffset;
    }
    
    public void setKafkaOffset(Long kafkaOffset) {
        this.kafkaOffset = kafkaOffset;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "MessageEntity{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", timestamp=" + timestamp +
                ", messageType=" + messageType +
                ", kafkaTopic='" + kafkaTopic + '\'' +
                '}';
    }
}
