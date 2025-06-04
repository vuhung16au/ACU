## DBCLASD (Distribution Based Clustering of LArge Spatial Databases)
## This algorithm performs clustering based on the similarity of distance distributions between points

import numpy as np
from typing import List, Dict, Set, Tuple, Optional
from scipy.stats import ks_2samp
from scipy.spatial import cKDTree
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.datasets import make_moons
from sklearn.preprocessing import StandardScaler
from sklearn.metrics import silhouette_score
import os

# Set random seed for reproducibility
np.random.seed(2220)

## Hyperparameters Explanation
# n_neighbors: Number of nearest neighbors to consider for each point's distribution
# distribution_threshold: Minimum similarity threshold between distributions to consider points as similar
# min_cluster_size: Minimum number of points required to form a cluster
# noise_threshold: Threshold below which points are considered noise

def compute_distributions(X: np.ndarray, n_neighbors: int = 10) -> Dict[int, np.ndarray]:
    """
    Compute distance distributions for each point.
    
    Parameters
    ----------
    X : ndarray
        Input data.
    n_neighbors : int
        Number of neighbors to consider for each point.
        
    Returns
    -------
    distributions : dict
        Dictionary mapping point indices to their distance distributions.
    """
    tree = cKDTree(X)
    distributions = {}
    
    for i in range(len(X)):
        # Get distances to k-nearest neighbors
        distances, _ = tree.query(X[i], k=n_neighbors + 1)
        # Remove self-distance
        distributions[i] = distances[1:]
        
    return distributions

def compute_distribution_similarity(dist1: np.ndarray, dist2: np.ndarray) -> float:
    """
    Compute similarity between two distance distributions using KS test.
    
    Parameters
    ----------
    dist1 : ndarray
        First distance distribution.
    dist2 : ndarray
        Second distance distribution.
        
    Returns
    -------
    similarity : float
        Similarity measure between distributions.
    """
    # Perform KS test
    statistic, _ = ks_2samp(dist1, dist2)
    return 1 - statistic  # Convert to similarity

def find_similar_points(
    point_idx: int,
    distributions: Dict[int, np.ndarray],
    distribution_threshold: float = 0.1
) -> Set[int]:
    """
    Find points with similar distance distributions.
    
    Parameters
    ----------
    point_idx : int
        Index of the reference point.
    distributions : dict
        Dictionary of point distributions.
    distribution_threshold : float
        Threshold for distribution similarity.
        
    Returns
    -------
    similar_points : set
        Set of indices of similar points.
    """
    similar_points = {point_idx}
    to_process = {point_idx}
    
    while to_process:
        current = to_process.pop()
        current_dist = distributions[current]
        
        # Check all points
        for i in range(len(distributions)):
            if i in similar_points:
                continue
                
            # Compute distribution similarity
            similarity = compute_distribution_similarity(
                current_dist,
                distributions[i]
            )
            
            if similarity >= distribution_threshold:
                similar_points.add(i)
                to_process.add(i)
                
    return similar_points

def is_noise(
    point_idx: int,
    distributions: Dict[int, np.ndarray],
    noise_threshold: float = 0.05
) -> bool:
    """
    Check if a point is noise based on its distribution.
    
    Parameters
    ----------
    point_idx : int
        Index of the point to check.
    distributions : dict
        Dictionary of point distributions.
    noise_threshold : float
        Threshold for noise detection.
        
    Returns
    -------
    is_noise : bool
        True if the point is considered noise.
    """
    # Get point's distribution
    dist = distributions[point_idx]
    
    # Compute average similarity to other points
    similarities = []
    for i in range(len(distributions)):
        if i != point_idx:
            similarity = compute_distribution_similarity(dist, distributions[i])
            similarities.append(similarity)
            
    # Check if average similarity is below threshold
    return np.mean(similarities) < noise_threshold

def dbclasd_clustering(
    X: np.ndarray,
    n_neighbors: int = 10,
    distribution_threshold: float = 0.1,
    min_cluster_size: int = 5,
    noise_threshold: float = 0.05
) -> np.ndarray:
    """
    Perform DBCLASD clustering from spatial data.
    
    Parameters
    ----------
    X : ndarray
        Input data.
    n_neighbors : int
        Number of neighbors to consider.
    distribution_threshold : float
        Threshold for distribution similarity.
    min_cluster_size : int
        Minimum cluster size.
    noise_threshold : float
        Threshold for noise detection.
        
    Returns
    -------
    labels : ndarray
        Cluster labels for each point.
    """
    # Compute distance distributions
    distributions = compute_distributions(X, n_neighbors)
    
    # Initialize labels
    labels = np.full(len(X), -1)
    cluster_label = 0
    
    # Process each point
    for i in range(len(X)):
        if labels[i] != -1:
            continue
            
        # Find distribution-similar points
        similar_points = find_similar_points(i, distributions, distribution_threshold)
        
        # Check if cluster is large enough
        if len(similar_points) >= min_cluster_size:
            # Assign cluster label
            for point in similar_points:
                labels[point] = cluster_label
            cluster_label += 1
    
    return labels

def plot_clusters(X: np.ndarray, labels: np.ndarray, title: str = "DBCLASD Clustering Results"):
    """
    Plot clustering results and save to PNG file.
    
    Parameters
    ----------
    X : ndarray
        Input data.
    labels : ndarray
        Cluster labels.
    title : str
        Plot title.
    """
    os.makedirs('algorithms/clustering/dbclasd', exist_ok=True)
    
    # Create figure
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
    filename = 'algorithms/clustering/dbclasd/dbclasd-clusters.png'
    plt.savefig(filename)
    plt.close()

def plot_distribution_similarities(distributions: Dict[int, np.ndarray], X: np.ndarray):
    """
    Plot distribution similarities between points and save to PNG file.
    
    Parameters
    ----------
    distributions : dict
        Dictionary of point distributions.
    X : ndarray
        Input data.
    """
    os.makedirs('algorithms/clustering/dbclasd', exist_ok=True)
    
    # Create figure
    plt.figure(figsize=(10, 8))
    
    # Compute similarity matrix
    n_samples = len(X)
    similarity_matrix = np.zeros((n_samples, n_samples))
    for i in range(n_samples):
        for j in range(i+1, n_samples):
            similarity = compute_distribution_similarity(
                distributions[i],
                distributions[j]
            )
            similarity_matrix[i, j] = similarity_matrix[j, i] = similarity
    
    # Plot similarity matrix
    sns.heatmap(similarity_matrix, cmap='viridis')
    plt.title('Distribution Similarity Matrix')
    filename = 'algorithms/clustering/dbclasd/dbclasd-similarity_matrix.png'
    plt.savefig(filename)
    plt.close()

## Generate and process sample data
# Generate sample data
X, _ = make_moons(n_samples=300, noise=0.1, random_state=42)

# Scale the data
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)

## Perform clustering
print("\nTraining DBCLASD Clustering...")
labels = dbclasd_clustering(
    X_scaled,
    n_neighbors=10,
    distribution_threshold=0.1,
    min_cluster_size=5,
    noise_threshold=0.05
)

## Visualize results
# Plot clusters
plot_clusters(X_scaled, labels)

# Compute and plot distribution similarities
distributions = compute_distributions(X_scaled)
plot_distribution_similarities(distributions, X_scaled)

## Evaluate clustering
# Compute silhouette score
if len(np.unique(labels)) > 1:
    score = silhouette_score(X_scaled, labels)
    print(f"Silhouette Score: {score:.3f}") 