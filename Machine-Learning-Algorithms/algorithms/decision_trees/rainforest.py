#!/usr/bin/env python3
# -*- coding: utf-8 -*-

## RainForest (Random Forest) Implementation
# This notebook demonstrates the RainForest algorithm, which is a decision tree algorithm
# that uses Attribute-Value-Class (AVC) sets for efficient tree construction. It is
# particularly effective for handling large datasets with categorical features.

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score, confusion_matrix, classification_report
from collections import Counter, defaultdict
from typing import List, Dict, Union, Tuple, Set
import os

# Set random seed for reproducibility
np.random.seed(2220)

## 2. Data Generation and Preprocessing
def generate_data(n_samples: int = 1000, n_features: int = 10):
    """
    Generate sample data for classification.
    
    Args:
        n_samples (int): Number of samples
        n_features (int): Number of features
        
    Returns:
        tuple: (X, y, feature_names) features, target, and feature names
    """
    # Generate features
    X = np.random.randn(n_samples, n_features)
    
    # Generate binary classification target
    y = np.zeros(n_samples)
    y[(X[:, 0] > 0) & (X[:, 2] > 0)] = 1
    y[(X[:, 1] < 0) & (X[:, 3] < 0)] = 1
    
    # Generate feature names
    feature_names = [f'Feature_{i+1}' for i in range(n_features)]
    
    return X, y, feature_names

## 3. RainForest Implementation
def calculate_gini(class_counts: Dict) -> float:
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

def get_class_counts(y: np.ndarray) -> Dict:
    """
    Get class counts from target values.
    
    Args:
        y (numpy.ndarray): Target values
        
    Returns:
        dict: Class counts
    """
    return dict(Counter(y))

def build_avc_sets(X: np.ndarray, y: np.ndarray) -> Dict[int, Dict[float, Dict]]:
    """
    Build Attribute-Value-Class (AVC) sets.
    
    Args:
        X (numpy.ndarray): Feature matrix
        y (numpy.ndarray): Target values
        
    Returns:
        dict: Dictionary mapping features to their AVC sets
    """
    n_features = X.shape[1]
    avc_sets = defaultdict(lambda: defaultdict(lambda: defaultdict(int)))
    
    for i in range(len(X)):
        for feature in range(n_features):
            value = X[i, feature]
            label = y[i]
            avc_sets[feature][value][label] += 1
            
    return avc_sets

def filter_avc_sets(avc_sets: Dict[int, Dict[float, Dict]], 
                   min_size: int) -> Dict[int, Dict[float, Dict]]:
    """
    Filter AVC sets based on minimum size threshold.
    
    Args:
        avc_sets (dict): Original AVC sets
        min_size (int): Minimum size threshold
        
    Returns:
        dict: Filtered AVC sets
    """
    filtered_sets = defaultdict(lambda: defaultdict(lambda: defaultdict(int)))
    
    for feature, value_dict in avc_sets.items():
        for value, class_dict in value_dict.items():
            total = sum(class_dict.values())
            if total >= min_size:
                filtered_sets[feature][value] = class_dict
                
    return filtered_sets

def find_best_split(X: np.ndarray, y: np.ndarray, avc_sets, min_impurity_decrease: float = 0.0) -> Tuple[int, float, float]:
    """
    Find best split for a node.
    
    Returns:
        tuple: (best_feature, best_threshold, best_impurity)
    """
    n_features = X.shape[1]
    best_impurity = float('inf')
    best_feature = None
    best_threshold = None
    
    # Calculate parent impurity
    class_counts = get_class_counts(y)
    parent_impurity = calculate_gini(class_counts)
    
    for feature in range(n_features):
        if feature not in avc_sets:
            continue
            
        # Get unique values for current feature
        values = sorted(avc_sets[feature].keys())
        
        for i in range(len(values) - 1):
            threshold = (values[i] + values[i + 1]) / 2
            
            # Calculate class distributions for split
            left_counts = defaultdict(int)
            right_counts = defaultdict(int)
            
            for value, class_dict in avc_sets[feature].items():
                if value <= threshold:
                    for label, count in class_dict.items():
                        left_counts[label] += count
                else:
                    for label, count in class_dict.items():
                        right_counts[label] += count
            
            # Calculate impurity for split
            n_left = sum(left_counts.values())
            n_right = sum(right_counts.values())
            
            if n_left == 0 or n_right == 0:
                continue
                
            left_impurity = calculate_gini(left_counts)
            right_impurity = calculate_gini(right_counts)
            
            impurity = (n_left * left_impurity + n_right * right_impurity) / len(y)
            
            if impurity < best_impurity:
                best_impurity = impurity
                best_feature = feature
                best_threshold = threshold
    
    # Check if split is worth it
    if best_feature is not None and parent_impurity - best_impurity < min_impurity_decrease:
        return None, None, parent_impurity
        
    return best_feature or 0, best_threshold or 0.0, best_impurity

