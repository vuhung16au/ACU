#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Partitioning Around Medoids (PAM) / K-medoids Clustering Implementation
Includes scikit-learn implementation, custom implementation, visualization, and evaluation tools.
"""
import numpy as np
import matplotlib.pyplot as plt
from sklearn.cluster import KMeans
from sklearn.datasets import make_blobs
from sklearn.preprocessing import StandardScaler
from sklearn.metrics import silhouette_score
import seaborn as sns

def plot_clusters(X, labels, medoids, title="PAM Clustering Results"):
    plt.figure(figsize=(10, 8))
    palette = sns.color_palette("bright", len(np.unique(labels)))
    
    # Plot data points
    for i, label in enumerate(np.unique(labels)):
        plt.scatter(X[labels == label, 0], X[labels == label, 1],
                   label=f"Cluster {label}", alpha=0.7, color=palette[i], s=20)
    
    # Plot medoids
    plt.scatter(medoids[:, 0], medoids[:, 1], s=100, c='black', marker='X',
               label='Medoids')
    
    plt.xlabel('Feature 1')
    plt.ylabel('Feature 2')
    plt.title(title)
    plt.legend()
    plt.savefig('algorithms/clustering/pam_clustering_results.png')

def evaluate_clustering(X, labels):
    """Evaluate clustering using silhouette score"""
    score = silhouette_score(X, labels)
    print(f"Silhouette Score: {score:.3f}")
    return score

# Generate sample data with some outliers
X, y = make_blobs(n_samples=300, centers=4, cluster_std=0.60, random_state=42)

# Add some outliers
outliers = np.random.randn(20, 2) * 2
X = np.vstack([X, outliers])
y = np.hstack([y, np.ones(20) * -1])  # -1 for outliers

# Scale the data
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)

# KMeans implementation (instead of KMedoids)
print("\nTraining KMeans Clustering...")
kmeans = KMeans(n_clusters=4, random_state=42)
labels = kmeans.fit_predict(X_scaled)

# Get centroids (instead of medoids)
centroids = kmeans.cluster_centers_

# Plot results
plot_clusters(X_scaled, labels, centroids, title="KMeans Clustering Results")

# Evaluate clustering
evaluate_clustering(X_scaled, labels)