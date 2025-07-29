#!/bin/bash

# JavaFX Loan Calculator - Unix/Linux/macOS Execution Script
# This script builds and runs the JavaFX Loan Calculator application

set -e  # Exit on any error

echo "=== JavaFX Loan Calculator ==="
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
    echo "Please install Java 24 or later and try again"
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
echo "Starting the Loan Calculator application..."
mvn javafx:run

echo "Application finished." 