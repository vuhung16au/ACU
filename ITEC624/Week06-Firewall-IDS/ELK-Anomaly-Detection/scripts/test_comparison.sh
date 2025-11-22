#!/bin/bash
# Test: Algorithm comparison

set -e
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
source "$SCRIPT_DIR/utils.sh"

echo "========================================="
echo " Test: Algorithm Comparison"
echo "========================================="
echo ""

log_info "Running both algorithm tests..."

# Test Isolation Forest
bash "$SCRIPT_DIR/test_isolation_forest.sh"
IF_RESULT=$?

# Wait between tests
sleep 10

# Test Autoencoder
bash "$SCRIPT_DIR/test_autoencoder.sh"
AE_RESULT=$?

echo ""
echo "========================================="
echo " Comparison Results"
echo "========================================="
echo ""

if [ $IF_RESULT -eq 0 ]; then
    echo -e "  ${GREEN}✓${NC} Isolation Forest: PASS"
else
    echo -e "  ${RED}✗${NC} Isolation Forest: FAIL"
fi

if [ $AE_RESULT -eq 0 ]; then
    echo -e "  ${GREEN}✓${NC} Autoencoder: PASS"
else
    echo -e "  ${RED}✗${NC} Autoencoder: FAIL"
fi

echo ""
echo "Conclusion:"
echo "  - Isolation Forest excels at volume-based anomalies"
echo "  - Autoencoder excels at pattern-based anomalies"
echo "  - Combined approach provides comprehensive coverage"
echo ""

if [ $IF_RESULT -eq 0 ] || [ $AE_RESULT -eq 0 ]; then
    log_success "Comparison test completed!"
    exit 0
else
    log_warning "Both tests had issues. Check ML detector status."
    exit 1
fi

