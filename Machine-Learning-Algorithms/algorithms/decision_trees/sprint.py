#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
SPRINT (Scalable PaRallelizable INduction of decision Trees) Implementation
This module implements the SPRINT algorithm, which is a scalable and parallelizable
decision tree algorithm designed for handling large datasets efficiently.
"""

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score, confusion_matrix, classification_report
from typing import List, Dict, Tuple, Optional, Union
from concurrent.futures import ProcessPoolExecutor
import multiprocessing
from collections import Counter
import os

# Set random seed for reproducibility
np.random.seed(2220)

def generate_data(n_samples: int = 1000, n_features: int = 10) -> Tuple[np.ndarray, np.ndarray, List[str]]:
    """
    Generate sample data for classification.
    
    Args:
        n_samples (int): Number of samples
        n_features (int): Number of features
        
    Returns:
        tuple: (X, y, feature_names) features, target, and feature names
    """
    if n_samples <= 0 or n_features <= 0:
        raise ValueError("n_samples and n_features must be positive integers")
        
    # Generate features
    X = np.random.randn(n_samples, n_features)
    
    # Generate binary classification target
    y = np.zeros(n_samples)
    y[(X[:, 0] > 0) & (X[:, 2] > 0)] = 1
    y[(X[:, 1] < 0) & (X[:, 3] < 0)] = 1
    
    # Generate feature names
    feature_names = [f'Feature_{i+1}' for i in range(n_features)]
    
    return X, y, feature_names

def calculate_gini(class_counts: Dict[Union[int, float], int]) -> float:
    """
    Calculate Gini impurity from class counts.
    
    Args:
        class_counts (dict): Dictionary of class counts
        
    Returns:
        float: Gini impurity
    """
    total = sum(class_counts.values())
    if total == 0:
        return 0
    return 1 - sum((count / total) ** 2 for count in class_counts.values())

def get_class_counts(y: np.ndarray) -> Dict[Union[int, float], int]:
    """
    Get class counts from target values.
    
    Args:
        y (numpy.ndarray): Target values
        
    Returns:
        dict: Class counts
    """
    return dict(Counter(y))

def find_best_split_parallel(X: np.ndarray, y: np.ndarray, feature: int,
                           min_impurity_decrease: float = 0.0) -> Tuple[float, float, float]:
    """
    Find best split for a feature in parallel.
    
    Args:
        X (numpy.ndarray): Features
        y (numpy.ndarray): Target values
        feature (int): Feature index
        min_impurity_decrease (float): Minimum impurity decrease required for split
        
    Returns:
        tuple: (best_impurity, best_threshold, parent_impurity)
    """
    if not isinstance(X, np.ndarray) or not isinstance(y, np.ndarray):
        raise TypeError("X and y must be numpy arrays")
        
    thresholds = np.unique(X[:, feature])
    best_impurity = float('inf')
    best_threshold = None
    
    # Calculate initial class counts
    class_counts = get_class_counts(y)
    parent_impurity = calculate_gini(class_counts)
    
    for threshold in thresholds:
        left_mask = X[:, feature] <= threshold
        right_mask = ~left_mask
        
        if np.sum(left_mask) == 0 or np.sum(right_mask) == 0:
            continue
        
        left_counts = get_class_counts(y[left_mask])
        right_counts = get_class_counts(y[right_mask])
        
        left_impurity = calculate_gini(left_counts)
        right_impurity = calculate_gini(right_counts)
        
        n_left = np.sum(left_mask)
        n_right = np.sum(right_mask)
        
        impurity = (n_left * left_impurity + n_right * right_impurity) / len(y)
        
        if impurity < best_impurity:
            best_impurity = impurity
            best_threshold = threshold
    
    return best_impurity, best_threshold or 0.0, parent_impurity

def find_best_split(X: np.ndarray, y: np.ndarray, n_jobs: Optional[int],
                   min_impurity_decrease: float = 0.0) -> Tuple[Optional[int], Optional[float], float]:
    """
    Find best split across all features in parallel.
    
    Args:
        X (numpy.ndarray): Features
        y (numpy.ndarray): Target values
        n_jobs (int): Number of parallel jobs
        min_impurity_decrease (float): Minimum impurity decrease required for split
        
    Returns:
        tuple: (best_feature, best_threshold, best_impurity)
    """
    if n_jobs is None:
        n_jobs = multiprocessing.cpu_count()
        
    n_features = X.shape[1]
    
    with ProcessPoolExecutor(max_workers=n_jobs) as executor:
        futures = [executor.submit(find_best_split_parallel, X, y, feature, min_impurity_decrease)
                  for feature in range(n_features)]
        
        results = [future.result() for future in futures]
    
    best_impurity = float('inf')
    best_feature = None
    best_threshold = None
    
    for feature, (impurity, threshold, parent_impurity) in enumerate(results):
        if impurity < best_impurity:
            best_impurity = impurity
            best_feature = feature
            best_threshold = threshold
            
            # Check if split is worth it
            if parent_impurity - best_impurity < min_impurity_decrease:
                return None, None, parent_impurity
    
    return best_feature or 0, best_threshold or 0.0, best_impurity

def most_common_label(class_counts: Dict[Union[int, float], int]) -> Union[int, float]:
    """
    Return the most common label from class counts.
    
    Args:
        class_counts (dict): Dictionary of class counts
        
    Returns:
        Union[int, float]: Most common label
    """
    return max(class_counts.items(), key=lambda x: x[1])[0]

def build_tree(X: np.ndarray, y: np.ndarray, n_jobs: Optional[int] = None,
              max_depth: Optional[int] = None, min_samples_split: int = 2,
              min_impurity_decrease: float = 0.0, depth: int = 0) -> Dict:
    """
    Recursively build the SPRINT decision tree.
    
    Args:
        X (numpy.ndarray): Features
        y (numpy.ndarray): Target values
        n_jobs (int): Number of parallel jobs
        max_depth (int): Maximum depth of the tree
        min_samples_split (int): Minimum samples for split
        min_impurity_decrease (float): Minimum impurity decrease for split
        depth (int): Current depth
        
    Returns:
        dict: Tree node
    """
    if not isinstance(X, np.ndarray) or not isinstance(y, np.ndarray):
        raise TypeError("X and y must be numpy arrays")
        
    n_samples = len(y)
    class_counts = get_class_counts(y)
    n_labels = len(class_counts)
    
    # Calculate current node impurity
    impurity = calculate_gini(class_counts)
    
    # Stopping criteria
    if (max_depth is not None and depth >= max_depth) or \
       n_samples < min_samples_split or \
       n_labels == 1:
        value = most_common_label(class_counts)
        return {
            'value': value,
            'impurity': impurity,
            'n_samples': n_samples,
            'class_counts': class_counts
        }
    
    # Find best split
    best_feature, best_threshold, best_impurity = find_best_split(
        X, y, n_jobs, min_impurity_decrease
    )
    
    if best_feature is None:
        value = most_common_label(class_counts)
        return {
            'value': value,
            'impurity': impurity,
            'n_samples': n_samples,
            'class_counts': class_counts
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
        'class_counts': class_counts,
        'left': build_tree(
            X[left_mask], y[left_mask], n_jobs,
            max_depth, min_samples_split,
            min_impurity_decrease, depth + 1
        ),
        'right': build_tree(
            X[right_mask], y[right_mask], n_jobs,
            max_depth, min_samples_split,
            min_impurity_decrease, depth + 1
        )
    }
    
    return node

def traverse_tree(x: np.ndarray, node: Dict) -> Union[int, float]:
    """
    Traverse the tree to make a prediction.
    
    Args:
        x (numpy.ndarray): Single sample
        node (dict): Current node
        
    Returns:
        Union[int, float]: Predicted class
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
        numpy.ndarray: Predicted labels
    """
    return np.array([traverse_tree(x, tree) for x in X])

def calculate_feature_importance(tree: Dict) -> Dict[int, float]:
    """
    Calculate feature importance based on impurity reduction.
    
    Args:
        tree (dict): Trained tree
        
    Returns:
        dict: Feature importance scores
    """
    importance = {}
    
    def _calculate_importance(node: Dict) -> None:
        if 'value' in node:
            return
            
        feature = node['feature']
        if feature not in importance:
            importance[feature] = 0
            
        # Calculate impurity reduction
        impurity_reduction = (node['impurity'] * node['n_samples'] -
                            node['left']['impurity'] * node['left']['n_samples'] -
                            node['right']['impurity'] * node['right']['n_samples'])
        
        importance[feature] += impurity_reduction
        
        _calculate_importance(node['left'])
        _calculate_importance(node['right'])
    
    _calculate_importance(tree)
    
    # Normalize importance scores
    total = sum(importance.values())
    if total > 0:
        importance = {k: v/total for k, v in importance.items()}
        
    return importance

if __name__ == '__main__':
    # Generate sample data
    X, y, feature_names = generate_data(n_samples=1000, n_features=10)
    
    # Split data into train and test sets
    X_train, X_test, y_train, y_test = train_test_split(
        X, y, test_size=0.2, random_state=42
    )
    
    # Build the tree
    tree = build_tree(
        X_train, y_train,
        n_jobs=multiprocessing.cpu_count(),
        max_depth=5,
        min_samples_split=2,
        min_impurity_decrease=0.01
    )
    
    # Make predictions
    y_pred = predict(X_test, tree)
    
    # Calculate accuracy
    accuracy = accuracy_score(y_test, y_pred)
    print(f"\nAccuracy: {accuracy:.4f}")
    
    # Print classification report
    print("\nClassification Report:")
    print(classification_report(y_test, y_pred))
    
    # Import visualization functions
    from sprint_visualization import (
        plot_confusion_matrix,
        plot_feature_importance,
        plot_tree_structure
    )
    
    # Create output directory
    output_dir = "output"
    os.makedirs(output_dir, exist_ok=True)
    
    # Generate visualizations
    plot_confusion_matrix(
        y_test, y_pred,
        "SPRINT Confusion Matrix",
        os.path.join(output_dir, "confusion_matrix.png")
    )
    
    importance = calculate_feature_importance(tree)
    plot_feature_importance(
        importance, feature_names,
        "SPRINT Feature Importance",
        os.path.join(output_dir, "feature_importance.png")
    )
    
    plot_tree_structure(
        tree, feature_names,
        os.path.join(output_dir, "tree_structure.png")
    )