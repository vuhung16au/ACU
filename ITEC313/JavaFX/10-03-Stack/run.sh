#!/bin/bash

# Stack Demo Application Runner Script
# For Unix/Linux/macOS systems

echo "Building and running Stack Demo Application..."

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

# Clean and compile
echo "Cleaning and compiling..."
mvn clean compile

if [ $? -ne 0 ]; then
    echo "Error: Compilation failed"
    exit 1
fi

# Run the application
echo "Starting Stack Demo Application..."
mvn javafx:run

echo "Application finished." 