#!/bin/bash

# Selection Sort Visualizer - JavaFX Application Runner
# This script runs the Selection Sort visualization application

echo "========================================"
echo "Selection Sort Visualizer - JavaFX"
echo "========================================"

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    echo "Please install Maven to run this application"
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java 11 or higher to run this application"
    exit 1
fi

echo "Compiling and running Selection Sort Visualizer..."
echo "This may take a moment on first run..."
echo ""

# Run the JavaFX application using Maven
mvn clean javafx:run

echo ""
echo "Application finished."
