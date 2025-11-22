#!/bin/bash
# Reset data without stopping services

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
source "$SCRIPT_DIR/utils.sh"

log_warning "This will clear all data but keep services running!"
read -p "Continue? [y/N] " -n 1 -r
echo

if [[ ! $REPLY =~ ^[Yy]$ ]]; then
    log_info "Reset cancelled."
    exit 0
fi

log_info "Clearing Elasticsearch indices..."
curl -s -X DELETE "$ES_URL/logs-*" > /dev/null 2>&1 || true
curl -s -X DELETE "$ES_URL/anomaly-scores*" > /dev/null 2>&1 || true
log_success "Elasticsearch indices cleared"

log_info "Clearing log files..."
rm -f ../logs/normal/*.log ../logs/anomaly/*.log ../logs/ml-detector/*.log 2>/dev/null || true
log_success "Log files cleared"

log_info "Clearing exports and reports..."
rm -rf ../exports/* ../reports/* 2>/dev/null || true
log_success "Exports and reports cleared"

log_success "Reset completed! Services are still running."
log_info "Normal traffic generation will resume automatically."

