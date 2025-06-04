#!/usr/bin/env python3
# -*- coding: utf-8 -*-

## CART (Classification and Regression Trees) Implementation
# This notebook demonstrates the CART algorithm, which is a decision tree algorithm
# that can be used for both classification and regression tasks. It uses Gini impurity
# for classification and mean squared error for regression as splitting criteria.

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.model_selection import train_test_split
from sklearn.metrics import (
    accuracy_score, confusion_matrix, classification_report,
    mean_squared_error, r2_score
)
from sklearn.preprocessing import StandardScaler
from collections import Counter
from typing import List, Dict, Union, Tuple
import os

# Set random seed for reproducibility
np.random.seed(2220)

## 2. Data Generation and Preprocessing
def generate_data(task: str = 'classification', n_samples: int = 1000, n_features: int = 10):
    """
    Generate sample data for classification or regression.
    
    Args:
        task (str): 'classification' or 'regression'
        n_samples (int): Number of samples
        n_features (int): Number of features
        
    Returns:
        tuple: (X, y) features and target
    """
    # Generate features
    X = np.random.randn(n_samples, n_features)
    
    if task == 'classification':
        # Generate binary classification target
        y = np.zeros(n_samples)
        y[(X[:, 0] > 0) & (X[:, 2] > 0)] = 1
        y[(X[:, 1] < 0) & (X[:, 3] < 0)] = 1
    else:
        # Generate regression target
        y = 2 * X[:, 0] - 3 * X[:, 1] + 0.5 * X[:, 2] + np.random.randn(n_samples) * 0.1
    
    return X, y

## 3. CART Implementation
def calculate_gini(y: np.ndarray) -> float:
    """
    Calculate Gini impurity for classification.
    
    Args:
        y (numpy.ndarray): Target values
        
    Returns:
        float: Gini impurity
    """
    hist = np.bincount(y.astype(int))
    ps = hist / len(y)
    return 1 - np.sum(ps ** 2)

def calculate_mse(y: np.ndarray) -> float:
    """
    Calculate mean squared error for regression.
    
    Args:
        y (numpy.ndarray): Target values
        
    Returns:
        float: Mean squared error
    """
    return np.mean((y - np.mean(y)) ** 2)

def calculate_impurity(y: np.ndarray, task: str) -> float:
    """
    Calculate impurity based on task.
    
    Args:
        y (numpy.ndarray): Target values
        task (str): 'classification' or 'regression'
        
    Returns:
        float: Impurity value
    """
    if task == 'classification':
        return calculate_gini(y)
    return calculate_mse(y)

def find_best_split(X: np.ndarray, y: np.ndarray, task: str,
                   min_impurity_decrease: float = 0.0) -> Tuple[int, float, float]:
    """
    Find best split for a node.
    
    Args:
        X (numpy.ndarray): Features
        y (numpy.ndarray): Target values
        task (str): 'classification' or 'regression'
        min_impurity_decrease (float): Minimum impurity decrease required for split
        
    Returns:
        tuple: (best_feature, best_threshold, best_impurity)
    """
    best_impurity = float('inf')
    best_feature = None
    best_threshold = None
    
    n_samples, n_features = X.shape
    parent_impurity = calculate_impurity(y, task)
    
    for feature in range(n_features):
        thresholds = np.unique(X[:, feature])
        
        for threshold in thresholds:
            left_mask = X[:, feature] <= threshold
            right_mask = ~left_mask
            
            if np.sum(left_mask) == 0 or np.sum(right_mask) == 0:
                continue
            
            left_impurity = calculate_impurity(y[left_mask], task)
            right_impurity = calculate_impurity(y[right_mask], task)
            
            n_left = np.sum(left_mask)
            n_right = np.sum(right_mask)
            
            impurity = (n_left * left_impurity + n_right * right_impurity) / n_samples
            
            if impurity < best_impurity:
                best_impurity = impurity
                best_feature = feature
                best_threshold = threshold
    
    # Check if split is worth it
    if parent_impurity - best_impurity < min_impurity_decrease:
        return None, None, parent_impurity
        
    return best_feature, best_threshold, best_impurity

def find_most_common_label(y: np.ndarray) -> int:
    """
    Return the most common label in a set of labels.
    
    Args:
        y (numpy.ndarray): Target values
        
    Returns:
        int: Most common label
    """
    return int(Counter(y).most_common(1)[0][0])

