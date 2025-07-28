#!/usr/bin/env python3
"""
Script to install required dependencies for the OpenCV Image Processing project.

This script installs all the necessary packages for the project to run properly.
"""

import subprocess
import sys
import os


def run_command(command: str, description: str) -> bool:
    """
    Run a command and return success status.
    
    Args:
        command: Command to run
        description: Description of what the command does
        
    Returns:
        True if successful, False otherwise
    """
    print(f"Installing {description}...")
    try:
        result = subprocess.run(command.split(), capture_output=True, text=True)
        if result.returncode == 0:
            print(f"  ✓ {description} installed successfully")
            return True
        else:
            print(f"  ✗ Failed to install {description}")
            print(f"    Error: {result.stderr}")
            return False
    except Exception as e:
        print(f"  ✗ Error running command: {e}")
        return False


def main():
    """Install all required dependencies."""
    print("OpenCV Image Processing - Dependency Installer")
    print("=" * 50)
    
    # List of packages to install
    packages = [
        ("opencv-python", "OpenCV"),
        ("numpy", "NumPy"),
        ("matplotlib", "Matplotlib"),
        ("scipy", "SciPy"),
        ("scikit-image", "scikit-image"),
        ("jupyter", "Jupyter"),
        ("pytest", "pytest")
    ]
    
    success_count = 0
    total_count = len(packages)
    
    for package_name, display_name in packages:
        if run_command(f"pip3 install {package_name}", display_name):
            success_count += 1
        print()
    
    print("=" * 50)
    print("INSTALLATION SUMMARY")
    print("=" * 50)
    print(f"Successfully installed: {success_count}/{total_count} packages")
    
    if success_count == total_count:
        print("\n🎉 All dependencies installed successfully!")
        print("You can now run: python3 validate_src_modules.py")
    else:
        print(f"\n⚠️  {total_count - success_count} package(s) failed to install.")
        print("Please check the error messages above and try installing manually.")
    
    return 0 if success_count == total_count else 1


if __name__ == "__main__":
    sys.exit(main()) 