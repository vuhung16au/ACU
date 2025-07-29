"""
Fourier Analysis Module

This module provides Fourier analysis techniques:
- Fourier Transform
- Frequency domain filtering
- Image restoration

Author: Vu Hung Nguyen
"""

import cv2
import numpy as np
from typing import Tuple, Optional, Union
from scipy import ndimage


def fourier_transform(image: np.ndarray) -> Tuple[np.ndarray, np.ndarray]:
    """
    Compute Fourier transform of an image.
    
    Args:
        image: Input image (grayscale)
        
    Returns:
        Tuple of (magnitude_spectrum, phase_spectrum)
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    # Apply Fourier transform
    f_transform = np.fft.fft2(gray)
    f_shift = np.fft.fftshift(f_transform)
    
    # Compute magnitude and phase
    magnitude_spectrum = np.log(np.abs(f_shift) + 1)
    phase_spectrum = np.angle(f_shift)
    
    return magnitude_spectrum, phase_spectrum


def inverse_fourier_transform(magnitude: np.ndarray, phase: np.ndarray) -> np.ndarray:
    """
    Compute inverse Fourier transform.
    
    Args:
        magnitude: Magnitude spectrum
        phase: Phase spectrum
        
    Returns:
        Reconstructed image
    """
    if magnitude is None or phase is None:
        raise ValueError("Magnitude and phase cannot be None")
    
    # Reconstruct complex spectrum
    complex_spectrum = magnitude * np.exp(1j * phase)
    
    # Apply inverse shift and transform
    f_ishift = np.fft.ifftshift(complex_spectrum)
    img_back = np.fft.ifft2(f_ishift)
    
    return np.abs(img_back)


def frequency_domain_filtering(image: np.ndarray, filter_type: str = 'lowpass',
                             cutoff_frequency: float = 30.0, order: int = 2) -> np.ndarray:
    """
    Apply frequency domain filtering.
    
    Args:
        image: Input image (grayscale)
        filter_type: Type of filter ('lowpass', 'highpass', 'bandpass', 'notch')
        cutoff_frequency: Cutoff frequency
        order: Filter order
        
    Returns:
        Filtered image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    # Get image dimensions
    rows, cols = gray.shape
    crow, ccol = rows // 2, cols // 2
    
    # Apply Fourier transform
    f_transform = np.fft.fft2(gray)
    f_shift = np.fft.fftshift(f_transform)
    
    # Create filter mask
    mask = np.zeros((rows, cols), np.uint8)
    
    if filter_type == 'lowpass':
        # Low-pass filter
        y, x = np.ogrid[:rows, :cols]
        mask_area = (x - ccol) ** 2 + (y - crow) ** 2 <= cutoff_frequency ** 2
        mask[mask_area] = 1
    
    elif filter_type == 'highpass':
        # High-pass filter
        y, x = np.ogrid[:rows, :cols]
        mask_area = (x - ccol) ** 2 + (y - crow) ** 2 > cutoff_frequency ** 2
        mask[mask_area] = 1
    
    elif filter_type == 'bandpass':
        # Band-pass filter
        y, x = np.ogrid[:rows, :cols]
        distance = np.sqrt((x - ccol) ** 2 + (y - crow) ** 2)
        mask = ((distance >= cutoff_frequency - order) & (distance <= cutoff_frequency + order)).astype(np.uint8)
    
    elif filter_type == 'notch':
        # Notch filter (removes specific frequencies)
        y, x = np.ogrid[:rows, :cols]
        mask_area = ((x - ccol) ** 2 + (y - crow) ** 2 > cutoff_frequency ** 2)
        mask[mask_area] = 1
    
    else:
        raise ValueError(f"Unknown filter type: {filter_type}")
    
    # Apply filter
    f_shift_filtered = f_shift * mask
    
    # Apply inverse Fourier transform
    f_ishift = np.fft.ifftshift(f_shift_filtered)
    img_back = np.fft.ifft2(f_ishift)
    
    return np.abs(img_back)


