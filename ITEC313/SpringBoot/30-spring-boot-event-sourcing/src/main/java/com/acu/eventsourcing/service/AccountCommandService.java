package com.acu.eventsourcing.service;

import com.acu.eventsourcing.domain.BankAccount;
import com.acu.eventsourcing.domain.DomainEvent;
import com.acu.eventsourcing.store.InMemoryEventStore;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountCommandService {
    private final InMemoryEventStore eventStore;

    public AccountCommandService(InMemoryEventStore eventStore) {
        this.eventStore = eventStore;
    }

    public String openAccount(String ownerName, BigDecimal initialBalance) {
        BankAccount aggregate = new BankAccount(ownerName, initialBalance);
        persist(aggregate.getAccountId(), aggregate.getUncommittedEvents());
        return aggregate.getAccountId();
    }

    public void deposit(String accountId, BigDecimal amount) {
        BankAccount aggregate = rehydrate(accountId);
        aggregate.deposit(amount);
        persist(accountId, aggregate.getUncommittedEvents());
    }

    public void withdraw(String accountId, BigDecimal amount) {
        BankAccount aggregate = rehydrate(accountId);
        aggregate.withdraw(amount);
        persist(accountId, aggregate.getUncommittedEvents());
    }

    private void persist(String aggregateId, List<DomainEvent> events) {
        eventStore.append(aggregateId, events);
        events.clear();
    }

    private BankAccount rehydrate(String accountId) {
        BankAccount aggregate = new BankAccount(accountId);
        eventStore.load(accountId).forEach(aggregate::apply);
        return aggregate;
    }
}


