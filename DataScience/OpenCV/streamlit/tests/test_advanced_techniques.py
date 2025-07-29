"""
Unit tests for AdvancedTechniquesComponent.
"""

import pytest
import cv2
import numpy as np
from unittest.mock import Mock, patch, MagicMock
from components.advanced_techniques import AdvancedTechniquesComponent


class TestAdvancedTechniquesComponent:
    """Test cases for AdvancedTechniquesComponent."""
    
    @pytest.fixture
    def component(self):
        """Create AdvancedTechniquesComponent instance."""
        return AdvancedTechniquesComponent()
    
    def test_init(self, component):
        """Test component initialization."""
        assert component.name == "Advanced Techniques"
        assert component.description == "Apply advanced image processing techniques"
    
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
        mock_selectbox.return_value = "Magnitude Spectrum"
        mock_button.return_value = True
        
        component.render(sample_image)
        
        mock_header.assert_called_once_with("⚡ Advanced Techniques")
        assert mock_subheader.call_count >= 1
        assert mock_columns.call_count >= 1
    
    @patch('streamlit.header')
    @patch('streamlit.warning')
    def test_render_with_invalid_image(self, mock_warning, mock_header, component):
        """Test render method with invalid image."""
        component.render(None)
        
        mock_header.assert_called_once_with("⚡ Advanced Techniques")
        mock_warning.assert_called_once_with("Please upload an image to start experimenting with advanced techniques.")
    
    def test_show_magnitude_spectrum(self, component, sample_image):
        """Test magnitude spectrum display."""
        result = component._show_magnitude_spectrum(sample_image)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_show_phase_spectrum(self, component, sample_image):
        """Test phase spectrum display."""
        result = component._show_phase_spectrum(sample_image)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_apply_low_pass_filter(self, component, sample_image):
        """Test low pass filter application."""
        cutoff_radius = 30
        
        result = component._apply_low_pass_filter(sample_image, cutoff_radius)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_apply_high_pass_filter(self, component, sample_image):
        """Test high pass filter application."""
        cutoff_radius = 30
        
        result = component._apply_high_pass_filter(sample_image, cutoff_radius)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_apply_band_pass_filter(self, component, sample_image):
        """Test band pass filter application."""
        low_cutoff = 10
        high_cutoff = 50
        
        result = component._apply_band_pass_filter(sample_image, low_cutoff, high_cutoff)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_apply_kmeans_segmentation_advanced(self, component, sample_image):
        """Test K-means segmentation."""
        k = 3
        
        result = component._apply_kmeans_segmentation_advanced(sample_image, k)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_apply_mean_shift_segmentation_advanced(self, component, sample_image):
        """Test mean shift segmentation."""
        spatial_radius = 20
        color_radius = 20
        
        result = component._apply_mean_shift_segmentation_advanced(sample_image, spatial_radius, color_radius)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    # def test_apply_watershed_segmentation(self, component, sample_image):
    #     """Test watershed segmentation."""
    #     result = component._apply_watershed_segmentation(sample_image)
    #     
    #     assert result is not None
    #     assert result.shape == sample_image.shape
    #     assert result.dtype == np.uint8
    
    def test_apply_grabcut_segmentation_advanced(self, component, sample_image):
        """Test GrabCut segmentation."""
        result = component._apply_grabcut_segmentation_advanced(sample_image)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    # def test_apply_slic_superpixels(self, component, sample_image):
    #     """Test SLIC superpixels."""
    #     num_segments = 100
    #     compactness = 10
    #     
    #     result = component._apply_slic_superpixels(sample_image, num_segments, compactness)
    #     
    #     assert result is not None
    #     assert result.shape == sample_image.shape
    #     assert result.dtype == np.uint8
    
    def test_apply_template_matching(self, component, sample_image):
        """Test template matching."""
        method = "TM_CCOEFF_NORMED"
        
        result = component._apply_template_matching(sample_image, method)
        
        assert result is not None
        assert result.dtype == np.uint8
    
    def test_detect_faces_ml(self, component, sample_image):
        """Test ML-based face detection."""
        result = component._detect_faces_ml(sample_image)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_objects_ml(self, component, sample_image):
        """Test ML-based object detection."""
        result = component._detect_objects_ml(sample_image)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_classify_image_ml(self, component, sample_image):
        """Test ML-based image classification."""
        result = component._classify_image_ml(sample_image)
        
        assert result is not None
        assert isinstance(result, str)
    
    def test_safe_operation_decorator(self, component):
        """Test safe operation decorator with error handling."""
        # Test with invalid parameters that should cause an error
        invalid_image = None
        
        result = component._show_magnitude_spectrum(invalid_image)
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