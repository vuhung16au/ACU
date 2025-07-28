"""
Color Space Conversions Module

This module provides color space conversion functions:
- RGB to HSV conversion
- RGB to LAB conversion
- Grayscale conversion
- Color space utilities

Author: OpenCV Image Processing Collection
"""

import cv2
import numpy as np
from typing import Tuple, Optional, Union


def rgb_to_hsv(image: np.ndarray) -> np.ndarray:
    """
    Convert RGB image to HSV color space.
    
    Args:
        image: Input RGB image
        
    Returns:
        HSV image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if len(image.shape) != 3:
        raise ValueError("Input image must be 3-channel")
    
    return cv2.cvtColor(image, cv2.COLOR_BGR2HSV)


def hsv_to_rgb(image: np.ndarray) -> np.ndarray:
    """
    Convert HSV image to RGB color space.
    
    Args:
        image: Input HSV image
        
    Returns:
        RGB image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if len(image.shape) != 3:
        raise ValueError("Input image must be 3-channel")
    
    return cv2.cvtColor(image, cv2.COLOR_HSV2BGR)


def rgb_to_lab(image: np.ndarray) -> np.ndarray:
    """
    Convert RGB image to LAB color space.
    
    Args:
        image: Input RGB image
        
    Returns:
        LAB image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if len(image.shape) != 3:
        raise ValueError("Input image must be 3-channel")
    
    return cv2.cvtColor(image, cv2.COLOR_BGR2LAB)


def lab_to_rgb(image: np.ndarray) -> np.ndarray:
    """
    Convert LAB image to RGB color space.
    
    Args:
        image: Input LAB image
        
    Returns:
        RGB image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if len(image.shape) != 3:
        raise ValueError("Input image must be 3-channel")
    
    return cv2.cvtColor(image, cv2.COLOR_LAB2BGR)


def rgb_to_gray(image: np.ndarray) -> np.ndarray:
    """
    Convert RGB image to grayscale.
    
    Args:
        image: Input RGB image
        
    Returns:
        Grayscale image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if len(image.shape) == 3:
        return cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        return image


def rgb_to_gray_average(image: np.ndarray) -> np.ndarray:
    """
    Convert RGB image to grayscale using simple average method.
    
    Args:
        image: Input RGB image (BGR format)
        
    Returns:
        Grayscale image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if len(image.shape) != 3:
        raise ValueError("Input image must be 3-channel")
    
    # Simple average of RGB channels (note: OpenCV uses BGR)
    return np.mean(image, axis=2).astype(np.uint8)


def rgb_to_gray_luminance(image: np.ndarray) -> np.ndarray:
    """
    Convert RGB image to grayscale using luminance method.
    
    Args:
        image: Input RGB image (BGR format)
        
    Returns:
        Grayscale image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if len(image.shape) != 3:
        raise ValueError("Input image must be 3-channel")
    
    # Luminance method with weights for BGR format
    b, g, r = cv2.split(image.astype(np.float32))
    gray = 0.07 * b + 0.72 * g + 0.21 * r
    return np.clip(gray, 0, 255).astype(np.uint8)


def rgb_to_gray_custom(image: np.ndarray, weights: list = [0.299, 0.587, 0.114]) -> np.ndarray:
    """
    Convert RGB image to grayscale using custom weights.
    
    Args:
        image: Input RGB image (BGR format)
        weights: Custom weights for [B, G, R] channels
        
    Returns:
        Grayscale image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if len(image.shape) != 3:
        raise ValueError("Input image must be 3-channel")
    
    if len(weights) != 3:
        raise ValueError("Weights must be a list of 3 values")
    
    if abs(sum(weights) - 1.0) > 0.01:
        # Normalize weights if they don't sum to 1
        weights = [w / sum(weights) for w in weights]
    
    # Apply custom weights to BGR channels
    b, g, r = cv2.split(image.astype(np.float32))
    gray = weights[0] * b + weights[1] * g + weights[2] * r
    return np.clip(gray, 0, 255).astype(np.uint8)


