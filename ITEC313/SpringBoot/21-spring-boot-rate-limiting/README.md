# 21-spring-boot-rate-limiting

Demo of API protection patterns:
- Bucket4j IP rate limiting
- Resilience4j Circuit Breaker / Retry / Bulkhead

## Build & Run

```bash
mvn -pl 21-spring-boot-rate-limiting -am clean compile
mvn -pl 21-spring-boot-rate-limiting test
mvn -pl 21-spring-boot-rate-limiting spring-boot:run
```

## Test

- Health: GET `http://localhost:8080/api/rl/health`
- Fast: GET `http://localhost:8080/api/rl/fast`
- Unstable (CB/Retry/Bulkhead demo): GET `http://localhost:8080/api/rl/unstable`
- Rate limit: call `/api/rl/fast` >3 times within 10s from same IP; 429 expected

See `docs` for diagrams and explanations.
