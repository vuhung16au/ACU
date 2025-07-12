#!/bin/bash

# JavaFX Shapes Demo Run Script
# This script compiles and runs the JavaFX application

echo "JavaFX Panes, UI Controls & Shapes Demo"
echo "======================================="

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    echo "Please install Maven to run this application"
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java 11 or higher"
    exit 1
fi

echo "Building the application..."
mvn clean compile -Djavafx.platform=mac-aarch64

if [ $? -eq 0 ]; then
    echo "Build successful! Starting the application..."
    # Run with correct platform for macOS Apple Silicon
    mvn javafx:run -Djavafx.platform=mac-aarch64
else
    echo "Build failed! Please check the error messages above."
    exit 1
fi
