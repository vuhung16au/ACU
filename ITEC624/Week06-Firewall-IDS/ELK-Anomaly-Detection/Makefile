.PHONY: help build up down restart logs ps health test tests clean
.DEFAULT_GOAL := help

# Colors for output
BLUE := \033[0;34m
GREEN := \033[0;32m
YELLOW := \033[1;33m
RED := \033[0;31m
NC := \033[0m # No Color

help: ## Display this help message
	@echo "$(BLUE)ELK Anomaly Detection Lab - Available Commands$(NC)"
	@echo "=================================================="
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "$(GREEN)%-20s$(NC) %s\n", $$1, $$2}'

# Setup and Build
build: ## Build all Docker containers and train initial models
	@echo "$(BLUE)Building Docker containers...$(NC)"
	docker-compose build
	@echo "$(GREEN)Build completed!$(NC)"
	@echo "$(YELLOW)Note: Models will be trained on first run$(NC)"

up: ## Start all services (detached mode)
	@echo "$(BLUE)Starting all services...$(NC)"
	docker-compose up -d
	@echo "$(GREEN)Services started!$(NC)"
	@echo "$(YELLOW)Run 'make health' to check status$(NC)"
	@echo "$(YELLOW)Kibana will be available at: http://localhost:5601$(NC)"

init: ## Initialize Elasticsearch for single-node deployment
	@echo "$(BLUE)Initializing Elasticsearch...$(NC)"
	@bash scripts/init_elasticsearch.sh

start: ## Start services and initialize (recommended for first run)
	@echo "$(BLUE)Starting ELK Stack...$(NC)"
	@$(MAKE) up
	@echo "$(YELLOW)Waiting for services to be ready...$(NC)"
	@sleep 10
	@$(MAKE) init
	@echo "$(GREEN)ELK Stack ready!$(NC)"
	@echo "$(YELLOW)Run 'make health' to verify everything is working$(NC)"

down: ## Stop all services
	@echo "$(BLUE)Stopping all services...$(NC)"
	docker-compose down
	@echo "$(GREEN)Services stopped!$(NC)"

restart: ## Restart all services
	@echo "$(BLUE)Restarting services...$(NC)"
	@$(MAKE) down
	@$(MAKE) up

logs: ## Tail logs from all containers
	docker-compose logs -f

ps: ## Show service status
	docker-compose ps

# Development
dev: ## Start in development mode (attached logs)
	@echo "$(BLUE)Starting in development mode...$(NC)"
	docker-compose up

attach-ml: ## Attach to ml-detector container terminal
	docker attach elk-ml-detector

attach-gen: ## Attach to log-generator container terminal
	docker attach elk-log-generator

# Testing
tests: test-baseline test-isolation-forest test-autoencoder test-comparison test-dashboard ## Run all tests sequentially

test-baseline: ## Verify normal traffic generation
	@echo "$(BLUE)Testing baseline traffic...$(NC)"
	@bash scripts/test_baseline.sh

test-isolation-forest: ## Test Isolation Forest with burst anomaly
	@echo "$(BLUE)Testing Isolation Forest detection...$(NC)"
	@bash scripts/test_isolation_forest.sh

test-autoencoder: ## Test Autoencoder with pattern anomaly
	@echo "$(BLUE)Testing Autoencoder detection...$(NC)"
	@bash scripts/test_autoencoder.sh

test-comparison: ## Compare both algorithms
	@echo "$(BLUE)Running algorithm comparison...$(NC)"
	@bash scripts/test_comparison.sh

test-dashboard: ## Verify Kibana dashboard access
	@echo "$(BLUE)Testing Kibana dashboard...$(NC)"
	@bash scripts/test_dashboard.sh

# Monitoring & Control
monitor: ## Real-time monitoring dashboard (terminal UI)
	@bash scripts/monitor.sh

health: ## Service health check
	@bash scripts/health_check.sh

reset: ## Clear data, keep services running
	@bash scripts/reset.sh

export: ## Export detection results to CSV
	@bash scripts/export_results.sh

report: ## Generate HTML report
	@bash scripts/generate_report.sh

# Utilities
clean: ## Remove all containers, volumes, and data
	@echo "$(YELLOW)Warning: This will remove all containers and data!$(NC)"
	@read -p "Continue? [y/N] " -n 1 -r; \
	echo; \
	if [[ $$REPLY =~ ^[Yy]$$ ]]; then \
		docker-compose down -v; \
		rm -rf data/elasticsearch/* logs/*/*.log exports/* reports/*; \
		echo "$(GREEN)Cleanup completed!$(NC)"; \
	else \
		echo "$(YELLOW)Cleanup cancelled.$(NC)"; \
	fi

clean-logs: ## Clear log files only
	@echo "$(BLUE)Clearing log files...$(NC)"
	rm -f logs/normal/*.log logs/anomaly/*.log logs/ml-detector/*.log
	@echo "$(GREEN)Logs cleared!$(NC)"

demo: ## Run automated demonstration
	@bash scripts/demo.sh

video: ## Generate video tutorial
	@bash scripts/video_tutorial.sh

# Anomaly Injection
inject-burst: ## Inject burst anomaly
	docker-compose run --rm anomaly-injector python injector.py --type burst --duration 30

inject-errors: ## Inject error flood
	docker-compose run --rm anomaly-injector python injector.py --type errors --duration 30

inject-slow: ## Inject slow requests
	docker-compose run --rm anomaly-injector python injector.py --type slow --duration 30

inject-scan: ## Inject scan pattern
	docker-compose run --rm anomaly-injector python injector.py --type scan --duration 30

inject-all: ## Inject all anomaly types
	docker-compose run --rm anomaly-injector python injector.py --type all --duration 30

inject-custom: ## Interactive custom anomaly injection
	@bash scripts/inject_custom_anomaly.sh

# CI/CD
ci-test: ## Run tests in CI mode (non-interactive)
	@bash scripts/ci_test.sh

# Quick Start
quickstart: build up health ## Quick start: build, start, and verify
	@echo "$(GREEN)Lab is ready!$(NC)"
	@echo "$(YELLOW)Open Kibana at: http://localhost:5601$(NC)"
	@echo "$(YELLOW)Run 'make demo' for automated demonstration$(NC)"

