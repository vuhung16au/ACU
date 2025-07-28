"""
Image I/O operations for loading, saving, and basic image manipulation.

This module provides functions for reading images from files, saving processed images,
and basic image information retrieval and validation.
"""

import cv2
import numpy as np
import os
from typing import Optional, Tuple, Union, List
from pathlib import Path


def load_image(image_path: Union[str, Path], color_mode: str = 'color') -> Optional[np.ndarray]:
    """
    Load an image from file with various color mode options.
    
    Args:
        image_path (Union[str, Path]): Path to the image file
        color_mode (str): Color mode - 'color', 'grayscale', or 'unchanged'
            - 'color': Load as BGR color image (default)
            - 'grayscale': Load as grayscale image
            - 'unchanged': Load image as-is including alpha channel
    
    Returns:
        Optional[np.ndarray]: Loaded image as numpy array, None if loading fails
    
    Example:
        >>> img = load_image('sample.jpg', color_mode='color')
        >>> gray_img = load_image('sample.jpg', color_mode='grayscale')
    """
    # Convert Path to string
    image_path = str(image_path)
    
    # Check if file exists
    if not os.path.exists(image_path):
        print(f"Error: Image file '{image_path}' not found.")
        return None
    
    # Determine OpenCV flag based on color mode
    mode_flags = {
        'color': cv2.IMREAD_COLOR,
        'grayscale': cv2.IMREAD_GRAYSCALE,
        'unchanged': cv2.IMREAD_UNCHANGED
    }
    
    if color_mode not in mode_flags:
        print(f"Error: Invalid color mode '{color_mode}'. Use 'color', 'grayscale', or 'unchanged'.")
        return None
    
    try:
        image = cv2.imread(image_path, mode_flags[color_mode])
        
        if image is None:
            print(f"Error: Could not load image '{image_path}'. File might be corrupted or in unsupported format.")
            return None
        
        return image
    
    except Exception as e:
        print(f"Error loading image '{image_path}': {e}")
        return None


def save_image(image: np.ndarray, output_path: Union[str, Path], 
               quality: Optional[int] = None) -> bool:
    """
    Save an image to file with optional quality control.
    
    Args:
        image (np.ndarray): Image to save
        output_path (Union[str, Path]): Output file path
        quality (Optional[int]): JPEG quality (1-100) or PNG compression (0-9)
            - For JPEG: higher values mean better quality (default: 95)
            - For PNG: higher values mean more compression (default: 1)
    
    Returns:
        bool: True if successful, False otherwise
    
    Example:
        >>> success = save_image(processed_img, 'output.jpg', quality=90)
        >>> success = save_image(processed_img, 'output.png', quality=3)
    """
    if not validate_image(image):
        print("Error: Invalid image provided for saving.")
        return False
    
    # Convert Path to string
    output_path = str(output_path)
    
    # Create output directory if it doesn't exist
    output_dir = os.path.dirname(output_path)
    if output_dir and not os.path.exists(output_dir):
        os.makedirs(output_dir, exist_ok=True)
    
    try:
        # Set compression parameters based on file extension and quality
        file_ext = os.path.splitext(output_path)[1].lower()
        params = []
        
        if file_ext in ['.jpg', '.jpeg']:
            if quality is None:
                quality = 95
            params = [cv2.IMWRITE_JPEG_QUALITY, max(1, min(100, quality))]
        elif file_ext == '.png':
            if quality is None:
                quality = 1
            params = [cv2.IMWRITE_PNG_COMPRESSION, max(0, min(9, quality))]
        
        # Save the image
        success = cv2.imwrite(output_path, image, params)
        
        if success:
            print(f"Image saved successfully to '{output_path}'")
            return True
        else:
            print(f"Error: Failed to save image to '{output_path}'")
            return False
    
    except Exception as e:
        print(f"Error saving image to '{output_path}': {e}")
        return False


