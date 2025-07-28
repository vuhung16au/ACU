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


def demonstrate_affine_transforms(image: np.ndarray):
    """Demonstrate various affine transformations."""
    print("\n" + "="*50)
    print("AFFINE TRANSFORMATIONS")
    print("="*50)
    
    # Translation
    translated_1 = affine_transforms.translate_image(image, 50, 30)
    translated_2 = affine_transforms.translate_image(image, -30, -50)
    print("✓ Applied translations (+50,+30 and -30,-50)")
    
    # Rotation
    rotated_30 = affine_transforms.rotate_image(image, 30)
    rotated_45 = affine_transforms.rotate_image(image, 45)
    rotated_90 = affine_transforms.rotate_image(image, 90)
    print("✓ Applied rotations (30°, 45°, 90°)")
    
    # Scaling
    scaled_up = affine_transforms.scale_image(image, 1.5, 1.5)
    scaled_down = affine_transforms.scale_image(image, 0.7, 0.7)
    scaled_asymmetric = affine_transforms.scale_image(image, 1.2, 0.8)
    print("✓ Applied scaling (up, down, asymmetric)")
    
    # Shearing
    sheared_x = affine_transforms.shear_image(image, 0.3, 0)
    sheared_y = affine_transforms.shear_image(image, 0, 0.3)
    sheared_xy = affine_transforms.shear_image(image, 0.2, 0.2)
    print("✓ Applied shearing (X, Y, XY)")
    
    # Combined transformations
    combined = affine_transforms.combine_transforms(image, 
                                                  translation=(30, 20),
                                                  rotation=15,
                                                  scaling=(1.1, 1.1))
    print("✓ Applied combined transformation")
    
    # Display results
    affine_results = [
        image, translated_1, rotated_30, scaled_up, sheared_x, combined
    ]
    affine_titles = [
        "Original", "Translated", "Rotated 30°", "Scaled Up", "Sheared X", "Combined"
    ]
    
    display.show_comparison(affine_results, affine_titles, grid_size=(2, 3), figsize=(18, 12))
    
    return affine_results, affine_titles


def demonstrate_perspective_transforms(image: np.ndarray):
    """Demonstrate perspective transformations."""
    print("\n" + "="*50)
    print("PERSPECTIVE TRANSFORMATIONS")
    print("="*50)
    
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
    
    # Bird's eye view
    bird_eye = perspective_transforms.birds_eye_view(image)
    print("✓ Applied bird's eye view transform")
    
    # Image rectification
    rectified = perspective_transforms.rectify_image(image)
    print("✓ Applied image rectification")
    
    # Homography estimation and application
    homography = perspective_transforms.estimate_homography(src_points, dst_points_1)
    homography_applied = perspective_transforms.apply_homography(image, homography, (w, h))
    print("✓ Applied homography transformation")
    
    # Display results
    perspective_results = [
        image, perspective_1, perspective_2, bird_eye, rectified, homography_applied
    ]
    perspective_titles = [
        "Original", "Perspective 1", "Perspective 2", "Bird's Eye", "Rectified", "Homography"
    ]
    
    display.show_comparison(perspective_results, perspective_titles, grid_size=(2, 3), figsize=(18, 12))
    
    return perspective_results, perspective_titles


def demonstrate_warping(image: np.ndarray):
    """Demonstrate image warping techniques."""
    print("\n" + "="*50)
    print("IMAGE WARPING")
    print("="*50)
    
    # Barrel distortion
    barrel_distorted = warping.apply_barrel_distortion(image, 0.1, 0.05, 0.01)
    barrel_corrected = warping.correct_barrel_distortion(barrel_distorted, 0.1, 0.05, 0.01)
    print("✓ Applied and corrected barrel distortion")
    
    # Pincushion distortion
    pincushion_distorted = warping.apply_pincushion_distortion(image, 0.1, 0.05, 0.01)
    pincushion_corrected = warping.correct_pincushion_distortion(pincushion_distorted, 0.1, 0.05, 0.01)
    print("✓ Applied and corrected pincushion distortion")
    
    # Custom warping
    custom_warped = warping.custom_warp(image, warping.wave_distortion)
    print("✓ Applied custom wave distortion")
    
    # Polynomial warping
    poly_warped = warping.polynomial_warp(image, degree=2)
    print("✓ Applied polynomial warping")
    
    # Display results
    warping_results = [
        image, barrel_distorted, barrel_corrected, pincushion_distorted, 
        pincushion_corrected, custom_warped, poly_warped
    ]
    warping_titles = [
        "Original", "Barrel Distorted", "Barrel Corrected", "Pincushion Distorted",
        "Pincushion Corrected", "Custom Warp", "Polynomial Warp"
    ]
    
    display.show_comparison(warping_results, warping_titles, grid_size=(2, 4), figsize=(20, 10))
    
    return warping_results, warping_titles


def demonstrate_advanced_transforms(image: np.ndarray):
    """Demonstrate advanced transformation techniques."""
    print("\n" + "="*50)
    print("ADVANCED TRANSFORMATIONS")
    print("="*50)
    
    # Multi-step transformation
    multi_step = affine_transforms.multi_step_transform(image, [
        ('translate', (20, 10)),
        ('rotate', 15),
        ('scale', (1.1, 1.1))
    ])
    print("✓ Applied multi-step transformation")
    
    # Interpolation comparison
    nearest = affine_transforms.rotate_image(image, 30, interpolation=cv2.INTER_NEAREST)
    bilinear = affine_transforms.rotate_image(image, 30, interpolation=cv2.INTER_LINEAR)
    cubic = affine_transforms.rotate_image(image, 30, interpolation=cv2.INTER_CUBIC)
    print("✓ Applied rotation with different interpolation methods")
    
    # Display results
    advanced_results = [image, multi_step, nearest, bilinear, cubic]
    advanced_titles = ["Original", "Multi-step", "Nearest", "Bilinear", "Cubic"]
    
    display.show_comparison(advanced_results, advanced_titles, figsize=(20, 4))
    
    return advanced_results, advanced_titles


def demonstrate_geometric_transformations(image_path: str = None):
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
    affine_results, affine_titles = demonstrate_affine_transforms(original)
    perspective_results, perspective_titles = demonstrate_perspective_transforms(original)
    warping_results, warping_titles = demonstrate_warping(original)
    advanced_results, advanced_titles = demonstrate_advanced_transforms(original)
    
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
        'transform_barrel_distorted.jpg': warping_results[1], # Barrel distorted
        'transform_barrel_corrected.jpg': warping_results[2], # Barrel corrected
        'transform_custom_warp.jpg': warping_results[5]     # Custom warp
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
        demonstrate_geometric_transformations(image_path)
    except KeyboardInterrupt:
        print("\nDemo interrupted by user.")
    except Exception as e:
        print(f"Error running demo: {e}")
        import traceback
        traceback.print_exc()


if __name__ == "__main__":
    main() 