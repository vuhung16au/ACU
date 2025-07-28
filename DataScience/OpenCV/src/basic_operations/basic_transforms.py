"""
Basic image transformations including resize, rotate, flip, crop, and translate.

This module provides fundamental geometric transformations for image preprocessing
and data augmentation.
"""

import cv2
import numpy as np
from typing import Tuple, Optional, Union
from .image_io import validate_image


def resize_image(image: np.ndarray, size: Tuple[int, int], 
                interpolation: int = cv2.INTER_LINEAR) -> Optional[np.ndarray]:
    """
    Resize an image to specified dimensions.
    
    Args:
        image (np.ndarray): Input image
        size (Tuple[int, int]): Target size (width, height)
        interpolation (int): Interpolation method
            - cv2.INTER_LINEAR: Bilinear interpolation (default)
            - cv2.INTER_CUBIC: Bicubic interpolation
            - cv2.INTER_NEAREST: Nearest neighbor
            - cv2.INTER_AREA: Area-based (good for shrinking)
            - cv2.INTER_LANCZOS4: Lanczos interpolation
    
    Returns:
        Optional[np.ndarray]: Resized image, None if operation fails
    
    Example:
        >>> resized = resize_image(img, (640, 480))
        >>> resized_hq = resize_image(img, (1920, 1080), cv2.INTER_CUBIC)
    """
    if not validate_image(image):
        print("Error: Invalid image provided for resizing.")
        return None
    
    if len(size) != 2 or size[0] <= 0 or size[1] <= 0:
        print("Error: Size must be a tuple of two positive integers (width, height).")
        return None
    
    try:
        resized = cv2.resize(image, size, interpolation=interpolation)
        return resized
    except Exception as e:
        print(f"Error resizing image: {e}")
        return None


def resize_with_aspect_ratio(image: np.ndarray, target_size: Union[int, Tuple[int, int]], 
                           fit_mode: str = 'fit') -> Optional[np.ndarray]:
    """
    Resize image while maintaining aspect ratio.
    
    Args:
        image (np.ndarray): Input image
        target_size (Union[int, Tuple[int, int]]): Target size
            - If int: maximum dimension (width or height)
            - If tuple: (width, height) - image will fit within these dimensions
        fit_mode (str): How to fit the image
            - 'fit': Fit within dimensions (may have padding)
            - 'fill': Fill dimensions (may crop image)
            - 'stretch': Stretch to exact dimensions (ignores aspect ratio)
    
    Returns:
        Optional[np.ndarray]: Resized image, None if operation fails
    
    Example:
        >>> resized = resize_with_aspect_ratio(img, 800)  # Max dimension 800px
        >>> resized = resize_with_aspect_ratio(img, (800, 600), 'fit')
    """
    if not validate_image(image):
        print("Error: Invalid image provided for resizing.")
        return None
    
    h, w = image.shape[:2]
    
    # Handle different target_size formats
    if isinstance(target_size, int):
        max_dim = target_size
        if w > h:
            new_w, new_h = max_dim, int(h * max_dim / w)
        else:
            new_w, new_h = int(w * max_dim / h), max_dim
    else:
        target_w, target_h = target_size
        
        if fit_mode == 'stretch':
            new_w, new_h = target_w, target_h
        elif fit_mode == 'fit':
            # Scale to fit within target dimensions
            scale = min(target_w / w, target_h / h)
            new_w, new_h = int(w * scale), int(h * scale)
        elif fit_mode == 'fill':
            # Scale to fill target dimensions
            scale = max(target_w / w, target_h / h)
            new_w, new_h = int(w * scale), int(h * scale)
        else:
            print(f"Error: Unknown fit_mode '{fit_mode}'. Use 'fit', 'fill', or 'stretch'.")
            return None
    
    return resize_image(image, (new_w, new_h))


