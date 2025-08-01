"""
Image Filtering Demo - Vu Hung Nguyen

This script demonstrates various image filtering techniques including:
- Smoothing filters (Gaussian, Box, Median, Bilateral)
- Edge detection (Canny, Sobel, Laplacian, Scharr)
- Noise reduction techniques

Usage:
    python 02_image_filtering_demo.py [image_path]

If no image path is provided, the script will use a sample image.
"""

import sys
import os
import numpy as np
import cv2


# Add the src directory to the path so we can import our modules
sys.path.append(os.path.join(os.path.dirname(__file__), '..', 'src'))

from filtering import smoothing, edge_detection, noise_reduction
from basic_operations import image_io, display, basic_transforms


def create_sample_image() -> np.ndarray:
    """Create a sample image with various features for filtering demonstration."""
    import cv2  # Explicit import to fix scoping issue
    # Create a base image with gradients and shapes
    height, width = 400, 500
    image = np.zeros((height, width, 3), dtype=np.uint8)
    
    # Create gradients
    for y in range(height):
        for x in range(width):
            image[y, x] = [
                int(255 * x / width),          # Red gradient
                int(255 * y / height),         # Green gradient
                int(255 * (x + y) / (width + height))  # Blue gradient
            ]
    
    # Add geometric shapes
    cv2.circle(image, (150, 150), 80, (255, 255, 255), -1)
    cv2.rectangle(image, (300, 100), (450, 250), (0, 0, 0), -1)
    cv2.line(image, (50, 300), (450, 350), (255, 0, 0), 5)
    
    # Add text
    cv2.putText(image, "Filtering Demo", (50, 380), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 255), 2)
    
    # Add some noise for demonstration
    noise = np.random.normal(0, 25, image.shape).astype(np.uint8)
    image = cv2.add(image, noise)
    
    return image


def demonstrate_smoothing_filters(image: np.ndarray, non_interactive: bool = True):
    """Demonstrate various smoothing filters."""
    print("\n" + "="*50)
    print("SMOOTHING FILTERS")
    print("="*50)
    
    # Create output directory
    output_dir = os.path.join(os.path.dirname(__file__), '..', 'sample_images', 'processed')
    os.makedirs(output_dir, exist_ok=True)
    
    # Convert to grayscale for some operations
    import cv2  # Explicit import to fix scoping issue
    gray = image_io.convert_color_space(image, cv2.COLOR_BGR2GRAY)
    
    # Gaussian blur
    gaussian_5 = smoothing.gaussian_blur(image, (5, 5), 0)
    gaussian_15 = smoothing.gaussian_blur(image, (15, 15), 0)
    print("✓ Applied Gaussian blur (5x5 and 15x15)")
    
    # Box filter
    box_5 = smoothing.box_filter(image, (5, 5))
    box_15 = smoothing.box_filter(image, (15, 15))
    print("✓ Applied Box filter (5x5 and 15x15)")
    
    # Median filter
    median_5 = smoothing.median_filter(image, 5)
    median_9 = smoothing.median_filter(image, 9)
    print("✓ Applied Median filter (5x5 and 9x9)")
    
    # Bilateral filter
    bilateral = smoothing.bilateral_filter(image, 9, 75, 75)
    print("✓ Applied Bilateral filter")
    
    # Display results
    smoothing_results = [
        image, gaussian_5, gaussian_15, box_5, box_15, median_5, median_9, bilateral
    ]
    smoothing_titles = [
        "Original", "Gaussian 5x5", "Gaussian 15x15", "Box 5x5",
        "Box 15x15", "Median 5x5", "Median 9x9", "Bilateral"
    ]
    
    display.save_comparison(smoothing_results, output_path=os.path.join(output_dir, 'smoothing_comparison.png'), grid_size=(2, 4), figsize=(20, 10), dpi=150)
    print(f"✓ Saved smoothing comparison to {os.path.join(output_dir, 'smoothing_comparison.png')}")
    
    return smoothing_results, smoothing_titles


