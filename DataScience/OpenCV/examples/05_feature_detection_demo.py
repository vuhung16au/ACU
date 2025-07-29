"""
Feature Detection Demo - Vu Hung Nguyen

This script demonstrates various feature detection techniques including:
- Corner detection (Harris, Shi-Tomasi, FAST)
- Keypoint detection (SIFT, SURF, ORB)
- Contour detection and analysis

Usage:
    python 05_feature_detection_demo.py [image_path]

If no image path is provided, the script will use a sample image.
"""

import sys
import os
import numpy as np
import cv2

# Add the src directory to the path so we can import our modules
sys.path.append(os.path.join(os.path.dirname(__file__), '..', 'src'))

from feature_detection import corner_detection, keypoint_detection, contour_detection
from basic_operations import image_io, display, basic_transforms


def create_sample_image() -> np.ndarray:
    """Create a sample image with various features for detection demonstration."""
    
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


def demonstrate_corner_detection(image: np.ndarray, non_interactive: bool = True):
    """Demonstrate various corner detection techniques."""
    print("\n" + "="*50)
    print("CORNER DETECTION")
    print("="*50)
    
    # Convert to grayscale
    gray = image_io.convert_color_space(image, cv2.COLOR_BGR2GRAY)
    
    # Harris corner detection
    harris_corners_mask, harris_response = corner_detection.harris_corners(gray, k=0.04, threshold=0.06)
    # Create visualization for Harris corners
    harris_result = image.copy()
    harris_result[harris_corners_mask] = [0, 255, 0]  # Mark corners in green
    print("✓ Applied Harris corner detection")
    
    # Shi-Tomasi corner detection
    shi_tomasi_corners_data = corner_detection.shi_tomasi_corners(gray, max_corners=25, quality_level=0.01, min_distance=10)
    if shi_tomasi_corners_data is not None:
        shi_tomasi_corners = corner_detection.draw_corners(image.copy(), shi_tomasi_corners_data.reshape(-1, 2))
    else:
        shi_tomasi_corners = image.copy()
    print("✓ Applied Shi-Tomasi corner detection")
    
    # FAST corner detection
    fast_keypoints, fast_response = corner_detection.fast_corners(gray, threshold=10, non_max_suppression=True)
    # Convert keypoints to coordinates for drawing
    fast_result = image.copy()
    for kp in fast_keypoints:
        x, y = int(kp.pt[0]), int(kp.pt[1])
        cv2.circle(fast_result, (x, y), 3, (0, 0, 255), 2)
    print("✓ Applied FAST corner detection")
    
    # Display results
    corner_results = [image, harris_result, shi_tomasi_corners, fast_result]
    corner_titles = ["Original", "Harris Corners", "Shi-Tomasi Corners", "FAST Corners"]
    
    display.save_comparison(corner_results, output_path=os.path.join(output_dir, 'corner_detection_comparison.png'), grid_size=(2, 2), figsize=(16, 12), dpi=150)
    print(f"✓ Saved corner_detection comparison to {os.path.join(output_dir, 'corner_detection_comparison.png')}")
    
    return corner_results, corner_titles


def demonstrate_keypoint_detection(image: np.ndarray, non_interactive=True):
    """Demonstrate various keypoint detection techniques."""
    print("\n" + "="*50)
    print("KEYPOINT DETECTION")
    print("="*50)
    
    # SIFT keypoints
    try:
        sift_keypoints, sift_descriptors = keypoint_detection.detect_sift(image)
        sift_img = keypoint_detection.draw_keypoints(image.copy(), sift_keypoints)
        print("✓ Applied SIFT keypoint detection")
    except Exception as e:
        print(f"SIFT detection failed: {e}")
        sift_img = image.copy()
    
    # SURF keypoints (may not be available in all OpenCV versions)
    try:
        surf_keypoints, surf_descriptors = keypoint_detection.detect_surf(image)
        surf_img = keypoint_detection.draw_keypoints(image.copy(), surf_keypoints)
        print("✓ Applied SURF keypoint detection")
    except Exception as e:
        print(f"SURF detection not available: {e}")
        surf_img = image.copy()
    
    # ORB keypoints
    try:
        orb_keypoints, orb_descriptors = keypoint_detection.detect_orb(image)
        orb_img = keypoint_detection.draw_keypoints(image.copy(), orb_keypoints)
        print("✓ Applied ORB keypoint detection")
    except Exception as e:
        print(f"ORB detection failed: {e}")
        orb_img = image.copy()
    
    # Display results
    keypoint_results = [image, sift_img, surf_img, orb_img]
    keypoint_titles = ["Original", "SIFT Keypoints", "SURF Keypoints", "ORB Keypoints"]
    
    display.save_comparison(keypoint_results, output_path=os.path.join(output_dir, 'keypoint_detection_comparison.png'), grid_size=(2, 2), figsize=(16, 12), dpi=150)
    print(f"✓ Saved keypoint_detection comparison to {os.path.join(output_dir, 'keypoint_detection_comparison.png')}")
    
    return keypoint_results, keypoint_titles


