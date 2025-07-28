"""
Image Utilities Module

This module provides image utility functions:
- Image validation
- Batch processing utilities
- File format conversion
- Metadata extraction

Author: OpenCV Image Processing Collection
"""

import cv2
import numpy as np
import os
from typing import Tuple, Optional, Union, List, Dict
from pathlib import Path


def validate_image(image: np.ndarray) -> bool:
    """
    Validate if an image is properly formatted.
    
    Args:
        image: Input image
        
    Returns:
        True if image is valid, False otherwise
    """
    if image is None:
        return False
    
    if not isinstance(image, np.ndarray):
        return False
    
    if len(image.shape) < 2 or len(image.shape) > 3:
        return False
    
    if image.size == 0:
        return False
    
    return True


def get_image_info(image: np.ndarray) -> Dict:
    """
    Get information about an image.
    
    Args:
        image: Input image
        
    Returns:
        Dictionary containing image information
    """
    if not validate_image(image):
        return {}
    
    info = {
        'shape': image.shape,
        'dtype': str(image.dtype),
        'size': image.size,
        'min_value': float(np.min(image)),
        'max_value': float(np.max(image)),
        'mean_value': float(np.mean(image)),
        'std_value': float(np.std(image))
    }
    
    if len(image.shape) == 3:
        info['channels'] = image.shape[2]
        info['color_space'] = 'BGR' if image.shape[2] == 3 else 'Multi-channel'
    else:
        info['channels'] = 1
        info['color_space'] = 'Grayscale'
    
    return info


def batch_process_images(image_paths: List[str], processing_function,
                        output_dir: Optional[str] = None, **kwargs) -> List[str]:
    """
    Process multiple images in batch.
    
    Args:
        image_paths: List of image file paths
        processing_function: Function to apply to each image
        output_dir: Output directory for processed images
        **kwargs: Additional arguments for processing function
        
    Returns:
        List of output file paths
    """
    if not image_paths:
        return []
    
    output_paths = []
    
    for i, image_path in enumerate(image_paths):
        try:
            # Load image
            image = cv2.imread(image_path)
            if image is None:
                print(f"Warning: Could not load image {image_path}")
                continue
            
            # Process image
            processed_image = processing_function(image, **kwargs)
            
            # Generate output path
            if output_dir:
                os.makedirs(output_dir, exist_ok=True)
                filename = os.path.basename(image_path)
                name, ext = os.path.splitext(filename)
                output_path = os.path.join(output_dir, f"{name}_processed{ext}")
            else:
                name, ext = os.path.splitext(image_path)
                output_path = f"{name}_processed{ext}"
            
            # Save processed image
            cv2.imwrite(output_path, processed_image)
            output_paths.append(output_path)
            
        except Exception as e:
            print(f"Error processing {image_path}: {str(e)}")
    
    return output_paths


def convert_image_format(image: np.ndarray, target_format: str) -> np.ndarray:
    """
    Convert image to different format.
    
    Args:
        image: Input image
        target_format: Target format ('rgb', 'hsv', 'lab', 'gray')
        
    Returns:
        Converted image
    """
    if not validate_image(image):
        raise ValueError("Invalid input image")
    
    if target_format.lower() == 'rgb':
        if len(image.shape) == 3:
            return cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
        else:
            return cv2.cvtColor(image, cv2.COLOR_GRAY2RGB)
    
    elif target_format.lower() == 'hsv':
        if len(image.shape) == 3:
            return cv2.cvtColor(image, cv2.COLOR_BGR2HSV)
        else:
            raise ValueError("Cannot convert grayscale to HSV")
    
    elif target_format.lower() == 'lab':
        if len(image.shape) == 3:
            return cv2.cvtColor(image, cv2.COLOR_BGR2LAB)
        else:
            raise ValueError("Cannot convert grayscale to LAB")
    
    elif target_format.lower() == 'gray':
        if len(image.shape) == 3:
            return cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        else:
            return image
    
    else:
        raise ValueError(f"Unsupported format: {target_format}")


def resize_image_maintaining_aspect(image: np.ndarray, target_size: Tuple[int, int],
                                  interpolation: int = cv2.INTER_LINEAR) -> np.ndarray:
    """
    Resize image while maintaining aspect ratio.
    
    Args:
        image: Input image
        target_size: Target size (width, height)
        interpolation: Interpolation method
        
    Returns:
        Resized image
    """
    if not validate_image(image):
        raise ValueError("Invalid input image")
    
    h, w = image.shape[:2]
    target_w, target_h = target_size
    
    # Calculate scaling factors
    scale_w = target_w / w
    scale_h = target_h / h
    scale = min(scale_w, scale_h)
    
    # Calculate new dimensions
    new_w = int(w * scale)
    new_h = int(h * scale)
    
    # Resize image
    resized = cv2.resize(image, (new_w, new_h), interpolation=interpolation)
    
    # Create output image with target size
    output = np.zeros((target_h, target_w, 3) if len(image.shape) == 3 else (target_h, target_w), dtype=image.dtype)
    
    # Calculate position to center the resized image
    y_offset = (target_h - new_h) // 2
    x_offset = (target_w - new_w) // 2
    
    # Place resized image in output
    output[y_offset:y_offset+new_h, x_offset:x_offset+new_w] = resized
    
    return output


