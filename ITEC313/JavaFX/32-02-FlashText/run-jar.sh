#!/bin/bash

# FlashText JavaFX Application - JAR Run Script
# This script builds the JAR and runs it

set -e  # Exit on any error

echo "=== FlashText JavaFX Application - JAR Version ==="
echo "Building and running the JAR application..."

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    echo "Please install Maven and try again"
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java 24 and try again"
    exit 1
fi

# Display Java version
echo "Java version:"
java -version

# Display Maven version
echo "Maven version:"
mvn -version

# Clean and package
echo "Cleaning and packaging..."
mvn clean package

# Get the JAR file name
JAR_FILE=$(find target -name "flashtext-*.jar" | head -1)

if [ -z "$JAR_FILE" ]; then
    echo "Error: JAR file not found in target directory"
    exit 1
fi

echo "Found JAR file: $JAR_FILE"

# Run the JAR
echo "Running the FlashText JAR application..."
java -jar "$JAR_FILE"

echo "Application finished."
