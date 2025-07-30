#!/bin/bash

# AVL Tree JavaFX Demo - Run Script
# This script runs the JavaFX application on Unix/Linux/macOS systems

echo "AVL Tree JavaFX Demo"
echo "===================="

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

# Get the directory of this script
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
cd "$SCRIPT_DIR"

echo "Building and running the application..."
echo ""

# Clean and compile
echo "Cleaning previous build..."
mvn clean

echo "Compiling and running..."
mvn javafx:run

echo ""
echo "Application finished." 