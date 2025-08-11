#!/usr/bin/env python3
"""
Test script to verify the iris classification works locally
before building the Docker container.
"""

import sys
import subprocess

def test_requirements():
    """Test if all required packages are installed."""
    print("Testing required packages...")
    required_packages = ['sklearn', 'numpy']
    
    for package in required_packages:
        try:
            __import__(package)
            print(f"✓ {package} is available")
        except ImportError:
            print(f"✗ {package} is missing")
            return False
    return True

def test_application():
    """Test the main application."""
    print("\nTesting iris classification application...")
    try:
        result = subprocess.run([sys.executable, 'iris_classification.py'], 
                              capture_output=True, text=True, timeout=30)
        
        if result.returncode == 0:
            print("✓ Application runs successfully")
            print("\nOutput preview:")
            lines = result.stdout.split('\n')
            for line in lines[:10]:  # Show first 10 lines
                print(f"  {line}")
            if len(lines) > 10:
                print("  ...")
            return True
        else:
            print("✗ Application failed to run")
            print(f"Error: {result.stderr}")
            return False
    except subprocess.TimeoutExpired:
        print("✗ Application timed out")
        return False
    except Exception as e:
        print(f"✗ Error running application: {e}")
        return False

def main():
    print("=== Local Testing ===")
    
    # Test requirements
    if not test_requirements():
        print("\nPlease install missing packages:")
        print("pip install -r requirements.txt")
        return False
    
    # Test application
    if not test_application():
        print("\nPlease fix the application before building Docker image")
        return False
    
    print("\n✓ All tests passed! Ready to build Docker image.")
    return True

if __name__ == "__main__":
    success = main()
    sys.exit(0 if success else 1)
