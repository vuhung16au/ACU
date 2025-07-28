"""
Machine Learning Module

This module provides machine learning applications:
- Face detection with Haar cascades
- Object detection
- Background subtraction

Author: OpenCV Image Processing Collection
"""

import cv2
import numpy as np
from typing import Tuple, Optional, Union, List, Dict
import os


def face_detection(image: np.ndarray, cascade_path: Optional[str] = None,
                   scale_factor: float = 1.1, min_neighbors: int = 5,
                   min_size: Tuple[int, int] = (30, 30)) -> Tuple[np.ndarray, List[Tuple[int, int, int, int]]]:
    """
    Detect faces in an image using Haar cascades.
    
    Args:
        image: Input image
        cascade_path: Path to Haar cascade file (if None, uses default)
        scale_factor: Scale factor for detection
        min_neighbors: Minimum neighbors for detection
        min_size: Minimum face size (width, height)
        
    Returns:
        Tuple of (image_with_detections, face_locations)
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Load Haar cascade classifier
    if cascade_path is None:
        # Try to find the default cascade file
        cascade_path = cv2.data.haarcascades + 'haarcascade_frontalface_default.xml'
    
    if not os.path.exists(cascade_path):
        raise FileNotFoundError(f"Cascade file not found: {cascade_path}")
    
    face_cascade = cv2.CascadeClassifier(cascade_path)
    
    # Convert to grayscale
    gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    
    # Detect faces
    faces = face_cascade.detectMultiScale(gray, scale_factor, min_neighbors, minSize=min_size)
    
    # Draw rectangles around faces
    result = image.copy()
    for (x, y, w, h) in faces:
        cv2.rectangle(result, (x, y), (x+w, y+h), (255, 0, 0), 2)
    
    return result, faces.tolist()


def eye_detection(image: np.ndarray, cascade_path: Optional[str] = None,
                  scale_factor: float = 1.1, min_neighbors: int = 5,
                  min_size: Tuple[int, int] = (20, 20)) -> Tuple[np.ndarray, List[Tuple[int, int, int, int]]]:
    """
    Detect eyes in an image using Haar cascades.
    
    Args:
        image: Input image
        cascade_path: Path to Haar cascade file (if None, uses default)
        scale_factor: Scale factor for detection
        min_neighbors: Minimum neighbors for detection
        min_size: Minimum eye size (width, height)
        
    Returns:
        Tuple of (image_with_detections, eye_locations)
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Load Haar cascade classifier
    if cascade_path is None:
        cascade_path = cv2.data.haarcascades + 'haarcascade_eye.xml'
    
    if not os.path.exists(cascade_path):
        raise FileNotFoundError(f"Cascade file not found: {cascade_path}")
    
    eye_cascade = cv2.CascadeClassifier(cascade_path)
    
    # Convert to grayscale
    gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    
    # Detect eyes
    eyes = eye_cascade.detectMultiScale(gray, scale_factor, min_neighbors, minSize=min_size)
    
    # Draw rectangles around eyes
    result = image.copy()
    for (x, y, w, h) in eyes:
        cv2.rectangle(result, (x, y), (x+w, y+h), (0, 255, 0), 2)
    
    return result, eyes.tolist()


