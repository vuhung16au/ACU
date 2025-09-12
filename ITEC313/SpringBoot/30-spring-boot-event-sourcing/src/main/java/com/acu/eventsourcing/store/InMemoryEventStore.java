package com.acu.eventsourcing.store;

import com.acu.eventsourcing.domain.DomainEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryEventStore {
    private final Map<String, List<DomainEvent>> eventsByAggregate = new ConcurrentHashMap<>();

    public void append(String aggregateId, List<DomainEvent> newEvents) {
        eventsByAggregate.computeIfAbsent(aggregateId, k -> new ArrayList<>()).addAll(newEvents);
    }

    public List<DomainEvent> load(String aggregateId) {
        return Collections.unmodifiableList(eventsByAggregate.getOrDefault(aggregateId, List.of()));
    }

    public List<DomainEvent> loadAll() {
        return eventsByAggregate.values().stream().flatMap(List::stream).toList();
    }
}


