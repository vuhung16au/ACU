"""
Basic Operations Demo - OpenCV Image Processing Collection

This script demonstrates the basic image operations including:
- Loading and saving images
- Basic transformations (resize, rotate, flip, crop)
- Image display and comparison

Usage:
    python basic_operations_demo.py [image_path]

If no image path is provided, the script will use a sample image.
"""

import sys
import os
import numpy as np

# Add the src directory to the path so we can import our modules
sys.path.append(os.path.join(os.path.dirname(__file__), '..', 'src'))

from basic_operations import image_io, display, basic_transforms


def create_sample_image() -> np.ndarray:
    """Create a simple sample image for demonstration if no image is provided."""
    # Create a colorful gradient image
    height, width = 300, 400
    image = np.zeros((height, width, 3), dtype=np.uint8)
    
    # Create horizontal gradient
    for y in range(height):
        for x in range(width):
            image[y, x] = [
                int(255 * x / width),          # Red gradient
                int(255 * y / height),         # Green gradient
                int(255 * (x + y) / (width + height))  # Blue gradient
            ]
    
    # Add some geometric shapes
    import cv2
    
    # Draw a circle
    cv2.circle(image, (100, 100), 50, (255, 255, 255), -1)
    
    # Draw a rectangle
    cv2.rectangle(image, (200, 150), (350, 250), (0, 0, 0), -1)
    
    # Add text
    cv2.putText(image, "Sample Image", (50, 280), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 255), 2)
    
    return image


def demonstrate_basic_operations(image_path: str = None):
    """Demonstrate various basic image operations."""
    
    print("=" * 60)
    print("OpenCV Image Processing - Basic Operations Demo")
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
    
    # 3. Basic transformations
    print("\nApplying basic transformations...")
    
    # Resize image
    resized = basic_transforms.resize_image(original, (300, 200))
    print("✓ Resized to 300x200")
    
    # Resize with aspect ratio preserved
    resized_aspect = basic_transforms.resize_with_aspect_ratio(original, 250)
    print("✓ Resized with aspect ratio (max dimension: 250)")
    
    # Rotate image
    rotated_45 = basic_transforms.rotate_image(original, 45)
    print("✓ Rotated 45 degrees")
    
    # Rotate 90 degrees (optimized)
    rotated_90 = basic_transforms.rotate_image_90(original, 1)
    print("✓ Rotated 90 degrees")
    
    # Flip horizontally
    flipped_h = basic_transforms.flip_image(original, 1)
    print("✓ Flipped horizontally")
    
    # Flip vertically
    flipped_v = basic_transforms.flip_image(original, 0)
    print("✓ Flipped vertically")
    
    # Crop center
    h, w = original.shape[:2]
    crop_size = (min(w//2, 200), min(h//2, 150))
    cropped = basic_transforms.crop_center(original, crop_size)
    print(f"✓ Center cropped to {crop_size}")
    
    # Translate image
    translated = basic_transforms.translate_image(original, 50, -30)
    print("✓ Translated (50, -30)")
    
    # Pad image
    padded = basic_transforms.pad_image(original, 30)
    print("✓ Added 30px padding")
    
    # 4. Create image collections for display
    transformations = [
        original, resized, resized_aspect, rotated_45,
        rotated_90, flipped_h, flipped_v, cropped
    ]
    
    titles = [
        "Original", "Resized", "Aspect Preserved", "Rotated 45°",
        "Rotated 90°", "Flipped H", "Flipped V", "Center Crop"
    ]
    
    # 5. Display comparisons
    print("\nDisplaying image comparisons...")
    
    # Show basic transformations (2x4 grid)
    display.show_comparison(transformations, titles, grid_size=(2, 4), figsize=(16, 8))
    
    # Show size comparisons
    size_transforms = [original, resized, resized_aspect, padded]
    size_titles = ["Original", "Resized", "Aspect Preserved", "Padded"]
    display.show_comparison(size_transforms, size_titles, figsize=(16, 4))
    
    # Show rotation and flip effects
    rotation_flip = [original, rotated_45, rotated_90, flipped_h, flipped_v]
    rotation_titles = ["Original", "Rotated 45°", "Rotated 90°", "Flipped H", "Flipped V"]
    display.show_comparison(rotation_flip, rotation_titles, figsize=(20, 4))
    
    # 6. Save results
    print("\nSaving results...")
    
    # Create output directory
    output_dir = os.path.join(os.path.dirname(__file__), '..', 'sample_images', 'processed')
    os.makedirs(output_dir, exist_ok=True)
    
    # Save individual processed images
    results = {
        'original.jpg': original,
        'resized.jpg': resized,
        'rotated_45.jpg': rotated_45,
        'rotated_90.jpg': rotated_90,
        'flipped_horizontal.jpg': flipped_h,
        'flipped_vertical.jpg': flipped_v,
        'cropped_center.jpg': cropped,
        'translated.jpg': translated,
        'padded.jpg': padded
    }
    
    saved_count = 0
    for filename, img in results.items():
        output_path = os.path.join(output_dir, filename)
        if image_io.save_image(img, output_path, quality=90):
            saved_count += 1
    
    print(f"✓ Saved {saved_count} processed images to {output_dir}")
    
    # Save comparison images
    comparison_output = os.path.join(output_dir, 'basic_operations_comparison.png')
    if display.save_comparison(transformations, comparison_output, titles, 
                             grid_size=(2, 4), figsize=(16, 8), dpi=150):
        print(f"✓ Saved comparison image to {comparison_output}")
    
    # 7. Demonstrate histogram
    print("\nDisplaying histogram...")
    # Convert to grayscale for histogram
    gray = image_io.convert_color_space(original, cv2.COLOR_BGR2GRAY)
    if gray is not None:
        display.show_histogram(gray, "Original Image Histogram")
    
    print("\n" + "=" * 60)
    print("Demo completed successfully!")
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
        demonstrate_basic_operations(image_path)
    except KeyboardInterrupt:
        print("\nDemo interrupted by user.")
    except Exception as e:
        print(f"Error running demo: {e}")
        import traceback
        traceback.print_exc()


if __name__ == "__main__":
    main()
