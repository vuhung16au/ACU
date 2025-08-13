package com.acu.kafka.controller;

import com.acu.kafka.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