def smile_detection(image: np.ndarray, cascade_path: Optional[str] = None,
                   scale_factor: float = 1.1, min_neighbors: int = 5,
                   min_size: Tuple[int, int] = (30, 30)) -> Tuple[np.ndarray, List[Tuple[int, int, int, int]]]:
    """
    Detect smiles in an image using Haar cascades.
    
    Args:
        image: Input image
        cascade_path: Path to Haar cascade file (if None, uses default)
        scale_factor: Scale factor for detection
        min_neighbors: Minimum neighbors for detection
        min_size: Minimum smile size (width, height)
        
    Returns:
        Tuple of (image_with_detections, smile_locations)
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Load Haar cascade classifier
    if cascade_path is None:
        cascade_path = cv2.data.haarcascades + 'haarcascade_smile.xml'
    
    if not os.path.exists(cascade_path):
        raise FileNotFoundError(f"Cascade file not found: {cascade_path}")
    
    smile_cascade = cv2.CascadeClassifier(cascade_path)
    
    # Convert to grayscale
    gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    
    # Detect smiles
    smiles = smile_cascade.detectMultiScale(gray, scale_factor, min_neighbors, minSize=min_size)
    
    # Draw rectangles around smiles
    result = image.copy()
    for (x, y, w, h) in smiles:
        cv2.rectangle(result, (x, y), (x+w, y+h), (0, 0, 255), 2)
    
    return result, smiles.tolist()


def object_detection_hog(image: np.ndarray, win_stride: Tuple[int, int] = (8, 8),
                        padding: Tuple[int, int] = (4, 4), scale: float = 1.05) -> Tuple[np.ndarray, List[Tuple[int, int, int, int]]]:
    """
    Detect objects using HOG (Histogram of Oriented Gradients) descriptor.
    
    Args:
        image: Input image
        win_stride: Window stride for detection
        padding: Padding for detection
        scale: Scale factor for detection
        
    Returns:
        Tuple of (image_with_detections, detection_locations)
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Initialize HOG descriptor
    hog = cv2.HOGDescriptor()
    hog.setSVMDetector(cv2.HOGDescriptor_getDefaultPeopleDetector())
    
    # Detect objects
    detections, weights = hog.detectMultiScale(image, winStride=win_stride, padding=padding, scale=scale)
    
    # Draw rectangles around detections
    result = image.copy()
    for (x, y, w, h) in detections:
        cv2.rectangle(result, (x, y), (x+w, y+h), (0, 255, 0), 2)
    
    return result, detections.tolist()


def background_subtraction_mog(image: np.ndarray, history: int = 500,
                              nmixtures: int = 5, background_ratio: float = 0.7,
                              noise_reduction: float = 0.1) -> Tuple[np.ndarray, np.ndarray]:
    """
    Perform background subtraction using MOG (Mixture of Gaussians).
    
    Args:
        image: Input image
        history: Number of frames to use for background modeling
        nmixtures: Number of Gaussian mixtures
        background_ratio: Background ratio
        noise_reduction: Noise reduction factor
        
    Returns:
        Tuple of (foreground_mask, background_model)
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Create MOG background subtractor
    bg_subtractor = cv2.createBackgroundSubtractorMOG2(
        history=history,
        varThreshold=16,
        detectShadows=True
    )
    
    # Apply background subtraction
    fg_mask = bg_subtractor.apply(image)
    
    # Apply noise reduction
    kernel = np.ones((3, 3), np.uint8)
    fg_mask = cv2.morphologyEx(fg_mask, cv2.MORPH_OPEN, kernel)
    fg_mask = cv2.morphologyEx(fg_mask, cv2.MORPH_CLOSE, kernel)
    
    # Get background model
    bg_model = bg_subtractor.getBackgroundImage()
    
    return fg_mask, bg_model


def background_subtraction_knn(image: np.ndarray, history: int = 500,
                              dist2_threshold: float = 400.0, detect_shadows: bool = True) -> Tuple[np.ndarray, np.ndarray]:
    """
    Perform background subtraction using KNN.
    
    Args:
        image: Input image
        history: Number of frames to use for background modeling
        dist2_threshold: Distance threshold
        detect_shadows: Whether to detect shadows
        
    Returns:
        Tuple of (foreground_mask, background_model)
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Create KNN background subtractor
    bg_subtractor = cv2.createBackgroundSubtractorKNN(
        history=history,
        dist2Threshold=dist2_threshold,
        detectShadows=detect_shadows
    )
    
    # Apply background subtraction
    fg_mask = bg_subtractor.apply(image)
    
    # Get background model
    bg_model = bg_subtractor.getBackgroundImage()
    
    return fg_mask, bg_model


