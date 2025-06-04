#!/usr/bin/env python3
# -*- coding: utf-8 -*-

## Isolation Forest Implementation
## This notebook demonstrates a functional implementation of Isolation Forest.
## Isolation Forest is an anomaly detection algorithm that works by isolating observations.
## It uses the fact that anomalies are easier to isolate than normal points.

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from typing import List, Tuple, Optional, Dict, Union
from sklearn.datasets import make_blobs

## 2. Set Random Seed
np.random.seed(2220)

## 3. Core Isolation Tree Functions
def build_isolation_tree(
    X: np.ndarray,
    max_depth: int,
    current_depth: int = 0
) -> Dict:
    """
    Build an isolation tree recursively.
    
    Hyperparameters:
    - max_depth (int): Maximum depth of the tree.
      Larger values allow for more complex splits but may overfit.
    
    Args:
        X: Input data of shape (n_samples, n_features)
        max_depth: Maximum depth of the tree
        current_depth: Current depth in the tree
        
    Returns:
        Dictionary representing a node in the tree
    """
    n_samples = X.shape[0]
    
    # Return leaf node if max depth reached or only one sample
    if current_depth >= max_depth or n_samples <= 1:
        return {
            'type': 'leaf',
            'size': n_samples
        }
    
    # Randomly select a feature
    feature_idx = np.random.randint(0, X.shape[1])
    feature_values = X[:, feature_idx]
    
    # Randomly select a split value
    min_val, max_val = feature_values.min(), feature_values.max()
    split_value = np.random.uniform(min_val, max_val)
    
    # Split data
    left_mask = feature_values < split_value
    right_mask = ~left_mask
    
    # Create internal node
    return {
        'type': 'internal',
        'feature_idx': feature_idx,
        'split_value': split_value,
        'left': build_isolation_tree(X[left_mask], max_depth, current_depth + 1),
        'right': build_isolation_tree(X[right_mask], max_depth, current_depth + 1)
    }

def calculate_path_length(
    x: np.ndarray,
    tree: Dict,
    current_depth: float = 0
) -> float:
    """
    Calculate the path length for a sample in the isolation tree.
    
    Args:
        x: Input sample
        tree: Isolation tree
        current_depth: Current depth in the tree
        
    Returns:
        Path length
    """
    if tree['type'] == 'leaf':
        # Adjust path length for leaf nodes
        n = tree['size']
        if n > 1:
            return current_depth + 2 * (np.log(n - 1) + 0.5772156649) - 2 * (n - 1) / n
        return current_depth
    
    # Traverse tree
    if x[tree['feature_idx']] < tree['split_value']:
        return calculate_path_length(x, tree['left'], current_depth + 1)
    else:
        return calculate_path_length(x, tree['right'], current_depth + 1)

## 4. Main Isolation Forest Functions
def build_isolation_forest(
    X: np.ndarray,
    n_estimators: int = 100,
    max_samples: Optional[int] = None,
    max_depth: int = 8
) -> List[Dict]:
    """
    Build an isolation forest.
    
    Hyperparameters:
    - n_estimators (int): Number of trees in the forest.
      More trees provide better anomaly detection but require more computation.
    - max_samples (int): Number of samples to train each tree.
      Smaller values make the algorithm more efficient but may reduce accuracy.
    - max_depth (int): Maximum depth of each tree.
      Larger values allow for more complex splits but may overfit.
    
    Args:
        X: Input data of shape (n_samples, n_features)
        n_estimators: Number of trees
        max_samples: Number of samples to train each tree
        max_depth: Maximum depth of each tree
        
    Returns:
        List of isolation trees
    """
    n_samples = X.shape[0]
    max_samples = max_samples or min(256, n_samples)
    
    # Build trees
    trees = []
    for _ in range(n_estimators):
        # Sample data
        indices = np.random.choice(n_samples, max_samples, replace=False)
        X_sample = X[indices]
        
        # Build tree
        tree = build_isolation_tree(X_sample, max_depth)
        trees.append(tree)
    
    return trees

