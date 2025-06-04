#!/usr/bin/env python3
# -*- coding: utf-8 -*-

## Self-Organizing Map (SOM) Implementation
## This notebook demonstrates a functional implementation of SOM.
## SOM is an unsupervised learning algorithm that creates a low-dimensional representation
## of high-dimensional data while preserving the topological properties of the input space.

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from typing import List, Tuple, Optional, Dict
from sklearn.datasets import load_iris
from sklearn.preprocessing import StandardScaler

## 2. Set Random Seed
np.random.seed(2220)

## 3. Core SOM Functions
def initialize_som(
    map_size: Tuple[int, int],
    input_dim: int
) -> Tuple[np.ndarray, np.ndarray]:
    """
    Initialize SOM weights and coordinate grid.
    
    Args:
        map_size: Size of the SOM grid (rows, columns)
        input_dim: Input dimension
        
    Returns:
        Tuple of (weights, coordinates)
    """
    # Initialize weights randomly
    weights = np.random.randn(map_size[0], map_size[1], input_dim)
    
    # Create coordinate grid for distance calculations
    coords = np.array([
        [(i, j) for j in range(map_size[1])]
        for i in range(map_size[0])
    ])
    
    return weights, coords

def find_bmu(
    x: np.ndarray,
    weights: np.ndarray
) -> Tuple[int, int]:
    """
    Find Best Matching Unit (BMU) for input vector.
    
    Args:
        x: Input vector
        weights: SOM weight matrix
        
    Returns:
        Tuple of (row, column) indices of BMU
    """
    # Calculate distances to all neurons
    distances = np.sum((weights - x) ** 2, axis=2)
    
    # Find BMU
    bmu_idx = np.unravel_index(np.argmin(distances), distances.shape)
    return bmu_idx

def calculate_neighborhood(
    bmu_idx: Tuple[int, int],
    coords: np.ndarray,
    iteration: int,
    n_iterations: int,
    learning_rate: float,
    sigma: float
) -> np.ndarray:
    """
    Calculate neighborhood function for current iteration.
    
    Hyperparameters:
    - learning_rate (float): Initial learning rate.
      Higher values make the SOM learn faster but may lead to instability.
    - sigma (float): Initial neighborhood radius.
      Higher values make the SOM consider more neurons in each update.
    - n_iterations (int): Number of training iterations.
      More iterations may improve the quality of the map but take longer to train.
    
    Args:
        bmu_idx: BMU indices
        coords: Coordinate grid
        iteration: Current iteration
        n_iterations: Total number of iterations
        learning_rate: Initial learning rate
        sigma: Initial neighborhood radius
        
    Returns:
        Neighborhood matrix
    """
    # Calculate current learning rate and neighborhood radius
    current_lr = learning_rate * (1 - iteration / n_iterations)
    current_sigma = sigma * (1 - iteration / n_iterations)
    
    # Calculate distances to BMU
    distances = np.sum((coords - bmu_idx) ** 2, axis=2)
    
    # Calculate neighborhood function
    neighborhood = np.exp(-distances / (2 * current_sigma ** 2))
    
    return current_lr * neighborhood

def train_som(
    X: np.ndarray,
    map_size: Tuple[int, int],
    learning_rate: float = 0.1,
    sigma: float = 1.0,
    n_iterations: int = 1000
) -> Tuple[np.ndarray, np.ndarray]:
    """
    Train the SOM.
    
    Args:
        X: Input data of shape (n_samples, n_features)
        map_size: Size of the SOM grid (rows, columns)
        learning_rate: Initial learning rate
        sigma: Initial neighborhood radius
        n_iterations: Number of training iterations
        
    Returns:
        Tuple of (weights, coordinates)
    """
    n_samples, input_dim = X.shape
    
    # Initialize SOM
    weights, coords = initialize_som(map_size, input_dim)
    
    for iteration in range(n_iterations):
        # Select random sample
        idx = np.random.randint(n_samples)
        x = X[idx]
        
        # Find BMU
        bmu_idx = find_bmu(x, weights)
        
        # Calculate neighborhood
        neighborhood = calculate_neighborhood(
            bmu_idx, coords, iteration,
            n_iterations, learning_rate, sigma
        )
        
        # Update weights
        for i in range(map_size[0]):
            for j in range(map_size[1]):
                weights[i, j] += neighborhood[i, j] * (x - weights[i, j])
        
        if (iteration + 1) % 100 == 0:
            print(f"Iteration {iteration + 1}/{n_iterations}")
    
    return weights, coords

