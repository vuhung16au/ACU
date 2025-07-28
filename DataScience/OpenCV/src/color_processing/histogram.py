"""
Histogram Operations Module

This module provides histogram-related operations:
- Histogram calculation
- Histogram equalization
- CLAHE (Contrast Limited Adaptive Histogram Equalization)
- Histogram matching

Author: OpenCV Image Processing Collection
"""

import cv2
import numpy as np
from typing import Tuple, Optional, Union, List


def compute_histogram(image: np.ndarray, mask: Optional[np.ndarray] = None,
                     bins: int = 256, range_: Tuple[int, int] = (0, 256)) -> np.ndarray:
    """
    Compute histogram of an image.
    
    Args:
        image: Input image (grayscale or color)
        mask: Optional mask for histogram computation
        bins: Number of histogram bins
        range_: Range of pixel values
        
    Returns:
        Histogram array
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    # Compute histogram
    hist = cv2.calcHist([gray], [0], mask, [bins], range_)
    
    return hist


def compute_color_histogram(image: np.ndarray, mask: Optional[np.ndarray] = None,
                           bins: int = 256, range_: Tuple[int, int] = (0, 256)) -> List[np.ndarray]:
    """
    Compute histogram for each color channel.
    
    Args:
        image: Input color image
        mask: Optional mask for histogram computation
        bins: Number of histogram bins
        range_: Range of pixel values
        
    Returns:
        List of histograms for each channel
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if len(image.shape) != 3:
        raise ValueError("Input image must be 3-channel")
    
    # Split channels
    channels = cv2.split(image)
    histograms = []
    
    for channel in channels:
        hist = cv2.calcHist([channel], [0], mask, [bins], range_)
        histograms.append(hist)
    
    return histograms


def equalize_histogram(image: np.ndarray) -> np.ndarray:
    """
    Apply histogram equalization to an image.
    
    Args:
        image: Input image (grayscale)
        
    Returns:
        Equalized image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    return cv2.equalizeHist(gray)


def equalize_color_histogram(image: np.ndarray) -> np.ndarray:
    """
    Apply histogram equalization to each color channel.
    
    Args:
        image: Input color image
        
    Returns:
        Equalized color image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if len(image.shape) != 3:
        raise ValueError("Input image must be 3-channel")
    
    # Split channels
    channels = cv2.split(image)
    equalized_channels = []
    
    for channel in channels:
        equalized = cv2.equalizeHist(channel)
        equalized_channels.append(equalized)
    
    # Merge channels
    return cv2.merge(equalized_channels)


def clahe(image: np.ndarray, clip_limit: float = 2.0, tile_grid_size: Tuple[int, int] = (8, 8)) -> np.ndarray:
    """
    Apply Contrast Limited Adaptive Histogram Equalization (CLAHE).
    
    Args:
        image: Input image (grayscale)
        clip_limit: Threshold for contrast limiting
        tile_grid_size: Size of grid for histogram equalization
        
    Returns:
        CLAHE processed image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    # Create CLAHE object
    clahe_obj = cv2.createCLAHE(clipLimit=clip_limit, tileGridSize=tile_grid_size)
    
    # Apply CLAHE
    return clahe_obj.apply(gray)


def clahe_color(image: np.ndarray, clip_limit: float = 2.0, 
                tile_grid_size: Tuple[int, int] = (8, 8)) -> np.ndarray:
    """
    Apply CLAHE to each color channel.
    
    Args:
        image: Input color image
        clip_limit: Threshold for contrast limiting
        tile_grid_size: Size of grid for histogram equalization
        
    Returns:
        CLAHE processed color image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if len(image.shape) != 3:
        raise ValueError("Input image must be 3-channel")
    
    # Split channels
    channels = cv2.split(image)
    clahe_channels = []
    
    # Create CLAHE object
    clahe_obj = cv2.createCLAHE(clipLimit=clip_limit, tileGridSize=tile_grid_size)
    
    for channel in channels:
        clahe_channel = clahe_obj.apply(channel)
        clahe_channels.append(clahe_channel)
    
    # Merge channels
    return cv2.merge(clahe_channels)


def histogram_matching(source_image: np.ndarray, reference_image: np.ndarray) -> np.ndarray:
    """
    Match histogram of source image to reference image.
    
    Args:
        source_image: Source image
        reference_image: Reference image
        
    Returns:
        Histogram matched image
    """
    if source_image is None or reference_image is None:
        raise ValueError("Both source and reference images must be provided")
    
    # Convert to grayscale if needed
    if len(source_image.shape) == 3:
        source_gray = cv2.cvtColor(source_image, cv2.COLOR_BGR2GRAY)
    else:
        source_gray = source_image
    
    if len(reference_image.shape) == 3:
        reference_gray = cv2.cvtColor(reference_image, cv2.COLOR_BGR2GRAY)
    else:
        reference_gray = reference_image
    
    # Calculate histograms
    source_hist = cv2.calcHist([source_gray], [0], None, [256], [0, 256])
    reference_hist = cv2.calcHist([reference_gray], [0], None, [256], [0, 256])
    
    # Calculate cumulative distribution functions
    source_cdf = source_hist.cumsum()
    reference_cdf = reference_hist.cumsum()
    
    # Normalize CDFs
    source_cdf_normalized = source_cdf / source_cdf.max()
    reference_cdf_normalized = reference_cdf / reference_cdf.max()
    
    # Create lookup table
    lookup_table = np.zeros(256)
    for i in range(256):
        j = 0
        while j < 256 and source_cdf_normalized[i] > reference_cdf_normalized[j]:
            j += 1
        lookup_table[i] = j
    
    # Apply lookup table
    matched = cv2.LUT(source_gray, lookup_table.astype(np.uint8))
    
    return matched


