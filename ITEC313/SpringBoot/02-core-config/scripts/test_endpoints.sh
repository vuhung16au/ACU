#!/bin/bash

# Test script for Day 2: Core Config endpoints
# This script demonstrates the core Spring Boot concepts

BASE_URL=${BASE_URL:-"http://localhost:8080"}
DELAY=${DELAY:-1}

echo "🧪 Testing Day 2: Core Config Endpoints"
echo "========================================"
echo "Base URL: $BASE_URL"
echo ""

# Function to test an endpoint
test_endpoint() {
    local method=$1
    local endpoint=$2
    local description=$3
    local data=$4
    
    echo "📋 Testing: $description"
    echo "   $method $BASE_URL$endpoint"
    
    if [ "$method" = "GET" ]; then
        response=$(curl -s -w "\n%{http_code}" "$BASE_URL$endpoint")
    elif [ "$method" = "POST" ]; then
        response=$(curl -s -w "\n%{http_code}" -X POST "$BASE_URL$endpoint$data")
    fi
    
    # Extract status code (last line)
    status_code=$(echo "$response" | tail -n1)
    # Extract response body (all lines except last)
    body=$(echo "$response" | head -n -1)
    
    echo "   Status: $status_code"
    echo "   Response: $body"
    echo ""
    
    sleep $DELAY
}

# Test configuration endpoints
echo "🔧 Configuration Endpoints"
echo "-------------------------"

test_endpoint "GET" "/api/config" "Get all application configuration"
test_endpoint "GET" "/api/config/timestamp" "Get current timestamp"
test_endpoint "GET" "/api/config/timestamp/default" "Get timestamp with default format"
test_endpoint "GET" "/api/config/timestamp/short" "Get timestamp with short format"
test_endpoint "POST" "/api/config/log" "Trigger configuration logging" "?level=debug"
test_endpoint "GET" "/api/config/health" "Health check endpoint"

# Test actuator endpoints
echo "📊 Actuator Endpoints"
echo "--------------------"

test_endpoint "GET" "/actuator/health" "Application health status"
test_endpoint "GET" "/actuator/info" "Application information"
test_endpoint "GET" "/actuator/configprops" "Configuration properties"
test_endpoint "GET" "/actuator/env" "Environment variables"

echo "✅ Testing completed!"
echo ""
echo "💡 Tips:"
echo "   - Run with different profiles: mvn spring-boot:run -Dspring-boot.run.profiles=dev"
echo "   - Check logs in logs/core-config.log"
echo "   - Explore actuator endpoints for detailed configuration information"
