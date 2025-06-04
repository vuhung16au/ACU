#!/usr/bin/env python3
# -*- coding: utf-8 -*-

## Random Forests Implementation
## This notebook demonstrates both custom and scikit-learn implementations of Random Forests.
## Random Forests are an ensemble learning method that operates by constructing multiple
## decision trees and outputting the class that is the mode of the classes of the
## individual trees.

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import train_test_split, cross_val_score
from sklearn.metrics import (
    accuracy_score, confusion_matrix, classification_report,
    roc_curve, auc
)
from sklearn.preprocessing import StandardScaler
from collections import Counter
from joblib import Parallel, delayed
import os

## 2. Set Random Seed
np.random.seed(2220)  # Set random seed for reproducibility

## 3. Helper Functions
def bootstrap_sample(X, y):
    """
    Create a bootstrap sample of the data.
    
    Args:
        X (numpy.ndarray): Features
        y (numpy.ndarray): Target values
        
    Returns:
        tuple: (X_bootstrap, y_bootstrap)
    """
    n_samples = X.shape[0]
    indices = np.random.choice(n_samples, n_samples, replace=True)
    return X[indices], y[indices]

def get_max_features(n_features, max_features):
    """
    Determine the number of features to consider for each split.
    
    Args:
        n_features (int): Total number of features
        max_features (str/int): Maximum features parameter
        
    Returns:
        int: Number of features to consider
    """
    if isinstance(max_features, str):
        if max_features == 'sqrt':
            return int(np.sqrt(n_features))
        elif max_features == 'log2':
            return int(np.log2(n_features))
    elif isinstance(max_features, int):
        return min(max_features, n_features)
    return n_features

## 4. Decision Tree Implementation
def calculate_gini(y):
    """
    Calculate Gini impurity.
    
    Args:
        y (numpy.ndarray): Target values
        
    Returns:
        float: Gini impurity
    """
    counter = Counter(y)
    impurity = 1
    for count in counter.values():
        prob = count / len(y)
        impurity -= prob ** 2
    return impurity

def calculate_information_gain(X, y, feature, threshold):
    """
    Calculate information gain for a split.
    
    Args:
        X (numpy.ndarray): Features
        y (numpy.ndarray): Target values
        feature (int): Feature index
        threshold (float): Split threshold
        
    Returns:
        float: Information gain
    """
    parent_impurity = calculate_gini(y)
    
    # Create split
    left_mask = X[:, feature] <= threshold
    right_mask = ~left_mask
    
    if len(y[left_mask]) == 0 or len(y[right_mask]) == 0:
        return 0
    
    # Calculate weighted impurity
    n = len(y)
    left_impurity = calculate_gini(y[left_mask])
    right_impurity = calculate_gini(y[right_mask])
    
    weighted_impurity = (len(y[left_mask]) / n * left_impurity +
                        len(y[right_mask]) / n * right_impurity)
    
    return parent_impurity - weighted_impurity

def find_best_split(X, y, max_features):
    """
    Find the best split for a node.
    
    Args:
        X (numpy.ndarray): Features
        y (numpy.ndarray): Target values
        max_features (int): Maximum features to consider
        
    Returns:
        tuple: (best_feature, best_threshold, best_gain)
    """
    best_gain = -1
    best_feature = None
    best_threshold = None
    
    n_features = get_max_features(X.shape[1], max_features)
    feature_indices = np.random.choice(X.shape[1], size=n_features, replace=False)
    
    for feature in feature_indices:
        thresholds = np.unique(X[:, feature])
        for threshold in thresholds:
            gain = calculate_information_gain(X, y, feature, threshold)
            if gain > best_gain:
                best_gain = gain
                best_feature = feature
                best_threshold = threshold
    
    return best_feature, best_threshold, best_gain

def build_tree(X, y, max_depth=None, min_samples_split=2, min_samples_leaf=1, max_features='sqrt'):
    """
    Build a decision tree recursively.
    
    Hyperparameters:
    - max_depth (int): Maximum depth of the tree. Controls the complexity
      of the model. Higher values can lead to overfitting.
    - min_samples_split (int): Minimum samples required to split a node.
      Higher values can help prevent overfitting by requiring more data
      to justify a split.
    - min_samples_leaf (int): Minimum samples required in a leaf node.
      Higher values can help prevent overfitting by ensuring leaf nodes
      have enough samples.
    - max_features (str/int): Maximum features to consider for split.
      Can be 'sqrt', 'log2', an integer, or None. Helps prevent overfitting
      by limiting the number of features evaluated at each split.
    
    Args:
        X (numpy.ndarray): Features
        y (numpy.ndarray): Target values
        max_depth (int): Maximum depth of the tree
        min_samples_split (int): Minimum samples required to split
        min_samples_leaf (int): Minimum samples required in a leaf
        max_features (str/int): Maximum features to consider
        
    Returns:
        dict: Tree node
    """
    n_samples, n_features = X.shape
    n_labels = len(np.unique(y))
    
    # Check stopping criteria
    if (max_depth is not None and max_depth <= 0 or
        n_samples < min_samples_split or
        n_labels == 1):
        return {'value': Counter(y).most_common(1)[0][0]}
    
    # Find best split
    best_feature, best_threshold, best_gain = find_best_split(X, y, max_features)
    
    if best_gain == 0:
        return {'value': Counter(y).most_common(1)[0][0]}
    
    # Create child nodes
    left_mask = X[:, best_feature] <= best_threshold
    right_mask = ~left_mask
    
    if (len(y[left_mask]) < min_samples_leaf or
        len(y[right_mask]) < min_samples_leaf):
        return {'value': Counter(y).most_common(1)[0][0]}
    
    left_subtree = build_tree(
        X[left_mask], y[left_mask],
        max_depth - 1 if max_depth is not None else None,
        min_samples_split, min_samples_leaf, max_features
    )
    right_subtree = build_tree(
        X[right_mask], y[right_mask],
        max_depth - 1 if max_depth is not None else None,
        min_samples_split, min_samples_leaf, max_features
    )
    
    return {
        'feature': best_feature,
        'threshold': best_threshold,
        'left': left_subtree,
        'right': right_subtree
    }

