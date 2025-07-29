"""
Unit tests for FeatureDetectionComponent.
"""

import pytest
import cv2
import numpy as np
from unittest.mock import Mock, patch, MagicMock
from components.feature_detection import FeatureDetectionComponent


class TestFeatureDetectionComponent:
    """Test cases for FeatureDetectionComponent."""
    
    @pytest.fixture
    def component(self):
        """Create FeatureDetectionComponent instance."""
        return FeatureDetectionComponent()
    
    def test_init(self, component):
        """Test component initialization."""
        assert component.name == "Feature Detection"
        assert component.description == "Detect corners, keypoints, contours, and lines"
    
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
        mock_selectbox.return_value = "Harris"
        mock_button.return_value = True
        
        component.render(sample_image)
        
        mock_header.assert_called_once_with("🎯 Feature Detection")
        assert mock_subheader.call_count >= 1
        assert mock_columns.call_count >= 1
    
    @patch('streamlit.header')
    @patch('streamlit.warning')
    def test_render_with_invalid_image(self, mock_warning, mock_header, component):
        """Test render method with invalid image."""
        component.render(None)
        
        mock_header.assert_called_once_with("🎯 Feature Detection")
        mock_warning.assert_called_once_with("Please upload an image to start experimenting with feature detection.")
    
    def test_detect_harris_corners(self, component, sample_image):
        """Test Harris corner detection."""
        block_size = 2
        ksize = 3
        k = 0.04
        
        result = component._detect_harris_corners(sample_image, block_size, ksize, k)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_shi_tomasi_corners(self, component, sample_image):
        """Test Shi-Tomasi corner detection."""
        max_corners = 25
        quality_level = 0.01
        min_distance = 10
        
        result = component._detect_shi_tomasi_corners(sample_image, max_corners, quality_level, min_distance)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_fast_corners(self, component, sample_image):
        """Test FAST corner detection."""
        threshold = 10
        non_max_suppression = True
        
        result = component._detect_fast_corners(sample_image, threshold, non_max_suppression)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_sift_keypoints(self, component, sample_image):
        """Test SIFT keypoint detection."""
        n_features = 0
        n_octave_layers = 3
        contrast_threshold = 0.04
        edge_threshold = 10
        sigma = 1.6
        
        result = component._detect_sift_keypoints(sample_image, n_features, n_octave_layers, 
                                                contrast_threshold, edge_threshold, sigma)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_surf_keypoints(self, component, sample_image):
        """Test SURF keypoint detection."""
        hessian_threshold = 100
        n_octaves = 4
        n_octave_layers = 3
        extended = False
        upright = False
        
        result = component._detect_surf_keypoints(sample_image, hessian_threshold, n_octaves, 
                                               n_octave_layers, extended, upright)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_orb_features(self, component, sample_image):
        """Test ORB feature detection."""
        max_features = 500
        scale_factor = 1.2
        n_levels = 8
        
        result = component._detect_orb_features(sample_image, max_features, scale_factor, n_levels)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_brisk_keypoints(self, component, sample_image):
        """Test BRISK keypoint detection."""
        threshold = 30
        octaves = 3
        
        result = component._detect_brisk_keypoints(sample_image, threshold, octaves)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_akaze_keypoints(self, component, sample_image):
        """Test AKAZE keypoint detection."""
        descriptor_size = 0
        descriptor_channels = 3
        threshold = 0.001
        
        result = component._detect_akaze_keypoints(sample_image, descriptor_size, descriptor_channels, threshold)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_simple_contours(self, component, sample_image):
        """Test simple contour detection."""
        threshold_value = 128
        
        result = component._detect_simple_contours(sample_image, threshold_value)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_approximated_contours(self, component, sample_image):
        """Test approximated contour detection."""
        threshold_value = 128
        epsilon_factor = 0.02
        
        result = component._detect_approximated_contours(sample_image, threshold_value, epsilon_factor)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_hierarchical_contours(self, component, sample_image):
        """Test hierarchical contour detection."""
        threshold_value = 128
        
        result = component._detect_hierarchical_contours(sample_image, threshold_value)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_hough_lines(self, component, sample_image):
        """Test Hough line detection."""
        rho = 1
        theta = 1
        threshold = 50
        
        result = component._detect_hough_lines(sample_image, rho, theta, threshold)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_probabilistic_hough_lines(self, component, sample_image):
        """Test probabilistic Hough line detection."""
        rho = 1
        theta = 1
        threshold = 50
        min_line_length = 100
        max_line_gap = 10
        
        result = component._detect_probabilistic_hough_lines(sample_image, rho, theta, threshold, min_line_length, max_line_gap)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_safe_operation_decorator(self, component):
        """Test safe operation decorator with error handling."""
        # Test with invalid parameters that should cause an error
        invalid_image = None
        
        result = component._detect_harris_corners(invalid_image, 2, 3, 0.04)
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