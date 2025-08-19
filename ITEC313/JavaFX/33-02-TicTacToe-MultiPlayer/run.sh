#!/bin/bash

# Single-Player Tic-Tac-Toe Game - Unix/Linux/macOS Run Script (Secondary Mode)
# This script builds and runs the single-player mode of the Tic-Tac-Toe game using Maven

set -e  # Exit on any error

echo "======================================"
echo "Single-Player Tic-Tac-Toe Game (Secondary Mode)"
echo "======================================"

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    echo "Please install Maven 3.9.x or later"
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install OpenJDK 24 or later"
    exit 1
fi

# Display system information
echo "System Information:"
echo "OS: $(uname -s)"
echo "Architecture: $(uname -m)"
echo "Java Version:"
java -version
echo "Maven Version:"
mvn -version
echo ""

# Clean and compile the project
echo "Building project..."
mvn clean compile

# Check if build was successful
if [ $? -eq 0 ]; then
    echo "Build successful!"
    echo ""
    echo "Starting application..."
    echo "Note: Close the application window to return to terminal"
    echo ""
    
    # Run the application
    mvn javafx:run
else
    echo "Build failed!"
    exit 1
fi
