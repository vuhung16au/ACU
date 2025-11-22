#!/bin/bash
#
# Forward Proxy Test Suite
# Tests Squid forward proxy functionality
#

# Color codes
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Test counters
TOTAL_TESTS=4
PASSED_TESTS=0
FAILED_TESTS=0

# Create logs directory
LOGS_DIR="logs"
mkdir -p "$LOGS_DIR"
TIMESTAMP=$(date +"%Y-%m-%d-%H%M%S")
LOG_FILE="$LOGS_DIR/forward-proxy-test-$TIMESTAMP.log"

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
    local required_containers=("forward-client" "squid-forward-proxy" "allowed-site-server" "blocked-site-server")
    local missing_containers=()
    
    for container in "${required_containers[@]}"; do
        if ! docker ps --format '{{.Names}}' | grep -q "^${container}$"; then
            missing_containers+=("$container")
        fi
    done
    
    if [ ${#missing_containers[@]} -gt 0 ]; then
        print_fail "Required containers are not running: ${missing_containers[*]}"
        echo ""
        echo "Please run 'make build' or 'cd forward-proxy-lab && docker compose up -d' first"
        exit 1
    fi
}

# Test 1: Allowed Traffic
test_allowed_traffic() {
    print_header "[TEST 1] Allowed Traffic"
    
    echo "ACTION: Request http://allowed-site.com via Squid proxy"
    echo "EXPECTED: HTTP 200 OK, content from allowed-site-server"
    echo ""
    
    # Execute curl request
    RESPONSE=$(docker exec forward-client curl -s -w "\nHTTP_CODE:%{http_code}" \
        --proxy squid-forward-proxy:3128 \
        http://allowed-site.com 2>&1)
    
    HTTP_CODE=$(echo "$RESPONSE" | grep "HTTP_CODE:" | cut -d: -f2)
    CONTENT=$(echo "$RESPONSE" | grep -v "HTTP_CODE:")
    
    # Check if successful
    if [ "$HTTP_CODE" = "200" ]; then
        print_success "Allowed site is accessible (HTTP $HTTP_CODE)"
        ((PASSED_TESTS++))
        
        echo "RESPONSE EXCERPT:"
        echo "$CONTENT" | grep -o "Allowed Site" | head -1
        echo ""
        
        # Get proxy logs
        echo -e "${BLUE}LOGS (Squid):${NC}"
        docker logs squid-forward-proxy 2>&1 | grep "allowed-site.com" | tail -2
        
    else
        print_fail "Allowed site returned unexpected code: HTTP $HTTP_CODE"
        ((FAILED_TESTS++))
        echo "RESPONSE: $CONTENT"
    fi
    
    echo ""
}

# Test 2: Blocked Traffic
test_blocked_traffic() {
    print_header "[TEST 2] Blocked Traffic"
    
    echo "ACTION: Request http://malicious-site.com via Squid proxy"
    echo "EXPECTED: HTTP 403 Forbidden, Squid error page"
    echo ""
    
    # Execute curl request
    RESPONSE=$(docker exec forward-client curl -s -w "\nHTTP_CODE:%{http_code}" \
        --proxy squid-forward-proxy:3128 \
        http://malicious-site.com 2>&1)
    
    HTTP_CODE=$(echo "$RESPONSE" | grep "HTTP_CODE:" | cut -d: -f2)
    CONTENT=$(echo "$RESPONSE" | grep -v "HTTP_CODE:")
    
    # Check if blocked (403)
    if [ "$HTTP_CODE" = "403" ]; then
        print_success "Malicious site is blocked (HTTP $HTTP_CODE)"
        ((PASSED_TESTS++))
        
        # Verify it's Squid's error page, not the actual site
        if echo "$CONTENT" | grep -qi "access denied\|forbidden\|squid"; then
            print_info "Confirmed: Squid error page returned (not actual malicious content)"
        fi
        
        echo ""
        echo -e "${BLUE}LOGS (Squid):${NC}"
        docker logs squid-forward-proxy 2>&1 | grep "malicious-site.com" | tail -2
        
    else
        print_fail "Malicious site returned unexpected code: HTTP $HTTP_CODE (expected 403)"
        ((FAILED_TESTS++))
        
        if [ "$HTTP_CODE" = "200" ]; then
            echo "⚠ WARNING: Blocked site is accessible! ACL rules may not be working."
        fi
    fi
    
    echo ""
}

# Test 3: Proxy Bypass Attempt
test_proxy_bypass() {
    print_header "[TEST 3] Proxy Bypass Attempt"
    
    echo "ACTION: Try direct connection to malicious-site.com (without proxy)"
    echo "EXPECTED: Connection failure (no route, timeout)"
    echo ""
    
    # Try direct connection (should fail due to network isolation)
    RESPONSE=$(docker exec forward-client curl -s -m 5 \
        http://malicious-site.com 2>&1)
    
    EXIT_CODE=$?
    
    # Connection should fail (exit code != 0)
    if [ $EXIT_CODE -ne 0 ]; then
        print_success "Direct connection blocked (network isolation enforced)"
        ((PASSED_TESTS++))
        
        if echo "$RESPONSE" | grep -qi "couldn't resolve\|timeout\|no route"; then
            print_info "Network isolation working correctly"
        fi
        
    else
        print_fail "Direct connection succeeded (bypass detected!)"
        ((FAILED_TESTS++))
        echo "⚠ WARNING: Client can bypass proxy - network isolation failed"
        echo "RESPONSE: $RESPONSE"
    fi
    
    echo ""
}

# Test 4: Log Inspection
test_log_inspection() {
    print_header "[TEST 4] Log Inspection"
    
    echo "ACTION: Retrieve Squid access logs"
    echo "EXPECTED: Logs show TCP_MISS (allowed) and TCP_DENIED (blocked) entries"
    echo ""
    
    # Get recent logs from the access.log file
    LOGS=$(docker exec squid-forward-proxy cat /var/log/squid/access.log 2>/dev/null | grep -E "TCP_MISS|TCP_DENIED" | tail -10)
    
    if [ -n "$LOGS" ]; then
        print_success "Access logs available"
        ((PASSED_TESTS++))
        
        echo -e "${BLUE}RECENT ACCESS LOG ENTRIES:${NC}"
        echo "$LOGS"
        echo ""
        
        # Count entries
        ALLOWED_COUNT=$(echo "$LOGS" | grep -c "allowed-site.com" || echo "0")
        BLOCKED_COUNT=$(echo "$LOGS" | grep -c "malicious-site.com" || echo "0")
        
        print_info "Allowed site requests: $ALLOWED_COUNT"
        print_info "Blocked site requests: $BLOCKED_COUNT"
        
        # Verify both types of logs exist
        if [ "$ALLOWED_COUNT" -gt 0 ] && [ "$BLOCKED_COUNT" -gt 0 ]; then
            print_info "✓ Both TCP_MISS (allowed) and TCP_DENIED (blocked) logs present"
        fi
        
    else
        print_fail "No access logs found"
        ((FAILED_TESTS++))
        echo "⚠ WARNING: Squid may not be logging properly"
    fi
    
    echo ""
}

# Main execution
main() {
    clear
    echo ""
    print_header "FORWARD PROXY TEST SUITE"
    echo ""
    print_info "Testing Squid forward proxy with domain-based ACLs"
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
    test_allowed_traffic
    test_blocked_traffic
    test_proxy_bypass
    test_log_inspection
    
    # Summary
    print_header "TEST SUMMARY"
    echo ""
    echo "Total Tests:  $TOTAL_TESTS"
    echo -e "Passed:       ${GREEN}$PASSED_TESTS${NC}"
    echo -e "Failed:       ${RED}$FAILED_TESTS${NC}"
    echo ""
    
    if [ $FAILED_TESTS -eq 0 ]; then
        print_success "All tests passed! Forward proxy is working correctly."
        echo ""
        echo "✓ Content filtering (ACL rules) working"
        echo "✓ Network isolation enforced"
        echo "✓ Audit logging functional"
        EXIT_STATUS=0
    else
        print_fail "Some tests failed. Please review the output above."
        echo ""
        echo "Common issues:"
        echo "  - Check squid.conf ACL rules"
        echo "  - Verify network isolation in docker-compose.yml"
        echo "  - Check container logs: docker logs squid-forward-proxy"
        EXIT_STATUS=1
    fi
    
    echo ""
    print_info "Full test log saved to: $LOG_FILE"
    echo ""
    
    exit $EXIT_STATUS
}

# Run main function with output logging
main 2>&1 | tee "$LOG_FILE"

