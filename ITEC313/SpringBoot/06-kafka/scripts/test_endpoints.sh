#!/bin/bash

# Test script for Kafka, Email, and Scheduling endpoints
BASE_URL="http://localhost:8086"

echo "Testing Kafka, Email, and Scheduling Endpoints"
echo "=============================================="

# Test Kafka endpoints
echo -e "\n1. Testing Kafka Endpoints:"
echo "----------------------------"

# Send a message to Kafka
echo "Sending message to Kafka..."
curl -X POST "$BASE_URL/api/messages" \
  -H "Content-Type: application/json" \
  -d '{
    "content": "Hello from test script!",
    "sender": "test-script",
    "type": "INFO"
  }'

echo -e "\n\nGetting messages from Kafka..."
curl -X GET "$BASE_URL/api/messages"

# Test Email endpoints
echo -e "\n\n2. Testing Email Endpoints:"
echo "----------------------------"

# Send a simple email
echo "Sending simple email..."
curl -X POST "$BASE_URL/api/email/send" \
  -H "Content-Type: application/json" \
  -d '{
    "to": "test@example.com",
    "subject": "Test Email",
    "text": "This is a test email from Spring Boot Kafka demo"
  }'

echo -e "\n\nSending welcome email..."
curl -X POST "$BASE_URL/api/email/send-welcome" \
  -H "Content-Type: application/json" \
  -d '{
    "to": "test@example.com",
    "username": "TestUser"
  }'

echo -e "\n\nSending notification email..."
curl -X POST "$BASE_URL/api/email/send-notification" \
  -H "Content-Type: application/json" \
  -d '{
    "to": "test@example.com",
    "type": "System Alert",
    "message": "This is a test notification from the scheduler"
  }'

echo -e "\n\nGetting available email templates..."
curl -X GET "$BASE_URL/api/email/templates"

# Test Scheduler endpoints
echo -e "\n\n3. Testing Scheduler Endpoints:"
echo "--------------------------------"

echo "Getting scheduler status..."
curl -X GET "$BASE_URL/api/scheduler/status"

echo -e "\n\nTriggering manual task..."
curl -X POST "$BASE_URL/api/scheduler/trigger"

echo -e "\n\nResetting counters..."
curl -X POST "$BASE_URL/api/scheduler/reset"

# Test Actuator endpoints
echo -e "\n\n4. Testing Actuator Endpoints:"
echo "--------------------------------"

echo "Health check..."
curl -X GET "$BASE_URL/actuator/health"

echo -e "\n\nApplication info..."
curl -X GET "$BASE_URL/actuator/info"

echo -e "\n\nMetrics..."
curl -X GET "$BASE_URL/actuator/metrics"

echo -e "\n\nTesting completed!"
