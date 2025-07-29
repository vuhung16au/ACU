#!/bin/bash

# JavaFX Loan Calculator - Direct Java Execution Script
# This script runs the JavaFX application directly without Maven
# Useful for development and testing

set -e  # Exit on any error

echo "=== JavaFX Loan Calculator - Direct Execution ==="
echo "Running the application directly with Java..."

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java 24 or later and try again"
    exit 1
fi

# Display Java version
echo "Java version:"
java -version

# Set the classpath to include compiled classes
CLASSPATH="target/classes"

# Check if classes are compiled
if [ ! -d "target/classes" ]; then
    echo "Error: Classes not compiled. Please run 'mvn compile' first."
    exit 1
fi

# Run the application directly
echo "Starting the Loan Calculator application..."
java --module-path "$CLASSPATH" \
     --add-modules javafx.controls,javafx.fxml \
     --add-opens javafx.graphics/javafx.scene=ALL-UNNAMED \
     com.acu.javafx.loancalculator.Launcher

echo "Application finished." 