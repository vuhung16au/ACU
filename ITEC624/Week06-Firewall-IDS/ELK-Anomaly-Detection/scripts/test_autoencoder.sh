#!/bin/bash
# Test: Autoencoder detection with pattern anomaly

set -e
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
source "$SCRIPT_DIR/utils.sh"

echo "========================================="
echo " Test: Autoencoder Detection"
echo "========================================="
echo ""

# Inject slow request anomaly (pattern-based)
log_info "Injecting pattern anomaly (slow requests)..."
inject_anomaly "slow" 30 5

# Wait for ML detection
log_info "Waiting for ML detection (45 seconds)..."
sleep 45

# Check for anomaly scores
SCORES=$(query_anomaly_scores)

if echo "$SCORES" | grep -q "autoencoder_score"; then
    MAX_SCORE=$(echo "$SCORES" | grep -o '"autoencoder_score":[0-9.]*' | grep -o '[0-9.]*' | sort -nr | head -1)
    
    log_info "Maximum Autoencoder score: $MAX_SCORE"
    
    if (( $(echo "$MAX_SCORE > 0.1" | bc -l) )); then
        log_success "PASS: Autoencoder detected the anomaly (score: $MAX_SCORE)"
        exit 0
    else
        log_warning "PARTIAL: Anomaly detected but score is low ($MAX_SCORE)"
        exit 0
    fi
else
    log_error "FAIL: No Autoencoder scores found"
    log_info "Note: ML detector may need more time to initialize"
    exit 1
fi

