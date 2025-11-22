#!/bin/bash

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo "=================================================="
echo "Network Security Lab - Cleanup"
echo "=================================================="
echo ""

# Check which docker compose command is available
if docker compose version &> /dev/null; then
    COMPOSE_CMD="docker compose"
else
    COMPOSE_CMD="docker-compose"
fi

# Ask about reports
echo "Do you want to preserve the reports directory? (y/n)"
read -p "Choice [y]: " PRESERVE_REPORTS
PRESERVE_REPORTS=${PRESERVE_REPORTS:-y}

echo ""
echo "Stopping and removing containers..."
$COMPOSE_CMD down

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓${NC} Containers stopped and removed successfully"
else
    echo -e "${RED}✗${NC} Failed to stop containers"
    exit 1
fi

# Remove network if it still exists
echo ""
echo "Cleaning up Docker network..."
docker network rm q4_pentest-net 2>/dev/null
if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓${NC} Network removed"
else
    echo -e "${YELLOW}!${NC} Network already removed or doesn't exist"
fi

# Handle reports directory
if [ "$PRESERVE_REPORTS" != "y" ] && [ "$PRESERVE_REPORTS" != "Y" ]; then
    echo ""
    echo "Removing reports directory..."
    rm -rf reports
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✓${NC} Reports directory removed"
    else
        echo -e "${RED}✗${NC} Failed to remove reports directory"
    fi
else
    echo ""
    echo -e "${GREEN}✓${NC} Reports directory preserved"
fi

echo ""
echo "=================================================="
echo -e "${GREEN}Cleanup completed!${NC}"
echo "=================================================="
echo ""

if [ "$PRESERVE_REPORTS" = "y" ] || [ "$PRESERVE_REPORTS" = "Y" ]; then
    echo "Your scan results are saved in the reports/ directory"
    echo ""
fi

echo "To start the lab again, run: ./setup.sh"
echo ""

