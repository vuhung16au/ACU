# Makefile for TensorFlow MNIST Docker Project

.PHONY: help build run clean test dev shell logs

# Default target
help:
	@echo "Available commands:"
	@echo "  build    - Build the Docker image"
	@echo "  run      - Run the container"
	@echo "  clean    - Clean up Docker images and containers"
	@echo "  test     - Test the application locally"
	@echo "  dev      - Start development environment"
	@echo "  shell    - Open shell in running container"
	@echo "  logs     - Show container logs"
	@echo "  all      - Build and run the application"

# Build the Docker image
build:
	@echo "Building Docker image..."
	docker build -t tensorflow-mnist-app .

# Run the container
run:
	@echo "Running container..."
	docker run --rm --memory=4g tensorflow-mnist-app

# Build and run
all: build run

# Clean up Docker resources
clean:
	@echo "Cleaning up Docker resources..."
	docker system prune -f
	docker rmi tensorflow-mnist-app 2>/dev/null || true

# Test the application locally (requires Python and TensorFlow)
test:
	@echo "Testing application locally..."
	python -c "import tensorflow as tf; print(f'TensorFlow version: {tf.__version__}')"
	@echo "Local test passed!"

# Start development environment
dev:
	@echo "Starting development environment..."
	docker-compose --profile dev up -d

# Open shell in running container
shell:
	@echo "Opening shell in container..."
	docker exec -it tensorflow-mnist-classification-dev bash

# Show container logs
logs:
	@echo "Showing container logs..."
	docker logs tensorflow-mnist-classification

# Stop development environment
stop-dev:
	@echo "Stopping development environment..."
	docker-compose down

# Full cleanup (including development environment)
clean-all: clean stop-dev
	@echo "Full cleanup completed"
