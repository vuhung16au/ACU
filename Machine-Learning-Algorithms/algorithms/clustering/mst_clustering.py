import numpy as np
from typing import List, Tuple
from scipy.spatial.distance import pdist, squareform
from scipy.sparse.csgraph import minimum_spanning_tree
import matplotlib.pyplot as plt
import seaborn as sns
import os
from sklearn.metrics import silhouette_score
from sklearn.datasets import make_blobs

def compute_distances(X: np.ndarray, distance_metric: str = 'euclidean') -> np.ndarray:
    """Compute pairwise distances between all points"""
    return squareform(pdist(X, metric=distance_metric))  # type: ignore

def construct_mst(distances: np.ndarray) -> np.ndarray:
    """Construct minimum spanning tree using Kruskal's algorithm"""
    return minimum_spanning_tree(distances).toarray()

def get_edge_weights(mst: np.ndarray) -> List[Tuple[int, int, float]]:
    """Get list of edges with their weights"""
    edges = []
    n = mst.shape[0]
    for i in range(n):
        for j in range(i+1, n):
            if mst[i,j] > 0:
                edges.append((i, j, mst[i,j]))
    return sorted(edges, key=lambda x: x[2], reverse=True)

def find_connected_components(mst: np.ndarray) -> List[List[int]]:
    """Find connected components in the MST"""
    n = mst.shape[0]
    visited = [False] * n
    components = []
    def dfs(node: int, component: List[int]):
        visited[node] = True
        component.append(node)
        for i in range(n):
            if not visited[i] and (mst[node,i] > 0 or mst[i,node] > 0):
                dfs(i, component)
    for i in range(n):
        if not visited[i]:
            component = []
            dfs(i, component)
            components.append(component)
    return components

def mst_clustering(X: np.ndarray, n_clusters: int = 2, distance_metric: str = 'euclidean'):
    distances = compute_distances(X, distance_metric)
    mst = construct_mst(distances)
    edge_weights = get_edge_weights(mst)
    # Remove k-1 longest edges
    for i in range(n_clusters - 1):
        if i < len(edge_weights):
            u, v, _ = edge_weights[i]
            mst[u,v] = 0
            mst[v,u] = 0
    components = find_connected_components(mst)
    labels = np.zeros(len(X), dtype=int)
    for i, component in enumerate(components):
        labels[component] = i
    return labels, mst, edge_weights

def plot_mst(X: np.ndarray, labels: np.ndarray, mst: np.ndarray):
    os.makedirs('algorithms/clustering/mst', exist_ok=True)
    plt.figure(figsize=(10, 8))
    palette = sns.color_palette("bright", len(np.unique(labels)))
    for i, label in enumerate(np.unique(labels)):
        plt.scatter(X[labels == label, 0], X[labels == label, 1],
                   label=f"Cluster {label}", alpha=0.7, color=palette[i], s=20)
    n = mst.shape[0]
    for i in range(n):
        for j in range(i+1, n):
            if mst[i,j] > 0:
                plt.plot([X[i,0], X[j,0]], [X[i,1], X[j,1]], 'k-', alpha=0.5)
    plt.title('MST Clustering Results')
    plt.legend()
    filename = 'algorithms/clustering/mst/mst-clusters.png'
    plt.savefig(filename)
    plt.close()

def plot_dendrogram(edge_weights: List[Tuple[int, int, float]], n_clusters: int):
    if edge_weights is None:
        raise ValueError("Model has not been fitted yet. Run clustering first.")
    os.makedirs('algorithms/clustering/mst', exist_ok=True)
    weights = [w for _, _, w in edge_weights]
    plt.figure(figsize=(10, 6))
    plt.plot(range(len(weights)), weights, 'b-')
    if n_clusters > 1 and len(weights) >= n_clusters-1:
        plt.axhline(y=weights[n_clusters-2], color='r', linestyle='--',
                   label=f'Cut for {n_clusters} clusters')
    plt.title('MST Edge Weights')
    plt.xlabel('Edge Index')
    plt.ylabel('Edge Weight')
    plt.legend()
    filename = 'algorithms/clustering/mst/mst-dendrogram.png'
    plt.savefig(filename)
    plt.close()

def compute_silhouette_score_func(X: np.ndarray, labels: np.ndarray) -> float:
    return float(silhouette_score(X, labels))

# Run code sequentially
X, _ = make_blobs(n_samples=100, centers=3, random_state=42)[:2]
print("\nTraining MST Clustering...")
labels, mst, edge_weights = mst_clustering(X, n_clusters=3)
plot_mst(X, labels, mst)
plot_dendrogram(edge_weights, n_clusters=3)
if len(np.unique(labels)) > 1:
    score = float(silhouette_score(X, labels))
    print(f"Silhouette Score: {score:.3f}")
n_clusters_range = range(2, 6)
scores = []
for n_clusters in n_clusters_range:
    labels, mst, edge_weights = mst_clustering(X, n_clusters=n_clusters)
    score = compute_silhouette_score_func(X, labels)
    scores.append(score)
plt.figure(figsize=(10, 7))
plt.plot(list(n_clusters_range), scores, 'bo-')
plt.xlabel('Number of Clusters')
plt.ylabel('Silhouette Score')
plt.title('Silhouette Score vs Number of Clusters')
plt.savefig('algorithms/clustering/mst/silhouette-vs-nclusters.png')
plt.close()