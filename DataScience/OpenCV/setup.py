"""
Setup script for OpenCV Image Processing Techniques Collection.

This package provides a comprehensive collection of OpenCV image processing
techniques implemented in Python, organized as a practical reference guide.
"""

from setuptools import setup, find_packages
import os

# Read the README file for long description
def read_readme():
    readme_path = os.path.join(os.path.dirname(__file__), 'README.md')
    if os.path.exists(readme_path):
        with open(readme_path, 'r', encoding='utf-8') as f:
            return f.read()
    return "OpenCV Image Processing Techniques Collection"

# Read requirements from requirements.txt
def read_requirements():
    requirements_path = os.path.join(os.path.dirname(__file__), 'requirements.txt')
    if os.path.exists(requirements_path):
        with open(requirements_path, 'r', encoding='utf-8') as f:
            return [line.strip() for line in f if line.strip() and not line.startswith('#')]
    return []

setup(
    name="opencv-image-processing-collection",
    version="1.0.0",
    author="Your Name",
    author_email="your.email@example.com",
    description="A comprehensive collection of OpenCV image processing techniques",
    long_description=read_readme(),
    long_description_content_type="text/markdown",
    url="https://github.com/yourusername/opencv-image-processing-collection",
    packages=find_packages(where="src"),
    package_dir={"": "src"},
    classifiers=[
        "Development Status :: 4 - Beta",
        "Intended Audience :: Developers",
        "Intended Audience :: Education",
        "Intended Audience :: Science/Research",
        "License :: OSI Approved :: MIT License",
        "Operating System :: OS Independent",
        "Programming Language :: Python :: 3",
        "Programming Language :: Python :: 3.7",
        "Programming Language :: Python :: 3.8",
        "Programming Language :: Python :: 3.9",
        "Programming Language :: Python :: 3.10",
        "Programming Language :: Python :: 3.11",
        "Topic :: Scientific/Engineering :: Image Processing",
        "Topic :: Software Development :: Libraries :: Python Modules",
        "Topic :: Education",
    ],
    python_requires=">=3.7",
    install_requires=read_requirements(),
    extras_require={
        "dev": [
            "pytest>=6.0.0",
            "pytest-cov>=3.0.0",
            "black>=22.0.0",
            "flake8>=4.0.0",
            "mypy>=0.991",
        ],
        "docs": [
            "sphinx>=4.0.0",
            "sphinx-rtd-theme>=1.0.0",
        ],
        "jupyter": [
            "jupyter>=1.0.0",
            "ipywidgets>=7.6.0",
            "notebook>=6.4.0",
        ],
    },
    entry_points={
        "console_scripts": [
            "opencv-demo=examples.basic_operations_demo:main",
        ],
    },
    include_package_data=True,
    package_data={
        "": ["*.md", "*.txt", "*.yml", "*.yaml"],
    },
    keywords=[
        "opencv",
        "computer-vision", 
        "image-processing",
        "python",
        "tutorial",
        "education",
        "machine-learning",
        "deep-learning"
    ],
    project_urls={
        "Bug Reports": "https://github.com/yourusername/opencv-image-processing-collection/issues",
        "Source": "https://github.com/yourusername/opencv-image-processing-collection",
        "Documentation": "https://opencv-image-processing-collection.readthedocs.io/",
    },
)
