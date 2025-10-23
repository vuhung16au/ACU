#!/bin/bash

# Snake Game - Run Script for Unix/Linux/macOS
# This script compiles and runs the Snake game using Maven

echo "🐍 Starting Snake Game..."
echo "================================"

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven is not installed. Please install Maven first."
    echo "   Visit: https://maven.apache.org/install.html"
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed. Please install Java 24 or later."
    echo "   Visit: https://openjdk.java.net/"
    exit 1
fi

echo "✅ Maven and Java found"
echo "🔨 Compiling project..."

# Clean and compile
mvn clean compile

if [ $? -ne 0 ]; then
    echo "❌ Compilation failed. Please check the errors above."
    exit 1
fi

echo "✅ Compilation successful"
echo "🚀 Starting Snake Game..."

# Run the game
mvn javafx:run

echo "👋 Thanks for playing Snake Game!"
