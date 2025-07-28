"""
Feature Detection Demo - OpenCV Image Processing Collection

This script demonstrates various feature detection techniques including:
- Corner detection (Harris, Shi-Tomasi, FAST)
- Keypoint detection (SIFT, SURF, ORB)
- Contour detection and analysis

Usage:
    python feature_detection_demo.py [image_path]

If no image path is provided, the script will use a sample image.
"""

import sys
import os
import numpy as np

# Add the src directory to the path so we can import our modules
sys.path.append(os.path.join(os.path.dirname(__file__), '..', 'src'))

from feature_detection import corner_detection, keypoint_detection, contour_detection
from basic_operations import image_io, display, basic_transforms


def create_sample_image() -> np.ndarray:
    """Create a sample image with various features for detection demonstration."""
    import cv2
    
    # Create a base image with geometric patterns
    height, width = 500, 600
    image = np.zeros((height, width, 3), dtype=np.uint8)
    
    # Create a grid pattern
    for i in range(0, width, 50):
        cv2.line(image, (i, 0), (i, height), (100, 100, 100), 1)
    for i in range(0, height, 50):
        cv2.line(image, (0, i), (width, i), (100, 100, 100), 1)
    
    # Add geometric shapes with corners
    cv2.rectangle(image, (100, 100), (200, 200), (255, 0, 0), -1)
    cv2.rectangle(image, (300, 150), (400, 250), (0, 255, 0), -1)
    cv2.rectangle(image, (450, 100), (550, 200), (0, 0, 255), -1)
    
    # Add circles
    cv2.circle(image, (150, 350), 60, (255, 255, 0), -1)
    cv2.circle(image, (350, 350), 60, (255, 0, 255), -1)
    
    # Add triangles
    triangle1 = np.array([[50, 450], [100, 400], [150, 450]], np.int32)
    triangle2 = np.array([[250, 450], [300, 400], [350, 450]], np.int32)
    cv2.fillPoly(image, [triangle1], (0, 255, 255))
    cv2.fillPoly(image, [triangle2], (255, 255, 0))
    
    # Add text
    cv2.putText(image, "Feature Detection", (50, 520), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 255), 2)
    
    return image


def demonstrate_corner_detection(image: np.ndarray):
    """Demonstrate various corner detection techniques."""
    print("\n" + "="*50)
    print("CORNER DETECTION")
    print("="*50)
    
    # Convert to grayscale
    gray = image_io.convert_color_space(image, cv2.COLOR_BGR2GRAY)
    
    # Harris corner detection
    harris_corners = corner_detection.harris_corner_detection(gray, 0.04, 0.06)
    print("✓ Applied Harris corner detection")
    
    # Shi-Tomasi corner detection
    shi_tomasi_corners = corner_detection.shi_tomasi_corner_detection(gray, 25, 0.01, 10)
    print("✓ Applied Shi-Tomasi corner detection")
    
    # FAST corner detection
    fast_corners = corner_detection.fast_corner_detection(gray, 10, True)
    print("✓ Applied FAST corner detection")
    
    # Display results
    corner_results = [image, harris_corners, shi_tomasi_corners, fast_corners]
    corner_titles = ["Original", "Harris Corners", "Shi-Tomasi Corners", "FAST Corners"]
    
    display.show_comparison(corner_results, corner_titles, grid_size=(2, 2), figsize=(16, 12))
    
    return corner_results, corner_titles


