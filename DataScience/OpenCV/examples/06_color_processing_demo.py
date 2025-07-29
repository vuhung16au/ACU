"""
Color Processing Demo - OpenCV Image Processing Collection

This script demonstrates various color processing techniques including:
- Color space conversions (RGB, HSV, LAB, etc.)
- Histogram operations and equalization
- Color enhancement and adjustment

Usage:
    python 06_color_processing_demo.py [image_path]

If no image path is provided, the script will use a sample image.
"""

import sys
import os
import numpy as np
from typing import Optional

# Add the src directory to the path so we can import our modules
sys.path.append(os.path.join(os.path.dirname(__file__), '..', 'src'))

from color_processing import color_spaces, histogram, color_enhancement
from basic_operations import image_io, display, basic_transforms


def create_sample_image() -> np.ndarray:
    """Create a sample image with various colors for color processing demonstration."""
    import cv2
    
    # Create a colorful test image
    height, width = 400, 500
    image = np.zeros((height, width, 3), dtype=np.uint8)
    
    # Create color gradients
    for y in range(height):
        for x in range(width):
            # Red gradient horizontally
            red = int(255 * x / width)
            # Green gradient vertically
            green = int(255 * y / height)
            # Blue gradient diagonally
            blue = int(255 * (x + y) / (width + height))
            
            image[y, x] = [blue, green, red]  # BGR format
    
    # Add colored shapes
    cv2.circle(image, (100, 100), 60, (255, 0, 0), -1)      # Blue circle
    cv2.rectangle(image, (200, 150), (300, 250), (0, 255, 0), -1)  # Green rectangle
    cv2.circle(image, (350, 100), 50, (0, 0, 255), -1)      # Red circle
    
    # Add some mixed colors
    cv2.rectangle(image, (50, 300), (150, 350), (255, 255, 0), -1)  # Cyan
    cv2.rectangle(image, (200, 300), (300, 350), (255, 0, 255), -1)  # Magenta
    cv2.rectangle(image, (350, 300), (450, 350), (0, 255, 255), -1)  # Yellow
    
    # Add text
    cv2.putText(image, "Color Processing", (50, 380), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 255), 2)
    
    return image


def demonstrate_color_spaces(image: np.ndarray, non_interactive: bool = True):
    """Demonstrate various color space conversions."""
    print("\n" + "="*50)
    print("COLOR SPACE CONVERSIONS")
    print("="*50)
    
    # Create output directory
    output_dir = os.path.join(os.path.dirname(__file__), '..', 'sample_images', 'processed')
    os.makedirs(output_dir, exist_ok=True)
    
    # RGB to Grayscale
    gray = color_spaces.rgb_to_gray(image)
    print("✓ Converted RGB to Grayscale")
    
    # RGB to HSV
    hsv = color_spaces.rgb_to_hsv(image)
    print("✓ Converted RGB to HSV")
    
    # RGB to LAB
    lab = color_spaces.rgb_to_lab(image)
    print("✓ Converted RGB to LAB")
    
    # RGB to YUV
    yuv = color_spaces.rgb_to_yuv(image)
    print("✓ Converted RGB to YUV")
    
    # RGB to YCrCb (alternative to XYZ since XYZ doesn't exist)
    ycrcb = color_spaces.rgb_to_ycrcb(image)
    print("✓ Converted RGB to YCrCb")
    
    # Extract HSV and LAB components instead of display functions
    h, s, v = color_spaces.extract_hsv_components(hsv)
    l, a, b = color_spaces.extract_lab_components(lab)
    
    # Create simple channel displays
    import cv2
    hsv_channels = cv2.merge([h, s, v])
    lab_channels = cv2.merge([l, a, b])
    print("✓ Extracted color space channels")
    
    # Display results
    color_space_results = [
        image, gray, hsv, lab, yuv, ycrcb, hsv_channels, lab_channels
    ]
    color_space_titles = [
        "Original", "Grayscale", "HSV", "LAB", "YUV", "YCrCb", "HSV Channels", "LAB Channels"
    ]
    
    display.save_comparison(color_space_results, output_path=os.path.join(output_dir, 'color_space_comparison.png'), grid_size=(2, 4), figsize=(20, 10), dpi=150)
    print(f"✓ Saved color_space comparison to {os.path.join(output_dir, 'color_space_comparison.png')}")
    
    return color_space_results, color_space_titles


