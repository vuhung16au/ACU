# DIANA (Divisive ANAlysis) Clustering Implementation
# This notebook demonstrates the implementation of the DIANA hierarchical clustering algorithm.

## Table of Contents
# 1. Import Required Libraries
# 2. Data Generation and Preprocessing
# 3. DIANA Implementation
# 4. Model Training and Evaluation
# 5. Visualization
# 6. Results and Analysis

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import os
from scipy.spatial.distance import pdist, squareform
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

## 3. DIANA Implementation
def find_most_dissimilar(distances, cluster):
    """
    Find the most dissimilar point in a cluster.
    
    Parameters:
    -----------
    distances : numpy.ndarray
        Distance matrix
    cluster : list
        List of point indices in the cluster
        
    Returns:
    --------
    tuple
        point : int
            Index of most dissimilar point
        distance : float
            Average distance of the point
    """
    if len(cluster) <= 1:
        return None, None
        
    # Compute average distance to other points
    avg_distances = np.zeros(len(cluster))
    for i, point_i in enumerate(cluster):
        for j, point_j in enumerate(cluster):
            if i != j:
                avg_distances[i] += distances[point_i, point_j]
        avg_distances[i] /= (len(cluster) - 1)
        
    # Find point with maximum average distance
    max_idx = np.argmax(avg_distances)
    return cluster[max_idx], avg_distances[max_idx]

def split_cluster(distances, cluster):
    """
    Split a cluster into two based on dissimilarity.
    
    Parameters:
    -----------
    distances : numpy.ndarray
        Distance matrix
    cluster : list
        List of point indices in the cluster
        
    Returns:
    --------
    tuple
        cluster1 : list
            First subcluster
        cluster2 : list
            Second subcluster
    """
    if len(cluster) <= 1:
        return cluster, []
        
    # Find most dissimilar point
    seed, _ = find_most_dissimilar(distances, cluster)
    if seed is None:
        return cluster, []
        
    # Initialize new clusters
    cluster1 = [seed]
    cluster2 = []
    
    # Reassign remaining points
    remaining = [p for p in cluster if p != seed]
    while remaining:
        # Find point with maximum difference in average distance
        max_diff = -float('inf')
        best_point = None
        
        for point in remaining:
            # Average distance to cluster1
            dist1 = np.mean([distances[point, p] for p in cluster1])
            # Average distance to cluster2
            dist2 = np.mean([distances[point, p] for p in cluster2]) if cluster2 else 0
            
            diff = dist2 - dist1
            if diff > max_diff:
                max_diff = diff
                best_point = point
                
        if max_diff > 0:
            cluster2.append(best_point)
        else:
            cluster1.append(best_point)
            
        remaining.remove(best_point)
        
    return cluster1, cluster2

def diana_clustering(X, n_clusters=None, metric='euclidean'):
    """
    Perform DIANA hierarchical clustering.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Input data of shape (n_samples, n_features)
    n_clusters : int, default=None
        Number of clusters to form
        - If None, builds full hierarchy
        - Determines where to cut the dendrogram
    metric : str, default='euclidean'
        Distance metric to use:
        - 'euclidean': Standard Euclidean distance
        - 'manhattan': Manhattan distance
        - 'cosine': Cosine similarity
        
    Returns:
    --------
    tuple
        labels : numpy.ndarray
            Cluster labels for each sample
        children : numpy.ndarray
            Hierarchical clustering tree
        distances : numpy.ndarray
            Distances between merged clusters
    """
    n_samples = X.shape[0]
    
    # Compute distance matrix
    distances = squareform(pdist(X, metric=metric))
    
    # Initialize clusters and hierarchy
    clusters = [list(range(n_samples))]
    children = []
    merge_distances = []
    
    # Main clustering loop
    while len(clusters) < n_samples:
        # Find cluster to split
        max_diameter = -float('inf')
        split_cluster_idx = -1
        
        for i, cluster in enumerate(clusters):
            if len(cluster) > 1:
                diameter = np.max(distances[np.ix_(cluster, cluster)])
                if diameter > max_diameter:
                    max_diameter = diameter
                    split_cluster_idx = i
                    
        if split_cluster_idx == -1:
            break
            
        # Split cluster
        cluster1, cluster2 = split_cluster(distances, clusters[split_cluster_idx])
        
        # Update clusters
        clusters.pop(split_cluster_idx)
        clusters.append(cluster1)
        clusters.append(cluster2)
        
        # Update hierarchy
        children.append([split_cluster_idx, len(clusters)-1])
        merge_distances.append(max_diameter)
        
        # Stop if desired number of clusters reached
        if n_clusters is not None and len(clusters) >= n_clusters:
            break
    
    # Convert children to numpy array
    children = np.array(children)
    merge_distances = np.array(merge_distances)
    
    # Assign labels
    labels = np.zeros(n_samples, dtype=int)
    if n_clusters is not None:
        for i, cluster in enumerate(clusters):
            labels[cluster] = i
    
    return labels, children, merge_distances

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
    metrics = {}
    n_labels = len(np.unique(labels))
    if n_labels < 2:
        metrics['silhouette_score'] = None
    else:
        metrics['silhouette_score'] = silhouette_score(X, labels)
    metrics['calinski_harabasz_score'] = calinski_harabasz_score(X, labels)
    return metrics

## 5. Visualization
def plot_clusters(X, labels, title):
    """
    Plot clustering results using seaborn.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Input data
    labels : numpy.ndarray
        Cluster labels
    title : str
        Plot title
    """
    os.makedirs('algorithms/other-algorithms/diana', exist_ok=True)
    plt.figure(figsize=(10, 6))
    sns.scatterplot(x=X[:, 0], y=X[:, 1], hue=labels, palette='viridis', legend='full')
    plt.title(title)
    plt.xlabel('Feature 1')
    plt.ylabel('Feature 2')
    filename = f'algorithms/other-algorithms/diana/diana-{title.lower().replace(" ", "_")}.png'
    plt.savefig(filename)
    plt.close()

def plot_dendrogram(children, distances, title):
    """
    Plot hierarchical clustering dendrogram using seaborn.
    
    Parameters:
    -----------
    children : numpy.ndarray
        Hierarchical clustering tree
    distances : numpy.ndarray
        Distances between merged clusters
    title : str
        Plot title
    """
    os.makedirs('algorithms/other-algorithms/diana', exist_ok=True)
    plt.figure(figsize=(12, 6))
    sns.clustermap(
        np.zeros((len(children) + 1, len(children) + 1)),
        row_linkage=children,
        col_linkage=children,
        cmap='viridis',
        figsize=(12, 6)
    )
    plt.title(title)
    filename = f'algorithms/other-algorithms/diana/diana-{title.lower().replace(" ", "_")}_dendrogram.png'
    plt.savefig(filename)
    plt.close() 