#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
# LightGBM Implementation

This notebook demonstrates the implementation and usage of LightGBM for classification tasks.
We'll use both the official LightGBM implementation and a simplified custom implementation to understand the algorithm better.

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
import lightgbm as lgb
from sklearn.model_selection import train_test_split, cross_val_score
from sklearn.metrics import (
    accuracy_score, confusion_matrix, classification_report,
    roc_curve, auc, mean_squared_error
)
from sklearn.preprocessing import StandardScaler
import os

# Create output directory for visuals
output_dir = 'algorithms/lightgbm'
os.makedirs(output_dir, exist_ok=True)

# Set random seed for reproducibility
np.random.seed(2220)

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
def train_lightgbm(X, y, params=None):
    """
    Train a LightGBM model using the official implementation.
    
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
    model : lgb.Booster
        Trained model
    """
    if params is None:
        params = {
            'objective': 'binary',
            'metric': 'binary_logloss',
            'boosting_type': 'gbdt',
            'num_leaves': 31,          # Maximum number of leaves in one tree
            'learning_rate': 0.1,      # Step size shrinkage
            'feature_fraction': 0.8,   # Subsample ratio of features
            'bagging_fraction': 0.8,   # Subsample ratio of training instances
            'bagging_freq': 5,         # Frequency of bagging
            'reg_alpha': 0,            # L1 regularization term
            'reg_lambda': 0,           # L2 regularization term
            'drop_rate': 0.1,          # Dropout rate
            'min_data_in_leaf': 20,    # Minimum number of data in one leaf
            'random_state': 2220       # Random seed for reproducibility
        }
    
    # Create dataset
    train_data = lgb.Dataset(X, label=y)
    
    # Train model
    model = lgb.train(params, train_data, num_boost_round=100)
    
    return model

## 4. Training and Evaluation
def evaluate_model(model, X_test, y_test):
    """
    Evaluate the model's performance.
    
    Parameters:
    -----------
    model : lgb.Booster
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
    y_pred_proba = model.predict(X_test)
    y_pred = np.array([1 if prob > 0.5 else 0 for prob in y_pred_proba])
    
    metrics = {
        'accuracy': accuracy_score(y_test, y_pred),
        'confusion_matrix': confusion_matrix(y_test, y_pred),
        'classification_report': classification_report(y_test, y_pred),
        'log_loss': mean_squared_error(y_test, y_pred_proba)
    }
    
    return metrics

## 5. Visualization
def plot_feature_importance(model, feature_names=None):
    """
    Plot feature importance using seaborn and save to PNG file.
    
    Parameters:
    -----------
    model : lgb.Booster
        Trained model
    feature_names : list, optional
        Names of features
    """
    if feature_names is None:
        feature_names = [f'Feature {i}' for i in range(model.num_feature())]
    
    importance = model.feature_importance()
    importance_df = pd.DataFrame({
        'Feature': feature_names,
        'Importance': importance
    }).sort_values('Importance', ascending=False)
    
    plt.figure(figsize=(10, 6))
    sns.barplot(data=importance_df, x='Importance', y='Feature')
    plt.title('Feature Importance')
    filename = f'{output_dir}/lightgbm-feature_importance.png'
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
    filename = f'{output_dir}/lightgbm-confusion_matrix.png'
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
model = train_lightgbm(X_train, y_train)

# Evaluate model
metrics = evaluate_model(model, X_test, y_test)

# Print metrics
print("Accuracy:", metrics['accuracy'])
print("\nClassification Report:")
print(metrics['classification_report'])
print("\nLog Loss:", metrics['log_loss'])

# Plot results
y_pred_proba = model.predict(X_test)
y_pred_binary = np.array([1 if prob > 0.5 else 0 for prob in y_pred_proba])
plot_confusion_matrix(y_test, y_pred_binary, "Confusion Matrix")
plot_feature_importance(model) 