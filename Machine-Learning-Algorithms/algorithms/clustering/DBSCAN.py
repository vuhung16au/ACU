#!/usr/bin/env python3
# -*- coding: utf-8 -*-

## DBSCAN (Density-Based Spatial Clustering of Applications with Noise) Implementation
## This implementation provides both custom and scikit-learn versions of DBSCAN clustering
## with visualization and evaluation capabilities.

## Import required libraries
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.cluster import DBSCAN as SKLearnDBSCAN
from sklearn.metrics import silhouette_score, adjusted_rand_score
from sklearn.datasets import make_moons, make_blobs
from sklearn.preprocessing import StandardScaler
from sklearn.neighbors import NearestNeighbors
import os

## Set random seed for reproducibility
np.random.seed(2220)

## Helper Functions

def region_query(X, idx, eps, metric='euclidean'):
    """
    Find all points within eps distance of point idx
    
    Parameters:
    -----------
    X : array-like
        Input data matrix
    idx : int
        Index of the point to find neighbors for
    eps : float
        Maximum distance between points to be considered neighbors
        - Higher values create larger clusters
        - Lower values create smaller, more distinct clusters
        - Typical range: 0.1 to 1.0
    metric : str, default='euclidean'
        Distance metric to use
    """
    if metric == 'euclidean':
        dists = np.linalg.norm(X - X[idx], axis=1)
    else:
        raise ValueError('Only euclidean metric is supported in custom implementation.')
    return np.where(dists <= eps)[0]

def dbscan_clustering(X, eps=0.5, min_samples=5, metric='euclidean'):
    """
    Perform DBSCAN clustering on input data
    
    Parameters:
    -----------
    X : array-like
        Input data matrix
    eps : float, default=0.5
        Maximum distance between points to be considered neighbors
        - Higher values create larger clusters
        - Lower values create smaller, more distinct clusters
        - Typical range: 0.1 to 1.0
    min_samples : int, default=5
        Minimum number of points required to form a cluster
        - Higher values require more points to form clusters
        - Lower values allow smaller clusters to form
        - Typical range: 3 to 10
    metric : str, default='euclidean'
        Distance metric to use
    """
    X = np.array(X)
    n = X.shape[0]
    labels = np.full(n, -1, dtype=int)  # -1 means noise
    cluster_id = 0
    visited = np.zeros(n, dtype=bool)

    for i in range(n):
        if visited[i]:
            continue
        visited[i] = True
        neighbors = region_query(X, i, eps, metric)
        if len(neighbors) < min_samples:
            labels[i] = -1  # noise
        else:
            labels[i] = cluster_id
            seeds = set(neighbors)
            seeds.discard(i)
            while seeds:
                j = seeds.pop()
                if not visited[j]:
                    visited[j] = True
                    j_neighbors = region_query(X, j, eps, metric)
                    if len(j_neighbors) >= min_samples:
                        seeds.update(j_neighbors)
                if labels[j] == -1:
                    labels[j] = cluster_id
                elif labels[j] == -1 or labels[j] == -2:
                    labels[j] = cluster_id
            cluster_id += 1
    
    return labels

def generate_data(n_samples=500, noise=0.05, kind='moons'):
    """
    Generate synthetic data for clustering
    
    Parameters:
    -----------
    n_samples : int, default=500
        Number of samples to generate
    noise : float, default=0.05
        Standard deviation of Gaussian noise
        - Higher values create more scattered points
        - Lower values create more compact clusters
        - Typical range: 0.01 to 0.2
    kind : str, default='moons'
        Type of data to generate
        - 'moons': Two interleaving half circles
        - 'blobs': Gaussian blobs
    """
    if kind == 'moons':
        X, y = make_moons(n_samples=n_samples, noise=noise, random_state=2220)
    elif kind == 'blobs':
        X, y = make_blobs(n_samples=n_samples, centers=3, cluster_std=0.6, random_state=2220)
    else:
        raise ValueError('Unknown data kind.')
    scaler = StandardScaler()
    X_scaled = scaler.fit_transform(X)
    return X_scaled, y

def plot_clusters(X, labels, title="DBSCAN Clustering Results"):
    """
    Plot clusters using seaborn
    
    Parameters:
    -----------
    X : array-like
        Input data matrix
    labels : array-like
        Cluster labels
    title : str
        Plot title
    """
    os.makedirs('algorithms/clustering/dbscan', exist_ok=True)
    
    # Create DataFrame for seaborn
    df = pd.DataFrame({
        'Feature 1': X[:, 0],
        'Feature 2': X[:, 1],
        'Cluster': labels
    })
    
    # Create plot
    plt.figure(figsize=(10, 8))
    sns.scatterplot(data=df, x='Feature 1', y='Feature 2', hue='Cluster', palette='bright')
    plt.title(title)
    plt.legend()
    
    # Save plot
    filename = f'algorithms/clustering/dbscan/dbscan-{title.lower().replace(" ", "_")}.png'
    plt.savefig(filename)
    plt.close()

