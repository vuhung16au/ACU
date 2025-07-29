"""
Geometric Transformations Module

This module provides various geometric transformation techniques:
- Affine transformations (translation, rotation, scaling, shearing)
- Perspective transformations
- Image warping and distortion correction

Author: Vu Hung Nguyen
"""

from .affine_transforms import *
from .perspective_transforms import *
from .warping import *

__all__ = [
    # Affine transformation functions
    'translate', 'rotate', 'scale', 'shear', 'affine_transform',
    # Perspective transformation functions
    'perspective_transform', 'homography_transform', 'rectify_image',
    # Warping functions
    'warp_image', 'barrel_distortion_correction', 'pincushion_distortion_correction'
] 