def traverse_tree(x, node):
    """
    Traverse the tree to make a prediction.
    
    Args:
        x (numpy.ndarray): Single sample
        node (dict): Current node
        
    Returns:
        int: Predicted class
    """
    if 'value' in node:
        return node['value']
    
    if x[node['feature']] <= node['threshold']:
        return traverse_tree(x, node['left'])
    return traverse_tree(x, node['right'])

## 5. Random Forest Implementation
def custom_random_forest_fit(X, y, n_estimators=100, max_depth=None,
                           min_samples_split=2, min_samples_leaf=1,
                           max_features='sqrt', n_jobs=-1):
    """
    Train a Random Forest model.
    
    Hyperparameters:
    - n_estimators (int): Number of trees in the forest. More trees generally
      improve performance but increase computation time.
    - max_depth (int): Maximum depth of each tree. Controls the complexity
      of individual trees. Higher values can lead to overfitting.
    - min_samples_split (int): Minimum samples required to split a node.
      Higher values can help prevent overfitting by requiring more data
      to justify a split.
    - min_samples_leaf (int): Minimum samples required in a leaf node.
      Higher values can help prevent overfitting by ensuring leaf nodes
      have enough samples.
    - max_features (str/int): Maximum features to consider for split.
      Can be 'sqrt', 'log2', an integer, or None. Helps prevent overfitting
      by limiting the number of features evaluated at each split.
    - n_jobs (int): Number of parallel jobs for training. -1 means using all processors.
    
    Args:
        X (numpy.ndarray): Features
        y (numpy.ndarray): Target values
        n_estimators (int): Number of trees
        max_depth (int): Maximum depth of trees
        min_samples_split (int): Minimum samples required to split
        min_samples_leaf (int): Minimum samples required in a leaf
        max_features (str/int): Maximum features to consider
        n_jobs (int): Number of parallel jobs
        
    Returns:
        list: List of trained trees
    """
    def build_tree_wrapper():
        X_bootstrap, y_bootstrap = bootstrap_sample(X, y)
        return build_tree(
            X_bootstrap, y_bootstrap,
            max_depth, min_samples_split,
            min_samples_leaf, max_features
        )
    
    trees = Parallel(n_jobs=n_jobs)(
        delayed(build_tree_wrapper)() for _ in range(n_estimators)
    )
    
    return trees

def custom_random_forest_predict(X, trees):
    """
    Make predictions using the trained Random Forest model.
    
    Args:
        X (numpy.ndarray): Features to predict
        trees (list): List of trained trees
        
    Returns:
        numpy.ndarray: Predicted classes
    """
    predictions = np.array([
        [traverse_tree(x, tree) for tree in trees]
        for x in X
    ])
    return np.array([Counter(pred).most_common(1)[0][0] for pred in predictions])

## 6. Data Generation and Visualization
def generate_data():
    """
    Generate sample data for classification.
    
    Returns:
        tuple: (X, y) features and target
    """
    n_samples = 1000
    n_classes = 3
    n_features = 10
    
    # Generate data for each class
    X = np.random.randn(n_samples, n_features)
    y = np.zeros(n_samples)
    
    # Create class boundaries
    for i in range(n_classes):
        mask = (X[:, 0] > i - 1) & (X[:, 0] <= i)
        y[mask] = i
    
    # Add some noise
    y += np.random.randint(0, n_classes, size=n_samples) * 0.1
    y = y.astype(int) % n_classes
    
    return X, y

