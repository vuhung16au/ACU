import numpy as np
from typing import List, Tuple, Union, Optional
from scipy.spatial.distance import pdist, squareform
import matplotlib.pyplot as plt
import seaborn as sns
import os

class DIANA:
    def __init__(self, n_clusters: int = 2, distance_metric: str = 'euclidean'):
        """
        Initialize DIANA (DIvisive ANAlysis) clustering
        
        Args:
            n_clusters (int): Number of clusters to form
            distance_metric (str): Distance metric to use ('euclidean', 'manhattan', etc.)
        """
        self.n_clusters = n_clusters
        self.distance_metric = distance_metric
        self.labels_ = None
        self.distances_ = None
        self.cluster_history_ = []
        
    def _compute_distances(self, X: np.ndarray) -> np.ndarray:
        """Compute pairwise distances between all points"""
        return squareform(pdist(X, metric=self.distance_metric))
    
    def _find_splinter_object(self, cluster: List[int], distances: np.ndarray) -> int:
        """Find the object with the largest average dissimilarity to other objects in the cluster"""
        if len(cluster) <= 1:
            return cluster[0]
            
        # Compute average distances to other objects in the cluster
        avg_distances = np.mean(distances[cluster][:, cluster], axis=1)
        return cluster[np.argmax(avg_distances)]
    
    def _split_cluster(self, cluster: List[int], distances: np.ndarray) -> Tuple[List[int], List[int]]:
        """Split a cluster into two based on the DIANA algorithm"""
        if len(cluster) <= 1:
            return cluster, []
            
        # Find the splinter object
        splinter = self._find_splinter_object(cluster, distances)
        
        # Initialize new clusters
        new_cluster = [splinter]
        remaining = [x for x in cluster if x != splinter]
        
        # Move objects to the new cluster if they are more similar to it
        while remaining:
            # Compute average distances to both clusters
            dist_to_new = np.mean(distances[remaining][:, new_cluster], axis=1)
            dist_to_old = np.mean(distances[remaining][:, [x for x in cluster if x not in new_cluster]], axis=1)
            
            # Find objects that are more similar to the new cluster
            to_move = [remaining[i] for i in range(len(remaining)) if dist_to_new[i] < dist_to_old[i]]
            
            if not to_move:
                break
                
            new_cluster.extend(to_move)
            remaining = [x for x in remaining if x not in to_move]
            
        return new_cluster, remaining
    
    def fit(self, X: np.ndarray) -> 'DIANA':
        """
        Fit the DIANA clustering on the data
        
        Args:
            X (np.ndarray): Training data of shape (n_samples, n_features)
            
        Returns:
            self: The fitted model
        """
        n_samples = X.shape[0]
        self.distances_ = self._compute_distances(X)
        
        # Start with all objects in one cluster
        clusters = [list(range(n_samples))]
        self.cluster_history_ = [clusters.copy()]
        
        # Split clusters until we reach the desired number
        while len(clusters) < self.n_clusters:
            # Find the cluster with the largest diameter
            max_diameter = -1
            cluster_to_split = 0
            
            for i, cluster in enumerate(clusters):
                if len(cluster) <= 1:
                    continue
                    
                diameter = np.max(self.distances_[cluster][:, cluster])
                if diameter > max_diameter:
                    max_diameter = diameter
                    cluster_to_split = i
            
            # Split the chosen cluster
            new_cluster, remaining = self._split_cluster(clusters[cluster_to_split], self.distances_)
            
            # Update clusters
            clusters[cluster_to_split] = remaining
            clusters.append(new_cluster)
            self.cluster_history_.append(clusters.copy())
        
        # Assign labels
        self.labels_ = np.zeros(n_samples, dtype=int)
        for i, cluster in enumerate(clusters):
            self.labels_[cluster] = i
            
        return self
    
    def fit_predict(self, X: np.ndarray) -> np.ndarray:
        """
        Fit the model and predict cluster labels
        
        Args:
            X (np.ndarray): Training data of shape (n_samples, n_features)
            
        Returns:
            np.ndarray: Cluster labels
        """
        self.fit(X)
        return self.labels_
    
    def plot_cluster_history(self, X: np.ndarray):
        """
        Plot the clustering history and save to PNG file
        Args:
            X (np.ndarray): Input data
        """
        n_steps = len(self.cluster_history_)
        fig, axes = plt.subplots(1, n_steps, figsize=(5*n_steps, 5))
        if n_steps == 1:
            axes = [axes]
        for i, clusters in enumerate(self.cluster_history_):
            ax = axes[i]
            palette = sns.color_palette("bright", len(clusters))
            for j, cluster in enumerate(clusters):
                ax.scatter(X[cluster, 0], X[cluster, 1], 
                          label=f'Cluster {j}', color=palette[j], alpha=0.7)
            ax.set_title(f'Step {i+1}')
            ax.legend()
        plt.tight_layout()
        os.makedirs('algorithms/clustering/divisive_clustering', exist_ok=True)
        filename = 'algorithms/clustering/divisive_clustering/divisive_clustering-cluster_history.png'
        plt.savefig(filename)
        plt.close()

def compute_silhouette_score(X: np.ndarray, labels: np.ndarray) -> float:
    """
    Compute the silhouette score for clustering evaluation
    
    Args:
        X (np.ndarray): Input data
        labels (np.ndarray): Cluster labels
        
    Returns:
        float: Silhouette score
    """
    from sklearn.metrics import silhouette_score
    return silhouette_score(X, labels)

# Run code sequentially (remove if __name__ == '__main__': block)
# Generate sample data
from sklearn.datasets import make_blobs
X, _ = make_blobs(n_samples=100, centers=3, random_state=42)

# Create and fit the model
model = DIANA(n_clusters=3)
labels = model.fit_predict(X)

# Plot results
plt.figure(figsize=(10, 7))
palette = sns.color_palette("bright", len(np.unique(labels)))
for i, label in enumerate(np.unique(labels)):
    plt.scatter(X[labels == label, 0], X[labels == label, 1],
                label=f"Cluster {label}", alpha=0.7, color=palette[i], s=20)
plt.title('DIANA Clustering Results')
plt.legend()
os.makedirs('algorithms/clustering/divisive_clustering', exist_ok=True)
plt.savefig('algorithms/clustering/divisive_clustering/divisive_clustering-results.png')
plt.close()

# Plot clustering history
model.plot_cluster_history(X)

# Compute silhouette score
score = compute_silhouette_score(X, labels)
print(f"Silhouette Score: {score:.3f}")