"""
UI components for the Streamlit OpenCV dashboard.
"""

import cv2
import numpy as np
import streamlit as st
from typing import Optional, Tuple, Union
from utils import display_image, display_comparison, get_image_info, create_download_button, validate_image


def basic_operations_section(image: np.ndarray):
    """Basic Operations section with interactive widgets."""
    st.header("🔧 Basic Operations")
    
    if not validate_image(image):
        st.warning("Please upload an image to start experimenting with basic operations.")
        return
    
    # Image Information
    st.subheader("📊 Image Information")
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
    
    st.divider()
    
    # Resize Operation
    st.subheader("📏 Resize Image")
    resize_col1, resize_col2 = st.columns(2)
    
    with resize_col1:
        resize_method = st.selectbox(
            "Resize Method",
            ["Fixed Size", "Scale Factor", "Aspect Ratio"],
            help="Choose how to resize the image"
        )
        
        if resize_method == "Fixed Size":
            new_width = st.slider("New Width", 50, 2000, info.get("width", 640), 10)
            new_height = st.slider("New Height", 50, 2000, info.get("height", 480), 10)
            interpolation = st.selectbox(
                "Interpolation",
                ["Linear", "Cubic", "Nearest", "Area", "Lanczos"],
                index=0,
                help="Interpolation method for resizing"
            )
            
            if st.button("Resize Image"):
                processed = resize_image_fixed(image, (new_width, new_height), interpolation)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Resized")
                    create_download_button(processed, "resized_image.png", "Download Resized Image")
        
        elif resize_method == "Scale Factor":
            scale_factor = st.slider("Scale Factor", 0.1, 3.0, 1.0, 0.1)
            interpolation = st.selectbox(
                "Interpolation",
                ["Linear", "Cubic", "Nearest", "Area", "Lanczos"],
                index=0
            )
            
            if st.button("Resize Image"):
                processed = resize_image_scale(image, scale_factor, interpolation)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Resized")
                    create_download_button(processed, "resized_image.png", "Download Resized Image")
        
        elif resize_method == "Aspect Ratio":
            max_dimension = st.slider("Max Dimension", 100, 1000, 800, 50)
            fit_mode = st.selectbox(
                "Fit Mode",
                ["Fit", "Fill", "Stretch"],
                help="How to fit the image within dimensions"
            )
            
            if st.button("Resize Image"):
                processed = resize_image_aspect_ratio(image, max_dimension, fit_mode)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Resized")
                    create_download_button(processed, "resized_image.png", "Download Resized Image")
    
    st.divider()
    
    # Rotate Operation
    st.subheader("🔄 Rotate Image")
    rotate_col1, rotate_col2 = st.columns(2)
    
    with rotate_col1:
        rotation_method = st.selectbox(
            "Rotation Method",
            ["Custom Angle", "90° Steps"],
            help="Choose rotation method"
        )
        
        if rotation_method == "Custom Angle":
            angle = st.slider("Rotation Angle (degrees)", -180, 180, 0, 1)
            scale = st.slider("Scale", 0.1, 2.0, 1.0, 0.1)
            border_mode = st.selectbox(
                "Border Mode",
                ["Constant", "Replicate", "Reflect", "Wrap"],
                help="How to handle borders"
            )
            
            if st.button("Rotate Image"):
                processed = rotate_image_custom(image, angle, scale, border_mode)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Rotated")
                    create_download_button(processed, "rotated_image.png", "Download Rotated Image")
        
        elif rotation_method == "90° Steps":
            times = st.selectbox("Rotate 90°", [1, 2, 3], format_func=lambda x: f"{x * 90}°")
            
            if st.button("Rotate Image"):
                processed = rotate_image_90_steps(image, times)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Rotated")
                    create_download_button(processed, "rotated_image.png", "Download Rotated Image")
    
    st.divider()
    
    # Flip Operation
    st.subheader("🔄 Flip Image")
    flip_col1, flip_col2 = st.columns(2)
    
    with flip_col1:
        flip_direction = st.selectbox(
            "Flip Direction",
            ["Horizontal", "Vertical", "Both"],
            help="Choose flip direction"
        )
        
        if st.button("Flip Image"):
            processed = flip_image_direction(image, flip_direction)
            if processed is not None:
                display_comparison(image, processed, "Original", "Flipped")
                create_download_button(processed, "flipped_image.png", "Download Flipped Image")
    
    st.divider()
    
    # Crop Operation
    st.subheader("✂️ Crop Image")
    crop_col1, crop_col2 = st.columns(2)
    
    with crop_col1:
        crop_method = st.selectbox(
            "Crop Method",
            ["Custom Region", "Center Crop"],
            help="Choose crop method"
        )
        
        if crop_method == "Custom Region":
            height, width = image.shape[:2]
            x = st.slider("X Position", 0, width-1, 0)
            y = st.slider("Y Position", 0, height-1, 0)
            crop_width = st.slider("Crop Width", 1, width-x, min(100, width-x))
            crop_height = st.slider("Crop Height", 1, height-y, min(100, height-y))
            
            if st.button("Crop Image"):
                processed = crop_image_region(image, x, y, crop_width, crop_height)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Cropped")
                    create_download_button(processed, "cropped_image.png", "Download Cropped Image")
        
        elif crop_method == "Center Crop":
            height, width = image.shape[:2]
            crop_size = st.slider("Crop Size", 50, min(width, height), min(width, height)//2, 10)
            
            if st.button("Crop Image"):
                processed = crop_image_center(image, crop_size)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Cropped")
                    create_download_button(processed, "cropped_image.png", "Download Cropped Image")


# Helper functions for image operations
def resize_image_fixed(image: np.ndarray, size: Tuple[int, int], interpolation: str) -> Optional[np.ndarray]:
    """Resize image to fixed size."""
    interpolation_map = {
        "Linear": cv2.INTER_LINEAR,
        "Cubic": cv2.INTER_CUBIC,
        "Nearest": cv2.INTER_NEAREST,
        "Area": cv2.INTER_AREA,
        "Lanczos": cv2.INTER_LANCZOS4
    }
    
    try:
        resized = cv2.resize(image, size, interpolation=interpolation_map[interpolation])
        return resized
    except Exception as e:
        st.error(f"Error resizing image: {e}")
        return None


def resize_image_scale(image: np.ndarray, scale: float, interpolation: str) -> Optional[np.ndarray]:
    """Resize image by scale factor."""
    height, width = image.shape[:2]
    new_size = (int(width * scale), int(height * scale))
    return resize_image_fixed(image, new_size, interpolation)


def resize_image_aspect_ratio(image: np.ndarray, max_dim: int, fit_mode: str) -> Optional[np.ndarray]:
    """Resize image maintaining aspect ratio."""
    height, width = image.shape[:2]
    
    if fit_mode == "Fit":
        scale = min(max_dim / width, max_dim / height)
    elif fit_mode == "Fill":
        scale = max(max_dim / width, max_dim / height)
    else:  # Stretch
        scale = max_dim / max(width, height)
    
    new_size = (int(width * scale), int(height * scale))
    return resize_image_fixed(image, new_size, "Linear")


def rotate_image_custom(image: np.ndarray, angle: float, scale: float, border_mode: str) -> Optional[np.ndarray]:
    """Rotate image by custom angle."""
    border_map = {
        "Constant": cv2.BORDER_CONSTANT,
        "Replicate": cv2.BORDER_REPLICATE,
        "Reflect": cv2.BORDER_REFLECT,
        "Wrap": cv2.BORDER_WRAP
    }
    
    try:
        height, width = image.shape[:2]
        center = (width // 2, height // 2)
        rotation_matrix = cv2.getRotationMatrix2D(center, angle, scale)
        rotated = cv2.warpAffine(image, rotation_matrix, (width, height), 
                                borderMode=border_map[border_mode])
        return rotated
    except Exception as e:
        st.error(f"Error rotating image: {e}")
        return None


def rotate_image_90_steps(image: np.ndarray, times: int) -> Optional[np.ndarray]:
    """Rotate image by 90° steps."""
    try:
        # Rotate 90 degrees clockwise 'times' number of times
        rotated = image
        for _ in range(times):
            rotated = cv2.rotate(rotated, cv2.ROTATE_90_CLOCKWISE)
        return rotated
    except Exception as e:
        st.error(f"Error rotating image: {e}")
        return None


def flip_image_direction(image: np.ndarray, direction: str) -> Optional[np.ndarray]:
    """Flip image in specified direction."""
    flip_map = {
        "Horizontal": 1,  # Flip around y-axis
        "Vertical": 0,    # Flip around x-axis
        "Both": -1        # Flip around both axes
    }
    
    try:
        flipped = cv2.flip(image, flip_map[direction])
        return flipped
    except Exception as e:
        st.error(f"Error flipping image: {e}")
        return None


def crop_image_region(image: np.ndarray, x: int, y: int, width: int, height: int) -> Optional[np.ndarray]:
    """Crop image from specific region."""
    try:
        cropped = image[y:y+height, x:x+width]
        return cropped
    except Exception as e:
        st.error(f"Error cropping image: {e}")
        return None


def crop_image_center(image: np.ndarray, crop_size: int) -> Optional[np.ndarray]:
    """Crop image from center."""
    try:
        height, width = image.shape[:2]
        center_x, center_y = width // 2, height // 2
        half_size = crop_size // 2
        
        x1 = max(0, center_x - half_size)
        y1 = max(0, center_y - half_size)
        x2 = min(width, center_x + half_size)
        y2 = min(height, center_y + half_size)
        
        cropped = image[y1:y2, x1:x2]
        return cropped
    except Exception as e:
        st.error(f"Error cropping image: {e}")
        return None


# Image Filtering Section
def image_filtering_section(image: np.ndarray):
    """Image Filtering section with interactive widgets."""
    st.header("🔍 Image Filtering")
    
    if not validate_image(image):
        st.warning("Please upload an image to start experimenting with image filtering.")
        return
    
    # Gaussian Blur
    st.subheader("🌫️ Gaussian Blur")
    blur_col1, blur_col2 = st.columns(2)
    
    with blur_col1:
        kernel_size = st.slider("Kernel Size", 3, 31, 5, 2)
        sigma_x = st.slider("Sigma X", 0.1, 10.0, 1.0, 0.1)
        sigma_y = st.slider("Sigma Y", 0.1, 10.0, 1.0, 0.1)
        
        if st.button("Apply Gaussian Blur"):
            processed = apply_gaussian_blur(image, kernel_size, sigma_x, sigma_y)
            if processed is not None:
                display_comparison(image, processed, "Original", "Gaussian Blur")
                create_download_button(processed, "gaussian_blur.png", "Download Blurred Image")
    
    st.divider()
    
    # Edge Detection
    st.subheader("🔍 Edge Detection")
    edge_col1, edge_col2 = st.columns(2)
    
    with edge_col1:
        edge_method = st.selectbox(
            "Edge Detection Method",
            ["Canny", "Sobel", "Laplacian"],
            help="Choose edge detection method"
        )
        
        if edge_method == "Canny":
            threshold1 = st.slider("Threshold 1", 0, 255, 100, 5)
            threshold2 = st.slider("Threshold 2", 0, 255, 200, 5)
            aperture_size = st.selectbox("Aperture Size", [3, 5, 7], index=0)
            
            if st.button("Apply Canny Edge Detection"):
                processed = apply_canny_edge_detection(image, threshold1, threshold2, aperture_size)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Canny Edge Detection")
                    create_download_button(processed, "canny_edges.png", "Download Edge Image")
        
        elif edge_method == "Sobel":
            dx = st.slider("dx", 0, 2, 1)
            dy = st.slider("dy", 0, 2, 1)
            ksize = st.selectbox("Kernel Size", [3, 5, 7], index=0)
            
            if st.button("Apply Sobel Edge Detection"):
                processed = apply_sobel_edge_detection(image, dx, dy, ksize)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Sobel Edge Detection")
                    create_download_button(processed, "sobel_edges.png", "Download Edge Image")
        
        elif edge_method == "Laplacian":
            ksize = st.selectbox("Kernel Size", [1, 3, 5], index=1)
            
            if st.button("Apply Laplacian Edge Detection"):
                processed = apply_laplacian_edge_detection(image, ksize)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Laplacian Edge Detection")
                    create_download_button(processed, "laplacian_edges.png", "Download Edge Image")
    
    st.divider()
    
    # Noise Reduction
    st.subheader("🔇 Noise Reduction")
    noise_col1, noise_col2 = st.columns(2)
    
    with noise_col1:
        noise_method = st.selectbox(
            "Noise Reduction Method",
            ["Bilateral Filter", "Median Filter", "Gaussian Filter"],
            help="Choose noise reduction method"
        )
        
        if noise_method == "Bilateral Filter":
            d = st.slider("Diameter", 5, 50, 15, 5)
            sigma_color = st.slider("Sigma Color", 10, 150, 75, 5)
            sigma_space = st.slider("Sigma Space", 10, 150, 75, 5)
            
            if st.button("Apply Bilateral Filter"):
                processed = apply_bilateral_filter(image, d, sigma_color, sigma_space)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Bilateral Filter")
                    create_download_button(processed, "bilateral_filter.png", "Download Filtered Image")
        
        elif noise_method == "Median Filter":
            kernel_size = st.slider("Kernel Size", 3, 15, 5, 2)
            
            if st.button("Apply Median Filter"):
                processed = apply_median_filter(image, kernel_size)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Median Filter")
                    create_download_button(processed, "median_filter.png", "Download Filtered Image")
        
        elif noise_method == "Gaussian Filter":
            kernel_size = st.slider("Kernel Size", 3, 31, 5, 2)
            sigma = st.slider("Sigma", 0.1, 10.0, 1.0, 0.1)
            
            if st.button("Apply Gaussian Filter"):
                processed = apply_gaussian_filter(image, kernel_size, sigma)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Gaussian Filter")
                    create_download_button(processed, "gaussian_filter.png", "Download Filtered Image")


# Helper functions for image filtering
def apply_gaussian_blur(image: np.ndarray, kernel_size: int, sigma_x: float, sigma_y: float) -> Optional[np.ndarray]:
    """Apply Gaussian blur to image."""
    try:
        # Ensure kernel size is odd
        if kernel_size % 2 == 0:
            kernel_size += 1
        
        blurred = cv2.GaussianBlur(image, (kernel_size, kernel_size), sigma_x, sigma_y)
        return blurred
    except Exception as e:
        st.error(f"Error applying Gaussian blur: {e}")
        return None


def apply_canny_edge_detection(image: np.ndarray, threshold1: int, threshold2: int, aperture_size: int) -> Optional[np.ndarray]:
    """Apply Canny edge detection."""
    try:
        # Convert to grayscale if needed
        if len(image.shape) == 3:
            gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        else:
            gray = image
        
        edges = cv2.Canny(gray, threshold1, threshold2, apertureSize=aperture_size)
        return edges
    except Exception as e:
        st.error(f"Error applying Canny edge detection: {e}")
        return None


def apply_sobel_edge_detection(image: np.ndarray, dx: int, dy: int, ksize: int) -> Optional[np.ndarray]:
    """Apply Sobel edge detection."""
    try:
        # Convert to grayscale if needed
        if len(image.shape) == 3:
            gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        else:
            gray = image
        
        sobel = cv2.Sobel(gray, cv2.CV_64F, dx, dy, ksize=ksize)
        sobel = np.absolute(sobel)
        sobel = np.uint8(sobel)
        return sobel
    except Exception as e:
        st.error(f"Error applying Sobel edge detection: {e}")
        return None


def apply_laplacian_edge_detection(image: np.ndarray, ksize: int) -> Optional[np.ndarray]:
    """Apply Laplacian edge detection."""
    try:
        # Convert to grayscale if needed
        if len(image.shape) == 3:
            gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        else:
            gray = image
        
        laplacian = cv2.Laplacian(gray, cv2.CV_64F, ksize=ksize)
        laplacian = np.absolute(laplacian)
        laplacian = np.uint8(laplacian)
        return laplacian
    except Exception as e:
        st.error(f"Error applying Laplacian edge detection: {e}")
        return None


def apply_bilateral_filter(image: np.ndarray, d: int, sigma_color: int, sigma_space: int) -> Optional[np.ndarray]:
    """Apply bilateral filter for noise reduction."""
    try:
        filtered = cv2.bilateralFilter(image, d, sigma_color, sigma_space)
        return filtered
    except Exception as e:
        st.error(f"Error applying bilateral filter: {e}")
        return None


def apply_median_filter(image: np.ndarray, kernel_size: int) -> Optional[np.ndarray]:
    """Apply median filter for noise reduction."""
    try:
        # Ensure kernel size is odd
        if kernel_size % 2 == 0:
            kernel_size += 1
        
        filtered = cv2.medianBlur(image, kernel_size)
        return filtered
    except Exception as e:
        st.error(f"Error applying median filter: {e}")
        return None


def apply_gaussian_filter(image: np.ndarray, kernel_size: int, sigma: float) -> Optional[np.ndarray]:
    """Apply Gaussian filter for noise reduction."""
    try:
        # Ensure kernel size is odd
        if kernel_size % 2 == 0:
            kernel_size += 1
        
        filtered = cv2.GaussianBlur(image, (kernel_size, kernel_size), sigma)
        return filtered
    except Exception as e:
        st.error(f"Error applying Gaussian filter: {e}")
        return None


# Transformations Section
def transformations_section(image: np.ndarray):
    """Transformations section with interactive widgets."""
    st.header("🔄 Transformations")
    
    if not validate_image(image):
        st.warning("Please upload an image to start experimenting with transformations.")
        return
    
    # Affine Transformations
    st.subheader("📐 Affine Transformations")
    affine_col1, affine_col2 = st.columns(2)
    
    with affine_col1:
        transform_type = st.selectbox(
            "Transform Type",
            ["Translation", "Rotation", "Scaling", "Shearing"],
            help="Choose transformation type"
        )
        
        if transform_type == "Translation":
            tx = st.slider("Translate X", -200, 200, 0, 10)
            ty = st.slider("Translate Y", -200, 200, 0, 10)
            
            if st.button("Apply Translation"):
                processed = apply_translation(image, tx, ty)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Translated")
                    create_download_button(processed, "translated_image.png", "Download Translated Image")
        
        elif transform_type == "Rotation":
            angle = st.slider("Rotation Angle", -180, 180, 0, 1)
            scale = st.slider("Scale", 0.1, 2.0, 1.0, 0.1)
            
            if st.button("Apply Rotation"):
                processed = apply_rotation(image, angle, scale)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Rotated")
                    create_download_button(processed, "rotated_image.png", "Download Rotated Image")
        
        elif transform_type == "Scaling":
            scale_x = st.slider("Scale X", 0.1, 3.0, 1.0, 0.1)
            scale_y = st.slider("Scale Y", 0.1, 3.0, 1.0, 0.1)
            
            if st.button("Apply Scaling"):
                processed = apply_scaling(image, scale_x, scale_y)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Scaled")
                    create_download_button(processed, "scaled_image.png", "Download Scaled Image")
        
        elif transform_type == "Shearing":
            shear_x = st.slider("Shear X", -1.0, 1.0, 0.0, 0.1)
            shear_y = st.slider("Shear Y", -1.0, 1.0, 0.0, 0.1)
            
            if st.button("Apply Shearing"):
                processed = apply_shearing(image, shear_x, shear_y)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Sheared")
                    create_download_button(processed, "sheared_image.png", "Download Sheared Image")
    
    st.divider()
    
    # Perspective Transformations
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
                processed = apply_perspective_preset(image, preset)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Perspective Transform")
                    create_download_button(processed, "perspective_image.png", "Download Perspective Image")
    
    st.divider()
    
    # Warping
    st.subheader("🌀 Image Warping")
    warp_col1, warp_col2 = st.columns(2)
    
    with warp_col1:
        warp_type = st.selectbox(
            "Warp Type",
            ["Spherical", "Cylindrical", "Fish Eye"],
            help="Choose warping type"
        )
        
        if warp_type == "Spherical":
            radius = st.slider("Radius", 100, 500, 200, 10)
            
            if st.button("Apply Spherical Warp"):
                processed = apply_spherical_warp(image, radius)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Spherical Warp")
                    create_download_button(processed, "spherical_warp.png", "Download Warped Image")
        
        elif warp_type == "Cylindrical":
            radius = st.slider("Radius", 100, 500, 200, 10)
            
            if st.button("Apply Cylindrical Warp"):
                processed = apply_cylindrical_warp(image, radius)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Cylindrical Warp")
                    create_download_button(processed, "cylindrical_warp.png", "Download Warped Image")
        
        elif warp_type == "Fish Eye":
            strength = st.slider("Fish Eye Strength", 0.1, 2.0, 0.5, 0.1)
            
            if st.button("Apply Fish Eye Warp"):
                processed = apply_fish_eye_warp(image, strength)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Fish Eye Warp")
                    create_download_button(processed, "fish_eye_warp.png", "Download Warped Image")


# Helper functions for transformations
def apply_translation(image: np.ndarray, tx: int, ty: int) -> Optional[np.ndarray]:
    """Apply translation transformation."""
    try:
        height, width = image.shape[:2]
        translation_matrix = np.float32([[1, 0, tx], [0, 1, ty]])
        translated = cv2.warpAffine(image, translation_matrix, (width, height))
        return translated
    except Exception as e:
        st.error(f"Error applying translation: {e}")
        return None


def apply_rotation(image: np.ndarray, angle: float, scale: float) -> Optional[np.ndarray]:
    """Apply rotation transformation."""
    try:
        height, width = image.shape[:2]
        center = (width // 2, height // 2)
        rotation_matrix = cv2.getRotationMatrix2D(center, angle, scale)
        rotated = cv2.warpAffine(image, rotation_matrix, (width, height))
        return rotated
    except Exception as e:
        st.error(f"Error applying rotation: {e}")
        return None


def apply_scaling(image: np.ndarray, scale_x: float, scale_y: float) -> Optional[np.ndarray]:
    """Apply scaling transformation."""
    try:
        height, width = image.shape[:2]
        new_width = int(width * scale_x)
        new_height = int(height * scale_y)
        scaled = cv2.resize(image, (new_width, new_height))
        return scaled
    except Exception as e:
        st.error(f"Error applying scaling: {e}")
        return None


def apply_shearing(image: np.ndarray, shear_x: float, shear_y: float) -> Optional[np.ndarray]:
    """Apply shearing transformation."""
    try:
        height, width = image.shape[:2]
        shear_matrix = np.float32([[1, shear_x, 0], [shear_y, 1, 0]])
        sheared = cv2.warpAffine(image, shear_matrix, (width, height))
        return sheared
    except Exception as e:
        st.error(f"Error applying shearing: {e}")
        return None


def apply_perspective_preset(image: np.ndarray, preset: str) -> Optional[np.ndarray]:
    """Apply predefined perspective transformation."""
    try:
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
    except Exception as e:
        st.error(f"Error applying perspective transform: {e}")
        return None


def apply_spherical_warp(image: np.ndarray, radius: int) -> Optional[np.ndarray]:
    """Apply spherical warping effect."""
    try:
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
    except Exception as e:
        st.error(f"Error applying spherical warp: {e}")
        return None


def apply_cylindrical_warp(image: np.ndarray, radius: int) -> Optional[np.ndarray]:
    """Apply cylindrical warping effect."""
    try:
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
    except Exception as e:
        st.error(f"Error applying cylindrical warp: {e}")
        return None


def apply_fish_eye_warp(image: np.ndarray, strength: float) -> Optional[np.ndarray]:
    """Apply fish eye warping effect."""
    try:
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
    except Exception as e:
        st.error(f"Error applying fish eye warp: {e}")
        return None


# Morphological Operations Section
def morphological_operations_section(image: np.ndarray):
    """Morphological Operations section with interactive widgets."""
    st.header("🔧 Morphological Operations")
    
    if not validate_image(image):
        st.warning("Please upload an image to start experimenting with morphological operations.")
        return
    
    # Convert to grayscale for morphological operations
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    # Basic Morphological Operations
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
            processed = apply_morphological_operation(gray, operation, kernel_size, iterations, kernel_shape)
            if processed is not None:
                display_comparison(image, processed, "Original", operation)
                create_download_button(processed, f"{operation.lower().replace(' ', '_')}.png", f"Download {operation} Image")
    
    st.divider()
    
    # Advanced Morphological Operations
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
                processed = apply_skeletonization(gray)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Skeletonization")
                    create_download_button(processed, "skeletonization.png", "Download Skeletonization Image")
        
        elif advanced_operation == "Distance Transform":
            distance_type = st.selectbox("Distance Type", ["Euclidean", "City Block", "Chessboard"])
            if st.button("Apply Distance Transform"):
                processed = apply_distance_transform(gray, distance_type)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Distance Transform")
                    create_download_button(processed, "distance_transform.png", "Download Distance Transform Image")
        
        elif advanced_operation == "Watershed":
            if st.button("Apply Watershed"):
                processed = apply_watershed(gray)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Watershed")
                    create_download_button(processed, "watershed.png", "Download Watershed Image")
        
        elif advanced_operation == "Connected Components":
            connectivity = st.selectbox("Connectivity", [4, 8])
            if st.button("Apply Connected Components"):
                processed = apply_connected_components(gray, connectivity)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Connected Components")
                    create_download_button(processed, "connected_components.png", "Download Connected Components Image")
    
    st.divider()
    
    # Custom Kernel Operations
    st.subheader("🎛️ Custom Kernel Operations")
    custom_col1, custom_col2 = st.columns(2)
    
    with custom_col1:
        st.subheader("Create Custom Kernel")
        kernel_width = st.slider("Kernel Width", 3, 9, 3, 2)
        kernel_height = st.slider("Kernel Height", 3, 9, 3, 2)
        
        # Create a simple custom kernel (can be enhanced with user input)
        custom_kernel = np.ones((kernel_height, kernel_width), np.uint8)
        
        if st.button("Apply Custom Kernel"):
            processed = apply_custom_kernel(gray, custom_kernel)
            if processed is not None:
                display_comparison(image, processed, "Original", "Custom Kernel")
                create_download_button(processed, "custom_kernel.png", "Download Custom Kernel Image")


# Helper functions for morphological operations
def apply_morphological_operation(image: np.ndarray, operation: str, kernel_size: int, iterations: int, kernel_shape: str) -> Optional[np.ndarray]:
    """Apply morphological operation to image."""
    try:
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
    except Exception as e:
        st.error(f"Error applying morphological operation: {e}")
        return None


def apply_skeletonization(image: np.ndarray) -> Optional[np.ndarray]:
    """Apply skeletonization to image."""
    try:
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
    except Exception as e:
        st.error(f"Error applying skeletonization: {e}")
        return None


def apply_distance_transform(image: np.ndarray, distance_type: str) -> Optional[np.ndarray]:
    """Apply distance transform to image."""
    try:
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
    except Exception as e:
        st.error(f"Error applying distance transform: {e}")
        return None


def apply_watershed(image: np.ndarray) -> Optional[np.ndarray]:
    """Apply watershed segmentation."""
    try:
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
    except Exception as e:
        st.error(f"Error applying watershed: {e}")
        return None


def apply_connected_components(image: np.ndarray, connectivity: int) -> Optional[np.ndarray]:
    """Apply connected components analysis."""
    try:
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
    except Exception as e:
        st.error(f"Error applying connected components: {e}")
        return None


def apply_custom_kernel(image: np.ndarray, kernel: np.ndarray) -> Optional[np.ndarray]:
    """Apply custom kernel to image."""
    try:
        result = cv2.morphologyEx(image, cv2.MORPH_OPEN, kernel)
        return result
    except Exception as e:
        st.error(f"Error applying custom kernel: {e}")
        return None


# Feature Detection Section
def feature_detection_section(image: np.ndarray):
    """Feature Detection section with interactive widgets."""
    st.header("🎯 Feature Detection")
    
    if not validate_image(image):
        st.warning("Please upload an image to start experimenting with feature detection.")
        return
    
    # Corner Detection
    st.subheader("🔲 Corner Detection")
    corner_col1, corner_col2 = st.columns(2)
    
    with corner_col1:
        corner_method = st.selectbox(
            "Corner Detection Method",
            ["Harris", "Shi-Tomasi", "FAST", "ORB"],
            help="Choose corner detection method"
        )
        
        if corner_method == "Harris":
            block_size = st.slider("Block Size", 2, 10, 2, 1)
            ksize = st.slider("Kernel Size", 3, 31, 3, 2)
            k = st.slider("Harris Parameter (k)", 0.01, 0.1, 0.04, 0.01)
            
            if st.button("Detect Harris Corners"):
                processed = detect_harris_corners(image, block_size, ksize, k)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Harris Corners")
                    create_download_button(processed, "harris_corners.png", "Download Harris Corners Image")
        
        elif corner_method == "Shi-Tomasi":
            max_corners = st.slider("Max Corners", 10, 100, 25, 5)
            quality_level = st.slider("Quality Level", 0.01, 0.1, 0.01, 0.01)
            min_distance = st.slider("Min Distance", 1, 20, 10, 1)
            
            if st.button("Detect Shi-Tomasi Corners"):
                processed = detect_shi_tomasi_corners(image, max_corners, quality_level, min_distance)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Shi-Tomasi Corners")
                    create_download_button(processed, "shi_tomasi_corners.png", "Download Shi-Tomasi Corners Image")
        
        elif corner_method == "FAST":
            threshold = st.slider("Threshold", 10, 100, 50, 5)
            nonmax_suppression = st.checkbox("Non-maximum Suppression", value=True)
            
            if st.button("Detect FAST Corners"):
                processed = detect_fast_corners(image, threshold, nonmax_suppression)
                if processed is not None:
                    display_comparison(image, processed, "Original", "FAST Corners")
                    create_download_button(processed, "fast_corners.png", "Download FAST Corners Image")
        
        elif corner_method == "ORB":
            max_features = st.slider("Max Features", 100, 1000, 500, 50)
            scale_factor = st.slider("Scale Factor", 1.1, 2.0, 1.2, 0.1)
            n_levels = st.slider("Number of Levels", 4, 16, 8, 1)
            
            if st.button("Detect ORB Features"):
                processed = detect_orb_features(image, max_features, scale_factor, n_levels)
                if processed is not None:
                    display_comparison(image, processed, "Original", "ORB Features")
                    create_download_button(processed, "orb_features.png", "Download ORB Features Image")
    
    st.divider()
    
    # Keypoint Detection
    st.subheader("🔑 Keypoint Detection")
    keypoint_col1, keypoint_col2 = st.columns(2)
    
    with keypoint_col1:
        keypoint_method = st.selectbox(
            "Keypoint Detection Method",
            ["SIFT", "SURF", "BRISK", "AKAZE"],
            help="Choose keypoint detection method"
        )
        
        if keypoint_method == "SIFT":
            n_features = st.slider("Number of Features", 100, 1000, 500, 50)
            n_octave_layers = st.slider("Octave Layers", 3, 6, 3, 1)
            contrast_threshold = st.slider("Contrast Threshold", 0.01, 0.1, 0.04, 0.01)
            
            if st.button("Detect SIFT Keypoints"):
                processed = detect_sift_keypoints(image, n_features, n_octave_layers, contrast_threshold)
                if processed is not None:
                    display_comparison(image, processed, "Original", "SIFT Keypoints")
                    create_download_button(processed, "sift_keypoints.png", "Download SIFT Keypoints Image")
        
        elif keypoint_method == "SURF":
            hessian_threshold = st.slider("Hessian Threshold", 100, 1000, 400, 50)
            n_octaves = st.slider("Octaves", 3, 6, 4, 1)
            n_octave_layers = st.slider("Octave Layers", 2, 5, 3, 1)
            
            if st.button("Detect SURF Keypoints"):
                processed = detect_surf_keypoints(image, hessian_threshold, n_octaves, n_octave_layers)
                if processed is not None:
                    display_comparison(image, processed, "Original", "SURF Keypoints")
                    create_download_button(processed, "surf_keypoints.png", "Download SURF Keypoints Image")
        
        elif keypoint_method == "BRISK":
            threshold = st.slider("Threshold", 10, 100, 30, 5)
            octaves = st.slider("Octaves", 3, 6, 4, 1)
            
            if st.button("Detect BRISK Keypoints"):
                processed = detect_brisk_keypoints(image, threshold, octaves)
                if processed is not None:
                    display_comparison(image, processed, "Original", "BRISK Keypoints")
                    create_download_button(processed, "brisk_keypoints.png", "Download BRISK Keypoints Image")
        
        elif keypoint_method == "AKAZE":
            descriptor_size = st.slider("Descriptor Size", 0, 1, 0, 1)
            descriptor_channels = st.slider("Descriptor Channels", 3, 4, 3, 1)
            threshold = st.slider("Threshold", 0.001, 0.01, 0.001, 0.001)
            
            if st.button("Detect AKAZE Keypoints"):
                processed = detect_akaze_keypoints(image, descriptor_size, descriptor_channels, threshold)
                if processed is not None:
                    display_comparison(image, processed, "Original", "AKAZE Keypoints")
                    create_download_button(processed, "akaze_keypoints.png", "Download AKAZE Keypoints Image")
    
    st.divider()
    
    # Contour Detection
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
                processed = detect_simple_contours(image, threshold_value)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Simple Contours")
                    create_download_button(processed, "simple_contours.png", "Download Simple Contours Image")
        
        elif contour_method == "Approximation":
            epsilon_factor = st.slider("Epsilon Factor", 0.01, 0.1, 0.02, 0.01)
            if st.button("Detect Approximated Contours"):
                processed = detect_approximated_contours(image, threshold_value, epsilon_factor)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Approximated Contours")
                    create_download_button(processed, "approximated_contours.png", "Download Approximated Contours Image")
        
        elif contour_method == "Hierarchy":
            if st.button("Detect Hierarchical Contours"):
                processed = detect_hierarchical_contours(image, threshold_value)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Hierarchical Contours")
                    create_download_button(processed, "hierarchical_contours.png", "Download Hierarchical Contours Image")
    
    st.divider()
    
    # Line Detection
    st.subheader("📏 Line Detection")
    line_col1, line_col2 = st.columns(2)
    
    with line_col1:
        line_method = st.selectbox(
            "Line Detection Method",
            ["Hough Lines", "Hough Lines P", "Probabilistic Hough"],
            help="Choose line detection method"
        )
        
        if line_method == "Hough Lines":
            rho = st.slider("Rho", 1, 10, 1, 1)
            theta = st.slider("Theta", 1, 180, 180, 1)
            threshold = st.slider("Threshold", 50, 300, 150, 10)
            
            if st.button("Detect Hough Lines"):
                processed = detect_hough_lines(image, rho, theta, threshold)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Hough Lines")
                    create_download_button(processed, "hough_lines.png", "Download Hough Lines Image")
        
        elif line_method == "Hough Lines P":
            rho = st.slider("Rho", 1, 10, 1, 1)
            theta = st.slider("Theta", 1, 180, 180, 1)
            threshold = st.slider("Threshold", 50, 300, 150, 10)
            min_line_length = st.slider("Min Line Length", 10, 100, 50, 5)
            max_line_gap = st.slider("Max Line Gap", 1, 20, 10, 1)
            
            if st.button("Detect Probabilistic Hough Lines"):
                processed = detect_probabilistic_hough_lines(image, rho, theta, threshold, min_line_length, max_line_gap)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Probabilistic Hough Lines")
                    create_download_button(processed, "probabilistic_hough_lines.png", "Download Probabilistic Hough Lines Image")


# Helper functions for feature detection
def detect_harris_corners(image: np.ndarray, block_size: int, ksize: int, k: float) -> Optional[np.ndarray]:
    """Detect Harris corners."""
    try:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY) if len(image.shape) == 3 else image
        gray = np.float32(gray)
        
        # Harris corner detection
        dst = cv2.cornerHarris(gray, block_size, ksize, k)
        
        # Dilate to mark the corners
        dst = cv2.dilate(dst, None)
        
        # Threshold for an optimal value
        result = image.copy()
        result[dst > 0.01 * dst.max()] = [0, 0, 255]
        
        return result
    except Exception as e:
        st.error(f"Error detecting Harris corners: {e}")
        return None


def detect_shi_tomasi_corners(image: np.ndarray, max_corners: int, quality_level: float, min_distance: int) -> Optional[np.ndarray]:
    """Detect Shi-Tomasi corners."""
    try:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY) if len(image.shape) == 3 else image
        
        # Shi-Tomasi corner detection
        corners = cv2.goodFeaturesToTrack(gray, max_corners, quality_level, min_distance)
        
        result = image.copy()
        if corners is not None:
            corners = np.int0(corners)
            for i in corners:
                x, y = i.ravel()
                cv2.circle(result, (x, y), 3, (0, 0, 255), -1)
        
        return result
    except Exception as e:
        st.error(f"Error detecting Shi-Tomasi corners: {e}")
        return None


def detect_fast_corners(image: np.ndarray, threshold: int, nonmax_suppression: bool) -> Optional[np.ndarray]:
    """Detect FAST corners."""
    try:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY) if len(image.shape) == 3 else image
        
        # FAST corner detection
        fast = cv2.FastFeatureDetector_create(threshold=threshold, nonmaxSuppression=nonmax_suppression)
        keypoints = fast.detect(gray, None)
        
        result = image.copy()
        cv2.drawKeypoints(image, keypoints, result, color=(0, 0, 255))
        
        return result
    except Exception as e:
        st.error(f"Error detecting FAST corners: {e}")
        return None