def plot_feature_importance(model, feature_names=None, title="Feature Importances"):
    """
    Plot feature importances using seaborn and save to PNG file.
    
    Args:
        model: Trained model
        feature_names: List of feature names
        title: Plot title
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/random_forests', exist_ok=True)
    
    # Get feature importances
    if hasattr(model, 'feature_importances_'):
        importances = model.feature_importances_
    else:
        # For custom implementation
        importances = np.zeros(len(feature_names))
        def traverse_and_count(node):
            if 'feature' in node:
                importances[node['feature']] += 1
                traverse_and_count(node['left'])
                traverse_and_count(node['right'])
        
        for tree in model:
            traverse_and_count(tree)
        importances = importances / len(model)
    
    if feature_names is None:
        feature_names = [f'feature_{i}' for i in range(len(importances))]
    
    # Create plot
    plt.figure(figsize=(10, 6))
    sns.barplot(x=importances, y=feature_names)
    plt.title(title)
    plt.xlabel('Importance')
    plt.ylabel('Feature')
    
    # Save plot
    filename = f'algorithms/random_forests/random_forests-{title.lower().replace(" ", "_")}.png'
    plt.savefig(filename)
    plt.close()

def plot_decision_boundary(X, y, model, title):
    """
    Plot decision boundary using seaborn and save to PNG file.
    
    Args:
        X: Features
        y: Target values
        model: Trained model
        title: Plot title
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/random_forests', exist_ok=True)
    
    # Use only first two features for visualization
    X_2d = X[:, :2]
    
    # Create mesh grid
    h = 0.02
    x_min, x_max = X_2d[:, 0].min() - 1, X_2d[:, 0].max() + 1
    y_min, y_max = X_2d[:, 1].min() - 1, X_2d[:, 1].max() + 1
    xx, yy = np.meshgrid(np.arange(x_min, x_max, h),
                        np.arange(y_min, y_max, h))
    
    # Create a full feature array for prediction
    mesh_points = np.c_[xx.ravel(), yy.ravel()]
    # Pad with zeros for remaining features
    full_mesh_points = np.zeros((mesh_points.shape[0], X.shape[1]))
    full_mesh_points[:, :2] = mesh_points
    
    # Get predictions
    if hasattr(model, 'predict'):
        Z = model.predict(full_mesh_points)
    else:
        Z = custom_random_forest_predict(full_mesh_points, model)
    Z = Z.reshape(xx.shape)
    
    # Create plot
    plt.figure(figsize=(10, 8))
    plt.contourf(xx, yy, Z, alpha=0.4)
    plt.scatter(X_2d[:, 0], X_2d[:, 1], c=y, alpha=0.8)
    plt.title(title)
    plt.xlabel('Feature 1')
    plt.ylabel('Feature 2')
    
    # Save plot
    filename = f'algorithms/random_forests/random_forests-{title.lower().replace(" ", "_")}.png'
    plt.savefig(filename)
    plt.close()

def plot_confusion_matrix(y_true, y_pred, title="Confusion Matrix"):
    """
    Plot confusion matrix using seaborn and save to PNG file.
    
    Args:
        y_true: True labels
        y_pred: Predicted labels
        title: Plot title
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/random_forests', exist_ok=True)
    
    cm = confusion_matrix(y_true, y_pred)
    plt.figure(figsize=(8, 6))
    sns.heatmap(cm, annot=True, fmt='d', cmap='Blues')
    plt.title(title)
    plt.xlabel('Predicted')
    plt.ylabel('True')
    
    # Save plot
    filename = f'algorithms/random_forests/random_forests-{title.lower().replace(" ", "_")}.png'
    plt.savefig(filename)
    plt.close()

## 7. Main Execution
# Generate data
X, y = generate_data()

# Split data
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.2, random_state=2220
)

# Scale features
scaler = StandardScaler()
X_train_scaled = scaler.fit_transform(X_train)
X_test_scaled = scaler.transform(X_test)

# Custom implementation
custom_model = custom_random_forest_fit(
    X_train_scaled, y_train,
    n_estimators=100,
    max_depth=5,
    min_samples_split=2,
    min_samples_leaf=1,
    max_features='sqrt',
    n_jobs=-1
)
custom_predictions = custom_random_forest_predict(X_test_scaled, custom_model)

# Scikit-learn implementation
sklearn_model = RandomForestClassifier(
    n_estimators=100,
    max_depth=5,
    min_samples_split=2,
    min_samples_leaf=1,
    max_features='sqrt',
    random_state=2220
)
sklearn_model.fit(X_train_scaled, y_train)
sklearn_predictions = sklearn_model.predict(X_test_scaled)

# Evaluate models
print("\nCustom Implementation Results:")
print(f"Accuracy: {accuracy_score(y_test, custom_predictions):.4f}")
print("\nClassification Report:")
print(classification_report(y_test, custom_predictions))

print("\nScikit-learn Implementation Results:")
print(f"Accuracy: {accuracy_score(y_test, sklearn_predictions):.4f}")
print("\nClassification Report:")
print(classification_report(y_test, sklearn_predictions))

# Plot results
plot_decision_boundary(X_test, y_test, custom_model, "Custom Implementation Decision Boundary")
plot_decision_boundary(X_test, y_test, sklearn_model, "Scikit-learn Implementation Decision Boundary")
plot_confusion_matrix(y_test, custom_predictions, "Custom Implementation Confusion Matrix")
plot_confusion_matrix(y_test, sklearn_predictions, "Scikit-learn Implementation Confusion Matrix")
plot_feature_importance(custom_model, [f'Feature {i}' for i in range(X.shape[1])])
plot_feature_importance(sklearn_model, [f'Feature {i}' for i in range(X.shape[1])])