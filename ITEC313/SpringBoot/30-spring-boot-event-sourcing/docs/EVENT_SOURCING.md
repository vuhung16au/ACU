# Event Sourcing

Event Sourcing stores every state change as an immutable domain event. Current state is reconstructed by replaying events for an aggregate in order.

## Why use it?
- Auditability and traceability
- Time-travel and temporal queries
- Rebuild read models by replaying
- Natural fit for event-driven architectures

## Core concepts
- Aggregate: consistency boundary (e.g., BankAccount)
- Domain Event: an immutable fact that happened
- Event Store: append-only log of events per aggregate
- Replay: fold events to rebuild current state

## Command flow
```mermaid
sequenceDiagram
  participant API as Command API
  participant CMD as Command Handler
  participant AGG as Aggregate
  participant ES as Event Store
  participant RM as Read Model

  API->>CMD: openAccount(owner, initialBalance)
  CMD->>AGG: validate business rules
  AGG-->>CMD: AccountOpenedEvent
  CMD->>ES: append(AccountOpenedEvent)
  ES-->>RM: publish event for projection
  RM->>RM: update projections
```

## Rebuild by replay
```mermaid
flowchart LR
  ES[(Event Store)] -->|events for aggregateId| Replayer
  Replayer -->|apply in order| Aggregate
  Aggregate -->|current state| API
```

## In this module
- Aggregate: `BankAccount`
- Events: `AccountOpened`, `MoneyDeposited`, `MoneyWithdrawn`
- Store: `InMemoryEventStore`
- Replay: applied on command handling and queries to rebuild state

Limitations (demo): in-memory store, no concurrency/version checks, simple projections.
