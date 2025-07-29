"""
Advanced Techniques component for OpenCV operations.
"""

import cv2
import numpy as np
import streamlit as st
from typing import Optional
from .base import BaseComponent, OperationMixin


class AdvancedTechniquesComponent(BaseComponent, OperationMixin):
    """Component for advanced image processing techniques."""
    
    def __init__(self):
        super().__init__("Advanced Techniques", "Apply advanced image processing techniques")
    
    def render(self, image: np.ndarray) -> None:
        """Render the advanced techniques section."""
        st.header("⚡ Advanced Techniques")
        
        if not self.validate_input(image):
            self.show_warning("Please upload an image to start experimenting with advanced techniques.")
            return
        
        # Fourier Transform
        self._render_fourier_transform(image)
        st.divider()
        
        # Image Segmentation
        self._render_image_segmentation(image)
        st.divider()
        
        # Template Matching
        self._render_template_matching(image)
        st.divider()
        
        # Machine Learning Integration
        self._render_machine_learning(image)
    
    def _render_fourier_transform(self, image: np.ndarray) -> None:
        """Render Fourier transform section."""
        st.subheader("📊 Fourier Transform")
        fourier_col1, fourier_col2 = st.columns(2)
        
        with fourier_col1:
            fourier_operation = st.selectbox(
                "Fourier Operation",
                ["Magnitude Spectrum", "Phase Spectrum", "Low Pass Filter", "High Pass Filter", "Band Pass Filter"],
                help="Choose Fourier transform operation"
            )
            
            if fourier_operation == "Magnitude Spectrum":
                if st.button("Show Magnitude Spectrum"):
                    processed = self._show_magnitude_spectrum(image)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "Magnitude Spectrum")
                        self.create_download(processed, "magnitude_spectrum.png", "Download Magnitude Spectrum")
            
            elif fourier_operation == "Phase Spectrum":
                if st.button("Show Phase Spectrum"):
                    processed = self._show_phase_spectrum(image)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "Phase Spectrum")
                        self.create_download(processed, "phase_spectrum.png", "Download Phase Spectrum")
            
            elif fourier_operation == "Low Pass Filter":
                cutoff_radius = st.slider("Cutoff Radius", 10, 100, 30, 5)
                if st.button("Apply Low Pass Filter"):
                    processed = self._apply_low_pass_filter(image, cutoff_radius)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "Low Pass Filtered")
                        self.create_download(processed, "low_pass_filtered.png", "Download Low Pass Filtered Image")
            
            elif fourier_operation == "High Pass Filter":
                cutoff_radius = st.slider("Cutoff Radius", 10, 100, 30, 5)
                if st.button("Apply High Pass Filter"):
                    processed = self._apply_high_pass_filter(image, cutoff_radius)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "High Pass Filtered")
                        self.create_download(processed, "high_pass_filtered.png", "Download High Pass Filtered Image")
            
            elif fourier_operation == "Band Pass Filter":
                low_cutoff = st.slider("Low Cutoff", 5, 50, 10, 5)
                high_cutoff = st.slider("High Cutoff", 20, 100, 50, 5)
                if st.button("Apply Band Pass Filter"):
                    processed = self._apply_band_pass_filter(image, low_cutoff, high_cutoff)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "Band Pass Filtered")
                        self.create_download(processed, "band_pass_filtered.png", "Download Band Pass Filtered Image")
    
    def _render_image_segmentation(self, image: np.ndarray) -> None:
        """Render image segmentation section."""
        st.subheader("🎯 Image Segmentation")
        segmentation_col1, segmentation_col2 = st.columns(2)
        
        with segmentation_col1:
            segmentation_method = st.selectbox(
                "Segmentation Method",
                ["K-Means Clustering", "Mean Shift", "Watershed", "GrabCut", "SLIC Superpixels"],
                help="Choose image segmentation method"
            )
            
            if segmentation_method == "K-Means Clustering":
                k_clusters = st.slider("Number of Clusters", 2, 10, 3, 1)
                if st.button("Apply K-Means Segmentation"):
                    processed = self._apply_kmeans_segmentation_advanced(image, k_clusters)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "K-Means Segmented")
                        self.create_download(processed, "kmeans_segmented.png", "Download K-Means Segmented Image")
            
            elif segmentation_method == "Mean Shift":
                spatial_radius = st.slider("Spatial Radius", 10, 50, 20, 5)
                color_radius = st.slider("Color Radius", 10, 50, 20, 5)
                if st.button("Apply Mean Shift Segmentation"):
                    processed = self._apply_mean_shift_segmentation_advanced(image, spatial_radius, color_radius)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "Mean Shift Segmented")
                        self.create_download(processed, "mean_shift_segmented.png", "Download Mean Shift Segmented Image")
            
            elif segmentation_method == "Watershed":
                if st.button("Apply Watershed Segmentation"):
                    processed = self._apply_watershed_segmentation(image)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "Watershed Segmented")
                        self.create_download(processed, "watershed_segmented.png", "Download Watershed Segmented Image")
            
            elif segmentation_method == "GrabCut":
                if st.button("Apply GrabCut Segmentation"):
                    processed = self._apply_grabcut_segmentation_advanced(image)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "GrabCut Segmented")
                        self.create_download(processed, "grabcut_segmented.png", "Download GrabCut Segmented Image")
            
            elif segmentation_method == "SLIC Superpixels":
                num_segments = st.slider("Number of Segments", 50, 500, 100, 50)
                compactness = st.slider("Compactness", 1, 50, 10, 1)
                if st.button("Apply SLIC Superpixels"):
                    processed = self._apply_slic_superpixels(image, num_segments, compactness)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "SLIC Superpixels")
                        self.create_download(processed, "slic_superpixels.png", "Download SLIC Superpixels Image")
    
    def _render_template_matching(self, image: np.ndarray) -> None:
        """Render template matching section."""
        st.subheader("🔍 Template Matching")
        template_col1, template_col2 = st.columns(2)
        
        with template_col1:
            template_method = st.selectbox(
                "Template Matching Method",
                ["TM_CCOEFF", "TM_CCOEFF_NORMED", "TM_CCORR", "TM_CCORR_NORMED", "TM_SQDIFF", "TM_SQDIFF_NORMED"],
                help="Choose template matching method"
            )
            
            # For demo purposes, we'll create a simple template from the image
            if st.button("Apply Template Matching"):
                processed = self._apply_template_matching(image, template_method)
                if processed is not None:
                    self.display_result(image, processed, "Original", "Template Matching")
                    self.create_download(processed, "template_matching.png", "Download Template Matching Image")
    
    def _render_machine_learning(self, image: np.ndarray) -> None:
        """Render machine learning section."""
        st.subheader("🤖 Machine Learning Integration")
        ml_col1, ml_col2 = st.columns(2)
        
        with ml_col1:
            ml_method = st.selectbox(
                "ML Method",
                ["Face Detection", "Object Detection", "Image Classification"],
                help="Choose machine learning method"
            )
            
            if ml_method == "Face Detection":
                if st.button("Detect Faces"):
                    processed = self._detect_faces_ml(image)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "Face Detection")
                        self.create_download(processed, "face_detection.png", "Download Face Detection Image")
            
            elif ml_method == "Object Detection":
                if st.button("Detect Objects"):
                    processed = self._detect_objects_ml(image)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "Object Detection")
                        self.create_download(processed, "object_detection.png", "Download Object Detection Image")
            
            elif ml_method == "Image Classification":
                if st.button("Classify Image"):
                    result = self._classify_image_ml(image)
                    if result is not None:
                        st.success(f"Classification Result: {result}")
    
    # Helper methods for advanced techniques
    @OperationMixin.safe_operation
    def _show_magnitude_spectrum(self, image: np.ndarray) -> Optional[np.ndarray]:
        """Show magnitude spectrum of image."""
        # Convert to grayscale
        gray = self.convert_to_grayscale(image)
        
        # Apply Fourier transform
        f_transform = np.fft.fft2(gray)
        f_shift = np.fft.fftshift(f_transform)
        
        # Calculate magnitude spectrum
        magnitude_spectrum = np.log(np.abs(f_shift) + 1)
        
        # Normalize
        magnitude_spectrum = cv2.normalize(magnitude_spectrum, None, 0, 255, cv2.NORM_MINMAX)
        magnitude_spectrum = np.uint8(magnitude_spectrum)
        
        # Convert back to BGR for display
        result = cv2.cvtColor(magnitude_spectrum, cv2.COLOR_GRAY2BGR)
        
        return result
    
    @OperationMixin.safe_operation
    def _show_phase_spectrum(self, image: np.ndarray) -> Optional[np.ndarray]:
        """Show phase spectrum of image."""
        # Convert to grayscale
        gray = self.convert_to_grayscale(image)
        
        # Apply Fourier transform
        f_transform = np.fft.fft2(gray)
        f_shift = np.fft.fftshift(f_transform)
        
        # Calculate phase spectrum
        phase_spectrum = np.angle(f_shift)
        
        # Normalize
        phase_spectrum = cv2.normalize(phase_spectrum, None, 0, 255, cv2.NORM_MINMAX)
        phase_spectrum = np.uint8(phase_spectrum)
        
        # Convert back to BGR for display
        result = cv2.cvtColor(phase_spectrum, cv2.COLOR_GRAY2BGR)
        
        return result
    
    @OperationMixin.safe_operation
    def _apply_low_pass_filter(self, image: np.ndarray, cutoff_radius: int) -> Optional[np.ndarray]:
        """Apply low pass filter to image."""
        # Convert to grayscale
        gray = self.convert_to_grayscale(image)
        
        # Apply Fourier transform
        f_transform = np.fft.fft2(gray)
        f_shift = np.fft.fftshift(f_transform)
        
        # Create low pass filter
        rows, cols = gray.shape
        crow, ccol = rows // 2, cols // 2
        mask = np.zeros((rows, cols), np.uint8)
        cv2.circle(mask, (ccol, crow), cutoff_radius, 1, -1)
        
        # Apply filter
        f_shift_filtered = f_shift * mask
        f_ishift = np.fft.ifftshift(f_shift_filtered)
        img_back = np.fft.ifft2(f_ishift)
        img_back = np.abs(img_back)
        
        # Normalize
        img_back = cv2.normalize(img_back, None, 0, 255, cv2.NORM_MINMAX)
        img_back = np.uint8(img_back)
        
        # Convert back to BGR for display
        result = cv2.cvtColor(img_back, cv2.COLOR_GRAY2BGR)
        
        return result
    
    @OperationMixin.safe_operation
    def _apply_high_pass_filter(self, image: np.ndarray, cutoff_radius: int) -> Optional[np.ndarray]:
        """Apply high pass filter to image."""
        # Convert to grayscale
        gray = self.convert_to_grayscale(image)
        
        # Apply Fourier transform
        f_transform = np.fft.fft2(gray)
        f_shift = np.fft.fftshift(f_transform)
        
        # Create high pass filter
        rows, cols = gray.shape
        crow, ccol = rows // 2, cols // 2
        mask = np.ones((rows, cols), np.uint8)
        cv2.circle(mask, (ccol, crow), cutoff_radius, 0, -1)
        
        # Apply filter
        f_shift_filtered = f_shift * mask
        f_ishift = np.fft.ifftshift(f_shift_filtered)
        img_back = np.fft.ifft2(f_ishift)
        img_back = np.abs(img_back)
        
        # Normalize
        img_back = cv2.normalize(img_back, None, 0, 255, cv2.NORM_MINMAX)
        img_back = np.uint8(img_back)
        
        # Convert back to BGR for display
        result = cv2.cvtColor(img_back, cv2.COLOR_GRAY2BGR)
        
        return result
    
    @OperationMixin.safe_operation
    def _apply_band_pass_filter(self, image: np.ndarray, low_cutoff: int, high_cutoff: int) -> Optional[np.ndarray]:
        """Apply band pass filter to image."""
        # Convert to grayscale
        gray = self.convert_to_grayscale(image)
        
        # Apply Fourier transform
        f_transform = np.fft.fft2(gray)
        f_shift = np.fft.fftshift(f_transform)
        
        # Create band pass filter
        rows, cols = gray.shape
        crow, ccol = rows // 2, cols // 2
        mask = np.zeros((rows, cols), np.uint8)
        cv2.circle(mask, (ccol, crow), high_cutoff, 1, -1)
        cv2.circle(mask, (ccol, crow), low_cutoff, 0, -1)
        
        # Apply filter
        f_shift_filtered = f_shift * mask
        f_ishift = np.fft.ifftshift(f_shift_filtered)
        img_back = np.fft.ifft2(f_ishift)
        img_back = np.abs(img_back)
        
        # Normalize
        img_back = cv2.normalize(img_back, None, 0, 255, cv2.NORM_MINMAX)
        img_back = np.uint8(img_back)
        
        # Convert back to BGR for display
        result = cv2.cvtColor(img_back, cv2.COLOR_GRAY2BGR)
        
        return result
    
    @OperationMixin.safe_operation
    def _apply_kmeans_segmentation_advanced(self, image: np.ndarray, k: int) -> Optional[np.ndarray]:
        """Apply K-means segmentation with advanced features."""
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
    def _apply_mean_shift_segmentation_advanced(self, image: np.ndarray, spatial_radius: int, color_radius: int) -> Optional[np.ndarray]:
        """Apply mean shift segmentation with advanced features."""
        # Apply mean shift filtering
        shifted = cv2.pyrMeanShiftFiltering(image, spatial_radius, color_radius)
        
        return shifted
    
    @OperationMixin.safe_operation
    def _apply_watershed_segmentation(self, image: np.ndarray) -> Optional[np.ndarray]:
        """Apply watershed segmentation."""
        # Convert to grayscale
        gray = self.convert_to_grayscale(image)
        
        # Threshold the image
        _, binary = cv2.threshold(gray, 127, 255, cv2.THRESH_BINARY)
        
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
        result[markers == -1] = [0, 0, 255]  # Watershed boundaries in red
        
        return result
    
    @OperationMixin.safe_operation
    def _apply_grabcut_segmentation_advanced(self, image: np.ndarray) -> Optional[np.ndarray]:
        """Apply GrabCut segmentation with advanced features."""
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
    
    @OperationMixin.safe_operation
    def _apply_slic_superpixels(self, image: np.ndarray, num_segments: int, compactness: int) -> Optional[np.ndarray]:
        """Apply SLIC superpixels segmentation."""
        # Convert to LAB color space
        lab = cv2.cvtColor(image, cv2.COLOR_BGR2LAB)
        
        # Apply SLIC superpixels
        slic = cv2.ximgproc.createSuperpixelSLIC(lab, cv2.ximgproc.SLIC, num_segments, compactness)
        slic.iterate(10)
        
        # Get labels
        labels = slic.getLabels()
        
        # Create result image
        result = image.copy()
        for i in range(num_segments):
            mask = labels == i
            color = np.random.randint(0, 255, 3)
            result[mask] = color
        
        return result
    
    @OperationMixin.safe_operation
    def _apply_template_matching(self, image: np.ndarray, method: str) -> Optional[np.ndarray]:
        """Apply template matching."""
        # Convert to grayscale
        gray = self.convert_to_grayscale(image)
        
        # Create a simple template (top-left quarter of the image)
        height, width = gray.shape
        template = gray[0:height//4, 0:width//4]
        
        # Template matching
        if method == "TM_CCOEFF":
            result = cv2.matchTemplate(gray, template, cv2.TM_CCOEFF)
        elif method == "TM_CCOEFF_NORMED":
            result = cv2.matchTemplate(gray, template, cv2.TM_CCOEFF_NORMED)
        elif method == "TM_CCORR":
            result = cv2.matchTemplate(gray, template, cv2.TM_CCORR)
        elif method == "TM_CCORR_NORMED":
            result = cv2.matchTemplate(gray, template, cv2.TM_CCORR_NORMED)
        elif method == "TM_SQDIFF":
            result = cv2.matchTemplate(gray, template, cv2.TM_SQDIFF)
        elif method == "TM_SQDIFF_NORMED":
            result = cv2.matchTemplate(gray, template, cv2.TM_SQDIFF_NORMED)
        else:
            result = cv2.matchTemplate(gray, template, cv2.TM_CCOEFF_NORMED)
        
        # Normalize result
        result = cv2.normalize(result, None, 0, 255, cv2.NORM_MINMAX)
        result = np.uint8(result)
        
        # Convert back to BGR for display
        result = cv2.cvtColor(result, cv2.COLOR_GRAY2BGR)
        
        return result
    
    @OperationMixin.safe_operation
    def _detect_faces_ml(self, image: np.ndarray) -> Optional[np.ndarray]:
        """Detect faces using machine learning."""
        # This is a placeholder for ML-based face detection
        # In a real implementation, you would load a pre-trained model
        result = image.copy()
        
        # For demo purposes, we'll just return the original image
        # In practice, you would use a model like OpenCV's DNN face detector
        st.info("ML-based face detection would be implemented here with a pre-trained model")
        
        return result
    
    @OperationMixin.safe_operation
    def _detect_objects_ml(self, image: np.ndarray) -> Optional[np.ndarray]:
        """Detect objects using machine learning."""
        # This is a placeholder for ML-based object detection
        result = image.copy()
        
        # For demo purposes, we'll just return the original image
        # In practice, you would use a model like YOLO or SSD
        st.info("ML-based object detection would be implemented here with a pre-trained model")
        
        return result
    
    @OperationMixin.safe_operation
    def _classify_image_ml(self, image: np.ndarray) -> Optional[str]:
        """Classify image using machine learning."""
        # This is a placeholder for ML-based image classification
        # In practice, you would use a model like ResNet or VGG
        
        st.info("ML-based image classification would be implemented here with a pre-trained model")
        return "Demo Classification Result" 