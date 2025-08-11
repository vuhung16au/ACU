#!/bin/bash

# Advanced JavaFX and FXML - Run Script
# This script compiles and runs the StyleSheetDemo application

echo "=== Advanced JavaFX and FXML - StyleSheetDemo ==="
echo "Building and running the application..."

# Check if Maven is available
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    echo "Please install Maven and try again"
    exit 1
fi

# Check if Java is available
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java 24 or higher and try again"
    exit 1
fi

# Clean and run with JavaFX Maven plugin
echo "Running with JavaFX Maven plugin..."
mvn clean javafx:run

echo "Application finished."
