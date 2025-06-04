#!/usr/bin/env python3
# -*- coding: utf-8 -*-

## CHAMELEON (Hierarchical Clustering Using Dynamic Modeling)
## This implementation provides a function-based approach to CHAMELEON clustering algorithm
## with visualization and evaluation capabilities.

## Import required libraries
import numpy as np
import seaborn as sns
import matplotlib.pyplot as plt
import networkx as nx
from sklearn.datasets import make_moons, make_blobs
from sklearn.preprocessing import StandardScaler
from sklearn.metrics import silhouette_score
from scipy.spatial.distance import pdist, squareform
import os

## Set random seed for reproducibility
np.random.seed(2220)

## Helper Functions

def build_knn_graph(X, k=10):
    """
    Build k-nearest neighbor graph from input data
    
    Parameters:
    -----------
    X : array-like
        Input data matrix
    k : int, default=10
        Number of nearest neighbors to consider
        - Higher k values create denser graphs
        - Lower k values create sparser graphs
        - Typical values range from 5 to 20
    """
    n_samples = X.shape[0]
    distances = squareform(pdist(X))
    
    G = nx.Graph()
    for i in range(n_samples):
        G.add_node(i)
        
    for i in range(n_samples):
        neighbors = np.argsort(distances[i])[:k + 1]
        for j in neighbors:
            if i != j:
                G.add_edge(i, j, weight=distances[i][j])
    
    return G

def compute_relative_interconnectivity(cluster1, cluster2, graph):
    """
    Compute relative interconnectivity between two clusters
    
    Parameters:
    -----------
    cluster1, cluster2 : list
        Lists of node indices representing clusters
    graph : networkx.Graph
        The k-nearest neighbor graph
    """
    edges = []
    for node1 in cluster1:
        for node2 in cluster2:
            if graph.has_edge(node1, node2):
                edges.append(graph[node1][node2]['weight'])
    
    if not edges:
        return 0.0
        
    internal_edges1 = []
    for node1 in cluster1:
        for node2 in cluster1:
            if node1 < node2 and graph.has_edge(node1, node2):
                internal_edges1.append(graph[node1][node2]['weight'])
                
    internal_edges2 = []
    for node1 in cluster2:
        for node2 in cluster2:
            if node1 < node2 and graph.has_edge(node1, node2):
                internal_edges2.append(graph[node1][node2]['weight'])
    
    if not internal_edges1 or not internal_edges2:
        return 0.0
        
    return len(edges) / (len(internal_edges1) + len(internal_edges2))

def compute_relative_closeness(cluster1, cluster2, graph):
    """
    Compute relative closeness between two clusters
    
    Parameters:
    -----------
    cluster1, cluster2 : list
        Lists of node indices representing clusters
    graph : networkx.Graph
        The k-nearest neighbor graph
    """
    edges = []
    for node1 in cluster1:
        for node2 in cluster2:
            if graph.has_edge(node1, node2):
                edges.append(graph[node1][node2]['weight'])
    
    if not edges:
        return 0.0
        
    internal_edges1 = []
    for node1 in cluster1:
        for node2 in cluster1:
            if node1 < node2 and graph.has_edge(node1, node2):
                internal_edges1.append(graph[node1][node2]['weight'])
                
    internal_edges2 = []
    for node1 in cluster2:
        for node2 in cluster2:
            if node1 < node2 and graph.has_edge(node1, node2):
                internal_edges2.append(graph[node1][node2]['weight'])
    
    if not internal_edges1 or not internal_edges2:
        return 0.0
        
    avg_edge = np.mean(edges)
    avg_internal1 = np.mean(internal_edges1)
    avg_internal2 = np.mean(internal_edges2)
    
    return avg_edge / ((avg_internal1 + avg_internal2) / 2)