def detect_orb_features(image: np.ndarray, max_features: int, scale_factor: float, n_levels: int) -> Optional[np.ndarray]:
    """Detect ORB features."""
    try:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY) if len(image.shape) == 3 else image
        
        # ORB feature detection
        orb = cv2.ORB_create(nfeatures=max_features, scaleFactor=scale_factor, nlevels=n_levels)
        keypoints = orb.detect(gray, None)
        
        result = image.copy()
        cv2.drawKeypoints(image, keypoints, result, color=(0, 0, 255))
        
        return result
    except Exception as e:
        st.error(f"Error detecting ORB features: {e}")
        return None


def detect_sift_keypoints(image: np.ndarray, n_features: int, n_octave_layers: int, contrast_threshold: float) -> Optional[np.ndarray]:
    """Detect SIFT keypoints."""
    try:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY) if len(image.shape) == 3 else image
        
        # SIFT keypoint detection
        sift = cv2.SIFT_create(nfeatures=n_features, nOctaveLayers=n_octave_layers, contrastThreshold=contrast_threshold)
        keypoints = sift.detect(gray, None)
        
        result = image.copy()
        cv2.drawKeypoints(image, keypoints, result, color=(0, 0, 255))
        
        return result
    except Exception as e:
        st.error(f"Error detecting SIFT keypoints: {e}")
        return None


