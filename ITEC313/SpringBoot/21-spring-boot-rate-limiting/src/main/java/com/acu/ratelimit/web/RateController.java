package com.acu.ratelimit.web;

import com.acu.ratelimit.service.UnstableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/rl")
public class RateController {
    private final UnstableService unstableService;

    public RateController(UnstableService unstableService) {
        this.unstableService = unstableService;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }

    @GetMapping("/fast")
    public Map<String, String> fast() {
        return Map.of("result", "FAST");
    }

    @GetMapping("/unstable")
    public ResponseEntity<String> unstable() {
        String res = unstableService.sometimesFails();
        return ResponseEntity.ok(res);
    }
}


