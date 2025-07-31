#!/bin/bash

# Direct JavaFX Application Runner (bypasses Maven JavaFX plugin issues)
# This script compiles and runs the application directly with JavaFX modules

echo "========================================"
echo "Selection Sort Visualizer - Direct Run"
echo "========================================"

# Set JavaFX module path (adjust path as needed for your system)
JAVAFX_PATH="/usr/local/lib/javafx-21/lib"

# Check if JavaFX path exists, if not try common locations
if [ ! -d "$JAVAFX_PATH" ]; then
    # Try Homebrew location on macOS
    JAVAFX_PATH="/opt/homebrew/lib/javafx-21/lib"
fi

if [ ! -d "$JAVAFX_PATH" ]; then
    # Try standard macOS location
    JAVAFX_PATH="/usr/local/Cellar/openjfx/*/libexec/lib"
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    exit 1
fi

# Compile the project first
echo "Compiling project..."
mvn clean compile

if [ $? -ne 0 ]; then
    echo "Compilation failed!"
    exit 1
fi

# Run with JavaFX modules if path exists
if [ -d "$JAVAFX_PATH" ]; then
    echo "Running with JavaFX modules from: $JAVAFX_PATH"
    java --module-path "$JAVAFX_PATH" \
         --add-modules javafx.controls,javafx.fxml \
         -cp "target/classes" \
         com.acu.javafx.selectionsort.Launcher
else
    echo "JavaFX path not found, trying Maven JavaFX plugin..."
    mvn javafx:run
fi

echo "Application finished."
