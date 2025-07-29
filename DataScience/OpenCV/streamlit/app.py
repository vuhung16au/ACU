"""
OpenCV Interactive Dashboard - Streamlit App

A comprehensive dashboard for experimenting with OpenCV image processing techniques.
Currently implements Basic Operations section with interactive widgets.
"""

import streamlit as st
import cv2
import numpy as np
from utils import load_image_from_upload, display_image, get_image_info
from component_sections import (
    basic_operations_section,
    image_filtering_section,
    transformations_section,
    morphological_operations_section,
    feature_detection_section,
    color_processing_section,
    advanced_techniques_section,
    practical_applications_section
)

# Page configuration
st.set_page_config(
    page_title="OpenCV Interactive Dashboard",
    page_icon="🔬",
    layout="wide",
    initial_sidebar_state="expanded"
)

# Custom CSS for better styling
st.markdown("""
<style>
    .main-header {
        font-size: 2.5rem;
        font-weight: bold;
        color: #1f77b4;
        text-align: center;
        margin-bottom: 2rem;
    }
    .section-header {
        font-size: 1.5rem;
        font-weight: bold;
        color: #2c3e50;
        margin-top: 2rem;
        margin-bottom: 1rem;
    }
    .metric-container {
        background-color: #f8f9fa;
        padding: 1rem;
        border-radius: 0.5rem;
        border-left: 4px solid #1f77b4;
    }
    .upload-container {
        background-color: #e8f4fd;
        padding: 2rem;
        border-radius: 0.5rem;
        text-align: center;
        border: 2px dashed #1f77b4;
    }
</style>
""", unsafe_allow_html=True)

def main():
    """Main application function."""
    
    # Header
    st.markdown('<h1 class="main-header">🔬 OpenCV Interactive Dashboard</h1>', unsafe_allow_html=True)
    st.markdown("---")
    
    # Sidebar for navigation
    st.sidebar.title("🎛️ Navigation")
    
    # Section selection
    sections = [
        "Basic Operations",
        "Image Filtering", 
        "Transformations",
        "Morphological Operations",
        "Feature Detection",
        "Color Processing",
        "Advanced Techniques",
        "Practical Applications"
    ]
    
    selected_section = st.sidebar.selectbox(
        "Choose a section:",
        sections,
        index=0
    )
    
    # Image upload section
    st.sidebar.markdown("---")
    st.sidebar.subheader("📁 Upload Image")
    
    uploaded_file = st.sidebar.file_uploader(
        "Choose an image file",
        type=['png', 'jpg', 'jpeg', 'bmp', 'tiff'],
        help="Upload an image to start experimenting"
    )
    
    # Load image
    image = None
    if uploaded_file is not None:
        image = load_image_from_upload(uploaded_file)
        
        if image is not None:
            # Display image info in sidebar
            st.sidebar.markdown("---")
            st.sidebar.subheader("📊 Image Info")
            info = get_image_info(image)
            
            st.sidebar.metric("Width", info.get("width", 0))
            st.sidebar.metric("Height", info.get("height", 0))
            st.sidebar.metric("Channels", info.get("channels", 0))
            
            # Display small preview in sidebar
            st.sidebar.markdown("---")
            st.sidebar.subheader("🖼️ Preview")
            display_image(image, "Uploaded Image", use_container_width=True)
    
    # Main content area
    if selected_section == "Basic Operations":
        if image is None:
            st.markdown("""
            <div class="upload-container">
                <h3>🚀 Welcome to OpenCV Interactive Dashboard!</h3>
                <p>Please upload an image using the sidebar to start experimenting with Basic Operations.</p>
                <p>Supported formats: PNG, JPG, JPEG, BMP, TIFF</p>
            </div>
            """, unsafe_allow_html=True)
        else:
            basic_operations_section(image)
    
    elif selected_section == "Image Filtering":
        if image is None:
            st.markdown("""
            <div class="upload-container">
                <h3>🔍 Image Filtering</h3>
                <p>Please upload an image using the sidebar to start experimenting with Image Filtering.</p>
                <p>Supported formats: PNG, JPG, JPEG, BMP, TIFF</p>
            </div>
            """, unsafe_allow_html=True)
        else:
            image_filtering_section(image)
    
    elif selected_section == "Transformations":
        if image is None:
            st.markdown("""
            <div class="upload-container">
                <h3>🔄 Transformations</h3>
                <p>Please upload an image using the sidebar to start experimenting with Transformations.</p>
                <p>Supported formats: PNG, JPG, JPEG, BMP, TIFF</p>
            </div>
            """, unsafe_allow_html=True)
        else:
            transformations_section(image)
    
    elif selected_section == "Morphological Operations":
        if image is None:
            st.markdown("""
            <div class="upload-container">
                <h3>🔧 Morphological Operations</h3>
                <p>Please upload an image using the sidebar to start experimenting with Morphological Operations.</p>
                <p>Supported formats: PNG, JPG, JPEG, BMP, TIFF</p>
            </div>
            """, unsafe_allow_html=True)
        else:
            morphological_operations_section(image)
    
    elif selected_section == "Feature Detection":
        if image is None:
            st.markdown("""
            <div class="upload-container">
                <h3>🎯 Feature Detection</h3>
                <p>Please upload an image using the sidebar to start experimenting with Feature Detection.</p>
                <p>Supported formats: PNG, JPG, JPEG, BMP, TIFF</p>
            </div>
            """, unsafe_allow_html=True)
        else:
            feature_detection_section(image)
    
    elif selected_section == "Color Processing":
        if image is None:
            st.markdown("""
            <div class="upload-container">
                <h3>🎨 Color Processing</h3>
                <p>Please upload an image using the sidebar to start experimenting with Color Processing.</p>
                <p>Supported formats: PNG, JPG, JPEG, BMP, TIFF</p>
            </div>
            """, unsafe_allow_html=True)
        else:
            color_processing_section(image)
    
    elif selected_section == "Advanced Techniques":
        if image is None:
            st.markdown("""
            <div class="upload-container">
                <h3>⚡ Advanced Techniques</h3>
                <p>Please upload an image using the sidebar to start experimenting with Advanced Techniques.</p>
                <p>Supported formats: PNG, JPG, JPEG, BMP, TIFF</p>
            </div>
            """, unsafe_allow_html=True)
        else:
            advanced_techniques_section(image)
    
    elif selected_section == "Practical Applications":
        if image is None:
            st.markdown("""
            <div class="upload-container">
                <h3>🏗️ Practical Applications</h3>
                <p>Please upload an image using the sidebar to start experimenting with Practical Applications.</p>
                <p>Supported formats: PNG, JPG, JPEG, BMP, TIFF</p>
            </div>
            """, unsafe_allow_html=True)
        else:
            practical_applications_section(image)
    
    # Footer
    st.markdown("---")
    st.markdown("""
    <div style="text-align: center; color: #666; font-size: 0.8rem;">
        <p>🔬 OpenCV Interactive Dashboard - Built with Streamlit</p>
        <p>Author: Vu Hung Nguyen</p>
    </div>
    """, unsafe_allow_html=True)

if __name__ == "__main__":
    main() 