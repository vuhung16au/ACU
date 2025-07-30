#!/bin/bash

# JavaFX Probing Demo - Unix/Linux/macOS Execution Script
# This script builds and runs the JavaFX application

set -e

echo "=========================================="
echo "JavaFX Probing Techniques Demo"
echo "=========================================="

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    exit 1
fi

echo "Java version:"
java -version

echo "Maven version:"
mvn -version

echo ""
echo "Building the project..."
mvn clean compile

echo ""
echo "Running the JavaFX application..."
mvn javafx:run

echo ""
echo "Application finished." 