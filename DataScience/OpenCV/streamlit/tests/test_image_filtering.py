"""
Unit tests for ImageFilteringComponent.
"""

import pytest
import cv2
import numpy as np
from unittest.mock import Mock, patch, MagicMock
from components.image_filtering import ImageFilteringComponent


class TestImageFilteringComponent:
    """Test cases for ImageFilteringComponent."""
    
    @pytest.fixture
    def component(self):
        """Create ImageFilteringComponent instance."""
        return ImageFilteringComponent()
    
    def test_init(self, component):
        """Test component initialization."""
        assert component.name == "Image Filtering"
        assert component.description == "Apply various filters and edge detection"
    
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
        mock_selectbox.return_value = "Gaussian"
        mock_button.return_value = True
        
        component.render(sample_image)
        
        mock_header.assert_called_once_with("🔍 Image Filtering")
        assert mock_subheader.call_count >= 1
        assert mock_columns.call_count >= 1
    
    @patch('streamlit.header')
    @patch('streamlit.warning')
    def test_render_with_invalid_image(self, mock_warning, mock_header, component):
        """Test render method with invalid image."""
        component.render(None)
        
        mock_header.assert_called_once_with("🔍 Image Filtering")
        mock_warning.assert_called_once_with("Please upload an image to start experimenting with image filtering.")
    
    def test_apply_gaussian_blur(self, component, sample_image):
        """Test Gaussian blur filtering."""
        kernel_size = 5
        sigma = 1.0
        
        result = component._apply_gaussian_blur(sample_image, kernel_size, sigma)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_apply_median_filter(self, component, sample_image):
        """Test median filtering."""
        kernel_size = 5
        
        result = component._apply_median_filter(sample_image, kernel_size)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_apply_bilateral_filter(self, component, sample_image):
        """Test bilateral filtering."""
        d = 9
        sigma_color = 75
        sigma_space = 75
        
        result = component._apply_bilateral_filter(sample_image, d, sigma_color, sigma_space)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_apply_gaussian_filter(self, component, sample_image):
        """Test Gaussian filtering."""
        kernel_size = 5
        sigma = 1.0
        
        result = component._apply_gaussian_filter(sample_image, kernel_size, sigma)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_apply_canny_edge_detection(self, component, sample_image):
        """Test Canny edge detection."""
        threshold1 = 50
        threshold2 = 150
        aperture_size = 3
        
        result = component._apply_canny_edge_detection(sample_image, threshold1, threshold2, aperture_size)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_apply_sobel_edge_detection(self, component, sample_image):
        """Test Sobel edge detection."""
        dx = 1
        dy = 1
        ksize = 3
        
        result = component._apply_sobel_edge_detection(sample_image, dx, dy, ksize)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_apply_laplacian_edge_detection(self, component, sample_image):
        """Test Laplacian edge detection."""
        ksize = 3
        
        result = component._apply_laplacian_edge_detection(sample_image, ksize)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    

    
    def test_safe_operation_decorator(self, component):
        """Test safe operation decorator with error handling."""
        # Test with invalid parameters that should cause an error
        invalid_image = None
        
        result = component._apply_gaussian_blur(invalid_image, 5, 1.0)
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