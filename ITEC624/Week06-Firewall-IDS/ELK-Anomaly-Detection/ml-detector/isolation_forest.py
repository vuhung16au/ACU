#!/usr/bin/env python3
"""
Isolation Forest Anomaly Detector
Detects anomalies based on isolation (distance from neighbors).
Excels at detecting volume-based and rate anomalies.
"""

import os
import numpy as np
from sklearn.ensemble import IsolationForest
import joblib
import yaml
from pathlib import Path


class IsolationForestDetector:
    """
    Isolation Forest implementation for anomaly detection.
    
    How it works:
    - Builds random decision trees
    - Anomalies are easier to isolate (fewer splits needed)
    - Returns anomaly score: -1 for anomalies, 1 for normal
    """
    
    def __init__(self, config_path='config/ml-config.yaml'):
        """Initialize the detector with configuration"""
        self.model = None
        self.config = self._load_config(config_path)
        self.model_path = 'models/isolation_forest.pkl'
        
        # Model parameters from config
        params = self.config.get('isolation_forest', {})
        self.contamination = params.get('contamination', 0.05)
        self.n_estimators = params.get('n_estimators', 100)
        self.max_samples = params.get('max_samples', 256)
        self.random_state = params.get('random_state', 42)
        
        # Detection threshold
        thresholds = self.config.get('thresholds', {})
        self.threshold = thresholds.get('isolation_forest', 0.7)
        
        print(f"[IF] Initialized with contamination={self.contamination}, "
              f"n_estimators={self.n_estimators}, threshold={self.threshold}")
    
    def _load_config(self, config_path):
        """Load configuration from YAML file"""
        try:
            full_path = Path(config_path)
            if full_path.exists():
                with open(full_path, 'r') as f:
                    return yaml.safe_load(f)
        except Exception as e:
            print(f"[IF] Warning: Could not load config: {e}")
        
        return {}
    
    def train(self, X, save=True):
        """
        Train the Isolation Forest model on normal data.
        
        Args:
            X: Feature matrix (numpy array or pandas DataFrame)
            save: Whether to save the trained model
        """
        print(f"[IF] Training on {len(X)} samples...")
        
        self.model = IsolationForest(
            contamination=self.contamination,
            n_estimators=self.n_estimators,
            max_samples=min(self.max_samples, len(X)),
            random_state=self.random_state,
            n_jobs=-1,  # Use all CPU cores
            verbose=0
        )
        
        self.model.fit(X)
        
        if save:
            self.save_model()
        
        print(f"[IF] Training completed!")
        return self
    
    def predict(self, X):
        """
        Predict anomaly scores for new data.
        
        Args:
            X: Feature matrix
            
        Returns:
            scores: Anomaly scores (higher = more anomalous)
            labels: Binary labels (1 = anomaly, 0 = normal)
        """
        if self.model is None:
            raise ValueError("Model not trained or loaded!")
        
        # Get decision scores (lower = more anomalous)
        decision_scores = self.model.decision_function(X)
        
        # Convert to anomaly scores (higher = more anomalous)
        # Normalize to 0-1 range
        scores = 1 - (decision_scores - decision_scores.min()) / (decision_scores.max() - decision_scores.min() + 1e-10)
        
        # Apply threshold
        labels = (scores > self.threshold).astype(int)
        
        return scores, labels
    
    def predict_single(self, features):
        """
        Predict anomaly score for a single sample.
        
        Args:
            features: Dictionary or array of features
            
        Returns:
            score: Anomaly score (0-1, higher = more anomalous)
            is_anomaly: Boolean flag
        """
        if isinstance(features, dict):
            # Convert dict to array
            feature_names = self.config.get('features', [])
            X = np.array([[features.get(f, 0) for f in feature_names]])
        else:
            X = np.array([features])
        
        scores, labels = self.predict(X)
        return float(scores[0]), bool(labels[0])
    
    def save_model(self):
        """Save the trained model to disk"""
        try:
            os.makedirs(os.path.dirname(self.model_path), exist_ok=True)
            joblib.dump(self.model, self.model_path)
            print(f"[IF] Model saved to {self.model_path}")
        except Exception as e:
            print(f"[IF] Error saving model: {e}")
    
    def load_model(self):
        """Load a pre-trained model from disk"""
        try:
            if os.path.exists(self.model_path):
                self.model = joblib.load(self.model_path)
                print(f"[IF] Model loaded from {self.model_path}")
                return True
            else:
                print(f"[IF] Model file not found: {self.model_path}")
                return False
        except Exception as e:
            print(f"[IF] Error loading model: {e}")
            return False
    
    def get_feature_importance(self):
        """
        Get feature importance (not directly available in IF,
        but we can estimate based on split frequency)
        """
        if self.model is None:
            return None
        
        # Isolation Forest doesn't have feature_importances_
        # This is a placeholder for future enhancement
        return None


if __name__ == '__main__':
    # Test the detector
    print("Testing Isolation Forest Detector...")
    
    # Generate synthetic data
    np.random.seed(42)
    
    # Normal data: 1000 samples, 6 features
    X_normal = np.random.randn(1000, 6) * 0.5 + np.array([150, 100, 0.02, 1000, 50, 0.8])
    
    # Anomalous data: high request rate, high response time
    X_anomaly = np.random.randn(50, 6) * 0.5 + np.array([500, 5000, 0.5, 10000, 200, 0.3])
    
    # Train detector
    detector = IsolationForestDetector()
    detector.train(X_normal)
    
    # Test on normal data
    scores_normal, labels_normal = detector.predict(X_normal[:10])
    print(f"\nNormal samples - Scores: {scores_normal}")
    print(f"Normal samples - Labels: {labels_normal}")
    
    # Test on anomalies
    scores_anomaly, labels_anomaly = detector.predict(X_anomaly[:10])
    print(f"\nAnomaly samples - Scores: {scores_anomaly}")
    print(f"Anomaly samples - Labels: {labels_anomaly}")
    
    print("\nTest completed!")

