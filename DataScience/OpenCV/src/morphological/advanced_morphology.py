"""
Advanced Morphological Operations Module

This module provides advanced morphological operations:
- Top hat transformation
- Black hat transformation
- Skeletonization
- Thinning
- Morphological reconstruction

Author: Vu Hung Nguyen
"""

import cv2
import numpy as np
from typing import Tuple, Optional, Union
from .basic_morphology import erode, dilate, open, close


def top_hat(image: np.ndarray, kernel: Optional[np.ndarray] = None,
            border_type: int = cv2.BORDER_CONSTANT, border_value: int = 0) -> np.ndarray:
    """
    Apply top hat morphological operation (image - opening).
    
    Args:
        image: Input image (binary or grayscale)
        kernel: Structuring element (if None, uses 3x3 rectangle)
        border_type: Border type
        border_value: Value for constant border
        
    Returns:
        Top hat transformed image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if kernel is None:
        kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (3, 3))
    
    return cv2.morphologyEx(image, cv2.MORPH_TOPHAT, kernel,
                           borderType=border_type, borderValue=border_value)


def black_hat(image: np.ndarray, kernel: Optional[np.ndarray] = None,
              border_type: int = cv2.BORDER_CONSTANT, border_value: int = 0) -> np.ndarray:
    """
    Apply black hat morphological operation (closing - image).
    
    Args:
        image: Input image (binary or grayscale)
        kernel: Structuring element (if None, uses 3x3 rectangle)
        border_type: Border type
        border_value: Value for constant border
        
    Returns:
        Black hat transformed image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if kernel is None:
        kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (3, 3))
    
    return cv2.morphologyEx(image, cv2.MORPH_BLACKHAT, kernel,
                           borderType=border_type, borderValue=border_value)


def skeletonize(image: np.ndarray, kernel: Optional[np.ndarray] = None) -> np.ndarray:
    """
    Skeletonize a binary image using morphological operations.
    
    Args:
        image: Input binary image
        kernel: Structuring element (if None, uses 3x3 cross)
        
    Returns:
        Skeletonized image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Ensure binary image
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    # Threshold to binary
    _, binary = cv2.threshold(gray, 127, 255, cv2.THRESH_BINARY)
    
    if kernel is None:
        kernel = cv2.getStructuringElement(cv2.MORPH_CROSS, (3, 3))
    
    skeleton = np.zeros_like(binary)
    done = False
    
    while not done:
        # Erosion
        eroded = erode(binary, kernel)
        # Opening of eroded image
        opened = open(eroded, kernel)
        # Subtraction
        temp = cv2.subtract(eroded, opened)
        # Union with skeleton
        skeleton = cv2.bitwise_or(skeleton, temp)
        # Update binary image
        binary = eroded.copy()
        
        # Check if done
        if cv2.countNonZero(binary) == 0:
            done = True
    
    return skeleton


def thin(image: np.ndarray, kernel: Optional[np.ndarray] = None) -> np.ndarray:
    """
    Thin a binary image using Zhang-Suen thinning algorithm.
    
    Args:
        image: Input binary image
        kernel: Structuring element (if None, uses 3x3 cross)
        
    Returns:
        Thinned image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Ensure binary image
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    # Threshold to binary
    _, binary = cv2.threshold(gray, 127, 1, cv2.THRESH_BINARY)
    
    # Zhang-Suen thinning algorithm
    def zhang_suen_thinning(img):
        # Create a copy of the image
        img_thinned = img.copy().astype(np.uint8)
        changing1 = changing2 = 1
        
        while changing1 or changing2:
            # Step 1
            changing1 = []
            rows, cols = img_thinned.shape
            for i in range(1, rows - 1):
                for j in range(1, cols - 1):
                    p2, p3, p4, p5, p6, p7, p8, p9 = neighbors = [
                        img_thinned[i-1][j], img_thinned[i-1][j+1], img_thinned[i][j+1], img_thinned[i+1][j+1],
                        img_thinned[i+1][j], img_thinned[i+1][j-1], img_thinned[i][j-1], img_thinned[i-1][j-1]
                    ]
                    if (img_thinned[i][j] == 1 and 2 <= sum(neighbors) <= 6 and
                        neighbors[0] * neighbors[2] * neighbors[4] == 0 and
                        neighbors[2] * neighbors[4] * neighbors[6] == 0):
                        changing1.append((i, j))
            
            for i, j in changing1:
                img_thinned[i][j] = 0
            
            # Step 2
            changing2 = []
            for i in range(1, rows - 1):
                for j in range(1, cols - 1):
                    p2, p3, p4, p5, p6, p7, p8, p9 = neighbors = [
                        img_thinned[i-1][j], img_thinned[i-1][j+1], img_thinned[i][j+1], img_thinned[i+1][j+1],
                        img_thinned[i+1][j], img_thinned[i+1][j-1], img_thinned[i][j-1], img_thinned[i-1][j-1]
                    ]
                    if (img_thinned[i][j] == 1 and 2 <= sum(neighbors) <= 6 and
                        neighbors[0] * neighbors[2] * neighbors[6] == 0 and
                        neighbors[0] * neighbors[4] * neighbors[6] == 0):
                        changing2.append((i, j))
            
            for i, j in changing2:
                img_thinned[i][j] = 0
        
        return img_thinned * 255
    
    return zhang_suen_thinning(binary)


