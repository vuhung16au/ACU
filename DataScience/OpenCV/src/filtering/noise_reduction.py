"""
Noise Reduction Module

This module provides various noise reduction techniques:
- Bilateral filtering
- Non-local means denoising
- Morphological noise reduction
- Wiener filtering

Author: OpenCV Image Processing Collection
"""

import cv2
import numpy as np
from typing import Tuple, Optional, Union


def denoise_bilateral(image: np.ndarray, diameter: int = 9, sigma_color: float = 75,
                     sigma_space: float = 75, border_type: int = cv2.BORDER_DEFAULT) -> np.ndarray:
    """
    Apply bilateral filtering for noise reduction.
    
    Args:
        image: Input image
        diameter: Diameter of each pixel neighborhood
        sigma_color: Filter sigma in the color space
        sigma_space: Filter sigma in the coordinate space
        border_type: Border type for convolution
        
    Returns:
        Denoised image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    return cv2.bilateralFilter(image, diameter, sigma_color, sigma_space, border_type)


def denoise_nlm(image: np.ndarray, h: float = 10, template_window_size: int = 7,
                search_window_size: int = 21) -> np.ndarray:
    """
    Apply non-local means denoising.
    
    Args:
        image: Input image
        h: Parameter regulating filter strength (10 is default)
        template_window_size: Size of template patch (7 is default)
        search_window_size: Size of window for searching similar patches (21 is default)
        
    Returns:
        Denoised image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    return cv2.fastNlMeansDenoising(image, None, h, template_window_size, search_window_size)


def denoise_nlm_colored(image: np.ndarray, h: float = 10, h_color: float = 10,
                        template_window_size: int = 7, search_window_size: int = 21) -> np.ndarray:
    """
    Apply non-local means denoising for colored images.
    
    Args:
        image: Input colored image
        h: Parameter regulating filter strength for luminance (10 is default)
        h_color: Parameter regulating filter strength for color (10 is default)
        template_window_size: Size of template patch (7 is default)
        search_window_size: Size of window for searching similar patches (21 is default)
        
    Returns:
        Denoised image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if len(image.shape) != 3:
        raise ValueError("Input image must be colored (3 channels)")
    
    return cv2.fastNlMeansDenoisingColored(image, None, h, h_color, template_window_size, search_window_size)


def denoise_morphological(image: np.ndarray, kernel_size: int = 3) -> np.ndarray:
    """
    Apply morphological operations for noise reduction.
    
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
    
    # Apply opening (erosion followed by dilation) to remove noise
    opened = cv2.morphologyEx(gray, cv2.MORPH_OPEN, kernel)
    
    # Apply closing (dilation followed by erosion) to fill gaps
    closed = cv2.morphologyEx(opened, cv2.MORPH_CLOSE, kernel)
    
    return closed


def denoise_wiener(image: np.ndarray, kernel_size: Tuple[int, int] = (5, 5),
                   noise_power: float = 0.1) -> np.ndarray:
    """
    Apply Wiener filtering for noise reduction.
    
    Args:
        image: Input image
        kernel_size: Size of the Wiener filter kernel
        noise_power: Noise power ratio
        
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
    
    # Convert to float
    gray_float = gray.astype(np.float64) / 255.0
    
    # Apply Wiener filter
    from scipy import signal
    denoised = signal.wiener(gray_float, kernel_size, noise_power)
    
    # Convert back to uint8
    return np.clip(denoised * 255, 0, 255).astype(np.uint8)


def denoise_median(image: np.ndarray, kernel_size: int = 5) -> np.ndarray:
    """
    Apply median filtering for noise reduction.
    
    Args:
        image: Input image
        kernel_size: Size of the median filter kernel (must be odd)
        
    Returns:
        Denoised image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if kernel_size % 2 == 0:
        raise ValueError("Kernel size must be odd")
    
    return cv2.medianBlur(image, kernel_size)


def denoise_gaussian(image: np.ndarray, kernel_size: Tuple[int, int] = (5, 5),
                     sigma_x: float = 0, sigma_y: float = 0) -> np.ndarray:
    """
    Apply Gaussian filtering for noise reduction.
    
    Args:
        image: Input image
        kernel_size: Size of the Gaussian kernel
        sigma_x: Standard deviation in X direction
        sigma_y: Standard deviation in Y direction
        
    Returns:
        Denoised image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    return cv2.GaussianBlur(image, kernel_size, sigma_x, sigma_y)


def adaptive_denoising(image: np.ndarray, noise_type: str = 'gaussian') -> np.ndarray:
    """
    Apply adaptive denoising based on noise type.
    
    Args:
        image: Input image
        noise_type: Type of noise ('gaussian', 'salt_pepper', 'poisson')
        
    Returns:
        Denoised image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if noise_type == 'gaussian':
        return denoise_nlm(image, h=10)
    elif noise_type == 'salt_pepper':
        return denoise_median(image, kernel_size=5)
    elif noise_type == 'poisson':
        return denoise_bilateral(image, diameter=9, sigma_color=75, sigma_space=75)
    else:
        raise ValueError("Unknown noise type. Use 'gaussian', 'salt_pepper', or 'poisson'")


def multi_scale_denoising(image: np.ndarray, scales: list = [1, 0.5, 0.25]) -> np.ndarray:
    """
    Apply multi-scale denoising.
    
    Args:
        image: Input image
        scales: List of scale factors
        
    Returns:
        Denoised image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    h, w = image.shape[:2]
    denoised = np.zeros_like(image, dtype=np.float64)
    weights = np.zeros_like(image, dtype=np.float64)
    
    for scale in scales:
        # Resize image
        new_h, new_w = int(h * scale), int(w * scale)
        resized = cv2.resize(image, (new_w, new_h))
        
        # Apply denoising
        denoised_resized = denoise_nlm(resized)
        
        # Resize back to original size
        denoised_full = cv2.resize(denoised_resized, (w, h))
        
        # Accumulate with weight
        weight = scale
        denoised += weight * denoised_full.astype(np.float64)
        weights += weight
    
    # Normalize
    denoised = denoised / weights
    return np.clip(denoised, 0, 255).astype(np.uint8)


def compare_denoising_methods(image: np.ndarray) -> dict:
    """
    Compare different denoising methods.
    
    Args:
        image: Input image
        
    Returns:
        Dictionary containing results from different methods
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    results = {
        'original': image,
        'bilateral': denoise_bilateral(image),
        'nlm': denoise_nlm(image),
        'median': denoise_median(image),
        'gaussian': denoise_gaussian(image),
        'morphological': denoise_morphological(image)
    }
    
    # Add colored NLM if image is colored
    if len(image.shape) == 3:
        results['nlm_colored'] = denoise_nlm_colored(image)
    
    return results 