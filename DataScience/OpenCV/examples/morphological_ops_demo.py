"""
Morphological Operations Demo - OpenCV Image Processing Collection

This script demonstrates various morphological operations including:
- Basic operations (erosion, dilation, opening, closing)
- Advanced operations (gradient, top hat, black hat, skeletonization)
- Structuring element manipulation

Usage:
    python morphological_ops_demo.py [image_path]

If no image path is provided, the script will use a sample image.
"""

import sys
import os
import numpy as np
import cv2
from typing import Optional


# Add the src directory to the path so we can import our modules
sys.path.append(os.path.join(os.path.dirname(__file__), '..', 'src'))

from morphological import basic_morphology, advanced_morphology
from basic_operations import image_io, display, basic_transforms


def create_sample_image() -> np.ndarray:
    """Create a sample image with various shapes for morphological demonstration."""
    import cv2
    
    # Create a base image with text and shapes
    height, width = 400, 500
    image = np.zeros((height, width), dtype=np.uint8)
    
    # Add text
    cv2.putText(image, "Morphology Demo", (50, 100), cv2.FONT_HERSHEY_SIMPLEX, 2, 255, 3)
    
    # Add geometric shapes
    cv2.circle(image, (150, 200), 50, 255, -1)
    cv2.rectangle(image, (250, 150), (350, 250), 255, -1)
    cv2.line(image, (50, 300), (450, 350), 255, 5)
    
    # Add some noise
    noise = np.random.randint(0, 255, (height, width), dtype=np.uint8)
    noise = cv2.threshold(noise, 240, 255, cv2.THRESH_BINARY)[1]
    image = cv2.bitwise_or(image, noise)
    
    return image


def demonstrate_basic_morphology(image: np.ndarray, non_interactive: bool = True):
    """Demonstrate basic morphological operations."""
    print("\n" + "="*50)
    print("BASIC MORPHOLOGICAL OPERATIONS")
    print("="*50)
    
    # Create output directory
    output_dir = os.path.join(os.path.dirname(__file__), '..', 'sample_images', 'processed')
    os.makedirs(output_dir, exist_ok=True)
    
    # Create structuring elements
    kernel_3x3 = basic_morphology.create_kernel('rect', (3, 3))
    kernel_5x5 = basic_morphology.create_kernel('rect', (5, 5))
    kernel_cross = basic_morphology.create_kernel('cross', (5, 5))
    kernel_ellipse = basic_morphology.create_kernel('ellipse', (5, 5))
    print("✓ Created structuring elements")
    
    # Erosion
    eroded_3x3 = basic_morphology.erode(image, kernel_3x3)
    eroded_5x5 = basic_morphology.erode(image, kernel_5x5)
    eroded_cross = basic_morphology.erode(image, kernel_cross)
    print("✓ Applied erosion with different kernels")
    
    # Dilation
    dilated_3x3 = basic_morphology.dilate(image, kernel_3x3)
    dilated_5x5 = basic_morphology.dilate(image, kernel_5x5)
    dilated_cross = basic_morphology.dilate(image, kernel_cross)
    print("✓ Applied dilation with different kernels")
    
    # Opening (erosion followed by dilation)
    opened_3x3 = basic_morphology.open(image, kernel_3x3)
    opened_5x5 = basic_morphology.open(image, kernel_5x5)
    print("✓ Applied opening operation")
    
    # Closing (dilation followed by erosion)
    closed_3x3 = basic_morphology.close(image, kernel_3x3)
    closed_5x5 = basic_morphology.close(image, kernel_5x5)
    print("✓ Applied closing operation")
    
    # Display results
    basic_results = [
        image, eroded_3x3, dilated_3x3, opened_3x3, closed_3x3,
        eroded_5x5, dilated_5x5, opened_5x5, closed_5x5
    ]
    basic_titles = [
        "Original", "Eroded 3x3", "Dilated 3x3", "Opened 3x3", "Closed 3x3",
        "Eroded 5x5", "Dilated 5x5", "Opened 5x5", "Closed 5x5"
    ]
    
    display.save_comparison(basic_results, output_path=os.path.join(output_dir, 'basic_morphology_comparison.png'), grid_size=(3, 3), figsize=(18, 15), dpi=150)
    print(f"✓ Saved basic morphology comparison to {os.path.join(output_dir, 'basic_morphology_comparison.png')}")
    
    return basic_results, basic_titles


