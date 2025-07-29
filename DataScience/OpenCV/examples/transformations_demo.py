"""
Geometric Transformations Demo - OpenCV Image Processing Collection

This script demonstrates various geometric transformation techniques including:
- Affine transformations (translation, rotation, scaling, shearing)
- Perspective transformations
- Image warping and distortion correction

Usage:
    python transformations_demo.py [image_path]

If no image path is provided, the script will use a sample image.
"""

import sys
import os
import numpy as np
import cv2
from typing import Optional


# Add the src directory to the path so we can import our modules
sys.path.append(os.path.join(os.path.dirname(__file__), '..', 'src'))

from transformations import affine_transforms, perspective_transforms, warping
from basic_operations import image_io, display, basic_transforms


def create_sample_image() -> np.ndarray:
    """Create a sample image with geometric patterns for transformation demonstration."""
    import cv2
    
    # Create a base image with grid and shapes
    height, width = 400, 500
    image = np.zeros((height, width, 3), dtype=np.uint8)
    
    # Create a grid pattern
    for i in range(0, width, 50):
        cv2.line(image, (i, 0), (i, height), (100, 100, 100), 1)
    for i in range(0, height, 50):
        cv2.line(image, (0, i), (width, i), (100, 100, 100), 1)
    
    # Add geometric shapes
    cv2.circle(image, (100, 100), 60, (255, 0, 0), -1)
    cv2.rectangle(image, (200, 150), (350, 250), (0, 255, 0), -1)
    cv2.line(image, (50, 300), (450, 350), (0, 0, 255), 5)
    
    # Add text
    cv2.putText(image, "Transform Demo", (50, 380), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 255), 2)
    
    return image


def demonstrate_affine_transforms(image: np.ndarray, non_interactive: bool = True):
    """Demonstrate various affine transformations."""
    print("\n" + "="*50)
    print("AFFINE TRANSFORMATIONS")
    print("="*50)
    
    # Create output directory
    output_dir = os.path.join(os.path.dirname(__file__), '..', 'sample_images', 'processed')
    os.makedirs(output_dir, exist_ok=True)
    
    # Translation
    translated_1 = affine_transforms.translate(image, 50, 30)
    translated_2 = affine_transforms.translate(image, -30, -50)
    print("✓ Applied translations (+50,+30 and -30,-50)")
    
    # Rotation
    rotated_30 = affine_transforms.rotate(image, 30)
    rotated_45 = affine_transforms.rotate(image, 45)
    rotated_90 = affine_transforms.rotate(image, 90)
    print("✓ Applied rotations (30°, 45°, 90°)")
    
    # Scaling
    scaled_up = affine_transforms.scale(image, 1.5, 1.5)
    scaled_down = affine_transforms.scale(image, 0.7, 0.7)
    scaled_asymmetric = affine_transforms.scale(image, 1.2, 0.8)
    print("✓ Applied scaling (up, down, asymmetric)")
    
    # Shearing
    sheared_x = affine_transforms.shear(image, shear_x=0.3, shear_y=0)
    sheared_y = affine_transforms.shear(image, shear_x=0, shear_y=0.3)
    sheared_xy = affine_transforms.shear(image, shear_x=0.2, shear_y=0.2)
    print("✓ Applied shearing (X, Y, XY)")
    
    # Combined transformations
    temp = affine_transforms.translate(image, 30, 20)
    temp = affine_transforms.rotate(temp, 15)
    combined = affine_transforms.scale(temp, 1.1, 1.1)
    print("✓ Applied combined transformation")
    
    # Display results
    affine_results = [
        image, translated_1, rotated_30, scaled_up, sheared_x, combined
    ]
    affine_titles = [
        "Original", "Translated", "Rotated 30°", "Scaled Up", "Sheared X", "Combined"
    ]
    
    display.save_comparison(affine_results, output_path=os.path.join(output_dir, 'affine_transforms_comparison.png'), grid_size=(2, 3), figsize=(18, 12), dpi=150)
    print(f"✓ Saved affine_transforms comparison to {os.path.join(output_dir, 'affine_transforms_comparison.png')}")
    
    return affine_results, affine_titles


