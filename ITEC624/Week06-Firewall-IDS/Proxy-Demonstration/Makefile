# Makefile for Application Proxy Firewall Lab
# Educational demonstration of forward and reverse proxies

.PHONY: help build clean test test-forward test-reverse logs-forward logs-reverse

# Default target
.DEFAULT_GOAL := help

# Colors for help output
BLUE := \033[0;34m
GREEN := \033[0;32m
YELLOW := \033[1;33m
NC := \033[0m # No Color

help: ## Show this help message
	@echo ""
	@echo "$(BLUE)═══════════════════════════════════════════════════════$(NC)"
	@echo "$(BLUE)  Application Proxy Firewall Lab - Makefile Commands$(NC)"
	@echo "$(BLUE)═══════════════════════════════════════════════════════$(NC)"
	@echo ""
	@echo "$(GREEN)Available targets:$(NC)"
	@echo ""
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | \
		awk 'BEGIN {FS = ":.*?## "}; {printf "  $(YELLOW)%-18s$(NC) %s\n", $$1, $$2}'
	@echo ""
	@echo "$(GREEN)Quick Start:$(NC)"
	@echo "  1. make build      # Build and start all containers"
	@echo "  2. make test       # Run all automated tests"
	@echo "  3. make clean      # Clean up (preserves logs)"
	@echo ""

build: ## Build and start all Docker containers for both labs
	@echo "$(BLUE)Building Forward Proxy Lab...$(NC)"
	@cd forward-proxy-lab && docker compose up -d
	@echo ""
	@echo "$(BLUE)Building Reverse Proxy Lab...$(NC)"
	@cd reverse-proxy-lab && docker compose up -d
	@echo ""
	@echo "$(GREEN)✓ All containers started successfully$(NC)"
	@echo ""
	@echo "Container status:"
	@docker ps --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}" | grep -E "NAME|forward-|squid-|allowed-|blocked-|internet-|nginx-|backend-"
	@echo ""
	@echo "$(YELLOW)Next step:$(NC) Run 'make test' to execute all tests"
	@echo ""

clean: ## Stop and remove all containers and networks (logs preserved)
	@echo "$(YELLOW)Stopping and removing containers...$(NC)"
	@cd forward-proxy-lab && docker compose down 2>/dev/null || true
	@cd reverse-proxy-lab && docker compose down 2>/dev/null || true
	@echo ""
	@echo "$(GREEN)✓ Cleanup complete$(NC)"
	@echo ""
	@echo "$(BLUE)Note:$(NC) Test logs are preserved in logs/ directory"
	@echo "      Docker volumes (proxy logs) are preserved"
	@echo ""
	@echo "To remove volumes as well: "
	@echo "  cd forward-proxy-lab && docker compose down -v"
	@echo "  cd reverse-proxy-lab && docker compose down -v"
	@echo ""

test: ## Run all automated tests (forward + reverse)
	@echo "$(BLUE)═══════════════════════════════════════════════════════$(NC)"
	@echo "$(BLUE)  Running All Tests$(NC)"
	@echo "$(BLUE)═══════════════════════════════════════════════════════$(NC)"
	@bash scripts/run-all-tests.sh

test-forward: ## Run forward proxy tests only
	@echo "$(BLUE)Running Forward Proxy Tests...$(NC)"
	@bash scripts/test-forward-proxy.sh

test-reverse: ## Run reverse proxy tests only
	@echo "$(BLUE)Running Reverse Proxy Tests...$(NC)"
	@bash scripts/test-reverse-proxy.sh

logs-forward: ## Display Squid forward proxy logs
	@echo "$(BLUE)═══════════════════════════════════════════════════════$(NC)"
	@echo "$(BLUE)  Squid Forward Proxy Logs$(NC)"
	@echo "$(BLUE)═══════════════════════════════════════════════════════$(NC)"
	@echo ""
	@docker logs squid-forward-proxy 2>&1 | tail -50
	@echo ""
	@echo "$(YELLOW)Tip:$(NC) Use 'docker logs -f squid-forward-proxy' to follow logs in real-time"
	@echo ""

logs-reverse: ## Display Nginx reverse proxy logs
	@echo "$(BLUE)═══════════════════════════════════════════════════════$(NC)"
	@echo "$(BLUE)  Nginx Reverse Proxy Logs$(NC)"
	@echo "$(BLUE)═══════════════════════════════════════════════════════$(NC)"
	@echo ""
	@docker logs nginx-reverse-proxy 2>&1 | tail -50
	@echo ""
	@echo "$(YELLOW)Tip:$(NC) Use 'docker logs -f nginx-reverse-proxy' to follow logs in real-time"
	@echo ""

status: ## Show status of all lab containers
	@echo "$(BLUE)Lab Container Status:$(NC)"
	@echo ""
	@docker ps -a --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}" | \
		grep -E "NAME|forward-|squid-|allowed-|blocked-|internet-|nginx-|backend-" || \
		echo "$(YELLOW)No lab containers found. Run 'make build' first.$(NC)"
	@echo ""

restart: ## Restart all lab containers
	@echo "$(YELLOW)Restarting all containers...$(NC)"
	@cd forward-proxy-lab && docker compose restart
	@cd reverse-proxy-lab && docker compose restart
	@echo "$(GREEN)✓ All containers restarted$(NC)"
	@echo ""

rebuild: clean build ## Clean and rebuild everything from scratch

shell-forward-client: ## Open shell in forward-client container
	@docker exec -it forward-client sh

shell-internet-client: ## Open shell in internet-client container
	@docker exec -it internet-client sh

validate: ## Validate configuration files (basic syntax check)
	@echo "$(BLUE)Validating configuration files...$(NC)"
	@echo ""
	@echo "Checking Squid configuration..."
	@docker compose -f forward-proxy-lab/docker-compose.yml config > /dev/null && \
		echo "$(GREEN)✓ forward-proxy-lab/docker-compose.yml is valid$(NC)" || \
		echo "$(RED)✗ forward-proxy-lab/docker-compose.yml has errors$(NC)"
	@echo ""
	@echo "Checking Nginx configuration..."
	@docker compose -f reverse-proxy-lab/docker-compose.yml config > /dev/null && \
		echo "$(GREEN)✓ reverse-proxy-lab/docker-compose.yml is valid$(NC)" || \
		echo "$(RED)✗ reverse-proxy-lab/docker-compose.yml has errors$(NC)"
	@echo ""

