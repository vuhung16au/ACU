"""
Image Warping Module

This module provides image warping and distortion correction implementations:
- Image warping
- Barrel distortion correction
- Pincushion distortion correction
- Custom warping functions

Author: Vu Hung Nguyen
"""

import cv2
import numpy as np
from typing import Tuple, Optional, Union


def warp_image(image: np.ndarray, transformation_matrix: np.ndarray,
              output_size: Optional[Tuple[int, int]] = None,
              border_mode: int = cv2.BORDER_CONSTANT, border_value: int = 0) -> np.ndarray:
    """
    Apply custom warping transformation to an image.
    
    Args:
        image: Input image
        transformation_matrix: Warping transformation matrix
        output_size: Size of output image (width, height)
        border_mode: Border mode for handling edge pixels
        border_value: Value for constant border mode
        
    Returns:
        Warped image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if transformation_matrix.shape != (3, 3):
        raise ValueError("Transformation matrix must be 3x3")
    
    if output_size is None:
        h, w = image.shape[:2]
        output_size = (w, h)
    
    return cv2.warpPerspective(image, transformation_matrix, output_size,
                              borderMode=border_mode, borderValue=border_value)


def barrel_distortion_correction(image: np.ndarray, k1: float = 0.1, k2: float = 0.05,
                               k3: float = 0.0, p1: float = 0.0, p2: float = 0.0) -> np.ndarray:
    """
    Correct barrel distortion in an image.
    
    Args:
        image: Input image
        k1: First radial distortion coefficient
        k2: Second radial distortion coefficient
        k3: Third radial distortion coefficient
        p1: First tangential distortion coefficient
        p2: Second tangential distortion coefficient
        
    Returns:
        Corrected image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    h, w = image.shape[:2]
    
    # Create camera matrix
    camera_matrix = np.array([[w, 0, w/2],
                             [0, w, h/2],
                             [0, 0, 1]], dtype=np.float32)
    
    # Create distortion coefficients
    dist_coeffs = np.array([k1, k2, p1, p2, k3], dtype=np.float32)
    
    # Get optimal camera matrix
    new_camera_matrix, roi = cv2.getOptimalNewCameraMatrix(camera_matrix, dist_coeffs, (w, h), 1, (w, h))
    
    # Undistort image
    undistorted = cv2.undistort(image, camera_matrix, dist_coeffs, None, new_camera_matrix)
    
    return undistorted


def pincushion_distortion_correction(image: np.ndarray, k1: float = -0.1, k2: float = 0.05,
                                   k3: float = 0.0, p1: float = 0.0, p2: float = 0.0) -> np.ndarray:
    """
    Correct pincushion distortion in an image.
    
    Args:
        image: Input image
        k1: First radial distortion coefficient (negative for pincushion)
        k2: Second radial distortion coefficient
        k3: Third radial distortion coefficient
        p1: First tangential distortion coefficient
        p2: Second tangential distortion coefficient
        
    Returns:
        Corrected image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    h, w = image.shape[:2]
    
    # Create camera matrix
    camera_matrix = np.array([[w, 0, w/2],
                             [0, w, h/2],
                             [0, 0, 1]], dtype=np.float32)
    
    # Create distortion coefficients
    dist_coeffs = np.array([k1, k2, p1, p2, k3], dtype=np.float32)
    
    # Get optimal camera matrix
    new_camera_matrix, roi = cv2.getOptimalNewCameraMatrix(camera_matrix, dist_coeffs, (w, h), 1, (w, h))
    
    # Undistort image
    undistorted = cv2.undistort(image, camera_matrix, dist_coeffs, None, new_camera_matrix)
    
    return undistorted


def custom_warping(image: np.ndarray, warp_function, output_size: Optional[Tuple[int, int]] = None) -> np.ndarray:
    """
    Apply custom warping function to an image.
    
    Args:
        image: Input image
        warp_function: Function that takes (x, y) and returns (u, v)
        output_size: Size of output image (width, height)
        
    Returns:
        Warped image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if output_size is None:
        h, w = image.shape[:2]
        output_size = (w, h)
    
    output_w, output_h = output_size
    
    # Create coordinate grids
    x_coords, y_coords = np.meshgrid(np.arange(output_w), np.arange(output_h))
    
    # Apply warping function
    u_coords, v_coords = warp_function(x_coords, y_coords)
    
    # Ensure coordinates are within image bounds
    u_coords = np.clip(u_coords, 0, image.shape[1] - 1)
    v_coords = np.clip(v_coords, 0, image.shape[0] - 1)
    
    # Sample from original image
    warped = cv2.remap(image, u_coords.astype(np.float32), v_coords.astype(np.float32),
                       cv2.INTER_LINEAR)
    
    return warped