def rotate_image(image: np.ndarray, angle: float, center: Optional[Tuple[int, int]] = None,
                scale: float = 1.0, border_mode: int = cv2.BORDER_CONSTANT,
                border_value: Union[int, Tuple[int, ...]] = 0) -> Optional[np.ndarray]:
    """
    Rotate an image by a specified angle.
    
    Args:
        image (np.ndarray): Input image
        angle (float): Rotation angle in degrees (positive = counterclockwise)
        center (Optional[Tuple[int, int]]): Rotation center (x, y). If None, uses image center
        scale (float): Scaling factor
        border_mode (int): Border extrapolation method
        border_value (Union[int, Tuple[int, ...]]): Border fill value
    
    Returns:
        Optional[np.ndarray]: Rotated image, None if operation fails
    
    Example:
        >>> rotated = rotate_image(img, 45)  # Rotate 45 degrees
        >>> rotated = rotate_image(img, -30, scale=1.2)  # Rotate and scale
    """
    if not validate_image(image):
        print("Error: Invalid image provided for rotation.")
        return None
    
    h, w = image.shape[:2]
    
    # Use image center if center not specified
    if center is None:
        center = (w // 2, h // 2)
    
    try:
        # Get rotation matrix
        rotation_matrix = cv2.getRotationMatrix2D(center, angle, scale)
        
        # Apply rotation
        rotated = cv2.warpAffine(image, rotation_matrix, (w, h), 
                               borderMode=border_mode, borderValue=border_value)
        
        return rotated
    except Exception as e:
        print(f"Error rotating image: {e}")
        return None


def rotate_image_90(image: np.ndarray, times: int = 1) -> Optional[np.ndarray]:
    """
    Rotate image by 90-degree increments (optimized for speed).
    
    Args:
        image (np.ndarray): Input image
        times (int): Number of 90-degree rotations (1=90°, 2=180°, 3=270°)
    
    Returns:
        Optional[np.ndarray]: Rotated image, None if operation fails
    
    Example:
        >>> rotated_90 = rotate_image_90(img, 1)   # 90 degrees
        >>> rotated_180 = rotate_image_90(img, 2)  # 180 degrees
    """
    if not validate_image(image):
        print("Error: Invalid image provided for rotation.")
        return None
    
    # Normalize times to 0-3 range
    times = times % 4
    
    if times == 0:
        return image.copy()
    elif times == 1:
        return cv2.rotate(image, cv2.ROTATE_90_COUNTERCLOCKWISE)
    elif times == 2:
        return cv2.rotate(image, cv2.ROTATE_180)
    elif times == 3:
        return cv2.rotate(image, cv2.ROTATE_90_CLOCKWISE)


def flip_image(image: np.ndarray, flip_code: int = 1) -> Optional[np.ndarray]:
    """
    Flip an image horizontally, vertically, or both.
    
    Args:
        image (np.ndarray): Input image
        flip_code (int): Flip direction
            - 1: Horizontal flip (default)
            - 0: Vertical flip
            - -1: Both horizontal and vertical flip
    
    Returns:
        Optional[np.ndarray]: Flipped image, None if operation fails
    
    Example:
        >>> h_flipped = flip_image(img, 1)   # Horizontal flip
        >>> v_flipped = flip_image(img, 0)   # Vertical flip
        >>> both_flipped = flip_image(img, -1)  # Both directions
    """
    if not validate_image(image):
        print("Error: Invalid image provided for flipping.")
        return None
    
    if flip_code not in [-1, 0, 1]:
        print("Error: flip_code must be -1 (both), 0 (vertical), or 1 (horizontal).")
        return None
    
    try:
        flipped = cv2.flip(image, flip_code)
        return flipped
    except Exception as e:
        print(f"Error flipping image: {e}")
        return None


def crop_image(image: np.ndarray, x: int, y: int, width: int, height: int) -> Optional[np.ndarray]:
    """
    Crop a rectangular region from an image.
    
    Args:
        image (np.ndarray): Input image
        x (int): Left coordinate of crop region
        y (int): Top coordinate of crop region
        width (int): Width of crop region
        height (int): Height of crop region
    
    Returns:
        Optional[np.ndarray]: Cropped image, None if operation fails
    
    Example:
        >>> cropped = crop_image(img, 100, 50, 200, 150)
    """
    if not validate_image(image):
        print("Error: Invalid image provided for cropping.")
        return None
    
    h, w = image.shape[:2]
    
    # Validate crop parameters
    if x < 0 or y < 0 or width <= 0 or height <= 0:
        print("Error: Invalid crop parameters. x, y must be >= 0, width and height must be > 0.")
        return None
    
    if x + width > w or y + height > h:
        print(f"Error: Crop region exceeds image boundaries. Image size: {w}x{h}")
        return None
    
    try:
        cropped = image[y:y+height, x:x+width]
        return cropped
    except Exception as e:
        print(f"Error cropping image: {e}")
        return None


def crop_center(image: np.ndarray, crop_size: Tuple[int, int]) -> Optional[np.ndarray]:
    """
    Crop a centered region from an image.
    
    Args:
        image (np.ndarray): Input image
        crop_size (Tuple[int, int]): Size of crop region (width, height)
    
    Returns:
        Optional[np.ndarray]: Cropped image, None if operation fails
    
    Example:
        >>> center_crop = crop_center(img, (400, 300))
    """
    if not validate_image(image):
        print("Error: Invalid image provided for center cropping.")
        return None
    
    h, w = image.shape[:2]
    crop_w, crop_h = crop_size
    
    if crop_w > w or crop_h > h:
        print(f"Error: Crop size {crop_size} exceeds image size {w}x{h}")
        return None
    
    # Calculate center coordinates
    start_x = (w - crop_w) // 2
    start_y = (h - crop_h) // 2
    
    return crop_image(image, start_x, start_y, crop_w, crop_h)


def translate_image(image: np.ndarray, dx: int, dy: int, 
                   border_mode: int = cv2.BORDER_CONSTANT,
                   border_value: Union[int, Tuple[int, ...]] = 0) -> Optional[np.ndarray]:
    """
    Translate (shift) an image by specified offsets.
    
    Args:
        image (np.ndarray): Input image
        dx (int): Horizontal translation (positive = right)
        dy (int): Vertical translation (positive = down)
        border_mode (int): Border extrapolation method
        border_value (Union[int, Tuple[int, ...]]): Border fill value
    
    Returns:
        Optional[np.ndarray]: Translated image, None if operation fails
    
    Example:
        >>> shifted = translate_image(img, 50, -30)  # Move right 50px, up 30px
    """
    if not validate_image(image):
        print("Error: Invalid image provided for translation.")
        return None
    
    h, w = image.shape[:2]
    
    try:
        # Create translation matrix
        translation_matrix = np.float32([[1, 0, dx], [0, 1, dy]])
        
        # Apply translation
        translated = cv2.warpAffine(image, translation_matrix, (w, h),
                                  borderMode=border_mode, borderValue=border_value)
        
        return translated
    except Exception as e:
        print(f"Error translating image: {e}")
        return None


def pad_image(image: np.ndarray, padding: Union[int, Tuple[int, ...]], 
             border_type: int = cv2.BORDER_CONSTANT,
             value: Union[int, Tuple[int, ...]] = 0) -> Optional[np.ndarray]:
    """
    Add padding around an image.
    
    Args:
        image (np.ndarray): Input image
        padding (Union[int, Tuple[int, ...]]): Padding specification
            - If int: same padding on all sides
            - If tuple of 2: (horizontal_padding, vertical_padding)
            - If tuple of 4: (top, bottom, left, right)
        border_type (int): Border type (cv2.BORDER_CONSTANT, etc.)
        value (Union[int, Tuple[int, ...]]): Fill value for constant border
    
    Returns:
        Optional[np.ndarray]: Padded image, None if operation fails
    
    Example:
        >>> padded = pad_image(img, 50)  # 50px padding all around
        >>> padded = pad_image(img, (20, 30, 20, 30))  # Different padding each side
    """
    if not validate_image(image):
        print("Error: Invalid image provided for padding.")
        return None
    
    try:
        if isinstance(padding, int):
            # Same padding on all sides
            top = bottom = left = right = padding
        elif len(padding) == 2:
            # Horizontal and vertical padding
            left = right = padding[0]
            top = bottom = padding[1]
        elif len(padding) == 4:
            # Individual padding for each side
            top, bottom, left, right = padding
        else:
            print("Error: Padding must be int or tuple of 2 or 4 values.")
            return None
        
        padded = cv2.copyMakeBorder(image, top, bottom, left, right, 
                                  border_type, value=value)
        return padded
    
    except Exception as e:
        print(f"Error padding image: {e}")
        return None


# Export functions for the module
__all__ = [
    'resize_image',
    'resize_with_aspect_ratio',
    'rotate_image',
    'rotate_image_90',
    'flip_image',
    'crop_image',
    'crop_center',
    'translate_image',
    'pad_image'
]
