"""
Edge Detection Module

This module provides various edge detection algorithms:
- Canny edge detection
- Sobel operator
- Laplacian operator
- Scharr operator

Author: OpenCV Image Processing Collection
"""

import cv2
import numpy as np
from typing import Tuple, Optional, Union


def canny_edge_detection(image: np.ndarray, threshold1: float = 100, 
                        threshold2: float = 200, aperture_size: int = 3,
                        l2_gradient: bool = False) -> np.ndarray:
    """
    Apply Canny edge detection to an image.
    
    Args:
        image: Input image (grayscale)
        threshold1: First threshold for the hysteresis procedure
        threshold2: Second threshold for the hysteresis procedure
        aperture_size: Aperture size for the Sobel operator
        l2_gradient: Flag indicating whether to use L2 norm
        
    Returns:
        Edge detected image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    return cv2.Canny(gray, threshold1, threshold2, apertureSize=aperture_size, L2gradient=l2_gradient)


def sobel_edge_detection(image: np.ndarray, dx: int = 1, dy: int = 0, 
                        ksize: int = 3, scale: float = 1, delta: float = 0,
                        border_type: int = cv2.BORDER_DEFAULT) -> np.ndarray:
    """
    Apply Sobel edge detection to an image.
    
    Args:
        image: Input image (grayscale)
        dx: Order of the derivative x
        dy: Order of the derivative y
        ksize: Size of the extended Sobel kernel
        scale: Optional scale factor for the computed derivative values
        delta: Optional delta value that is added to the results
        border_type: Pixel extrapolation method
        
    Returns:
        Edge detected image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    return cv2.Sobel(gray, cv2.CV_64F, dx, dy, ksize=ksize, scale=scale, delta=delta, borderType=border_type)


def laplacian_edge_detection(image: np.ndarray, ksize: int = 1, scale: float = 1,
                           delta: float = 0, border_type: int = cv2.BORDER_DEFAULT) -> np.ndarray:
    """
    Apply Laplacian edge detection to an image.
    
    Args:
        image: Input image (grayscale)
        ksize: Aperture size used to compute the second-derivative filters
        scale: Optional scale factor for the computed Laplacian values
        delta: Optional delta value that is added to the results
        border_type: Pixel extrapolation method
        
    Returns:
        Edge detected image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    return cv2.Laplacian(gray, cv2.CV_64F, ksize=ksize, scale=scale, delta=delta, borderType=border_type)


def scharr_edge_detection(image: np.ndarray, dx: int = 1, dy: int = 0,
                         scale: float = 1, delta: float = 0,
                         border_type: int = cv2.BORDER_DEFAULT) -> np.ndarray:
    """
    Apply Scharr edge detection to an image.
    
    Args:
        image: Input image (grayscale)
        dx: Order of the derivative x
        dy: Order of the derivative y
        scale: Optional scale factor for the computed derivative values
        delta: Optional delta value that is added to the results
        border_type: Pixel extrapolation method
        
    Returns:
        Edge detected image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    return cv2.Scharr(gray, cv2.CV_64F, dx, dy, scale=scale, delta=delta, borderType=border_type)


def multi_scale_edge_detection(image: np.ndarray, scales: list = [1, 2, 4]) -> dict:
    """
    Apply edge detection at multiple scales.
    
    Args:
        image: Input image
        scales: List of scale factors
        
    Returns:
        Dictionary containing edge maps at different scales
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    results = {}
    
    for scale in scales:
        # Resize image
        h, w = image.shape[:2]
        new_h, new_w = int(h / scale), int(w / scale)
        resized = cv2.resize(image, (new_w, new_h))
        
        # Apply edge detection
        edges = canny_edge_detection(resized)
        
        # Resize back to original size
        edges_resized = cv2.resize(edges, (w, h))
        results[f'scale_{scale}'] = edges_resized
    
    return results


def combine_edge_detectors(image: np.ndarray, weights: dict = None) -> np.ndarray:
    """
    Combine multiple edge detection methods.
    
    Args:
        image: Input image
        weights: Dictionary of weights for each method
        
    Returns:
        Combined edge map
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if weights is None:
        weights = {'canny': 0.4, 'sobel': 0.3, 'laplacian': 0.3}
    
    # Apply different edge detectors
    canny = canny_edge_detection(image)
    sobel_x = sobel_edge_detection(image, dx=1, dy=0)
    sobel_y = sobel_edge_detection(image, dx=0, dy=1)
    sobel = np.sqrt(sobel_x**2 + sobel_y**2)
    laplacian = laplacian_edge_detection(image)
    
    # Normalize to 0-255
    sobel = np.clip(sobel, 0, 255).astype(np.uint8)
    laplacian = np.clip(np.abs(laplacian), 0, 255).astype(np.uint8)
    
    # Combine with weights
    combined = (weights['canny'] * canny.astype(float) + 
                weights['sobel'] * sobel.astype(float) + 
                weights['laplacian'] * laplacian.astype(float))
    
    return np.clip(combined, 0, 255).astype(np.uint8)


def adaptive_edge_detection(image: np.ndarray, noise_level: float = 0.1) -> np.ndarray:
    """
    Apply adaptive edge detection based on noise level.
    
    Args:
        image: Input image
        noise_level: Estimated noise level (0-1)
        
    Returns:
        Edge detected image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if noise_level < 0 or noise_level > 1:
        raise ValueError("Noise level must be between 0 and 1")
    
    # Adjust parameters based on noise level
    if noise_level < 0.05:
        # Low noise - use Canny with low thresholds
        return canny_edge_detection(image, 50, 150)
    elif noise_level < 0.2:
        # Medium noise - use Sobel
        sobel_x = sobel_edge_detection(image, dx=1, dy=0)
        sobel_y = sobel_edge_detection(image, dx=0, dy=1)
        return np.sqrt(sobel_x**2 + sobel_y**2)
    else:
        # High noise - use Laplacian with smoothing
        blurred = cv2.GaussianBlur(image, (5, 5), 1.0)
        return laplacian_edge_detection(blurred) 