#!/usr/bin/env python3
# -*- coding: utf-8 -*-

## K-Means Clustering Implementation
## This implementation provides both custom and scikit-learn versions of K-Means clustering
## with visualization and evaluation capabilities.

## Import required libraries
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.cluster import KMeans as SKLearnKMeans
from sklearn.metrics import silhouette_score, adjusted_rand_score
from sklearn.datasets import make_blobs
from sklearn.preprocessing import StandardScaler
import os

## Set random seed for reproducibility
np.random.seed(2220)

## Helper Functions

def initialize_centroids(X, n_clusters, init_method="k-means++", random_state=None):
    """
    Initialize cluster centroids using specified method
    
    Parameters:
    -----------
    X : array-like
        Input data matrix
    n_clusters : int
        Number of clusters to form
        - Higher values create more granular clusters
        - Lower values create broader clusters
        - Typical range: 2 to 10
    init_method : str, default="k-means++"
        Method for initialization
        - "k-means++": Uses k-means++ algorithm for initialization
        - "random": Randomly selects k points from data
    random_state : int, optional
        Random state for reproducibility
    """
    n_samples, n_features = X.shape
    if init_method == "random":
        indices = np.random.choice(n_samples, n_clusters, replace=False)
        return X[indices]
    elif init_method == "k-means++":
        centroids = []
        centroids.append(X[np.random.randint(n_samples)])
        for _ in range(1, n_clusters):
            dist_sq = np.min(
                [np.sum((X - c) ** 2, axis=1) for c in centroids], axis=0
            )
            probs = dist_sq / np.sum(dist_sq)
            next_idx = np.random.choice(n_samples, p=probs)
            centroids.append(X[next_idx])
        return np.array(centroids)
    else:
        raise ValueError("Unknown initialization method.")

def kmeans_clustering(X, n_clusters=3, max_iter=300, tol=1e-4, init="k-means++"):
    """
    Perform K-Means clustering on input data
    
    Parameters:
    -----------
    X : array-like
        Input data matrix
    n_clusters : int, default=3
        Number of clusters to form
        - Higher values create more granular clusters
        - Lower values create broader clusters
        - Typical range: 2 to 10
    max_iter : int, default=300
        Maximum number of iterations
        - Higher values allow more iterations for convergence
        - Lower values may stop before optimal solution
        - Typical range: 100 to 500
    tol : float, default=1e-4
        Tolerance for convergence
        - Higher values stop earlier
        - Lower values require more precise convergence
        - Typical range: 1e-2 to 1e-6
    init : str, default="k-means++"
        Method for initialization
        - "k-means++": Uses k-means++ algorithm for initialization
        - "random": Randomly selects k points from data
    """
    X = np.array(X)
    n_samples, n_features = X.shape
    cluster_centers = initialize_centroids(X, n_clusters, init)
    n_iter = 0
    
    for i in range(max_iter):
        # Assignment step
        distances = np.linalg.norm(X[:, np.newaxis] - cluster_centers, axis=2)
        labels = np.argmin(distances, axis=1)
        
        # Update step
        new_centers = np.array([
            X[labels == j].mean(axis=0) if np.any(labels == j) else cluster_centers[j]
            for j in range(n_clusters)
        ])
        
        # Check for convergence
        shift = np.linalg.norm(cluster_centers - new_centers)
        cluster_centers = new_centers
        n_iter = i + 1
        if shift < tol:
            break
    
    # Final assignment
    distances = np.linalg.norm(X[:, np.newaxis] - cluster_centers, axis=2)
    labels = np.argmin(distances, axis=1)
    inertia = np.sum((X - cluster_centers[labels]) ** 2)
    
    return labels, cluster_centers, inertia, n_iter

def generate_data(n_samples=500, n_features=2, n_clusters=3, cluster_std=1.0):
    """
    Generate synthetic data for clustering
    
    Parameters:
    -----------
    n_samples : int, default=500
        Number of samples to generate
    n_features : int, default=2
        Number of features
    n_clusters : int, default=3
        Number of clusters
    cluster_std : float, default=1.0
        Standard deviation of clusters
        - Higher values create more spread out clusters
        - Lower values create more compact clusters
        - Typical range: 0.5 to 2.0
    """
    X, y = make_blobs(n_samples=n_samples, n_features=n_features, centers=n_clusters,
                      cluster_std=cluster_std, random_state=2220)
    scaler = StandardScaler()
    X_scaled = scaler.fit_transform(X)
    return X_scaled, y

