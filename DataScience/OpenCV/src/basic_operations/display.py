"""
Image display utilities for visualization and comparison.

This module provides functions for displaying images using OpenCV and matplotlib,
creating side-by-side comparisons, and saving visualization results.
"""

import cv2
import numpy as np
import matplotlib.pyplot as plt
from typing import List, Optional, Tuple, Union
import os
from .image_io import validate_image, save_image


def show_image(image: np.ndarray, title: str = "Image", window_size: Optional[Tuple[int, int]] = None,
               wait_key: bool = True, backend: str = 'opencv') -> None:
    """
    Display a single image using OpenCV or matplotlib.
    
    Args:
        image (np.ndarray): Image to display
        title (str): Window/plot title
        window_size (Optional[Tuple[int, int]]): Window size (width, height)
        wait_key (bool): Whether to wait for key press (OpenCV only)
        backend (str): Display backend - 'opencv' or 'matplotlib'
    
    Example:
        >>> show_image(img, "Original Image", backend='matplotlib')
        >>> show_image(img, "Processed", window_size=(800, 600))
    """
    if not validate_image(image):
        print("Error: Invalid image provided for display.")
        return
    
    if backend == 'opencv':
        # OpenCV display
        cv2.namedWindow(title, cv2.WINDOW_NORMAL)
        if window_size:
            cv2.resizeWindow(title, window_size[0], window_size[1])
        
        cv2.imshow(title, image)
        
        if wait_key:
            cv2.waitKey(0)
            cv2.destroyWindow(title)
    
    elif backend == 'matplotlib':
        # Matplotlib display
        plt.figure(figsize=(10, 8) if window_size is None else (window_size[0]/100, window_size[1]/100))
        
        if len(image.shape) == 3:
            # Color image - convert BGR to RGB for matplotlib
            if image.shape[2] == 3:
                image_rgb = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
                plt.imshow(image_rgb)
            else:
                plt.imshow(image)
        else:
            # Grayscale image
            plt.imshow(image, cmap='gray')
        
        plt.title(title)
        plt.axis('off')
        plt.tight_layout()
        plt.show()
    
    else:
        print(f"Error: Unknown backend '{backend}'. Use 'opencv' or 'matplotlib'.")


def show_comparison(images: List[np.ndarray], titles: Optional[List[str]] = None,
                   grid_size: Optional[Tuple[int, int]] = None,
                   figsize: Tuple[int, int] = (15, 5)) -> None:
    """
    Display multiple images side by side for comparison.
    
    Args:
        images (List[np.ndarray]): List of images to display
        titles (Optional[List[str]]): List of titles for each image
        grid_size (Optional[Tuple[int, int]]): Grid layout (rows, cols). Auto-calculated if None
        figsize (Tuple[int, int]): Figure size in inches
    
    Example:
        >>> show_comparison([original, blurred, edges], 
        ...                 titles=['Original', 'Blurred', 'Edges'])
    """
    if not images:
        print("Error: No images provided for comparison.")
        return
    
    # Validate all images
    valid_images = []
    valid_titles = []
    
    for i, img in enumerate(images):
        if validate_image(img):
            valid_images.append(img)
            if titles and i < len(titles):
                valid_titles.append(titles[i])
            else:
                valid_titles.append(f"Image {i+1}")
        else:
            print(f"Warning: Invalid image at index {i}, skipping.")
    
    if not valid_images:
        print("Error: No valid images to display.")
        return
    
    num_images = len(valid_images)
    
    # Calculate grid size if not provided
    if grid_size is None:
        cols = min(num_images, 4)  # Max 4 columns
        rows = (num_images + cols - 1) // cols
    else:
        rows, cols = grid_size
    
    # Create subplots
    fig, axes = plt.subplots(rows, cols, figsize=figsize)
    
    # Handle single subplot case
    if num_images == 1:
        axes = [axes]
    elif rows == 1:
        axes = axes if isinstance(axes, (list, np.ndarray)) else [axes]
    else:
        axes = axes.flatten()
    
    # Display images
    for i, (img, title) in enumerate(zip(valid_images, valid_titles)):
        if i >= len(axes):
            break
        
        ax = axes[i]
        
        if len(img.shape) == 3:
            # Color image - convert BGR to RGB
            if img.shape[2] == 3:
                img_rgb = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
                ax.imshow(img_rgb)
            else:
                ax.imshow(img)
        else:
            # Grayscale image
            ax.imshow(img, cmap='gray')
        
        ax.set_title(title)
        ax.axis('off')
    
    # Hide unused subplots
    for i in range(num_images, len(axes)):
        axes[i].axis('off')
    
    plt.tight_layout()
    plt.show()


