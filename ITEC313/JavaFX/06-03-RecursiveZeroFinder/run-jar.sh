#!/bin/bash

# Recursive Zero Finder - JAR Run Script for Unix/Linux/macOS
# This script builds and runs the executable JAR

echo "=== Recursive Zero Finder - JAR Demo ==="
echo "Building and running the executable JAR..."
echo

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

# Clean and package
echo "Cleaning and packaging..."
mvn clean package

if [ $? -ne 0 ]; then
    echo "Error: Packaging failed"
    exit 1
fi

echo "Packaging successful!"
echo

# Run the JAR
echo "Starting the application from JAR..."
java -jar target/recursive-zero-finder-1.0.0.jar

echo
echo "Application finished."
