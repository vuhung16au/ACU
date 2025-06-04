#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
CACTUS (CAtegorical ClusTering Using Summaries) clustering algorithm implementation.
Includes custom implementation, visualization, and evaluation tools.
"""

import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from typing import List, Dict, Set, Tuple, Optional
from sklearn.base import BaseEstimator, ClusterMixin
from collections import defaultdict
import os

# Set random seed for reproducibility
np.random.seed(2220)

# %% [markdown]
# ## 1. Helper Functions

# %%
def compute_summaries(X: pd.DataFrame, min_support: float = 0.1, noise_threshold: float = 0.05) -> Dict[str, Dict[str, float]]:
    """
    Compute support for each attribute-value pair.
    
    Parameters:
    -----------
    X : DataFrame
        Input data.
    min_support : float, default=0.1
        Minimum support threshold for attribute-value pairs.
    noise_threshold : float, default=0.05
        Minimum support threshold for noise filtering.
            
    Returns:
    --------
    summaries : dict
        Dictionary of attribute-value support.
    """
    summaries = {}
    n_samples = len(X)
    
    for column in X.columns:
        value_counts = X[column].value_counts()
        summaries[column] = {
            value: count / n_samples
            for value, count in value_counts.items()
            if count / n_samples >= noise_threshold
        }
        
    return summaries

def compute_cluster_support(cluster: Set[Tuple[str, str]], X: pd.DataFrame) -> float:
    """
    Compute support for a cluster.
    
    Parameters:
    -----------
    cluster : set
        Set of attribute-value pairs.
    X : DataFrame
        Input data.
            
    Returns:
    --------
    support : float
        Support value for the cluster.
    """
    mask = pd.Series(True, index=X.index)
    for attr, value in cluster:
        mask &= (X[attr] == value)
    return mask.mean()

# %% [markdown]
# ## 2. Core Clustering Functions

# %%
def generate_clusters(X: pd.DataFrame, attribute_summaries: Dict[str, Dict[str, float]], 
                     min_support: float = 0.1, max_cluster_size: int = 10) -> List[Set[Tuple[str, str]]]:
    """
    Generate initial clusters based on attribute summaries.
    
    Parameters:
    -----------
    X : DataFrame
        Input data.
    attribute_summaries : dict
        Dictionary of attribute-value support.
    min_support : float, default=0.1
        Minimum support threshold for attribute-value pairs.
    max_cluster_size : int, default=10
        Maximum number of attribute-value pairs in a cluster.
            
    Returns:
    --------
    clusters : list
        List of sets containing attribute-value pairs.
    """
    clusters = []
    n_samples = len(X)
    
    # Generate 1-item clusters
    for attr, values in attribute_summaries.items():
        for value, support in values.items():
            if support >= min_support:
                clusters.append({(attr, value)})
    
    # Generate larger clusters
    current_size = 1
    while current_size < max_cluster_size:
        new_clusters = []
        for c1 in clusters:
            for c2 in clusters:
                if len(c1.union(c2)) == current_size + 1:
                    # Check support of combined cluster
                    support = compute_cluster_support(c1.union(c2), X)
                    if support >= min_support:
                        new_clusters.append(c1.union(c2))
        
        if not new_clusters:
            break
            
        clusters.extend(new_clusters)
        current_size += 1
        
    return clusters

def refine_clusters(clusters: List[Set[Tuple[str, str]]], X: pd.DataFrame, 
                   min_confidence: float = 0.5) -> List[Set[Tuple[str, str]]]:
    """
    Refine clusters using confidence measures.
    
    Parameters:
    -----------
    clusters : list
        List of initial clusters.
    X : DataFrame
        Input data.
    min_confidence : float, default=0.5
        Minimum confidence threshold for cluster refinement.
            
    Returns:
    --------
    refined_clusters : list
        List of refined clusters.
    """
    refined_clusters = []
    
    for cluster in clusters:
        if len(cluster) == 1:
            refined_clusters.append(cluster)
            continue
            
        # Check confidence between all pairs of attribute-value pairs
        valid_cluster = True
        for (attr1, val1), (attr2, val2) in zip(cluster, list(cluster)[1:]):
            c1 = {(attr1, val1)}
            c2 = {(attr2, val2)}
            
            support_union = compute_cluster_support(c1.union(c2), X)
            support_c1 = compute_cluster_support(c1, X)
            
            confidence = support_union / support_c1 if support_c1 > 0 else 0
            
            if confidence < min_confidence:
                valid_cluster = False
                break
        
        if valid_cluster:
            refined_clusters.append(cluster)
            
    return refined_clusters

def assign_labels(X: pd.DataFrame, clusters: List[Set[Tuple[str, str]]]) -> np.ndarray:
    """
    Assign cluster labels to data points.
    
    Parameters:
    -----------
    X : DataFrame
        Input data.
    clusters : list
        List of clusters.
            
    Returns:
    --------
    labels : ndarray
        Cluster labels for each point.
    """
    labels = np.full(len(X), -1)
    
    for i, cluster in enumerate(clusters):
        mask = pd.Series(True, index=X.index)
        for attr, value in cluster:
            mask &= (X[attr] == value)
        labels[mask] = i
        
    return labels

# %% [markdown]
# ## 3. Main Clustering Function

# %%
def fit_cactus(X: pd.DataFrame, min_support: float = 0.1, min_confidence: float = 0.5,
               max_cluster_size: int = 10, noise_threshold: float = 0.05) -> Tuple[np.ndarray, List[Set[Tuple[str, str]]]]:
    """
    Perform CACTUS clustering from categorical data.
    
    Parameters:
    -----------
    X : DataFrame of shape (n_samples, n_features)
        Training instances to cluster. All features must be categorical.
    min_support : float, default=0.1
        Minimum support threshold for attribute-value pairs.
    min_confidence : float, default=0.5
        Minimum confidence threshold for cluster refinement.
    max_cluster_size : int, default=10
        Maximum number of attribute-value pairs in a cluster.
    noise_threshold : float, default=0.05
        Minimum support threshold for noise filtering.
            
    Returns:
    --------
    labels : ndarray
        Cluster labels for each point.
    clusters : list
        List of refined clusters.
    """
    # Validate input
    if not isinstance(X, pd.DataFrame):
        raise TypeError("Input must be a pandas DataFrame")
        
    # Compute attribute summaries
    attribute_summaries = compute_summaries(X, min_support, noise_threshold)
    
    # Generate initial clusters
    initial_clusters = generate_clusters(X, attribute_summaries, min_support, max_cluster_size)
    
    # Refine clusters
    refined_clusters = refine_clusters(initial_clusters, X, min_confidence)
    
    # Assign labels
    labels = assign_labels(X, refined_clusters)
    
    return labels, refined_clusters

# %% [markdown]
# ## 4. Visualization Functions

# %%
def plot_cluster_distribution(labels: np.ndarray, title: str = "Cluster Distribution"):
    """
    Plot the distribution of data points across clusters using seaborn.
    
    Parameters:
    -----------
    labels : ndarray
        Cluster labels for each point.
    title : str, default="Cluster Distribution"
        Title for the plot.
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/clustering/cactus', exist_ok=True)
    
    # Create figure with seaborn style
    plt.figure(figsize=(10, 6))
    sns.set_style("whitegrid")
    
    # Count points in each cluster
    cluster_counts = pd.Series(labels).value_counts().sort_index()
    
    # Create bar plot
    sns.barplot(x=cluster_counts.index, y=cluster_counts.values)
    
    plt.title(title)
    plt.xlabel('Cluster')
    plt.ylabel('Number of Points')
    
    # Save plot
    filename = 'algorithms/clustering/cactus/cactus-cluster-distribution.png'
    plt.savefig(filename, dpi=300, bbox_inches='tight')
    plt.close()

