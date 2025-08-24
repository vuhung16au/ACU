#!/bin/bash

# Redis Demo Script
# This script demonstrates the Redis messaging functionality

set -e  # Exit on any error

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Function to check if Redis is running
check_redis() {
    print_status "Checking if Redis server is running..."
    
    if docker ps | grep -q redis-server; then
        print_success "Redis server is running"
        return 0
    else
        print_warning "Redis server is not running"
        return 1
    fi
}

# Function to start Redis if not running
start_redis() {
    print_status "Starting Redis server..."
    
    if [ -d "docker" ]; then
        cd docker
        docker-compose up -d
        cd ..
        print_success "Redis server started"
    else
        print_error "Docker directory not found"
        exit 1
    fi
}

# Function to wait for Redis to be ready
wait_for_redis() {
    print_status "Waiting for Redis to be ready..."
    
    local max_attempts=30
    local attempt=1
    
    while [ $attempt -le $max_attempts ]; do
        if docker exec redis-server redis-cli ping > /dev/null 2>&1; then
            print_success "Redis is ready"
            return 0
        fi
        
        print_status "Attempt $attempt/$max_attempts - Redis not ready yet, waiting..."
        sleep 2
        attempt=$((attempt + 1))
    done
    
    print_error "Redis failed to start within expected time"
    exit 1
}

# Function to build the project
build_project() {
    print_status "Building the project..."
    
    if mvn clean compile; then
        print_success "Project built successfully"
    else
        print_error "Failed to build project"
        exit 1
    fi
}

# Function to send custom message via Redis CLI
send_custom_message() {
    local message="$1"
    print_status "Sending custom message via Redis CLI: '$message'"
    
    if docker exec redis-server redis-cli publish chat "$message" > /dev/null 2>&1; then
        print_success "Message sent successfully"
    else
        print_error "Failed to send message"
    fi
}

# Function to monitor Redis messages
monitor_redis() {
    print_status "Monitoring Redis messages (press Ctrl+C to stop)..."
    print_status "This will show all messages being sent to Redis channels"
    echo
    
    docker exec redis-server redis-cli monitor
}

# Function to show Redis info
show_redis_info() {
    print_status "Redis server information:"
    docker exec redis-server redis-cli info server | head -10
}

# Function to clean up
cleanup() {
    print_status "Cleaning up..."
    
    # Stop the application if it's running
    pkill -f "spring-boot:run" 2>/dev/null || true
    
    print_success "Cleanup completed"
}

# Main execution
main() {
    echo "=========================================="
    echo "    Redis Messaging Demo Script"
    echo "=========================================="
    echo
    
    # Check if Docker is running
    if ! docker info > /dev/null 2>&1; then
        print_error "Docker is not running. Please start Docker and try again."
        exit 1
    fi
    
    # Check and start Redis if needed
    if ! check_redis; then
        start_redis
        wait_for_redis
    fi
    
    # Show Redis info
    show_redis_info
    echo
    
    # Build the project
    build_project
    echo
    
    # Start monitoring in background
    print_status "Starting Redis monitoring in background..."
    docker exec redis-server redis-cli monitor > /tmp/redis_monitor.log 2>&1 &
    monitor_pid=$!
    
    # Wait a moment for monitoring to start
    sleep 2
    
    # Send a custom message via Redis CLI
    print_status "Step 1: Sending custom message via Redis CLI..."
    send_custom_message "Hello from demo script!"
    echo
    
    # Wait a moment
    sleep 1
    
    # Run the Spring Boot application (ONLY ONCE at the beginning)
    print_status "Step 2: Running Spring Boot application..."
    print_status "The application will send 5 messages and receive them back"
    echo
    
    # Run the application
    mvn spring-boot:run
    
    # Stop monitoring
    print_status "Stopping Redis monitoring..."
    kill $monitor_pid 2>/dev/null || true
    
    # Show captured messages
    if [ -f /tmp/redis_monitor.log ]; then
        echo
        print_status "Redis activity during demo:"
        cat /tmp/redis_monitor.log | head -30
        rm -f /tmp/redis_monitor.log
    fi
    
    echo
    print_success "Demo completed! All tests executed successfully."
}

# Trap to ensure cleanup on script exit
trap cleanup EXIT

# Run main function
main "$@"
