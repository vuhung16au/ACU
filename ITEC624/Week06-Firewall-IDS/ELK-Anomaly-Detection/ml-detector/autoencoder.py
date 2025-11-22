#!/usr/bin/env python3
"""
Autoencoder Anomaly Detector
Detects anomalies based on reconstruction error.
Excels at detecting pattern-based and sequential anomalies.
"""

import os
import numpy as np
import yaml
from pathlib import Path

# Suppress TensorFlow warnings
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

import tensorflow as tf
from tensorflow import keras
from tensorflow.keras import layers, Model
from tensorflow.keras.callbacks import EarlyStopping


class AutoencoderDetector:
    """
    Autoencoder implementation for anomaly detection.
    
    How it works:
    - Trained to reconstruct normal data
    - Anomalies have high reconstruction error
    - Returns anomaly score based on reconstruction error
    """
    
    def __init__(self, config_path='config/ml-config.yaml'):
        """Initialize the detector with configuration"""
        self.model = None
        self.threshold = None
        self.config = self._load_config(config_path)
        self.model_path = 'models/autoencoder.h5'
        
        # Model parameters from config
        params = self.config.get('autoencoder', {})
        self.architecture = params.get('architecture', [8, 16, 8, 4, 8, 16, 8])
        self.epochs = params.get('epochs', 50)
        self.batch_size = params.get('batch_size', 32)
        self.learning_rate = params.get('learning_rate', 0.001)
        self.validation_split = params.get('validation_split', 0.2)
        self.early_stopping_patience = params.get('early_stopping_patience', 5)
        
        # Detection threshold from config
        thresholds = self.config.get('thresholds', {})
        self.threshold = thresholds.get('autoencoder_reconstruction', 0.08)
        
        print(f"[AE] Initialized with architecture={self.architecture}, "
              f"epochs={self.epochs}, threshold={self.threshold}")
    
    def _load_config(self, config_path):
        """Load configuration from YAML file"""
        try:
            full_path = Path(config_path)
            if full_path.exists():
                with open(full_path, 'r') as f:
                    return yaml.safe_load(f)
        except Exception as e:
            print(f"[AE] Warning: Could not load config: {e}")
        
        return {}
    
    def _build_model(self, input_dim):
        """
        Build the autoencoder model with symmetric architecture.
        
        Args:
            input_dim: Number of input features
        """
        # Input layer
        input_layer = layers.Input(shape=(input_dim,))
        
        # Encoder
        encoded = input_layer
        for i, units in enumerate(self.architecture[:len(self.architecture)//2 + 1]):
            if i == 0:
                encoded = layers.Dense(units, activation='relu', name=f'encoder_{i}')(encoded)
            else:
                encoded = layers.Dense(units, activation='relu', name=f'encoder_{i}')(encoded)
        
        # Decoder
        decoded = encoded
        decoder_layers = self.architecture[len(self.architecture)//2 + 1:]
        for i, units in enumerate(decoder_layers):
            decoded = layers.Dense(units, activation='relu', name=f'decoder_{i}')(decoded)
        
        # Output layer (reconstruction)
        output_layer = layers.Dense(input_dim, activation='sigmoid', name='output')(decoded)
        
        # Build model
        self.model = Model(inputs=input_layer, outputs=output_layer)
        
        # Compile
        optimizer = keras.optimizers.Adam(learning_rate=self.learning_rate)
        self.model.compile(optimizer=optimizer, loss='mse', metrics=['mae'])
        
        print(f"[AE] Model built with input_dim={input_dim}")
        return self.model
    
    def train(self, X, save=True):
        """
        Train the Autoencoder model on normal data.
        
        Args:
            X: Feature matrix (numpy array or pandas DataFrame)
            save: Whether to save the trained model
        """
        print(f"[AE] Training on {len(X)} samples...")
        
        # Normalize data
        X_normalized = self._normalize(X)
        
        # Build model if not exists
        if self.model is None:
            self._build_model(X.shape[1])
        
        # Early stopping callback
        early_stopping = EarlyStopping(
            monitor='val_loss',
            patience=self.early_stopping_patience,
            restore_best_weights=True,
            verbose=0
        )
        
        # Train
        history = self.model.fit(
            X_normalized, X_normalized,
            epochs=self.epochs,
            batch_size=self.batch_size,
            validation_split=self.validation_split,
            callbacks=[early_stopping],
            verbose=0
        )
        
        # Calculate threshold (95th percentile of training reconstruction errors)
        reconstructions = self.model.predict(X_normalized, verbose=0)
        errors = np.mean(np.square(X_normalized - reconstructions), axis=1)
        self.threshold = np.percentile(errors, 95)
        
        if save:
            self.save_model()
        
        final_loss = history.history['loss'][-1]
        final_val_loss = history.history['val_loss'][-1]
        print(f"[AE] Training completed! Loss: {final_loss:.4f}, Val Loss: {final_val_loss:.4f}")
        print(f"[AE] Threshold set to: {self.threshold:.4f}")
        
        return self
    
    def _normalize(self, X):
        """Normalize data to 0-1 range (min-max scaling)"""
        X_min = X.min(axis=0)
        X_max = X.max(axis=0)
        return (X - X_min) / (X_max - X_min + 1e-10)
    
    def predict(self, X):
        """
        Predict anomaly scores for new data.
        
        Args:
            X: Feature matrix
            
        Returns:
            scores: Reconstruction errors (higher = more anomalous)
            labels: Binary labels (1 = anomaly, 0 = normal)
        """
        if self.model is None:
            raise ValueError("Model not trained or loaded!")
        
        # Normalize
        X_normalized = self._normalize(X)
        
        # Reconstruct
        reconstructions = self.model.predict(X_normalized, verbose=0)
        
        # Calculate reconstruction errors (MSE per sample)
        errors = np.mean(np.square(X_normalized - reconstructions), axis=1)
        
        # Normalize scores to 0-1 range
        scores = errors / (errors.max() + 1e-10)
        
        # Apply threshold
        if self.threshold is not None:
            labels = (errors > self.threshold).astype(int)
        else:
            # Use median as threshold if not set
            labels = (errors > np.median(errors)).astype(int)
        
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
            self.model.save(self.model_path)
            
            # Save threshold separately
            threshold_path = self.model_path.replace('.h5', '_threshold.npy')
            np.save(threshold_path, self.threshold)
            
            print(f"[AE] Model saved to {self.model_path}")
            print(f"[AE] Threshold saved to {threshold_path}")
        except Exception as e:
            print(f"[AE] Error saving model: {e}")
    
    def load_model(self):
        """Load a pre-trained model from disk"""
        try:
            if os.path.exists(self.model_path):
                self.model = keras.models.load_model(self.model_path)
                print(f"[AE] Model loaded from {self.model_path}")
                
                # Load threshold
                threshold_path = self.model_path.replace('.h5', '_threshold.npy')
                if os.path.exists(threshold_path):
                    self.threshold = float(np.load(threshold_path))
                    print(f"[AE] Threshold loaded: {self.threshold:.4f}")
                
                return True
            else:
                print(f"[AE] Model file not found: {self.model_path}")
                return False
        except Exception as e:
            print(f"[AE] Error loading model: {e}")
            return False


if __name__ == '__main__':
    # Test the detector
    print("Testing Autoencoder Detector...")
    
    # Generate synthetic data
    np.random.seed(42)
    
    # Normal data: 1000 samples, 6 features
    X_normal = np.random.randn(1000, 6) * 0.5 + np.array([150, 100, 0.02, 1000, 50, 0.8])
    X_normal = np.abs(X_normal)  # Ensure positive values
    
    # Anomalous data: different patterns
    X_anomaly = np.random.randn(50, 6) * 0.5 + np.array([500, 5000, 0.5, 10000, 200, 0.3])
    X_anomaly = np.abs(X_anomaly)
    
    # Train detector
    detector = AutoencoderDetector()
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

