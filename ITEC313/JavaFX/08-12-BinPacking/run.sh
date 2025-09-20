#!/bin/bash

# Bin Packing Demo - Run Script
# This script compiles and runs the Bin Packing Problem Solver

echo "=== Bin Packing Problem Solver ==="
echo "Compiling and running the application..."
echo

# Change to the project directory
cd "$(dirname "$0")"

# Clean and compile
echo "Cleaning and compiling..."
mvn clean compile

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo
    echo "Running tests..."
    mvn test
    
    if [ $? -eq 0 ]; then
        echo "All tests passed!"
        echo
        echo "Starting JavaFX application..."
        mvn javafx:run
    else
        echo "Tests failed. Please check the output above."
        exit 1
    fi
else
    echo "Compilation failed. Please check the errors above."
    exit 1
fi
