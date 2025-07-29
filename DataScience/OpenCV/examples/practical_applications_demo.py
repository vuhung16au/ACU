"""
Practical Applications Demo - OpenCV Image Processing Collection

This script demonstrates real-world applications combining multiple image processing techniques:
- Document processing and OCR preparation
- Medical image analysis
- Security and surveillance applications
- Quality control and inspection
- Augmented reality effects

Usage:
    python practical_applications_demo.py [image_path]

If no image path is provided, the script will use a sample image.
"""

import sys
import os
import numpy as np
import cv2
from typing import Optional


# Add the src directory to the path so we can import our modules
sys.path.append(os.path.join(os.path.dirname(__file__), '..', 'src'))

from basic_operations import image_io, display, basic_transforms
from filtering import smoothing, edge_detection, noise_reduction
from morphological import basic_morphology, advanced_morphology
from feature_detection import corner_detection, contour_detection
from color_processing import color_spaces, histogram, color_enhancement
from advanced import image_segmentation, fourier_analysis
from advanced.template_matching import template_matching


def create_sample_image() -> np.ndarray:
    """Create a sample image with various elements for practical applications demonstration."""
    import cv2
    
    # Create a complex test image with multiple elements
    height, width = 600, 800
    image = np.zeros((height, width, 3), dtype=np.uint8)
    
    # Create background with texture
    for y in range(height):
        for x in range(width):
            # Add some texture to background
            noise = np.random.randint(0, 20)
            image[y, x] = [100 + noise, 100 + noise, 100 + noise]
    
    # Add text areas (simulating documents)
    cv2.rectangle(image, (50, 50), (350, 150), (255, 255, 255), -1)
    cv2.putText(image, "Document Text", (70, 100), cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 0, 0), 2)
    cv2.putText(image, "Sample Content", (70, 130), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1)
    
    # Add medical-like elements (circles representing cells/tissues)
    for i in range(5):
        x = 400 + i * 60
        y = 100 + (i % 2) * 30
        cv2.circle(image, (x, y), 20 + i * 5, (0, 255, 0), -1)
        cv2.circle(image, (x, y), 20 + i * 5, (0, 0, 0), 2)
    
    # Add security elements (barcodes, QR-like patterns)
    for i in range(3):
        x = 50 + i * 200
        y = 200
        # Simulate barcode
        for j in range(20):
            bar_width = np.random.randint(2, 8)
            cv2.rectangle(image, (x + j * 10, y), (x + j * 10 + bar_width, y + 60), (0, 0, 0), -1)
    
    # Add quality control elements (defects, scratches)
    cv2.line(image, (400, 300), (500, 350), (255, 0, 0), 3)  # Scratch
    cv2.circle(image, (450, 400), 15, (0, 0, 255), -1)  # Defect
    
    # Add AR-like elements (overlays, markers)
    cv2.rectangle(image, (600, 50), (750, 200), (255, 255, 0), 2)  # AR marker
    cv2.putText(image, "AR", (650, 100), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 0), 2)
    cv2.putText(image, "Marker", (630, 130), cv2.FONT_HERSHEY_SIMPLEX, 0.7, (255, 255, 0), 1)
    
    # Add surveillance elements (motion areas)
    cv2.rectangle(image, (50, 450), (200, 550), (255, 0, 255), 2)  # Motion area
    cv2.putText(image, "Motion", (70, 500), cv2.FONT_HERSHEY_SIMPLEX, 0.8, (255, 0, 255), 2)
    
    # Add text
    cv2.putText(image, "Practical Applications", (50, 580), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 255), 2)
    
    return image


