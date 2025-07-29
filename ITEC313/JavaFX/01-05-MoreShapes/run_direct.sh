#!/bin/bash

# JavaFX More Shapes Demo - Direct Java Execution Script
# This script runs the application directly without Maven (for testing)

set -e  # Exit on any error

echo "JavaFX More Shapes Demo - Direct Execution"
echo "========================================="

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

echo "Compiling and running directly..."
echo ""

# Create target directory if it doesn't exist
mkdir -p target/classes

# Compile Java files
echo "Compiling Java source files..."
javac -d target/classes \
    --module-path /path/to/javafx-sdk/lib \
    --add-modules javafx.controls,javafx.fxml,javafx.graphics \
    src/main/java/module-info.java \
    src/main/java/com/example/*.java

# Run the application
echo "Running the application..."
java --module-path /path/to/javafx-sdk/lib \
    --add-modules javafx.controls,javafx.fxml,javafx.graphics \
    --module com.example.moreshapes/com.example.Launcher

echo ""
echo "Application finished." 