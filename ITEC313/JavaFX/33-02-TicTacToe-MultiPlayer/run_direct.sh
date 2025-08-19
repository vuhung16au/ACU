#!/bin/bash

# Tic-Tac-Toe Game - Direct Java Execution Script
# This script runs the Tic-Tac-Toe game directly with Java (without Maven)

set -e  # Exit on any error

echo "=========================================="
echo "Tic-Tac-Toe Game - Direct Execution"
echo "=========================================="

# Configuration
MAIN_CLASS="com.example.Launcher"
JAVAFX_VERSION="21.0.2"

# Detect platform and architecture
OS=$(uname -s)
ARCH=$(uname -m)

case "$OS" in
    Darwin)
        if [ "$ARCH" = "arm64" ]; then
            PLATFORM="mac-aarch64"
        else
            PLATFORM="mac"
        fi
        ;;
    Linux)
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

# Check if compiled classes exist
if [ ! -d "target/classes" ]; then
    echo "Error: Compiled classes not found. Please run 'mvn compile' first."
    exit 1
fi

# Check if JavaFX modules are available in local repository
JAVAFX_MODULES_PATH="$HOME/.m2/repository/org/openjfx"

if [ ! -d "$JAVAFX_MODULES_PATH" ]; then
    echo "Error: JavaFX modules not found in local Maven repository."
    echo "Please run 'mvn compile' first to download dependencies."
    exit 1
fi

# Build classpath
CLASSPATH="target/classes"

# Add JavaFX modules to classpath
for module in javafx-base javafx-graphics javafx-controls javafx-fxml javafx-media javafx-web; do
    JAR_PATH="$JAVAFX_MODULES_PATH/$module/$JAVAFX_VERSION/$module-$JAVAFX_VERSION-$PLATFORM.jar"
    if [ -f "$JAR_PATH" ]; then
        CLASSPATH="$CLASSPATH:$JAR_PATH"
    else
        echo "Warning: JavaFX module not found: $JAR_PATH"
    fi
done

echo "Classpath configured"

# Run the application
echo "Starting application with direct Java execution..."
echo "Note: Close the application window to return to terminal"
echo ""

java --enable-preview \
     --module-path "$CLASSPATH" \
     --add-modules javafx.controls,javafx.fxml,javafx.media,javafx.web \
     -cp "$CLASSPATH" \
     "$MAIN_CLASS"
