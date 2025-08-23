package com.acu.kafka.controller;

import com.acu.kafka.model.Message;
import com.acu.kafka.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*")
public class KafkaController {

    @Autowired
    private KafkaService kafkaService;

    // Basic message operations
    @PostMapping
    public ResponseEntity<Map<String, Object>> sendMessage(@RequestBody Message message) {
        try {
            CompletableFuture<org.springframework.kafka.support.SendResult<String, Message>> future = 
                kafkaService.sendMessage(message);
            
            future.join(); // Wait for completion
            
            Map<String, Object> response = Map.of(
                "status", "success",
                "message", "Message sent successfully",
                "messageId", message.getId(),
                "timestamp", LocalDateTime.now()
            );
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = Map.of(
                "status", "error",
                "message", "Failed to send message: " + e.getMessage(),
                "timestamp", LocalDateTime.now()
            );
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<List<Message>> getMessages() {
        List<Message> messages = kafkaService.getReceivedMessages();
        return ResponseEntity.ok(messages);
    }

    // Message filtering endpoints
    @GetMapping("/sender/{sender}")
    public ResponseEntity<List<Message>> getMessagesBySender(@PathVariable String sender) {
        List<Message> messages = kafkaService.getMessagesBySender(sender);
        return ResponseEntity.ok(messages);
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Message>> getMessagesByType(@PathVariable String type) {
        try {
            Message.MessageType messageType = Message.MessageType.valueOf(type.toUpperCase());
            List<Message> messages = kafkaService.getMessagesByType(messageType);
            return ResponseEntity.ok(messages);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/recipient/{recipient}")
    public ResponseEntity<List<Message>> getMessagesByRecipient(@PathVariable String recipient) {
        List<Message> messages = kafkaService.getMessagesByRecipient(recipient);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/priority/high")
    public ResponseEntity<List<Message>> getHighPriorityMessages() {
        List<Message> messages = kafkaService.getHighPriorityMessages();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/recent/{count}")
    public ResponseEntity<List<Message>> getRecentMessages(@PathVariable int count) {
        if (count <= 0 || count > 100) {
            return ResponseEntity.badRequest().build();
        }
        List<Message> messages = kafkaService.getRecentMessages(count);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Message>> searchMessages(@RequestParam String q) {
        if (q == null || q.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<Message> messages = kafkaService.searchMessages(q.trim());
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/timerange")
    public ResponseEntity<List<Message>> getMessagesByTimeRange(
            @RequestParam String start,
            @RequestParam String end) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime startTime = LocalDateTime.parse(start, formatter);
            LocalDateTime endTime = LocalDateTime.parse(end, formatter);
            
            List<Message> messages = kafkaService.getMessagesByTimeRange(startTime, endTime);
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Statistics and monitoring endpoints
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getMessageStatistics() {
        Map<String, Object> stats = kafkaService.getMessageStatistics();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/stats/summary")
    public ResponseEntity<Map<String, Object>> getMessageSummary() {
        Map<String, Object> fullStats = kafkaService.getMessageStatistics();
        
        Map<String, Object> summary = Map.of(
            "totalMessagesSent", fullStats.get("totalMessagesSent"),
            "totalMessagesReceived", fullStats.get("totalMessagesReceived"),
            "successRate", fullStats.get("successRate"),
            "messagesInMemory", fullStats.get("messagesInMemory"),
            "lastMessageTime", fullStats.get("lastMessageTime")
        );
        
        return ResponseEntity.ok(summary);
    }

    // Demo endpoints for testing different message types
    @PostMapping("/demo/info")
    public ResponseEntity<Map<String, Object>> sendInfoMessage(@RequestBody Map<String, String> request) {
        Message message = Message.info(
            request.get("content"), 
            request.get("sender")
        );
        return sendMessage(message);
    }

    @PostMapping("/demo/warning")
    public ResponseEntity<Map<String, Object>> sendWarningMessage(@RequestBody Map<String, String> request) {
        Message message = Message.warning(
            request.get("content"), 
            request.get("sender")
        );
        return sendMessage(message);
    }

    @PostMapping("/demo/error")
    public ResponseEntity<Map<String, Object>> sendErrorMessage(@RequestBody Map<String, String> request) {
        Message message = Message.error(
            request.get("content"), 
            request.get("sender")
        );
        return sendMessage(message);
    }

    @PostMapping("/demo/success")
    public ResponseEntity<Map<String, Object>> sendSuccessMessage(@RequestBody Map<String, String> request) {
        Message message = Message.success(
            request.get("content"), 
            request.get("sender")
        );
        return sendMessage(message);
    }

    @PostMapping("/demo/alert")
    public ResponseEntity<Map<String, Object>> sendAlertMessage(@RequestBody Map<String, String> request) {
        Message message = Message.alert(
            request.get("content"), 
            request.get("sender"),
            request.get("recipient")
        );
        return sendMessage(message);
    }

    // Bulk operations
    @PostMapping("/demo/bulk")
    public ResponseEntity<Map<String, Object>> sendBulkMessages(@RequestBody List<Message> messages) {
        if (messages.size() > 10) {
            return ResponseEntity.badRequest().body(Map.of(
                "status", "error",
                "message", "Maximum 10 messages allowed in bulk operation"
            ));
        }

        int successCount = 0;
        int failureCount = 0;

        for (Message message : messages) {
            try {
                CompletableFuture<org.springframework.kafka.support.SendResult<String, Message>> future = 
                    kafkaService.sendMessage(message);
                future.join();
                successCount++;
            } catch (Exception e) {
                failureCount++;
            }
        }

        Map<String, Object> response = Map.of(
            "status", "completed",
            "totalMessages", messages.size(),
            "successCount", successCount,
            "failureCount", failureCount,
            "timestamp", LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    // Management endpoints
    @DeleteMapping
    public ResponseEntity<Map<String, Object>> clearMessages() {
        kafkaService.clearMessages();
        return ResponseEntity.ok(Map.of(
            "status", "success",
            "message", "Messages cleared successfully",
            "timestamp", LocalDateTime.now()
        ));
    }

    @DeleteMapping("/stats")
    public ResponseEntity<Map<String, Object>> clearStatistics() {
        kafkaService.clearStatistics();
        return ResponseEntity.ok(Map.of(
            "status", "success",
            "message", "Statistics cleared successfully",
            "timestamp", LocalDateTime.now()
        ));
    }

    // Health check endpoint
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> health = Map.of(
            "status", "UP",
            "service", "Kafka Message Service",
            "timestamp", LocalDateTime.now(),
            "version", "1.0.0"
        );
        return ResponseEntity.ok(health);
    }
}