def morphological_reconstruction(marker: np.ndarray, mask: np.ndarray,
                              kernel: Optional[np.ndarray] = None) -> np.ndarray:
    """
    Perform morphological reconstruction.
    
    Args:
        marker: Marker image
        mask: Mask image
        kernel: Structuring element (if None, uses 3x3 rectangle)
        
    Returns:
        Reconstructed image
    """
    if marker is None or mask is None:
        raise ValueError("Marker and mask images cannot be None")
    
    if kernel is None:
        kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (3, 3))
    
    # Ensure marker <= mask
    marker = np.minimum(marker, mask)
    
    # Iterative dilation with mask constraint
    prev = np.zeros_like(marker)
    current = marker.copy()
    
    while not np.array_equal(prev, current):
        prev = current.copy()
        current = dilate(current, kernel)
        current = np.minimum(current, mask)
    
    return current


def h_dome_transform(image: np.ndarray, h: float = 10,
                    kernel: Optional[np.ndarray] = None) -> np.ndarray:
    """
    Apply H-dome transformation.
    
    Args:
        image: Input image
        h: Height parameter
        kernel: Structuring element (if None, uses 3x3 rectangle)
        
    Returns:
        H-dome transformed image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if kernel is None:
        kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (3, 3))
    
    # Create marker
    marker = image - h
    
    # Perform reconstruction
    reconstructed = morphological_reconstruction(marker, image, kernel)
    
    # H-dome is the difference
    h_dome = image - reconstructed
    
    return h_dome


def watershed_morphology(image: np.ndarray, markers: np.ndarray,
                        kernel: Optional[np.ndarray] = None) -> np.ndarray:
    """
    Apply watershed segmentation with morphological operations.
    
    Args:
        image: Input image
        markers: Marker image
        kernel: Structuring element (if None, uses 3x3 rectangle)
        
    Returns:
        Watershed segmented image
    """
    if image is None or markers is None:
        raise ValueError("Image and markers cannot be None")
    
    if kernel is None:
        kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (3, 3))
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    # Apply watershed
    markers = markers.astype(np.int32)
    watershed_result = cv2.watershed(image, markers)
    
    return watershed_result


def morphological_granulometry(image: np.ndarray, kernel_sizes: list = [3, 5, 7, 9]) -> dict:
    """
    Perform morphological granulometry analysis.
    
    Args:
        image: Input image
        kernel_sizes: List of kernel sizes for analysis
        
    Returns:
        Dictionary containing granulometry results
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    results = {}
    
    for size in kernel_sizes:
        kernel = cv2.getStructuringElement(cv2.MORPH_ELLIPSE, (size, size))
        
        # Opening
        opened = open(gray, kernel)
        # Closing
        closed = close(gray, kernel)
        
        results[f'size_{size}_opened'] = opened
        results[f'size_{size}_closed'] = closed
        results[f'size_{size}_opening_area'] = np.sum(opened > 0)
        results[f'size_{size}_closing_area'] = np.sum(closed > 0)
    
    return results


