#!/bin/bash
# Interactive custom anomaly injection

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
source "$SCRIPT_DIR/utils.sh"

echo "╔════════════════════════════════════════╗"
echo "║   Custom Anomaly Injection Tool       ║"
echo "╚════════════════════════════════════════╝"
echo ""

# Select anomaly type
echo "Select anomaly type:"
echo "  1) Burst (volume spike)"
echo "  2) Errors (404/500 flood)"
echo "  3) Slow (latency spike)"
echo "  4) Scan (pattern anomaly)"
echo ""
read -p "Choice [1-4]: " choice

case $choice in
    1) TYPE="burst" ;;
    2) TYPE="errors" ;;
    3) TYPE="slow" ;;
    4) TYPE="scan" ;;
    *) log_error "Invalid choice"; exit 1 ;;
esac

# Set duration
read -p "Duration (seconds) [30]: " duration
DURATION=${duration:-30}

# Set intensity
read -p "Intensity (1-10) [8]: " intensity
INTENSITY=${intensity:-8}

echo ""
log_info "Configuration:"
echo "  Type: $TYPE"
echo "  Duration: ${DURATION}s"
echo "  Intensity: $INTENSITY"
echo ""

read -p "Start injection? [Y/n] " -n 1 -r
echo

if [[ ! $REPLY =~ ^[Nn]$ ]]; then
    inject_anomaly "$TYPE" "$DURATION" "$INTENSITY"
    
    if [ $? -eq 0 ]; then
        log_success "Anomaly injection completed!"
        echo ""
        log_info "Check detection results with: make monitor"
    else
        log_error "Anomaly injection failed!"
        exit 1
    fi
else
    log_info "Injection cancelled."
fi