def demonstrate_advanced_morphology(image: np.ndarray, non_interactive=True):
    """Demonstrate advanced morphological operations."""
    print("\n" + "="*50)
    print("ADVANCED MORPHOLOGICAL OPERATIONS")
    print("="*50)
    
    # Create output directory
    output_dir = os.path.join(os.path.dirname(__file__), '..', 'sample_images', 'processed')
    os.makedirs(output_dir, exist_ok=True)
    
    # Create structuring element
    kernel = basic_morphology.create_kernel('rect', (5, 5))
    
    # Morphological gradient
    gradient = advanced_morphology.morphological_gradient(image, kernel)
    print("✓ Applied morphological gradient")
    
    # Top hat (white hat)
    top_hat = advanced_morphology.top_hat(image, kernel)
    print("✓ Applied top hat operation")
    
    # Black hat
    black_hat = advanced_morphology.black_hat(image, kernel)
    print("✓ Applied black hat operation")
    
    # Hit-or-miss transform (using thin as alternative)
    hit_miss = advanced_morphology.thin(image, kernel)
    print("✓ Applied thinning operation")
    
    # Skeletonization
    skeleton = advanced_morphology.skeletonize(image)
    print("✓ Applied skeletonization")
    
    # Distance transform
    distance = cv2.distanceTransform(cv2.threshold(image, 127, 255, cv2.THRESH_BINARY)[1], cv2.DIST_L2, 5)
    print("✓ Applied distance transform")
    
    # Watershed segmentation (create simple markers)
    _, markers = cv2.connectedComponents(cv2.threshold(image, 127, 255, cv2.THRESH_BINARY)[1])
    if len(image.shape) == 2:
        # Convert grayscale to BGR for watershed
        image_bgr = cv2.cvtColor(image, cv2.COLOR_GRAY2BGR)
        watershed = cv2.watershed(image_bgr, markers)
    else:
        watershed = cv2.watershed(image, markers)
    print("✓ Applied watershed segmentation")
    
    # Display results
    advanced_results = [
        image, gradient, top_hat, black_hat, hit_miss, skeleton, distance, watershed
    ]
    advanced_titles = [
        "Original", "Gradient", "Top Hat", "Black Hat", "Hit-or-Miss", 
        "Skeleton", "Distance", "Watershed"
    ]
    
    display.save_comparison(advanced_results, output_path=os.path.join(output_dir, 'advanced_morphology_comparison.png'), grid_size=(2, 4), figsize=(20, 10), dpi=150)
    print(f"✓ Saved advanced morphology comparison to {os.path.join(output_dir, 'advanced_morphology_comparison.png')}")
    
    return advanced_results, advanced_titles


def demonstrate_morphological_reconstruction(image: np.ndarray, non_interactive=True):
    """Demonstrate morphological reconstruction techniques."""
    print("\n" + "="*50)
    print("MORPHOLOGICAL RECONSTRUCTION")
    print("="*50)
    
    # Create output directory
    output_dir = os.path.join(os.path.dirname(__file__), '..', 'sample_images', 'processed')
    os.makedirs(output_dir, exist_ok=True)
    
    # Create marker image
    kernel = basic_morphology.create_kernel('rect', (3, 3))
    marker = basic_morphology.erode(image, kernel)
    
    # Morphological reconstruction
    reconstructed = advanced_morphology.morphological_reconstruction(marker, image, kernel)
    print("✓ Applied morphological reconstruction")
    
    # Alternative reconstruction with smaller kernel
    small_kernel = basic_morphology.create_kernel('rect', (2, 2))
    reconstructed_small = advanced_morphology.morphological_reconstruction(marker, image, small_kernel)
    print("✓ Applied reconstruction with smaller kernel")
    
    # Opening by reconstruction (using basic operations)
    opening_reconstruction = basic_morphology.open(image, kernel)
    print("✓ Applied opening by reconstruction")
    
    # Closing by reconstruction (using basic operations)
    closing_reconstruction = basic_morphology.close(image, kernel)
    print("✓ Applied closing by reconstruction")
    
    # Display results
    reconstruction_results = [
        image, marker, reconstructed, reconstructed_small, 
        opening_reconstruction, closing_reconstruction
    ]
    reconstruction_titles = [
        "Original", "Marker", "Reconstruction", "Reconstruction Small",
        "Opening by Reconstruction", "Closing by Reconstruction"
    ]
    
    display.save_comparison(reconstruction_results, output_path=os.path.join(output_dir, 'reconstruction_comparison.png'), grid_size=(2, 3), figsize=(18, 12), dpi=150)
    print(f"✓ Saved reconstruction comparison to {os.path.join(output_dir, 'reconstruction_comparison.png')}")
    
    return reconstruction_results, reconstruction_titles


