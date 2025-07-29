# OpenCV Image Processing Techniques Collection

A comprehensive collection of OpenCV image processing techniques implemented in Python, designed as both a learning resource and practical reference guide.

## 🎯 Purpose

This repository provides:
- **Educational Content**: Step-by-step tutorials with clear explanations
- **Practical Examples**: Real-world image processing applications
- **Code Reference**: Well-documented, reusable functions
- **Interactive Learning**: Jupyter notebooks for hands-on experimentation
- **Web Application**: Interactive Streamlit dashboard for real-time experimentation

## 📋 Table of Contents

- [Installation](#installation)
- [Quick Start](#quick-start)
- [Module Overview](#module-overview)
- [Tutorials](#tutorials)
- [Examples](#examples)
- [Web Application](#web-application)
- [Verification Tools](#verification-tools)
- [License](#license)

## 🚀 Installation

### Prerequisites
- Python 3.7 or higher
- pip package manager

### Install Dependencies
```bash
pip install -r requirements.txt
```

### Development Setup
```bash
# Clone the repository
git clone <repository-url>
cd OpenCV

# Install in development mode
pip install -e .

# Install additional development dependencies
pip install pytest black flake8 jupyter
```

### Validate Installation
After installation, you can validate that all modules can be imported successfully:

```bash
# Run the validation script
python validate_src_modules.py

# Or with virtual environment
source .venv/bin/activate
python validate_src_modules.py
```

The validation script checks:
- ✅ All required dependencies (OpenCV, NumPy, Matplotlib, SciPy, scikit-image, scikit-learn)
- ✅ All Python modules can be imported without errors
- ✅ Package structure integrity
- ✅ Relative import compatibility

### Verify Examples
To ensure all example scripts can run without errors:

```bash
# Using the shell script (recommended)
./verify_examples.sh

# Or manually with virtual environment
source venv/bin/activate
python verify_examples.py
```

The verification system:
- ✅ Tests all Python files in the `examples/` directory
- ✅ Sets up proper import paths automatically
- ✅ Provides timeout protection for complex demos
- ✅ Reports detailed error messages for debugging
- ✅ Shows summary of passed/failed files

For more details, see [VERIFICATION_README.md](VERIFICATION_README.md).

### Verify Notebooks
To ensure all Jupyter notebooks can execute without errors:

```bash
# Test all notebooks
make verify-notebooks

# Quick verification with shorter timeout
make verify-notebooks-quick

# Verbose output for debugging
make verify-notebooks-verbose

# Test setup and dependencies
make test-setup
```

The notebook verification system:
- ✅ Executes all notebooks in the `notebooks/` directory
- ✅ Reports execution time and any errors
- ✅ Provides detailed error reporting for debugging
- ✅ Supports excluding specific notebooks during development
- ✅ Can generate JSON reports for CI/CD integration

For more details, see [NOTEBOOK_VERIFICATION.md](NOTEBOOK_VERIFICATION.md).
- ✅ All Python modules can be imported without errors
- ✅ Package structure integrity
- ✅ Relative import compatibility

## ⚡ Quick Start

```python
import cv2
import numpy as np
from src.basic_operations import image_io, display
from src.filtering import smoothing, edge_detection

# Load an image
image = image_io.load_image('sample_images/original/sample.jpg')

# Apply Gaussian blur
blurred = smoothing.gaussian_blur(image, kernel_size=15, sigma=2.0)

# Detect edges
edges = edge_detection.canny_edge_detection(image, low_threshold=50, high_threshold=150)

# Display results
display.show_comparison([image, blurred, edges], 
                       titles=['Original', 'Blurred', 'Edges'])
```

## 📚 Module Overview

### Basic Operations
- **Image I/O**: Reading, writing, and manipulating images
- **Display**: Visualization utilities for images and results
- **Basic Transforms**: Resize, rotate, flip, and crop operations

### Image Filtering
- **Smoothing**: Gaussian, box, median, and bilateral filters
- **Edge Detection**: Canny, Sobel, Laplacian, and Scharr operators
- **Noise Reduction**: Denoising techniques and morphological filtering

### Transformations
- **Affine Transforms**: Translation, rotation, scaling, and shearing
- **Perspective Transforms**: Homography and image rectification
- **Image Warping**: Distortion correction and custom warping

### Morphological Operations
- **Basic Operations**: Erosion, dilation, opening, and closing
- **Advanced Operations**: Morphological gradients and skeletonization

### Feature Detection
- **Corner Detection**: Harris, Shi-Tomasi, and FAST detectors
- **Keypoint Detection**: SIFT, SURF, and ORB algorithms
- **Contour Analysis**: Finding and analyzing object contours

### Color Processing
- **Color Spaces**: RGB, HSV, LAB conversions
- **Histogram Operations**: Equalization and analysis
- **Color Enhancement**: Brightness, contrast, and gamma correction

### Advanced Techniques
- **Template Matching**: Pattern recognition and object location
- **Image Segmentation**: Threshold, watershed, and clustering methods
- **Fourier Analysis**: Frequency domain processing
- **Machine Learning**: Object detection and recognition

## 🎓 Tutorials

Interactive Jupyter notebooks are available in the `notebooks/` directory:

1. **Basic Operations** (`01_basic_operations.ipynb`)
   - Image loading and display
   - Basic transformations
   - Pixel manipulation

2. **Image Filtering** (`02_image_filtering.ipynb`)
   - Smoothing techniques
   - Edge detection methods
   - Noise reduction

3. **Transformations** (`03_transformations.ipynb`)
   - Geometric transformations
   - Perspective correction
   - Image warping

4. **Morphological Operations** (`04_morphological_ops.ipynb`)
   - Basic morphological operations
   - Advanced morphological techniques
   - Shape analysis

5. **Feature Detection** (`05_feature_detection.ipynb`)
   - Corner and keypoint detection
   - Feature matching
   - Object recognition

6. **Color Processing** (`06_color_processing.ipynb`)
   - Color space conversions
   - Histogram analysis
   - Color enhancement

7. **Advanced Techniques** (`07_advanced_techniques.ipynb`)
   - Template matching
   - Image segmentation
   - Fourier transforms

8. **Practical Applications** (`08_practical_applications.ipynb`)
   - Real-world examples
   - Performance optimization
   - Best practices

### Running Tutorials
```bash
jupyter notebook notebooks/
```

## 💡 Examples

The `examples/` directory contains standalone scripts demonstrating specific techniques:

```bash
# Run a specific example
python examples/edge_detection_demo.py

# Run batch processing example
python examples/batch_image_processing.py --input_dir sample_images/original/
```

## 🌐 Web Application

### Interactive Streamlit Dashboard

Experience OpenCV image processing techniques through our interactive web application! The Streamlit dashboard provides a user-friendly interface for real-time experimentation with all the techniques covered in this repository.

**Features:**
- 🎛️ **Interactive Controls**: Adjust parameters in real-time
- 📸 **Image Upload**: Support for multiple image formats
- 🔄 **Side-by-side Comparison**: Original vs processed images
- 📱 **Responsive Design**: Works on desktop and mobile devices
- 🎯 **8 Processing Sections**: All major OpenCV techniques covered

**Quick Start:**
```bash
cd streamlit
pip install -r requirements.txt
streamlit run app.py
```

**Live Demo**: [Deployed on Streamlit Cloud](https://your-app-url.streamlit.app)

For detailed documentation, setup instructions, and deployment guide, see [streamlit/README.md](streamlit/README.md).

## 🔍 Verification Tools

This project includes comprehensive verification tools to ensure code quality and functionality:

### Example Verification
Verify that all example scripts can execute without errors:

```bash
# Quick verification with shell script
./verify_examples.sh

# Manual verification
source venv/bin/activate
python verify_examples.py
```

**Features:**
- ✅ Tests all 8 example files in `examples/` directory
- ✅ Automatic virtual environment setup
- ✅ Proper import path configuration
- ✅ Timeout protection (30s regular, 60s for advanced demos)
- ✅ Detailed error reporting and debugging info
- ✅ Summary statistics with pass/fail counts

**Expected Output:**
```
🚀 Starting verification of examples/*.py files...
Found 8 Python files to verify:
  - 01_basic_operations_demo.py
  - 02_image_filtering_demo.py
  - 03_transformations_demo.py
  - 04_morphological_ops_demo.py
  - 05_feature_detection_demo.py
  - 06_color_processing_demo.py
  - 07_advanced_techniques_demo.py
  - 08_practical_applications_demo.py

✅ All example files passed verification!
```

### Module Validation
Validate that all source modules can be imported correctly:

```bash
python validate_src_modules.py
```

### Notebook Verification
Ensure all Jupyter notebooks execute properly:

```bash
make verify-notebooks
```

For detailed documentation on verification tools, see:
- [VERIFICATION_README.md](VERIFICATION_README.md) - Example verification details
- [NOTEBOOK_VERIFICATION.md](NOTEBOOK_VERIFICATION.md) - Notebook verification details

## 🔧 Usage Patterns

### Function Signature Convention
All processing functions follow a consistent pattern:

```python
def process_image(image, param1, param2=default_value, **kwargs):
    """
    Brief description of the function.
    
    Args:
        image (np.ndarray): Input image
        param1 (type): Description of parameter
        param2 (type, optional): Description with default
        **kwargs: Additional OpenCV parameters
    
    Returns:
        np.ndarray: Processed image
    
    Example:
        >>> result = process_image(img, value1, param2=custom_value)
    """
    # Implementation
    return processed_image
```

### Error Handling
```python
from src.utils.image_utils import validate_image

def safe_processing_function(image, **params):
    # Validate input
    image = validate_image(image)
    
    try:
        # Process image
        result = cv2.someFunction(image, **params)
        return result
    except Exception as e:
        print(f"Processing failed: {e}")
        return None
```

## 🧪 Testing

### Module Validation
Before running tests, validate that all modules can be imported:

```bash
# Validate all source modules
python validate_src_modules.py
```

This script provides a comprehensive check of:
- **Dependency Verification**: Ensures all required packages are installed
- **Import Testing**: Validates that all Python modules can be imported
- **Package Structure**: Confirms proper package organization
- **Relative Import Handling**: Tests compatibility with package imports

### Test Suite
Run the test suite:

```bash
# Run all tests
pytest tests/

# Run specific test file
pytest tests/test_basic_operations.py

# Run with coverage
pytest --cov=src tests/
```

## 📊 Performance Considerations

- **Memory Management**: Large images are processed in chunks when possible
- **Optimization**: Critical functions include optimized implementations
- **Benchmarking**: Performance tests are available in `tests/performance/`



### Development Workflow
1. Fork the repository
2. Create a feature branch
3. Add your implementation with tests
4. **Validate your changes**: Run `python validate_src_modules.py` to ensure all modules still import correctly
5. Ensure code passes linting and tests
6. Submit a pull request

### Code Style
- Follow PEP 8 guidelines
- Use descriptive variable names
- Add comprehensive docstrings
- Include type hints where appropriate


## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙋‍♂️ Support

If you have questions or need help:
- Check the [documentation](docs/)
- Look at the [examples](examples/)
- Open an [issue](issues/)
- Review the [tutorials](notebooks/)

### Troubleshooting

**Import Errors?** Run the validation script to diagnose issues:
```bash
python validate_src_modules.py
```

**Example Scripts Not Working?** Use the verification tools:
```bash
# Test all example scripts
./verify_examples.sh

# Check specific issues
python verify_examples.py
```

These tools will help identify:
- Missing dependencies
- Import path issues
- Package structure problems
- Relative import conflicts
- Example script execution errors

## 🎯 Roadmap

- [x] Complete basic operations module
- [x] Add comprehensive test coverage
- [x] Implement advanced segmentation techniques
- [x] Create web-based demo interface (Streamlit)
- [ ] Add GPU acceleration support
- [ ] Add video processing examples
- [ ] Integrate deep learning models
- [ ] Enhance Streamlit app with additional features

---

**Happy Image Processing!** 📸✨
