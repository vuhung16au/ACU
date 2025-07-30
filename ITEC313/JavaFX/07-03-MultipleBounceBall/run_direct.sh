#!/bin/bash

# Direct Java Execution Script
# Bypasses Maven for faster development cycles

set -e

echo "=== Multiple Bounce Ball JavaFX Application (Direct Execution) ==="
echo "Platform: $(uname -s) $(uname -m)"
echo "Java Version: $(java -version 2>&1 | head -n 1)"
echo ""

# Check if Java is available
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    exit 1
fi

# Set JavaFX module path (adjust path as needed for your system)
JAVAFX_PATH=""
if [[ "$OSTYPE" == "darwin"* ]]; then
    # macOS
    JAVAFX_PATH="/Library/Java/JavaVirtualMachines/openjfx-21.0.2/Contents/Home/lib"
elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
    # Linux
    JAVAFX_PATH="/usr/share/openjfx/lib"
else
    echo "Warning: JavaFX path not configured for this platform"
    echo "Please install JavaFX and update the JAVAFX_PATH variable"
    exit 1
fi

# Check if JavaFX is available
if [ ! -d "$JAVAFX_PATH" ]; then
    echo "Error: JavaFX not found at $JAVAFX_PATH"
    echo "Please install JavaFX or update the JAVAFX_PATH variable"
    exit 1
fi

# Compile
echo "Compiling..."
javac -cp "$JAVAFX_PATH/*" -d target/classes src/main/java/com/acu/javafx/multiplebounceball/*.java

# Run
echo "Starting application..."
java --module-path "$JAVAFX_PATH" --add-modules javafx.controls,javafx.fxml -cp target/classes com.acu.javafx.multiplebounceball.MultipleBounceBall

echo "Application finished." 