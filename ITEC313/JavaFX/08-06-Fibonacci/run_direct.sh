#!/bin/bash

# JavaFX Fibonacci Demo - Direct Java Execution Script
# This script runs the JavaFX application directly without Maven
# Useful for quick testing when Maven is not available

set -e  # Exit on any error

echo "=== JavaFX Fibonacci Demo (Direct Execution) ==="
echo "Running the application directly..."

# Check if Java is available
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java 24 and try again"
    exit 1
fi

# Check if JavaFX modules are available
JAVAFX_PATH=""
if [ -d "/usr/share/openjfx/lib" ]; then
    JAVAFX_PATH="/usr/share/openjfx/lib"
elif [ -d "/opt/openjfx/lib" ]; then
    JAVAFX_PATH="/opt/openjfx/lib"
elif [ -d "/Library/Java/JavaVirtualMachines/openjfx-21.jdk/Contents/Home/lib" ]; then
    JAVAFX_PATH="/Library/Java/JavaVirtualMachines/openjfx-21.jdk/Contents/Home/lib"
fi

if [ -z "$JAVAFX_PATH" ]; then
    echo "Warning: JavaFX modules not found in standard locations"
    echo "You may need to install JavaFX separately or use Maven"
    echo "Trying to run without explicit JavaFX modules..."
    
    # Try to run with module path detection
    java --module-path . --add-modules javafx.controls,javafx.fxml \
         -cp "src/main/java" com.acu.javafx.fibonacci.FibonacciDemo
else
    echo "Found JavaFX at: $JAVAFX_PATH"
    
    # Run with explicit JavaFX modules
    java --module-path "$JAVAFX_PATH" --add-modules javafx.controls,javafx.fxml \
         -cp "src/main/java" com.acu.javafx.fibonacci.FibonacciDemo
fi

echo "Application finished." 