"""
Pytest configuration and common fixtures for Streamlit component tests.
"""

import pytest
import cv2
import numpy as np
import os
from unittest.mock import Mock, patch
import streamlit as st


@pytest.fixture
def sample_image():
    """Load sample image for testing."""
    image_path = "sample_images/original/demo_image.jpg"
    if os.path.exists(image_path):
        return cv2.imread(image_path)
    else:
        # Create a test image if the file doesn't exist
        return np.random.randint(0, 255, (480, 640, 3), dtype=np.uint8)


@pytest.fixture
def grayscale_image():
    """Create a grayscale test image."""
    return np.random.randint(0, 255, (480, 640), dtype=np.uint8)


@pytest.fixture
def mock_streamlit():
    """Mock Streamlit functions."""
    def create_mock_column():
        mock_col = Mock()
        mock_col.__enter__ = Mock(return_value=mock_col)
        mock_col.__exit__ = Mock(return_value=None)
        return mock_col
    
    with patch('streamlit.header'), \
         patch('streamlit.subheader'), \
         patch('streamlit.columns', side_effect=lambda num: [create_mock_column() for _ in range(num)]), \
         patch('streamlit.selectbox'), \
         patch('streamlit.slider'), \
         patch('streamlit.button'), \
         patch('streamlit.warning'), \
         patch('streamlit.error'), \
         patch('streamlit.success'), \
         patch('streamlit.info'), \
         patch('streamlit.metric'), \
         patch('streamlit.divider'):
        yield


@pytest.fixture
def mock_display_functions():
    """Mock display and utility functions."""
    with patch('utils.display_image'), \
         patch('utils.display_comparison'), \
         patch('utils.get_image_info'), \
         patch('utils.create_download_button'), \
         patch('utils.validate_image'):
        yield


class MockStreamlit:
    """Mock Streamlit class for testing."""
    
    def __init__(self):
        self.called_functions = []
    
    def header(self, text):
        self.called_functions.append(('header', text))
    
    def subheader(self, text):
        self.called_functions.append(('subheader', text))
    
    def warning(self, text):
        self.called_functions.append(('warning', text))
    
    def error(self, text):
        self.called_functions.append(('error', text))
    
    def success(self, text):
        self.called_functions.append(('success', text))
    
    def info(self, text):
        self.called_functions.append(('info', text))
    
    def columns(self, num_columns):
        mock_columns = []
        for i in range(num_columns):
            mock_col = Mock()
            mock_col.__enter__ = Mock(return_value=mock_col)
            mock_col.__exit__ = Mock(return_value=None)
            mock_columns.append(mock_col)
        return mock_columns
    
    def selectbox(self, label, options, **kwargs):
        return options[0] if options else None
    
    def slider(self, label, min_val, max_val, value, step, **kwargs):
        return value
    
    def button(self, label, **kwargs):
        return True
    
    def metric(self, label, value):
        self.called_functions.append(('metric', label, value))
    
    def divider(self):
        self.called_functions.append('divider')


@pytest.fixture
def mock_st():
    """Mock Streamlit module."""
    mock_st = MockStreamlit()
    with patch('streamlit.st', mock_st):
        yield mock_st 