def demonstrate_keypoint_detection(image: np.ndarray):
    """Demonstrate various keypoint detection techniques."""
    print("\n" + "="*50)
    print("KEYPOINT DETECTION")
    print("="*50)
    
    # SIFT keypoints
    sift_keypoints = keypoint_detection.detect_sift_keypoints(image)
    print("✓ Applied SIFT keypoint detection")
    
    # SURF keypoints
    surf_keypoints = keypoint_detection.detect_surf_keypoints(image)
    print("✓ Applied SURF keypoint detection")
    
    # ORB keypoints
    orb_keypoints = keypoint_detection.detect_orb_keypoints(image)
    print("✓ Applied ORB keypoint detection")
    
    # BRISK keypoints
    brisk_keypoints = keypoint_detection.detect_brisk_keypoints(image)
    print("✓ Applied BRISK keypoint detection")
    
    # Display results
    keypoint_results = [image, sift_keypoints, surf_keypoints, orb_keypoints, brisk_keypoints]
    keypoint_titles = ["Original", "SIFT Keypoints", "SURF Keypoints", "ORB Keypoints", "BRISK Keypoints"]
    
    display.show_comparison(keypoint_results, keypoint_titles, grid_size=(2, 3), figsize=(18, 12))
    
    return keypoint_results, keypoint_titles


def demonstrate_contour_detection(image: np.ndarray):
    """Demonstrate contour detection and analysis."""
    print("\n" + "="*50)
    print("CONTOUR DETECTION")
    print("="*50)
    
    # Convert to grayscale
    gray = image_io.convert_color_space(image, cv2.COLOR_BGR2GRAY)
    
    # Find contours
    contours = contour_detection.find_contours(gray)
    print("✓ Found contours")
    
    # Draw all contours
    contours_drawn = contour_detection.draw_contours(image.copy(), contours, -1, (0, 255, 0), 2)
    print("✓ Drew all contours")
    
    # Filter contours by area
    large_contours = contour_detection.filter_contours_by_area(contours, 1000)
    large_contours_drawn = contour_detection.draw_contours(image.copy(), large_contours, -1, (255, 0, 0), 2)
    print("✓ Filtered contours by area")
    
    # Approximate contours
    approximated = contour_detection.approximate_contours(contours, 0.02)
    approximated_drawn = contour_detection.draw_contours(image.copy(), approximated, -1, (0, 0, 255), 2)
    print("✓ Approximated contours")
    
    # Convex hull
    convex_hulls = contour_detection.compute_convex_hulls(contours)
    convex_hulls_drawn = contour_detection.draw_contours(image.copy(), convex_hulls, -1, (255, 255, 0), 2)
    print("✓ Computed convex hulls")
    
    # Display results
    contour_results = [
        image, contours_drawn, large_contours_drawn, approximated_drawn, convex_hulls_drawn
    ]
    contour_titles = [
        "Original", "All Contours", "Large Contours", "Approximated", "Convex Hulls"
    ]
    
    display.show_comparison(contour_results, contour_titles, grid_size=(2, 3), figsize=(18, 12))
    
    return contour_results, contour_titles


def demonstrate_shape_analysis(image: np.ndarray):
    """Demonstrate shape analysis techniques."""
    print("\n" + "="*50)
    print("SHAPE ANALYSIS")
    print("="*50)
    
    # Convert to grayscale
    gray = image_io.convert_color_space(image, cv2.COLOR_BGR2GRAY)
    
    # Find contours
    contours = contour_detection.find_contours(gray)
    
    # Analyze shapes
    shape_analysis = contour_detection.analyze_shapes(contours)
    print("✓ Analyzed shapes")
    
    # Draw shape analysis
    shape_analysis_drawn = contour_detection.draw_shape_analysis(image.copy(), shape_analysis)
    print("✓ Drew shape analysis")
    
    # Moment analysis
    moments_analysis = contour_detection.compute_moments(contours)
    print("✓ Computed moments")
    
    # Bounding boxes
    bounding_boxes = contour_detection.draw_bounding_boxes(image.copy(), contours)
    print("✓ Drew bounding boxes")
    
    # Minimum area rectangles
    min_rectangles = contour_detection.draw_min_area_rectangles(image.copy(), contours)
    print("✓ Drew minimum area rectangles")
    
    # Display results
    shape_results = [
        image, shape_analysis_drawn, bounding_boxes, min_rectangles
    ]
    shape_titles = [
        "Original", "Shape Analysis", "Bounding Boxes", "Min Area Rectangles"
    ]
    
    display.show_comparison(shape_results, shape_titles, grid_size=(2, 2), figsize=(16, 12))
    
    return shape_results, shape_titles


