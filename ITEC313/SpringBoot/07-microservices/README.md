# Day 7 - Microservices + Testing Strategy

## Goals
- Build a simple microservice system (2-3 services)
- Service discovery, centralized config (Spring Cloud), resilience basics
- Testing: unit, slice, integration, Testcontainers (optional)

## Features
- Service A (API Gateway)
- Service B (Data Service)
- Service Discovery with Eureka
- Centralized Configuration
- Circuit Breaker with Resilience4j
- Comprehensive testing strategy

## Architecture
```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   API Gateway   │    │  Data Service   │    │  Config Server  │
│   (Port 8087)   │◄──►│   (Port 8088)   │◄──►│   (Port 8888)   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         └───────────────────────┼───────────────────────┘
                                 │
                    ┌─────────────────┐
                    │  Eureka Server  │
                    │   (Port 8761)   │
                    └─────────────────┘
```

## How to Run

### Prerequisites
- Java 17+
- Maven 3.9+
- Docker and Docker Compose

### Start Infrastructure
```bash
cd docker
docker-compose up -d
```

### Run Services (in separate terminals)
```bash
# Terminal 1 - Eureka Server
cd eureka-server
mvn spring-boot:run

# Terminal 2 - Config Server
cd config-server
mvn spring-boot:run

# Terminal 3 - Data Service
cd data-service
mvn spring-boot:run

# Terminal 4 - API Gateway
cd api-gateway
mvn spring-boot:run
```

## Endpoints

### Eureka Server
- `http://localhost:8761` - Eureka Dashboard

### Config Server
- `GET /config/{application}/{profile}` - Get configuration

### Data Service
- `GET /api/data` - Get all data
- `GET /api/data/{id}` - Get data by ID
- `POST /api/data` - Create new data
- `PUT /api/data/{id}` - Update data
- `DELETE /api/data/{id}` - Delete data

### API Gateway
- `GET /api/data` - Proxy to data service
- `GET /api/data/{id}` - Proxy to data service
- `POST /api/data` - Proxy to data service
- `GET /actuator/health` - Health check

## Testing Strategy

### Unit Tests
- Service layer tests with Mockito
- Controller tests with MockMvc
- Repository tests with @DataJpaTest

### Integration Tests
- End-to-end API tests
- Database integration tests
- Service communication tests

### Testcontainers (Optional)
- Database tests with real PostgreSQL
- Service integration tests

## Links
- [Spring Cloud](https://spring.io/projects/spring-cloud)
- [Service Discovery](https://spring.io/guides/gs/service-registration-and-discovery/)
- [Spring Boot Testing](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing)
- [Why Spring Boot for Microservices](https://www.geeksforgeeks.org/blogs/why-to-choose-spring-boot-for-microservices-development/)
