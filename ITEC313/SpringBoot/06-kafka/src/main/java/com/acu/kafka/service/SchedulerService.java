package com.acu.kafka.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerService.class);
    
    @Autowired
    private KafkaService kafkaService;
    
    @Autowired
    private EmailService emailService;

    private final AtomicInteger taskCounter = new AtomicInteger(0);
    private final AtomicInteger emailCounter = new AtomicInteger(0);

    // Run every 30 seconds
    @Scheduled(fixedRate = 30000)
    public void scheduledTask() {
        int count = taskCounter.incrementAndGet();
        logger.info("Scheduled task #{} executed at: {}", count, LocalDateTime.now());
        
        // Send a message to Kafka
        com.acu.kafka.model.Message message = new com.acu.kafka.model.Message(
            "Scheduled task #" + count + " executed",
            "SchedulerService",
            com.acu.kafka.model.Message.MessageType.INFO
        );
        
        kafkaService.sendMessage(message);
    }

    // Run every minute
    @Scheduled(cron = "0 * * * * *")
    public void minuteTask() {
        logger.info("Minute task executed at: {}", LocalDateTime.now());
    }

    // Run every hour
    @Scheduled(cron = "0 0 * * * *")
    public void hourlyTask() {
        logger.info("Hourly task executed at: {}", LocalDateTime.now());
        
        // Send a notification email (in a real app, this would be to actual users)
        try {
            emailService.sendNotificationEmail(
                "admin@example.com",
                "Hourly Report",
                "Hourly scheduled task completed successfully at " + LocalDateTime.now()
            );
        } catch (Exception e) {
            logger.error("Failed to send hourly notification email", e);
        }
    }

    // Run daily at 9 AM
    @Scheduled(cron = "0 0 9 * * *")
    public void dailyTask() {
        logger.info("Daily task executed at: {}", LocalDateTime.now());
        
        // Send a daily summary email
        try {
            emailService.sendNotificationEmail(
                "admin@example.com",
                "Daily Summary",
                "Daily scheduled task completed. Total tasks executed today: " + taskCounter.get()
            );
        } catch (Exception e) {
            logger.error("Failed to send daily summary email", e);
        }
    }

    public int getTaskCount() {
        return taskCounter.get();
    }

    public int getEmailCount() {
        return emailCounter.get();
    }

    public void resetCounters() {
        taskCounter.set(0);
        emailCounter.set(0);
    }
}
