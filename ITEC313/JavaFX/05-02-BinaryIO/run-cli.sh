#!/bin/bash

# Binary I/O CLI Demo Runner Script
# This script runs the CLI version of the Binary I/O demonstration

echo "=== Binary I/O CLI Demo ==="
echo "Running CLI version of Binary I/O demonstration..."
echo ""

# Check if Java is available
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    exit 1
fi

# Check if Maven is available
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    exit 1
fi

# Compile and run the CLI version
echo "Compiling and running BinaryIOCLIDemo..."
echo ""

# Run using Maven exec plugin
mvn exec:java -Dexec.mainClass="com.acu.javafx.binaryio.BinaryIOCLIDemo" -Dexec.args=""

echo ""
echo "CLI demo completed." 