def detect_surf_keypoints(image: np.ndarray, hessian_threshold: int, n_octaves: int, n_octave_layers: int) -> Optional[np.ndarray]:
    """Detect SURF keypoints."""
    try:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY) if len(image.shape) == 3 else image
        
        # SURF keypoint detection
        surf = cv2.xfeatures2d.SURF_create(hessianThreshold=hessian_threshold, nOctaves=n_octaves, nOctaveLayers=n_octave_layers)
        keypoints = surf.detect(gray, None)
        
        result = image.copy()
        cv2.drawKeypoints(image, keypoints, result, color=(0, 0, 255))
        
        return result
    except Exception as e:
        st.error(f"Error detecting SURF keypoints: {e}")
        return None


def detect_brisk_keypoints(image: np.ndarray, threshold: int, octaves: int) -> Optional[np.ndarray]:
    """Detect BRISK keypoints."""
    try:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY) if len(image.shape) == 3 else image
        
        # BRISK keypoint detection
        brisk = cv2.BRISK_create(thresh=threshold, octaves=octaves)
        keypoints = brisk.detect(gray, None)
        
        result = image.copy()
        cv2.drawKeypoints(image, keypoints, result, color=(0, 0, 255))
        
        return result
    except Exception as e:
        st.error(f"Error detecting BRISK keypoints: {e}")
        return None


