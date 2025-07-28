#!/usr/bin/env python3
"""
Minimal test to replicate the cv2 issue
"""

import sys
import os
import numpy as np
import cv2

# Add the src directory to the path so we can import our modules
sys.path.append(os.path.join(os.path.dirname(__file__), 'src'))

from filtering import smoothing, edge_detection, noise_reduction
from basic_operations import image_io, display, basic_transforms

def test_function():
    """Test the problematic function."""
    # Create a sample image
    image = np.zeros((100, 100, 3), dtype=np.uint8)
    
    print("About to call image_io.convert_color_space...")
    print(f"cv2 module: {cv2}")
    print(f"cv2.COLOR_BGR2GRAY: {cv2.COLOR_BGR2GRAY}")
    
    # This is the line that's failing
    gray = image_io.convert_color_space(image, cv2.COLOR_BGR2GRAY)
    print("Success!")
    return gray

if __name__ == "__main__":
    test_function()
