#!/usr/bin/env python3
# -*- coding: utf-8 -*-

## ID3 (Iterative Dichotomiser 3) Implementation
# This notebook demonstrates the ID3 algorithm, which is a decision tree learning algorithm
# that uses information gain as its splitting criterion. It's particularly well-suited
# for categorical features and binary classification tasks.

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
from typing import List, Dict, Union, Tuple
import os

# Set random seed for reproducibility
np.random.seed(2220)

## 2. Data Generation and Preprocessing
def generate_data(n_samples: int = 1000, n_features: int = 5):
    """
    Generate sample data for binary classification.
    
    Args:
        n_samples (int): Number of samples to generate
        n_features (int): Number of features to generate
        
    Returns:
        tuple: (X, y, feature_names) features, target, and feature names
    """
    # Generate features (categorical)
    X = np.random.randint(0, 2, size=(n_samples, n_features))
    
    # Generate target based on feature values
    y = np.zeros(n_samples)
    y[(X[:, 0] == 1) & (X[:, 1] == 1)] = 1
    y[(X[:, 0] == 0) & (X[:, 2] == 1)] = 1
    
    # Generate feature names
    feature_names = [f'Feature_{i+1}' for i in range(n_features)]
    
    return X, y, feature_names

## 3. ID3 Implementation
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

def calculate_information_gain(X: np.ndarray, y: np.ndarray, feature: int) -> float:
    """
    Calculate information gain for a feature.
    
    Args:
        X (numpy.ndarray): Features
        y (numpy.ndarray): Target values
        feature (int): Feature index
        
    Returns:
        float: Information gain
    """
    parent_entropy = calculate_entropy(y)
    
    # Calculate weighted entropy of children
    unique_values = np.unique(X[:, feature])
    child_entropy = 0
    
    for value in unique_values:
        mask = X[:, feature] == value
        if np.sum(mask) > 0:
            child_entropy += (np.sum(mask) / len(y)) * calculate_entropy(y[mask])
    
    return parent_entropy - child_entropy

def find_best_feature(X: np.ndarray, y: np.ndarray, features: List[str]) -> str:
    """
    Find the best feature to split on.
    
    Args:
        X (numpy.ndarray): Features
        y (numpy.ndarray): Target values
        features (list): List of feature names
        
    Returns:
        str: Best feature name
    """
    best_gain = -1
    best_feature = None
    
    for i, feature in enumerate(features):
        gain = calculate_information_gain(X, y, i)
        if gain > best_gain:
            best_gain = gain
            best_feature = feature
            
    return best_feature

def find_most_common_label(y: np.ndarray) -> str:
    """
    Return the most common label in a set of labels.
    
    Args:
        y (numpy.ndarray): Target values
        
    Returns:
        str: Most common label
    """
    return str(Counter(y).most_common(1)[0][0])

