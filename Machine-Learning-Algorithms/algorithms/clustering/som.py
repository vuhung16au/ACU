import numpy as np
from typing import List, Tuple, Union, Optional
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.preprocessing import MinMaxScaler
from sklearn.datasets import make_blobs
import os

class SOM:
    def __init__(self, 
                 grid_size: Tuple[int, int] = (10, 10),
                 input_dim: int = 2,
                 learning_rate: float = 0.1,
                 neighborhood_radius: float = 1.0,
                 max_iter: int = 1000,
                 random_state: Optional[int] = None):
        """
        Initialize Self-Organizing Map
        
        Args:
            grid_size (Tuple[int, int]): Size of the neuron grid (rows, cols)
            input_dim (int): Dimension of input data
            learning_rate (float): Initial learning rate
            neighborhood_radius (float): Initial neighborhood radius
            max_iter (int): Maximum number of iterations
            random_state (int): Random state for reproducibility
        """
        self.grid_size = grid_size
        self.input_dim = input_dim
        self.learning_rate = learning_rate
        self.neighborhood_radius = neighborhood_radius
        self.max_iter = max_iter
        self.random_state = random_state
        
        # Initialize weights
        if random_state is not None:
            np.random.seed(random_state)
        self.weights = np.random.rand(grid_size[0], grid_size[1], input_dim)
        
        # Initialize grid coordinates
        self.grid_coords = np.array([[i, j] for i in range(grid_size[0]) 
                                   for j in range(grid_size[1])]).reshape(grid_size[0], grid_size[1], 2)
        
    def _find_bmu(self, x: np.ndarray) -> Tuple[int, int]:
        """
        Find Best Matching Unit (BMU) for input vector x
        
        Args:
            x (np.ndarray): Input vector
            
        Returns:
            Tuple[int, int]: Coordinates of BMU
        """
        # Compute distances to all neurons
        distances = np.sum((self.weights - x) ** 2, axis=2)
        return np.unravel_index(np.argmin(distances), distances.shape)
    
    def _compute_neighborhood(self, bmu_coords: Tuple[int, int], iteration: int) -> np.ndarray:
        """
        Compute neighborhood function for BMU
        
        Args:
            bmu_coords (Tuple[int, int]): Coordinates of BMU
            iteration (int): Current iteration
            
        Returns:
            np.ndarray: Neighborhood values
        """
        # Compute distances to BMU
        distances = np.sum((self.grid_coords - bmu_coords) ** 2, axis=2)
        
        # Compute neighborhood radius
        radius = self.neighborhood_radius * np.exp(-iteration / self.max_iter)
        
        # Compute neighborhood function (Gaussian)
        return np.exp(-distances / (2 * radius ** 2))
    
    def _update_weights(self, x: np.ndarray, bmu_coords: Tuple[int, int], 
                       neighborhood: np.ndarray, iteration: int):
        """
        Update neuron weights
        
        Args:
            x (np.ndarray): Input vector
            bmu_coords (Tuple[int, int]): Coordinates of BMU
            neighborhood (np.ndarray): Neighborhood values
            iteration (int): Current iteration
        """
        # Compute learning rate
        lr = self.learning_rate * np.exp(-iteration / self.max_iter)
        
        # Update weights
        for i in range(self.grid_size[0]):
            for j in range(self.grid_size[1]):
                self.weights[i, j] += lr * neighborhood[i, j] * (x - self.weights[i, j])
    
    def fit(self, X: np.ndarray) -> 'SOM':
        """
        Fit the SOM on the data
        
        Args:
            X (np.ndarray): Training data of shape (n_samples, n_features)
            
        Returns:
            self: The fitted model
        """
        # Normalize input data
        scaler = MinMaxScaler()
        X = scaler.fit_transform(X)
        
        # Training loop
        for iteration in range(self.max_iter):
            # Randomly select input vector
            idx = np.random.randint(len(X))
            x = X[idx]
            
            # Find BMU
            bmu_coords = self._find_bmu(x)
            
            # Compute neighborhood
            neighborhood = self._compute_neighborhood(bmu_coords, iteration)
            
            # Update weights
            self._update_weights(x, bmu_coords, neighborhood, iteration)
            
        return self
    
    def predict(self, X: np.ndarray) -> np.ndarray:
        """
        Predict cluster labels for new data
        
        Args:
            X (np.ndarray): New data of shape (n_samples, n_features)
            
        Returns:
            np.ndarray: Cluster labels
        """
        # Normalize input data
        scaler = MinMaxScaler()
        X = scaler.fit_transform(X)
        
        # Find BMU for each input vector
        labels = np.zeros(len(X), dtype=int)
        for i, x in enumerate(X):
            bmu_coords = self._find_bmu(x)
            labels[i] = bmu_coords[0] * self.grid_size[1] + bmu_coords[1]
            
        return labels
    
    def plot_weights(self, feature_names: Optional[List[str]] = None):
        """
        Plot the weight vectors and save to PNG file.
        
        Args:
            feature_names (List[str]): Names of features
        """
        # Create directory if it doesn't exist
        os.makedirs('algorithms/clustering/som', exist_ok=True)
        
        if feature_names is None:
            feature_names = [f'Feature {i}' for i in range(self.input_dim)]
            
        fig, axes = plt.subplots(1, self.input_dim, figsize=(5*self.input_dim, 5))
        if self.input_dim == 1:
            axes = [axes]
            
        for i, ax in enumerate(axes):
            sns.heatmap(self.weights[:, :, i], ax=ax, cmap='viridis')
            ax.set_title(feature_names[i])
            
        plt.tight_layout()
        
        # Save plot
        filename = 'algorithms/clustering/som/som-weight_vectors.png'
        plt.savefig(filename)
        plt.close()
    
    def plot_umat(self):
        """
        Plot the U-matrix (unified distance matrix) and save to PNG file.
        """
        # Create directory if it doesn't exist
        os.makedirs('algorithms/clustering/som', exist_ok=True)
        
        # Compute U-matrix
        umat = np.zeros(self.grid_size)
        for i in range(self.grid_size[0]):
            for j in range(self.grid_size[1]):
                # Get neighbors
                neighbors = []
                if i > 0:
                    neighbors.append(self.weights[i-1, j])
                if i < self.grid_size[0]-1:
                    neighbors.append(self.weights[i+1, j])
                if j > 0:
                    neighbors.append(self.weights[i, j-1])
                if j < self.grid_size[1]-1:
                    neighbors.append(self.weights[i, j+1])
                    
                # Compute average distance to neighbors
                if neighbors:
                    umat[i, j] = np.mean([np.linalg.norm(self.weights[i, j] - n) for n in neighbors])
        
        # Plot U-matrix
        plt.figure(figsize=(10, 8))
        sns.heatmap(umat, cmap='viridis')
        plt.title('U-Matrix')
        
        # Save plot
        filename = 'algorithms/clustering/som/som-u_matrix.png'
        plt.savefig(filename)
        plt.close()

    def plot_clusters(self, X: np.ndarray, labels: np.ndarray):
        """
        Plot clustering results and save to PNG file.
        
        Args:
            X (np.ndarray): Input data
            labels (np.ndarray): Cluster labels
        """
        # Create directory if it doesn't exist
        os.makedirs('algorithms/clustering/som', exist_ok=True)
        
        # Create plot
        plt.figure(figsize=(10, 7))
        palette = sns.color_palette("bright", len(np.unique(labels)))
        for i, label in enumerate(np.unique(labels)):
            plt.scatter(X[labels == label, 0], X[labels == label, 1],
                       label=f"Cluster {label}", alpha=0.7, color=palette[i], s=20)
        plt.title('SOM Clustering Results')
        plt.legend()
        
        # Save plot
        filename = 'algorithms/clustering/som/som-clustering_results.png'
        plt.savefig(filename)
        plt.close()

    def plot_learning_process(self, X: np.ndarray, n_samples: int = 100):
        """
        Plot the learning process and save to PNG file.
        
        Args:
            X (np.ndarray): Input data
            n_samples (int): Number of samples to plot
        """
        # Create directory if it doesn't exist
        os.makedirs('algorithms/clustering/som', exist_ok=True)
        
        # Normalize input data
        scaler = MinMaxScaler()
        X = scaler.fit_transform(X)
        
        # Plot initial state
        plt.figure(figsize=(10, 7))
        plt.scatter(X[:n_samples, 0], X[:n_samples, 1], c='blue', alpha=0.5, label='Data')
        plt.scatter(self.weights[:, :, 0].flatten(), self.weights[:, :, 1].flatten(),
                   c='red', marker='x', label='Neurons')
        plt.title('Initial State')
        plt.legend()
        filename = 'algorithms/clustering/som/som-initial_state.png'
        plt.savefig(filename)
        plt.close()
        
        # Plot final state
        plt.figure(figsize=(10, 7))
        plt.scatter(X[:n_samples, 0], X[:n_samples, 1], c='blue', alpha=0.5, label='Data')
        plt.scatter(self.weights[:, :, 0].flatten(), self.weights[:, :, 1].flatten(),
                   c='red', marker='x', label='Neurons')
        plt.title('Final State')
        plt.legend()
        filename = 'algorithms/clustering/som/som-final_state.png'
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

# Run code sequentially
# Generate sample data
X, _ = make_blobs(n_samples=300, centers=4, cluster_std=0.60, random_state=42)

# Create and fit the model
print("\nTraining Self-Organizing Map...")
model = SOM(grid_size=(10, 10), input_dim=2, learning_rate=0.1, 
           neighborhood_radius=1.0, max_iter=1000, random_state=42)
model.fit(X)

# Get cluster labels
labels = model.predict(X)

# Plot results
model.plot_weights()
model.plot_umat()
model.plot_clusters(X, labels)

# Compute silhouette score
score = compute_silhouette_score(X, labels)
print(f"Silhouette Score: {score:.3f}")