def demonstrate_feature_matching(image: np.ndarray):
    """Demonstrate feature matching techniques."""
    print("\n" + "="*50)
    print("FEATURE MATCHING")
    print("="*50)
    
    # Create a slightly transformed version of the image for matching
    h, w = image.shape[:2]
    matrix = cv2.getRotationMatrix2D((w/2, h/2), 15, 1.1)
    transformed = cv2.warpAffine(image, matrix, (w, h))
    
    # SIFT matching
    sift_matches = keypoint_detection.match_sift_features(image, transformed)
    print("✓ Applied SIFT feature matching")
    
    # ORB matching
    orb_matches = keypoint_detection.match_orb_features(image, transformed)
    print("✓ Applied ORB feature matching")
    
    # Display results
    matching_results = [image, transformed, sift_matches, orb_matches]
    matching_titles = ["Original", "Transformed", "SIFT Matches", "ORB Matches"]
    
    display.show_comparison(matching_results, matching_titles, grid_size=(2, 2), figsize=(16, 12))
    
    return matching_results, matching_titles


def demonstrate_feature_detection(image_path: str = None):
    """Demonstrate various feature detection techniques."""
    
    print("=" * 60)
    print("OpenCV Image Processing - Feature Detection Demo")
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
    
    # 3. Demonstrate different feature detection techniques
    corner_results, corner_titles = demonstrate_corner_detection(original)
    keypoint_results, keypoint_titles = demonstrate_keypoint_detection(original)
    contour_results, contour_titles = demonstrate_contour_detection(original)
    shape_results, shape_titles = demonstrate_shape_analysis(original)
    matching_results, matching_titles = demonstrate_feature_matching(original)
    
    # 4. Save results
    print("\nSaving results...")
    
    # Create output directory
    output_dir = os.path.join(os.path.dirname(__file__), '..', 'sample_images', 'processed')
    os.makedirs(output_dir, exist_ok=True)
    
    # Save key results
    results = {
        'feature_harris_corners.jpg': corner_results[1],        # Harris corners
        'feature_shi_tomasi_corners.jpg': corner_results[2],    # Shi-Tomasi corners
        'feature_fast_corners.jpg': corner_results[3],          # FAST corners
        'feature_sift_keypoints.jpg': keypoint_results[1],      # SIFT keypoints
        'feature_orb_keypoints.jpg': keypoint_results[3],       # ORB keypoints
        'feature_contours.jpg': contour_results[1],             # All contours
        'feature_large_contours.jpg': contour_results[2],       # Large contours
        'feature_convex_hulls.jpg': contour_results[4],         # Convex hulls
        'feature_shape_analysis.jpg': shape_results[1],         # Shape analysis
        'feature_bounding_boxes.jpg': shape_results[2]          # Bounding boxes
    }
    
    saved_count = 0
    for filename, img in results.items():
        output_path = os.path.join(output_dir, filename)
        if image_io.save_image(img, output_path, quality=90):
            saved_count += 1
    
    print(f"✓ Saved {saved_count} feature detection images to {output_dir}")
    
    # Save comparison images
    comparison_output = os.path.join(output_dir, 'feature_detection_comparison.png')
    if display.save_comparison(corner_results, comparison_output, corner_titles, 
                             grid_size=(2, 2), figsize=(16, 12), dpi=150):
        print(f"✓ Saved feature detection comparison image to {comparison_output}")
    
    print("\n" + "=" * 60)
    print("Feature Detection Demo completed successfully!")
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
        demonstrate_feature_detection(image_path)
    except KeyboardInterrupt:
        print("\nDemo interrupted by user.")
    except Exception as e:
        print(f"Error running demo: {e}")
        import traceback
        traceback.print_exc()


if __name__ == "__main__":
    main() 