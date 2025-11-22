#!/bin/bash

# Test Case 2: Intrusion Detection
# This script tests that the IDS correctly detects various types of changes

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(dirname "$SCRIPT_DIR")"

# Color codes
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo "======================================================================"
echo "Test Case 2: Intrusion Detection"
echo "======================================================================"

# Clean up any existing test files
cd "$PROJECT_DIR"
rm -f baseline.txt intrusion_report.txt

# Step 1: Setup test environment
echo ""
echo "[Step 1] Setting up test environment..."
bash "$SCRIPT_DIR/setup_test_env.sh" > /dev/null 2>&1

if [ ! -d "$PROJECT_DIR/test-folder" ]; then
    echo -e "${RED}✗ FAIL: Test environment setup failed${NC}"
    exit 1
fi

echo -e "${GREEN}✓ Test environment created${NC}"

# Step 2: Create baseline
echo ""
echo "[Step 2] Creating baseline verification file..."
python3.9 "$PROJECT_DIR/ids.py" -c baseline.txt > /dev/null 2>&1

if [ ! -f "baseline.txt" ]; then
    echo -e "${RED}✗ FAIL: Baseline creation failed${NC}"
    exit 1
fi

echo -e "${GREEN}✓ Baseline created${NC}"

# Step 3: Wait to ensure timestamp differences
echo ""
echo "[Step 3] Waiting 2 seconds to ensure timestamp differences..."
sleep 2

# Step 4: Simulate intrusions
echo ""
echo "[Step 4] Simulating intrusions..."

# Modify file content
echo "HACKED! This file has been compromised." >> "$PROJECT_DIR/test-folder/important.txt"
echo -e "${YELLOW}  - Modified: important.txt (content changed)${NC}"

# Change file permissions
chmod 777 "$PROJECT_DIR/test-folder/config.conf"
echo -e "${YELLOW}  - Modified: config.conf (permissions changed to 777)${NC}"

# Delete a file
rm -f "$PROJECT_DIR/test-folder/data.bin"
echo -e "${YELLOW}  - Deleted: data.bin${NC}"

# Modify file in subdirectory
echo "This secret has been read!" >> "$PROJECT_DIR/test-folder/documents/secret.txt"
echo -e "${YELLOW}  - Modified: documents/secret.txt (content changed)${NC}"

# Add a new file (potential backdoor)
echo "#!/bin/bash" > "$PROJECT_DIR/test-folder/backdoor.sh"
echo "# Malicious script" >> "$PROJECT_DIR/test-folder/backdoor.sh"
chmod +x "$PROJECT_DIR/test-folder/backdoor.sh"
echo -e "${YELLOW}  - Added: backdoor.sh (new file not in baseline)${NC}"

echo -e "${GREEN}✓ Intrusions simulated${NC}"

# Step 5: Run verification
echo ""
echo "[Step 5] Running verification..."
python3.9 "$PROJECT_DIR/ids.py" -o intrusion_report.txt baseline.txt > /dev/null

if [ ! -f "intrusion_report.txt" ]; then
    echo -e "${RED}✗ FAIL: Verification failed to create output file${NC}"
    exit 1
fi

# Step 6: Verify detections
echo ""
echo "[Step 6] Analyzing detection results..."

detected_count=0
failed_detections=()

# Check for modified important.txt
if grep -q "important.txt" intrusion_report.txt && grep -q "CHANGED" intrusion_report.txt; then
    echo -e "${GREEN}✓ Detected modification to important.txt${NC}"
    ((detected_count++))
else
    echo -e "${RED}✗ Failed to detect modification to important.txt${NC}"
    failed_detections+=("important.txt modification")
fi

# Check for permission change on config.conf
if grep -q "config.conf" intrusion_report.txt && grep -q "CHANGED" intrusion_report.txt; then
    echo -e "${GREEN}✓ Detected permission change on config.conf${NC}"
    ((detected_count++))
else
    echo -e "${RED}✗ Failed to detect permission change on config.conf${NC}"
    failed_detections+=("config.conf permission change")
fi

# Check for deleted data.bin
if grep -q "data.bin" intrusion_report.txt && grep -q "DELETED" intrusion_report.txt; then
    echo -e "${GREEN}✓ Detected deletion of data.bin${NC}"
    ((detected_count++))
else
    echo -e "${RED}✗ Failed to detect deletion of data.bin${NC}"
    failed_detections+=("data.bin deletion")
fi

# Check for modified secret.txt
if grep -q "secret.txt" intrusion_report.txt && grep -q "CHANGED" intrusion_report.txt; then
    echo -e "${GREEN}✓ Detected modification to documents/secret.txt${NC}"
    ((detected_count++))
else
    echo -e "${RED}✗ Failed to detect modification to documents/secret.txt${NC}"
    failed_detections+=("secret.txt modification")
fi

# Check for added backdoor.sh
if grep -q "backdoor.sh" intrusion_report.txt && grep -q "ADDED" intrusion_report.txt; then
    echo -e "${GREEN}✓ Detected new file backdoor.sh${NC}"
    ((detected_count++))
else
    echo -e "${RED}✗ Failed to detect new file backdoor.sh${NC}"
    failed_detections+=("backdoor.sh addition")
fi

# Check for intrusion detected message
if grep -q "INTRUSION DETECTED" intrusion_report.txt; then
    echo -e "${GREEN}✓ Intrusion alert generated${NC}"
    ((detected_count++))
else
    echo -e "${RED}✗ No intrusion alert in report${NC}"
    failed_detections+=("intrusion alert")
fi

# Final assessment
echo ""
echo "======================================================================"
if [ $detected_count -eq 6 ] && [ ${#failed_detections[@]} -eq 0 ]; then
    echo -e "${GREEN}✓ PASS: Intrusion Detection Test${NC}"
    echo "All 5 intrusions detected successfully + alert generated"
    echo "======================================================================"
    exit 0
else
    echo -e "${RED}✗ FAIL: Intrusion Detection Test${NC}"
    echo "Detected: $detected_count/6 expected items"
    if [ ${#failed_detections[@]} -gt 0 ]; then
        echo "Failed detections:"
        for failure in "${failed_detections[@]}"; do
            echo "  - $failure"
        done
    fi
    echo ""
    echo "Full report:"
    cat intrusion_report.txt
    echo "======================================================================"
    exit 1
fi

