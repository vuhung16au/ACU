#!/bin/bash
# Shared utility functions for all scripts

# Colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Configuration
ES_URL="http://localhost:9200"
KIBANA_URL="http://localhost:5601"
MAX_RETRIES=30
RETRY_DELAY=2

# Logging functions
log_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

log_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

log_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Wait for Elasticsearch to be ready
wait_for_elasticsearch() {
    local retries=0
    log_info "Waiting for Elasticsearch..."
    
    while [ $retries -lt $MAX_RETRIES ]; do
        if curl -s "$ES_URL/_cluster/health" > /dev/null 2>&1; then
            log_success "Elasticsearch is ready!"
            return 0
        fi
        
        retries=$((retries + 1))
        log_info "Attempt $retries/$MAX_RETRIES - Elasticsearch not ready yet..."
        sleep $RETRY_DELAY
    done
    
    log_error "Elasticsearch did not become ready in time"
    return 1
}

# Wait for Kibana to be ready
wait_for_kibana() {
    local retries=0
    log_info "Waiting for Kibana..."
    
    while [ $retries -lt $MAX_RETRIES ]; do
        if curl -s "$KIBANA_URL/api/status" > /dev/null 2>&1; then
            log_success "Kibana is ready!"
            return 0
        fi
        
        retries=$((retries + 1))
        log_info "Attempt $retries/$MAX_RETRIES - Kibana not ready yet..."
        sleep $RETRY_DELAY
    done
    
    log_error "Kibana did not become ready in time"
    return 1
}

# Query Elasticsearch
query_elasticsearch() {
    local index="$1"
    local query="$2"
    
    curl -s -X GET "$ES_URL/$index/_search" \
        -H 'Content-Type: application/json' \
        -d "$query"
}

# Get document count from Elasticsearch
get_doc_count() {
    local index="$1"
    
    local result=$(curl -s "$ES_URL/$index/_count")
    echo "$result" | grep -o '"count":[0-9]*' | grep -o '[0-9]*'
}

# Query anomaly scores from Elasticsearch
query_anomaly_scores() {
    local query='{
        "size": 100,
        "sort": [{"@timestamp": "desc"}],
        "query": {
            "range": {
                "@timestamp": {
                    "gte": "now-5m"
                }
            }
        }
    }'
    
    query_elasticsearch "anomaly-scores" "$query"
}

# Inject anomaly (trigger anomaly-injector container)
inject_anomaly() {
    local type="$1"
    local duration="${2:-30}"
    local intensity="${3:-8}"
    
    log_info "Injecting $type anomaly (duration: ${duration}s, intensity: $intensity)..."
    
    docker-compose run --rm anomaly-injector \
        python injector.py --type "$type" --duration "$duration" --intensity "$intensity"
    
    return $?
}

# Calculate log rate (logs per minute)
calculate_log_rate() {
    local index="$1"
    
    local query='{
        "size": 0,
        "query": {
            "range": {
                "@timestamp": {
                    "gte": "now-1m"
                }
            }
        }
    }'
    
    local result=$(query_elasticsearch "$index" "$query")
    local count=$(echo "$result" | grep -o '"value":[0-9]*' | head -1 | grep -o '[0-9]*')
    
    echo "${count:-0}"
}

# Check if service is running
is_service_running() {
    local service_name="$1"
    
    docker-compose ps | grep "$service_name" | grep -q "Up"
    return $?
}

# Get service status
get_service_status() {
    local service_name="$1"
    
    if is_service_running "$service_name"; then
        echo -e "${GREEN}✓${NC}"
    else
        echo -e "${RED}✗${NC}"
    fi
}

# Pretty print JSON
pretty_json() {
    python3 -m json.tool 2>/dev/null || cat
}

# Format timestamp
format_timestamp() {
    date '+%Y-%m-%d %H:%M:%S'
}

# Check if command exists
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