def butterworth_filter(image: np.ndarray, filter_type: str = 'lowpass',
                     cutoff_frequency: float = 30.0, order: int = 2) -> np.ndarray:
    """
    Apply Butterworth filter in frequency domain.
    
    Args:
        image: Input image (grayscale)
        filter_type: Type of filter ('lowpass', 'highpass')
        cutoff_frequency: Cutoff frequency
        order: Filter order
        
    Returns:
        Filtered image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    # Get image dimensions
    rows, cols = gray.shape
    crow, ccol = rows // 2, cols // 2
    
    # Apply Fourier transform
    f_transform = np.fft.fft2(gray)
    f_shift = np.fft.fftshift(f_transform)
    
    # Create Butterworth filter
    y, x = np.ogrid[:rows, :cols]
    distance = np.sqrt((x - ccol) ** 2 + (y - crow) ** 2)
    
    if filter_type == 'lowpass':
        # Low-pass Butterworth filter
        filter_mask = 1 / (1 + (distance / cutoff_frequency) ** (2 * order))
    elif filter_type == 'highpass':
        # High-pass Butterworth filter
        filter_mask = 1 / (1 + (cutoff_frequency / (distance + 1e-10)) ** (2 * order))
    else:
        raise ValueError(f"Unknown filter type: {filter_type}")
    
    # Apply filter
    f_shift_filtered = f_shift * filter_mask
    
    # Apply inverse Fourier transform
    f_ishift = np.fft.ifftshift(f_shift_filtered)
    img_back = np.fft.ifft2(f_ishift)
    
    return np.abs(img_back)


def gaussian_filter_frequency(image: np.ndarray, filter_type: str = 'lowpass',
                            sigma: float = 30.0) -> np.ndarray:
    """
    Apply Gaussian filter in frequency domain.
    
    Args:
        image: Input image (grayscale)
        filter_type: Type of filter ('lowpass', 'highpass')
        sigma: Standard deviation of Gaussian
        
    Returns:
        Filtered image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    # Get image dimensions
    rows, cols = gray.shape
    crow, ccol = rows // 2, cols // 2
    
    # Apply Fourier transform
    f_transform = np.fft.fft2(gray)
    f_shift = np.fft.fftshift(f_transform)
    
    # Create Gaussian filter
    y, x = np.ogrid[:rows, :cols]
    distance = np.sqrt((x - ccol) ** 2 + (y - crow) ** 2)
    
    if filter_type == 'lowpass':
        # Low-pass Gaussian filter
        filter_mask = np.exp(-(distance ** 2) / (2 * sigma ** 2))
    elif filter_type == 'highpass':
        # High-pass Gaussian filter
        filter_mask = 1 - np.exp(-(distance ** 2) / (2 * sigma ** 2))
    else:
        raise ValueError(f"Unknown filter type: {filter_type}")
    
    # Apply filter
    f_shift_filtered = f_shift * filter_mask
    
    # Apply inverse Fourier transform
    f_ishift = np.fft.ifftshift(f_shift_filtered)
    img_back = np.fft.ifft2(f_ishift)
    
    return np.abs(img_back)


