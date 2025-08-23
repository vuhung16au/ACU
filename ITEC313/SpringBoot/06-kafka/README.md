# Kafka Demo Application

A simple Spring Boot application demonstrating Apache Kafka integration with Spring Kafka.

## 🎯 What This Demo Shows

- **Kafka Producer/Consumer**: Send and receive messages using Spring Kafka
- **KRaft Mode**: Apache Kafka 3.9.1 running without ZooKeeper
- **REST API**: Simple HTTP endpoints to interact with Kafka
- **Message Filtering**: Filter messages by sender, type, and other criteria
- **Real-time Processing**: Asynchronous message processing with Kafka

## 🚀 Quick Start

### ⚡ One-Command Setup
```bash
./scripts/quick-start.sh
```
This single command will set up everything and run a live demonstration!

### 📋 Prerequisites
- Java 17+
- Maven 3.9+
- Docker and Docker Compose
- curl (for testing)

### 🎯 Manual Setup (3 Steps)

#### Step 1: Start Infrastructure
```bash
./scripts/docker-setup.sh start
# This starts Kafka and Kafka UI with health checks
```

#### Step 2: Run Application
```bash
mvn spring-boot:run
# Application starts on port 8086
# Kafka UI available at http://localhost:8080
```

#### Step 3: Test Everything
```bash
./scripts/demo.sh
# Runs a complete API demonstration
```

### 🧪 Development Testing
```bash
./scripts/dev-test.sh smoke      # Quick test
./scripts/dev-test.sh            # Full test suite
./scripts/dev-test.sh interactive # Interactive testing
```

## 📡 API Endpoints

### Core Kafka Operations

#### Send a Message
```bash
curl -X POST http://localhost:8086/api/messages \
  -H "Content-Type: application/json" \
  -d '{
    "content": "Hello Kafka!",
    "sender": "Alice",
    "type": "INFO"
  }'
```

#### Get All Messages
```bash
curl http://localhost:8086/api/messages
```

#### Filter Messages by Sender
```bash
curl http://localhost:8086/api/messages/sender/Alice
```

#### Filter Messages by Type
```bash
curl http://localhost:8086/api/messages/type/info
```

### Message Types
- `INFO` - Information messages
- `WARNING` - Warning messages  
- `ERROR` - Error messages
- `SUCCESS` - Success messages
- `ALERT` - Alert messages

### Management Endpoints

#### Clear All Messages
```bash
curl -X DELETE http://localhost:8080/api/messages
```

#### Health Check
```bash
curl http://localhost:8086/api/messages/health
```

#### Get Statistics
```bash
curl http://localhost:8086/api/messages/stats
```

## 🧪 Testing

### Run All Tests
```bash
mvn test
```

### Test Categories

1. **KafkaControllerTest**: Tests REST API endpoints with mocked Kafka service
2. **KafkaServiceIntegrationTest**: Tests Kafka service with embedded Kafka
3. **KafkaIntegrationTest**: Tests Kafka producer/consumer functionality
4. **EndToEndTest**: Tests complete message flow from API to storage

### Test Focus Areas
- ✅ Kafka producer/consumer integration
- ✅ Message filtering and retrieval
- ✅ Error handling
- ✅ Message statistics
- ❌ Database operations (removed)
- ❌ Complex business logic (simplified)

## 🏗️ Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   REST Client   │───▶│  Spring Boot    │───▶│   Apache Kafka  │
│                 │    │   Application   │    │   (KRaft Mode)  │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                              │
                              ▼
                       ┌─────────────────┐
                       │  In-Memory      │
                       │  Message Store  │
                       └─────────────────┘
```

### Key Components

- **KafkaController**: REST API endpoints
- **KafkaService**: Kafka producer/consumer logic
- **Message**: Data model for messages
- **KafkaConfig**: Kafka configuration

## 🔧 Configuration

### Kafka Configuration (`application.yml`)
```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
    consumer:
      group-id: message-group
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      properties:
        spring.json.trusted.packages: com.acu.kafka.model
```

### Docker Configuration (`docker-compose.yml`)
- Apache Kafka 3.9.1 in KRaft mode
- Kafka UI for monitoring
- No ZooKeeper dependency

## 📊 Monitoring

### Kafka UI
- **URL**: http://localhost:8080
- **Features**: 
  - View topics and messages
  - Monitor consumer groups
  - Real-time message inspection

### Application Health
- **Health Check**: `GET /api/messages/health`
- **Statistics**: `GET /api/messages/stats`

## 📚 Documentation

- **🚀 Quick Start**: See `QUICK_START_GUIDE.md` for complete setup in under 5 minutes
- **📖 API Reference**: See `API_DOCUMENTATION.md` for detailed endpoint documentation
- **🌐 cURL Examples**: See `CURL_EXAMPLES.md` for comprehensive API testing examples
- **🐳 Docker Setup**: See `DOCKER_README.md` for Docker-specific instructions
- **🧪 Testing**: Run `mvn test` to execute the simplified Kafka-focused test suite

## 🛠️ Development Scripts

- **`./scripts/quick-start.sh`** - Complete setup and demo in one command
- **`./scripts/docker-setup.sh`** - Manage Docker services with health checks
- **`./scripts/demo.sh`** - Interactive API demonstration with examples
- **`./scripts/dev-test.sh`** - Comprehensive testing suite with multiple modes

## 🐳 Docker Commands

```bash
# Start services
./scripts/docker-setup.sh start

# Check status
./scripts/docker-setup.sh status

# View logs
./scripts/docker-setup.sh logs

# Stop services
./scripts/docker-setup.sh stop

# Clean up
./scripts/docker-setup.sh cleanup
```

## 📚 Learning Resources

- [Spring Kafka Documentation](https://docs.spring.io/spring-kafka/reference/)
- [Apache Kafka Documentation](https://kafka.apache.org/documentation/)
- [Kafka KRaft Mode](https://kafka.apache.org/documentation/#kraft)
- [Spring Boot Testing](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-testing)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## 📄 License

This project is for educational purposes as part of the ITEC313 course.
