# Day 6 - Kafka + Email + Scheduling

## Goals
- Produce/consume messages with Spring for Apache Kafka
- Send email via JavaMailSender
- Add `@Scheduled` tasks

## Features
- Kafka producer/consumer with JSON payloads
- Error handling and retries
- Email sending with templated content
- Scheduled jobs and tasks

## How to Run

### Prerequisites
- Java 17+
- Maven 3.9+
- Docker and Docker Compose (for Kafka)

### Start Development Environment

#### Option 1: Using Docker Compose (Recommended)
```bash
# Start all services (Kafka, PostgreSQL, pgAdmin, Kafka UI)
docker-compose up -d

# Or use the management script
./scripts/docker-setup.sh start
```

#### Option 2: Manual Docker Commands
```bash
# Start services
docker-compose up -d

# Check status
docker-compose ps

# View logs
docker-compose logs -f

# Stop services
docker-compose down
```

### Access Services
- **Kafka UI**: http://localhost:8080 (Monitor Kafka topics and messages)
- **pgAdmin**: http://localhost:5050 (admin@kafka-demo.com / admin123)
- **PostgreSQL**: localhost:5432 (kafka_user / kafka_password)
- **Kafka**: localhost:9092

### Run the Application
```bash
mvn spring-boot:run
```

## Endpoints

### Kafka Endpoints
- `POST /api/messages` - Send a message to Kafka topic
- `GET /api/messages` - Get recent messages from Kafka

### Email Endpoints
- `POST /api/email/send` - Send an email
- `GET /api/email/templates` - List available email templates

### Scheduling Endpoints
- `GET /api/scheduler/status` - Check scheduler status
- `POST /api/scheduler/trigger` - Manually trigger a scheduled task

## Configuration
- Kafka configuration in `application.yml`
- Email configuration (SMTP settings)
- Scheduling configuration

## Docker Development Environment

For detailed information about the Docker setup, including PostgreSQL, pgAdmin, and Kafka UI, see [DOCKER_README.md](DOCKER_README.md).

### Quick Docker Commands
```bash
# Start all services
./scripts/docker-setup.sh start

# Check status
./scripts/docker-setup.sh status

# View logs
./scripts/docker-setup.sh logs

# Stop services
./scripts/docker-setup.sh stop

# Clean up everything
./scripts/docker-setup.sh cleanup
```

## Links
- [Spring Kafka project](https://spring.io/projects/spring-kafka)
- [Kafka reference](https://docs.spring.io/spring-kafka/reference/)
- [Email documentation](https://docs.spring.io/spring-boot/reference/io/email.html)
- [Docker Compose documentation](https://docs.docker.com/compose/)
