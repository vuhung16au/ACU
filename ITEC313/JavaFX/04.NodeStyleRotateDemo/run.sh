#!/bin/bash

# JavaFX Node Style Rotate Demo - Unix/Linux/macOS Execution Script
# This script builds and runs the JavaFX application using Maven

set -e  # Exit on any error

echo "=== JavaFX Node Style Rotate Demo - Build & Run ==="
echo

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    echo "Please install Maven 3.9.x or later"
    exit 1
fi

# Check Java version
echo "Checking Java version..."
java --version
echo

# Check Maven version
echo "Checking Maven version..."
mvn --version
echo

# Clean and compile the project
echo "Cleaning and compiling the project..."
mvn clean compile
echo

# Run the application
echo "Starting JavaFX application..."
echo "Note: This will run the enhanced demo version"
mvn javafx:run

echo
echo "Application finished."
