"""
Contour Detection Module

This module provides contour detection and analysis functions:
- Finding contours
- Contour approximation
- Contour analysis
- Shape matching

Author: Vu Hung Nguyen
"""

import cv2
import numpy as np
from typing import Tuple, Optional, Union, List


def find_contours(image: np.ndarray, mode: int = cv2.RETR_EXTERNAL,
                  method: int = cv2.CHAIN_APPROX_SIMPLE) -> Tuple[List[np.ndarray], np.ndarray]:
    """
    Find contours in an image.
    
    Args:
        image: Input image (binary)
        mode: Contour retrieval mode
        method: Contour approximation method
        
    Returns:
        Tuple of (contours, hierarchy)
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    # Threshold if needed
    if gray.max() > 1:
        _, binary = cv2.threshold(gray, 127, 255, cv2.THRESH_BINARY)
    else:
        binary = gray
    
    # Find contours
    contours, hierarchy = cv2.findContours(binary, mode, method)
    
    return contours, hierarchy


def approximate_contours(contours: List[np.ndarray], epsilon_factor: float = 0.02,
                        closed: bool = True) -> List[np.ndarray]:
    """
    Approximate contours using Douglas-Peucker algorithm.
    
    Args:
        contours: List of contours
        epsilon_factor: Approximation accuracy (factor of contour perimeter)
        closed: Whether the contours are closed
        
    Returns:
        List of approximated contours
    """
    if contours is None:
        return []
    
    approximated = []
    
    for contour in contours:
        # Calculate epsilon as a fraction of the contour perimeter
        epsilon = epsilon_factor * cv2.arcLength(contour, closed)
        approx = cv2.approxPolyDP(contour, epsilon, closed)
        approximated.append(approx)
    
    return approximated


def analyze_contours(contours: List[np.ndarray]) -> List[dict]:
    """
    Analyze contours and compute various properties.
    
    Args:
        contours: List of contours
        
    Returns:
        List of dictionaries containing contour properties
    """
    if contours is None:
        return []
    
    analyses = []
    
    for i, contour in enumerate(contours):
        # Basic properties
        area = cv2.contourArea(contour)
        perimeter = cv2.arcLength(contour, True)
        
        # Bounding rectangle
        x, y, w, h = cv2.boundingRect(contour)
        
        # Minimum area rectangle
        rect = cv2.minAreaRect(contour)
        box = cv2.boxPoints(rect)
        box = np.int0(box)
        
        # Minimum enclosing circle
        (cx, cy), radius = cv2.minEnclosingCircle(contour)
        
        # Convex hull
        hull = cv2.convexHull(contour)
        hull_area = cv2.contourArea(hull)
        
        # Convexity defects
        if len(contour) > 3:
            defects = cv2.convexityDefects(contour, cv2.convexHull(contour, returnPoints=False))
        else:
            defects = None
        
        # Fit ellipse
        if len(contour) >= 5:
            ellipse = cv2.fitEllipse(contour)
        else:
            ellipse = None
        
        # Fit line
        if len(contour) >= 2:
            [vx, vy, x, y] = cv2.fitLine(contour, cv2.DIST_L2, 0, 0.01, 0.01)
        else:
            vx, vy, x, y = 0, 0, 0, 0
        
        # Shape analysis
        aspect_ratio = float(w) / h if h > 0 else 0
        extent = float(area) / (w * h) if w * h > 0 else 0
        solidity = float(area) / hull_area if hull_area > 0 else 0
        
        # Compactness (circularity)
        compactness = (perimeter ** 2) / (4 * np.pi * area) if area > 0 else 0
        
        analysis = {
            'index': i,
            'area': area,
            'perimeter': perimeter,
            'bounding_rect': (x, y, w, h),
            'min_area_rect': rect,
            'bounding_box': box,
            'min_enclosing_circle': ((cx, cy), radius),
            'convex_hull': hull,
            'convexity_defects': defects,
            'ellipse': ellipse,
            'fit_line': (vx, vy, x, y),
            'aspect_ratio': aspect_ratio,
            'extent': extent,
            'solidity': solidity,
            'compactness': compactness,
            'num_points': len(contour)
        }
        
        analyses.append(analysis)
    
    return analyses


def filter_contours_by_area(contours: List[np.ndarray], min_area: float = 100,
                           max_area: float = float('inf')) -> List[np.ndarray]:
    """
    Filter contours by area.
    
    Args:
        contours: List of contours
        min_area: Minimum area threshold
        max_area: Maximum area threshold
        
    Returns:
        Filtered contours
    """
    if contours is None:
        return []
    
    filtered = []
    
    for contour in contours:
        area = cv2.contourArea(contour)
        if min_area <= area <= max_area:
            filtered.append(contour)
    
    return filtered


def match_shapes(contour1: np.ndarray, contour2: np.ndarray,
                 method: int = cv2.CONTOURS_MATCH_I1) -> float:
    """
    Match two contours using shape matching.
    
    Args:
        contour1: First contour
        contour2: Second contour
        method: Shape matching method
        
    Returns:
        Similarity score (lower is better)
    """
    if contour1 is None or contour2 is None:
        return float('inf')
    
    return cv2.matchShapes(contour1, contour2, method, 0.0)


def find_similar_contours(target_contour: np.ndarray, contours: List[np.ndarray],
                         threshold: float = 0.1, method: int = cv2.CONTOURS_MATCH_I1) -> List[Tuple[int, float]]:
    """
    Find contours similar to a target contour.
    
    Args:
        target_contour: Target contour
        contours: List of contours to search
        threshold: Similarity threshold
        method: Shape matching method
        
    Returns:
        List of (index, similarity_score) tuples
    """
    if target_contour is None or contours is None:
        return []
    
    similar = []
    
    for i, contour in enumerate(contours):
        similarity = match_shapes(target_contour, contour, method)
        if similarity <= threshold:
            similar.append((i, similarity))
    
    # Sort by similarity (ascending)
    similar.sort(key=lambda x: x[1])
    
    return similar


def draw_contours(image: np.ndarray, contours: List[np.ndarray],
                  color: Tuple[int, int, int] = (0, 255, 0), thickness: int = 2,
                  hierarchy: Optional[np.ndarray] = None, max_level: int = 0) -> np.ndarray:
    """
    Draw contours on an image.
    
    Args:
        image: Input image
        contours: List of contours
        color: Color of the contours (BGR)
        thickness: Thickness of the contours
        hierarchy: Contour hierarchy
        max_level: Maximum level to draw
        
    Returns:
        Image with drawn contours
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    result = image.copy()
    cv2.drawContours(result, contours, -1, color, thickness, hierarchy, max_level)
    
    return result


