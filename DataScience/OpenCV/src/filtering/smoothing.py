"""
Smoothing Filters Module

This module provides various smoothing filter implementations:
- Gaussian blur
- Box filter
- Median filter
- Bilateral filter

Author: OpenCV Image Processing Collection
"""

import cv2
import numpy as np
from typing import Tuple, Optional, Union


def gaussian_blur(image: np.ndarray, kernel_size: Tuple[int, int] = (5, 5), 
                  sigma_x: float = 0, sigma_y: float = 0, 
                  border_type: int = cv2.BORDER_DEFAULT) -> np.ndarray:
    """
    Apply Gaussian blur to an image.
    
    Args:
        image: Input image (grayscale or color)
        kernel_size: Size of the Gaussian kernel (width, height)
        sigma_x: Standard deviation in X direction
        sigma_y: Standard deviation in Y direction
        border_type: Border type for convolution
        
    Returns:
        Blurred image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if len(kernel_size) != 2:
        raise ValueError("Kernel size must be a tuple of 2 integers")
    
    if kernel_size[0] % 2 == 0 or kernel_size[1] % 2 == 0:
        raise ValueError("Kernel size must be odd numbers")
    
    return cv2.GaussianBlur(image, kernel_size, sigma_x, sigma_y, border_type)


def box_filter(image: np.ndarray, kernel_size: Tuple[int, int] = (5, 5),
               normalize: bool = True, border_type: int = cv2.BORDER_DEFAULT) -> np.ndarray:
    """
    Apply box filter (averaging filter) to an image.
    
    Args:
        image: Input image (grayscale or color)
        kernel_size: Size of the box kernel (width, height)
        normalize: Whether to normalize the kernel
        border_type: Border type for convolution
        
    Returns:
        Filtered image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if len(kernel_size) != 2:
        raise ValueError("Kernel size must be a tuple of 2 integers")
    
    return cv2.boxFilter(image, -1, kernel_size, normalize=normalize, borderType=border_type)


def median_filter(image: np.ndarray, kernel_size: int = 5) -> np.ndarray:
    """
    Apply median filter to an image.
    
    Args:
        image: Input image (grayscale or color)
        kernel_size: Size of the median filter kernel (must be odd)
        
    Returns:
        Filtered image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if kernel_size % 2 == 0:
        raise ValueError("Kernel size must be odd")
    
    return cv2.medianBlur(image, kernel_size)


def bilateral_filter(image: np.ndarray, diameter: int = 9, sigma_color: float = 75,
                    sigma_space: float = 75, border_type: int = cv2.BORDER_DEFAULT) -> np.ndarray:
    """
    Apply bilateral filter to an image.
    
    Args:
        image: Input image (grayscale or color)
        diameter: Diameter of each pixel neighborhood
        sigma_color: Filter sigma in the color space
        sigma_space: Filter sigma in the coordinate space
        border_type: Border type for convolution
        
    Returns:
        Filtered image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    return cv2.bilateralFilter(image, diameter, sigma_color, sigma_space, border_type)


def apply_smoothing_comparison(image: np.ndarray, kernel_size: int = 5) -> dict:
    """
    Apply all smoothing filters and return comparison results.
    
    Args:
        image: Input image
        kernel_size: Kernel size for filters
        
    Returns:
        Dictionary containing all filtered images
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    results = {
        'original': image,
        'gaussian': gaussian_blur(image, (kernel_size, kernel_size)),
        'box': box_filter(image, (kernel_size, kernel_size)),
        'median': median_filter(image, kernel_size),
        'bilateral': bilateral_filter(image, kernel_size)
    }
    
    return results


def adaptive_smoothing(image: np.ndarray, noise_level: float = 0.1) -> np.ndarray:
    """
    Apply adaptive smoothing based on noise level.
    
    Args:
        image: Input image
        noise_level: Estimated noise level (0-1)
        
    Returns:
        Adaptively smoothed image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if noise_level < 0 or noise_level > 1:
        raise ValueError("Noise level must be between 0 and 1")
    
    # Choose filter based on noise level
    if noise_level < 0.05:
        # Low noise - use bilateral filter
        return bilateral_filter(image, 9, 50, 50)
    elif noise_level < 0.2:
        # Medium noise - use Gaussian blur
        return gaussian_blur(image, (5, 5), 1.0)
    else:
        # High noise - use median filter
        return median_filter(image, 5) 