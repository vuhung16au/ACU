#!/bin/bash
# Test: Baseline verification

set -e
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
source "$SCRIPT_DIR/utils.sh"

echo "========================================="
echo " Test: Baseline Traffic Verification"
echo "========================================="
echo ""

# Wait for services
wait_for_elasticsearch || exit 1

# Wait for some logs to be generated
log_info "Waiting for log generation (30 seconds)..."
sleep 30

# Check log count
LOG_COUNT=$(get_doc_count "logs-*")

if [ "$LOG_COUNT" -lt 10 ]; then
    log_error "FAIL: Insufficient logs ($LOG_COUNT < 10)"
    exit 1
fi

log_success "PASS: Logs are being generated ($LOG_COUNT documents)"

# Check log rate
LOG_RATE=$(calculate_log_rate "logs-*")

if [ "$LOG_RATE" -ge 50 ] && [ "$LOG_RATE" -le 250 ]; then
    log_success "PASS: Log rate is normal ($LOG_RATE logs/min)"
elif [ "$LOG_RATE" -gt 0 ]; then
    log_warning "PASS (with warning): Log rate is $LOG_RATE logs/min (expected 100-200)"
else
    log_error "FAIL: No recent logs detected"
    exit 1
fi

log_success "Baseline test completed successfully!"
exit 0

