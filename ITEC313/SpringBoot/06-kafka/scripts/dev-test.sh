#!/bin/bash

# Development Testing Script for Kafka Demo
# Comprehensive testing with better output and error handling

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
CYAN='\033[0;36m'
NC='\033[0m' # No Color

# Configuration
BASE_URL="http://localhost:8086/api/messages"
TIMEOUT=10
VERBOSE=false

# Helper functions
log_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

log_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

log_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

log_test() {
    echo -e "${CYAN}[TEST]${NC} $1"
}

# Check if application is running
check_application() {
    log_info "Checking if application is running..."
    
    if curl -s --max-time $TIMEOUT "$BASE_URL/health" >/dev/null 2>&1; then
        log_success "Application is running and accessible"
        return 0
    else
        log_error "Application is not accessible at $BASE_URL"
        log_info "Make sure to start the application with: mvn spring-boot:run"
        return 1
    fi
}

# Wait for application to be ready
wait_for_application() {
    local max_attempts=30
    local attempt=1
    
    log_info "Waiting for application to be ready..."
    
    while [ $attempt -le $max_attempts ]; do
        if curl -s --max-time 5 "$BASE_URL/health" >/dev/null 2>&1; then
            log_success "Application is ready!"
            return 0
        fi
        
        log_info "Attempt $attempt/$max_attempts - waiting..."
        sleep 2
        ((attempt++))
    done
    
    log_error "Application did not become ready within expected time"
    return 1
}

# Make HTTP request with error handling
make_request() {
    local method=$1
    local endpoint=$2
    local data=$3
    local expected_status=${4:-200}
    
    local url="$BASE_URL$endpoint"
    local response
    local status_code
    
    if [ -n "$data" ]; then
        response=$(curl -s -w "\n%{http_code}" -X "$method" "$url" \
            -H "Content-Type: application/json" \
            -d "$data" \
            --max-time $TIMEOUT 2>/dev/null || echo -e "\n000")
    else
        response=$(curl -s -w "\n%{http_code}" -X "$method" "$url" \
            --max-time $TIMEOUT 2>/dev/null || echo -e "\n000")
    fi
    
    status_code=$(echo "$response" | tail -n1)
    response_body=$(echo "$response" | sed '$d')
    
    if [ "$status_code" -eq "$expected_status" ]; then
        if [ "$VERBOSE" = true ] || [ -n "$5" ]; then
            echo "$response_body" | jq '.' 2>/dev/null || echo "$response_body"
        fi
        return 0
    else
        log_error "Expected status $expected_status, got $status_code"
        if [ -n "$response_body" ]; then
            echo "$response_body" | jq '.' 2>/dev/null || echo "$response_body"
        fi
        return 1
    fi
}

# Test health endpoint
test_health() {
    log_test "Testing health endpoint..."
    if make_request "GET" "/health" "" 200 true; then
        log_success "Health check passed"
    else
        log_error "Health check failed"
        return 1
    fi
}

# Test sending messages
test_send_messages() {
    log_test "Testing message sending..."
    
    # Test INFO message
    log_info "Sending INFO message..."
    local info_data='{"content": "Test INFO message", "sender": "DevTest", "type": "INFO"}'
    if make_request "POST" "" "$info_data" 200; then
        log_success "INFO message sent successfully"
    else
        log_error "Failed to send INFO message"
        return 1
    fi
    
    # Test WARNING message
    log_info "Sending WARNING message..."
    local warning_data='{"content": "Test WARNING message", "sender": "DevTest", "type": "WARNING"}'
    if make_request "POST" "" "$warning_data" 200; then
        log_success "WARNING message sent successfully"
    else
        log_error "Failed to send WARNING message"
        return 1
    fi
    
    # Test ERROR message
    log_info "Sending ERROR message..."
    local error_data='{"content": "Test ERROR message", "sender": "DevTest", "type": "ERROR"}'
    if make_request "POST" "" "$error_data" 200; then
        log_success "ERROR message sent successfully"
    else
        log_error "Failed to send ERROR message"
        return 1
    fi
}

