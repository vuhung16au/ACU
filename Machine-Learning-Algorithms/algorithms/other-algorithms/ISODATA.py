# ISODATA Clustering Implementation
# This notebook demonstrates the implementation of the ISODATA (Iterative Self-Organizing Data Analysis Technique) clustering algorithm.

## Table of Contents
# 1. Import Required Libraries
# 2. Data Generation and Preprocessing
# 3. ISODATA Implementation
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

# Set random seed for reproducibility
np.random.seed(2220)

## 2. Data Generation and Preprocessing
def generate_data(n_samples=300, n_features=2, centers=3, cluster_std=1.0):
    """
    Generate sample data for clustering.
    
    Parameters:
    -----------
    n_samples : int, default=300
        Number of samples to generate
    n_features : int, default=2
        Number of features
    centers : int, default=3
        Number of clusters
    cluster_std : float, default=1.0
        Standard deviation of clusters
        
    Returns:
    --------
    tuple
        X : numpy.ndarray
            Features array of shape (n_samples, n_features)
        y : numpy.ndarray
            True cluster labels
    """
    X, y = make_blobs(
        n_samples=n_samples,
        n_features=n_features,
        centers=centers,
        cluster_std=cluster_std,
        random_state=2220
    )
    return X, y

## 3. ISODATA Implementation
def initialize_centroids(X, k, random_state=None):
    """
    Initialize centroids using k-means++ strategy.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Input data
    k : int
        Number of clusters
    random_state : int, default=None
        Random seed for reproducibility
        
    Returns:
    --------
    numpy.ndarray
        Initial centroids
    """
    n_samples = X.shape[0]
    np.random.seed(random_state)
    centroids = [X[np.random.randint(n_samples)]]
    
    for _ in range(1, k):
        distances = np.min(cdist(X, np.array(centroids)), axis=1)
        probs = distances / distances.sum()
        centroids.append(X[np.random.choice(n_samples, p=probs)])
        
    return np.array(centroids)

def assign_clusters(X, centroids):
    """
    Assign points to nearest centroids.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Input data
    centroids : numpy.ndarray
        Current centroids
        
    Returns:
    --------
    numpy.ndarray
        Cluster labels
    """
    distances = cdist(X, centroids)
    return np.argmin(distances, axis=1)

def update_centroids(X, labels):
    """
    Update centroids based on current assignments.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Input data
    labels : numpy.ndarray
        Current cluster labels
        
    Returns:
    --------
    numpy.ndarray
        Updated centroids
    """
    centroids = []
    for i in range(len(np.unique(labels))):
        if np.sum(labels == i) > 0:
            centroids.append(np.mean(X[labels == i], axis=0))
    return np.array(centroids)

def compute_cluster_stats(X, labels, centroids):
    """
    Compute cluster statistics.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Input data
    labels : numpy.ndarray
        Current cluster labels
    centroids : numpy.ndarray
        Current centroids
        
    Returns:
    --------
    tuple
        cluster_sizes : numpy.ndarray
            Number of points in each cluster
        cluster_std : numpy.ndarray
            Standard deviation of each cluster
    """
    n_clusters = len(centroids)
    cluster_sizes = np.zeros(n_clusters)
    cluster_std = np.zeros((n_clusters, X.shape[1]))
    
    for i in range(n_clusters):
        mask = labels == i
        cluster_sizes[i] = np.sum(mask)
        if cluster_sizes[i] > 0:
            cluster_std[i] = np.std(X[mask], axis=0)
            
    return cluster_sizes, cluster_std

def merge_clusters(centroids, cluster_sizes, theta_c, L):
    """
    Merge close clusters.
    
    Parameters:
    -----------
    centroids : numpy.ndarray
        Current centroids
    cluster_sizes : numpy.ndarray
        Number of points in each cluster
    theta_c : float
        Distance threshold for merging
    L : int
        Maximum number of pairs to merge
        
    Returns:
    --------
    numpy.ndarray
        Updated centroids after merging
    """
    n_clusters = len(centroids)
    distances = cdist(centroids, centroids)
    np.fill_diagonal(distances, np.inf)
    
    # Find pairs of clusters to merge
    merge_pairs = []
    for i in range(n_clusters):
        for j in range(i+1, n_clusters):
            if distances[i, j] < theta_c:
                merge_pairs.append((i, j, distances[i, j]))
                
    # Sort pairs by distance
    merge_pairs.sort(key=lambda x: x[2])
    
    # Merge clusters
    new_centroids = []
    merged = set()
    for i, j, _ in merge_pairs[:L]:
        if i not in merged and j not in merged:
            # Merge centroids weighted by cluster size
            new_centroid = (centroids[i] * cluster_sizes[i] + centroids[j] * cluster_sizes[j]) / (cluster_sizes[i] + cluster_sizes[j])
            new_centroids.append(new_centroid)
            merged.add(i)
            merged.add(j)
            
    # Add unmerged centroids
    for i in range(n_clusters):
        if i not in merged:
            new_centroids.append(centroids[i])
            
    return np.array(new_centroids)

