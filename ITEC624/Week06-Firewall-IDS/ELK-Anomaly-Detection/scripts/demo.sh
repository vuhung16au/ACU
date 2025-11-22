#!/bin/bash
# Automated demonstration script

set -e
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
source "$SCRIPT_DIR/utils.sh"

echo "╔════════════════════════════════════════════════════════════╗"
echo "║     ELK Stack Anomaly Detection - Automated Demo          ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""

# Step 1: Check health
log_info "Step 1/5: Checking system health..."
bash "$SCRIPT_DIR/health_check.sh" || {
    log_error "System not healthy. Please run 'make up' first."
    exit 1
}

echo ""
sleep 2

# Step 2: Observe baseline
log_info "Step 2/5: Observing baseline traffic (30 seconds)..."
bash "$SCRIPT_DIR/test_baseline.sh"

echo ""
sleep 2

# Step 3: Inject anomaly
log_info "Step 3/5: Injecting burst anomaly..."
inject_anomaly "burst" 30 8

echo ""
sleep 2

# Step 4: Wait for detection
log_info "Step 4/5: Waiting for ML detection (45 seconds)..."
sleep 45

# Step 5: Show results
log_info "Step 5/5: Displaying results..."
echo ""

SCORES=$(query_anomaly_scores)

if echo "$SCORES" | grep -q "isolation_forest_score"; then
    IF_SCORE=$(echo "$SCORES" | grep -o '"isolation_forest_score":[0-9.]*' | head -1 | grep -o '[0-9.]*')
    AE_SCORE=$(echo "$SCORES" | grep -o '"autoencoder_score":[0-9.]*' | head -1 | grep -o '[0-9.]*')
    
    echo "Recent Detection Scores:"
    echo "  Isolation Forest: $IF_SCORE"
    echo "  Autoencoder: $AE_SCORE"
    echo ""
fi

log_success "Demo completed!"
echo ""
echo "═══════════════════════════════════════════════════════════"
echo ""
echo "Next steps:"
echo "  1. Open Kibana: $KIBANA_URL"
echo "  2. Monitor live: make monitor"
echo "  3. Export results: make export"
echo "  4. Generate report: make report"
echo ""
echo "Try more tests:"
echo "  - make test-isolation-forest"
echo "  - make test-autoencoder"
echo "  - make inject-custom"

