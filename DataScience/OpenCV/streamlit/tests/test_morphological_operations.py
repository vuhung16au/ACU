"""
Unit tests for MorphologicalOperationsComponent.
"""

import pytest
import cv2
import numpy as np
from unittest.mock import Mock, patch, MagicMock
from components.morphological import MorphologicalComponent


class TestMorphologicalComponent:
    """Test cases for MorphologicalComponent."""
    
    @pytest.fixture
    def component(self):
        """Create MorphologicalComponent instance."""
        return MorphologicalComponent()
    
    def test_init(self, component):
        """Test component initialization."""
        assert component.name == "Morphological Operations"
        assert component.description == "Apply morphological operations to images"
    
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
        mock_selectbox.return_value = "Erosion"
        mock_button.return_value = True
        
        component.render(sample_image)
        
        mock_header.assert_called_once_with("🔧 Morphological Operations")
        assert mock_subheader.call_count >= 1
        assert mock_columns.call_count >= 1
    
    @patch('streamlit.header')
    @patch('streamlit.warning')
    def test_render_with_invalid_image(self, mock_warning, mock_header, component):
        """Test render method with invalid image."""
        component.render(None)
        
        mock_header.assert_called_once_with("🔧 Morphological Operations")
        mock_warning.assert_called_once_with("Please upload an image to start experimenting with morphological operations.")
    
    def test_apply_morphological_operation(self, component, sample_image):
        """Test morphological operation."""
        operation = "Erosion"
        kernel_size = 3
        iterations = 1
        kernel_shape = "Rectangle"
        
        result = component._apply_morphological_operation(sample_image, operation, kernel_size, iterations, kernel_shape)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    # def test_apply_dilation(self, component, sample_image):
    #     """Test dilation operation."""
    #     kernel_size = 3
    #     iterations = 1
    #     
    #     result = component._apply_dilation(sample_image, kernel_size, iterations)
    #     
    #     assert result is not None
    #     assert result.shape == sample_image.shape
    #     assert result.dtype == np.uint8
    
    # def test_apply_opening(self, component, sample_image):
    #     """Test opening operation."""
    #     kernel_size = 3
    #     iterations = 1
    #     
    #     result = component._apply_opening(sample_image, kernel_size, iterations)
    #     
    #     assert result is not None
    #     assert result.shape == sample_image.shape
    #     assert result.dtype == np.uint8
    
    # def test_apply_closing(self, component, sample_image):
    #     """Test closing operation."""
    #     kernel_size = 3
    #     iterations = 1
    #     
    #     result = component._apply_closing(sample_image, kernel_size, iterations)
    #     
    #     assert result is not None
    #     assert result.shape == sample_image.shape
    #     assert result.dtype == np.uint8
    
    # def test_apply_gradient(self, component, sample_image):
    #     """Test morphological gradient."""
    #     kernel_size = 3
    #     
    #     result = component._apply_gradient(sample_image, kernel_size)
    #     
    #     assert result is not None
    #     assert result.shape == sample_image.shape
    #     assert result.dtype == np.uint8
    
    # def test_apply_tophat(self, component, sample_image):
    #     """Test top-hat operation."""
    #     kernel_size = 3
    #     
    #     result = component._apply_tophat(sample_image, kernel_size)
    #     
    #     assert result is not None
    #     assert result.shape == sample_image.shape
    #     assert result.dtype == np.uint8
    
    # def test_apply_blackhat(self, component, sample_image):
    #     """Test black-hat operation."""
    #     kernel_size = 3
    #     
    #     result = component._apply_blackhat(sample_image, kernel_size)
    #     
    #     assert result is not None
    #     assert result.shape == sample_image.shape
    #     assert result.dtype == np.uint8
    
    # def test_apply_hit_miss(self, component, sample_image):
    #     """Test hit-or-miss operation."""
    #     kernel = np.array([[0, 1, 0], [1, 1, 1], [0, 1, 0]], dtype=np.uint8)
    #     
    #     result = component._apply_hit_miss(sample_image, kernel)
    #     
    #     assert result is not None
    #     assert result.shape == sample_image.shape
    #     assert result.dtype == np.uint8
    
    # def test_apply_skeletonization(self, component, sample_image):
    #     """Test skeletonization operation."""
    #     result = component._apply_skeletonization(sample_image)
    #     
    #     assert result is not None
    #     assert result.shape == sample_image.shape
    #     assert result.dtype == np.uint8
    
    # def test_apply_thinning(self, component, sample_image):
    #     """Test thinning operation."""
    #     result = component._apply_thinning(sample_image)
    #     
    #     assert result is not None
    #     assert result.shape == sample_image.shape
    #     assert result.dtype == np.uint8
    
    # def test_apply_thickening(self, component, sample_image):
    #     """Test thickening operation."""
    #     result = component._apply_thickening(sample_image)
    #     
    #     assert result is not None
    #     assert result.shape == sample_image.shape
    #     assert result.dtype == np.uint8
    
    # def test_apply_distance_transform(self, component, sample_image):
    #     """Test distance transform."""
    #     distance_type = "DIST_L2"
    #     mask_size = 3
    #     
    #     result = component._apply_distance_transform(sample_image, distance_type, mask_size)
    #     
    #     assert result is not None
    #     assert result.shape == sample_image.shape
    #     assert result.dtype == np.uint8
    
    # def test_apply_watershed(self, component, sample_image):
    #     """Test watershed segmentation."""
    #     result = component._apply_watershed(sample_image)
    #     
    #     assert result is not None
    #     assert result.shape == sample_image.shape
    #     assert result.dtype == np.uint8
    
    # def test_apply_flood_fill(self, component, sample_image):
    #     """Test flood fill operation."""
    #     seed_point = (100, 100)
    #     new_val = (255, 0, 0)
    #     
    #     result = component._apply_flood_fill(sample_image, seed_point, new_val)
    #     
    #     assert result is not None
    #     assert result.shape == sample_image.shape
    #     assert result.dtype == np.uint8
    
    def test_safe_operation_decorator(self, component):
        """Test safe operation decorator with error handling."""
        # Test with invalid parameters that should cause an error
        invalid_image = None
        
        result = component._apply_morphological_operation(invalid_image, "Erosion", 3, 1, "Rectangle")
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