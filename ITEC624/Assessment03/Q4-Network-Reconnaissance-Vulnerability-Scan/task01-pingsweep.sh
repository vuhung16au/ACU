#!/bin/bash

# Task 1: Network Discovery and Host Enumeration
# This script performs a ping sweep to identify active hosts on the network

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

TASK_NAME="Task 1: Network Discovery and Host Enumeration"
TIMESTAMP=$(date '+%Y-%m-%d %H:%M:%S')
SUBNET="172.20.0.0/24"

echo "=================================================="
echo -e "${BLUE}${TASK_NAME}${NC}"
echo "=================================================="
echo "Timestamp: $TIMESTAMP"
echo ""

# Check if containers are running
echo "Checking container status..."
KALI_RUNNING=$(docker inspect -f '{{.State.Running}}' kali-linux 2>/dev/null)
META_RUNNING=$(docker inspect -f '{{.State.Running}}' metasploitable2 2>/dev/null)

if [ "$KALI_RUNNING" != "true" ]; then
    echo -e "${RED}Error: Kali Linux container is not running${NC}"
    echo "Please run ./setup.sh first"
    exit 1
fi

if [ "$META_RUNNING" != "true" ]; then
    echo -e "${RED}Error: Metasploitable2 container is not running${NC}"
    echo "Please run ./setup.sh first"
    exit 1
fi

echo -e "${GREEN}✓${NC} Both containers are running"
echo ""

# Get Metasploitable 2 IP address
META_IP=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' metasploitable2)
echo "Target Network: $SUBNET"
echo "Metasploitable2 IP: $META_IP"
echo ""

# Create temporary file for output
TEMP_OUTPUT=$(mktemp)

# Run the ping sweep
echo "Running ping sweep (this may take a moment)..."
COMMAND="nmap -sn $SUBNET"
echo "Command: $COMMAND"
echo ""

docker exec kali-linux $COMMAND > "$TEMP_OUTPUT" 2>&1
SCAN_RESULT=$?

if [ $SCAN_RESULT -ne 0 ]; then
    echo -e "${RED}Error: Nmap scan failed${NC}"
    cat "$TEMP_OUTPUT"
    rm "$TEMP_OUTPUT"
    exit 1
fi

# Display output
echo "Scan Output:"
echo "-----------------------------------"
cat "$TEMP_OUTPUT"
echo "-----------------------------------"
echo ""

# Parse results
ACTIVE_HOSTS=$(grep "Nmap scan report for" "$TEMP_OUTPUT" | wc -l)
echo -e "${GREEN}Total active hosts discovered: $ACTIVE_HOSTS${NC}"
echo ""

# Extract IPs and response times
echo "Active Hosts Summary:"
grep -A 1 "Nmap scan report for" "$TEMP_OUTPUT" | while read -r line; do
    if [[ $line == *"Nmap scan report"* ]]; then
        echo "$line"
    elif [[ $line == *"Host is up"* ]]; then
        echo "  $line"
    fi
done
echo ""

# Append to reports/commands.md
echo "Updating reports/commands.md..."
cat >> reports/commands.md << EOF

## ${TASK_NAME}

**Timestamp:** $TIMESTAMP

**Command:**
\`\`\`bash
$COMMAND
\`\`\`

**Purpose:** Perform a ping sweep of the local network subnet to identify all active hosts without performing port scanning.

**Flags Explanation:**
- \`-sn\`: Ping scan - disable port scan, only do host discovery
- Network range: $SUBNET

**Target:** $SUBNET (Metasploitable2 at $META_IP)

---

EOF

# Append to reports/output.md
echo "Updating reports/output.md..."
cat >> reports/output.md << EOF

## ${TASK_NAME}

**Timestamp:** $TIMESTAMP

**Command:** \`$COMMAND\`

**Raw Output:**
\`\`\`
$(cat "$TEMP_OUTPUT")
\`\`\`

---

EOF

# Append to reports/results.md
echo "Updating reports/results.md..."
cat >> reports/results.md << EOF

## ${TASK_NAME}

**Timestamp:** $TIMESTAMP

**Summary:**
- Total active hosts discovered: $ACTIVE_HOSTS
- Network scanned: $SUBNET
- Target identified: $META_IP (Metasploitable2)

**Active Hosts:**
\`\`\`
$(grep -A 1 "Nmap scan report for" "$TEMP_OUTPUT")
\`\`\`

**Key Findings:**
- Successfully identified Metasploitable2 container at $META_IP
- Network discovery completed without port scanning (stealth host enumeration)
- Ping sweep reveals live systems on the network segment

**Response Times:**
$(grep "Host is up" "$TEMP_OUTPUT")

---

EOF

# Cleanup
rm "$TEMP_OUTPUT"

echo -e "${GREEN}✓${NC} Task completed successfully"
echo -e "${GREEN}✓${NC} Results appended to reports/"
echo ""
echo "View results: cat reports/results.md"
echo ""

