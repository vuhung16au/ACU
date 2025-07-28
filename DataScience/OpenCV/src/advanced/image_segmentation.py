"""
Image Segmentation Module

This module provides image segmentation techniques:
- Threshold-based segmentation
- Watershed algorithm
- GrabCut algorithm
- K-means clustering

Author: OpenCV Image Processing Collection
"""

import cv2
import numpy as np
from typing import Tuple, Optional, Union, List
from sklearn.cluster import KMeans


def threshold_segmentation(image: np.ndarray, method: str = 'otsu',
                          threshold_value: int = 127, max_value: int = 255) -> Tuple[np.ndarray, int]:
    """
    Perform threshold-based segmentation.
    
    Args:
        image: Input image (grayscale)
        method: Threshold method ('binary', 'binary_inv', 'trunc', 'tozero', 'tozero_inv', 'otsu', 'triangle')
        threshold_value: Threshold value (used for manual thresholding)
        max_value: Maximum value for binary thresholding
        
    Returns:
        Tuple of (segmented_image, threshold_value)
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    if method == 'otsu':
        threshold, segmented = cv2.threshold(gray, 0, max_value, cv2.THRESH_BINARY + cv2.THRESH_OTSU)
    elif method == 'triangle':
        threshold, segmented = cv2.threshold(gray, 0, max_value, cv2.THRESH_BINARY + cv2.THRESH_TRIANGLE)
    elif method == 'binary':
        threshold, segmented = cv2.threshold(gray, threshold_value, max_value, cv2.THRESH_BINARY)
    elif method == 'binary_inv':
        threshold, segmented = cv2.threshold(gray, threshold_value, max_value, cv2.THRESH_BINARY_INV)
    elif method == 'trunc':
        threshold, segmented = cv2.threshold(gray, threshold_value, max_value, cv2.THRESH_TRUNC)
    elif method == 'tozero':
        threshold, segmented = cv2.threshold(gray, threshold_value, max_value, cv2.THRESH_TOZERO)
    elif method == 'tozero_inv':
        threshold, segmented = cv2.threshold(gray, threshold_value, max_value, cv2.THRESH_TOZERO_INV)
    else:
        raise ValueError(f"Unknown threshold method: {method}")
    
    return segmented, threshold


def adaptive_threshold_segmentation(image: np.ndarray, block_size: int = 11, c: int = 2,
                                   method: str = 'gaussian') -> np.ndarray:
    """
    Perform adaptive threshold segmentation.
    
    Args:
        image: Input image (grayscale)
        block_size: Size of pixel neighborhood
        c: Constant subtracted from mean or weighted mean
        method: Adaptive method ('mean', 'gaussian')
        
    Returns:
        Segmented image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    if method == 'gaussian':
        adaptive_method = cv2.ADAPTIVE_THRESH_GAUSSIAN_C
    elif method == 'mean':
        adaptive_method = cv2.ADAPTIVE_THRESH_MEAN_C
    else:
        raise ValueError(f"Unknown adaptive method: {method}")
    
    return cv2.adaptiveThreshold(gray, 255, adaptive_method, cv2.THRESH_BINARY, block_size, c)


