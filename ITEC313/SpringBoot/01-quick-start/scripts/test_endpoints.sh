#!/bin/bash

# Test script for 01-quick-start Spring Boot application
# This script tests the basic endpoints and actuator endpoints

BASE_URL=${BASE_URL:-"http://localhost:8080"}
ACTUATOR_BASE="${BASE_URL}/actuator"

echo "Testing 01-quick-start Spring Boot Application"
echo "Base URL: $BASE_URL"
echo "Actuator Base URL: $ACTUATOR_BASE"
echo "================================================"

# Test basic endpoints
echo ""
echo "1. Testing basic endpoints:"
echo "---------------------------"

echo "GET /hello"
curl -s "${BASE_URL}/hello"
echo -e "\n"

echo "GET /hello/name"
curl -s "${BASE_URL}/hello/name" | jq . 2>/dev/null || curl -s "${BASE_URL}/hello/name"
echo -e "\n"

echo "GET /hello/name?name=Spring"
curl -s "${BASE_URL}/hello/name?name=Spring" | jq . 2>/dev/null || curl -s "${BASE_URL}/hello/name?name=Spring"
echo -e "\n"

echo "GET /health"
curl -s "${BASE_URL}/health" | jq . 2>/dev/null || curl -s "${BASE_URL}/health"
echo -e "\n"

# Test actuator endpoints
echo ""
echo "2. Testing actuator endpoints:"
echo "-----------------------------"

echo "GET /actuator/health"
curl -s "${ACTUATOR_BASE}/health" | jq . 2>/dev/null || curl -s "${ACTUATOR_BASE}/health"
echo -e "\n"

echo "GET /actuator/info"
curl -s "${ACTUATOR_BASE}/info" | jq . 2>/dev/null || curl -s "${ACTUATOR_BASE}/info"
echo -e "\n"

echo "GET /actuator/metrics"
curl -s "${ACTUATOR_BASE}/metrics" | jq . 2>/dev/null || curl -s "${ACTUATOR_BASE}/metrics"
echo -e "\n"

echo "GET /actuator/env"
curl -s "${ACTUATOR_BASE}/env" | jq . 2>/dev/null || curl -s "${ACTUATOR_BASE}/env"
echo -e "\n"

echo "================================================"
echo "Testing completed!"
echo ""
echo "To run with different profiles:"
echo "  Development: mvn spring-boot:run -Dspring-boot.run.profiles=dev"
echo "  Production: mvn spring-boot:run -Dspring-boot.run.profiles=prod"
