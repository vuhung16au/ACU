"""
Template Matching Module

This module provides template matching algorithms:
- Template matching algorithms
- Multi-scale template matching
- Rotation-invariant matching

Author: OpenCV Image Processing Collection
"""

import cv2
import numpy as np
from typing import Tuple, Optional, Union, List


def template_matching(image: np.ndarray, template: np.ndarray,
                     method: int = cv2.TM_CCOEFF_NORMED, threshold: float = 0.8) -> List[Tuple[Tuple[int, int], float]]:
    """
    Perform template matching on an image.
    
    Args:
        image: Input image
        template: Template to match
        method: Template matching method
        threshold: Minimum correlation threshold
        
    Returns:
        List of (location, correlation) tuples
    """
    if image is None or template is None:
        raise ValueError("Image and template cannot be None")
    
    # Perform template matching
    result = cv2.matchTemplate(image, template, method)
    
    # Find locations where correlation exceeds threshold
    locations = np.where(result >= threshold)
    matches = []
    
    for pt in zip(*locations[::-1]):  # Switch columns and rows
        matches.append((pt, result[pt[1], pt[0]]))
    
    # Sort by correlation score (descending)
    matches.sort(key=lambda x: x[1], reverse=True)
    
    return matches


def multi_scale_template_matching(image: np.ndarray, template: np.ndarray,
                                scales: list = [0.5, 0.75, 1.0, 1.25, 1.5],
                                method: int = cv2.TM_CCOEFF_NORMED, threshold: float = 0.8) -> List[Tuple[Tuple[int, int], float, float]]:
    """
    Perform multi-scale template matching.
    
    Args:
        image: Input image
        template: Template to match
        scales: List of scale factors to try
        method: Template matching method
        threshold: Minimum correlation threshold
        
    Returns:
        List of (location, correlation, scale) tuples
    """
    if image is None or template is None:
        raise ValueError("Image and template cannot be None")
    
    all_matches = []
    
    for scale in scales:
        # Resize template
        h, w = template.shape[:2]
        new_h, new_w = int(h * scale), int(w * scale)
        resized_template = cv2.resize(template, (new_w, new_h))
        
        # Perform template matching
        matches = template_matching(image, resized_template, method, threshold)
        
        # Add scale information to matches
        for (pt, correlation) in matches:
            all_matches.append((pt, correlation, scale))
    
    # Sort by correlation score (descending)
    all_matches.sort(key=lambda x: x[1], reverse=True)
    
    return all_matches


