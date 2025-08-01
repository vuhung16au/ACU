#!/bin/bash
# JavaFX HelloWorld Application Launcher for macOS
# This script provides a reliable way to run the JavaFX application on macOS

cd "$(dirname "$0")"

echo "Starting JavaFX HelloWorld Application..."
mvn javafx:run
