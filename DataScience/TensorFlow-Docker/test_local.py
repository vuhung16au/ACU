#!/usr/bin/env python3
"""
Local test script for TensorFlow MNIST application
==================================================

This script tests the TensorFlow installation and basic functionality
without running the full training process.
"""

import sys
import numpy as np

def test_tensorflow_import():
    """Test TensorFlow import and version"""
    try:
        import tensorflow as tf
        print(f"✓ TensorFlow imported successfully")
        print(f"  Version: {tf.__version__}")
        return True
    except ImportError as e:
        print(f"✗ Failed to import TensorFlow: {e}")
        return False

def test_mnist_dataset():
    """Test MNIST dataset loading"""
    try:
        import tensorflow as tf
        
        print("\nTesting MNIST dataset loading...")
        mnist = tf.keras.datasets.mnist
        
        # Load a small subset for testing
        (x_train, y_train), (x_test, y_test) = mnist.load_data()
        
        print(f"✓ MNIST dataset loaded successfully")
        print(f"  Training data shape: {x_train.shape}")
        print(f"  Test data shape: {x_test.shape}")
        print(f"  Number of classes: {len(np.unique(y_train))}")
        
        # Test data preprocessing
        x_train, x_test = x_train / 255.0, x_test / 255.0
        x_train = x_train[..., tf.newaxis].astype("float32")
        x_test = x_test[..., tf.newaxis].astype("float32")
        
        print(f"✓ Data preprocessing completed")
        print(f"  Processed training shape: {x_train.shape}")
        print(f"  Processed test shape: {x_test.shape}")
        
        return True
    except Exception as e:
        print(f"✗ Failed to load MNIST dataset: {e}")
        return False

def test_model_creation():
    """Test model creation"""
    try:
        import tensorflow as tf
        from tensorflow.keras.layers import Dense, Flatten, Conv2D
        from tensorflow.keras import Model
        
        print("\nTesting model creation...")
        
        class MyModel(Model):
            def __init__(self):
                super().__init__()
                self.conv1 = Conv2D(32, 3, activation='relu')
                self.flatten = Flatten()
                self.d1 = Dense(128, activation='relu')
                self.d2 = Dense(10)
            
            def call(self, x):
                x = self.conv1(x)
                x = self.flatten(x)
                x = self.d1(x)
                return self.d2(x)
        
        # Create model instance
        model = MyModel()
        
        # Test with dummy data
        dummy_input = tf.random.normal([1, 28, 28, 1])
        output = model(dummy_input)
        
        print(f"✓ Model created successfully")
        print(f"  Model parameters: {model.count_params():,}")
        print(f"  Output shape: {output.shape}")
        
        return True
    except Exception as e:
        print(f"✗ Failed to create model: {e}")
        return False

def test_training_components():
    """Test training components"""
    try:
        import tensorflow as tf
        
        print("\nTesting training components...")
        
        # Test loss function
        loss_object = tf.keras.losses.SparseCategoricalCrossentropy(from_logits=True)
        print(f"✓ Loss function created")
        
        # Test optimizer
        optimizer = tf.keras.optimizers.Adam()
        print(f"✓ Optimizer created")
        
        # Test metrics
        train_loss = tf.keras.metrics.Mean(name='train_loss')
        train_accuracy = tf.keras.metrics.SparseCategoricalAccuracy(name='train_accuracy')
        print(f"✓ Metrics created")
        
        return True
    except Exception as e:
        print(f"✗ Failed to create training components: {e}")
        return False

def main():
    """Run all tests"""
    print("=== TensorFlow MNIST Local Test ===")
    print("Testing TensorFlow installation and basic functionality...")
    
    tests = [
        ("TensorFlow Import", test_tensorflow_import),
        ("MNIST Dataset", test_mnist_dataset),
        ("Model Creation", test_model_creation),
        ("Training Components", test_training_components),
    ]
    
    passed = 0
    total = len(tests)
    
    for test_name, test_func in tests:
        print(f"\n--- {test_name} ---")
        if test_func():
            passed += 1
        else:
            print(f"Test '{test_name}' failed!")
    
    print(f"\n=== Test Results ===")
    print(f"Passed: {passed}/{total}")
    
    if passed == total:
        print("✓ All tests passed! TensorFlow is ready to use.")
        return 0
    else:
        print("✗ Some tests failed. Please check your TensorFlow installation.")
        return 1

if __name__ == "__main__":
    sys.exit(main())
