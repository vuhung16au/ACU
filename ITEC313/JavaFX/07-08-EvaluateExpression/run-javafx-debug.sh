#!/bin/bash

# Script to run JavaFX application with proper module path for debugging
# This script sets up the JavaFX modules and runs the application

# Get the Maven repository path
MAVEN_REPO="$HOME/.m2/repository"

# JavaFX module paths
JAVAFX_CONTROLS="$MAVEN_REPO/org/openjfx/javafx-controls/21/javafx-controls-21-mac-aarch64.jar"
JAVAFX_FXML="$MAVEN_REPO/org/openjfx/javafx-fxml/21/javafx-fxml-21-mac-aarch64.jar"
JAVAFX_GRAPHICS="$MAVEN_REPO/org/openjfx/javafx-graphics/21/javafx-graphics-21-mac-aarch64.jar"
JAVAFX_BASE="$MAVEN_REPO/org/openjfx/javafx-base/21/javafx-base-21-mac-aarch64.jar"

# Build the module path
MODULE_PATH="target/classes:$JAVAFX_CONTROLS:$JAVAFX_FXML:$JAVAFX_GRAPHICS:$JAVAFX_BASE"

# Compile the project first
echo "Compiling project..."
mvn compile

if [ $? -eq 0 ]; then
    echo "Running JavaFX application with debug support..."
    echo "Module path: $MODULE_PATH"
    
    # Run with JavaFX modules
    java --module-path "$MODULE_PATH" \
         --add-modules javafx.controls,javafx.fxml \
         -cp target/classes \
         com.acu.javafx.evaluateexpression.EvaluateExpressionApp
else
    echo "Compilation failed!"
    exit 1
fi