def build_tree(X: np.ndarray, y: np.ndarray, features: List[str],
              max_depth: int = None, min_samples_split: int = 2,
              min_information_gain: float = 0.0, depth: int = 0) -> Dict:
    """
    Recursively build the ID3 decision tree.
    
    Hyperparameters:
    - max_depth (int): Maximum depth of the tree. Controls the complexity
      of the model. Higher values can lead to overfitting.
    - min_samples_split (int): Minimum number of samples required to split a node.
      Higher values can help prevent overfitting by ensuring each split has
      enough samples to be statistically significant.
    - min_information_gain (float): Minimum information gain required for split.
      Higher values result in more aggressive pruning, which can help prevent
      overfitting by ensuring each split provides sufficient information gain.
    
    Args:
        X (numpy.ndarray): Features
        y (numpy.ndarray): Target values
        features (list): List of feature names
        max_depth (int): Maximum depth of the tree
        min_samples_split (int): Minimum samples for split
        min_information_gain (float): Minimum information gain for split
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
    best_feature = find_best_feature(X, y, features)
    if best_feature is None:
        return {'value': find_most_common_label(y)}
    
    # Get feature index
    feature_idx = features.index(best_feature)
    
    # Calculate information gain
    information_gain = calculate_information_gain(X, y, feature_idx)
    
    # Check if split is worth it
    if information_gain < min_information_gain:
        return {'value': find_most_common_label(y)}
    
    # Create node
    node = {'feature': best_feature, 'children': {}}
    
    # Create child nodes for each value of the feature
    unique_values = np.unique(X[:, feature_idx])
    remaining_features = [f for f in features if f != best_feature]
    
    for value in unique_values:
        mask = X[:, feature_idx] == value
        if np.sum(mask) > 0:
            node['children'][value] = build_tree(
                X[mask], y[mask], remaining_features,
                max_depth, min_samples_split,
                min_information_gain, depth + 1
            )
        else:
            node['children'][value] = {'value': find_most_common_label(y)}
    
    return node

def traverse_tree(x: np.ndarray, node: Dict, feature_names: List[str]) -> str:
    """
    Traverse the tree to make a prediction.
    
    Args:
        x (numpy.ndarray): Single sample
        node (dict): Current node
        feature_names (list): List of feature names
        
    Returns:
        str: Predicted class
    """
    if 'value' in node:
        return node['value']
    
    feature_idx = feature_names.index(node['feature'])
    value = x[feature_idx]
    
    if value in node['children']:
        return traverse_tree(x, node['children'][value], feature_names)
    else:
        # Handle unseen values by returning most common class
        return find_most_common_label(np.array([child['value'] for child in node['children'].values()]))

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
    Calculate feature importance based on information gain.
    
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
        
        for child in node['children'].values():
            _calculate_importance(child)
    
    _calculate_importance(tree)
    
    # Normalize importance scores
    total = sum(importance.values())
    if total > 0:
        importance = {k: v/total for k, v in importance.items()}
        
    return importance

def find_best_split(X: np.ndarray, y: np.ndarray, task: str,
                   min_impurity_decrease: float = 0.0) -> Tuple[int, float, float]:
    """
    Find best split for a node.
    
    Returns:
        tuple: (best_feature, best_threshold, best_impurity)
    """
    best_feature = None
    best_threshold = None
    best_impurity = float('inf')
    
    for feature in range(X.shape[1]):
        thresholds = np.unique(X[:, feature])
        
        for threshold in thresholds:
            # Split data
            left_mask = X[:, feature] <= threshold
            right_mask = X[:, feature] > threshold
            
            if np.sum(left_mask) == 0 or np.sum(right_mask) == 0:
                continue
            
            # Calculate impurity (Gini impurity for classification)
            impurity = 1.0 - sum((np.sum(y[left_mask] == c) / np.sum(left_mask))**2 for c in np.unique(y)) \
                       - sum((np.sum(y[right_mask] == c) / np.sum(right_mask))**2 for c in np.unique(y))
            
            # Check if this is the best split so far
            if impurity < best_impurity - min_impurity_decrease:
                best_impurity = impurity
                best_feature = feature
                best_threshold = threshold
    
    return best_feature or 0, best_threshold or 0.0, best_impurity

## 4. Model Training and Evaluation
# Generate and prepare data
X, y, feature_names = generate_data(
    n_samples=1000,
    n_features=5
)

# Split data
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.2, random_state=2220
)

# Train model
tree = build_tree(
    X_train, y_train, feature_names,
    max_depth=5,  # Limit tree depth to prevent overfitting
    min_samples_split=5,  # Require at least 5 samples to split a node
    min_information_gain=0.01  # Minimum information gain for splitting
)

# Make predictions
predictions = predict(X_test, tree, feature_names)

## 5. Model Evaluation
# Print evaluation metrics
print("\nID3 Implementation Results:")
print(f"Accuracy: {accuracy_score(y_test, predictions):.4f}")
print("\nClassification Report:")
print(classification_report([str(label) for label in y_test], [str(label) for label in predictions]))

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
    plt.savefig('algorithms/decision_trees/id3_confusion_matrix.png')
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
    plt.savefig('algorithms/decision_trees/id3_feature_importance.png')
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
        return 1 + max(get_tree_depth(child) for child in node['children'].values())
    
    def plot_node(node: Dict, x: float, y: float, width: float):
        if 'value' in node:
            plt.text(x, y, f'Class {node["value"]}', 
                    bbox=dict(facecolor='white', alpha=0.5),
                    ha='center', va='center')
            return
        
        plt.text(x, y, node['feature'],
                bbox=dict(facecolor='white', alpha=0.5),
                ha='center', va='center')
        
        # Plot children
        n_children = len(node['children'])
        child_y = y - 1
        child_width = width / n_children
        
        for i, (value, child) in enumerate(node['children'].items()):
            child_x = x - width/2 + (i + 0.5) * child_width
            plt.plot([x, child_x], [y, child_y], 'k-')
            plt.text((x + child_x)/2, (y + child_y)/2, f'= {value}',
                    ha='center', va='center')
            plot_node(child, child_x, child_y, child_width)
    
    depth = get_tree_depth(tree)
    plt.figure(figsize=(12, depth*2))
    plot_node(tree, 0.5, depth, 1)
    plt.axis('off')
    plt.title('ID3 Decision Tree Structure')
    plt.savefig('algorithms/decision_trees/id3_tree_structure.png')
    plt.close()

# Create visualizations
plot_confusion_matrix([str(label) for label in y_test], [str(label) for label in predictions],
                     "ID3 Classification Confusion Matrix")
plot_feature_importance(calculate_feature_importance(tree, feature_names),
                       "ID3 Feature Importance")
plot_tree_structure(tree, feature_names)