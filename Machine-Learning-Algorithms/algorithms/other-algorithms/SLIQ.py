# SLIQ (Supervised Learning In Quest) Implementation
# This notebook demonstrates the implementation of the SLIQ decision tree algorithm.

## Table of Contents
# 1. Import Required Libraries
# 2. Data Generation and Preprocessing
# 3. SLIQ Implementation
# 4. Model Training and Evaluation
# 5. Visualization
# 6. Results and Analysis

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import os
from collections import defaultdict
from sklearn.metrics import accuracy_score, classification_report
from sklearn.datasets import make_classification

# Set random seed for reproducibility
np.random.seed(2220)

## 2. Data Generation and Preprocessing
def generate_data(n_samples=300, n_features=10, n_classes=2, random_state=2220):
    """
    Generate sample data for classification.
    
    Parameters:
    -----------
    n_samples : int, default=300
        Number of samples to generate
    n_features : int, default=10
        Number of features
    n_classes : int, default=2
        Number of classes
    random_state : int, default=2220
        Random seed for reproducibility
        
    Returns:
    --------
    tuple
        X : numpy.ndarray
            Features array of shape (n_samples, n_features)
        y : numpy.ndarray
            Target labels
    """
    X, y = make_classification(
        n_samples=n_samples,
        n_features=n_features,
        n_classes=n_classes,
        n_informative=n_features//2,
        random_state=random_state
    )
    return X, y

## 3. SLIQ Implementation
def calculate_gini(class_distribution):
    """
    Calculate Gini index for a node.
    
    Parameters:
    -----------
    class_distribution : dict
        Dictionary mapping class labels to their counts
        
    Returns:
    --------
    float
        Gini index value
    """
    total = sum(class_distribution.values())
    if total == 0:
        return 0
    return 1 - sum((count/total)**2 for count in class_distribution.values())

def find_best_split(node_id, attribute_lists, class_list):
    """
    Find the best split for a node.
    
    Parameters:
    -----------
    node_id : int
        ID of the current node
    attribute_lists : dict
        Dictionary mapping feature indices to their attribute lists
    class_list : dict
        Dictionary containing class values and node IDs
        
    Returns:
    --------
    tuple
        best_feature : int or None
            Index of best feature for splitting
        best_split : float or None
            Best split value
    """
    best_gini = float('inf')
    best_feature = None
    best_split = None
    
    # Get samples in current node
    node_mask = class_list['node_ids'] == node_id
    node_classes = class_list['class_values'][node_mask]
    
    for feature, attr_list in attribute_lists.items():
        if np.issubdtype(attr_list['attribute_values'].dtype, np.number):
            # For continuous attributes
            sorted_indices = np.argsort(attr_list['attribute_values'])
            sorted_values = attr_list['attribute_values'][sorted_indices]
            sorted_classes = attr_list['class_values'][sorted_indices]
            sorted_nodes = attr_list['node_ids'][sorted_indices]
            
            # Only consider values in current node
            node_mask = sorted_nodes == node_id
            sorted_values = sorted_values[node_mask]
            sorted_classes = sorted_classes[node_mask]
            
            for i in range(len(sorted_values)-1):
                if sorted_values[i] != sorted_values[i+1]:
                    split_value = (sorted_values[i] + sorted_values[i+1]) / 2
                    left_mask = sorted_values <= split_value
                    right_mask = ~left_mask
                    
                    left_dist = defaultdict(int)
                    right_dist = defaultdict(int)
                    
                    for c in sorted_classes[left_mask]:
                        left_dist[c] += 1
                    for c in sorted_classes[right_mask]:
                        right_dist[c] += 1
                        
                    gini = (len(left_mask) * calculate_gini(left_dist) +
                           len(right_mask) * calculate_gini(right_dist)) / len(sorted_values)
                    
                    if gini < best_gini:
                        best_gini = gini
                        best_feature = feature
                        best_split = split_value
        else:
            # For categorical attributes
            unique_values = np.unique(attr_list['attribute_values'])
            for value in unique_values:
                value_mask = (attr_list['attribute_values'] == value) & (attr_list['node_ids'] == node_id)
                other_mask = (attr_list['attribute_values'] != value) & (attr_list['node_ids'] == node_id)
                
                value_dist = defaultdict(int)
                other_dist = defaultdict(int)
                
                for c in attr_list['class_values'][value_mask]:
                    value_dist[c] += 1
                for c in attr_list['class_values'][other_mask]:
                    other_dist[c] += 1
                    
                gini = (np.sum(value_mask) * calculate_gini(value_dist) +
                       np.sum(other_mask) * calculate_gini(other_dist)) / np.sum(node_mask)
                
                if gini < best_gini:
                    best_gini = gini
                    best_feature = feature
                    best_split = value
                    
    return best_feature, best_split

