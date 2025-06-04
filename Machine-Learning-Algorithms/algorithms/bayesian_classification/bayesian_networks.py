#!/usr/bin/env python3
# -*- coding: utf-8 -*-
## Bayesian Networks Classification Implementation
# This notebook demonstrates Bayesian Networks for classification, including structure learning, parameter learning, and inference.

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import networkx as nx
from collections import defaultdict
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score, classification_report, confusion_matrix
import seaborn as sns
import matplotlib.pyplot as plt
import os

# Set random seed for reproducibility
np.random.seed(2220)

# Create output directory for visuals
output_dir = 'algorithms/bayesian_classification/bayesian_networks'
os.makedirs(output_dir, exist_ok=True)

## 2. Helper Functions for Bayesian Network

def calculate_mutual_information(X, y, feature_idx):
    """
    Calculate mutual information between feature and class.
    """
    feature_values = X[:, feature_idx]
    unique_features = np.unique(feature_values)
    unique_classes = np.unique(y)
    mi = 0
    for feature_val in unique_features:
        for class_val in unique_classes:
            joint_prob = np.mean((feature_values == feature_val) & (y == class_val))
            if joint_prob == 0:
                continue
            feature_prob = np.mean(feature_values == feature_val)
            class_prob = np.mean(y == class_val)
            mi += joint_prob * np.log2(joint_prob / (feature_prob * class_prob))
    return mi

def learn_structure(X, y, top_k=3, mi_threshold=0.1):
    """
    Learn network structure using mutual information.
    """
    n_features = X.shape[1]
    mi_scores = []
    for i in range(n_features):
        mi = calculate_mutual_information(X, y, i)
        mi_scores.append((i, mi))
    mi_scores.sort(key=lambda x: x[1], reverse=True)
    G = nx.DiGraph()
    G.add_node('class')
    for feature_idx, _ in mi_scores[:top_k]:
        G.add_edge('class', f'feature_{feature_idx}')
    for i, (feature1_idx, _) in enumerate(mi_scores):
        for feature2_idx, _ in mi_scores[i+1:]:
            # Stack the two features as columns to form a 2D array
            X_pair = np.column_stack([X[:, feature1_idx], X[:, feature2_idx]])
            mi = calculate_mutual_information(X_pair, X[:, feature2_idx], 0)
            if mi > mi_threshold:
                G.add_edge(f'feature_{feature1_idx}', f'feature_{feature2_idx}')
    return G

def learn_parameters(X, y, structure, alpha=1.0):
    """
    Learn conditional probability tables (CPTs) for the Bayesian Network.
    """
    cpt = {}
    unique_classes = np.unique(y)
    class_counts = np.bincount(y)
    cpt['class'] = (class_counts + alpha) / (len(y) + alpha * len(unique_classes))
    for node in structure.nodes():
        if node == 'class':
            continue
        feature_idx = int(node.split('_')[1])
        feature_values = X[:, feature_idx]
        unique_features = np.unique(feature_values)
        parents = list(structure.predecessors(node))
        if not parents:
            probs = np.zeros((len(unique_classes), len(unique_features)))
            for i, class_val in enumerate(unique_classes):
                for j, feature_val in enumerate(unique_features):
                    count = np.sum((y == class_val) & (feature_values == feature_val))
                    probs[i, j] = (count + alpha) / (
                        np.sum(y == class_val) + alpha * len(unique_features))
            cpt[node] = (unique_features, probs)
        else:
            parent_values = []
            for parent in parents:
                if parent == 'class':
                    parent_values.append(y)
                else:
                    parent_idx = int(parent.split('_')[1])
                    parent_values.append(X[:, parent_idx])
            parent_shapes = [len(np.unique(pv)) for pv in parent_values]
            probs = np.zeros(tuple(parent_shapes + [len(unique_features)]))
            for i, feature_val in enumerate(unique_features):
                for parent_vals in np.ndindex(*parent_shapes):
                    parent_conditions = [
                        pv == np.unique(pv)[j] for pv, j in zip(parent_values, parent_vals)
                    ]
                    count = np.sum((feature_values == feature_val) & np.all(parent_conditions, axis=0))
                    total = np.sum(np.all(parent_conditions, axis=0))
                    probs[parent_vals + (i,)] = (count + alpha) / (
                        total + alpha * len(unique_features))
            cpt[node] = (unique_features, probs)
    return cpt

