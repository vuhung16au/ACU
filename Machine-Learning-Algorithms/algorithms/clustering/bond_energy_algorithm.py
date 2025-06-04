# %% [markdown]
# # Bond Energy Algorithm Implementation
# 
# This notebook implements the Bond Energy Algorithm (BEA) for clustering and matrix reordering.
# The algorithm works by reordering rows and columns of a matrix to maximize bond energy between adjacent elements.

"""
DO NOT DELETE THIS COMMENT

The Bond Energy Algorithm (BEA) is a specific clustering algorithm that works by reordering rows and columns of a matrix to maximize bond energy between adjacent elements. While scikit-learn and TensorFlow don't have direct implementations of BEA, we can achieve similar clustering results using other algorithms from these libraries. Here are the alternatives:

- Using scikit-learn:
    - Hierarchical Clustering (AgglomerativeClustering)
    - Spectral Clustering
    - DBSCAN
    - K-means
- Using TensorFlow/Keras:
    - Self-Organizing Maps (SOM)
    - Autoencoders for dimensionality reduction followed by clustering


"""

# %% [markdown]
# ## 1. Import Required Libraries

# %%
import numpy as np
import seaborn as sns
from sklearn.metrics import silhouette_score
import os

# Set random seed for reproducibility
np.random.seed(2220)

# Create output directory
OUTPUT_DIR = 'algorithms/clustering/bond_energy_algorithm/output'
os.makedirs(OUTPUT_DIR, exist_ok=True)

# %% [markdown]
# ## 2. Helper Functions

# %%
def compute_similarity(a: np.ndarray, b: np.ndarray, metric: str = 'binary') -> float:
    """
    Compute similarity between two vectors using specified metric
    
    Parameters:
    -----------
    a, b : np.ndarray
        Input vectors to compare
    metric : str
        Similarity metric to use ('binary', 'cosine', 'jaccard')
    
    Returns:
    --------
    float
        Similarity score between vectors
    """
    if metric == 'binary':
        return np.sum(a * b)
    elif metric == 'cosine':
        return np.dot(a, b) / (np.linalg.norm(a) * np.linalg.norm(b))
    elif metric == 'jaccard':
        intersection = np.sum(a * b)
        union = np.sum(a + b) - intersection
        return intersection / union if union > 0 else 0
    else:
        raise ValueError(f"Unknown similarity metric: {metric}")

def compute_bond_energy(matrix: np.ndarray, i: int, j: int, metric: str = 'binary') -> float:
    """
    Compute bond energy for a position in the matrix
    
    Parameters:
    -----------
    matrix : np.ndarray
        Input matrix
    i, j : int
        Position in matrix
    metric : str
        Similarity metric to use
    
    Returns:
    --------
    float
        Bond energy at position (i,j)
    """
    m, n = matrix.shape
    energy = 0
    
    # Check all four neighbors
    if i > 0:  # Up
        energy += compute_similarity(matrix[i,j], matrix[i-1,j], metric)
    if i < m-1:  # Down
        energy += compute_similarity(matrix[i,j], matrix[i+1,j], metric)
    if j > 0:  # Left
        energy += compute_similarity(matrix[i,j], matrix[i,j-1], metric)
    if j < n-1:  # Right
        energy += compute_similarity(matrix[i,j], matrix[i,j+1], metric)
        
    return energy

# %% [markdown]
# ## 3. Main Algorithm Functions

# %%
def find_best_position(matrix: np.ndarray, element: np.ndarray, 
                      current_order: list, is_row: bool, metric: str = 'binary') -> int:
    """
    Find the best position for an element in the current order
    
    Parameters:
    -----------
    matrix : np.ndarray
        Input matrix
    element : np.ndarray
        Element to position
    current_order : list
        Current ordering
    is_row : bool
        Whether element is a row (True) or column (False)
    metric : str
        Similarity metric to use
    
    Returns:
    --------
    int
        Best position for the element
    """
    best_energy = float('-inf')
    best_pos = 0
    
    for pos in range(len(current_order) + 1):
        # Create temporary order
        temp_order = current_order.copy()
        temp_order.insert(pos, len(current_order))
        
        # Create temporary matrix
        if is_row:
            temp_matrix = matrix[temp_order]
        else:
            temp_matrix = matrix[:, temp_order]
        
        # Compute total bond energy
        energy = 0
        for i in range(temp_matrix.shape[0]):
            for j in range(temp_matrix.shape[1]):
                energy += compute_bond_energy(temp_matrix, i, j, metric)
        
        if energy > best_energy:
            best_energy = energy
            best_pos = pos
            
    return best_pos

