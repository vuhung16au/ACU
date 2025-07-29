#!/bin/bash

# ControlCircle - Direct JavaFX Application Runner
# This script runs the JavaFX application directly without Maven

set -e

echo "=== ControlCircle JavaFX Application (Direct Execution) ==="
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

echo ""
echo "Compiling and running the application..."
echo ""

# Compile the Java files
echo "Compiling Java source files..."
javac -cp ".:$(find ~/.m2/repository -name "*.jar" | tr '\n' ':')" \
    src/main/java/com/acu/javafx/controlcircle/*.java

# Run the application
echo "Running the application..."
java -cp ".:$(find ~/.m2/repository -name "*.jar" | tr '\n' ':')" \
    com.acu.javafx.controlcircle.ControlCircle

echo ""
echo "Application finished." 