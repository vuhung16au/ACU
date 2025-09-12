package com.acu.eventsourcing.domain;

import java.time.Instant;

public interface DomainEvent {
    String aggregateId();
    Instant occurredAt();
}