def compute_histogram_statistics(histogram: np.ndarray) -> dict:
    """
    Compute statistics from a histogram.
    
    Args:
        histogram: Input histogram
        
    Returns:
        Dictionary containing histogram statistics
    """
    if histogram is None:
        return {}
    
    # Calculate statistics
    total_pixels = np.sum(histogram)
    mean_intensity = np.sum(np.arange(len(histogram)) * histogram.flatten()) / total_pixels if total_pixels > 0 else 0
    
    # Calculate variance
    variance = np.sum(((np.arange(len(histogram)) - mean_intensity) ** 2) * histogram.flatten()) / total_pixels if total_pixels > 0 else 0
    std_intensity = np.sqrt(variance)
    
    # Find mode (most frequent intensity)
    mode_intensity = np.argmax(histogram)
    
    # Calculate percentiles
    cumulative = np.cumsum(histogram.flatten())
    p25_idx = np.argmax(cumulative >= 0.25 * total_pixels) if total_pixels > 0 else 0
    p50_idx = np.argmax(cumulative >= 0.50 * total_pixels) if total_pixels > 0 else 0
    p75_idx = np.argmax(cumulative >= 0.75 * total_pixels) if total_pixels > 0 else 0
    
    return {
        'total_pixels': total_pixels,
        'mean_intensity': mean_intensity,
        'std_intensity': std_intensity,
        'mode_intensity': mode_intensity,
        'p25_intensity': p25_idx,
        'p50_intensity': p50_idx,
        'p75_intensity': p75_idx,
        'min_intensity': np.min(np.nonzero(histogram.flatten())[0]) if np.any(histogram > 0) else 0,
        'max_intensity': np.max(np.nonzero(histogram.flatten())[0]) if np.any(histogram > 0) else 0
    }


def plot_histogram(histogram: np.ndarray, title: str = "Histogram") -> None:
    """
    Plot histogram using matplotlib.
    
    Args:
        histogram: Input histogram
        title: Plot title
        
    Returns:
        None (displays plot)
    """
    try:
        import matplotlib.pyplot as plt
        
        plt.figure(figsize=(10, 6))
        plt.plot(histogram)
        plt.title(title)
        plt.xlabel('Pixel Intensity')
        plt.ylabel('Frequency')
        plt.grid(True, alpha=0.3)
        plt.show()
    except ImportError:
        print("Matplotlib is required for plotting histograms")


def compare_histogram_methods(image: np.ndarray) -> dict:
    """
    Compare different histogram equalization methods.
    
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
    
    results = {
        'original': gray,
        'equalized': equalize_histogram(gray),
        'clahe': clahe(gray),
        'clahe_aggressive': clahe(gray, clip_limit=4.0, tile_grid_size=(4, 4))
    }
    
    # Add color versions if input is color
    if len(image.shape) == 3:
        results['color_equalized'] = equalize_color_histogram(image)
        results['color_clahe'] = clahe_color(image)
    
    return results


def adaptive_histogram_equalization(image: np.ndarray, method: str = 'clahe',
                                  clip_limit: float = 2.0, tile_grid_size: Tuple[int, int] = (8, 8)) -> np.ndarray:
    """
    Apply adaptive histogram equalization.
    
    Args:
        image: Input image
        method: Method to use ('clahe', 'equalize')
        clip_limit: CLAHE clip limit
        tile_grid_size: CLAHE tile grid size
        
    Returns:
        Equalized image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if method.lower() == 'clahe':
        return clahe(image, clip_limit, tile_grid_size)
    elif method.lower() == 'equalize':
        return equalize_histogram(image)
    else:
        raise ValueError(f"Unknown method: {method}")


def multi_scale_histogram_equalization(image: np.ndarray, scales: list = [1.0, 0.5, 0.25]) -> np.ndarray:
    """
    Apply histogram equalization at multiple scales.
    
    Args:
        image: Input image
        scales: List of scale factors
        
    Returns:
        Multi-scale equalized image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    h, w = image.shape[:2]
    equalized = np.zeros_like(image, dtype=np.float64)
    weights = np.zeros_like(image, dtype=np.float64)
    
    for scale in scales:
        # Resize image
        new_h, new_w = int(h * scale), int(w * scale)
        resized = cv2.resize(image, (new_w, new_h))
        
        # Apply histogram equalization
        if len(resized.shape) == 3:
            equalized_resized = equalize_color_histogram(resized)
        else:
            equalized_resized = equalize_histogram(resized)
        
        # Resize back to original size
        equalized_full = cv2.resize(equalized_resized.astype(np.float64), (w, h))
        
        # Accumulate with weight
        weight = scale
        equalized += weight * equalized_full
        weights += weight
    
    # Normalize
    equalized = equalized / weights
    return np.clip(equalized, 0, 255).astype(np.uint8) 