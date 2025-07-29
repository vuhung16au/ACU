#!/bin/bash

# JavaFX AnonymousHandlerDemo - Direct Run Script
# This script runs the JavaFX application directly without Maven

echo "JavaFX AnonymousHandlerDemo - Direct Execution"
echo "============================================="

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

# Check if target directory exists
if [ ! -d "target/classes" ]; then
    echo "Error: Classes not compiled. Please run 'mvn compile' first."
    exit 1
fi

echo "Starting JavaFX application directly..."
echo ""

# Run the application directly
java --module-path "$(find ~/.m2/repository/org/openjfx -name "javafx-*.jar" | head -1 | xargs dirname)" \
     --add-modules javafx.controls,javafx.fxml \
     -cp target/classes \
     com.acu.javafx.anonymoushandlerdemo.Launcher

echo ""
echo "Application finished." 