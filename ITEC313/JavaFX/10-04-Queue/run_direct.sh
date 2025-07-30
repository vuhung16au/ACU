#!/bin/bash

# JavaFX Queue Demo - Direct Java Execution Script
# This script runs the JavaFX application directly without Maven
# Note: This requires JavaFX to be in the module path

set -e

echo "=== JavaFX Queue Demo (Direct Execution) ==="
echo "Running the application directly..."

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

# Compile the Java files
echo "Compiling Java files..."
mkdir -p target/classes
javac -d target/classes -cp "src/main/java" src/main/java/com/acu/javafx/queue/*.java

# Copy resources
echo "Copying resources..."
cp -r src/main/resources/* target/classes/ 2>/dev/null || true

# Run the application
echo "Starting JavaFX application..."
java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml,javafx.graphics -cp target/classes com.acu.javafx.queue.QueueDemo

echo "Application finished." 