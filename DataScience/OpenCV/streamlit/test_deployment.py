#!/usr/bin/env python3
"""
Deployment Test Script for OpenCV Streamlit App
Tests that all components can be imported and basic functionality works.
"""

import sys
import os

def test_imports():
    """Test that all required modules can be imported."""
    print("🧪 Testing imports...")
    
    try:
        import streamlit
        print("✅ streamlit imported successfully")
    except ImportError as e:
        print(f"❌ Failed to import streamlit: {e}")
        return False
    
    try:
        import cv2
        print("✅ opencv-python imported successfully")
    except ImportError as e:
        print(f"❌ Failed to import opencv-python: {e}")
        return False
    
    try:
        import numpy as np
        print("✅ numpy imported successfully")
    except ImportError as e:
        print(f"❌ Failed to import numpy: {e}")
        return False
    
    try:
        import matplotlib
        print("✅ matplotlib imported successfully")
    except ImportError as e:
        print(f"❌ Failed to import matplotlib: {e}")
        return False
    
    try:
        from PIL import Image
        print("✅ pillow imported successfully")
    except ImportError as e:
        print(f"❌ Failed to import pillow: {e}")
        return False
    
    try:
        import plotly
        print("✅ plotly imported successfully")
    except ImportError as e:
        print(f"❌ Failed to import plotly: {e}")
        return False
    
    return True

def test_app_components():
    """Test that app components can be imported."""
    print("\n🧪 Testing app components...")
    
    try:
        from utils import load_image_from_upload, display_image, get_image_info
        print("✅ utils module imported successfully")
    except ImportError as e:
        print(f"❌ Failed to import utils: {e}")
        return False
    
    try:
        from components import (
            basic_operations_section,
            image_filtering_section,
            transformations_section,
            morphological_operations_section,
            feature_detection_section,
            color_processing_section,
            advanced_techniques_section,
            practical_applications_section
        )
        print("✅ components module imported successfully")
    except ImportError as e:
        print(f"❌ Failed to import components: {e}")
        return False
    
    return True

def test_app_creation():
    """Test that the main app can be created without errors."""
    print("\n🧪 Testing app creation...")
    
    try:
        # Import the main app
        import app
        print("✅ app.py imported successfully")
    except Exception as e:
        print(f"❌ Failed to import app.py: {e}")
        return False
    
    return True

def test_config_files():
    """Test that configuration files exist."""
    print("\n🧪 Testing configuration files...")
    
    # Check requirements.txt
    if os.path.exists("requirements.txt"):
        print("✅ requirements.txt exists")
    else:
        print("❌ requirements.txt not found")
        return False
    
    # Check .streamlit/config.toml
    if os.path.exists(".streamlit/config.toml"):
        print("✅ .streamlit/config.toml exists")
    else:
        print("❌ .streamlit/config.toml not found")
        return False
    
    return True

def main():
    """Run all deployment tests."""
    print("🚀 OpenCV Streamlit App - Deployment Test")
    print("=" * 50)
    
    all_tests_passed = True
    
    # Test imports
    if not test_imports():
        all_tests_passed = False
    
    # Test app components
    if not test_app_components():
        all_tests_passed = False
    
    # Test app creation
    if not test_app_creation():
        all_tests_passed = False
    
    # Test config files
    if not test_config_files():
        all_tests_passed = False
    
    print("\n" + "=" * 50)
    if all_tests_passed:
        print("🎉 All tests passed! Your app is ready for deployment.")
        print("\n📋 Next steps:")
        print("1. Push your code to GitHub")
        print("2. Go to https://share.streamlit.io")
        print("3. Deploy your app")
        return True
    else:
        print("❌ Some tests failed. Please fix the issues before deploying.")
        return False

if __name__ == "__main__":
    success = main()
    sys.exit(0 if success else 1) 