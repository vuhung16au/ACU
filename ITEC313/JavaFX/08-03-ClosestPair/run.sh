#!/bin/bash

# ClosestPair JavaFX Demo - Run Script
# This script builds and runs the JavaFX application

set -e

echo "Building ClosestPair JavaFX Demo..."

# Clean and compile
mvn clean compile

echo "Running ClosestPair JavaFX Demo..."

# Run with JavaFX Maven plugin
mvn javafx:run

echo "Application finished."