def update_node_assignments(node, left_node_id, right_node_id, attribute_lists, class_list):
    """
    Update node assignments after splitting.
    
    Parameters:
    -----------
    node : dict
        Current node information
    left_node_id : int
        ID of the left child node
    right_node_id : int
        ID of the right child node
    attribute_lists : dict
        Dictionary mapping feature indices to their attribute lists
    class_list : dict
        Dictionary containing class values and node IDs
    """
    if node['split_value'] is not None:
        for feature, attr_list in attribute_lists.items():
            if feature == node['feature']:
                mask = (attr_list['node_ids'] == node['id']) & (attr_list['attribute_values'] <= node['split_value'])
                attr_list['node_ids'][mask] = left_node_id
                mask = (attr_list['node_ids'] == node['id']) & (attr_list['attribute_values'] > node['split_value'])
                attr_list['node_ids'][mask] = right_node_id
            else:
                mask = attr_list['node_ids'] == node['id']
                attr_list['node_ids'][mask] = left_node_id
    else:
        for feature, attr_list in attribute_lists.items():
            if feature == node['feature']:
                mask = (attr_list['node_ids'] == node['id']) & (attr_list['attribute_values'] == node['split_value'])
                attr_list['node_ids'][mask] = left_node_id
                mask = (attr_list['node_ids'] == node['id']) & (attr_list['attribute_values'] != node['split_value'])
                attr_list['node_ids'][mask] = right_node_id
            else:
                mask = attr_list['node_ids'] == node['id']
                attr_list['node_ids'][mask] = left_node_id

def build_tree(X, y, min_samples_leaf=20, max_depth=None):
    """
    Build the decision tree.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Input data of shape (n_samples, n_features)
    y : numpy.ndarray
        Target labels
    min_samples_leaf : int, default=20
        Minimum number of samples required to be a leaf node
        - Prevents overfitting by requiring sufficient samples
        - Larger values create simpler trees
    max_depth : int, default=None
        Maximum depth of the tree
        - Prevents overfitting by limiting tree complexity
        - None means unlimited depth
        
    Returns:
    --------
    dict
        Root node of the decision tree
    """
    # Initialize attribute lists and class list
    attribute_lists = {}
    for i in range(X.shape[1]):
        attribute_lists[i] = {
            'attribute_values': X[:, i],
            'class_values': y,
            'node_ids': np.zeros(len(y), dtype=int)
        }
    class_list = {
        'class_values': y,
        'node_ids': np.zeros(len(y), dtype=int)
    }
    
    # Initialize root node
    root = {
        'id': 0,
        'depth': 0,
        'feature': None,
        'split_value': None,
        'left': None,
        'right': None,
        'label': None,
        'class_distribution': defaultdict(int)
    }
    for c in y:
        root['class_distribution'][c] += 1
        
    queue = [root]
    next_node_id = 1
    
    while queue and (max_depth is None or queue[0]['depth'] < max_depth):
        node = queue.pop(0)
        
        if len(np.unique(y[class_list['node_ids'] == node['id']])) == 1:
            node['label'] = y[class_list['node_ids'] == node['id']][0]
            continue
            
        if len(y[class_list['node_ids'] == node['id']]) < min_samples_leaf:
            if node['class_distribution']:
                node['label'] = max(node['class_distribution'].items(), key=lambda x: x[1])[0]
            else:
                node['label'] = 0
            continue
            
        best_feature, best_split = find_best_split(
            node['id'], attribute_lists, class_list
        )
        
        if best_feature is None:
            if node['class_distribution']:
                node['label'] = max(node['class_distribution'].items(), key=lambda x: x[1])[0]
            else:
                node['label'] = 0
            continue
            
        node['feature'] = best_feature
        node['split_value'] = best_split
        
        left_node = {
            'id': next_node_id,
            'depth': node['depth'] + 1,
            'feature': None,
            'split_value': None,
            'left': None,
            'right': None,
            'label': None,
            'class_distribution': defaultdict(int)
        }
        right_node = {
            'id': next_node_id + 1,
            'depth': node['depth'] + 1,
            'feature': None,
            'split_value': None,
            'left': None,
            'right': None,
            'label': None,
            'class_distribution': defaultdict(int)
        }
        next_node_id += 2
        
        update_node_assignments(
            node, left_node['id'], right_node['id'],
            attribute_lists, class_list
        )
        
        node['left'] = left_node
        node['right'] = right_node
        
        queue.extend([left_node, right_node])
        
    return root

