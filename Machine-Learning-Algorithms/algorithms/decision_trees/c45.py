#!/usr/bin/env python3
# -*- coding: utf-8 -*-

## C4.5 Decision Tree Implementation
# This notebook demonstrates the C4.5 algorithm, which is an extension of ID3 that can handle
# both categorical and continuous features. It uses gain ratio as its splitting criterion
# and includes pruning to prevent overfitting.

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.model_selection import train_test_split
from sklearn.metrics import (
    accuracy_score, confusion_matrix, classification_report,
    roc_curve, auc
)
from sklearn.preprocessing import StandardScaler
from collections import Counter
from typing import List, Dict, Union, Tuple, Optional
import os

# Set random seed for reproducibility
np.random.seed(2220)

## 2. Data Generation and Preprocessing
def generate_data(n_samples: int = 1000, n_continuous: int = 2, n_categorical: int = 2):
    """
    Generate sample data for binary classification with both categorical and continuous features.
    
    Args:
        n_samples (int): Number of samples
        n_continuous (int): Number of continuous features
        n_categorical (int): Number of categorical features
        
    Returns:
        tuple: (X, y, feature_names, feature_types) features, target, feature names and types
    """
    # Generate continuous features
    X_continuous = np.random.randn(n_samples, n_continuous)
    
    # Generate categorical features
    X_categorical = np.random.randint(0, 2, size=(n_samples, n_categorical))
    
    # Combine features
    X = np.column_stack([X_continuous, X_categorical])
    
    # Generate target based on feature values
    y = np.zeros(n_samples)
    y[(X[:, 0] > 0) & (X[:, 2] == 1)] = 1
    y[(X[:, 1] < 0) & (X[:, 3] == 1)] = 1
    
    # Define feature names and types
    feature_names = ([f'Continuous_{i+1}' for i in range(n_continuous)] +
                    [f'Categorical_{i+1}' for i in range(n_categorical)])
    feature_types = (['continuous'] * n_continuous +
                    ['categorical'] * n_categorical)
    
    return X, y, feature_names, feature_types

## 3. C4.5 Implementation
def calculate_entropy(y: np.ndarray) -> float:
    """
    Calculate entropy of a target variable.
    
    Args:
        y (numpy.ndarray): Target values
        
    Returns:
        float: Entropy value
    """
    hist = np.bincount(y.astype(int))
    ps = hist / len(y)
    return -np.sum([p * np.log2(p) for p in ps if p > 0])

def calculate_split_info(X: np.ndarray, feature: int) -> float:
    """
    Calculate split information for a feature.
    
    Args:
        X (numpy.ndarray): Features
        feature (int): Feature index
        
    Returns:
        float: Split information value
    """
    unique_values = np.unique(X[:, feature])
    split_info = 0
    
    for value in unique_values:
        mask = X[:, feature] == value
        if np.sum(mask) > 0:
            p = np.sum(mask) / len(X)
            split_info -= p * np.log2(p)
            
    return split_info

def calculate_gain_ratio(X: np.ndarray, y: np.ndarray, feature: int) -> float:
    """
    Calculate gain ratio for a feature.
    
    Args:
        X (numpy.ndarray): Features
        y (numpy.ndarray): Target values
        feature (int): Feature index
        
    Returns:
        float: Gain ratio value
    """
    parent_entropy = calculate_entropy(y)
    
    # Calculate information gain
    unique_values = np.unique(X[:, feature])
    child_entropy = 0
    
    for value in unique_values:
        mask = X[:, feature] == value
        if np.sum(mask) > 0:
            child_entropy += (np.sum(mask) / len(y)) * calculate_entropy(y[mask])
    
    information_gain = parent_entropy - child_entropy
    
    # Calculate split information
    split_info = calculate_split_info(X, feature)
    
    # Avoid division by zero
    if split_info == 0:
        return 0
        
    return information_gain / split_info

def find_best_split_continuous(X: np.ndarray, y: np.ndarray, feature: int) -> Tuple[float, Optional[float]]:
    """
    Find best split point for continuous feature.
    
    Args:
        X (numpy.ndarray): Features
        y (numpy.ndarray): Target values
        feature (int): Feature index
        
    Returns:
        tuple: (best_gain_ratio, best_threshold)
    """
    unique_values = np.unique(X[:, feature])
    best_gain_ratio = -1
    best_threshold = None
    
    for i in range(len(unique_values) - 1):
        threshold = (unique_values[i] + unique_values[i + 1]) / 2
        X_temp = X.copy()
        X_temp[:, feature] = X_temp[:, feature] <= threshold
        
        gain_ratio = calculate_gain_ratio(X_temp, y, feature)
        
        if gain_ratio > best_gain_ratio:
            best_gain_ratio = gain_ratio
            best_threshold = threshold
            
    return best_gain_ratio, best_threshold

