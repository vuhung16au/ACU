#!/bin/bash
# Health check script for all services

set -e

# Load utility functions
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
source "$SCRIPT_DIR/utils.sh"

echo "======================================"
echo " ELK Anomaly Detection - Health Check"
echo "======================================"
echo ""

all_healthy=true

# Check Docker Compose services
log_info "Checking Docker services..."
echo ""

services=("elasticsearch" "kibana" "logstash" "log-generator" "ml-detector")
services_down=0

for service in "${services[@]}"; do
    status=$(get_service_status "elk-$service")
    if [[ "$status" == *"✓"* ]]; then
        echo "  $status $service"
    else
        echo "  $status $service"
        all_healthy=false
        services_down=$((services_down + 1))
    fi
done

# If most services are down, suggest starting them
if [ $services_down -gt 2 ]; then
    echo ""
    log_warning "Most services are not running. Start them with: make up"
fi

echo ""

# Check Elasticsearch health
log_info "Checking Elasticsearch cluster health..."

if curl -s "$ES_URL/_cluster/health" > /dev/null 2>&1; then
    cluster_health=$(curl -s "$ES_URL/_cluster/health" | grep -o '"status":"[^"]*"' | cut -d'"' -f4)
    
    case "$cluster_health" in
        "green")
            log_success "Elasticsearch cluster: $cluster_health"
            ;;
        "yellow")
            log_warning "Elasticsearch cluster: $cluster_health (single-node cluster - this is expected)"
            ;;
        "red")
            log_error "Elasticsearch cluster: $cluster_health"
            all_healthy=false
            ;;
        *)
            log_warning "Elasticsearch cluster: unknown status"
            ;;
    esac
else
    log_error "Cannot connect to Elasticsearch (is it running?)"
    all_healthy=false
fi

# Check Kibana
log_info "Checking Kibana..."

if curl -s "$KIBANA_URL/api/status" > /dev/null 2>&1; then
    log_success "Kibana is accessible at $KIBANA_URL"
else
    log_error "Cannot connect to Kibana (is it running and fully initialized?)"
    all_healthy=false
fi

# Check log ingestion (only if Elasticsearch is accessible)
log_info "Checking log ingestion..."

if curl -s "$ES_URL/_cluster/health" > /dev/null 2>&1; then
    log_count=$(get_doc_count "logs-*")

    if [ "$log_count" -gt 0 ]; then
        log_success "Logs in Elasticsearch: $log_count documents"
    else
        log_warning "No logs found in Elasticsearch yet"
    fi

    # Check log rate
    log_rate=$(calculate_log_rate "logs-*")

    if [ "$log_rate" -gt 0 ]; then
        log_success "Current log rate: ~$log_rate logs/minute"
    elif [ "$log_count" -gt 0 ]; then
        log_warning "Log rate is 0 (no recent logs in last minute)"
    else
        log_warning "Log generator may not be running yet"
    fi
else
    log_warning "Skipping log checks - Elasticsearch not accessible"
fi

# Check ML detector
log_info "Checking ML detector..."

if docker-compose logs ml-detector 2>/dev/null | grep -q "ML Service.*Initialized"; then
    log_success "ML detector initialized"
else
    log_warning "ML detector may still be initializing"
fi

# Summary
echo ""
echo "======================================"

if [ "$all_healthy" = true ] && [ "$log_count" -gt 0 ]; then
    log_success "All systems are healthy!"
    echo ""
    echo "Next steps:"
    echo "  - Open Kibana: $KIBANA_URL"
    echo "  - Run demo: make demo"
    echo "  - Monitor: make monitor"
    echo "  - Test detection: make test-isolation-forest"
    exit 0
else
    log_warning "Some issues detected. Please review the above messages."
    echo ""
    echo "Troubleshooting:"
    echo "  - Check logs: make logs"
    echo "  - Restart services: make restart"
    echo "  - Check Docker resources: docker stats"
    exit 1
fi