def plot_k_distance(X, k=5):
    """
    Plot k-distance graph for eps selection
    
    Parameters:
    -----------
    X : array-like
        Input data matrix
    k : int, default=5
        Number of nearest neighbors
        - Higher values consider more neighbors
        - Lower values consider fewer neighbors
        - Typical range: 3 to 10
    """
    os.makedirs('algorithms/clustering/dbscan', exist_ok=True)
    
    # Compute k-distances
    neigh = NearestNeighbors(n_neighbors=k)
    neigh.fit(X)
    dists, _ = neigh.kneighbors(X)
    k_distances = np.sort(dists[:, k-1])
    
    # Create plot
    plt.figure(figsize=(10, 6))
    sns.lineplot(x=range(len(k_distances)), y=k_distances)
    plt.xlabel('Points sorted by distance')
    plt.ylabel(f'{k}-th Nearest Neighbor Distance')
    plt.title('k-distance Graph for DBSCAN eps Selection')
    
    # Save plot
    filename = 'algorithms/clustering/dbscan/dbscan-k_distance_graph.png'
    plt.savefig(filename)
    plt.close()

def plot_silhouette_scores(X, eps_range, min_samples_range):
    """
    Plot silhouette scores for different parameters
    
    Parameters:
    -----------
    X : array-like
        Input data matrix
    eps_range : list
        Range of eps values to try
        - Typical range: [0.1, 0.2, 0.3, 0.4, 0.5]
    min_samples_range : list
        Range of min_samples values to try
        - Typical range: [3, 5, 7, 9, 11]
    """
    os.makedirs('algorithms/clustering/dbscan', exist_ok=True)
    
    # Compute silhouette scores
    scores = np.zeros((len(eps_range), len(min_samples_range)))
    for i, eps in enumerate(eps_range):
        for j, min_samples in enumerate(min_samples_range):
            dbscan = SKLearnDBSCAN(eps=eps, min_samples=min_samples)
            labels = dbscan.fit_predict(X)
            if len(np.unique(labels)) > 1:  # More than one cluster
                scores[i, j] = silhouette_score(X, labels)
            else:
                scores[i, j] = -1  # Invalid clustering
    
    # Create plot
    plt.figure(figsize=(12, 8))
    sns.heatmap(scores, xticklabels=min_samples_range, yticklabels=eps_range,
                cmap='viridis', annot=True, fmt='.2f')
    plt.xlabel('min_samples')
    plt.ylabel('eps')
    plt.title('Silhouette Scores for Different DBSCAN Parameters')
    
    # Save plot
    filename = 'algorithms/clustering/dbscan/dbscan-silhouette_scores.png'
    plt.savefig(filename)
    plt.close()

## Generate and process sample data
X, y_true = generate_data(n_samples=500, noise=0.07, kind='moons')

## Model parameter selection
plot_k_distance(X, k=5)
eps_range = [0.1, 0.2, 0.3, 0.4, 0.5]
min_samples_range = [3, 5, 7, 9, 11]
plot_silhouette_scores(X, eps_range, min_samples_range)

## Custom DBSCAN clustering
print("\nTraining Custom DBSCAN...")
labels = dbscan_clustering(X, eps=0.3, min_samples=5)
n_clusters = len(set(labels)) - (1 if -1 in labels else 0)
print(f"Custom DBSCAN found {n_clusters} clusters.")
print(f"Silhouette Score: {silhouette_score(X, labels):.3f}")
print(f"Adjusted Rand Index: {adjusted_rand_score(y_true, labels):.3f}")
plot_clusters(X, labels, title="Custom DBSCAN Clustering")

## scikit-learn DBSCAN
print("\nTraining scikit-learn DBSCAN...")
skl_dbscan = SKLearnDBSCAN(eps=0.3, min_samples=5)
skl_labels = skl_dbscan.fit_predict(X)
n_clusters = len(set(skl_labels)) - (1 if -1 in skl_labels else 0)
print(f"scikit-learn DBSCAN found {n_clusters} clusters.")
print(f"Silhouette Score: {silhouette_score(X, skl_labels):.3f}")
print(f"Adjusted Rand Index: {adjusted_rand_score(y_true, skl_labels):.3f}")
plot_clusters(X, skl_labels, title="scikit-learn DBSCAN Clustering")