def find_best_feature(X: np.ndarray, y: np.ndarray, features: List[str],
                     feature_types: List[str]) -> Tuple[Optional[str], Optional[float], bool]:
    """
    Find the best feature to split on.
    
    Args:
        X (numpy.ndarray): Features
        y (numpy.ndarray): Target values
        features (list): List of feature names
        feature_types (list): List of feature types
        
    Returns:
        tuple: (best_feature, best_threshold, is_continuous)
    """
    best_gain_ratio = -1
    best_feature = None
    best_threshold = None
    is_continuous = False
    
    for i, (feature, feature_type) in enumerate(zip(features, feature_types)):
        if feature_type == 'continuous':
            gain_ratio, threshold = find_best_split_continuous(X, y, i)
            if gain_ratio > best_gain_ratio:
                best_gain_ratio = gain_ratio
                best_feature = feature
                best_threshold = threshold
                is_continuous = True
        else:
            gain_ratio = calculate_gain_ratio(X, y, i)
            if gain_ratio > best_gain_ratio:
                best_gain_ratio = gain_ratio
                best_feature = feature
                best_threshold = None
                is_continuous = False
                
    return best_feature, best_threshold, is_continuous

def find_most_common_label(y: np.ndarray) -> int:
    """
    Return the most common label in a set of labels.
    If y is empty, return -1.
    Args:
        y (numpy.ndarray): Target values
    Returns:
        int: Most common label
    """
    if len(y) == 0:
        return -1
    return int(Counter(y).most_common(1)[0][0])

def build_tree(X: np.ndarray, y: np.ndarray, features: List[str],
              feature_types: List[str], max_depth: Optional[int] = None,
              min_samples_split: int = 2, min_gain_ratio: float = 0.01,
              depth: int = 0) -> Dict:
    """
    Recursively build the C4.5 decision tree.
    
    Hyperparameters:
    - max_depth (int): Maximum depth of the tree. Controls the complexity
      of the model. Higher values can lead to overfitting.
    - min_samples_split (int): Minimum number of samples required to split a node.
      Higher values can help prevent overfitting by ensuring each split has
      enough samples to be statistically significant.
    - min_gain_ratio (float): Minimum gain ratio required to split a node.
      Higher values result in more aggressive pruning, which can help prevent
      overfitting by ensuring each split provides sufficient information gain.
    
    Args:
        X (numpy.ndarray): Features
        y (numpy.ndarray): Target values
        features (list): List of feature names
        feature_types (list): List of feature types
        max_depth (int): Maximum depth of the tree
        min_samples_split (int): Minimum samples for split
        min_gain_ratio (float): Minimum gain ratio for split
        depth (int): Current depth
        
    Returns:
        dict: Tree node
    """
    n_samples = len(y)
    n_labels = len(np.unique(y))
    
    # Stopping criteria
    if (max_depth is not None and depth >= max_depth) or \
       n_samples < min_samples_split or \
       n_labels == 1 or \
       len(features) == 0:
        return {'value': find_most_common_label(y)}
    
    # Find best feature
    best_feature, best_threshold, is_continuous = find_best_feature(
        X, y, features, feature_types
    )
    if best_feature is None:
        return {'value': find_most_common_label(y)}
    
    # Calculate gain ratio for best feature
    feature_idx = features.index(best_feature)
    if is_continuous:
        X_temp = X.copy()
        X_temp[:, feature_idx] = X_temp[:, feature_idx] <= best_threshold
        gain_ratio = calculate_gain_ratio(X_temp, y, feature_idx)
    else:
        gain_ratio = calculate_gain_ratio(X, y, feature_idx)
    
    # Check if gain ratio is sufficient
    if gain_ratio < min_gain_ratio:
        return {'value': find_most_common_label(y)}
    
    # Create node
    node = {
        'feature': best_feature,
        'threshold': best_threshold,
        'is_continuous': is_continuous
    }
    
    # Create splits
    if is_continuous:
        left_mask = X[:, feature_idx] <= best_threshold
        right_mask = ~left_mask
    else:
        left_mask = X[:, feature_idx] == 0
        right_mask = ~left_mask
    
    # Remove used feature
    remaining_features = features.copy()
    remaining_feature_types = feature_types.copy()
    remaining_features.pop(feature_idx)
    remaining_feature_types.pop(feature_idx)
    
    # Create child nodes
    node['left'] = build_tree(
        X[left_mask], y[left_mask],
        remaining_features, remaining_feature_types,
        max_depth, min_samples_split, min_gain_ratio,
        depth + 1
    )
    node['right'] = build_tree(
        X[right_mask], y[right_mask],
        remaining_features, remaining_feature_types,
        max_depth, min_samples_split, min_gain_ratio,
        depth + 1
    )
    
    return node

def traverse_tree(x: np.ndarray, node: Dict, feature_names: List[str]) -> int:
    """
    Traverse the tree to make a prediction.
    Args:
        x (numpy.ndarray): Single sample
        node (dict): Current node
        feature_names (list): List of feature names
    Returns:
        int: Predicted class
    """
    if 'value' in node:
        return node['value']
    
    feature_idx = feature_names.index(node['feature'])
    
    if node['is_continuous']:
        if x[feature_idx] <= node['threshold']:
            return traverse_tree(x, node['left'], feature_names)
        return traverse_tree(x, node['right'], feature_names)
    else:
        if x[feature_idx] == 0:
            return traverse_tree(x, node['left'], feature_names)
        return traverse_tree(x, node['right'], feature_names)

