#!/bin/bash

# Test Case 1: No Changes Detection
# This script tests that the IDS correctly reports no changes when files are unchanged

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(dirname "$SCRIPT_DIR")"

# Color codes
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo "======================================================================"
echo "Test Case 1: No Changes Detection"
echo "======================================================================"

# Clean up any existing test files
cd "$PROJECT_DIR"
rm -f baseline.txt results.txt

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

# Step 3: Immediately verify (no changes should be detected)
echo ""
echo "[Step 3] Verifying files (expecting no changes)..."
python3.9 "$PROJECT_DIR/ids.py" -o results.txt baseline.txt > /dev/null

if [ ! -f "results.txt" ]; then
    echo -e "${RED}✗ FAIL: Verification failed to create output file${NC}"
    exit 1
fi

# Step 4: Check results
echo ""
echo "[Step 4] Analyzing results..."

# Check if any changes were detected
if grep -q "CHANGED" results.txt || grep -q "DELETED" results.txt || grep -q "ADDED" results.txt; then
    echo -e "${RED}✗ FAIL: Unexpected changes detected!${NC}"
    echo ""
    echo "Results:"
    cat results.txt
    exit 1
fi

# Check for success message
if grep -q "No intrusions detected" results.txt; then
    echo -e "${GREEN}✓ No changes detected (as expected)${NC}"
    echo ""
    echo "======================================================================"
    echo -e "${GREEN}✓ PASS: No Changes Test${NC}"
    echo "======================================================================"
    exit 0
else
    echo -e "${RED}✗ FAIL: Expected 'No intrusions detected' message${NC}"
    echo ""
    echo "Results:"
    cat results.txt
    exit 1
fi

