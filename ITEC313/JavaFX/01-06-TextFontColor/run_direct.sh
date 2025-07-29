#!/bin/bash

# JavaFX Text Font Color Demo - Direct Execution Script
# This script runs the JavaFX application directly without Maven
# Useful for development and testing

set -e  # Exit on any error

echo "JavaFX Text Font Color Demo - Direct Execution"
echo "=============================================="

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

# Check if JavaFX modules are available
if ! java --list-modules | grep -q "javafx"; then
    echo "Error: JavaFX modules not found in Java installation"
    echo "Please ensure you have JavaFX installed or use the Maven build script"
    exit 1
fi

echo "Running application directly..."
echo ""

# Compile the source files
echo "Compiling source files..."
javac -p "$(java --print-module-path)" --add-modules javafx.controls,javafx.fxml \
    src/main/java/module-info.java \
    src/main/java/com/example/*.java

# Run the application
echo "Running the application..."
java -p "$(java --print-module-path)" --add-modules javafx.controls,javafx.fxml \
    -cp src/main/java com.example.Launcher

echo ""
echo "Application finished." 