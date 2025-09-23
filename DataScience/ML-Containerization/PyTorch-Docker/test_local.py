#!/usr/bin/env python3
"""
Local test script for PyTorch MNIST application
===============================================

This script tests the PyTorch installation and basic functionality
without running the full training process.
"""

import sys
import numpy as np

def test_pytorch_import():
    """Test PyTorch import and version"""
    try:
        import torch
        print(f"✓ PyTorch imported successfully")
        print(f"  Version: {torch.__version__}")
        print(f"  CUDA available: {torch.cuda.is_available()}")
        if torch.cuda.is_available():
            print(f"  CUDA version: {torch.version.cuda}")
        return True
    except ImportError as e:
        print(f"✗ Failed to import PyTorch: {e}")
        return False

def test_torchvision_import():
    """Test torchvision import"""
    try:
        import torchvision
        print(f"✓ Torchvision imported successfully")
        print(f"  Version: {torchvision.__version__}")
        return True
    except ImportError as e:
        print(f"✗ Failed to import torchvision: {e}")
        return False

def test_mnist_dataset():
    """Test MNIST dataset loading"""
    try:
        import torch
        import torchvision
        import torchvision.transforms as transforms
        
        print("\nTesting MNIST dataset loading...")
        
        # Define transforms
        transform = transforms.Compose([
            transforms.ToTensor(),
            transforms.Normalize((0.5,), (0.5,))
        ])
        
        # Load a small subset for testing
        train_dataset = torchvision.datasets.MNIST(
            root='./data', 
            train=True, 
            download=True, 
            transform=transform
        )
        
        test_dataset = torchvision.datasets.MNIST(
            root='./data', 
            train=False, 
            download=True, 
            transform=transform
        )
        
        print(f"✓ MNIST dataset loaded successfully")
        print(f"  Training data size: {len(train_dataset)}")
        print(f"  Test data size: {len(test_dataset)}")
        print(f"  Number of classes: 10")
        
        # Test data preprocessing
        sample_data, sample_label = train_dataset[0]
        print(f"✓ Data preprocessing completed")
        print(f"  Sample data shape: {sample_data.shape}")
        print(f"  Sample label: {sample_label}")
        print(f"  Data type: {sample_data.dtype}")
        
        return True
    except Exception as e:
        print(f"✗ Failed to load MNIST dataset: {e}")
        return False

def test_model_creation():
    """Test model creation"""
    try:
        import torch
        import torch.nn as nn
        import torch.nn.functional as F
        
        print("\nTesting model creation...")
        
        class CNN(nn.Module):
            def __init__(self):
                super(CNN, self).__init__()
                self.conv1 = nn.Conv2d(1, 32, kernel_size=3, padding=1)
                self.flatten = nn.Flatten()
                self.fc1 = nn.Linear(32 * 28 * 28, 128)
                self.fc2 = nn.Linear(128, 10)
            
            def forward(self, x):
                x = F.relu(self.conv1(x))
                x = self.flatten(x)
                x = F.relu(self.fc1(x))
                x = self.fc2(x)
                return x
        
        # Create model instance
        model = CNN()
        
        # Test with dummy data
        dummy_input = torch.randn(1, 1, 28, 28)
        output = model(dummy_input)
        
        # Count parameters
        total_params = sum(p.numel() for p in model.parameters())
        
        print(f"✓ Model created successfully")
        print(f"  Model parameters: {total_params:,}")
        print(f"  Output shape: {output.shape}")
        
        return True
    except Exception as e:
        print(f"✗ Failed to create model: {e}")
        return False

def test_training_components():
    """Test training components"""
    try:
        import torch
        import torch.nn as nn
        import torch.optim as optim
        
        print("\nTesting training components...")
        
        # Test loss function
        criterion = nn.CrossEntropyLoss()
        print(f"✓ Loss function created")
        
        # Test optimizer with dummy parameters
        dummy_params = [torch.randn(10, requires_grad=True)]
        optimizer = optim.Adam(dummy_params)
        print(f"✓ Optimizer created")
        
        # Test device selection
        device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')
        print(f"✓ Device selected: {device}")
        
        return True
    except Exception as e:
        print(f"✗ Failed to create training components: {e}")
        return False

def test_data_loader():
    """Test DataLoader creation"""
    try:
        import torch
        import torchvision
        import torchvision.transforms as transforms
        from torch.utils.data import DataLoader
        
        print("\nTesting DataLoader creation...")
        
        # Define transforms
        transform = transforms.Compose([
            transforms.ToTensor(),
            transforms.Normalize((0.5,), (0.5,))
        ])
        
        # Create a small dataset for testing
        test_dataset = torchvision.datasets.MNIST(
            root='./data', 
            train=False, 
            download=True, 
            transform=transform
        )
        
        # Create DataLoader
        test_loader = DataLoader(test_dataset, batch_size=16, shuffle=False)
        
        # Test iteration
        batch = next(iter(test_loader))
        data, target = batch
        
        print(f"✓ DataLoader created successfully")
        print(f"  Batch data shape: {data.shape}")
        print(f"  Batch target shape: {target.shape}")
        
        return True
    except Exception as e:
        print(f"✗ Failed to create DataLoader: {e}")
        return False

def main():
    """Run all tests"""
    print("=== PyTorch MNIST Local Test ===")
    print("Testing PyTorch installation and basic functionality...")
    
    tests = [
        ("PyTorch Import", test_pytorch_import),
        ("Torchvision Import", test_torchvision_import),
        ("MNIST Dataset", test_mnist_dataset),
        ("Model Creation", test_model_creation),
        ("Training Components", test_training_components),
        ("DataLoader", test_data_loader),
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
        print("✓ All tests passed! PyTorch is ready to use.")
        return 0
    else:
        print("✗ Some tests failed. Please check your PyTorch installation.")
        return 1

if __name__ == "__main__":
    sys.exit(main())