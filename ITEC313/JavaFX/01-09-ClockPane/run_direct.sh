#!/bin/bash

# JavaFX ClockPane Demo - Direct Java Execution Script
# This script runs the application directly with Java without Maven

set -e

echo "=== JavaFX ClockPane Demo - Direct Execution ==="
echo "Running the application directly with Java..."

# Check if Java is available
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java 24 and try again"
    exit 1
fi

# Display Java version
echo "Java version:"
java -version

# Set JavaFX modules path (adjust as needed for your system)
JAVAFX_PATH=""
if [ -d "/usr/share/openjfx/lib" ]; then
    JAVAFX_PATH="/usr/share/openjfx/lib"
elif [ -d "/opt/openjfx/lib" ]; then
    JAVAFX_PATH="/opt/openjfx/lib"
elif [ -d "/Library/Java/JavaVirtualMachines/openjfx-21.jdk/Contents/Home/lib" ]; then
    JAVAFX_PATH="/Library/Java/JavaVirtualMachines/openjfx-21.jdk/Contents/Home/lib"
fi

if [ -z "$JAVAFX_PATH" ]; then
    echo "Warning: JavaFX libraries not found in common locations"
    echo "You may need to set JAVAFX_PATH manually"
    echo "Example: export JAVAFX_PATH=/path/to/javafx/lib"
fi

# Compile the source files
echo "Compiling source files..."
mkdir -p target/classes
javac -d target/classes \
    --add-modules javafx.controls,javafx.fxml \
    -cp "$JAVAFX_PATH/*" \
    src/main/java/com/acu/javafx/clockpanesdemo/*.java

# Run the application
echo "Running the application..."
java -cp "target/classes:$JAVAFX_PATH/*" \
    --add-modules javafx.controls,javafx.fxml \
    com.acu.javafx.clockpanesdemo.Launcher

echo "Application finished." 