#!/bin/bash

# JavaFX Generics Demo - Unix/Linux/macOS Execution Script
# This script builds and runs the JavaFX generics demonstration application

echo "JavaFX Generics Demo - Build and Run Script"
echo "============================================="

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
    echo "Build successful!"
    echo "Starting JavaFX application..."
    mvn javafx:run
else
    echo "Build failed!"
    exit 1
fi 