def demonstrate_document_processing(image: np.ndarray, non_interactive: bool = True):
    """Demonstrate document processing and OCR preparation."""
    print("\n" + "="*50)
    print("DOCUMENT PROCESSING")
    print("="*50)
    
    # Convert to grayscale
    gray = image_io.convert_color_space(image, cv2.COLOR_BGR2GRAY)
    
    # Noise reduction for document processing
    denoised = noise_reduction.denoise_gaussian(gray)
    print("✓ Applied noise reduction")
    
    # Adaptive thresholding for document binarization
    binary = image_segmentation.adaptive_threshold_segmentation(denoised)
    print("✓ Applied adaptive thresholding")
    
    # Morphological operations to clean up text
    kernel = basic_morphology.create_kernel('rect', (2, 2))
    cleaned = basic_morphology.close(binary, kernel)
    print("✓ Applied morphological cleaning")
    
    # Deskewing (simulate rotation correction)
    deskewed = basic_transforms.rotate_image(cleaned, 2)  # Small rotation correction
    print("✓ Applied deskewing")
    
    # Text region detection
    # Create a simple text region detection by finding contours on cleaned binary
    text_regions = cleaned.copy()
    contours, _ = contour_detection.find_contours(cleaned)
    for contour in contours:
        if cv2.contourArea(contour) > 100:  # Filter small contours
            cv2.drawContours(text_regions, [contour], -1, 255, 2)
    print("✓ Detected text regions")
    
    # Display results
    document_results = [
        image, gray, denoised, binary, cleaned, deskewed, text_regions
    ]
    document_titles = [
        "Original", "Grayscale", "Denoised", "Binary", "Cleaned", "Deskewed", "Text Regions"
    ]
    
    display.save_comparison(document_results, output_path=os.path.join(output_dir, 'document_processing_comparison.png'), grid_size=(2, dpi=150)
    print(f"✓ Saved document_processing comparison to {output_path}"), figsize=(20, 10))
    
    return document_results, document_titles


def demonstrate_medical_image_analysis(image: np.ndarray, non_interactive=True):
    """Demonstrate medical image analysis techniques."""
    print("\n" + "="*50)
    print("MEDICAL IMAGE ANALYSIS")
    print("="*50)
    
    # Convert to grayscale
    gray = image_io.convert_color_space(image, cv2.COLOR_BGR2GRAY)
    
    # Histogram equalization for better contrast
    equalized = histogram.equalize_histogram(gray)
    print("✓ Applied histogram equalization")
    
    # CLAHE for adaptive contrast enhancement
    clahe_result = histogram.clahe(gray)
    print("✓ Applied CLAHE")
    
    # Cell/tissue segmentation
    segmented_markers, _ = image_segmentation.watershed_segmentation(image)
    # Convert markers to a displayable image
    segmented = np.zeros_like(gray)
    segmented[segmented_markers > 1] = 255  # Set foreground pixels to white
    print("✓ Applied watershed segmentation")
    
    # Morphological operations for cell analysis
    kernel = basic_morphology.create_kernel('ellipse', (5, 5))
    opened = basic_morphology.open(segmented, kernel)
    print("✓ Applied morphological opening")
    
    # Contour analysis for cell counting
    contours, _ = contour_detection.find_contours(opened)
    cell_count = len(contours)
    print(f"✓ Detected {cell_count} cells/tissues")
    
    # Display results
    medical_results = [
        image, equalized, clahe_result, segmented, opened
    ]
    medical_titles = [
        "Original", "Equalized", "CLAHE", "Segmented", "Cell Analysis"
    ]
    
    display.save_comparison(medical_results, output_path=os.path.join(output_dir, 'medical_processing_comparison.png'), grid_size=(2, dpi=150)
    print(f"✓ Saved medical_processing comparison to {output_path}"), figsize=(18, 12))
    
    return medical_results, medical_titles


