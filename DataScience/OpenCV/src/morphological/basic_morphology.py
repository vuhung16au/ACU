"""
Basic Morphological Operations Module

This module provides basic morphological operations:
- Erosion
- Dilation
- Opening
- Closing
- Morphological gradient

Author: Vu Hung Nguyen
"""

import cv2
import numpy as np
from typing import Tuple, Optional, Union


def erode(image: np.ndarray, kernel: Optional[np.ndarray] = None, 
          iterations: int = 1, border_type: int = cv2.BORDER_CONSTANT,
          border_value: int = 0) -> np.ndarray:
    """
    Apply erosion morphological operation.
    
    Args:
        image: Input image (binary or grayscale)
        kernel: Structuring element (if None, uses 3x3 rectangle)
        iterations: Number of iterations
        border_type: Border type
        border_value: Value for constant border
        
    Returns:
        Eroded image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if kernel is None:
        kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (3, 3))
    
    return cv2.erode(image, kernel, iterations=iterations, 
                     borderType=border_type, borderValue=border_value)


def dilate(image: np.ndarray, kernel: Optional[np.ndarray] = None,
           iterations: int = 1, border_type: int = cv2.BORDER_CONSTANT,
           border_value: int = 0) -> np.ndarray:
    """
    Apply dilation morphological operation.
    
    Args:
        image: Input image (binary or grayscale)
        kernel: Structuring element (if None, uses 3x3 rectangle)
        iterations: Number of iterations
        border_type: Border type
        border_value: Value for constant border
        
    Returns:
        Dilated image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if kernel is None:
        kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (3, 3))
    
    return cv2.dilate(image, kernel, iterations=iterations,
                      borderType=border_type, borderValue=border_value)


def open(image: np.ndarray, kernel: Optional[np.ndarray] = None,
         iterations: int = 1, border_type: int = cv2.BORDER_CONSTANT,
         border_value: int = 0) -> np.ndarray:
    """
    Apply opening morphological operation (erosion followed by dilation).
    
    Args:
        image: Input image (binary or grayscale)
        kernel: Structuring element (if None, uses 3x3 rectangle)
        iterations: Number of iterations
        border_type: Border type
        border_value: Value for constant border
        
    Returns:
        Opened image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if kernel is None:
        kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (3, 3))
    
    return cv2.morphologyEx(image, cv2.MORPH_OPEN, kernel, iterations=iterations,
                           borderType=border_type, borderValue=border_value)


def close(image: np.ndarray, kernel: Optional[np.ndarray] = None,
          iterations: int = 1, border_type: int = cv2.BORDER_CONSTANT,
          border_value: int = 0) -> np.ndarray:
    """
    Apply closing morphological operation (dilation followed by erosion).
    
    Args:
        image: Input image (binary or grayscale)
        kernel: Structuring element (if None, uses 3x3 rectangle)
        iterations: Number of iterations
        border_type: Border type
        border_value: Value for constant border
        
    Returns:
        Closed image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if kernel is None:
        kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (3, 3))
    
    return cv2.morphologyEx(image, cv2.MORPH_CLOSE, kernel, iterations=iterations,
                           borderType=border_type, borderValue=border_value)


