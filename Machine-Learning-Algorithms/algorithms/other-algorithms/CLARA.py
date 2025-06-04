#!/usr/bin/env python3
# -*- coding: utf-8 -*-

## CLARA (Clustering LARge Applications) Implementation
## This notebook demonstrates the implementation of the CLARA clustering algorithm.
## CLARA is a clustering algorithm designed for large datasets that uses sampling
## to reduce computational complexity while maintaining clustering quality.

## Table of Contents
# 1. Import Required Libraries
# 2. Data Generation and Preprocessing
# 3. CLARA Implementation
# 4. Model Training and Evaluation
# 5. Visualization
# 6. Results and Analysis

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import os
from scipy.spatial.distance import cdist
from sklearn.metrics import silhouette_score, calinski_harabasz_score
from sklearn.datasets import make_blobs
from typing import Tuple, List, Optional, Dict

## 2. Set Random Seed
np.random.seed(2220)

## 3. Data Generation and Preprocessing
def generate_data(
    n_samples: int = 300,
    n_features: int = 2,
    centers: int = 3,
    cluster_std: float = 1.0
) -> Tuple[np.ndarray, np.ndarray]:
    """
    Generate sample data for clustering.
    
    Args:
        n_samples: Number of samples to generate
        n_features: Number of features
        centers: Number of clusters
        cluster_std: Standard deviation of clusters
        
    Returns:
        Tuple of (X, y) where:
        - X: Features array of shape (n_samples, n_features)
        - y: True cluster labels
    """
    X, y = make_blobs(
        n_samples=n_samples,
        n_features=n_features,
        centers=centers,
        cluster_std=cluster_std,
        random_state=2220
    )
    return X, y

## 4. Core CLARA Functions
def compute_cost(
    X: np.ndarray,
    medoids: np.ndarray
) -> float:
    """
    Compute total cost of clustering.
    
    Args:
        X: Input data
        medoids: Current medoids
        
    Returns:
        Total clustering cost
    """
    distances = cdist(X, X[medoids])
    return np.sum(np.min(distances, axis=1))

def pam_iteration(
    X: np.ndarray,
    medoids: np.ndarray
) -> Tuple[np.ndarray, float]:
    """
    One iteration of PAM algorithm.
    
    Args:
        X: Input data
        medoids: Current medoids
        
    Returns:
        Tuple of (best_medoids, best_cost) where:
        - best_medoids: Best medoids found
        - best_cost: Cost of best medoids
    """
    n_samples = X.shape[0]
    n_clusters = len(medoids)
    
    # Assign points to nearest medoid
    distances = cdist(X, X[medoids])
    labels = np.argmin(distances, axis=1)
    
    # Try swapping each medoid with each non-medoid
    best_cost = compute_cost(X, medoids)
    best_medoids = medoids.copy()
    
    for i in range(n_clusters):
        for j in range(n_samples):
            if j not in medoids:
                # Try swapping medoid i with point j
                new_medoids = medoids.copy()
                new_medoids[i] = j
                new_cost = compute_cost(X, new_medoids)
                
                if new_cost < best_cost:
                    best_cost = new_cost
                    best_medoids = new_medoids
                    
    return best_medoids, best_cost

def pam(
    X: np.ndarray,
    n_clusters: int,
    max_iter: int = 100,
    random_state: Optional[int] = None
) -> Tuple[np.ndarray, float]:
    """
    Apply PAM algorithm to a sample.
    
    Hyperparameters:
    - max_iter (int): Maximum number of PAM iterations.
      * More iterations can lead to better convergence
      * May cause overfitting if too high
    - random_state (int): Random seed for reproducibility
    
    Args:
        X: Input data
        n_clusters: Number of clusters to form
        max_iter: Maximum number of PAM iterations
        random_state: Random seed for reproducibility
        
    Returns:
        Tuple of (medoids, cost) where:
        - medoids: Final medoids
        - cost: Final clustering cost
    """
    n_samples = X.shape[0]
    
    # Initialize medoids randomly
    np.random.seed(random_state)
    medoids = np.random.choice(n_samples, n_clusters, replace=False)
    
    # PAM iterations
    for _ in range(max_iter):
        new_medoids, cost = pam_iteration(X, medoids)
        if np.array_equal(new_medoids, medoids):
            break
        medoids = new_medoids
        
    return medoids, cost

