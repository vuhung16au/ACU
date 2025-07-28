"""
Corner Detection Module

This module provides various corner detection algorithms:
- Harris corner detector
- Shi-Tomasi corner detector
- FAST corner detector

Author: OpenCV Image Processing Collection
"""

import cv2
import numpy as np
from typing import Tuple, Optional, Union


def harris_corners(image: np.ndarray, block_size: int = 2, ksize: int = 3,
                   k: float = 0.04, threshold: float = 0.01) -> Tuple[np.ndarray, np.ndarray]:
    """
    Detect corners using Harris corner detector.
    
    Args:
        image: Input image (grayscale)
        block_size: Size of neighborhood considered
        ksize: Aperture parameter for Sobel operator
        k: Harris detector free parameter
        threshold: Threshold for corner detection
        
    Returns:
        Tuple of (corners, response image)
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    # Convert to float32
    gray = np.float32(gray)
    
    # Apply Harris corner detection
    response = cv2.cornerHarris(gray, block_size, ksize, k)
    
    # Dilate to mark the corners
    response = cv2.dilate(response, None)
    
    # Threshold for an optimal value
    corners = response > threshold * response.max()
    
    return corners, response


def shi_tomasi_corners(image: np.ndarray, max_corners: int = 25,
                       quality_level: float = 0.01, min_distance: float = 10,
                       block_size: int = 3, use_harris_detector: bool = False,
                       k: float = 0.04) -> np.ndarray:
    """
    Detect corners using Shi-Tomasi corner detector.
    
    Args:
        image: Input image (grayscale)
        max_corners: Maximum number of corners to return
        quality_level: Minimum quality level below which everyone is rejected
        min_distance: Minimum possible euclidean distance between corners
        block_size: Size of an average block for computing a derivative covariation matrix
        use_harris_detector: Whether to use Harris detector or not
        k: Free parameter of the Harris detector
        
    Returns:
        Array of corner points
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    # Apply Shi-Tomasi corner detection
    corners = cv2.goodFeaturesToTrack(gray, max_corners, quality_level, min_distance,
                                     blockSize=block_size, useHarrisDetector=use_harris_detector, k=k)
    
    return corners


def fast_corners(image: np.ndarray, threshold: int = 10,
                 non_max_suppression: bool = True, type_: int = cv2.FAST_FEATURE_DETECTOR_TYPE_9_16) -> Tuple[np.ndarray, np.ndarray]:
    """
    Detect corners using FAST corner detector.
    
    Args:
        image: Input image (grayscale)
        threshold: Threshold on difference between intensity of the central pixel and pixels on a circle around this pixel
        non_max_suppression: If true, non-maximum suppression is applied to detected corners
        type_: One of the three neighborhoods, cv2.FAST_FEATURE_DETECTOR_TYPE_9_16, cv2.FAST_FEATURE_DETECTOR_TYPE_7_12, cv2.FAST_FEATURE_DETECTOR_TYPE_5_8
        
    Returns:
        Tuple of (keypoints, response image)
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    # Create FAST detector
    fast = cv2.FastFeatureDetector_create(threshold=threshold, 
                                         nonmaxSuppression=non_max_suppression, 
                                         type=type_)
    
    # Detect keypoints
    keypoints = fast.detect(gray, None)
    
    # Create response image
    response = np.zeros_like(gray)
    for kp in keypoints:
        x, y = int(kp.pt[0]), int(kp.pt[1])
        if 0 <= x < response.shape[1] and 0 <= y < response.shape[0]:
            response[y, x] = kp.response
    
    return keypoints, response


def corner_subpix(image: np.ndarray, corners: np.ndarray,
                  win_size: Tuple[int, int] = (11, 11),
                  zero_zone: Tuple[int, int] = (-1, -1),
                  criteria: Tuple[int, int, float] = (cv2.TERM_CRITERIA_EPS + cv2.TERM_CRITERIA_MAX_ITER, 30, 0.001)) -> np.ndarray:
    """
    Refine corner locations to subpixel accuracy.
    
    Args:
        image: Input image
        corners: Initial coordinates of the input corners
        win_size: Half of the side length of the search window
        zero_zone: Half of the size of the dead region in the middle of the search zone
        criteria: Criteria for termination of the iterative search algorithm
        
    Returns:
        Refined corner coordinates
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if corners is None or len(corners) == 0:
        return corners
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    # Refine corners
    refined_corners = cv2.cornerSubPix(gray, corners, win_size, zero_zone, criteria)
    
    return refined_corners


