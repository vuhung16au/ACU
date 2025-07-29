"""
Basic Operations component for OpenCV operations.
"""

import cv2
import numpy as np
import streamlit as st
from typing import Optional, Tuple
from .base import BaseComponent, OperationMixin
from utils import get_image_info


class BasicOperationsComponent(BaseComponent, OperationMixin):
    """Component for basic image operations."""
    
    def __init__(self):
        super().__init__("Basic Operations", "Resize, rotate, flip, and crop images")
    
    def render(self, image: np.ndarray) -> None:
        """Render the basic operations section."""
        st.header("🔧 Basic Operations")
        
        if not self.validate_input(image):
            self.show_warning("Please upload an image to start experimenting with basic operations.")
            return
        
        # Image Information
        self._render_image_info(image)
        st.divider()
        
        # Resize Operation
        self._render_resize_section(image)
        st.divider()
        
        # Rotate Operation
        self._render_rotate_section(image)
        st.divider()
        
        # Flip Operation
        self._render_flip_section(image)
        st.divider()
        
        # Crop Operation
        self._render_crop_section(image)
    
    def _render_image_info(self, image: np.ndarray) -> None:
        """Render image information section."""
        st.subheader("📊 Image Information")
        self.get_image_info_display(image)
    
    def _render_resize_section(self, image: np.ndarray) -> None:
        """Render resize section."""
        st.subheader("📏 Resize Image")
        resize_col1, resize_col2 = st.columns(2)
        
        with resize_col1:
            resize_method = st.selectbox(
                "Resize Method",
                ["Fixed Size", "Scale Factor", "Aspect Ratio"],
                help="Choose how to resize the image"
            )
            
            if resize_method == "Fixed Size":
                self._render_fixed_size_resize(image)
            elif resize_method == "Scale Factor":
                self._render_scale_factor_resize(image)
            elif resize_method == "Aspect Ratio":
                self._render_aspect_ratio_resize(image)
    
    def _render_fixed_size_resize(self, image: np.ndarray) -> None:
        """Render fixed size resize controls."""
        info = get_image_info(image)
        new_width = st.slider("New Width", 50, 2000, info.get("width", 640), 10)
        new_height = st.slider("New Height", 50, 2000, info.get("height", 480), 10)
        interpolation = st.selectbox(
            "Interpolation",
            ["Linear", "Cubic", "Nearest", "Area", "Lanczos"],
            index=0,
            help="Interpolation method for resizing"
        )
        
        if st.button("Resize Image"):
            processed = self._resize_image_fixed(image, (new_width, new_height), interpolation)
            if processed is not None:
                self.display_result(image, processed, "Original", "Resized")
                self.create_download(processed, "resized_image.png", "Download Resized Image")
    
    def _render_scale_factor_resize(self, image: np.ndarray) -> None:
        """Render scale factor resize controls."""
        scale_factor = st.slider("Scale Factor", 0.1, 3.0, 1.0, 0.1)
        interpolation = st.selectbox(
            "Interpolation",
            ["Linear", "Cubic", "Nearest", "Area", "Lanczos"],
            index=0
        )
        
        if st.button("Resize Image"):
            processed = self._resize_image_scale(image, scale_factor, interpolation)
            if processed is not None:
                self.display_result(image, processed, "Original", "Resized")
                self.create_download(processed, "resized_image.png", "Download Resized Image")
    
    def _render_aspect_ratio_resize(self, image: np.ndarray) -> None:
        """Render aspect ratio resize controls."""
        max_dimension = st.slider("Max Dimension", 100, 1000, 800, 50)
        fit_mode = st.selectbox(
            "Fit Mode",
            ["Fit", "Fill", "Stretch"],
            help="How to fit the image within dimensions"
        )
        
        if st.button("Resize Image"):
            processed = self._resize_image_aspect_ratio(image, max_dimension, fit_mode)
            if processed is not None:
                self.display_result(image, processed, "Original", "Resized")
                self.create_download(processed, "resized_image.png", "Download Resized Image")
    
    def _render_rotate_section(self, image: np.ndarray) -> None:
        """Render rotate section."""
        st.subheader("🔄 Rotate Image")
        rotate_col1, rotate_col2 = st.columns(2)
        
        with rotate_col1:
            rotation_method = st.selectbox(
                "Rotation Method",
                ["Custom Angle", "90° Steps"],
                help="Choose rotation method"
            )
            
            if rotation_method == "Custom Angle":
                self._render_custom_rotation(image)
            elif rotation_method == "90° Steps":
                self._render_90_degree_rotation(image)
    
    def _render_custom_rotation(self, image: np.ndarray) -> None:
        """Render custom rotation controls."""
        angle = st.slider("Rotation Angle (degrees)", -180, 180, 0, 1)
        scale = st.slider("Scale", 0.1, 2.0, 1.0, 0.1)
        border_mode = st.selectbox(
            "Border Mode",
            ["Constant", "Replicate", "Reflect", "Wrap"],
            help="How to handle borders"
        )
        
        if st.button("Rotate Image"):
            processed = self._rotate_image_custom(image, angle, scale, border_mode)
            if processed is not None:
                self.display_result(image, processed, "Original", "Rotated")
                self.create_download(processed, "rotated_image.png", "Download Rotated Image")
    
    def _render_90_degree_rotation(self, image: np.ndarray) -> None:
        """Render 90-degree rotation controls."""
        times = st.selectbox("Rotate 90°", [1, 2, 3], format_func=lambda x: f"{x * 90}°")
        
        if st.button("Rotate Image"):
            processed = self._rotate_image_90_steps(image, times)
            if processed is not None:
                self.display_result(image, processed, "Original", "Rotated")
                self.create_download(processed, "rotated_image.png", "Download Rotated Image")
    
    def _render_flip_section(self, image: np.ndarray) -> None:
        """Render flip section."""
        st.subheader("🔄 Flip Image")
        flip_col1, flip_col2 = st.columns(2)
        
        with flip_col1:
            flip_direction = st.selectbox(
                "Flip Direction",
                ["Horizontal", "Vertical", "Both"],
                help="Choose flip direction"
            )
            
            if st.button("Flip Image"):
                processed = self._flip_image_direction(image, flip_direction)
                if processed is not None:
                    self.display_result(image, processed, "Original", "Flipped")
                    self.create_download(processed, "flipped_image.png", "Download Flipped Image")
    
    def _render_crop_section(self, image: np.ndarray) -> None:
        """Render crop section."""
        st.subheader("✂️ Crop Image")
        crop_col1, crop_col2 = st.columns(2)
        
        with crop_col1:
            crop_method = st.selectbox(
                "Crop Method",
                ["Custom Region", "Center Crop"],
                help="Choose crop method"
            )
            
            if crop_method == "Custom Region":
                self._render_custom_crop(image)
            elif crop_method == "Center Crop":
                self._render_center_crop(image)
    
    def _render_custom_crop(self, image: np.ndarray) -> None:
        """Render custom crop controls."""
        height, width = image.shape[:2]
        x = st.slider("X Position", 0, width-1, 0)
        y = st.slider("Y Position", 0, height-1, 0)
        crop_width = st.slider("Crop Width", 1, width-x, min(100, width-x))
        crop_height = st.slider("Crop Height", 1, height-y, min(100, height-y))
        
        if st.button("Crop Image"):
            processed = self._crop_image_region(image, x, y, crop_width, crop_height)
            if processed is not None:
                self.display_result(image, processed, "Original", "Cropped")
                self.create_download(processed, "cropped_image.png", "Download Cropped Image")
    
    def _render_center_crop(self, image: np.ndarray) -> None:
        """Render center crop controls."""
        height, width = image.shape[:2]
        crop_size = st.slider("Crop Size", 50, min(width, height), min(width, height)//2, 10)
        
        if st.button("Crop Image"):
            processed = self._crop_image_center(image, crop_size)
            if processed is not None:
                self.display_result(image, processed, "Original", "Cropped")
                self.create_download(processed, "cropped_image.png", "Download Cropped Image")
    
    # Helper methods for image operations
    @OperationMixin.safe_operation
    def _resize_image_fixed(self, image: np.ndarray, size: Tuple[int, int], interpolation: str) -> Optional[np.ndarray]:
        """Resize image to fixed size."""
        interpolation_map = {
            "Linear": cv2.INTER_LINEAR,
            "Cubic": cv2.INTER_CUBIC,
            "Nearest": cv2.INTER_NEAREST,
            "Area": cv2.INTER_AREA,
            "Lanczos": cv2.INTER_LANCZOS4
        }
        
        resized = cv2.resize(image, size, interpolation=interpolation_map[interpolation])
        return resized
    
    @OperationMixin.safe_operation
    def _resize_image_scale(self, image: np.ndarray, scale: float, interpolation: str) -> Optional[np.ndarray]:
        """Resize image by scale factor."""
        height, width = image.shape[:2]
        new_size = (int(width * scale), int(height * scale))
        return self._resize_image_fixed(image, new_size, interpolation)
    
    @OperationMixin.safe_operation
    def _resize_image_aspect_ratio(self, image: np.ndarray, max_dim: int, fit_mode: str) -> Optional[np.ndarray]:
        """Resize image maintaining aspect ratio."""
        height, width = image.shape[:2]
        
        if fit_mode == "Fit":
            scale = min(max_dim / width, max_dim / height)
        elif fit_mode == "Fill":
            scale = max(max_dim / width, max_dim / height)
        else:  # Stretch
            scale = max_dim / max(width, height)
        
        new_size = (int(width * scale), int(height * scale))
        return self._resize_image_fixed(image, new_size, "Linear")
    
    @OperationMixin.safe_operation
    def _rotate_image_custom(self, image: np.ndarray, angle: float, scale: float, border_mode: str) -> Optional[np.ndarray]:
        """Rotate image by custom angle."""
        border_map = {
            "Constant": cv2.BORDER_CONSTANT,
            "Replicate": cv2.BORDER_REPLICATE,
            "Reflect": cv2.BORDER_REFLECT,
            "Wrap": cv2.BORDER_WRAP
        }
        
        height, width = image.shape[:2]
        center = (width // 2, height // 2)
        rotation_matrix = cv2.getRotationMatrix2D(center, angle, scale)
        rotated = cv2.warpAffine(image, rotation_matrix, (width, height), 
                                borderMode=border_map[border_mode])
        return rotated
    
    @OperationMixin.safe_operation
    def _rotate_image_90_steps(self, image: np.ndarray, times: int) -> Optional[np.ndarray]:
        """Rotate image by 90° steps."""
        rotated = image
        for _ in range(times):
            rotated = cv2.rotate(rotated, cv2.ROTATE_90_CLOCKWISE)
        return rotated
    
    @OperationMixin.safe_operation
    def _flip_image_direction(self, image: np.ndarray, direction: str) -> Optional[np.ndarray]:
        """Flip image in specified direction."""
        flip_map = {
            "Horizontal": 1,  # Flip around y-axis
            "Vertical": 0,    # Flip around x-axis
            "Both": -1        # Flip around both axes
        }
        
        flipped = cv2.flip(image, flip_map[direction])
        return flipped
    
    @OperationMixin.safe_operation
    def _crop_image_region(self, image: np.ndarray, x: int, y: int, width: int, height: int) -> Optional[np.ndarray]:
        """Crop image from specific region."""
        cropped = image[y:y+height, x:x+width]
        return cropped
    
    @OperationMixin.safe_operation
    def _crop_image_center(self, image: np.ndarray, crop_size: int) -> Optional[np.ndarray]:
        """Crop image from center."""
        height, width = image.shape[:2]
        center_x, center_y = width // 2, height // 2
        half_size = crop_size // 2
        
        x1 = max(0, center_x - half_size)
        y1 = max(0, center_y - half_size)
        x2 = min(width, center_x + half_size)
        y2 = min(height, center_y + half_size)
        
        cropped = image[y1:y2, x1:x2]
        return cropped 