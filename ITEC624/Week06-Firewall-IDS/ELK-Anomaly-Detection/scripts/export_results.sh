#!/bin/bash
# Export anomaly detection results to CSV

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
source "$SCRIPT_DIR/utils.sh"

# Create exports directory
mkdir -p ../exports

# Generate filename with timestamp
TIMESTAMP=$(date +%Y%m%d_%H%M%S)
OUTPUT_FILE="../exports/results_${TIMESTAMP}.csv"

log_info "Exporting anomaly detection results..."

# Query all anomaly scores
QUERY='{
  "size": 1000,
  "sort": [{"@timestamp": "desc"}],
  "query": {"match_all": {}}
}'

RESULTS=$(query_elasticsearch "anomaly-scores" "$QUERY")

# Parse JSON and create CSV
echo "timestamp,isolation_forest_score,isolation_forest_label,autoencoder_score,autoencoder_label,combined_score,combined_label" > "$OUTPUT_FILE"

echo "$RESULTS" | grep -o '"_source":{[^}]*}' | while read -r line; do
    timestamp=$(echo "$line" | grep -o '"@timestamp":"[^"]*"' | cut -d'"' -f4)
    if_score=$(echo "$line" | grep -o '"isolation_forest_score":[0-9.]*' | cut -d: -f2)
    if_label=$(echo "$line" | grep -o '"isolation_forest_label":[0-9]*' | cut -d: -f2)
    ae_score=$(echo "$line" | grep -o '"autoencoder_score":[0-9.]*' | cut -d: -f2)
    ae_label=$(echo "$line" | grep -o '"autoencoder_label":[0-9]*' | cut -d: -f2)
    combined_score=$(echo "$line" | grep -o '"combined_score":[0-9.]*' | cut -d: -f2)
    combined_label=$(echo "$line" | grep -o '"combined_label":[0-9]*' | cut -d: -f2)
    
    if [ -n "$timestamp" ]; then
        echo "$timestamp,$if_score,$if_label,$ae_score,$ae_label,$combined_score,$combined_label" >> "$OUTPUT_FILE"
    fi
done

ROW_COUNT=$(wc -l < "$OUTPUT_FILE")
ROW_COUNT=$((ROW_COUNT - 1))  # Subtract header

log_success "Exported $ROW_COUNT results to: $OUTPUT_FILE"
echo ""
echo "Preview:"
head -n 11 "$OUTPUT_FILE"

if [ $ROW_COUNT -gt 10 ]; then
    echo "... (showing first 10 rows)"
fi

