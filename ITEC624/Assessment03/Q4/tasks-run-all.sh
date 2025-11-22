#!/bin/bash

# Master Script to Execute All Network Reconnaissance Tasks
# This script runs all five tasks sequentially

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
CYAN='\033[0;36m'
NC='\033[0m' # No Color

SCRIPT_START=$(date '+%Y-%m-%d %H:%M:%S')

echo "=================================================="
echo -e "${CYAN}Network Security Lab - Complete Assessment${NC}"
echo "=================================================="
echo "Starting comprehensive network reconnaissance..."
echo "Start time: $SCRIPT_START"
echo ""

# Check if containers are running
echo "Verifying environment setup..."
KALI_RUNNING=$(docker inspect -f '{{.State.Running}}' kali-linux 2>/dev/null)
META_RUNNING=$(docker inspect -f '{{.State.Running}}' metasploitable2 2>/dev/null)

if [ "$KALI_RUNNING" != "true" ] || [ "$META_RUNNING" != "true" ]; then
    echo -e "${RED}Error: Containers are not running${NC}"
    echo ""
    echo "Please run the setup first:"
    echo "  ./setup.sh"
    echo ""
    exit 1
fi

echo -e "${GREEN}✓${NC} Environment ready"
echo ""

# Make sure all task scripts are executable
chmod +x task01-pingsweep.sh task02-service-version.sh task03-os-detection.sh task04-port-scanning.sh task05-vulnerability-scan.sh 2>/dev/null

# Array of tasks
declare -a TASKS=(
    "task01-pingsweep.sh:Task 1 - Network Discovery and Host Enumeration"
    "task02-service-version.sh:Task 2 - Service Version Detection"
    "task03-os-detection.sh:Task 3 - Operating System Fingerprinting"
    "task04-port-scanning.sh:Task 4 - Advanced Port Scanning (SYN vs TCP)"
    "task05-vulnerability-scan.sh:Task 5 - Vulnerability Assessment with NSE"
)