def rgb_to_yuv(image: np.ndarray) -> np.ndarray:
    """
    Convert RGB image to YUV color space.
    
    Args:
        image: Input RGB image
        
    Returns:
        YUV image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if len(image.shape) != 3:
        raise ValueError("Input image must be 3-channel")
    
    return cv2.cvtColor(image, cv2.COLOR_BGR2YUV)


def yuv_to_rgb(image: np.ndarray) -> np.ndarray:
    """
    Convert YUV image to RGB color space.
    
    Args:
        image: Input YUV image
        
    Returns:
        RGB image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if len(image.shape) != 3:
        raise ValueError("Input image must be 3-channel")
    
    return cv2.cvtColor(image, cv2.COLOR_YUV2BGR)


def rgb_to_ycrcb(image: np.ndarray) -> np.ndarray:
    """
    Convert RGB image to YCrCb color space.
    
    Args:
        image: Input RGB image
        
    Returns:
        YCrCb image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if len(image.shape) != 3:
        raise ValueError("Input image must be 3-channel")
    
    return cv2.cvtColor(image, cv2.COLOR_BGR2YCrCb)


def ycrcb_to_rgb(image: np.ndarray) -> np.ndarray:
    """
    Convert YCrCb image to RGB color space.
    
    Args:
        image: Input YCrCb image
        
    Returns:
        RGB image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if len(image.shape) != 3:
        raise ValueError("Input image must be 3-channel")
    
    return cv2.cvtColor(image, cv2.COLOR_YCrCb2BGR)


def split_channels(image: np.ndarray) -> Tuple[np.ndarray, np.ndarray, np.ndarray]:
    """
    Split a 3-channel image into individual channels.
    
    Args:
        image: Input 3-channel image
        
    Returns:
        Tuple of (channel1, channel2, channel3)
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    if len(image.shape) != 3:
        raise ValueError("Input image must be 3-channel")
    
    return cv2.split(image)


def merge_channels(channel1: np.ndarray, channel2: np.ndarray, 
                  channel3: np.ndarray) -> np.ndarray:
    """
    Merge three channels into a 3-channel image.
    
    Args:
        channel1: First channel
        channel2: Second channel
        channel3: Third channel
        
    Returns:
        Merged 3-channel image
    """
    if channel1 is None or channel2 is None or channel3 is None:
        raise ValueError("All channels must be provided")
    
    if channel1.shape != channel2.shape or channel2.shape != channel3.shape:
        raise ValueError("All channels must have the same shape")
    
    return cv2.merge([channel1, channel2, channel3])


def extract_hsv_components(image: np.ndarray) -> Tuple[np.ndarray, np.ndarray, np.ndarray]:
    """
    Extract H, S, V components from an image.
    
    Args:
        image: Input RGB image
        
    Returns:
        Tuple of (H, S, V) components
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    hsv = rgb_to_hsv(image)
    return split_channels(hsv)


def extract_lab_components(image: np.ndarray) -> Tuple[np.ndarray, np.ndarray, np.ndarray]:
    """
    Extract L, A, B components from an image.
    
    Args:
        image: Input RGB image
        
    Returns:
        Tuple of (L, A, B) components
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    lab = rgb_to_lab(image)
    return split_channels(lab)


def color_space_conversion(image: np.ndarray, from_space: str, to_space: str) -> np.ndarray:
    """
    Convert image between different color spaces.
    
    Args:
        image: Input image
        from_space: Source color space ('rgb', 'hsv', 'lab', 'yuv', 'ycrcb')
        to_space: Target color space ('rgb', 'hsv', 'lab', 'yuv', 'ycrcb')
        
    Returns:
        Converted image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Define color space conversion codes
    conversions = {
        ('rgb', 'hsv'): cv2.COLOR_BGR2HSV,
        ('hsv', 'rgb'): cv2.COLOR_HSV2BGR,
        ('rgb', 'lab'): cv2.COLOR_BGR2LAB,
        ('lab', 'rgb'): cv2.COLOR_LAB2BGR,
        ('rgb', 'yuv'): cv2.COLOR_BGR2YUV,
        ('yuv', 'rgb'): cv2.COLOR_YUV2BGR,
        ('rgb', 'ycrcb'): cv2.COLOR_BGR2YCrCb,
        ('ycrcb', 'rgb'): cv2.COLOR_YCrCb2BGR,
        ('rgb', 'gray'): cv2.COLOR_BGR2GRAY,
        ('hsv', 'lab'): lambda img: cv2.cvtColor(cv2.cvtColor(img, cv2.COLOR_HSV2BGR), cv2.COLOR_BGR2LAB),
        ('lab', 'hsv'): lambda img: cv2.cvtColor(cv2.cvtColor(img, cv2.COLOR_LAB2BGR), cv2.COLOR_BGR2HSV)
    }
    
    conversion_key = (from_space.lower(), to_space.lower())
    
    if conversion_key not in conversions:
        raise ValueError(f"Unsupported conversion: {from_space} to {to_space}")
    
    conversion_func = conversions[conversion_key]
    
    if callable(conversion_func):
        return conversion_func(image)
    else:
        return cv2.cvtColor(image, conversion_func)


