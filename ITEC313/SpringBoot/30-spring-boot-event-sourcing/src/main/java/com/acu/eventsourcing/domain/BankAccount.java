package com.acu.eventsourcing.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BankAccount {
    private final String accountId;
    private String ownerName;
    private BigDecimal balance;

    private final List<DomainEvent> uncommittedEvents = new ArrayList<>();

    public BankAccount(String ownerName, BigDecimal initialBalance) {
        this.accountId = UUID.randomUUID().toString();
        applyChange(new AccountOpenedEvent(accountId, ownerName, initialBalance));
    }

    public BankAccount(String accountId) {
        this.accountId = accountId;
    }

    public void deposit(BigDecimal amount) {
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Deposit must be positive");
        }
        applyChange(new MoneyDepositedEvent(accountId, amount));
    }

    public void withdraw(BigDecimal amount) {
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Withdrawal must be positive");
        }
        if (balance.compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient funds");
        }
        applyChange(new MoneyWithdrawnEvent(accountId, amount));
    }

    public void apply(DomainEvent event) {
        if (event instanceof AccountOpenedEvent e) {
            this.ownerName = e.ownerName();
            this.balance = e.initialBalance();
        } else if (event instanceof MoneyDepositedEvent e) {
            this.balance = this.balance.add(e.amount());
        } else if (event instanceof MoneyWithdrawnEvent e) {
            this.balance = this.balance.subtract(e.amount());
        }
    }

    private void applyChange(DomainEvent event) {
        apply(event);
        uncommittedEvents.add(event);
    }

    public List<DomainEvent> getUncommittedEvents() {
        return uncommittedEvents;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}


