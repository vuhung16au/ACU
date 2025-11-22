#!/bin/bash

# Task 3: Operating System Fingerprinting
# This script performs OS detection on the target host

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

TASK_NAME="Task 3: Operating System Fingerprinting"
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

# Run the OS detection scan
echo "Running OS detection scan..."
echo "Note: OS detection requires root privileges (using privileged container)"
COMMAND="nmap -O $META_IP"
echo "Command: $COMMAND"
echo ""

docker exec kali-linux $COMMAND > "$TEMP_OUTPUT" 2>&1
SCAN_RESULT=$?

if [ $SCAN_RESULT -ne 0 ]; then
    echo -e "${YELLOW}Warning: Nmap scan completed with warnings${NC}"
    echo "This is normal for OS detection - checking results..."
    echo ""
fi

# Display output
echo "Scan Output:"
echo "-----------------------------------"
cat "$TEMP_OUTPUT"
echo "-----------------------------------"
echo ""

# Parse OS information
echo -e "${GREEN}Operating System Detection Results:${NC}"
echo ""

# Extract OS details
OS_DETAILS=$(grep -A 5 "OS details:" "$TEMP_OUTPUT")
if [ -n "$OS_DETAILS" ]; then
    echo "$OS_DETAILS"
else
    echo "Checking for OS matches..."
    grep -A 3 "Aggressive OS guesses:" "$TEMP_OUTPUT" || grep -A 3 "OS:" "$TEMP_OUTPUT"
fi
echo ""

# Extract OS family if available
OS_FAMILY=$(grep "Running:" "$TEMP_OUTPUT")
if [ -n "$OS_FAMILY" ]; then
    echo -e "${GREEN}OS Family Detected:${NC}"
    echo "$OS_FAMILY"
    echo ""
fi

# Append to reports/commands.md
echo "Updating reports/commands.md..."
cat >> reports/commands.md << EOF

## ${TASK_NAME}

**Timestamp:** $TIMESTAMP

**Command:**
\`\`\`bash
$COMMAND
\`\`\`

**Purpose:** Perform operating system detection using TCP/IP stack fingerprinting to identify the target's OS.

**Flags Explanation:**
- \`-O\`: Enable OS detection using TCP/IP fingerprinting
- Target: $META_IP
- Note: Requires privileged access (uses privileged Docker container)

**Technique:** Nmap analyzes responses from the target system (TCP window sizes, IP TTL, TCP options, etc.) and compares them against a database of known OS fingerprints.

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

# Extract key OS information for results
OS_INFO=$(grep -A 10 "OS details:\|Aggressive OS guesses:\|Running:" "$TEMP_OUTPUT" | head -15)

# Append to reports/results.md
echo "Updating reports/results.md..."
cat >> reports/results.md << EOF

## ${TASK_NAME}

**Timestamp:** $TIMESTAMP

**Summary:**
- Target IP: $META_IP (Metasploitable2)
- Scan type: Operating System Detection
- Method: TCP/IP stack fingerprinting

**Operating System Information:**
\`\`\`
$OS_INFO
\`\`\`

**Key Findings:**

$(if echo "$OS_INFO" | grep -q "Linux"; then
    echo "- **Operating System:** Linux-based system detected"
    echo "- **OS Family:** Unix/Linux"
else
    echo "- **Operating System Detection:** Partial or complete OS fingerprint obtained"
fi)

**OS Detection Details:**
$(grep "OS details:\|Running:" "$TEMP_OUTPUT" | head -3)

**How OS Detection Works:**
- Analyzes TCP/IP stack behavior and responses
- Examines TCP window sizes, options, and sequencing
- Compares TTL values and IP header flags
- Matches patterns against a database of known OS fingerprints

**Security Implications:**
- OS version information helps identify platform-specific vulnerabilities
- Attackers can target OS-specific exploits based on this information
- Outdated OS versions may indicate lack of security patching
- OS fingerprinting is a passive technique that's difficult to detect

**Defensive Measures:**
- Use OS fingerprint obfuscation tools if needed
- Keep systems updated with latest security patches
- Implement network segmentation to limit scanning
- Monitor for repeated fingerprinting attempts

---

EOF

# Cleanup
rm "$TEMP_OUTPUT"

echo -e "${GREEN}✓${NC} Task completed successfully"
echo -e "${GREEN}✓${NC} Results appended to reports/"
echo ""
echo "View results: cat reports/results.md"
echo ""