def predict(X: np.ndarray, tree: Dict, feature_names: List[str]) -> np.ndarray:
    """
    Make predictions for input data.
    
    Args:
        X (numpy.ndarray): Input data
        tree (dict): Trained tree
        feature_names (list): List of feature names
        
    Returns:
        numpy.ndarray: Predicted labels
    """
    return np.array([traverse_tree(x, tree, feature_names) for x in X])

def calculate_feature_importance(tree: Dict, feature_names: List[str]) -> Dict[str, float]:
    """
    Calculate feature importance based on usage in tree.
    
    Args:
        tree (dict): Trained tree
        feature_names (list): List of feature names
        
    Returns:
        dict: Feature importance scores
    """
    importance = {feature: 0.0 for feature in feature_names}
    
    def _calculate_importance(node: Dict):
        if 'value' in node:
            return
            
        feature = node['feature']
        importance[feature] += 1
        
        _calculate_importance(node['left'])
        _calculate_importance(node['right'])
    
    _calculate_importance(tree)
    
    # Normalize importance scores
    total = sum(importance.values())
    if total > 0:
        importance = {k: v/total for k, v in importance.items()}
        
    return importance

## 4. Model Training and Evaluation
# Generate and prepare data
X, y, feature_names, feature_types = generate_data(
    n_samples=1000,
    n_continuous=2,
    n_categorical=2
)

# Split data
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.2, random_state=2220
)

# Train model
tree = build_tree(
    X_train, y_train,
    feature_names, feature_types,
    max_depth=5,  # Limit tree depth to prevent overfitting
    min_samples_split=5,  # Require at least 5 samples to split a node
    min_gain_ratio=0.01  # Minimum gain ratio for splitting
)

# Make predictions
predictions = predict(X_test, tree, feature_names)

## 5. Model Evaluation
# Print evaluation metrics
print("\nC4.5 Implementation Results:")
print(f"Accuracy: {accuracy_score(y_test, predictions):.4f}")
print("\nClassification Report:")
print(classification_report(y_test, predictions))

## 6. Visualization
def plot_confusion_matrix(y_true: np.ndarray, y_pred: np.ndarray, title: str):
    """
    Plot confusion matrix using seaborn.
    
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
    plt.savefig('algorithms/decision_trees/c45_confusion_matrix.png')
    plt.close()

def plot_feature_importance(importance: Dict[str, float], title: str):
    """
    Plot feature importance using seaborn.
    
    Args:
        importance (dict): Feature importance scores
        title (str): Plot title
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/decision_trees', exist_ok=True)
    
    plt.figure(figsize=(10, 6))
    features = list(importance.keys())
    importances = list(importance.values())
    sns.barplot(x=features, y=importances)
    plt.title(title)
    plt.xlabel('Features')
    plt.ylabel('Importance')
    plt.xticks(rotation=45)
    plt.savefig('algorithms/decision_trees/c45_feature_importance.png')
    plt.close()

def plot_tree_structure(tree: Dict, feature_names: List[str]):
    """
    Plot the decision tree structure.
    
    Args:
        tree (dict): Trained tree
        feature_names (list): List of feature names
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/decision_trees', exist_ok=True)
    
    def get_tree_depth(node: Dict) -> int:
        if 'value' in node:
            return 0
        return 1 + max(get_tree_depth(node['left']), get_tree_depth(node['right']))
    
    def plot_node(node: Dict, x: float, y: float, width: float):
        if 'value' in node:
            plt.text(x, y, f'Class {node["value"]}', 
                    bbox=dict(facecolor='white', alpha=0.5),
                    ha='center', va='center')
            return
        
        if node['is_continuous']:
            condition = f'{node["feature"]} ≤ {node["threshold"]:.2f}'
        else:
            condition = f'{node["feature"]} == 0'
            
        plt.text(x, y, condition,
                bbox=dict(facecolor='white', alpha=0.5),
                ha='center', va='center')
        
        child_y = y - 1
        left_x = x - width/4
        right_x = x + width/4
        
        plt.plot([x, left_x], [y, child_y], 'k-')
        plt.plot([x, right_x], [y, child_y], 'k-')
        
        plt.text((x + left_x)/2, (y + child_y)/2, 'True',
                ha='center', va='center')
        plt.text((x + right_x)/2, (y + child_y)/2, 'False',
                ha='center', va='center')
        
        plot_node(node['left'], left_x, child_y, width/2)
        plot_node(node['right'], right_x, child_y, width/2)
    
    depth = get_tree_depth(tree)
    plt.figure(figsize=(12, depth*2))
    plot_node(tree, 0.5, depth, 1)
    plt.axis('off')
    plt.title('C4.5 Decision Tree Structure')
    plt.savefig('algorithms/decision_trees/c45_tree_structure.png')
    plt.close()