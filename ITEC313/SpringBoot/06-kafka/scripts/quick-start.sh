#!/bin/bash

# Quick Start Script for Kafka Demo
# Complete setup and testing in one command

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
CYAN='\033[0;36m'
PURPLE='\033[0;35m'
NC='\033[0m' # No Color

# Configuration
SKIP_BUILD=false
SKIP_DOCKER=false
SKIP_TEST=false
AUTO_OPEN=false

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

log_step() {
    echo -e "${PURPLE}[STEP]${NC} $1"
}

# Print banner
print_banner() {
    echo -e "${CYAN}"
    echo "╔══════════════════════════════════════════════════════════════╗"
    echo "║                    🚀 Kafka Demo Quick Start                 ║"
    echo "║                                                              ║"
    echo "║  This script will set up the complete Kafka development     ║"
    echo "║  environment and run a demo of the application.             ║"
    echo "╚══════════════════════════════════════════════════════════════╝"
    echo -e "${NC}"
    echo ""
}

# Check prerequisites
check_prerequisites() {
    log_step "Checking prerequisites..."
    
    local missing_tools=()
    
    # Check required tools
    command -v java >/dev/null 2>&1 || missing_tools+=("java")
    command -v mvn >/dev/null 2>&1 || missing_tools+=("maven")
    command -v docker >/dev/null 2>&1 || missing_tools+=("docker")
    command -v docker-compose >/dev/null 2>&1 || missing_tools+=("docker-compose")
    command -v curl >/dev/null 2>&1 || missing_tools+=("curl")
    
    if [ ${#missing_tools[@]} -ne 0 ]; then
        log_error "Missing required tools: ${missing_tools[*]}"
        echo ""
        echo "Please install the missing tools and try again:"
        echo "  - Java 17+: https://adoptium.net/"
        echo "  - Maven 3.9+: https://maven.apache.org/"
        echo "  - Docker: https://www.docker.com/"
        echo "  - Docker Compose: https://docs.docker.com/compose/"
        echo "  - curl: Usually pre-installed or available via package manager"
        exit 1
    fi
    
    # Check Java version
    local java_version=$(java -version 2>&1 | head -n1 | cut -d'"' -f2 | cut -d'.' -f1)
    if [ "$java_version" -lt 17 ]; then
        log_error "Java 17 or higher is required. Current version: $(java -version 2>&1 | head -n1)"
        exit 1
    fi
    
    log_success "All prerequisites are satisfied"
}

# Build the application
build_application() {
    if [ "$SKIP_BUILD" = true ]; then
        log_info "Skipping application build"
        return
    fi
    
    log_step "Building the Spring Boot application..."
    
    if mvn clean compile -q; then
        log_success "Application built successfully"
    else
        log_error "Failed to build application"
        exit 1
    fi
}

# Start Docker infrastructure
start_infrastructure() {
    if [ "$SKIP_DOCKER" = true ]; then
        log_info "Skipping Docker infrastructure setup"
        return
    fi
    
    log_step "Starting Kafka infrastructure with Docker..."
    
    # Use our Docker management script
    if ./scripts/docker-setup.sh start; then
        log_success "Kafka infrastructure started successfully"
    else
        log_error "Failed to start Kafka infrastructure"
        exit 1
    fi
}

# Start the Spring Boot application
start_application() {
    log_step "Starting Spring Boot application..."
    
    log_info "Starting application in background..."
    log_info "This may take a few moments..."
    
    # Start the application in background
    mvn spring-boot:run > application.log 2>&1 &
    local app_pid=$!
    
    # Save PID for cleanup
    echo $app_pid > .app.pid
    
    # Wait for application to start
    log_info "Waiting for application to be ready..."
    local max_attempts=60
    local attempt=1
    
    while [ $attempt -le $max_attempts ]; do
        if curl -s --max-time 5 http://localhost:8086/api/messages/health >/dev/null 2>&1; then
            log_success "Application is ready!"
            return 0
        fi
        
        # Check if process is still running
        if ! kill -0 $app_pid 2>/dev/null; then
            log_error "Application process died. Check application.log for details."
            exit 1
        fi
        
        if [ $((attempt % 10)) -eq 0 ]; then
            log_info "Still waiting... (attempt $attempt/$max_attempts)"
        fi
        
        sleep 2
        ((attempt++))
    done
    
    log_error "Application did not start within expected time"
    log_info "Check application.log for details"
    exit 1
}

# Run demonstration
run_demonstration() {
    if [ "$SKIP_TEST" = true ]; then
        log_info "Skipping demonstration"
        return
    fi
    
    log_step "Running Kafka Demo..."
    
    echo ""
    echo -e "${CYAN}🎯 Demo: Sending and retrieving messages${NC}"
    echo ""
    
    # Run the demo script
    if ./scripts/demo.sh; then
        log_success "Demo completed successfully!"
    else
        log_warning "Demo had some issues, but this might be expected"
    fi
}

# Show access information
show_access_info() {
    echo ""
    echo -e "${BLUE}🎉 Quick Start Complete!${NC}"
    echo ""
    echo -e "${CYAN}📊 Access Information:${NC}"
    echo -e "  🌐 Kafka UI:           ${GREEN}http://localhost:8080${NC}"
    echo -e "  🚀 Spring Boot API:    ${GREEN}http://localhost:8086/api/messages${NC}"
    echo -e "  📈 Health Check:       ${GREEN}http://localhost:8086/api/messages/health${NC}"
    echo -e "  ⚡ Kafka Broker:       ${GREEN}localhost:9092${NC}"
    echo ""
    echo -e "${CYAN}🛠️  Development Commands:${NC}"
    echo -e "  📋 View logs:          ${YELLOW}tail -f application.log${NC}"
    echo -e "  🧪 Run tests:          ${YELLOW}./scripts/dev-test.sh${NC}"
    echo -e "  🐳 Manage Docker:      ${YELLOW}./scripts/docker-setup.sh status${NC}"
    echo -e "  📚 API docs:           ${YELLOW}cat API_DOCUMENTATION.md${NC}"
    echo ""
    echo -e "${CYAN}🔧 Useful Curl Commands:${NC}"
    echo -e "  Send message:"
    echo -e "    ${YELLOW}curl -X POST http://localhost:8086/api/messages \\${NC}"
    echo -e "    ${YELLOW}  -H \"Content-Type: application/json\" \\${NC}"
    echo -e "    ${YELLOW}  -d '{\"content\": \"Hello!\", \"sender\": \"You\", \"type\": \"INFO\"}'${NC}"
    echo ""
    echo -e "  Get messages:"
    echo -e "    ${YELLOW}curl http://localhost:8086/api/messages${NC}"
    echo ""
    echo -e "  Get statistics:"
    echo -e "    ${YELLOW}curl http://localhost:8086/api/messages/stats${NC}"
    echo ""
    
    if [ "$AUTO_OPEN" = true ]; then
        log_info "Opening Kafka UI in browser..."
        if command -v open >/dev/null 2>&1; then
            open http://localhost:8080
        elif command -v xdg-open >/dev/null 2>&1; then
            xdg-open http://localhost:8080
        else
            log_info "Please open http://localhost:8080 manually"
        fi
    fi
}

# Cleanup function
cleanup() {
    log_info "Cleaning up..."
    
    # Kill application if running
    if [ -f .app.pid ]; then
        local app_pid=$(cat .app.pid)
        if kill -0 $app_pid 2>/dev/null; then
            log_info "Stopping Spring Boot application (PID: $app_pid)..."
            kill $app_pid
            sleep 3
            # Force kill if still running
            if kill -0 $app_pid 2>/dev/null; then
                kill -9 $app_pid
            fi
        fi
        rm -f .app.pid
    fi
    
    # Clean up log file
    if [ -f application.log ]; then
        mv application.log "application-$(date +%Y%m%d-%H%M%S).log"
        log_info "Application log saved as application-$(date +%Y%m%d-%H%M%S).log"
    fi
}

# Handle interrupt signals
trap cleanup EXIT INT TERM

# Show help
show_help() {
    echo "Kafka Demo Quick Start Script"
    echo ""
    echo "Usage: $0 [OPTIONS]"
    echo ""
    echo "Options:"
    echo "  --skip-build       Skip Maven build step"
    echo "  --skip-docker      Skip Docker infrastructure setup"
    echo "  --skip-test        Skip demonstration"
    echo "  --open             Auto-open Kafka UI in browser"
    echo "  --help             Show this help message"
    echo ""
    echo "This script will:"
    echo "  1. Check prerequisites (Java, Maven, Docker, etc.)"
    echo "  2. Build the Spring Boot application"
    echo "  3. Start Kafka infrastructure with Docker"
    echo "  4. Start the Spring Boot application"
    echo "  5. Run a demonstration of the API"
    echo "  6. Show access information and examples"
    echo ""
    echo "Examples:"
    echo "  $0                     # Full quick start"
    echo "  $0 --skip-build        # Skip build, use existing artifacts"
    echo "  $0 --skip-docker       # Skip Docker, assume services running"
    echo "  $0 --open              # Auto-open Kafka UI"
    echo ""
}

# Parse command line arguments
parse_args() {
    while [[ $# -gt 0 ]]; do
        case $1 in
            --skip-build)
                SKIP_BUILD=true
                shift
                ;;
            --skip-docker)
                SKIP_DOCKER=true
                shift
                ;;
            --skip-test)
                SKIP_TEST=true
                shift
                ;;
            --open)
                AUTO_OPEN=true
                shift
                ;;
            --help|-h)
                show_help
                exit 0
                ;;
            *)
                log_error "Unknown option: $1"
                show_help
                exit 1
                ;;
        esac
    done
}

# Main function
main() {
    parse_args "$@"
    
    print_banner
    
    # Execute all steps
    check_prerequisites
    build_application
    start_infrastructure
    start_application
    run_demonstration
    show_access_info
    
    echo ""
    log_success "🎉 Kafka Demo is now running! Press Ctrl+C to stop."
    echo ""
    
    # Keep the script running
    while true; do
        sleep 10
        # Check if application is still running
        if [ -f .app.pid ]; then
            local app_pid=$(cat .app.pid)
            if ! kill -0 $app_pid 2>/dev/null; then
                log_error "Application stopped unexpectedly"
                break
            fi
        fi
    done
}

# Run main function with all arguments
main "$@"
