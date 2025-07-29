"""
Main components module for OpenCV operations.
This file now serves as a facade for the modular components.
"""

import streamlit as st
import numpy as np
from components import (
    BasicOperationsComponent,
    ImageFilteringComponent,
    TransformationsComponent,
    MorphologicalComponent,
    FeatureDetectionComponent,
    ColorProcessingComponent,
    AdvancedTechniquesComponent,
    PracticalApplicationsComponent
)


def basic_operations_section(image: np.ndarray):
    """Basic Operations section with interactive widgets."""
    component = BasicOperationsComponent()
    component.render(image)


def image_filtering_section(image: np.ndarray):
    """Image Filtering section with interactive widgets."""
    component = ImageFilteringComponent()
    component.render(image)


def transformations_section(image: np.ndarray):
    """Transformations section with interactive widgets."""
    component = TransformationsComponent()
    component.render(image)


def morphological_operations_section(image: np.ndarray):
    """Morphological Operations section with interactive widgets."""
    component = MorphologicalComponent()
    component.render(image)


def feature_detection_section(image: np.ndarray):
    """Feature Detection section with interactive widgets."""
    component = FeatureDetectionComponent()
    component.render(image)


def color_processing_section(image: np.ndarray):
    """Color Processing section with interactive widgets."""
    component = ColorProcessingComponent()
    component.render(image)


def advanced_techniques_section(image: np.ndarray):
    """Advanced Techniques section with interactive widgets."""
    component = AdvancedTechniquesComponent()
    component.render(image)


def practical_applications_section(image: np.ndarray):
    """Practical Applications section with interactive widgets."""
    component = PracticalApplicationsComponent()
    component.render(image) 