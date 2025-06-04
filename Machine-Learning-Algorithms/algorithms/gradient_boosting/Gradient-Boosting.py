#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
# Gradient Boosting Implementation

This notebook demonstrates the implementation and usage of Gradient Boosting for classification tasks.
We'll use both scikit-learn's implementation and a custom implementation to understand the algorithm better.

## Table of Contents
1. Import Required Libraries
2. Data Generation
3. Model Implementation
4. Training and Evaluation
5. Visualization
"""

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from sklearn.ensemble import GradientBoostingClassifier
from sklearn.model_selection import train_test_split, cross_val_score
from sklearn.metrics import (
    accuracy_score, confusion_matrix, classification_report,
    roc_curve, auc, log_loss
)
from sklearn.preprocessing import StandardScaler
import os

# Set random seed for reproducibility
np.random.seed(2220)

# Create output directory for visuals
output_dir = 'algorithms/gradient_boosting'
os.makedirs(output_dir, exist_ok=True)

## 2. Data Generation
def generate_data(n_samples=1000, n_features=10):
    """
    Generate sample data for classification with non-linear relationships.
    
    Parameters:
    -----------
    n_samples : int, default=1000
        Number of samples to generate
    n_features : int, default=10
        Number of features to generate
        
    Returns:
    --------
    X : numpy.ndarray
        Generated features
    y : numpy.ndarray
        Generated binary target values
    """
    # Generate features
    X = np.random.randn(n_samples, n_features)
    
    # Generate target with non-linear relationship
    y = np.zeros(n_samples)
    y[X[:, 0] + X[:, 1]**2 + np.sin(X[:, 2]) > 0] = 1
    
    # Add some noise
    y += np.random.normal(0, 0.1, n_samples)
    y = (y > 0.5).astype(int)
    
    return X, y

## 3. Model Implementation
def train_gradient_boosting(X, y, params=None):
    """
    Train a Gradient Boosting model using scikit-learn.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Training features
    y : numpy.ndarray
        Target values
    params : dict, optional
        Model hyperparameters
        
    Returns:
    --------
    model : GradientBoostingClassifier
        Trained model
    """
    if params is None:
        params = {
            'n_estimators': 100,      # Number of boosting stages
            'learning_rate': 0.1,     # Shrinks the contribution of each tree
            'max_depth': 3,           # Maximum depth of the trees
            'min_samples_split': 2,   # Minimum samples required to split a node
            'min_samples_leaf': 1,    # Minimum samples required in a leaf node
            'subsample': 1.0,         # Fraction of samples used for fitting
            'max_features': 'sqrt',   # Number of features to consider for best split
            'random_state': 2220      # Random seed for reproducibility
        }
    
    model = GradientBoostingClassifier(**params)
    model.fit(X, y)
    return model

## 4. Training and Evaluation
def evaluate_model(model, X_test, y_test):
    """
    Evaluate the model's performance.
    
    Parameters:
    -----------
    model : GradientBoostingClassifier
        Trained model
    X_test : numpy.ndarray
        Test features
    y_test : numpy.ndarray
        True test labels
        
    Returns:
    --------
    dict
        Dictionary containing evaluation metrics
    """
    y_pred = model.predict(X_test)
    y_pred_proba = model.predict_proba(X_test)[:, 1]
    
    metrics = {
        'accuracy': accuracy_score(y_test, y_pred),
        'confusion_matrix': confusion_matrix(y_test, y_pred),
        'classification_report': classification_report(y_test, y_pred),
        'log_loss': log_loss(y_test, y_pred_proba)
    }
    
    return metrics

## 5. Visualization
def plot_learning_curve(train_scores, val_scores, title):
    """
    Plot learning curve using seaborn and save to PNG file.
    
    Parameters:
    -----------
    train_scores : list
        Training scores
    val_scores : list
        Validation scores
    title : str
        Plot title
    """
    plt.figure(figsize=(10, 6))
    sns.lineplot(data=pd.DataFrame({
        'Training Score': train_scores,
        'Validation Score': val_scores
    }))
    plt.xlabel('Number of Trees')
    plt.ylabel('Score')
    plt.title(title)
    filename = f'{output_dir}/gradient_boosting-learning_curve.png'
    plt.savefig(filename)
    plt.close()

def plot_feature_importance(model, feature_names=None):
    """
    Plot feature importance using seaborn and save to PNG file.
    
    Parameters:
    -----------
    model : GradientBoostingClassifier
        Trained model
    feature_names : list, optional
        Names of features
    """
    if feature_names is None:
        feature_names = [f'Feature {i}' for i in range(len(model.feature_importances_))]
    
    importance_df = pd.DataFrame({
        'Feature': feature_names,
        'Importance': model.feature_importances_
    }).sort_values('Importance', ascending=False)
    
    plt.figure(figsize=(10, 6))
    sns.barplot(data=importance_df, x='Importance', y='Feature')
    plt.title('Feature Importance')
    filename = f'{output_dir}/gradient_boosting-feature_importance.png'
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
    filename = f'{output_dir}/gradient_boosting-confusion_matrix.png'
    plt.savefig(filename)
    plt.close()

## 6. Main Execution
# Generate data
X, y = generate_data()

# Split data
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.2, random_state=2220
)

# Train model
model = train_gradient_boosting(X_train, y_train)

# Evaluate model
metrics = evaluate_model(model, X_test, y_test)

# Print metrics
print("Accuracy:", metrics['accuracy'])
print("\nClassification Report:")
print(metrics['classification_report'])
print("\nLog Loss:", metrics['log_loss'])

# Plot results
plot_confusion_matrix(y_test, model.predict(X_test), "Confusion Matrix")
plot_feature_importance(model) 