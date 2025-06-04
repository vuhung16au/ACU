import numpy as np
from typing import List, Tuple, Union, Optional
from scipy.spatial.distance import pdist, squareform
import matplotlib.pyplot as plt
import seaborn as sns
import os
from sklearn.datasets import make_blobs
from sklearn.preprocessing import StandardScaler
from sklearn.metrics import silhouette_score

# Set matplotlib to non-interactive backend
plt.switch_backend('agg')

class PAM:
    def __init__(self, n_clusters: int = 2, max_iter: int = 100, random_state: Optional[int] = None):
        """
        Initialize PAM (Partitioning Around Medoids) clustering
        
        Args:
            n_clusters (int): Number of clusters to form
            max_iter (int): Maximum number of iterations
            random_state (int): Random state for reproducibility
        """
        self.n_clusters = n_clusters
        self.max_iter = max_iter
        self.random_state = random_state
        self.labels_ = None
        self.medoids_ = None
        self.cost_ = None
        self.distances_ = None
        
    def _compute_distances(self, X: np.ndarray) -> np.ndarray:
        """Compute pairwise distances between all points"""
        return squareform(pdist(X))
    
    def _initialize_medoids(self, X: np.ndarray) -> np.ndarray:
        """Initialize medoids using random selection"""
        if self.random_state is not None:
            np.random.seed(self.random_state)
            
        n_samples = X.shape[0]
        indices = np.random.choice(n_samples, self.n_clusters, replace=False)
        return indices
    
    def _assign_clusters(self, distances: np.ndarray, medoids: np.ndarray) -> Tuple[np.ndarray, float]:
        """Assign points to clusters and compute total cost"""
        n_samples = distances.shape[0]
        labels = np.zeros(n_samples, dtype=int)
        cost = 0
        
        for i in range(n_samples):
            # Find nearest medoid
            distances_to_medoids = distances[i, medoids]
            labels[i] = np.argmin(distances_to_medoids)
            cost += np.min(distances_to_medoids)
            
        return labels, cost
    
    def _find_best_swap(self, distances: np.ndarray, medoids: np.ndarray, 
                       labels: np.ndarray, cost: float) -> Tuple[float, int, int]:
        """Find the best swap of a medoid with a non-medoid"""
        best_cost = cost
        best_swap = None
        
        for i in range(len(medoids)):
            for j in range(distances.shape[0]):
                if j in medoids:
                    continue
                    
                # Try swapping medoid i with point j
                new_medoids = medoids.copy()
                new_medoids[i] = j
                
                # Compute new cost
                new_labels, new_cost = self._assign_clusters(distances, new_medoids)
                
                if new_cost < best_cost:
                    best_cost = new_cost
                    best_swap = (i, j)
                    
        return best_cost, best_swap
    
    def fit(self, X: np.ndarray) -> 'PAM':
        """
        Fit the PAM clustering on the data
        
        Args:
            X (np.ndarray): Training data of shape (n_samples, n_features)
            
        Returns:
            self: The fitted model
        """
        # Compute distances
        self.distances_ = self._compute_distances(X)
        
        # Initialize medoids
        medoids = self._initialize_medoids(X)
        
        # Initial assignment
        labels, cost = self._assign_clusters(self.distances_, medoids)
        
        # Main loop
        for iteration in range(self.max_iter):
            # Find best swap
            best_cost, best_swap = self._find_best_swap(self.distances_, medoids, labels, cost)
            
            # If no improvement, stop
            if best_swap is None:
                break
                
            # Perform swap
            i, j = best_swap
            medoids[i] = j
            labels, cost = self._assign_clusters(self.distances_, medoids)
            
        # Store results
        self.labels_ = labels
        self.medoids_ = medoids
        self.cost_ = cost
        
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

class CLARA(PAM):
    def __init__(self, n_clusters: int = 2, n_samples: int = 40, n_sampling: int = 5,
                 max_iter: int = 100, random_state: Optional[int] = None):
        """
        Initialize CLARA (Clustering LARge Applications)
        
        Args:
            n_clusters (int): Number of clusters to form
            n_samples (int): Number of samples to draw
            n_sampling (int): Number of sampling iterations
            max_iter (int): Maximum number of iterations
            random_state (int): Random state for reproducibility
        """
        super().__init__(n_clusters, max_iter, random_state)
        self.n_samples = n_samples
        self.n_sampling = n_sampling
        
    def fit(self, X: np.ndarray) -> 'CLARA':
        """
        Fit the CLARA clustering on the data
        
        Args:
            X (np.ndarray): Training data of shape (n_samples, n_features)
            
        Returns:
            self: The fitted model
        """
        best_cost = float('inf')
        best_medoids = None
        best_labels = None
        
        for _ in range(self.n_sampling):
            # Draw sample
            if self.random_state is not None:
                np.random.seed(self.random_state + _)
            sample_indices = np.random.choice(len(X), self.n_samples, replace=False)
            X_sample = X[sample_indices]
            
            # Apply PAM to sample
            distances_sample = self._compute_distances(X_sample)
            medoids_sample = self._initialize_medoids(X_sample)
            labels_sample, cost_sample = self._assign_clusters(distances_sample, medoids_sample)
            
            # Find best swap in sample
            for _ in range(self.max_iter):
                best_cost_sample, best_swap = self._find_best_swap(
                    distances_sample, medoids_sample, labels_sample, cost_sample)
                
                if best_swap is None:
                    break
                    
                i, j = best_swap
                medoids_sample[i] = j
                labels_sample, cost_sample = self._assign_clusters(distances_sample, medoids_sample)
            
            # Map sample medoids back to original data
            medoids = sample_indices[medoids_sample]
            
            # Compute cost on full dataset
            distances = self._compute_distances(X)
            labels, cost = self._assign_clusters(distances, medoids)
            
            if cost < best_cost:
                best_cost = cost
                best_medoids = medoids
                best_labels = labels
        
        # Store results
        self.labels_ = best_labels
        self.medoids_ = best_medoids
        self.cost_ = best_cost
        
        return self

