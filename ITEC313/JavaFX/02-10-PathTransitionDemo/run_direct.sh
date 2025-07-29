#!/bin/bash

# JavaFX PathTransition Demo - Direct Java Execution Script
# This script runs the application directly with Java (without Maven)

set -e  # Exit on any error

echo "=========================================="
echo "JavaFX PathTransition Demo - Direct Execution"
echo "=========================================="

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java 24 and try again"
    exit 1
fi

# Display Java version
echo "Java version:"
java -version
echo ""

# Set up classpath and modules
JAVAFX_MODULES="javafx.controls,javafx.fxml,javafx.graphics"
MAIN_CLASS="com.acu.javafx.pathtransitiondemo.PathTransitionDemo"

# Try to find JavaFX modules in common locations
JAVAFX_PATH=""
for path in "/usr/share/openjfx/lib" "/opt/openjfx/lib" "$HOME/.m2/repository/org/openjfx" "/Library/Java/JavaVirtualMachines/openjfx-21.jdk/Contents/Home/lib"; do
    if [ -d "$path" ]; then
        JAVAFX_PATH="$path"
        break
    fi
done

if [ -z "$JAVAFX_PATH" ]; then
    echo "Warning: JavaFX modules not found in common locations"
    echo "You may need to install JavaFX separately or use Maven"
    echo "Trying to run with system JavaFX modules..."
fi

# Run the application
echo "Running JavaFX PathTransition Demo directly..."
if [ -n "$JAVAFX_PATH" ]; then
    java --module-path "$JAVAFX_PATH" --add-modules "$JAVAFX_MODULES" -cp "target/classes" "$MAIN_CLASS"
else
    java --add-modules "$JAVAFX_MODULES" -cp "target/classes" "$MAIN_CLASS"
fi

echo "=========================================="
echo "Application finished"
echo "==========================================" 