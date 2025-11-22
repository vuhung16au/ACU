#!/bin/bash
#
# Run All Tests - Master Test Script
# Executes both forward and reverse proxy test suites
#

# Color codes
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
CYAN='\033[0;36m'
NC='\033[0m' # No Color

# Get script directory
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

print_header() {
    echo ""
    echo -e "${CYAN}╔════════════════════════════════════════════════════════╗${NC}"
    echo -e "${CYAN}║${NC}  $1"
    echo -e "${CYAN}╚════════════════════════════════════════════════════════╝${NC}"
    echo ""
}

print_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}✓${NC} $1"
}

print_fail() {
    echo -e "${RED}✗${NC} $1"
}

# Main execution
main() {
    clear
    print_header "APPLICATION PROXY FIREWALL LAB - FULL TEST SUITE"
    
    print_info "This will run all tests for both scenarios:"
    echo "  1. Forward Proxy (Squid) - 4 tests"
    echo "  2. Reverse Proxy (Nginx) - 5 tests"
    echo ""
    print_info "Total: 9 tests"
    echo ""
    
    # Track overall results
    TOTAL_SUITES=2
    PASSED_SUITES=0
    FAILED_SUITES=0
    
    # ========================================
    # FORWARD PROXY TESTS
    # ========================================
    print_header "SCENARIO 1: FORWARD PROXY (SQUID)"
    
    if [ -f "$SCRIPT_DIR/test-forward-proxy.sh" ]; then
        bash "$SCRIPT_DIR/test-forward-proxy.sh"
        FORWARD_RESULT=$?
        
        if [ $FORWARD_RESULT -eq 0 ]; then
            ((PASSED_SUITES++))
        else
            ((FAILED_SUITES++))
        fi
    else
        print_fail "Forward proxy test script not found"
        ((FAILED_SUITES++))
    fi
    
    echo ""
    echo ""
    echo "Press Enter to continue to Reverse Proxy tests..."
    read -r
    
    # ========================================
    # REVERSE PROXY TESTS
    # ========================================
    print_header "SCENARIO 2: REVERSE PROXY (NGINX)"
    
    if [ -f "$SCRIPT_DIR/test-reverse-proxy.sh" ]; then
        bash "$SCRIPT_DIR/test-reverse-proxy.sh"
        REVERSE_RESULT=$?
        
        if [ $REVERSE_RESULT -eq 0 ]; then
            ((PASSED_SUITES++))
        else
            ((FAILED_SUITES++))
        fi
    else
        print_fail "Reverse proxy test script not found"
        ((FAILED_SUITES++))
    fi
    
    # ========================================
    # OVERALL SUMMARY
    # ========================================
    echo ""
    echo ""
    print_header "OVERALL TEST SUMMARY"
    
    echo "Test Suites Run:    $TOTAL_SUITES"
    echo -e "Suites Passed:      ${GREEN}$PASSED_SUITES${NC}"
    echo -e "Suites Failed:      ${RED}$FAILED_SUITES${NC}"
    echo ""
    
    if [ $FAILED_SUITES -eq 0 ]; then
        echo -e "${GREEN}═══════════════════════════════════════════════════════${NC}"
        echo -e "${GREEN}  ✓✓✓  ALL TESTS PASSED SUCCESSFULLY!  ✓✓✓${NC}"
        echo -e "${GREEN}═══════════════════════════════════════════════════════${NC}"
        echo ""
        echo "Congratulations! Your Application Proxy Firewall lab is working perfectly."
        echo ""
        echo "Key concepts demonstrated:"
        echo "  ✓ Forward proxy controls outbound (egress) traffic"
        echo "  ✓ Reverse proxy protects inbound (ingress) traffic"
        echo "  ✓ Application-layer filtering and inspection"
        echo "  ✓ Network isolation and topology hiding"
        echo "  ✓ Load balancing and high availability"
        echo "  ✓ Audit logging for security monitoring"
        echo ""
        EXIT_CODE=0
    else
        echo -e "${RED}═══════════════════════════════════════════════════════${NC}"
        echo -e "${RED}  ✗✗✗  SOME TESTS FAILED  ✗✗✗${NC}"
        echo -e "${RED}═══════════════════════════════════════════════════════${NC}"
        echo ""
        echo "Please review the test output above for details."
        echo ""
        echo "Troubleshooting steps:"
        echo "  1. Check container status: docker ps"
        echo "  2. View container logs: docker logs <container-name>"
        echo "  3. Rebuild lab: make clean && make build"
        echo "  4. Review configuration files in forward-proxy-lab/ and reverse-proxy-lab/"
        echo ""
        EXIT_CODE=1
    fi
    
    # Show log location
    echo "Test logs are preserved in the 'logs/' directory:"
    ls -lh logs/*.log 2>/dev/null | tail -5
    echo ""
    
    exit $EXIT_CODE
}

# Execute main
main

