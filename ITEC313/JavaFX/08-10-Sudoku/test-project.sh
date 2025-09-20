#!/bin/bash

# Test script for Sudoku Solver project
echo "=== Testing Sudoku Solver Project ==="

# Test 1: Compilation
echo "1. Testing compilation..."
mvn clean compile
if [ $? -eq 0 ]; then
    echo "✅ Compilation successful"
else
    echo "❌ Compilation failed"
    exit 1
fi

# Test 2: Unit tests
echo "2. Testing unit tests..."
mvn test
if [ $? -eq 0 ]; then
    echo "✅ All tests passed"
else
    echo "❌ Some tests failed"
    exit 1
fi

# Test 3: Package creation
echo "3. Testing package creation..."
mvn package -DskipTests
if [ $? -eq 0 ]; then
    echo "✅ Package created successfully"
else
    echo "❌ Package creation failed"
    exit 1
fi

echo "🎉 All tests passed! The Sudoku Solver project is working correctly."
echo ""
echo "To run the application:"
echo "  mvn javafx:run"
echo ""
echo "Or use the run scripts:"
echo "  ./run.sh (Unix/Linux/macOS)"
echo "  run.bat (Windows)"
