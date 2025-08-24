#!/bin/bash

# Neo4j Spring Boot Demo Script
# This script demonstrates the complete workflow of the project

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

# Function to wait for Neo4j to be ready
wait_for_neo4j() {
    print_status "Waiting for Neo4j to be ready..."
    local max_attempts=30
    local attempt=1
    
    while [ $attempt -le $max_attempts ]; do
        if curl -s http://localhost:7474 > /dev/null 2>&1; then
            print_success "Neo4j is ready!"
            return 0
        fi
        print_status "Attempt $attempt/$max_attempts - Neo4j not ready yet, waiting..."
        sleep 2
        attempt=$((attempt + 1))
    done
    
    print_error "Neo4j failed to start within expected time"
    return 1
}

# Function to wait for Spring Boot to be ready
wait_for_spring_boot() {
    print_status "Waiting for Spring Boot application to be ready..."
    local max_attempts=30
    local attempt=1
    
    while [ $attempt -le $max_attempts ]; do
        if curl -s http://localhost:8080/api/people > /dev/null 2>&1; then
            print_success "Spring Boot application is ready!"
            return 0
        fi
        print_status "Attempt $attempt/$max_attempts - Spring Boot not ready yet, waiting..."
        sleep 3
        attempt=$((attempt + 1))
    done
    
    print_error "Spring Boot application failed to start within expected time"
    return 1
}

# Function to store data in Neo4j
store_data() {
    print_status "Storing data in Neo4j..."
    
    # Create some sample people
    print_status "Creating sample people..."
    
    # Person 1
    curl -X POST http://localhost:8080/api/people \
        -H "Content-Type: application/json" \
        -d '{"name": "John Doe", "age": 30, "email": "john.doe@example.com"}' \
        -s | jq '.' || echo "Failed to create John Doe"
    
    # Person 2
    curl -X POST http://localhost:8080/api/people \
        -H "Content-Type: application/json" \
        -d '{"name": "Jane Smith", "age": 25, "email": "jane.smith@example.com"}' \
        -s | jq '.' || echo "Failed to create Jane Smith"
    
    # Person 3
    curl -X POST http://localhost:8080/api/people \
        -H "Content-Type: application/json" \
        -d '{"name": "Bob Johnson", "age": 35, "email": "bob.johnson@example.com"}' \
        -s | jq '.' || echo "Failed to create Bob Johnson"
    
    # Person 4
    curl -X POST http://localhost:8080/api/people \
        -H "Content-Type: application/json" \
        -d '{"name": "Alice Brown", "age": 28, "email": "alice.brown@example.com"}' \
        -s | jq '.' || echo "Failed to create Alice Brown"
    
    print_success "Data stored successfully!"
}

# Function to retrieve data from Neo4j
retrieve_data() {
    print_status "Retrieving data from Neo4j..."
    
    echo ""
    print_status "1. Getting all people:"
    curl -X GET http://localhost:8080/api/people -s | jq '.' || echo "Failed to retrieve all people"
    
    echo ""
    print_status "2. Getting people ordered by name:"
    curl -X GET http://localhost:8080/api/people/ordered -s | jq '.' || echo "Failed to retrieve ordered people"
    
    echo ""
    print_status "3. Getting people older than 30:"
    curl -X GET http://localhost:8080/api/people/older-than/30 -s | jq '.' || echo "Failed to retrieve people older than 30"
    
    echo ""
    print_status "4. Getting people by name (John):"
    curl -X GET http://localhost:8080/api/people/name/John -s | jq '.' || echo "Failed to retrieve people by name"
    
    echo ""
    print_status "5. Getting specific person by ID (1):"
    curl -X GET http://localhost:8080/api/people/1 -s | jq '.' || echo "Failed to retrieve person by ID"
    
    print_success "Data retrieved successfully!"
}

# Function to clean up
cleanup() {
    print_status "Cleaning up..."
    
    # Stop Spring Boot application
    if [ ! -z "$SPRING_PID" ]; then
        print_status "Stopping Spring Boot application..."
        kill $SPRING_PID 2>/dev/null || true
    fi
    
    # Stop Docker Compose
    print_status "Stopping Docker Compose..."
    cd docker && docker-compose down
    cd ..
    
    print_success "Cleanup completed!"
}

# Trap to ensure cleanup on script exit
trap cleanup EXIT

# Main demo execution
main() {
    echo "=========================================="
    echo "Neo4j Spring Boot Demo"
    echo "=========================================="
    echo ""
    
    # Check if Docker is running
    if ! docker info > /dev/null 2>&1; then
        print_error "Docker is not running. Please start Docker and try again."
        exit 1
    fi
    
    # Check if jq is installed
    if ! command -v jq &> /dev/null; then
        print_warning "jq is not installed. Installing jq for better JSON formatting..."
        if [[ "$OSTYPE" == "darwin"* ]]; then
            brew install jq
        elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
            sudo apt-get update && sudo apt-get install -y jq
        else
            print_warning "Please install jq manually for better JSON formatting"
        fi
    fi
    
    # Step 1: Start Docker Compose
    print_status "Step 1: Starting Docker Compose..."
    cd docker
    docker-compose up -d
    cd ..
    
    # Wait for Neo4j to be ready
    wait_for_neo4j
    
    # Step 2: Run Spring Boot application
    print_status "Step 2: Running Spring Boot application..."
    mvn spring-boot:run &
    SPRING_PID=$!
    
    # Wait for Spring Boot to be ready
    wait_for_spring_boot
    
    # Step 3: Store data in Neo4j
    print_status "Step 3: Storing data in Neo4j..."
    store_data
    
    # Step 4: Retrieve data from Neo4j
    print_status "Step 4: Retrieving data from Neo4j..."
    retrieve_data
    
    echo ""
    echo "=========================================="
    print_success "Demo completed successfully!"
    echo "=========================================="
    echo ""
    print_status "You can now:"
    echo "  - Access Neo4j Browser at: http://localhost:7474"
    echo "  - Username: neo4j"
    echo "  - Password: Sydney@9876"
    echo "  - Access Spring Boot API at: http://localhost:8080/api/people"
    echo ""
    print_status "Press Ctrl+C to stop the demo and cleanup..."
    
    # Keep the script running until user interrupts
    wait $SPRING_PID
}

# Run the main function
main "$@"
