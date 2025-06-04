#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Kernel Principal Component Analysis (Kernel PCA) Implementation
Includes custom and scikit-learn implementations, visualization, and evaluation tools.
"""
import numpy as np
import matplotlib.pyplot as plt
from sklearn.decomposition import KernelPCA as SKLearnKernelPCA
from sklearn.datasets import make_moons
from sklearn.preprocessing import StandardScaler
import seaborn as sns
import os

def create_kernel_pca_model(n_components=2, kernel='rbf', gamma=None, degree=3, coef0=1):
    """
    Create a Kernel PCA model with specified parameters.
    """
    return {
        'n_components': n_components,
        'kernel': kernel,
        'gamma': gamma,
        'degree': degree,
        'coef0': coef0,
        'alphas_': None,
        'lambdas_': None,
        'X_fit_': None,
        'K_fit_rows_': None
    }

def _get_kernel(model, X, Y=None):
    if Y is None:
        Y = X
    if model['kernel'] == 'linear':
        return np.dot(X, Y.T)
    elif model['kernel'] == 'poly':
        return (model['gamma'] * np.dot(X, Y.T) + model['coef0']) ** model['degree']
    elif model['kernel'] == 'rbf':
        if model['gamma'] is None:
            model['gamma'] = 1.0 / X.shape[1]
        X_norm = np.sum(X ** 2, axis=-1)
        Y_norm = np.sum(Y ** 2, axis=-1)
        K = -2 * np.dot(X, Y.T) + X_norm[:, None] + Y_norm[None, :]
        return np.exp(-model['gamma'] * K)
    else:
        raise ValueError('Unsupported kernel')

def _center_kernel(K):
    n = K.shape[0]
    one_n = np.ones((n, n)) / n
    return K - one_n @ K - K @ one_n + one_n @ K @ one_n

def fit_kernel_pca(model, X):
    X = np.array(X)
    model['X_fit_'] = X
    K = _get_kernel(model, X)
    K_centered = _center_kernel(K)
    eigvals, eigvecs = np.linalg.eigh(K_centered)
    # Sort eigenvalues and eigenvectors in descending order
    idx = np.argsort(eigvals)[::-1]
    eigvals, eigvecs = eigvals[idx], eigvecs[:, idx]
    model['lambdas_'] = eigvals[:model['n_components']]
    model['alphas_'] = eigvecs[:, :model['n_components']] / np.sqrt(model['lambdas_'])
    model['K_fit_rows_'] = K_centered
    return model

def transform_kernel_pca(model, X):
    K = _get_kernel(model, np.array(X), model['X_fit_'])
    n_fit = model['X_fit_'].shape[0]
    one_n = np.ones((K.shape[0], n_fit)) / n_fit
    K_centered = K - one_n @ model['K_fit_rows_'] - K @ np.ones((n_fit, n_fit)) / n_fit + one_n @ model['K_fit_rows_'] @ np.ones((n_fit, n_fit)) / n_fit
    return np.dot(K_centered, model['alphas_'])

def fit_transform_kernel_pca(model, X):
    model = fit_kernel_pca(model, X)
    return transform_kernel_pca(model, X)

def plot_kpca_projection(X_kpca, y, title="Kernel PCA Projection (2D)"):
    plt.figure(figsize=(8, 6))
    palette = sns.color_palette("bright", len(np.unique(y)))
    for i, label in enumerate(np.unique(y)):
        plt.scatter(X_kpca[y == label, 0], X_kpca[y == label, 1], label=f"Class {label}", alpha=0.7, color=palette[i])
    plt.xlabel('PC1')
    plt.ylabel('PC2')
    plt.title(title)
    plt.legend()
    os.makedirs('algorithms/dimensionality_reduction/kernel_pca', exist_ok=True)
    plt.savefig(f'algorithms/dimensionality_reduction/kernel_pca/{title.lower().replace(" ", "_")}.png')
    plt.close()

# Generate nonlinear data (moons)
X, y = make_moons(n_samples=400, noise=0.07, random_state=42)
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)

# Custom Kernel PCA (RBF)
print("\nTraining Custom Kernel PCA (RBF)...")
custom_kpca = create_kernel_pca_model(n_components=2, kernel='rbf', gamma=5)
X_kpca_custom = fit_transform_kernel_pca(custom_kpca, X_scaled)
plot_kpca_projection(X_kpca_custom, y, title="Custom Kernel PCA (RBF) Projection (2D)")

# scikit-learn KernelPCA (RBF)
print("\nTraining scikit-learn KernelPCA (RBF)...")
skl_kpca = SKLearnKernelPCA(n_components=2, kernel='rbf', gamma=5)
X_kpca_skl = skl_kpca.fit_transform(X_scaled)
plot_kpca_projection(X_kpca_skl, y, title="scikit-learn KernelPCA (RBF) Projection (2D)")

# Custom Kernel PCA (Polynomial)
print("\nTraining Custom Kernel PCA (Polynomial)...")
custom_kpca_poly = create_kernel_pca_model(n_components=2, kernel='poly', gamma=1, degree=3, coef0=1)
X_kpca_custom_poly = fit_transform_kernel_pca(custom_kpca_poly, X_scaled)
plot_kpca_projection(X_kpca_custom_poly, y, title="Custom Kernel PCA (Poly) Projection (2D)")

# scikit-learn KernelPCA (Polynomial)
print("\nTraining scikit-learn KernelPCA (Polynomial)...")
skl_kpca_poly = SKLearnKernelPCA(n_components=2, kernel='poly', gamma=1, degree=3, coef0=1)
X_kpca_skl_poly = skl_kpca_poly.fit_transform(X_scaled)
plot_kpca_projection(X_kpca_skl_poly, y, title="scikit-learn KernelPCA (Poly) Projection (2D)")