def demonstrate_morphological_filtering(image: np.ndarray, non_interactive=True):
    """Demonstrate morphological filtering techniques."""
    print("\n" + "="*50)
    print("MORPHOLOGICAL FILTERING")
    print("="*50)
    
    # Create output directory
    output_dir = os.path.join(os.path.dirname(__file__), '..', 'sample_images', 'processed')
    os.makedirs(output_dir, exist_ok=True)
    
    # Add noise to image for filtering demonstration
    noisy_image = image.copy()
    noise = np.random.randint(0, 255, image.shape, dtype=np.uint8)
    noise = cv2.threshold(noise, 240, 255, cv2.THRESH_BINARY)[1]
    noisy_image = cv2.bitwise_or(noisy_image, noise)
    
    kernel = basic_morphology.create_kernel('rect', (3, 3))
    
    # Morphological filtering (using basic operations)
    filtered_opening = basic_morphology.open(noisy_image, kernel)
    filtered_closing = basic_morphology.close(noisy_image, kernel)
    # Alternative: combine opening and closing
    filtered_alternating = basic_morphology.close(basic_morphology.open(noisy_image, kernel), kernel)
    print("✓ Applied morphological filtering")
    
    # Area opening and closing (using basic operations with larger kernels)
    large_kernel = basic_morphology.create_kernel('rect', (5, 5))
    area_opened = basic_morphology.open(noisy_image, large_kernel)
    area_closed = basic_morphology.close(noisy_image, large_kernel)
    print("✓ Applied area opening and closing")
    
    # Display results
    filtering_results = [
        image, noisy_image, filtered_opening, filtered_closing, 
        filtered_alternating, area_opened, area_closed
    ]
    filtering_titles = [
        "Original", "Noisy", "Filtered Opening", "Filtered Closing",
        "Filtered Alternating", "Area Opening", "Area Closing"
    ]
    
    display.save_comparison(filtering_results, output_path=os.path.join(output_dir, 'filtering_comparison.png'), grid_size=(2, 4), figsize=(20, 10), dpi=150)
    print(f"✓ Saved filtering comparison to {os.path.join(output_dir, 'filtering_comparison.png')}")
    
    return filtering_results, filtering_titles


def demonstrate_morphological_operations(image_path: Optional[str] = None, non_interactive=True):
    """Demonstrate various morphological operations."""
    
    print("=" * 60)
    print("OpenCV Image Processing - Morphological Operations Demo")
    print("=" * 60)
    
    # 1. Load or create image
    if image_path and os.path.exists(image_path):
        print(f"Loading image from: {image_path}")
        original = image_io.load_image(image_path)
        if original is None:
            print("Failed to load image. Creating sample image...")
            original = create_sample_image()
        else:
            # Convert to grayscale for morphological operations
            original = image_io.convert_color_space(original, cv2.COLOR_BGR2GRAY)
    else:
        print("No valid image path provided. Creating sample image...")
        original = create_sample_image()
    
    # 2. Get image information
    print("\nImage Information:")
    info = image_io.get_image_info(original)
    for key, value in info.items():
        print(f"  {key}: {value}")
    
    # 3. Demonstrate different morphological techniques
    basic_results, basic_titles = demonstrate_basic_morphology(original, non_interactive=True)
    advanced_results, advanced_titles = demonstrate_advanced_morphology(original, non_interactive=True)
    reconstruction_results, reconstruction_titles = demonstrate_morphological_reconstruction(original, non_interactive=True)
    filtering_results, filtering_titles = demonstrate_morphological_filtering(original, non_interactive=True)
    
    # 4. Save results
    print("\nSaving results...")
    
    # Create output directory
    output_dir = os.path.join(os.path.dirname(__file__), '..', 'sample_images', 'processed')
    os.makedirs(output_dir, exist_ok=True)
    
    # Save key results
    results = {
        'morphology_eroded.jpg': basic_results[1],           # Eroded 3x3
        'morphology_dilated.jpg': basic_results[2],          # Dilated 3x3
        'morphology_opened.jpg': basic_results[3],           # Opened 3x3
        'morphology_closed.jpg': basic_results[4],           # Closed 3x3
        'morphology_gradient.jpg': advanced_results[1],      # Gradient
        'morphology_top_hat.jpg': advanced_results[2],       # Top hat
        'morphology_black_hat.jpg': advanced_results[3],     # Black hat
        'morphology_skeleton.jpg': advanced_results[5],      # Skeleton
        'morphology_distance.jpg': advanced_results[6],      # Distance transform
        'morphology_watershed.jpg': advanced_results[7]      # Watershed
    }
    
    saved_count = 0
    for filename, img in results.items():
        output_path = os.path.join(output_dir, filename)
        if image_io.save_image(img, output_path, quality=90):
            saved_count += 1
    
    print(f"✓ Saved {saved_count} morphological images to {output_dir}")
    
    # Save comparison images
    comparison_output = os.path.join(output_dir, 'morphological_comparison.png')
    if display.save_comparison(basic_results, comparison_output, basic_titles, 
                             grid_size=(3, 3), figsize=(18, 15), dpi=150):
        print(f"✓ Saved morphological comparison image to {comparison_output}")
    
    print("\n" + "=" * 60)
    print("Morphological Operations Demo completed successfully!")
    print("Check the 'sample_images/processed' directory for saved results.")
    print("=" * 60)


def main():
    """Main function to run the demo."""
    import cv2  # Import here to check if OpenCV is available
    
    # Get image path from command line argument
    image_path = None
    if len(sys.argv) > 1:
        image_path = sys.argv[1]
        if not os.path.exists(image_path):
            print(f"Warning: Image file '{image_path}' not found.")
            image_path = None
    
    try:
        demonstrate_morphological_operations(image_path, non_interactive=True)
    except KeyboardInterrupt:
        print("\nDemo interrupted by user.")
    except Exception as e:
        print(f"Error running demo: {e}")
        import traceback
        traceback.print_exc()


if __name__ == "__main__":
    main() 