class CLARANS(PAM):
    def __init__(self, n_clusters: int = 2, num_local: int = 2, max_neighbor: int = 250,
                 random_state: Optional[int] = None):
        """
        Initialize CLARANS (Clustering Large Applications based on RANdomized Search)
        
        Args:
            n_clusters (int): Number of clusters to form
            num_local (int): Number of local minima to find
            max_neighbor (int): Maximum number of neighbors to examine
            random_state (int): Random state for reproducibility
        """
        super().__init__(n_clusters)
        self.num_local = num_local
        self.max_neighbor = max_neighbor
        self.random_state = random_state
        
    def fit(self, X: np.ndarray) -> 'CLARANS':
        """
        Fit the CLARANS clustering on the data
        
        Args:
            X (np.ndarray): Training data of shape (n_samples, n_features)
            
        Returns:
            self: The fitted model
        """
        # Compute distances
        distances = self._compute_distances(X)
        
        best_cost = float('inf')
        best_medoids = None
        best_labels = None
        
        for _ in range(self.num_local):
            # Initialize medoids
            medoids = self._initialize_medoids(X)
            labels, cost = self._assign_clusters(distances, medoids)
            
            # Local search
            i = 0
            while i < self.max_neighbor:
                # Randomly select a medoid and a non-medoid
                medoid_idx = np.random.randint(self.n_clusters)
                non_medoid = np.random.randint(len(X))
                while non_medoid in medoids:
                    non_medoid = np.random.randint(len(X))
                
                # Try swap
                new_medoids = medoids.copy()
                new_medoids[medoid_idx] = non_medoid
                new_labels, new_cost = self._assign_clusters(distances, new_medoids)
                
                if new_cost < cost:
                    medoids = new_medoids
                    labels = new_labels
                    cost = new_cost
                    i = 0
                else:
                    i += 1
            
            if cost < best_cost:
                best_cost = cost
                best_medoids = medoids
                best_labels = labels
        
        # Store results
        self.labels_ = best_labels
        self.medoids_ = best_medoids
        self.cost_ = best_cost
        
        return self

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

def plot_clusters(X: np.ndarray, labels: np.ndarray, medoids: np.ndarray, title: str = "PAM Clustering Results"):
    """Plot clusters and medoids"""
    # Create directory if it doesn't exist
    os.makedirs('algorithms/clustering/pam', exist_ok=True)
    
    plt.figure(figsize=(10, 8))
    palette = sns.color_palette("bright", len(np.unique(labels)))
    
    # Plot data points
    for i, label in enumerate(np.unique(labels)):
        plt.scatter(X[labels == label, 0], X[labels == label, 1],
                   label=f"Cluster {label}", alpha=0.7, color=palette[i], s=20)
    
    # Plot medoids
    plt.scatter(X[medoids, 0], X[medoids, 1], c='black', marker='x', s=100,
               label='Medoids')
    
    plt.xlabel('Feature 1')
    plt.ylabel('Feature 2')
    plt.title(title)
    plt.legend()
    
    # Save plot
    filename = 'algorithms/clustering/pam/pam-clusters.png'
    plt.savefig(filename)
    plt.close()

def plot_cost_history(cost_history: List[float], title: str = "PAM Cost History"):
    """Plot cost history during iterations"""
    # Create directory if it doesn't exist
    os.makedirs('algorithms/clustering/pam', exist_ok=True)
    
    plt.figure(figsize=(10, 6))
    plt.plot(cost_history, marker='o')
    plt.xlabel('Iteration')
    plt.ylabel('Cost')
    plt.title(title)
    
    # Save plot
    filename = 'algorithms/clustering/pam/pam-cost_history.png'
    plt.savefig(filename)
    plt.close()

# Run code sequentially
# Generate sample data
X, y_true = make_blobs(n_samples=300, centers=4, cluster_std=1.0, random_state=42)

# Scale the data
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)

# PAM implementation
print("\nTraining PAM Clustering...")
pam = PAM(n_clusters=4, max_iter=100, random_state=42)
labels = pam.fit_predict(X_scaled)

# Plot results
plot_clusters(X_scaled, labels, pam.medoids_, title="PAM Clustering Results")

# Evaluate clustering
if len(np.unique(labels)) > 1:
    score = silhouette_score(X_scaled, labels)
    print(f"Silhouette Score: {score:.3f}")

# CLARA implementation
print("\nTraining CLARA Clustering...")
clara = CLARA(n_clusters=4, n_samples=40, n_sampling=5, max_iter=100, random_state=42)
labels = clara.fit_predict(X_scaled)

# Plot results
plot_clusters(X_scaled, labels, clara.medoids_, title="CLARA Clustering Results")

# Evaluate clustering
if len(np.unique(labels)) > 1:
    score = silhouette_score(X_scaled, labels)
    print(f"Silhouette Score: {score:.3f}")

# CLARANS implementation
print("\nTraining CLARANS Clustering...")
clarans = CLARANS(n_clusters=4, num_local=2, max_neighbor=250, random_state=42)
labels = clarans.fit_predict(X_scaled)

# Plot results
plot_clusters(X_scaled, labels, clarans.medoids_, title="CLARANS Clustering Results")

# Evaluate clustering
if len(np.unique(labels)) > 1:
    score = silhouette_score(X_scaled, labels)
    print(f"Silhouette Score: {score:.3f}") 