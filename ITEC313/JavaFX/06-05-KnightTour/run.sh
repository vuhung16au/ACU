#!/bin/bash

# Knight's Tour Demo - Run Script for Unix/Linux/macOS
# This script compiles and runs the Knight's Tour JavaFX application

echo "=== Knight's Tour Demo ==="
echo "Compiling and running the Knight's Tour application..."
echo

# Check if Maven is available
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    echo "Please install Maven to run this application"
    exit 1
fi

# Check if Java is available
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java 24 or later to run this application"
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
echo "Starting Knight's Tour application..."
mvn javafx:run

echo
echo "Application finished."
