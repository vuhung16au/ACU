"""
Color Enhancement Module

This module provides color enhancement and correction functions:
- Brightness and contrast adjustment
- Gamma correction
- White balance
- Color grading

Author: Vu Hung Nguyen
"""

import cv2
import numpy as np
from typing import Tuple, Optional, Union


def adjust_brightness_contrast(image: np.ndarray, alpha: float = 1.0, beta: float = 0) -> np.ndarray:
    """
    Adjust brightness and contrast of an image.
    
    Args:
        image: Input image
        alpha: Contrast factor (1.0 = no change)
        beta: Brightness offset (0 = no change)
        
    Returns:
        Adjusted image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    return cv2.convertScaleAbs(image, alpha=alpha, beta=beta)


def gamma_correction(image: np.ndarray, gamma: float = 1.0) -> np.ndarray:
    """
    Apply gamma correction to an image.
    
    Args:
        image: Input image
        gamma: Gamma value (1.0 = no change, < 1.0 = brighter, > 1.0 = darker)
        
    Returns:
        Gamma corrected image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if gamma <= 0:
        raise ValueError("Gamma must be positive")
    
    # Create lookup table
    inv_gamma = 1.0 / gamma
    table = np.array([((i / 255.0) ** inv_gamma) * 255 for i in np.arange(0, 256)]).astype("uint8")
    
    # Apply gamma correction
    return cv2.LUT(image, table)


def adjust_brightness(image: np.ndarray, beta: float = 0) -> np.ndarray:
    """
    Adjust brightness of an image.
    
    Args:
        image: Input image
        beta: Brightness offset (0 = no change, positive = brighter, negative = darker)
        
    Returns:
        Brightness adjusted image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    return cv2.convertScaleAbs(image, alpha=1.0, beta=beta)


def adjust_contrast(image: np.ndarray, alpha: float = 1.0) -> np.ndarray:
    """
    Adjust contrast of an image.
    
    Args:
        image: Input image
        alpha: Contrast factor (1.0 = no change, > 1.0 = higher contrast, < 1.0 = lower contrast)
        
    Returns:
        Contrast adjusted image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    return cv2.convertScaleAbs(image, alpha=alpha, beta=0)


def adjust_color_temperature(image: np.ndarray, temperature: float = 1.0) -> np.ndarray:
    """
    Adjust color temperature of an image.
    
    Args:
        image: Input image
        temperature: Temperature factor (1.0 = no change, > 1.0 = warmer, < 1.0 = cooler)
        
    Returns:
        Color temperature adjusted image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to float for calculations
    img_float = image.astype(np.float32)
    
    if temperature > 1.0:
        # Make warmer (increase red, decrease blue)
        img_float[:, :, 2] *= temperature  # Red channel
        img_float[:, :, 0] /= temperature  # Blue channel
    else:
        # Make cooler (decrease red, increase blue)
        img_float[:, :, 2] *= temperature  # Red channel
        img_float[:, :, 0] /= temperature  # Blue channel
    
    # Clip values and convert back to uint8
    return np.clip(img_float, 0, 255).astype(np.uint8)


def white_balance_correction(image: np.ndarray, method: str = 'gray_world') -> np.ndarray:
    """
    Apply white balance correction using specified method.
    
    Args:
        image: Input image
        method: White balance method ('gray_world' or 'perfect_reflector')
        
    Returns:
        White balance corrected image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if method == 'gray_world':
        return white_balance_gray_world(image)
    elif method == 'perfect_reflector':
        return white_balance_perfect_reflector(image)
    else:
        raise ValueError(f"Unknown white balance method: {method}")


def white_balance_gray_world(image: np.ndarray) -> np.ndarray:
    """
    Apply gray world white balance correction.
    
    Args:
        image: Input image
        
    Returns:
        White balanced image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if len(image.shape) != 3:
        raise ValueError("Input image must be 3-channel")
    
    # Split channels
    b, g, r = cv2.split(image)
    
    # Calculate mean values
    b_mean = np.mean(b)
    g_mean = np.mean(g)
    r_mean = np.mean(r)
    
    # Calculate scaling factors (assuming gray world)
    gray_mean = (b_mean + g_mean + r_mean) / 3.0
    
    b_scale = gray_mean / b_mean if b_mean > 0 else 1.0
    g_scale = gray_mean / g_mean if g_mean > 0 else 1.0
    r_scale = gray_mean / r_mean if r_mean > 0 else 1.0
    
    # Apply scaling
    b_balanced = np.clip(b * b_scale, 0, 255).astype(np.uint8)
    g_balanced = np.clip(g * g_scale, 0, 255).astype(np.uint8)
    r_balanced = np.clip(r * r_scale, 0, 255).astype(np.uint8)
    
    # Merge channels
    return cv2.merge([b_balanced, g_balanced, r_balanced])


def white_balance_perfect_reflector(image: np.ndarray, percentile: float = 95) -> np.ndarray:
    """
    Apply perfect reflector white balance correction.
    
    Args:
        image: Input image
        percentile: Percentile for brightest pixels
        
    Returns:
        White balanced image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if len(image.shape) != 3:
        raise ValueError("Input image must be 3-channel")
    
    # Split channels
    b, g, r = cv2.split(image)
    
    # Calculate maximum values (brightest pixels)
    b_max = np.percentile(b, percentile)
    g_max = np.percentile(g, percentile)
    r_max = np.percentile(r, percentile)
    
    # Calculate scaling factors
    max_value = max(b_max, g_max, r_max)
    
    b_scale = max_value / b_max if b_max > 0 else 1.0
    g_scale = max_value / g_max if g_max > 0 else 1.0
    r_scale = max_value / r_max if r_max > 0 else 1.0
    
    # Apply scaling
    b_balanced = np.clip(b * b_scale, 0, 255).astype(np.uint8)
    g_balanced = np.clip(g * g_scale, 0, 255).astype(np.uint8)
    r_balanced = np.clip(r * r_scale, 0, 255).astype(np.uint8)
    
    # Merge channels
    return cv2.merge([b_balanced, g_balanced, r_balanced])