def watershed_segmentation(image: np.ndarray, markers: Optional[np.ndarray] = None,
                          compactness: float = 0.0) -> Tuple[np.ndarray, np.ndarray]:
    """
    Perform watershed segmentation.
    
    Args:
        image: Input image
        markers: Marker image (if None, will be created automatically)
        compactness: Compactness parameter for watershed
        
    Returns:
        Tuple of (segmented_image, markers)
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    # Apply Gaussian blur to reduce noise
    blurred = cv2.GaussianBlur(gray, (5, 5), 0)
    
    # Apply morphological operations to create markers
    if markers is None:
        # Create markers using distance transform
        _, binary = cv2.threshold(blurred, 0, 255, cv2.THRESH_BINARY_INV + cv2.THRESH_OTSU)
        
        # Morphological operations to remove noise
        kernel = np.ones((3, 3), np.uint8)
        opening = cv2.morphologyEx(binary, cv2.MORPH_OPEN, kernel, iterations=2)
        
        # Sure background area
        sure_bg = cv2.dilate(opening, kernel, iterations=3)
        
        # Distance transform
        dist_transform = cv2.distanceTransform(opening, cv2.DIST_L2, 5)
        
        # Sure foreground area
        _, sure_fg = cv2.threshold(dist_transform, 0.7 * dist_transform.max(), 255, 0)
        sure_fg = sure_fg.astype(np.uint8)
        
        # Finding unknown region
        unknown = cv2.subtract(sure_bg, sure_fg)
        
        # Marker labeling
        _, markers = cv2.connectedComponents(sure_fg)
        markers = markers + 1
        markers[unknown == 255] = 0
    
    # Apply watershed
    markers = cv2.watershed(image, markers)
    
    return markers, markers


def grabcut_segmentation(image: np.ndarray, rect: Optional[Tuple[int, int, int, int]] = None,
                        mask: Optional[np.ndarray] = None, iterations: int = 5) -> Tuple[np.ndarray, np.ndarray]:
    """
    Perform GrabCut segmentation.
    
    Args:
        image: Input image
        rect: Rectangle (x, y, width, height) for initial segmentation
        mask: Initial mask (if None, will be created from rect)
        iterations: Number of iterations
        
    Returns:
        Tuple of (segmented_image, mask)
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if rect is None and mask is None:
        # Use entire image as rectangle
        h, w = image.shape[:2]
        rect = (0, 0, w, h)
    
    # Create mask if not provided
    if mask is None:
        mask = np.zeros(image.shape[:2], np.uint8)
        if rect is not None:
            x, y, w, h = rect
            mask[y:y+h, x:x+w] = cv2.GC_PR_FGD
    
    # Create temporary arrays
    bgd_model = np.zeros((1, 65), np.float64)
    fgd_model = np.zeros((1, 65), np.float64)
    
    # Apply GrabCut
    if rect is not None:
        cv2.grabCut(image, mask, rect, bgd_model, fgd_model, iterations, cv2.GC_INIT_WITH_RECT)
    else:
        cv2.grabCut(image, mask, None, bgd_model, fgd_model, iterations, cv2.GC_INIT_WITH_MASK)
    
    # Create mask for probable and definite foreground
    mask2 = np.where((mask == 2) | (mask == 0), 0, 1).astype('uint8')
    
    # Apply mask to image
    segmented = image * mask2[:, :, np.newaxis]
    
    return segmented, mask2


def kmeans_segmentation(image: np.ndarray, n_clusters: int = 3,
                       max_iterations: int = 100, tolerance: float = 1e-4) -> Tuple[np.ndarray, np.ndarray]:
    """
    Perform K-means clustering segmentation.
    
    Args:
        image: Input image
        n_clusters: Number of clusters
        max_iterations: Maximum number of iterations
        tolerance: Tolerance for convergence
        
    Returns:
        Tuple of (segmented_image, labels)
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Reshape image for clustering
    if len(image.shape) == 3:
        # For color images, use all channels
        reshaped = image.reshape(-1, 3)
    else:
        # For grayscale images, add a dimension
        reshaped = image.reshape(-1, 1)
    
    # Apply K-means clustering
    kmeans = KMeans(n_clusters=n_clusters, max_iter=max_iterations, tol=tolerance, random_state=42)
    labels = kmeans.fit_predict(reshaped)
    
    # Reshape labels back to image shape
    labels = labels.reshape(image.shape[:2])
    
    # Create segmented image using cluster centers
    segmented = np.zeros_like(image)
    for i in range(n_clusters):
        mask = labels == i
        if len(image.shape) == 3:
            segmented[mask] = kmeans.cluster_centers_[i]
        else:
            segmented[mask] = kmeans.cluster_centers_[i][0]
    
    return segmented.astype(np.uint8), labels


def mean_shift_segmentation(image: np.ndarray, spatial_radius: int = 10, color_radius: int = 20,
                           min_density: int = 10) -> np.ndarray:
    """
    Perform mean shift segmentation.
    
    Args:
        image: Input image
        spatial_radius: Spatial radius for mean shift
        color_radius: Color radius for mean shift
        min_density: Minimum density for filtering
        
    Returns:
        Segmented image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Apply mean shift filtering
    shifted = cv2.pyrMeanShiftFiltering(image, spatial_radius, color_radius, min_density)
    
    return shifted


