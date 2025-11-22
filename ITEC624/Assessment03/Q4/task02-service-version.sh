#!/bin/bash

# Task 2: Service Version Detection
# This script performs a service version scan on the target host

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

TASK_NAME="Task 2: Service Version Detection"
TIMESTAMP=$(date '+%Y-%m-%d %H:%M:%S')

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
echo "Target IP: $META_IP (Metasploitable2)"
echo ""

# Create temporary file for output
TEMP_OUTPUT=$(mktemp)

# Run the service version scan
echo "Running service version scan on top 1000 ports..."
echo "This will take a few minutes..."
COMMAND="nmap -sV -T4 $META_IP"
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

# Parse and highlight services
echo -e "${GREEN}Services Detected:${NC}"
echo ""
grep -E "^[0-9]+/(tcp|udp)" "$TEMP_OUTPUT" | head -10

TOTAL_SERVICES=$(grep -c -E "^[0-9]+/(tcp|udp)" "$TEMP_OUTPUT")
echo ""
echo -e "${GREEN}Total services detected: $TOTAL_SERVICES${NC}"
echo ""

# Highlight at least 3 interesting services
echo "Notable Services with Versions:"
echo "-----------------------------------"
grep -E "^[0-9]+/(tcp|udp)" "$TEMP_OUTPUT" | head -5
echo "-----------------------------------"
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

**Purpose:** Perform service version detection on the most common 1000 ports to identify running services and their version numbers.

**Flags Explanation:**
- \`-sV\`: Version detection - probe open ports to determine service/version info
- \`-T4\`: Timing template (0-5, higher is faster) - aggressive timing for faster scans
- Target: $META_IP

**Scan Duration:** Top 1000 most common ports

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

# Create detailed service list for results
SERVICE_LIST=$(grep -E "^[0-9]+/(tcp|udp)" "$TEMP_OUTPUT")

# Append to reports/results.md
echo "Updating reports/results.md..."
cat >> reports/results.md << EOF

## ${TASK_NAME}

**Timestamp:** $TIMESTAMP

**Summary:**
- Target IP: $META_IP (Metasploitable2)
- Total services detected: $TOTAL_SERVICES
- Scan type: Service version detection on top 1000 ports
- Timing: Aggressive (-T4)

**Services Detected:**
\`\`\`
$SERVICE_LIST
\`\`\`

**Key Findings:**

The scan revealed multiple services running on the target system. Notable findings include:

$(grep -E "^[0-9]+/(tcp|udp)" "$TEMP_OUTPUT" | head -3 | awk '{print "- **Port " $1 ":** " $3 " " $4 " " $5 " " $6}')

**Security Implications:**
- Multiple network services are exposed, increasing the attack surface
- Version information can be used to identify known vulnerabilities
- Older service versions may contain unpatched security flaws
- Service banners provide valuable information for attackers

**Recommendations:**
- Disable unnecessary services to reduce attack surface
- Update services to latest secure versions
- Implement network segmentation and access controls
- Monitor service logs for suspicious activity

---

EOF

# Cleanup
rm "$TEMP_OUTPUT"

echo -e "${GREEN}✓${NC} Task completed successfully"
echo -e "${GREEN}✓${NC} Results appended to reports/"
echo ""
echo "View results: cat reports/results.md"
echo ""

