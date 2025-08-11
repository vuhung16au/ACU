#!/bin/bash

# Advanced JavaFX and FXML - JAR Run Script
# This script builds the executable JAR and runs it

echo "=== Advanced JavaFX and FXML - Building and Running JAR ==="

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

# Build the project
echo "Building the project..."
mvn clean package

if [ $? -ne 0 ]; then
    echo "Error: Build failed"
    exit 1
fi

# Check if JAR file exists
JAR_FILE="target/advanced-javafx-fxml-1.0.0.jar"
if [ ! -f "$JAR_FILE" ]; then
    echo "Error: JAR file not found at $JAR_FILE"
    exit 1
fi

echo "Running the JAR file..."
java -jar "$JAR_FILE"

echo "Application finished."
