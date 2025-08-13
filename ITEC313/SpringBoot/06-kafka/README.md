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

### Start Kafka
```bash
cd postgresql-pgadmin
docker-compose up -d
```

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

## Links
- [Spring Kafka project](https://spring.io/projects/spring-kafka)
- [Kafka reference](https://docs.spring.io/spring-kafka/reference/)
- [Email documentation](https://docs.spring.io/spring-boot/reference/io/email.html)