def detect_akaze_keypoints(image: np.ndarray, descriptor_size: int, descriptor_channels: int, threshold: float) -> Optional[np.ndarray]:
    """Detect AKAZE keypoints."""
    try:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY) if len(image.shape) == 3 else image
        
        # AKAZE keypoint detection
        akaze = cv2.AKAZE_create(descriptor_size=descriptor_size, descriptor_channels=descriptor_channels, threshold=threshold)
        keypoints = akaze.detect(gray, None)
        
        result = image.copy()
        cv2.drawKeypoints(image, keypoints, result, color=(0, 0, 255))
        
        return result
    except Exception as e:
        st.error(f"Error detecting AKAZE keypoints: {e}")
        return None


def detect_simple_contours(image: np.ndarray, threshold_value: int) -> Optional[np.ndarray]:
    """Detect simple contours."""
    try:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY) if len(image.shape) == 3 else image
        
        # Threshold the image
        _, binary = cv2.threshold(gray, threshold_value, 255, cv2.THRESH_BINARY)
        
        # Find contours
        contours, _ = cv2.findContours(binary, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
        
        result = image.copy()
        cv2.drawContours(result, contours, -1, (0, 255, 0), 2)
        
        return result
    except Exception as e:
        st.error(f"Error detecting simple contours: {e}")
        return None


def detect_approximated_contours(image: np.ndarray, threshold_value: int, epsilon_factor: float) -> Optional[np.ndarray]:
    """Detect approximated contours."""
    try:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY) if len(image.shape) == 3 else image
        
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
    except Exception as e:
        st.error(f"Error detecting approximated contours: {e}")
        return None


