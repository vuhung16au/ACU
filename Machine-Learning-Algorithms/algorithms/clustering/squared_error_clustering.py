# %% [markdown]
# # Squared Error Clustering Implementation
# 
# This notebook implements the Squared Error Clustering algorithm, which minimizes the sum of squared errors
# between data points and their cluster centers. The algorithm is similar to K-means but uses a different
# optimization criterion and can handle various distance metrics.

# %% [markdown]
# ## 1. Import Required Libraries

# %%
import numpy as np
import seaborn as sns
import pandas as pd
import matplotlib.pyplot as plt
from typing import Tuple, List, Optional, Union
from sklearn.metrics import silhouette_score, calinski_harabasz_score, davies_bouldin_score
from sklearn.datasets import make_blobs
import os

# Set random seed for reproducibility
np.random.seed(2220)

# %% [markdown]
# ## 2. Helper Functions

# %%
def compute_distance(X: np.ndarray, centers: np.ndarray, metric: str = 'euclidean') -> np.ndarray:
    """
    Compute distances between points and centers using specified metric.
    
    Parameters:
    -----------
    X : np.ndarray
        Input data points
    centers : np.ndarray
        Cluster centers
    metric : str, default='euclidean'
        Distance metric to use ('euclidean', 'manhattan', or 'cosine')
        
    Returns:
    --------
    np.ndarray
        Distance matrix between points and centers
    """
    if metric == 'euclidean':
        return np.sqrt(((X[:, np.newaxis] - centers) ** 2).sum(axis=2))
    elif metric == 'manhattan':
        return np.abs(X[:, np.newaxis] - centers).sum(axis=2)
    elif metric == 'cosine':
        X_norm = X / np.linalg.norm(X, axis=1)[:, np.newaxis]
        centers_norm = centers / np.linalg.norm(centers, axis=1)[:, np.newaxis]
        return 1 - np.dot(X_norm, centers_norm.T)
    else:
        raise ValueError(f"Unsupported metric: {metric}")

def initialize_centers(X: np.ndarray, n_clusters: int) -> np.ndarray:
    """
    Initialize cluster centers using random selection.
    
    Parameters:
    -----------
    X : np.ndarray
        Input data points
    n_clusters : int
        Number of clusters to form
        
    Returns:
    --------
    np.ndarray
        Initial cluster centers
    """
    indices = np.random.choice(X.shape[0], n_clusters, replace=False)
    return X[indices]

def update_centers(X: np.ndarray, labels: np.ndarray, n_clusters: int) -> np.ndarray:
    """
    Update cluster centers to minimize squared error.
    
    Parameters:
    -----------
    X : np.ndarray
        Input data points
    labels : np.ndarray
        Current cluster assignments
    n_clusters : int
        Number of clusters
        
    Returns:
    --------
    np.ndarray
        Updated cluster centers
    """
    centers = np.zeros((n_clusters, X.shape[1]))
    for k in range(n_clusters):
        if np.sum(labels == k) > 0:
            centers[k] = np.mean(X[labels == k], axis=0)
    return centers

# %% [markdown]
# ## 3. Main Clustering Function

# %%
def squared_error_clustering(
    X: np.ndarray,
    n_clusters: int = 3,
    max_iter: int = 100,
    tol: float = 1e-4,
    metric: str = 'euclidean'
) -> Tuple[np.ndarray, np.ndarray, float, int]:
    """
    Perform squared error clustering on the input data.
    
    Parameters:
    -----------
    X : np.ndarray
        Input data points
    n_clusters : int, default=3
        Number of clusters to form
    max_iter : int, default=100
        Maximum number of iterations
    tol : float, default=1e-4
        Convergence tolerance
    metric : str, default='euclidean'
        Distance metric to use
        
    Returns:
    --------
    Tuple[np.ndarray, np.ndarray, float, int]
        - Cluster centers
        - Cluster labels
        - Final inertia
        - Number of iterations performed
    """
    # Initialize centers
    centers = initialize_centers(X, n_clusters)
    old_inertia = np.inf
    n_iter = 0
    
    for iteration in range(max_iter):
        # Assign points to nearest centers
        distances = compute_distance(X, centers, metric)
        labels = np.argmin(distances, axis=1)
        
        # Update centers
        centers = update_centers(X, labels, n_clusters)
        
        # Compute inertia
        inertia = np.sum(np.min(distances, axis=1) ** 2)
        
        # Check convergence
        if abs(old_inertia - inertia) < tol * old_inertia:
            break
            
        old_inertia = inertia
        n_iter = iteration + 1
        
    return centers, labels, inertia, n_iter

# %% [markdown]
# ## 4. Evaluation Functions

# %%
def evaluate_clustering(X: np.ndarray, labels: np.ndarray, inertia: float, n_iter: int) -> dict:
    """
    Evaluate clustering quality using multiple metrics.
    
    Parameters:
    -----------
    X : np.ndarray
        Input data points
    labels : np.ndarray
        Cluster assignments
    inertia : float
        Final inertia value
    n_iter : int
        Number of iterations performed
        
    Returns:
    --------
    dict
        Dictionary containing evaluation metrics
    """
    metrics = {
        'silhouette_score': silhouette_score(X, labels),
        'calinski_harabasz_score': calinski_harabasz_score(X, labels),
        'davies_bouldin_score': davies_bouldin_score(X, labels),
        'inertia': inertia,
        'n_iterations': n_iter
    }
    return metrics

# %% [markdown]
# ## 5. Visualization Functions

# %%
def plot_clusters(X: np.ndarray, labels: np.ndarray, centers: np.ndarray, title: str = "Clustering Results"):
    """
    Visualize clustering results using seaborn.
    
    Parameters:
    -----------
    X : np.ndarray
        Input data points
    labels : np.ndarray
        Cluster assignments
    centers : np.ndarray
        Cluster centers
    title : str
        Plot title
    """
    # Create DataFrame for plotting
    df = pd.DataFrame(X, columns=['x', 'y'])
    df['cluster'] = labels
    
    # Create plot
    plt.figure(figsize=(10, 8))
    sns.scatterplot(data=df, x='x', y='y', hue='cluster', palette='deep')
    sns.scatterplot(x=centers[:, 0], y=centers[:, 1], color='red', marker='X', s=200, label='Centers')
    
    plt.title(title)
    plt.legend()
    
    # Save plot
    os.makedirs('plots', exist_ok=True)
    plt.savefig(f'plots/{title.lower().replace(" ", "_")}.png')
    plt.close()

# %% [markdown]
# ## 6. Example Usage

# %%
# Generate sample data
X, _ = make_blobs(n_samples=300, centers=4, cluster_std=0.60, random_state=2220)

# Perform clustering
centers, labels, inertia, n_iter = squared_error_clustering(
    X,
    n_clusters=4,
    max_iter=100,
    tol=1e-4,
    metric='euclidean'
)

# Evaluate results
metrics = evaluate_clustering(X, labels, inertia, n_iter)
print("\nClustering Metrics:")
for metric_name, value in metrics.items():
    print(f"{metric_name}: {value:.4f}")

# Visualize results
plot_clusters(X, labels, centers, "Squared Error Clustering Results") 