def most_common_label(class_counts: Dict) -> str:
    """
    Return the most common label from class counts.
    
    Args:
        class_counts (dict): Dictionary of class counts
        
    Returns:
        str: Most common label
    """
    if not class_counts:
        return None
    return max(class_counts.items(), key=lambda x: x[1])[0]

def build_tree(X: np.ndarray, y: np.ndarray,
              max_depth: int = None, min_samples_split: int = 2,
              min_impurity_decrease: float = 0.0, avc_threshold: int = 10,
              depth: int = 0) -> Dict:
    """
    Recursively build the RainForest decision tree.
    
    Hyperparameters:
    - max_depth (int): Maximum depth of the tree. Controls the complexity
      of the model. Higher values can lead to overfitting.
    - min_samples_split (int): Minimum number of samples required to split a node.
      Higher values can help prevent overfitting by ensuring each split has
      enough samples to be statistically significant.
    - min_impurity_decrease (float): Minimum impurity decrease required for split.
      Higher values result in more aggressive pruning, which can help prevent
      overfitting by ensuring each split provides sufficient improvement.
    - avc_threshold (int): Minimum size for AVC sets to be considered. Higher values
      can help reduce memory usage and speed up training by filtering out small
      AVC sets that are unlikely to provide meaningful splits.
    
    Args:
        X (numpy.ndarray): Features
        y (numpy.ndarray): Target values
        max_depth (int): Maximum depth of the tree
        min_samples_split (int): Minimum samples for split
        min_impurity_decrease (float): Minimum impurity decrease for split
        avc_threshold (int): Minimum size for AVC sets
        depth (int): Current depth
        
    Returns:
        dict: Tree node
    """
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
    
    # Build and filter AVC sets
    avc_sets = build_avc_sets(X, y)
    filtered_avc_sets = filter_avc_sets(avc_sets, avc_threshold)
    
    # Find best split
    best_feature, best_threshold, best_impurity = find_best_split(
        X, y, filtered_avc_sets, min_impurity_decrease
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
            X[left_mask], y[left_mask],
            max_depth, min_samples_split,
            min_impurity_decrease, avc_threshold,
            depth + 1
        ),
        'right': build_tree(
            X[right_mask], y[right_mask],
            max_depth, min_samples_split,
            min_impurity_decrease, avc_threshold,
            depth + 1
        )
    }
    
    return node

def traverse_tree(x: np.ndarray, node: Dict) -> str:
    """
    Traverse the tree to make a prediction.
    
    Args:
        x (numpy.ndarray): Single sample
        node (dict): Current node
        
    Returns:
        str: Predicted class
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
    
    def _calculate_importance(node: Dict):
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

## 4. Model Training and Evaluation
# Generate and prepare data
X, y, feature_names = generate_data(
    n_samples=1000,
    n_features=10
)

# Split data
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.2, random_state=2220
)

# Train model
tree = build_tree(
    X_train, y_train,
    max_depth=5,  # Limit tree depth to prevent overfitting
    min_samples_split=5,  # Require at least 5 samples to split a node
    min_impurity_decrease=0.01,  # Minimum impurity decrease for splitting
    avc_threshold=10  # Minimum size for AVC sets
)

# Make predictions
predictions = predict(X_test, tree)

## 5. Model Evaluation
# Print evaluation metrics
print("\nRainForest Implementation Results:")
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
    plt.savefig('algorithms/decision_trees/rainforest_confusion_matrix.png')
    plt.close()

def plot_feature_importance(importance: Dict[int, float], feature_names: List[str], title: str):
    """
    Plot feature importance using seaborn.
    
    Args:
        importance (dict): Feature importance scores
        feature_names (list): List of feature names
        title (str): Plot title
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/decision_trees', exist_ok=True)
    
    plt.figure(figsize=(10, 6))
    features = [feature_names[i] for i in importance.keys()]
    importances = list(importance.values())
    sns.barplot(x=features, y=importances)
    plt.title(title)
    plt.xlabel('Features')
    plt.ylabel('Importance')
    plt.xticks(rotation=45)
    plt.savefig('algorithms/decision_trees/rainforest_feature_importance.png')
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
        
        condition = f'{feature_names[node["feature"]]} ≤ {node["threshold"]:.2f}'
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
    plt.title('RainForest Decision Tree Structure')
    plt.savefig('algorithms/decision_trees/rainforest_tree_structure.png')
    plt.close()

# Create visualizations
plot_confusion_matrix(y_test, predictions,
                     "RainForest Classification Confusion Matrix")
plot_feature_importance(calculate_feature_importance(tree), feature_names,
                       "RainForest Feature Importance")
plot_tree_structure(tree, feature_names)