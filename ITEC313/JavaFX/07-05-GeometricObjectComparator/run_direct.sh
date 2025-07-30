#!/bin/bash

# Geometric Object Comparator Demo - Direct Java Execution Script
# This script runs the JavaFX application directly without Maven

set -e  # Exit on any error

echo "=== Geometric Object Comparator Demo (Direct Java) ==="
echo "Running the JavaFX application directly..."

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java 24 and try again."
    exit 1
fi

# Display Java version
echo "Java version:"
java -version

# Check if the target directory exists
if [ ! -d "target/classes" ]; then
    echo "Error: Classes not found. Please run 'mvn compile' first."
    exit 1
fi

# Run the application directly
echo "Starting the application..."
java --module-path "$(find ~/.m2/repository/org/openjfx -name "*.jar" | head -1 | xargs dirname)" \
     --add-modules javafx.controls,javafx.fxml \
     -cp target/classes \
     com.acu.javafx.geometricobjectcomparator.Launcher

echo "Application finished." 