def compare_color_spaces(image: np.ndarray) -> dict:
    """
    Compare different color space representations of an image.
    
    Args:
        image: Input RGB image
        
    Returns:
        Dictionary containing different color space representations
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    results = {
        'rgb': image,
        'hsv': rgb_to_hsv(image),
        'lab': rgb_to_lab(image),
        'yuv': rgb_to_yuv(image),
        'ycrcb': rgb_to_ycrcb(image),
        'gray': rgb_to_gray(image)
    }
    
    # Extract individual channels
    h, s, v = extract_hsv_components(image)
    l, a, b = extract_lab_components(image)
    
    results['hsv_h'] = h
    results['hsv_s'] = s
    results['hsv_v'] = v
    results['lab_l'] = l
    results['lab_a'] = a
    results['lab_b'] = b
    
    return results


def color_space_statistics(image: np.ndarray) -> dict:
    """
    Compute statistics for different color spaces.
    
    Args:
        image: Input RGB image
        
    Returns:
        Dictionary containing color space statistics
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to different color spaces
    hsv = rgb_to_hsv(image)
    lab = rgb_to_lab(image)
    gray = rgb_to_gray(image)
    
    # Split channels
    b, g, r = split_channels(image)
    h, s, v = split_channels(hsv)
    l, a, b_lab = split_channels(lab)
    
    statistics = {
        'rgb': {
            'r_mean': np.mean(r), 'r_std': np.std(r),
            'g_mean': np.mean(g), 'g_std': np.std(g),
            'b_mean': np.mean(b), 'b_std': np.std(b)
        },
        'hsv': {
            'h_mean': np.mean(h), 'h_std': np.std(h),
            's_mean': np.mean(s), 's_std': np.std(s),
            'v_mean': np.mean(v), 'v_std': np.std(v)
        },
        'lab': {
            'l_mean': np.mean(l), 'l_std': np.std(l),
            'a_mean': np.mean(a), 'a_std': np.std(a),
            'b_mean': np.mean(b_lab), 'b_std': np.std(b_lab)
        },
        'gray': {
            'mean': np.mean(gray), 'std': np.std(gray)
        }
    }
    
    return statistics


def create_color_mask(image: np.ndarray, color_space: str = 'hsv',
                     lower_bound: Tuple[int, int, int] = (0, 0, 0),
                     upper_bound: Tuple[int, int, int] = (255, 255, 255)) -> np.ndarray:
    """
    Create a color mask based on color range.
    
    Args:
        image: Input image
        color_space: Color space to use ('hsv', 'lab', 'rgb')
        lower_bound: Lower bound for color range
        upper_bound: Upper bound for color range
        
    Returns:
        Binary mask
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to specified color space
    if color_space.lower() == 'hsv':
        converted = rgb_to_hsv(image)
    elif color_space.lower() == 'lab':
        converted = rgb_to_lab(image)
    else:
        converted = image
    
    # Create mask
    mask = cv2.inRange(converted, np.array(lower_bound), np.array(upper_bound))
    
    return mask 