#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Principal Component Analysis (PCA) Implementation
Includes custom and scikit-learn implementations, visualization, and evaluation tools.
"""
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from sklearn.decomposition import PCA as SKLearnPCA
from sklearn.datasets import load_iris, make_blobs
from sklearn.preprocessing import StandardScaler
import seaborn as sns
import os

def create_pca_model(n_components=None, whiten=False):
    """
    Create a PCA model with specified parameters.
    """
    return {
        'n_components': n_components,
        'whiten': whiten,
        'components_': None,
        'explained_variance_': None,
        'explained_variance_ratio_': None,
        'mean_': None,
        'singular_values_': None
    }

def fit_pca(model, X):
    X = np.array(X)
    model['mean_'] = np.mean(X, axis=0)
    X_centered = X - model['mean_']
    U, S, Vt = np.linalg.svd(X_centered, full_matrices=False)
    components = Vt
    explained_variance = (S ** 2) / (X.shape[0] - 1)
    explained_variance_ratio = explained_variance / explained_variance.sum()
    if model['n_components'] is not None:
        components = components[:model['n_components']]
        explained_variance = explained_variance[:model['n_components']]
        explained_variance_ratio = explained_variance_ratio[:model['n_components']]
        S = S[:model['n_components']]
    model['components_'] = components
    model['explained_variance_'] = explained_variance
    model['explained_variance_ratio_'] = explained_variance_ratio
    model['singular_values_'] = S
    return model

def transform_pca(model, X):
    X = np.array(X)
    X_centered = X - model['mean_']
    X_pca = np.dot(X_centered, model['components_'].T)
    if model['whiten']:
        X_pca /= np.sqrt(model['explained_variance_'])
    return X_pca

def fit_transform_pca(model, X):
    model = fit_pca(model, X)
    return transform_pca(model, X)

def plot_explained_variance(pca, title="Explained Variance Ratio"):
    plt.figure(figsize=(8, 5))
    # Handle both dictionary and sklearn PCA objects
    if isinstance(pca, dict):
        explained_variance_ratio = pca['explained_variance_ratio_']
    else:
        explained_variance_ratio = pca.explained_variance_ratio_
    
    plt.bar(range(1, len(explained_variance_ratio) + 1), explained_variance_ratio, alpha=0.7)
    plt.step(range(1, len(explained_variance_ratio) + 1), np.cumsum(explained_variance_ratio), where='mid', label='Cumulative')
    plt.xlabel('Principal Component')
    plt.ylabel('Explained Variance Ratio')
    plt.title(title)
    plt.legend(['Cumulative', 'Individual'])
    os.makedirs('algorithms/dimensionality_reduction/pca', exist_ok=True)
    plt.savefig('algorithms/dimensionality_reduction/pca/explained_variance.png')
    plt.close()

def plot_pca_projection(X_pca, y, title="PCA Projection (2D)"):
    plt.figure(figsize=(8, 6))
    palette = sns.color_palette("bright", len(np.unique(y)))
    for i, label in enumerate(np.unique(y)):
        plt.scatter(X_pca[y == label, 0], X_pca[y == label, 1], label=f"Class {label}", alpha=0.7, color=palette[i])
    plt.xlabel('PC1')
    plt.ylabel('PC2')
    plt.title(title)
    plt.legend()
    os.makedirs('algorithms/dimensionality_reduction/pca', exist_ok=True)
    plt.savefig('algorithms/dimensionality_reduction/pca/projection.png')
    plt.close()

# Load Iris dataset for demonstration
data = load_iris()
X = data.data
y = data.target
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)

# Custom PCA
print("\nTraining Custom PCA...")
custom_pca = create_pca_model(n_components=2)
X_pca_custom = fit_transform_pca(custom_pca, X_scaled)
print(f"Explained variance ratio (custom): {custom_pca['explained_variance_ratio_']}")
plot_explained_variance(custom_pca, title="Custom PCA Explained Variance Ratio")
plot_pca_projection(X_pca_custom, y, title="Custom PCA Projection (2D)")

# scikit-learn PCA
print("\nTraining scikit-learn PCA...")
skl_pca = SKLearnPCA(n_components=2)
X_pca_skl = skl_pca.fit_transform(X_scaled)
print(f"Explained variance ratio (sklearn): {skl_pca.explained_variance_ratio_}")
plot_explained_variance(skl_pca, title="scikit-learn PCA Explained Variance Ratio")
plot_pca_projection(X_pca_skl, y, title="scikit-learn PCA Projection (2D)")