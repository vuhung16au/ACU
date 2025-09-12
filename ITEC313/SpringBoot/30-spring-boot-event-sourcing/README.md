# 30-spring-boot-event-sourcing

Demonstrates Event Sourcing and CQRS using a simple bank account domain.

## Features
- Event Store: in-memory
- Aggregate: `BankAccount` with open, deposit, withdraw
- Commands/Queries split via services
- REST API endpoints
- Minimal E2E test with `TestRestTemplate`

## Run
```bash
mvn -pl 30-spring-boot-event-sourcing spring-boot:run
```

## API
- POST `/api/accounts?ownerName=Alice&initialBalance=100`
- POST `/api/accounts/{id}/deposit?amount=50`
- POST `/api/accounts/{id}/withdraw?amount=20`
- GET `/api/accounts/{id}/balance`


