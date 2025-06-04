#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Hierarchical Clustering Implementation
Includes custom agglomerative implementation, scikit-learn comparison, dendrogram visualization, and evaluation tools.
"""
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from scipy.cluster.hierarchy import dendrogram, linkage, fcluster, cophenet
from scipy.spatial.distance import pdist
from sklearn.cluster import AgglomerativeClustering
from sklearn.metrics import adjusted_rand_score, silhouette_score
from sklearn.datasets import make_blobs
from sklearn.preprocessing import StandardScaler
import seaborn as sns
import os

# Set matplotlib to non-interactive backend
plt.switch_backend('agg')

class CustomAgglomerativeClustering:
    """
    Custom implementation of agglomerative hierarchical clustering (single linkage).
    """
    def __init__(self, n_clusters=2, linkage='single'):
        self.n_clusters = n_clusters
        self.linkage = linkage
        self.labels_ = None
        self.children_ = None
        self.distances_ = None

    def fit(self, X):
        X = np.array(X)
        n_samples = X.shape[0]
        clusters = [[i] for i in range(n_samples)]
        distances = pdist(X)
        dist_matrix = np.zeros((n_samples, n_samples))
        dist_matrix[np.triu_indices(n_samples, 1)] = distances
        dist_matrix += dist_matrix.T
        children = []
        dists = []
        current_cluster = n_samples
        cluster_map = {i: [i] for i in range(n_samples)}
        active = set(range(n_samples))
        while len(active) > 1:
            min_dist = np.inf
            to_merge = (None, None)
            for i in active:
                for j in active:
                    if i < j:
                        if self.linkage == 'single':
                            dist = np.min([dist_matrix[x, y] for x in cluster_map[i] for y in cluster_map[j]])
                        elif self.linkage == 'complete':
                            dist = np.max([dist_matrix[x, y] for x in cluster_map[i] for y in cluster_map[j]])
                        elif self.linkage == 'average':
                            dist = np.mean([dist_matrix[x, y] for x in cluster_map[i] for y in cluster_map[j]])
                        else:
                            raise ValueError('Unsupported linkage')
                        if dist < min_dist:
                            min_dist = dist
                            to_merge = (i, j)
            i, j = to_merge
            children.append([i, j])
            dists.append(min_dist)
            cluster_map[current_cluster] = cluster_map[i] + cluster_map[j]
            active.remove(i)
            active.remove(j)
            active.add(current_cluster)
            current_cluster += 1
        self.children_ = np.array(children)
        self.distances_ = np.array(dists)
        # Assign cluster labels by cutting the tree
        n_leaves = n_samples
        n_merges = len(children)
        Z = np.column_stack([self.children_, self.distances_, np.zeros(n_merges)])
        # Use scipy's fcluster for label assignment
        self.labels_ = fcluster(np.column_stack([self.children_, self.distances_, np.zeros(n_merges)]), self.n_clusters, criterion='maxclust') - 1
        return self

def generate_data(n_samples=300, n_features=2, n_clusters=3, cluster_std=1.2, random_state=42):
    X, y = make_blobs(n_samples=n_samples, n_features=n_features, centers=n_clusters,
                      cluster_std=cluster_std, random_state=random_state)
    scaler = StandardScaler()
    X_scaled = scaler.fit_transform(X)
    return X_scaled, y

def plot_dendrogram(X, method='ward', title="Dendrogram"):
    """
    Plot dendrogram and save to PNG file.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Features
    method : str
        Linkage method
    title : str
        Plot title
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/clustering/hierarchical_clustering', exist_ok=True)
    
    # Compute linkage matrix
    Z = linkage(X, method=method)
    
    # Create plot
    plt.figure(figsize=(10, 6))
    dendrogram(Z)
    plt.title(title)
    plt.xlabel('Sample Index')
    plt.ylabel('Distance')
    
    # Save plot
    filename = f'algorithms/clustering/hierarchical_clustering/hierarchical_clustering-{title.lower().replace(" ", "_")}.png'
    plt.savefig(filename)
    plt.close()

def plot_clusters(X, labels, title="Hierarchical Clustering Results"):
    """
    Plot clusters and save to PNG file.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Features
    labels : numpy.ndarray
        Cluster labels
    title : str
        Plot title
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/clustering/hierarchical_clustering', exist_ok=True)
    
    # Create plot
    plt.figure(figsize=(8, 6))
    palette = sns.color_palette("bright", np.unique(labels).max() + 1)
    sns.scatterplot(x=X[:, 0], y=X[:, 1], hue=labels, palette=palette, legend="full", alpha=0.7)
    plt.title(title)
    plt.xlabel("Feature 1")
    plt.ylabel("Feature 2")
    plt.legend()
    
    # Save plot
    filename = f'algorithms/clustering/hierarchical_clustering/hierarchical_clustering-{title.lower().replace(" ", "_")}.png'
    plt.savefig(filename)
    plt.close()

def plot_cophenetic_correlation(X, methods=['single', 'complete', 'average', 'ward']):
    """
    Plot cophenetic correlation for different linkage methods and save to PNG file.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Features
    methods : list
        List of linkage methods to compare
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/clustering/hierarchical_clustering', exist_ok=True)
    
    # Compute cophenetic correlations
    correlations = []
    for method in methods:
        Z = linkage(X, method=method)
        c, _ = cophenet(Z, pdist(X))
        correlations.append(c)
    
    # Create plot
    plt.figure(figsize=(10, 6))
    plt.bar(methods, correlations)
    plt.title('Cophenetic Correlation for Different Linkage Methods')
    plt.xlabel('Linkage Method')
    plt.ylabel('Cophenetic Correlation')
    plt.ylim(0, 1)
    
    # Save plot
    filename = 'algorithms/clustering/hierarchical_clustering/hierarchical_clustering-cophenetic_correlation.png'
    plt.savefig(filename)
    plt.close()

# Run code sequentially
# Generate synthetic data
X, y_true = generate_data(n_samples=300, n_features=2, n_clusters=3, cluster_std=1.2, random_state=42)

# Plot dendrogram (Ward's method)
plot_dendrogram(X, method='ward', title="Ward Dendrogram (scipy)")

# Plot cophenetic correlation for different methods
plot_cophenetic_correlation(X)

# Custom Agglomerative Clustering (single linkage)
print("\nTraining Custom Agglomerative Clustering (single linkage)...")
custom_hc = CustomAgglomerativeClustering(n_clusters=3, linkage='single')
custom_hc.fit(X)
print(f"Adjusted Rand Index: {adjusted_rand_score(y_true, custom_hc.labels_):.3f}")
print(f"Silhouette Score: {silhouette_score(X, custom_hc.labels_):.3f}")
plot_clusters(X, custom_hc.labels_, title="Custom Agglomerative Clustering (Single Linkage)")

# scikit-learn AgglomerativeClustering (Ward)
print("\nTraining scikit-learn AgglomerativeClustering (Ward)...")
skl_hc = AgglomerativeClustering(n_clusters=3, linkage='ward')
skl_hc.fit(X)
print(f"Adjusted Rand Index: {adjusted_rand_score(y_true, skl_hc.labels_):.3f}")
print(f"Silhouette Score: {silhouette_score(X, skl_hc.labels_):.3f}")
plot_clusters(X, skl_hc.labels_, title="scikit-learn AgglomerativeClustering (Ward)") 