def identify_clusters(matrix: np.ndarray, threshold: float = 0.5) -> list:
    """
    Identify clusters in the reordered matrix
    
    Parameters:
    -----------
    matrix : np.ndarray
        Input matrix
    threshold : float
        Threshold for cluster formation
    
    Returns:
    --------
    list
        List of identified clusters
    """
    m, n = matrix.shape
    visited = np.zeros((m, n), dtype=bool)
    clusters = []
    
    def dfs(i: int, j: int, cluster: list):
        if (i < 0 or i >= m or j < 0 or j >= n or 
            visited[i,j] or matrix[i,j] < threshold):
            return
        
        visited[i,j] = True
        cluster.append((i, j))
        
        # Check all four neighbors
        dfs(i-1, j, cluster)
        dfs(i+1, j, cluster)
        dfs(i, j-1, cluster)
        dfs(i, j+1, cluster)
    
    # Find all clusters
    for i in range(m):
        for j in range(n):
            if not visited[i,j] and matrix[i,j] >= threshold:
                cluster = []
                dfs(i, j, cluster)
                if cluster:
                    clusters.append(cluster)
    
    return clusters

# %% [markdown]
# ## 4. Visualization Functions

# %%
def plot_matrix(matrix: np.ndarray, title: str = "Reordered Matrix", 
                save_path: str = None):
    """
    Plot the reordered matrix using seaborn
    
    Parameters:
    -----------
    matrix : np.ndarray
        Input matrix
    title : str
        Plot title
    save_path : str
        Path to save the plot
    """
    if save_path is None:
        save_path = os.path.join(OUTPUT_DIR, 'bond_energy_algorithm-matrix.png')
    
    sns.set(style="whitegrid")
    ax = sns.heatmap(matrix, cmap='viridis')
    ax.set_title(title)
    ax.figure.savefig(save_path, bbox_inches='tight', dpi=300)
    ax.figure.clear()

def plot_clusters(matrix: np.ndarray, clusters: list, 
                 save_path: str = None):
    """
    Plot the identified clusters using seaborn
    
    Parameters:
    -----------
    matrix : np.ndarray
        Input matrix
    clusters : list
        List of identified clusters
    save_path : str
        Path to save the plot
    """
    if save_path is None:
        save_path = os.path.join(OUTPUT_DIR, 'bond_energy_algorithm-clusters.png')
    
    sns.set(style="whitegrid")
    ax = sns.heatmap(matrix, cmap='viridis')
    
    # Plot cluster points
    for cluster in clusters:
        x = [j for _, j in cluster]
        y = [i for i, _ in cluster]
        ax.plot(x, y, 'r.', alpha=0.5)
    
    ax.set_title("Identified Clusters")
    ax.figure.savefig(save_path, bbox_inches='tight', dpi=300)
    ax.figure.clear()

# %% [markdown]
# ## 5. Main Algorithm Execution

# %%
# Generate sample data
matrix = np.random.rand(20, 20)
matrix = (matrix + matrix.T) / 2  # Make symmetric

# Initialize parameters
similarity_metric = 'cosine'  # Options: 'binary', 'cosine', 'jaccard'
threshold = 0.5  # Threshold for cluster formation

# Reorder rows and columns
m, n = matrix.shape
row_order = list(range(m))
col_order = list(range(n))

# Reorder rows
for i in range(1, m):
    best_pos = find_best_position(matrix, matrix[i], row_order[:i], True, similarity_metric)
    row_order.insert(best_pos, i)
    row_order.remove(i)

# Reorder columns
for j in range(1, n):
    best_pos = find_best_position(matrix, matrix[:,j], col_order[:j], False, similarity_metric)
    col_order.insert(best_pos, j)
    col_order.remove(j)

# Get reordered matrix
reordered_matrix = matrix[row_order][:, col_order]

# Identify clusters
clusters = identify_clusters(reordered_matrix, threshold)

# Plot results
plot_matrix(reordered_matrix)
plot_clusters(reordered_matrix, clusters)

# Compute silhouette score
labels = np.zeros(len(matrix), dtype=int)
for i, cluster in enumerate(clusters):
    for row, _ in cluster:
        labels[row] = i

if len(np.unique(labels)) >= 2 and len(np.unique(labels)) < len(labels):
    score = silhouette_score(matrix, labels)
    print(f"Silhouette Score: {score:.3f}")
else:
    print("Silhouette Score: Cannot compute (number of clusters is not in [2, n_samples-1])")