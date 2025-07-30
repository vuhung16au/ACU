#!/bin/bash

# JavaFX Sets and Maps Demo - Direct Java Execution Script
# This script runs the JavaFX application directly without Maven

set -e  # Exit on any error

echo "=== JavaFX Sets and Maps Demo (Direct Execution) ==="
echo "Running the application directly..."

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

# Set up classpath and module path for JavaFX
JAVAFX_PATH="$(find ~/.m2/repository -name 'javafx-*.jar' | head -1 | xargs dirname)"
if [ -z "$JAVAFX_PATH" ]; then
    echo "Error: JavaFX libraries not found. Please run 'mvn compile' first to download dependencies."
    exit 1
fi

# Build classpath
CLASSPATH="target/classes"
MODULE_PATH="$JAVAFX_PATH"

echo "Using JavaFX path: $JAVAFX_PATH"
echo "Running application..."

# Run the application
java --module-path "$MODULE_PATH" \
     --add-modules javafx.controls,javafx.fxml \
     -cp "$CLASSPATH" \
     com.acu.javafx.setsandmaps.SetsAndMapsDemo

echo "Application finished." 