def demonstrate_perspective_transforms(image: np.ndarray, non_interactive=True):
    """Demonstrate perspective transformations."""
    print("\n" + "="*50)
    print("PERSPECTIVE TRANSFORMATIONS")
    print("="*50)
    
    # Create output directory
    output_dir = os.path.join(os.path.dirname(__file__), '..', 'sample_images', 'processed')
    os.makedirs(output_dir, exist_ok=True)
    
    h, w = image.shape[:2]
    
    # Define source points (corners of the image)
    src_points = np.float32([[0, 0], [w, 0], [w, h], [0, h]])
    
    # Perspective transform 1: Forward perspective
    dst_points_1 = np.float32([[50, 50], [w-50, 100], [w-100, h-50], [100, h-100]])
    perspective_1 = perspective_transforms.perspective_transform(image, src_points, dst_points_1)
    print("✓ Applied forward perspective transform")
    
    # Perspective transform 2: Backward perspective
    dst_points_2 = np.float32([[100, 50], [w-100, 50], [w-50, h-100], [50, h-50]])
    perspective_2 = perspective_transforms.perspective_transform(image, src_points, dst_points_2)
    print("✓ Applied backward perspective transform")
    
    # Bird's eye view (provide required parameters)
    bird_eye_src = np.float32([[w*0.2, h*0.3], [w*0.8, h*0.3], [w*0.9, h*0.7], [w*0.1, h*0.7]])
    bird_eye = perspective_transforms.bird_eye_view(image, bird_eye_src, w, h)
    print("✓ Applied bird's eye view transform")
    
    # Image rectification (provide required parameters)
    rect_src = np.float32([[w*0.1, h*0.2], [w*0.9, h*0.1], [w*0.8, h*0.8], [w*0.2, h*0.9]])
    rectified = perspective_transforms.rectify_image(image, rect_src, w, h)
    print("✓ Applied image rectification")
    
    # Homography estimation and application
    homography, _ = perspective_transforms.find_homography(src_points, dst_points_1)
    homography_applied = perspective_transforms.homography_transform(image, homography, (w, h))
    print("✓ Applied homography transformation")
    
    # Display results
    perspective_results = [
        image, perspective_1, perspective_2, bird_eye, rectified, homography_applied
    ]
    perspective_titles = [
        "Original", "Perspective 1", "Perspective 2", "Bird's Eye", "Rectified", "Homography"
    ]
    
    display.save_comparison(perspective_results, output_path=os.path.join(output_dir, 'perspective_transforms_comparison.png'), grid_size=(2, 3), figsize=(18, 12), dpi=150)
    print(f"✓ Saved perspective_transforms comparison to {os.path.join(output_dir, 'perspective_transforms_comparison.png')}")
    
    return perspective_results, perspective_titles


def demonstrate_warping(image: np.ndarray, non_interactive=True):
    """Demonstrate image warping techniques."""
    print("\n" + "="*50)
    print("IMAGE WARPING")
    print("="*50)
    
    # Create output directory
    output_dir = os.path.join(os.path.dirname(__file__), '..', 'sample_images', 'processed')
    os.makedirs(output_dir, exist_ok=True)
    
    # Barrel distortion correction (create synthetic distorted image first)
    barrel_corrected = warping.barrel_distortion_correction(image, 0.1, 0.05, 0.01)
    print("✓ Applied barrel distortion correction")
    
    # Pincushion distortion correction
    pincushion_corrected = warping.pincushion_distortion_correction(image, -0.1, 0.05, 0.01)
    print("✓ Applied pincushion distortion correction")
    
    # Custom warping (if available)
    try:
        # Create a simple custom warp function inline
        def simple_warp(x, y, w, h):
            return x + 10 * np.sin(y / 20), y + 10 * np.sin(x / 20)
        
        custom_warped = warping.custom_warping(image, simple_warp)
        print("✓ Applied custom warping")
    except:
        # If custom warping fails, use the original image
        custom_warped = image
        print("✓ Custom warping not available, using original")
    
    # Fisheye correction
    fisheye_corrected = warping.fisheye_correction(image, 0.3, 0.1)
    print("✓ Applied fisheye correction")
    
    # Display results
    warping_results = [
        image, barrel_corrected, pincushion_corrected, custom_warped, fisheye_corrected
    ]
    warping_titles = [
        "Original", "Barrel Corrected", "Pincushion Corrected", "Custom Warp", "Fisheye Corrected"
    ]
    
    display.save_comparison(warping_results, output_path=os.path.join(output_dir, 'warping_comparison.png'), grid_size=(2, 4), figsize=(20, 10), dpi=150)
    print(f"✓ Saved warping comparison to {os.path.join(output_dir, 'warping_comparison.png')}")
    
    return warping_results, warping_titles


