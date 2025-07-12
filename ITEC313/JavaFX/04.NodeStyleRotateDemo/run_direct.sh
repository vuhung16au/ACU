#!/bin/bash

# Direct Java Execution Script (without Maven)
# This script runs the JavaFX application directly using java command

set -e  # Exit on any error

echo "=== JavaFX Direct Execution Script ==="
echo

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install OpenJDK 24 or later"
    exit 1
fi

# Check Java version
echo "Checking Java version..."
java --version
echo

# Check if compiled classes exist
if [ ! -d "target/classes" ]; then
    echo "Error: Compiled classes not found."
    echo "Please run 'mvn compile' first or use run.sh instead."
    exit 1
fi

# Determine platform for JavaFX modules
OS=$(uname -s)
ARCH=$(uname -m)

case "$OS" in
    "Darwin")
        if [ "$ARCH" = "arm64" ]; then
            PLATFORM="mac-aarch64"
        else
            PLATFORM="mac"
        fi
        ;;
    "Linux")
        if [ "$ARCH" = "aarch64" ]; then
            PLATFORM="linux-aarch64"
        else
            PLATFORM="linux"
        fi
        ;;
    *)
        echo "Unsupported operating system: $OS"
        exit 1
        ;;
esac

echo "Detected platform: $PLATFORM"

# Find JavaFX runtime libraries
JAVAFX_LIB=""
if [ -d "$HOME/.m2/repository/org/openjfx" ]; then
    JAVAFX_LIB="$HOME/.m2/repository/org/openjfx"
else
    echo "Warning: JavaFX libraries not found in local Maven repository"
    echo "Please run 'mvn compile' first to download dependencies"
    exit 1
fi

# Build module path
MODULE_PATH="$JAVAFX_LIB/javafx-controls/21/javafx-controls-21-$PLATFORM.jar"
MODULE_PATH="$MODULE_PATH:$JAVAFX_LIB/javafx-fxml/21/javafx-fxml-21-$PLATFORM.jar"
MODULE_PATH="$MODULE_PATH:$JAVAFX_LIB/javafx-base/21/javafx-base-21-$PLATFORM.jar"
MODULE_PATH="$MODULE_PATH:$JAVAFX_LIB/javafx-graphics/21/javafx-graphics-21-$PLATFORM.jar"

# Run the application
echo "Starting JavaFX application (direct execution)..."
java --enable-preview \
     --module-path "$MODULE_PATH" \
     --add-modules javafx.controls,javafx.fxml \
     -cp target/classes \
     com.example.Launcher

echo
echo "Application finished."