def predict_single(x, tree):
    """
    Make prediction for a single sample.
    
    Parameters:
    -----------
    x : numpy.ndarray
        Input sample
    tree : dict
        Decision tree
        
    Returns:
    --------
    int
        Predicted class label
    """
    if tree['label'] is not None:
        return tree['label']
        
    if tree['split_value'] is not None:
        if x[tree['feature']] <= tree['split_value']:
            return predict_single(x, tree['left'])
        else:
            return predict_single(x, tree['right'])
    else:
        if x[tree['feature']] == tree['split_value']:
            return predict_single(x, tree['left'])
        else:
            return predict_single(x, tree['right'])

def sliq_classifier(X, y, min_samples_leaf=20, max_depth=None):
    """
    Train a SLIQ classifier.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Input data of shape (n_samples, n_features)
    y : numpy.ndarray
        Target labels
    min_samples_leaf : int, default=20
        Minimum number of samples required to be a leaf node
        - Prevents overfitting by requiring sufficient samples
        - Larger values create simpler trees
    max_depth : int, default=None
        Maximum depth of the tree
        - Prevents overfitting by limiting tree complexity
        - None means unlimited depth
        
    Returns:
    --------
    tuple
        tree : dict
            Trained decision tree
        predictions : numpy.ndarray
            Predicted class labels
    """
    # Build tree
    tree = build_tree(X, y, min_samples_leaf, max_depth)
    
    # Make predictions
    predictions = np.array([predict_single(x, tree) for x in X])
    
    return tree, predictions

## 4. Model Training and Evaluation
def evaluate_classifier(y_true, y_pred):
    """
    Evaluate classification results.
    
    Parameters:
    -----------
    y_true : numpy.ndarray
        True labels
    y_pred : numpy.ndarray
        Predicted labels
        
    Returns:
    --------
    dict
        Dictionary containing evaluation metrics
    """
    metrics = {
        'accuracy': accuracy_score(y_true, y_pred),
        'classification_report': classification_report(y_true, y_pred)
    }
    return metrics

## 5. Visualization
def plot_feature_importance(X, tree, title=None):
    """
    Plot feature importance.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Input data
    tree : dict
        Decision tree
    title : str, optional
        Plot title
    """
    feature_importance = np.zeros(X.shape[1])
    
    def traverse(node):
        if node['feature'] is not None:
            feature_importance[node['feature']] += 1
            if node['left'] is not None:
                traverse(node['left'])
            if node['right'] is not None:
                traverse(node['right'])
    
    traverse(tree)
    feature_importance = feature_importance / np.sum(feature_importance)
    
    plt.figure(figsize=(10, 6))
    sns.barplot(x=np.arange(len(feature_importance)), y=feature_importance)
    plt.title(title or 'Feature Importance')
    plt.xlabel('Feature Index')
    plt.ylabel('Importance')
    
    # Create directory if it doesn't exist
    os.makedirs('algorithms/other-algorithms/sliq', exist_ok=True)
    
    # Save the plot to a PNG file
    filename = f'algorithms/other-algorithms/sliq/sliq-{title.lower().replace(" ", "_") if title else "feature_importance"}.png'
    plt.savefig(filename)
    plt.close()