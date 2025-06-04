# Expectation-Maximization (EM) Clustering Implementation
# This notebook demonstrates the implementation of the EM algorithm for Gaussian Mixture Models.

## Table of Contents
# 1. Import Required Libraries
# 2. Data Generation and Preprocessing
# 3. EM Implementation
# 4. Model Training and Evaluation
# 5. Visualization
# 6. Results and Analysis

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import os
from scipy.stats import multivariate_normal
from sklearn.metrics import silhouette_score, calinski_harabasz_score
from sklearn.datasets import make_blobs

# Set random seed for reproducibility
np.random.seed(2220)

## 2. Data Generation and Preprocessing
def generate_data(n_samples=300, n_features=2, centers=3, cluster_std=1.0):
    """
    Generate sample data for clustering.
    
    Parameters:
    -----------
    n_samples : int, default=300
        Number of samples to generate
    n_features : int, default=2
        Number of features
    centers : int, default=3
        Number of clusters
    cluster_std : float, default=1.0
        Standard deviation of clusters
        
    Returns:
    --------
    tuple
        X : numpy.ndarray
            Features array of shape (n_samples, n_features)
        y : numpy.ndarray
            True cluster labels
    """
    X, y = make_blobs(
        n_samples=n_samples,
        n_features=n_features,
        centers=centers,
        cluster_std=cluster_std,
        random_state=2220
    )
    return X, y

## 3. EM Implementation
def initialize_parameters(X, n_components, random_state=None):
    """
    Initialize GMM parameters.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Input data
    n_components : int
        Number of mixture components
    random_state : int, default=None
        Random seed for reproducibility
        
    Returns:
    --------
    tuple
        means : numpy.ndarray
            Initial means
        covariances : numpy.ndarray
            Initial covariances
        weights : numpy.ndarray
            Initial mixture weights
    """
    n_samples, n_features = X.shape
    
    # Initialize means using k-means++ strategy
    np.random.seed(random_state)
    means = X[np.random.choice(n_samples, n_components, replace=False)]
    
    # Initialize covariances as identity matrices
    covariances = np.array([np.eye(n_features) for _ in range(n_components)])
    
    # Initialize weights uniformly
    weights = np.ones(n_components) / n_components
    
    return means, covariances, weights

def e_step(X, means, covariances, weights):
    """
    Expectation step: compute responsibilities.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Input data
    means : numpy.ndarray
        Current means
    covariances : numpy.ndarray
        Current covariances
    weights : numpy.ndarray
        Current mixture weights
        
    Returns:
    --------
    numpy.ndarray
        Responsibilities matrix
    """
    n_samples = X.shape[0]
    n_components = len(weights)
    responsibilities = np.zeros((n_samples, n_components))
    
    for k in range(n_components):
        responsibilities[:, k] = weights[k] * multivariate_normal.pdf(
            X, mean=means[k], cov=covariances[k]
        )
        
    # Normalize responsibilities
    responsibilities /= responsibilities.sum(axis=1, keepdims=True)
    
    return responsibilities

def m_step(X, responsibilities):
    """
    Maximization step: update parameters.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Input data
    responsibilities : numpy.ndarray
        Responsibilities matrix
        
    Returns:
    --------
    tuple
        means : numpy.ndarray
            Updated means
        covariances : numpy.ndarray
            Updated covariances
        weights : numpy.ndarray
            Updated mixture weights
    """
    n_samples, n_features = X.shape
    n_components = responsibilities.shape[1]
    
    # Update weights
    weights = responsibilities.sum(axis=0) / n_samples
    
    # Update means
    means = np.zeros((n_components, n_features))
    for k in range(n_components):
        means[k] = np.average(X, axis=0, weights=responsibilities[:, k])
        
    # Update covariances
    covariances = np.zeros((n_components, n_features, n_features))
    for k in range(n_components):
        diff = X - means[k]
        covariances[k] = np.average(
            diff[:, :, np.newaxis] * diff[:, np.newaxis, :],
            axis=0,
            weights=responsibilities[:, k]
        )
        
        # Add small value to diagonal for numerical stability
        covariances[k] += np.eye(n_features) * 1e-6
        
    return means, covariances, weights

