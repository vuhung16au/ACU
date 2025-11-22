#!/bin/bash

# Task 4: Advanced Port Scanning Techniques
# This script compares SYN stealth scan with TCP connect scan

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

TASK_NAME="Task 4: Advanced Port Scanning Techniques"
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

# Create temporary files for outputs
TEMP_SYN_OUTPUT=$(mktemp)
TEMP_TCP_OUTPUT=$(mktemp)

# Run SYN stealth scan
echo "==================================="
echo "Running SYN Stealth Scan (Half-Open)"
echo "==================================="
COMMAND_SYN="nmap -sS -T4 $META_IP"
echo "Command: $COMMAND_SYN"
echo ""

docker exec kali-linux $COMMAND_SYN > "$TEMP_SYN_OUTPUT" 2>&1
SYN_RESULT=$?

if [ $SYN_RESULT -ne 0 ]; then
    echo -e "${YELLOW}Warning: SYN scan completed with warnings${NC}"
fi

echo "SYN Scan Output:"
echo "-----------------------------------"
cat "$TEMP_SYN_OUTPUT"
echo "-----------------------------------"
echo ""

# Count open ports from SYN scan
SYN_OPEN_PORTS=$(grep -c "^[0-9]*.*open" "$TEMP_SYN_OUTPUT")
echo -e "${GREEN}SYN Scan - Open ports found: $SYN_OPEN_PORTS${NC}"
echo ""

# Short pause between scans
sleep 2

# Run TCP connect scan
echo "==================================="
echo "Running TCP Connect Scan (Full)"
echo "==================================="
COMMAND_TCP="nmap -sT -T4 $META_IP"
echo "Command: $COMMAND_TCP"
echo ""

docker exec kali-linux $COMMAND_TCP > "$TEMP_TCP_OUTPUT" 2>&1
TCP_RESULT=$?

if [ $TCP_RESULT -ne 0 ]; then
    echo -e "${YELLOW}Warning: TCP scan completed with warnings${NC}"
fi

echo "TCP Connect Scan Output:"
echo "-----------------------------------"
cat "$TEMP_TCP_OUTPUT"
echo "-----------------------------------"
echo ""

# Count open ports from TCP scan
TCP_OPEN_PORTS=$(grep -c "^[0-9]*.*open" "$TEMP_TCP_OUTPUT")
echo -e "${GREEN}TCP Connect Scan - Open ports found: $TCP_OPEN_PORTS${NC}"
echo ""

# Compare results
echo "==================================="
echo "Comparison of Scan Results"
echo "==================================="
echo ""
echo "SYN Scan (Stealth) - Open Ports: $SYN_OPEN_PORTS"
echo "TCP Scan (Full)    - Open Ports: $TCP_OPEN_PORTS"
echo ""

if [ "$SYN_OPEN_PORTS" -eq "$TCP_OPEN_PORTS" ]; then
    echo -e "${GREEN}✓${NC} Both scans found the same number of open ports"
else
    echo -e "${YELLOW}!${NC} Different number of ports found (this can happen due to timing or firewall behavior)"
fi
echo ""

# Append to reports/commands.md
echo "Updating reports/commands.md..."
cat >> reports/commands.md << EOF

## ${TASK_NAME}

**Timestamp:** $TIMESTAMP

### SYN Stealth Scan (Half-Open Scan)

