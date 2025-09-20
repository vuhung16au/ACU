#!/bin/bash

# Run script for Largest Block Demo
# This script compiles and runs the JavaFX application

echo "Starting Largest Block Demo..."

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

# Run the application
echo "Running Largest Block Demo with Maven..."
mvn clean javafx:run

echo "Application finished."