def color_grading(image: np.ndarray, shadows: Tuple[float, float, float] = (1.0, 1.0, 1.0),
                  midtones: Tuple[float, float, float] = (1.0, 1.0, 1.0),
                  highlights: Tuple[float, float, float] = (1.0, 1.0, 1.0)) -> np.ndarray:
    """
    Apply color grading to an image.
    
    Args:
        image: Input image
        shadows: RGB multipliers for shadows
        midtones: RGB multipliers for midtones
        highlights: RGB multipliers for highlights
        
    Returns:
        Color graded image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if len(image.shape) != 3:
        raise ValueError("Input image must be 3-channel")
    
    # Convert to float for processing
    img_float = image.astype(np.float32) / 255.0
    
    # Split channels
    b, g, r = cv2.split(img_float)
    
    # Create masks for different tonal ranges
    luminance = 0.299 * r + 0.587 * g + 0.114 * b
    
    shadows_mask = np.clip((0.3 - luminance) / 0.3, 0, 1)
    highlights_mask = np.clip((luminance - 0.7) / 0.3, 0, 1)
    midtones_mask = 1.0 - shadows_mask - highlights_mask
    
    # Apply color grading
    b_graded = (b * shadows_mask * shadows[0] + 
                b * midtones_mask * midtones[0] + 
                b * highlights_mask * highlights[0])
    
    g_graded = (g * shadows_mask * shadows[1] + 
                g * midtones_mask * midtones[1] + 
                g * highlights_mask * highlights[1])
    
    r_graded = (r * shadows_mask * shadows[2] + 
                r * midtones_mask * midtones[2] + 
                r * highlights_mask * highlights[2])
    
    # Merge and convert back to uint8
    graded = cv2.merge([b_graded, g_graded, r_graded])
    return np.clip(graded * 255, 0, 255).astype(np.uint8)


def adjust_saturation(image: np.ndarray, saturation_factor: float = 1.0) -> np.ndarray:
    """
    Adjust saturation of an image.
    
    Args:
        image: Input image
        saturation_factor: Saturation multiplier (1.0 = no change)
        
    Returns:
        Image with adjusted saturation
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if len(image.shape) != 3:
        raise ValueError("Input image must be 3-channel")
    
    # Convert to HSV
    hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)
    
    # Adjust saturation channel
    hsv[:, :, 1] = np.clip(hsv[:, :, 1] * saturation_factor, 0, 255)
    
    # Convert back to BGR
    return cv2.cvtColor(hsv, cv2.COLOR_HSV2BGR)


def adjust_hue(image: np.ndarray, hue_shift: int = 0) -> np.ndarray:
    """
    Adjust hue of an image.
    
    Args:
        image: Input image
        hue_shift: Hue shift in degrees (-180 to 180)
        
    Returns:
        Image with adjusted hue
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if len(image.shape) != 3:
        raise ValueError("Input image must be 3-channel")
    
    # Convert to HSV
    hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)
    
    # Adjust hue channel
    hsv[:, :, 0] = (hsv[:, :, 0] + hue_shift) % 180
    
    # Convert back to BGR
    return cv2.cvtColor(hsv, cv2.COLOR_HSV2BGR)


def create_color_lookup_table(color_matrix: np.ndarray) -> np.ndarray:
    """
    Create a color lookup table from a color transformation matrix.
    
    Args:
        color_matrix: 3x3 color transformation matrix
        
    Returns:
        Color lookup table
    """
    if color_matrix.shape != (3, 3):
        raise ValueError("Color matrix must be 3x3")
    
    # Create lookup table
    table = np.zeros((256, 3), dtype=np.uint8)
    
    for i in range(256):
        # Apply color transformation
        pixel = np.array([i, i, i], dtype=np.float32)
        transformed = np.dot(color_matrix, pixel)
        table[i] = np.clip(transformed, 0, 255).astype(np.uint8)
    
    return table


def apply_color_lookup_table(image: np.ndarray, lookup_table: np.ndarray) -> np.ndarray:
    """
    Apply a color lookup table to an image.
    
    Args:
        image: Input image
        lookup_table: Color lookup table
        
    Returns:
        Image with applied color transformation
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if len(image.shape) != 3:
        raise ValueError("Input image must be 3-channel")
    
    # Split channels
    b, g, r = cv2.split(image)
    
    # Apply lookup table to each channel
    b_transformed = cv2.LUT(b, lookup_table[:, 0])
    g_transformed = cv2.LUT(g, lookup_table[:, 1])
    r_transformed = cv2.LUT(r, lookup_table[:, 2])
    
    # Merge channels
    return cv2.merge([b_transformed, g_transformed, r_transformed])


