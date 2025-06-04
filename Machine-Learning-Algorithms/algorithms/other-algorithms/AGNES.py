#!/usr/bin/env python3
# -*- coding: utf-8 -*-

## AGNES (Agglomerative Nesting) Clustering Implementation
## This notebook demonstrates the implementation of the AGNES hierarchical clustering algorithm.
## AGNES is a bottom-up hierarchical clustering algorithm that starts with individual points
## and merges the closest clusters iteratively until the desired number of clusters is reached.

## Table of Contents
# 1. Import Required Libraries
# 2. Data Generation and Preprocessing
# 3. AGNES Implementation
# 4. Model Training and Evaluation
# 5. Visualization
# 6. Results and Analysis

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from scipy.spatial.distance import pdist, squareform
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

## 4. Core AGNES Functions
def compute_linkage(
    distances: np.ndarray,
    cluster_i: List[int],
    cluster_j: List[int],
    linkage_method: str = 'complete'
) -> float:
    """
    Compute linkage between clusters i and j.
    
    Hyperparameters:
    - linkage_method (str): Linkage criterion to use:
      * 'complete': Maximum distance between clusters (conservative)
      * 'single': Minimum distance between clusters (liberal)
      * 'average': Average distance between clusters (balanced)
      * 'ward': Minimizes variance within clusters (optimizes cluster shape)
    
    Args:
        distances: Distance matrix
        cluster_i: Indices of first cluster
        cluster_j: Indices of second cluster
        linkage_method: Linkage criterion to use
        
    Returns:
        Linkage distance between clusters
    """
    if linkage_method == 'complete':
        return np.max(distances[np.ix_(cluster_i, cluster_j)])
    elif linkage_method == 'single':
        return np.min(distances[np.ix_(cluster_i, cluster_j)])
    elif linkage_method == 'average':
        return np.mean(distances[np.ix_(cluster_i, cluster_j)])
    elif linkage_method == 'ward':
        n_i = len(cluster_i)
        n_j = len(cluster_j)
        return np.sqrt((n_i * n_j) / (n_i + n_j)) * np.mean(distances[np.ix_(cluster_i, cluster_j)])
    else:
        raise ValueError(f"Unknown linkage method: {linkage_method}")

def agnes_clustering(
    X: np.ndarray,
    n_clusters: Optional[int] = None,
    linkage_method: str = 'complete',
    metric: str = 'euclidean'
) -> Tuple[np.ndarray, np.ndarray, np.ndarray]:
    """
    Perform AGNES hierarchical clustering.
    
    Hyperparameters:
    - n_clusters (int): Number of clusters to form.
      If None, builds full hierarchy.
    - linkage_method (str): Linkage criterion to use.
      See compute_linkage for details.
    - metric (str): Distance metric to use:
      * 'euclidean': Standard Euclidean distance
      * 'manhattan': Manhattan distance
      * 'cosine': Cosine similarity
    
    Args:
        X: Input data of shape (n_samples, n_features)
        n_clusters: Number of clusters to form
        linkage_method: Linkage criterion to use
        metric: Distance metric to use
        
    Returns:
        Tuple of (labels, children, distances) where:
        - labels: Cluster labels for each sample
        - children: Hierarchical clustering tree
        - distances: Distances between merged clusters
    """
    n_samples = X.shape[0]
    # Compute initial distance matrix (original, never shrinks)
    orig_distances = squareform(pdist(X, metric=metric))
    # Initialize clusters
    clusters = [[i] for i in range(n_samples)]
    children = []
    merge_distances = []
    # Main clustering loop
    while len(clusters) > 1:
        min_dist = float('inf')
        min_i = min_j = -1
        for i in range(len(clusters)):
            for j in range(i+1, len(clusters)):
                dist = compute_linkage(orig_distances, clusters[i], clusters[j], linkage_method)
                if dist < min_dist:
                    min_dist = dist
                    min_i, min_j = i, j
        # Merge clusters
        children.append([min_i, min_j])
        merge_distances.append(min_dist)
        clusters[min_i].extend(clusters[min_j])
        clusters.pop(min_j)
        # Stop if desired number of clusters reached
        if n_clusters is not None and len(clusters) <= n_clusters:
            break
    children = np.array(children)
    merge_distances = np.array(merge_distances)
    labels = np.zeros(n_samples, dtype=int)
    if n_clusters is not None:
        for i, cluster in enumerate(clusters):
            labels[cluster] = i
    return labels, children, merge_distances

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
    title: str
) -> None:
    """
    Plot clustering results using seaborn.
    
    Args:
        X: Input data
        labels: Cluster labels
        title: Plot title
    """
    # Create directory if it doesn't exist
    import os
    os.makedirs('algorithms/other-algorithms/agnes', exist_ok=True)
    
    plt.figure(figsize=(10, 6))
    sns.scatterplot(
        x=X[:, 0],
        y=X[:, 1],
        hue=labels,
        palette='viridis',
        legend='full'
    )
    plt.title(title)
    plt.xlabel('Feature 1')
    plt.ylabel('Feature 2')
    
    # Save the plot to a PNG file
    filename = f'algorithms/other-algorithms/agnes/agnes-{title.lower().replace(" ", "_")}.png'
    plt.savefig(filename)
    plt.close()

def plot_dendrogram(
    X: np.ndarray,
    title: str,
    method: str = 'complete',
    metric: str = 'euclidean'
) -> None:
    """
    Plot hierarchical clustering dendrogram using scipy's linkage for visualization.
    Args:
        X: Input data
        title: Plot title
        method: Linkage method (default: 'complete')
        metric: Distance metric (default: 'euclidean')
    """
    import os
    os.makedirs('algorithms/other-algorithms/agnes', exist_ok=True)
    import scipy.cluster.hierarchy as sch
    import matplotlib.pyplot as plt
    # Compute linkage matrix using scipy for visualization
    Z = sch.linkage(X, method=method, metric=metric)
    plt.figure(figsize=(12, 6))
    sch.dendrogram(Z)
    plt.title(title)
    filename = f'algorithms/other-algorithms/agnes/agnes-{title.lower().replace(" ", "_")}_dendrogram.png'
    plt.savefig(filename)
    plt.close()

## 7. Example Usage
# Generate sample data
X, y_true = generate_data(
    n_samples=300,
    n_features=2,
    centers=3,
    cluster_std=1.0
)

# Perform clustering
labels, children, distances = agnes_clustering(
    X,
    n_clusters=3,
    linkage_method='complete',
    metric='euclidean'
)

# Evaluate results
metrics = evaluate_clustering(X, labels)
print("\nClustering Metrics:")
for metric_name, value in metrics.items():
    print(f"{metric_name}: {value:.4f}")

# Plot results
plot_clusters(X, labels, "AGNES Clustering Results")
plot_dendrogram(X, "AGNES Dendrogram", method='complete', metric='euclidean') 