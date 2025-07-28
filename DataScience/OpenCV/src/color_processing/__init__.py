"""
Color Processing Module

This module provides various color processing techniques:
- Color space conversions (RGB, HSV, LAB)
- Histogram operations and equalization
- Color enhancement and correction

Author: OpenCV Image Processing Collection
"""

from .color_spaces import *
from .histogram import *
from .color_enhancement import *

__all__ = [
    # Color space functions
    'rgb_to_hsv', 'hsv_to_rgb', 'rgb_to_lab', 'lab_to_rgb', 'rgb_to_gray',
    # Histogram functions
    'compute_histogram', 'equalize_histogram', 'clahe', 'histogram_matching',
    # Color enhancement functions
    'adjust_brightness_contrast', 'gamma_correction', 'white_balance', 'color_grading'
] 