def clara_clustering(
    X: np.ndarray,
    n_clusters: int = 3,
    n_samples: int = 5,
    sample_size: int = 40,
    max_iter: int = 100,
    random_state: Optional[int] = None
) -> Tuple[np.ndarray, np.ndarray, float]:
    """
    Perform CLARA clustering.
    
    Hyperparameters:
    - n_clusters (int): Number of clusters to form.
      * Determines the number of medoids
      * Should be chosen based on domain knowledge
    - n_samples (int): Number of samples to draw.
      * More samples increase chances of finding good clustering
      * Higher values increase computation time
    - sample_size (int): Size of each sample.
      * Should be large enough to represent data distribution
      * Smaller values speed up computation
    - max_iter (int): Maximum number of PAM iterations.
      See pam function for details.
    - random_state (int): Random seed for reproducibility
    
    Args:
        X: Input data of shape (n_samples, n_features)
        n_clusters: Number of clusters to form
        n_samples: Number of samples to draw
        sample_size: Size of each sample
        max_iter: Maximum number of PAM iterations
        random_state: Random seed for reproducibility
        
    Returns:
        Tuple of (labels, medoids, cost) where:
        - labels: Cluster labels for each sample
        - medoids: Final medoids
        - cost: Final clustering cost
    """
    n_samples_total = X.shape[0]
    
    # Ensure sample size is not larger than dataset
    sample_size = min(sample_size, n_samples_total)
    
    # Try multiple samples
    best_cost = float('inf')
    best_medoids = None
    
    for _ in range(n_samples):
        # Draw random sample
        np.random.seed(random_state)
        sample_indices = np.random.choice(n_samples_total, sample_size, replace=False)
        X_sample = X[sample_indices]
        
        # Apply PAM to sample
        sample_medoids, sample_cost = pam(X_sample, n_clusters, max_iter, random_state)
        
        # Convert sample medoids to original indices
        medoids = sample_indices[sample_medoids]
        
        # Compute cost on full dataset
        cost = compute_cost(X, X[medoids])
        
        if cost < best_cost:
            best_cost = cost
            best_medoids = medoids
    
    # Assign labels based on best medoids
    distances = cdist(X, X[best_medoids])
    labels = np.argmin(distances, axis=1)
    
    return labels, best_medoids, best_cost

## 5. Evaluation Functions
def evaluate_clustering(
    X: np.ndarray,
    labels: np.ndarray
) -> Dict[str, float]:
    """
    Evaluate clustering results using various metrics.
    
    Args:
        X: Input data
        labels: Cluster labels
        
    Returns:
        Dictionary containing evaluation metrics:
        - silhouette_score: Measures cluster separation and cohesion
        - calinski_harabasz_score: Ratio of between-cluster to within-cluster variance
    """
    metrics = {
        'silhouette_score': silhouette_score(X, labels),
        'calinski_harabasz_score': calinski_harabasz_score(X, labels)
    }
    return metrics

## 6. Visualization Functions
def plot_clusters(
    X: np.ndarray,
    labels: np.ndarray,
    medoids: Optional[np.ndarray] = None,
    title: Optional[str] = None
) -> None:
    """
    Plot clustering results using seaborn.
    
    Args:
        X: Input data
        labels: Cluster labels
        medoids: Medoid indices (optional)
        title: Plot title
    """
    os.makedirs('algorithms/other-algorithms/clara', exist_ok=True)
    plt.figure(figsize=(10, 6))
    sns.scatterplot(x=X[:, 0], y=X[:, 1], hue=labels, palette='viridis', legend='full')
    if medoids is not None:
        plt.scatter(X[medoids, 0], X[medoids, 1], c='red', marker='X', s=200, label='Medoids')
    plt.title(title or 'CLARA Clustering Results')
    plt.xlabel('Feature 1')
    plt.ylabel('Feature 2')
    filename = f'algorithms/other-algorithms/clara/clara-{(title or "results").lower().replace(" ", "_")}.png'
    plt.savefig(filename)
    plt.close() 