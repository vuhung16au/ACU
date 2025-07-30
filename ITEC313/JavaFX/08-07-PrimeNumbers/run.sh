#!/bin/bash

# Prime Numbers Algorithms Demo - Unix/Linux/macOS Execution Script
# This script builds and runs the JavaFX application

set -e  # Exit on any error

echo "=== Prime Numbers Algorithms Demo ==="
echo "Building and running the JavaFX application..."
echo

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    echo "Please install Maven and try again"
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java 24 or later and try again"
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 24 ]; then
    echo "Warning: Java version $JAVA_VERSION detected. Java 24 or later is recommended."
fi

echo "Java version: $(java -version 2>&1 | head -n 1)"
echo "Maven version: $(mvn -version 2>&1 | head -n 1)"
echo

# Clean and compile
echo "Cleaning and compiling..."
mvn clean compile

# Run the application
echo "Starting JavaFX application..."
echo "=================================="
mvn javafx:run

echo
echo "Application finished." 