#!/usr/bin/env python3
# -*- coding: utf-8 -*-

## Hierarchical Clustering Implementation
## This implementation provides both custom and scikit-learn versions of hierarchical clustering
## with visualization and evaluation capabilities.

## Import required libraries
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from sklearn.cluster import AgglomerativeClustering
from sklearn.datasets import make_blobs
from sklearn.preprocessing import StandardScaler
from scipy.cluster.hierarchy import dendrogram, linkage
from sklearn.metrics import silhouette_score
import os

## Set random seed for reproducibility
np.random.seed(2220)

## Helper Functions

def generate_data(n_samples=300, n_features=2, n_clusters=4, cluster_std=0.60):
    """
    Generate synthetic data for clustering
    
    Parameters:
    -----------
    n_samples : int, default=300
        Number of samples to generate
    n_features : int, default=2
        Number of features
    n_clusters : int, default=4
        Number of clusters
        - Higher values create more clusters
        - Lower values create fewer clusters
        - Typical range: 2 to 10
    cluster_std : float, default=0.60
        Standard deviation of clusters
        - Higher values create more spread out clusters
        - Lower values create more compact clusters
        - Typical range: 0.3 to 1.0
    """
    X, y = make_blobs(
        n_samples=n_samples,
        n_features=n_features,
        centers=n_clusters,
        cluster_std=cluster_std,
        random_state=2220
    )
    scaler = StandardScaler()
    X_scaled = scaler.fit_transform(X)
    return X_scaled, y

def hierarchical_clustering(X, n_clusters=4, linkage='ward'):
    """
    Perform hierarchical clustering on input data
    
    Parameters:
    -----------
    X : array-like
        Input data matrix
    n_clusters : int, default=4
        Number of clusters to form
        - Higher values create more granular clusters
        - Lower values create broader clusters
        - Typical range: 2 to 10
    linkage : str, default='ward'
        Linkage criterion to use
        - 'ward': Minimizes variance within clusters
        - 'complete': Maximum distance between clusters
        - 'average': Average distance between clusters
        - 'single': Minimum distance between clusters
    """
    model = AgglomerativeClustering(
        n_clusters=n_clusters,
        linkage=linkage,
        compute_distances=True
    )
    labels = model.fit_predict(X)
    return model, labels

def plot_clusters(X, labels, title="Hierarchical Clustering Results"):
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
    os.makedirs('algorithms/clustering/hierarchical_clustering', exist_ok=True)
    
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
    filename = 'algorithms/clustering/hierarchical_clustering/hierarchical_clustering-clusters.png'
    plt.savefig(filename)
    plt.close()

def plot_dendrogram(model, **kwargs):
    """
    Plot hierarchical clustering dendrogram
    
    Parameters:
    -----------
    model : AgglomerativeClustering
        Fitted hierarchical clustering model
    **kwargs : dict
        Additional arguments for dendrogram plotting
        - truncate_mode : str, default='level'
            How to truncate the dendrogram
        - p : int, default=3
            Number of levels to show
    """
    os.makedirs('algorithms/clustering/hierarchical_clustering', exist_ok=True)
    
    # Create linkage matrix
    counts = np.zeros(model.children_.shape[0])
    n_samples = len(model.labels_)
    for i, merge in enumerate(model.children_):
        current_count = 0
        for child_idx in merge:
            if child_idx < n_samples:
                current_count += 1  # leaf node
            else:
                current_count += counts[child_idx - n_samples]
        counts[i] = current_count
    
    linkage_matrix = np.column_stack([model.children_, model.distances_, counts]).astype(float)
    
    # Create plot
    plt.figure(figsize=(12, 8))
    dendrogram(linkage_matrix, **kwargs)
    plt.title('Hierarchical Clustering Dendrogram')
    plt.xlabel('Sample Index')
    plt.ylabel('Distance')
    
    # Save plot
    filename = 'algorithms/clustering/hierarchical_clustering/hierarchical_clustering-dendrogram.png'
    plt.savefig(filename)
    plt.close()

def plot_silhouette_scores(X, n_clusters_range, linkage='ward'):
    """
    Plot silhouette scores for different numbers of clusters
    
    Parameters:
    -----------
    X : array-like
        Input data matrix
    n_clusters_range : list
        Range of cluster numbers to try
        - Typical range: [2, 3, 4, 5, 6]
    linkage : str, default='ward'
        Linkage criterion to use
    """
    os.makedirs('algorithms/clustering/hierarchical_clustering', exist_ok=True)
    
    # Compute silhouette scores
    scores = []
    for n_clusters in n_clusters_range:
        model = AgglomerativeClustering(n_clusters=n_clusters, linkage=linkage)
        labels = model.fit_predict(X)
        score = silhouette_score(X, labels)
        scores.append(score)
    
    # Create plot
    plt.figure(figsize=(10, 6))
    sns.lineplot(x=n_clusters_range, y=scores, marker='o')
    plt.xlabel('Number of Clusters')
    plt.ylabel('Silhouette Score')
    plt.title('Silhouette Analysis for Optimal Number of Clusters')
    
    # Save plot
    filename = 'algorithms/clustering/hierarchical_clustering/hierarchical_clustering-silhouette_scores.png'
    plt.savefig(filename)
    plt.close()

## Generate and process sample data
X, y_true = generate_data(n_samples=300, n_clusters=4, cluster_std=0.60)

## Model parameter selection
n_clusters_range = [2, 3, 4, 5, 6]
plot_silhouette_scores(X, n_clusters_range, linkage='ward')

## Perform hierarchical clustering
print("\nTraining Hierarchical Clustering...")
model, labels = hierarchical_clustering(X, n_clusters=4, linkage='ward')
print(f"Number of clusters: {len(np.unique(labels))}")
print(f"Silhouette Score: {silhouette_score(X, labels):.3f}")

## Visualize results
plot_clusters(X, labels, title="Hierarchical Clustering Results")
plot_dendrogram(model, truncate_mode='level', p=3)