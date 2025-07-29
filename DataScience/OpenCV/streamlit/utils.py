"""
Utility functions for the Streamlit OpenCV dashboard.
"""

import cv2
import numpy as np
import streamlit as st
from PIL import Image
import io
from typing import Optional, Tuple, Union


def pil_to_cv2(pil_image: Image.Image) -> np.ndarray:
    """Convert PIL Image to OpenCV format (BGR)."""
    # Convert PIL to RGB numpy array
    rgb_array = np.array(pil_image)
    
    # Convert RGB to BGR for OpenCV
    bgr_array = cv2.cvtColor(rgb_array, cv2.COLOR_RGB2BGR)
    
    return bgr_array


def cv2_to_pil(cv2_image: np.ndarray) -> Image.Image:
    """Convert OpenCV image (BGR) to PIL Image (RGB)."""
    # Convert BGR to RGB
    rgb_array = cv2.cvtColor(cv2_image, cv2.COLOR_BGR2RGB)
    
    # Convert to PIL Image
    pil_image = Image.fromarray(rgb_array)
    
    return pil_image


def load_image_from_upload(uploaded_file) -> Optional[np.ndarray]:
    """Load image from Streamlit uploaded file."""
    if uploaded_file is not None:
        try:
            # Read the uploaded file
            file_bytes = np.asarray(bytearray(uploaded_file.read()), dtype=np.uint8)
            image = cv2.imdecode(file_bytes, cv2.IMREAD_COLOR)
            return image
        except Exception as e:
            st.error(f"Error loading image: {e}")
            return None
    return None


def display_image(image: np.ndarray, caption: str = "Image", use_container_width: bool = True):
    """Display OpenCV image in Streamlit."""
    if image is not None:
        pil_image = cv2_to_pil(image)
        st.image(pil_image, caption=caption, use_container_width=use_container_width)


def display_comparison(original: np.ndarray, processed: np.ndarray, 
                      original_caption: str = "Original", 
                      processed_caption: str = "Processed"):
    """Display original and processed images side by side."""
    col1, col2 = st.columns(2)
    
    with col1:
        display_image(original, original_caption)
    
    with col2:
        display_image(processed, processed_caption)


def get_image_info(image: np.ndarray) -> dict:
    """Get basic information about the image."""
    if image is None:
        return {}
    
    height, width = image.shape[:2]
    channels = image.shape[2] if len(image.shape) > 2 else 1
    dtype = str(image.dtype)
    
    return {
        "width": width,
        "height": height,
        "channels": channels,
        "dtype": dtype,
        "size": f"{width} x {height}"
    }


def create_download_button(image: np.ndarray, filename: str, caption: str = "Download processed image"):
    """Create a download button for the processed image."""
    if image is not None:
        pil_image = cv2_to_pil(image)
        
        # Convert PIL image to bytes
        img_buffer = io.BytesIO()
        pil_image.save(img_buffer, format='PNG')
        img_buffer.seek(0)
        
        st.download_button(
            label=caption,
            data=img_buffer.getvalue(),
            file_name=filename,
            mime="image/png"
        )


def validate_image(image: np.ndarray) -> bool:
    """Validate if the image is valid for processing."""
    if image is None:
        return False
    
    if not isinstance(image, np.ndarray):
        return False
    
    if len(image.shape) < 2:
        return False
    
    return True 