def draw_contour_analysis(image: np.ndarray, contours: List[np.ndarray],
                         analyses: List[dict], draw_centers: bool = True,
                         draw_bounding_boxes: bool = True, draw_ellipses: bool = True) -> np.ndarray:
    """
    Draw contour analysis results on an image.
    
    Args:
        image: Input image
        contours: List of contours
        analyses: List of contour analyses
        draw_centers: Whether to draw contour centers
        draw_bounding_boxes: Whether to draw bounding boxes
        draw_ellipses: Whether to draw fitted ellipses
        
    Returns:
        Image with analysis drawings
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    result = image.copy()
    
    for i, (contour, analysis) in enumerate(zip(contours, analyses)):
        # Draw contour
        cv2.drawContours(result, [contour], -1, (0, 255, 0), 2)
        
        # Draw center
        if draw_centers:
            M = cv2.moments(contour)
            if M["m00"] != 0:
                cx = int(M["m10"] / M["m00"])
                cy = int(M["m01"] / M["m00"])
                cv2.circle(result, (cx, cy), 5, (255, 0, 0), -1)
        
        # Draw bounding box
        if draw_bounding_boxes:
            x, y, w, h = analysis['bounding_rect']
            cv2.rectangle(result, (x, y), (x + w, y + h), (0, 0, 255), 2)
        
        # Draw fitted ellipse
        if draw_ellipses and analysis['ellipse'] is not None:
            cv2.ellipse(result, analysis['ellipse'], (255, 255, 0), 2)
        
        # Draw convex hull
        cv2.drawContours(result, [analysis['convex_hull']], -1, (255, 0, 255), 1)
    
    return result


def extract_contour_features(contours: List[np.ndarray]) -> np.ndarray:
    """
    Extract numerical features from contours.
    
    Args:
        contours: List of contours
        
    Returns:
        Feature matrix (n_contours x n_features)
    """
    if contours is None or len(contours) == 0:
        return np.array([])
    
    analyses = analyze_contours(contours)
    features = []
    
    for analysis in analyses:
        feature_vector = [
            analysis['area'],
            analysis['perimeter'],
            analysis['aspect_ratio'],
            analysis['extent'],
            analysis['solidity'],
            analysis['compactness'],
            analysis['num_points']
        ]
        features.append(feature_vector)
    
    return np.array(features)


def find_contours_hierarchy(image: np.ndarray, mode: int = cv2.RETR_TREE) -> Tuple[List[np.ndarray], np.ndarray]:
    """
    Find contours with hierarchy information.
    
    Args:
        image: Input image
        mode: Contour retrieval mode (use cv2.RETR_TREE for hierarchy)
        
    Returns:
        Tuple of (contours, hierarchy)
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    # Threshold if needed
    if gray.max() > 1:
        _, binary = cv2.threshold(gray, 127, 255, cv2.THRESH_BINARY)
    else:
        binary = gray
    
    # Find contours with hierarchy
    contours, hierarchy = cv2.findContours(binary, mode, cv2.CHAIN_APPROX_SIMPLE)
    
    return contours, hierarchy