**Command:**
\`\`\`bash
$COMMAND_SYN
\`\`\`

**Flags Explanation:**
- \`-sS\`: SYN stealth scan (half-open scan) - sends SYN packets and analyzes responses without completing TCP handshake
- \`-T4\`: Aggressive timing template for faster scanning
- Target: $META_IP

**How it works:** Sends SYN packet → receives SYN/ACK → sends RST (doesn't complete handshake)

### TCP Connect Scan (Full Connection)

**Command:**
\`\`\`bash
$COMMAND_TCP
\`\`\`

**Flags Explanation:**
- \`-sT\`: TCP connect scan - completes full TCP three-way handshake
- \`-T4\`: Aggressive timing template
- Target: $META_IP

**How it works:** Sends SYN → receives SYN/ACK → sends ACK → completes full connection → closes connection

---

EOF

# Append to reports/output.md
echo "Updating reports/output.md..."
cat >> reports/output.md << EOF

## ${TASK_NAME}

**Timestamp:** $TIMESTAMP

### SYN Stealth Scan Output

**Command:** \`$COMMAND_SYN\`

**Raw Output:**
\`\`\`
$(cat "$TEMP_SYN_OUTPUT")
\`\`\`

### TCP Connect Scan Output

**Command:** \`$COMMAND_TCP\`

**Raw Output:**
\`\`\`
$(cat "$TEMP_TCP_OUTPUT")
\`\`\`

---

EOF

# Create comparison table
echo "Updating reports/results.md..."
cat >> reports/results.md << EOF

## ${TASK_NAME}

**Timestamp:** $TIMESTAMP

**Summary:**
- Target IP: $META_IP (Metasploitable2)
- Two scan types performed: SYN Stealth (-sS) and TCP Connect (-sT)
- Timing: Aggressive (-T4)

### Scan Results Comparison

| Scan Type | Open Ports Found | Connection Method |
|-----------|------------------|-------------------|
| SYN Stealth | $SYN_OPEN_PORTS | Half-open (doesn't complete handshake) |
| TCP Connect | $TCP_OPEN_PORTS | Full connection (completes handshake) |

### SYN Stealth Scan Ports
\`\`\`
$(grep "^[0-9]*.*open" "$TEMP_SYN_OUTPUT" | head -20)
\`\`\`

### TCP Connect Scan Ports
\`\`\`
$(grep "^[0-9]*.*open" "$TEMP_TCP_OUTPUT" | head -20)
\`\`\`

### Key Differences Between Scan Types

**SYN Stealth Scan (-sS):**
- ✓ **Stealthier:** Doesn't complete TCP handshake, less likely to be logged
- ✓ **Faster:** Requires fewer packets (SYN → SYN/ACK → RST)
- ✓ **Lower footprint:** May evade simple IDS/IPS rules
- ✗ **Requires privileges:** Needs root/admin to craft raw packets
- ✗ **Not invisible:** Modern IDS can detect SYN scans
- Uses raw sockets to send crafted SYN packets
- Doesn't appear in application logs (only connection attempts)

**TCP Connect Scan (-sT):**
- ✓ **No privileges needed:** Uses standard OS socket API
- ✓ **More reliable:** Full connection ensures accurate results
- ✓ **Works everywhere:** No raw socket requirement
- ✗ **More detectable:** Full connections are always logged
- ✗ **Slower:** More packets required (full 3-way handshake + close)
- ✗ **Higher footprint:** Easy to detect and trace
- Creates complete connection records in logs
- Applications may log these connection attempts

### Why SYN Scanning is "Stealthier"

1. **Incomplete Handshake:** Never completes TCP connection, so some applications don't log it
2. **Fewer Packets:** Less network traffic = smaller detection footprint
3. **Faster Execution:** Quick RST after SYN/ACK reduces exposure time
4. **Historical Evasion:** Older firewalls and IDS systems didn't detect half-open connections

**Important Note:** Modern security systems (IDS/IPS, firewalls, SIEM) detect BOTH types:
- SYN floods and scan patterns are well-known attack signatures
- Connection logs capture both complete and incomplete handshakes
- Neither technique is truly "invisible" to modern defenses

### Security Implications

**For Attackers:**
- SYN scans can reduce detection probability against legacy systems
- Both methods reveal open ports and services
- Scan timing and patterns are often more important than scan type

**For Defenders:**
- Monitor for both SYN scan patterns and unusual connection attempts
- Use IDS/IPS to detect port scanning behavior
- Implement rate limiting and connection thresholds
- Log and analyze both successful and failed connection attempts
- Use tools like fail2ban or iptables recent module to block scanners

---

EOF

# Cleanup
rm "$TEMP_SYN_OUTPUT" "$TEMP_TCP_OUTPUT"

echo -e "${GREEN}✓${NC} Task completed successfully"
echo -e "${GREEN}✓${NC} Results appended to reports/"
echo ""
echo "View results: cat reports/results.md"
echo ""

