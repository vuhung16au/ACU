"""
Keypoint Detection Module

This module provides various keypoint detection algorithms:
- SIFT (Scale-Invariant Feature Transform)
- SURF (Speeded-Up Robust Features)
- ORB (Oriented FAST and Rotated BRIEF)
- Feature matching

Author: Vu Hung Nguyen
"""

import cv2
import numpy as np
from typing import Tuple, Optional, Union, List


def detect_sift(image: np.ndarray, n_features: int = 0, n_octave_layers: int = 3,
                contrast_threshold: float = 0.04, edge_threshold: float = 10,
                sigma: float = 1.6) -> Tuple[List[cv2.KeyPoint], np.ndarray]:
    """
    Detect SIFT keypoints and compute descriptors.
    
    Args:
        image: Input image (grayscale)
        n_features: Number of best features to retain
        n_octave_layers: Number of layers in each octave
        contrast_threshold: Contrast threshold used to filter out weak features
        edge_threshold: Edge threshold used to filter out edge-like features
        sigma: Sigma of the Gaussian applied to the input image at the octave #0
        
    Returns:
        Tuple of (keypoints, descriptors)
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    # Create SIFT detector
    sift = cv2.SIFT_create(nfeatures=n_features, nOctaveLayers=n_octave_layers,
                           contrastThreshold=contrast_threshold, edgeThreshold=edge_threshold,
                           sigma=sigma)
    
    # Detect keypoints and compute descriptors
    keypoints, descriptors = sift.detectAndCompute(gray, None)
    
    return keypoints, descriptors


def detect_surf(image: np.ndarray, hessian_threshold: float = 100,
                n_octaves: int = 4, n_octave_layers: int = 3,
                extended: bool = False, upright: bool = False) -> Tuple[List[cv2.KeyPoint], np.ndarray]:
    """
    Detect SURF keypoints and compute descriptors.
    
    Args:
        image: Input image (grayscale)
        hessian_threshold: Threshold for the keypoint filter
        n_octaves: Number of octaves
        n_octave_layers: Number of layers in each octave
        extended: Extended descriptor flag
        upright: Up-right or rotated features flag
        
    Returns:
        Tuple of (keypoints, descriptors)
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    # Create SURF detector
    surf = cv2.xfeatures2d.SURF_create(hessianThreshold=hessian_threshold,
                                       nOctaves=n_octaves, nOctaveLayers=n_octave_layers,
                                       extended=extended, upright=upright)
    
    # Detect keypoints and compute descriptors
    keypoints, descriptors = surf.detectAndCompute(gray, None)
    
    return keypoints, descriptors


