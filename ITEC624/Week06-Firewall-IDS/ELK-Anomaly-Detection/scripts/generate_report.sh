#!/bin/bash
# Generate HTML summary report

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(dirname "$SCRIPT_DIR")"
source "$SCRIPT_DIR/utils.sh"

# Create reports directory in project root
mkdir -p "$PROJECT_DIR/reports"

# Generate filename with timestamp
TIMESTAMP=$(date +%Y%m%d_%H%M%S)
OUTPUT_FILE="$PROJECT_DIR/reports/lab_report_${TIMESTAMP}.html"

log_info "Generating HTML report..."

# Get statistics
TOTAL_LOGS=$(get_doc_count "logs-*")
LOG_RATE=$(calculate_log_rate "logs-*")

# Get anomaly scores
SCORES=$(query_anomaly_scores)
ANOMALY_COUNT=$(echo "$SCORES" | grep -o '"combined_label":1' | wc -l | tr -d ' ')

# Debug: Check if anomaly-scores index exists and has data
ANOMALY_INDEX_EXISTS=$(curl -s "$ES_URL/_cat/indices" | grep -c "anomaly-scores" || echo "0")
ANOMALY_TOTAL_COUNT=$(echo "$SCORES" | grep -o '"combined_label":[01]' | wc -l | tr -d ' ')

# Enhanced anomaly detection diagnostics
if [ "$ANOMALY_COUNT" = "0" ]; then
    # Check if we have any anomaly scores at all
    if [ "$ANOMALY_INDEX_EXISTS" = "0" ]; then
        ANOMALY_STATUS="No anomaly-scores index found. ML detector may not be running."
    elif [ "$ANOMALY_TOTAL_COUNT" = "0" ]; then
        ANOMALY_STATUS="ML detector running but no scores generated yet."
    else
        ANOMALY_STATUS="ML detector generating scores but no anomalies detected."
    fi
else
    ANOMALY_STATUS="Anomalies successfully detected!"
fi

# Generate HTML
cat > "$OUTPUT_FILE" << 'EOF'
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ELK Anomaly Detection Lab Report</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 30px;
            border-radius: 10px;
            margin-bottom: 20px;
        }
        .header h1 {
            margin: 0;
        }
        .stats {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 20px;
        }
        .stat-card {
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .stat-value {
            font-size: 2em;
            font-weight: bold;
            color: #667eea;
        }
        .stat-label {
            color: #666;
            margin-top: 10px;
        }
        .section {
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }
        .section h2 {
            margin-top: 0;
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #667eea;
            color: white;
        }
        .anomaly {
            color: #e74c3c;
            font-weight: bold;
        }
        .normal {
            color: #27ae60;
        }
        .footer {
            text-align: center;
            color: #666;
            margin-top: 40px;
            padding: 20px;
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>ELK Stack Anomaly Detection Lab</h1>
        <p>Report generated: TIMESTAMP_PLACEHOLDER</p>
    </div>

    <div class="stats">
        <div class="stat-card">
            <div class="stat-value">TOTAL_LOGS_PLACEHOLDER</div>
            <div class="stat-label">Total Logs Processed</div>
        </div>
        <div class="stat-card">
            <div class="stat-value">LOG_RATE_PLACEHOLDER</div>
            <div class="stat-label">Logs per Minute (current)</div>
        </div>
        <div class="stat-card">
            <div class="stat-value">ANOMALY_COUNT_PLACEHOLDER</div>
            <div class="stat-label">Anomalies Detected</div>
        </div>
    </div>

    <div class="section">
        <h2>Summary</h2>
        <p>This lab demonstrates machine learning-based anomaly detection using the ELK Stack with two complementary algorithms:</p>
        <ul>
            <li><strong>Isolation Forest:</strong> Detects volume-based anomalies (bursts, rate spikes)</li>
            <li><strong>Autoencoder:</strong> Detects pattern-based anomalies (unusual sequences, latency spikes)</li>
        </ul>
    </div>

    <div class="section">
        <h2>Algorithm Performance</h2>
        <p><strong>Status:</strong> ANOMALY_STATUS_PLACEHOLDER</p>
        <p>Both algorithms work together to detect different types of anomalies. The combined approach provides comprehensive coverage of volume-based and pattern-based anomalies.</p>
    </div>

    <div class="section">
        <h2>Troubleshooting</h2>
        <p>If no anomalies are detected, try the following:</p>
        <ul>
            <li><strong>Check services:</strong> <code>make health</code></li>
            <li><strong>Run demo:</strong> <code>make demo</code></li>
            <li><strong>Inject anomalies:</strong> <code>make inject-burst</code></li>
            <li><strong>Monitor logs:</strong> <code>docker-compose logs ml-detector</code></li>
            <li><strong>Check Elasticsearch:</strong> <code>curl http://localhost:9200/_cat/indices</code></li>
        </ul>
    </div>

    <div class="footer">
        <p>ELK Stack Anomaly Detection Lab</p>
        <p>Generated by: make report</p>
    </div>
</body>
</html>
EOF

# Replace placeholders
sed -i.bak "s/TIMESTAMP_PLACEHOLDER/$(date '+%Y-%m-%d %H:%M:%S')/g" "$OUTPUT_FILE"
sed -i.bak "s/TOTAL_LOGS_PLACEHOLDER/$TOTAL_LOGS/g" "$OUTPUT_FILE"
sed -i.bak "s/LOG_RATE_PLACEHOLDER/$LOG_RATE/g" "$OUTPUT_FILE"
sed -i.bak "s/ANOMALY_COUNT_PLACEHOLDER/$ANOMALY_COUNT/g" "$OUTPUT_FILE"
sed -i.bak "s/ANOMALY_STATUS_PLACEHOLDER/$ANOMALY_STATUS/g" "$OUTPUT_FILE"
rm "${OUTPUT_FILE}.bak"

log_success "Report generated: $OUTPUT_FILE"
echo ""
log_info "Open in browser: file://$(cd $(dirname "$OUTPUT_FILE") && pwd)/$(basename "$OUTPUT_FILE")"

