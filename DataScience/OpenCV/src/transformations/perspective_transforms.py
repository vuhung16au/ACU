"""
Perspective Transformations Module

This module provides perspective transformation implementations:
- Perspective transformation
- Homography transformation
- Image rectification

Author: OpenCV Image Processing Collection
"""

import cv2
import numpy as np
from typing import Tuple, Optional, Union


def perspective_transform(image: np.ndarray, src_points: np.ndarray, 
                        dst_points: np.ndarray, output_size: Optional[Tuple[int, int]] = None) -> np.ndarray:
    """
    Apply perspective transformation to an image.
    
    Args:
        image: Input image
        src_points: Source points (4x2 array)
        dst_points: Destination points (4x2 array)
        output_size: Size of output image (width, height)
        
    Returns:
        Perspective transformed image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if src_points.shape != (4, 2) or dst_points.shape != (4, 2):
        raise ValueError("Points must be 4x2 arrays")
    
    # Get perspective transformation matrix
    matrix = cv2.getPerspectiveTransform(src_points.astype(np.float32), 
                                       dst_points.astype(np.float32))
    
    if output_size is None:
        h, w = image.shape[:2]
        output_size = (w, h)
    
    return cv2.warpPerspective(image, matrix, output_size)


def homography_transform(image: np.ndarray, homography_matrix: np.ndarray,
                       output_size: Optional[Tuple[int, int]] = None) -> np.ndarray:
    """
    Apply homography transformation to an image.
    
    Args:
        image: Input image
        homography_matrix: 3x3 homography matrix
        output_size: Size of output image (width, height)
        
    Returns:
        Transformed image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if homography_matrix.shape != (3, 3):
        raise ValueError("Homography matrix must be 3x3")
    
    if output_size is None:
        h, w = image.shape[:2]
        output_size = (w, h)
    
    return cv2.warpPerspective(image, homography_matrix, output_size)


def find_homography(src_points: np.ndarray, dst_points: np.ndarray, 
                   method: int = cv2.RANSAC, ransac_reproj_thresh: float = 3.0,
                   max_iters: int = 2000, confidence: float = 0.995) -> Tuple[np.ndarray, np.ndarray]:
    """
    Find homography matrix between two sets of points.
    
    Args:
        src_points: Source points
        dst_points: Destination points
        method: Method for finding homography
        ransac_reproj_thresh: Maximum allowed reprojection error
        max_iters: Maximum number of RANSAC iterations
        confidence: Confidence level
        
    Returns:
        Tuple of (homography_matrix, mask)
    """
    if src_points.shape != dst_points.shape:
        raise ValueError("Source and destination points must have same shape")
    
    if len(src_points) < 4:
        raise ValueError("At least 4 points are required")
    
    return cv2.findHomography(src_points.astype(np.float32), 
                             dst_points.astype(np.float32), 
                             method, ransac_reproj_thresh, 
                             maxIters=max_iters, confidence=confidence)


def rectify_image(image: np.ndarray, src_points: np.ndarray, 
                 target_width: int, target_height: int) -> np.ndarray:
    """
    Rectify an image to a rectangular shape.
    
    Args:
        image: Input image
        src_points: Source points defining the region to rectify
        target_width: Width of the rectified image
        target_height: Height of the rectified image
        
    Returns:
        Rectified image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if src_points.shape != (4, 2):
        raise ValueError("Source points must be 4x2 array")
    
    # Define destination points for rectangular output
    dst_points = np.float32([
        [0, 0],
        [target_width, 0],
        [target_width, target_height],
        [0, target_height]
    ])
    
    return perspective_transform(image, src_points, dst_points, (target_width, target_height))


def bird_eye_view(image: np.ndarray, src_points: np.ndarray, 
                  target_width: int, target_height: int) -> np.ndarray:
    """
    Create a bird's eye view of an image.
    
    Args:
        image: Input image
        src_points: Source points defining the region
        target_width: Width of the output image
        target_height: Height of the output image
        
    Returns:
        Bird's eye view image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if src_points.shape != (4, 2):
        raise ValueError("Source points must be 4x2 array")
    
    # Define destination points for bird's eye view
    dst_points = np.float32([
        [0, 0],
        [target_width, 0],
        [target_width, target_height],
        [0, target_height]
    ])
    
    return perspective_transform(image, src_points, dst_points, (target_width, target_height))


def perspective_correction(image: np.ndarray, corners: np.ndarray,
                         target_width: int, target_height: int) -> np.ndarray:
    """
    Correct perspective distortion in an image.
    
    Args:
        image: Input image
        corners: Four corner points of the region to correct
        target_width: Width of the corrected image
        target_height: Height of the corrected image
        
    Returns:
        Perspective corrected image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if corners.shape != (4, 2):
        raise ValueError("Corners must be 4x2 array")
    
    # Sort corners in order: top-left, top-right, bottom-right, bottom-left
    corners_sorted = sort_corners(corners)
    
    # Define destination points
    dst_points = np.float32([
        [0, 0],
        [target_width, 0],
        [target_width, target_height],
        [0, target_height]
    ])
    
    return perspective_transform(image, corners_sorted, dst_points, (target_width, target_height))


def sort_corners(corners: np.ndarray) -> np.ndarray:
    """
    Sort corners in order: top-left, top-right, bottom-right, bottom-left.
    
    Args:
        corners: Array of 4 corner points
        
    Returns:
        Sorted corners
    """
    if corners.shape != (4, 2):
        raise ValueError("Corners must be 4x2 array")
    
    # Calculate centroid
    centroid = np.mean(corners, axis=0)
    
    # Separate top and bottom points
    top_points = []
    bottom_points = []
    
    for corner in corners:
        if corner[1] < centroid[1]:
            top_points.append(corner)
        else:
            bottom_points.append(corner)
    
    # Sort top points (left to right)
    top_points = sorted(top_points, key=lambda p: p[0])
    
    # Sort bottom points (left to right)
    bottom_points = sorted(bottom_points, key=lambda p: p[0])
    
    # Return in order: top-left, top-right, bottom-right, bottom-left
    return np.float32([top_points[0], top_points[1], bottom_points[1], bottom_points[0]])


def estimate_perspective_transform(src_points: np.ndarray, dst_points: np.ndarray) -> np.ndarray:
    """
    Estimate perspective transformation matrix.
    
    Args:
        src_points: Source points
        dst_points: Destination points
        
    Returns:
        Perspective transformation matrix
    """
    if src_points.shape != (4, 2) or dst_points.shape != (4, 2):
        raise ValueError("Points must be 4x2 arrays")
    
    return cv2.getPerspectiveTransform(src_points.astype(np.float32), 
                                     dst_points.astype(np.float32))


def apply_perspective_with_matrix(image: np.ndarray, matrix: np.ndarray,
                                output_size: Optional[Tuple[int, int]] = None) -> np.ndarray:
    """
    Apply perspective transformation using a pre-computed matrix.
    
    Args:
        image: Input image
        matrix: Perspective transformation matrix
        output_size: Size of output image (width, height)
        
    Returns:
        Transformed image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if matrix.shape != (3, 3):
        raise ValueError("Matrix must be 3x3")
    
    if output_size is None:
        h, w = image.shape[:2]
        output_size = (w, h)
    
    return cv2.warpPerspective(image, matrix, output_size) 