#!/bin/bash

# ClosestPair JavaFX Demo - Direct Run Script
# This script runs the application directly with Java (faster for development)

set -e

echo "Compiling ClosestPair JavaFX Demo..."

# Create target directory if it doesn't exist
mkdir -p target/classes

# Compile Java files
javac -cp "$(find ~/.m2/repository -name "*.jar" | tr '\n' ':')" \
      -d target/classes \
      src/main/java/com/acu/javafx/closestpair/*.java

echo "Running ClosestPair JavaFX Demo..."

# Run the application
java -cp "target/classes:$(find ~/.m2/repository -name "*.jar" | tr '\n' ':')" \
     --module-path "$(find ~/.m2/repository -name "javafx-*.jar" | head -1 | xargs dirname)" \
     --add-modules javafx.controls,javafx.fxml \
     com.acu.javafx.closestpair.Launcher

echo "Application finished."
