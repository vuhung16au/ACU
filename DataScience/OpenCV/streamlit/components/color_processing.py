"""
Color Processing component for OpenCV operations.
"""

import cv2
import numpy as np
import streamlit as st
from typing import Optional
from .base import BaseComponent, OperationMixin


class ColorProcessingComponent(BaseComponent, OperationMixin):
    """Component for color processing operations."""
    
    def __init__(self):
        super().__init__("Color Processing", "Process and enhance image colors")
    
    def render(self, image: np.ndarray) -> None:
        """Render the color processing section."""
        st.header("🎨 Color Processing")
        
        if not self.validate_input(image):
            self.show_warning("Please upload an image to start experimenting with color processing.")
            return
        
        # Color Space Conversion
        self._render_color_space_conversion(image)
        st.divider()
        
        # Histogram Equalization
        self._render_histogram_equalization(image)
        st.divider()
        
        # Color Enhancement
        self._render_color_enhancement(image)
        st.divider()
        
        # Color Segmentation
        self._render_color_segmentation(image)
    
    def _render_color_space_conversion(self, image: np.ndarray) -> None:
        """Render color space conversion section."""
        st.subheader("🌈 Color Space Conversion")
        colorspace_col1, colorspace_col2 = st.columns(2)
        
        with colorspace_col1:
            target_colorspace = st.selectbox(
                "Target Color Space",
                ["HSV", "LAB", "YUV", "XYZ", "Grayscale"],
                help="Choose target color space"
            )
            
            if st.button("Convert Color Space"):
                processed = self._convert_colorspace(image, target_colorspace)
                if processed is not None:
                    self.display_result(image, processed, "Original", f"{target_colorspace} Color Space")
                    self.create_download(processed, f"{target_colorspace.lower()}_colorspace.png", f"Download {target_colorspace} Image")
    
    def _render_histogram_equalization(self, image: np.ndarray) -> None:
        """Render histogram equalization section."""
        st.subheader("📊 Histogram Equalization")
        histogram_col1, histogram_col2 = st.columns(2)
        
        with histogram_col1:
            equalization_method = st.selectbox(
                "Equalization Method",
                ["Global", "CLAHE", "Adaptive"],
                help="Choose histogram equalization method"
            )
            
            if equalization_method == "Global":
                if st.button("Apply Global Equalization"):
                    processed = self._apply_global_equalization(image)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "Global Equalization")
                        self.create_download(processed, "global_equalization.png", "Download Global Equalization Image")
            
            elif equalization_method == "CLAHE":
                clip_limit = st.slider("Clip Limit", 1.0, 10.0, 2.0, 0.5)
                tile_grid_size = st.slider("Tile Grid Size", 2, 16, 8, 2)
                
                if st.button("Apply CLAHE"):
                    processed = self._apply_clahe_equalization(image, clip_limit, tile_grid_size)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "CLAHE Equalization")
                        self.create_download(processed, "clahe_equalization.png", "Download CLAHE Equalization Image")
            
            elif equalization_method == "Adaptive":
                if st.button("Apply Adaptive Equalization"):
                    processed = self._apply_adaptive_equalization(image)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "Adaptive Equalization")
                        self.create_download(processed, "adaptive_equalization.png", "Download Adaptive Equalization Image")
    
    def _render_color_enhancement(self, image: np.ndarray) -> None:
        """Render color enhancement section."""
        st.subheader("✨ Color Enhancement")
        enhancement_col1, enhancement_col2 = st.columns(2)
        
        with enhancement_col1:
            enhancement_method = st.selectbox(
                "Enhancement Method",
                ["Brightness", "Contrast", "Saturation", "Gamma Correction", "Color Balance"],
                help="Choose color enhancement method"
            )
            
            if enhancement_method == "Brightness":
                self._render_brightness_controls(image)
            elif enhancement_method == "Contrast":
                self._render_contrast_controls(image)
            elif enhancement_method == "Saturation":
                self._render_saturation_controls(image)
            elif enhancement_method == "Gamma Correction":
                self._render_gamma_correction_controls(image)
            elif enhancement_method == "Color Balance":
                self._render_color_balance_controls(image)
    
    def _render_brightness_controls(self, image: np.ndarray) -> None:
        """Render brightness controls."""
        brightness_factor = st.slider("Brightness Factor", -100, 100, 0, 5)
        
        if st.button("Apply Brightness Adjustment"):
            processed = self._adjust_brightness(image, brightness_factor)
            if processed is not None:
                self.display_result(image, processed, "Original", "Brightness Adjusted")
                self.create_download(processed, "brightness_adjusted.png", "Download Brightness Adjusted Image")
    
    def _render_contrast_controls(self, image: np.ndarray) -> None:
        """Render contrast controls."""
        contrast_factor = st.slider("Contrast Factor", 0.1, 3.0, 1.0, 0.1)
        
        if st.button("Apply Contrast Adjustment"):
            processed = self._adjust_contrast(image, contrast_factor)
            if processed is not None:
                self.display_result(image, processed, "Original", "Contrast Adjusted")
                self.create_download(processed, "contrast_adjusted.png", "Download Contrast Adjusted Image")
    
    def _render_saturation_controls(self, image: np.ndarray) -> None:
        """Render saturation controls."""
        saturation_factor = st.slider("Saturation Factor", 0.0, 3.0, 1.0, 0.1)
        
        if st.button("Apply Saturation Adjustment"):
            processed = self._adjust_saturation(image, saturation_factor)
            if processed is not None:
                self.display_result(image, processed, "Original", "Saturation Adjusted")
                self.create_download(processed, "saturation_adjusted.png", "Download Saturation Adjusted Image")
    
    def _render_gamma_correction_controls(self, image: np.ndarray) -> None:
        """Render gamma correction controls."""
        gamma = st.slider("Gamma", 0.1, 3.0, 1.0, 0.1)
        
        if st.button("Apply Gamma Correction"):
            processed = self._apply_gamma_correction(image, gamma)
            if processed is not None:
                self.display_result(image, processed, "Original", "Gamma Corrected")
                self.create_download(processed, "gamma_corrected.png", "Download Gamma Corrected Image")
    
    def _render_color_balance_controls(self, image: np.ndarray) -> None:
        """Render color balance controls."""
        red_factor = st.slider("Red Factor", 0.0, 2.0, 1.0, 0.1)
        green_factor = st.slider("Green Factor", 0.0, 2.0, 1.0, 0.1)
        blue_factor = st.slider("Blue Factor", 0.0, 2.0, 1.0, 0.1)
        
        if st.button("Apply Color Balance"):
            processed = self._apply_color_balance(image, red_factor, green_factor, blue_factor)
            if processed is not None:
                self.display_result(image, processed, "Original", "Color Balanced")
                self.create_download(processed, "color_balanced.png", "Download Color Balanced Image")
    
    def _render_color_segmentation(self, image: np.ndarray) -> None:
        """Render color segmentation section."""
        st.subheader("🎯 Color Segmentation")
        segmentation_col1, segmentation_col2 = st.columns(2)
        
        with segmentation_col1:
            segmentation_method = st.selectbox(
                "Segmentation Method",
                ["HSV Range", "K-Means", "Mean Shift", "GrabCut"],
                help="Choose color segmentation method"
            )
            
            if segmentation_method == "HSV Range":
                self._render_hsv_segmentation_controls(image)
            elif segmentation_method == "K-Means":
                self._render_kmeans_segmentation_controls(image)
            elif segmentation_method == "Mean Shift":
                self._render_mean_shift_segmentation_controls(image)
            elif segmentation_method == "GrabCut":
                if st.button("Apply GrabCut Segmentation"):
                    processed = self._apply_grabcut_segmentation(image)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "GrabCut Segmented")
                        self.create_download(processed, "grabcut_segmented.png", "Download GrabCut Segmented Image")
    
    def _render_hsv_segmentation_controls(self, image: np.ndarray) -> None:
        """Render HSV segmentation controls."""
        h_min = st.slider("Hue Min", 0, 179, 0, 1)
        h_max = st.slider("Hue Max", 0, 179, 179, 1)
        s_min = st.slider("Saturation Min", 0, 255, 0, 5)
        s_max = st.slider("Saturation Max", 0, 255, 255, 5)
        v_min = st.slider("Value Min", 0, 255, 0, 5)
        v_max = st.slider("Value Max", 0, 255, 255, 5)
        
        if st.button("Apply HSV Segmentation"):
            processed = self._apply_hsv_segmentation(image, h_min, h_max, s_min, s_max, v_min, v_max)
            if processed is not None:
                self.display_result(image, processed, "Original", "HSV Segmented")
                self.create_download(processed, "hsv_segmented.png", "Download HSV Segmented Image")
    
    def _render_kmeans_segmentation_controls(self, image: np.ndarray) -> None:
        """Render K-means segmentation controls."""
        k_clusters = st.slider("Number of Clusters", 2, 10, 3, 1)
        
        if st.button("Apply K-Means Segmentation"):
            processed = self._apply_kmeans_segmentation(image, k_clusters)
            if processed is not None:
                self.display_result(image, processed, "Original", "K-Means Segmented")
                self.create_download(processed, "kmeans_segmented.png", "Download K-Means Segmented Image")
    
    def _render_mean_shift_segmentation_controls(self, image: np.ndarray) -> None:
        """Render mean shift segmentation controls."""
        spatial_radius = st.slider("Spatial Radius", 10, 50, 20, 5)
        color_radius = st.slider("Color Radius", 10, 50, 20, 5)
        
        if st.button("Apply Mean Shift Segmentation"):
            processed = self._apply_mean_shift_segmentation(image, spatial_radius, color_radius)
            if processed is not None:
                self.display_result(image, processed, "Original", "Mean Shift Segmented")
                self.create_download(processed, "mean_shift_segmented.png", "Download Mean Shift Segmented Image")
    
    # Helper methods for color processing
    @OperationMixin.safe_operation
    def _convert_colorspace(self, image: np.ndarray, target_colorspace: str) -> Optional[np.ndarray]:
        """Convert image to target color space."""
        if target_colorspace == "HSV":
            converted = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)
        elif target_colorspace == "LAB":
            converted = cv2.cvtColor(image, cv2.COLOR_BGR2LAB)
        elif target_colorspace == "YUV":
            converted = cv2.cvtColor(image, cv2.COLOR_BGR2YUV)
        elif target_colorspace == "XYZ":
            converted = cv2.cvtColor(image, cv2.COLOR_BGR2XYZ)
        elif target_colorspace == "Grayscale":
            converted = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
            # Convert back to BGR for display
            converted = cv2.cvtColor(converted, cv2.COLOR_GRAY2BGR)
        else:
            st.error(f"Unknown color space: {target_colorspace}")
            return None
        
        return converted
    
    @OperationMixin.safe_operation
    def _apply_global_equalization(self, image: np.ndarray) -> Optional[np.ndarray]:
        """Apply global histogram equalization."""
        # Convert to YUV for better equalization
        yuv = cv2.cvtColor(image, cv2.COLOR_BGR2YUV)
        
        # Equalize the Y channel
        yuv[:,:,0] = cv2.equalizeHist(yuv[:,:,0])
        
        # Convert back to BGR
        equalized = cv2.cvtColor(yuv, cv2.COLOR_YUV2BGR)
        
        return equalized
    
    @OperationMixin.safe_operation
    def _apply_clahe_equalization(self, image: np.ndarray, clip_limit: float, tile_grid_size: int) -> Optional[np.ndarray]:
        """Apply CLAHE histogram equalization."""
        # Convert to LAB for better equalization
        lab = cv2.cvtColor(image, cv2.COLOR_BGR2LAB)
        
        # Apply CLAHE to L channel
        clahe = cv2.createCLAHE(clipLimit=clip_limit, tileGridSize=(tile_grid_size, tile_grid_size))
        lab[:,:,0] = clahe.apply(lab[:,:,0])
        
        # Convert back to BGR
        equalized = cv2.cvtColor(lab, cv2.COLOR_LAB2BGR)
        
        return equalized
    
    @OperationMixin.safe_operation
    def _apply_adaptive_equalization(self, image: np.ndarray) -> Optional[np.ndarray]:
        """Apply adaptive histogram equalization."""
        # Convert to grayscale
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        
        # Apply adaptive equalization
        clahe = cv2.createCLAHE(clipLimit=2.0, tileGridSize=(8, 8))
        equalized = clahe.apply(gray)
        
        # Convert back to BGR
        result = cv2.cvtColor(equalized, cv2.COLOR_GRAY2BGR)
        
        return result
    
    @OperationMixin.safe_operation
    def _adjust_brightness(self, image: np.ndarray, factor: int) -> Optional[np.ndarray]:
        """Adjust image brightness."""
        # Convert to HSV
        hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)
        
        # Adjust V channel
        hsv[:,:,2] = cv2.add(hsv[:,:,2], factor)
        
        # Ensure values are in valid range
        hsv[:,:,2] = np.clip(hsv[:,:,2], 0, 255)
        
        # Convert back to BGR
        result = cv2.cvtColor(hsv, cv2.COLOR_HSV2BGR)
        
        return result
    
    @OperationMixin.safe_operation
    def _adjust_contrast(self, image: np.ndarray, factor: float) -> Optional[np.ndarray]:
        """Adjust image contrast."""
        # Apply contrast adjustment
        result = cv2.convertScaleAbs(image, alpha=factor, beta=0)
        
        return result
    
    @OperationMixin.safe_operation
    def _adjust_saturation(self, image: np.ndarray, factor: float) -> Optional[np.ndarray]:
        """Adjust image saturation."""
        # Convert to HSV
        hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)
        
        # Adjust S channel
        hsv[:,:,1] = cv2.multiply(hsv[:,:,1], factor)
        
        # Ensure values are in valid range
        hsv[:,:,1] = np.clip(hsv[:,:,1], 0, 255)
        
        # Convert back to BGR
        result = cv2.cvtColor(hsv, cv2.COLOR_HSV2BGR)
        
        return result
    
    @OperationMixin.safe_operation
    def _apply_gamma_correction(self, image: np.ndarray, gamma: float) -> Optional[np.ndarray]:
        """Apply gamma correction to image."""
        # Build lookup table
        inv_gamma = 1.0 / gamma
        table = np.array([((i / 255.0) ** inv_gamma) * 255 for i in np.arange(0, 256)]).astype("uint8")
        
        # Apply gamma correction
        result = cv2.LUT(image, table)
        
        return result
    
    @OperationMixin.safe_operation
    def _apply_color_balance(self, image: np.ndarray, red_factor: float, green_factor: float, blue_factor: float) -> Optional[np.ndarray]:
        """Apply color balance to image."""
        # Split channels
        b, g, r = cv2.split(image)
        
        # Apply factors
        b = cv2.multiply(b, blue_factor)
        g = cv2.multiply(g, green_factor)
        r = cv2.multiply(r, red_factor)
        
        # Ensure values are in valid range
        b = np.clip(b, 0, 255)
        g = np.clip(g, 0, 255)
        r = np.clip(r, 0, 255)
        
        # Merge channels
        result = cv2.merge([b, g, r])
        
        return result
    
    @OperationMixin.safe_operation
    def _apply_hsv_segmentation(self, image: np.ndarray, h_min: int, h_max: int, s_min: int, s_max: int, v_min: int, v_max: int) -> Optional[np.ndarray]:
        """Apply HSV-based color segmentation."""
        # Convert to HSV
        hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)
        
        # Create mask
        lower = np.array([h_min, s_min, v_min])
        upper = np.array([h_max, s_max, v_max])
        mask = cv2.inRange(hsv, lower, upper)
        
        # Apply mask
        result = cv2.bitwise_and(image, image, mask=mask)
        
        return result
    
    @OperationMixin.safe_operation
    def _apply_kmeans_segmentation(self, image: np.ndarray, k: int) -> Optional[np.ndarray]:
        """Apply K-means color segmentation."""
        # Reshape image for K-means
        pixel_values = image.reshape((-1, 3))
        pixel_values = np.float32(pixel_values)
        
        # Define criteria and apply K-means
        criteria = (cv2.TERM_CRITERIA_EPS + cv2.TERM_CRITERIA_MAX_ITER, 100, 0.2)
        _, labels, centers = cv2.kmeans(pixel_values, k, None, criteria, 10, cv2.KMEANS_RANDOM_CENTERS)
        
        # Convert back to uint8
        centers = np.uint8(centers)
        segmented = centers[labels.flatten()]
        segmented = segmented.reshape(image.shape)
        
        return segmented
    
    @OperationMixin.safe_operation
    def _apply_mean_shift_segmentation(self, image: np.ndarray, spatial_radius: int, color_radius: int) -> Optional[np.ndarray]:
        """Apply mean shift segmentation."""
        # Apply mean shift filtering
        shifted = cv2.pyrMeanShiftFiltering(image, spatial_radius, color_radius)
        
        return shifted
    
    @OperationMixin.safe_operation
    def _apply_grabcut_segmentation(self, image: np.ndarray) -> Optional[np.ndarray]:
        """Apply GrabCut segmentation."""
        # Create mask
        mask = np.zeros(image.shape[:2], np.uint8)
        
        # Create background and foreground models
        bgd_model = np.zeros((1, 65), np.float64)
        fgd_model = np.zeros((1, 65), np.float64)
        
        # Define rectangle (you can modify this for interactive selection)
        height, width = image.shape[:2]
        rect = (10, 10, width-20, height-20)
        
        # Apply GrabCut
        cv2.grabCut(image, mask, rect, bgd_model, fgd_model, 5, cv2.GC_INIT_WITH_RECT)
        
        # Create mask
        mask2 = np.where((mask == 2) | (mask == 0), 0, 1).astype('uint8')
        
        # Apply mask
        result = image * mask2[:,:,np.newaxis]
        
        return result 