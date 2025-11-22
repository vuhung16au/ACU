#!/bin/bash
# Generate fake anomaly scores for demonstration

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
source "$SCRIPT_DIR/utils.sh"

log_info "Generating demonstration anomaly scores..."

# Create some fake anomaly score documents
TIMESTAMP=$(date -u +"%Y-%m-%dT%H:%M:%S.000Z")
TIMESTAMP_MINUS_5=$(date -u -d "5 minutes ago" +"%Y-%m-%dT%H:%M:%S.000Z")
TIMESTAMP_MINUS_3=$(date -u -d "3 minutes ago" +"%Y-%m-%dT%H:%M:%S.000Z")

# Normal score
curl -s -X POST "$ES_URL/anomaly-scores/_doc" \
  -H 'Content-Type: application/json' \
  -d "{
    \"@timestamp\": \"$TIMESTAMP_MINUS_5\",
    \"isolation_forest_score\": 0.2,
    \"isolation_forest_label\": 0,
    \"autoencoder_score\": 0.05,
    \"autoencoder_label\": 0,
    \"combined_score\": 0.125,
    \"combined_label\": 0,
    \"service\": \"demo-generator\"
  }" > /dev/null

# Anomaly score 1
curl -s -X POST "$ES_URL/anomaly-scores/_doc" \
  -H 'Content-Type: application/json' \
  -d "{
    \"@timestamp\": \"$TIMESTAMP_MINUS_3\",
    \"isolation_forest_score\": 0.8,
    \"isolation_forest_label\": 1,
    \"autoencoder_score\": 0.12,
    \"autoencoder_label\": 1,
    \"combined_score\": 0.46,
    \"combined_label\": 1,
    \"service\": \"demo-generator\"
  }" > /dev/null

# Anomaly score 2
curl -s -X POST "$ES_URL/anomaly-scores/_doc" \
  -H 'Content-Type: application/json' \
  -d "{
    \"@timestamp\": \"$TIMESTAMP\",
    \"isolation_forest_score\": 0.75,
    \"isolation_forest_label\": 1,
    \"autoencoder_score\": 0.09,
    \"autoencoder_label\": 1,
    \"combined_score\": 0.42,
    \"combined_label\": 1,
    \"service\": \"demo-generator\"
  }" > /dev/null

log_success "Generated 3 anomaly scores (2 anomalies detected)"
log_info "You can now run 'make report' to see the updated report with anomalies."