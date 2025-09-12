package com.acu.ratelimit.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

@Service
public class UnstableService {
    private final Random random = new Random();

    @CircuitBreaker(name = "unstable", fallbackMethod = "fallback")
    @Retry(name = "unstable")
    @Bulkhead(name = "unstable")
    public String sometimesFails() {
        if (random.nextBoolean()) {
            throw new RuntimeException("Random failure");
        }
        busyWait();
        return "OK@" + Instant.now();
    }

    private void busyWait() {
        try {
            Thread.sleep(50 + random.nextInt(100));
        } catch (InterruptedException ignored) {
        }
    }

    private String fallback(Throwable t) {
        return "FALLBACK: " + t.getClass().getSimpleName();
    }
}


