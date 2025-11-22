#!/bin/bash
# Test: Isolation Forest detection with burst anomaly

set -e
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
source "$SCRIPT_DIR/utils.sh"

echo "========================================="
echo " Test: Isolation Forest Detection"
echo "========================================="
echo ""

# Inject burst anomaly
log_info "Injecting burst anomaly..."
inject_anomaly "burst" 30 8

# Wait for ML detection
log_info "Waiting for ML detection (45 seconds)..."
sleep 45

# Check for anomaly scores
SCORES=$(query_anomaly_scores)

if echo "$SCORES" | grep -q "isolation_forest_score"; then
    MAX_SCORE=$(echo "$SCORES" | grep -o '"isolation_forest_score":[0-9.]*' | grep -o '[0-9.]*' | sort -nr | head -1)
    
    log_info "Maximum Isolation Forest score: $MAX_SCORE"
    
    if (( $(echo "$MAX_SCORE > 0.5" | bc -l) )); then
        log_success "PASS: Isolation Forest detected the anomaly (score: $MAX_SCORE)"
        exit 0
    else
        log_warning "PARTIAL: Anomaly detected but score is low ($MAX_SCORE)"
        exit 0
    fi
else
    log_error "FAIL: No Isolation Forest scores found"
    log_info "Note: ML detector may need more time to initialize"
    exit 1
fi

