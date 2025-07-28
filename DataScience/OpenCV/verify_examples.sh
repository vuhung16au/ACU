#!/bin/bash

# Script to verify that all examples/*.py files can run without errors
# This script activates the virtual environment and runs the verification

echo "🔍 Verifying examples/*.py files..."

# Check if virtual environment exists
if [ ! -d "venv" ]; then
    echo "❌ Virtual environment not found. Creating one..."
    python3 -m venv venv
    source venv/bin/activate
    pip install -r requirements.txt
else
    source venv/bin/activate
fi

# Run the verification script
python verify_examples.py

# Exit with the same code as the verification script
exit_code=$?
if [ $exit_code -eq 0 ]; then
    echo "✅ All examples verified successfully!"
else
    echo "❌ Some examples failed verification."
fi

exit $exit_code 