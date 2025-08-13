package com.acu.kafka.controller;

import com.acu.kafka.model.Message;
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

    @DeleteMapping
    public ResponseEntity<String> clearMessages() {
        kafkaService.clearMessages();
        return ResponseEntity.ok("Messages cleared successfully");
    }
}