def calculate_anomaly_scores(
    X: np.ndarray,
    trees: List[Dict]
) -> np.ndarray:
    """
    Calculate anomaly scores for samples.
    
    Args:
        X: Input data
        trees: List of isolation trees
        
    Returns:
        Anomaly scores
    """
    n_samples = X.shape[0]
    scores = np.zeros(n_samples)
    
    for tree in trees:
        for i in range(n_samples):
            scores[i] += calculate_path_length(X[i], tree)
    
    # Normalize scores
    scores /= len(trees)
    return -scores  # Negative because lower path length means more anomalous

def detect_anomalies(
    X: np.ndarray,
    contamination: float = 0.1,
    n_estimators: int = 100,
    max_samples: Optional[int] = None,
    max_depth: int = 8
) -> Tuple[np.ndarray, np.ndarray]:
    """
    Detect anomalies using Isolation Forest.
    
    Hyperparameters:
    - contamination (float): Expected proportion of outliers.
      Higher values will classify more points as anomalies.
    
    Args:
        X: Input data of shape (n_samples, n_features)
        contamination: Expected proportion of outliers
        n_estimators: Number of trees
        max_samples: Number of samples to train each tree
        max_depth: Maximum depth of each tree
        
    Returns:
        Tuple of (predictions, scores)
        predictions: Array of predictions (-1 for outliers, 1 for inliers)
        scores: Anomaly scores
    """
    # Build isolation forest
    trees = build_isolation_forest(X, n_estimators, max_samples, max_depth)
    
    # Calculate scores
    scores = calculate_anomaly_scores(X, trees)
    
    # Calculate threshold
    threshold = np.percentile(scores, 100 * (1 - contamination))
    
    # Make predictions
    predictions = np.where(scores < threshold, -1, 1)
    
    return predictions, scores

## 5. Visualization Functions
def plot_anomaly_detection(
    X: np.ndarray,
    predictions: np.ndarray,
    scores: np.ndarray
) -> None:
    """
    Plot anomaly detection results using seaborn.
    
    Args:
        X: Input data
        predictions: Array of predictions (-1 for outliers, 1 for inliers)
        scores: Anomaly scores
    """
    plt.figure(figsize=(12, 5))
    
    # Create DataFrame for seaborn
    data = pd.DataFrame({
        'x': X[:, 0],
        'y': X[:, 1],
        'prediction': predictions,
        'score': scores
    })
    
    # Plot scatter plot
    plt.subplot(1, 2, 1)
    sns.scatterplot(
        data=data,
        x='x',
        y='y',
        hue='prediction',
        palette={-1: 'red', 1: 'blue'},
        legend='full'
    )
    plt.title('Anomaly Detection Results')
    plt.xlabel('Feature 1')
    plt.ylabel('Feature 2')
    
    # Plot score distribution
    plt.subplot(1, 2, 2)
    sns.histplot(
        data=data,
        x='score',
        hue='prediction',
        palette={-1: 'red', 1: 'blue'},
        bins=30
    )
    plt.title('Anomaly Score Distribution')
    plt.xlabel('Anomaly Score')
    plt.ylabel('Count')
    
    plt.tight_layout()
    plt.savefig('algorithms/specialized_models/isolation_forest-anomaly-detection.png')
    plt.close()

## 6. Example Usage
# Generate sample data
X, _ = make_blobs(n_samples=300, centers=2, random_state=42)

# Add some outliers
X_outliers = np.random.uniform(low=-10, high=10, size=(20, 2))
X = np.vstack([X, X_outliers])

# Detect anomalies
predictions, scores = detect_anomalies(
    X,
    contamination=0.1,
    n_estimators=100,
    max_depth=8
)

# Plot results
plot_anomaly_detection(X, predictions, scores)