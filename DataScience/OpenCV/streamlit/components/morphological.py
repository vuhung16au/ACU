"""
Morphological Operations component for OpenCV operations.
"""

import cv2
import numpy as np
import streamlit as st
from typing import Optional
from .base import BaseComponent, OperationMixin


class MorphologicalComponent(BaseComponent, OperationMixin):
    """Component for morphological operations."""
    
    def __init__(self):
        super().__init__("Morphological Operations", "Apply morphological operations to images")
    
    def render(self, image: np.ndarray) -> None:
        """Render the morphological operations section."""
        st.header("🔧 Morphological Operations")
        
        if not self.validate_input(image):
            self.show_warning("Please upload an image to start experimenting with morphological operations.")
            return
        
        # Convert to grayscale for morphological operations
        gray = self.convert_to_grayscale(image)
        
        # Basic Morphological Operations
        self._render_basic_morphological_operations(gray)
        st.divider()
        
        # Advanced Morphological Operations
        self._render_advanced_morphological_operations(gray)
        st.divider()
        
        # Custom Kernel Operations
        self._render_custom_kernel_operations(gray)
    
    def _render_basic_morphological_operations(self, gray: np.ndarray) -> None:
        """Render basic morphological operations section."""
        st.subheader("🔧 Basic Morphological Operations")
        basic_col1, basic_col2 = st.columns(2)
        
        with basic_col1:
            operation = st.selectbox(
                "Morphological Operation",
                ["Erosion", "Dilation", "Opening", "Closing", "Morphological Gradient", "Top Hat", "Black Hat"],
                help="Choose morphological operation"
            )
            
            kernel_size = st.slider("Kernel Size", 3, 21, 5, 2)
            iterations = st.slider("Iterations", 1, 10, 1, 1)
            
            kernel_shape = st.selectbox(
                "Kernel Shape",
                ["Rectangle", "Ellipse", "Cross"],
                help="Choose kernel shape"
            )
            
            if st.button("Apply Morphological Operation"):
                processed = self._apply_morphological_operation(gray, operation, kernel_size, iterations, kernel_shape)
                if processed is not None:
                    self.display_result(gray, processed, "Original", operation)
                    self.create_download(processed, f"{operation.lower().replace(' ', '_')}.png", f"Download {operation} Image")
    
    def _render_advanced_morphological_operations(self, gray: np.ndarray) -> None:
        """Render advanced morphological operations section."""
        st.subheader("⚡ Advanced Morphological Operations")
        advanced_col1, advanced_col2 = st.columns(2)
        
        with advanced_col1:
            advanced_operation = st.selectbox(
                "Advanced Operation",
                ["Skeletonization", "Distance Transform", "Watershed", "Connected Components"],
                help="Choose advanced morphological operation"
            )
            
            if advanced_operation == "Skeletonization":
                if st.button("Apply Skeletonization"):
                    processed = self._apply_skeletonization(gray)
                    if processed is not None:
                        self.display_result(gray, processed, "Original", "Skeletonization")
                        self.create_download(processed, "skeletonization.png", "Download Skeletonization Image")
            
            elif advanced_operation == "Distance Transform":
                distance_type = st.selectbox("Distance Type", ["Euclidean", "City Block", "Chessboard"])
                if st.button("Apply Distance Transform"):
                    processed = self._apply_distance_transform(gray, distance_type)
                    if processed is not None:
                        self.display_result(gray, processed, "Original", "Distance Transform")
                        self.create_download(processed, "distance_transform.png", "Download Distance Transform Image")
            
            elif advanced_operation == "Watershed":
                if st.button("Apply Watershed"):
                    processed = self._apply_watershed(gray)
                    if processed is not None:
                        self.display_result(gray, processed, "Original", "Watershed")
                        self.create_download(processed, "watershed.png", "Download Watershed Image")
            
            elif advanced_operation == "Connected Components":
                connectivity = st.selectbox("Connectivity", [4, 8])
                if st.button("Apply Connected Components"):
                    processed = self._apply_connected_components(gray, connectivity)
                    if processed is not None:
                        self.display_result(gray, processed, "Original", "Connected Components")
                        self.create_download(processed, "connected_components.png", "Download Connected Components Image")
    
    def _render_custom_kernel_operations(self, gray: np.ndarray) -> None:
        """Render custom kernel operations section."""
        st.subheader("🎛️ Custom Kernel Operations")
        custom_col1, custom_col2 = st.columns(2)
        
        with custom_col1:
            st.subheader("Create Custom Kernel")
            kernel_width = st.slider("Kernel Width", 3, 9, 3, 2)
            kernel_height = st.slider("Kernel Height", 3, 9, 3, 2)
            
            # Create a simple custom kernel (can be enhanced with user input)
            custom_kernel = np.ones((kernel_height, kernel_width), np.uint8)
            
            if st.button("Apply Custom Kernel"):
                processed = self._apply_custom_kernel(gray, custom_kernel)
                if processed is not None:
                    self.display_result(gray, processed, "Original", "Custom Kernel")
                    self.create_download(processed, "custom_kernel.png", "Download Custom Kernel Image")
    
    # Helper methods for morphological operations
    @OperationMixin.safe_operation
    def _apply_morphological_operation(self, image: np.ndarray, operation: str, kernel_size: int, iterations: int, kernel_shape: str) -> Optional[np.ndarray]:
        """Apply morphological operation to image."""
        # Create kernel
        if kernel_shape == "Rectangle":
            kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (kernel_size, kernel_size))
        elif kernel_shape == "Ellipse":
            kernel = cv2.getStructuringElement(cv2.MORPH_ELLIPSE, (kernel_size, kernel_size))
        else:  # Cross
            kernel = cv2.getStructuringElement(cv2.MORPH_CROSS, (kernel_size, kernel_size))
        
        # Apply operation
        if operation == "Erosion":
            result = cv2.erode(image, kernel, iterations=iterations)
        elif operation == "Dilation":
            result = cv2.dilate(image, kernel, iterations=iterations)
        elif operation == "Opening":
            result = cv2.morphologyEx(image, cv2.MORPH_OPEN, kernel, iterations=iterations)
        elif operation == "Closing":
            result = cv2.morphologyEx(image, cv2.MORPH_CLOSE, kernel, iterations=iterations)
        elif operation == "Morphological Gradient":
            result = cv2.morphologyEx(image, cv2.MORPH_GRADIENT, kernel, iterations=iterations)
        elif operation == "Top Hat":
            result = cv2.morphologyEx(image, cv2.MORPH_TOPHAT, kernel, iterations=iterations)
        elif operation == "Black Hat":
            result = cv2.morphologyEx(image, cv2.MORPH_BLACKHAT, kernel, iterations=iterations)
        else:
            st.error(f"Unknown operation: {operation}")
            return None
        
        return result
    
    @OperationMixin.safe_operation
    def _apply_skeletonization(self, image: np.ndarray) -> Optional[np.ndarray]:
        """Apply skeletonization to image."""
        # Threshold the image
        _, binary = cv2.threshold(image, 127, 255, cv2.THRESH_BINARY)
        
        # Skeletonization
        skeleton = np.zeros_like(binary)
        kernel = cv2.getStructuringElement(cv2.MORPH_CROSS, (3, 3))
        
        while True:
            # Erosion
            eroded = cv2.erode(binary, kernel)
            # Opening
            opened = cv2.morphologyEx(eroded, cv2.MORPH_OPEN, kernel)
            # Subtract
            temp = cv2.subtract(binary, opened)
            # Union
            skeleton = cv2.bitwise_or(skeleton, temp)
            binary = eroded.copy()
            
            if cv2.countNonZero(binary) == 0:
                break
        
        return skeleton
    
    @OperationMixin.safe_operation
    def _apply_distance_transform(self, image: np.ndarray, distance_type: str) -> Optional[np.ndarray]:
        """Apply distance transform to image."""
        # Threshold the image
        _, binary = cv2.threshold(image, 127, 255, cv2.THRESH_BINARY)
        
        # Distance transform
        if distance_type == "Euclidean":
            dist = cv2.distanceTransform(binary, cv2.DIST_L2, 5)
        elif distance_type == "City Block":
            dist = cv2.distanceTransform(binary, cv2.DIST_L1, 5)
        else:  # Chessboard
            dist = cv2.distanceTransform(binary, cv2.DIST_C, 5)
        
        # Normalize
        dist = cv2.normalize(dist, None, 0, 255, cv2.NORM_MINMAX)
        dist = np.uint8(dist)
        
        return dist
    
    @OperationMixin.safe_operation
    def _apply_watershed(self, image: np.ndarray) -> Optional[np.ndarray]:
        """Apply watershed segmentation."""
        # Threshold the image
        _, binary = cv2.threshold(image, 127, 255, cv2.THRESH_BINARY)
        
        # Noise removal
        kernel = np.ones((3, 3), np.uint8)
        opening = cv2.morphologyEx(binary, cv2.MORPH_OPEN, kernel, iterations=2)
        
        # Sure background area
        sure_bg = cv2.dilate(opening, kernel, iterations=3)
        
        # Finding sure foreground area
        dist_transform = cv2.distanceTransform(opening, cv2.DIST_L2, 5)
        _, sure_fg = cv2.threshold(dist_transform, 0.7*dist_transform.max(), 255, 0)
        sure_fg = np.uint8(sure_fg)
        
        # Finding unknown region
        unknown = cv2.subtract(sure_bg, sure_fg)
        
        # Marker labelling
        _, markers = cv2.connectedComponents(sure_fg)
        markers = markers + 1
        markers[unknown == 255] = 0
        
        # Apply watershed
        markers = cv2.watershed(cv2.cvtColor(image, cv2.COLOR_GRAY2BGR), markers)
        
        # Create result image
        result = np.zeros_like(image)
        result[markers == -1] = 255
        
        return result
    
    @OperationMixin.safe_operation
    def _apply_connected_components(self, image: np.ndarray, connectivity: int) -> Optional[np.ndarray]:
        """Apply connected components analysis."""
        # Threshold the image
        _, binary = cv2.threshold(image, 127, 255, cv2.THRESH_BINARY)
        
        # Connected components
        num_labels, labels = cv2.connectedComponents(binary, connectivity=connectivity)
        
        # Create colored result
        result = np.zeros((image.shape[0], image.shape[1], 3), dtype=np.uint8)
        
        # Assign random colors to components
        for i in range(1, num_labels):
            mask = labels == i
            color = np.random.randint(0, 255, 3)
            result[mask] = color
        
        return result
    
    @OperationMixin.safe_operation
    def _apply_custom_kernel(self, image: np.ndarray, kernel: np.ndarray) -> Optional[np.ndarray]:
        """Apply custom kernel to image."""
        result = cv2.morphologyEx(image, cv2.MORPH_OPEN, kernel)
        return result 