#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
OPTICS (Ordering Points To Identify the Clustering Structure) Implementation
Includes scikit-learn implementation, visualization, and evaluation tools.
"""
import numpy as np
import matplotlib.pyplot as plt
from sklearn.cluster import OPTICS
from sklearn.datasets import make_moons, make_blobs
from sklearn.preprocessing import StandardScaler
from sklearn.metrics import silhouette_score
import seaborn as sns
import os

# Set matplotlib to non-interactive backend
plt.switch_backend('agg')

def plot_clusters(X, labels, title="OPTICS Clustering Results"):
    os.makedirs('algorithms/clustering/optics', exist_ok=True)
    plt.figure(figsize=(10, 8))
    palette = sns.color_palette("bright", len(np.unique(labels)))
    
    # Plot data points
    for i, label in enumerate(np.unique(labels)):
        if label == -1:  # Noise points
            plt.scatter(X[labels == label, 0], X[labels == label, 1],
                       label='Noise', color='gray', alpha=0.3, s=20)
        else:
            plt.scatter(X[labels == label, 0], X[labels == label, 1],
                       label=f"Cluster {label}", alpha=0.7, color=palette[i], s=20)
    
    plt.xlabel('Feature 1')
    plt.ylabel('Feature 2')
    plt.title(title)
    plt.legend()
    filename = 'algorithms/clustering/optics/optics-clusters.png'
    plt.savefig(filename)
    plt.close()

def plot_reachability(model, title="OPTICS Reachability Plot"):
    os.makedirs('algorithms/clustering/optics', exist_ok=True)
    plt.figure(figsize=(10, 6))
    plt.plot(model.reachability_)
    plt.title(title)
    plt.xlabel('Sample Index')
    plt.ylabel('Reachability Distance')
    filename = 'algorithms/clustering/optics/optics-reachability_plot.png'
    plt.savefig(filename)
    plt.close()

def evaluate_clustering(X, labels):
    """Evaluate clustering using silhouette score"""
    if len(np.unique(labels)) > 1:  # Only calculate if there are at least 2 clusters
        score = silhouette_score(X, labels)
        print(f"Silhouette Score: {score:.3f}")
        return score
    return None

# Run code sequentially (remove main function)
# Generate sample data with varying densities
X1, y1 = make_blobs(n_samples=300, centers=[[0, 0]], cluster_std=0.3, random_state=42)
X2, y2 = make_blobs(n_samples=200, centers=[[3, 3]], cluster_std=0.6, random_state=42)
X = np.vstack([X1, X2])

# Scale the data
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)

# OPTICS implementation
print("\nTraining OPTICS Clustering...")
optics = OPTICS(min_samples=5, xi=0.05, min_cluster_size=0.05)
labels = optics.fit_predict(X_scaled)

# Plot results
plot_clusters(X_scaled, labels, title="OPTICS Clustering Results")
plot_reachability(optics, title="OPTICS Reachability Plot")

# Evaluate clustering
evaluate_clustering(X_scaled, labels) 