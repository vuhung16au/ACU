#!/bin/bash

# OpenCV Interactive Dashboard - Run Script
# This script activates the virtual environment and runs the Streamlit app

echo "🔬 Starting OpenCV Interactive Dashboard..."
echo "=========================================="

# Check if virtual environment exists
if [ ! -d "../.venv" ]; then
    echo "❌ Virtual environment not found. Please run 'python -m venv .venv' in the parent directory first."
    exit 1
fi

# Activate virtual environment
echo "📦 Activating virtual environment..."
source ../.venv/bin/activate

# Check if required packages are installed
echo "🔍 Checking dependencies..."
python -c "import streamlit, cv2, numpy, PIL" 2>/dev/null
if [ $? -ne 0 ]; then
    echo "📥 Installing required packages..."
    pip install -r requirements.txt
fi

# Run the Streamlit app
echo "🚀 Starting Streamlit app..."
echo "📱 The app will open in your browser at: http://localhost:8501"
echo "🛑 Press Ctrl+C to stop the app"
echo ""

streamlit run app.py --server.headless false --server.port 8501 