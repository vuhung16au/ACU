#!/bin/bash

# Spring Boot CORS Demo Script
# This script demonstrates CORS functionality by running the Spring Boot application
# and serving a test page that makes cross-origin requests.

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

# Function to check if a command exists
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Function to check if a port is in use
port_in_use() {
    lsof -i :$1 >/dev/null 2>&1
}

# Function to wait for a service to be ready
wait_for_service() {
    local host=$1
    local port=$2
    local max_attempts=30
    local attempt=1
    
    print_status "Waiting for $host:$port to be ready..."
    
    while [ $attempt -le $max_attempts ]; do
        if curl -s "$host:$port" >/dev/null 2>&1; then
            print_success "$host:$port is ready!"
            return 0
        fi
        
        echo -n "."
        sleep 1
        attempt=$((attempt + 1))
    done
    
    print_error "Timeout waiting for $host:$port"
    return 1
}

# Function to cleanup background processes
cleanup() {
    print_status "Cleaning up background processes..."
    
    # Kill Spring Boot application
    if [ ! -z "$SPRING_PID" ]; then
        kill $SPRING_PID 2>/dev/null || true
    fi
    
    # Kill HTTP server
    if [ ! -z "$HTTP_PID" ]; then
        kill $HTTP_PID 2>/dev/null || true
    fi
    
    # Kill any processes on ports 8080 and 9000
    pkill -f "spring-boot:run" 2>/dev/null || true
    pkill -f "http.server 9000" 2>/dev/null || true
    
    print_success "Cleanup completed"
}

# Set up cleanup on script exit
trap cleanup EXIT

# Main script
main() {
    echo "🌐 Spring Boot CORS Demo Script"
    echo "================================"
    echo ""
    
    # Check prerequisites
    print_status "Checking prerequisites..."
    
    if ! command_exists java; then
        print_error "Java is not installed or not in PATH"
        exit 1
    fi
    
    if ! command_exists mvn; then
        print_error "Maven is not installed or not in PATH"
        exit 1
    fi
    
    if ! command_exists python3; then
        print_error "Python 3 is not installed or not in PATH"
        exit 1
    fi
    
    if ! command_exists curl; then
        print_error "curl is not installed or not in PATH"
        exit 1
    fi
    
    print_success "All prerequisites are satisfied"
    
    # Check if ports are available
    print_status "Checking port availability..."
    
    if port_in_use 8080; then
        print_warning "Port 8080 is already in use. Attempting to kill existing processes..."
        pkill -f "spring-boot:run" 2>/dev/null || true
        sleep 2
    fi
    
    if port_in_use 9000; then
        print_warning "Port 9000 is already in use. Attempting to kill existing processes..."
        pkill -f "http.server 9000" 2>/dev/null || true
        sleep 2
    fi
    
    print_success "Ports are available"
    
    # Build and test the project
    print_status "Building and testing the project..."
    
    if ! mvn clean compile test; then
        print_error "Build or tests failed"
        exit 1
    fi
    
    print_success "Project built and tested successfully"
    
    # Start Spring Boot application
    print_status "Starting Spring Boot application on port 8080..."
    
    mvn spring-boot:run > spring-boot.log 2>&1 &
    SPRING_PID=$!
    
    # Wait for Spring Boot to start
    if ! wait_for_service "http://localhost" "8080"; then
        print_error "Failed to start Spring Boot application"
        exit 1
    fi
    
    print_success "Spring Boot application is running on http://localhost:8080"
    
    # Test the API
    print_status "Testing the API..."
    
    # Test basic greeting
    RESPONSE=$(curl -s http://localhost:8080/greeting)
    if [[ $RESPONSE == *"Hello, World!"* ]]; then
        print_success "Basic greeting endpoint working: $RESPONSE"
    else
        print_error "Basic greeting endpoint failed: $RESPONSE"
        exit 1
    fi
    
    # Test CORS headers
    CORS_HEADER=$(curl -s -H "Origin: http://localhost:9000" -I http://localhost:8080/greeting | grep "Access-Control-Allow-Origin")
    if [[ $CORS_HEADER == *"http://localhost:9000"* ]]; then
        print_success "CORS headers are properly configured: $CORS_HEADER"
    else
        print_error "CORS headers are not properly configured"
        exit 1
    fi
    
    # Start HTTP server for CORS test page
    print_status "Starting HTTP server on port 9000..."
    
    python3 -m http.server 9000 > http-server.log 2>&1 &
    HTTP_PID=$!
    
    # Wait for HTTP server to start
    if ! wait_for_service "http://localhost" "9000"; then
        print_error "Failed to start HTTP server"
        exit 1
    fi
    
    print_success "HTTP server is running on http://localhost:9000"
    
    # Test CORS test page
    print_status "Testing CORS test page..."
    
    if curl -s -I http://localhost:9000/script/cors-test.html | grep -q "200 OK"; then
        print_success "CORS test page is accessible"
    else
        print_error "CORS test page is not accessible"
        exit 1
    fi
    
    # Display final status
    echo ""
    echo "🎉 Demo is ready!"
    echo "================"
    echo ""
    echo "🌐 Spring Boot API: http://localhost:8080"
    echo "   - Endpoint: http://localhost:8080/greeting"
    echo "   - CORS configured for: http://localhost:9000"
    echo ""
    echo "📄 CORS Test Page: http://localhost:9000/script/cors-test.html"
    echo "   - Interactive testing interface"
    echo "   - Test cross-origin requests"
    echo ""
    echo "🧪 Available Tests:"
    echo "   1. Basic Greeting (Default)"
    echo "   2. Custom Greeting (with name parameter)"
    echo "   3. CORS Headers Check"
    echo "   4. Multiple Requests"
    echo "   5. Error Handling"
    echo ""
    echo "📋 Manual Testing:"
    echo "   curl http://localhost:8080/greeting"
    echo "   curl \"http://localhost:8080/greeting?name=YourName\""
    echo "   curl -H \"Origin: http://localhost:9000\" -v http://localhost:8080/greeting"
    echo ""
    echo "⏹️  To stop the demo, press Ctrl+C"
    echo ""
    
    # Keep the script running
    print_status "Demo is running. Press Ctrl+C to stop..."
    
    # Wait for user interruption
    while true; do
        sleep 1
    done
}

# Run the main function
main "$@"
