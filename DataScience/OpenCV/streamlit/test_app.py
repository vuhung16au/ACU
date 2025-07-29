"""
Test script to verify the Streamlit app components work correctly.
"""

import cv2
import numpy as np
from utils import pil_to_cv2, cv2_to_pil, get_image_info, validate_image
from components import (
    resize_image_fixed, resize_image_scale, resize_image_aspect_ratio,
    rotate_image_custom, rotate_image_90_steps, flip_image_direction,
    crop_image_region, crop_image_center
)

def create_test_image():
    """Create a simple test image."""
    # Create a colorful test image
    img = np.zeros((300, 400, 3), dtype=np.uint8)
    
    # Add some colored rectangles
    cv2.rectangle(img, (50, 50), (150, 150), (255, 0, 0), -1)  # Blue
    cv2.rectangle(img, (200, 50), (300, 150), (0, 255, 0), -1)  # Green
    cv2.rectangle(img, (50, 200), (150, 300), (0, 0, 255), -1)  # Red
    cv2.rectangle(img, (200, 200), (300, 300), (255, 255, 0), -1)  # Cyan
    
    # Add some text
    cv2.putText(img, "TEST", (150, 175), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 255), 2)
    
    return img

def test_basic_functions():
    """Test basic utility functions."""
    print("Testing basic utility functions...")
    
    # Create test image
    test_img = create_test_image()
    
    # Test image info
    info = get_image_info(test_img)
    print(f"Image info: {info}")
    assert info["width"] == 400
    assert info["height"] == 300
    assert info["channels"] == 3
    
    # Test validation
    assert validate_image(test_img) == True
    assert validate_image(None) == False
    
    # Test PIL conversion
    pil_img = cv2_to_pil(test_img)
    cv2_img = pil_to_cv2(pil_img)
    assert cv2_img.shape == test_img.shape
    
    print("✅ Basic functions test passed!")

def test_resize_functions():
    """Test resize functions."""
    print("Testing resize functions...")
    
    test_img = create_test_image()
    
    # Test fixed size resize
    resized = resize_image_fixed(test_img, (200, 150), "Linear")
    assert resized is not None
    assert resized.shape == (150, 200, 3)
    
    # Test scale resize
    scaled = resize_image_scale(test_img, 0.5, "Linear")
    assert scaled is not None
    assert scaled.shape == (150, 200, 3)
    
    # Test aspect ratio resize
    aspect_resized = resize_image_aspect_ratio(test_img, 200, "Fit")
    assert aspect_resized is not None
    
    print("✅ Resize functions test passed!")

def test_rotate_functions():
    """Test rotation functions."""
    print("Testing rotation functions...")
    
    test_img = create_test_image()
    
    # Test custom rotation
    rotated = rotate_image_custom(test_img, 45, 1.0, "Constant")
    assert rotated is not None
    assert rotated.shape == test_img.shape
    
    # Test 90-degree rotation
    rotated_90 = rotate_image_90_steps(test_img, 1)
    assert rotated_90 is not None
    assert rotated_90.shape == (400, 300, 3)  # Width and height swapped
    
    print("✅ Rotation functions test passed!")

def test_flip_functions():
    """Test flip functions."""
    print("Testing flip functions...")
    
    test_img = create_test_image()
    
    # Test horizontal flip
    flipped_h = flip_image_direction(test_img, "Horizontal")
    assert flipped_h is not None
    assert flipped_h.shape == test_img.shape
    
    # Test vertical flip
    flipped_v = flip_image_direction(test_img, "Vertical")
    assert flipped_v is not None
    assert flipped_v.shape == test_img.shape
    
    print("✅ Flip functions test passed!")

def test_crop_functions():
    """Test crop functions."""
    print("Testing crop functions...")
    
    test_img = create_test_image()
    
    # Test region crop
    cropped = crop_image_region(test_img, 50, 50, 100, 100)
    assert cropped is not None
    assert cropped.shape == (100, 100, 3)
    
    # Test center crop
    center_cropped = crop_image_center(test_img, 150)
    assert center_cropped is not None
    assert center_cropped.shape == (150, 150, 3)
    
    print("✅ Crop functions test passed!")

def main():
    """Run all tests."""
    print("🧪 Running Streamlit app tests...")
    print("=" * 50)
    
    try:
        test_basic_functions()
        test_resize_functions()
        test_rotate_functions()
        test_flip_functions()
        test_crop_functions()
        
        print("=" * 50)
        print("🎉 All tests passed! The Streamlit app is ready to run.")
        print("\nTo run the app:")
        print("1. Activate virtual environment: source ../.venv/bin/activate")
        print("2. Navigate to streamlit directory: cd streamlit")
        print("3. Run the app: streamlit run app.py")
        print("4. Open browser to: http://localhost:8501")
        
    except Exception as e:
        print(f"❌ Test failed: {e}")
        raise

if __name__ == "__main__":
    main() 