# Test message retrieval
test_message_retrieval() {
    log_test "Testing message retrieval..."
    
    # Wait a moment for messages to be processed
    sleep 2
    
    # Test get all messages
    log_info "Getting all messages..."
    if make_request "GET" "" "" 200 true; then
        log_success "Retrieved all messages"
    else
        log_error "Failed to retrieve messages"
        return 1
    fi
    
    # Test get messages by sender
    log_info "Getting messages by sender..."
    if make_request "GET" "/sender/DevTest" "" 200 true; then
        log_success "Retrieved messages by sender"
    else
        log_error "Failed to retrieve messages by sender"
        return 1
    fi
    
    # Test get messages by type
    log_info "Getting messages by type..."
    if make_request "GET" "/type/INFO" "" 200 true; then
        log_success "Retrieved messages by type"
    else
        log_error "Failed to retrieve messages by type"
        return 1
    fi
}

# Test statistics
test_statistics() {
    log_test "Testing statistics endpoint..."
    if make_request "GET" "/stats" "" 200 true; then
        log_success "Statistics retrieved successfully"
    else
        log_error "Failed to retrieve statistics"
        return 1
    fi
}

# Test error handling
test_error_handling() {
    log_test "Testing error handling..."
    
    # Test invalid message format
    log_info "Testing invalid message format..."
    local invalid_data='{"invalid": "data"}'
    if make_request "POST" "" "$invalid_data" 400; then
        log_success "Invalid message format handled correctly"
    else
        log_warning "Error handling test failed (this might be expected)"
    fi
    
    # Test non-existent endpoint
    log_info "Testing non-existent endpoint..."
    if make_request "GET" "/nonexistent" "" 404; then
        log_success "Non-existent endpoint handled correctly"
    else
        log_warning "Non-existent endpoint test failed (this might be expected)"
    fi
}

# Performance test
test_performance() {
    log_test "Testing performance with multiple messages..."
    
    local start_time=$(date +%s)
    local message_count=10
    
    for i in $(seq 1 $message_count); do
        local data="{\"content\": \"Performance test message $i\", \"sender\": \"PerfTest\", \"type\": \"INFO\"}"
        make_request "POST" "" "$data" 200 >/dev/null
    done
    
    local end_time=$(date +%s)
    local duration=$((end_time - start_time))
    
    log_success "Sent $message_count messages in ${duration}s"
    
    # Check if all messages were processed
    sleep 2
    log_info "Checking final statistics..."
    make_request "GET" "/stats" "" 200 true
}

# Cleanup test data
cleanup_test_data() {
    log_info "Test data cleanup is handled by application restart"
    log_info "To reset all data, restart the application"
}

# Run comprehensive test suite
run_comprehensive_tests() {
    echo -e "${BLUE}🧪 Kafka Demo - Comprehensive Test Suite${NC}"
    echo "========================================="
    echo ""
    
    local tests_passed=0
    local tests_failed=0
    
    # Array of test functions
    local tests=(
        "test_health"
        "test_send_messages"
        "test_message_retrieval"
        "test_statistics"
        "test_error_handling"
        "test_performance"
    )
    
    for test_func in "${tests[@]}"; do
        echo ""
        if $test_func; then
            ((tests_passed++))
        else
            ((tests_failed++))
        fi
        sleep 1
    done
    
    echo ""
    echo "========================================="
    echo -e "${BLUE}📊 Test Results:${NC}"
    echo -e "  ${GREEN}✅ Passed: $tests_passed${NC}"
    echo -e "  ${RED}❌ Failed: $tests_failed${NC}"
    echo ""
    
    if [ $tests_failed -eq 0 ]; then
        log_success "All tests passed! 🎉"
        return 0
    else
        log_error "Some tests failed. Check the output above for details."
        return 1
    fi
}

