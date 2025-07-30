#!/bin/bash

# Prime Numbers Algorithms Demo - Direct Java Execution Script
# This script runs the JavaFX application directly without Maven
# Note: This requires JavaFX to be in the module path

set -e  # Exit on any error

echo "=== Prime Numbers Algorithms Demo (Direct Execution) ==="
echo "Running the JavaFX application directly..."
echo

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

echo "Java version: $(java -version 2>&1 | head -n 1)"
echo

# Check if target directory exists
if [ ! -d "target/classes" ]; then
    echo "Error: Classes not compiled. Please run 'mvn compile' first."
    exit 1
fi

# Run the application directly
echo "Starting JavaFX application..."
echo "=================================="

# For macOS, we need to specify the JavaFX modules
if [[ "$OSTYPE" == "darwin"* ]]; then
    java --module-path /opt/homebrew/opt/openjfx/libexec/lib/openjfx-jvm --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.base -cp target/classes com.acu.javafx.primenumbers.PrimeNumbersDemo
else
    # For Linux, try to find JavaFX modules
    JAVAFX_PATH=$(find /usr -name "javafx-sdk" 2>/dev/null | head -n 1)
    if [ -n "$JAVAFX_PATH" ]; then
        java --module-path "$JAVAFX_PATH/lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.base -cp target/classes com.acu.javafx.primenumbers.PrimeNumbersDemo
    else
        echo "Error: JavaFX modules not found. Please install JavaFX or use the Maven script instead."
        exit 1
    fi
fi

echo
echo "Application finished." 