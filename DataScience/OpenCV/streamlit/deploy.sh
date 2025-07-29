#!/bin/bash

# OpenCV Streamlit App Deployment Script
# This script helps deploy the Streamlit app to Streamlit Cloud

echo "🚀 OpenCV Streamlit App Deployment Helper"
echo "=========================================="

# Check if we're in the right directory
if [ ! -f "app.py" ]; then
    echo "❌ Error: app.py not found. Please run this script from the streamlit/ directory."
    exit 1
fi

echo "✅ Found app.py in current directory"

# Check if requirements.txt exists
if [ ! -f "requirements.txt" ]; then
    echo "❌ Error: requirements.txt not found."
    exit 1
fi

echo "✅ Found requirements.txt"

# Check if .streamlit/config.toml exists
if [ ! -f ".streamlit/config.toml" ]; then
    echo "❌ Error: .streamlit/config.toml not found."
    exit 1
fi

echo "✅ Found .streamlit/config.toml"

echo ""
echo "📋 Deployment Options:"
echo "1. Deploy to Streamlit Cloud (GitHub)"
echo "2. Deploy to Streamlit Cloud (Local files)"
echo "3. Test locally first"
echo "4. Exit"

read -p "Choose an option (1-4): " choice

case $choice in
    1)
        echo ""
        echo "🌐 Deploying to Streamlit Cloud via GitHub..."
        echo ""
        echo "📝 Steps to follow:"
        echo "1. Push your code to GitHub:"
        echo "   git add ."
        echo "   git commit -m 'Add Streamlit app'"
        echo "   git push origin main"
        echo ""
        echo "2. Go to https://share.streamlit.io"
        echo "3. Sign in with GitHub"
        echo "4. Click 'New app'"
        echo "5. Select your repository"
        echo "6. Set the path to: streamlit/app.py"
        echo "7. Click 'Deploy'"
        echo ""
        echo "🎉 Your app will be available at: https://your-app-name.streamlit.app"
        ;;
    2)
        echo ""
        echo "🌐 Deploying to Streamlit Cloud via local files..."
        echo ""
        echo "📝 Make sure you have streamlit CLI installed:"
        echo "pip install streamlit"
        echo ""
        echo "Then run:"
        echo "streamlit deploy app.py"
        ;;
    3)
        echo ""
        echo "🧪 Testing locally..."
        echo ""
        echo "Starting local server..."
        streamlit run app.py
        ;;
    4)
        echo "👋 Goodbye!"
        exit 0
        ;;
    *)
        echo "❌ Invalid option. Please choose 1-4."
        exit 1
        ;;
esac

echo ""
echo "✅ Deployment script completed!" 