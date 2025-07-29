"""
Unit tests for BasicOperationsComponent.
"""

import pytest
import cv2
import numpy as np
from unittest.mock import Mock, patch, MagicMock
from components.basic_operations import BasicOperationsComponent


class TestBasicOperationsComponent:
    """Test cases for BasicOperationsComponent."""
    
    @pytest.fixture
    def component(self):
        """Create BasicOperationsComponent instance."""
        return BasicOperationsComponent()
    
    def test_init(self, component):
        """Test component initialization."""
        assert component.name == "Basic Operations"
        assert component.description == "Resize, rotate, flip, and crop images"
    
    def test_validate_input_valid_image(self, component, sample_image):
        """Test input validation with valid image."""
        assert component.validate_input(sample_image) is True
    
    def test_validate_input_invalid_image(self, component):
        """Test input validation with invalid image."""
        assert component.validate_input(None) is False
        assert component.validate_input(np.array([])) is False
    
    @patch('streamlit.header')
    @patch('streamlit.subheader')
    @patch('streamlit.columns')
    @patch('streamlit.selectbox')
    @patch('streamlit.slider')
    @patch('streamlit.button')
    @patch('streamlit.warning')
    @patch('streamlit.divider')
    def test_render_with_valid_image(self, mock_divider, mock_warning, mock_button, 
                                   mock_slider, mock_selectbox, mock_columns, 
                                   mock_subheader, mock_header, component, sample_image):
        """Test render method with valid image."""
        def mock_columns_side_effect(num):
            mock_cols = []
            for i in range(num):
                mock_col = Mock()
                mock_col.__enter__ = Mock(return_value=mock_col)
                mock_col.__exit__ = Mock(return_value=None)
                mock_cols.append(mock_col)
            return mock_cols
        
        mock_columns.side_effect = mock_columns_side_effect
        mock_selectbox.return_value = "Fixed Size"
        mock_button.return_value = True
        
        component.render(sample_image)
        
        mock_header.assert_called_once_with("🔧 Basic Operations")
        assert mock_subheader.call_count >= 1
        assert mock_columns.call_count >= 1
    
    @patch('streamlit.header')
    @patch('streamlit.warning')
    def test_render_with_invalid_image(self, mock_warning, mock_header, component):
        """Test render method with invalid image."""
        component.render(None)
        
        mock_header.assert_called_once_with("🔧 Basic Operations")
        mock_warning.assert_called_once_with("Please upload an image to start experimenting with basic operations.")
    
    def test_resize_image_fixed(self, component, sample_image):
        """Test fixed size image resizing."""
        new_size = (320, 240)
        interpolation = "Linear"
        
        result = component._resize_image_fixed(sample_image, new_size, interpolation)
        
        assert result is not None
        assert result.shape[:2] == new_size[::-1]  # OpenCV uses (height, width)
        assert result.dtype == np.uint8
    
    def test_resize_image_scale(self, component, sample_image):
        """Test scale factor image resizing."""
        scale = 0.5
        interpolation = "Linear"
        
        result = component._resize_image_scale(sample_image, scale, interpolation)
        
        assert result is not None
        expected_height = int(sample_image.shape[0] * scale)
        expected_width = int(sample_image.shape[1] * scale)
        assert result.shape[:2] == (expected_height, expected_width)
    
    def test_resize_image_aspect_ratio(self, component, sample_image):
        """Test aspect ratio image resizing."""
        max_dim = 300
        fit_mode = "Fit"
        
        result = component._resize_image_aspect_ratio(sample_image, max_dim, fit_mode)
        
        assert result is not None
        height, width = result.shape[:2]
        assert max(height, width) <= max_dim
    
    def test_rotate_image_custom(self, component, sample_image):
        """Test custom angle image rotation."""
        angle = 45.0
        scale = 1.0
        border_mode = "Constant"
        
        result = component._rotate_image_custom(sample_image, angle, scale, border_mode)
        
        assert result is not None
        assert result.dtype == np.uint8
    
    def test_rotate_image_90_steps(self, component, sample_image):
        """Test 90-degree step rotation."""
        times = 2  # 180 degrees
        
        result = component._rotate_image_90_steps(sample_image, times)
        
        assert result is not None
        assert result.dtype == np.uint8
    
    def test_flip_image_direction(self, component, sample_image):
        """Test image flipping."""
        directions = ["Horizontal", "Vertical", "Both"]
        
        for direction in directions:
            result = component._flip_image_direction(sample_image, direction)
            assert result is not None
            assert result.dtype == np.uint8
    
    def test_crop_image_region(self, component, sample_image):
        """Test region-based image cropping."""
        x, y, width, height = 100, 100, 200, 200
        
        result = component._crop_image_region(sample_image, x, y, width, height)
        
        assert result is not None
        assert result.shape[:2] == (height, width)
        assert result.dtype == np.uint8
    
    def test_crop_image_center(self, component, sample_image):
        """Test center-based image cropping."""
        crop_size = 200
        
        result = component._crop_image_center(sample_image, crop_size)
        
        assert result is not None
        assert result.shape[:2] == (crop_size, crop_size)
        assert result.dtype == np.uint8
    
    def test_safe_operation_decorator(self, component):
        """Test safe operation decorator with error handling."""
        # Test with invalid parameters that should cause an error
        invalid_image = None
        
        result = component._resize_image_fixed(invalid_image, (100, 100), "Linear")
        assert result is None
    
    def test_ensure_odd_kernel(self, component):
        """Test ensure_odd_kernel utility method."""
        assert component.ensure_odd_kernel(4) == 5
        assert component.ensure_odd_kernel(5) == 5
        assert component.ensure_odd_kernel(6) == 7
    
    def test_convert_to_grayscale(self, component, sample_image):
        """Test grayscale conversion."""
        result = component.convert_to_grayscale(sample_image)
        
        assert result is not None
        assert len(result.shape) == 2  # Grayscale should be 2D
        assert result.dtype == np.uint8
    
    def test_convert_to_bgr(self, component, grayscale_image):
        """Test BGR conversion."""
        result = component.convert_to_bgr(grayscale_image)
        
        assert result is not None
        assert len(result.shape) == 3  # BGR should be 3D
        assert result.shape[2] == 3  # 3 channels
        assert result.dtype == np.uint8
    
    @patch('streamlit.metric')
    def test_get_image_info_display(self, mock_metric, component, sample_image):
        """Test image info display."""
        component.get_image_info_display(sample_image)
        
        # Should call metric 4 times (width, height, channels, size)
        assert mock_metric.call_count == 4
    
    def test_display_result(self, component, sample_image):
        """Test display result method."""
        processed_image = sample_image.copy()
        
        # This should not raise any exceptions
        component.display_result(sample_image, processed_image, "Original", "Processed")
    
    def test_create_download(self, component, sample_image):
        """Test create download method."""
        # This should not raise any exceptions
        component.create_download(sample_image, "test.png", "Download Test")
    
    def test_show_warning(self, component):
        """Test show warning method."""
        with patch('streamlit.warning') as mock_warning:
            component.show_warning("Test warning")
            mock_warning.assert_called_once_with("Test warning")
    
    def test_show_error(self, component):
        """Test show error method."""
        with patch('streamlit.error') as mock_error:
            component.show_error("Test error")
            mock_error.assert_called_once_with("Test error")
    
    def test_show_success(self, component):
        """Test show success method."""
        with patch('streamlit.success') as mock_success:
            component.show_success("Test success")
            mock_success.assert_called_once_with("Test success") 