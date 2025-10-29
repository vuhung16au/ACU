#!/bin/bash

# Sudoku Solver - Run Script for Unix/Linux/macOS
# This script compiles and runs the Sudoku Solver application

echo "=== Sudoku Solver - JavaFX Application ==="
echo "Building and running the Sudoku Solver..."

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

# Run tests
echo "Running tests..."
mvn test

if [ $? -ne 0 ]; then
    echo "Warning: Some tests failed, but continuing..."
fi

# Run the application
echo "Starting Sudoku Solver application..."
mvn javafx:run

echo "Sudoku Solver application finished."
