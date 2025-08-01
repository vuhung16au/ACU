#!/bin/bash
# JavaFX HelloWorld - Direct JAR execution for macOS
# This script runs the application using the compiled JAR with all dependencies

cd "$(dirname "$0")"

# Check if target directory exists
if [ ! -d "target" ]; then
    echo "Project not built. Building now..."
    mvn clean package
fi

echo "Running JavaFX HelloWorld Application..."

# Run with JavaFX modules on the module path
java --module-path target/lib \
     --add-modules javafx.controls,javafx.fxml \
     -jar target/JavaFX-HelloWorld-1.0.jar
