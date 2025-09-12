package com.acu.sse.service;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.Instant;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class SseBroadcaster {
    private final Set<SseEmitter> clients = new CopyOnWriteArraySet<>();

    public SseEmitter subscribe(long timeoutMs) {
        SseEmitter emitter = new SseEmitter(timeoutMs);
        clients.add(emitter);
        emitter.onCompletion(() -> clients.remove(emitter));
        emitter.onTimeout(() -> clients.remove(emitter));
        try {
            emitter.send(SseEmitter.event().name("connected").data("connected@" + Instant.now()));
        } catch (IOException ignored) {
        }
        return emitter;
    }

    public void broadcast(Object data) {
        for (SseEmitter emitter : clients) {
            try {
                emitter.send(SseEmitter.event().name("message").data(data));
            } catch (IOException e) {
                emitter.complete();
                clients.remove(emitter);
            }
        }
    }
}


