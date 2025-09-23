#!/usr/bin/env python3
"""
PyTorch MNIST Classification with Docker
=======================================

This script demonstrates a PyTorch implementation for MNIST digit classification.
"""

import torch
import torch.nn as nn
import torch.nn.functional as F
import torchvision
import torchvision.transforms as transforms
import sys

def main():
    print("=== PyTorch MNIST Classification with Docker ===")
    print(f"PyTorch version: {torch.__version__}")
    print(f"Device: {'cuda' if torch.cuda.is_available() else 'cpu'}")
    
    # Load and prepare the MNIST dataset
    print("\nLoading MNIST dataset...")
    
    # Define transforms
    transform = transforms.Compose([
        transforms.ToTensor(),
        transforms.Normalize((0.5,), (0.5,))
    ])
    
    # Load datasets
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
    
    print(f"Training data size: {len(train_dataset)}")
    print(f"Test data size: {len(test_dataset)}")
    print(f"Number of classes: 10")
    
    # Build a simple CNN model
    print("\nBuilding CNN model...")
    
    class SimpleCNN(nn.Module):
        def __init__(self):
            super(SimpleCNN, self).__init__()
            self.conv1 = nn.Conv2d(1, 32, kernel_size=3, padding=1)
            self.fc1 = nn.Linear(32 * 28 * 28, 128)
            self.fc2 = nn.Linear(128, 10)
        
        def forward(self, x):
            x = F.relu(self.conv1(x))
            x = x.view(x.size(0), -1)  # Flatten
            x = F.relu(self.fc1(x))
            x = self.fc2(x)
            return x
    
    # Create model instance
    model = SimpleCNN()
    
    # Count parameters
    total_params = sum(p.numel() for p in model.parameters())
    print(f"Model created with {total_params:,} parameters")
    
    # Test model with dummy data
    print("\nTesting model with dummy data...")
    dummy_input = torch.randn(1, 1, 28, 28)
    output = model(dummy_input)
    print(f"Model output shape: {output.shape}")
    
    # Test with a single real sample
    print("\nTesting with a single MNIST sample...")
    try:
        # Get one sample from test dataset
        sample, label = test_dataset[0]
        print(f"Sample shape: {sample.shape}")
        print(f"True label: {label}")
        
        # Get prediction
        model.eval()
        with torch.no_grad():
            # Add batch dimension
            sample_batch = sample.unsqueeze(0)
            prediction = model(sample_batch)
            _, predicted = torch.max(prediction, 1)
            probabilities = F.softmax(prediction, dim=1)
        
        pred_label = predicted[0].item()
        confidence = probabilities[0][pred_label].item()
        print(f"Predicted: {pred_label}, Confidence: {confidence:.3f}")
    
    except Exception as e:
        print(f"Error during prediction: {e}")
    
    print("\n=== PyTorch MNIST Demo Complete ===")
    print("✓ PyTorch installation working")
    print("✓ MNIST dataset loaded successfully")
    print("✓ Model created and tested")
    print("✓ Basic functionality verified")

if __name__ == "__main__":
    try:
        main()
    except Exception as e:
        print(f"Error: {e}")
        sys.exit(1)