def wiener_filter(image: np.ndarray, noise_power: float = 0.01,
                 blur_kernel: Optional[np.ndarray] = None) -> np.ndarray:
    """
    Apply Wiener filter for image restoration.
    
    Args:
        image: Input image (grayscale)
        noise_power: Noise power ratio
        blur_kernel: Blur kernel (if None, identity kernel is used)
        
    Returns:
        Restored image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    # Get image dimensions
    rows, cols = gray.shape
    
    # Create blur kernel if not provided
    if blur_kernel is None:
        blur_kernel = np.array([[1, 1, 1], [1, 1, 1], [1, 1, 1]]) / 9.0
    
    # Pad kernel to image size
    kernel_padded = np.zeros((rows, cols))
    kernel_padded[:blur_kernel.shape[0], :blur_kernel.shape[1]] = blur_kernel
    
    # Apply Fourier transform
    f_image = np.fft.fft2(gray)
    f_kernel = np.fft.fft2(kernel_padded)
    
    # Wiener filter
    f_kernel_conj = np.conj(f_kernel)
    f_kernel_mag_sq = np.abs(f_kernel) ** 2
    
    # Avoid division by zero
    denominator = f_kernel_mag_sq + noise_power
    wiener_filter = f_kernel_conj / denominator
    
    # Apply filter
    f_restored = f_image * wiener_filter
    
    # Apply inverse Fourier transform
    restored = np.fft.ifft2(f_restored)
    
    return np.abs(restored)


def homomorphic_filter(image: np.ndarray, gamma_low: float = 0.5, gamma_high: float = 2.0,
                      cutoff_frequency: float = 30.0, order: int = 2) -> np.ndarray:
    """
    Apply homomorphic filtering for illumination correction.
    
    Args:
        image: Input image (grayscale)
        gamma_low: Low frequency gain
        gamma_high: High frequency gain
        cutoff_frequency: Cutoff frequency
        order: Filter order
        
    Returns:
        Filtered image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    # Add small constant to avoid log(0)
    gray = gray.astype(np.float64) + 1
    
    # Apply log transform
    log_image = np.log(gray)
    
    # Apply Fourier transform
    f_transform = np.fft.fft2(log_image)
    f_shift = np.fft.fftshift(f_transform)
    
    # Create homomorphic filter
    rows, cols = gray.shape
    crow, ccol = rows // 2, cols // 2
    
    y, x = np.ogrid[:rows, :cols]
    distance = np.sqrt((x - ccol) ** 2 + (y - crow) ** 2)
    
    # Homomorphic filter function
    filter_mask = (gamma_high - gamma_low) * (1 - np.exp(-(distance ** 2) / (2 * cutoff_frequency ** 2))) + gamma_low
    
    # Apply filter
    f_shift_filtered = f_shift * filter_mask
    
    # Apply inverse Fourier transform
    f_ishift = np.fft.ifftshift(f_shift_filtered)
    log_filtered = np.fft.ifft2(f_ishift)
    
    # Apply exponential transform
    filtered = np.exp(np.real(log_filtered))
    
    return np.clip(filtered, 0, 255).astype(np.uint8)


