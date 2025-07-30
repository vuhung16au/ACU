#!/bin/bash

# Eight Queens JavaFX Application - Direct Java Execution Script
# This script runs the application directly with Java without Maven

set -e  # Exit on any error

echo "=== Eight Queens JavaFX Application (Direct Java Execution) ==="
echo "Running the application directly with Java..."

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java 24 and try again"
    exit 1
fi

# Display Java version
echo "Java version:"
java -version

# Set JavaFX modules path (adjust this path based on your JavaFX installation)
JAVAFX_PATH=""
if [ -d "/usr/share/openjfx/lib" ]; then
    JAVAFX_PATH="/usr/share/openjfx/lib"
elif [ -d "/opt/openjfx/lib" ]; then
    JAVAFX_PATH="/opt/openjfx/lib"
elif [ -d "/Library/Java/JavaVirtualMachines/openjfx-21.jdk/Contents/Home/lib" ]; then
    JAVAFX_PATH="/Library/Java/JavaVirtualMachines/openjfx-21.jdk/Contents/Home/lib"
else
    echo "Warning: JavaFX libraries not found in common locations"
    echo "You may need to specify the JavaFX path manually"
    echo "Please set JAVAFX_PATH environment variable or modify this script"
fi

# Compile the Java files
echo "Compiling Java files..."
mkdir -p target/classes
javac -cp "$JAVAFX_PATH/*" -d target/classes src/main/java/com/acu/javafx/eightqueens/*.java

# Run the application
echo "Running the Eight Queens application..."
java --module-path "$JAVAFX_PATH" --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.base -cp target/classes com.acu.javafx.eightqueens.EightQueens

echo "Application finished." 