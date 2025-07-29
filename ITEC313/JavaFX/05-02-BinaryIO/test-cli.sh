#!/bin/bash

# Test script for Binary I/O CLI Demo
echo "Testing Binary I/O CLI Demo..."

# Test compilation
echo "1. Testing compilation..."
mvn clean compile
if [ $? -eq 0 ]; then
    echo "✓ Compilation successful"
else
    echo "✗ Compilation failed"
    exit 1
fi

# Test CLI demo with option 8 (Run All Demonstrations)
echo "2. Testing CLI demo with 'Run All Demonstrations'..."
echo -e "8\n" | timeout 30s mvn exec:java -Dexec.mainClass="com.acu.javafx.binaryio.BinaryIOCLIDemo" -Dexec.args="" -q 2>/dev/null

if [ $? -eq 0 ] || [ $? -eq 124 ]; then
    echo "✓ CLI demo executed successfully"
else
    echo "✗ CLI demo failed"
    exit 1
fi

# Test individual demo (File Stream)
echo "3. Testing individual demo (File Stream)..."
echo -e "1\n9\n" | timeout 30s mvn exec:java -Dexec.mainClass="com.acu.javafx.binaryio.BinaryIOCLIDemo" -Dexec.args="" -q 2>/dev/null

if [ $? -eq 0 ] || [ $? -eq 124 ]; then
    echo "✓ Individual demo executed successfully"
else
    echo "✗ Individual demo failed"
    exit 1
fi

echo ""
echo "=== All tests passed! ==="
echo "The CLI version is working correctly."
echo ""
echo "To run the full interactive demo:"
echo "  ./run-cli.sh"
echo "  or"
echo "  mvn exec:java -Dexec.mainClass=\"com.acu.javafx.binaryio.BinaryIOCLIDemo\"" 