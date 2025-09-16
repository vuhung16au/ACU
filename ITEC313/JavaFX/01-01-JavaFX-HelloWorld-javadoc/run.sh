#!/bin/bash

# JavaFX HelloWorld Demo - Run Script for macOS/Linux
# This script compiles and runs the JavaFX application

echo "=== JavaFX HelloWorld Demo ==="
echo "Platform: $(uname -s)"
echo "Java Version: $(java -version 2>&1 | head -n 1)"
echo "Maven Version: $(mvn -version 2>&1 | head -n 1)"
echo ""

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Error: Maven is not installed or not in PATH"
    echo "Please install Maven and try again."
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Error: Java is not installed or not in PATH"
    echo "Please install Java 21 or later and try again."
    exit 1
fi

# Clean and compile the project
echo "🔧 Cleaning and compiling project..."
mvn clean compile

if [ $? -ne 0 ]; then
    echo "❌ Error: Compilation failed"
    exit 1
fi

echo "✅ Compilation successful"

# Run the JavaFX application
echo "🚀 Starting JavaFX HelloWorld application..."
echo ""

# Use JavaFX Maven plugin to run the application
mvn javafx:run

if [ $? -ne 0 ]; then
    echo "❌ Error: Application failed to start"
    exit 1
fi

echo ""
echo "✅ Application completed successfully" 