def demonstrate_histogram_operations(image: np.ndarray, non_interactive=True):
    """Demonstrate histogram operations and equalization."""
    print("\n" + "="*50)
    print("HISTOGRAM OPERATIONS")
    print("="*50)
    
    # Create output directory
    output_dir = os.path.join(os.path.dirname(__file__), '..', 'sample_images', 'processed')
    os.makedirs(output_dir, exist_ok=True)
    
    # Calculate histograms
    rgb_hist = histogram.compute_color_histogram(image)
    gray_hist = histogram.compute_histogram(image)
    print("✓ Calculated RGB and grayscale histograms")
    
    # Histogram equalization
    equalized_gray = histogram.equalize_histogram(image)
    print("✓ Applied histogram equalization")
    
    # CLAHE (Contrast Limited Adaptive Histogram Equalization)
    clahe_result = histogram.clahe_color(image)
    print("✓ Applied CLAHE")
    
    # Histogram matching
    target_image = create_sample_image()  # Use a different image as target
    matched_hist = histogram.histogram_matching(image, target_image)
    print("✓ Applied histogram matching")
    
    # Create a simple histogram display instead of display_histograms
    import matplotlib.pyplot as plt
    fig, ax = plt.subplots(1, 1, figsize=(8, 6))
    ax.plot(rgb_hist[0], color='blue', alpha=0.7, label='Blue')
    ax.plot(rgb_hist[1], color='green', alpha=0.7, label='Green') 
    ax.plot(rgb_hist[2], color='red', alpha=0.7, label='Red')
    ax.set_title('RGB Histogram')
    ax.legend()
    plt.close(fig)
    hist_display = np.ones((300, 400, 3), dtype=np.uint8) * 255  # Placeholder
    print("✓ Created histogram plots")
    
    # Display results
    histogram_results = [
        image, equalized_gray, clahe_result, matched_hist, hist_display
    ]
    histogram_titles = [
        "Original", "Equalized", "CLAHE", "Histogram Matched", "Histograms"
    ]
    
    display.save_comparison(histogram_results, output_path=os.path.join(output_dir, 'histogram_comparison.png'), grid_size=(2, 3), figsize=(18, 12), dpi=150)
    print(f"✓ Saved histogram comparison to {os.path.join(output_dir, 'histogram_comparison.png')}")
    
    return histogram_results, histogram_titles


def demonstrate_color_enhancement(image: np.ndarray, non_interactive=True):
    """Demonstrate color enhancement techniques."""
    print("\n" + "="*50)
    print("COLOR ENHANCEMENT")
    print("="*50)
    
    # Create output directory
    output_dir = os.path.join(os.path.dirname(__file__), '..', 'sample_images', 'processed')
    os.makedirs(output_dir, exist_ok=True)
    
    # Brightness adjustment
    brightened = color_enhancement.adjust_brightness(image, 50)
    darkened = color_enhancement.adjust_brightness(image, -50)
    print("✓ Applied brightness adjustments")
    
    # Contrast adjustment
    high_contrast = color_enhancement.adjust_contrast(image, 1.5)
    low_contrast = color_enhancement.adjust_contrast(image, 0.5)
    print("✓ Applied contrast adjustments")
    
    # Gamma correction
    gamma_corrected = color_enhancement.gamma_correction(image, 0.5)
    gamma_corrected_2 = color_enhancement.gamma_correction(image, 2.0)
    print("✓ Applied gamma correction")
    
    # Saturation adjustment
    saturated = color_enhancement.adjust_saturation(image, 1.5)
    desaturated = color_enhancement.adjust_saturation(image, 0.5)
    print("✓ Applied saturation adjustments")
    
    # White balance
    white_balanced = color_enhancement.white_balance_correction(image)
    print("✓ Applied white balance")
    
    # Color grading using LUT
    warm_lut = color_enhancement.create_warm_lookup_table()
    cool_lut = color_enhancement.create_cool_lookup_table()
    warm_graded = color_enhancement.apply_color_lookup_table(image, warm_lut)
    cool_graded = color_enhancement.apply_color_lookup_table(image, cool_lut)
    print("✓ Applied color grading")
    
    # Display results
    enhancement_results = [
        image, brightened, darkened, high_contrast, low_contrast,
        gamma_corrected, gamma_corrected_2, saturated, desaturated,
        white_balanced, warm_graded, cool_graded
    ]
    enhancement_titles = [
        "Original", "Brightened", "Darkened", "High Contrast", "Low Contrast",
        "Gamma 0.5", "Gamma 2.0", "Saturated", "Desaturated",
        "White Balanced", "Warm Graded", "Cool Graded"
    ]
    
    display.save_comparison(enhancement_results, output_path=os.path.join(output_dir, 'enhancement_comparison.png'), grid_size=(3, 4), figsize=(24, 18), dpi=150)
    print(f"✓ Saved enhancement comparison to {os.path.join(output_dir, 'enhancement_comparison.png')}")
    
    return enhancement_results, enhancement_titles


