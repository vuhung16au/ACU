#!/usr/bin/env python3
# -*- coding: utf-8 -*-

## Random Forest Implementation
## This notebook demonstrates both custom and scikit-learn implementations of Random Forest.
## Random Forest is an ensemble learning method that operates by constructing multiple
## decision trees and outputting the class that is the mode of the classes of the
## individual trees.

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import (
    accuracy_score, confusion_matrix, classification_report,
    roc_curve, auc
)
from sklearn.preprocessing import StandardScaler
from collections import Counter
from typing import List, Dict, Any, Tuple
import os
from sklearn.preprocessing import label_binarize

## 2. Set Random Seed
np.random.seed(2220)  # Set random seed for reproducibility

## 3. Data Generation and Preprocessing
def generate_data():
    """
    Generate sample data for classification.
    
    Returns:
        tuple: (X, y) features and target
    """
    n_samples = 100
    n_features = 2
    
    # Generate features
    X = np.random.randn(n_samples, n_features)
    
    # Generate target based on feature values
    y = np.zeros(n_samples)
    y[X[:, 0] > 0] = 1
    y[X[:, 1] > 0] = 2
    y[(X[:, 0] > 0) & (X[:, 1] > 0)] = 3
    
    return X, y

## 4. Custom Random Forest Implementation
def calculate_gini(y: np.ndarray) -> float:
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

def calculate_information_gain(X: np.ndarray, y: np.ndarray, 
                             feature: int, threshold: float) -> float:
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

def find_best_split(X: np.ndarray, y: np.ndarray, 
                   max_features: int = None) -> Tuple[int, float, float]:
    """
    Find the best split for a node.
    
    Hyperparameters:
    - max_features (int): Maximum features to consider for split.
      If None, all features are considered. Can help prevent overfitting
      by limiting the number of features evaluated at each split.
    
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
    
    n_features = X.shape[1]
    if max_features is not None:
        n_features = min(n_features, max_features)
    
    # Randomly select features
    feature_indices = np.random.choice(
        X.shape[1], size=n_features, replace=False
    )
    
    for feature in feature_indices:
        thresholds = np.unique(X[:, feature])
        for threshold in thresholds:
            gain = calculate_information_gain(X, y, feature, threshold)
            if gain > best_gain:
                best_gain = gain
                best_feature = feature
                best_threshold = threshold
    
    return best_feature, best_threshold, best_gain

def build_tree(X: np.ndarray, y: np.ndarray, 
              max_depth: int = None,
              min_samples_split: int = 2,
              min_samples_leaf: int = 1,
              max_features: int = None) -> Dict[str, Any]:
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
    - max_features (int): Maximum features to consider for split.
      If None, all features are considered. Can help prevent overfitting
      by limiting the number of features evaluated at each split.
    
    Args:
        X (numpy.ndarray): Features
        y (numpy.ndarray): Target values
        max_depth (int): Maximum depth of the tree
        min_samples_split (int): Minimum samples required to split
        min_samples_leaf (int): Minimum samples required in a leaf
        max_features (int): Maximum features to consider
        
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
    best_feature, best_threshold, best_gain = find_best_split(
        X, y, max_features
    )
    
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

def traverse_tree(x: np.ndarray, node: Dict[str, Any]) -> int:
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

def custom_random_forest_predict(X: np.ndarray, trees: List[Dict[str, Any]]) -> np.ndarray:
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

def custom_random_forest_fit(X: np.ndarray, y: np.ndarray,
                           n_estimators: int = 100,
                           max_depth: int = None,
                           min_samples_split: int = 2,
                           min_samples_leaf: int = 1,
                           max_features: int = None) -> List[Dict[str, Any]]:
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
    - max_features (int): Maximum features to consider for split.
      If None, all features are considered. Can help prevent overfitting
      by limiting the number of features evaluated at each split.
    
    Args:
        X (numpy.ndarray): Features
        y (numpy.ndarray): Target values
        n_estimators (int): Number of trees
        max_depth (int): Maximum depth of trees
        min_samples_split (int): Minimum samples required to split
        min_samples_leaf (int): Minimum samples required in a leaf
        max_features (int): Maximum features to consider
        
    Returns:
        list: List of trained trees
    """
    trees = []
    n_samples = X.shape[0]
    
    for _ in range(n_estimators):
        # Bootstrap sampling
        indices = np.random.choice(n_samples, size=n_samples, replace=True)
        X_bootstrap = X[indices]
        y_bootstrap = y[indices]
        
        # Build tree
        tree = build_tree(
            X_bootstrap, y_bootstrap,
            max_depth, min_samples_split,
            min_samples_leaf, max_features
        )
        trees.append(tree)
    
    return trees

