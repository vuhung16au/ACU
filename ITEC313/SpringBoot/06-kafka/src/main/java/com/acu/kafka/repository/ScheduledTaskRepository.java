package com.acu.kafka.repository;

import com.acu.kafka.model.ScheduledTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduledTaskRepository extends JpaRepository<ScheduledTaskEntity, java.util.UUID> {
    
    List<ScheduledTaskEntity> findByTaskNameOrderByExecutionTimeDesc(String taskName);
    
    List<ScheduledTaskEntity> findByStatusOrderByExecutionTimeDesc(ScheduledTaskEntity.TaskStatus status);
    
    List<ScheduledTaskEntity> findByExecutionTimeBetweenOrderByExecutionTimeDesc(LocalDateTime start, LocalDateTime end);
    
    List<ScheduledTaskEntity> findByTaskNameAndStatusOrderByExecutionTimeDesc(String taskName, ScheduledTaskEntity.TaskStatus status);
}
