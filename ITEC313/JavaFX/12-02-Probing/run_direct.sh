#!/bin/bash

# JavaFX Probing Demo - Direct Java Execution Script
# This script runs the JavaFX application directly without Maven

set -e

echo "=========================================="
echo "JavaFX Probing Techniques Demo (Direct)"
echo "=========================================="

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    exit 1
fi

echo "Java version:"
java -version

echo ""
echo "Compiling the project..."

# Create target directory if it doesn't exist
mkdir -p target/classes

# Compile Java files
javac -cp ".:lib/*" -d target/classes src/main/java/com/acu/javafx/probing/*.java

echo ""
echo "Running the JavaFX application..."
java -cp "target/classes:lib/*" com.acu.javafx.probing.ProbingDemo

echo ""
echo "Application finished." 