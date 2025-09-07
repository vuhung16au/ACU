#!/bin/bash

# Hilbert Curve Demo - Run Script for Unix/Linux/macOS
# This script compiles and runs the Hilbert Curve JavaFX application

echo "Hilbert Curve Demo - Starting Application"
echo "========================================"

# Check if Maven is available
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    echo "Please install Maven and try again"
    exit 1
fi

# Check if Java is available
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java 24 or later and try again"
    exit 1
fi

echo "Building and running Hilbert Curve Demo..."
echo ""

# Clean, compile, and run the application
mvn clean javafx:run

echo ""
echo "Application finished."
