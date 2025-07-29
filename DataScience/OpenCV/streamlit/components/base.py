"""
Base component class for OpenCV operations.
"""

import cv2
import numpy as np
import streamlit as st
from typing import Optional, Tuple, Union
from abc import ABC, abstractmethod
from utils import display_image, display_comparison, get_image_info, create_download_button, validate_image


class BaseComponent(ABC):
    """Base class for all OpenCV components."""
    
    def __init__(self, name: str, description: str):
        self.name = name
        self.description = description
    
    @abstractmethod
    def render(self, image: np.ndarray) -> None:
        """Render the component UI."""
        pass
    
    def validate_input(self, image: np.ndarray) -> bool:
        """Validate input image."""
        return validate_image(image)
    
    def show_warning(self, message: str) -> None:
        """Show warning message."""
        st.warning(message)
    
    def show_error(self, message: str) -> None:
        """Show error message."""
        st.error(message)
    
    def show_success(self, message: str) -> None:
        """Show success message."""
        st.success(message)
    
    def display_result(self, original: np.ndarray, processed: np.ndarray, 
                      original_caption: str = "Original", 
                      processed_caption: str = "Processed") -> None:
        """Display comparison of original and processed images."""
        display_comparison(original, processed, original_caption, processed_caption)
    
    def create_download(self, image: np.ndarray, filename: str, caption: str) -> None:
        """Create download button for processed image."""
        create_download_button(image, filename, caption)
    
    def get_image_info_display(self, image: np.ndarray) -> None:
        """Display image information."""
        info = get_image_info(image)
        col1, col2, col3, col4 = st.columns(4)
        
        with col1:
            st.metric("Width", info.get("width", 0))
        with col2:
            st.metric("Height", info.get("height", 0))
        with col3:
            st.metric("Channels", info.get("channels", 0))
        with col4:
            st.metric("Size", info.get("size", "0 x 0"))


class OperationMixin:
    """Mixin for common image operations."""
    
    @staticmethod
    def safe_operation(func):
        """Decorator for safe operation execution."""
        def wrapper(*args, **kwargs):
            try:
                return func(*args, **kwargs)
            except Exception as e:
                st.error(f"Error in {func.__name__}: {e}")
                return None
        return wrapper
    
    @staticmethod
    def ensure_odd_kernel(kernel_size: int) -> int:
        """Ensure kernel size is odd."""
        return kernel_size + 1 if kernel_size % 2 == 0 else kernel_size
    
    @staticmethod
    def convert_to_grayscale(image: np.ndarray) -> np.ndarray:
        """Convert image to grayscale if needed."""
        if len(image.shape) == 3:
            return cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        return image
    
    @staticmethod
    def convert_to_bgr(image: np.ndarray) -> np.ndarray:
        """Convert image to BGR if needed."""
        if len(image.shape) == 2:
            return cv2.cvtColor(image, cv2.COLOR_GRAY2BGR)
        return image 