def morphological_gradient(image: np.ndarray, kernel: Optional[np.ndarray] = None,
                         border_type: int = cv2.BORDER_CONSTANT,
                         border_value: int = 0) -> np.ndarray:
    """
    Apply morphological gradient (dilation - erosion).
    
    Args:
        image: Input image (binary or grayscale)
        kernel: Structuring element (if None, uses 3x3 rectangle)
        border_type: Border type
        border_value: Value for constant border
        
    Returns:
        Morphological gradient image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if kernel is None:
        kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (3, 3))
    
    return cv2.morphologyEx(image, cv2.MORPH_GRADIENT, kernel,
                           borderType=border_type, borderValue=border_value)


def create_kernel(kernel_type: str, kernel_size: Tuple[int, int]) -> np.ndarray:
    """
    Create a morphological kernel.
    
    Args:
        kernel_type: Type of kernel ('rect', 'ellipse', 'cross')
        kernel_size: Size of the kernel (width, height)
        
    Returns:
        Morphological kernel
    """
    if kernel_type == 'rect':
        return cv2.getStructuringElement(cv2.MORPH_RECT, kernel_size)
    elif kernel_type == 'ellipse':
        return cv2.getStructuringElement(cv2.MORPH_ELLIPSE, kernel_size)
    elif kernel_type == 'cross':
        return cv2.getStructuringElement(cv2.MORPH_CROSS, kernel_size)
    else:
        raise ValueError(f"Unknown kernel type: {kernel_type}")


def apply_morphological_operation(image: np.ndarray, operation: str,
                                kernel: Optional[np.ndarray] = None,
                                iterations: int = 1) -> np.ndarray:
    """
    Apply a morphological operation by name.
    
    Args:
        image: Input image
        operation: Operation name ('erode', 'dilate', 'open', 'close', 'gradient')
        kernel: Structuring element
        iterations: Number of iterations
        
    Returns:
        Processed image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if kernel is None:
        kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (3, 3))
    
    if operation == 'erode':
        return erode(image, kernel, iterations)
    elif operation == 'dilate':
        return dilate(image, kernel, iterations)
    elif operation == 'open':
        return open(image, kernel, iterations)
    elif operation == 'close':
        return close(image, kernel, iterations)
    elif operation == 'gradient':
        return morphological_gradient(image, kernel)
    else:
        raise ValueError(f"Unknown operation: {operation}")


def multi_scale_morphology(image: np.ndarray, operation: str,
                          kernel_sizes: list = [(3, 3), (5, 5), (7, 7)]) -> dict:
    """
    Apply morphological operations at multiple scales.
    
    Args:
        image: Input image
        operation: Morphological operation
        kernel_sizes: List of kernel sizes
        
    Returns:
        Dictionary containing results at different scales
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    results = {}
    
    for size in kernel_sizes:
        kernel = cv2.getStructuringElement(cv2.MORPH_RECT, size)
        result = apply_morphological_operation(image, operation, kernel)
        results[f'size_{size[0]}x{size[1]}'] = result
    
    return results


def morphological_noise_removal(image: np.ndarray, kernel_size: int = 3) -> np.ndarray:
    """
    Remove noise using morphological operations.
    
    Args:
        image: Input image
        kernel_size: Size of the morphological kernel
        
    Returns:
        Denoised image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    # Create kernel
    kernel = cv2.getStructuringElement(cv2.MORPH_ELLIPSE, (kernel_size, kernel_size))
    
    # Apply opening to remove noise
    opened = open(gray, kernel)
    
    # Apply closing to fill gaps
    closed = close(opened, kernel)
    
    return closed


def morphological_edge_detection(image: np.ndarray, kernel_size: int = 3) -> np.ndarray:
    """
    Detect edges using morphological gradient.
    
    Args:
        image: Input image
        kernel_size: Size of the morphological kernel
        
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
    
    # Create kernel
    kernel = cv2.getStructuringElement(cv2.MORPH_ELLIPSE, (kernel_size, kernel_size))
    
    # Apply morphological gradient
    gradient = morphological_gradient(gray, kernel)
    
    return gradient


def compare_morphological_operations(image: np.ndarray, kernel_size: int = 3) -> dict:
    """
    Compare different morphological operations.
    
    Args:
        image: Input image
        kernel_size: Size of the morphological kernel
        
    Returns:
        Dictionary containing results from different operations
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    # Create kernel
    kernel = cv2.getStructuringElement(cv2.MORPH_ELLIPSE, (kernel_size, kernel_size))
    
    results = {
        'original': gray,
        'eroded': erode(gray, kernel),
        'dilated': dilate(gray, kernel),
        'opened': open(gray, kernel),
        'closed': close(gray, kernel),
        'gradient': morphological_gradient(gray, kernel)
    }
    
    return results 