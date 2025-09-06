#!/bin/bash

# Koch Snowflake Fractal Demo - Run Script for Unix/Linux/macOS
# This script compiles and runs the JavaFX application

echo "Koch Snowflake Fractal Demo"
echo "=========================="
echo ""

# Check if Maven is available
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    echo "Please install Maven and try again"
    exit 1
fi

# Check if Java is available
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java and try again"
    exit 1
fi

echo "Building and running the Koch Snowflake Fractal Demo..."
echo ""

# Clean, compile, and run
mvn clean javafx:run

echo ""
echo "Application finished."
