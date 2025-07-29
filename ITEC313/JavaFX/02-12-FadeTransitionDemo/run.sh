#!/bin/bash

# JavaFX FadeTransition Demo - Unix/Linux/macOS Execution Script
# This script builds and runs the JavaFX application

set -e  # Exit on any error

echo "=== JavaFX FadeTransition Demo ==="
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

# Clean and compile
echo "Cleaning previous build..."
mvn clean

echo "Compiling and running..."
mvn javafx:run

echo "Application completed." 