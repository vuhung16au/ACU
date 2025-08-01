#!/bin/bash

# JavaFX Heap Sort Demo - JAR Runner Script
# This script creates dependencies and runs the JAR with proper JavaFX module path

echo "🚀 JavaFX Heap Sort Demo - JAR Runner Script"
echo "====================================="

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

# Clean and package the project
echo ""
echo "🔨 Building the project and creating JAR..."
mvn clean package

if [ $? -ne 0 ]; then
    echo "❌ Error: Build failed"
    exit 1
fi

echo "✅ Build successful"

# Copy dependencies to lib directory
echo ""
echo "📦 Copying JavaFX dependencies to lib directory..."
mvn dependency:copy-dependencies -DoutputDirectory=lib

if [ $? -ne 0 ]; then
    echo "❌ Error: Failed to copy dependencies"
    exit 1
fi

echo "✅ Dependencies copied to lib directory"

# Find JavaFX version and path
JAVAFX_PATH=$(find lib -name "javafx-controls-*.jar" | head -1 | xargs dirname)

if [ -z "$JAVAFX_PATH" ]; then
    echo "❌ Error: JavaFX libraries not found in lib directory"
    exit 1
fi

echo "✅ JavaFX libraries found at: $JAVAFX_PATH"

# Run the JavaFX application
echo ""
echo "🎯 Running the JavaFX Heap Sort Demo from JAR..."
echo "Note: A window should open with the JavaFX application"
echo ""

java --module-path "$JAVAFX_PATH" \
     --add-modules javafx.controls,javafx.fxml \
     -jar target/heapsort-demo-1.0.0.jar

echo ""
echo "👋 Thanks for using the JavaFX Heap Sort Demo!"
