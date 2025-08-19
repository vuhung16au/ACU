#!/bin/bash
# Student Client/Server Application Launcher for macOS
# This script provides a reliable way to run the JavaFX application on macOS

cd "$(dirname "$0")"

echo "Starting Student Client/Server Application..."
mvn javafx:run