def plot_attribute_distribution(X: pd.DataFrame, labels: np.ndarray, attribute: str,
                              title: str = None):
    """
    Plot the distribution of an attribute across clusters using seaborn.
    
    Parameters:
    -----------
    X : DataFrame
        Input data.
    labels : ndarray
        Cluster labels for each point.
    attribute : str
        Name of the attribute to plot.
    title : str, default=None
        Title for the plot.
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/clustering/cactus', exist_ok=True)
    
    # Create figure with seaborn style
    plt.figure(figsize=(12, 6))
    sns.set_style("whitegrid")
    
    # Create DataFrame for plotting
    plot_data = pd.DataFrame({
        'Cluster': labels,
        attribute: X[attribute]
    })
    
    # Create count plot
    sns.countplot(data=plot_data, x='Cluster', hue=attribute)
    
    if title is None:
        title = f'Distribution of {attribute} Across Clusters'
    plt.title(title)
    plt.xlabel('Cluster')
    plt.ylabel('Count')
    plt.legend(title=attribute, bbox_to_anchor=(1.05, 1), loc='upper left')
    
    # Save plot
    filename = f'algorithms/clustering/cactus/cactus-{attribute}-distribution.png'
    plt.savefig(filename, dpi=300, bbox_inches='tight')
    plt.close()

def plot_cluster_heatmap(X: pd.DataFrame, labels: np.ndarray, title: str = "Cluster Attribute Distribution"):
    """
    Plot a heatmap showing the distribution of attributes across clusters using seaborn.
    
    Parameters:
    -----------
    X : DataFrame
        Input data.
    labels : ndarray
        Cluster labels for each point.
    title : str, default="Cluster Attribute Distribution"
        Title for the plot.
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/clustering/cactus', exist_ok=True)
    
    # Create figure with seaborn style
    plt.figure(figsize=(12, 8))
    sns.set_style("whitegrid")
    
    # Compute attribute distributions for each cluster
    cluster_attr_dist = pd.DataFrame()
    for cluster in np.unique(labels):
        cluster_data = X[labels == cluster]
        for col in X.columns:
            value_counts = cluster_data[col].value_counts(normalize=True)
            cluster_attr_dist.loc[cluster, col] = value_counts.max()
    
    # Create heatmap
    sns.heatmap(cluster_attr_dist, annot=True, cmap='YlOrRd', fmt='.2f')
    
    plt.title(title)
    plt.xlabel('Attribute')
    plt.ylabel('Cluster')
    
    # Save plot
    filename = 'algorithms/clustering/cactus/cactus-cluster-heatmap.png'
    plt.savefig(filename, dpi=300, bbox_inches='tight')
    plt.close() 