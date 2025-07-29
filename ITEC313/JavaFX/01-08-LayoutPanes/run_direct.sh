#!/bin/bash

# JavaFX Layout Panes Demo - Direct Java Execution Script
# This script runs the JavaFX application directly without Maven

set -e

echo "JavaFX Layout Panes Demo - Direct Java Execution"
echo "================================================"

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
echo "Running the application directly..."
echo ""

# Set JavaFX module path (adjust path as needed for your system)
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
    echo "You may need to install JavaFX or adjust the JAVAFX_PATH variable"
    echo "Trying to run without explicit JavaFX module path..."
    
    # Try to run with classpath
    java -cp "target/classes" com.acu.javafx.layoutpanesdemo.Launcher
else
    echo "Using JavaFX path: $JAVAFX_PATH"
    
    # Run with JavaFX module path
    java --module-path "$JAVAFX_PATH" \
         --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.base \
         -cp "target/classes" \
         com.acu.javafx.layoutpanesdemo.Launcher
fi

echo ""
echo "Application finished." 