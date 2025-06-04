#!/usr/bin/env python3
# -*- coding: utf-8 -*-

# %% [markdown]
# # Naive Bayes Classification Implementation
# 
# This notebook demonstrates implementations of Gaussian and Multinomial Naive Bayes classifiers.
# 
# ## Table of Contents:
# 1. Import Required Libraries
# 2. Data Generation and Preprocessing
# 3. Gaussian Naive Bayes Implementation
# 4. Multinomial Naive Bayes Implementation
# 5. Model Training and Evaluation
# 6. Visualization
# 7. Results and Analysis

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.model_selection import train_test_split
from sklearn.metrics import (
    accuracy_score, confusion_matrix, classification_report,
    roc_curve, auc, precision_score, recall_score, f1_score
)
from sklearn.preprocessing import StandardScaler
import os

# Set random seed for reproducibility
np.random.seed(2220)

## 2. Data Generation and Preprocessing
# 
# We'll create synthetic data for binary classification with a non-linear decision boundary.

def generate_data(n_samples=1000, n_features=2, class_separation=1.0):
    """
    Generate sample data for classification.
    
    Parameters:
    -----------
    n_samples : int, default=1000
        Number of samples to generate
    n_features : int, default=2
        Number of features
    class_separation : float, default=1.0
        Separation between classes (higher values create more distinct classes)
        
    Returns:
    --------
    tuple
        X : numpy.ndarray
            Features array of shape (n_samples, n_features)
        y : numpy.ndarray
            Target array of shape (n_samples,)
    """
    # Generate features
    X = np.random.randn(n_samples, n_features)
    
    # Generate target with non-linear boundary
    y = np.zeros(n_samples)
    y[X[:, 0]**2 + X[:, 1]**2 > class_separation] = 1
    
    return X, y

## 3. Gaussian Naive Bayes Implementation
# 
# Gaussian Naive Bayes assumes that the features follow a normal distribution.

def gaussian_naive_bayes_fit(X, y, var_smoothing=1e-9):
    """
    Fit Gaussian Naive Bayes model.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Training features
    y : numpy.ndarray
        Target values
    var_smoothing : float, default=1e-9
        Portion of the largest variance to add to variances for stability
        - Prevents zero variance issues
        - Improves numerical stability
        - Higher values make the model more robust but less sensitive
        - Typical values range from 1e-9 to 1e-6
    """
    # Calculate class prior probabilities
    classes = np.unique(y)
    n_samples = len(y)
    class_priors = np.array([
        np.sum(y == c) / n_samples for c in classes
    ])

    # Calculate mean and variance for each feature per class
    n_features = X.shape[1]
    theta = np.zeros((len(classes), n_features))
    sigma = np.zeros((len(classes), n_features))
    
    for i, c in enumerate(classes):
        X_c = X[y == c]
        theta[i] = np.mean(X_c, axis=0)
        sigma[i] = np.var(X_c, axis=0) + var_smoothing

    return class_priors, theta, sigma, classes

def gaussian_naive_bayes_predict(X, class_priors, theta, sigma, classes):
    """
    Make predictions using Gaussian Naive Bayes model.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Features to predict
    class_priors : numpy.ndarray
        Class prior probabilities
    theta : numpy.ndarray
        Mean of each feature per class
    sigma : numpy.ndarray
        Variance of each feature per class
    classes : numpy.ndarray
        Unique class labels
    
    Returns:
    --------
    numpy.ndarray
        Predicted classes
    """
    n_samples = X.shape[0]
    n_classes = len(classes)
    log_likelihood = np.zeros((n_samples, n_classes))
    
    for i in range(n_classes):
        # Calculate log probability using Gaussian distribution
        log_likelihood[:, i] = np.sum(
            -0.5 * np.log(2 * np.pi * sigma[i]) -
            0.5 * ((X - theta[i]) ** 2) / sigma[i],
            axis=1
        )
    
    log_prior = np.log(class_priors)
    log_posterior = log_likelihood + log_prior
    return classes[np.argmax(log_posterior, axis=1)]

## 4. Multinomial Naive Bayes Implementation
# 
# Multinomial Naive Bayes is suitable for discrete features and text classification.

def multinomial_naive_bayes_fit(X, y, alpha=1.0):
    """
    Fit Multinomial Naive Bayes model.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Training features
    y : numpy.ndarray
        Target values
    alpha : float, default=1.0
        Smoothing parameter (Laplace smoothing)
        - Prevents zero probability issues
        - Higher values make the model more robust
        - Typical values range from 0.1 to 1.0
        - alpha=1.0 is known as Laplace smoothing
        - alpha<1.0 is known as Lidstone smoothing
    """
    # Calculate class prior probabilities
    classes = np.unique(y)
    n_samples = len(y)
    class_priors = np.array([
        np.sum(y == c) / n_samples for c in classes
    ])

    # Calculate feature probabilities for each class
    n_features = X.shape[1]
    feature_log_prob = np.zeros((len(classes), n_features))
    
    # Count features for each class
    feature_counts = np.zeros((len(classes), n_features))
    for i, c in enumerate(classes):
        feature_counts[i] = np.sum(X[y == c], axis=0)
    
    # Add smoothing
    smoothed_counts = feature_counts + alpha
    smoothed_class_counts = np.sum(smoothed_counts, axis=1)
    
    # Calculate log probabilities with error handling
    feature_log_prob = np.log1p(smoothed_counts) - np.log1p(smoothed_class_counts.reshape(-1, 1))

    return class_priors, feature_log_prob, classes

