#!/bin/bash

# Point2D Class Demo - Run Script for Unix/Linux/macOS
# This script compiles and runs the Point2D JavaFX demonstration

echo "=== Point2D Class Demo - ACU JavaFX Course ==="
echo "Compiling and running the Point2D demonstration..."
echo

# Check if Maven is available
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    echo "Please install Maven and try again"
    exit 1
fi

# Clean and compile the project
echo "Cleaning and compiling project..."
mvn clean compile

if [ $? -ne 0 ]; then
    echo "Error: Compilation failed"
    exit 1
fi

echo "Compilation successful!"
echo

# Ask user which demo to run
echo "Which demo would you like to run?"
echo "1) JavaFX GUI Demo (Point2DDemo)"
echo "2) CLI Demo (Point2DCLIDemo)"
echo "3) Run Tests"
echo "4) Exit"
echo

read -p "Enter your choice (1-4): " choice

case $choice in
    1)
        echo "Starting JavaFX GUI Demo..."
        mvn javafx:run
        ;;
    2)
        echo "Starting CLI Demo..."
        mvn exec:java
        ;;
    3)
        echo "Running tests..."
        mvn test
        ;;
    4)
        echo "Exiting..."
        exit 0
        ;;
    *)
        echo "Invalid choice. Exiting..."
        exit 1
        ;;
esac
