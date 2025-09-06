#!/bin/bash

# Koch Snowflake Fractal Demo - JAR Run Script for Unix/Linux/macOS
# This script builds the JAR and runs it

echo "Koch Snowflake Fractal Demo - JAR Version"
echo "========================================"
echo ""

# Check if Maven is available
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    echo "Please install Maven and try again"
    exit 1
fi

# Check if Java is available
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java and try again"
    exit 1
fi

echo "Building the JAR file..."
echo ""

# Clean and package
mvn clean package

if [ $? -eq 0 ]; then
    echo ""
    echo "JAR built successfully. Running the application..."
    echo ""
    
    # Run the JAR
    java -jar target/koch-snowflake-demo-1.0.0.jar
else
    echo ""
    echo "Error: Failed to build the JAR file"
    exit 1
fi

echo ""
echo "Application finished."
