"""
Unit tests for ColorProcessingComponent.
"""

import pytest
import cv2
import numpy as np
from unittest.mock import Mock, patch, MagicMock
from components.color_processing import ColorProcessingComponent


class TestColorProcessingComponent:
    """Test cases for ColorProcessingComponent."""
    
    @pytest.fixture
    def component(self):
        """Create ColorProcessingComponent instance."""
        return ColorProcessingComponent()
    
    def test_init(self, component):
        """Test component initialization."""
        assert component.name == "Color Processing"
        assert component.description == "Process and enhance image colors"
    
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
        mock_selectbox.return_value = "RGB"
        mock_button.return_value = True
        
        component.render(sample_image)
        
        mock_header.assert_called_once_with("🎨 Color Processing")
        assert mock_subheader.call_count >= 1
        assert mock_columns.call_count >= 1
    
    @patch('streamlit.header')
    @patch('streamlit.warning')
    def test_render_with_invalid_image(self, mock_warning, mock_header, component):
        """Test render method with invalid image."""
        component.render(None)
        
        mock_header.assert_called_once_with("🎨 Color Processing")
        mock_warning.assert_called_once_with("Please upload an image to start experimenting with color processing.")
    
    def test_convert_colorspace(self, component, sample_image):
        """Test color space conversion."""
        color_space = "HSV"
        
        result = component._convert_colorspace(sample_image, color_space)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_adjust_brightness(self, component, sample_image):
        """Test brightness adjustment."""
        beta = 50
        
        result = component._adjust_brightness(sample_image, beta)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_adjust_contrast(self, component, sample_image):
        """Test contrast adjustment."""
        alpha = 1.5
        
        result = component._adjust_contrast(sample_image, alpha)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_adjust_saturation(self, component, sample_image):
        """Test saturation adjustment."""
        factor = 1.5
        
        result = component._adjust_saturation(sample_image, factor)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_adjust_brightness(self, component, sample_image):
        """Test brightness adjustment."""
        factor = 50
        
        result = component._adjust_brightness(sample_image, factor)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_apply_global_equalization(self, component, sample_image):
        """Test global histogram equalization."""
        result = component._apply_global_equalization(sample_image)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_apply_clahe_equalization(self, component, sample_image):
        """Test CLAHE equalization."""
        clip_limit = 2.0
        tile_grid_size = 8
        
        result = component._apply_clahe_equalization(sample_image, clip_limit, tile_grid_size)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_apply_adaptive_equalization(self, component, sample_image):
        """Test adaptive equalization."""
        result = component._apply_adaptive_equalization(sample_image)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_apply_gamma_correction(self, component, sample_image):
        """Test gamma correction."""
        gamma = 0.5
        
        result = component._apply_gamma_correction(sample_image, gamma)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_apply_color_balance(self, component, sample_image):
        """Test color balance application."""
        red_factor = 1.1
        green_factor = 1.0
        blue_factor = 0.9
        
        result = component._apply_color_balance(sample_image, red_factor, green_factor, blue_factor)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_apply_color_balance(self, component, sample_image):
        """Test color balance adjustment."""
        red_shift = 10
        green_shift = -5
        blue_shift = 15
        
        result = component._apply_color_balance(sample_image, red_shift, green_shift, blue_shift)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    # Note: _apply_white_balance and _apply_histogram_equalization methods don't exist in the component
    # These tests are removed as they test non-existent functionality
    

    
    def test_safe_operation_decorator(self, component):
        """Test safe operation decorator with error handling."""
        # Test with invalid parameters that should cause an error
        invalid_image = None
        
        result = component._adjust_brightness(invalid_image, 50)
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