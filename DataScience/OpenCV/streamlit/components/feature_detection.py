"""
Feature Detection component for OpenCV operations.
"""

import cv2
import numpy as np
import streamlit as st
from typing import Optional
from .base import BaseComponent, OperationMixin


class FeatureDetectionComponent(BaseComponent, OperationMixin):
    """Component for feature detection operations."""
    
    def __init__(self):
        super().__init__("Feature Detection", "Detect corners, keypoints, contours, and lines")
    
    def render(self, image: np.ndarray) -> None:
        """Render the feature detection section."""
        st.header("🎯 Feature Detection")
        
        if not self.validate_input(image):
            self.show_warning("Please upload an image to start experimenting with feature detection.")
            return
        
        # Corner Detection
        self._render_corner_detection(image)
        st.divider()
        
        # Keypoint Detection
        self._render_keypoint_detection(image)
        st.divider()
        
        # Contour Detection
        self._render_contour_detection(image)
        st.divider()
        
        # Line Detection
        self._render_line_detection(image)
    
    def _render_corner_detection(self, image: np.ndarray) -> None:
        """Render corner detection section."""
        st.subheader("🔲 Corner Detection")
        corner_col1, corner_col2 = st.columns(2)
        
        with corner_col1:
            corner_method = st.selectbox(
                "Corner Detection Method",
                ["Harris", "Shi-Tomasi", "FAST", "ORB"],
                help="Choose corner detection method"
            )
            
            if corner_method == "Harris":
                self._render_harris_corners(image)
            elif corner_method == "Shi-Tomasi":
                self._render_shi_tomasi_corners(image)
            elif corner_method == "FAST":
                self._render_fast_corners(image)
            elif corner_method == "ORB":
                self._render_orb_features(image)
    
    def _render_harris_corners(self, image: np.ndarray) -> None:
        """Render Harris corners controls."""
        block_size = st.slider("Block Size", 2, 10, 2, 1)
        ksize = st.slider("Kernel Size", 3, 31, 3, 2)
        k = st.slider("Harris Parameter (k)", 0.01, 0.1, 0.04, 0.01)
        
        if st.button("Detect Harris Corners"):
            processed = self._detect_harris_corners(image, block_size, ksize, k)
            if processed is not None:
                self.display_result(image, processed, "Original", "Harris Corners")
                self.create_download(processed, "harris_corners.png", "Download Harris Corners Image")
    
    def _render_shi_tomasi_corners(self, image: np.ndarray) -> None:
        """Render Shi-Tomasi corners controls."""
        max_corners = st.slider("Max Corners", 10, 100, 25, 5)
        quality_level = st.slider("Quality Level", 0.01, 0.1, 0.01, 0.01)
        min_distance = st.slider("Min Distance", 1, 20, 10, 1)
        
        if st.button("Detect Shi-Tomasi Corners"):
            processed = self._detect_shi_tomasi_corners(image, max_corners, quality_level, min_distance)
            if processed is not None:
                self.display_result(image, processed, "Original", "Shi-Tomasi Corners")
                self.create_download(processed, "shi_tomasi_corners.png", "Download Shi-Tomasi Corners Image")
    
    def _render_fast_corners(self, image: np.ndarray) -> None:
        """Render FAST corners controls."""
        threshold = st.slider("Threshold", 10, 100, 50, 5)
        nonmax_suppression = st.checkbox("Non-maximum Suppression", value=True)
        
        if st.button("Detect FAST Corners"):
            processed = self._detect_fast_corners(image, threshold, nonmax_suppression)
            if processed is not None:
                self.display_result(image, processed, "Original", "FAST Corners")
                self.create_download(processed, "fast_corners.png", "Download FAST Corners Image")
    
    def _render_orb_features(self, image: np.ndarray) -> None:
        """Render ORB features controls."""
        max_features = st.slider("Max Features", 100, 1000, 500, 50)
        scale_factor = st.slider("Scale Factor", 1.1, 2.0, 1.2, 0.1)
        n_levels = st.slider("Number of Levels", 4, 16, 8, 1)
        
        if st.button("Detect ORB Features"):
            processed = self._detect_orb_features(image, max_features, scale_factor, n_levels)
            if processed is not None:
                self.display_result(image, processed, "Original", "ORB Features")
                self.create_download(processed, "orb_features.png", "Download ORB Features Image")
    
    def _render_keypoint_detection(self, image: np.ndarray) -> None:
        """Render keypoint detection section."""
        st.subheader("🔑 Keypoint Detection")
        keypoint_col1, keypoint_col2 = st.columns(2)
        
        with keypoint_col1:
            keypoint_method = st.selectbox(
                "Keypoint Detection Method",
                ["SIFT", "SURF", "BRISK", "AKAZE"],
                help="Choose keypoint detection method"
            )
            
            if keypoint_method == "SIFT":
                self._render_sift_keypoints(image)
            elif keypoint_method == "SURF":
                self._render_surf_keypoints(image)
            elif keypoint_method == "BRISK":
                self._render_brisk_keypoints(image)
            elif keypoint_method == "AKAZE":
                self._render_akaze_keypoints(image)
    
    def _render_sift_keypoints(self, image: np.ndarray) -> None:
        """Render SIFT keypoints controls."""
        n_features = st.slider("Number of Features", 100, 1000, 500, 50)
        n_octave_layers = st.slider("Octave Layers", 3, 6, 3, 1)
        contrast_threshold = st.slider("Contrast Threshold", 0.01, 0.1, 0.04, 0.01)
        
        if st.button("Detect SIFT Keypoints"):
            processed = self._detect_sift_keypoints(image, n_features, n_octave_layers, contrast_threshold)
            if processed is not None:
                self.display_result(image, processed, "Original", "SIFT Keypoints")
                self.create_download(processed, "sift_keypoints.png", "Download SIFT Keypoints Image")
    
    def _render_surf_keypoints(self, image: np.ndarray) -> None:
        """Render SURF keypoints controls."""
        hessian_threshold = st.slider("Hessian Threshold", 100, 1000, 400, 50)
        n_octaves = st.slider("Octaves", 3, 6, 4, 1)
        n_octave_layers = st.slider("Octave Layers", 2, 5, 3, 1)
        
        if st.button("Detect SURF Keypoints"):
            processed = self._detect_surf_keypoints(image, hessian_threshold, n_octaves, n_octave_layers)
            if processed is not None:
                self.display_result(image, processed, "Original", "SURF Keypoints")
                self.create_download(processed, "surf_keypoints.png", "Download SURF Keypoints Image")
    
    def _render_brisk_keypoints(self, image: np.ndarray) -> None:
        """Render BRISK keypoints controls."""
        threshold = st.slider("Threshold", 10, 100, 30, 5)
        octaves = st.slider("Octaves", 3, 6, 4, 1)
        
        if st.button("Detect BRISK Keypoints"):
            processed = self._detect_brisk_keypoints(image, threshold, octaves)
            if processed is not None:
                self.display_result(image, processed, "Original", "BRISK Keypoints")
                self.create_download(processed, "brisk_keypoints.png", "Download BRISK Keypoints Image")
    
    def _render_akaze_keypoints(self, image: np.ndarray) -> None:
        """Render AKAZE keypoints controls."""
        descriptor_size = st.slider("Descriptor Size", 0, 1, 0, 1)
        descriptor_channels = st.slider("Descriptor Channels", 3, 4, 3, 1)
        threshold = st.slider("Threshold", 0.001, 0.01, 0.001, 0.001)
        
        if st.button("Detect AKAZE Keypoints"):
            processed = self._detect_akaze_keypoints(image, descriptor_size, descriptor_channels, threshold)
            if processed is not None:
                self.display_result(image, processed, "Original", "AKAZE Keypoints")
                self.create_download(processed, "akaze_keypoints.png", "Download AKAZE Keypoints Image")
    
    def _render_contour_detection(self, image: np.ndarray) -> None:
        """Render contour detection section."""
        st.subheader("📐 Contour Detection")
        contour_col1, contour_col2 = st.columns(2)
        
        with contour_col1:
            contour_method = st.selectbox(
                "Contour Detection Method",
                ["Simple", "Approximation", "Hierarchy"],
                help="Choose contour detection method"
            )
            
            threshold_value = st.slider("Threshold Value", 0, 255, 127, 5)
            
            if contour_method == "Simple":
                if st.button("Detect Simple Contours"):
                    processed = self._detect_simple_contours(image, threshold_value)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "Simple Contours")
                        self.create_download(processed, "simple_contours.png", "Download Simple Contours Image")
            
            elif contour_method == "Approximation":
                epsilon_factor = st.slider("Epsilon Factor", 0.01, 0.1, 0.02, 0.01)
                if st.button("Detect Approximated Contours"):
                    processed = self._detect_approximated_contours(image, threshold_value, epsilon_factor)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "Approximated Contours")
                        self.create_download(processed, "approximated_contours.png", "Download Approximated Contours Image")
            
            elif contour_method == "Hierarchy":
                if st.button("Detect Hierarchical Contours"):
                    processed = self._detect_hierarchical_contours(image, threshold_value)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "Hierarchical Contours")
                        self.create_download(processed, "hierarchical_contours.png", "Download Hierarchical Contours Image")
    
    def _render_line_detection(self, image: np.ndarray) -> None:
        """Render line detection section."""
        st.subheader("📏 Line Detection")
        line_col1, line_col2 = st.columns(2)
        
        with line_col1:
            line_method = st.selectbox(
                "Line Detection Method",
                ["Hough Lines", "Hough Lines P", "Probabilistic Hough"],
                help="Choose line detection method"
            )
            
            if line_method == "Hough Lines":
                self._render_hough_lines(image)
            elif line_method == "Hough Lines P":
                self._render_probabilistic_hough_lines(image)
    
    def _render_hough_lines(self, image: np.ndarray) -> None:
        """Render Hough lines controls."""
        rho = st.slider("Rho", 1, 10, 1, 1)
        theta = st.slider("Theta", 1, 180, 180, 1)
        threshold = st.slider("Threshold", 50, 300, 150, 10)
        
        if st.button("Detect Hough Lines"):
            processed = self._detect_hough_lines(image, rho, theta, threshold)
            if processed is not None:
                self.display_result(image, processed, "Original", "Hough Lines")
                self.create_download(processed, "hough_lines.png", "Download Hough Lines Image")
    
    def _render_probabilistic_hough_lines(self, image: np.ndarray) -> None:
        """Render probabilistic Hough lines controls."""
        rho = st.slider("Rho", 1, 10, 1, 1)
        theta = st.slider("Theta", 1, 180, 180, 1)
        threshold = st.slider("Threshold", 50, 300, 150, 10)
        min_line_length = st.slider("Min Line Length", 10, 100, 50, 5)
        max_line_gap = st.slider("Max Line Gap", 1, 20, 10, 1)
        
        if st.button("Detect Probabilistic Hough Lines"):
            processed = self._detect_probabilistic_hough_lines(image, rho, theta, threshold, min_line_length, max_line_gap)
            if processed is not None:
                self.display_result(image, processed, "Original", "Probabilistic Hough Lines")
                self.create_download(processed, "probabilistic_hough_lines.png", "Download Probabilistic Hough Lines Image")
    
    # Helper methods for feature detection
    @OperationMixin.safe_operation
    def _detect_harris_corners(self, image: np.ndarray, block_size: int, ksize: int, k: float) -> Optional[np.ndarray]:
        """Detect Harris corners."""
        gray = self.convert_to_grayscale(image)
        gray = np.float32(gray)
        
        # Harris corner detection
        dst = cv2.cornerHarris(gray, block_size, ksize, k)
        
        # Dilate to mark the corners
        dst = cv2.dilate(dst, None)
        
        # Threshold for an optimal value
        result = image.copy()
        result[dst > 0.01 * dst.max()] = [0, 0, 255]
        
        return result
    
    @OperationMixin.safe_operation
    def _detect_shi_tomasi_corners(self, image: np.ndarray, max_corners: int, quality_level: float, min_distance: int) -> Optional[np.ndarray]:
        """Detect Shi-Tomasi corners."""
        gray = self.convert_to_grayscale(image)
        
        # Shi-Tomasi corner detection
        corners = cv2.goodFeaturesToTrack(gray, max_corners, quality_level, min_distance)
        
        result = image.copy()
        if corners is not None:
            corners = np.int0(corners)
            for i in corners:
                x, y = i.ravel()
                cv2.circle(result, (x, y), 3, (0, 0, 255), -1)
        
        return result
    
    @OperationMixin.safe_operation
    def _detect_fast_corners(self, image: np.ndarray, threshold: int, nonmax_suppression: bool) -> Optional[np.ndarray]:
        """Detect FAST corners."""
        gray = self.convert_to_grayscale(image)
        
        # FAST corner detection
        fast = cv2.FastFeatureDetector_create(threshold=threshold, nonmaxSuppression=nonmax_suppression)
        keypoints = fast.detect(gray, None)
        
        result = image.copy()
        cv2.drawKeypoints(image, keypoints, result, color=(0, 0, 255))
        
        return result
    
    @OperationMixin.safe_operation
    def _detect_orb_features(self, image: np.ndarray, max_features: int, scale_factor: float, n_levels: int) -> Optional[np.ndarray]:
        """Detect ORB features."""
        gray = self.convert_to_grayscale(image)
        
        # ORB feature detection
        orb = cv2.ORB_create(nfeatures=max_features, scaleFactor=scale_factor, nlevels=n_levels)
        keypoints = orb.detect(gray, None)
        
        result = image.copy()
        cv2.drawKeypoints(image, keypoints, result, color=(0, 0, 255))
        
        return result
    
    @OperationMixin.safe_operation
    def _detect_sift_keypoints(self, image: np.ndarray, n_features: int, n_octave_layers: int, contrast_threshold: float) -> Optional[np.ndarray]:
        """Detect SIFT keypoints."""
        gray = self.convert_to_grayscale(image)
        
        # SIFT keypoint detection
        sift = cv2.SIFT_create(nfeatures=n_features, nOctaveLayers=n_octave_layers, contrastThreshold=contrast_threshold)
        keypoints = sift.detect(gray, None)
        
        result = image.copy()
        cv2.drawKeypoints(image, keypoints, result, color=(0, 0, 255))
        
        return result
    
    @OperationMixin.safe_operation
    def _detect_surf_keypoints(self, image: np.ndarray, hessian_threshold: int, n_octaves: int, n_octave_layers: int) -> Optional[np.ndarray]:
        """Detect SURF keypoints."""
        gray = self.convert_to_grayscale(image)
        
        # SURF keypoint detection
        surf = cv2.xfeatures2d.SURF_create(hessianThreshold=hessian_threshold, nOctaves=n_octaves, nOctaveLayers=n_octave_layers)
        keypoints = surf.detect(gray, None)
        
        result = image.copy()
        cv2.drawKeypoints(image, keypoints, result, color=(0, 0, 255))
        
        return result
    
    @OperationMixin.safe_operation
    def _detect_brisk_keypoints(self, image: np.ndarray, threshold: int, octaves: int) -> Optional[np.ndarray]:
        """Detect BRISK keypoints."""
        gray = self.convert_to_grayscale(image)
        
        # BRISK keypoint detection
        brisk = cv2.BRISK_create(thresh=threshold, octaves=octaves)
        keypoints = brisk.detect(gray, None)
        
        result = image.copy()
        cv2.drawKeypoints(image, keypoints, result, color=(0, 0, 255))
        
        return result
    
    @OperationMixin.safe_operation
    def _detect_akaze_keypoints(self, image: np.ndarray, descriptor_size: int, descriptor_channels: int, threshold: float) -> Optional[np.ndarray]:
        """Detect AKAZE keypoints."""
        gray = self.convert_to_grayscale(image)
        
        # AKAZE keypoint detection
        akaze = cv2.AKAZE_create(descriptor_size=descriptor_size, descriptor_channels=descriptor_channels, threshold=threshold)
        keypoints = akaze.detect(gray, None)
        
        result = image.copy()
        cv2.drawKeypoints(image, keypoints, result, color=(0, 0, 255))
        
        return result
    
    @OperationMixin.safe_operation
    def _detect_simple_contours(self, image: np.ndarray, threshold_value: int) -> Optional[np.ndarray]:
        """Detect simple contours."""
        gray = self.convert_to_grayscale(image)
        
        # Threshold the image
        _, binary = cv2.threshold(gray, threshold_value, 255, cv2.THRESH_BINARY)
        
        # Find contours
        contours, _ = cv2.findContours(binary, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
        
        result = image.copy()
        cv2.drawContours(result, contours, -1, (0, 255, 0), 2)
        
        return result
    
    @OperationMixin.safe_operation
    def _detect_approximated_contours(self, image: np.ndarray, threshold_value: int, epsilon_factor: float) -> Optional[np.ndarray]:
        """Detect approximated contours."""
        gray = self.convert_to_grayscale(image)
        
        # Threshold the image
        _, binary = cv2.threshold(gray, threshold_value, 255, cv2.THRESH_BINARY)
        
        # Find contours
        contours, _ = cv2.findContours(binary, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
        
        # Approximate contours
        approximated_contours = []
        for contour in contours:
            epsilon = epsilon_factor * cv2.arcLength(contour, True)
            approx = cv2.approxPolyDP(contour, epsilon, True)
            approximated_contours.append(approx)
        
        result = image.copy()
        cv2.drawContours(result, approximated_contours, -1, (0, 255, 0), 2)
        
        return result
    
    @OperationMixin.safe_operation
    def _detect_hierarchical_contours(self, image: np.ndarray, threshold_value: int) -> Optional[np.ndarray]:
        """Detect hierarchical contours."""
        gray = self.convert_to_grayscale(image)
        
        # Threshold the image
        _, binary = cv2.threshold(gray, threshold_value, 255, cv2.THRESH_BINARY)
        
        # Find contours with hierarchy
        contours, hierarchy = cv2.findContours(binary, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)
        
        result = image.copy()
        cv2.drawContours(result, contours, -1, (0, 255, 0), 2)
        
        return result
    
    @OperationMixin.safe_operation
    def _detect_hough_lines(self, image: np.ndarray, rho: int, theta: int, threshold: int) -> Optional[np.ndarray]:
        """Detect Hough lines."""
        gray = self.convert_to_grayscale(image)
        
        # Edge detection
        edges = cv2.Canny(gray, 50, 150, apertureSize=3)
        
        # Hough line detection
        lines = cv2.HoughLines(edges, rho, np.pi/theta, threshold)
        
        result = image.copy()
        if lines is not None:
            for rho, theta in lines[:20]:  # Limit to first 20 lines
                a = np.cos(theta)
                b = np.sin(theta)
                x0 = a * rho
                y0 = b * rho
                x1 = int(x0 + 1000 * (-b))
                y1 = int(y0 + 1000 * (a))
                x2 = int(x0 - 1000 * (-b))
                y2 = int(y0 - 1000 * (a))
                cv2.line(result, (x1, y1), (x2, y2), (0, 0, 255), 2)
        
        return result
    
    @OperationMixin.safe_operation
    def _detect_probabilistic_hough_lines(self, image: np.ndarray, rho: int, theta: int, threshold: int, min_line_length: int, max_line_gap: int) -> Optional[np.ndarray]:
        """Detect probabilistic Hough lines."""
        gray = self.convert_to_grayscale(image)
        
        # Edge detection
        edges = cv2.Canny(gray, 50, 150, apertureSize=3)
        
        # Probabilistic Hough line detection
        lines = cv2.HoughLinesP(edges, rho, np.pi/theta, threshold, minLineLength=min_line_length, maxLineGap=max_line_gap)
        
        result = image.copy()
        if lines is not None:
            for line in lines[:20]:  # Limit to first 20 lines
                x1, y1, x2, y2 = line[0]
                cv2.line(result, (x1, y1), (x2, y2), (0, 0, 255), 2)
        
        return result 