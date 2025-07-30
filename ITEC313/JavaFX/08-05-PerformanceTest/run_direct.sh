#!/bin/bash

# JavaFX Performance Test Application - Direct Java Runner
# This script runs the application directly with Java (without Maven)

set -e  # Exit on any error

echo "=== JavaFX Performance Test Application (Direct Java) ==="
echo "Running on $(uname -s) $(uname -m)"
echo

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    exit 1
fi

# Display Java version
echo "Java version:"
java -version
echo

# Check if the compiled classes exist
if [ ! -d "target/classes" ]; then
    echo "Error: Compiled classes not found. Please run 'mvn compile' first."
    exit 1
fi

echo "Running JavaFX application directly..."
echo "Note: The application will open in a new window."
echo

# Run the application directly with Java
# Note: This requires JavaFX modules to be available
java --module-path "$(find ~/.m2/repository/org/openjfx -name "*.jar" | head -1 | xargs dirname)" \
     --add-modules javafx.controls,javafx.fxml \
     --add-opens javafx.graphics/javafx.scene=ALL-UNNAMED \
     -cp target/classes \
     com.acu.javafx.performancetest.PerformanceTestApp

echo
echo "Application completed." 