## 5. Visualization Functions
def plot_decision_boundary(X: np.ndarray, y: np.ndarray, 
                         model: Any, title: str = "Decision Boundary"):
    """
    Plot decision boundary and data points using seaborn and save to PNG file.
    
    Args:
        X (numpy.ndarray): Features
        y (numpy.ndarray): Target values
        model: Trained model
        title (str): Plot title
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/random_forest/random_forest', exist_ok=True)
    
    # Create mesh grid
    h = 0.02  # Step size
    x_min, x_max = X[:, 0].min() - 1, X[:, 0].max() + 1
    y_min, y_max = X[:, 1].min() - 1, X[:, 1].max() + 1
    xx, yy = np.meshgrid(np.arange(x_min, x_max, h),
                        np.arange(y_min, y_max, h))
    
    # Predict for each point in mesh grid
    if isinstance(model, list):
        Z = custom_random_forest_predict(np.c_[xx.ravel(), yy.ravel()], model)
    else:
        Z = model.predict(np.c_[xx.ravel(), yy.ravel()])
    Z = Z.reshape(xx.shape)
    
    # Create plot
    plt.figure(figsize=(10, 8))
    plt.contourf(xx, yy, Z, alpha=0.4)
    plt.scatter(X[:, 0], X[:, 1], c=y, alpha=0.8)
    plt.title(title)
    plt.xlabel('Feature 1')
    plt.ylabel('Feature 2')
    
    # Save plot
    filename = f'algorithms/random_forest/random_forest/random_forest-{title.lower().replace(" ", "_")}.png'
    plt.savefig(filename)
    plt.close()

def plot_confusion_matrix(y_true: np.ndarray, y_pred: np.ndarray, 
                         title: str = "Confusion Matrix"):
    """
    Plot confusion matrix using seaborn and save to PNG file.
    
    Args:
        y_true (numpy.ndarray): True labels
        y_pred (numpy.ndarray): Predicted labels
        title (str): Plot title
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/random_forest/random_forest', exist_ok=True)
    
    # Compute confusion matrix
    cm = confusion_matrix(y_true, y_pred)
    
    # Create plot
    plt.figure(figsize=(8, 6))
    sns.heatmap(cm, annot=True, fmt='d', cmap='Blues')
    plt.title(title)
    plt.xlabel('Predicted')
    plt.ylabel('True')
    
    # Save plot
    filename = f'algorithms/random_forest/random_forest/random_forest-{title.lower().replace(" ", "_")}.png'
    plt.savefig(filename)
    plt.close()

def plot_feature_importance(model: Any, feature_names: List[str] = None):
    """
    Plot feature importance using seaborn and save to PNG file.
    
    Args:
        model: Trained Random Forest model
        feature_names (list): List of feature names
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/random_forest/random_forest', exist_ok=True)
    
    # Get feature importance
    if hasattr(model, 'feature_importances_'):
        importance = model.feature_importances_
    else:
        # For custom implementation
        importance = np.zeros(len(feature_names))
        for tree in model:
            def traverse_and_count(node):
                if 'feature' in node:
                    importance[node['feature']] += 1
                    traverse_and_count(node['left'])
                    traverse_and_count(node['right'])
            traverse_and_count(tree)
        importance = importance / len(model)
    
    # Create plot
    plt.figure(figsize=(10, 6))
    if feature_names is None:
        feature_names = [f'Feature {i}' for i in range(len(importance))]
    
    # Sort features by importance
    indices = np.argsort(importance)
    plt.barh(range(len(indices)), importance[indices])
    plt.yticks(range(len(indices)), [feature_names[i] for i in indices])
    plt.title('Feature Importance')
    plt.xlabel('Importance')
    
    # Save plot
    filename = 'algorithms/random_forest/random_forest/random_forest-feature_importance.png'
    plt.savefig(filename)
    plt.close()

def plot_roc_curve(y_true: np.ndarray, y_pred_proba: np.ndarray, 
                  title: str = "ROC Curve"):
    """
    Plot ROC curve using seaborn and save to PNG file.
    Handles multiclass by plotting one curve per class (One-vs-Rest).
    
    Args:
        y_true (numpy.ndarray): True labels
        y_pred_proba (numpy.ndarray): Predicted probabilities
        title (str): Plot title
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/random_forest/random_forest', exist_ok=True)
    
    n_classes = y_pred_proba.shape[1]
    y_true_bin = label_binarize(y_true, classes=np.arange(n_classes))
    
    plt.figure(figsize=(8, 6))
    for i in range(n_classes):
        fpr, tpr, _ = roc_curve(y_true_bin[:, i], y_pred_proba[:, i])
        roc_auc = auc(fpr, tpr)
        plt.plot(fpr, tpr, lw=2, label=f'Class {i} (AUC = {roc_auc:.2f})')
    plt.plot([0, 1], [0, 1], color='navy', lw=2, linestyle='--')
    plt.xlim([0.0, 1.0])
    plt.ylim([0.0, 1.05])
    plt.xlabel('False Positive Rate')
    plt.ylabel('True Positive Rate')
    plt.title(title)
    plt.legend(loc="lower right")
    
    # Save plot
    filename = f'algorithms/random_forest/random_forest/random_forest-{title.lower().replace(" ", "_")}.png'
    plt.savefig(filename)
    plt.close()

## 6. Main Execution
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
    max_features=2
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
plot_feature_importance(custom_model, ['Feature 1', 'Feature 2'])
plot_feature_importance(sklearn_model, ['Feature 1', 'Feature 2'])
plot_roc_curve(y_test, sklearn_model.predict_proba(X_test_scaled), "Scikit-learn Implementation ROC Curve")