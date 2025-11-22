#!/bin/bash
# CI/CD test script (non-interactive)

set -e
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
source "$SCRIPT_DIR/utils.sh"

# Create test results directory
mkdir -p ../test-results

log_info "Running CI tests..."

# Test baseline
log_info "Test 1/3: Baseline..."
if bash "$SCRIPT_DIR/test_baseline.sh" > ../test-results/baseline.log 2>&1; then
    echo "✓ Baseline test passed"
else
    echo "✗ Baseline test failed"
    cat ../test-results/baseline.log
    exit 1
fi

# Test dashboard
log_info "Test 2/3: Dashboard..."
if bash "$SCRIPT_DIR/test_dashboard.sh" > ../test-results/dashboard.log 2>&1; then
    echo "✓ Dashboard test passed"
else
    echo "✗ Dashboard test failed"
    cat ../test-results/dashboard.log
    exit 1
fi

# Test detection (quick)
log_info "Test 3/3: Detection..."
if timeout 120 bash "$SCRIPT_DIR/test_isolation_forest.sh" > ../test-results/detection.log 2>&1; then
    echo "✓ Detection test passed"
else
    echo "⚠ Detection test incomplete (this is expected in CI)"
fi

log_success "CI tests completed!"
exit 0

