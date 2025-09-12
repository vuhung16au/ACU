# Rate Limiting and Resilience Concepts

This module demonstrates API protection and resilience: Bucket4j rate limiting and Resilience4j Circuit Breaker, Retry, and Bulkhead.

## Bucket4j Rate Limiting

- Token bucket: capacity N, refilled at R tokens/time
- Each request consumes 1 token; if empty → 429 Too Many Requests

```mermaid
flowchart LR
  IP[Client IP] -->|HTTP| Interceptor[Rate Limit Interceptor]
  subgraph Bucket4j
    B[(Bucket)] --> Decision{Token available?}
  end
  Interceptor --> B
  Decision -- Yes --> App[Controller]
  Decision -- No --> R429[HTTP 429]
```

## Circuit Breaker

- Tracks failures; opens when threshold exceeded to prevent cascading failures
- States: Closed → Open → Half-Open → Closed

```mermaid
stateDiagram-v2
  [*] --> Closed
  Closed --> Open: failures exceed threshold
  Open --> HalfOpen: wait duration expires
  HalfOpen --> Closed: limited success
  HalfOpen --> Open: failure
```

## Retry

- Automatically re-executes failing calls with backoff
- Combine with Circuit Breaker carefully to avoid overload

```mermaid
sequenceDiagram
  participant C as Controller
  participant S as UnstableService
  C->>S: call
  S-->>C: error
  C->>S: retry 1
  S-->>C: success
```

## Bulkhead

- Limits concurrent calls to protect overall system
- Semaphore/ThreadPool isolation

```mermaid
flowchart LR
  Requests --> Gate{Permit available?}
  Gate -- Yes --> Service
  Gate -- No --> Fallback
```

## In this module

- Rate limiting: `IpRateLimitInterceptor` + Bucket4j
- Resilience: `UnstableService` annotated with CB/Retry/Bulkhead
- Endpoints: `/api/rl/health`, `/api/rl/fast`, `/api/rl/unstable`
