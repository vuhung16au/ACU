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


def demonstrate_basic_morphology(image: np.ndarray):
    """Demonstrate basic morphological operations."""
    print("\n" + "="*50)
    print("BASIC MORPHOLOGICAL OPERATIONS")
    print("="*50)
    
    # Create structuring elements
    kernel_3x3 = basic_morphology.create_structuring_element('rect', (3, 3))
    kernel_5x5 = basic_morphology.create_structuring_element('rect', (5, 5))
    kernel_cross = basic_morphology.create_structuring_element('cross', (5, 5))
    kernel_ellipse = basic_morphology.create_structuring_element('ellipse', (5, 5))
    print("✓ Created structuring elements")
    
    # Erosion
    eroded_3x3 = basic_morphology.erode_image(image, kernel_3x3)
    eroded_5x5 = basic_morphology.erode_image(image, kernel_5x5)
    eroded_cross = basic_morphology.erode_image(image, kernel_cross)
    print("✓ Applied erosion with different kernels")
    
    # Dilation
    dilated_3x3 = basic_morphology.dilate_image(image, kernel_3x3)
    dilated_5x5 = basic_morphology.dilate_image(image, kernel_5x5)
    dilated_cross = basic_morphology.dilate_image(image, kernel_cross)
    print("✓ Applied dilation with different kernels")
    
    # Opening (erosion followed by dilation)
    opened_3x3 = basic_morphology.open_image(image, kernel_3x3)
    opened_5x5 = basic_morphology.open_image(image, kernel_5x5)
    print("✓ Applied opening operation")
    
    # Closing (dilation followed by erosion)
    closed_3x3 = basic_morphology.close_image(image, kernel_3x3)
    closed_5x5 = basic_morphology.close_image(image, kernel_5x5)
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
    
    display.show_comparison(basic_results, basic_titles, grid_size=(3, 3), figsize=(18, 15))
    
    return basic_results, basic_titles


def demonstrate_advanced_morphology(image: np.ndarray):
    """Demonstrate advanced morphological operations."""
    print("\n" + "="*50)
    print("ADVANCED MORPHOLOGICAL OPERATIONS")
    print("="*50)
    
    # Create structuring element
    kernel = basic_morphology.create_structuring_element('rect', (5, 5))
    
    # Morphological gradient
    gradient = advanced_morphology.morphological_gradient(image, kernel)
    print("✓ Applied morphological gradient")
    
    # Top hat (white hat)
    top_hat = advanced_morphology.top_hat(image, kernel)
    print("✓ Applied top hat operation")
    
    # Black hat
    black_hat = advanced_morphology.black_hat(image, kernel)
    print("✓ Applied black hat operation")
    
    # Hit-or-miss transform
    hit_miss = advanced_morphology.hit_or_miss_transform(image, kernel)
    print("✓ Applied hit-or-miss transform")
    
    # Skeletonization
    skeleton = advanced_morphology.skeletonize_image(image)
    print("✓ Applied skeletonization")
    
    # Distance transform
    distance = advanced_morphology.distance_transform(image)
    print("✓ Applied distance transform")
    
    # Watershed segmentation
    watershed = advanced_morphology.watershed_segmentation(image)
    print("✓ Applied watershed segmentation")
    
    # Display results
    advanced_results = [
        image, gradient, top_hat, black_hat, hit_miss, skeleton, distance, watershed
    ]
    advanced_titles = [
        "Original", "Gradient", "Top Hat", "Black Hat", "Hit-or-Miss", 
        "Skeleton", "Distance", "Watershed"
    ]
    
    display.show_comparison(advanced_results, advanced_titles, grid_size=(2, 4), figsize=(20, 10))
    
    return advanced_results, advanced_titles


def demonstrate_morphological_reconstruction(image: np.ndarray):
    """Demonstrate morphological reconstruction techniques."""
    print("\n" + "="*50)
    print("MORPHOLOGICAL RECONSTRUCTION")
    print("="*50)
    
    # Create marker image
    kernel = basic_morphology.create_structuring_element('rect', (3, 3))
    marker = basic_morphology.erode_image(image, kernel)
    
    # Geodesic dilation
    geodesic_dilated = advanced_morphology.geodesic_dilation(marker, image, kernel)
    print("✓ Applied geodesic dilation")
    
    # Geodesic erosion
    geodesic_eroded = advanced_morphology.geodesic_erosion(marker, image, kernel)
    print("✓ Applied geodesic erosion")
    
    # Opening by reconstruction
    opening_reconstruction = advanced_morphology.opening_by_reconstruction(image, kernel)
    print("✓ Applied opening by reconstruction")
    
    # Closing by reconstruction
    closing_reconstruction = advanced_morphology.closing_by_reconstruction(image, kernel)
    print("✓ Applied closing by reconstruction")
    
    # Display results
    reconstruction_results = [
        image, marker, geodesic_dilated, geodesic_eroded, 
        opening_reconstruction, closing_reconstruction
    ]
    reconstruction_titles = [
        "Original", "Marker", "Geodesic Dilation", "Geodesic Erosion",
        "Opening by Reconstruction", "Closing by Reconstruction"
    ]
    
    display.show_comparison(reconstruction_results, reconstruction_titles, grid_size=(2, 3), figsize=(18, 12))
    
    return reconstruction_results, reconstruction_titles


def demonstrate_morphological_filtering(image: np.ndarray):
    """Demonstrate morphological filtering techniques."""
    print("\n" + "="*50)
    print("MORPHOLOGICAL FILTERING")
    print("="*50)
    
    # Add noise to image for filtering demonstration
    noisy_image = image.copy()
    noise = np.random.randint(0, 255, image.shape, dtype=np.uint8)
    noise = cv2.threshold(noise, 240, 255, cv2.THRESH_BINARY)[1]
    noisy_image = cv2.bitwise_or(noisy_image, noise)
    
    kernel = basic_morphology.create_structuring_element('rect', (3, 3))
    
    # Morphological filtering
    filtered_opening = advanced_morphology.morphological_filter(noisy_image, kernel, 'opening')
    filtered_closing = advanced_morphology.morphological_filter(noisy_image, kernel, 'closing')
    filtered_alternating = advanced_morphology.morphological_filter(noisy_image, kernel, 'alternating')
    print("✓ Applied morphological filtering")
    
    # Area opening and closing
    area_opened = advanced_morphology.area_opening(noisy_image, 100)
    area_closed = advanced_morphology.area_closing(noisy_image, 100)
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
    
    display.show_comparison(filtering_results, filtering_titles, grid_size=(2, 4), figsize=(20, 10))
    
    return filtering_results, filtering_titles


def demonstrate_morphological_operations(image_path: str = None):
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
    basic_results, basic_titles = demonstrate_basic_morphology(original)
    advanced_results, advanced_titles = demonstrate_advanced_morphology(original)
    reconstruction_results, reconstruction_titles = demonstrate_morphological_reconstruction(original)
    filtering_results, filtering_titles = demonstrate_morphological_filtering(original)
    
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
        demonstrate_morphological_operations(image_path)
    except KeyboardInterrupt:
        print("\nDemo interrupted by user.")
    except Exception as e:
        print(f"Error running demo: {e}")
        import traceback
        traceback.print_exc()


if __name__ == "__main__":
    main() 