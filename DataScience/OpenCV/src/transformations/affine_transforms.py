"""
Affine Transformations Module

This module provides various affine transformation implementations:
- Translation
- Rotation
- Scaling
- Shearing
- Combined affine transformations

Author: OpenCV Image Processing Collection
"""

import cv2
import numpy as np
from typing import Tuple, Optional, Union


def translate(image: np.ndarray, tx: float, ty: float, 
             border_mode: int = cv2.BORDER_CONSTANT, border_value: int = 0) -> np.ndarray:
    """
    Translate an image by given offsets.
    
    Args:
        image: Input image
        tx: Translation in x direction
        ty: Translation in y direction
        border_mode: Border mode for handling edge pixels
        border_value: Value for constant border mode
        
    Returns:
        Translated image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    h, w = image.shape[:2]
    translation_matrix = np.float32([[1, 0, tx], [0, 1, ty]])
    
    return cv2.warpAffine(image, translation_matrix, (w, h), 
                         borderMode=border_mode, borderValue=border_value)


def rotate(image: np.ndarray, angle: float, center: Optional[Tuple[int, int]] = None,
           scale: float = 1.0, border_mode: int = cv2.BORDER_CONSTANT, 
           border_value: int = 0) -> np.ndarray:
    """
    Rotate an image by given angle.
    
    Args:
        image: Input image
        angle: Rotation angle in degrees (positive = counterclockwise)
        center: Center of rotation (if None, uses image center)
        scale: Scale factor
        border_mode: Border mode for handling edge pixels
        border_value: Value for constant border mode
        
    Returns:
        Rotated image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    h, w = image.shape[:2]
    
    if center is None:
        center = (w // 2, h // 2)
    
    rotation_matrix = cv2.getRotationMatrix2D(center, angle, scale)
    
    return cv2.warpAffine(image, rotation_matrix, (w, h), 
                         borderMode=border_mode, borderValue=border_value)


def scale(image: np.ndarray, fx: float, fy: float, 
          interpolation: int = cv2.INTER_LINEAR) -> np.ndarray:
    """
    Scale an image by given factors.
    
    Args:
        image: Input image
        fx: Scale factor in x direction
        fy: Scale factor in y direction
        interpolation: Interpolation method
        
    Returns:
        Scaled image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    return cv2.resize(image, None, fx=fx, fy=fy, interpolation=interpolation)


def shear(image: np.ndarray, shear_x: float = 0, shear_y: float = 0,
          border_mode: int = cv2.BORDER_CONSTANT, border_value: int = 0) -> np.ndarray:
    """
    Apply shearing transformation to an image.
    
    Args:
        image: Input image
        shear_x: Shear factor in x direction
        shear_y: Shear factor in y direction
        border_mode: Border mode for handling edge pixels
        border_value: Value for constant border mode
        
    Returns:
        Sheared image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    h, w = image.shape[:2]
    
    # Create shear matrix
    shear_matrix = np.float32([[1, shear_x, 0], [shear_y, 1, 0]])
    
    return cv2.warpAffine(image, shear_matrix, (w, h), 
                         borderMode=border_mode, borderValue=border_value)


def affine_transform(image: np.ndarray, matrix: np.ndarray,
                    border_mode: int = cv2.BORDER_CONSTANT, border_value: int = 0) -> np.ndarray:
    """
    Apply custom affine transformation using a transformation matrix.
    
    Args:
        image: Input image
        matrix: 2x3 affine transformation matrix
        border_mode: Border mode for handling edge pixels
        border_value: Value for constant border mode
        
    Returns:
        Transformed image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if matrix.shape != (2, 3):
        raise ValueError("Matrix must be 2x3")
    
    h, w = image.shape[:2]
    
    return cv2.warpAffine(image, matrix, (w, h), 
                         borderMode=border_mode, borderValue=border_value)


def get_affine_matrix(src_points: np.ndarray, dst_points: np.ndarray) -> np.ndarray:
    """
    Get affine transformation matrix from source and destination points.
    
    Args:
        src_points: Source points (3x2 array)
        dst_points: Destination points (3x2 array)
        
    Returns:
        Affine transformation matrix
    """
    if src_points.shape != (3, 2) or dst_points.shape != (3, 2):
        raise ValueError("Points must be 3x2 arrays")
    
    return cv2.getAffineTransform(src_points.astype(np.float32), 
                                 dst_points.astype(np.float32))


def apply_affine_from_points(image: np.ndarray, src_points: np.ndarray, 
                           dst_points: np.ndarray) -> np.ndarray:
    """
    Apply affine transformation using source and destination points.
    
    Args:
        image: Input image
        src_points: Source points (3x2 array)
        dst_points: Destination points (3x2 array)
        
    Returns:
        Transformed image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    matrix = get_affine_matrix(src_points, dst_points)
    return affine_transform(image, matrix)


def rotate_with_padding(image: np.ndarray, angle: float, 
                       border_mode: int = cv2.BORDER_CONSTANT, 
                       border_value: int = 0) -> np.ndarray:
    """
    Rotate image with automatic padding to avoid cropping.
    
    Args:
        image: Input image
        angle: Rotation angle in degrees
        border_mode: Border mode for handling edge pixels
        border_value: Value for constant border mode
        
    Returns:
        Rotated image with padding
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    h, w = image.shape[:2]
    
    # Calculate new dimensions
    angle_rad = np.radians(angle)
    cos_a = abs(np.cos(angle_rad))
    sin_a = abs(np.sin(angle_rad))
    
    new_w = int(w * cos_a + h * sin_a)
    new_h = int(h * cos_a + w * sin_a)
    
    # Create rotation matrix
    center = (w // 2, h // 2)
    rotation_matrix = cv2.getRotationMatrix2D(center, angle, 1.0)
    
    # Adjust translation to center the result
    rotation_matrix[0, 2] += (new_w - w) // 2
    rotation_matrix[1, 2] += (new_h - h) // 2
    
    return cv2.warpAffine(image, rotation_matrix, (new_w, new_h), 
                         borderMode=border_mode, borderValue=border_value)


def multi_scale_rotation(image: np.ndarray, angles: list = [0, 45, 90, 135, 180]) -> dict:
    """
    Apply rotation at multiple angles.
    
    Args:
        image: Input image
        angles: List of rotation angles
        
    Returns:
        Dictionary containing rotated images
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    results = {}
    
    for angle in angles:
        rotated = rotate(image, angle)
        results[f'angle_{angle}'] = rotated
    
    return results


def chain_transformations(image: np.ndarray, transformations: list) -> np.ndarray:
    """
    Apply a chain of transformations.
    
    Args:
        image: Input image
        transformations: List of transformation functions with parameters
        
    Returns:
        Transformed image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    result = image.copy()
    
    for transform_tuple in transformations:
        transform_func = transform_tuple[0]
        args = transform_tuple[1:] if len(transform_tuple) > 1 else []
        result = transform_func(result, *args)
    
    return result 