def detect_hierarchical_contours(image: np.ndarray, threshold_value: int) -> Optional[np.ndarray]:
    """Detect hierarchical contours."""
    try:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY) if len(image.shape) == 3 else image
        
        # Threshold the image
        _, binary = cv2.threshold(gray, threshold_value, 255, cv2.THRESH_BINARY)
        
        # Find contours with hierarchy
        contours, hierarchy = cv2.findContours(binary, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)
        
        result = image.copy()
        cv2.drawContours(result, contours, -1, (0, 255, 0), 2)
        
        return result
    except Exception as e:
        st.error(f"Error detecting hierarchical contours: {e}")
        return None


def detect_hough_lines(image: np.ndarray, rho: int, theta: int, threshold: int) -> Optional[np.ndarray]:
    """Detect Hough lines."""
    try:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY) if len(image.shape) == 3 else image
        
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
    except Exception as e:
        st.error(f"Error detecting Hough lines: {e}")
        return None


def detect_probabilistic_hough_lines(image: np.ndarray, rho: int, theta: int, threshold: int, min_line_length: int, max_line_gap: int) -> Optional[np.ndarray]:
    """Detect probabilistic Hough lines."""
    try:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY) if len(image.shape) == 3 else image
        
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
    except Exception as e:
        st.error(f"Error detecting probabilistic Hough lines: {e}")
        return None


# Color Processing Section
def color_processing_section(image: np.ndarray):
    """Color Processing section with interactive widgets."""
    st.header("🎨 Color Processing")
    
    if not validate_image(image):
        st.warning("Please upload an image to start experimenting with color processing.")
        return
    
    # Color Space Conversion
    st.subheader("🌈 Color Space Conversion")
    colorspace_col1, colorspace_col2 = st.columns(2)
    
    with colorspace_col1:
        target_colorspace = st.selectbox(
            "Target Color Space",
            ["HSV", "LAB", "YUV", "XYZ", "Grayscale"],
            help="Choose target color space"
        )
        
        if st.button("Convert Color Space"):
            processed = convert_colorspace(image, target_colorspace)
            if processed is not None:
                display_comparison(image, processed, "Original", f"{target_colorspace} Color Space")
                create_download_button(processed, f"{target_colorspace.lower()}_colorspace.png", f"Download {target_colorspace} Image")
    
    st.divider()
    
    # Histogram Equalization
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
                processed = apply_global_equalization(image)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Global Equalization")
                    create_download_button(processed, "global_equalization.png", "Download Global Equalization Image")
        
        elif equalization_method == "CLAHE":
            clip_limit = st.slider("Clip Limit", 1.0, 10.0, 2.0, 0.5)
            tile_grid_size = st.slider("Tile Grid Size", 2, 16, 8, 2)
            
            if st.button("Apply CLAHE"):
                processed = apply_clahe_equalization(image, clip_limit, tile_grid_size)
                if processed is not None:
                    display_comparison(image, processed, "Original", "CLAHE Equalization")
                    create_download_button(processed, "clahe_equalization.png", "Download CLAHE Equalization Image")
        
        elif equalization_method == "Adaptive":
            if st.button("Apply Adaptive Equalization"):
                processed = apply_adaptive_equalization(image)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Adaptive Equalization")
                    create_download_button(processed, "adaptive_equalization.png", "Download Adaptive Equalization Image")
    
    st.divider()
    
    # Color Enhancement
    st.subheader("✨ Color Enhancement")
    enhancement_col1, enhancement_col2 = st.columns(2)
    
    with enhancement_col1:
        enhancement_method = st.selectbox(
            "Enhancement Method",
            ["Brightness", "Contrast", "Saturation", "Gamma Correction", "Color Balance"],
            help="Choose color enhancement method"
        )
        
        if enhancement_method == "Brightness":
            brightness_factor = st.slider("Brightness Factor", -100, 100, 0, 5)
            
            if st.button("Apply Brightness Adjustment"):
                processed = adjust_brightness(image, brightness_factor)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Brightness Adjusted")
                    create_download_button(processed, "brightness_adjusted.png", "Download Brightness Adjusted Image")
        
        elif enhancement_method == "Contrast":
            contrast_factor = st.slider("Contrast Factor", 0.1, 3.0, 1.0, 0.1)
            
            if st.button("Apply Contrast Adjustment"):
                processed = adjust_contrast(image, contrast_factor)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Contrast Adjusted")
                    create_download_button(processed, "contrast_adjusted.png", "Download Contrast Adjusted Image")
        
        elif enhancement_method == "Saturation":
            saturation_factor = st.slider("Saturation Factor", 0.0, 3.0, 1.0, 0.1)
            
            if st.button("Apply Saturation Adjustment"):
                processed = adjust_saturation(image, saturation_factor)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Saturation Adjusted")
                    create_download_button(processed, "saturation_adjusted.png", "Download Saturation Adjusted Image")
        
        elif enhancement_method == "Gamma Correction":
            gamma = st.slider("Gamma", 0.1, 3.0, 1.0, 0.1)
            
            if st.button("Apply Gamma Correction"):
                processed = apply_gamma_correction(image, gamma)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Gamma Corrected")
                    create_download_button(processed, "gamma_corrected.png", "Download Gamma Corrected Image")
        
        elif enhancement_method == "Color Balance":
            red_factor = st.slider("Red Factor", 0.0, 2.0, 1.0, 0.1)
            green_factor = st.slider("Green Factor", 0.0, 2.0, 1.0, 0.1)
            blue_factor = st.slider("Blue Factor", 0.0, 2.0, 1.0, 0.1)
            
            if st.button("Apply Color Balance"):
                processed = apply_color_balance(image, red_factor, green_factor, blue_factor)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Color Balanced")
                    create_download_button(processed, "color_balanced.png", "Download Color Balanced Image")
    
    st.divider()
    
    # Color Segmentation
    st.subheader("🎯 Color Segmentation")
    segmentation_col1, segmentation_col2 = st.columns(2)
    
    with segmentation_col1:
        segmentation_method = st.selectbox(
            "Segmentation Method",
            ["HSV Range", "K-Means", "Mean Shift", "GrabCut"],
            help="Choose color segmentation method"
        )
        
        if segmentation_method == "HSV Range":
            h_min = st.slider("Hue Min", 0, 179, 0, 1)
            h_max = st.slider("Hue Max", 0, 179, 179, 1)
            s_min = st.slider("Saturation Min", 0, 255, 0, 5)
            s_max = st.slider("Saturation Max", 0, 255, 255, 5)
            v_min = st.slider("Value Min", 0, 255, 0, 5)
            v_max = st.slider("Value Max", 0, 255, 255, 5)
            
            if st.button("Apply HSV Segmentation"):
                processed = apply_hsv_segmentation(image, h_min, h_max, s_min, s_max, v_min, v_max)
                if processed is not None:
                    display_comparison(image, processed, "Original", "HSV Segmented")
                    create_download_button(processed, "hsv_segmented.png", "Download HSV Segmented Image")
        
        elif segmentation_method == "K-Means":
            k_clusters = st.slider("Number of Clusters", 2, 10, 3, 1)
            
            if st.button("Apply K-Means Segmentation"):
                processed = apply_kmeans_segmentation(image, k_clusters)
                if processed is not None:
                    display_comparison(image, processed, "Original", "K-Means Segmented")
                    create_download_button(processed, "kmeans_segmented.png", "Download K-Means Segmented Image")
        
        elif segmentation_method == "Mean Shift":
            spatial_radius = st.slider("Spatial Radius", 10, 50, 20, 5)
            color_radius = st.slider("Color Radius", 10, 50, 20, 5)
            
            if st.button("Apply Mean Shift Segmentation"):
                processed = apply_mean_shift_segmentation(image, spatial_radius, color_radius)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Mean Shift Segmented")
                    create_download_button(processed, "mean_shift_segmented.png", "Download Mean Shift Segmented Image")
        
        elif segmentation_method == "GrabCut":
            if st.button("Apply GrabCut Segmentation"):
                processed = apply_grabcut_segmentation(image)
                if processed is not None:
                    display_comparison(image, processed, "Original", "GrabCut Segmented")
                    create_download_button(processed, "grabcut_segmented.png", "Download GrabCut Segmented Image")


# Helper functions for color processing
def convert_colorspace(image: np.ndarray, target_colorspace: str) -> Optional[np.ndarray]:
    """Convert image to target color space."""
    try:
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
    except Exception as e:
        st.error(f"Error converting color space: {e}")
        return None


def apply_global_equalization(image: np.ndarray) -> Optional[np.ndarray]:
    """Apply global histogram equalization."""
    try:
        # Convert to YUV for better equalization
        yuv = cv2.cvtColor(image, cv2.COLOR_BGR2YUV)
        
        # Equalize the Y channel
        yuv[:,:,0] = cv2.equalizeHist(yuv[:,:,0])
        
        # Convert back to BGR
        equalized = cv2.cvtColor(yuv, cv2.COLOR_YUV2BGR)
        
        return equalized
    except Exception as e:
        st.error(f"Error applying global equalization: {e}")
        return None


def apply_clahe_equalization(image: np.ndarray, clip_limit: float, tile_grid_size: int) -> Optional[np.ndarray]:
    """Apply CLAHE histogram equalization."""
    try:
        # Convert to LAB for better equalization
        lab = cv2.cvtColor(image, cv2.COLOR_BGR2LAB)
        
        # Apply CLAHE to L channel
        clahe = cv2.createCLAHE(clipLimit=clip_limit, tileGridSize=(tile_grid_size, tile_grid_size))
        lab[:,:,0] = clahe.apply(lab[:,:,0])
        
        # Convert back to BGR
        equalized = cv2.cvtColor(lab, cv2.COLOR_LAB2BGR)
        
        return equalized
    except Exception as e:
        st.error(f"Error applying CLAHE equalization: {e}")
        return None