def create_image_pyramid(image: np.ndarray, levels: int = 4) -> List[np.ndarray]:
    """
    Create an image pyramid.
    
    Args:
        image: Input image
        levels: Number of pyramid levels
        
    Returns:
        List of images at different scales
    """
    if not validate_image(image):
        raise ValueError("Invalid input image")
    
    pyramid = [image]
    
    for i in range(1, levels):
        # Downsample by factor of 2
        downsampled = cv2.pyrDown(pyramid[-1])
        pyramid.append(downsampled)
    
    return pyramid


def extract_image_patches(image: np.ndarray, patch_size: Tuple[int, int],
                         stride: Tuple[int, int] = None) -> List[np.ndarray]:
    """
    Extract patches from an image.
    
    Args:
        image: Input image
        patch_size: Size of patches (width, height)
        stride: Stride for patch extraction (width, height)
        
    Returns:
        List of image patches
    """
    if not validate_image(image):
        raise ValueError("Invalid input image")
    
    if stride is None:
        stride = patch_size
    
    h, w = image.shape[:2]
    patch_w, patch_h = patch_size
    stride_w, stride_h = stride
    
    patches = []
    
    for y in range(0, h - patch_h + 1, stride_h):
        for x in range(0, w - patch_w + 1, stride_w):
            patch = image[y:y+patch_h, x:x+patch_w]
            patches.append(patch)
    
    return patches


def reconstruct_from_patches(patches: List[np.ndarray], original_size: Tuple[int, int],
                           patch_size: Tuple[int, int], stride: Tuple[int, int] = None) -> np.ndarray:
    """
    Reconstruct image from patches.
    
    Args:
        patches: List of image patches
        original_size: Original image size (height, width)
        patch_size: Size of patches (width, height)
        stride: Stride used for patch extraction (width, height)
        
    Returns:
        Reconstructed image
    """
    if not patches:
        raise ValueError("No patches provided")
    
    if stride is None:
        stride = patch_size
    
    h, w = original_size
    patch_w, patch_h = patch_size
    stride_w, stride_h = stride
    
    # Create output image
    if len(patches[0].shape) == 3:
        output = np.zeros((h, w, patches[0].shape[2]), dtype=patches[0].dtype)
    else:
        output = np.zeros((h, w), dtype=patches[0].dtype)
    
    # Count array for averaging overlapping regions
    count = np.zeros_like(output, dtype=np.float32)
    
    patch_idx = 0
    for y in range(0, h - patch_h + 1, stride_h):
        for x in range(0, w - patch_w + 1, stride_w):
            if patch_idx < len(patches):
                output[y:y+patch_h, x:x+patch_w] += patches[patch_idx].astype(np.float32)
                count[y:y+patch_h, x:x+patch_w] += 1
                patch_idx += 1
    
    # Average overlapping regions
    count[count == 0] = 1  # Avoid division by zero
    output = output / count
    
    return output.astype(patches[0].dtype)


def compute_image_statistics(image: np.ndarray) -> Dict:
    """
    Compute comprehensive image statistics.
    
    Args:
        image: Input image
        
    Returns:
        Dictionary containing image statistics
    """
    if not validate_image(image):
        return {}
    
    stats = {
        'basic_info': get_image_info(image),
        'histogram': {},
        'texture': {},
        'noise': {}
    }
    
    # Convert to grayscale for some calculations
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    # Histogram statistics
    hist = cv2.calcHist([gray], [0], None, [256], [0, 256])
    stats['histogram'] = {
        'total_pixels': np.sum(hist),
        'mean_intensity': np.mean(gray),
        'std_intensity': np.std(gray),
        'median_intensity': np.median(gray),
        'mode_intensity': np.argmax(hist),
        'min_intensity': np.min(gray),
        'max_intensity': np.max(gray),
        'dynamic_range': np.max(gray) - np.min(gray)
    }
    
    # Texture statistics (using local variance)
    kernel_size = 5
    kernel = np.ones((kernel_size, kernel_size), np.float32) / (kernel_size * kernel_size)
    mean_local = cv2.filter2D(gray.astype(np.float32), -1, kernel)
    var_local = cv2.filter2D((gray.astype(np.float32) - mean_local) ** 2, -1, kernel)
    
    stats['texture'] = {
        'mean_local_variance': np.mean(var_local),
        'std_local_variance': np.std(var_local),
        'texture_uniformity': np.sum(hist ** 2) / (np.sum(hist) ** 2)
    }
    
    # Noise estimation (using high-frequency components)
    laplacian = cv2.Laplacian(gray, cv2.CV_64F)
    stats['noise'] = {
        'estimated_noise': np.std(laplacian),
        'snr_estimate': np.mean(gray) / np.std(laplacian) if np.std(laplacian) > 0 else float('inf')
    }
    
    return stats


def validate_image_file(file_path: str) -> bool:
    """
    Validate if an image file exists and can be read.
    
    Args:
        file_path: Path to image file
        
    Returns:
        True if file is valid, False otherwise
    """
    if not os.path.exists(file_path):
        return False
    
    if not os.path.isfile(file_path):
        return False
    
    # Try to read the image
    image = cv2.imread(file_path)
    return image is not None


def get_supported_formats() -> List[str]:
    """
    Get list of supported image formats.
    
    Returns:
        List of supported file extensions
    """
    return ['.jpg', '.jpeg', '.png', '.bmp', '.tiff', '.tif', '.webp', '.ppm', '.pgm']


def is_supported_format(file_path: str) -> bool:
    """
    Check if file format is supported.
    
    Args:
        file_path: Path to image file
        
    Returns:
        True if format is supported, False otherwise
    """
    file_ext = Path(file_path).suffix.lower()
    return file_ext in get_supported_formats() 