def motion_detection(image1: np.ndarray, image2: np.ndarray,
                    threshold: float = 25.0, min_area: int = 500) -> Tuple[np.ndarray, List[Tuple[int, int, int, int]]]:
    """
    Detect motion between two consecutive frames.
    
    Args:
        image1: First frame
        image2: Second frame
        threshold: Threshold for motion detection
        min_area: Minimum area for motion regions
        
    Returns:
        Tuple of (motion_mask, motion_regions)
    """
    if image1 is None or image2 is None:
        raise ValueError("Both images cannot be None")
    
    # Convert to grayscale
    gray1 = cv2.cvtColor(image1, cv2.COLOR_BGR2GRAY) if len(image1.shape) == 3 else image1
    gray2 = cv2.cvtColor(image2, cv2.COLOR_BGR2GRAY) if len(image2.shape) == 3 else image2
    
    # Compute absolute difference
    diff = cv2.absdiff(gray1, gray2)
    
    # Apply threshold
    _, thresh = cv2.threshold(diff, threshold, 255, cv2.THRESH_BINARY)
    
    # Apply morphological operations
    kernel = np.ones((5, 5), np.uint8)
    thresh = cv2.morphologyEx(thresh, cv2.MORPH_OPEN, kernel)
    thresh = cv2.morphologyEx(thresh, cv2.MORPH_CLOSE, kernel)
    
    # Find contours
    contours, _ = cv2.findContours(thresh, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    
    # Filter contours by area
    motion_regions = []
    for contour in contours:
        area = cv2.contourArea(contour)
        if area > min_area:
            x, y, w, h = cv2.boundingRect(contour)
            motion_regions.append((x, y, w, h))
    
    return thresh, motion_regions


def optical_flow_lucas_kanade(image1: np.ndarray, image2: np.ndarray,
                              max_corners: int = 100, quality_level: float = 0.01,
                              min_distance: int = 10, block_size: int = 3) -> Tuple[np.ndarray, np.ndarray]:
    """
    Compute optical flow using Lucas-Kanade method.
    
    Args:
        image1: First frame
        image2: Second frame
        max_corners: Maximum number of corners to track
        quality_level: Quality level for corner detection
        min_distance: Minimum distance between corners
        block_size: Block size for optical flow
        
    Returns:
        Tuple of (flow_image, flow_vectors)
    """
    if image1 is None or image2 is None:
        raise ValueError("Both images cannot be None")
    
    # Convert to grayscale
    gray1 = cv2.cvtColor(image1, cv2.COLOR_BGR2GRAY) if len(image1.shape) == 3 else image1
    gray2 = cv2.cvtColor(image2, cv2.COLOR_BGR2GRAY) if len(image2.shape) == 3 else image2
    
    # Find corners in first frame
    corners = cv2.goodFeaturesToTrack(gray1, max_corners, quality_level, min_distance, blockSize=block_size)
    
    if corners is None:
        return image1, np.array([])
    
    # Calculate optical flow
    next_corners, status, error = cv2.calcOpticalFlowPyrLK(gray1, gray2, corners, None)
    
    # Filter good points
    good_old = corners[status == 1]
    good_new = next_corners[status == 1]
    
    # Draw flow vectors
    result = image1.copy()
    for i, (old, new) in enumerate(zip(good_old, good_new)):
        a, b = old.ravel()
        c, d = new.ravel()
        
        # Draw line
        cv2.line(result, (int(a), int(b)), (int(c), int(d)), (0, 255, 0), 2)
        # Draw circle
        cv2.circle(result, (int(c), int(d)), 3, (0, 0, 255), -1)
    
    return result, np.column_stack((good_old, good_new))


def template_matching_detection(image: np.ndarray, template: np.ndarray,
                              threshold: float = 0.8, method: int = cv2.TM_CCOEFF_NORMED) -> Tuple[np.ndarray, List[Tuple[int, int, int, int]]]:
    """
    Detect objects using template matching.
    
    Args:
        image: Input image
        template: Template to match
        threshold: Matching threshold
        method: Template matching method
        
    Returns:
        Tuple of (image_with_detections, detection_locations)
    """
    if image is None or template is None:
        raise ValueError("Image and template cannot be None")
    
    # Perform template matching
    result = cv2.matchTemplate(image, template, method)
    
    # Find locations where correlation exceeds threshold
    locations = np.where(result >= threshold)
    detections = []
    
    # Get template dimensions
    h, w = template.shape[:2]
    
    for pt in zip(*locations[::-1]):  # Switch columns and rows
        detections.append((pt[0], pt[1], w, h))
    
    # Draw rectangles around detections
    result_image = image.copy()
    for (x, y, w, h) in detections:
        cv2.rectangle(result_image, (x, y), (x+w, y+h), (0, 255, 0), 2)
    
    return result_image, detections


def compare_detection_methods(image: np.ndarray) -> dict:
    """
    Compare different object detection methods.
    
    Args:
        image: Input image
        
    Returns:
        Dictionary containing results from different methods
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    results = {}
    
    # Face detection
    try:
        face_result, face_locations = face_detection(image)
        results['face_detection'] = {
            'result': face_result,
            'locations': face_locations,
            'count': len(face_locations)
        }
    except Exception as e:
        results['face_detection'] = {'error': str(e)}
    
    # Eye detection
    try:
        eye_result, eye_locations = eye_detection(image)
        results['eye_detection'] = {
            'result': eye_result,
            'locations': eye_locations,
            'count': len(eye_locations)
        }
    except Exception as e:
        results['eye_detection'] = {'error': str(e)}
    
    # HOG object detection
    try:
        hog_result, hog_locations = object_detection_hog(image)
        results['hog_detection'] = {
            'result': hog_result,
            'locations': hog_locations,
            'count': len(hog_locations)
        }
    except Exception as e:
        results['hog_detection'] = {'error': str(e)}
    
    return results


def create_detection_video(input_video_path: str, output_video_path: str,
                          detection_type: str = 'face', **kwargs) -> None:
    """
    Create a video with object detections.
    
    Args:
        input_video_path: Path to input video
        output_video_path: Path to output video
        detection_type: Type of detection ('face', 'eye', 'hog')
        **kwargs: Additional arguments for detection function
        
    Returns:
        None
    """
    if not os.path.exists(input_video_path):
        raise FileNotFoundError(f"Input video not found: {input_video_path}")
    
    # Open video
    cap = cv2.VideoCapture(input_video_path)
    
    # Get video properties
    fps = int(cap.get(cv2.CAP_PROP_FPS))
    width = int(cap.get(cv2.CAP_PROP_FRAME_WIDTH))
    height = int(cap.get(cv2.CAP_PROP_FRAME_HEIGHT))
    
    # Create video writer
    fourcc = cv2.VideoWriter_fourcc(*'mp4v')
    out = cv2.VideoWriter(output_video_path, fourcc, fps, (width, height))
    
    # Process frames
    while True:
        ret, frame = cap.read()
        if not ret:
            break
        
        # Apply detection
        if detection_type == 'face':
            result, _ = face_detection(frame, **kwargs)
        elif detection_type == 'eye':
            result, _ = eye_detection(frame, **kwargs)
        elif detection_type == 'hog':
            result, _ = object_detection_hog(frame, **kwargs)
        else:
            result = frame
        
        # Write frame
        out.write(result)
    
    # Release resources
    cap.release()
    out.release()
    cv2.destroyAllWindows()


def extract_detection_features(detection_results: dict) -> dict:
    """
    Extract features from detection results.
    
    Args:
        detection_results: Dictionary containing detection results
        
    Returns:
        Dictionary containing extracted features
    """
    features = {}
    
    for method, result in detection_results.items():
        if 'error' not in result:
            features[method] = {
                'count': result.get('count', 0),
                'locations': result.get('locations', []),
                'success': True
            }
        else:
            features[method] = {
                'count': 0,
                'locations': [],
                'success': False,
                'error': result['error']
            }
    
    return features 