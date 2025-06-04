#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
DENCLUE (DENsity-based CLUstering) Implementation
Includes custom implementation, visualization, and evaluation tools.
"""
import numpy as np
import matplotlib.pyplot as plt
from sklearn.datasets import make_moons, make_blobs
from sklearn.preprocessing import StandardScaler
from sklearn.metrics import silhouette_score
from scipy.stats import gaussian_kde
import seaborn as sns
import os

# Set matplotlib to non-interactive backend
plt.switch_backend('agg')

class DENCLUE:
    def __init__(self, sigma=0.5, xi=0.1, convergence_threshold=1e-4):
        self.sigma = sigma  # Kernel bandwidth
        self.xi = xi  # Density threshold
        self.convergence_threshold = convergence_threshold
        self.attractors = None
        self.labels = None
        
    def _gaussian_kernel(self, x, y):
        """Gaussian kernel function"""
        return np.exp(-np.sum((x - y) ** 2) / (2 * self.sigma ** 2))
    
    def _density_function(self, X, point):
        """Calculate density at a point using kernel density estimation"""
        return np.mean([self._gaussian_kernel(point, x) for x in X])
    
    def _gradient(self, X, point):
        """Calculate gradient of density function"""
        gradient = np.zeros_like(point)
        for x in X:
            diff = x - point
            gradient += diff * self._gaussian_kernel(point, x)
        return gradient / (self.sigma ** 2)
    
    def _hill_climbing(self, X, start_point):
        """Hill climbing to find density attractor"""
        current_point = start_point.copy()
        while True:
            gradient = self._gradient(X, current_point)
            if np.linalg.norm(gradient) < self.convergence_threshold:
                break
            current_point += gradient
        return current_point
    
    def fit_predict(self, X):
        """Fit the model and predict cluster labels"""
        n_samples = X.shape[0]
        self.attractors = []
        self.labels = np.zeros(n_samples, dtype=int)
        
        # Find density attractors for each point
        for i in range(n_samples):
            attractor = self._hill_climbing(X, X[i])
            density = self._density_function(X, attractor)
            
            if density >= self.xi:
                # Check if this attractor is close to any existing attractor
                is_new = True
                for j, existing_attractor in enumerate(self.attractors):
                    if np.linalg.norm(attractor - existing_attractor) < self.sigma:
                        self.labels[i] = j
                        is_new = False
                        break
                
                if is_new:
                    self.attractors.append(attractor)
                    self.labels[i] = len(self.attractors) - 1
            else:
                self.labels[i] = -1  # Noise point
        
        return self.labels

def plot_clusters(X, labels, title="DENCLUE Clustering Results"):
    os.makedirs('algorithms/clustering/denclue', exist_ok=True)
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
    filename = 'algorithms/clustering/denclue/denclue-clusters.png'
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
# Generate sample data with non-spherical clusters
X, y = make_moons(n_samples=300, noise=0.1, random_state=42)

# Scale the data
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)

# DENCLUE implementation
print("\nTraining DENCLUE Clustering...")
denclue = DENCLUE(sigma=0.3, xi=0.1)
labels = denclue.fit_predict(X_scaled)

# Plot results
plot_clusters(X_scaled, labels, title="DENCLUE Clustering Results")

# Evaluate clustering
evaluate_clustering(X_scaled, labels) 