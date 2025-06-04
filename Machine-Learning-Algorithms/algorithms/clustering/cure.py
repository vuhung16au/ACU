#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
# CURE (Clustering Using Representatives) Implementation

This notebook implements the CURE clustering algorithm, which is designed to handle non-spherical clusters.
The algorithm uses representative points and shrinking to better capture cluster shapes.

## Key Features:
- Handles non-spherical clusters
- Uses representative points to capture cluster shapes
- Includes visualization and evaluation tools
"""

# Import required libraries
import numpy as np
import seaborn as sns
import matplotlib.pyplot as plt
from sklearn.datasets import make_moons, make_blobs
from sklearn.preprocessing import StandardScaler
from sklearn.metrics import silhouette_score
from scipy.spatial.distance import pdist, squareform
import os

# Set random seed for reproducibility
np.random.seed(2220)

# Create directory for saving plots
os.makedirs('algorithms/clustering/cure', exist_ok=True)

def select_representatives(cluster, n_representatives):
    """
    Select representative points for a cluster.
    
    Parameters:
    -----------
    cluster : array-like
        The cluster points
    n_representatives : int
        Number of representative points to select
        
    Returns:
    --------
    array-like
        Selected representative points
    """
    if len(cluster) <= n_representatives:
        return cluster
    
    # Select points farthest from each other
    representatives = [cluster[0]]
    for _ in range(n_representatives - 1):
        max_dist = -1
        next_point = None
        for point in cluster:
            min_dist = min(np.linalg.norm(point - rep) for rep in representatives)
            if min_dist > max_dist:
                max_dist = min_dist
                next_point = point
        representatives.append(next_point)
    return np.array(representatives)

def shrink_representatives(representatives, center, shrinking_factor):
    """
    Shrink representative points towards cluster center.
    
    Parameters:
    -----------
    representatives : array-like
        Current representative points
    center : array-like
        Cluster center
    shrinking_factor : float
        Factor to control shrinking (0 to 1)
        
    Returns:
    --------
    array-like
        Shrunk representative points
    """
    return center + shrinking_factor * (representatives - center)

def merge_clusters(clusters):
    """
    Find the closest pair of clusters to merge.
    
    Parameters:
    -----------
    clusters : list
        List of cluster representatives
        
    Returns:
    --------
    tuple
        Indices of clusters to merge
    """
    min_dist = float('inf')
    merge_indices = (0, 0)
    
    for i in range(len(clusters)):
        for j in range(i + 1, len(clusters)):
            dist = min(np.linalg.norm(r1 - r2) 
                      for r1 in clusters[i] 
                      for r2 in clusters[j])
            if dist < min_dist:
                min_dist = dist
                merge_indices = (i, j)
    
    return merge_indices

def cure_clustering(X, n_clusters=2, n_representatives=5, shrinking_factor=0.3, sample_size=100):
    """
    Perform CURE clustering on the input data.
    
    Parameters:
    -----------
    X : array-like
        Input data
    n_clusters : int
        Number of clusters to form
    n_representatives : int
        Number of representative points per cluster
    shrinking_factor : float
        Factor to control shrinking of representative points (0 to 1)
    sample_size : int
        Size of random sample if input data is large
        
    Returns:
    --------
    tuple
        (cluster labels, representative points)
    """
    n_samples = X.shape[0]
    
    # Random sampling
    if n_samples > sample_size:
        indices = np.random.choice(n_samples, sample_size, replace=False)
        sample = X[indices]
    else:
        sample = X
    
    # Initial clusters (one point per cluster)
    clusters = [[point] for point in sample]
    representatives = [select_representatives(cluster, n_representatives) for cluster in clusters]
    
    # Hierarchical clustering
    while len(clusters) > n_clusters:
        i, j = merge_clusters(representatives)
        
        # Merge clusters
        clusters[i].extend(clusters[j])
        del clusters[j]
        
        # Update representatives
        center = np.mean(clusters[i], axis=0)
        representatives[i] = select_representatives(clusters[i], n_representatives)
        representatives[i] = shrink_representatives(representatives[i], center, shrinking_factor)
        del representatives[j]
    
    # Assign labels to all points
    labels = np.zeros(n_samples, dtype=int)
    for i, cluster in enumerate(clusters):
        for point in cluster:
            idx = np.where((X == point).all(axis=1))[0]
            if len(idx) > 0:
                labels[idx] = i
    
    return labels, representatives

def plot_clusters(X, labels, representatives, title="CURE Clustering Results"):
    """
    Plot clustering results using seaborn.
    
    Parameters:
    -----------
    X : array-like
        Input data
    labels : array-like
        Cluster labels
    representatives : list
        Representative points for each cluster
    title : str
        Plot title
    """
    plt.figure(figsize=(10, 8))
    palette = sns.color_palette("bright", len(np.unique(labels)))
    
    # Plot data points
    for i, label in enumerate(np.unique(labels)):
        plt.scatter(X[labels == label, 0], X[labels == label, 1],
                   label=f"Cluster {label}", alpha=0.7, color=palette[i], s=20)
    
    # Plot representatives
    for i, reps in enumerate(representatives):
        plt.scatter(reps[:, 0], reps[:, 1], c='black', marker='x', s=100,
                   label=f'Representatives {i}' if i == 0 else "")
    
    plt.xlabel('Feature 1')
    plt.ylabel('Feature 2')
    plt.title(title)
    plt.legend()
    
    # Save plot
    filename = 'algorithms/clustering/cure/cure-clusters.png'
    plt.savefig(filename)
    plt.close()

def evaluate_clustering(X, labels):
    """
    Evaluate clustering using silhouette score.
    
    Parameters:
    -----------
    X : array-like
        Input data
    labels : array-like
        Cluster labels
        
    Returns:
    --------
    float or None
        Silhouette score if more than one cluster, None otherwise
    """
    if len(np.unique(labels)) > 1:
        score = silhouette_score(X, labels)
        print(f"Silhouette Score: {score:.3f}")
        return score
    return None

# Generate sample data with non-spherical clusters
X, y = make_moons(n_samples=300, noise=0.1, random_state=42)

# Scale the data
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)

# CURE clustering parameters
n_clusters = 2  # Number of clusters to form
n_representatives = 5  # Number of representative points per cluster
shrinking_factor = 0.3  # Factor to control shrinking of representative points
sample_size = 100  # Size of random sample if input data is large

# Perform CURE clustering
print("\nTraining CURE Clustering...")
labels, representatives = cure_clustering(
    X_scaled,
    n_clusters=n_clusters,
    n_representatives=n_representatives,
    shrinking_factor=shrinking_factor,
    sample_size=sample_size
)

# Plot results
plot_clusters(X_scaled, labels, representatives, title="CURE Clustering Results")

# Evaluate clustering
evaluate_clustering(X_scaled, labels) 