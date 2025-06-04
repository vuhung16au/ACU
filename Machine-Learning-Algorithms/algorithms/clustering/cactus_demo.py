#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Demo script for CACTUS clustering algorithm.
"""

import numpy as np
import pandas as pd
from cactus import fit_cactus, plot_cluster_distribution, plot_attribute_distribution, plot_cluster_heatmap

# Set random seed for reproducibility
np.random.seed(2220)

# %% [markdown]
# ## 1. Generate Sample Data

# %%
# Generate sample categorical data
n_samples = 1000
n_features = 4

# Create sample data with some patterns
data = {
    'color': np.random.choice(['red', 'blue', 'green'], size=n_samples),
    'size': np.random.choice(['small', 'medium', 'large'], size=n_samples),
    'shape': np.random.choice(['circle', 'square', 'triangle'], size=n_samples),
    'material': np.random.choice(['wood', 'metal', 'plastic'], size=n_samples)
}

# Create some patterns in the data
for i in range(n_samples):
    if data['color'][i] == 'red':
        data['size'][i] = 'small' if np.random.random() < 0.8 else 'medium'
        data['material'][i] = 'plastic' if np.random.random() < 0.7 else 'metal'
    elif data['color'][i] == 'blue':
        data['size'][i] = 'large' if np.random.random() < 0.8 else 'medium'
        data['material'][i] = 'metal' if np.random.random() < 0.7 else 'wood'
    else:  # green
        data['size'][i] = 'medium' if np.random.random() < 0.8 else 'large'
        data['material'][i] = 'wood' if np.random.random() < 0.7 else 'plastic'

# Convert to DataFrame
X = pd.DataFrame(data)

# %% [markdown]
# ## 2. Run CACTUS Clustering

# %%
# Run CACTUS clustering
labels, clusters = fit_cactus(
    X,
    min_support=0.1,      # Minimum support threshold for attribute-value pairs
    min_confidence=0.5,   # Minimum confidence threshold for cluster refinement
    max_cluster_size=4,   # Maximum number of attribute-value pairs in a cluster
    noise_threshold=0.05  # Minimum support threshold for noise filtering
)

# %% [markdown]
# ## 3. Visualize Results

# %%
# Plot cluster distribution
plot_cluster_distribution(labels, "CACTUS Cluster Distribution")

# Plot attribute distributions for each feature
for attribute in X.columns:
    plot_attribute_distribution(X, labels, attribute)

# Plot cluster heatmap
plot_cluster_heatmap(X, labels, "CACTUS Cluster Attribute Distribution")

# %% [markdown]
# ## 4. Print Cluster Information

# %%
# Print information about each cluster
print("\nCluster Information:")
print("-" * 50)
for i, cluster in enumerate(clusters):
    print(f"\nCluster {i}:")
    for attr, value in cluster:
        print(f"  {attr}: {value}")
    cluster_size = sum(labels == i)
    print(f"  Size: {cluster_size} points")
    print(f"  Percentage: {cluster_size/len(X)*100:.1f}%") 