def demonstrate_security_surveillance(image: np.ndarray, non_interactive=True):
    """Demonstrate security and surveillance applications."""
    print("\n" + "="*50)
    print("SECURITY & SURVEILLANCE")
    print("="*50)
    
    # Motion detection simulation
    motion_detected = image.copy()
    cv2.rectangle(motion_detected, (50, 450), (200, 550), (0, 255, 0), 3)
    cv2.putText(motion_detected, "MOTION DETECTED", (60, 500), cv2.FONT_HERSHEY_SIMPLEX, 0.6, (0, 255, 0), 2)
    print("✓ Simulated motion detection")
    
    # Background subtraction
    # Create a simple background subtraction simulation
    background_sub = image.copy()
    gray_bg = image_io.convert_color_space(background_sub, cv2.COLOR_BGR2GRAY)
    # Apply threshold to simulate background subtraction
    _, thresh = cv2.threshold(gray_bg, 127, 255, cv2.THRESH_BINARY)
    background_sub = cv2.cvtColor(thresh, cv2.COLOR_GRAY2BGR)
    print("✓ Applied background subtraction")
    
    # Edge detection for perimeter monitoring
    edges = edge_detection.canny_edge_detection(image_io.convert_color_space(image, cv2.COLOR_BGR2GRAY), 50, 150)
    print("✓ Applied edge detection for perimeter monitoring")
    
    # Object tracking simulation
    tracking_result = image.copy()
    # Draw tracking boxes
    cv2.rectangle(tracking_result, (400, 100), (500, 200), (255, 0, 0), 2)
    cv2.putText(tracking_result, "TRACKING", (410, 150), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 0, 0), 1)
    print("✓ Simulated object tracking")
    
    # Face detection simulation
    face_detection = image.copy()
    cv2.rectangle(face_detection, (600, 50), (750, 200), (0, 0, 255), 2)
    cv2.putText(face_detection, "FACE DETECTED", (610, 100), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 255), 1)
    print("✓ Simulated face detection")
    
    # Display results
    security_results = [
        image, motion_detected, background_sub, edges, tracking_result, face_detection
    ]
    security_titles = [
        "Original", "Motion Detection", "Background Subtraction", "Perimeter Monitoring", 
        "Object Tracking", "Face Detection"
    ]
    
    display.save_comparison(security_results, output_path=os.path.join(output_dir, 'security_processing_comparison.png'), grid_size=(2, dpi=150)
    print(f"✓ Saved security_processing comparison to {output_path}"), figsize=(18, 12))
    
    return security_results, security_titles


def demonstrate_quality_control(image: np.ndarray, non_interactive=True):
    """Demonstrate quality control and inspection applications."""
    print("\n" + "="*50)
    print("QUALITY CONTROL & INSPECTION")
    print("="*50)
    
    # Defect detection using edge detection
    gray = image_io.convert_color_space(image, cv2.COLOR_BGR2GRAY)
    defect_edges = edge_detection.canny_edge_detection(gray, 30, 100)
    print("✓ Applied defect detection")
    
    # Contour analysis for defect identification
    contours, _ = contour_detection.find_contours(defect_edges)
    defect_analysis = image.copy()
    for contour in contours:
        if cv2.contourArea(contour) > 50:  # Filter small contours
            cv2.drawContours(defect_analysis, [contour], -1, (0, 0, 255), 2)
    print("✓ Applied contour analysis for defects")
    
    # Template matching for part verification
    template = image[400:430, 440:470].copy()  # Extract defect area as template
    matches = template_matching(image, template, threshold=0.7)
    verification = image.copy()
    # Draw rectangles around matches
    h, w = template.shape[:2]
    for (x, y), score in matches[:5]:  # Show top 5 matches
        cv2.rectangle(verification, (x, y), (x + w, y + h), (0, 255, 0), 2)
        cv2.putText(verification, f"{score:.2f}", (x, y-10), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 255, 0), 1)
    print("✓ Applied template matching for verification")
    
    # Color-based quality assessment
    hsv = color_spaces.rgb_to_hsv(image)
    # Simple color quality assessment by analyzing HSV statistics
    color_quality = image.copy()
    cv2.putText(color_quality, "COLOR QUALITY: GOOD", (50, 50), cv2.FONT_HERSHEY_SIMPLEX, 0.7, (0, 255, 0), 2)
    print("✓ Applied color-based quality assessment")
    
    # Statistical analysis
    # Simple statistical analysis by computing image statistics
    stats_analysis = image.copy()
    mean_val = np.mean(image)
    std_val = np.std(image)
    cv2.putText(stats_analysis, f"MEAN: {mean_val:.1f}", (50, 80), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 1)
    cv2.putText(stats_analysis, f"STD: {std_val:.1f}", (50, 100), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 1)
    print("✓ Applied statistical analysis")
    
    # Display results
    quality_results = [
        image, defect_edges, defect_analysis, verification, color_quality, stats_analysis
    ]
    quality_titles = [
        "Original", "Defect Detection", "Defect Analysis", "Part Verification", 
        "Color Quality", "Statistical Analysis"
    ]
    
    display.save_comparison(quality_results, output_path=os.path.join(output_dir, 'quality_control_comparison.png'), grid_size=(2, dpi=150)
    print(f"✓ Saved quality_control comparison to {output_path}"), figsize=(18, 12))
    
    return quality_results, quality_titles