def frequency_domain_noise_reduction(image: np.ndarray, noise_type: str = 'periodic',
                                   threshold: float = 0.1) -> np.ndarray:
    """
    Reduce noise in frequency domain.
    
    Args:
        image: Input image (grayscale)
        noise_type: Type of noise ('periodic', 'salt_pepper', 'gaussian')
        threshold: Threshold for noise removal
        
    Returns:
        Denoised image
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    # Convert to grayscale if needed
    if len(image.shape) == 3:
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    else:
        gray = image
    
    # Apply Fourier transform
    f_transform = np.fft.fft2(gray)
    f_shift = np.fft.fftshift(f_transform)
    
    # Get magnitude spectrum
    magnitude = np.abs(f_shift)
    
    if noise_type == 'periodic':
        # Remove periodic noise by thresholding high frequencies
        magnitude_filtered = magnitude.copy()
        magnitude_filtered[magnitude > threshold * magnitude.max()] = 0
    
    elif noise_type == 'salt_pepper':
        # Remove salt and pepper noise
        magnitude_filtered = magnitude.copy()
        # Apply median filter in frequency domain
        magnitude_filtered = ndimage.median_filter(magnitude_filtered, size=3)
    
    elif noise_type == 'gaussian':
        # Remove Gaussian noise using Wiener filter
        noise_power = threshold
        magnitude_filtered = magnitude / (magnitude + noise_power)
    
    else:
        raise ValueError(f"Unknown noise type: {noise_type}")
    
    # Reconstruct phase
    phase = np.angle(f_shift)
    
    # Reconstruct complex spectrum
    f_shift_filtered = magnitude_filtered * np.exp(1j * phase)
    
    # Apply inverse Fourier transform
    f_ishift = np.fft.ifftshift(f_shift_filtered)
    denoised = np.fft.ifft2(f_ishift)
    
    return np.abs(denoised)


def compare_frequency_filters(image: np.ndarray) -> dict:
    """
    Compare different frequency domain filters.
    
    Args:
        image: Input image
        
    Returns:
        Dictionary containing results from different filters
    """
    if image is None:
        raise ValueError("Input image cannot be None")
    
    results = {}
    
    # Fourier transform
    try:
        magnitude, phase = fourier_transform(image)
        results['fourier_transform'] = {
            'magnitude': magnitude,
            'phase': phase
        }
    except Exception as e:
        results['fourier_transform'] = {'error': str(e)}
    
    # Low-pass filter
    try:
        lowpass_result = frequency_domain_filtering(image, 'lowpass', 30.0)
        results['lowpass_filter'] = {
            'filtered': lowpass_result
        }
    except Exception as e:
        results['lowpass_filter'] = {'error': str(e)}
    
    # High-pass filter
    try:
        highpass_result = frequency_domain_filtering(image, 'highpass', 30.0)
        results['highpass_filter'] = {
            'filtered': highpass_result
        }
    except Exception as e:
        results['highpass_filter'] = {'error': str(e)}
    
    # Butterworth filter
    try:
        butterworth_result = butterworth_filter(image, 'lowpass', 30.0, 2)
        results['butterworth_filter'] = {
            'filtered': butterworth_result
        }
    except Exception as e:
        results['butterworth_filter'] = {'error': str(e)}
    
    # Gaussian filter
    try:
        gaussian_result = gaussian_filter_frequency(image, 'lowpass', 30.0)
        results['gaussian_filter'] = {
            'filtered': gaussian_result
        }
    except Exception as e:
        results['gaussian_filter'] = {'error': str(e)}
    
    # Homomorphic filter
    try:
        homomorphic_result = homomorphic_filter(image)
        results['homomorphic_filter'] = {
            'filtered': homomorphic_result
        }
    except Exception as e:
        results['homomorphic_filter'] = {'error': str(e)}
    
    return results


def plot_frequency_spectrum(magnitude: np.ndarray, title: str = "Frequency Spectrum") -> None:
    """
    Plot frequency spectrum.
    
    Args:
        magnitude: Magnitude spectrum
        title: Plot title
        
    Returns:
        None (displays plot)
    """
    try:
        import matplotlib.pyplot as plt
        
        plt.figure(figsize=(10, 8))
        plt.imshow(magnitude, cmap='gray')
        plt.title(title)
        plt.colorbar()
        plt.axis('off')
        plt.show()
    except ImportError:
        print("Matplotlib is required for plotting frequency spectrum")


def extract_frequency_features(image: np.ndarray) -> dict:
    """
    Extract features from frequency domain.
    
    Args:
        image: Input image
        
    Returns:
        Dictionary containing frequency features
    """
    if image is None:
        return {}
    
    try:
        magnitude, phase = fourier_transform(image)
        
        # Calculate frequency features
        features = {
            'magnitude_mean': np.mean(magnitude),
            'magnitude_std': np.std(magnitude),
            'magnitude_max': np.max(magnitude),
            'magnitude_min': np.min(magnitude),
            'phase_mean': np.mean(phase),
            'phase_std': np.std(phase),
            'energy_low_freq': np.sum(magnitude[:magnitude.shape[0]//4, :magnitude.shape[1]//4]),
            'energy_high_freq': np.sum(magnitude[magnitude.shape[0]//4:, magnitude.shape[1]//4:])
        }
        
        return features
    except Exception as e:
        return {'error': str(e)} 