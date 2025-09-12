package com.acu.eventsourcing.domain;

import java.math.BigDecimal;
import java.time.Instant;

public record AccountOpenedEvent(String aggregateId, String ownerName, BigDecimal initialBalance, Instant occurredAt) implements DomainEvent {
    public AccountOpenedEvent(String aggregateId, String ownerName, BigDecimal initialBalance) {
        this(aggregateId, ownerName, initialBalance, Instant.now());
    }
}