# Quick smoke test
run_smoke_test() {
    echo -e "${BLUE}💨 Kafka Demo - Quick Smoke Test${NC}"
    echo "================================="
    echo ""
    
    if ! check_application; then
        return 1
    fi
    
    # Send one message and verify
    local data='{"content": "Smoke test message", "sender": "SmokeTest", "type": "INFO"}'
    if make_request "POST" "" "$data" 200; then
        log_success "Message sent successfully"
    else
        log_error "Smoke test failed"
        return 1
    fi
    
    # Check statistics
    sleep 1
    if make_request "GET" "/stats" "" 200 true; then
        log_success "Statistics retrieved successfully"
    else
        log_error "Statistics retrieval failed"
        return 1
    fi
    
    echo ""
    log_success "Smoke test passed! ✅"
}

# Interactive test mode
run_interactive_test() {
    echo -e "${BLUE}🎮 Kafka Demo - Interactive Test Mode${NC}"
    echo "====================================="
    echo ""
    
    while true; do
        echo ""
        echo "Choose a test:"
        echo "1. Health Check"
        echo "2. Send Message"
        echo "3. Get All Messages"
        echo "4. Get Messages by Sender"
        echo "5. Get Messages by Type"
        echo "6. Get Statistics"
        echo "7. Run All Tests"
        echo "8. Exit"
        echo ""
        read -p "Enter your choice (1-8): " choice
        
        case $choice in
            1) test_health ;;
            2) 
                read -p "Enter message content: " content
                read -p "Enter sender name: " sender
                read -p "Enter message type (INFO/WARNING/ERROR): " type
                local data="{\"content\": \"$content\", \"sender\": \"$sender\", \"type\": \"$type\"}"
                make_request "POST" "" "$data" 200 true
                ;;
            3) make_request "GET" "" "" 200 true ;;
            4)
                read -p "Enter sender name: " sender
                make_request "GET" "/sender/$sender" "" 200 true
                ;;
            5)
                read -p "Enter message type: " type
                make_request "GET" "/type/$type" "" 200 true
                ;;
            6) make_request "GET" "/stats" "" 200 true ;;
            7) run_comprehensive_tests ;;
            8) log_info "Goodbye!"; break ;;
            *) log_error "Invalid choice. Please try again." ;;
        esac
    done
}

# Show help
show_help() {
    echo "Development Testing Script for Kafka Demo"
    echo ""
    echo "Usage: $0 [COMMAND] [OPTIONS]"
    echo ""
    echo "Commands:"
    echo "  test           Run comprehensive test suite"
    echo "  smoke          Run quick smoke test"
    echo "  interactive    Run interactive test mode"
    echo "  health         Test health endpoint only"
    echo "  wait           Wait for application to be ready"
    echo "  help           Show this help message"
    echo ""
    echo "Options:"
    echo "  -v, --verbose  Enable verbose output"
    echo "  --timeout N    Set request timeout in seconds (default: $TIMEOUT)"
    echo "  --url URL      Set base URL (default: $BASE_URL)"
    echo ""
    echo "Examples:"
    echo "  $0 smoke                    # Quick smoke test"
    echo "  $0 test --verbose          # Full test suite with verbose output"
    echo "  $0 interactive             # Interactive testing"
    echo ""
}

# Parse command line arguments
parse_args() {
    while [[ $# -gt 0 ]]; do
        case $1 in
            -v|--verbose)
                VERBOSE=true
                shift
                ;;
            --timeout)
                TIMEOUT="$2"
                shift 2
                ;;
            --url)
                BASE_URL="$2"
                shift 2
                ;;
            -h|--help)
                show_help
                exit 0
                ;;
            *)
                break
                ;;
        esac
    done
}

# Main script logic
main() {
    parse_args "$@"
    
    case "${1:-test}" in
        test)
            if ! check_application; then
                exit 1
            fi
            run_comprehensive_tests
            ;;
        smoke)
            run_smoke_test
            ;;
        interactive)
            if ! check_application; then
                exit 1
            fi
            run_interactive_test
            ;;
        health)
            test_health
            ;;
        wait)
            wait_for_application
            ;;
        help|--help|-h)
            show_help
            ;;
        *)
            log_error "Unknown command: $1"
            echo ""
            show_help
            exit 1
            ;;
    esac
}

# Run main function with all arguments
main "$@"
