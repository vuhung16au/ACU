#!/bin/bash

# Heap Sort Visualization - Direct Java Execution Script
# This script runs the application directly with Java (requires compiled classes)

set -e  # Exit on any error

echo "=== Heap Sort Visualization (Direct Java Execution) ==="

# Check if target directory exists
if [ ! -d "target/classes" ]; then
    echo "Error: Compiled classes not found. Please run 'mvn compile' first."
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
echo "Java version: $JAVA_VERSION"

if [ "$JAVA_VERSION" -lt "24" ]; then
    echo "Warning: Java 24 or higher is recommended"
fi

# Set JavaFX module path (you may need to adjust this path)
# For macOS with Homebrew: /opt/homebrew/opt/openjfx/libexec/lib
# For Linux: /usr/share/openjfx/lib
# For Windows: C:\Program Files\Java\javafx-sdk-21\lib

# Detect OS and set module path
OS=$(uname -s | tr '[:upper:]' '[:lower:]')
if [[ "$OS" == "darwin" ]]; then
    # macOS
    JAVAFX_PATH="/opt/homebrew/opt/openjfx/libexec/lib"
    if [ ! -d "$JAVAFX_PATH" ]; then
        JAVAFX_PATH="/usr/local/opt/openjfx/libexec/lib"
    fi
elif [[ "$OS" == "linux" ]]; then
    # Linux
    JAVAFX_PATH="/usr/share/openjfx/lib"
else
    echo "Unsupported OS: $OS"
    exit 1
fi

if [ ! -d "$JAVAFX_PATH" ]; then
    echo "Warning: JavaFX not found at $JAVAFX_PATH"
    echo "Please install JavaFX or use Maven to run the application"
    exit 1
fi

echo "Using JavaFX from: $JAVAFX_PATH"

# Run the application
echo "Starting the application..."
java --module-path "$JAVAFX_PATH" \
     --add-modules javafx.controls,javafx.fxml \
     -cp target/classes \
     com.acu.javafx.heapsort.HeapSortDemo

echo "Application finished." 