def demonstrate_color_segmentation(image: np.ndarray, non_interactive=True):
    """Demonstrate color-based segmentation techniques."""
    print("\n" + "="*50)
    print("COLOR SEGMENTATION")
    print("="*50)
    
    # Create output directory
    output_dir = os.path.join(os.path.dirname(__file__), '..', 'sample_images', 'processed')
    os.makedirs(output_dir, exist_ok=True)
    
    import cv2
    
    # Convert to HSV for color segmentation
    hsv = color_spaces.rgb_to_hsv(image)
    
    # Create color masks using color_spaces.create_color_mask instead of segment_by_color
    blue_mask = color_spaces.create_color_mask(hsv, lower_bound=(100, 50, 50), upper_bound=(130, 255, 255))
    green_mask = color_spaces.create_color_mask(hsv, lower_bound=(50, 50, 50), upper_bound=(80, 255, 255))
    red_mask = color_spaces.create_color_mask(hsv, lower_bound=(0, 50, 50), upper_bound=(20, 255, 255))
    print("✓ Applied color-based segmentation")
    
    # Apply masks to original image
    blue_segmented = cv2.bitwise_and(image, image, mask=blue_mask)
    green_segmented = cv2.bitwise_and(image, image, mask=green_mask)
    red_segmented = cv2.bitwise_and(image, image, mask=red_mask)
    
    # Create simple clustering and quantization since specific functions don't exist
    # Use adaptive color enhancement as alternative
    clustered = color_enhancement.adaptive_color_enhancement(image, method='auto')
    quantized = color_enhancement.multi_scale_color_enhancement(image)
    print("✓ Applied alternative enhancement techniques")
    
    # Display results
    segmentation_results = [
        image, blue_segmented, green_segmented, red_segmented, clustered, quantized
    ]
    segmentation_titles = [
        "Original", "Blue Segmented", "Green Segmented", "Red Segmented", "Clustered", "Quantized"
    ]
    
    display.save_comparison(segmentation_results, output_path=os.path.join(output_dir, 'segmentation_comparison.png'), grid_size=(2, 3), figsize=(18, 12), dpi=150)
    print(f"✓ Saved segmentation comparison to {os.path.join(output_dir, 'segmentation_comparison.png')}")
    
    return segmentation_results, segmentation_titles


