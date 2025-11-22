#!/bin/bash
# Inject extreme anomalies for easy demonstration

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(dirname "$SCRIPT_DIR")"
source "$SCRIPT_DIR/utils.sh"

log_info "Injecting EXTREME anomalies for demonstration..."
echo ""

# Run burst anomaly with very high intensity
log_info "1. Injecting EXTREME BURST (60s, intensity 10)..."
docker-compose run --rm anomaly-injector python injector.py --type burst --duration 60 --intensity 10

echo ""
log_info "Waiting 10 seconds for ML detector to process..."
sleep 10

# Run error flood
log_info "2. Injecting ERROR FLOOD (30s, intensity 10)..."
docker-compose run --rm anomaly-injector python injector.py --type errors --duration 30 --intensity 10

echo ""
log_info "Waiting 10 seconds for ML detector to process..."
sleep 10

# Run slow requests
log_info "3. Injecting SLOW REQUESTS (30s, intensity 8)..."
docker-compose run --rm anomaly-injector python injector.py --type slow --duration 30 --intensity 8

echo ""
log_success "Extreme anomaly injection completed!"
log_info "Wait 30-60 seconds for ML detector to analyze, then run: make report"
