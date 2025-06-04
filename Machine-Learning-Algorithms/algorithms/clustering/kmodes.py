import numpy as np
from typing import List, Tuple, Union, Optional
from collections import Counter
import matplotlib.pyplot as plt
import seaborn as sns
import os
from sklearn.metrics import silhouette_score

# Set matplotlib to non-interactive backend
plt.switch_backend('agg')

class KModes:
    def __init__(self, n_clusters: int = 2, max_iter: int = 100, random_state: Optional[int] = None):
        """
        Initialize K-Modes clustering
        
        Args:
            n_clusters (int): Number of clusters to form
            max_iter (int): Maximum number of iterations
            random_state (int): Random state for reproducibility
        """
        self.n_clusters = n_clusters
        self.max_iter = max_iter
        self.random_state = random_state
        self.labels_ = None
        self.cluster_centers_ = None
        self.cost_ = None
        
    def _compute_dissimilarity(self, x: np.ndarray, y: np.ndarray) -> int:
        """
        Compute dissimilarity between two categorical objects
        
        Args:
            x (np.ndarray): First object
            y (np.ndarray): Second object
            
        Returns:
            int: Dissimilarity score
        """
        return np.sum(x != y)
    
    def _compute_mode(self, X: np.ndarray) -> np.ndarray:
        """
        Compute the mode of each attribute
        
        Args:
            X (np.ndarray): Input data
            
        Returns:
            np.ndarray: Mode of each attribute
        """
        return np.array([Counter(col).most_common(1)[0][0] for col in X.T])
    
    def _initialize_centers(self, X: np.ndarray) -> np.ndarray:
        """
        Initialize cluster centers using random selection
        
        Args:
            X (np.ndarray): Input data
            
        Returns:
            np.ndarray: Initial cluster centers
        """
        if self.random_state is not None:
            np.random.seed(self.random_state)
            
        n_samples = X.shape[0]
        indices = np.random.choice(n_samples, self.n_clusters, replace=False)
        return X[indices]
    
    def _assign_clusters(self, X: np.ndarray, centers: np.ndarray) -> Tuple[np.ndarray, float]:
        """
        Assign objects to clusters
        
        Args:
            X (np.ndarray): Input data
            centers (np.ndarray): Cluster centers
            
        Returns:
            Tuple[np.ndarray, float]: Cluster assignments and cost
        """
        n_samples = X.shape[0]
        labels = np.zeros(n_samples, dtype=int)
        cost = 0
        
        for i in range(n_samples):
            # Compute dissimilarities to all centers
            dissimilarities = [self._compute_dissimilarity(X[i], center) for center in centers]
            # Assign to nearest center
            labels[i] = np.argmin(dissimilarities)
            cost += min(dissimilarities)
            
        return labels, cost
    
    def _update_centers(self, X: np.ndarray, labels: np.ndarray) -> np.ndarray:
        """
        Update cluster centers
        
        Args:
            X (np.ndarray): Input data
            labels (np.ndarray): Cluster assignments
            
        Returns:
            np.ndarray: Updated cluster centers
        """
        centers = np.zeros((self.n_clusters, X.shape[1]), dtype=X.dtype)
        
        for k in range(self.n_clusters):
            if np.sum(labels == k) > 0:
                centers[k] = self._compute_mode(X[labels == k])
                
        return centers
    
    def fit(self, X: np.ndarray) -> 'KModes':
        """
        Fit the K-Modes clustering on the data
        
        Args:
            X (np.ndarray): Training data of shape (n_samples, n_features)
            
        Returns:
            self: The fitted model
        """
        # Initialize centers
        centers = self._initialize_centers(X)
        
        # Initialize variables
        old_cost = float('inf')
        
        # Main loop
        for iteration in range(self.max_iter):
            # Assign clusters
            labels, cost = self._assign_clusters(X, centers)
            
            # Check convergence
            if abs(old_cost - cost) < 1e-6:
                break
                
            # Update centers
            centers = self._update_centers(X, labels)
            old_cost = cost
            
        # Store results
        self.labels_ = labels
        self.cluster_centers_ = centers
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
    
    def predict(self, X: np.ndarray) -> np.ndarray:
        """
        Predict cluster labels for new data
        
        Args:
            X (np.ndarray): New data of shape (n_samples, n_features)
            
        Returns:
            np.ndarray: Cluster labels
        """
        if self.cluster_centers_ is None:
            raise ValueError("Model has not been fitted yet. Call fit() first.")
            
        labels, _ = self._assign_clusters(X, self.cluster_centers_)
        return labels

def compute_silhouette_score(X: np.ndarray, labels: np.ndarray) -> float:
    """
    Compute the silhouette score for clustering evaluation
    
    Args:
        X (np.ndarray): Input data
        labels (np.ndarray): Cluster labels
        
    Returns:
        float: Silhouette score
    """
    return silhouette_score(X, labels)

def plot_clusters(X: np.ndarray, labels: np.ndarray, centers: np.ndarray, title: str = "K-Modes Clustering Results"):
    """Plot clusters and centers"""
    # Create directory if it doesn't exist
    os.makedirs('algorithms/clustering/kmodes', exist_ok=True)
    
    plt.figure(figsize=(10, 8))
    palette = sns.color_palette("bright", len(np.unique(labels)))
    
    # Plot data points
    for i, label in enumerate(np.unique(labels)):
        plt.scatter(X[labels == label, 0], X[labels == label, 1],
                   label=f"Cluster {label}", alpha=0.7, color=palette[i], s=20)
    
    # Plot centers
    plt.scatter(centers[:, 0], centers[:, 1], c='black', marker='x', s=100,
               label='Centers')
    
    plt.xlabel('Feature 1')
    plt.ylabel('Feature 2')
    plt.title(title)
    plt.legend()
    
    # Save plot
    filename = 'algorithms/clustering/kmodes/kmodes-clusters.png'
    plt.savefig(filename)
    plt.close()

def plot_cost_history(cost_history: List[float], title: str = "K-Modes Cost History"):
    """Plot cost history during iterations"""
    # Create directory if it doesn't exist
    os.makedirs('algorithms/clustering/kmodes', exist_ok=True)
    
    plt.figure(figsize=(10, 6))
    plt.plot(cost_history, marker='o')
    plt.xlabel('Iteration')
    plt.ylabel('Cost')
    plt.title(title)
    
    # Save plot
    filename = 'algorithms/clustering/kmodes/kmodes-cost_history.png'
    plt.savefig(filename)
    plt.close()

# Run code sequentially
# Generate sample categorical data
n_samples = 300
n_features = 2
n_categories = 5

X = np.random.randint(0, n_categories, size=(n_samples, n_features))

# Create and fit the model
print("\nTraining K-Modes Clustering...")
model = KModes(n_clusters=4, random_state=42)
labels = model.fit_predict(X)

# Print results
print(f"Number of clusters: {model.n_clusters}")
print(f"Final cost: {model.cost_:.2f}")
print(f"Cluster centers:\n{model.cluster_centers_}")

# Compute silhouette score
if len(np.unique(labels)) > 1:
    score = silhouette_score(X, labels)
    print(f"Silhouette Score: {score:.3f}")

# Plot results
plot_clusters(X, labels, model.cluster_centers_, title="K-Modes Clustering Results") 