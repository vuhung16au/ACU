#!/bin/bash

# JavaFX Button Demo - Simple JAR Runner Script
# This script runs the executable JAR with all dependencies included

echo "🚀 JavaFX Button Demo - Simple JAR Runner"
echo "========================================"

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Error: Java is not installed or not in PATH"
    echo "Please install Java 11 or higher"
    exit 1
fi

echo "✅ Java is available"

# Check if the executable JAR exists
if [ ! -f "target/javafx-button-demo-executable.jar" ]; then
    echo "❌ Error: Executable JAR not found"
    echo "Please run: mvn clean package first"
    exit 1
fi

echo "✅ Executable JAR found"

# Run the JavaFX application
echo ""
echo "🎯 Running the JavaFX Button Demo..."
echo "Note: A window should open with the JavaFX application"
echo ""

java -jar target/javafx-button-demo-executable.jar

echo ""
echo "👋 Thanks for using the JavaFX Button Demo!"
