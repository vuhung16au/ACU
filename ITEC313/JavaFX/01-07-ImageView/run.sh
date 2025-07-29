#!/bin/bash

# JavaFX Image and ImageView Demo - Run Script
# This script builds and runs the JavaFX application on Unix/Linux/macOS systems

echo "JavaFX Image and ImageView Demo"
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

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 24 ]; then
    echo "Warning: Java version $JAVA_VERSION detected. Java 24 or later is recommended."
fi

echo "Building project..."
mvn clean compile

if [ $? -eq 0 ]; then
    echo "Build successful. Running application..."
    echo ""
    mvn javafx:run
else
    echo "Build failed. Please check the error messages above."
    exit 1
fi 