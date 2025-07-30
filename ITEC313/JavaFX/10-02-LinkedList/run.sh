#!/bin/bash

# LinkedList Demo - Unix/Linux/macOS Execution Script
# This script builds and runs the JavaFX LinkedList Demo application

set -e  # Exit on any error

echo "=== LinkedList Demo - Build and Run Script ==="
echo "Platform: $(uname -s) $(uname -m)"
echo "Java Version: $(java -version 2>&1 | head -n 1)"
echo "Maven Version: $(mvn -version 2>&1 | head -n 1)"
echo ""

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Error: Java is not installed or not in PATH"
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Error: Maven is not installed or not in PATH"
    exit 1
fi

# Clean and compile
echo "🔨 Building project..."
mvn clean compile

# Run the application
echo "🚀 Running LinkedList Demo..."
mvn javafx:run

echo "✅ Application finished"
