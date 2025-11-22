#!/bin/bash

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo "=================================================="
echo "Network Security Lab - Environment Setup"
echo "=================================================="
echo ""

# Check if Docker is installed
echo -n "Checking Docker installation... "
if ! command -v docker &> /dev/null; then
    echo -e "${RED}FAILED${NC}"
    echo "Error: Docker is not installed. Please install Docker first."
    echo "Visit: https://docs.docker.com/get-docker/"
    exit 1
fi
echo -e "${GREEN}OK${NC}"

# Check if Docker Compose is installed
echo -n "Checking Docker Compose installation... "
if ! command -v docker-compose &> /dev/null && ! docker compose version &> /dev/null; then
    echo -e "${RED}FAILED${NC}"
    echo "Error: Docker Compose is not installed. Please install Docker Compose first."
    echo "Visit: https://docs.docker.com/compose/install/"
    exit 1
fi
echo -e "${GREEN}OK${NC}"

# Check if Docker daemon is running
echo -n "Checking Docker daemon... "
if ! docker info &> /dev/null; then
    echo -e "${RED}FAILED${NC}"
    echo "Error: Docker daemon is not running. Please start Docker."
    exit 1
fi
echo -e "${GREEN}OK${NC}"

echo ""
echo "Creating reports directory structure..."
mkdir -p reports

# Initialize report files with headers if they don't exist
if [ ! -f reports/commands.md ]; then
    cat > reports/commands.md << 'EOF'
# Network Reconnaissance - Command Documentation

This file contains all commands used in the network reconnaissance tasks.

---

EOF
fi

if [ ! -f reports/output.md ]; then
    cat > reports/output.md << 'EOF'
# Network Reconnaissance - Command Outputs

This file contains the raw outputs from all network reconnaissance tasks.

---

EOF
fi

if [ ! -f reports/results.md ]; then
    cat > reports/results.md << 'EOF'
# Network Reconnaissance - Results Summary

This file contains the summarized findings from all network reconnaissance tasks.

---

EOF
fi

if [ ! -f reports/reflection.md ]; then
    cat > reports/reflection.md << 'EOF'
# Reflection on Network Reconnaissance

## Instructions
Write 1-2 paragraphs reflecting on the importance of reconnaissance in penetration testing.
Consider the following aspects:
- How attackers use reconnaissance information
- How defenders can use the same information to secure systems
- The ethical implications of network scanning
- The importance of proper authorization before conducting security assessments

## Your Reflection

[Write your reflection here]

EOF
fi

echo -e "${GREEN}Reports directory created successfully${NC}"

echo ""
echo "Starting Docker containers..."
echo "This may take a few minutes if images need to be downloaded..."
echo ""

# Use docker compose or docker-compose based on what's available
if docker compose version &> /dev/null; then
    COMPOSE_CMD="docker compose"
else
    COMPOSE_CMD="docker-compose"
fi

# Start containers
$COMPOSE_CMD up -d

if [ $? -ne 0 ]; then
    echo -e "${RED}Failed to start containers${NC}"
    exit 1
fi

echo ""
echo "Waiting for containers to be ready..."
sleep 5

# Verify containers are running
echo ""
echo "Verifying container status..."
KALI_RUNNING=$(docker inspect -f '{{.State.Running}}' kali-linux 2>/dev/null)
META_RUNNING=$(docker inspect -f '{{.State.Running}}' metasploitable2 2>/dev/null)

if [ "$KALI_RUNNING" != "true" ]; then
    echo -e "${RED}Error: Kali Linux container is not running${NC}"
    exit 1
fi
echo -e "${GREEN}✓${NC} Kali Linux container is running"

if [ "$META_RUNNING" != "true" ]; then
    echo -e "${RED}Error: Metasploitable2 container is not running${NC}"
    exit 1
fi
echo -e "${GREEN}✓${NC} Metasploitable2 container is running"

# Get container IP addresses
KALI_IP=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' kali-linux)
META_IP=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' metasploitable2)

echo ""
echo "Container IP Addresses:"
echo "  Kali Linux:       $KALI_IP"
echo "  Metasploitable2:  $META_IP"

# Install nmap and iputils-ping in Kali if not present
echo ""
echo "Ensuring nmap and network utilities are installed in Kali Linux container..."
docker exec kali-linux bash -c "apt-get update -qq && apt-get install -y nmap iputils-ping > /dev/null 2>&1" &
INSTALL_PID=$!

# Show a spinner while installing
spin='-\|/'
i=0
while kill -0 $INSTALL_PID 2>/dev/null; do
  i=$(( (i+1) %4 ))
  printf "\r${spin:$i:1} Installing nmap..."
  sleep .1
done
wait $INSTALL_PID
INSTALL_RESULT=$?

if [ $INSTALL_RESULT -eq 0 ]; then
    echo -e "\r${GREEN}✓${NC} Nmap and network utilities installed successfully"
else
    echo -e "\r${YELLOW}!${NC} Installation may have issues, but continuing..."
fi

# Test connectivity between containers
echo ""
echo "Testing connectivity between containers..."
# Try ping first, fallback to nmap if ping not available
if docker exec kali-linux ping -c 2 $META_IP > /dev/null 2>&1; then
    echo -e "${GREEN}✓${NC} Connectivity test successful (ping)"
elif docker exec kali-linux nmap -sn -Pn $META_IP > /dev/null 2>&1; then
    echo -e "${GREEN}✓${NC} Connectivity test successful (nmap)"
else
    echo -e "${YELLOW}!${NC} Warning: Connectivity test inconclusive, but containers are running"
    echo "  You can verify manually: docker exec kali-linux nmap -sn $META_IP"
fi

echo ""
echo "=================================================="
echo -e "${GREEN}Setup completed successfully!${NC}"
echo "=================================================="
echo ""
echo "Next steps:"
echo "  1. Run all tasks:       ./tasks-run-all.sh"
echo "  2. Run individual task: ./task01-pingsweep.sh"
echo "  3. View results:        cat reports/results.md"
echo "  4. Cleanup:             ./cleanup.sh"
echo ""

