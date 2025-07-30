#!/bin/bash

# JavaFX Performance Test Application - Unix/Linux/macOS Runner
# This script builds and runs the JavaFX application on Unix-like systems

set -e  # Exit on any error

echo "=== JavaFX Performance Test Application ==="
echo "Building and running on $(uname -s) $(uname -m)"
echo

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

# Display Java and Maven versions
echo "Java version:"
java -version
echo
echo "Maven version:"
mvn -version
echo

# Clean and compile
echo "Cleaning previous build..."
mvn clean

echo "Compiling project..."
mvn compile

echo "Running JavaFX application..."
echo "Note: The application will open in a new window."
echo

# Run the application using JavaFX Maven plugin
mvn javafx:run

echo
echo "Application completed." 