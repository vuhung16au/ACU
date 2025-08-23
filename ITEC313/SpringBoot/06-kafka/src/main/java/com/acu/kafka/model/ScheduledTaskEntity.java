package com.acu.kafka.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "scheduled_tasks")
public class ScheduledTaskEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID id;
    
    @Column(name = "task_name", nullable = false)
    private String taskName;
    
    @Column(name = "execution_time", nullable = false)
    private LocalDateTime executionTime;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status;
    
    @Column(name = "duration_ms")
    private Integer durationMs;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    public enum TaskStatus {
        COMPLETED, FAILED, RUNNING
    }
    
    // Default constructor
    public ScheduledTaskEntity() {
        this.createdAt = LocalDateTime.now();
    }
    
    // Constructor for task execution
    public ScheduledTaskEntity(String taskName) {
        this();
        this.taskName = taskName;
        this.executionTime = LocalDateTime.now();
        this.status = TaskStatus.RUNNING;
    }
    
    // Getters and Setters
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public String getTaskName() {
        return taskName;
    }
    
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    
    public LocalDateTime getExecutionTime() {
        return executionTime;
    }
    
    public void setExecutionTime(LocalDateTime executionTime) {
        this.executionTime = executionTime;
    }
    
    public TaskStatus getStatus() {
        return status;
    }
    
    public void setStatus(TaskStatus status) {
        this.status = status;
    }
    
    public Integer getDurationMs() {
        return durationMs;
    }
    
    public void setDurationMs(Integer durationMs) {
        this.durationMs = durationMs;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "ScheduledTaskEntity{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", executionTime=" + executionTime +
                ", status=" + status +
                ", durationMs=" + durationMs +
                '}';
    }
}
