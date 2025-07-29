#!/bin/bash

# JavaFX DisplayResizableClock - Unix/Linux/macOS Execution Script
# This script builds and runs the JavaFX application

set -e

echo "=== JavaFX DisplayResizableClock ==="
echo "Building and running the application..."

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    echo "Please install Maven and try again"
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java 24 and try again"
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 24 ]; then
    echo "Warning: Java version $JAVA_VERSION detected. Java 24+ is recommended."
fi

# Clean and compile
echo "Cleaning and compiling..."
mvn clean compile

# Run the application
echo "Starting the application..."
mvn javafx:run

echo "Application finished." 