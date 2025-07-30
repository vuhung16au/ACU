#!/bin/bash

# JavaFX Fibonacci Demo - Unix/Linux/macOS Execution Script
# This script builds and runs the JavaFX Fibonacci application

set -e  # Exit on any error

echo "=== JavaFX Fibonacci Demo ==="
echo "Building and running the application..."

# Check if Maven is available
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    echo "Please install Maven and try again"
    exit 1
fi

# Check if Java is available
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java 24 and try again"
    exit 1
fi

# Display Java version
echo "Java version:"
java -version

# Display Maven version
echo "Maven version:"
mvn -version

# Clean and compile
echo "Cleaning and compiling..."
mvn clean compile

# Run the application
echo "Starting JavaFX application..."
mvn javafx:run

echo "Application finished." 