def apply_adaptive_equalization(image: np.ndarray) -> Optional[np.ndarray]:
    """Apply adaptive histogram equalization."""
    try:
        # Convert to grayscale
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        
        # Apply adaptive equalization
        clahe = cv2.createCLAHE(clipLimit=2.0, tileGridSize=(8, 8))
        equalized = clahe.apply(gray)
        
        # Convert back to BGR
        result = cv2.cvtColor(equalized, cv2.COLOR_GRAY2BGR)
        
        return result
    except Exception as e:
        st.error(f"Error applying adaptive equalization: {e}")
        return None


def adjust_brightness(image: np.ndarray, factor: int) -> Optional[np.ndarray]:
    """Adjust image brightness."""
    try:
        # Convert to HSV
        hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)
        
        # Adjust V channel
        hsv[:,:,2] = cv2.add(hsv[:,:,2], factor)
        
        # Ensure values are in valid range
        hsv[:,:,2] = np.clip(hsv[:,:,2], 0, 255)
        
        # Convert back to BGR
        result = cv2.cvtColor(hsv, cv2.COLOR_HSV2BGR)
        
        return result
    except Exception as e:
        st.error(f"Error adjusting brightness: {e}")
        return None


def adjust_contrast(image: np.ndarray, factor: float) -> Optional[np.ndarray]:
    """Adjust image contrast."""
    try:
        # Apply contrast adjustment
        result = cv2.convertScaleAbs(image, alpha=factor, beta=0)
        
        return result
    except Exception as e:
        st.error(f"Error adjusting contrast: {e}")
        return None


def adjust_saturation(image: np.ndarray, factor: float) -> Optional[np.ndarray]:
    """Adjust image saturation."""
    try:
        # Convert to HSV
        hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)
        
        # Adjust S channel
        hsv[:,:,1] = cv2.multiply(hsv[:,:,1], factor)
        
        # Ensure values are in valid range
        hsv[:,:,1] = np.clip(hsv[:,:,1], 0, 255)
        
        # Convert back to BGR
        result = cv2.cvtColor(hsv, cv2.COLOR_HSV2BGR)
        
        return result
    except Exception as e:
        st.error(f"Error adjusting saturation: {e}")
        return None


def apply_gamma_correction(image: np.ndarray, gamma: float) -> Optional[np.ndarray]:
    """Apply gamma correction to image."""
    try:
        # Build lookup table
        inv_gamma = 1.0 / gamma
        table = np.array([((i / 255.0) ** inv_gamma) * 255 for i in np.arange(0, 256)]).astype("uint8")
        
        # Apply gamma correction
        result = cv2.LUT(image, table)
        
        return result
    except Exception as e:
        st.error(f"Error applying gamma correction: {e}")
        return None


def apply_color_balance(image: np.ndarray, red_factor: float, green_factor: float, blue_factor: float) -> Optional[np.ndarray]:
    """Apply color balance to image."""
    try:
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
    except Exception as e:
        st.error(f"Error applying color balance: {e}")
        return None


def apply_hsv_segmentation(image: np.ndarray, h_min: int, h_max: int, s_min: int, s_max: int, v_min: int, v_max: int) -> Optional[np.ndarray]:
    """Apply HSV-based color segmentation."""
    try:
        # Convert to HSV
        hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)
        
        # Create mask
        lower = np.array([h_min, s_min, v_min])
        upper = np.array([h_max, s_max, v_max])
        mask = cv2.inRange(hsv, lower, upper)
        
        # Apply mask
        result = cv2.bitwise_and(image, image, mask=mask)
        
        return result
    except Exception as e:
        st.error(f"Error applying HSV segmentation: {e}")
        return None


def apply_kmeans_segmentation(image: np.ndarray, k: int) -> Optional[np.ndarray]:
    """Apply K-means color segmentation."""
    try:
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
    except Exception as e:
        st.error(f"Error applying K-means segmentation: {e}")
        return None


def apply_mean_shift_segmentation(image: np.ndarray, spatial_radius: int, color_radius: int) -> Optional[np.ndarray]:
    """Apply mean shift segmentation."""
    try:
        # Apply mean shift filtering
        shifted = cv2.pyrMeanShiftFiltering(image, spatial_radius, color_radius)
        
        return shifted
    except Exception as e:
        st.error(f"Error applying mean shift segmentation: {e}")
        return None


def apply_grabcut_segmentation(image: np.ndarray) -> Optional[np.ndarray]:
    """Apply GrabCut segmentation."""
    try:
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
    except Exception as e:
        st.error(f"Error applying GrabCut segmentation: {e}")
        return None


# Advanced Techniques Section
def advanced_techniques_section(image: np.ndarray):
    """Advanced Techniques section with interactive widgets."""
    st.header("⚡ Advanced Techniques")
    
    if not validate_image(image):
        st.warning("Please upload an image to start experimenting with advanced techniques.")
        return
    
    # Fourier Transform
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
                processed = show_magnitude_spectrum(image)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Magnitude Spectrum")
                    create_download_button(processed, "magnitude_spectrum.png", "Download Magnitude Spectrum")
        
        elif fourier_operation == "Phase Spectrum":
            if st.button("Show Phase Spectrum"):
                processed = show_phase_spectrum(image)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Phase Spectrum")
                    create_download_button(processed, "phase_spectrum.png", "Download Phase Spectrum")
        
        elif fourier_operation == "Low Pass Filter":
            cutoff_radius = st.slider("Cutoff Radius", 10, 100, 30, 5)
            if st.button("Apply Low Pass Filter"):
                processed = apply_low_pass_filter(image, cutoff_radius)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Low Pass Filtered")
                    create_download_button(processed, "low_pass_filtered.png", "Download Low Pass Filtered Image")
        
        elif fourier_operation == "High Pass Filter":
            cutoff_radius = st.slider("Cutoff Radius", 10, 100, 30, 5)
            if st.button("Apply High Pass Filter"):
                processed = apply_high_pass_filter(image, cutoff_radius)
                if processed is not None:
                    display_comparison(image, processed, "Original", "High Pass Filtered")
                    create_download_button(processed, "high_pass_filtered.png", "Download High Pass Filtered Image")
        
        elif fourier_operation == "Band Pass Filter":
            low_cutoff = st.slider("Low Cutoff", 5, 50, 10, 5)
            high_cutoff = st.slider("High Cutoff", 20, 100, 50, 5)
            if st.button("Apply Band Pass Filter"):
                processed = apply_band_pass_filter(image, low_cutoff, high_cutoff)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Band Pass Filtered")
                    create_download_button(processed, "band_pass_filtered.png", "Download Band Pass Filtered Image")
    
    st.divider()
    
    # Image Segmentation
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
                processed = apply_kmeans_segmentation_advanced(image, k_clusters)
                if processed is not None:
                    display_comparison(image, processed, "Original", "K-Means Segmented")
                    create_download_button(processed, "kmeans_segmented.png", "Download K-Means Segmented Image")
        
        elif segmentation_method == "Mean Shift":
            spatial_radius = st.slider("Spatial Radius", 10, 50, 20, 5)
            color_radius = st.slider("Color Radius", 10, 50, 20, 5)
            if st.button("Apply Mean Shift Segmentation"):
                processed = apply_mean_shift_segmentation_advanced(image, spatial_radius, color_radius)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Mean Shift Segmented")
                    create_download_button(processed, "mean_shift_segmented.png", "Download Mean Shift Segmented Image")
        
        elif segmentation_method == "Watershed":
            if st.button("Apply Watershed Segmentation"):
                processed = apply_watershed_segmentation(image)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Watershed Segmented")
                    create_download_button(processed, "watershed_segmented.png", "Download Watershed Segmented Image")
        
        elif segmentation_method == "GrabCut":
            if st.button("Apply GrabCut Segmentation"):
                processed = apply_grabcut_segmentation_advanced(image)
                if processed is not None:
                    display_comparison(image, processed, "Original", "GrabCut Segmented")
                    create_download_button(processed, "grabcut_segmented.png", "Download GrabCut Segmented Image")
        
        elif segmentation_method == "SLIC Superpixels":
            num_segments = st.slider("Number of Segments", 50, 500, 100, 50)
            compactness = st.slider("Compactness", 1, 50, 10, 1)
            if st.button("Apply SLIC Superpixels"):
                processed = apply_slic_superpixels(image, num_segments, compactness)
                if processed is not None:
                    display_comparison(image, processed, "Original", "SLIC Superpixels")
                    create_download_button(processed, "slic_superpixels.png", "Download SLIC Superpixels Image")
    
    st.divider()
    
    # Template Matching
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
            processed = apply_template_matching(image, template_method)
            if processed is not None:
                display_comparison(image, processed, "Original", "Template Matching")
                create_download_button(processed, "template_matching.png", "Download Template Matching Image")
    
    st.divider()
    
    # Machine Learning Integration
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
                processed = detect_faces_ml(image)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Face Detection")
                    create_download_button(processed, "face_detection.png", "Download Face Detection Image")
        
        elif ml_method == "Object Detection":
            if st.button("Detect Objects"):
                processed = detect_objects_ml(image)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Object Detection")
                    create_download_button(processed, "object_detection.png", "Download Object Detection Image")
        
        elif ml_method == "Image Classification":
            if st.button("Classify Image"):
                result = classify_image_ml(image)
                if result is not None:
                    st.success(f"Classification Result: {result}")


# Helper functions for advanced techniques
def show_magnitude_spectrum(image: np.ndarray) -> Optional[np.ndarray]:
    """Show magnitude spectrum of image."""
    try:
        # Convert to grayscale
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY) if len(image.shape) == 3 else image
        
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
    except Exception as e:
        st.error(f"Error showing magnitude spectrum: {e}")
        return None


def show_phase_spectrum(image: np.ndarray) -> Optional[np.ndarray]:
    """Show phase spectrum of image."""
    try:
        # Convert to grayscale
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY) if len(image.shape) == 3 else image
        
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
    except Exception as e:
        st.error(f"Error showing phase spectrum: {e}")
        return None


def apply_low_pass_filter(image: np.ndarray, cutoff_radius: int) -> Optional[np.ndarray]:
    """Apply low pass filter to image."""
    try:
        # Convert to grayscale
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY) if len(image.shape) == 3 else image
        
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
    except Exception as e:
        st.error(f"Error applying low pass filter: {e}")
        return None


def apply_high_pass_filter(image: np.ndarray, cutoff_radius: int) -> Optional[np.ndarray]:
    """Apply high pass filter to image."""
    try:
        # Convert to grayscale
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY) if len(image.shape) == 3 else image
        
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
    except Exception as e:
        st.error(f"Error applying high pass filter: {e}")
        return None