def detect_corners_multi_scale(image: np.ndarray, scales: list = [1.0, 0.75, 0.5],
                              detector: str = 'harris', **kwargs) -> dict:
    """
    Detect corners at multiple scales.
    
    Args:
        image: Input image
        scales: List of scale factors
        detector: Corner detector to use ('harris', 'shi_tomasi', 'fast')
        **kwargs: Additional arguments for the detector
        
    Returns:
        Dictionary containing corners at different scales
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    results = {}
    
    for scale in scales:
        # Resize image
        h, w = image.shape[:2]
        new_h, new_w = int(h * scale), int(w * scale)
        resized = cv2.resize(image, (new_w, new_h))
        
        # Detect corners
        if detector == 'harris':
            corners, response = harris_corners(resized, **kwargs)
        elif detector == 'shi_tomasi':
            corners = shi_tomasi_corners(resized, **kwargs)
        elif detector == 'fast':
            corners, response = fast_corners(resized, **kwargs)
        else:
            raise ValueError(f"Unknown detector: {detector}")
        
        # Scale corners back to original size
        if corners is not None and len(corners) > 0:
            if detector == 'shi_tomasi':
                corners = corners.reshape(-1, 2)
            corners = corners / scale
            corners = corners.astype(np.float32)
        
        results[f'scale_{scale}'] = corners
    
    return results


def compare_corner_detectors(image: np.ndarray) -> dict:
    """
    Compare different corner detection methods.
    
    Args:
        image: Input image
        
    Returns:
        Dictionary containing results from different detectors
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    results = {}
    
    # Harris corners
    harris_corners_mask, harris_response = harris_corners(gray)
    results['harris'] = {
        'corners': harris_corners_mask,
        'response': harris_response,
        'corner_count': np.sum(harris_corners_mask)
    }
    
    # Shi-Tomasi corners
    shi_corners = shi_tomasi_corners(gray)
    results['shi_tomasi'] = {
        'corners': shi_corners,
        'corner_count': len(shi_corners) if shi_corners is not None else 0
    }
    
    # FAST corners
    fast_keypoints, fast_response = fast_corners(gray)
    results['fast'] = {
        'keypoints': fast_keypoints,
        'response': fast_response,
        'corner_count': len(fast_keypoints)
    }
    
    return results


def draw_corners(image: np.ndarray, corners: np.ndarray, 
                 color: Tuple[int, int, int] = (0, 255, 0), 
                 radius: int = 3, thickness: int = 2) -> np.ndarray:
    """
    Draw detected corners on an image.
    
    Args:
        image: Input image
        corners: Corner coordinates
        color: Color of the circles (BGR)
        radius: Radius of the circles
        thickness: Thickness of the circles
        
    Returns:
        Image with drawn corners
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    result = image.copy()
    
    if corners is not None and len(corners) > 0:
        for corner in corners:
            x, y = int(corner[0]), int(corner[1])
            cv2.circle(result, (x, y), radius, color, thickness)
    
    return result


def corner_quality_analysis(image: np.ndarray, corners: np.ndarray,
                          window_size: int = 5) -> dict:
    """
    Analyze the quality of detected corners.
    
    Args:
        image: Input image
        corners: Corner coordinates
        window_size: Size of the analysis window
        
    Returns:
        Dictionary containing quality metrics
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if corners is None or len(corners) == 0:
        return {'corner_count': 0, 'average_response': 0, 'response_std': 0}
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    responses = []
    
    for corner in corners:
        x, y = int(corner[0]), int(corner[1])
        
        # Extract window around corner
        half_window = window_size // 2
        y1, y2 = max(0, y - half_window), min(gray.shape[0], y + half_window + 1)
        x1, x2 = max(0, x - half_window), min(gray.shape[1], x + half_window + 1)
        
        window = gray[y1:y2, x1:x2]
        
        if window.size > 0:
            # Calculate response as variance of the window
            response = np.var(window)
            responses.append(response)
    
    if responses:
        return {
            'corner_count': len(corners),
            'average_response': np.mean(responses),
            'response_std': np.std(responses),
            'min_response': np.min(responses),
            'max_response': np.max(responses)
        }
    else:
        return {
            'corner_count': len(corners),
            'average_response': 0,
            'response_std': 0,
            'min_response': 0,
            'max_response': 0
        } 