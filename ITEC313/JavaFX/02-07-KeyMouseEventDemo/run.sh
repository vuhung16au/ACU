#!/bin/bash

# JavaFX Key and Mouse Event Demo - Run Script
# This script builds and runs the JavaFX application on Unix/Linux/macOS systems

echo "JavaFX Key and Mouse Event Demo"
echo "================================"

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    echo "Please install Maven and try again"
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java 24 or later and try again"
    exit 1
fi

# Display Java version
echo "Java version:"
java -version
echo ""

# Clean and compile the project
echo "Building the project..."
mvn clean compile

if [ $? -ne 0 ]; then
    echo "Error: Build failed"
    exit 1
fi

# Run the application
echo "Running the application..."
mvn javafx:run

echo ""
echo "Application finished." 