def plot_clusters(X, labels, centers=None, title="K-Means Clustering Results"):
    """
    Plot clusters using seaborn
    
    Parameters:
    -----------
    X : array-like
        Input data matrix
    labels : array-like
        Cluster labels
    centers : array-like, optional
        Cluster centers
    title : str
        Plot title
    """
    os.makedirs('algorithms/clustering/kmeans', exist_ok=True)
    
    # Create DataFrame for seaborn
    df = pd.DataFrame({
        'Feature 1': X[:, 0],
        'Feature 2': X[:, 1],
        'Cluster': labels
    })
    
    # Create plot
    plt.figure(figsize=(10, 8))
    sns.scatterplot(data=df, x='Feature 1', y='Feature 2', hue='Cluster', palette='bright')
    
    if centers is not None:
        plt.scatter(centers[:, 0], centers[:, 1], c='black', s=200, marker='X', label='Centroids')
    
    plt.title(title)
    plt.legend()
    
    # Save plot
    filename = f'algorithms/clustering/kmeans/kmeans-{title.lower().replace(" ", "_")}.png'
    plt.savefig(filename)
    plt.close()

def plot_elbow(X, max_k=10):
    """
    Plot elbow curve for optimal k selection
    
    Parameters:
    -----------
    X : array-like
        Input data matrix
    max_k : int, default=10
        Maximum number of clusters to try
        - Higher values explore more options
        - Lower values limit exploration
        - Typical range: 5 to 15
    """
    os.makedirs('algorithms/clustering/kmeans', exist_ok=True)
    
    # Compute inertias
    inertias = []
    for k in range(1, max_k + 1):
        kmeans = SKLearnKMeans(n_clusters=k, n_init=10, random_state=2220)
        kmeans.fit(X)
        inertias.append(kmeans.inertia_)
    
    # Create plot
    plt.figure(figsize=(10, 6))
    sns.lineplot(x=range(1, max_k + 1), y=inertias, marker='o')
    plt.xlabel('Number of clusters (K)')
    plt.ylabel('Inertia (WCSS)')
    plt.title('Elbow Method for Optimal K')
    
    # Save plot
    filename = 'algorithms/clustering/kmeans/kmeans-elbow_method.png'
    plt.savefig(filename)
    plt.close()

def plot_silhouette_scores(X, max_k=10):
    """
    Plot silhouette scores for optimal k selection
    
    Parameters:
    -----------
    X : array-like
        Input data matrix
    max_k : int, default=10
        Maximum number of clusters to try
        - Higher values explore more options
        - Lower values limit exploration
        - Typical range: 5 to 15
    """
    os.makedirs('algorithms/clustering/kmeans', exist_ok=True)
    
    # Compute silhouette scores
    silhouette_scores = []
    for k in range(2, max_k + 1):
        kmeans = SKLearnKMeans(n_clusters=k, n_init=10, random_state=2220)
        labels = kmeans.fit_predict(X)
        score = silhouette_score(X, labels)
        silhouette_scores.append(score)
    
    # Create plot
    plt.figure(figsize=(10, 6))
    sns.lineplot(x=range(2, max_k + 1), y=silhouette_scores, marker='o')
    plt.xlabel('Number of clusters (K)')
    plt.ylabel('Silhouette Score')
    plt.title('Silhouette Analysis for Optimal K')
    
    # Save plot
    filename = 'algorithms/clustering/kmeans/kmeans-silhouette_analysis.png'
    plt.savefig(filename)
    plt.close()

## Generate and process sample data
X, y_true = generate_data(n_samples=600, n_features=2, n_clusters=4, cluster_std=1.2)

## Custom K-Means clustering
print("\nTraining Custom K-Means...")
labels, centers, inertia, n_iter = kmeans_clustering(
    X, 
    n_clusters=4,
    max_iter=300,
    tol=1e-4,
    init="k-means++"
)
print(f"Custom K-Means Inertia: {inertia:.2f}, Iterations: {n_iter}")
print(f"Silhouette Score: {silhouette_score(X, labels):.3f}")
print(f"Adjusted Rand Index: {adjusted_rand_score(y_true, labels):.3f}")
plot_clusters(X, labels, centers, title="Custom K-Means Clustering")

## scikit-learn KMeans
print("\nTraining scikit-learn KMeans...")
skl_kmeans = SKLearnKMeans(
    n_clusters=4,
    n_init=10,
    max_iter=300,
    tol=1e-4,
    random_state=2220
)
skl_kmeans.fit(X)
print(f"scikit-learn KMeans Inertia: {skl_kmeans.inertia_:.2f}, Iterations: {skl_kmeans.n_iter_}")
print(f"Silhouette Score: {silhouette_score(X, skl_kmeans.labels_):.3f}")
print(f"Adjusted Rand Index: {adjusted_rand_score(y_true, skl_kmeans.labels_):.3f}")
plot_clusters(X, skl_kmeans.labels_, skl_kmeans.cluster_centers_, title="scikit-learn KMeans Clustering")

## Model evaluation plots
plot_elbow(X, max_k=10)
plot_silhouette_scores(X, max_k=10)