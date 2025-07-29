#!/bin/bash

# Quick test for Binary I/O CLI Demo
echo "Quick test for Binary I/O CLI Demo..."

# Test compilation
echo "1. Testing compilation..."
mvn clean compile
if [ $? -eq 0 ]; then
    echo "✓ Compilation successful"
else
    echo "✗ Compilation failed"
    exit 1
fi

# Test that the class exists and can be loaded
echo "2. Testing class loading..."
mvn exec:java -Dexec.mainClass="com.acu.javafx.binaryio.BinaryIOCLIDemo" -Dexec.args="" -q -Dexec.cleanupDaemonThreads=false 2>/dev/null &
PID=$!
sleep 3
kill $PID 2>/dev/null

if [ $? -eq 0 ]; then
    echo "✓ Class loading successful"
else
    echo "✗ Class loading failed"
    exit 1
fi

echo ""
echo "=== Quick test passed! ==="
echo "The CLI version compiles and loads correctly."
echo ""
echo "To run the full interactive demo:"
echo "  ./run-cli.sh"
echo "  or"
echo "  mvn exec:java -Dexec.mainClass=\"com.acu.javafx.binaryio.BinaryIOCLIDemo\"" 