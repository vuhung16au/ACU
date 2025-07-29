"""
Feature Detection Module

This module provides various feature detection algorithms:
- Corner detection (Harris, Shi-Tomasi, FAST)
- Keypoint detection (SIFT, SURF, ORB)
- Contour detection and analysis

Author: Vu Hung Nguyen
"""

from .corner_detection import *
from .keypoint_detection import *
from .contour_detection import *

__all__ = [
    # Corner detection functions
    'harris_corners', 'shi_tomasi_corners', 'fast_corners',
    # Keypoint detection functions
    'detect_sift', 'detect_surf', 'detect_orb', 'match_features',
    # Contour detection functions
    'find_contours', 'approximate_contours', 'analyze_contours', 'match_shapes'
] 