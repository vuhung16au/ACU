#!/bin/bash

# ControlCircleWithoutEventHandling - JavaFX Application Runner
# This script runs the JavaFX application using Maven

set -e

echo "=== ControlCircleWithoutEventHandling JavaFX Application ==="
echo "Running the application..."

# Check if Maven is available
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    echo "Please install Maven and try again"
    exit 1
fi

# Check if Java is available
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java 24 and try again"
    exit 1
fi

# Display versions
echo "Maven version:"
mvn --version | head -1

echo "Java version:"
java -version

echo ""
echo "Building and running the application..."
echo ""

# Clean and run the application
mvn clean javafx:run

echo ""
echo "Application finished." 