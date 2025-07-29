"""
Practical Applications component for OpenCV operations.
"""

import cv2
import numpy as np
import streamlit as st
from typing import Optional
from .base import BaseComponent, OperationMixin


class PracticalApplicationsComponent(BaseComponent, OperationMixin):
    """Component for practical applications."""
    
    def __init__(self):
        super().__init__("Practical Applications", "Real-world applications of OpenCV")
    
    def render(self, image: np.ndarray) -> None:
        """Render the practical applications section."""
        st.header("🏥 Practical Applications")
        
        if not self.validate_input(image):
            self.show_warning("Please upload an image to start experimenting with practical applications.")
            return
        
        # Face Detection
        self._render_face_detection(image)
        st.divider()
        
        # Object Detection
        self._render_object_detection(image)
        st.divider()
        
        # Medical Imaging
        self._render_medical_imaging(image)
        st.divider()
        
        # Computer Vision Tasks
        self._render_computer_vision_tasks(image)
    
    def _render_face_detection(self, image: np.ndarray) -> None:
        """Render face detection section."""
        st.subheader("👤 Face Detection")
        face_col1, face_col2 = st.columns(2)
        
        with face_col1:
            face_method = st.selectbox(
                "Face Detection Method",
                ["Haar Cascade", "HOG", "DNN"],
                help="Choose face detection method"
            )
            
            if face_method == "Haar Cascade":
                self._render_haar_face_detection(image)
            elif face_method == "HOG":
                if st.button("Detect Faces (HOG)"):
                    processed = self._detect_faces_hog(image)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "HOG Face Detection")
                        self.create_download(processed, "hog_face_detection.png", "Download HOG Face Detection")
            elif face_method == "DNN":
                confidence_threshold = st.slider("Confidence Threshold", 0.1, 1.0, 0.5, 0.1)
                if st.button("Detect Faces (DNN)"):
                    processed = self._detect_faces_dnn(image, confidence_threshold)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "DNN Face Detection")
                        self.create_download(processed, "dnn_face_detection.png", "Download DNN Face Detection")
    
    def _render_haar_face_detection(self, image: np.ndarray) -> None:
        """Render Haar cascade face detection controls."""
        scale_factor = st.slider("Scale Factor", 1.01, 1.5, 1.1, 0.01)
        min_neighbors = st.slider("Min Neighbors", 1, 10, 3, 1)
        
        if st.button("Detect Faces (Haar)"):
            processed = self._detect_faces_haar(image, scale_factor, min_neighbors)
            if processed is not None:
                self.display_result(image, processed, "Original", "Haar Face Detection")
                self.create_download(processed, "haar_face_detection.png", "Download Haar Face Detection")
    
    def _render_object_detection(self, image: np.ndarray) -> None:
        """Render object detection section."""
        st.subheader("🎯 Object Detection")
        object_col1, object_col2 = st.columns(2)
        
        with object_col1:
            object_method = st.selectbox(
                "Object Detection Method",
                ["Contour Detection", "Template Matching", "Feature Matching"],
                help="Choose object detection method"
            )
            
            if object_method == "Contour Detection":
                threshold_value = st.slider("Threshold Value", 0, 255, 127, 5)
                if st.button("Detect Objects (Contours)"):
                    processed = self._detect_objects_contours(image, threshold_value)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "Contour Object Detection")
                        self.create_download(processed, "contour_object_detection.png", "Download Contour Object Detection")
            
            elif object_method == "Template Matching":
                if st.button("Detect Objects (Template)"):
                    processed = self._detect_objects_template(image)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "Template Object Detection")
                        self.create_download(processed, "template_object_detection.png", "Download Template Object Detection")
            
            elif object_method == "Feature Matching":
                if st.button("Detect Objects (Features)"):
                    processed = self._detect_objects_features(image)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "Feature Object Detection")
                        self.create_download(processed, "feature_object_detection.png", "Download Feature Object Detection")
    
    def _render_medical_imaging(self, image: np.ndarray) -> None:
        """Render medical imaging section."""
        st.subheader("🏥 Medical Imaging")
        medical_col1, medical_col2 = st.columns(2)
        
        with medical_col1:
            medical_application = st.selectbox(
                "Medical Application",
                ["Tumor Detection", "Blood Cell Counting", "X-Ray Enhancement"],
                help="Choose medical imaging application"
            )
            
            if medical_application == "Tumor Detection":
                if st.button("Detect Tumors"):
                    processed = self._detect_tumors_demo(image)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "Tumor Detection")
                        self.create_download(processed, "tumor_detection.png", "Download Tumor Detection")
            
            elif medical_application == "Blood Cell Counting":
                if st.button("Count Blood Cells"):
                    processed = self._count_blood_cells_demo(image)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "Blood Cell Counting")
                        self.create_download(processed, "blood_cell_counting.png", "Download Blood Cell Counting")
            
            elif medical_application == "X-Ray Enhancement":
                if st.button("Enhance X-Ray"):
                    processed = self._enhance_xray_demo(image)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "X-Ray Enhancement")
                        self.create_download(processed, "xray_enhancement.png", "Download X-Ray Enhancement")
    
    def _render_computer_vision_tasks(self, image: np.ndarray) -> None:
        """Render computer vision tasks section."""
        st.subheader("🤖 Computer Vision Tasks")
        cv_col1, cv_col2 = st.columns(2)
        
        with cv_col1:
            cv_task = st.selectbox(
                "Computer Vision Task",
                ["Image Stitching", "Frame Extraction", "Motion Detection", "Background Subtraction", "OCR"],
                help="Choose computer vision task"
            )
            
            if cv_task == "Image Stitching":
                if st.button("Stitch Images"):
                    processed = self._demo_image_stitching(image)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "Image Stitching")
                        self.create_download(processed, "image_stitching.png", "Download Image Stitching")
            
            elif cv_task == "Frame Extraction":
                if st.button("Extract Frame"):
                    processed = self._extract_frame_demo(image)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "Frame Extraction")
                        self.create_download(processed, "frame_extraction.png", "Download Frame Extraction")
            
            elif cv_task == "Motion Detection":
                if st.button("Detect Motion"):
                    processed = self._detect_motion_demo(image)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "Motion Detection")
                        self.create_download(processed, "motion_detection.png", "Download Motion Detection")
            
            elif cv_task == "Background Subtraction":
                if st.button("Subtract Background"):
                    processed = self._subtract_background_demo(image)
                    if processed is not None:
                        self.display_result(image, processed, "Original", "Background Subtraction")
                        self.create_download(processed, "background_subtraction.png", "Download Background Subtraction")
            
            elif cv_task == "OCR":
                ocr_method = st.selectbox("OCR Method", ["Tesseract", "EasyOCR"], 
                                        help="Tesseract: Fast and lightweight. EasyOCR: More accurate but slower.")
                
                # OCR preprocessing options
                with st.expander("OCR Preprocessing Options"):
                    apply_preprocessing = st.checkbox("Apply Image Preprocessing", value=True,
                                                   help="Apply denoising and thresholding for better OCR results")
                    confidence_threshold = st.slider("Confidence Threshold (EasyOCR)", 0.1, 1.0, 0.5, 0.1,
                                                  help="Minimum confidence for text detection (EasyOCR only)")
                
                if st.button("Perform OCR"):
                    with st.spinner(f"Performing OCR with {ocr_method}..."):
                        result = self._perform_ocr_demo(image, ocr_method, apply_preprocessing, confidence_threshold)
                        if result is not None:
                            st.success("OCR completed successfully!")
                            st.text_area("OCR Result:", result, height=200)
                            
                            # Add download option for OCR result
                            if result and result != "No text detected in the image." and not result.startswith("OCR Error"):
                                st.download_button(
                                    label="Download OCR Result as Text",
                                    data=result,
                                    file_name=f"ocr_result_{ocr_method.lower()}.txt",
                                    mime="text/plain"
                                )
    
    # Helper methods for practical applications
    @OperationMixin.safe_operation
    def _detect_faces_haar(self, image: np.ndarray, scale_factor: float, min_neighbors: int) -> Optional[np.ndarray]:
        """Detect faces using Haar cascade."""
        # Load Haar cascade classifier
        face_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + 'haarcascade_frontalface_default.xml')
        
        # Convert to grayscale
        gray = self.convert_to_grayscale(image)
        
        # Detect faces
        faces = face_cascade.detectMultiScale(gray, scale_factor, min_neighbors)
        
        # Draw rectangles around faces
        result = image.copy()
        for (x, y, w, h) in faces:
            cv2.rectangle(result, (x, y), (x+w, y+h), (255, 0, 0), 2)
        
        return result
    
    @OperationMixin.safe_operation
    def _detect_faces_hog(self, image: np.ndarray) -> Optional[np.ndarray]:
        """Detect faces using HOG."""
        # Load HOG face detector
        hog = cv2.HOGDescriptor()
        hog.setSVMDetector(cv2.HOGDescriptor_getDefaultPeopleDetector())
        
        # Detect faces
        faces, weights = hog.detectMultiScale(image, winStride=(8, 8))
        
        # Draw rectangles around faces
        result = image.copy()
        for (x, y, w, h) in faces:
            cv2.rectangle(result, (x, y), (x+w, y+h), (0, 255, 0), 2)
        
        return result
    
    @OperationMixin.safe_operation
    def _detect_faces_dnn(self, image: np.ndarray, confidence_threshold: float) -> Optional[np.ndarray]:
        """Detect faces using DNN."""
        # Load DNN face detector
        net = cv2.dnn.readNetFromCaffe(
            cv2.data.haarcascades + 'deploy.prototxt',
            cv2.data.haarcascades + 'res10_300x300_ssd_iter_140000.caffemodel'
        )
        
        # Prepare image for DNN
        blob = cv2.dnn.blobFromImage(cv2.resize(image, (300, 300)), 1.0, (300, 300), (104.0, 177.0, 123.0))
        
        # Detect faces
        net.setInput(blob)
        detections = net.forward()
        
        # Draw rectangles around faces
        result = image.copy()
        height, width = image.shape[:2]
        
        for i in range(0, detections.shape[2]):
            confidence = detections[0, 0, i, 2]
            
            if confidence > confidence_threshold:
                box = detections[0, 0, i, 3:7] * np.array([width, height, width, height])
                (startX, startY, endX, endY) = box.astype("int")
                
                cv2.rectangle(result, (startX, startY), (endX, endY), (0, 0, 255), 2)
        
        return result
    
    @OperationMixin.safe_operation
    def _detect_objects_contours(self, image: np.ndarray, threshold_value: int) -> Optional[np.ndarray]:
        """Detect objects using contour detection."""
        # Convert to grayscale
        gray = self.convert_to_grayscale(image)
        
        # Threshold the image
        _, binary = cv2.threshold(gray, threshold_value, 255, cv2.THRESH_BINARY)
        
        # Find contours
        contours, _ = cv2.findContours(binary, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
        
        # Draw contours
        result = image.copy()
        cv2.drawContours(result, contours, -1, (0, 255, 0), 2)
        
        return result
    
    @OperationMixin.safe_operation
    def _detect_objects_template(self, image: np.ndarray) -> Optional[np.ndarray]:
        """Detect objects using template matching."""
        # Convert to grayscale
        gray = self.convert_to_grayscale(image)
        
        # Create a simple template (top-left quarter of the image)
        height, width = gray.shape
        template = gray[0:height//4, 0:width//4]
        
        # Template matching
        result = cv2.matchTemplate(gray, template, cv2.TM_CCOEFF_NORMED)
        
        # Find matches
        threshold = 0.8
        locations = np.where(result >= threshold)
        
        # Draw rectangles around matches
        result_img = image.copy()
        for pt in zip(*locations[::-1]):
            cv2.rectangle(result_img, pt, (pt[0] + template.shape[1], pt[1] + template.shape[0]), (0, 255, 0), 2)
        
        return result_img
    
    @OperationMixin.safe_operation
    def _detect_objects_features(self, image: np.ndarray) -> Optional[np.ndarray]:
        """Detect objects using feature matching."""
        # Convert to grayscale
        gray = self.convert_to_grayscale(image)
        
        # Create ORB detector
        orb = cv2.ORB_create()
        
        # Detect keypoints and descriptors
        keypoints, descriptors = orb.detectAndCompute(gray, None)
        
        # Draw keypoints
        result = image.copy()
        cv2.drawKeypoints(image, keypoints, result, color=(0, 255, 0))
        
        return result
    
    @OperationMixin.safe_operation
    def _demo_image_stitching(self, image: np.ndarray) -> Optional[np.ndarray]:
        """Demo image stitching."""
        # For demo purposes, we'll create a simple panorama effect
        height, width = image.shape[:2]
        
        # Create a wider canvas
        panorama = np.zeros((height, width * 2, 3), dtype=np.uint8)
        
        # Copy the image to the left side
        panorama[:, :width] = image
        
        # Create a slightly modified version for the right side
        modified = cv2.resize(image, (width, height))
        panorama[:, width:] = modified
        
        return panorama
    
    @OperationMixin.safe_operation
    def _extract_frame_demo(self, image: np.ndarray) -> Optional[np.ndarray]:
        """Demo frame extraction."""
        # For demo purposes, we'll just return the image
        # In practice, this would extract frames from a video
        return image
    
    @OperationMixin.safe_operation
    def _detect_motion_demo(self, image: np.ndarray) -> Optional[np.ndarray]:
        """Demo motion detection."""
        # For demo purposes, we'll create a simple motion detection effect
        # In practice, this would compare frames from a video
        
        # Convert to grayscale
        gray = self.convert_to_grayscale(image)
        
        # Apply some blur to simulate motion blur
        motion_blur = cv2.GaussianBlur(gray, (15, 15), 0)
        
        # Convert back to BGR for display
        result = cv2.cvtColor(motion_blur, cv2.COLOR_GRAY2BGR)
        
        return result
    
    @OperationMixin.safe_operation
    def _subtract_background_demo(self, image: np.ndarray) -> Optional[np.ndarray]:
        """Demo background subtraction."""
        # For demo purposes, we'll create a simple background subtraction effect
        # In practice, this would use a background model
        
        # Convert to grayscale
        gray = self.convert_to_grayscale(image)
        
        # Create a simple background (blurred version)
        background = cv2.GaussianBlur(gray, (21, 21), 0)
        
        # Subtract background
        foreground = cv2.absdiff(gray, background)
        
        # Threshold
        _, foreground = cv2.threshold(foreground, 25, 255, cv2.THRESH_BINARY)
        
        # Convert back to BGR for display
        result = cv2.cvtColor(foreground, cv2.COLOR_GRAY2BGR)
        
        return result
    
    @OperationMixin.safe_operation
    def _perform_ocr_demo(self, image: np.ndarray, method: str, apply_preprocessing: bool = True, confidence_threshold: float = 0.5) -> Optional[str]:
        """Perform OCR using Tesseract or EasyOCR."""
        try:
            if method == "Tesseract":
                return self._perform_ocr_tesseract(image, apply_preprocessing)
            elif method == "EasyOCR":
                return self._perform_ocr_easyocr(image, apply_preprocessing, confidence_threshold)
            else:
                return f"Unsupported OCR method: {method}"
        except Exception as e:
            return f"OCR Error: {str(e)}"
    
    def _perform_ocr_tesseract(self, image: np.ndarray, apply_preprocessing: bool = True) -> str:
        """Perform OCR using Tesseract."""
        try:
            import pytesseract
            
            # Convert to grayscale for better OCR
            gray = self.convert_to_grayscale(image)
            
            if apply_preprocessing:
                # Apply some preprocessing to improve OCR accuracy
                # Denoise
                denoised = cv2.fastNlMeansDenoising(gray)
                
                # Apply threshold to get binary image
                _, binary = cv2.threshold(denoised, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)
                
                # Perform OCR on preprocessed image
                text = pytesseract.image_to_string(binary, config='--psm 6')
            else:
                # Perform OCR on original grayscale image
                text = pytesseract.image_to_string(gray, config='--psm 6')
            
            # Clean up the text
            text = text.strip()
            
            if not text:
                return "No text detected in the image."
            
            return text
            
        except ImportError:
            return "Tesseract not installed. Please install pytesseract and tesseract-ocr."
        except Exception as e:
            return f"Tesseract OCR Error: {str(e)}"
    
    def _perform_ocr_easyocr(self, image: np.ndarray, apply_preprocessing: bool = True, confidence_threshold: float = 0.5) -> str:
        """Perform OCR using EasyOCR."""
        try:
            import easyocr
            
            # Initialize EasyOCR reader (English)
            reader = easyocr.Reader(['en'])
            
            # Apply preprocessing if requested
            if apply_preprocessing:
                # Convert to grayscale
                gray = self.convert_to_grayscale(image)
                
                # Apply denoising
                denoised = cv2.fastNlMeansDenoising(gray)
                
                # Apply adaptive threshold
                binary = cv2.adaptiveThreshold(denoised, 255, cv2.ADAPTIVE_THRESH_GAUSSIAN_C, cv2.THRESH_BINARY, 11, 2)
                
                # Convert back to BGR for EasyOCR
                processed_image = cv2.cvtColor(binary, cv2.COLOR_GRAY2BGR)
            else:
                processed_image = image
            
            # Perform OCR
            results = reader.readtext(processed_image)
            
            # Extract text from results
            if not results:
                return "No text detected in the image."
            
            # Combine all detected text
            text_parts = []
            for (bbox, text, confidence) in results:
                if confidence > confidence_threshold:  # Use user-defined confidence threshold
                    text_parts.append(text)
            
            if not text_parts:
                return f"No text detected with confidence above {confidence_threshold}."
            
            return " ".join(text_parts)
            
        except ImportError:
            return "EasyOCR not installed. Please install easyocr."
        except Exception as e:
            return f"EasyOCR Error: {str(e)}"
    
    @OperationMixin.safe_operation
    def _detect_tumors_demo(self, image: np.ndarray) -> Optional[np.ndarray]:
        """Demo tumor detection."""
        # For demo purposes, we'll create a simple tumor detection effect
        # In practice, this would use a medical imaging model
        
        # Convert to grayscale
        gray = self.convert_to_grayscale(image)
        
        # Apply some processing to simulate tumor detection
        # In practice, this would use a trained model
        processed = cv2.adaptiveThreshold(gray, 255, cv2.ADAPTIVE_THRESH_GAUSSIAN_C, cv2.THRESH_BINARY, 11, 2)
        
        # Convert back to BGR for display
        result = cv2.cvtColor(processed, cv2.COLOR_GRAY2BGR)
        
        return result
    
    @OperationMixin.safe_operation
    def _count_blood_cells_demo(self, image: np.ndarray) -> Optional[np.ndarray]:
        """Demo blood cell counting."""
        # For demo purposes, we'll create a simple cell counting effect
        # In practice, this would use a cell detection model
        
        # Convert to grayscale
        gray = self.convert_to_grayscale(image)
        
        # Apply some processing to simulate cell detection
        # In practice, this would use a trained model
        processed = cv2.medianBlur(gray, 5)
        
        # Convert back to BGR for display
        result = cv2.cvtColor(processed, cv2.COLOR_GRAY2BGR)
        
        return result
    
    @OperationMixin.safe_operation
    def _enhance_xray_demo(self, image: np.ndarray) -> Optional[np.ndarray]:
        """Demo X-ray enhancement."""
        # For demo purposes, we'll create a simple X-ray enhancement effect
        # In practice, this would use medical imaging techniques
        
        # Convert to grayscale
        gray = self.convert_to_grayscale(image)
        
        # Apply CLAHE for enhancement
        clahe = cv2.createCLAHE(clipLimit=2.0, tileGridSize=(8, 8))
        enhanced = clahe.apply(gray)
        
        # Convert back to BGR for display
        result = cv2.cvtColor(enhanced, cv2.COLOR_GRAY2BGR)
        
        return result 