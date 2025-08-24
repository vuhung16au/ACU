# Spring Boot Redis Messaging Demo

This is a demo project that demonstrates how to send and receive messages using Redis with Spring Boot. The project is based on the [Spring Boot Redis Messaging Guide](https://spring.io/guides/gs/messaging-redis).

## Project Overview

This demo shows how to:
- Set up a Spring Boot application with Redis messaging
- Configure Redis message listeners
- Send messages to Redis channels
- Receive and process messages from Redis channels

## Project Structure

```
├── src/main/java/com/acu/redis/demo/
│   ├── RedisDemoApplication.java    # Main Spring Boot application
│   ├── Receiver.java                # Message receiver component
│   └── RedisConfig.java             # Redis configuration
├── src/main/resources/
│   └── application.properties       # Application configuration
├── docker/
│   ├── docker-compose.yml           # Redis server configuration
│   └── README.md                    # Docker management instructions
├── pom.xml                          # Maven dependencies
└── README.md                        # This file
```

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Docker and Docker Compose

## Quick Start

### 1. Start Redis Server

```bash
cd docker
docker-compose up -d
```

### 2. Build and Run the Application

```bash
# Build the project
mvn clean compile

# Run the application
mvn spring-boot:run
```

### 3. Expected Output

When you run the application, you should see output similar to:

```
Received <Hello from Redis!>
Sending message...
Received <Hello from Redis!>
Sending message...
Received <Hello from Redis!>
Sending message...
Received <Hello from Redis!>
Sending message...
Received <Hello from Redis!>
```

## How It Works

1. **RedisDemoApplication**: The main application that starts Spring Boot and sends messages
2. **Receiver**: A component that receives messages from Redis channels
3. **RedisConfig**: Configuration class that sets up Redis message listeners and templates
4. **Redis Server**: Runs in Docker and handles message pub/sub

### Message Flow

1. The application starts and waits for the receiver to be ready
2. Every second, it sends a message "Hello from Redis!" to the "chat" channel
3. The Receiver component listens to the "chat" channel and prints received messages
4. After receiving 5 messages, the application exits

## Configuration

The application is configured to connect to Redis at:
- **Host**: localhost
- **Port**: 6379
- **Channel**: "chat"

You can modify these settings in `src/main/resources/application.properties`.

## Development

### Building the Project

```bash
mvn clean compile
```

### Running Tests

```bash
mvn test
```

### Creating JAR

```bash
mvn clean package
```

### Running JAR

```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

## Docker Management

See `docker/README.md` for detailed instructions on managing the Redis server.

### Quick Docker Commands

```bash
# Start Redis
cd docker && docker-compose up -d

# Stop Redis
cd docker && docker-compose down

# View logs
cd docker && docker-compose logs -f redis
```

## Troubleshooting

### Redis Connection Issues

1. Ensure Redis server is running:
   ```bash
   cd docker && docker-compose ps
   ```

2. Check Redis logs:
   ```bash
   cd docker && docker-compose logs redis
   ```

3. Test Redis connection:
   ```bash
   docker exec -it redis-server redis-cli ping
   ```

### Application Issues

1. Check application logs for connection errors
2. Verify Redis is running on localhost:6379
3. Ensure no other applications are using port 6379

## Dependencies

- Spring Boot 3.2.0
- Spring Data Redis
- Spring Boot Web Starter
- Redis (latest Docker image)

## License

This is a demo project for educational purposes.
