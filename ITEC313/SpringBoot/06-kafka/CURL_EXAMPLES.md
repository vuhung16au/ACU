# 🌐 Kafka Demo - cURL Examples

Complete collection of cURL commands for testing the Kafka Demo API.

## 🚀 Base Configuration

```bash
# Set base URL (adjust port if needed)
BASE_URL="http://localhost:8086/api/messages"

# Optional: Set timeout for all requests
TIMEOUT=10
```

## 🏥 Health Check

### Basic Health Check
```bash
curl -X GET "$BASE_URL/health"
```

### Health Check with Pretty Output
```bash
curl -X GET "$BASE_URL/health" | jq '.'
```

### Health Check with Error Handling
```bash
curl -X GET "$BASE_URL/health" \
  --max-time 10 \
  --fail \
  --silent \
  --show-error | jq '.'
```

## 📨 Sending Messages

### Send INFO Message
```bash
curl -X POST "$BASE_URL" \
  -H "Content-Type: application/json" \
  -d '{
    "content": "Application started successfully",
    "sender": "System",
    "type": "INFO"
  }'
```

### Send WARNING Message
```bash
curl -X POST "$BASE_URL" \
  -H "Content-Type: application/json" \
  -d '{
    "content": "High memory usage detected: 85%",
    "sender": "Monitor",
    "type": "WARNING"
  }'
```

### Send ERROR Message
```bash
curl -X POST "$BASE_URL" \
  -H "Content-Type: application/json" \
  -d '{
    "content": "Database connection failed: timeout after 30s",
    "sender": "Database",
    "type": "ERROR"
  }'
```

### Send Message with Special Characters
```bash
curl -X POST "$BASE_URL" \
  -H "Content-Type: application/json" \
  -d '{
    "content": "User \"admin\" logged in from IP: 192.168.1.100",
    "sender": "Auth Service",
    "type": "INFO"
  }'
```

### Send Message with Emoji
```bash
curl -X POST "$BASE_URL" \
  -H "Content-Type: application/json" \
  -d '{
    "content": "🚀 Deployment completed successfully! ✅",
    "sender": "CI/CD",
    "type": "INFO"
  }'
```

## 📥 Retrieving Messages

### Get All Messages
```bash
curl -X GET "$BASE_URL"
```

### Get All Messages (Pretty Format)
```bash
curl -X GET "$BASE_URL" | jq '.'
```

### Get Messages by Sender
```bash
# Get messages from specific sender
curl -X GET "$BASE_URL/sender/System" | jq '.'

# URL encode sender names with spaces
curl -X GET "$BASE_URL/sender/Auth%20Service" | jq '.'
```

### Get Messages by Type
```bash
# Get INFO messages
curl -X GET "$BASE_URL/type/INFO" | jq '.'

# Get WARNING messages
curl -X GET "$BASE_URL/type/WARNING" | jq '.'

# Get ERROR messages
curl -X GET "$BASE_URL/type/ERROR" | jq '.'
```

### Get Statistics
```bash
curl -X GET "$BASE_URL/stats" | jq '.'
```

## 📊 Advanced Examples

### Send Multiple Messages in Sequence
```bash
# Send a batch of messages
for i in {1..5}; do
  curl -X POST "$BASE_URL" \
    -H "Content-Type: application/json" \
    -d "{
      \"content\": \"Batch message number $i\",
      \"sender\": \"BatchSender\",
      \"type\": \"INFO\"
    }"
  sleep 1
done
```

### Performance Testing
```bash
# Send 10 messages quickly
for i in {1..10}; do
  curl -X POST "$BASE_URL" \
    -H "Content-Type: application/json" \
    -d "{
      \"content\": \"Performance test message $i\",
      \"sender\": \"LoadTester\",
      \"type\": \"INFO\"
    }" &
done
wait
```

### Test Different Message Types
```bash
# Array of message types
types=("INFO" "WARNING" "ERROR")

for type in "${types[@]}"; do
  curl -X POST "$BASE_URL" \
    -H "Content-Type: application/json" \
    -d "{
      \"content\": \"This is a $type message\",
      \"sender\": \"TypeTester\",
      \"type\": \"$type\"
    }"
done
```

## 🔍 Filtering and Analysis

### Count Messages by Type
```bash
echo "INFO messages:"
curl -s "$BASE_URL/type/INFO" | jq '. | length'

echo "WARNING messages:"
curl -s "$BASE_URL/type/WARNING" | jq '. | length'

echo "ERROR messages:"
curl -s "$BASE_URL/type/ERROR" | jq '. | length'
```

