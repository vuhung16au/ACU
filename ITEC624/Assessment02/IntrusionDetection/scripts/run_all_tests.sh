#!/bin/bash

# Master Test Runner
# Runs all test scenarios and provides summary

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# Color codes
GREEN='\033[0;32m'
RED='\033[0;31m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
BOLD='\033[1m'
NC='\033[0m' # No Color

echo ""
echo "======================================================================"
echo -e "${BOLD}Host-Based IDS - Test Suite${NC}"
echo "======================================================================"
echo ""

# Run cleanup first
echo "Cleaning up previous test artifacts..."
bash "$SCRIPT_DIR/cleanup.sh" > /dev/null 2>&1
echo ""

# Track results
tests_passed=0
tests_failed=0
failed_tests=()

# Test 1: No Changes Detection
echo "Running Test 1: No Changes Detection..."
echo "----------------------------------------------------------------------"
if bash "$SCRIPT_DIR/test_no_changes.sh"; then
    ((tests_passed++))
else
    ((tests_failed++))
    failed_tests+=("No Changes Detection")
fi
echo ""

# Clean up between tests
bash "$SCRIPT_DIR/cleanup.sh" > /dev/null 2>&1

# Test 2: Intrusion Detection
echo "Running Test 2: Intrusion Detection..."
echo "----------------------------------------------------------------------"
if bash "$SCRIPT_DIR/test_intrusion.sh"; then
    ((tests_passed++))
else
    ((tests_failed++))
    failed_tests+=("Intrusion Detection")
fi
echo ""

# Final Summary
echo "======================================================================"
echo -e "${BOLD}Test Suite Summary${NC}"
echo "======================================================================"
echo ""
echo "Total Tests: $((tests_passed + tests_failed))"
echo -e "${GREEN}Passed: $tests_passed${NC}"
echo -e "${RED}Failed: $tests_failed${NC}"
echo ""

if [ $tests_failed -eq 0 ]; then
    echo "======================================================================"
    echo -e "${GREEN}${BOLD}✓ ALL TESTS PASSED${NC}"
    echo "======================================================================"
    exit 0
else
    echo "Failed tests:"
    for test in "${failed_tests[@]}"; do
        echo -e "  ${RED}✗ $test${NC}"
    done
    echo ""
    echo "======================================================================"
    echo -e "${RED}${BOLD}✗ SOME TESTS FAILED${NC}"
    echo "======================================================================"
    exit 1
fi

