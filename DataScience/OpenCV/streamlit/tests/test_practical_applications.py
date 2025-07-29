"""
Unit tests for PracticalApplicationsComponent.
"""

import pytest
import cv2
import numpy as np
from unittest.mock import Mock, patch, MagicMock
from components.practical_applications import PracticalApplicationsComponent


class TestPracticalApplicationsComponent:
    """Test cases for PracticalApplicationsComponent."""
    
    @pytest.fixture
    def component(self):
        """Create PracticalApplicationsComponent instance."""
        return PracticalApplicationsComponent()
    
    def test_init(self, component):
        """Test component initialization."""
        assert component.name == "Practical Applications"
        assert component.description == "Real-world applications of OpenCV"
    
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
        mock_selectbox.return_value = "Face Detection"
        mock_button.return_value = True
        
        component.render(sample_image)
        
        mock_header.assert_called_once_with("🏥 Practical Applications")
        assert mock_subheader.call_count >= 1
        assert mock_columns.call_count >= 1
    
    @patch('streamlit.header')
    @patch('streamlit.warning')
    def test_render_with_invalid_image(self, mock_warning, mock_header, component):
        """Test render method with invalid image."""
        component.render(None)
        
        mock_header.assert_called_once_with("🏥 Practical Applications")
        mock_warning.assert_called_once_with("Please upload an image to start experimenting with practical applications.")
    
    def test_detect_faces_haar(self, component, sample_image):
        """Test Haar face detection."""
        scale_factor = 1.1
        min_neighbors = 5
        
        result = component._detect_faces_haar(sample_image, scale_factor, min_neighbors)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_eyes(self, component, sample_image):
        """Test eye detection."""
        scale_factor = 1.1
        min_neighbors = 5
        min_size = (20, 20)
        
        result = component._detect_eyes(sample_image, scale_factor, min_neighbors, min_size)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_smile(self, component, sample_image):
        """Test smile detection."""
        scale_factor = 1.1
        min_neighbors = 5
        min_size = (30, 30)
        
        result = component._detect_smile(sample_image, scale_factor, min_neighbors, min_size)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_license_plate(self, component, sample_image):
        """Test license plate detection."""
        result = component._detect_license_plate(sample_image)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_text_ocr(self, component, sample_image):
        """Test OCR text detection."""
        result = component._detect_text_ocr(sample_image)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_barcode(self, component, sample_image):
        """Test barcode detection."""
        result = component._detect_barcode(sample_image)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_qr_code(self, component, sample_image):
        """Test QR code detection."""
        result = component._detect_qr_code(sample_image)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_objects(self, component, sample_image):
        """Test object detection."""
        confidence_threshold = 0.5
        nms_threshold = 0.4
        
        result = component._detect_objects(sample_image, confidence_threshold, nms_threshold)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_people(self, component, sample_image):
        """Test people detection."""
        win_stride = (8, 8)
        padding = (4, 4)
        scale = 1.05
        
        result = component._detect_people(sample_image, win_stride, padding, scale)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_cars(self, component, sample_image):
        """Test car detection."""
        scale_factor = 1.1
        min_neighbors = 3
        min_size = (30, 30)
        
        result = component._detect_cars(sample_image, scale_factor, min_neighbors, min_size)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_pedestrians(self, component, sample_image):
        """Test pedestrian detection."""
        win_stride = (8, 8)
        padding = (4, 4)
        scale = 1.05
        
        result = component._detect_pedestrians(sample_image, win_stride, padding, scale)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_hand_gestures(self, component, sample_image):
        """Test hand gesture detection."""
        result = component._detect_hand_gestures(sample_image)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_emotions(self, component, sample_image):
        """Test emotion detection."""
        result = component._detect_emotions(sample_image)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_age_gender(self, component, sample_image):
        """Test age and gender detection."""
        result = component._detect_age_gender(sample_image)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_pose(self, component, sample_image):
        """Test pose detection."""
        result = component._detect_pose(sample_image)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_landmarks(self, component, sample_image):
        """Test facial landmarks detection."""
        result = component._detect_landmarks(sample_image)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_skin_color(self, component, sample_image):
        """Test skin color detection."""
        result = component._detect_skin_color(sample_image)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_motion(self, component, sample_image):
        """Test motion detection."""
        result = component._detect_motion(sample_image)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_background_subtraction(self, component, sample_image):
        """Test background subtraction."""
        result = component._detect_background_subtraction(sample_image)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_foreground(self, component, sample_image):
        """Test foreground detection."""
        result = component._detect_foreground(sample_image)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_shadows(self, component, sample_image):
        """Test shadow detection."""
        result = component._detect_shadows(sample_image)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_edges_advanced(self, component, sample_image):
        """Test advanced edge detection."""
        result = component._detect_edges_advanced(sample_image)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_corners_advanced(self, component, sample_image):
        """Test advanced corner detection."""
        result = component._detect_corners_advanced(sample_image)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_detect_blobs_advanced(self, component, sample_image):
        """Test advanced blob detection."""
        result = component._detect_blobs_advanced(sample_image)
        
        assert result is not None
        assert result.shape == sample_image.shape
        assert result.dtype == np.uint8
    
    def test_safe_operation_decorator(self, component):
        """Test safe operation decorator with error handling."""
        # Test with invalid parameters that should cause an error
        invalid_image = None
        
        result = component._detect_faces(invalid_image, 1.1, 5, (30, 30))
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