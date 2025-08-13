#!/bin/bash

# Test script for Microservices endpoints
echo "Testing Microservices Endpoints"
echo "==============================="

# Test Eureka Server
echo -e "\n1. Testing Eureka Server:"
echo "----------------------------"
echo "Eureka Dashboard: http://localhost:8761"

# Test Config Server
echo -e "\n2. Testing Config Server:"
echo "----------------------------"
curl -X GET "http://localhost:8888/actuator/health"

# Test Data Service directly
echo -e "\n\n3. Testing Data Service (Direct):"
echo "------------------------------------"

# Create a data item
echo "Creating a data item..."
curl -X POST "http://localhost:8088/api/data" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test Item",
    "description": "This is a test item created via API"
  }'

echo -e "\n\nGetting all data items..."
curl -X GET "http://localhost:8088/api/data"

echo -e "\n\nSearching for items..."
curl -X GET "http://localhost:8088/api/data/search?keyword=test"

echo -e "\n\nHealth check..."
curl -X GET "http://localhost:8088/api/data/health"

# Test API Gateway
echo -e "\n\n4. Testing API Gateway:"
echo "-------------------------"

# Test gateway routing to data service
echo "Testing gateway routing to data service..."
curl -X GET "http://localhost:8087/api/data"

echo -e "\n\nTesting gateway health..."
curl -X GET "http://localhost:8087/actuator/health"

echo -e "\n\nTesting gateway info..."
curl -X GET "http://localhost:8087/actuator/info"

# Test Circuit Breaker (if available)
echo -e "\n\n5. Testing Circuit Breaker:"
echo "-----------------------------"
curl -X GET "http://localhost:8088/actuator/circuitbreakers"

echo -e "\n\nTesting completed!"
echo "===================="
echo "Services to check:"
echo "- Eureka Dashboard: http://localhost:8761"
echo "- Config Server: http://localhost:8888"
echo "- Data Service: http://localhost:8088"
echo "- API Gateway: http://localhost:8087"