def create_warm_lookup_table() -> np.ndarray:
    """
    Create a warm color lookup table.
    
    Returns:
        Warm color lookup table
    """
    # Warm color transformation matrix (increases red/yellow, decreases blue)
    warm_matrix = np.array([
        [1.2, 0.0, 0.0],
        [0.0, 1.1, 0.0],
        [0.0, 0.0, 0.8]
    ])
    
    return create_color_lookup_table(warm_matrix)


def create_cool_lookup_table() -> np.ndarray:
    """
    Create a cool color lookup table.
    
    Returns:
        Cool color lookup table
    """
    # Cool color transformation matrix (increases blue, decreases red/yellow)
    cool_matrix = np.array([
        [0.8, 0.0, 0.0],
        [0.0, 0.9, 0.0],
        [0.0, 0.0, 1.2]
    ])
    
    return create_color_lookup_table(cool_matrix)


def create_vintage_lookup_table() -> np.ndarray:
    """
    Create a vintage color lookup table.
    
    Returns:
        Vintage color lookup table
    """
    # Vintage color transformation matrix (sepia-like effect)
    vintage_matrix = np.array([
        [0.393, 0.769, 0.189],
        [0.349, 0.686, 0.168],
        [0.272, 0.534, 0.131]
    ])
    
    return create_color_lookup_table(vintage_matrix)


def compare_color_enhancement_methods(image: np.ndarray) -> dict:
    """
    Compare different color enhancement methods.
    
    Args:
        image: Input image
        
    Returns:
        Dictionary containing results from different methods
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    results = {
        'original': image,
        'brightness_contrast': adjust_brightness_contrast(image, alpha=1.2, beta=10),
        'gamma_corrected': gamma_correction(image, gamma=0.8),
        'gray_world_wb': white_balance_gray_world(image),
        'perfect_reflector_wb': white_balance_perfect_reflector(image),
        'saturated': adjust_saturation(image, saturation_factor=1.5),
        'desaturated': adjust_saturation(image, saturation_factor=0.5),
        'warm': apply_color_lookup_table(image, create_warm_lookup_table()),
        'cool': apply_color_lookup_table(image, create_cool_lookup_table()),
        'vintage': apply_color_lookup_table(image, create_vintage_lookup_table())
    }
    
    return results


def adaptive_color_enhancement(image: np.ndarray, method: str = 'auto') -> np.ndarray:
    """
    Apply adaptive color enhancement based on image characteristics.
    
    Args:
        image: Input image
        method: Enhancement method ('auto', 'bright', 'dark', 'low_contrast')
        
    Returns:
        Enhanced image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if method == 'auto':
        # Analyze image characteristics
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        mean_intensity = np.mean(gray)
        std_intensity = np.std(gray)
        
        if mean_intensity < 100:  # Dark image
            return adjust_brightness_contrast(image, alpha=1.3, beta=20)
        elif mean_intensity > 180:  # Bright image
            return adjust_brightness_contrast(image, alpha=0.8, beta=-10)
        elif std_intensity < 30:  # Low contrast
            return adjust_brightness_contrast(image, alpha=1.5, beta=0)
        else:
            return image
    
    elif method == 'bright':
        return adjust_brightness_contrast(image, alpha=0.8, beta=-10)
    
    elif method == 'dark':
        return adjust_brightness_contrast(image, alpha=1.3, beta=20)
    
    elif method == 'low_contrast':
        return adjust_brightness_contrast(image, alpha=1.5, beta=0)
    
    else:
        raise ValueError(f"Unknown method: {method}")


def multi_scale_color_enhancement(image: np.ndarray, scales: list = [1.0, 0.5, 0.25]) -> np.ndarray:
    """
    Apply color enhancement at multiple scales.
    
    Args:
        image: Input image
        scales: List of scale factors
        
    Returns:
        Multi-scale enhanced image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    h, w = image.shape[:2]
    enhanced = np.zeros_like(image, dtype=np.float64)
    weights = np.zeros_like(image, dtype=np.float64)
    
    for scale in scales:
        # Resize image
        new_h, new_w = int(h * scale), int(w * scale)
        resized = cv2.resize(image, (new_w, new_h))
        
        # Apply color enhancement
        enhanced_resized = adaptive_color_enhancement(resized)
        
        # Resize back to original size
        enhanced_full = cv2.resize(enhanced_resized.astype(np.float64), (w, h))
        
        # Accumulate with weight
        weight = scale
        enhanced += weight * enhanced_full
        weights += weight
    
    # Normalize
    enhanced = enhanced / weights
    return np.clip(enhanced, 0, 255).astype(np.uint8) 