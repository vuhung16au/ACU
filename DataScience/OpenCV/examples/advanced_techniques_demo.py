"""
Advanced Techniques Demo - OpenCV Image Processing Collection

This script demonstrates various advanced image processing techniques including:
- Template matching and object detection
- Image segmentation algorithms
- Fourier analysis and frequency domain processing
- Machine learning applications

Usage:
    python advanced_techniques_demo.py [image_path]

If no image path is provided, the script will use a sample image.
"""

import sys
import os
import numpy as np
import cv2


# Add the src directory to the path so we can import our modules
sys.path.append(os.path.join(os.path.dirname(__file__), '..', 'src'))

from advanced import template_matching, image_segmentation, fourier_analysis, machine_learning
from basic_operations import image_io, display, basic_transforms


def create_sample_image() -> np.ndarray:
    """Create a sample image with various objects for advanced processing demonstration."""
    import cv2
    
    # Create a base image with multiple objects
    height, width = 500, 600
    image = np.zeros((height, width, 3), dtype=np.uint8)
    
    # Create background with texture
    for y in range(height):
        for x in range(width):
            # Add some texture to background
            noise = np.random.randint(0, 30)
            image[y, x] = [50 + noise, 50 + noise, 50 + noise]
    
    # Add various shapes and objects
    # Rectangles
    cv2.rectangle(image, (50, 50), (150, 150), (255, 0, 0), -1)
    cv2.rectangle(image, (200, 100), (300, 200), (0, 255, 0), -1)
    cv2.rectangle(image, (350, 50), (450, 150), (0, 0, 255), -1)
    
    # Circles
    cv2.circle(image, (100, 300), 60, (255, 255, 0), -1)
    cv2.circle(image, (300, 300), 60, (255, 0, 255), -1)
    cv2.circle(image, (500, 300), 60, (0, 255, 255), -1)
    
    # Triangles
    triangle1 = np.array([[50, 450], [100, 400], [150, 450]], np.int32)
    triangle2 = np.array([[250, 450], [300, 400], [350, 450]], np.int32)
    cv2.fillPoly(image, [triangle1], (128, 128, 255))
    cv2.fillPoly(image, [triangle2], (255, 128, 128))
    
    # Add text
    cv2.putText(image, "Advanced Techniques", (50, 520), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 255), 2)
    
    return image


def demonstrate_template_matching(image: np.ndarray):
    """Demonstrate template matching techniques."""
    print("\n" + "="*50)
    print("TEMPLATE MATCHING")
    print("="*50)
    
    # Create a template (small rectangle)
    template = image[100:150, 200:250].copy()
    
    # Single template matching
    single_match = template_matching.single_template_matching(image, template)
    print("✓ Applied single template matching")
    
    # Multi-scale template matching
    multi_scale_match = template_matching.multi_scale_template_matching(image, template)
    print("✓ Applied multi-scale template matching")
    
    # Rotation-invariant template matching
    rotation_invariant = template_matching.rotation_invariant_matching(image, template)
    print("✓ Applied rotation-invariant matching")
    
    # Multiple template matching
    templates = [template, image[50:100, 350:400].copy()]  # Two different templates
    multiple_matches = template_matching.multiple_template_matching(image, templates)
    print("✓ Applied multiple template matching")
    
    # Display results
    template_results = [
        image, template, single_match, multi_scale_match, rotation_invariant, multiple_matches
    ]
    template_titles = [
        "Original", "Template", "Single Match", "Multi-scale", "Rotation Invariant", "Multiple"
    ]
    
    display.show_comparison(template_results, template_titles, grid_size=(2, 3), figsize=(18, 12))
    
    return template_results, template_titles