def detect_orb(image: np.ndarray, n_features: int = 500, scale_factor: float = 1.2,
               n_levels: int = 8, edge_threshold: int = 31, first_level: int = 0,
               wta_k: int = 2, patch_size: int = 31, fast_threshold: int = 20) -> Tuple[List[cv2.KeyPoint], np.ndarray]:
    """
    Detect ORB keypoints and compute descriptors.
    
    Args:
        image: Input image (grayscale)
        n_features: Maximum number of features to retain
        scale_factor: Pyramid decimation ratio
        n_levels: Number of pyramid levels
        edge_threshold: Size of the border where the features are not detected
        first_level: Level of pyramid to put source image to
        wta_k: Number of points that produce each element of the oriented BRIEF descriptor
        patch_size: Size of the patch used by the oriented BRIEF descriptor
        fast_threshold: Fast threshold
        
    Returns:
        Tuple of (keypoints, descriptors)
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    # Create ORB detector
    orb = cv2.ORB_create(nfeatures=n_features, scaleFactor=scale_factor,
                         nlevels=n_levels, edgeThreshold=edge_threshold,
                         firstLevel=first_level, WTA_K=wta_k,
                         patchSize=patch_size, fastThreshold=fast_threshold)
    
    # Detect keypoints and compute descriptors
    keypoints, descriptors = orb.detectAndCompute(gray, None)
    
    return keypoints, descriptors


def match_features(descriptors1: np.ndarray, descriptors2: np.ndarray,
                  matcher_type: str = 'bf', distance_metric: str = 'euclidean',
                  ratio_threshold: float = 0.75, max_distance: float = 100.0) -> List[cv2.DMatch]:
    """
    Match features between two sets of descriptors.
    
    Args:
        descriptors1: Descriptors from first image
        descriptors2: Descriptors from second image
        matcher_type: Type of matcher ('bf' for brute force, 'flann' for FLANN)
        distance_metric: Distance metric ('euclidean', 'manhattan', 'hamming')
        ratio_threshold: Ratio threshold for Lowe's ratio test
        max_distance: Maximum distance for matching
        
    Returns:
        List of good matches
    """
    if descriptors1 is None or descriptors2 is None:
        return []
    
    if matcher_type == 'bf':
        # Brute Force Matcher
        if distance_metric == 'hamming':
            matcher = cv2.BFMatcher(cv2.NORM_HAMMING, crossCheck=False)
        else:
            matcher = cv2.BFMatcher(cv2.NORM_L2, crossCheck=False)
        
        # Find matches
        matches = matcher.knnMatch(descriptors1, descriptors2, k=2)
        
        # Apply ratio test
        good_matches = []
        for match_pair in matches:
            if len(match_pair) == 2:
                m, n = match_pair
                if m.distance < ratio_threshold * n.distance and m.distance < max_distance:
                    good_matches.append(m)
    
    elif matcher_type == 'flann':
        # FLANN Matcher
        if distance_metric == 'hamming':
            index_params = dict(algorithm=6, table_number=6, key_size=12, multi_probe_level=1)
        else:
            index_params = dict(algorithm=1, trees=5)
        
        search_params = dict(checks=50)
        flann = cv2.FlannBasedMatcher(index_params, search_params)
        
        # Find matches
        matches = flann.knnMatch(descriptors1, descriptors2, k=2)
        
        # Apply ratio test
        good_matches = []
        for match_pair in matches:
            if len(match_pair) == 2:
                m, n = match_pair
                if m.distance < ratio_threshold * n.distance and m.distance < max_distance:
                    good_matches.append(m)
    
    else:
        raise ValueError(f"Unknown matcher type: {matcher_type}")
    
    return good_matches


def draw_keypoints(image: np.ndarray, keypoints: List[cv2.KeyPoint],
                  color: Tuple[int, int, int] = (0, 255, 0),
                  flags: int = cv2.DRAW_MATCHES_FLAGS_DRAW_RICH_KEYPOINTS) -> np.ndarray:
    """
    Draw keypoints on an image.
    
    Args:
        image: Input image
        keypoints: List of keypoints
        color: Color of the keypoints (BGR)
        flags: Drawing flags
        
    Returns:
        Image with drawn keypoints
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    result = image.copy()
    cv2.drawKeypoints(image, keypoints, result, color, flags)
    
    return result


def draw_matches(image1: np.ndarray, keypoints1: List[cv2.KeyPoint],
                image2: np.ndarray, keypoints2: List[cv2.KeyPoint],
                matches: List[cv2.DMatch], color: Tuple[int, int, int] = (0, 255, 0),
                flags: int = cv2.DRAW_MATCHES_FLAGS_NOT_DRAW_SINGLE_POINTS) -> np.ndarray:
    """
    Draw matches between two images.
    
    Args:
        image1: First image
        keypoints1: Keypoints from first image
        image2: Second image
        keypoints2: Keypoints from second image
        matches: List of matches
        color: Color of the matches (BGR)
        flags: Drawing flags
        
    Returns:
        Image showing matches
    """
    if image1 is None or image2 is None:
        raise ValueError("Input images cannot be None")
    
    return cv2.drawMatches(image1, keypoints1, image2, keypoints2, matches, None,
                          matchColor=color, singlePointColor=(255, 0, 0), matchesMask=None, flags=flags)


def compute_keypoint_statistics(keypoints: List[cv2.KeyPoint]) -> dict:
    """
    Compute statistics about keypoints.
    
    Args:
        keypoints: List of keypoints
        
    Returns:
        Dictionary containing keypoint statistics
    """
    if not keypoints:
        return {
            'count': 0,
            'mean_response': 0,
            'mean_size': 0,
            'mean_angle': 0,
            'response_std': 0,
            'size_std': 0,
            'angle_std': 0
        }
    
    responses = [kp.response for kp in keypoints]
    sizes = [kp.size for kp in keypoints]
    angles = [kp.angle for kp in keypoints]
    
    return {
        'count': len(keypoints),
        'mean_response': np.mean(responses),
        'mean_size': np.mean(sizes),
        'mean_angle': np.mean(angles),
        'response_std': np.std(responses),
        'size_std': np.std(sizes),
        'angle_std': np.std(angles),
        'min_response': np.min(responses),
        'max_response': np.max(responses),
        'min_size': np.min(sizes),
        'max_size': np.max(sizes)
    }


