package com.acu.eventsourcing.service;

import com.acu.eventsourcing.domain.BankAccount;
import com.acu.eventsourcing.store.InMemoryEventStore;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountQueryService {
    private final InMemoryEventStore eventStore;

    public AccountQueryService(InMemoryEventStore eventStore) {
        this.eventStore = eventStore;
    }

    public BigDecimal currentBalance(String accountId) {
        BankAccount aggregate = new BankAccount(accountId);
        eventStore.load(accountId).forEach(aggregate::apply);
        return aggregate.getBalance();
    }
}


