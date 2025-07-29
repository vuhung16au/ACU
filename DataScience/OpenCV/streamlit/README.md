# OpenCV Interactive Dashboard

A comprehensive Streamlit dashboard for experimenting with OpenCV image processing techniques.

## 🚀 Features

### Currently Implemented
- **Basic Operations**: Complete interactive section with:
  - Image resizing (fixed size, scale factor, aspect ratio)
  - Image rotation (custom angle, 90° steps)
  - Image flipping (horizontal, vertical, both)
  - Image cropping (custom region, center crop)
  - Real-time image information display
  - Download processed images

### Coming Soon
- Image Filtering (Gaussian blur, edge detection, noise reduction)
- Transformations (affine, perspective, warping)
- Morphological Operations (erosion, dilation, opening, closing)
- Feature Detection (corner detection, keypoints, contours)
- Color Processing (color spaces, histogram equalization)
- Advanced Techniques (Fourier transform, segmentation)
- Practical Applications (face detection, object recognition)

## 📦 Installation

0. **Create a virtual environment**:
   ```bash
   python3.13 -m venv .venv
   source .venv/bin/activate
   ```

1. **Install dependencies**:
   ```bash
   pip install -r requirements.txt
   ```

2. **Run the dashboard**:
   ```bash
   streamlit run app.py
   ```

3. **Open your browser** and navigate to the URL shown in the terminal (usually `http://localhost:8501`)

## 🎯 How to Use

1. **Upload an image** using the file uploader in the sidebar
2. **Select a section** from the navigation menu
3. **Adjust parameters** using the interactive widgets
4. **View results** in real-time with side-by-side comparisons
5. **Download processed images** using the download buttons

## 🛠️ Technical Details

### Architecture
- **`app.py`**: Main Streamlit application with navigation and layout
- **`components.py`**: UI components for each processing section
- **`utils.py`**: Utility functions for image handling and display

### Dependencies
- `streamlit`: Web framework for the dashboard
- `opencv-python`: Image processing library
- `numpy`: Numerical computing
- `matplotlib`: Plotting and visualization
- `pillow`: Image handling
- `plotly`: Interactive charts (future use)

### Image Processing Features
- **Multiple resize methods**: Fixed size, scale factor, aspect ratio preservation
- **Advanced rotation**: Custom angles with different border handling modes
- **Flexible cropping**: Custom regions or center-based cropping
- **Real-time preview**: Instant feedback on parameter changes
- **Image validation**: Robust error handling for invalid inputs

## 🎨 UI Features

- **Responsive design**: Works on desktop and mobile devices
- **Sidebar navigation**: Easy access to all sections
- **Image preview**: See uploaded images in the sidebar
- **Parameter controls**: Intuitive sliders, dropdowns, and buttons
- **Download functionality**: Save processed images directly
- **Error handling**: Clear error messages for invalid operations

## 🔧 Development

### Adding New Sections
1. Create a new function in `components.py` (e.g., `image_filtering_section()`)
2. Add the section to the navigation in `app.py`
3. Implement the UI components and processing logic
4. Test with various image types and parameters

### Customization
- Modify CSS styles in `app.py` for different themes
- Add new utility functions in `utils.py`
- Extend image processing capabilities in `components.py`

## 📝 License

This project is part of the OpenCV Image Processing Collection by Vu Hung Nguyen.

## 🤝 Contributing

Feel free to contribute by:
- Adding new image processing techniques
- Improving the UI/UX
- Fixing bugs or adding features
- Writing documentation

---

**Author**: Vu Hung Nguyen  
**Framework**: Streamlit  
**Image Processing**: OpenCV  
**Version**: 1.0.0 