def compare_keypoint_detectors(image: np.ndarray) -> dict:
    """
    Compare different keypoint detection methods.
    
    Args:
        image: Input image
        
    Returns:
        Dictionary containing results from different detectors
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    results = {}
    
    # SIFT
    try:
        sift_kp, sift_desc = detect_sift(image)
        results['sift'] = {
            'keypoints': sift_kp,
            'descriptors': sift_desc,
            'count': len(sift_kp),
            'statistics': compute_keypoint_statistics(sift_kp)
        }
    except Exception as e:
        results['sift'] = {'error': str(e)}
    
    # SURF
    try:
        surf_kp, surf_desc = detect_surf(image)
        results['surf'] = {
            'keypoints': surf_kp,
            'descriptors': surf_desc,
            'count': len(surf_kp),
            'statistics': compute_keypoint_statistics(surf_kp)
        }
    except Exception as e:
        results['surf'] = {'error': str(e)}
    
    # ORB
    try:
        orb_kp, orb_desc = detect_orb(image)
        results['orb'] = {
            'keypoints': orb_kp,
            'descriptors': orb_desc,
            'count': len(orb_kp),
            'statistics': compute_keypoint_statistics(orb_kp)
        }
    except Exception as e:
        results['orb'] = {'error': str(e)}
    
    return results


def match_images(image1: np.ndarray, image2: np.ndarray,
                detector: str = 'sift', matcher: str = 'bf') -> dict:
    """
    Match features between two images.
    
    Args:
        image1: First image
        image2: Second image
        detector: Keypoint detector to use ('sift', 'surf', 'orb')
        matcher: Matcher to use ('bf', 'flann')
        
    Returns:
        Dictionary containing matching results
    """
    if image1 is None or image2 is None:
        raise ValueError("Input images cannot be None")
    
    # Detect keypoints and compute descriptors
    if detector == 'sift':
        kp1, desc1 = detect_sift(image1)
        kp2, desc2 = detect_sift(image2)
    elif detector == 'surf':
        kp1, desc1 = detect_surf(image1)
        kp2, desc2 = detect_surf(image2)
    elif detector == 'orb':
        kp1, desc1 = detect_orb(image1)
        kp2, desc2 = detect_orb(image2)
    else:
        raise ValueError(f"Unknown detector: {detector}")
    
    # Match features
    if desc1 is not None and desc2 is not None and len(desc1) > 0 and len(desc2) > 0:
        matches = match_features(desc1, desc2, matcher)
    else:
        matches = []
    
    return {
        'keypoints1': kp1,
        'keypoints2': kp2,
        'descriptors1': desc1,
        'descriptors2': desc2,
        'matches': matches,
        'match_count': len(matches),
        'detector': detector,
        'matcher': matcher
    }


def filter_matches_by_distance(matches: List[cv2.DMatch], max_distance: float = 100.0) -> List[cv2.DMatch]:
    """
    Filter matches by distance.
    
    Args:
        matches: List of matches
        max_distance: Maximum distance threshold
        
    Returns:
        Filtered matches
    """
    return [match for match in matches if match.distance < max_distance]


def compute_homography_from_matches(keypoints1: List[cv2.KeyPoint], keypoints2: List[cv2.KeyPoint],
                                  matches: List[cv2.DMatch], min_matches: int = 10) -> Tuple[np.ndarray, np.ndarray]:
    """
    Compute homography matrix from matched keypoints.
    
    Args:
        keypoints1: Keypoints from first image
        keypoints2: Keypoints from second image
        matches: List of matches
        min_matches: Minimum number of matches required
        
    Returns:
        Tuple of (homography_matrix, mask)
    """
    if len(matches) < min_matches:
        return None, None
    
    # Extract matched keypoints
    src_pts = np.float32([keypoints1[m.queryIdx].pt for m in matches]).reshape(-1, 1, 2)
    dst_pts = np.float32([keypoints2[m.trainIdx].pt for m in matches]).reshape(-1, 1, 2)
    
    # Compute homography
    homography, mask = cv2.findHomography(src_pts, dst_pts, cv2.RANSAC, 5.0)
    
    return homography, mask 