def split_clusters(centroids, cluster_std, theta_s):
    """
    Split large clusters.
    
    Parameters:
    -----------
    centroids : numpy.ndarray
        Current centroids
    cluster_std : numpy.ndarray
        Standard deviation of each cluster
    theta_s : float
        Standard deviation threshold
        
    Returns:
    --------
    numpy.ndarray
        Updated centroids after splitting
    """
    n_clusters = len(centroids)
    new_centroids = []
    
    for i in range(n_clusters):
        if np.any(cluster_std[i] > theta_s):
            # Find dimension with maximum standard deviation
            max_std_dim = np.argmax(cluster_std[i])
            
            # Split centroid along this dimension
            centroid1 = centroids[i].copy()
            centroid2 = centroids[i].copy()
            centroid1[max_std_dim] += cluster_std[i, max_std_dim]
            centroid2[max_std_dim] -= cluster_std[i, max_std_dim]
            
            new_centroids.extend([centroid1, centroid2])
        else:
            new_centroids.append(centroids[i])
            
    return np.array(new_centroids)

def remove_small_clusters(X, labels, centroids, cluster_sizes, theta_n):
    """
    Remove clusters with too few samples.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Input data
    labels : numpy.ndarray
        Current cluster labels
    centroids : numpy.ndarray
        Current centroids
    cluster_sizes : numpy.ndarray
        Number of points in each cluster
    theta_n : int
        Minimum number of samples in a cluster
        
    Returns:
    --------
    tuple
        centroids : numpy.ndarray
            Updated centroids
        labels : numpy.ndarray
            Updated cluster labels
    """
    valid_clusters = cluster_sizes >= theta_n
    if np.sum(valid_clusters) < len(valid_clusters):
        # Reassign points from removed clusters
        new_labels = labels.copy()
        for i in np.where(~valid_clusters)[0]:
            mask = labels == i
            if np.sum(mask) > 0:
                distances = cdist(X[mask], centroids[valid_clusters])
                new_labels[mask] = np.argmin(distances, axis=1)
        return centroids[valid_clusters], new_labels
    return centroids, labels

def isodata_clustering(X, k=3, theta_n=2, theta_s=1.0, theta_c=4.0, L=3, I=100, random_state=None):
    """
    Perform ISODATA clustering.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Input data of shape (n_samples, n_features)
    k : int, default=3
        Desired number of clusters
        - Determines the target number of clusters
        - May not be achieved exactly due to merging/splitting
    theta_n : int, default=2
        Minimum number of samples in a cluster
        - Clusters with fewer samples will be removed
        - Points will be reassigned to nearest valid cluster
    theta_s : float, default=1.0
        Standard deviation threshold
        - Clusters with std > theta_s will be split
        - Controls cluster compactness
    theta_c : float, default=4.0
        Distance threshold for merging
        - Clusters closer than theta_c will be merged
        - Controls cluster separation
    L : int, default=3
        Maximum number of pairs to merge
        - Limits number of merges per iteration
        - Prevents excessive merging
    I : int, default=100
        Maximum number of iterations
        - More iterations can lead to better convergence
        - May cause overfitting if too high
    random_state : int, default=None
        Random seed for reproducibility
        
    Returns:
    --------
    tuple
        labels : numpy.ndarray
            Cluster labels for each sample
        centroids : numpy.ndarray
            Final centroids
        n_clusters : int
            Final number of clusters
    """
    # Initialize centroids
    centroids = initialize_centroids(X, k, random_state)
    
    for iteration in range(I):
        # Assign points to clusters
        labels = assign_clusters(X, centroids)
        
        # Update centroids
        centroids = update_centroids(X, labels)
        
        # Compute cluster statistics
        cluster_sizes, cluster_std = compute_cluster_stats(X, labels, centroids)
        
        # Remove small clusters
        centroids, labels = remove_small_clusters(X, labels, centroids, cluster_sizes, theta_n)
        
        # Merge close clusters
        centroids = merge_clusters(centroids, cluster_sizes, theta_c, L)
        
        # Split large clusters
        centroids = split_clusters(centroids, cluster_std, theta_s)
        
        # Check convergence
        if len(centroids) == k:
            break
            
    n_clusters = len(centroids)
    return labels, centroids, n_clusters

## 4. Model Training and Evaluation
def evaluate_clustering(X, labels):
    """
    Evaluate clustering results using various metrics.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Input data
    labels : numpy.ndarray
        Cluster labels
        
    Returns:
    --------
    dict
        Dictionary containing evaluation metrics
    """
    metrics = {
        'silhouette_score': silhouette_score(X, labels),
        'calinski_harabasz_score': calinski_harabasz_score(X, labels)
    }
    return metrics

## 5. Visualization
def plot_clusters(X, labels, centroids=None, title=None):
    """
    Plot clustering results using seaborn.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Input data
    labels : numpy.ndarray
        Cluster labels
    centroids : numpy.ndarray, default=None
        Cluster centroids
    title : str, default=None
        Plot title
    """
    os.makedirs('algorithms/other-algorithms/isodata', exist_ok=True)
    plt.figure(figsize=(10, 6))
    sns.scatterplot(x=X[:, 0], y=X[:, 1], hue=labels, palette='viridis', legend='full')
    if centroids is not None:
        plt.scatter(centroids[:, 0], centroids[:, 1], c='red', marker='X', s=100, label='Centroids')
    plot_title = title if title else "ISODATA Clustering Results"
    plt.title(plot_title)
    plt.xlabel('Feature 1')
    plt.ylabel('Feature 2')
    filename = f'algorithms/other-algorithms/isodata/isodata-{plot_title.lower().replace(" ", "_")}.png'
    plt.savefig(filename)
    plt.close() 