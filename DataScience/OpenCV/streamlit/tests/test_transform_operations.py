"""
Unit tests for TransformOperationsComponent.
"""

import pytest
import cv2
import numpy as np
from unittest.mock import Mock, patch, MagicMock
from components.transformations import TransformationsComponent


class TestTransformationsComponent:
    """Test cases for TransformationsComponent."""
    
    @pytest.fixture
    def component(self):
        """Create TransformationsComponent instance."""
        return TransformationsComponent()
    
    def test_init(self, component):
        """Test component initialization."""
        assert component.name == "Transformations"
        assert component.description == "Apply affine and perspective transformations"
    
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
        mock_selectbox.return_value = "Translation"
        mock_button.return_value = True
        
        component.render(sample_image)
        
        mock_header.assert_called_once_with("🔄 Transformations")
        assert mock_subheader.call_count >= 1
        assert mock_columns.call_count >= 1
    
    @patch('streamlit.header')
    @patch('streamlit.warning')
    def test_render_with_invalid_image(self, mock_warning, mock_header, component):
        """Test render method with invalid image."""
        component.render(None)
        
        mock_header.assert_called_once_with("🔄 Transformations")
        mock_warning.assert_called_once_with("Please upload an image to start experimenting with transformations.")
    
    def test_apply_translation(self, component, sample_image):
        """Test translation transformation."""
        tx = 50
        ty = 30
        
        result = component._apply_translation(sample_image, tx, ty)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_apply_rotation(self, component, sample_image):
        """Test rotation transformation."""
        angle = 45.0
        scale = 1.0
        
        result = component._apply_rotation(sample_image, angle, scale)
        
        assert result is not None
        assert result.dtype == np.uint8
    
    def test_apply_scaling(self, component, sample_image):
        """Test scaling transformation."""
        scale_x = 1.5
        scale_y = 0.8
        
        result = component._apply_scaling(sample_image, scale_x, scale_y)
        
        assert result is not None
        assert result.dtype == np.uint8
    
    def test_apply_shearing(self, component, sample_image):
        """Test shearing transformation."""
        shear_x = 0.3
        shear_y = 0.2
        
        result = component._apply_shearing(sample_image, shear_x, shear_y)
        
        assert result is not None
        assert result.dtype == np.uint8
    
    def test_apply_reflection(self, component, sample_image):
        """Test reflection transformation."""
        axis = "Horizontal"
        
        result = component._apply_reflection(sample_image, axis)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_apply_perspective_transform(self, component, sample_image):
        """Test perspective transformation."""
        # Define source and destination points
        height, width = sample_image.shape[:2]
        src_points = np.float32([[0, 0], [width, 0], [width, height], [0, height]])
        dst_points = np.float32([[50, 50], [width-50, 100], [width-100, height-50], [100, height-100]])
        
        result = component._apply_perspective_transform(sample_image, src_points, dst_points)
        
        assert result is not None
        assert result.dtype == np.uint8
    
    def test_apply_affine_transform(self, component, sample_image):
        """Test affine transformation."""
        # Define transformation matrix
        height, width = sample_image.shape[:2]
        src_points = np.float32([[0, 0], [width, 0], [width//2, height]])
        dst_points = np.float32([[50, 50], [width-50, 50], [width//2, height-50]])
        
        result = component._apply_affine_transform(sample_image, src_points, dst_points)
        
        assert result is not None
        assert result.dtype == np.uint8
    
    def test_apply_homography(self, component, sample_image):
        """Test homography transformation."""
        # Define source and destination points
        height, width = sample_image.shape[:2]
        src_points = np.float32([[0, 0], [width, 0], [width, height], [0, height]])
        dst_points = np.float32([[50, 50], [width-50, 100], [width-100, height-50], [100, height-100]])
        
        result = component._apply_homography(sample_image, src_points, dst_points)
        
        assert result is not None
        assert result.dtype == np.uint8
    
    def test_apply_warp_polar(self, component, sample_image):
        """Test polar warping."""
        center = (sample_image.shape[1]//2, sample_image.shape[0]//2)
        max_radius = min(sample_image.shape[:2]) // 2
        
        result = component._apply_warp_polar(sample_image, center, max_radius)
        
        assert result is not None
        assert result.dtype == np.uint8
    
    def test_apply_remap(self, component, sample_image):
        """Test remap transformation."""
        height, width = sample_image.shape[:2]
        
        # Create simple remap
        map_x = np.zeros((height, width), np.float32)
        map_y = np.zeros((height, width), np.float32)
        
        for i in range(height):
            for j in range(width):
                map_x[i, j] = j + 10
                map_y[i, j] = i + 10
        
        result = component._apply_remap(sample_image, map_x, map_y)
        
        assert result is not None
        assert result.dtype == np.uint8
    
    def test_safe_operation_decorator(self, component):
        """Test safe operation decorator with error handling."""
        # Test with invalid parameters that should cause an error
        invalid_image = None
        
        result = component._apply_translation(invalid_image, 10, 10)
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