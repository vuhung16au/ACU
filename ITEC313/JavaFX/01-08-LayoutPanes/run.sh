#!/bin/bash

# JavaFX Layout Panes Demo - Unix/Linux/macOS Execution Script
# This script builds and runs the JavaFX application

set -e

echo "JavaFX Layout Panes Demo - Build and Run Script"
echo "================================================"

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    echo "Please install Maven and try again"
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java 24 and try again"
    exit 1
fi

# Display Java version
echo "Java version:"
java -version

# Display Maven version
echo "Maven version:"
mvn -version

echo ""
echo "Building and running the application..."
echo ""

# Clean and compile
echo "Cleaning previous build..."
mvn clean

echo "Compiling the application..."
mvn compile

echo "Running the application..."
mvn javafx:run

echo ""
echo "Application finished." 