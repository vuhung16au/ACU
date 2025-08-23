package com.acu.kafka.service;

import com.acu.kafka.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class KafkaService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);
    private static final String TOPIC_NAME = "messages-topic";
    private static final int MAX_MESSAGES_IN_MEMORY = 1000;

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    private final List<Message> receivedMessages = new ArrayList<>();
    private final AtomicLong totalMessagesSent = new AtomicLong(0);
    private final AtomicLong totalMessagesReceived = new AtomicLong(0);
    private final AtomicLong failedMessages = new AtomicLong(0);
    private final Map<Message.MessageType, AtomicLong> messageTypeCounts = new HashMap<>();
    private final Map<String, AtomicLong> senderCounts = new HashMap<>();

    public KafkaService() {
        // Initialize counters for all message types
        for (Message.MessageType type : Message.MessageType.values()) {
            messageTypeCounts.put(type, new AtomicLong(0));
        }
    }

    public CompletableFuture<SendResult<String, Message>> sendMessage(Message message) {
        if (message.getId() == null) {
            message.setId(UUID.randomUUID().toString());
        }
        
        logger.info("🚀 Sending message to Kafka - ID: {}, Type: {}, Sender: {}, Content: {}", 
                   message.getId(), message.getType(), message.getSender(), 
                   message.getContent().length() > 50 ? message.getContent().substring(0, 50) + "..." : message.getContent());
        
        return kafkaTemplate.send(TOPIC_NAME, message.getId(), message)
                .whenComplete((result, throwable) -> {
                    if (throwable == null) {
                        totalMessagesSent.incrementAndGet();
                        message.setStatus(Message.MessageStatus.SENT);
                        logger.info("✅ Message sent successfully - Topic: {}, Partition: {}, Offset: {}", 
                                  result.getRecordMetadata().topic(),
                                  result.getRecordMetadata().partition(),
                                  result.getRecordMetadata().offset());
                    } else {
                        failedMessages.incrementAndGet();
                        message.setStatus(Message.MessageStatus.FAILED);
                        logger.error("❌ Failed to send message to Kafka - ID: {}", message.getId(), throwable);
                    }
                });
    }

    @KafkaListener(topics = TOPIC_NAME, groupId = "message-group")
    public void consumeMessage(Message message) {
        totalMessagesReceived.incrementAndGet();
        message.setStatus(Message.MessageStatus.DELIVERED);
        
        // Update statistics
        messageTypeCounts.get(message.getType()).incrementAndGet();
        senderCounts.computeIfAbsent(message.getSender(), k -> new AtomicLong(0)).incrementAndGet();
        
        receivedMessages.add(message);
        
        logger.info("📨 Received message from Kafka - ID: {}, Type: {}, Sender: {}, Priority: {}", 
                   message.getId(), message.getType(), message.getSender(), message.getPriority());
        
        // Log high priority messages with special attention
        if (message.isHighPriority()) {
            logger.warn("🚨 HIGH PRIORITY MESSAGE - ID: {}, Content: {}", message.getId(), message.getContent());
        }
        
        // Log error messages
        if (message.isError()) {
            logger.error("💥 ERROR MESSAGE - ID: {}, Content: {}", message.getId(), message.getContent());
        }
        
        // Keep only last MAX_MESSAGES_IN_MEMORY messages in memory
        if (receivedMessages.size() > MAX_MESSAGES_IN_MEMORY) {
            receivedMessages.remove(0);
        }
    }

    public List<Message> getReceivedMessages() {
        return new ArrayList<>(receivedMessages);
    }
    
    public List<Message> getMessagesBySender(String sender) {
        return receivedMessages.stream()
                .filter(message -> sender.equals(message.getSender()))
                .collect(Collectors.toList());
    }
    
    public List<Message> getMessagesByType(Message.MessageType type) {
        return receivedMessages.stream()
                .filter(message -> type.equals(message.getType()))
                .collect(Collectors.toList());
    }

    public List<Message> getMessagesByRecipient(String recipient) {
        return receivedMessages.stream()
                .filter(message -> recipient.equals(message.getRecipient()))
                .collect(Collectors.toList());
    }

    public List<Message> getHighPriorityMessages() {
        return receivedMessages.stream()
                .filter(Message::isHighPriority)
                .collect(Collectors.toList());
    }

    public List<Message> getMessagesByTimeRange(LocalDateTime start, LocalDateTime end) {
        return receivedMessages.stream()
                .filter(message -> message.getTimestamp().isAfter(start) && message.getTimestamp().isBefore(end))
                .collect(Collectors.toList());
    }

    public List<Message> getRecentMessages(int count) {
        return receivedMessages.stream()
                .sorted(Comparator.comparing(Message::getTimestamp).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    public List<Message> searchMessages(String searchTerm) {
        return receivedMessages.stream()
                .filter(message -> message.getContent().toLowerCase().contains(searchTerm.toLowerCase()) ||
                                 message.getSender().toLowerCase().contains(searchTerm.toLowerCase()) ||
                                 (message.getRecipient() != null && message.getRecipient().toLowerCase().contains(searchTerm.toLowerCase())))
                .collect(Collectors.toList());
    }

    public Map<String, Object> getMessageStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalMessagesSent", totalMessagesSent.get());
        stats.put("totalMessagesReceived", totalMessagesReceived.get());
        stats.put("failedMessages", failedMessages.get());
        stats.put("messagesInMemory", receivedMessages.size());
        stats.put("successRate", totalMessagesSent.get() > 0 ? 
                  (double)(totalMessagesSent.get() - failedMessages.get()) / totalMessagesSent.get() * 100 : 0);
        
        // Message type statistics
        Map<String, Long> typeStats = new HashMap<>();
        messageTypeCounts.forEach((type, count) -> typeStats.put(type.name(), count.get()));
        stats.put("messageTypeCounts", typeStats);
        
        // Sender statistics
        Map<String, Long> senderStats = new HashMap<>();
        senderCounts.forEach((sender, count) -> senderStats.put(sender, count.get()));
        stats.put("senderCounts", senderStats);
        
        // Recent activity
        if (!receivedMessages.isEmpty()) {
            stats.put("lastMessageTime", receivedMessages.get(receivedMessages.size() - 1).getTimestamp());
            stats.put("firstMessageTime", receivedMessages.get(0).getTimestamp());
        }
        
        return stats;
    }

    public void clearMessages() {
        receivedMessages.clear();
        logger.info("🗑️ All messages cleared from memory");
    }

    public void clearStatistics() {
        totalMessagesSent.set(0);
        totalMessagesReceived.set(0);
        failedMessages.set(0);
        messageTypeCounts.values().forEach(count -> count.set(0));
        senderCounts.values().forEach(count -> count.set(0));
        logger.info("📊 All statistics cleared");
    }
}
