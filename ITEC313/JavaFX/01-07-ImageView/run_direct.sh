#!/bin/bash

# JavaFX Image and ImageView Demo - Direct Run Script
# This script runs the JavaFX application directly without Maven
# Useful for quick testing when Maven is not available

echo "JavaFX Image and ImageView Demo - Direct Run"
echo "============================================="

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java 24 or later and try again"
    exit 1
fi

# Check if the compiled classes exist
if [ ! -d "target/classes" ]; then
    echo "Error: Compiled classes not found. Please run 'mvn compile' first."
    exit 1
fi

# Set JavaFX module path (adjust path as needed for your system)
# For macOS with Homebrew: /opt/homebrew/opt/openjfx/libexec/lib
# For Linux: /usr/share/openjfx/lib
# For Windows: C:\Program Files\Java\javafx-sdk-21\lib

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
    echo "Warning: JavaFX libraries not found in standard locations."
    echo "Please set JAVAFX_PATH environment variable to the JavaFX lib directory."
    echo "Example: export JAVAFX_PATH=/path/to/javafx/lib"
    exit 1
fi

echo "Using JavaFX path: $JAVAFX_PATH"
echo "Running application..."

java --module-path "$JAVAFX_PATH" \
     --add-modules javafx.controls,javafx.fxml,javafx.graphics \
     -cp "target/classes" \
     com.acu.javafx.imagedemo.Launcher 