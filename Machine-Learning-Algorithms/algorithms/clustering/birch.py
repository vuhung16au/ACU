#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
# BIRCH (Balanced Iterative Reducing and Clustering using Hierarchies) Implementation

This notebook demonstrates the implementation of BIRCH clustering algorithm using scikit-learn.
BIRCH is particularly useful for large datasets as it builds a CF (Clustering Feature) tree
that summarizes the data in a compact way.

## Key Features:
- Hierarchical clustering
- Memory efficient
- Works well with large datasets
- Handles noise points
"""

# Import required libraries
import numpy as np
import seaborn as sns
import matplotlib.pyplot as plt
from sklearn.cluster import Birch
from sklearn.datasets import make_blobs
from sklearn.preprocessing import StandardScaler
from sklearn.metrics import silhouette_score
import os

# Set random seed for reproducibility
np.random.seed(2220)

## Data Visualization Functions

def plot_clusters(X, labels, title="BIRCH Clustering Results"):
    """
    Visualize clustering results using seaborn
    
    Parameters:
    -----------
    X : array-like
        Input data points
    labels : array-like
        Cluster labels for each point
    title : str
        Title for the plot
    """
    os.makedirs('algorithms/clustering/birch', exist_ok=True)
    
    # Create figure with seaborn style
    plt.figure(figsize=(10, 8))
    sns.set_style("whitegrid")
    
    # Create scatter plot using seaborn
    palette = sns.color_palette("bright", len(np.unique(labels)))
    
    # Plot data points
    for i, label in enumerate(np.unique(labels)):
        if label == -1:  # Noise points
            sns.scatterplot(x=X[labels == label, 0], y=X[labels == label, 1],
                          label='Noise', color='gray', alpha=0.3, s=20)
        else:
            sns.scatterplot(x=X[labels == label, 0], y=X[labels == label, 1],
                          label=f"Cluster {label}", alpha=0.7, color=palette[i], s=20)
    
    plt.xlabel('Feature 1')
    plt.ylabel('Feature 2')
    plt.title(title)
    plt.legend()
    
    # Save plot
    filename = 'algorithms/clustering/birch/birch-clusters.png'
    plt.savefig(filename, dpi=300, bbox_inches='tight')
    plt.close()

def plot_subclusters(model, X, title="BIRCH Subclusters"):
    """
    Visualize BIRCH subclusters using seaborn
    
    Parameters:
    -----------
    model : Birch
        Fitted BIRCH model
    X : array-like
        Input data points
    title : str
        Title for the plot
    """
    os.makedirs('algorithms/clustering/birch', exist_ok=True)
    
    # Create figure with seaborn style
    plt.figure(figsize=(10, 8))
    sns.set_style("whitegrid")
    
    # Plot subclusters
    for subcluster in model.subcluster_centers_:
        plt.scatter(subcluster[0], subcluster[1], c='red', marker='x', s=100)
    
    # Plot data points using seaborn
    sns.scatterplot(x=X[:, 0], y=X[:, 1], color='blue', alpha=0.1, s=20)
    
    plt.xlabel('Feature 1')
    plt.ylabel('Feature 2')
    plt.title(title)
    
    # Save plot
    filename = 'algorithms/clustering/birch/birch-subclusters.png'
    plt.savefig(filename, dpi=300, bbox_inches='tight')
    plt.close()

def evaluate_clustering(X, labels):
    """
    Evaluate clustering quality using silhouette score
    
    Parameters:
    -----------
    X : array-like
        Input data points
    labels : array-like
        Cluster labels for each point
    
    Returns:
    --------
    float or None
        Silhouette score if more than one cluster exists, None otherwise
    """
    if len(np.unique(labels)) > 1:  # Only calculate if there are at least 2 clusters
        score = silhouette_score(X, labels)
        print(f"Silhouette Score: {score:.3f}")
        return score
    return None

## Data Generation and Preprocessing

# Generate sample data
X, y = make_blobs(n_samples=1000, centers=4, cluster_std=0.60, random_state=42)

# Scale the data
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)

## BIRCH Clustering Implementation

# Initialize and train BIRCH model
"""
BIRCH Hyperparameters:
- n_clusters: Number of clusters to form (4 in this case)
- threshold: The radius of the subcluster (0.5 in this case)
- branching_factor: Maximum number of CF subclusters in each node (50 in this case)
"""
print("\nTraining BIRCH Clustering...")
birch = Birch(
    n_clusters=4,          # Number of clusters to form
    threshold=0.5,         # The radius of the subcluster
    branching_factor=50    # Maximum number of CF subclusters in each node
)
labels = birch.fit_predict(X_scaled)

## Visualization and Evaluation

# Plot clustering results
plot_clusters(X_scaled, labels, title="BIRCH Clustering Results")
plot_subclusters(birch, X_scaled, title="BIRCH Subclusters")

# Evaluate clustering quality
evaluate_clustering(X_scaled, labels)