#!/bin/bash

# Alternative run script using direct java command
echo "JavaFX Panes, UI Controls & Shapes Demo (Direct Java Run)"
echo "========================================================="

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    echo "Please install Maven to run this application"
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java 17 or higher"
    exit 1
fi

echo "Building the application..."
mvn clean compile -Djavafx.platform=mac-aarch64

if [ $? -eq 0 ]; then
    echo "Build successful! Starting the application..."
    
    # Get the classpath from Maven
    CLASSPATH=$(mvn dependency:build-classpath -Dmdep.outputFile=/dev/stdout -q)
    CLASSPATH="target/classes:$CLASSPATH"
    
    # Set JavaFX module path to use platform-specific natives
    JAVA_OPTS="--module-path $CLASSPATH --add-modules javafx.controls,javafx.fxml"
    JAVA_OPTS="$JAVA_OPTS -Dprism.order=sw"
    JAVA_OPTS="$JAVA_OPTS -Djavafx.platform=mac-aarch64"
    JAVA_OPTS="$JAVA_OPTS -Dprism.vsync=false"
    
    # Run the application
    java $JAVA_OPTS -cp "$CLASSPATH" com.example.ShapesDemo
else
    echo "Build failed! Please check the error messages above."
    exit 1
fi
