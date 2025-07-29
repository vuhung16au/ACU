"""
Image Filtering component for OpenCV operations.
"""

import cv2
import numpy as np
import streamlit as st
from typing import Optional
from .base import BaseComponent, OperationMixin


class ImageFilteringComponent(BaseComponent, OperationMixin):
    """Component for image filtering operations."""
    
    def __init__(self):
        super().__init__("Image Filtering", "Apply various filters and edge detection")
    
    def render(self, image: np.ndarray) -> None:
        """Render the image filtering section."""
        st.header("🔍 Image Filtering")
        
        if not self.validate_input(image):
            self.show_warning("Please upload an image to start experimenting with image filtering.")
            return
        
        # Gaussian Blur
        self._render_gaussian_blur_section(image)
        st.divider()
        
        # Edge Detection
        self._render_edge_detection_section(image)
        st.divider()
        
        # Noise Reduction
        self._render_noise_reduction_section(image)
    
    def _render_gaussian_blur_section(self, image: np.ndarray) -> None:
        """Render Gaussian blur section."""
        st.subheader("🌫️ Gaussian Blur")
        blur_col1, blur_col2 = st.columns(2)
        
        with blur_col1:
            kernel_size = st.slider("Kernel Size", 3, 31, 5, 2)
            sigma_x = st.slider("Sigma X", 0.1, 10.0, 1.0, 0.1)
            sigma_y = st.slider("Sigma Y", 0.1, 10.0, 1.0, 0.1)
            
            if st.button("Apply Gaussian Blur"):
                processed = self._apply_gaussian_blur(image, kernel_size, sigma_x, sigma_y)
                if processed is not None:
                    self.display_result(image, processed, "Original", "Gaussian Blur")
                    self.create_download(processed, "gaussian_blur.png", "Download Blurred Image")
    
    def _render_edge_detection_section(self, image: np.ndarray) -> None:
        """Render edge detection section."""
        st.subheader("🔍 Edge Detection")
        edge_col1, edge_col2 = st.columns(2)
        
        with edge_col1:
            edge_method = st.selectbox(
                "Edge Detection Method",
                ["Canny", "Sobel", "Laplacian"],
                help="Choose edge detection method"
            )
            
            if edge_method == "Canny":
                self._render_canny_edge_detection(image)
            elif edge_method == "Sobel":
                self._render_sobel_edge_detection(image)
            elif edge_method == "Laplacian":
                self._render_laplacian_edge_detection(image)
    
    def _render_canny_edge_detection(self, image: np.ndarray) -> None:
        """Render Canny edge detection controls."""
        threshold1 = st.slider("Threshold 1", 0, 255, 100, 5)
        threshold2 = st.slider("Threshold 2", 0, 255, 200, 5)
        aperture_size = st.selectbox("Aperture Size", [3, 5, 7], index=0)
        
        if st.button("Apply Canny Edge Detection"):
            processed = self._apply_canny_edge_detection(image, threshold1, threshold2, aperture_size)
            if processed is not None:
                self.display_result(image, processed, "Original", "Canny Edge Detection")
                self.create_download(processed, "canny_edges.png", "Download Edge Image")
    
    def _render_sobel_edge_detection(self, image: np.ndarray) -> None:
        """Render Sobel edge detection controls."""
        dx = st.slider("dx", 0, 2, 1)
        dy = st.slider("dy", 0, 2, 1)
        ksize = st.selectbox("Kernel Size", [3, 5, 7], index=0)
        
        if st.button("Apply Sobel Edge Detection"):
            processed = self._apply_sobel_edge_detection(image, dx, dy, ksize)
            if processed is not None:
                self.display_result(image, processed, "Original", "Sobel Edge Detection")
                self.create_download(processed, "sobel_edges.png", "Download Edge Image")
    
    def _render_laplacian_edge_detection(self, image: np.ndarray) -> None:
        """Render Laplacian edge detection controls."""
        ksize = st.selectbox("Kernel Size", [1, 3, 5], index=1)
        
        if st.button("Apply Laplacian Edge Detection"):
            processed = self._apply_laplacian_edge_detection(image, ksize)
            if processed is not None:
                self.display_result(image, processed, "Original", "Laplacian Edge Detection")
                self.create_download(processed, "laplacian_edges.png", "Download Edge Image")
    
    def _render_noise_reduction_section(self, image: np.ndarray) -> None:
        """Render noise reduction section."""
        st.subheader("🔍 Noise Reduction")
        noise_col1, noise_col2 = st.columns(2)
        
        with noise_col1:
            noise_method = st.selectbox(
                "Noise Reduction Method",
                ["Bilateral Filter", "Median Filter", "Gaussian Filter"],
                help="Choose noise reduction method"
            )
            
            if noise_method == "Bilateral Filter":
                self._render_bilateral_filter(image)
            elif noise_method == "Median Filter":
                self._render_median_filter(image)
            elif noise_method == "Gaussian Filter":
                self._render_gaussian_filter(image)
    
    def _render_bilateral_filter(self, image: np.ndarray) -> None:
        """Render bilateral filter controls."""
        d = st.slider("Diameter", 5, 50, 15, 5)
        sigma_color = st.slider("Sigma Color", 10, 150, 75, 5)
        sigma_space = st.slider("Sigma Space", 10, 150, 75, 5)
        
        if st.button("Apply Bilateral Filter"):
            processed = self._apply_bilateral_filter(image, d, sigma_color, sigma_space)
            if processed is not None:
                self.display_result(image, processed, "Original", "Bilateral Filter")
                self.create_download(processed, "bilateral_filter.png", "Download Filtered Image")
    
    def _render_median_filter(self, image: np.ndarray) -> None:
        """Render median filter controls."""
        kernel_size = st.slider("Kernel Size", 3, 15, 5, 2)
        
        if st.button("Apply Median Filter"):
            processed = self._apply_median_filter(image, kernel_size)
            if processed is not None:
                self.display_result(image, processed, "Original", "Median Filter")
                self.create_download(processed, "median_filter.png", "Download Filtered Image")
    
    def _render_gaussian_filter(self, image: np.ndarray) -> None:
        """Render Gaussian filter controls."""
        kernel_size = st.slider("Kernel Size", 3, 31, 5, 2)
        sigma = st.slider("Sigma", 0.1, 10.0, 1.0, 0.1)
        
        if st.button("Apply Gaussian Filter"):
            processed = self._apply_gaussian_filter(image, kernel_size, sigma)
            if processed is not None:
                self.display_result(image, processed, "Original", "Gaussian Filter")
                self.create_download(processed, "gaussian_filter.png", "Download Filtered Image")
    
    # Helper methods for filtering operations
    @OperationMixin.safe_operation
    def _apply_gaussian_blur(self, image: np.ndarray, kernel_size: int, sigma_x: float, sigma_y: float) -> Optional[np.ndarray]:
        """Apply Gaussian blur to image."""
        kernel_size = self.ensure_odd_kernel(kernel_size)
        blurred = cv2.GaussianBlur(image, (kernel_size, kernel_size), sigma_x, sigma_y)
        return blurred
    
    @OperationMixin.safe_operation
    def _apply_canny_edge_detection(self, image: np.ndarray, threshold1: int, threshold2: int, aperture_size: int) -> Optional[np.ndarray]:
        """Apply Canny edge detection."""
        gray = self.convert_to_grayscale(image)
        edges = cv2.Canny(gray, threshold1, threshold2, apertureSize=aperture_size)
        return self.convert_to_bgr(edges)
    
    @OperationMixin.safe_operation
    def _apply_sobel_edge_detection(self, image: np.ndarray, dx: int, dy: int, ksize: int) -> Optional[np.ndarray]:
        """Apply Sobel edge detection."""
        gray = self.convert_to_grayscale(image)
        sobel = cv2.Sobel(gray, cv2.CV_64F, dx, dy, ksize=ksize)
        sobel = np.absolute(sobel)
        sobel = np.uint8(sobel)
        return self.convert_to_bgr(sobel)
    
    @OperationMixin.safe_operation
    def _apply_laplacian_edge_detection(self, image: np.ndarray, ksize: int) -> Optional[np.ndarray]:
        """Apply Laplacian edge detection."""
        gray = self.convert_to_grayscale(image)
        laplacian = cv2.Laplacian(gray, cv2.CV_64F, ksize=ksize)
        laplacian = np.absolute(laplacian)
        laplacian = np.uint8(laplacian)
        return self.convert_to_bgr(laplacian)
    
    @OperationMixin.safe_operation
    def _apply_bilateral_filter(self, image: np.ndarray, d: int, sigma_color: int, sigma_space: int) -> Optional[np.ndarray]:
        """Apply bilateral filter for noise reduction."""
        filtered = cv2.bilateralFilter(image, d, sigma_color, sigma_space)
        return filtered
    
    @OperationMixin.safe_operation
    def _apply_median_filter(self, image: np.ndarray, kernel_size: int) -> Optional[np.ndarray]:
        """Apply median filter for noise reduction."""
        kernel_size = self.ensure_odd_kernel(kernel_size)
        filtered = cv2.medianBlur(image, kernel_size)
        return filtered
    
    @OperationMixin.safe_operation
    def _apply_gaussian_filter(self, image: np.ndarray, kernel_size: int, sigma: float) -> Optional[np.ndarray]:
        """Apply Gaussian filter for noise reduction."""
        kernel_size = self.ensure_odd_kernel(kernel_size)
        filtered = cv2.GaussianBlur(image, (kernel_size, kernel_size), sigma)
        return filtered 