def predict_proba_bn(X, structure, cpt):
    """
    Predict class probabilities for each sample.
    """
    n_samples = X.shape[0]
    n_classes = len(cpt['class'])
    probs = np.zeros((n_samples, n_classes))
    for i in range(n_samples):
        for class_val in range(n_classes):
            prob = cpt['class'][class_val]
            for node in structure.nodes():
                if node == 'class':
                    continue
                parent_values = {'class': class_val}
                for parent in structure.predecessors(node):
                    if parent != 'class':
                        parent_idx = int(parent.split('_')[1])
                        parent_values[parent] = X[i, parent_idx]
                feature_idx = int(node.split('_')[1])
                feature_val = X[i, feature_idx]
                unique_features, probs_table = cpt[node]
                if len(parent_values) == 1:
                    class_idx = class_val
                    feature_idx_ = np.where(unique_features == feature_val)[0][0]
                    prob *= probs_table[class_idx, feature_idx_]
                else:
                    parent_indices = []
                    for parent, value in parent_values.items():
                        if parent == 'class':
                            parent_indices.append(class_val)
                        else:
                            parent_idx = int(parent.split('_')[1])
                            parent_indices.append(np.where(np.unique(X[:, parent_idx]) == value)[0][0])
                    feature_idx_ = np.where(unique_features == feature_val)[0][0]
                    prob *= probs_table[tuple(parent_indices) + (feature_idx_,)]
            probs[i, class_val] = prob
    return probs

def predict_bn(X, structure, cpt):
    """
    Predict class labels for each sample.
    """
    probs = predict_proba_bn(X, structure, cpt)
    return np.argmax(probs, axis=1)

## 3. Visualization Functions

def plot_network(structure, title="Bayesian Network Structure"):
    """
    Plot the Bayesian Network structure using networkx and seaborn.
    """
    plt.figure(figsize=(8, 6))
    pos = nx.spring_layout(structure)
    nx.draw(structure, pos, with_labels=True, node_color='skyblue', node_size=2000, font_size=10, font_weight='bold', edge_color='gray')
    plt.title(title)
    plt.savefig(f'{output_dir}/bayesian_networks-network-structure.png')
    plt.close()

def plot_confusion_matrix(y_true, y_pred, title="Confusion Matrix"):
    """
    Plot confusion matrix using seaborn.
    """
    cm = confusion_matrix(y_true, y_pred)
    plt.figure(figsize=(8, 6))
    sns.heatmap(cm, annot=True, fmt='d', cmap='Blues')
    plt.xlabel('Predicted')
    plt.ylabel('True')
    plt.title(title)
    plt.savefig(f'{output_dir}/bayesian_networks-confusion-matrix.png')
    plt.close()

def plot_feature_importance(X, y, structure, title="Feature Importance"):
    """
    Plot feature importance based on mutual information.
    """
    n_features = X.shape[1]
    mi_scores = [calculate_mutual_information(X, y, i) for i in range(n_features)]
    plt.figure(figsize=(10, 6))
    sns.barplot(x=[f'feature_{i}' for i in range(n_features)], y=mi_scores)
    plt.title(title)
    plt.xlabel('Feature')
    plt.ylabel('Mutual Information')
    plt.savefig(f'{output_dir}/bayesian_networks-feature-importance.png')
    plt.close()

## 4. Model Training and Evaluation

# Generate sample data
n_samples = 500
n_features = 5
X = np.random.randint(0, 3, size=(n_samples, n_features))
y = (X[:, 0] + X[:, 1] > 2).astype(int)

# Split data
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=2220)

# Hyperparameters explained:
# - alpha: Smoothing parameter for probability estimation (Laplace smoothing)
# - top_k: Number of top features to connect to the class node
# - mi_threshold: Threshold for adding edges between features based on mutual information
alpha = 1.0
structure = learn_structure(X_train, y_train, top_k=3, mi_threshold=0.1)
cpt = learn_parameters(X_train, y_train, structure, alpha=alpha)

# Plot network structure
plot_network(structure, title="Bayesian Network Structure")

# Plot feature importance
plot_feature_importance(X_train, y_train, structure, title="Feature Importance (Mutual Information)")

# Predict and evaluate
y_pred = predict_bn(X_test, structure, cpt)
accuracy = accuracy_score(y_test, y_pred)
print(f"Accuracy: {accuracy:.4f}")
print("\nClassification Report:")
print(classification_report(y_test, y_pred))

# Plot confusion matrix
plot_confusion_matrix(y_test, y_pred, title="Confusion Matrix") 