### Get Recent Messages (Last 5)
```bash
curl -s "$BASE_URL" | jq '.[-5:]'
```

### Extract Message Content Only
```bash
curl -s "$BASE_URL" | jq '.[].content'
```

### Get Messages from Last Hour (if timestamp filtering was implemented)
```bash
# This would require additional API implementation
# curl -s "$BASE_URL/since/$(date -d '1 hour ago' -Iseconds)"
```

## ⚠️ Error Testing

### Test Invalid Message Format
```bash
# Missing required fields
curl -X POST "$BASE_URL" \
  -H "Content-Type: application/json" \
  -d '{
    "invalid": "data"
  }'
```

### Test Invalid Message Type
```bash
curl -X POST "$BASE_URL" \
  -H "Content-Type: application/json" \
  -d '{
    "content": "Test message",
    "sender": "Tester",
    "type": "INVALID_TYPE"
  }'
```

### Test Empty Message
```bash
curl -X POST "$BASE_URL" \
  -H "Content-Type: application/json" \
  -d '{
    "content": "",
    "sender": "Tester",
    "type": "INFO"
  }'
```

### Test Non-existent Endpoint
```bash
curl -X GET "$BASE_URL/nonexistent"
```

## 🧪 Testing Scripts

### Complete API Test Script
```bash
#!/bin/bash
BASE_URL="http://localhost:8086/api/messages"

echo "🧪 Testing Kafka Demo API"
echo "========================"

# Test health
echo "1. Health Check:"
curl -s "$BASE_URL/health" | jq '.'
echo ""

# Send test messages
echo "2. Sending test messages..."
curl -s -X POST "$BASE_URL" \
  -H "Content-Type: application/json" \
  -d '{
    "content": "Test INFO message",
    "sender": "TestScript",
    "type": "INFO"
  }' | jq '.'

curl -s -X POST "$BASE_URL" \
  -H "Content-Type: application/json" \
  -d '{
    "content": "Test WARNING message",
    "sender": "TestScript",
    "type": "WARNING"
  }' | jq '.'

# Wait for processing
sleep 2

# Get results
echo "3. Retrieving messages:"
curl -s "$BASE_URL/sender/TestScript" | jq '.'

echo "4. Statistics:"
curl -s "$BASE_URL/stats" | jq '.'
```

### Interactive Message Sender
```bash
#!/bin/bash
BASE_URL="http://localhost:8086/api/messages"

echo "📨 Interactive Message Sender"
echo "============================="

while true; do
  echo ""
  read -p "Enter message content (or 'quit' to exit): " content
  
  if [ "$content" = "quit" ]; then
    break
  fi
  
  read -p "Enter sender name: " sender
  read -p "Enter message type (INFO/WARNING/ERROR): " type
  
  curl -X POST "$BASE_URL" \
    -H "Content-Type: application/json" \
    -d "{
      \"content\": \"$content\",
      \"sender\": \"$sender\",
      \"type\": \"$type\"
    }" | jq '.'
done
```

## 📈 Monitoring and Analytics

### Real-time Message Monitoring
```bash
# Monitor new messages (requires additional implementation)
while true; do
  clear
  echo "📊 Current Statistics:"
  curl -s "$BASE_URL/stats" | jq '.'
  echo ""
  echo "📨 Recent Messages:"
  curl -s "$BASE_URL" | jq '.[-3:]'
  sleep 5
done
```

### Export Messages to File
```bash
# Export all messages to JSON file
curl -s "$BASE_URL" | jq '.' > messages_export.json

# Export messages by type
curl -s "$BASE_URL/type/ERROR" | jq '.' > error_messages.json
```

### Generate Report
```bash
#!/bin/bash
BASE_URL="http://localhost:8086/api/messages"

echo "📊 Kafka Demo Message Report"
echo "============================"
echo "Generated: $(date)"
echo ""

# Get statistics
stats=$(curl -s "$BASE_URL/stats")
total=$(echo "$stats" | jq -r '.totalMessagesReceived')
memory=$(echo "$stats" | jq -r '.messagesInMemory')

echo "Total Messages Received: $total"
echo "Messages in Memory: $memory"
echo ""

# Count by type
info_count=$(curl -s "$BASE_URL/type/INFO" | jq '. | length')
warning_count=$(curl -s "$BASE_URL/type/WARNING" | jq '. | length')
error_count=$(curl -s "$BASE_URL/type/ERROR" | jq '. | length')

echo "Message Breakdown:"
echo "  INFO: $info_count"
echo "  WARNING: $warning_count"
echo "  ERROR: $error_count"
echo ""

# Top senders
echo "Top Senders:"
curl -s "$BASE_URL" | jq -r '.[].sender' | sort | uniq -c | sort -rn | head -5
```

