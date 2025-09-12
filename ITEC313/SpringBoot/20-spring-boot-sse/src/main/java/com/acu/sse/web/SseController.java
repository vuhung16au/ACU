package com.acu.sse.web;

import com.acu.sse.service.SseBroadcaster;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/sse")
public class SseController {
    private final SseBroadcaster broadcaster;

    public SseController(SseBroadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    @GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream(@RequestParam(defaultValue = "180000") long timeoutMs) {
        return broadcaster.subscribe(timeoutMs);
    }

    @PostMapping("/send")
    public ResponseEntity<Void> send(@RequestParam String message) {
        broadcaster.broadcast(Map.of("timestamp", Instant.now().toString(), "message", message));
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }
}


