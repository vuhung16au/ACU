#!/bin/bash

# JavaFX HandleEvent Demo - Direct Java Execution Script
# This script runs the application directly without Maven

set -e

echo "=== JavaFX HandleEvent Demo - Direct Execution ==="
echo "Running the application directly..."

# Check if Java is available
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java 24 and try again"
    exit 1
fi

# Display Java version
echo "Java version:"
java -version

# Check if target directory exists
if [ ! -d "target/classes" ]; then
    echo "Error: Classes not compiled. Please run 'mvn compile' first."
    exit 1
fi

# Try to find JavaFX modules
JAVAFX_PATH=""

# Common JavaFX installation paths
POSSIBLE_PATHS=(
    "/usr/share/openjfx/lib"
    "/usr/lib/jvm/java-24-openjdk/lib"
    "/Library/Java/JavaVirtualMachines/openjdk-24.jdk/Contents/Home/lib"
    "/opt/homebrew/opt/openjfx/libexec/lib"
    "/usr/local/opt/openjfx/libexec/lib"
)

for path in "${POSSIBLE_PATHS[@]}"; do
    if [ -d "$path" ]; then
        JAVAFX_PATH="$path"
        echo "Found JavaFX at: $JAVAFX_PATH"
        break
    fi
done

if [ -z "$JAVAFX_PATH" ]; then
    echo "Warning: JavaFX modules not found in common locations."
    echo "You may need to install JavaFX or specify the path manually."
    echo "Common installation commands:"
    echo "  Ubuntu/Debian: sudo apt install openjfx"
    echo "  macOS: brew install openjfx"
    echo "  Windows: Download from https://openjfx.io/"
    echo ""
    echo "Trying to run anyway..."
fi

# Run the application
if [ -n "$JAVAFX_PATH" ]; then
    java --module-path "$JAVAFX_PATH" \
         --add-modules javafx.controls,javafx.fxml \
         -cp "target/classes" \
         com.acu.javafx.handleeventdemo.Launcher
else
    echo "Attempting to run without explicit JavaFX path..."
    java -cp "target/classes" \
         com.acu.javafx.handleeventdemo.Launcher
fi

echo "Application finished." 