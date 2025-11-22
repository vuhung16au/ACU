#!/bin/bash
#
# Reverse Proxy Test Suite
# Tests Nginx reverse proxy functionality
#

# Color codes
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Test counters
TOTAL_TESTS=5
PASSED_TESTS=0
FAILED_TESTS=0

# Create logs directory
LOGS_DIR="logs"
mkdir -p "$LOGS_DIR"
TIMESTAMP=$(date +"%Y-%m-%d-%H%M%S")
LOG_FILE="$LOGS_DIR/reverse-proxy-test-$TIMESTAMP.log"

# Function to print colored output
print_header() {
    echo -e "${YELLOW}========================================${NC}"
    echo -e "${YELLOW}$1${NC}"
    echo -e "${YELLOW}========================================${NC}"
}

print_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}✓ PASS${NC} $1"
}

print_fail() {
    echo -e "${RED}✗ FAIL${NC} $1"
}

# Function to log and display
log_and_display() {
    echo "$1" | tee -a "$LOG_FILE"
}

# Function to check if Docker is running
check_docker() {
    if ! docker info > /dev/null 2>&1; then
        print_fail "Docker is not running or not accessible"
        echo "Please start Docker and try again."
        exit 1
    fi
}

# Function to check if containers are running
check_containers() {
    local required_containers=("internet-client" "nginx-reverse-proxy" "backend-server-1" "backend-server-2")
    local missing_containers=()
    
    for container in "${required_containers[@]}"; do
        if ! docker ps --format '{{.Names}}' | grep -q "^${container}$"; then
            missing_containers+=("$container")
        fi
    done
    
    if [ ${#missing_containers[@]} -gt 0 ]; then
        print_fail "Required containers are not running: ${missing_containers[*]}"
        echo ""
        echo "Please run 'make build' or 'cd reverse-proxy-lab && docker compose up -d' first"
        exit 1
    fi
}

# Test 1: Load Balancing
test_load_balancing() {
    print_header "[TEST 1] Load Balancing"
    
    echo "ACTION: Send 10 requests to nginx-reverse-proxy"
    echo "EXPECTED: ~50/50 distribution between backend-1 and backend-2 (round-robin)"
    echo ""
    
    # Send 10 requests and count backends
    BACKEND1_COUNT=0
    BACKEND2_COUNT=0
    
    for i in {1..10}; do
        RESPONSE=$(docker exec internet-client curl -s http://nginx-reverse-proxy 2>&1)
        
        if echo "$RESPONSE" | grep -q "BACKEND-1"; then
            ((BACKEND1_COUNT++))
        elif echo "$RESPONSE" | grep -q "BACKEND-2"; then
            ((BACKEND2_COUNT++))
        fi
    done
    
    echo "RESULTS:"
    echo "  Backend Server 1: $BACKEND1_COUNT requests"
    echo "  Backend Server 2: $BACKEND2_COUNT requests"
    echo ""
    
    # Check if load is distributed (both should have at least 1 request)
    if [ $BACKEND1_COUNT -gt 0 ] && [ $BACKEND2_COUNT -gt 0 ]; then
        print_success "Load balancing working (traffic distributed to both backends)"
        ((PASSED_TESTS++))
        
        # Calculate balance (should be roughly even)
        DIFF=$((BACKEND1_COUNT - BACKEND2_COUNT))
        DIFF=${DIFF#-}  # Absolute value
        
        if [ $DIFF -le 2 ]; then
            print_info "Distribution is well-balanced (difference: $DIFF)"
        else
            print_info "Distribution has some variance (difference: $DIFF) - this is normal for small sample sizes"
        fi
        
    else
        print_fail "Load balancing not working (one or both backends not receiving traffic)"
        ((FAILED_TESTS++))
        
        if [ $BACKEND1_COUNT -eq 0 ]; then
            echo "⚠ WARNING: Backend Server 1 received no requests"
        fi
        if [ $BACKEND2_COUNT -eq 0 ]; then
            echo "⚠ WARNING: Backend Server 2 received no requests"
        fi
    fi
    
    echo ""
}

# Test 2: Backend Isolation
test_backend_isolation() {
    print_header "[TEST 2] Backend Isolation"
    
    echo "ACTION: Try direct connection to backend-server-1 (without proxy)"
    echo "EXPECTED: Connection failure (no route to backend network)"
    echo ""
    
    # Try direct connection to backend-server-1
    RESPONSE1=$(docker exec internet-client curl -s -m 5 \
        http://backend-server-1 2>&1)
    EXIT_CODE1=$?
    
    # Try direct connection to backend-server-2
    RESPONSE2=$(docker exec internet-client curl -s -m 5 \
        http://backend-server-2 2>&1)
    EXIT_CODE2=$?
    
    # Both connections should fail
    if [ $EXIT_CODE1 -ne 0 ] && [ $EXIT_CODE2 -ne 0 ]; then
        print_success "Backend isolation working (direct access blocked)"
        ((PASSED_TESTS++))
        print_info "Backends are properly isolated on internal network"
        
    else
        print_fail "Backend isolation failed (direct access possible!)"
        ((FAILED_TESTS++))
        echo "⚠ WARNING: Security issue - backends should not be directly accessible"
        
        if [ $EXIT_CODE1 -eq 0 ]; then
            echo "  - backend-server-1 is accessible"
        fi
        if [ $EXIT_CODE2 -eq 0 ]; then
            echo "  - backend-server-2 is accessible"
        fi
    fi
    
    echo ""
}

# Test 3: Header Manipulation
test_header_manipulation() {
    print_header "[TEST 3] Header Manipulation"
    
    echo "ACTION: Check response headers from reverse proxy"
    echo "EXPECTED: Backend details hidden, custom security headers present"
    echo ""
    
    # Get headers
    HEADERS=$(docker exec internet-client curl -s -I http://nginx-reverse-proxy 2>&1)
    
    echo -e "${BLUE}RESPONSE HEADERS:${NC}"
    echo "$HEADERS" | grep -E "^HTTP|^Server:|^X-"
    echo ""
    
    # Check for security features
    CHECKS_PASSED=0
    CHECKS_TOTAL=3
    
    # Check 1: Generic server header (not revealing backend details)
    if echo "$HEADERS" | grep -qi "Server: nginx"; then
        print_info "✓ Server header is generic (nginx)"
        ((CHECKS_PASSED++))
    else
        print_info "✗ Server header may reveal too much information"
    fi
    
    # Check 2: Custom proxy header present
    if echo "$HEADERS" | grep -qi "X-Proxy-By"; then
        print_info "✓ Custom proxy header present (X-Proxy-By)"
        ((CHECKS_PASSED++))
    else
        print_info "✗ Custom proxy header missing"
    fi
    
    # Check 3: No sensitive headers leaked
    if ! echo "$HEADERS" | grep -qi "X-Powered-By"; then
        print_info "✓ Sensitive headers removed (X-Powered-By not present)"
        ((CHECKS_PASSED++))
    else
        print_info "✗ Sensitive header leaked (X-Powered-By)"
    fi
    
    echo ""
    
    if [ $CHECKS_PASSED -ge 2 ]; then
        print_success "Header manipulation working ($CHECKS_PASSED/$CHECKS_TOTAL checks passed)"
        ((PASSED_TESTS++))
    else
        print_fail "Header manipulation needs improvement ($CHECKS_PASSED/$CHECKS_TOTAL checks passed)"
        ((FAILED_TESTS++))
    fi
    
    echo ""
}

# Test 4: Forbidden Method
test_forbidden_method() {
    print_header "[TEST 4] Forbidden Method (HTTP Method Filtering)"
    
    echo "ACTION: Send DELETE request to reverse proxy"
    echo "EXPECTED: HTTP 405 Method Not Allowed"
    echo ""
    
    # Try DELETE method
    RESPONSE=$(docker exec internet-client curl -s -w "\nHTTP_CODE:%{http_code}" \
        -X DELETE \
        http://nginx-reverse-proxy/index.html 2>&1)
    
    HTTP_CODE=$(echo "$RESPONSE" | grep "HTTP_CODE:" | cut -d: -f2)
    
    echo "HTTP Response Code: $HTTP_CODE"
    echo ""
    
    # Should be 403 (limit_except) or 405
    if [ "$HTTP_CODE" = "403" ] || [ "$HTTP_CODE" = "405" ]; then
        print_success "HTTP method filtering working (DELETE blocked with HTTP $HTTP_CODE)"
        ((PASSED_TESTS++))
        print_info "Only GET and HEAD methods are allowed (limit_except directive)"
        
    else
        print_fail "HTTP method filtering not working (expected 403 or 405, got $HTTP_CODE)"
        ((FAILED_TESTS++))
        
        if [ "$HTTP_CODE" = "200" ]; then
            echo "⚠ WARNING: DELETE method was allowed - security risk!"
        fi
    fi
    
    echo ""
}

# Test 5: Access Logs
test_access_logs() {
    print_header "[TEST 5] Access Logs"
    
    echo "ACTION: Retrieve Nginx access logs"
    echo "EXPECTED: Logs show requests with client IPs and upstream backend info"
    echo ""
    
    # Get recent logs
    LOGS=$(docker logs nginx-reverse-proxy 2>&1 | grep "GET" | tail -10)
    
    if [ -n "$LOGS" ]; then
        print_success "Access logs available"
        ((PASSED_TESTS++))
        
        echo -e "${BLUE}RECENT ACCESS LOG ENTRIES:${NC}"
        echo "$LOGS" | head -5
        echo ""
        
        # Check if upstream info is logged
        if echo "$LOGS" | grep -q "upstream="; then
            print_info "✓ Upstream backend information logged"
        else
            print_info "✗ Upstream backend info not in logs"
        fi
        
        # Count requests to each backend
        BACKEND1_LOGS=$(echo "$LOGS" | grep -c "10.21.0.100" || echo "0")
        BACKEND2_LOGS=$(echo "$LOGS" | grep -c "10.21.0.101" || echo "0")
        
        print_info "Requests logged for backend-1: $BACKEND1_LOGS"
        print_info "Requests logged for backend-2: $BACKEND2_LOGS"
        
    else
        print_fail "No access logs found"
        ((FAILED_TESTS++))
        echo "⚠ WARNING: Nginx may not be logging properly"
    fi
    
    echo ""
}

# Main execution
main() {
    clear
    echo ""
    print_header "REVERSE PROXY TEST SUITE"
    echo ""
    print_info "Testing Nginx reverse proxy with load balancing and security features"
    print_info "Total tests: $TOTAL_TESTS"
    print_info "Log file: $LOG_FILE"
    echo ""
    
    # Pre-flight checks
    print_info "Checking prerequisites..."
    check_docker
    check_containers
    print_success "All prerequisites met"
    echo ""
    
    # Run tests
    test_load_balancing
    test_backend_isolation
    test_header_manipulation
    test_forbidden_method
    test_access_logs
    
    # Summary
    print_header "TEST SUMMARY"
    echo ""
    echo "Total Tests:  $TOTAL_TESTS"
    echo -e "Passed:       ${GREEN}$PASSED_TESTS${NC}"
    echo -e "Failed:       ${RED}$FAILED_TESTS${NC}"
    echo ""
    
    if [ $FAILED_TESTS -eq 0 ]; then
        print_success "All tests passed! Reverse proxy is working correctly."
        echo ""
        echo "✓ Load balancing functional"
        echo "✓ Backend isolation enforced"
        echo "✓ Header security implemented"
        echo "✓ HTTP method filtering active"
        echo "✓ Access logging operational"
        EXIT_STATUS=0
    else
        print_fail "Some tests failed. Please review the output above."
        echo ""
        echo "Common issues:"
        echo "  - Check nginx.conf upstream configuration"
        echo "  - Verify backend network isolation in docker-compose.yml"
        echo "  - Check container logs: docker logs nginx-reverse-proxy"
        EXIT_STATUS=1
    fi
    
    echo ""
    print_info "Full test log saved to: $LOG_FILE"
    echo ""
    
    exit $EXIT_STATUS
}

# Run main function with output logging
main 2>&1 | tee "$LOG_FILE"

