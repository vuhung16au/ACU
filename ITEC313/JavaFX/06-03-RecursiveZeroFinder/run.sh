#!/bin/bash

# Recursive Zero Finder - Run Script for Unix/Linux/macOS
# This script compiles and runs the JavaFX application

echo "=== Recursive Zero Finder - JavaFX Demo ==="
echo "Compiling and running the application..."
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

# Clean and compile
echo "Cleaning and compiling..."
mvn clean compile

if [ $? -ne 0 ]; then
    echo "Error: Compilation failed"
    exit 1
fi

echo "Compilation successful!"
echo

# Run the application
echo "Starting the JavaFX application..."
mvn javafx:run

echo
echo "Application finished."
