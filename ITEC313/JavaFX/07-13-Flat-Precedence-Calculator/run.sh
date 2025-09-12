#!/bin/bash

# Flat Precedence Calculator - Run Script for Unix/Linux/macOS
# This script compiles and runs the JavaFX application

echo "=========================================="
echo "Flat Precedence Calculator"
echo "=========================================="
echo ""

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    echo "Please install Maven and try again"
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java and try again"
    exit 1
fi

echo "Building and running the application..."
echo ""

# Clean, compile, and run the JavaFX application
mvn clean javafx:run

echo ""
echo "Application finished."