def apply_band_pass_filter(image: np.ndarray, low_cutoff: int, high_cutoff: int) -> Optional[np.ndarray]:
    """Apply band pass filter to image."""
    try:
        # Convert to grayscale
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY) if len(image.shape) == 3 else image
        
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
    except Exception as e:
        st.error(f"Error applying band pass filter: {e}")
        return None


def apply_kmeans_segmentation_advanced(image: np.ndarray, k: int) -> Optional[np.ndarray]:
    """Apply K-means segmentation with advanced features."""
    try:
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
    except Exception as e:
        st.error(f"Error applying K-means segmentation: {e}")
        return None


def apply_mean_shift_segmentation_advanced(image: np.ndarray, spatial_radius: int, color_radius: int) -> Optional[np.ndarray]:
    """Apply mean shift segmentation with advanced features."""
    try:
        # Apply mean shift filtering
        shifted = cv2.pyrMeanShiftFiltering(image, spatial_radius, color_radius)
        
        return shifted
    except Exception as e:
        st.error(f"Error applying mean shift segmentation: {e}")
        return None


def apply_watershed_segmentation(image: np.ndarray) -> Optional[np.ndarray]:
    """Apply watershed segmentation."""
    try:
        # Convert to grayscale
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY) if len(image.shape) == 3 else image
        
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
    except Exception as e:
        st.error(f"Error applying watershed segmentation: {e}")
        return None


def apply_grabcut_segmentation_advanced(image: np.ndarray) -> Optional[np.ndarray]:
    """Apply GrabCut segmentation with advanced features."""
    try:
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
    except Exception as e:
        st.error(f"Error applying GrabCut segmentation: {e}")
        return None


def apply_slic_superpixels(image: np.ndarray, num_segments: int, compactness: int) -> Optional[np.ndarray]:
    """Apply SLIC superpixels segmentation."""
    try:
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
    except Exception as e:
        st.error(f"Error applying SLIC superpixels: {e}")
        return None


def apply_template_matching(image: np.ndarray, method: str) -> Optional[np.ndarray]:
    """Apply template matching."""
    try:
        # Convert to grayscale
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY) if len(image.shape) == 3 else image
        
        # Create a simple template (top-left quarter of the image)
        height, width = gray.shape
        template = gray[0:height//4, 0:width//4]
        
        # Apply template matching
        method_map = {
            "TM_CCOEFF": cv2.TM_CCOEFF,
            "TM_CCOEFF_NORMED": cv2.TM_CCOEFF_NORMED,
            "TM_CCORR": cv2.TM_CCORR,
            "TM_CCORR_NORMED": cv2.TM_CCORR_NORMED,
            "TM_SQDIFF": cv2.TM_SQDIFF,
            "TM_SQDIFF_NORMED": cv2.TM_SQDIFF_NORMED
        }
        
        result = cv2.matchTemplate(gray, template, method_map[method])
        
        # Find the best match
        min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(result)
        
        # Draw rectangle around the match
        result_img = image.copy()
        h, w = template.shape
        if method in ["TM_SQDIFF", "TM_SQDIFF_NORMED"]:
            top_left = min_loc
        else:
            top_left = max_loc
        bottom_right = (top_left[0] + w, top_left[1] + h)
        cv2.rectangle(result_img, top_left, bottom_right, (0, 255, 0), 2)
        
        return result_img
    except Exception as e:
        st.error(f"Error applying template matching: {e}")
        return None


def detect_faces_ml(image: np.ndarray) -> Optional[np.ndarray]:
    """Detect faces using machine learning."""
    try:
        # Load pre-trained face detection model
        face_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + 'haarcascade_frontalface_default.xml')
        
        # Convert to grayscale
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        
        # Detect faces
        faces = face_cascade.detectMultiScale(gray, 1.1, 4)
        
        # Draw rectangles around faces
        result = image.copy()
        for (x, y, w, h) in faces:
            cv2.rectangle(result, (x, y), (x+w, y+h), (255, 0, 0), 2)
        
        return result
    except Exception as e:
        st.error(f"Error detecting faces: {e}")
        return None


def detect_objects_ml(image: np.ndarray) -> Optional[np.ndarray]:
    """Detect objects using machine learning."""
    try:
        # For demo purposes, we'll use a simple edge-based approach
        # In a real application, you would use a pre-trained model like YOLO or SSD
        
        # Convert to grayscale
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        
        # Apply edge detection
        edges = cv2.Canny(gray, 50, 150)
        
        # Find contours
        contours, _ = cv2.findContours(edges, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
        
        # Draw contours
        result = image.copy()
        cv2.drawContours(result, contours, -1, (0, 255, 0), 2)
        
        return result
    except Exception as e:
        st.error(f"Error detecting objects: {e}")
        return None


def classify_image_ml(image: np.ndarray) -> Optional[str]:
    """Classify image using machine learning."""
    try:
        # For demo purposes, we'll return a simple classification
        # In a real application, you would use a pre-trained model
        
        # Simple rule-based classification based on image properties
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        mean_brightness = np.mean(gray)
        
        if mean_brightness < 85:
            return "Dark Image"
        elif mean_brightness < 170:
            return "Medium Brightness Image"
        else:
            return "Bright Image"
    except Exception as e:
        st.error(f"Error classifying image: {e}")
        return None


# Practical Applications Section
def practical_applications_section(image: np.ndarray):
    """Practical Applications section with interactive widgets."""
    st.header("🏗️ Practical Applications")
    
    if not validate_image(image):
        st.warning("Please upload an image to start experimenting with practical applications.")
        return
    
    # Face Detection
    st.subheader("👤 Face Detection")
    face_col1, face_col2 = st.columns(2)
    
    with face_col1:
        face_method = st.selectbox(
            "Face Detection Method",
            ["Haar Cascade", "HOG", "DNN"],
            help="Choose face detection method"
        )
        
        if face_method == "Haar Cascade":
            scale_factor = st.slider("Scale Factor", 1.01, 1.5, 1.1, 0.01)
            min_neighbors = st.slider("Min Neighbors", 1, 10, 4, 1)
            
            if st.button("Detect Faces (Haar)"):
                processed = detect_faces_haar(image, scale_factor, min_neighbors)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Face Detection (Haar)")
                    create_download_button(processed, "face_detection_haar.png", "Download Face Detection Image")
        
        elif face_method == "HOG":
            if st.button("Detect Faces (HOG)"):
                processed = detect_faces_hog(image)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Face Detection (HOG)")
                    create_download_button(processed, "face_detection_hog.png", "Download Face Detection Image")
        
        elif face_method == "DNN":
            confidence_threshold = st.slider("Confidence Threshold", 0.1, 1.0, 0.5, 0.1)
            if st.button("Detect Faces (DNN)"):
                processed = detect_faces_dnn(image, confidence_threshold)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Face Detection (DNN)")
                    create_download_button(processed, "face_detection_dnn.png", "Download Face Detection Image")
    
    st.divider()
    
    # Object Recognition
    st.subheader("🎯 Object Recognition")
    object_col1, object_col2 = st.columns(2)
    
    with object_col1:
        object_method = st.selectbox(
            "Object Recognition Method",
            ["Contour Detection", "Template Matching", "Feature Matching"],
            help="Choose object recognition method"
        )
        
        if object_method == "Contour Detection":
            threshold_value = st.slider("Threshold Value", 0, 255, 127, 5)
            if st.button("Detect Objects (Contours)"):
                processed = detect_objects_contours(image, threshold_value)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Object Detection (Contours)")
                    create_download_button(processed, "object_detection_contours.png", "Download Object Detection Image")
        
        elif object_method == "Template Matching":
            if st.button("Detect Objects (Template)"):
                processed = detect_objects_template(image)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Object Detection (Template)")
                    create_download_button(processed, "object_detection_template.png", "Download Object Detection Image")
        
        elif object_method == "Feature Matching":
            if st.button("Detect Objects (Features)"):
                processed = detect_objects_features(image)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Object Detection (Features)")
                    create_download_button(processed, "object_detection_features.png", "Download Object Detection Image")
    
    st.divider()
    
    # Image Stitching
    st.subheader("🖼️ Image Stitching")
    stitch_col1, stitch_col2 = st.columns(2)
    
    with stitch_col1:
        st.info("Image stitching requires multiple images. This is a demo with the current image.")
        
        if st.button("Demo Image Stitching"):
            processed = demo_image_stitching(image)
            if processed is not None:
                display_comparison(image, processed, "Original", "Stitched Image Demo")
                create_download_button(processed, "stitched_image_demo.png", "Download Stitched Image Demo")
    
    st.divider()
    
    # Video Processing
    st.subheader("🎬 Video Processing")
    video_col1, video_col2 = st.columns(2)
    
    with video_col1:
        video_operation = st.selectbox(
            "Video Operation",
            ["Frame Extraction", "Motion Detection", "Background Subtraction"],
            help="Choose video processing operation"
        )
        
        if video_operation == "Frame Extraction":
            if st.button("Extract Frame"):
                processed = extract_frame_demo(image)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Extracted Frame")
                    create_download_button(processed, "extracted_frame.png", "Download Extracted Frame")
        
        elif video_operation == "Motion Detection":
            if st.button("Detect Motion"):
                processed = detect_motion_demo(image)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Motion Detection")
                    create_download_button(processed, "motion_detection.png", "Download Motion Detection Image")
        
        elif video_operation == "Background Subtraction":
            if st.button("Subtract Background"):
                processed = subtract_background_demo(image)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Background Subtraction")
                    create_download_button(processed, "background_subtraction.png", "Download Background Subtraction Image")
    
    st.divider()
    
    # OCR (Optical Character Recognition)
    st.subheader("📝 OCR (Optical Character Recognition)")
    ocr_col1, ocr_col2 = st.columns(2)
    
    with ocr_col1:
        ocr_method = st.selectbox(
            "OCR Method",
            ["Tesseract", "EasyOCR", "PaddleOCR"],
            help="Choose OCR method"
        )
        
        if st.button("Perform OCR"):
            result = perform_ocr_demo(image, ocr_method)
            if result is not None:
                st.success(f"OCR Result: {result}")
    
    st.divider()
    
    # Medical Image Processing
    st.subheader("🏥 Medical Image Processing")
    medical_col1, medical_col2 = st.columns(2)
    
    with medical_col1:
        medical_operation = st.selectbox(
            "Medical Operation",
            ["Tumor Detection", "Blood Cell Counting", "X-ray Enhancement"],
            help="Choose medical image processing operation"
        )
        
        if medical_operation == "Tumor Detection":
            if st.button("Detect Tumors"):
                processed = detect_tumors_demo(image)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Tumor Detection")
                    create_download_button(processed, "tumor_detection.png", "Download Tumor Detection Image")
        
        elif medical_operation == "Blood Cell Counting":
            if st.button("Count Blood Cells"):
                processed = count_blood_cells_demo(image)
                if processed is not None:
                    display_comparison(image, processed, "Original", "Blood Cell Counting")
                    create_download_button(processed, "blood_cell_counting.png", "Download Blood Cell Counting Image")
        
        elif medical_operation == "X-ray Enhancement":
            if st.button("Enhance X-ray"):
                processed = enhance_xray_demo(image)
                if processed is not None:
                    display_comparison(image, processed, "Original", "X-ray Enhancement")
                    create_download_button(processed, "xray_enhancement.png", "Download X-ray Enhancement Image")


# Helper functions for practical applications
def detect_faces_haar(image: np.ndarray, scale_factor: float, min_neighbors: int) -> Optional[np.ndarray]:
    """Detect faces using Haar cascade."""
    try:
        # Load pre-trained face detection model
        face_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + 'haarcascade_frontalface_default.xml')
        
        # Convert to grayscale
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        
        # Detect faces
        faces = face_cascade.detectMultiScale(gray, scale_factor, min_neighbors)
        
        # Draw rectangles around faces
        result = image.copy()
        for (x, y, w, h) in faces:
            cv2.rectangle(result, (x, y), (x+w, y+h), (255, 0, 0), 2)
        
        return result
    except Exception as e:
        st.error(f"Error detecting faces with Haar: {e}")
        return None