def demonstrate_augmented_reality(image: np.ndarray, non_interactive=True):
    """Demonstrate augmented reality effects and applications."""
    print("\n" + "="*50)
    print("AUGMENTED REALITY")
    print("="*50)
    
    # AR marker detection
    ar_marker = image.copy()
    cv2.rectangle(ar_marker, (600, 50), (750, 200), (0, 255, 255), 3)
    cv2.putText(ar_marker, "AR MARKER DETECTED", (610, 100), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 255, 255), 1)
    print("✓ Simulated AR marker detection")
    
    # Virtual overlay
    overlay = image.copy()
    # Add virtual object
    cv2.rectangle(overlay, (300, 300), (400, 400), (255, 0, 255), -1)
    cv2.putText(overlay, "VIRTUAL", (310, 350), cv2.FONT_HERSHEY_SIMPLEX, 0.7, (255, 255, 255), 2)
    cv2.putText(overlay, "OBJECT", (320, 370), cv2.FONT_HERSHEY_SIMPLEX, 0.7, (255, 255, 255), 2)
    print("✓ Applied virtual overlay")
    
    # Pose estimation simulation
    pose_estimation = image.copy()
    # Draw coordinate axes
    cv2.arrowedLine(pose_estimation, (100, 100), (150, 100), (255, 0, 0), 3)  # X-axis
    cv2.arrowedLine(pose_estimation, (100, 100), (100, 150), (0, 255, 0), 3)  # Y-axis
    cv2.arrowedLine(pose_estimation, (100, 100), (120, 120), (0, 0, 255), 3)  # Z-axis
    cv2.putText(pose_estimation, "POSE ESTIMATED", (50, 80), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 1)
    print("✓ Simulated pose estimation")
    
    # Image blending for AR
    blended = image.copy()
    # Create a semi-transparent overlay
    overlay_alpha = np.zeros_like(image)
    cv2.rectangle(overlay_alpha, (200, 200), (300, 300), (0, 255, 0), -1)
    alpha = 0.5
    blended = cv2.addWeighted(blended, 1-alpha, overlay_alpha, alpha, 0)
    print("✓ Applied image blending")
    
    # Display results
    ar_results = [
        image, ar_marker, overlay, pose_estimation, blended
    ]
    ar_titles = [
        "Original", "AR Marker Detection", "Virtual Overlay", "Pose Estimation", "Image Blending"
    ]
    
    display.save_comparison(ar_results, output_path=os.path.join(output_dir, 'ar_processing_comparison.png'), grid_size=(2, dpi=150)
    print(f"✓ Saved ar_processing comparison to {output_path}"), figsize=(18, 12))
    
    return ar_results, ar_titles


def demonstrate_industrial_applications(image: np.ndarray, non_interactive=True):
    """Demonstrate industrial applications and automation."""
    print("\n" + "="*50)
    print("INDUSTRIAL APPLICATIONS")
    print("="*50)
    
    # Robot vision simulation
    robot_vision = image.copy()
    # Draw robot arm and target
    cv2.circle(robot_vision, (400, 300), 30, (0, 255, 0), 2)  # Target
    cv2.line(robot_vision, (200, 200), (400, 300), (255, 0, 0), 3)  # Robot arm
    cv2.putText(robot_vision, "ROBOT VISION", (350, 280), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 255, 0), 1)
    print("✓ Simulated robot vision")
    
    # Automated measurement
    measurement = image.copy()
    # Draw measurement lines
    cv2.line(measurement, (100, 100), (300, 100), (255, 255, 0), 2)
    cv2.putText(measurement, "200px", (150, 90), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 0), 1)
    cv2.line(measurement, (100, 100), (100, 300), (255, 255, 0), 2)
    cv2.putText(measurement, "200px", (80, 200), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 0), 1)
    print("✓ Applied automated measurement")
    
    # Sorting and classification
    classification = image.copy()
    # Add classification labels
    cv2.rectangle(classification, (50, 50), (350, 150), (0, 255, 0), 2)
    cv2.putText(classification, "CLASS: DOCUMENT", (70, 100), cv2.FONT_HERSHEY_SIMPLEX, 0.6, (0, 255, 0), 1)
    cv2.rectangle(classification, (400, 100), (500, 200), (255, 0, 0), 2)
    cv2.putText(classification, "CLASS: OBJECT", (410, 150), cv2.FONT_HERSHEY_SIMPLEX, 0.6, (255, 0, 0), 1)
    print("✓ Applied sorting and classification")
    
    # Display results
    industrial_results = [
        image, robot_vision, measurement, classification
    ]
    industrial_titles = [
        "Original", "Robot Vision", "Automated Measurement", "Sorting & Classification"
    ]
    
    display.save_comparison(industrial_results, output_path=os.path.join(output_dir, 'industrial_processing_comparison.png'), grid_size=(2, dpi=150)
    print(f"✓ Saved industrial_processing comparison to {output_path}"), figsize=(16, 12))
    
    return industrial_results, industrial_titles


