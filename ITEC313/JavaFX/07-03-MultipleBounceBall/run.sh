#!/bin/bash

# Multiple Bounce Ball JavaFX Application Runner
# Cross-platform script for Unix/Linux/macOS

set -e

echo "=== Multiple Bounce Ball JavaFX Application ==="
echo "Platform: $(uname -s) $(uname -m)"
echo "Java Version: $(java -version 2>&1 | head -n 1)"
echo "Maven Version: $(mvn -version 2>&1 | head -n 1)"
echo ""

# Check if Java is available
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    exit 1
fi

# Check if Maven is available
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    exit 1
fi

# Clean and compile
echo "Building project..."
mvn clean compile

# Run the application
echo "Starting Multiple Bounce Ball application..."
mvn javafx:run

echo "Application finished." 