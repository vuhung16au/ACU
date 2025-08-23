package com.acu.kafka.controller;

import com.acu.kafka.model.Message;
import com.acu.kafka.model.MessageEntity;
import com.acu.kafka.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/messages")
public class KafkaController {

    @Autowired
    private KafkaService kafkaService;

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody Message message) {
        try {
            CompletableFuture<org.springframework.kafka.support.SendResult<String, Message>> future = 
                kafkaService.sendMessage(message);
            
            future.join(); // Wait for completion
            
            return ResponseEntity.ok("Message sent successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Failed to send message: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Message>> getMessages() {
        List<Message> messages = kafkaService.getReceivedMessages();
        return ResponseEntity.ok(messages);
    }
    
    @GetMapping("/database")
    public ResponseEntity<List<MessageEntity>> getMessagesFromDatabase() {
        List<MessageEntity> messages = kafkaService.getMessagesFromDatabase();
        return ResponseEntity.ok(messages);
    }
    
    @GetMapping("/sender/{sender}")
    public ResponseEntity<List<MessageEntity>> getMessagesBySender(@PathVariable String sender) {
        List<MessageEntity> messages = kafkaService.getMessagesBySender(sender);
        return ResponseEntity.ok(messages);
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<MessageEntity>> getMessagesByType(@PathVariable String type) {
        try {
            MessageEntity.MessageType messageType = MessageEntity.MessageType.valueOf(type.toUpperCase());
            List<MessageEntity> messages = kafkaService.getMessagesByType(messageType);
            return ResponseEntity.ok(messages);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> clearMessages() {
        kafkaService.clearMessages();
        return ResponseEntity.ok("Messages cleared successfully");
    }
}