def felzenszwalb_segmentation(image: np.ndarray, scale: int = 1, sigma: float = 0.5,
                             min_size: int = 100) -> np.ndarray:
    """
    Perform Felzenszwalb segmentation (using scikit-image).
    
    Args:
        image: Input image
        scale: Scale parameter
        sigma: Sigma for Gaussian blur
        min_size: Minimum component size
        
    Returns:
        Segmented image
    """
    try:
        from skimage.segmentation import felzenszwalb
        from skimage.color import label2rgb
        
        # Convert BGR to RGB
        if len(image.shape) == 3:
            rgb_image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
        else:
            rgb_image = image
        
        # Apply Felzenszwalb segmentation
        segments = felzenszwalb(rgb_image, scale=scale, sigma=sigma, min_size=min_size)
        
        # Convert segments to colored image
        segmented = label2rgb(segments, rgb_image, kind='avg')
        
        # Convert back to BGR
        return cv2.cvtColor(segmented, cv2.COLOR_RGB2BGR)
    
    except ImportError:
        print("scikit-image is required for Felzenszwalb segmentation")
        return image


def slic_segmentation(image: np.ndarray, n_segments: int = 100, compactness: float = 10.0,
                     sigma: float = 1.0) -> np.ndarray:
    """
    Perform SLIC (Simple Linear Iterative Clustering) segmentation.
    
    Args:
        image: Input image
        n_segments: Number of segments
        compactness: Compactness parameter
        sigma: Sigma for Gaussian blur
        
    Returns:
        Segmented image
    """
    try:
        from skimage.segmentation import slic
        from skimage.color import label2rgb
        
        # Convert BGR to RGB
        if len(image.shape) == 3:
            rgb_image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
        else:
            rgb_image = image
        
        # Apply SLIC segmentation
        segments = slic(rgb_image, n_segments=n_segments, compactness=compactness, sigma=sigma)
        
        # Convert segments to colored image
        segmented = label2rgb(segments, rgb_image, kind='avg')
        
        # Convert back to BGR
        return cv2.cvtColor(segmented, cv2.COLOR_RGB2BGR)
    
    except ImportError:
        print("scikit-image is required for SLIC segmentation")
        return image


