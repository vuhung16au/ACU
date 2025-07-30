#!/bin/bash

# JavaFX Binary Search Demo - Run Script
# Cross-platform script for Unix/Linux/macOS

echo "JavaFX Binary Search Demo"
echo "========================"

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
echo "Building and running the application..."

# Clean and compile
echo "Cleaning previous build..."
mvn clean

# Compile and run
echo "Compiling and running..."
mvn javafx:run

echo ""
echo "Application finished." 