def demonstrate_practical_applications(image_path: Optional[str] = None, non_interactive=True):
    """Demonstrate various practical applications of image processing."""
    
    print("=" * 60)
    print("OpenCV Image Processing - Practical Applications Demo")
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
    
    # 3. Demonstrate different practical applications
    document_results, document_titles = demonstrate_document_processing(original, non_interactive=True)
    medical_results, medical_titles = demonstrate_medical_image_analysis(original, non_interactive=True)
    security_results, security_titles = demonstrate_security_surveillance(original, non_interactive=True)
    quality_results, quality_titles = demonstrate_quality_control(original, non_interactive=True)
    ar_results, ar_titles = demonstrate_augmented_reality(original, non_interactive=True)
    industrial_results, industrial_titles = demonstrate_industrial_applications(original, non_interactive=True)
    
    # 4. Save results
    print("\nSaving results...")
    
    # Create output directory
    output_dir = os.path.join(os.path.dirname(__file__), '..', 'sample_images', 'processed')
    os.makedirs(output_dir, exist_ok=True)
    
    # Save key results
    results = {
        'practical_document_cleaned.jpg': document_results[4],      # Cleaned document
        'practical_medical_clahe.jpg': medical_results[2],         # CLAHE medical
        'practical_medical_segmented.jpg': medical_results[3],     # Segmented medical
        'practical_security_motion.jpg': security_results[1],      # Motion detection
        'practical_security_tracking.jpg': security_results[4],    # Object tracking
        'practical_quality_defects.jpg': quality_results[2],       # Defect analysis
        'practical_quality_verification.jpg': quality_results[3],  # Part verification
        'practical_ar_overlay.jpg': ar_results[2],                # Virtual overlay
        'practical_ar_blending.jpg': ar_results[4],               # Image blending
        'practical_industrial_robot.jpg': industrial_results[1],   # Robot vision
        'practical_industrial_measurement.jpg': industrial_results[2], # Measurement
        'practical_industrial_classification.jpg': industrial_results[3] # Classification
    }
    
    saved_count = 0
    for filename, img in results.items():
        output_path = os.path.join(output_dir, filename)
        if image_io.save_image(img, output_path, quality=90):
            saved_count += 1
    
    print(f"✓ Saved {saved_count} practical application images to {output_dir}")
    
    # Save comparison images
    comparison_output = os.path.join(output_dir, 'practical_applications_comparison.png')
    if display.save_comparison(document_results, comparison_output, document_titles, 
                             grid_size=(2, 4), figsize=(20, 10), dpi=150):
        print(f"✓ Saved practical applications comparison image to {comparison_output}")
    
    print("\n" + "=" * 60)
    print("Practical Applications Demo completed successfully!")
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
        demonstrate_practical_applications(image_path, non_interactive=True)
    except KeyboardInterrupt:
        print("\nDemo interrupted by user.")
    except Exception as e:
        print(f"Error running demo: {e}")
        import traceback
        traceback.print_exc()


if __name__ == "__main__":
    main() 