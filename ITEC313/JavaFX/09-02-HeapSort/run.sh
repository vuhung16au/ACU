#!/bin/bash

# Heap Sort Visualization - Run Script for Unix/Linux/macOS
# This script builds and runs the JavaFX application

set -e  # Exit on any error

echo "=== Heap Sort Visualization ==="
echo "Building and running the application..."

# Detect OS and architecture
OS=$(uname -s | tr '[:upper:]' '[:lower:]')
ARCH=$(uname -m)

echo "Detected OS: $OS, Architecture: $ARCH"

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
echo "Java version: $JAVA_VERSION"

if [ "$JAVA_VERSION" -lt "24" ]; then
    echo "Warning: Java 24 or higher is recommended"
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    exit 1
fi

echo "Maven version: $(mvn -version | head -n 1)"

# Clean and compile
echo "Cleaning previous build..."
mvn clean

echo "Compiling the application..."
mvn compile

# Run the application
echo "Starting the application..."
mvn javafx:run

echo "Application finished." 