def demonstrate_contour_detection(image: np.ndarray, non_interactive=True):
    """Demonstrate contour detection and analysis."""
    print("\n" + "="*50)
    print("CONTOUR DETECTION")
    print("="*50)
    
    # Convert to grayscale
    gray = image_io.convert_color_space(image, cv2.COLOR_BGR2GRAY)
    
    # Find contours
    contours, hierarchy = contour_detection.find_contours(gray)
    print("✓ Found contours")
    
    # Draw all contours manually to avoid function signature issues
    contours_drawn = image.copy()
    cv2.drawContours(contours_drawn, contours, -1, (0, 255, 0), 2)
    print("✓ Drew all contours")
    
    # Filter contours by area
    large_contours = contour_detection.filter_contours_by_area(contours, min_area=1000)
    large_contours_drawn = image.copy()
    cv2.drawContours(large_contours_drawn, large_contours, -1, (255, 0, 0), 2)
    print("✓ Filtered contours by area")
    
    # Approximate contours
    approximated = contour_detection.approximate_contours(contours, epsilon_factor=0.02)
    approximated_drawn = image.copy()
    cv2.drawContours(approximated_drawn, approximated, -1, (0, 0, 255), 2)
    print("✓ Approximated contours")
    
    # Simple analysis visualization
    analysis_drawn = image.copy()
    cv2.drawContours(analysis_drawn, contours, -1, (255, 255, 0), 2)
    print("✓ Drew contour analysis")
    
    # Display results
    contour_results = [
        image, contours_drawn, large_contours_drawn, approximated_drawn, analysis_drawn
    ]
    contour_titles = [
        "Original", "All Contours", "Large Contours", "Approximated", "Analysis"
    ]
    
    display.save_comparison(contour_results, output_path=os.path.join(output_dir, 'contour_detection_comparison.png'), grid_size=(2, 3), figsize=(18, 12), dpi=150)
    print(f"✓ Saved contour_detection comparison to {os.path.join(output_dir, 'contour_detection_comparison.png')}")
    
    return contour_results, contour_titles


def demonstrate_shape_analysis(image: np.ndarray, non_interactive=True):
    """Demonstrate shape analysis techniques."""
    print("\n" + "="*50)
    print("SHAPE ANALYSIS")
    print("="*50)
    
    # Convert to grayscale
    gray = image_io.convert_color_space(image, cv2.COLOR_BGR2GRAY)
    
    # Find contours
    contours, hierarchy = contour_detection.find_contours(gray)
    
    # Try to analyze shapes, but handle numpy compatibility issues
    try:
        shape_analysis = contour_detection.analyze_contours(contours)
        print("✓ Analyzed shapes")
    except AttributeError as e:
        if "int0" in str(e):
            print("! Shape analysis skipped due to numpy compatibility issue")
            shape_analysis = []
        else:
            raise e
    
    # Simple shape visualization
    shape_analysis_drawn = image.copy()
    cv2.drawContours(shape_analysis_drawn, contours, -1, (0, 255, 255), 2)
    print("✓ Drew shape analysis")
    
    # Filter by different criteria
    large_contours = contour_detection.filter_contours_by_area(contours, min_area=1000)
    large_contours_drawn = image.copy()
    cv2.drawContours(large_contours_drawn, large_contours, -1, (255, 0, 0), 2)
    print("✓ Filtered by area")
    
    # Try to extract features
    try:
        features = contour_detection.extract_contour_features(contours)
        print("✓ Extracted contour features")
    except AttributeError as e:
        if "int0" in str(e):
            print("! Feature extraction skipped due to numpy compatibility issue")
        else:
            raise e
    
    # Display results
    shape_results = [
        image, shape_analysis_drawn, large_contours_drawn, image.copy()
    ]
    shape_titles = [
        "Original", "Shape Analysis", "Large Contours", "Features"
    ]
    
    display.save_comparison(shape_results, output_path=os.path.join(output_dir, 'shape_analysis_comparison.png'), grid_size=(2, 2), figsize=(16, 12), dpi=150)
    print(f"✓ Saved shape_analysis comparison to {os.path.join(output_dir, 'shape_analysis_comparison.png')}")
    
    return shape_results, shape_titles