def compute_log_likelihood(X, means, covariances, weights):
    """
    Compute log likelihood of the data.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Input data
    means : numpy.ndarray
        Current means
    covariances : numpy.ndarray
        Current covariances
    weights : numpy.ndarray
        Current mixture weights
        
    Returns:
    --------
    float
        Log likelihood
    """
    n_samples = X.shape[0]
    n_components = len(weights)
    log_likelihood = 0
    
    for k in range(n_components):
        log_likelihood += weights[k] * multivariate_normal.pdf(
            X, mean=means[k], cov=covariances[k]
        )
        
    return np.sum(np.log(log_likelihood))

def em_clustering(X, n_components=3, max_iter=100, tol=1e-4, random_state=None):
    """
    Perform EM clustering using Gaussian Mixture Models.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Input data of shape (n_samples, n_features)
    n_components : int, default=3
        Number of mixture components
        - Determines the number of clusters
        - Should be chosen based on domain knowledge
    max_iter : int, default=100
        Maximum number of EM iterations
        - More iterations can lead to better convergence
        - May cause overfitting if too high
    tol : float, default=1e-4
        Convergence threshold
        - Smaller values lead to more precise convergence
        - Larger values may cause early stopping
    random_state : int, default=None
        Random seed for reproducibility
        
    Returns:
    --------
    tuple
        labels : numpy.ndarray
            Cluster labels for each sample
        means : numpy.ndarray
            Final means
        covariances : numpy.ndarray
            Final covariances
        weights : numpy.ndarray
            Final mixture weights
        log_likelihood : float
            Final log likelihood
    """
    # Initialize parameters
    means, covariances, weights = initialize_parameters(X, n_components, random_state)
    
    # EM iterations
    prev_log_likelihood = -np.inf
    for iteration in range(max_iter):
        # E-step
        responsibilities = e_step(X, means, covariances, weights)
        
        # M-step
        means, covariances, weights = m_step(X, responsibilities)
        
        # Compute log likelihood
        log_likelihood = compute_log_likelihood(X, means, covariances, weights)
        
        # Check convergence
        if abs(log_likelihood - prev_log_likelihood) < tol:
            break
            
        prev_log_likelihood = log_likelihood
    
    # Assign labels based on responsibilities
    labels = np.argmax(responsibilities, axis=1)
    
    return labels, means, covariances, weights, log_likelihood

## 4. Model Training and Evaluation
def evaluate_clustering(X, labels):
    """
    Evaluate clustering results using various metrics.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Input data
    labels : numpy.ndarray
        Cluster labels
        
    Returns:
    --------
    dict
        Dictionary containing evaluation metrics
    """
    metrics = {
        'silhouette_score': silhouette_score(X, labels),
        'calinski_harabasz_score': calinski_harabasz_score(X, labels)
    }
    return metrics

## 5. Visualization
def plot_clusters(X, labels, means=None, covariances=None, title=None):
    """
    Plot clustering results using seaborn and save to PNG file.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Input data
    labels : numpy.ndarray
        Cluster labels
    means : numpy.ndarray, default=None
        Cluster means
    covariances : numpy.ndarray, default=None
        Cluster covariances
    title : str, default=None
        Plot title
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/other-algorithms/em', exist_ok=True)
    
    plt.figure(figsize=(10, 6))
    sns.scatterplot(x=X[:, 0], y=X[:, 1], hue=labels, palette='deep')
    
    if means is not None:
        plt.scatter(means[:, 0], means[:, 1], c='red', marker='x', s=100, label='Centroids')
        
    plot_title = title if title else "EM Clustering Results"
    plt.title(plot_title)
    plt.xlabel('Feature 1')
    plt.ylabel('Feature 2')
    plt.legend()
    
    # Save the plot to a PNG file
    filename = f'algorithms/other-algorithms/em/em-{plot_title.lower().replace(" ", "_")}.png'
    plt.savefig(filename)
    plt.close() 