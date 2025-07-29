"""
Simple test to verify the test infrastructure works.
"""

import pytest
import numpy as np
from unittest.mock import Mock, patch


def test_sample_image_fixture(sample_image):
    """Test that the sample_image fixture works."""
    assert sample_image is not None
    assert isinstance(sample_image, np.ndarray)
    assert len(sample_image.shape) == 3  # Should be 3D (height, width, channels)
    assert sample_image.dtype == np.uint8


def test_grayscale_image_fixture(grayscale_image):
    """Test that the grayscale_image fixture works."""
    assert grayscale_image is not None
    assert isinstance(grayscale_image, np.ndarray)
    assert len(grayscale_image.shape) == 2  # Should be 2D (height, width)
    assert grayscale_image.dtype == np.uint8


def test_mock_streamlit_fixture(mock_streamlit):
    """Test that the mock_streamlit fixture works."""
    # This test should pass if the fixture is working
    assert True


def test_mock_display_functions_fixture(mock_display_functions):
    """Test that the mock_display_functions fixture works."""
    # This test should pass if the fixture is working
    assert True


def test_basic_assertions():
    """Test basic pytest functionality."""
    assert 1 + 1 == 2
    assert "hello" == "hello"
    assert [1, 2, 3] == [1, 2, 3]


def test_mock_functionality():
    """Test that mocking works correctly."""
    with patch('builtins.print') as mock_print:
        print("test")
        mock_print.assert_called_once_with("test")


def test_numpy_operations():
    """Test that numpy operations work correctly."""
    arr = np.array([1, 2, 3, 4, 5])
    assert arr.shape == (5,)
    assert arr.dtype == np.int64
    
    # Test image-like array
    img = np.random.randint(0, 255, (100, 100, 3), dtype=np.uint8)
    assert img.shape == (100, 100, 3)
    assert img.dtype == np.uint8


def test_opencv_import():
    """Test that OpenCV can be imported."""
    try:
        import cv2
        assert cv2 is not None
        # Test basic OpenCV functionality
        img = np.zeros((100, 100, 3), dtype=np.uint8)
        gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        assert gray.shape == (100, 100)
    except ImportError:
        pytest.skip("OpenCV not available")


if __name__ == "__main__":
    pytest.main([__file__, "-v"]) 