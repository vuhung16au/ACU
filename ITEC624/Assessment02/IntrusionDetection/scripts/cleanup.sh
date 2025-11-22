#!/bin/bash

# Cleanup Script
# Removes test artifacts and temporary files

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(dirname "$SCRIPT_DIR")"

cd "$PROJECT_DIR"

# Remove test folder
if [ -d "test-folder" ]; then
    rm -rf test-folder
    echo "Removed test-folder/"
fi

# Remove verification and output files
if [ -f "baseline.txt" ]; then
    rm -f baseline.txt
    echo "Removed baseline.txt"
fi

if [ -f "results.txt" ]; then
    rm -f results.txt
    echo "Removed results.txt"
fi

if [ -f "intrusion_report.txt" ]; then
    rm -f intrusion_report.txt
    echo "Removed intrusion_report.txt"
fi

# Remove any other .txt files (except README.md which is already excluded by extension)
# But be careful not to remove the README
for file in *.txt; do
    if [ -f "$file" ]; then
        rm -f "$file"
        echo "Removed $file"
    fi
done

# Remove Python cache
if [ -d "__pycache__" ]; then
    rm -rf __pycache__
    echo "Removed __pycache__/"
fi

# Remove any .pyc files
find . -name "*.pyc" -delete 2>/dev/null
find . -name "*.pyo" -delete 2>/dev/null

echo "Cleanup complete"

