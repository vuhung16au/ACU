# CHAID (Chi-square Automatic Interaction Detection) Implementation
# This notebook demonstrates the implementation of the CHAID decision tree algorithm.

## Table of Contents
# 1. Import Required Libraries
# 2. Data Generation and Preprocessing
# 3. CHAID Implementation
# 4. Model Training and Evaluation
# 5. Visualization
# 6. Results and Analysis

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import os
from scipy.stats import chi2_contingency
from sklearn.datasets import make_classification
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score, classification_report

# Set random seed for reproducibility
np.random.seed(2220)

## 2. Data Generation and Preprocessing
def generate_data(n_samples=1000, n_features=10, n_classes=2):
    """
    Generate sample classification data.
    
    Parameters:
    -----------
    n_samples : int, default=1000
        Number of samples to generate
    n_features : int, default=10
        Number of features
    n_classes : int, default=2
        Number of classes
        
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
        random_state=2220
    )
    return X, y

## 3. CHAID Implementation
def calculate_chi_square(X, y, feature):
    """
    Calculate chi-square statistic for a feature.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Feature matrix
    y : numpy.ndarray
        Target labels
    feature : int
        Index of feature to analyze
        
    Returns:
    --------
    tuple
        chi2 : float
            Chi-square statistic
        p_value : float
            P-value of the chi-square test
    """
    contingency = np.zeros((len(np.unique(X[:, feature])), len(np.unique(y))))
    for i, val in enumerate(np.unique(X[:, feature])):
        for j, class_val in enumerate(np.unique(y)):
            contingency[i, j] = np.sum((X[:, feature] == val) & (y == class_val))
    
    chi2, p_value, _, _ = chi2_contingency(contingency)
    return chi2, p_value

def find_best_split(X, y, alpha=0.05):
    """
    Find the best feature to split on based on chi-square test.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Feature matrix
    y : numpy.ndarray
        Target labels
    alpha : float, default=0.05
        Significance level for chi-square test
        - Lower values (e.g., 0.01) make splits more conservative
        - Higher values (e.g., 0.1) allow more splits
        
    Returns:
    --------
    tuple
        best_feature : int or None
            Index of best feature to split on
        best_p_value : float
            P-value of the best split
    """
    best_chi2 = -np.inf
    best_feature = None
    best_p_value = 1.0
    
    for feature in range(X.shape[1]):
        chi2, p_value = calculate_chi_square(X, y, feature)
        if p_value < alpha and chi2 > best_chi2:
            best_chi2 = chi2
            best_feature = feature
            best_p_value = p_value
            
    return best_feature, best_p_value

def build_tree(X, y, min_samples_leaf=20, max_depth=None, alpha=0.05, depth=0):
    """
    Recursively build the CHAID decision tree.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Feature matrix
    y : numpy.ndarray
        Target labels
    min_samples_leaf : int, default=20
        Minimum number of samples required to be a leaf node
        - Higher values create more conservative trees
        - Lower values allow more splits
    max_depth : int or None, default=None
        Maximum depth of the tree
        - None means unlimited depth
        - Higher values allow more complex trees
    alpha : float, default=0.05
        Significance level for chi-square test
    depth : int, default=0
        Current depth of the tree
        
    Returns:
    --------
    dict
        Tree structure with nodes and splits
    """
    # Create leaf node if stopping criteria met
    if len(np.unique(y)) == 1 or len(y) < min_samples_leaf:
        return {'label': np.bincount(y).argmax()}
        
    if max_depth is not None and depth >= max_depth:
        return {'label': np.bincount(y).argmax()}
        
    # Find best split
    best_feature, p_value = find_best_split(X, y, alpha)
    
    if best_feature is None or p_value >= alpha:
        return {'label': np.bincount(y).argmax()}
        
    # Create node with split
    node = {
        'feature': best_feature,
        'children': {}
    }
    
    # Recursively build subtrees
    unique_values = np.unique(X[:, best_feature])
    for value in unique_values:
        mask = X[:, best_feature] == value
        if np.sum(mask) >= min_samples_leaf:
            node['children'][value] = build_tree(
                X[mask], y[mask],
                min_samples_leaf=min_samples_leaf,
                max_depth=max_depth,
                alpha=alpha,
                depth=depth + 1
            )
            
    return node

def predict_single(x, tree):
    """
    Make prediction for a single sample.
    
    Parameters:
    -----------
    x : numpy.ndarray
        Single sample features
    tree : dict
        Tree structure
        
    Returns:
    --------
    int
        Predicted class label
    """
    if 'label' in tree:
        return tree['label']
        
    if 'feature' in tree and x[tree['feature']] in tree['children']:
        return predict_single(x, tree['children'][x[tree['feature']]])
        
    return tree['label']

def predict(X, tree):
    """
    Make predictions for multiple samples.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Feature matrix
    tree : dict
        Tree structure
        
    Returns:
    --------
    numpy.ndarray
        Predicted class labels
    """
    return np.array([predict_single(x, tree) for x in X])

## 4. Model Training and Evaluation
def evaluate_model(y_true, y_pred):
    """
    Evaluate model performance.
    
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
def plot_feature_importance(tree, feature_names=None):
    """
    Plot feature importance.
    
    Parameters:
    -----------
    tree : dict
        Tree structure
    feature_names : list or None
        List of feature names
    """
    # Compute feature usage
    feature_counts = {}
    def traverse(node):
        if 'feature' in node and node['feature'] is not None:
            feature_counts[node['feature']] = feature_counts.get(node['feature'], 0) + 1
            for child in node['children'].values():
                traverse(child)
    traverse(tree)
    
    if not feature_counts:
        print("No features used in splits.")
        return
    
    features = list(feature_counts.keys())
    counts = [feature_counts[f] for f in features]
    if feature_names is not None:
        labels = [feature_names[f] for f in features]
    else:
        labels = [str(f) for f in features]
    
    plt.figure(figsize=(10, 6))
    sns.barplot(x=labels, y=counts)
    plt.title('CHAID Feature Importance')
    plt.xlabel('Feature')
    plt.ylabel('Usage Count')
    os.makedirs('algorithms/other-algorithms/chaid', exist_ok=True)
    filename = 'algorithms/other-algorithms/chaid/chaid-feature_importance.png'
    plt.savefig(filename)
    plt.close() 