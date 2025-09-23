# Makefile for Dockerized ML Application

.PHONY: help build run test clean dev

# Default target
help:
	@echo "Available commands:"
	@echo "  make build    - Build the Docker image"
	@echo "  make run      - Run the container"
	@echo "  make test     - Test the application locally"
	@echo "  make clean    - Clean up Docker images and containers"
	@echo "  make dev      - Start development container with shell"
	@echo "  make all      - Build and run the application"

# Build the Docker image
build:
	@echo "Building Docker image..."
	docker build -t iris-ml-app .

# Run the container
run:
	@echo "Running container..."
	docker run --rm iris-ml-app

# Test locally
test:
	@echo "Testing application locally..."
	python test_local.py

# Clean up
clean:
	@echo "Cleaning up Docker resources..."
	docker system prune -f

# Development mode
dev:
	@echo "Starting development container..."
	docker-compose --profile dev run --rm iris-ml-dev

# Build and run
all: build run

# Docker Compose commands
up:
	docker-compose up --build

down:
	docker-compose down

# Show container logs
logs:
	docker-compose logs

# Interactive shell in running container
shell:
	docker-compose exec iris-ml-app /bin/bash
