# 20-spring-boot-sse

Server-Sent Events (SSE) demo with Spring Boot. Shows real-time one-way event streaming, basic broadcaster, and client reconnection behavior.

## Build & Run

```bash
mvn -pl 20-spring-boot-sse -am clean compile
mvn -pl 20-spring-boot-sse test
mvn -pl 20-spring-boot-sse spring-boot:run
```

## Test

- Health: GET `http://localhost:8080/api/sse/health`
- Stream: open in browser or curl:

```bash
curl -N http://localhost:8080/api/sse/stream
```

- Send event:

```bash
curl -X POST "http://localhost:8080/api/sse/send?message=hello"
```

## Endpoints

- GET `/api/sse/stream` → `text/event-stream`
- POST `/api/sse/send?message=...` → broadcast
- GET `/api/sse/health`

See `docs` for concepts and diagrams.
