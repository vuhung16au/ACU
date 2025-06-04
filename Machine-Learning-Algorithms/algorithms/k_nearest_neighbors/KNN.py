#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
# k-Nearest Neighbors (KNN) Implementation

This notebook demonstrates the implementation and usage of K-Nearest Neighbors for classification tasks.
We'll use both scikit-learn's implementation and a custom implementation to understand the algorithm better.

## Table of Contents
1. Import Required Libraries
2. Data Generation and Preprocessing
3. Custom KNN Implementation
4. Model Training and Evaluation
5. Visualization
"""

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from sklearn.neighbors import KNeighborsClassifier
from sklearn.model_selection import train_test_split, cross_val_score
from sklearn.metrics import (
    accuracy_score, confusion_matrix, classification_report,
    roc_curve, auc
)
from sklearn.preprocessing import StandardScaler
from scipy.stats import mode
from sklearn.metrics.pairwise import euclidean_distances, manhattan_distances
import os

# Set random seed for reproducibility
np.random.seed(2220)

# Create output directory for visuals
output_dir = 'algorithms/k_nearest_neighbors'
os.makedirs(output_dir, exist_ok=True)

## 2. Data Generation and Preprocessing
def generate_data(n_samples=100, n_classes=3):
    """
    Generate sample data for classification with distinct class regions.
    
    Parameters:
    -----------
    n_samples : int, default=100
        Total number of samples to generate
    n_classes : int, default=3
        Number of classes to generate
        
    Returns:
    --------
    X : numpy.ndarray
        Generated features
    y : numpy.ndarray
        Generated class labels
    """
    # Generate data for each class
    X = []
    y = []
    
    for i in range(n_classes):
        # Generate points in different regions
        angle = 2 * np.pi * i / n_classes
        center = np.array([np.cos(angle), np.sin(angle)]) * 2
        X_class = np.random.randn(n_samples//n_classes, 2) + center
        X.append(X_class)
        y.extend([i] * (n_samples//n_classes))
    
    return np.vstack(X), np.array(y)

## 3. Custom KNN Implementation
def compute_distance(x1, x2, metric='euclidean', p=2):
    """
    Compute distance between two points using specified metric.
    
    Parameters:
    -----------
    x1 : numpy.ndarray
        First point
    x2 : numpy.ndarray
        Second point
    metric : str, default='euclidean'
        Distance metric ('euclidean', 'manhattan', 'minkowski')
    p : int, default=2
        Power parameter for Minkowski distance
        
    Returns:
    --------
    float
        Distance between points
    """
    if metric == 'euclidean':
        return np.sqrt(np.sum((x1 - x2) ** 2))
    elif metric == 'manhattan':
        return np.sum(np.abs(x1 - x2))
    elif metric == 'minkowski':
        return np.power(np.sum(np.power(np.abs(x1 - x2), p)), 1/p)
    else:
        raise ValueError(f"Unknown metric: {metric}")

def compute_distances(X, X_train, metric='euclidean', p=2):
    """
    Compute distances between test points and all training points.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Test points
    X_train : numpy.ndarray
        Training points
    metric : str, default='euclidean'
        Distance metric
    p : int, default=2
        Power parameter for Minkowski distance
        
    Returns:
    --------
    numpy.ndarray
        Distance matrix
    """
    n_test = X.shape[0]
    n_train = X_train.shape[0]
    distances = np.zeros((n_test, n_train))
    
    for i in range(n_test):
        for j in range(n_train):
            distances[i, j] = compute_distance(X[i], X_train[j], metric, p)
    
    return distances

def custom_knn_predict(X, X_train, y_train, n_neighbors=5, weights='uniform', 
                      metric='euclidean', p=2):
    """
    Make predictions using KNN.
    
    Hyperparameters:
    ---------------
    n_neighbors : int, default=5
        Number of neighbors to use. Controls the complexity of the model.
        Higher values make the model more robust to noise but may lead to underfitting.
    weights : str, default='uniform'
        Weight function used in prediction ('uniform' or 'distance').
        'uniform' gives equal weight to all neighbors, while 'distance' gives more weight
        to closer neighbors.
    metric : str, default='euclidean'
        Distance metric ('euclidean', 'manhattan', 'minkowski').
        Different metrics may work better for different types of data.
    p : int, default=2
        Power parameter for Minkowski distance. p=1 is Manhattan distance,
        p=2 is Euclidean distance.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Features to predict
    X_train : numpy.ndarray
        Training features
    y_train : numpy.ndarray
        Training labels
        
    Returns:
    --------
    numpy.ndarray
        Predicted classes
    """
    distances = compute_distances(X, X_train, metric, p)
    n_test = X.shape[0]
    predictions = np.zeros(n_test)
    classes = np.unique(y_train)
    
    for i in range(n_test):
        # Get indices of k nearest neighbors
        k_indices = np.argsort(distances[i])[:n_neighbors]
        k_nearest_labels = y_train[k_indices]
        
        if weights == 'uniform':
            # Simple majority vote
            predictions[i] = mode(k_nearest_labels)[0][0]
        else:  # 'distance'
            # Weighted vote based on distance
            distance_weights = 1 / (distances[i, k_indices] + 1e-10)
            weighted_votes = np.zeros(len(classes))
            for j, label in enumerate(k_nearest_labels):
                weighted_votes[label] += distance_weights[j]
            predictions[i] = np.argmax(weighted_votes)
    
    return predictions

def custom_knn_predict_proba(X, X_train, y_train, n_neighbors=5, weights='uniform',
                           metric='euclidean', p=2):
    """
    Predict class probabilities using KNN.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Features to predict
    X_train : numpy.ndarray
        Training features
    y_train : numpy.ndarray
        Training labels
    n_neighbors : int, default=5
        Number of neighbors to use
    weights : str, default='uniform'
        Weight function used in prediction
    metric : str, default='euclidean'
        Distance metric
    p : int, default=2
        Power parameter for Minkowski distance
        
    Returns:
    --------
    numpy.ndarray
        Predicted probabilities for each class
    """
    distances = compute_distances(X, X_train, metric, p)
    n_test = X.shape[0]
    classes = np.unique(y_train)
    probabilities = np.zeros((n_test, len(classes)))
    
    for i in range(n_test):
        k_indices = np.argsort(distances[i])[:n_neighbors]
        k_nearest_labels = y_train[k_indices]
        
        if weights == 'uniform':
            # Simple probability based on counts
            for j, label in enumerate(classes):
                probabilities[i, j] = np.mean(k_nearest_labels == label)
        else:  # 'distance'
            # Weighted probability based on distance
            distance_weights = 1 / (distances[i, k_indices] + 1e-10)
            for j, label in enumerate(classes):
                mask = k_nearest_labels == label
                probabilities[i, j] = np.sum(distance_weights[mask]) / np.sum(distance_weights)
    
    return probabilities

## 4. Model Training and Evaluation
# Generate and prepare data
X, y = generate_data()
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.2, random_state=2220
)

# Scale features
scaler = StandardScaler()
X_train_scaled = scaler.fit_transform(X_train)
X_test_scaled = scaler.transform(X_test)

# Train custom model
custom_predictions = custom_knn_predict(
    X_test_scaled, X_train_scaled, y_train,
    n_neighbors=5,      # Number of neighbors
    weights='distance', # Weight by distance
    metric='euclidean'  # Use Euclidean distance
)
custom_predictions_proba = custom_knn_predict_proba(
    X_test_scaled, X_train_scaled, y_train,
    n_neighbors=5,
    weights='distance',
    metric='euclidean'
)

# Train scikit-learn model
sklearn_model = KNeighborsClassifier(
    n_neighbors=5,      # Number of neighbors
    weights='distance', # Weight by distance
    metric='euclidean', # Use Euclidean distance
    algorithm='auto'    # Automatically choose the best algorithm
)
sklearn_model.fit(X_train_scaled, y_train)
sklearn_predictions = sklearn_model.predict(X_test_scaled)
sklearn_predictions_proba = sklearn_model.predict_proba(X_test_scaled)

## 5. Model Evaluation
# Print evaluation metrics
print("\nCustom Implementation Results:")
print(f"Accuracy: {accuracy_score(y_test, custom_predictions):.4f}")
print("\nClassification Report:")
print(classification_report(y_test, custom_predictions))

print("\nScikit-learn Implementation Results:")
print(f"Accuracy: {accuracy_score(y_test, sklearn_predictions):.4f}")
print("\nClassification Report:")
print(classification_report(y_test, sklearn_predictions))

## 6. Visualization
def plot_decision_boundary(X, y, X_train, y_train, n_neighbors=5, weights='distance',
                         metric='euclidean', title="Decision Boundary"):
    """
    Plot decision boundary using seaborn and save to PNG file.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Test points
    y : numpy.ndarray
        Test labels
    X_train : numpy.ndarray
        Training points
    y_train : numpy.ndarray
        Training labels
    n_neighbors : int, default=5
        Number of neighbors
    weights : str, default='distance'
        Weight function
    metric : str, default='euclidean'
        Distance metric
    title : str, default="Decision Boundary"
        Plot title
    """
    # Create mesh grid
    h = 0.02  # Step size
    x_min, x_max = X[:, 0].min() - 1, X[:, 0].max() + 1
    y_min, y_max = X[:, 1].min() - 1, X[:, 1].max() + 1
    xx, yy = np.meshgrid(np.arange(x_min, x_max, h),
                        np.arange(y_min, y_max, h))
    
    # Make predictions on mesh grid
    Z = custom_knn_predict(
        np.c_[xx.ravel(), yy.ravel()],
        X_train, y_train,
        n_neighbors=n_neighbors,
        weights=weights,
        metric=metric
    )
    Z = Z.reshape(xx.shape)
    
    # Plot decision boundary
    plt.figure(figsize=(10, 8))
    plt.contourf(xx, yy, Z, alpha=0.4)
    plt.scatter(X[:, 0], X[:, 1], c=y, alpha=0.8)
    plt.scatter(X_train[:, 0], X_train[:, 1], c=y_train, alpha=0.4, marker='x')
    plt.title(title)
    plt.xlabel('Feature 1')
    plt.ylabel('Feature 2')
    filename = f'{output_dir}/knn-decision_boundary.png'
    plt.savefig(filename)
    plt.close()

def plot_confusion_matrix(y_true, y_pred, title):
    """
    Plot confusion matrix using seaborn and save to PNG file.
    
    Parameters:
    -----------
    y_true : numpy.ndarray
        True labels
    y_pred : numpy.ndarray
        Predicted labels
    title : str
        Plot title
    """
    cm = confusion_matrix(y_true, y_pred)
    plt.figure(figsize=(8, 6))
    sns.heatmap(cm, annot=True, fmt='d', cmap='Blues')
    plt.title(title)
    plt.ylabel('True Label')
    plt.xlabel('Predicted Label')
    filename = f'{output_dir}/knn-confusion_matrix.png'
    plt.savefig(filename)
    plt.close()

def plot_roc_curve(y_true, y_pred_proba, title):
    """
    Plot ROC curve using seaborn and save to PNG file.
    
    Parameters:
    -----------
    y_true : numpy.ndarray
        True labels
    y_pred_proba : numpy.ndarray
        Predicted probabilities
    title : str
        Plot title
    """
    plt.figure(figsize=(8, 6))
    
    # Plot ROC curve for each class
    for i in range(y_pred_proba.shape[1]):
        fpr, tpr, _ = roc_curve(y_true == i, y_pred_proba[:, i])
        roc_auc = auc(fpr, tpr)
        plt.plot(fpr, tpr, label=f'Class {i} (AUC = {roc_auc:.2f})')
    
    plt.plot([0, 1], [0, 1], 'k--')
    plt.xlim([0.0, 1.0])
    plt.ylim([0.0, 1.05])
    plt.xlabel('False Positive Rate')
    plt.ylabel('True Positive Rate')
    plt.title(title)
    plt.legend(loc="lower right")
    filename = f'{output_dir}/knn-roc_curve.png'
    plt.savefig(filename)
    plt.close()

# Plot results
plot_decision_boundary(X_test_scaled, y_test, X_train_scaled, y_train,
                      title="KNN Decision Boundary")
plot_confusion_matrix(y_test, custom_predictions, "Custom KNN Confusion Matrix")
plot_confusion_matrix(y_test, sklearn_predictions, "Scikit-learn KNN Confusion Matrix")
plot_roc_curve(y_test, sklearn_predictions_proba, "Scikit-learn KNN ROC Curve") 