def demonstrate_image_segmentation(image: np.ndarray):
    """Demonstrate various image segmentation techniques."""
    print("\n" + "="*50)
    print("IMAGE SEGMENTATION")
    print("="*50)
    
    # Convert to grayscale for some operations
    gray = image_io.convert_color_space(image, cv2.COLOR_BGR2GRAY)
    
    # Threshold-based segmentation
    threshold_seg = image_segmentation.threshold_segmentation(gray)
    print("✓ Applied threshold-based segmentation")
    
    # Otsu's thresholding
    otsu_seg = image_segmentation.otsu_thresholding(gray)
    print("✓ Applied Otsu's thresholding")
    
    # Adaptive thresholding
    adaptive_seg = image_segmentation.adaptive_thresholding(gray)
    print("✓ Applied adaptive thresholding")
    
    # Watershed segmentation
    watershed_seg = image_segmentation.watershed_segmentation(image)
    print("✓ Applied watershed segmentation")
    
    # GrabCut segmentation
    grabcut_seg = image_segmentation.grabcut_segmentation(image)
    print("✓ Applied GrabCut segmentation")
    
    # K-means clustering
    kmeans_seg = image_segmentation.kmeans_segmentation(image, 4)
    print("✓ Applied K-means clustering")
    
    # Display results
    segmentation_results = [
        image, threshold_seg, otsu_seg, adaptive_seg, watershed_seg, grabcut_seg, kmeans_seg
    ]
    segmentation_titles = [
        "Original", "Threshold", "Otsu", "Adaptive", "Watershed", "GrabCut", "K-means"
    ]
    
    display.show_comparison(segmentation_results, segmentation_titles, grid_size=(2, 4), figsize=(20, 10))
    
    return segmentation_results, segmentation_titles


def demonstrate_fourier_analysis(image: np.ndarray):
    """Demonstrate Fourier analysis and frequency domain processing."""
    print("\n" + "="*50)
    print("FOURIER ANALYSIS")
    print("="*50)
    
    # Convert to grayscale
    gray = image_io.convert_color_space(image, cv2.COLOR_BGR2GRAY)
    
    # Compute Fourier transform
    fourier_transform = fourier_analysis.compute_fourier_transform(gray)
    print("✓ Computed Fourier transform")
    
    # Display magnitude spectrum
    magnitude_spectrum = fourier_analysis.display_magnitude_spectrum(fourier_transform)
    print("✓ Displayed magnitude spectrum")
    
    # Low-pass filtering
    low_pass_filtered = fourier_analysis.low_pass_filter(gray, 30)
    print("✓ Applied low-pass filtering")
    
    # High-pass filtering
    high_pass_filtered = fourier_analysis.high_pass_filter(gray, 30)
    print("✓ Applied high-pass filtering")
    
    # Band-pass filtering
    band_pass_filtered = fourier_analysis.band_pass_filter(gray, 10, 50)
    print("✓ Applied band-pass filtering")
    
    # Notch filtering
    notch_filtered = fourier_analysis.notch_filter(gray, 50, 10)
    print("✓ Applied notch filtering")
    
    # Display results
    fourier_results = [
        gray, magnitude_spectrum, low_pass_filtered, high_pass_filtered, 
        band_pass_filtered, notch_filtered
    ]
    fourier_titles = [
        "Grayscale", "Magnitude Spectrum", "Low-pass", "High-pass", "Band-pass", "Notch"
    ]
    
    display.show_comparison(fourier_results, fourier_titles, grid_size=(2, 3), figsize=(18, 12))
    
    return fourier_results, fourier_titles


def demonstrate_machine_learning(image: np.ndarray):
    """Demonstrate machine learning applications in computer vision."""
    print("\n" + "="*50)
    print("MACHINE LEARNING APPLICATIONS")
    print("="*50)
    
    # Face detection
    face_detection = machine_learning.detect_faces(image)
    print("✓ Applied face detection")
    
    # Object detection (using pre-trained models)
    object_detection = machine_learning.detect_objects(image)
    print("✓ Applied object detection")
    
    # Background subtraction
    background_subtraction = machine_learning.background_subtraction(image)
    print("✓ Applied background subtraction")
    
    # Motion detection
    motion_detection = machine_learning.motion_detection(image)
    print("✓ Applied motion detection")
    
    # Edge detection with machine learning
    ml_edge_detection = machine_learning.ml_edge_detection(image)
    print("✓ Applied ML-based edge detection")
    
    # Display results
    ml_results = [
        image, face_detection, object_detection, background_subtraction, 
        motion_detection, ml_edge_detection
    ]
    ml_titles = [
        "Original", "Face Detection", "Object Detection", "Background Subtraction",
        "Motion Detection", "ML Edge Detection"
    ]
    
    display.show_comparison(ml_results, ml_titles, grid_size=(2, 3), figsize=(18, 12))
    
    return ml_results, ml_titles


