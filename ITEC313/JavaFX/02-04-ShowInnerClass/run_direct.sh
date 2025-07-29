#!/bin/bash

# JavaFX ShowInnerClass Demo - Direct Java Execution Script
# This script compiles and runs the JavaFX application directly without Maven

echo "JavaFX ShowInnerClass Demo - Direct Execution"
echo "============================================"

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java 24 or later and try again"
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt "24" ]; then
    echo "Warning: Java version $JAVA_VERSION detected. Java 24 or later is recommended."
fi

# Set up classpath and module path for JavaFX
JAVAFX_PATH=""
if [ -d "$HOME/.m2/repository/org/openjfx" ]; then
    # Try to find JavaFX in Maven repository
    JAVAFX_PATH=$(find $HOME/.m2/repository/org/openjfx -name "*.jar" | head -10 | tr '\n' ':')
elif [ -d "/usr/share/openjfx/lib" ]; then
    # Try system JavaFX installation
    JAVAFX_PATH="/usr/share/openjfx/lib/*"
fi

if [ -z "$JAVAFX_PATH" ]; then
    echo "Error: JavaFX libraries not found"
    echo "Please run 'mvn dependency:resolve' first to download JavaFX dependencies"
    exit 1
fi

echo "Compiling Java source files..."
echo ""

# Create target directory
mkdir -p target/classes

# Compile Java files
javac -cp "$JAVAFX_PATH" -d target/classes src/main/java/com/acu/javafx/showinnerclass/*.java

if [ $? -ne 0 ]; then
    echo "Compilation failed!"
    exit 1
fi

echo "Compilation successful!"
echo "Starting JavaFX application..."
echo ""

# Run the application
java -cp "target/classes:$JAVAFX_PATH" --add-modules javafx.controls,javafx.fxml com.acu.javafx.showinnerclass.Launcher

echo ""
echo "Application finished." 