def spherical_warping(image: np.ndarray, center: Tuple[int, int], radius: float) -> np.ndarray:
    """
    Apply spherical warping to an image.
    
    Args:
        image: Input image
        center: Center of the spherical transformation
        radius: Radius of the sphere
        
    Returns:
        Spherically warped image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    h, w = image.shape[:2]
    cx, cy = center
    
    def spherical_warp(x, y):
        # Convert to polar coordinates
        dx = x - cx
        dy = y - cy
        r = np.sqrt(dx**2 + dy**2)
        
        # Apply spherical transformation
        theta = np.arctan2(dy, dx)
        r_new = radius * np.arcsin(r / radius)
        
        # Convert back to Cartesian coordinates
        u = cx + r_new * np.cos(theta)
        v = cy + r_new * np.sin(theta)
        
        return u, v
    
    return custom_warping(image, spherical_warp, (w, h))


def fisheye_correction(image: np.ndarray, k1: float = 0.3, k2: float = 0.1) -> np.ndarray:
    """
    Correct fisheye lens distortion.
    
    Args:
        image: Input image
        k1: First fisheye distortion coefficient
        k2: Second fisheye distortion coefficient
        
    Returns:
        Corrected image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    h, w = image.shape[:2]
    
    # Create camera matrix
    camera_matrix = np.array([[w, 0, w/2],
                             [0, w, h/2],
                             [0, 0, 1]], dtype=np.float32)
    
    # Create fisheye distortion coefficients
    dist_coeffs = np.array([k1, k2, 0, 0], dtype=np.float32)
    
    # Get optimal camera matrix
    new_camera_matrix, roi = cv2.getOptimalNewCameraMatrix(camera_matrix, dist_coeffs, (w, h), 1, (w, h))
    
    # Undistort image
    undistorted = cv2.undistort(image, camera_matrix, dist_coeffs, None, new_camera_matrix)
    
    return undistorted


def elastic_deformation(image: np.ndarray, alpha: float = 1.0, sigma: float = 50.0) -> np.ndarray:
    """
    Apply elastic deformation to an image.
    
    Args:
        image: Input image
        alpha: Amplitude of the deformation
        sigma: Standard deviation of the Gaussian filter
        
    Returns:
        Elastically deformed image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    h, w = image.shape[:2]
    
    # Create random displacement fields
    dx = np.random.randn(h, w) * alpha
    dy = np.random.randn(h, w) * alpha
    
    # Apply Gaussian smoothing
    dx = cv2.GaussianBlur(dx, (0, 0), sigma)
    dy = cv2.GaussianBlur(dy, (0, 0), sigma)
    
    # Create coordinate grids
    x_coords, y_coords = np.meshgrid(np.arange(w), np.arange(h))
    
    # Apply displacement
    u_coords = x_coords + dx
    v_coords = y_coords + dy
    
    # Ensure coordinates are within bounds
    u_coords = np.clip(u_coords, 0, w - 1)
    v_coords = np.clip(v_coords, 0, h - 1)
    
    # Apply warping
    deformed = cv2.remap(image, u_coords.astype(np.float32), v_coords.astype(np.float32),
                         cv2.INTER_LINEAR)
    
    return deformed


def perspective_warping(image: np.ndarray, src_points: np.ndarray, 
                      dst_points: np.ndarray) -> np.ndarray:
    """
    Apply perspective warping to an image.
    
    Args:
        image: Input image
        src_points: Source points (4x2 array)
        dst_points: Destination points (4x2 array)
        
    Returns:
        Perspective warped image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if src_points.shape != (4, 2) or dst_points.shape != (4, 2):
        raise ValueError("Points must be 4x2 arrays")
    
    # Get perspective transformation matrix
    matrix = cv2.getPerspectiveTransform(src_points.astype(np.float32), 
                                       dst_points.astype(np.float32))
    
    h, w = image.shape[:2]
    return cv2.warpPerspective(image, matrix, (w, h))


def create_warping_matrix(transformation_type: str, parameters: dict) -> np.ndarray:
    """
    Create a warping transformation matrix.
    
    Args:
        transformation_type: Type of transformation ('rotation', 'scaling', 'translation')
        parameters: Parameters for the transformation
        
    Returns:
        Transformation matrix
    """
    if transformation_type == 'rotation':
        angle = parameters.get('angle', 0)
        center = parameters.get('center', (0, 0))
        scale = parameters.get('scale', 1.0)
        return cv2.getRotationMatrix2D(center, angle, scale)
    
    elif transformation_type == 'scaling':
        fx = parameters.get('fx', 1.0)
        fy = parameters.get('fy', 1.0)
        return np.float32([[fx, 0, 0], [0, fy, 0]])
    
    elif transformation_type == 'translation':
        tx = parameters.get('tx', 0)
        ty = parameters.get('ty', 0)
        return np.float32([[1, 0, tx], [0, 1, ty]])
    
    else:
        raise ValueError(f"Unknown transformation type: {transformation_type}") 