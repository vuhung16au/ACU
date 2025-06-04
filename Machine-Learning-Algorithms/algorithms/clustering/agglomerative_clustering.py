# %% [markdown]
# # Agglomerative Clustering Implementation
# 
# This notebook demonstrates the implementation and usage of Agglomerative Clustering algorithm.
# Agglomerative Clustering is a hierarchical clustering method that builds clusters by merging
# the closest pairs of clusters iteratively.

# %% [markdown]
# ## Import Required Libraries

# %%
import numpy as np
from scipy.spatial.distance import pdist, squareform
from scipy.cluster.hierarchy import linkage, fcluster, dendrogram
from typing import List, Tuple, Union, Optional
import seaborn as sns
import matplotlib.pyplot as plt
import os

# Set random seed for reproducibility
np.random.seed(2220)

# %% [markdown]
# ## Helper Functions

# %%
def compute_linkage_matrix(X: np.ndarray, method: str = 'average') -> np.ndarray:
    """
    Compute the linkage matrix for hierarchical clustering
    
    Args:
        X (np.ndarray): Input data of shape (n_samples, n_features)
        method (str): Linkage method to use
            - 'single': Uses the minimum distance between points in clusters
            - 'complete': Uses the maximum distance between points in clusters
            - 'average': Uses the average distance between points in clusters
            
    Returns:
        np.ndarray: Linkage matrix
    """
    return linkage(X, method=method)

def get_cluster_labels(linkage_matrix: np.ndarray, n_clusters: int) -> np.ndarray:
    """
    Get cluster labels from linkage matrix
    
    Args:
        linkage_matrix (np.ndarray): Linkage matrix from hierarchical clustering
        n_clusters (int): Number of clusters to form
        
    Returns:
        np.ndarray: Cluster labels
    """
    return fcluster(linkage_matrix, n_clusters, criterion='maxclust')

def compute_silhouette_score(X: np.ndarray, labels: np.ndarray) -> float:
    """
    Compute the silhouette score for clustering evaluation
    
    Args:
        X (np.ndarray): Input data
        labels (np.ndarray): Cluster labels
        
    Returns:
        float: Silhouette score between -1 and 1, where:
            - Higher values indicate better-defined clusters
            - Values near 0 indicate overlapping clusters
            - Negative values indicate incorrect clustering
    """
    from sklearn.metrics import silhouette_score
    return silhouette_score(X, labels)

# %% [markdown]
# ## Visualization Functions

# %%
def plot_dendrogram(linkage_matrix: np.ndarray, **kwargs):
    """
    Plot the hierarchical clustering dendrogram
    
    Args:
        linkage_matrix (np.ndarray): Linkage matrix from hierarchical clustering
        **kwargs: Additional arguments to pass to scipy's dendrogram function
    """
    os.makedirs('algorithms/clustering/agglomerative_clustering', exist_ok=True)
    
    # Create figure with seaborn style
    sns.set_style("whitegrid")
    plt.figure(figsize=(10, 7))
    
    # Plot dendrogram
    dendrogram(linkage_matrix, **kwargs)
    
    # Customize plot
    plt.title('Hierarchical Clustering Dendrogram', fontsize=14, pad=20)
    plt.xlabel('Sample Index', fontsize=12)
    plt.ylabel('Distance', fontsize=12)
    
    # Save and close
    filename = 'algorithms/clustering/agglomerative_clustering/agglomerative_clustering-dendrogram.png'
    plt.savefig(filename, dpi=300, bbox_inches='tight')
    plt.close()

def plot_clusters(X: np.ndarray, labels: np.ndarray, title: str = "Agglomerative Clustering Results"):
    """
    Plot the clustering results
    
    Args:
        X (np.ndarray): Input data
        labels (np.ndarray): Cluster labels
        title (str): Plot title
    """
    os.makedirs('algorithms/clustering/agglomerative_clustering', exist_ok=True)
    
    # Create figure with seaborn style
    sns.set_style("whitegrid")
    plt.figure(figsize=(10, 8))
    
    # Create scatter plot using seaborn
    palette = sns.color_palette("bright", len(np.unique(labels)))
    for i, label in enumerate(np.unique(labels)):
        sns.scatterplot(
            x=X[labels == label, 0],
            y=X[labels == label, 1],
            label=f"Cluster {label}",
            alpha=0.7,
            color=palette[i],
            s=100
        )
    
    # Customize plot
    plt.title(title, fontsize=14, pad=20)
    plt.xlabel('Feature 1', fontsize=12)
    plt.ylabel('Feature 2', fontsize=12)
    plt.legend(fontsize=10)
    
    # Save and close
    filename = 'algorithms/clustering/agglomerative_clustering/agglomerative_clustering-clusters.png'
    plt.savefig(filename, dpi=300, bbox_inches='tight')
    plt.close()

# %% [markdown]
# ## Main Implementation

# %%
# Generate sample data
from sklearn.datasets import make_blobs
X, _ = make_blobs(
    n_samples=100,  # Number of samples
    centers=3,      # Number of centers/clusters
    random_state=42 # Random state for reproducibility
)

# %% [markdown]
# ### Perform Clustering
# 
# We'll use the following hyperparameters:
# - n_clusters: Number of clusters to form (3 in this case)
# - linkage: Method to compute the distance between clusters ('average' in this case)
#   - 'single': Uses minimum distance between points in clusters
#   - 'complete': Uses maximum distance between points in clusters
#   - 'average': Uses average distance between points in clusters

# %%
# Compute linkage matrix
linkage_matrix = compute_linkage_matrix(X, method='average')

# Get cluster labels
labels = get_cluster_labels(linkage_matrix, n_clusters=3)

# %% [markdown]
# ### Visualize Results

# %%
# Plot clustering results
plot_clusters(X, labels, title="Agglomerative Clustering Results")

# Plot dendrogram
plot_dendrogram(linkage_matrix)

# %% [markdown]
# ### Evaluate Clustering

# %%
# Compute silhouette score
score = compute_silhouette_score(X, labels)
print(f"Silhouette Score: {score:.3f}")