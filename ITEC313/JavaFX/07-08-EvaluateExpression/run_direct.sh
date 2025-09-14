#!/bin/bash

# Direct Java Execution Script for EvaluateExpression
# This script runs the JavaFX application directly without Maven

echo "Starting EvaluateExpression JavaFX Application (Direct Java Execution)..."

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java 24 or later and try again"
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt "24" ]; then
    echo "Warning: Java version $JAVA_VERSION detected. Java 24 or later is recommended."
fi

# Set JavaFX modules path (adjust this path based on your JavaFX installation)
JAVAFX_PATH=""
# Homebrew path on Apple Silicon
if [ -d "/opt/homebrew/opt/openjfx/lib" ]; then
    JAVAFX_PATH="/opt/homebrew/opt/openjfx/lib"
# Homebrew path on Intel Macs
elif [ -d "/usr/local/opt/openjfx/lib" ]; then
    JAVAFX_PATH="/usr/local/opt/openjfx/lib"
# Custom SDK path example
elif [ -d "$HOME/javafx-sdks/javafx-sdk-21.0.8/lib" ]; then
    JAVAFX_PATH="$HOME/javafx-sdks/javafx-sdk-21.0.8/lib"
# Fallbacks
elif [ -d "/usr/local/openjfx/lib" ]; then
    JAVAFX_PATH="/usr/local/openjfx/lib"
elif [ -d "/opt/openjfx/lib" ]; then
    JAVAFX_PATH="/opt/openjfx/lib"
elif [ -d "$HOME/openjfx/lib" ]; then
    JAVAFX_PATH="$HOME/openjfx/lib"
else
    echo "Warning: JavaFX libraries not found in common locations"
    echo "You may need to install JavaFX or adjust the JAVAFX_PATH variable"
fi

# Compile the application
echo "Compiling application..."
mkdir -p target/classes
javac -cp ".:$JAVAFX_PATH/*" -d target/classes src/main/java/com/acu/javafx/evaluateexpression/*.java

if [ $? -ne 0 ]; then
    echo "Error: Compilation failed"
    exit 1
fi

# Run the application
echo "Launching application..."
if [ -n "$JAVAFX_PATH" ]; then
    java --module-path "$JAVAFX_PATH" \
         --add-modules javafx.controls,javafx.fxml \
         --enable-native-access=javafx.graphics \
         -cp "target/classes" \
         com.acu.javafx.evaluateexpression.EvaluateExpressionApp
else
    java -cp "target/classes" com.acu.javafx.evaluateexpression.EvaluateExpressionApp
fi

echo "Application finished." 