def morphological_texture_analysis(image: np.ndarray, kernel_sizes: list = [3, 5, 7]) -> dict:
    """
    Perform morphological texture analysis.
    
    Args:
        image: Input image
        kernel_sizes: List of kernel sizes for analysis
        
    Returns:
        Dictionary containing texture analysis results
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    results = {}
    
    for size in kernel_sizes:
        kernel = cv2.getStructuringElement(cv2.MORPH_ELLIPSE, (size, size))
        
        # Top hat
        tophat = top_hat(gray, kernel)
        # Black hat
        blackhat = black_hat(gray, kernel)
        # Gradient
        gradient = cv2.morphologyEx(gray, cv2.MORPH_GRADIENT, kernel)
        
        results[f'size_{size}_tophat'] = tophat
        results[f'size_{size}_blackhat'] = blackhat
        results[f'size_{size}_gradient'] = gradient
        results[f'size_{size}_tophat_mean'] = np.mean(tophat)
        results[f'size_{size}_blackhat_mean'] = np.mean(blackhat)
        results[f'size_{size}_gradient_mean'] = np.mean(gradient)
    
    return results


def compare_advanced_morphology(image: np.ndarray, kernel_size: int = 3) -> dict:
    """
    Compare different advanced morphological operations.
    
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
        'tophat': top_hat(gray, kernel),
        'blackhat': black_hat(gray, kernel),
        'skeleton': skeletonize(gray, kernel),
        'thinned': thin(gray, kernel)
    }
    
    return results


def find_endpoints(skeleton: np.ndarray) -> np.ndarray:
    """
    Find endpoints in a skeletonized binary image.
    
    Args:
        skeleton: Binary skeleton image
        
    Returns:
        Binary image with endpoint pixels marked
    """
    if skeleton is None:
        raise ValueError("Input skeleton cannot be None")
    
    # Convert to binary if needed
    if len(skeleton.shape) == 3:
        skeleton = cv2.cvtColor(skeleton, cv2.COLOR_BGR2GRAY)
    
    # Ensure binary
    _, skeleton = cv2.threshold(skeleton, 127, 255, cv2.THRESH_BINARY)
    
    # Define kernel for endpoint detection
    # An endpoint has only one neighbor
    kernel = np.array([[1, 1, 1],
                      [1, 0, 1],
                      [1, 1, 1]], dtype=np.uint8)
    
    # Count neighbors for each pixel
    neighbors = cv2.filter2D(skeleton, -1, kernel)
    
    # Endpoints have exactly one neighbor
    endpoints = np.zeros_like(skeleton)
    endpoints[(skeleton == 255) & (neighbors == 255)] = 255
    
    return endpoints


def find_branch_points(skeleton: np.ndarray) -> np.ndarray:
    """
    Find branch points in a skeletonized binary image.
    
    Args:
        skeleton: Binary skeleton image
        
    Returns:
        Binary image with branch point pixels marked
    """
    if skeleton is None:
        raise ValueError("Input skeleton cannot be None")
    
    # Convert to binary if needed
    if len(skeleton.shape) == 3:
        skeleton = cv2.cvtColor(skeleton, cv2.COLOR_BGR2GRAY)
    
    # Ensure binary
    _, skeleton = cv2.threshold(skeleton, 127, 255, cv2.THRESH_BINARY)
    
    # Define kernel for branch point detection
    kernel = np.array([[1, 1, 1],
                      [1, 0, 1],
                      [1, 1, 1]], dtype=np.uint8)
    
    # Count neighbors for each pixel
    neighbors = cv2.filter2D(skeleton, -1, kernel)
    
    # Branch points have 3 or more neighbors
    branch_points = np.zeros_like(skeleton)
    branch_points[(skeleton == 255) & (neighbors >= 3 * 255)] = 255
    
    return branch_points


def morphological_gradient(image: np.ndarray, kernel: Optional[np.ndarray] = None) -> np.ndarray:
    """
    Apply morphological gradient operation (dilation - erosion).
    
    Args:
        image: Input image
        kernel: Structuring element (if None, uses 3x3 rectangle)
        
    Returns:
        Morphological gradient image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if kernel is None:
        kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (3, 3))
    
    return cv2.morphologyEx(image, cv2.MORPH_GRADIENT, kernel) 