# Initialize counters
TOTAL_TASKS=${#TASKS[@]}
COMPLETED_TASKS=0
FAILED_TASKS=0

# Log file for execution summary
LOG_FILE="reports/execution-summary-$(date '+%Y%m%d-%H%M%S').log"

echo "Execution Summary" > "$LOG_FILE"
echo "Start Time: $SCRIPT_START" >> "$LOG_FILE"
echo "Total Tasks: $TOTAL_TASKS" >> "$LOG_FILE"
echo "======================================" >> "$LOG_FILE"
echo "" >> "$LOG_FILE"

# Execute each task
for i in "${!TASKS[@]}"; do
    TASK_NUM=$((i + 1))
    IFS=':' read -r SCRIPT_NAME TASK_DESC <<< "${TASKS[$i]}"
    
    echo ""
    echo "=================================================="
    echo -e "${BLUE}[$TASK_NUM/$TOTAL_TASKS] $TASK_DESC${NC}"
    echo "=================================================="
    echo "Script: $SCRIPT_NAME"
    echo ""
    
    TASK_START=$(date '+%s')
    
    # Check if script exists
    if [ ! -f "$SCRIPT_NAME" ]; then
        echo -e "${RED}✗ Error: Script not found: $SCRIPT_NAME${NC}"
        echo "Task $TASK_NUM: FAILED - Script not found" >> "$LOG_FILE"
        FAILED_TASKS=$((FAILED_TASKS + 1))
        continue
    fi
    
    # Execute the task
    if bash "$SCRIPT_NAME"; then
        TASK_END=$(date '+%s')
        DURATION=$((TASK_END - TASK_START))
        echo ""
        echo -e "${GREEN}✓ Task $TASK_NUM completed successfully${NC}"
        echo -e "${GREEN}  Duration: ${DURATION}s${NC}"
        echo "Task $TASK_NUM: SUCCESS (${DURATION}s) - $TASK_DESC" >> "$LOG_FILE"
        COMPLETED_TASKS=$((COMPLETED_TASKS + 1))
    else
        TASK_END=$(date '+%s')
        DURATION=$((TASK_END - TASK_START))
        echo ""
        echo -e "${RED}✗ Task $TASK_NUM failed${NC}"
        echo -e "${RED}  Duration: ${DURATION}s${NC}"
        echo "Task $TASK_NUM: FAILED (${DURATION}s) - $TASK_DESC" >> "$LOG_FILE"
        FAILED_TASKS=$((FAILED_TASKS + 1))
        
        # Ask if user wants to continue
        echo ""
        read -p "Do you want to continue with remaining tasks? (y/n) [y]: " CONTINUE
        CONTINUE=${CONTINUE:-y}
        if [ "$CONTINUE" != "y" ] && [ "$CONTINUE" != "Y" ]; then
            echo "Aborting remaining tasks..."
            break
        fi
    fi
    
    # Small delay between tasks
    if [ $TASK_NUM -lt $TOTAL_TASKS ]; then
        echo ""
        echo -e "${YELLOW}Waiting 3 seconds before next task...${NC}"
        sleep 3
    fi
done

SCRIPT_END=$(date '+%Y-%m-%d %H:%M:%S')

# Calculate total duration
START_EPOCH=$(date -j -f "%Y-%m-%d %H:%M:%S" "$SCRIPT_START" "+%s" 2>/dev/null || date -d "$SCRIPT_START" "+%s" 2>/dev/null || echo "0")
END_EPOCH=$(date "+%s")
TOTAL_DURATION=$((END_EPOCH - START_EPOCH))

# Format duration
HOURS=$((TOTAL_DURATION / 3600))
MINUTES=$(((TOTAL_DURATION % 3600) / 60))
SECONDS=$((TOTAL_DURATION % 60))

# Print final summary
echo ""
echo "=================================================="
echo -e "${CYAN}Execution Summary${NC}"
echo "=================================================="
echo ""
echo "Total Tasks:      $TOTAL_TASKS"
echo -e "Completed:        ${GREEN}$COMPLETED_TASKS${NC}"
if [ $FAILED_TASKS -gt 0 ]; then
    echo -e "Failed:           ${RED}$FAILED_TASKS${NC}"
else
    echo -e "Failed:           ${GREEN}$FAILED_TASKS${NC}"
fi
echo ""
echo "Start Time:       $SCRIPT_START"
echo "End Time:         $SCRIPT_END"
echo "Total Duration:   ${HOURS}h ${MINUTES}m ${SECONDS}s"
echo ""

# Write summary to log
echo "" >> "$LOG_FILE"
echo "======================================" >> "$LOG_FILE"
echo "Execution Complete" >> "$LOG_FILE"
echo "End Time: $SCRIPT_END" >> "$LOG_FILE"
echo "Total Duration: ${HOURS}h ${MINUTES}m ${SECONDS}s" >> "$LOG_FILE"
echo "Completed: $COMPLETED_TASKS/$TOTAL_TASKS" >> "$LOG_FILE"
echo "Failed: $FAILED_TASKS/$TOTAL_TASKS" >> "$LOG_FILE"

if [ $COMPLETED_TASKS -eq $TOTAL_TASKS ]; then
    echo -e "${GREEN}✓ All tasks completed successfully!${NC}"
    echo ""
    echo "Results have been saved to the reports/ directory:"
    echo "  - reports/commands.md   (Command documentation)"
    echo "  - reports/output.md     (Raw command outputs)"
    echo "  - reports/results.md    (Analysis and findings)"
    echo "  - reports/reflection.md (Complete your reflection)"
    echo "  - $LOG_FILE"
    echo ""
    echo "Next steps:"
    echo "  1. Review the results: cat reports/results.md"
    echo "  2. Complete reflection: nano reports/reflection.md"
    echo "  3. View execution log: cat $LOG_FILE"
else
    echo -e "${YELLOW}⚠ Some tasks failed or were not completed${NC}"
    echo ""
    echo "Check the execution log for details:"
    echo "  cat $LOG_FILE"
    echo ""
    echo "You can run individual tasks to retry:"
    echo "  ./task01-pingsweep.sh"
    echo "  ./task02-service-version.sh"
    echo "  (etc.)"
fi

echo ""
echo "=================================================="
echo ""

# Exit with appropriate code
if [ $FAILED_TASKS -gt 0 ]; then
    exit 1
else
    exit 0
fi

