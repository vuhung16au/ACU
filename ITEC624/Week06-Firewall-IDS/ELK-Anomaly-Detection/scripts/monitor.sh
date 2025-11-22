#!/bin/bash
# Real-time monitoring dashboard (terminal UI)

set -e

# Load utility functions
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
source "$SCRIPT_DIR/utils.sh"

# Refresh interval (seconds)
REFRESH_INTERVAL=2

# Progress bar function
progress_bar() {
    local value=$1
    local max=$2
    local width=20
    
    local filled=$(( value * width / max ))
    local empty=$(( width - filled ))
    
    printf "["
    printf "%0.s█" $(seq 1 $filled)
    printf "%0.s░" $(seq 1 $empty)
    printf "]"
}

# Monitor loop
monitor_loop() {
    while true; do
        clear
        
        echo "╔════════════════════════════════════════════════════════════╗"
        echo "║     ELK Anomaly Detection - Real-Time Monitor             ║"
        echo "╚════════════════════════════════════════════════════════════╝"
        echo ""
        echo "Time: $(format_timestamp)"
        echo ""
        
        # Get log rate
        log_rate=$(calculate_log_rate "logs-*")
        log_rate_normalized=$(( log_rate > 300 ? 300 : log_rate ))
        
        # Determine status
        if [ "$log_rate" -ge 100 ] && [ "$log_rate" -le 200 ]; then
            log_status="${GREEN}NORMAL${NC}"
        elif [ "$log_rate" -gt 200 ]; then
            log_status="${YELLOW}HIGH${NC}"
        elif [ "$log_rate" -gt 0 ]; then
            log_status="${YELLOW}LOW${NC}"
        else
            log_status="${RED}NONE${NC}"
        fi
        
        echo -e "Logs/min: ${BLUE}$log_rate${NC} [$log_status] $(progress_bar $log_rate_normalized 300)"
        echo ""
        
        # Get anomaly scores (if available)
        scores=$(query_anomaly_scores 2>/dev/null)
        
        if [ -n "$scores" ] && echo "$scores" | grep -q "hits"; then
            # Extract latest scores
            if_score=$(echo "$scores" | grep -o '"isolation_forest_score":[0-9.]*' | head -1 | grep -o '[0-9.]*')
            ae_score=$(echo "$scores" | grep -o '"autoencoder_score":[0-9.]*' | head -1 | grep -o '[0-9.]*')
            
            # Default to 0 if not found
            if_score=${if_score:-0}
            ae_score=${ae_score:-0}
            
            # Normalize for progress bar (0-1 range, scale to 0-100)
            if_normalized=$(echo "$if_score * 100" | bc -l | cut -d. -f1)
            ae_normalized=$(echo "$ae_score * 100" | bc -l | cut -d. -f1)
            
            # Status determination
            if (( $(echo "$if_score > 0.7" | bc -l) )); then
                if_status="${RED}ANOMALY${NC}"
            elif (( $(echo "$if_score > 0.5" | bc -l) )); then
                if_status="${YELLOW}SUSPICIOUS${NC}"
            else
                if_status="${GREEN}OK${NC}"
            fi
            
            if (( $(echo "$ae_score > 0.15" | bc -l) )); then
                ae_status="${RED}ANOMALY${NC}"
            elif (( $(echo "$ae_score > 0.10" | bc -l) )); then
                ae_status="${YELLOW}SUSPICIOUS${NC}"
            else
                ae_status="${GREEN}OK${NC}"
            fi
            
            echo -e "Isolation Forest: ${BLUE}$if_score${NC} [$if_status] $(progress_bar ${if_normalized:-0} 100)"
            echo -e "Autoencoder:      ${BLUE}$ae_score${NC} [$ae_status] $(progress_bar ${ae_normalized:-0} 100)"
        else
            echo -e "Isolation Forest: ${YELLOW}No data yet${NC}"
            echo -e "Autoencoder:      ${YELLOW}No data yet${NC}"
        fi
        
        echo ""
        echo "────────────────────────────────────────────────────────────"
        
        # Service status
        echo "Services:"
        echo -ne "  $(get_service_status 'elk-elasticsearch') Elasticsearch  "
        echo -ne "$(get_service_status 'elk-kibana') Kibana  "
        echo -ne "$(get_service_status 'elk-ml-detector') ML  "
        echo -e "$(get_service_status 'elk-log-generator') LogGen"
        
        echo ""
        echo "────────────────────────────────────────────────────────────"
        echo -e "${BLUE}Refreshing every ${REFRESH_INTERVAL}s... (Ctrl+C to stop)${NC}"
        
        sleep $REFRESH_INTERVAL
    done
}

# Trap Ctrl+C
trap 'echo ""; log_info "Monitor stopped."; exit 0' INT

# Check if services are running
if ! docker-compose ps | grep -q "Up"; then
    log_error "No services are running. Start them with: make up"
    exit 1
fi

# Start monitoring
monitor_loop