def compare_segmentation_methods(image: np.ndarray) -> dict:
    """
    Compare different segmentation methods.
    
    Args:
        image: Input image
        
    Returns:
        Dictionary containing results from different methods
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    results = {}
    
    # Threshold segmentation
    try:
        otsu_result, otsu_threshold = threshold_segmentation(image, 'otsu')
        results['otsu'] = {
            'segmented': otsu_result,
            'threshold': otsu_threshold
        }
    except Exception as e:
        results['otsu'] = {'error': str(e)}
    
    # Adaptive threshold
    try:
        adaptive_result = adaptive_threshold_segmentation(image)
        results['adaptive'] = {
            'segmented': adaptive_result
        }
    except Exception as e:
        results['adaptive'] = {'error': str(e)}
    
    # Watershed
    try:
        watershed_result, watershed_markers = watershed_segmentation(image)
        results['watershed'] = {
            'segmented': watershed_result,
            'markers': watershed_markers
        }
    except Exception as e:
        results['watershed'] = {'error': str(e)}
    
    # K-means
    try:
        kmeans_result, kmeans_labels = kmeans_segmentation(image, n_clusters=3)
        results['kmeans'] = {
            'segmented': kmeans_result,
            'labels': kmeans_labels
        }
    except Exception as e:
        results['kmeans'] = {'error': str(e)}
    
    # Mean shift
    try:
        meanshift_result = mean_shift_segmentation(image)
        results['meanshift'] = {
            'segmented': meanshift_result
        }
    except Exception as e:
        results['meanshift'] = {'error': str(e)}
    
    return results


def create_segmentation_mask(segmented_image: np.ndarray, target_label: int = 1) -> np.ndarray:
    """
    Create a binary mask from segmented image.
    
    Args:
        segmented_image: Segmented image
        target_label: Label to extract as mask
        
    Returns:
        Binary mask
    """
    if segmented_image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(segmented_image.shape) == 3:
        gray = cv2.cvtColor(segmented_image, cv2.COLOR_BGR2GRAY)
    else:
        gray = segmented_image
    
    # Create mask
    mask = (gray == target_label).astype(np.uint8) * 255
    
    return mask


def post_process_segmentation(segmented_image: np.ndarray, kernel_size: int = 3,
                            operation: str = 'morphological') -> np.ndarray:
    """
    Post-process segmented image.
    
    Args:
        segmented_image: Segmented image
        kernel_size: Size of morphological kernel
        operation: Post-processing operation ('morphological', 'smoothing', 'edge_enhancement')
        
    Returns:
        Post-processed image
    """
    if segmented_image is None:
        raise ValueError("Input image cannot be None")
    
    if operation == 'morphological':
        kernel = np.ones((kernel_size, kernel_size), np.uint8)
        processed = cv2.morphologyEx(segmented_image, cv2.MORPH_CLOSE, kernel)
        processed = cv2.morphologyEx(processed, cv2.MORPH_OPEN, kernel)
    
    elif operation == 'smoothing':
        processed = cv2.medianBlur(segmented_image, kernel_size)
    
    elif operation == 'edge_enhancement':
        kernel = np.array([[-1, -1, -1], [-1, 9, -1], [-1, -1, -1]])
        processed = cv2.filter2D(segmented_image, -1, kernel)
    
    else:
        raise ValueError(f"Unknown operation: {operation}")
    
    return processed


def evaluate_segmentation(ground_truth: np.ndarray, segmented: np.ndarray) -> dict:
    """
    Evaluate segmentation quality.
    
    Args:
        ground_truth: Ground truth segmentation
        segmented: Segmented image
        
    Returns:
        Dictionary containing evaluation metrics
    """
    if ground_truth is None or segmented is None:
        return {}
    
    # Convert to binary if needed
    if len(ground_truth.shape) == 3:
        gt_binary = cv2.cvtColor(ground_truth, cv2.COLOR_BGR2GRAY)
    else:
        gt_binary = ground_truth
    
    if len(segmented.shape) == 3:
        seg_binary = cv2.cvtColor(segmented, cv2.COLOR_BGR2GRAY)
    else:
        seg_binary = segmented
    
    # Threshold to binary
    _, gt_binary = cv2.threshold(gt_binary, 127, 255, cv2.THRESH_BINARY)
    _, seg_binary = cv2.threshold(seg_binary, 127, 255, cv2.THRESH_BINARY)
    
    # Calculate metrics
    intersection = cv2.bitwise_and(gt_binary, seg_binary)
    union = cv2.bitwise_or(gt_binary, seg_binary)
    
    intersection_pixels = np.count_nonzero(intersection)
    union_pixels = np.count_nonzero(union)
    gt_pixels = np.count_nonzero(gt_binary)
    seg_pixels = np.count_nonzero(seg_binary)
    
    # IoU (Intersection over Union)
    iou = intersection_pixels / union_pixels if union_pixels > 0 else 0
    
    # Dice coefficient
    dice = 2 * intersection_pixels / (gt_pixels + seg_pixels) if (gt_pixels + seg_pixels) > 0 else 0
    
    # Precision and Recall
    precision = intersection_pixels / seg_pixels if seg_pixels > 0 else 0
    recall = intersection_pixels / gt_pixels if gt_pixels > 0 else 0
    
    # F1 score
    f1 = 2 * (precision * recall) / (precision + recall) if (precision + recall) > 0 else 0
    
    return {
        'iou': iou,
        'dice': dice,
        'precision': precision,
        'recall': recall,
        'f1_score': f1
    } 