def transform_data(
    X: np.ndarray,
    weights: np.ndarray
) -> np.ndarray:
    """
    Transform input data to SOM coordinates.
    
    Args:
        X: Input data of shape (n_samples, n_features)
        weights: SOM weight matrix
        
    Returns:
        Array of BMU coordinates for each sample
    """
    n_samples = X.shape[0]
    bmu_coords = np.zeros((n_samples, 2))
    
    for i in range(n_samples):
        bmu_idx = find_bmu(X[i], weights)
        bmu_coords[i] = bmu_idx
    
    return bmu_coords

## 4. Visualization Functions
def plot_weights(
    weights: np.ndarray,
    feature_names: Optional[List[str]] = None
) -> None:
    """
    Plot the learned weights using seaborn.
    
    Args:
        weights: SOM weight matrix
        feature_names: List of feature names
    """
    n_features = weights.shape[2]
    n_cols = min(4, n_features)
    n_rows = (n_features + n_cols - 1) // n_cols
    
    plt.figure(figsize=(4 * n_cols, 4 * n_rows))
    
    for i in range(n_features):
        plt.subplot(n_rows, n_cols, i + 1)
        sns.heatmap(
            weights[:, :, i],
            cmap='viridis',
            cbar_kws={'label': 'Weight Value'}
        )
        
        if feature_names is not None:
            plt.title(feature_names[i])
        else:
            plt.title(f'Feature {i + 1}')
    
    plt.tight_layout()
    plt.savefig('algorithms/specialized_models/som-weights.png')
    plt.close()

def plot_umatrix(weights: np.ndarray) -> None:
    """
    Plot the U-matrix (unified distance matrix) using seaborn.
    
    Args:
        weights: SOM weight matrix
    """
    map_size = weights.shape[:2]
    
    # Calculate distances between neighboring neurons
    umatrix = np.zeros(map_size)
    
    for i in range(map_size[0]):
        for j in range(map_size[1]):
            # Get neighboring neurons
            neighbors = []
            if i > 0:
                neighbors.append(weights[i-1, j])
            if i < map_size[0] - 1:
                neighbors.append(weights[i+1, j])
            if j > 0:
                neighbors.append(weights[i, j-1])
            if j < map_size[1] - 1:
                neighbors.append(weights[i, j+1])
            
            # Calculate average distance to neighbors
            if neighbors:
                umatrix[i, j] = np.mean([
                    np.linalg.norm(weights[i, j] - neighbor)
                    for neighbor in neighbors
                ])
    
    plt.figure(figsize=(8, 6))
    sns.heatmap(
        umatrix,
        cmap='viridis',
        cbar_kws={'label': 'Distance'}
    )
    plt.title('U-Matrix')
    plt.savefig('algorithms/specialized_models/som-umatrix.png')
    plt.close()

## 5. Example Usage
# Load data
iris = load_iris()
X = iris.data
feature_names = iris.feature_names

# Scale data
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)

# Train SOM
weights, coords = train_som(
    X_scaled,
    map_size=(10, 10),
    learning_rate=0.1,
    sigma=1.0,
    n_iterations=1000
)

# Transform data
bmu_coords = transform_data(X_scaled, weights)

# Plot results
plot_weights(weights, feature_names)
plot_umatrix(weights)

# Print sample mappings
print("\nSample Mappings:")
for i in range(5):
    print(f"Sample {i + 1}: ({bmu_coords[i, 0]:.0f}, {bmu_coords[i, 1]:.0f})")