#!/bin/bash

# Flat Precedence Calculator - Run JAR Script for Unix/Linux/macOS
# This script builds the JAR file and runs it

echo "=========================================="
echo "Flat Precedence Calculator - JAR Version"
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

echo "Building the JAR file..."
echo ""

# Clean and package the application
mvn clean package

if [ $? -eq 0 ]; then
    echo ""
    echo "JAR file built successfully. Running the application..."
    echo ""
    
    # Run the JAR file
    java -jar target/flat-precedence-calculator-1.0.0.jar
else
    echo ""
    echo "Error: Failed to build the JAR file"
    exit 1
fi

echo ""
echo "Application finished."