def show_grid(images: List[np.ndarray], grid_size: Tuple[int, int],
              titles: Optional[List[str]] = None, figsize: Tuple[int, int] = (12, 8)) -> None:
    """
    Display images in a specific grid layout.
    
    Args:
        images (List[np.ndarray]): List of images to display
        grid_size (Tuple[int, int]): Grid layout (rows, cols)
        titles (Optional[List[str]]): List of titles for each image
        figsize (Tuple[int, int]): Figure size in inches
    
    Example:
        >>> show_grid(images, (2, 3), titles=image_titles)
    """
    rows, cols = grid_size
    expected_images = rows * cols
    
    if len(images) > expected_images:
        print(f"Warning: Too many images ({len(images)}) for grid {grid_size}. "
              f"Only showing first {expected_images}.")
        images = images[:expected_images]
    
    show_comparison(images, titles, grid_size, figsize)


def save_comparison(images: List[np.ndarray], output_path: str,
                   titles: Optional[List[str]] = None,
                   grid_size: Optional[Tuple[int, int]] = None,
                   figsize: Tuple[int, int] = (15, 5), dpi: int = 300) -> bool:
    """
    Save a comparison of multiple images to file.
    
    Args:
        images (List[np.ndarray]): List of images to save
        output_path (str): Output file path
        titles (Optional[List[str]]): List of titles for each image
        grid_size (Optional[Tuple[int, int]]): Grid layout (rows, cols)
        figsize (Tuple[int, int]): Figure size in inches
        dpi (int): Resolution for saved image
    
    Returns:
        bool: True if successful, False otherwise
    
    Example:
        >>> save_comparison([orig, processed], "comparison.png", 
        ...                 titles=['Before', 'After'])
    """
    if not images:
        print("Error: No images provided for saving comparison.")
        return False
    
    # Validate all images
    valid_images = []
    valid_titles = []
    
    for i, img in enumerate(images):
        if validate_image(img):
            valid_images.append(img)
            if titles and i < len(titles):
                valid_titles.append(titles[i])
            else:
                valid_titles.append(f"Image {i+1}")
    
    if not valid_images:
        print("Error: No valid images to save.")
        return False
    
    try:
        num_images = len(valid_images)
        
        # Calculate grid size if not provided
        if grid_size is None:
            cols = min(num_images, 4)
            rows = (num_images + cols - 1) // cols
        else:
            rows, cols = grid_size
        
        # Create figure
        fig, axes = plt.subplots(rows, cols, figsize=figsize)
        
        # Handle single subplot case
        if num_images == 1:
            axes = [axes]
        elif rows == 1:
            axes = axes if isinstance(axes, (list, np.ndarray)) else [axes]
        else:
            axes = axes.flatten()
        
        # Display images
        for i, (img, title) in enumerate(zip(valid_images, valid_titles)):
            if i >= len(axes):
                break
            
            ax = axes[i]
            
            if len(img.shape) == 3:
                # Color image - convert BGR to RGB
                if img.shape[2] == 3:
                    img_rgb = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
                    ax.imshow(img_rgb)
                else:
                    ax.imshow(img)
            else:
                # Grayscale image
                ax.imshow(img, cmap='gray')
            
            ax.set_title(title)
            ax.axis('off')
        
        # Hide unused subplots
        for i in range(num_images, len(axes)):
            axes[i].axis('off')
        
        plt.tight_layout()
        
        # Create output directory if needed
        output_dir = os.path.dirname(output_path)
        if output_dir and not os.path.exists(output_dir):
            os.makedirs(output_dir, exist_ok=True)
        
        # Save figure
        plt.savefig(output_path, dpi=dpi, bbox_inches='tight', 
                   facecolor='white', edgecolor='none')
        plt.close(fig)
        
        print(f"Comparison saved to '{output_path}'")
        return True
    
    except Exception as e:
        print(f"Error saving comparison: {e}")
        return False


def show_histogram(image: np.ndarray, title: str = "Histogram", 
                  color: str = 'blue', bins: int = 256) -> None:
    """
    Display histogram of image pixel values.
    
    Args:
        image (np.ndarray): Input image
        title (str): Plot title
        color (str): Histogram color
        bins (int): Number of histogram bins
    
    Example:
        >>> show_histogram(gray_image, "Grayscale Histogram")
    """
    if not validate_image(image):
        print("Error: Invalid image provided for histogram display.")
        return
    
    plt.figure(figsize=(10, 6))
    
    if len(image.shape) == 3:
        # Color image - show histograms for each channel
        colors = ['blue', 'green', 'red']
        for i, col in enumerate(colors):
            hist = cv2.calcHist([image], [i], None, [bins], [0, 256])
            plt.plot(hist, color=col, label=f'Channel {i}')
        plt.legend()
    else:
        # Grayscale image
        hist = cv2.calcHist([image], [0], None, [bins], [0, 256])
        plt.plot(hist, color=color)
    
    plt.title(title)
    plt.xlabel('Pixel Value')
    plt.ylabel('Frequency')
    plt.grid(True, alpha=0.3)
    plt.show()


def close_all_windows() -> None:
    """Close all OpenCV windows."""
    cv2.destroyAllWindows()


# Export functions for the module
__all__ = [
    'show_image',
    'show_comparison',
    'show_grid',
    'save_comparison',
    'show_histogram',
    'close_all_windows'
]