def demonstrate_edge_detection(image: np.ndarray, non_interactive=True):
    """Demonstrate various edge detection techniques."""
    print("\n" + "="*50)
    print("EDGE DETECTION")
    print("="*50)
    
    # Create output directory
    output_dir = os.path.join(os.path.dirname(__file__), '..', 'sample_images', 'processed')
    os.makedirs(output_dir, exist_ok=True)
    
    # Convert to grayscale
    import cv2  # Explicit import to fix scoping issue
    gray = image_io.convert_color_space(image, cv2.COLOR_BGR2GRAY)
    
    # Canny edge detection
    canny_low = edge_detection.canny_edge_detection(gray, 50, 150)
    canny_high = edge_detection.canny_edge_detection(gray, 100, 200)
    print("✓ Applied Canny edge detection (low and high thresholds)")
    
    # Sobel operators
    sobel_x = edge_detection.sobel_edge_detection(gray, 1, 0, ksize=3)
    sobel_y = edge_detection.sobel_edge_detection(gray, 0, 1, ksize=3)
    sobel_combined = edge_detection.sobel_edge_detection(gray, 1, 1, ksize=3)
    print("✓ Applied Sobel operators (X, Y, and combined)")
    
    # Laplacian
    laplacian = edge_detection.laplacian_edge_detection(gray, ksize=3)
    print("✓ Applied Laplacian operator")
    
    # Scharr operator
    scharr_x = edge_detection.scharr_edge_detection(gray, 1, 0)
    scharr_y = edge_detection.scharr_edge_detection(gray, 0, 1)
    print("✓ Applied Scharr operators (X and Y)")
    
    # Display results
    edge_results = [
        gray, canny_low, canny_high, sobel_x, sobel_y, sobel_combined, laplacian, scharr_x, scharr_y
    ]
    edge_titles = [
        "Grayscale", "Canny Low", "Canny High", "Sobel X", "Sobel Y", 
        "Sobel Combined", "Laplacian", "Scharr X", "Scharr Y"
    ]
    
    display.save_comparison(edge_results, output_path=os.path.join(output_dir, 'edge_detection_comparison.png'), grid_size=(3, 3), figsize=(18, 15), dpi=150)
    print(f"✓ Saved edge_detection comparison to {os.path.join(output_dir, 'edge_detection_comparison.png')}")
    
    return edge_results, edge_titles


def add_noise_to_image(image: np.ndarray, noise_type: str = 'gaussian') -> np.ndarray:
    """Add noise to an image for demonstration purposes."""
    if noise_type == 'gaussian':
        noise = np.random.normal(0, 25, image.shape).astype(np.int16)
        noisy = np.clip(image.astype(np.int16) + noise, 0, 255).astype(np.uint8)
        return noisy
    elif noise_type == 'salt_pepper':
        noisy = image.copy()
        # Salt noise
        salt_coords = np.random.random(image.shape[:2]) < 0.025
        noisy[salt_coords] = 255
        # Pepper noise
        pepper_coords = np.random.random(image.shape[:2]) < 0.025
        noisy[pepper_coords] = 0
        return noisy
    return image


def demonstrate_noise_reduction(image: np.ndarray, non_interactive=True):
    """Demonstrate noise reduction techniques."""
    print("\n" + "="*50)
    print("NOISE REDUCTION")
    print("="*50)
    
    # Create output directory
    output_dir = os.path.join(os.path.dirname(__file__), '..', 'sample_images', 'processed')
    os.makedirs(output_dir, exist_ok=True)
    
    # Add different types of noise for demonstration
    noisy_gaussian = add_noise_to_image(image, 'gaussian')
    noisy_salt_pepper = add_noise_to_image(image, 'salt_pepper')
    print("✓ Created noisy test images")
    
    # Denoise using different methods
    denoised_gaussian = noise_reduction.denoise_gaussian(noisy_gaussian)
    denoised_median = noise_reduction.denoise_median(noisy_gaussian)
    denoised_bilateral = noise_reduction.denoise_bilateral(noisy_gaussian)
    print("✓ Applied denoising to Gaussian noise")
    
    # Handle salt and pepper noise
    denoised_salt_pepper = noise_reduction.denoise_median(noisy_salt_pepper)
    print("✓ Applied median filtering to salt & pepper noise")
    
    # Non-local means denoising
    denoised_nlm = noise_reduction.denoise_nlm(noisy_gaussian)
    print("✓ Applied Non-local means denoising")
    
    # Display results
    noise_results = [
        image, noisy_gaussian, denoised_gaussian, denoised_median, 
        denoised_bilateral, denoised_nlm, noisy_salt_pepper, denoised_salt_pepper
    ]
    noise_titles = [
        "Original", "Gaussian Noise", "Gaussian Denoised", "Median Denoised",
        "Bilateral Denoised", "NLM Denoised", "Salt & Pepper", "Median Filtered"
    ]
    
    display.save_comparison(noise_results, output_path=os.path.join(output_dir, 'noise_reduction_comparison.png'), grid_size=(2, 4), figsize=(20, 10), dpi=150)
    print(f"✓ Saved noise_reduction comparison to {os.path.join(output_dir, 'noise_reduction_comparison.png')}")
    
    return noise_results, noise_titles


