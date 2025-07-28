#!/usr/bin/env python3
"""
Debug script to isolate the cv2 import issue
"""

import sys
import os
import numpy as np

print("1. Starting debug script")

try:
    import cv2
    print("2. cv2 imported successfully")
    print(f"   OpenCV version: {cv2.__version__}")
    print(f"   cv2.COLOR_BGR2GRAY: {cv2.COLOR_BGR2GRAY}")
except Exception as e:
    print(f"2. Error importing cv2: {e}")
    sys.exit(1)

# Add the src directory to the path
sys.path.append(os.path.join(os.path.dirname(__file__), 'src'))
print("3. Added src to path")

try:
    from basic_operations import image_io
    print("4. Imported image_io successfully")
except Exception as e:
    print(f"4. Error importing image_io: {e}")
    sys.exit(1)

# Test the problematic line
try:
    # Create a sample image
    image = np.zeros((100, 100, 3), dtype=np.uint8)
    print("5. Created sample image")
    
    # This is the line that's failing in the original script
    gray = image_io.convert_color_space(image, cv2.COLOR_BGR2GRAY)
    print("6. convert_color_space call succeeded")
    print(f"   Result shape: {gray.shape}")
    
except Exception as e:
    print(f"6. Error in convert_color_space: {e}")
    import traceback
    traceback.print_exc()
