package com.acu.eventsourcing.domain;

import java.math.BigDecimal;
import java.time.Instant;

public record MoneyWithdrawnEvent(String aggregateId, BigDecimal amount, Instant occurredAt) implements DomainEvent {
    public MoneyWithdrawnEvent(String aggregateId, BigDecimal amount) {
        this(aggregateId, amount, Instant.now());
    }
}


