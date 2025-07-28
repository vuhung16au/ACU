"""
Visualization Module

This module provides visualization functions:
- Display images
- Create comparison grids
- Plot histograms
- Show progress

Author: OpenCV Image Processing Collection
"""

import cv2
import numpy as np
from typing import List, Tuple, Optional, Union
import matplotlib.pyplot as plt


def display_images(images: List[np.ndarray], titles: Optional[List[str]] = None,
                  window_name: str = "Image Display", wait_time: int = 0) -> None:
    """
    Display multiple images using OpenCV.
    
    Args:
        images: List of images to display
        titles: List of titles for each image
        window_name: Base name for windows
        wait_time: Time to wait for key press (0 = wait indefinitely)
        
    Returns:
        None
    """
    if not images:
        return
    
    if titles is None:
        titles = [f"Image {i+1}" for i in range(len(images))]
    
    for i, (image, title) in enumerate(zip(images, titles)):
        if image is not None:
            window_title = f"{window_name} - {title}"
            cv2.imshow(window_title, image)
    
    cv2.waitKey(wait_time)
    cv2.destroyAllWindows()


def create_comparison_grid(images: List[np.ndarray], titles: Optional[List[str]] = None,
                          grid_size: Optional[Tuple[int, int]] = None,
                          max_width: int = 800) -> np.ndarray:
    """
    Create a grid of images for comparison.
    
    Args:
        images: List of images to display
        titles: List of titles for each image
        grid_size: Grid size (rows, cols). If None, auto-calculate
        max_width: Maximum width of the grid
        
    Returns:
        Grid image
    """
    if not images:
        return np.array([])
    
    if titles is None:
        titles = [f"Image {i+1}" for i in range(len(images))]
    
    # Determine grid size
    if grid_size is None:
        n_images = len(images)
        cols = int(np.ceil(np.sqrt(n_images)))
        rows = int(np.ceil(n_images / cols))
    else:
        rows, cols = grid_size
    
    # Get dimensions of first image
    if images[0] is not None:
        h, w = images[0].shape[:2]
    else:
        h, w = 100, 100
    
    # Calculate cell size
    cell_width = min(w, max_width // cols)
    cell_height = int(cell_width * h / w)
    
    # Create grid
    grid_h = cell_height * rows
    grid_w = cell_width * cols
    
    if len(images[0].shape) == 3:
        grid = np.zeros((grid_h, grid_w, 3), dtype=np.uint8)
    else:
        grid = np.zeros((grid_h, grid_w), dtype=np.uint8)
    
    # Place images in grid
    for i, (image, title) in enumerate(zip(images, titles)):
        if i >= rows * cols:
            break
        
        row = i // cols
        col = i % cols
        
        if image is not None:
            # Resize image to fit cell
            resized = cv2.resize(image, (cell_width, cell_height))
            
            # Convert to 3-channel if needed
            if len(resized.shape) == 2 and len(grid.shape) == 3:
                resized = cv2.cvtColor(resized, cv2.COLOR_GRAY2BGR)
            
            # Place in grid
            y_start = row * cell_height
            y_end = y_start + cell_height
            x_start = col * cell_width
            x_end = x_start + cell_width
            
            grid[y_start:y_end, x_start:x_end] = resized
            
            # Add title
            if len(grid.shape) == 3:
                cv2.putText(grid, title, (x_start + 5, y_start + 20),
                           cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 1)
            else:
                cv2.putText(grid, title, (x_start + 5, y_start + 20),
                           cv2.FONT_HERSHEY_SIMPLEX, 0.5, 255, 1)
    
    return grid


def plot_histogram(histogram: np.ndarray, title: str = "Histogram",
                   color: str = 'blue', bins: Optional[int] = None) -> None:
    """
    Plot histogram using matplotlib.
    
    Args:
        histogram: Histogram data
        title: Plot title
        color: Color of the histogram
        bins: Number of bins (if None, use length of histogram)
        
    Returns:
        None (displays plot)
    """
    try:
        plt.figure(figsize=(10, 6))
        
        if bins is None:
            bins = len(histogram)
        
        plt.bar(range(bins), histogram.flatten(), color=color, alpha=0.7)
        plt.title(title)
        plt.xlabel('Pixel Intensity')
        plt.ylabel('Frequency')
        plt.grid(True, alpha=0.3)
        plt.show()
    except ImportError:
        print("Matplotlib is required for plotting histograms")


def plot_color_histograms(histograms: List[np.ndarray], colors: List[str] = ['blue', 'green', 'red'],
                         title: str = "Color Histograms") -> None:
    """
    Plot histograms for multiple color channels.
    
    Args:
        histograms: List of histograms for each channel
        colors: List of colors for each channel
        title: Plot title
        
    Returns:
        None (displays plot)
    """
    try:
        plt.figure(figsize=(12, 8))
        
        for i, (hist, color) in enumerate(zip(histograms, colors)):
            plt.subplot(len(histograms), 1, i+1)
            plt.bar(range(len(hist)), hist.flatten(), color=color, alpha=0.7)
            plt.title(f'{title} - Channel {i+1}')
            plt.xlabel('Pixel Intensity')
            plt.ylabel('Frequency')
            plt.grid(True, alpha=0.3)
        
        plt.tight_layout()
        plt.show()
    except ImportError:
        print("Matplotlib is required for plotting histograms")


def plot_image_statistics(image: np.ndarray, title: str = "Image Statistics") -> None:
    """
    Plot comprehensive image statistics.
    
    Args:
        image: Input image
        title: Plot title
        
    Returns:
        None (displays plot)
    """
    try:
        from .image_utils import compute_image_statistics
        
        stats = compute_image_statistics(image)
        
        if not stats:
            print("Could not compute image statistics")
            return
        
        fig, axes = plt.subplots(2, 2, figsize=(15, 10))
        fig.suptitle(title)
        
        # Basic statistics
        basic_stats = stats['basic_info']
        axes[0, 0].bar(['Min', 'Max', 'Mean', 'Std'], 
                       [basic_stats['min_value'], basic_stats['max_value'], 
                        basic_stats['mean_value'], basic_stats['std_value']])
        axes[0, 0].set_title('Basic Statistics')
        axes[0, 0].grid(True, alpha=0.3)
        
        # Histogram
        if len(image.shape) == 3:
            gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        else:
            gray = image
        
        hist = cv2.calcHist([gray], [0], None, [256], [0, 256])
        axes[0, 1].plot(hist)
        axes[0, 1].set_title('Histogram')
        axes[0, 1].set_xlabel('Pixel Intensity')
        axes[0, 1].set_ylabel('Frequency')
        axes[0, 1].grid(True, alpha=0.3)
        
        # Texture statistics
        texture_stats = stats['texture']
        axes[1, 0].bar(['Local Var Mean', 'Local Var Std', 'Uniformity'], 
                       [texture_stats['mean_local_variance'], texture_stats['std_local_variance'],
                        texture_stats['texture_uniformity']])
        axes[1, 0].set_title('Texture Statistics')
        axes[1, 0].grid(True, alpha=0.3)
        
        # Noise statistics
        noise_stats = stats['noise']
        axes[1, 1].bar(['Estimated Noise', 'SNR'], 
                       [noise_stats['estimated_noise'], noise_stats['snr_estimate']])
        axes[1, 1].set_title('Noise Statistics')
        axes[1, 1].grid(True, alpha=0.3)
        
        plt.tight_layout()
        plt.show()
    except ImportError:
        print("Matplotlib is required for plotting statistics")


def show_progress(current: int, total: int, description: str = "Processing") -> None:
    """
    Show progress bar.
    
    Args:
        current: Current progress
        total: Total number of items
        description: Description of the process
        
    Returns:
        None
    """
    try:
        import tqdm
        # This would be used in a loop with tqdm
        pass
    except ImportError:
        # Simple progress display
        percentage = (current / total) * 100
        bar_length = 50
        filled_length = int(bar_length * current // total)
        bar = '█' * filled_length + '-' * (bar_length - filled_length)
        print(f'\r{description}: |{bar}| {percentage:.1f}% ({current}/{total})', end='')
        
        if current == total:
            print()


def create_side_by_side_comparison(image1: np.ndarray, image2: np.ndarray,
                                  title1: str = "Image 1", title2: str = "Image 2") -> np.ndarray:
    """
    Create a side-by-side comparison of two images.
    
    Args:
        image1: First image
        image2: Second image
        title1: Title for first image
        title2: Title for second image
        
    Returns:
        Side-by-side comparison image
    """
    if image1 is None or image2 is None:
        return np.array([])
    
    # Get dimensions
    h1, w1 = image1.shape[:2]
    h2, w2 = image2.shape[:2]
    
    # Use the larger height
    max_h = max(h1, h2)
    
    # Resize images to have the same height
    if h1 != max_h:
        scale1 = max_h / h1
        new_w1 = int(w1 * scale1)
        image1 = cv2.resize(image1, (new_w1, max_h))
    
    if h2 != max_h:
        scale2 = max_h / h2
        new_w2 = int(w2 * scale2)
        image2 = cv2.resize(image2, (new_w2, max_h))
    
    # Create side-by-side image
    if len(image1.shape) == 3 and len(image2.shape) == 3:
        combined = np.zeros((max_h, image1.shape[1] + image2.shape[1], 3), dtype=np.uint8)
    else:
        # Convert to grayscale if needed
        if len(image1.shape) == 3:
            image1 = cv2.cvtColor(image1, cv2.COLOR_BGR2GRAY)
        if len(image2.shape) == 3:
            image2 = cv2.cvtColor(image2, cv2.COLOR_BGR2GRAY)
        combined = np.zeros((max_h, image1.shape[1] + image2.shape[1]), dtype=np.uint8)
    
    # Place images side by side
    combined[:, :image1.shape[1]] = image1
    combined[:, image1.shape[1]:] = image2
    
    # Add titles
    if len(combined.shape) == 3:
        cv2.putText(combined, title1, (10, 30), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 255), 2)
        cv2.putText(combined, title2, (image1.shape[1] + 10, 30), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 255), 2)
    else:
        cv2.putText(combined, title1, (10, 30), cv2.FONT_HERSHEY_SIMPLEX, 1, 255, 2)
        cv2.putText(combined, title2, (image1.shape[1] + 10, 30), cv2.FONT_HERSHEY_SIMPLEX, 1, 255, 2)
    
    return combined


