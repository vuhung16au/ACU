#!/bin/bash

# JavaFX String Sorting Demo - Unix/Linux/macOS Execution Script
# This script builds and runs the JavaFX application

set -e

echo "JavaFX String Sorting Demo"
echo "=========================="

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

# Display Java version
echo "Java version:"
java -version
echo ""

# Display Maven version
echo "Maven version:"
mvn -version
echo ""

# Clean and compile
echo "Building project..."
mvn clean compile

# Run the application
echo "Running JavaFX application..."
mvn javafx:run

echo "Application finished." 