def demonstrate_advanced_transforms(image: np.ndarray, non_interactive=True):
    """Demonstrate advanced transformation techniques."""
    print("\n" + "="*50)
    print("ADVANCED TRANSFORMATIONS")
    print("="*50)
    
    # Create output directory
    output_dir = os.path.join(os.path.dirname(__file__), '..', 'sample_images', 'processed')
    os.makedirs(output_dir, exist_ok=True)
    
    # Multi-step transformation using chain_transformations if available
    try:
        transformations = [
            ('translate', (20, 10)),
            ('rotate', 15),
            ('scale', (1.1, 1.1))
        ]
        multi_step = affine_transforms.chain_transformations(image, transformations)
        print("✓ Applied multi-step transformation")
    except:
        # If chain_transformations doesn't work, apply manually
        temp = affine_transforms.translate(image, 20, 10)
        temp = affine_transforms.rotate(temp, 15)
        multi_step = affine_transforms.scale(temp, 1.1, 1.1)
        print("✓ Applied multi-step transformation (manual)")
    
    # Different scaling methods
    nearest = affine_transforms.scale(image, 1.5, 1.5, cv2.INTER_NEAREST)
    bilinear = affine_transforms.scale(image, 1.5, 1.5, cv2.INTER_LINEAR)
    cubic = affine_transforms.scale(image, 1.5, 1.5, cv2.INTER_CUBIC)
    print("✓ Applied scaling with different interpolation methods")
    
    # Display results
    advanced_results = [image, multi_step, nearest, bilinear, cubic]
    advanced_titles = ["Original", "Multi-step", "Nearest", "Bilinear", "Cubic"]
    
    display.save_comparison(advanced_results, output_path=os.path.join(output_dir, 'advanced_transforms_comparison.png'), figsize=(20, 4), dpi=150)
    print(f"✓ Saved advanced transforms comparison to {os.path.join(output_dir, 'advanced_transforms_comparison.png')}")
    
    return advanced_results, advanced_titles


def demonstrate_geometric_transformations(image_path: Optional[str] = None, non_interactive=True):
    """Demonstrate various geometric transformation techniques."""
    
    print("=" * 60)
    print("OpenCV Image Processing - Geometric Transformations Demo")
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
    
    # 3. Demonstrate different transformation techniques
    affine_results, affine_titles = demonstrate_affine_transforms(original, non_interactive=True)
    perspective_results, perspective_titles = demonstrate_perspective_transforms(original, non_interactive=True)
    warping_results, warping_titles = demonstrate_warping(original, non_interactive=True)
    advanced_results, advanced_titles = demonstrate_advanced_transforms(original, non_interactive=True)
    
    # 4. Save results
    print("\nSaving results...")
    
    # Create output directory
    output_dir = os.path.join(os.path.dirname(__file__), '..', 'sample_images', 'processed')
    os.makedirs(output_dir, exist_ok=True)
    
    # Save key results
    results = {
        'transform_translated.jpg': affine_results[1],      # Translated
        'transform_rotated.jpg': affine_results[2],         # Rotated 30°
        'transform_scaled.jpg': affine_results[3],          # Scaled up
        'transform_sheared.jpg': affine_results[4],         # Sheared X
        'transform_combined.jpg': affine_results[5],        # Combined
        'transform_perspective.jpg': perspective_results[1], # Perspective 1
        'transform_birds_eye.jpg': perspective_results[3],  # Bird's eye
        'transform_barrel_corrected.jpg': warping_results[1], # Barrel corrected
        'transform_pincushion_corrected.jpg': warping_results[2], # Pincushion corrected
        'transform_fisheye_corrected.jpg': warping_results[4]     # Fisheye corrected
    }
    
    saved_count = 0
    for filename, img in results.items():
        output_path = os.path.join(output_dir, filename)
        if image_io.save_image(img, output_path, quality=90):
            saved_count += 1
    
    print(f"✓ Saved {saved_count} transformed images to {output_dir}")
    
    # Save comparison images
    comparison_output = os.path.join(output_dir, 'transformations_comparison.png')
    if display.save_comparison(affine_results, comparison_output, affine_titles, 
                             grid_size=(2, 3), figsize=(18, 12), dpi=150):
        print(f"✓ Saved transformations comparison image to {comparison_output}")
    
    print("\n" + "=" * 60)
    print("Geometric Transformations Demo completed successfully!")
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
        demonstrate_geometric_transformations(image_path, non_interactive=True)
    except KeyboardInterrupt:
        print("\nDemo interrupted by user.")
    except Exception as e:
        print(f"Error running demo: {e}")
        import traceback
        traceback.print_exc()


if __name__ == "__main__":
    main() 