def get_image_info(image: np.ndarray) -> dict:
    """
    Get comprehensive information about an image.
    
    Args:
        image (np.ndarray): Input image
    
    Returns:
        dict: Dictionary containing image information
            - shape: Image dimensions (height, width, channels)
            - dtype: Data type of image pixels
            - size: Total number of pixels
            - channels: Number of color channels
            - min_value: Minimum pixel value
            - max_value: Maximum pixel value
            - mean_value: Mean pixel value
    
    Example:
        >>> info = get_image_info(image)
        >>> print(f"Image size: {info['shape']}")
    """
    if not validate_image(image):
        return {}
    
    info = {
        'shape': image.shape,
        'dtype': str(image.dtype),
        'size': image.size,
        'channels': len(image.shape) if len(image.shape) == 2 else image.shape[2],
        'min_value': float(np.min(image)),
        'max_value': float(np.max(image)),
        'mean_value': float(np.mean(image))
    }
    
    # Add memory usage information
    info['memory_usage_mb'] = image.nbytes / (1024 * 1024)
    
    return info


def validate_image(image: np.ndarray) -> bool:
    """
    Validate if the input is a valid image array.
    
    Args:
        image (np.ndarray): Image to validate
    
    Returns:
        bool: True if valid image, False otherwise
    
    Example:
        >>> if validate_image(img):
        ...     process_image(img)
    """
    if image is None:
        return False
    
    if not isinstance(image, np.ndarray):
        return False
    
    # Check if image has valid dimensions (2D or 3D)
    if len(image.shape) not in [2, 3]:
        return False
    
    # For 3D images, check if it has valid number of channels
    if len(image.shape) == 3 and image.shape[2] not in [1, 3, 4]:
        return False
    
    # Check if image has valid size
    if image.size == 0:
        return False
    
    return True


def convert_color_space(image: np.ndarray, conversion_code: int) -> Optional[np.ndarray]:
    """
    Convert image between different color spaces.
    
    Args:
        image (np.ndarray): Input image
        conversion_code (int): OpenCV color conversion code (e.g., cv2.COLOR_BGR2RGB)
    
    Returns:
        Optional[np.ndarray]: Converted image, None if conversion fails
    
    Example:
        >>> rgb_image = convert_color_space(bgr_image, cv2.COLOR_BGR2RGB)
        >>> gray_image = convert_color_space(bgr_image, cv2.COLOR_BGR2GRAY)
    """
    if not validate_image(image):
        print("Error: Invalid image provided for color conversion.")
        return None
    
    try:
        converted = cv2.cvtColor(image, conversion_code)
        return converted
    except Exception as e:
        print(f"Error converting color space: {e}")
        return None


def batch_load_images(image_dir: Union[str, Path], pattern: str = "*",
                     color_mode: str = 'color') -> List[Tuple[str, np.ndarray]]:
    """
    Load multiple images from a directory.
    
    Args:
        image_dir (Union[str, Path]): Directory containing images
        pattern (str): File pattern to match (e.g., "*.jpg", "*.png")
        color_mode (str): Color mode for loading images
    
    Returns:
        List[Tuple[str, np.ndarray]]: List of (filename, image) tuples
    
    Example:
        >>> images = batch_load_images("images/", "*.jpg", "grayscale")
        >>> for filename, img in images:
        ...     process_image(img)
    """
    image_dir = Path(image_dir)
    
    if not image_dir.exists():
        print(f"Error: Directory '{image_dir}' does not exist.")
        return []
    
    # Supported image extensions
    supported_extensions = {'.jpg', '.jpeg', '.png', '.bmp', '.tiff', '.tif'}
    
    images = []
    
    # Find all matching files
    for image_path in image_dir.glob(pattern):
        if image_path.suffix.lower() in supported_extensions:
            image = load_image(image_path, color_mode)
            if image is not None:
                images.append((image_path.name, image))
            else:
                print(f"Warning: Could not load '{image_path}'")
    
    print(f"Loaded {len(images)} images from '{image_dir}'")
    return images


# Convenience functions for common operations
def load_image_rgb(image_path: Union[str, Path]) -> Optional[np.ndarray]:
    """Load image and convert BGR to RGB for matplotlib display."""
    image = load_image(image_path, 'color')
    if image is not None:
        return convert_color_space(image, cv2.COLOR_BGR2RGB)
    return None


def load_image_gray(image_path: Union[str, Path]) -> Optional[np.ndarray]:
    """Load image in grayscale mode."""
    return load_image(image_path, 'grayscale')


# Export functions for the module
__all__ = [
    'load_image',
    'save_image',
    'get_image_info',
    'validate_image',
    'convert_color_space',
    'batch_load_images',
    'load_image_rgb',
    'load_image_gray'
]