def create_before_after_comparison(before_image: np.ndarray, after_image: np.ndarray,
                                 before_title: str = "Before", after_title: str = "After") -> np.ndarray:
    """
    Create a before/after comparison image.
    
    Args:
        before_image: Original image
        after_image: Processed image
        before_title: Title for original image
        after_title: Title for processed image
        
    Returns:
        Before/after comparison image
    """
    return create_side_by_side_comparison(before_image, after_image, before_title, after_title)


def plot_processing_results(original: np.ndarray, results: dict, max_images_per_row: int = 3) -> None:
    """
    Plot processing results in a grid.
    
    Args:
        original: Original image
        results: Dictionary of {name: processed_image} pairs
        max_images_per_row: Maximum number of images per row
        
    Returns:
        None (displays plot)
    """
    try:
        # Prepare images and titles
        images = [original] + list(results.values())
        titles = ['Original'] + list(results.keys())
        
        # Create grid
        n_images = len(images)
        n_cols = min(max_images_per_row, n_images)
        n_rows = (n_images + n_cols - 1) // n_cols
        
        fig, axes = plt.subplots(n_rows, n_cols, figsize=(15, 5 * n_rows))
        if n_rows == 1:
            axes = axes.reshape(1, -1)
        
        for i, (image, title) in enumerate(zip(images, titles)):
            row = i // n_cols
            col = i % n_cols
            
            if image is not None:
                if len(image.shape) == 3:
                    # Convert BGR to RGB for matplotlib
                    image_rgb = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
                    axes[row, col].imshow(image_rgb)
                else:
                    axes[row, col].imshow(image, cmap='gray')
                
                axes[row, col].set_title(title)
                axes[row, col].axis('off')
        
        # Hide empty subplots
        for i in range(n_images, n_rows * n_cols):
            row = i // n_cols
            col = i % n_cols
            axes[row, col].axis('off')
        
        plt.tight_layout()
        plt.show()
    except ImportError:
        print("Matplotlib is required for plotting results")


def create_animation_frames(images: List[np.ndarray], output_path: str = "animation.gif") -> None:
    """
    Create an animated GIF from a list of images.
    
    Args:
        images: List of images for animation
        output_path: Output file path
        
    Returns:
        None
    """
    try:
        import imageio
        
        # Convert images to RGB if needed
        rgb_images = []
        for image in images:
            if image is not None:
                if len(image.shape) == 3:
                    rgb_image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
                else:
                    rgb_image = cv2.cvtColor(image, cv2.COLOR_GRAY2RGB)
                rgb_images.append(rgb_image)
        
        # Save as GIF
        imageio.mimsave(output_path, rgb_images, duration=0.5)
        print(f"Animation saved to {output_path}")
    except ImportError:
        print("Imageio is required for creating animations")
    except Exception as e:
        print(f"Error creating animation: {str(e)}") 