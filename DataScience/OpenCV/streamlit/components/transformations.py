"""
Transformations component for OpenCV operations.
"""

import cv2
import numpy as np
import streamlit as st
from typing import Optional
from .base import BaseComponent, OperationMixin


class TransformationsComponent(BaseComponent, OperationMixin):
    """Component for image transformations."""
    
    def __init__(self):
        super().__init__("Transformations", "Apply affine and perspective transformations")
    
    def render(self, image: np.ndarray) -> None:
        """Render the transformations section."""
        st.header("🔄 Transformations")
        
        if not self.validate_input(image):
            self.show_warning("Please upload an image to start experimenting with transformations.")
            return
        
        # Affine Transformations
        self._render_affine_transformations(image)
        st.divider()
        
        # Perspective Transformations
        self._render_perspective_transformations(image)
        st.divider()
        
        # Image Warping
        self._render_image_warping(image)
    
    def _render_affine_transformations(self, image: np.ndarray) -> None:
        """Render affine transformations section."""
        st.subheader("📐 Affine Transformations")
        affine_col1, affine_col2 = st.columns(2)
        
        with affine_col1:
            transform_type = st.selectbox(
                "Transform Type",
                ["Translation", "Rotation", "Scaling", "Shearing"],
                help="Choose transformation type"
            )
            
            if transform_type == "Translation":
                self._render_translation_controls(image)
            elif transform_type == "Rotation":
                self._render_rotation_controls(image)
            elif transform_type == "Scaling":
                self._render_scaling_controls(image)
            elif transform_type == "Shearing":
                self._render_shearing_controls(image)
    
    def _render_translation_controls(self, image: np.ndarray) -> None:
        """Render translation controls."""
        tx = st.slider("Translate X", -200, 200, 0, 10)
        ty = st.slider("Translate Y", -200, 200, 0, 10)
        
        if st.button("Apply Translation"):
            processed = self._apply_translation(image, tx, ty)
            if processed is not None:
                self.display_result(image, processed, "Original", "Translated")
                self.create_download(processed, "translated_image.png", "Download Translated Image")
    
    def _render_rotation_controls(self, image: np.ndarray) -> None:
        """Render rotation controls."""
        angle = st.slider("Rotation Angle", -180, 180, 0, 1)
        scale = st.slider("Scale", 0.1, 2.0, 1.0, 0.1)
        
        if st.button("Apply Rotation"):
            processed = self._apply_rotation(image, angle, scale)
            if processed is not None:
                self.display_result(image, processed, "Original", "Rotated")
                self.create_download(processed, "rotated_image.png", "Download Rotated Image")
    
    def _render_scaling_controls(self, image: np.ndarray) -> None:
        """Render scaling controls."""
        scale_x = st.slider("Scale X", 0.1, 3.0, 1.0, 0.1)
        scale_y = st.slider("Scale Y", 0.1, 3.0, 1.0, 0.1)
        
        if st.button("Apply Scaling"):
            processed = self._apply_scaling(image, scale_x, scale_y)
            if processed is not None:
                self.display_result(image, processed, "Original", "Scaled")
                self.create_download(processed, "scaled_image.png", "Download Scaled Image")
    
    def _render_shearing_controls(self, image: np.ndarray) -> None:
        """Render shearing controls."""
        shear_x = st.slider("Shear X", -1.0, 1.0, 0.0, 0.1)
        shear_y = st.slider("Shear Y", -1.0, 1.0, 0.0, 0.1)
        
        if st.button("Apply Shearing"):
            processed = self._apply_shearing(image, shear_x, shear_y)
            if processed is not None:
                self.display_result(image, processed, "Original", "Sheared")
                self.create_download(processed, "sheared_image.png", "Download Sheared Image")
    
    def _render_perspective_transformations(self, image: np.ndarray) -> None:
        """Render perspective transformations section."""
        st.subheader("🔲 Perspective Transformations")
        perspective_col1, perspective_col2 = st.columns(2)
        
        with perspective_col1:
            perspective_type = st.selectbox(
                "Perspective Type",
                ["Custom Points", "Predefined"],
                help="Choose perspective transformation type"
            )
            
            if perspective_type == "Custom Points":
                st.info("Click on the image to set perspective points (coming soon)")
            
            elif perspective_type == "Predefined":
                preset = st.selectbox(
                    "Preset",
                    ["Bird's Eye View", "Worm's Eye View", "Left Tilt", "Right Tilt"],
                    help="Choose a predefined perspective"
                )
                
                if st.button("Apply Perspective Transform"):
                    processed = self._apply_perspective_preset(image, preset)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "Perspective Transform")
                        self.create_download(processed, "perspective_image.png", "Download Perspective Image")
    
    def _render_image_warping(self, image: np.ndarray) -> None:
        """Render image warping section."""
        st.subheader("🌀 Image Warping")
        warp_col1, warp_col2 = st.columns(2)
        
        with warp_col1:
            warp_type = st.selectbox(
                "Warp Type",
                ["Spherical", "Cylindrical", "Fish Eye"],
                help="Choose warping type"
            )
            
            if warp_type == "Spherical":
                self._render_spherical_warp_controls(image)
            elif warp_type == "Cylindrical":
                self._render_cylindrical_warp_controls(image)
            elif warp_type == "Fish Eye":
                self._render_fish_eye_warp_controls(image)
    
    def _render_spherical_warp_controls(self, image: np.ndarray) -> None:
        """Render spherical warp controls."""
        radius = st.slider("Radius", 100, 500, 200, 10)
        
        if st.button("Apply Spherical Warp"):
            processed = self._apply_spherical_warp(image, radius)
            if processed is not None:
                self.display_result(image, processed, "Original", "Spherical Warp")
                self.create_download(processed, "spherical_warp.png", "Download Warped Image")
    
    def _render_cylindrical_warp_controls(self, image: np.ndarray) -> None:
        """Render cylindrical warp controls."""
        radius = st.slider("Radius", 100, 500, 200, 10)
        
        if st.button("Apply Cylindrical Warp"):
            processed = self._apply_cylindrical_warp(image, radius)
            if processed is not None:
                self.display_result(image, processed, "Original", "Cylindrical Warp")
                self.create_download(processed, "cylindrical_warp.png", "Download Warped Image")
    
    def _render_fish_eye_warp_controls(self, image: np.ndarray) -> None:
        """Render fish eye warp controls."""
        strength = st.slider("Fish Eye Strength", 0.1, 2.0, 0.5, 0.1)
        
        if st.button("Apply Fish Eye Warp"):
            processed = self._apply_fish_eye_warp(image, strength)
            if processed is not None:
                self.display_result(image, processed, "Original", "Fish Eye Warp")
                self.create_download(processed, "fish_eye_warp.png", "Download Warped Image")
    
    # Helper methods for transformations
    @OperationMixin.safe_operation
    def _apply_translation(self, image: np.ndarray, tx: int, ty: int) -> Optional[np.ndarray]:
        """Apply translation transformation."""
        height, width = image.shape[:2]
        translation_matrix = np.float32([[1, 0, tx], [0, 1, ty]])
        translated = cv2.warpAffine(image, translation_matrix, (width, height))
        return translated
    
    @OperationMixin.safe_operation
    def _apply_rotation(self, image: np.ndarray, angle: float, scale: float) -> Optional[np.ndarray]:
        """Apply rotation transformation."""
        height, width = image.shape[:2]
        center = (width // 2, height // 2)
        rotation_matrix = cv2.getRotationMatrix2D(center, angle, scale)
        rotated = cv2.warpAffine(image, rotation_matrix, (width, height))
        return rotated
    
    @OperationMixin.safe_operation
    def _apply_scaling(self, image: np.ndarray, scale_x: float, scale_y: float) -> Optional[np.ndarray]:
        """Apply scaling transformation."""
        height, width = image.shape[:2]
        new_width = int(width * scale_x)
        new_height = int(height * scale_y)
        scaled = cv2.resize(image, (new_width, new_height))
        return scaled
    
    @OperationMixin.safe_operation
    def _apply_shearing(self, image: np.ndarray, shear_x: float, shear_y: float) -> Optional[np.ndarray]:
        """Apply shearing transformation."""
        height, width = image.shape[:2]
        shear_matrix = np.float32([[1, shear_x, 0], [shear_y, 1, 0]])
        sheared = cv2.warpAffine(image, shear_matrix, (width, height))
        return sheared
    
    @OperationMixin.safe_operation
    def _apply_perspective_preset(self, image: np.ndarray, preset: str) -> Optional[np.ndarray]:
        """Apply predefined perspective transformation."""
        height, width = image.shape[:2]
        
        if preset == "Bird's Eye View":
            # Top-down view
            src_points = np.float32([[0, 0], [width, 0], [0, height], [width, height]])
            dst_points = np.float32([[width*0.1, height*0.1], [width*0.9, height*0.1], 
                                   [width*0.2, height*0.9], [width*0.8, height*0.9]])
        
        elif preset == "Worm's Eye View":
            # Bottom-up view
            src_points = np.float32([[0, 0], [width, 0], [0, height], [width, height]])
            dst_points = np.float32([[width*0.2, height*0.9], [width*0.8, height*0.9], 
                                   [width*0.1, height*0.1], [width*0.9, height*0.1]])
        
        elif preset == "Left Tilt":
            # Tilt to the left
            src_points = np.float32([[0, 0], [width, 0], [0, height], [width, height]])
            dst_points = np.float32([[width*0.2, 0], [width*0.8, height*0.1], 
                                   [width*0.1, height*0.9], [width*0.9, height]])
        
        else:  # Right Tilt
            # Tilt to the right
            src_points = np.float32([[0, 0], [width, 0], [0, height], [width, height]])
            dst_points = np.float32([[width*0.1, height*0.1], [width*0.9, 0], 
                                   [width*0.2, height], [width*0.8, height*0.9]])
        
        perspective_matrix = cv2.getPerspectiveTransform(src_points, dst_points)
        transformed = cv2.warpPerspective(image, perspective_matrix, (width, height))
        return transformed
    
    @OperationMixin.safe_operation
    def _apply_spherical_warp(self, image: np.ndarray, radius: int) -> Optional[np.ndarray]:
        """Apply spherical warping effect."""
        height, width = image.shape[:2]
        center_x, center_y = width // 2, height // 2
        
        # Create meshgrid
        x, y = np.meshgrid(np.arange(width), np.arange(height))
        
        # Calculate spherical coordinates
        dx = x - center_x
        dy = y - center_y
        distance = np.sqrt(dx**2 + dy**2)
        
        # Apply spherical distortion
        angle = np.arctan2(dy, dx)
        new_distance = distance * (1 + distance / radius)
        
        new_x = center_x + new_distance * np.cos(angle)
        new_y = center_y + new_distance * np.sin(angle)
        
        # Remap image
        map_x = new_x.astype(np.float32)
        map_y = new_y.astype(np.float32)
        
        warped = cv2.remap(image, map_x, map_y, cv2.INTER_LINEAR)
        return warped
    
    @OperationMixin.safe_operation
    def _apply_cylindrical_warp(self, image: np.ndarray, radius: int) -> Optional[np.ndarray]:
        """Apply cylindrical warping effect."""
        height, width = image.shape[:2]
        center_x, center_y = width // 2, height // 2
        
        # Create meshgrid
        x, y = np.meshgrid(np.arange(width), np.arange(height))
        
        # Calculate cylindrical coordinates
        dx = x - center_x
        angle = np.arctan2(dx, radius)
        
        new_x = center_x + radius * np.sin(angle)
        new_y = y
        
        # Remap image
        map_x = new_x.astype(np.float32)
        map_y = new_y.astype(np.float32)
        
        warped = cv2.remap(image, map_x, map_y, cv2.INTER_LINEAR)
        return warped
    
    @OperationMixin.safe_operation
    def _apply_fish_eye_warp(self, image: np.ndarray, strength: float) -> Optional[np.ndarray]:
        """Apply fish eye warping effect."""
        height, width = image.shape[:2]
        center_x, center_y = width // 2, height // 2
        
        # Create meshgrid
        x, y = np.meshgrid(np.arange(width), np.arange(height))
        
        # Calculate fish eye distortion
        dx = x - center_x
        dy = y - center_y
        distance = np.sqrt(dx**2 + dy**2)
        
        # Normalize distance
        max_distance = np.sqrt(center_x**2 + center_y**2)
        normalized_distance = distance / max_distance
        
        # Apply fish eye distortion
        distortion = 1 + strength * normalized_distance**2
        new_distance = distance * distortion
        
        # Calculate new coordinates
        angle = np.arctan2(dy, dx)
        new_x = center_x + new_distance * np.cos(angle)
        new_y = center_y + new_distance * np.sin(angle)
        
        # Remap image
        map_x = new_x.astype(np.float32)
        map_y = new_y.astype(np.float32)
        
        warped = cv2.remap(image, map_x, map_y, cv2.INTER_LINEAR)
        return warped 