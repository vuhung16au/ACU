"""
Image Filtering Techniques Module

This module provides various image filtering techniques including:
- Smoothing filters (Gaussian, Box, Median, Bilateral)
- Edge detection (Canny, Sobel, Laplacian, Scharr)
- Noise reduction techniques

Author: Vu Hung Nguyen
"""

from .smoothing import *
from .edge_detection import *
from .noise_reduction import *

__all__ = [
    # Smoothing functions
    'gaussian_blur', 'box_filter', 'median_filter', 'bilateral_filter',
    # Edge detection functions
    'canny_edge_detection', 'sobel_edge_detection', 'laplacian_edge_detection', 'scharr_edge_detection',
    # Noise reduction functions
    'denoise_bilateral', 'denoise_nlm', 'denoise_morphological'
] 