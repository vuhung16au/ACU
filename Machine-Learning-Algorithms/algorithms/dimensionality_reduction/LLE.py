#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Locally Linear Embedding (LLE) Implementation
Includes custom and scikit-learn implementations, visualization, and evaluation tools.
"""
import numpy as np
import matplotlib.pyplot as plt
from sklearn.manifold import LocallyLinearEmbedding as SKLearnLLE
from sklearn.datasets import make_swiss_roll
from sklearn.preprocessing import StandardScaler
from sklearn.neighbors import NearestNeighbors
import seaborn as sns
import os

def create_lle_model(n_neighbors=10, n_components=2, reg=1e-3):
    """
    Create an LLE model with specified parameters.
    """
    return {
        'n_neighbors': n_neighbors,
        'n_components': n_components,
        'reg': reg,
        'embedding_': None,
        'reconstruction_weights_': None,
        'neighbors_': None
    }

def fit_lle(model, X):
    X = np.array(X)
    n_samples = X.shape[0]
    # Find neighbors
    nbrs = NearestNeighbors(n_neighbors=model['n_neighbors'] + 1).fit(X)
    distances, indices = nbrs.kneighbors(X)
    neighbors = indices[:, 1:]  # Exclude self
    model['neighbors_'] = neighbors
    # Compute reconstruction weights
    W = np.zeros((n_samples, n_samples))
    for i in range(n_samples):
        Z = X[neighbors[i]] - X[i]  # Centered neighbors
        C = np.dot(Z, Z.T)
        C += np.eye(model['n_neighbors']) * model['reg'] * np.trace(C)  # Regularization
        w = np.linalg.solve(C, np.ones(model['n_neighbors']))
        w /= w.sum()
        W[i, neighbors[i]] = w
    model['reconstruction_weights_'] = W
    # Compute embedding
    M = np.eye(n_samples) - W
    M = np.dot(M.T, M)
    eigvals, eigvecs = np.linalg.eigh(M)
    idx = np.argsort(eigvals)[1:model['n_components']+1]  # Skip the zero eigenvalue
    model['embedding_'] = eigvecs[:, idx]
    return model

def transform_lle(model, X):
    # LLE is not naturally out-of-sample; return embedding for fit data
    return model['embedding_']

def fit_transform_lle(model, X):
    model = fit_lle(model, X)
    return model['embedding_']

def plot_lle_projection(X_lle, color, title="LLE Projection (2D)"):
    plt.figure(figsize=(8, 6))
    plt.scatter(X_lle[:, 0], X_lle[:, 1], c=color, cmap=plt.cm.Spectral, s=20)
    plt.xlabel('LLE1')
    plt.ylabel('LLE2')
    plt.title(title)
    plt.colorbar()
    os.makedirs('algorithms/dimensionality_reduction/lle', exist_ok=True)
    plt.savefig(f'algorithms/dimensionality_reduction/lle/{title.lower().replace(" ", "_")}.png')
    plt.close()

# Generate Swiss roll data (nonlinear manifold)
X, t = make_swiss_roll(n_samples=1200, noise=0.05, random_state=42)
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)

# Custom LLE
print("\nTraining Custom LLE...")
custom_lle = create_lle_model(n_neighbors=12, n_components=2)
X_lle_custom = fit_transform_lle(custom_lle, X_scaled)
plot_lle_projection(X_lle_custom, t, title="Custom LLE Projection (2D)")

# scikit-learn LLE
print("\nTraining scikit-learn LLE...")
skl_lle = SKLearnLLE(n_neighbors=12, n_components=2, method='standard')
X_lle_skl = skl_lle.fit_transform(X_scaled)
plot_lle_projection(X_lle_skl, t, title="scikit-learn LLE Projection (2D)")