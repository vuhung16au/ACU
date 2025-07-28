"""
Image Filtering Demo - OpenCV Image Processing Collection

This script demonstrates various image filtering techniques including:
- Smoothing filters (Gaussian, Box, Median, Bilateral)
- Edge detection (Canny, Sobel, Laplacian, Scharr)
- Noise reduction techniques

Usage:
    python image_filtering_demo.py [image_path]

If no image path is provided, the script will use a sample image.
"""

import sys
import os
import numpy as np

# Add the src directory to the path so we can import our modules
sys.path.append(os.path.join(os.path.dirname(__file__), '..', 'src'))

from filtering import smoothing, edge_detection, noise_reduction
from basic_operations import image_io, display, basic_transforms


def create_sample_image() -> np.ndarray:
    """Create a sample image with various features for filtering demonstration."""
    import cv2
    
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


def demonstrate_smoothing_filters(image: np.ndarray):
    """Demonstrate various smoothing filters."""
    print("\n" + "="*50)
    print("SMOOTHING FILTERS")
    print("="*50)
    
    # Convert to grayscale for some operations
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
    
    display.show_comparison(smoothing_results, smoothing_titles, grid_size=(2, 4), figsize=(20, 10))
    
    return smoothing_results, smoothing_titles


def demonstrate_edge_detection(image: np.ndarray):
    """Demonstrate various edge detection techniques."""
    print("\n" + "="*50)
    print("EDGE DETECTION")
    print("="*50)
    
    # Convert to grayscale
    gray = image_io.convert_color_space(image, cv2.COLOR_BGR2GRAY)
    
    # Canny edge detection
    canny_low = edge_detection.canny_edge_detection(gray, 50, 150)
    canny_high = edge_detection.canny_edge_detection(gray, 100, 200)
    print("✓ Applied Canny edge detection (low and high thresholds)")
    
    # Sobel operators
    sobel_x = edge_detection.sobel_operator(gray, 1, 0, ksize=3)
    sobel_y = edge_detection.sobel_operator(gray, 0, 1, ksize=3)
    sobel_combined = edge_detection.sobel_operator(gray, 1, 1, ksize=3)
    print("✓ Applied Sobel operators (X, Y, and combined)")
    
    # Laplacian
    laplacian = edge_detection.laplacian_operator(gray, ksize=3)
    print("✓ Applied Laplacian operator")
    
    # Scharr operator
    scharr_x = edge_detection.scharr_operator(gray, 1, 0)
    scharr_y = edge_detection.scharr_operator(gray, 0, 1)
    print("✓ Applied Scharr operators (X and Y)")
    
    # Display results
    edge_results = [
        gray, canny_low, canny_high, sobel_x, sobel_y, sobel_combined, laplacian, scharr_x, scharr_y
    ]
    edge_titles = [
        "Grayscale", "Canny Low", "Canny High", "Sobel X", "Sobel Y", 
        "Sobel Combined", "Laplacian", "Scharr X", "Scharr Y"
    ]
    
    display.show_comparison(edge_results, edge_titles, grid_size=(3, 3), figsize=(18, 15))
    
    return edge_results, edge_titles


def demonstrate_noise_reduction(image: np.ndarray):
    """Demonstrate noise reduction techniques."""
    print("\n" + "="*50)
    print("NOISE REDUCTION")
    print("="*50)
    
    # Add different types of noise for demonstration
    noisy_gaussian = noise_reduction.add_gaussian_noise(image, 0, 30)
    noisy_salt_pepper = noise_reduction.add_salt_pepper_noise(image, 0.05)
    print("✓ Created noisy test images")
    
    # Denoise using different methods
    denoised_gaussian = noise_reduction.gaussian_denoising(noisy_gaussian)
    denoised_median = noise_reduction.median_denoising(noisy_gaussian)
    denoised_bilateral = noise_reduction.bilateral_denoising(noisy_gaussian)
    print("✓ Applied denoising to Gaussian noise")
    
    # Handle salt and pepper noise
    denoised_salt_pepper = noise_reduction.median_denoising(noisy_salt_pepper)
    print("✓ Applied median filtering to salt & pepper noise")
    
    # Non-local means denoising
    denoised_nlm = noise_reduction.non_local_means_denoising(noisy_gaussian)
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
    
    display.show_comparison(noise_results, noise_titles, grid_size=(2, 4), figsize=(20, 10))
    
    return noise_results, noise_titles


def demonstrate_advanced_filtering(image: np.ndarray):
    """Demonstrate advanced filtering techniques."""
    print("\n" + "="*50)
    print("ADVANCED FILTERING")
    print("="*50)
    
    gray = image_io.convert_color_space(image, cv2.COLOR_BGR2GRAY)
    
    # Multi-scale edge detection
    edges_multi = edge_detection.multi_scale_edge_detection(gray)
    print("✓ Applied multi-scale edge detection")
    
    # Adaptive thresholding
    adaptive_thresh = edge_detection.adaptive_thresholding(gray)
    print("✓ Applied adaptive thresholding")
    
    # Morphological edge detection
    morph_edges = edge_detection.morphological_edge_detection(gray)
    print("✓ Applied morphological edge detection")
    
    # Display results
    advanced_results = [gray, edges_multi, adaptive_thresh, morph_edges]
    advanced_titles = ["Grayscale", "Multi-scale Edges", "Adaptive Threshold", "Morphological Edges"]
    
    display.show_comparison(advanced_results, advanced_titles, figsize=(16, 4))
    
    return advanced_results, advanced_titles


def demonstrate_image_filtering(image_path: str = None):
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
    smoothing_results, smoothing_titles = demonstrate_smoothing_filters(original)
    edge_results, edge_titles = demonstrate_edge_detection(original)
    noise_results, noise_titles = demonstrate_noise_reduction(original)
    advanced_results, advanced_titles = demonstrate_advanced_filtering(original)
    
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
    import cv2  # Import here to check if OpenCV is available
    
    # Get image path from command line argument
    image_path = None
    if len(sys.argv) > 1:
        image_path = sys.argv[1]
        if not os.path.exists(image_path):
            print(f"Warning: Image file '{image_path}' not found.")
            image_path = None
    
    try:
        demonstrate_image_filtering(image_path)
    except KeyboardInterrupt:
        print("\nDemo interrupted by user.")
    except Exception as e:
        print(f"Error running demo: {e}")
        import traceback
        traceback.print_exc()


if __name__ == "__main__":
    main() 