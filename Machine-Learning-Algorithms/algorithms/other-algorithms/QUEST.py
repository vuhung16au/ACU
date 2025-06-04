# QUEST (Quick, Unbiased, Efficient Statistical Tree) Implementation
# This notebook demonstrates the implementation of the QUEST decision tree algorithm.

## Table of Contents
# 1. Import Required Libraries
# 2. Data Generation and Preprocessing
# 3. QUEST Implementation
# 4. Model Training and Evaluation
# 5. Visualization
# 6. Results and Analysis

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import os
from scipy.stats import f_oneway, chi2_contingency
from sklearn.discriminant_analysis import LinearDiscriminantAnalysis
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

## 3. QUEST Implementation
def compute_association(X, y, feature):
    """
    Compute association measure for a feature.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Input data
    y : numpy.ndarray
        Target labels
    feature : int
        Feature index
        
    Returns:
    --------
    tuple
        score : float
            Association score (F-statistic or chi-square)
        p_value : float
            P-value of the test
    """
    if np.issubdtype(X[:, feature].dtype, np.number):
        # For continuous variables, use F-test
        groups = [X[y == c, feature] for c in np.unique(y)]
        f_stat, p_value = f_oneway(*groups)
        return f_stat, p_value
    else:
        # For categorical variables, use chi-square
        contingency = np.zeros((len(np.unique(X[:, feature])), len(np.unique(y))))
        for i, val in enumerate(np.unique(X[:, feature])):
            for j, class_val in enumerate(np.unique(y)):
                contingency[i, j] = np.sum((X[:, feature] == val) & (y == class_val))
        chi2, p_value, _, _ = chi2_contingency(contingency)
        return chi2, p_value

def find_best_split(X, y):
    """
    Find the best feature and split point.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Input data
    y : numpy.ndarray
        Target labels
        
    Returns:
    --------
    tuple
        best_feature : int
            Index of best feature
        best_split : float
            Best split value
    """
    best_feature = None
    best_split = None
    best_score = -np.inf
    
    for feature in range(X.shape[1]):
        if np.issubdtype(X[:, feature].dtype, np.number):
            # For continuous variables, use LDA
            lda = LinearDiscriminantAnalysis(n_components=1)
            try:
                lda.fit(X[:, feature].reshape(-1, 1), y)
                score = lda.score(X[:, feature].reshape(-1, 1), y)
                if score > best_score:
                    best_score = score
                    best_feature = feature
                    best_split = lda.means_[0]
            except:
                continue
        else:
            # For categorical variables, use chi-square
            chi2, p_value = compute_association(X, y, feature)
            if chi2 > best_score:
                best_score = chi2
                best_feature = feature
                best_split = None
                
    return best_feature, best_split

def build_tree(X, y, min_samples_leaf=20, max_depth=None, depth=0):
    """
    Recursively build the decision tree.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Input data
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
    depth : int, default=0
        Current depth of the tree
        
    Returns:
    --------
    dict
        Tree node with the following structure:
        {
            'feature': int or None,
            'split_value': float or None,
            'left': dict or None,
            'right': dict or None,
            'label': int or None
        }
    """
    if len(np.unique(y)) == 1 or len(y) < min_samples_leaf:
        return {'label': np.bincount(y).argmax()}
        
    if max_depth is not None and depth >= max_depth:
        return {'label': np.bincount(y).argmax()}
        
    best_feature, best_split = find_best_split(X, y)
    
    if best_feature is None:
        return {'label': np.bincount(y).argmax()}
        
    node = {
        'feature': best_feature,
        'split_value': best_split,
        'left': None,
        'right': None,
        'label': None
    }
    
    if best_split is not None:
        # Binary split for continuous variable
        left_mask = X[:, best_feature] <= best_split
        right_mask = ~left_mask
    else:
        # Binary split for categorical variable
        left_mask = X[:, best_feature] == np.unique(X[:, best_feature])[0]
        right_mask = ~left_mask
        
    if np.sum(left_mask) >= min_samples_leaf:
        node['left'] = build_tree(
            X[left_mask],
            y[left_mask],
            min_samples_leaf,
            max_depth,
            depth + 1
        )
    if np.sum(right_mask) >= min_samples_leaf:
        node['right'] = build_tree(
            X[right_mask],
            y[right_mask],
            min_samples_leaf,
            max_depth,
            depth + 1
        )
        
    return node

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
            if tree['left'] is not None:
                return predict_single(x, tree['left'])
            else:
                return tree['label']
        else:
            if tree['right'] is not None:
                return predict_single(x, tree['right'])
            else:
                return tree['label']
    else:
        if x[tree['feature']] == np.unique(x[tree['feature']])[0]:
            if tree['left'] is not None:
                return predict_single(x, tree['left'])
            else:
                return tree['label']
        else:
            if tree['right'] is not None:
                return predict_single(x, tree['right'])
            else:
                return tree['label']

def quest_classifier(X, y, min_samples_leaf=20, max_depth=None):
    """
    Train a QUEST classifier.
    
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
    os.makedirs('algorithms/other-algorithms/quest', exist_ok=True)
    
    # Save the plot to a PNG file
    filename = f'algorithms/other-algorithms/quest/quest-{title.lower().replace(" ", "_") if title else "feature_importance"}.png'
    plt.savefig(filename)
    plt.close() 