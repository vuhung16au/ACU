#!/bin/bash

# PriorityQueue JavaFX Demo - Direct Java Execution Script
# This script runs the application directly with Java (without Maven)

set -e

echo "=== PriorityQueue JavaFX Demo (Direct Java Execution) ==="
echo "Running the application directly..."

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java 24 and try again"
    exit 1
fi

# Display Java version
echo "Java version:"
java -version

# Check if the compiled classes exist
if [ ! -d "target/classes" ]; then
    echo "Error: Compiled classes not found. Please run 'mvn compile' first."
    exit 1
fi

# Set the module path for JavaFX (you may need to adjust this path)
# For macOS with Homebrew: /opt/homebrew/opt/openjfx/libexec/lib
# For Linux: /usr/share/openjfx/lib
# For Windows: C:\path\to\javafx-sdk\lib

JAVAFX_PATH=""
if [[ "$OSTYPE" == "darwin"* ]]; then
    # macOS
    if [ -d "/opt/homebrew/opt/openjfx/libexec/lib" ]; then
        JAVAFX_PATH="/opt/homebrew/opt/openjfx/libexec/lib"
    elif [ -d "/usr/local/opt/openjfx/libexec/lib" ]; then
        JAVAFX_PATH="/usr/local/opt/openjfx/libexec/lib"
    fi
elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
    # Linux
    if [ -d "/usr/share/openjfx/lib" ]; then
        JAVAFX_PATH="/usr/share/openjfx/lib"
    fi
fi

if [ -z "$JAVAFX_PATH" ]; then
    echo "Warning: JavaFX runtime not found in standard locations."
    echo "You may need to install JavaFX or specify the path manually."
    echo "For macOS: brew install openjfx"
    echo "For Linux: sudo apt-get install openjfx"
    echo "For Windows: Download from https://openjfx.io/"
    exit 1
fi

echo "Using JavaFX from: $JAVAFX_PATH"

# Run the application
echo "Starting JavaFX application..."
java --module-path "$JAVAFX_PATH" \
     --add-modules javafx.controls,javafx.fxml \
     -cp target/classes \
     com.acu.javafx.priorityqueue.PriorityQueueJavaFXApp

echo "Application finished." 