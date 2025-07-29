#!/bin/bash

# JavaFX Recursion Demonstrations - Unix/Linux/macOS Execution Script
# This script builds and runs the JavaFX application

set -e  # Exit on any error

echo "JavaFX Recursion Demonstrations"
echo "================================"

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
echo "Building project..."
mvn clean compile

echo ""
echo "Running JavaFX application..."
mvn javafx:run

echo ""
echo "Application finished." 