def build_tree(X: np.ndarray, y: np.ndarray, task: str = 'classification',
              max_depth: int = None, min_samples_split: int = 2,
              min_impurity_decrease: float = 0.0, depth: int = 0) -> Dict:
    """
    Recursively build the CART decision tree.
    
    Hyperparameters:
    - max_depth (int): Maximum depth of the tree. Controls the complexity
      of the model. Higher values can lead to overfitting.
    - min_samples_split (int): Minimum number of samples required to split a node.
      Higher values can help prevent overfitting by ensuring each split has
      enough samples to be statistically significant.
    - min_impurity_decrease (float): Minimum impurity decrease required for split.
      Higher values result in more aggressive pruning, which can help prevent
      overfitting.
    
    Args:
        X (numpy.ndarray): Features
        y (numpy.ndarray): Target values
        task (str): 'classification' or 'regression'
        max_depth (int): Maximum depth of the tree
        min_samples_split (int): Minimum samples for split
        min_impurity_decrease (float): Minimum impurity decrease for split
        depth (int): Current depth
        
    Returns:
        dict: Tree node
    """
    n_samples = len(y)
    n_labels = len(np.unique(y))
    
    # Calculate current node impurity
    impurity = calculate_impurity(y, task)
    
    # Stopping criteria
    if (max_depth is not None and depth >= max_depth) or \
       n_samples < min_samples_split or \
       n_labels == 1:
        if task == 'classification':
            value = find_most_common_label(y)
        else:
            value = np.mean(y)
        return {
            'value': value,
            'impurity': impurity,
            'n_samples': n_samples
        }
    
    # Find best split
    best_feature, best_threshold, best_impurity = find_best_split(
        X, y, task, min_impurity_decrease
    )
    
    if best_feature is None:
        if task == 'classification':
            value = find_most_common_label(y)
        else:
            value = np.mean(y)
        return {
            'value': value,
            'impurity': impurity,
            'n_samples': n_samples
        }
    
    # Create split
    left_mask = X[:, best_feature] <= best_threshold
    right_mask = ~left_mask
    
    # Create node
    node = {
        'feature': best_feature,
        'threshold': best_threshold,
        'impurity': impurity,
        'n_samples': n_samples,
        'left': build_tree(
            X[left_mask], y[left_mask], task,
            max_depth, min_samples_split,
            min_impurity_decrease, depth + 1
        ),
        'right': build_tree(
            X[right_mask], y[right_mask], task,
            max_depth, min_samples_split,
            min_impurity_decrease, depth + 1
        )
    }
    
    return node

def traverse_tree(x: np.ndarray, node: Dict) -> Union[str, float]:
    """
    Traverse the tree to make a prediction.
    
    Args:
        x (numpy.ndarray): Single sample
        node (dict): Current node
        
    Returns:
        Union[str, float]: Predicted value (class label or regression value)
    """
    if 'value' in node:
        return node['value']
    
    if x[node['feature']] <= node['threshold']:
        return traverse_tree(x, node['left'])
    return traverse_tree(x, node['right'])

def predict(X: np.ndarray, tree: Dict) -> np.ndarray:
    """
    Make predictions for input data.
    
    Args:
        X (numpy.ndarray): Input data
        tree (dict): Trained tree
        
    Returns:
        numpy.ndarray: Predicted values
    """
    return np.array([traverse_tree(x, tree) for x in X])

def calculate_feature_importance(tree: Dict) -> Dict[int, float]:
    """
    Calculate feature importance based on impurity decrease.
    
    Args:
        tree (dict): Trained tree
        
    Returns:
        dict: Feature importance scores
    """
    importance = {}
    
    def _calculate_importance(node: Dict):
        if 'value' in node:
            return
            
        # Calculate importance for current node
        n_samples = node['n_samples']
        left_impurity = node['left']['impurity']
        right_impurity = node['right']['impurity']
        left_samples = node['left']['n_samples']
        right_samples = node['right']['n_samples']
        
        importance[node['feature']] = importance.get(node['feature'], 0) + \
            (node['impurity'] - (left_samples * left_impurity + right_samples * right_impurity) / n_samples)
        
        _calculate_importance(node['left'])
        _calculate_importance(node['right'])
    
    _calculate_importance(tree)
    
    # Normalize importance scores
    total = sum(importance.values())
    if total > 0:
        importance = {k: v/total for k, v in importance.items()}
        
    return importance

## 4. Model Training and Evaluation
# Generate and prepare data for both tasks
X_cls, y_cls = generate_data(task='classification')
X_reg, y_reg = generate_data(task='regression')

