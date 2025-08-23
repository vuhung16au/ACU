#!/bin/bash

# Kafka Demo Script
# This script demonstrates the basic Kafka API functionality

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
CYAN='\033[0;36m'
NC='\033[0m' # No Color

BASE_URL="http://localhost:8086/api/messages"

echo -e "${CYAN}🚀 Kafka Demo - Spring Boot with Apache Kafka${NC}"
echo -e "${CYAN}==============================================${NC}"
echo ""

# Check if jq is available
if ! command -v jq &> /dev/null; then
    echo -e "${YELLOW}⚠️  jq not found. Output will be raw JSON.${NC}"
    echo -e "${YELLOW}   Install jq for better formatted output: brew install jq${NC}"
    echo ""
fi

# Function to format JSON output
format_json() {
    if command -v jq &> /dev/null; then
        jq '.'
    else
        cat
    fi
}

# Function to make HTTP requests with better error handling
make_request() {
    local method=$1
    local endpoint=$2
    local data=$3
    
    local url="$BASE_URL$endpoint"
    local response
    local status_code
    
    if [ -n "$data" ]; then
        response=$(curl -s -w "\n%{http_code}" -X "$method" "$url" \
            -H "Content-Type: application/json" \
            -d "$data" \
            --max-time 10 2>/dev/null || echo -e "\n000")
    else
        response=$(curl -s -w "\n%{http_code}" -X "$method" "$url" \
            --max-time 10 2>/dev/null || echo -e "\n000")
    fi
    
    status_code=$(echo "$response" | tail -n1)
    response_body=$(echo "$response" | sed '$d')
    
    if [ "$status_code" -eq 200 ] || [ "$status_code" -eq 201 ]; then
        echo "$response_body" | format_json
        return 0
    else
        echo -e "${RED}❌ Request failed with status: $status_code${NC}"
        if [ -n "$response_body" ]; then
            echo "$response_body" | format_json
        fi
        return 1
    fi
}

# Wait for application to start
echo -e "${BLUE}⏳ Checking if application is running...${NC}"
if ! curl -s --max-time 5 "$BASE_URL/health" >/dev/null 2>&1; then
    echo -e "${YELLOW}⚠️  Application not accessible at $BASE_URL${NC}"
    echo -e "${YELLOW}   Make sure to start the application first:${NC}"
    echo -e "${YELLOW}   ./scripts/quick-start.sh${NC}"
    echo -e "${YELLOW}   or${NC}"
    echo -e "${YELLOW}   mvn spring-boot:run${NC}"
    echo ""
    exit 1
fi
echo -e "${GREEN}✅ Application is running!${NC}"
echo ""

# Test health check
echo -e "${BLUE}1. Health Check${NC}"
echo "---------------"
make_request "GET" "/health"
echo ""

# Send different types of messages
echo -e "${BLUE}2. Sending INFO Message${NC}"
echo "----------------------"
make_request "POST" "" '{
  "content": "This is an informational message from the demo",
  "sender": "DemoUser",
  "type": "INFO"
}'
echo ""

echo -e "${BLUE}3. Sending WARNING Message${NC}"
echo "-------------------------"
make_request "POST" "" '{
  "content": "This is a warning message from the demo",
  "sender": "DemoUser", 
  "type": "WARNING"
}'
echo ""

echo -e "${BLUE}4. Sending ERROR Message${NC}"
echo "-----------------------"
make_request "POST" "" '{
  "content": "This is an error message from the demo",
  "sender": "DemoUser",
  "type": "ERROR"
}'
echo ""

# Wait a moment for messages to be processed
echo -e "${YELLOW}⏳ Waiting for messages to be processed...${NC}"
sleep 3
echo ""

# Get all messages
echo -e "${BLUE}5. Getting All Messages${NC}"
echo "----------------------"
make_request "GET" ""
echo ""

# Get messages by sender
echo -e "${BLUE}6. Getting Messages by Sender (DemoUser)${NC}"
echo "----------------------------------------"
make_request "GET" "/sender/DemoUser"
echo ""

# Get messages by type
echo -e "${BLUE}7. Getting Messages by Type (INFO)${NC}"
echo "---------------------------------"
make_request "GET" "/type/INFO"
echo ""

# Get statistics
echo -e "${BLUE}8. Getting Message Statistics${NC}"
echo "----------------------------"
make_request "GET" "/stats"
echo ""

echo -e "${GREEN}✅ Demo completed successfully!${NC}"
echo ""
echo -e "${CYAN}📊 Access Information:${NC}"
echo -e "  🌐 Kafka UI:       ${GREEN}http://localhost:8080${NC}"
echo -e "  🚀 API Base:       ${GREEN}$BASE_URL${NC}"
echo -e "  📈 Health Check:   ${GREEN}$BASE_URL/health${NC}"
echo ""
echo -e "${CYAN}🔍 Next Steps:${NC}"
echo -e "  • View real-time logs: ${YELLOW}tail -f application.log${NC}"
echo -e "  • Run more tests: ${YELLOW}./scripts/dev-test.sh${NC}"
echo -e "  • Monitor Kafka: ${YELLOW}open http://localhost:8080${NC}"
echo -e "  • Read API docs: ${YELLOW}cat API_DOCUMENTATION.md${NC}"
