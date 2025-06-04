#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
t-Distributed Stochastic Neighbor Embedding (t-SNE) Implementation
Includes scikit-learn implementation, visualization, and evaluation tools.
"""
import numpy as np
import matplotlib.pyplot as plt
from sklearn.manifold import TSNE
from sklearn.datasets import load_digits
from sklearn.preprocessing import StandardScaler
import seaborn as sns
import os

def plot_tsne_projection(X_tsne, y, title="t-SNE Projection (2D)"):
    plt.figure(figsize=(10, 8))
    palette = sns.color_palette("bright", len(np.unique(y)))
    for i, label in enumerate(np.unique(y)):
        plt.scatter(X_tsne[y == label, 0], X_tsne[y == label, 1], 
                   label=f"Class {label}", alpha=0.7, color=palette[i], s=20)
    plt.xlabel('t-SNE1')
    plt.ylabel('t-SNE2')
    plt.title(title)
    plt.legend()
    os.makedirs('algorithms/dimensionality_reduction/tsne', exist_ok=True)
    plt.savefig('algorithms/dimensionality_reduction/tsne/projection.png')
    plt.close()

# Load digits dataset (high-dimensional, 10 classes)
data = load_digits()
X = data.data
y = data.target
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)

# scikit-learn t-SNE
print("\nTraining scikit-learn t-SNE...")
tsne = TSNE(n_components=2, perplexity=30, learning_rate=200, 
            n_iter=1000, random_state=42, init='pca')
X_tsne = tsne.fit_transform(X_scaled)
plot_tsne_projection(X_tsne, y, title="scikit-learn t-SNE Projection (2D)")