# Split data for classification
X_cls_train, X_cls_test, y_cls_train, y_cls_test = train_test_split(
    X_cls, y_cls, test_size=0.2, random_state=2220
)

# Split data for regression
X_reg_train, X_reg_test, y_reg_train, y_reg_test = train_test_split(
    X_reg, y_reg, test_size=0.2, random_state=2220
)

# Train classification tree
cls_tree = build_tree(
    X_cls_train, y_cls_train,
    task='classification',
    max_depth=5,  # Limit tree depth to prevent overfitting
    min_samples_split=5,  # Require at least 5 samples to split a node
    min_impurity_decrease=0.01  # Minimum impurity decrease for split
)

# Train regression tree
reg_tree = build_tree(
    X_reg_train, y_reg_train,
    task='regression',
    max_depth=5,  # Limit tree depth to prevent overfitting
    min_samples_split=5,  # Require at least 5 samples to split a node
    min_impurity_decrease=0.01  # Minimum impurity decrease for split
)

# Make predictions
cls_predictions = predict(X_cls_test, cls_tree)
reg_predictions = predict(X_reg_test, reg_tree)

## 5. Model Evaluation
# Print classification metrics
print("\nCART Classification Results:")
print(f"Accuracy: {accuracy_score(y_cls_test, cls_predictions):.4f}")
print("\nClassification Report:")
print(classification_report(y_cls_test, cls_predictions))

# Print regression metrics
print("\nCART Regression Results:")
print(f"MSE: {mean_squared_error(y_reg_test, reg_predictions):.4f}")
print(f"R² Score: {r2_score(y_reg_test, reg_predictions):.4f}")

## 6. Visualization
def plot_confusion_matrix(y_true: np.ndarray, y_pred: np.ndarray, title: str):
    """
    Plot confusion matrix using seaborn and save to a PNG file.
    
    Args:
        y_true (numpy.ndarray): True labels
        y_pred (numpy.ndarray): Predicted labels
        title (str): Plot title
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/decision_trees', exist_ok=True)
    
    cm = confusion_matrix(y_true, y_pred)
    plt.figure(figsize=(8, 6))
    sns.heatmap(cm, annot=True, fmt='d', cmap='Blues',
                xticklabels=['Class 0', 'Class 1'],
                yticklabels=['Class 0', 'Class 1'])
    plt.title(title)
    plt.xlabel('Predicted')
    plt.ylabel('True')
    plt.savefig(f'algorithms/decision_trees/{title.replace(" ", "_").lower()}_confusion_matrix.png')
    plt.close()

def plot_regression_results(y_true: np.ndarray, y_pred: np.ndarray, title: str):
    """
    Plot regression results using seaborn and save to a PNG file.
    
    Args:
        y_true (numpy.ndarray): True values
        y_pred (numpy.ndarray): Predicted values
        title (str): Plot title
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/decision_trees', exist_ok=True)
    
    plt.figure(figsize=(8, 6))
    sns.scatterplot(x=y_true, y=y_pred)
    plt.plot([y_true.min(), y_true.max()], [y_true.min(), y_true.max()], 'r--')
    plt.title(title)
    plt.xlabel('True Values')
    plt.ylabel('Predicted Values')
    plt.savefig(f'algorithms/decision_trees/{title.replace(" ", "_").lower()}_regression_results.png')
    plt.close()

def plot_feature_importance(importance: Dict[int, float], title: str):
    """
    Plot feature importance using seaborn and save to a PNG file.
    
    Args:
        importance (dict): Feature importance scores
        title (str): Plot title
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/decision_trees', exist_ok=True)
    
    plt.figure(figsize=(8, 6))
    sorted_keys = sorted(importance.keys())
    features = [f'Feature {i}' for i in sorted_keys]
    importances = [importance[i] for i in sorted_keys]
    sns.barplot(x=features, y=importances)
    plt.title(title)
    plt.xlabel('Features')
    plt.ylabel('Importance')
    plt.xticks(rotation=45)
    plt.savefig(f'algorithms/decision_trees/{title.replace(" ", "_").lower()}_feature_importance.png')
    plt.close()

# Create visualizations
plot_confusion_matrix(y_cls_test, cls_predictions,
                     "CART Classification Confusion Matrix")
plot_regression_results(y_reg_test, reg_predictions,
                       "CART Regression Results")
plot_feature_importance(calculate_feature_importance(cls_tree),
                       "CART Classification Feature Importance")
plot_feature_importance(calculate_feature_importance(reg_tree),
                       "CART Regression Feature Importance")