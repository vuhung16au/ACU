#!/bin/bash

# JavaFX AnonymousHandlerDemo - Run Script
# This script runs the JavaFX application using Maven

echo "JavaFX AnonymousHandlerDemo"
echo "==========================="

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
if [ "$JAVA_VERSION" -lt "24" ]; then
    echo "Warning: Java version $JAVA_VERSION detected. Java 24 or later is recommended."
fi

echo "Starting JavaFX application..."
echo ""

# Run the application using Maven
mvn clean javafx:run -Djavafx.mainClass=com.acu.javafx.anonymoushandlerdemo.Launcher

echo ""
echo "Application finished." 