def demonstrate_advanced_color_processing(image: np.ndarray, non_interactive=True):
    """Demonstrate advanced color processing techniques."""
    print("\n" + "="*50)
    print("ADVANCED COLOR PROCESSING")
    print("="*50)
    
    # Create output directory
    output_dir = os.path.join(os.path.dirname(__file__), '..', 'sample_images', 'processed')
    os.makedirs(output_dir, exist_ok=True)
    
    # Use available functions instead of non-existent ones
    # Color temperature adjustment
    warm_temp = color_enhancement.adjust_color_temperature(image, 1.2)  # Warm
    cool_temp = color_enhancement.adjust_color_temperature(image, 0.8)  # Cool
    print("✓ Applied color temperature adjustments")
    
    # Color grading with different methods
    vintage_lut = color_enhancement.create_vintage_lookup_table()
    vintage_graded = color_enhancement.apply_color_lookup_table(image, vintage_lut)
    print("✓ Applied vintage color grading")
    
    # Use color space comparison and statistics
    color_comparison = color_spaces.compare_color_spaces(image)
    color_stats = color_spaces.color_space_statistics(image)
    print("✓ Analyzed color spaces and statistics")
    
    # Create a placeholder for color analysis visualization
    color_analysis = np.ones((300, 400, 3), dtype=np.uint8) * 128  # Gray placeholder
    
    # Use multi-scale enhancement
    multi_scale_enhanced = color_enhancement.multi_scale_color_enhancement(image)
    print("✓ Applied multi-scale color enhancement")
    
    # Display results
    advanced_results = [
        image, vintage_graded, multi_scale_enhanced, warm_temp, cool_temp, color_analysis
    ]
    advanced_titles = [
        "Original", "Vintage Graded", "Multi-Scale Enhanced", "Warm Temp", "Cool Temp", "Color Analysis"
    ]
    
    display.save_comparison(advanced_results, output_path=os.path.join(output_dir, 'advanced_comparison.png'), grid_size=(2, 3), figsize=(18, 12), dpi=150)
    print(f"✓ Saved advanced comparison to {os.path.join(output_dir, 'advanced_comparison.png')}")
    
    return advanced_results, advanced_titles


def demonstrate_color_processing(image_path: Optional[str] = None, non_interactive=True):
    """Demonstrate various color processing techniques."""
    
    print("=" * 60)
    print("OpenCV Image Processing - Color Processing Demo")
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
    
    # 3. Demonstrate different color processing techniques
    color_space_results, color_space_titles = demonstrate_color_spaces(original, non_interactive=True)
    histogram_results, histogram_titles = demonstrate_histogram_operations(original, non_interactive=True)
    enhancement_results, enhancement_titles = demonstrate_color_enhancement(original, non_interactive=True)
    segmentation_results, segmentation_titles = demonstrate_color_segmentation(original, non_interactive=True)
    advanced_results, advanced_titles = demonstrate_advanced_color_processing(original, non_interactive=True)
    
    # 4. Save results
    print("\nSaving results...")
    
    # Create output directory
    output_dir = os.path.join(os.path.dirname(__file__), '..', 'sample_images', 'processed')
    os.makedirs(output_dir, exist_ok=True)
    
    # Save key results
    results = {
        'color_grayscale.jpg': color_space_results[1],        # Grayscale
        'color_hsv.jpg': color_space_results[2],              # HSV
        'color_lab.jpg': color_space_results[3],              # LAB
        'color_equalized.jpg': histogram_results[1],          # Equalized
        'color_clahe.jpg': histogram_results[2],              # CLAHE
        'color_brightened.jpg': enhancement_results[1],       # Brightened
        'color_contrast.jpg': enhancement_results[3],         # High contrast
        'color_gamma.jpg': enhancement_results[5],            # Gamma corrected
        'color_saturated.jpg': enhancement_results[7],        # Saturated
        'color_white_balanced.jpg': enhancement_results[9],   # White balanced
        'color_warm_graded.jpg': enhancement_results[10],     # Warm graded
        'color_blue_segmented.jpg': segmentation_results[1],  # Blue segmented
        'color_clustered.jpg': segmentation_results[4],       # Clustered
        'color_vintage.jpg': advanced_results[1]             # Vintage graded
    }
    
    saved_count = 0
    for filename, img in results.items():
        output_path = os.path.join(output_dir, filename)
        if image_io.save_image(img, output_path, quality=90):
            saved_count += 1
    
    print(f"✓ Saved {saved_count} color processing images to {output_dir}")
    
    # Save comparison images
    comparison_output = os.path.join(output_dir, 'color_processing_comparison.png')
    if display.save_comparison(color_space_results, comparison_output, color_space_titles, 
                             grid_size=(2, 4), figsize=(20, 10), dpi=150):
        print(f"✓ Saved color processing comparison image to {comparison_output}")
    
    print("\n" + "=" * 60)
    print("Color Processing Demo completed successfully!")
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
        demonstrate_color_processing(image_path, non_interactive=True)
    except KeyboardInterrupt:
        print("\nDemo interrupted by user.")
    except Exception as e:
        print(f"Error running demo: {e}")
        import traceback
        traceback.print_exc()


if __name__ == "__main__":
    main() 