package com.acu.kafka.service;

import com.acu.kafka.model.ScheduledTaskEntity;
import com.acu.kafka.repository.ScheduledTaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerService.class);
    
    @Autowired
    private KafkaService kafkaService;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private ScheduledTaskRepository scheduledTaskRepository;

    @Value("${scheduling.enabled:true}")
    private boolean schedulingEnabled;

    private final AtomicInteger taskCounter = new AtomicInteger(0);
    private final AtomicInteger emailCounter = new AtomicInteger(0);

    // Run every 30 seconds
    @Scheduled(fixedRate = 30000)
    public void scheduledTask() {
        // Skip if scheduling is disabled
        if (!schedulingEnabled) {
            return;
        }
        
        int count = taskCounter.incrementAndGet();
        logger.info("Scheduled task #{} executed at: {}", count, LocalDateTime.now());
        
        // Create task record
        ScheduledTaskEntity task = new ScheduledTaskEntity("scheduledTask");
        task.setStatus(ScheduledTaskEntity.TaskStatus.RUNNING);
        scheduledTaskRepository.save(task);
        
        try {
            // Send a message to Kafka
            com.acu.kafka.model.Message message = new com.acu.kafka.model.Message(
                "Scheduled task #" + count + " executed",
                "SchedulerService",
                com.acu.kafka.model.Message.MessageType.INFO
            );
            
            kafkaService.sendMessage(message);
            
            // Update task status to completed
            task.setStatus(ScheduledTaskEntity.TaskStatus.COMPLETED);
            long duration = System.currentTimeMillis() - task.getCreatedAt().atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
            task.setDurationMs((int) duration);
            scheduledTaskRepository.save(task);
        } catch (Exception e) {
            logger.error("Scheduled task failed", e);
            task.setStatus(ScheduledTaskEntity.TaskStatus.FAILED);
            scheduledTaskRepository.save(task);
        }
    }

    // Run every minute
    @Scheduled(cron = "0 * * * * *")
    public void minuteTask() {
        // Skip if scheduling is disabled
        if (!schedulingEnabled) {
            return;
        }
        
        logger.info("Minute task executed at: {}", LocalDateTime.now());
    }

    // Run every hour
    @Scheduled(cron = "0 0 * * * *")
    public void hourlyTask() {
        // Skip if scheduling is disabled
        if (!schedulingEnabled) {
            return;
        }
        
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
        // Skip if scheduling is disabled
        if (!schedulingEnabled) {
            return;
        }
        
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

    // Run every 5 seconds for testing purposes
    @Scheduled(fixedRate = 5000)
    public void testTask() {
        // Skip if scheduling is disabled
        if (!schedulingEnabled) {
            return;
        }
        
        int count = taskCounter.incrementAndGet();
        logger.info("Test task #{} executed at: {}", count, LocalDateTime.now());
        
        // Create a test message and save it to the database
        try {
            com.acu.kafka.model.Message message = new com.acu.kafka.model.Message(
                "Database test message",
                "TestScheduler",
                com.acu.kafka.model.Message.MessageType.INFO
            );
            
            kafkaService.consumeMessage(message);
            
            // Create task record
            ScheduledTaskEntity task = new ScheduledTaskEntity("testTask");
            task.setStatus(ScheduledTaskEntity.TaskStatus.COMPLETED);
            task.setDurationMs(100);
            scheduledTaskRepository.save(task);
            
        } catch (Exception e) {
            logger.error("Test task failed", e);
        }
    }

    public void resetCounters() {
        taskCounter.set(0);
        emailCounter.set(0);
        logger.info("Counters reset");
    }

    public int getTaskCount() {
        return taskCounter.get();
    }

    public int getEmailCount() {
        return emailCounter.get();
    }

    public List<ScheduledTaskEntity> getAllTasks() {
        return scheduledTaskRepository.findAll();
    }

    public List<ScheduledTaskEntity> getScheduledTasks() {
        return scheduledTaskRepository.findAll();
    }

    public List<ScheduledTaskEntity> getScheduledTasksByStatus(ScheduledTaskEntity.TaskStatus status) {
        return scheduledTaskRepository.findByStatusOrderByExecutionTimeDesc(status);
    }

    public List<ScheduledTaskEntity> getScheduledTasksByName(String taskName) {
        return scheduledTaskRepository.findByTaskNameOrderByExecutionTimeDesc(taskName);
    }

    public void clearAllTasks() {
        scheduledTaskRepository.deleteAll();
        logger.info("All scheduled tasks cleared");
    }
}
