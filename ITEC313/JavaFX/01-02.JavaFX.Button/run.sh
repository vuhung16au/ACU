#!/bin/bash

# JavaFX Button Demo - Build and Run Script
# This script helps you easily build and run the JavaFX application

echo "🚀 JavaFX Button Demo - Build and Run Script"
echo "=============================================="

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Error: Maven is not installed or not in PATH"
    echo "Please install Maven first: https://maven.apache.org/install.html"
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Error: Java is not installed or not in PATH"
    echo "Please install Java 11 or higher"
    exit 1
fi

echo "✅ Maven and Java are available"

# Clean and compile the project
echo ""
echo "🔨 Cleaning and compiling the project..."
mvn clean compile

if [ $? -ne 0 ]; then
    echo "❌ Error: Compilation failed"
    exit 1
fi

echo "✅ Compilation successful"

# Run the JavaFX application
echo ""
echo "🎯 Running the JavaFX Button Demo..."
echo "Note: A window should open with the JavaFX application"
echo ""

mvn javafx:run

echo ""
echo "👋 Thanks for using the JavaFX Button Demo!"
