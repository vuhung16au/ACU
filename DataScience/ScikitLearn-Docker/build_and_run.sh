#!/bin/bash

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

# Check if Docker is running
check_docker() {
    if ! docker info > /dev/null 2>&1; then
        print_error "Docker is not running. Please start Docker and try again."
        exit 1
    fi
    print_success "Docker is running"
}

# Build the Docker image
build_image() {
    print_status "Building Docker image..."
    if docker build -t iris-ml-app .; then
        print_success "Docker image built successfully"
    else
        print_error "Failed to build Docker image"
        exit 1
    fi
}

# Run the container
run_container() {
    print_status "Running container..."
    if docker run --rm iris-ml-app; then
        print_success "Container completed successfully"
    else
        print_error "Container failed to run"
        exit 1
    fi
}

# Clean up old images and containers
cleanup() {
    print_status "Cleaning up old images and containers..."
    docker system prune -f
    print_success "Cleanup completed"
}

# Main function
main() {
    echo "=== Dockerized ML Application Build and Run Script ==="
    echo
    
    # Check Docker
    check_docker
    
    # Build image
    build_image
    
    # Run container
    run_container
    
    echo
    print_success "All done! 🎉"
}

# Handle command line arguments
case "${1:-}" in
    "cleanup")
        cleanup
        ;;
    "build")
        check_docker
        build_image
        ;;
    "run")
        check_docker
        run_container
        ;;
    "help"|"-h"|"--help")
        echo "Usage: $0 [command]"
        echo
        echo "Commands:"
        echo "  (no args)  Build and run the application"
        echo "  build      Build the Docker image only"
        echo "  run        Run the container only"
        echo "  cleanup    Clean up old Docker images and containers"
        echo "  help       Show this help message"
        ;;
    *)
        main
        ;;
esac
