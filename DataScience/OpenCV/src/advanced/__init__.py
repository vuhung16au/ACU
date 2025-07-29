"""
Advanced Techniques Module

This module provides advanced image processing techniques:
- Template matching algorithms
- Image segmentation methods
- Fourier analysis
- Machine learning applications

Author: Vu Hung Nguyen
"""

from .template_matching import *
from .image_segmentation import *
from .fourier_analysis import *
from .machine_learning import *

__all__ = [
    # Template matching functions
    'template_matching', 'multi_scale_template_matching', 'rotation_invariant_matching',
    # Image segmentation functions
    'threshold_segmentation', 'watershed_segmentation', 'grabcut_segmentation', 'kmeans_segmentation',
    # Fourier analysis functions
    'fourier_transform', 'inverse_fourier_transform', 'frequency_domain_filtering',
    # Machine learning functions
    'face_detection', 'object_detection', 'background_subtraction'
] 