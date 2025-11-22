#!/bin/bash
# Test: Kibana dashboard accessibility

set -e
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
source "$SCRIPT_DIR/utils.sh"

echo "========================================="
echo " Test: Kibana Dashboard"
echo "========================================="
echo ""

wait_for_kibana || exit 1

# Check if we can access Kibana
STATUS=$(curl -s -o /dev/null -w "%{http_code}" "$KIBANA_URL/api/status")

if [ "$STATUS" -eq 200 ]; then
    log_success "PASS: Kibana is accessible at $KIBANA_URL"
    echo ""
    echo "Open in browser: $KIBANA_URL"
    exit 0
else
    log_error "FAIL: Cannot access Kibana (HTTP $STATUS)"
    exit 1
fi