def demonstrate_advanced_filtering(image: np.ndarray, non_interactive=True):
    """Demonstrate advanced filtering techniques."""
    print("\n" + "="*50)
    print("ADVANCED FILTERING")
    print("="*50)
    
    # Create output directory
    output_dir = os.path.join(os.path.dirname(__file__), '..', 'sample_images', 'processed')
    os.makedirs(output_dir, exist_ok=True)
    
    import cv2  # Explicit import to fix scoping issue
    gray = image_io.convert_color_space(image, cv2.COLOR_BGR2GRAY)
    
    # Multi-scale edge detection
    edges_multi_dict = edge_detection.multi_scale_edge_detection(gray)
    edges_multi = edges_multi_dict['scale_1']  # Use scale 1 for display
    print("✓ Applied multi-scale edge detection")
    
    # Adaptive edge detection
    adaptive_edges = edge_detection.adaptive_edge_detection(gray)
    print("✓ Applied adaptive edge detection")
    
    # Combined edge detection
    combined_edges = edge_detection.combine_edge_detectors(gray)
    print("✓ Applied combined edge detection")
    
    # Display results
    advanced_results = [gray, edges_multi, adaptive_edges, combined_edges]
    advanced_titles = ["Grayscale", "Multi-scale Edges", "Adaptive Edges", "Combined Edges"]
    
    display.save_comparison(advanced_results, output_path=os.path.join(output_dir, 'advanced_filtering_comparison.png'), figsize=(16, 4), dpi=150)
    print(f"✓ Saved advanced filtering comparison to {os.path.join(output_dir, 'advanced_filtering_comparison.png')}")
    
    return advanced_results, advanced_titles


def demonstrate_image_filtering(image_path: str | None = None, non_interactive=True):
    """Demonstrate various image filtering techniques."""
    
    print("=" * 60)
    print("OpenCV Image Processing - Image Filtering Demo")
    print("=" * 60)
    
    # 1. Load or create image
    if image_path and os.path.exists(image_path):
        print(f"Loading image from: {image_path}")
        original = image_io.load_image(image_path)
        if original is None:
            print("Failed to load image. Creating sample image...")
            original = create_sample_image()
    else:
        print("No valid image path provided. Creating sample image...")
        original = create_sample_image()
    
    # 2. Get image information
    print("\nImage Information:")
    info = image_io.get_image_info(original)
    for key, value in info.items():
        print(f"  {key}: {value}")
    
    # 3. Demonstrate different filtering techniques
    smoothing_results, smoothing_titles = demonstrate_smoothing_filters(original, non_interactive=True)
    edge_results, edge_titles = demonstrate_edge_detection(original, non_interactive=True)
    noise_results, noise_titles = demonstrate_noise_reduction(original, non_interactive=True)
    advanced_results, advanced_titles = demonstrate_advanced_filtering(original, non_interactive=True)
    
    # 4. Save results
    print("\nSaving results...")
    
    # Create output directory
    output_dir = os.path.join(os.path.dirname(__file__), '..', 'sample_images', 'processed')
    os.makedirs(output_dir, exist_ok=True)
    
    # Save key results
    results = {
        'filtering_gaussian_blur.jpg': smoothing_results[1],  # Gaussian 5x5
        'filtering_bilateral.jpg': smoothing_results[7],       # Bilateral
        'filtering_canny_edges.jpg': edge_results[1],         # Canny low
        'filtering_sobel_combined.jpg': edge_results[5],      # Sobel combined
        'filtering_laplacian.jpg': edge_results[6],           # Laplacian
        'filtering_denoised.jpg': noise_results[2],           # Gaussian denoised
        'filtering_median_denoised.jpg': noise_results[3],    # Median denoised
        'filtering_adaptive_threshold.jpg': advanced_results[2] # Adaptive threshold
    }
    
    saved_count = 0
    for filename, img in results.items():
        output_path = os.path.join(output_dir, filename)
        if image_io.save_image(img, output_path, quality=90):
            saved_count += 1
    
    print(f"✓ Saved {saved_count} filtered images to {output_dir}")
    
    # Save comparison images
    comparison_output = os.path.join(output_dir, 'filtering_comparison.png')
    if display.save_comparison(smoothing_results, comparison_output, smoothing_titles, 
                             grid_size=(2, 4), figsize=(20, 10), dpi=150):
        print(f"✓ Saved filtering comparison image to {comparison_output}")
    
    print("\n" + "=" * 60)
    print("Image Filtering Demo completed successfully!")
    print("Check the 'sample_images/processed' directory for saved results.")
    print("=" * 60)


def main():
    """Main function to run the demo."""
    # Get image path from command line argument
    image_path = None
    if len(sys.argv) > 1:
        image_path = sys.argv[1]
        if not os.path.exists(image_path):
            print(f"Warning: Image file '{image_path}' not found.")
            image_path = None
    
    try:
        demonstrate_image_filtering(image_path, non_interactive=True)
    except KeyboardInterrupt:
        print("\nDemo interrupted by user.")
    except Exception as e:
        print(f"Error running demo: {e}")
        import traceback
        traceback.print_exc()


if __name__ == "__main__":
    main() 