## 🔧 Utility Functions

### Bash Functions for Common Operations
```bash
# Add these to your ~/.bashrc or ~/.zshrc

# Quick message sender
send_message() {
  local content="$1"
  local sender="${2:-$(whoami)}"
  local type="${3:-INFO}"
  
  curl -X POST "http://localhost:8086/api/messages" \
    -H "Content-Type: application/json" \
    -d "{
      \"content\": \"$content\",
      \"sender\": \"$sender\",
      \"type\": \"$type\"
    }" | jq '.'
}

# Quick stats check
kafka_stats() {
  curl -s "http://localhost:8086/api/messages/stats" | jq '.'
}

# Get messages by sender
messages_by_sender() {
  local sender="$1"
  curl -s "http://localhost:8086/api/messages/sender/$sender" | jq '.'
}

# Usage examples:
# send_message "Hello World"
# send_message "Warning message" "System" "WARNING"
# kafka_stats
# messages_by_sender "System"
```

## 🎯 Production-Ready Examples

### Health Check with Retry
```bash
#!/bin/bash
check_health() {
  local max_attempts=5
  local attempt=1
  
  while [ $attempt -le $max_attempts ]; do
    if curl -s --max-time 5 "$BASE_URL/health" >/dev/null 2>&1; then
      echo "✅ Service is healthy"
      return 0
    fi
    
    echo "⏳ Attempt $attempt/$max_attempts failed, retrying..."
    sleep 2
    ((attempt++))
  done
  
  echo "❌ Service is unhealthy after $max_attempts attempts"
  return 1
}
```

### Bulk Message Import
```bash
#!/bin/bash
# Import messages from CSV file
# CSV format: content,sender,type

import_messages() {
  local csv_file="$1"
  
  if [ ! -f "$csv_file" ]; then
    echo "Error: File $csv_file not found"
    return 1
  fi
  
  while IFS=',' read -r content sender type; do
    curl -X POST "$BASE_URL" \
      -H "Content-Type: application/json" \
      -d "{
        \"content\": \"$content\",
        \"sender\": \"$sender\",
        \"type\": \"$type\"
      }"
    sleep 0.1  # Rate limiting
  done < "$csv_file"
}
```

## 🔒 Security Considerations

### Using Environment Variables
```bash
# Set sensitive data in environment variables
export KAFKA_API_BASE_URL="http://localhost:8086/api/messages"
export KAFKA_API_TOKEN="your-api-token"  # If authentication was implemented

# Use in requests
curl -X GET "$KAFKA_API_BASE_URL/health" \
  -H "Authorization: Bearer $KAFKA_API_TOKEN"
```

### Request Validation
```bash
# Validate JSON before sending
validate_and_send() {
  local json_data="$1"
  
  # Validate JSON syntax
  if ! echo "$json_data" | jq empty; then
    echo "Error: Invalid JSON format"
    return 1
  fi
  
  # Send the request
  curl -X POST "$BASE_URL" \
    -H "Content-Type: application/json" \
    -d "$json_data"
}
```

## 📝 Notes

- **Base URL**: Adjust `http://localhost:8086` if your application runs on a different port
- **JSON Formatting**: Install `jq` for better JSON output formatting
- **Error Handling**: Add `--fail` flag to curl for proper error handling in scripts
- **Rate Limiting**: Add delays between requests for bulk operations
- **Timeouts**: Use `--max-time` to prevent hanging requests

## 🚀 Quick Reference

```bash
# Health check
curl http://localhost:8086/api/messages/health

# Send message
curl -X POST http://localhost:8086/api/messages \
  -H "Content-Type: application/json" \
  -d '{"content":"Hello","sender":"Me","type":"INFO"}'

# Get all messages
curl http://localhost:8086/api/messages | jq '.'

# Get statistics
curl http://localhost:8086/api/messages/stats | jq '.'
```

That's it! Use these examples as a starting point for your own API testing and integration work. 🎉