def get_contour_hierarchy_tree(hierarchy: np.ndarray) -> dict:
    """
    Build a hierarchy tree from contour hierarchy.
    
    Args:
        hierarchy: Contour hierarchy array
        
    Returns:
        Dictionary representing hierarchy tree
    """
    if hierarchy is None:
        return {}
    
    tree = {}
    
    for i, (next_idx, prev_idx, first_child_idx, parent_idx) in enumerate(hierarchy[0]):
        tree[i] = {
            'next': next_idx,
            'prev': prev_idx,
            'first_child': first_child_idx,
            'parent': parent_idx
        }
    
    return tree


def filter_contours_by_hierarchy(contours: List[np.ndarray], hierarchy: np.ndarray,
                                max_level: int = 2) -> List[np.ndarray]:
    """
    Filter contours by hierarchy level.
    
    Args:
        contours: List of contours
        hierarchy: Contour hierarchy
        max_level: Maximum hierarchy level to include
        
    Returns:
        Filtered contours
    """
    if contours is None or hierarchy is None:
        return []
    
    filtered = []
    
    def get_level(contour_idx, current_level=0):
        if current_level > max_level or contour_idx == -1:
            return
        
        filtered.append(contours[contour_idx])
        
        # Get children
        child_idx = hierarchy[0][contour_idx][2]  # first_child
        while child_idx != -1:
            get_level(child_idx, current_level + 1)
            child_idx = hierarchy[0][child_idx][0]  # next
    
    # Start with top-level contours
    for i in range(len(contours)):
        if hierarchy[0][i][3] == -1:  # parent == -1 (top level)
            get_level(i)
    
    return filtered


def compare_contour_detection_methods(image: np.ndarray) -> dict:
    """
    Compare different contour detection methods.
    
    Args:
        image: Input image
        
    Returns:
        Dictionary containing results from different methods
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    # Threshold if needed
    if gray.max() > 1:
        _, binary = cv2.threshold(gray, 127, 255, cv2.THRESH_BINARY)
    else:
        binary = gray
    
    results = {}
    
    # External contours
    contours_external, _ = find_contours(binary, cv2.RETR_EXTERNAL)
    results['external'] = {
        'contours': contours_external,
        'count': len(contours_external),
        'analyses': analyze_contours(contours_external)
    }
    
    # All contours
    contours_all, _ = find_contours(binary, cv2.RETR_LIST)
    results['all'] = {
        'contours': contours_all,
        'count': len(contours_all),
        'analyses': analyze_contours(contours_all)
    }
    
    # Hierarchical contours
    contours_hierarchy, hierarchy = find_contours_hierarchy(binary, cv2.RETR_TREE)
    results['hierarchy'] = {
        'contours': contours_hierarchy,
        'hierarchy': hierarchy,
        'count': len(contours_hierarchy),
        'analyses': analyze_contours(contours_hierarchy),
        'tree': get_contour_hierarchy_tree(hierarchy)
    }
    
    return results 