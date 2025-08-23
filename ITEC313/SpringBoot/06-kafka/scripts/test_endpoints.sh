#!/bin/bash

# Kafka Demo API Test Script
# This script demonstrates the basic API endpoints

BASE_URL="http://localhost:8080/api/messages"

echo "🚀 Testing Kafka Demo API Endpoints"
echo "=================================="

# Wait for application to start
echo "⏳ Waiting for application to start..."
sleep 5

# Test health check
echo ""
echo "1. Testing Health Check"
echo "----------------------"
curl -s "$BASE_URL/health" | jq '.'

# Send a test message
echo ""
echo "2. Sending Test Message"
echo "----------------------"
curl -s -X POST "$BASE_URL" \
  -H "Content-Type: application/json" \
  -d '{
    "content": "Hello from test script!",
    "sender": "TestUser",
    "type": "INFO"
  }' | jq '.'

# Send another message
echo ""
echo "3. Sending Another Message"
echo "-------------------------"
curl -s -X POST "$BASE_URL" \
  -H "Content-Type: application/json" \
  -d '{
    "content": "This is a warning message",
    "sender": "TestUser",
    "type": "WARNING"
  }' | jq '.'

# Get all messages
echo ""
echo "4. Getting All Messages"
echo "----------------------"
curl -s "$BASE_URL" | jq '.'

# Filter by sender
echo ""
echo "5. Filtering Messages by Sender"
echo "-------------------------------"
curl -s "$BASE_URL/sender/TestUser" | jq '.'

# Filter by type
echo ""
echo "6. Filtering Messages by Type"
echo "-----------------------------"
curl -s "$BASE_URL/type/info" | jq '.'

# Get statistics
echo ""
echo "7. Getting Message Statistics"
echo "-----------------------------"
curl -s "$BASE_URL/stats" | jq '.'

# Test error handling
echo ""
echo "8. Testing Error Handling (Invalid Message Type)"
echo "------------------------------------------------"
curl -s "$BASE_URL/type/invalid" | jq '.'

echo ""
echo "✅ API Testing Complete!"
echo ""
echo "💡 Try these additional commands:"
echo "   - Clear messages: curl -X DELETE $BASE_URL"
echo "   - Get recent messages: curl $BASE_URL"
echo "   - Filter by warning: curl $BASE_URL/type/warning"