def rotation_invariant_matching(image: np.ndarray, template: np.ndarray,
                              angles: list = range(0, 360, 10),
                              method: int = cv2.TM_CCOEFF_NORMED, threshold: float = 0.8) -> List[Tuple[Tuple[int, int], float, float]]:
    """
    Perform rotation-invariant template matching.
    
    Args:
        image: Input image
        template: Template to match
        angles: List of rotation angles to try (in degrees)
        method: Template matching method
        threshold: Minimum correlation threshold
        
    Returns:
        List of (location, correlation, angle) tuples
    """
    if image is None or template is None:
        raise ValueError("Image and template cannot be None")
    
    all_matches = []
    h, w = template.shape[:2]
    center = (w // 2, h // 2)
    
    for angle in angles:
        # Create rotation matrix
        rotation_matrix = cv2.getRotationMatrix2D(center, angle, 1.0)
        
        # Rotate template
        rotated_template = cv2.warpAffine(template, rotation_matrix, (w, h))
        
        # Perform template matching
        matches = template_matching(image, rotated_template, method, threshold)
        
        # Add angle information to matches
        for (pt, correlation) in matches:
            all_matches.append((pt, correlation, angle))
    
    # Sort by correlation score (descending)
    all_matches.sort(key=lambda x: x[1], reverse=True)
    
    return all_matches


def template_matching_with_nms(image: np.ndarray, template: np.ndarray,
                              method: int = cv2.TM_CCOEFF_NORMED, threshold: float = 0.8,
                              nms_threshold: float = 0.3) -> List[Tuple[Tuple[int, int], float]]:
    """
    Perform template matching with non-maximum suppression.
    
    Args:
        image: Input image
        template: Template to match
        method: Template matching method
        threshold: Minimum correlation threshold
        nms_threshold: Non-maximum suppression threshold
        
    Returns:
        List of (location, correlation) tuples after NMS
    """
    if image is None or template is None:
        raise ValueError("Image and template cannot be None")
    
    # Get all matches
    matches = template_matching(image, template, method, threshold)
    
    if not matches:
        return []
    
    # Apply non-maximum suppression
    filtered_matches = []
    h, w = template.shape[:2]
    
    for (pt, correlation) in matches:
        # Check if this match is suppressed by any existing match
        suppressed = False
        
        for (existing_pt, _) in filtered_matches:
            # Calculate distance between points
            distance = np.sqrt((pt[0] - existing_pt[0])**2 + (pt[1] - existing_pt[1])**2)
            
            # If distance is less than threshold, suppress this match
            if distance < nms_threshold * min(w, h):
                suppressed = True
                break
        
        if not suppressed:
            filtered_matches.append((pt, correlation))
    
    return filtered_matches


def draw_template_matches(image: np.ndarray, template: np.ndarray,
                         matches: List[Tuple[Tuple[int, int], float]],
                         color: Tuple[int, int, int] = (0, 255, 0), thickness: int = 2) -> np.ndarray:
    """
    Draw template matches on an image.
    
    Args:
        image: Input image
        template: Template used for matching
        matches: List of (location, correlation) tuples
        color: Color of the rectangles (BGR)
        thickness: Thickness of the rectangles
        
    Returns:
        Image with drawn matches
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    result = image.copy()
    h, w = template.shape[:2]
    
    for (pt, correlation) in matches:
        # Draw rectangle around match
        top_left = pt
        bottom_right = (pt[0] + w, pt[1] + h)
        cv2.rectangle(result, top_left, bottom_right, color, thickness)
        
        # Draw correlation score
        cv2.putText(result, f'{correlation:.2f}', (pt[0], pt[1] - 10),
                   cv2.FONT_HERSHEY_SIMPLEX, 0.5, color, 1)
    
    return result


def compare_template_matching_methods(image: np.ndarray, template: np.ndarray) -> dict:
    """
    Compare different template matching methods.
    
    Args:
        image: Input image
        template: Template to match
        
    Returns:
        Dictionary containing results from different methods
    """
    if image is None or template is None:
        raise ValueError("Image and template cannot be None")
    
    methods = {
        'TM_CCOEFF': cv2.TM_CCOEFF,
        'TM_CCOEFF_NORMED': cv2.TM_CCOEFF_NORMED,
        'TM_CCORR': cv2.TM_CCORR,
        'TM_CCORR_NORMED': cv2.TM_CCORR_NORMED,
        'TM_SQDIFF': cv2.TM_SQDIFF,
        'TM_SQDIFF_NORMED': cv2.TM_SQDIFF_NORMED
    }
    
    results = {}
    
    for method_name, method in methods.items():
        try:
            matches = template_matching(image, template, method, threshold=0.5)
            results[method_name] = {
                'matches': matches,
                'count': len(matches),
                'best_correlation': matches[0][1] if matches else 0
            }
        except Exception as e:
            results[method_name] = {'error': str(e)}
    
    return results


def template_matching_with_roi(image: np.ndarray, template: np.ndarray,
                              roi: Tuple[int, int, int, int],
                              method: int = cv2.TM_CCOEFF_NORMED, threshold: float = 0.8) -> List[Tuple[Tuple[int, int], float]]:
    """
    Perform template matching within a region of interest.
    
    Args:
        image: Input image
        template: Template to match
        roi: Region of interest (x, y, width, height)
        method: Template matching method
        threshold: Minimum correlation threshold
        
    Returns:
        List of (location, correlation) tuples (coordinates relative to ROI)
    """
    if image is None or template is None:
        raise ValueError("Image and template cannot be None")
    
    x, y, w, h = roi
    
    # Extract ROI
    roi_image = image[y:y+h, x:x+w]
    
    # Perform template matching on ROI
    matches = template_matching(roi_image, template, method, threshold)
    
    # Adjust coordinates to be relative to original image
    adjusted_matches = []
    for (pt, correlation) in matches:
        adjusted_pt = (pt[0] + x, pt[1] + y)
        adjusted_matches.append((adjusted_pt, correlation))
    
    return adjusted_matches


def template_matching_with_preprocessing(image: np.ndarray, template: np.ndarray,
                                       preprocessing: str = 'none',
                                       method: int = cv2.TM_CCOEFF_NORMED, threshold: float = 0.8) -> List[Tuple[Tuple[int, int], float]]:
    """
    Perform template matching with preprocessing.
    
    Args:
        image: Input image
        template: Template to match
        preprocessing: Preprocessing method ('none', 'blur', 'edge', 'gradient')
        method: Template matching method
        threshold: Minimum correlation threshold
        
    Returns:
        List of (location, correlation) tuples
    """
    if image is None or template is None:
        raise ValueError("Image and template cannot be None")
    
    # Apply preprocessing
    if preprocessing == 'blur':
        processed_image = cv2.GaussianBlur(image, (5, 5), 0)
        processed_template = cv2.GaussianBlur(template, (5, 5), 0)
    elif preprocessing == 'edge':
        processed_image = cv2.Canny(image, 50, 150)
        processed_template = cv2.Canny(template, 50, 150)
    elif preprocessing == 'gradient':
        # Sobel gradient
        grad_x = cv2.Sobel(image, cv2.CV_64F, 1, 0, ksize=3)
        grad_y = cv2.Sobel(image, cv2.CV_64F, 0, 1, ksize=3)
        processed_image = np.sqrt(grad_x**2 + grad_y**2)
        
        grad_x_t = cv2.Sobel(template, cv2.CV_64F, 1, 0, ksize=3)
        grad_y_t = cv2.Sobel(template, cv2.CV_64F, 0, 1, ksize=3)
        processed_template = np.sqrt(grad_x_t**2 + grad_y_t**2)
    else:
        processed_image = image
        processed_template = template
    
    # Perform template matching
    return template_matching(processed_image, processed_template, method, threshold)


def adaptive_template_matching(image: np.ndarray, template: np.ndarray,
                             method: str = 'auto') -> List[Tuple[Tuple[int, int], float]]:
    """
    Perform adaptive template matching based on image characteristics.
    
    Args:
        image: Input image
        template: Template to match
        method: Adaptive method ('auto', 'bright', 'dark', 'noisy')
        
    Returns:
        List of (location, correlation) tuples
    """
    if image is None or template is None:
        raise ValueError("Image and template cannot be None")
    
    if method == 'auto':
        # Analyze image characteristics
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY) if len(image.shape) == 3 else image
        mean_intensity = np.mean(gray)
        std_intensity = np.std(gray)
        
        if mean_intensity < 100:  # Dark image
            return template_matching_with_preprocessing(image, template, 'blur')
        elif std_intensity > 50:  # Noisy image
            return template_matching_with_preprocessing(image, template, 'blur')
        else:
            return template_matching(image, template)
    
    elif method == 'bright':
        return template_matching_with_preprocessing(image, template, 'edge')
    
    elif method == 'dark':
        return template_matching_with_preprocessing(image, template, 'blur')
    
    elif method == 'noisy':
        return template_matching_with_preprocessing(image, template, 'blur')
    
    else:
        raise ValueError(f"Unknown method: {method}") 