#!/bin/bash

# Master script to run complete Quick Sort performance comparison
# This script executes all steps and generates comprehensive analysis

echo "=========================================="
echo "COMPLETE QUICK SORT PERFORMANCE STUDY"
echo "=========================================="
echo ""

# Get base directory
BASE_DIR=$(cd "$(dirname "$0")/.." && pwd)
SCRIPT_DIR="$(dirname "$0")"

# Make scripts executable
chmod +x "$SCRIPT_DIR/run_comparison.sh"

# Step 1: Run the performance comparison
echo "Phase 1: Running performance comparison..."
"$SCRIPT_DIR/run_comparison.sh"

if [ $? -ne 0 ]; then
    echo "Error: Performance comparison failed"
    exit 1
fi

echo ""
echo "Phase 2: Generating detailed analysis..."

# Step 2: Generate detailed analysis
python3 "$SCRIPT_DIR/analyze_results.py"

if [ $? -ne 0 ]; then
    echo "Error: Analysis generation failed"
    exit 1
fi

echo ""
echo "=========================================="
echo "STUDY COMPLETED SUCCESSFULLY"
echo "=========================================="
echo ""
echo "Generated files:"
echo "• random_list.txt - Test data (100,000 integers)"
echo "• results_*.txt - Individual language results"
echo "• consolidated_results.txt - Combined results"
echo "• performance_analysis.txt - Detailed analysis"
echo ""
echo "To view the complete analysis:"
echo "cat performance_analysis.txt"
echo ""
echo "To view the quick summary:"
echo "cat consolidated_results.txt"
