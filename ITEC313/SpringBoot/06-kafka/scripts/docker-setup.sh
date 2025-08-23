#!/bin/bash

# Docker Compose Management Script for Kafka Demo
# This script provides easy commands to manage the development environment

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

print_header() {
    echo -e "${BLUE}================================${NC}"
    echo -e "${BLUE}$1${NC}"
    echo -e "${BLUE}================================${NC}"
}

# Function to check if Docker is running
check_docker() {
    if ! docker info > /dev/null 2>&1; then
        print_error "Docker is not running. Please start Docker and try again."
        exit 1
    fi
}

# Function to check if Docker Compose is available
check_docker_compose() {
    if ! command -v docker-compose &> /dev/null; then
        print_error "Docker Compose is not installed. Please install Docker Compose and try again."
        exit 1
    fi
}

# Function to start services
start_services() {
    print_header "Starting Kafka Demo Development Environment"
    check_docker
    check_docker_compose
    
    print_status "Starting all services..."
    docker-compose up -d
    
    print_status "Waiting for services to be ready..."
    sleep 10
    
    print_status "Checking service status..."
    docker-compose ps
    
    print_header "Services Access Information"
    echo "Kafka UI:     http://localhost:8080"
    echo "pgAdmin:      http://localhost:5050 (admin@kafka-demo.com / admin123)"
    echo "PostgreSQL:   localhost:5432 (kafka_user / kafka_password)"
    echo "Kafka:        localhost:9092"
    echo "ZooKeeper:    localhost:2181"
    echo ""
    print_status "All services are ready!"
}

# Function to stop services
stop_services() {
    print_header "Stopping Kafka Demo Development Environment"
    print_status "Stopping all services..."
    docker-compose down
    print_status "Services stopped successfully!"
}

# Function to restart services
restart_services() {
    print_header "Restarting Kafka Demo Development Environment"
    stop_services
    sleep 2
    start_services
}

# Function to show logs
show_logs() {
    if [ -z "$1" ]; then
        print_status "Showing logs for all services..."
        docker-compose logs -f
    else
        print_status "Showing logs for service: $1"
        docker-compose logs -f "$1"
    fi
}

# Function to show status
show_status() {
    print_header "Service Status"
    docker-compose ps
}

# Function to clean up everything
cleanup() {
    print_header "Cleaning Up Development Environment"
    print_warning "This will stop all services and remove all data!"
    read -p "Are you sure you want to continue? (y/N): " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        print_status "Stopping services and removing volumes..."
        docker-compose down -v --remove-orphans
        print_status "Cleaning up Docker system..."
        docker system prune -f
        print_status "Cleanup completed!"
    else
        print_status "Cleanup cancelled."
    fi
}

# Function to show help
show_help() {
    print_header "Docker Compose Management Script"
    echo "Usage: $0 [COMMAND]"
    echo ""
    echo "Commands:"
    echo "  start     - Start all services"
    echo "  stop      - Stop all services"
    echo "  restart   - Restart all services"
    echo "  status    - Show service status"
    echo "  logs      - Show logs (all services or specific service)"
    echo "  cleanup   - Stop services and remove all data"
    echo "  help      - Show this help message"
    echo ""
    echo "Examples:"
    echo "  $0 start"
    echo "  $0 logs kafka"
    echo "  $0 logs postgres"
    echo ""
}

# Main script logic
case "${1:-help}" in
    start)
        start_services
        ;;
    stop)
        stop_services
        ;;
    restart)
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
    help|--help|-h)
        show_help
        ;;
    *)
        print_error "Unknown command: $1"
        echo ""
        show_help
        exit 1
        ;;
esac