def detect_faces_hog(image: np.ndarray) -> Optional[np.ndarray]:
    """Detect faces using HOG."""
    try:
        # For demo purposes, we'll use a simple approach
        # In a real application, you would use dlib or similar library
        
        # Convert to grayscale
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        
        # Apply edge detection to simulate HOG-like features
        edges = cv2.Canny(gray, 50, 150)
        
        # Find contours
        contours, _ = cv2.findContours(edges, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
        
        # Draw contours
        result = image.copy()
        cv2.drawContours(result, contours, -1, (0, 255, 0), 2)
        
        return result
    except Exception as e:
        st.error(f"Error detecting faces with HOG: {e}")
        return None


def detect_faces_dnn(image: np.ndarray, confidence_threshold: float) -> Optional[np.ndarray]:
    """Detect faces using DNN."""
    try:
        # For demo purposes, we'll use a simple approach
        # In a real application, you would use a pre-trained DNN model
        
        # Convert to grayscale
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        
        # Apply Gaussian blur
        blurred = cv2.GaussianBlur(gray, (5, 5), 0)
        
        # Apply adaptive threshold
        thresh = cv2.adaptiveThreshold(blurred, 255, cv2.ADAPTIVE_THRESH_GAUSSIAN_C, cv2.THRESH_BINARY, 11, 2)
        
        # Find contours
        contours, _ = cv2.findContours(thresh, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
        
        # Draw contours
        result = image.copy()
        cv2.drawContours(result, contours, -1, (0, 0, 255), 2)
        
        return result
    except Exception as e:
        st.error(f"Error detecting faces with DNN: {e}")
        return None


def detect_objects_contours(image: np.ndarray, threshold_value: int) -> Optional[np.ndarray]:
    """Detect objects using contour detection."""
    try:
        # Convert to grayscale
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        
        # Apply threshold
        _, binary = cv2.threshold(gray, threshold_value, 255, cv2.THRESH_BINARY)
        
        # Find contours
        contours, _ = cv2.findContours(binary, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
        
        # Draw contours
        result = image.copy()
        cv2.drawContours(result, contours, -1, (0, 255, 0), 2)
        
        return result
    except Exception as e:
        st.error(f"Error detecting objects with contours: {e}")
        return None


def detect_objects_template(image: np.ndarray) -> Optional[np.ndarray]:
    """Detect objects using template matching."""
    try:
        # Convert to grayscale
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        
        # Create a simple template (top-left quarter of the image)
        height, width = gray.shape
        template = gray[0:height//4, 0:width//4]
        
        # Apply template matching
        result = cv2.matchTemplate(gray, template, cv2.TM_CCOEFF_NORMED)
        
        # Find the best match
        min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(result)
        
        # Draw rectangle around the match
        result_img = image.copy()
        h, w = template.shape
        top_left = max_loc
        bottom_right = (top_left[0] + w, top_left[1] + h)
        cv2.rectangle(result_img, top_left, bottom_right, (0, 255, 0), 2)
        
        return result_img
    except Exception as e:
        st.error(f"Error detecting objects with template matching: {e}")
        return None


def detect_objects_features(image: np.ndarray) -> Optional[np.ndarray]:
    """Detect objects using feature matching."""
    try:
        # Convert to grayscale
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        
        # Detect keypoints
        orb = cv2.ORB_create()
        keypoints = orb.detect(gray, None)
        
        # Draw keypoints
        result = image.copy()
        cv2.drawKeypoints(image, keypoints, result, color=(0, 255, 0))
        
        return result
    except Exception as e:
        st.error(f"Error detecting objects with feature matching: {e}")
        return None


def demo_image_stitching(image: np.ndarray) -> Optional[np.ndarray]:
    """Demo image stitching with the current image."""
    try:
        # For demo purposes, we'll create a simple effect
        # In a real application, you would stitch multiple images
        
        # Create a panoramic effect by duplicating the image
        height, width = image.shape[:2]
        
        # Create a wider canvas
        stitched = np.zeros((height, width * 2, 3), dtype=np.uint8)
        
        # Place the original image on the left
        stitched[:, :width] = image
        
        # Place a slightly modified version on the right
        modified = cv2.convertScaleAbs(image, alpha=0.8, beta=10)
        stitched[:, width:] = modified
        
        return stitched
    except Exception as e:
        st.error(f"Error in image stitching demo: {e}")
        return None


def extract_frame_demo(image: np.ndarray) -> Optional[np.ndarray]:
    """Demo frame extraction."""
    try:
        # For demo purposes, we'll return the image as a "frame"
        # In a real application, you would extract frames from a video
        
        return image.copy()
    except Exception as e:
        st.error(f"Error extracting frame: {e}")
        return None


def detect_motion_demo(image: np.ndarray) -> Optional[np.ndarray]:
    """Demo motion detection."""
    try:
        # For demo purposes, we'll simulate motion detection
        # In a real application, you would compare frames from a video
        
        # Convert to grayscale
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        
        # Apply edge detection to simulate motion
        edges = cv2.Canny(gray, 50, 150)
        
        # Create result image
        result = image.copy()
        result[edges > 0] = [0, 255, 0]  # Highlight edges in green
        
        return result
    except Exception as e:
        st.error(f"Error detecting motion: {e}")
        return None


def subtract_background_demo(image: np.ndarray) -> Optional[np.ndarray]:
    """Demo background subtraction."""
    try:
        # For demo purposes, we'll simulate background subtraction
        # In a real application, you would use a background model
        
        # Convert to grayscale
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        
        # Apply threshold to simulate background subtraction
        _, binary = cv2.threshold(gray, 127, 255, cv2.THRESH_BINARY)
        
        # Create result image
        result = image.copy()
        result[binary == 0] = [0, 0, 0]  # Set background to black
        
        return result
    except Exception as e:
        st.error(f"Error subtracting background: {e}")
        return None


def perform_ocr_demo(image: np.ndarray, method: str) -> Optional[str]:
    """Demo OCR processing."""
    try:
        # For demo purposes, we'll return a simple message
        # In a real application, you would use OCR libraries like Tesseract
        
        return f"OCR Demo using {method} - No text detected in this image"
    except Exception as e:
        st.error(f"Error performing OCR: {e}")
        return None


def detect_tumors_demo(image: np.ndarray) -> Optional[np.ndarray]:
    """Demo tumor detection."""
    try:
        # For demo purposes, we'll simulate tumor detection
        # In a real application, you would use medical image analysis
        
        # Convert to grayscale
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        
        # Apply Gaussian blur
        blurred = cv2.GaussianBlur(gray, (5, 5), 0)
        
        # Apply adaptive threshold
        thresh = cv2.adaptiveThreshold(blurred, 255, cv2.ADAPTIVE_THRESH_GAUSSIAN_C, cv2.THRESH_BINARY, 11, 2)
        
        # Find contours
        contours, _ = cv2.findContours(thresh, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
        
        # Draw contours
        result = image.copy()
        cv2.drawContours(result, contours, -1, (0, 0, 255), 2)
        
        return result
    except Exception as e:
        st.error(f"Error detecting tumors: {e}")
        return None


def count_blood_cells_demo(image: np.ndarray) -> Optional[np.ndarray]:
    """Demo blood cell counting."""
    try:
        # For demo purposes, we'll simulate blood cell counting
        # In a real application, you would use specialized medical image analysis
        
        # Convert to grayscale
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        
        # Apply Hough circle detection to simulate cell counting
        circles = cv2.HoughCircles(gray, cv2.HOUGH_GRADIENT, 1, 20, param1=50, param2=30, minRadius=5, maxRadius=50)
        
        # Draw circles
        result = image.copy()
        if circles is not None:
            circles = np.uint16(np.around(circles))
            for i in circles[0, :]:
                center = (i[0], i[1])
                radius = i[2]
                cv2.circle(result, center, radius, (0, 255, 0), 2)
                cv2.circle(result, center, 2, (0, 0, 255), 3)
        
        return result
    except Exception as e:
        st.error(f"Error counting blood cells: {e}")
        return None


def enhance_xray_demo(image: np.ndarray) -> Optional[np.ndarray]:
    """Demo X-ray enhancement."""
    try:
        # For demo purposes, we'll simulate X-ray enhancement
        # In a real application, you would use specialized medical image processing
        
        # Convert to grayscale
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        
        # Apply histogram equalization
        enhanced = cv2.equalizeHist(gray)
        
        # Apply CLAHE for better enhancement
        clahe = cv2.createCLAHE(clipLimit=2.0, tileGridSize=(8, 8))
        enhanced = clahe.apply(enhanced)
        
        # Convert back to BGR
        result = cv2.cvtColor(enhanced, cv2.COLOR_GRAY2BGR)
        
        return result
    except Exception as e:
        st.error(f"Error enhancing X-ray: {e}")
        return None 