def multinomial_naive_bayes_predict(X, class_priors, feature_log_prob, classes):
    """
    Make predictions using Multinomial Naive Bayes model.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Features to predict
    class_priors : numpy.ndarray
        Class prior probabilities
    feature_log_prob : numpy.ndarray
        Log probabilities of features given class
    classes : numpy.ndarray
        Unique class labels
    
    Returns:
    --------
    numpy.ndarray
        Predicted classes
    """
    log_probs = np.dot(X, feature_log_prob.T)
    log_probs += np.log(class_priors)
    
    probs = np.exp(log_probs - np.max(log_probs, axis=1, keepdims=True))
    return classes[np.argmax(probs / np.sum(probs, axis=1, keepdims=True), axis=1)]

## 5. Model Training and Evaluation
# Generate and prepare data
X, y = generate_data(n_samples=1000, n_features=2, class_separation=1.0)
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.2, random_state=2220
)

# Scale features
scaler = StandardScaler()
X_train_scaled = scaler.fit_transform(X_train)
X_test_scaled = scaler.transform(X_test)

# Train Gaussian Naive Bayes
gnb_class_priors, gnb_theta, gnb_sigma, gnb_classes = gaussian_naive_bayes_fit(
    X_train_scaled, y_train, var_smoothing=1e-9
)
gnb_predictions = gaussian_naive_bayes_predict(
    X_test_scaled, gnb_class_priors, gnb_theta, gnb_sigma, gnb_classes
)

# Train Multinomial Naive Bayes
mnb_class_priors, mnb_feature_log_prob, mnb_classes = multinomial_naive_bayes_fit(
    X_train_scaled, y_train, alpha=1.0
)
mnb_predictions = multinomial_naive_bayes_predict(
    X_test_scaled, mnb_class_priors, mnb_feature_log_prob, mnb_classes
)

## 6. Visualization
# Create output directory for visuals
os.makedirs('algorithms/naive_bayes/naive_bayes', exist_ok=True)

def plot_confusion_matrix(y_true, y_pred, title, model_name):
    """
    Plot confusion matrix using seaborn.
    
    Parameters:
    -----------
    y_true : numpy.ndarray
        True labels
    y_pred : numpy.ndarray
        Predicted labels
    title : str
        Plot title
    model_name : str
        Name of the model for file naming
    """
    cm = confusion_matrix(y_true, y_pred)
    plt.figure(figsize=(8, 6))
    sns.heatmap(cm, annot=True, fmt='d', cmap='Blues')
    plt.title(title)
    plt.xlabel('Predicted')
    plt.ylabel('True')
    plt.savefig(f'algorithms/naive_bayes/naive_bayes/{model_name}_confusion_matrix.png')
    plt.close()

def plot_decision_boundary(X, y, model_func, title, model_name):
    """
    Plot decision boundary using seaborn.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Features
    y : numpy.ndarray
        Target values
    model_func : function
        Function to make predictions
    title : str
        Plot title
    model_name : str
        Name of the model for file naming
    """
    h = 0.02
    x_min, x_max = X[:, 0].min() - 1, X[:, 0].max() + 1
    y_min, y_max = X[:, 1].min() - 1, X[:, 1].max() + 1
    xx, yy = np.meshgrid(np.arange(x_min, x_max, h),
                        np.arange(y_min, y_max, h))
    
    Z = model_func(np.c_[xx.ravel(), yy.ravel()])
    Z = Z.reshape(xx.shape)
    
    plt.figure(figsize=(10, 8))
    sns.scatterplot(x=X[:, 0], y=X[:, 1], hue=y, palette='Set1')
    plt.contourf(xx, yy, Z, alpha=0.4)
    plt.title(title)
    plt.xlabel('Feature 1')
    plt.ylabel('Feature 2')
    plt.savefig(f'algorithms/naive_bayes/naive_bayes/{model_name}_decision_boundary.png')
    plt.close()

# Plot results
plot_confusion_matrix(y_test, gnb_predictions, 
                     'Gaussian Naive Bayes Confusion Matrix', 'gnb')
plot_confusion_matrix(y_test, mnb_predictions, 
                     'Multinomial Naive Bayes Confusion Matrix', 'mnb')

plot_decision_boundary(X_test_scaled, y_test,
                      lambda X: gaussian_naive_bayes_predict(X, gnb_class_priors, gnb_theta, gnb_sigma, gnb_classes),
                      'Gaussian Naive Bayes Decision Boundary', 'gnb')
plot_decision_boundary(X_test_scaled, y_test,
                      lambda X: multinomial_naive_bayes_predict(X, mnb_class_priors, mnb_feature_log_prob, mnb_classes),
                      'Multinomial Naive Bayes Decision Boundary', 'mnb')

## 7. Results and Analysis
# Print evaluation metrics
print("\nGaussian Naive Bayes Results:")
print(f"Accuracy: {accuracy_score(y_test, gnb_predictions):.4f}")
print(f"Precision: {precision_score(y_test, gnb_predictions):.4f}")
print(f"Recall: {recall_score(y_test, gnb_predictions):.4f}")
print(f"F1 Score: {f1_score(y_test, gnb_predictions):.4f}")

print("\nMultinomial Naive Bayes Results:")
print(f"Accuracy: {accuracy_score(y_test, mnb_predictions):.4f}")
print(f"Precision: {precision_score(y_test, mnb_predictions):.4f}")
print(f"Recall: {recall_score(y_test, mnb_predictions):.4f}")
print(f"F1 Score: {f1_score(y_test, mnb_predictions):.4f}")

# Print classification report
print("\nGaussian Naive Bayes Classification Report:")
print(classification_report(y_test, gnb_predictions))

print("\nMultinomial Naive Bayes Classification Report:")
print(classification_report(y_test, mnb_predictions))