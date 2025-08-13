package com.acu.kafka.service;

import com.acu.kafka.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class KafkaService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);
    private static final String TOPIC_NAME = "messages-topic";

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    private final List<Message> receivedMessages = new ArrayList<>();

    public CompletableFuture<SendResult<String, Message>> sendMessage(Message message) {
        message.setId(UUID.randomUUID().toString());
        logger.info("Sending message to Kafka: {}", message);
        
        return kafkaTemplate.send(TOPIC_NAME, message.getId(), message)
                .whenComplete((result, throwable) -> {
                    if (throwable == null) {
                        logger.info("Message sent successfully to topic: {}", result.getRecordMetadata().topic());
                    } else {
                        logger.error("Failed to send message to Kafka", throwable);
                    }
                });
    }

    @KafkaListener(topics = TOPIC_NAME, groupId = "message-group")
    public void consumeMessage(Message message) {
        logger.info("Received message from Kafka: {}", message);
        receivedMessages.add(message);
        
        // Keep only last 100 messages
        if (receivedMessages.size() > 100) {
            receivedMessages.remove(0);
        }
    }

    public List<Message> getReceivedMessages() {
        return new ArrayList<>(receivedMessages);
    }

    public void clearMessages() {
        receivedMessages.clear();
    }
}