def demonstrate_feature_matching(image: np.ndarray, non_interactive=True):
    """Demonstrate feature matching techniques."""
    print("\n" + "="*50)
    print("FEATURE MATCHING")
    print("="*50)
    
    # Create a slightly transformed version of the image for matching
    h, w = image.shape[:2]
    matrix = cv2.getRotationMatrix2D((w/2, h/2), 15, 1.1)
    transformed = cv2.warpAffine(image, matrix, (w, h))
    
    # Use the match_images function from keypoint_detection
    try:
        matches_result = keypoint_detection.match_images(image, transformed, detector='sift')
        
        # Draw matches if we have them
        if matches_result['match_count'] > 0:
            sift_matches = keypoint_detection.draw_matches(
                image, matches_result['keypoints1'], 
                transformed, matches_result['keypoints2'], 
                matches_result['matches'][:50]  # Limit to first 50 matches
            )
        else:
            sift_matches = image.copy()
        print("✓ Applied SIFT feature matching")
    except Exception as e:
        print(f"SIFT matching failed: {e}")
        sift_matches = image.copy()
    
    try:
        matches_result_orb = keypoint_detection.match_images(image, transformed, detector='orb')
        
        # Draw matches if we have them
        if matches_result_orb['match_count'] > 0:
            orb_matches = keypoint_detection.draw_matches(
                image, matches_result_orb['keypoints1'], 
                transformed, matches_result_orb['keypoints2'], 
                matches_result_orb['matches'][:50]  # Limit to first 50 matches
            )
        else:
            orb_matches = image.copy()
        print("✓ Applied ORB feature matching")
    except Exception as e:
        print(f"ORB matching failed: {e}")
        orb_matches = image.copy()
    
    # Display results
    matching_results = [image, transformed, sift_matches, orb_matches]
    matching_titles = ["Original", "Transformed", "SIFT Matches", "ORB Matches"]
    
    display.save_comparison(matching_results, output_path=os.path.join(output_dir, 'feature_matching_comparison.png'), grid_size=(2, 2), figsize=(16, 12), dpi=150)
    print(f"✓ Saved feature_matching comparison to {os.path.join(output_dir, 'feature_matching_comparison.png')}")
    
    return matching_results, matching_titles


def demonstrate_feature_detection(image_path: str | None = None, non_interactive=True):
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
    corner_results, corner_titles = demonstrate_corner_detection(original, non_interactive=True)
    keypoint_results, keypoint_titles = demonstrate_keypoint_detection(original, non_interactive=True)
    contour_results, contour_titles = demonstrate_contour_detection(original, non_interactive=True)
    shape_results, shape_titles = demonstrate_shape_analysis(original, non_interactive=True)
    matching_results, matching_titles = demonstrate_feature_matching(original, non_interactive=True)
    
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
        'feature_surf_keypoints.jpg': keypoint_results[2],      # SURF keypoints
        'feature_orb_keypoints.jpg': keypoint_results[3],       # ORB keypoints
        'feature_contours.jpg': contour_results[1],             # All contours
        'feature_large_contours.jpg': contour_results[2],       # Large contours
        'feature_shape_analysis.jpg': shape_results[1],         # Shape analysis
        'feature_filtered_contours.jpg': shape_results[2]       # Filtered contours
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
    
    # Get image path from command line argument
    image_path = None
    if len(sys.argv) > 1:
        image_path = sys.argv[1]
        if not os.path.exists(image_path):
            print(f"Warning: Image file '{image_path}' not found.")
            image_path = None
    
    try:
        demonstrate_feature_detection(image_path, non_interactive=True)
    except KeyboardInterrupt:
        print("\nDemo interrupted by user.")
    except Exception as e:
        print(f"Error running demo: {e}")
        import traceback
        traceback.print_exc()


if __name__ == "__main__":
    main() 