def merge_clusters(clusters, graph, alpha=2.0, beta=2.0):
    """
    Merge clusters based on dynamic modeling
    
    Parameters:
    -----------
    clusters : list
        List of clusters to merge
    graph : networkx.Graph
        The k-nearest neighbor graph
    alpha : float, default=2.0
        Interconnectivity threshold
        - Higher values require stronger connections between clusters
        - Lower values allow more clusters to merge
        - Typical range: 1.0 to 3.0
    beta : float, default=2.0
        Closeness threshold
        - Higher values require closer clusters to merge
        - Lower values allow more distant clusters to merge
        - Typical range: 1.0 to 3.0
    """
    while len(clusters) > 1:
        best_score = -float('inf')
        best_pair = None
        
        for i in range(len(clusters)):
            for j in range(i + 1, len(clusters)):
                ri = compute_relative_interconnectivity(clusters[i], clusters[j], graph)
                rc = compute_relative_closeness(clusters[i], clusters[j], graph)
                
                if ri > alpha and rc > beta:
                    score = ri * rc
                    if score > best_score:
                        best_score = score
                        best_pair = (i, j)
        
        if best_pair is None:
            break
            
        i, j = best_pair
        clusters[i].extend(clusters[j])
        del clusters[j]
    
    return clusters

def chameleon_clustering(X, k=10, alpha=2.0, beta=2.0):
    """
    Perform CHAMELEON clustering on input data
    
    Parameters:
    -----------
    X : array-like
        Input data matrix
    k : int, default=10
        Number of nearest neighbors
        - Controls the density of the k-nearest neighbor graph
        - Higher values create more connections
        - Typical range: 5 to 20
    alpha : float, default=2.0
        Interconnectivity threshold
        - Controls how strongly connected clusters need to be to merge
        - Higher values create more distinct clusters
        - Typical range: 1.0 to 3.0
    beta : float, default=2.0
        Closeness threshold
        - Controls how close clusters need to be to merge
        - Higher values create more compact clusters
        - Typical range: 1.0 to 3.0
    """
    n_samples = X.shape[0]
    graph = build_knn_graph(X, k)
    clusters = [[i] for i in range(n_samples)]
    final_clusters = merge_clusters(clusters, graph, alpha, beta)
    
    labels = np.zeros(n_samples, dtype=int)
    for i, cluster in enumerate(final_clusters):
        for node in cluster:
            labels[node] = i
    
    return labels

def plot_clusters(X, labels, title="CHAMELEON Clustering Results"):
    """
    Plot clustering results using seaborn
    
    Parameters:
    -----------
    X : array-like
        Input data matrix
    labels : array-like
        Cluster labels
    title : str
        Plot title
    """
    os.makedirs('algorithms/clustering/chameleon', exist_ok=True)
    
    # Create a DataFrame for seaborn
    import pandas as pd
    df = pd.DataFrame({
        'Feature 1': X[:, 0],
        'Feature 2': X[:, 1],
        'Cluster': labels
    })
    
    # Create the plot
    plt.figure(figsize=(10, 8))
    sns.scatterplot(data=df, x='Feature 1', y='Feature 2', hue='Cluster', palette='bright')
    plt.title(title)
    
    # Save the plot
    filename = 'algorithms/clustering/chameleon/chameleon-clusters.png'
    plt.savefig(filename)
    plt.close()

def evaluate_clustering(X, labels):
    """
    Evaluate clustering using silhouette score
    
    Parameters:
    -----------
    X : array-like
        Input data matrix
    labels : array-like
        Cluster labels
    """
    n_clusters = len(np.unique(labels))
    n_samples = len(labels)
    if n_clusters >= 2 and n_clusters < n_samples:
        score = silhouette_score(X, labels)
        print(f"Silhouette Score: {score:.3f}")
        return score
    print("Silhouette Score: Cannot compute (number of clusters is not in [2, n_samples-1])")
    return None

## Generate and process sample data
X1, _ = make_blobs(n_samples=100, centers=1, cluster_std=0.5, random_state=42)
X2, _ = make_moons(n_samples=100, noise=0.1, random_state=42)
if isinstance(X1, tuple):
    X1 = X1[0]
if isinstance(X2, tuple):
    X2 = X2[0]
X = np.vstack([X1, X2])

## Scale the data
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)

## Perform CHAMELEON clustering
print("\nTraining CHAMELEON Clustering...")
labels = chameleon_clustering(X_scaled, k=10, alpha=2.0, beta=2.0)

## Plot and evaluate results
plot_clusters(X_scaled, labels, title="CHAMELEON Clustering Results")
evaluate_clustering(X_scaled, labels)