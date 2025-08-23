package com.acu.kafka.controller;

import com.acu.kafka.model.ScheduledTaskEntity;
import com.acu.kafka.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/scheduler")
public class SchedulerController {

    @Autowired
    private SchedulerService schedulerService;

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus() {
        Map<String, Object> status = Map.of(
            "taskCount", schedulerService.getTaskCount(),
            "emailCount", schedulerService.getEmailCount(),
            "status", "running"
        );
        return ResponseEntity.ok(status);
    }

    @PostMapping("/trigger")
    public ResponseEntity<String> triggerTask() {
        // This would trigger a manual task execution
        // For now, we'll just return a success message
        return ResponseEntity.ok("Task triggered successfully");
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetCounters() {
        schedulerService.resetCounters();
        return ResponseEntity.ok("Counters reset successfully");
    }
    
    @GetMapping("/tasks")
    public ResponseEntity<List<ScheduledTaskEntity>> getScheduledTasks() {
        List<ScheduledTaskEntity> tasks = schedulerService.getScheduledTasks();
        return ResponseEntity.ok(tasks);
    }
    
    @GetMapping("/tasks/status/{status}")
    public ResponseEntity<List<ScheduledTaskEntity>> getScheduledTasksByStatus(@PathVariable String status) {
        try {
            ScheduledTaskEntity.TaskStatus taskStatus = ScheduledTaskEntity.TaskStatus.valueOf(status.toUpperCase());
            List<ScheduledTaskEntity> tasks = schedulerService.getScheduledTasksByStatus(taskStatus);
            return ResponseEntity.ok(tasks);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/tasks/name/{taskName}")
    public ResponseEntity<List<ScheduledTaskEntity>> getScheduledTasksByName(@PathVariable String taskName) {
        List<ScheduledTaskEntity> tasks = schedulerService.getScheduledTasksByName(taskName);
        return ResponseEntity.ok(tasks);
    }
}
