#!/bin/bash

# Anagram Checker Demo - Run Script
# This script compiles and runs the Anagram Checker Demo application

echo "Anagram Checker Demo - Starting..."
echo "=================================="

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

echo "Running tests..."
mvn test

if [ $? -eq 0 ]; then
    echo "Tests passed! Starting application..."
    echo "=================================="
    mvn javafx:run
else
    echo "Tests failed! Please fix the issues before running the application."
    exit 1
fi
