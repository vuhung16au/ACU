#!/bin/bash

# Hilbert Curve Demo - Run JAR Script for Unix/Linux/macOS
# This script builds the JAR file and runs it

echo "Hilbert Curve Demo - Building and Running JAR"
echo "============================================="

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

echo "Building JAR file..."
echo ""

# Clean and package the application
mvn clean package

if [ $? -eq 0 ]; then
    echo ""
    echo "JAR built successfully. Running application..."
    echo ""
    
    # Run the JAR file
    java -jar target/hilbert-curve-demo-1.0.0.jar
else
    echo ""
    echo "Error: Failed to build JAR file"
    exit 1
fi

echo ""
echo "Application finished."
