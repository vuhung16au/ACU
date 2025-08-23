#!/bin/bash

# Docker Setup Management Script for Kafka Demo
# Provides easy management of Docker services with health checks and better startup

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Configuration
COMPOSE_FILE="docker-compose.yml"
SERVICES=("kafka" "kafka-ui")

# Helper functions
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

# Check if Docker and Docker Compose are available
check_prerequisites() {
    log_info "Checking prerequisites..."
    
    if ! command -v docker &> /dev/null; then
        log_error "Docker is not installed or not in PATH"
        exit 1
    fi
    
    if ! command -v docker-compose &> /dev/null; then
        log_error "Docker Compose is not installed or not in PATH"
        exit 1
    fi
    
    if [ ! -f "$COMPOSE_FILE" ]; then
        log_error "docker-compose.yml not found in current directory"
        exit 1
    fi
    
    log_success "Prerequisites check passed"
}

# Start services with health checks
start_services() {
    log_info "Starting Kafka infrastructure..."
    
    # Check if services are already running
    if docker-compose ps -q | grep -q .; then
        log_warning "Some services are already running. Use 'restart' to restart them."
        show_status
        return
    fi
    
    # Start services
    log_info "Starting services in detached mode..."
    docker-compose up -d
    
    # Wait for services to be healthy
    log_info "Waiting for services to become healthy..."
    wait_for_health
    
    log_success "All services started successfully!"
    echo ""
    show_access_info
}

# Stop services
stop_services() {
    log_info "Stopping Kafka infrastructure..."
    docker-compose down
    log_success "All services stopped successfully!"
}

# Restart services
restart_services() {
    log_info "Restarting Kafka infrastructure..."
    docker-compose down
    sleep 2
    docker-compose up -d
    wait_for_health
    log_success "All services restarted successfully!"
    echo ""
    show_access_info
}

# Wait for services to be healthy
wait_for_health() {
    local max_attempts=30
    local attempt=1
    
    while [ $attempt -le $max_attempts ]; do
        log_info "Health check attempt $attempt/$max_attempts..."
        
        local all_healthy=true
        for service in "${SERVICES[@]}"; do
            local health=$(docker-compose ps --format json | jq -r ".[] | select(.Service == \"$service\") | .Health")
            if [[ "$health" != "healthy" ]]; then
                all_healthy=false
                log_info "$service is $health"
                break
            fi
        done
        
        if $all_healthy; then
            log_success "All services are healthy!"
            return 0
        fi
        
        sleep 5
        ((attempt++))
    done
    
    log_error "Services did not become healthy within expected time"
    log_info "You can check service logs with: $0 logs"
    return 1
}

# Show service status
show_status() {
    log_info "Service Status:"
    echo ""
    docker-compose ps
    echo ""
    
    # Show health status
    log_info "Health Status:"
    for service in "${SERVICES[@]}"; do
        local status=$(docker-compose ps --format json | jq -r ".[] | select(.Service == \"$service\") | .Health // \"unknown\"")
        local state=$(docker-compose ps --format json | jq -r ".[] | select(.Service == \"$service\") | .State")
        
        if [[ "$status" == "healthy" ]]; then
            echo -e "  ${service}: ${GREEN}${status}${NC} (${state})"
        elif [[ "$status" == "unhealthy" ]]; then
            echo -e "  ${service}: ${RED}${status}${NC} (${state})"
        else
            echo -e "  ${service}: ${YELLOW}${status}${NC} (${state})"
        fi
    done
}

# Show logs
show_logs() {
    local service=$1
    if [ -n "$service" ]; then
        log_info "Showing logs for $service..."
        docker-compose logs -f "$service"
    else
        log_info "Showing logs for all services..."
        docker-compose logs -f
    fi
}

# Clean up everything
cleanup() {
    log_warning "This will stop all services and remove all data!"
    read -p "Are you sure? (y/N): " -n 1 -r
    echo
    
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        log_info "Cleaning up..."
        docker-compose down -v --remove-orphans
        docker system prune -f
        log_success "Cleanup completed!"
    else
        log_info "Cleanup cancelled"
    fi
}

# Show access information
show_access_info() {
    echo -e "${BLUE}📊 Access Information:${NC}"
    echo -e "  🌐 Kafka UI:    ${GREEN}http://localhost:8080${NC}"
    echo -e "  ⚡ Kafka:       ${GREEN}localhost:9092${NC}"
    echo -e "  📈 JMX:         ${GREEN}localhost:9101${NC}"
    echo ""
    echo -e "${BLUE}🚀 Next Steps:${NC}"
    echo -e "  1. Start your Spring Boot app: ${YELLOW}mvn spring-boot:run${NC}"
    echo -e "  2. Test the API: ${YELLOW}./scripts/demo.sh${NC}"
    echo -e "  3. Monitor Kafka: ${YELLOW}open http://localhost:8080${NC}"
}

# Show help
show_help() {
    echo "Kafka Docker Management Script"
    echo ""
    echo "Usage: $0 [COMMAND] [OPTIONS]"
    echo ""
    echo "Commands:"
    echo "  start          Start all services with health checks"
    echo "  stop           Stop all services"
    echo "  restart        Restart all services"
    echo "  status         Show service status and health"
    echo "  logs [service] Show logs (all services or specific service)"
    echo "  cleanup        Stop services and remove all data (WARNING: destructive)"
    echo "  health         Wait for services to become healthy"
    echo "  help           Show this help message"
    echo ""
    echo "Examples:"
    echo "  $0 start                 # Start all services"
    echo "  $0 logs kafka           # Show Kafka logs"
    echo "  $0 status               # Show service status"
    echo ""
    echo "Services managed: ${SERVICES[*]}"
}

# Test connectivity
test_connectivity() {
    log_info "Testing service connectivity..."
    
    # Test Kafka
    if curl -s --max-time 5 http://localhost:8080/api/clusters >/dev/null; then
        log_success "Kafka UI is accessible"
    else
        log_error "Kafka UI is not accessible"
    fi
    
    # Test Kafka broker (requires kafkacat or similar)
    if command -v kafkacat &> /dev/null; then
        if timeout 5 kafkacat -b localhost:9092 -L >/dev/null 2>&1; then
            log_success "Kafka broker is accessible"
        else
            log_error "Kafka broker is not accessible"
        fi
    else
        log_info "kafkacat not available, skipping Kafka broker test"
    fi
}

# Main script logic
main() {
    case "${1:-help}" in
        start)
            check_prerequisites
            start_services
            ;;
        stop)
            stop_services
            ;;
        restart)
            check_prerequisites
            restart_services
            ;;
        status)
            show_status
            ;;
        logs)
            show_logs "$2"
            ;;
        cleanup)
            cleanup
            ;;
        health)
            wait_for_health
            ;;
        test)
            test_connectivity
            ;;
        help|--help|-h)
            show_help
            ;;
        *)
            log_error "Unknown command: $1"
            echo ""
            show_help
            exit 1
            ;;
    esac
}

# Run main function with all arguments
main "$@"