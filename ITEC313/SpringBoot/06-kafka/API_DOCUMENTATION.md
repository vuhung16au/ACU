# Kafka Demo API Documentation

## Overview

This API provides a simple interface for sending and receiving messages through Apache Kafka. The application demonstrates Spring Boot integration with Kafka for real-time message processing.

## Base URL

```
http://localhost:8080/api/messages
```

## Endpoints

### 1. Health Check

**GET** `/health`

Check if the application is running and healthy.

**Response:**
```json
{
  "status": "UP",
  "timestamp": "2024-01-15T10:30:00.000Z",
  "kafka": "CONNECTED"
}
```

**Example:**
```bash
curl http://localhost:8080/api/messages/health
```

### 2. Send Message

**POST** `/`

Send a message to Kafka topic.

**Request Body:**
```json
{
  "content": "Your message content",
  "sender": "SenderName",
  "type": "INFO"
}
```

**Message Types:**
- `INFO` - Informational messages
- `WARNING` - Warning messages  
- `ERROR` - Error messages

**Response:**
```json
{
  "message": "Message sent successfully",
  "messageId": "uuid-1234-5678-90ab",
  "timestamp": "2024-01-15T10:30:00.000Z"
}
```

**Example:**
```bash
curl -X POST http://localhost:8080/api/messages \
  -H "Content-Type: application/json" \
  -d '{
    "content": "Hello Kafka!",
    "sender": "TestUser",
    "type": "INFO"
  }'
```

### 3. Get All Messages

**GET** `/`

Retrieve all messages currently in memory.

**Response:**
```json
[
  {
    "id": "uuid-1234-5678-90ab",
    "content": "Hello Kafka!",
    "sender": "TestUser",
    "type": "INFO",
    "timestamp": "2024-01-15T10:30:00.000Z",
    "priority": 1
  }
]
```

**Example:**
```bash
curl http://localhost:8080/api/messages
```

### 4. Get Messages by Sender

**GET** `/sender/{senderName}`

Filter messages by sender name.

**Parameters:**
- `senderName` (path) - Name of the sender to filter by

**Response:**
```json
[
  {
    "id": "uuid-1234-5678-90ab",
    "content": "Hello Kafka!",
    "sender": "TestUser",
    "type": "INFO",
    "timestamp": "2024-01-15T10:30:00.000Z",
    "priority": 1
  }
]
```

**Example:**
```bash
curl http://localhost:8080/api/messages/sender/TestUser
```

### 5. Get Messages by Type

**GET** `/type/{messageType}`

Filter messages by message type.

**Parameters:**
- `messageType` (path) - Type of message (INFO, WARNING, ERROR)

**Response:**
```json
[
  {
    "id": "uuid-1234-5678-90ab",
    "content": "Hello Kafka!",
    "sender": "TestUser",
    "type": "INFO",
    "timestamp": "2024-01-15T10:30:00.000Z",
    "priority": 1
  }
]
```

**Example:**
```bash
curl http://localhost:8080/api/messages/type/INFO
```

### 6. Get Message Statistics

**GET** `/stats`

Get current message statistics.

**Response:**
```json
{
  "totalMessagesSent": 5,
  "totalMessagesReceived": 5,
  "messagesInMemory": 5,
  "failedMessages": 0
}
```

**Example:**
```bash
curl http://localhost:8080/api/messages/stats
```

## Message Model

```json
{
  "id": "string (UUID)",
  "content": "string",
  "sender": "string",
  "type": "INFO | WARNING | ERROR",
  "timestamp": "ISO 8601 datetime",
  "priority": "integer (1-5)"
}
```

## Error Responses

### 400 Bad Request
```json
{
  "error": "Invalid message format",
  "timestamp": "2024-01-15T10:30:00.000Z"
}
```

### 500 Internal Server Error
```json
{
  "error": "Failed to send message to Kafka",
  "timestamp": "2024-01-15T10:30:00.000Z"
}
```

## Usage Examples

### Complete Workflow

1. **Start the application:**
   ```bash
   mvn spring-boot:run
   ```

2. **Send a message:**
   ```bash
   curl -X POST http://localhost:8080/api/messages \
     -H "Content-Type: application/json" \
     -d '{
       "content": "Hello from API!",
       "sender": "APIUser",
       "type": "INFO"
     }'
   ```

3. **Check statistics:**
   ```bash
   curl http://localhost:8080/api/messages/stats
   ```

4. **Get all messages:**
   ```bash
   curl http://localhost:8080/api/messages
   ```

### Using the Demo Script

Run the provided demo script to see all endpoints in action:

```bash
./scripts/demo.sh
```

## Kafka Integration

- **Topic:** `messages-topic`
- **Producer:** Sends messages to Kafka topic
- **Consumer:** Listens for messages and stores them in memory
- **Real-time Processing:** Messages are processed asynchronously

## Monitoring

- **Kafka UI:** http://localhost:8080 (if enabled)
- **Application Logs:** Check console output for real-time message processing
- **Health Check:** Use `/health` endpoint to monitor application status

## Testing

Run the test suite:

```bash
mvn test
```

The tests cover:
- Kafka producer/consumer integration
- Message filtering and statistics
- API endpoint functionality
- Error handling scenarios