def demonstrate_advanced_processing(image: np.ndarray):
    """Demonstrate advanced processing techniques."""
    print("\n" + "="*50)
    print("ADVANCED PROCESSING")
    print("="*50)
    
    # Image restoration
    restored = fourier_analysis.image_restoration(image)
    print("✓ Applied image restoration")
    
    # Noise reduction using frequency domain
    denoised_freq = fourier_analysis.frequency_domain_denoising(image)
    print("✓ Applied frequency domain denoising")
    
    # Texture analysis
    texture_analysis = image_segmentation.texture_analysis(image)
    print("✓ Applied texture analysis")
    
    # Contour analysis
    contour_analysis = image_segmentation.contour_analysis(image)
    print("✓ Applied contour analysis")
    
    # Shape recognition
    shape_recognition = machine_learning.shape_recognition(image)
    print("✓ Applied shape recognition")
    
    # Display results
    advanced_results = [
        image, restored, denoised_freq, texture_analysis, contour_analysis, shape_recognition
    ]
    advanced_titles = [
        "Original", "Restored", "Frequency Denoised", "Texture Analysis", 
        "Contour Analysis", "Shape Recognition"
    ]
    
    display.show_comparison(advanced_results, advanced_titles, grid_size=(2, 3), figsize=(18, 12))
    
    return advanced_results, advanced_titles


def demonstrate_advanced_techniques(image_path: str = None):
    """Demonstrate various advanced image processing techniques."""
    
    print("=" * 60)
    print("OpenCV Image Processing - Advanced Techniques Demo")
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
    
    # 3. Demonstrate different advanced techniques
    template_results, template_titles = demonstrate_template_matching(original)
    segmentation_results, segmentation_titles = demonstrate_image_segmentation(original)
    fourier_results, fourier_titles = demonstrate_fourier_analysis(original)
    ml_results, ml_titles = demonstrate_machine_learning(original)
    advanced_results, advanced_titles = demonstrate_advanced_processing(original)
    
    # 4. Save results
    print("\nSaving results...")
    
    # Create output directory
    output_dir = os.path.join(os.path.dirname(__file__), '..', 'sample_images', 'processed')
    os.makedirs(output_dir, exist_ok=True)
    
    # Save key results
    results = {
        'advanced_template_match.jpg': template_results[2],      # Single match
        'advanced_multi_scale.jpg': template_results[3],        # Multi-scale
        'advanced_threshold_seg.jpg': segmentation_results[1],  # Threshold
        'advanced_watershed_seg.jpg': segmentation_results[4],  # Watershed
        'advanced_grabcut_seg.jpg': segmentation_results[5],    # GrabCut
        'advanced_kmeans_seg.jpg': segmentation_results[6],     # K-means
        'advanced_magnitude_spectrum.jpg': fourier_results[1],  # Magnitude spectrum
        'advanced_low_pass.jpg': fourier_results[2],            # Low-pass
        'advanced_high_pass.jpg': fourier_results[3],           # High-pass
        'advanced_face_detection.jpg': ml_results[1],           # Face detection
        'advanced_object_detection.jpg': ml_results[2],         # Object detection
        'advanced_background_sub.jpg': ml_results[3],           # Background subtraction
        'advanced_restored.jpg': advanced_results[1],           # Restored
        'advanced_texture_analysis.jpg': advanced_results[3]    # Texture analysis
    }
    
    saved_count = 0
    for filename, img in results.items():
        output_path = os.path.join(output_dir, filename)
        if image_io.save_image(img, output_path, quality=90):
            saved_count += 1
    
    print(f"✓ Saved {saved_count} advanced processing images to {output_dir}")
    
    # Save comparison images
    comparison_output = os.path.join(output_dir, 'advanced_techniques_comparison.png')
    if display.save_comparison(template_results, comparison_output, template_titles, 
                             grid_size=(2, 3), figsize=(18, 12), dpi=150):
        print(f"✓ Saved advanced techniques comparison image to {comparison_output}")
    
    print("\n" + "=" * 60)
    print("Advanced Techniques Demo completed successfully!")
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
        demonstrate_advanced_techniques(image_path)
    except KeyboardInterrupt:
        print("\nDemo interrupted by user.")
    except Exception as e:
        print(f"Error running demo: {e}")
        import traceback
        traceback.print_exc()


if __name__ == "__main__":
    main() 