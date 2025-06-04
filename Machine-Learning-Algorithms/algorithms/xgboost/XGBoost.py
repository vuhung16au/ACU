#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
XGBoost Implementation
This module provides both custom and official implementations of XGBoost.
"""

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import xgboost as xgb
from sklearn.model_selection import train_test_split, cross_val_score
from sklearn.metrics import (
    accuracy_score, confusion_matrix, classification_report,
    roc_curve, auc, mean_squared_error
)
from sklearn.preprocessing import StandardScaler
import seaborn as sns

## XGBoost Implementation (Functional, Jupyter-Notebook-Ready)

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import xgboost as xgb
from sklearn.model_selection import train_test_split, cross_val_score
from sklearn.metrics import (
    accuracy_score, confusion_matrix, classification_report,
    roc_curve, auc, mean_squared_error
)
from sklearn.preprocessing import StandardScaler

# Set random seed for reproducibility
np.random.seed(2220)

## 2. Data Generation Function

def generate_data(seed=2220):
    """
    Generate sample data for binary classification.
    Returns:
        tuple: (X, y) features and target
    """
    np.random.seed(seed)
    n_samples = 1000
    n_features = 10
    X = np.random.randn(n_samples, n_features)
    y = np.zeros(n_samples)
    y[X[:, 0] + X[:, 1]**2 + np.sin(X[:, 2]) > 0] = 1
    y += np.random.normal(0, 0.1, n_samples)
    y = (y > 0.5).astype(int)
    return X, y

## 3. Custom XGBoost Implementation (Simplified)

def sigmoid(x):
    """Sigmoid function."""
    return 1 / (1 + np.exp(-np.clip(x, -500, 500)))

def log_loss_gradient(y_true, y_pred):
    """Gradient and hessian for log loss."""
    proba = sigmoid(y_pred)
    gradient = proba - y_true
    hessian = proba * (1 - proba)
    return gradient, hessian

def build_tree(X, gradient, hessian):
    """Dummy tree builder (for demonstration)."""
    tree = {
        'feature': 0,
        'threshold': 0,
        'left': None,
        'right': None,
        'value': 0
    }
    return tree

def predict_tree(X, tree):
    """Dummy tree predictor (for demonstration)."""
    return np.zeros(X.shape[0])

def fit_custom_xgboost(
    X, y,
    n_estimators=100, # Number of boosting rounds
    learning_rate=0.1, # Step size shrinkage
    max_depth=3, # Maximum tree depth
    min_child_weight=1, # Minimum sum of instance weight needed in a child
    subsample=0.8, # Subsample ratio of training instances
    colsample_bytree=0.8, # Subsample ratio of columns when constructing each tree
    reg_alpha=0, # L1 regularization term
    reg_lambda=1, # L2 regularization term
    gamma=0, # Minimum loss reduction required for a split
    random_state=2220 # Random seed
):
    """
    Train a custom (simplified) XGBoost model.
    Hyperparameters explained:
    - n_estimators: Number of boosting rounds (trees)
    - learning_rate: Step size shrinkage (controls contribution of each tree)
    - max_depth: Maximum depth of a tree
    - min_child_weight: Minimum sum of instance weight needed in a child
    - subsample: Subsample ratio of training instances (for bagging)
    - colsample_bytree: Subsample ratio of columns for each tree
    - reg_alpha: L1 regularization term
    - reg_lambda: L2 regularization term
    - gamma: Minimum loss reduction required for a split
    - random_state: Random seed for reproducibility
    Returns: dict with model info
    """
    np.random.seed(random_state)
    n_features = X.shape[1]
    initial_prediction = np.log(np.mean(y) / (1 - np.mean(y)))
    y_pred = np.full_like(y, initial_prediction, dtype=float)
    trees = []
    for _ in range(n_estimators):
        gradient, hessian = log_loss_gradient(y, y_pred)
        if subsample < 1.0:
            indices = np.random.choice(
                X.shape[0], int(X.shape[0] * subsample), replace=False
            )
            X_sub = X[indices]
            gradient_sub = gradient[indices]
            hessian_sub = hessian[indices]
        else:
            X_sub = X
            gradient_sub = gradient
            hessian_sub = hessian
        tree = build_tree(X_sub, gradient_sub, hessian_sub)
        trees.append(tree)
        y_pred += learning_rate * predict_tree(X, tree)
    feature_importances = np.zeros(n_features)
    for tree in trees:
        feature_importances[tree['feature']] += 1
    feature_importances /= n_estimators
    return {
        'trees': trees,
        'feature_importances': feature_importances,
        'initial_prediction': initial_prediction,
        'learning_rate': learning_rate
    }

def predict_custom_xgboost(model, X):
    """Predict class probabilities and labels using custom XGBoost."""
    y_pred = np.full(X.shape[0], model['initial_prediction'], dtype=float)
    for tree in model['trees']:
        y_pred += model['learning_rate'] * predict_tree(X, tree)
    proba = sigmoid(y_pred)
    return np.column_stack((1 - proba, proba)), (proba > 0.5).astype(int)

## 4. Plotting Functions

def plot_feature_importance(feature_importances, feature_names=None):
    """
    Plot feature importance using seaborn and save to PNG file.
    Args:
        feature_importances: Array of importances
        feature_names: List of feature names
    """
    import os
    output_dir = 'algorithms/xgboost'
    os.makedirs(output_dir, exist_ok=True)
    if feature_names is None:
        feature_names = [f'Feature {i}' for i in range(len(feature_importances))]
    plt.figure(figsize=(10, 6))
    sns.barplot(x=feature_importances, y=feature_names, orient='h')
    plt.xlabel('Importance')
    plt.ylabel('Features')
    plt.title('Feature Importance')
    plt.tight_layout()
    filename = f'{output_dir}/xgboost-feature_importance.png'
    plt.savefig(filename)
    plt.close()

def plot_confusion_matrix(y_true, y_pred, title):
    """
    Plot confusion matrix using seaborn and save to PNG file.
    """
    import os
    output_dir = 'algorithms/xgboost'
    os.makedirs(output_dir, exist_ok=True)
    cm = confusion_matrix(y_true, y_pred)
    plt.figure(figsize=(6, 5))
    sns.heatmap(cm, annot=True, fmt='d', cmap='Blues')
    plt.xlabel('Predicted')
    plt.ylabel('Actual')
    plt.title(title)
    filename = f'{output_dir}/xgboost-confusion_matrix.png'
    plt.savefig(filename)
    plt.close()

def plot_roc_curve(y_true, y_proba, title):
    """
    Plot ROC curve using seaborn/matplotlib and save to PNG file.
    """
    import os
    output_dir = 'algorithms/xgboost'
    os.makedirs(output_dir, exist_ok=True)
    fpr, tpr, _ = roc_curve(y_true, y_proba)
    roc_auc = auc(fpr, tpr)
    plt.figure(figsize=(7, 5))
    plt.plot(fpr, tpr, color='darkorange', lw=2, label=f'ROC curve (AUC = {roc_auc:.2f})')
    plt.plot([0, 1], [0, 1], color='navy', lw=2, linestyle='--')
    plt.xlabel('False Positive Rate')
    plt.ylabel('True Positive Rate')
    plt.title(title)
    plt.legend(loc='lower right')
    filename = f'{output_dir}/xgboost-roc_curve.png'
    plt.savefig(filename)
    plt.close()

## 5. Example Usage: Custom XGBoost

# Generate data
X, y = generate_data(seed=2220)

# Split data
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=2220)

# Fit custom XGBoost model
# Hyperparameters explained in fit_custom_xgboost docstring
custom_model = fit_custom_xgboost(
    X_train, y_train,
    n_estimators=50,
    learning_rate=0.1,
    max_depth=3,
    min_child_weight=1,
    subsample=0.8,
    colsample_bytree=0.8,
    reg_alpha=0,
    reg_lambda=1,
    gamma=0,
    random_state=2220
)

# Predict
proba, y_pred = predict_custom_xgboost(custom_model, X_test)

# Evaluate
print("\nCustom XGBoost Classification Report:")
print(classification_report(y_test, y_pred))

# Plot feature importance
plot_feature_importance(custom_model['feature_importances'])

# Plot confusion matrix
plot_confusion_matrix(y_test, y_pred, title='Custom XGBoost Confusion Matrix')

# Plot ROC curve
plot_roc_curve(y_test, proba[:, 1], title='Custom XGBoost ROC Curve')

## 6. Example Usage: Official XGBoost (scikit-learn API)

# Create and fit official XGBoost model
# Hyperparameters explained:
# n_estimators=50: Number of boosting rounds
# learning_rate=0.1: Step size shrinkage
# max_depth=3: Maximum tree depth
# subsample=0.8: Subsample ratio of training instances
# colsample_bytree=0.8: Subsample ratio of columns per tree
# reg_alpha=0: L1 regularization
# reg_lambda=1: L2 regularization
# gamma=0: Minimum loss reduction for split
# random_state=2220: Random seed
xgb_model = xgb.XGBClassifier(
    n_estimators=50,
    learning_rate=0.1,
    max_depth=3,
    subsample=0.8,
    colsample_bytree=0.8,
    reg_alpha=0,
    reg_lambda=1,
    gamma=0,
    use_label_encoder=False,
    eval_metric='logloss',
    random_state=2220
)
xgb_model.fit(X_train, y_train)

# Predict
xgb_pred = xgb_model.predict(X_test)
xgb_proba = xgb_model.predict_proba(X_test)[:, 1]

# Evaluate
print("\nOfficial XGBoost Classification Report:")
print(classification_report(y_test, xgb_pred))

# Plot feature importance
plot_feature_importance(xgb_model.feature_importances_)

# Plot confusion matrix
plot_confusion_matrix(y_test, xgb_pred, title='Official XGBoost Confusion Matrix')

# Plot ROC curve
plot_roc_curve(y_test, xgb_proba, title='Official XGBoost ROC Curve')