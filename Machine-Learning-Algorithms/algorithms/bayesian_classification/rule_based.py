## Rule-Based Classification Implementation
# This notebook demonstrates 1R and PRISM rule-based classification algorithms with visualization and evaluation tools.

## 1. Import Required Libraries
import numpy as np
import pandas as pd
from collections import defaultdict
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score, classification_report, confusion_matrix
import seaborn as sns
import matplotlib.pyplot as plt
import os

# Set random seed for reproducibility
np.random.seed(2220)

# Create output directory for visuals
output_dir = 'algorithms/bayesian_classification/rule_based'
os.makedirs(output_dir, exist_ok=True)

## 2. Helper Functions for Rule-Based Algorithms

def oner_fit(X, y, min_rule_coverage=0.1):
    """
    Train the 1R classifier and return the best feature and rules.
    """
    best_accuracy = 0
    best_feature = None
    best_rules = None
    n = len(y)
    for i in range(X.shape[1]):
        value_counts = defaultdict(lambda: defaultdict(int))
        for value, label in zip(X[:, i], y):
            value_counts[value][label] += 1
        rules = {}
        for value, counts in value_counts.items():
            total = sum(counts.values())
            if total / n >= min_rule_coverage:
                best_label = max(counts.items(), key=lambda x: x[1])[0]
                accuracy = counts[best_label] / total
                rules[value] = (best_label, accuracy)
        if not rules:
            continue
        accuracy = sum(counts[best_label] for value, (best_label, _) in rules.items()) / n
        if accuracy > best_accuracy:
            best_accuracy = accuracy
            best_feature = i
            best_rules = rules
    return best_feature, best_rules

def oner_predict(X, best_feature, rules):
    """
    Make predictions using the best feature and rules from 1R.
    """
    predictions = []
    for x in X:
        value = x[best_feature]
        if value in rules:
            predictions.append(rules[value][0])
        else:
            predictions.append(max(rules.items(), key=lambda x: x[1][1])[1][0])
    return np.array(predictions)

def prism_fit(X, y, min_rule_coverage=0.1, min_rule_accuracy=0.8):
    """
    Train the PRISM classifier and return the list of rules.
    """
    covered_indices = np.arange(len(y))
    rules = []
    n = len(y)
    while len(covered_indices) > 0:
        best_rule = None
        best_accuracy = 0
        best_coverage = 0
        for i in range(X.shape[1]):
            for value in np.unique(X[:, i]):
                matching_indices = np.where(X[:, i] == value)[0]
                matching_indices = np.intersect1d(matching_indices, covered_indices)
                if len(matching_indices) / n < min_rule_coverage:
                    continue
                rule_labels = y[matching_indices]
                if len(rule_labels) == 0:
                    continue
                most_common = np.bincount(rule_labels).argmax()
                accuracy = np.mean(rule_labels == most_common)
                if accuracy > best_accuracy:
                    best_accuracy = accuracy
                    best_coverage = len(matching_indices) / n
                    best_rule = (i, value, most_common)
        if best_rule is None or best_accuracy < min_rule_accuracy:
            break
        rules.append(best_rule)
        feature_idx, value, _ = best_rule
        matching_indices = np.where(X[:, feature_idx] == value)[0]
        covered_indices = np.setdiff1d(covered_indices, matching_indices)
    return rules

def prism_predict(X, rules):
    """
    Make predictions using the rules from PRISM.
    """
    predictions = []
    for x in X:
        predicted = False
        for feature_idx, value, label in rules:
            if x[feature_idx] == value:
                predictions.append(label)
                predicted = True
                break
        if not predicted:
            predictions.append(rules[0][2])
    return np.array(predictions)

## 3. Visualization Functions

def plot_confusion_matrix(y_true, y_pred, title="Confusion Matrix"):
    """
    Plot confusion matrix using seaborn.
    """
    cm = confusion_matrix(y_true, y_pred)
    plt.figure(figsize=(7, 5))
    sns.heatmap(cm, annot=True, fmt='d', cmap='Blues')
    plt.xlabel('Predicted')
    plt.ylabel('True')
    plt.title(title)
    plt.savefig(f'{output_dir}/rule_based-{title.lower().replace(" ", "_")}.png')
    plt.close()

def plot_rule_coverage(y_true, y_pred, title="Rule Coverage"):
    """
    Plot rule coverage as a bar plot.
    """
    plt.figure(figsize=(7, 5))
    sns.countplot(x=y_pred)
    plt.title(title)
    plt.xlabel('Predicted Label')
    plt.ylabel('Count')
    plt.savefig(f'{output_dir}/rule_based-{title.lower().replace(" ", "_")}.png')
    plt.close()

## 4. Model Training and Evaluation

# Generate sample data (smaller for speed)
n_samples = 30  # Reduced from 400
n_features = 2  # Reduced from 3
X = np.random.randint(0, 2, size=(n_samples, n_features))  # Only 2 values per feature
y = (X[:, 0] + X[:, 1] > 1).astype(int)  # Simpler rule

# Split data
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=2220)

# Hyperparameters explained:
# - min_rule_coverage: Minimum fraction of samples a rule must cover
# - min_rule_accuracy: Minimum accuracy for a rule to be accepted
min_rule_coverage = 0.1
min_rule_accuracy = 0.8

# 1R Algorithm
best_feature, best_rules = oner_fit(X_train, y_train, min_rule_coverage)
y_pred_oner = oner_predict(X_test, best_feature, best_rules)
print("\n1R Algorithm Results:")
print(f"Accuracy: {accuracy_score(y_test, y_pred_oner):.4f}")
print(classification_report(y_test, y_pred_oner))
plot_confusion_matrix(y_test, y_pred_oner, title="1R Confusion Matrix")
plot_rule_coverage(y_test, y_pred_oner, title="1R Rule Coverage")

# PRISM Algorithm
prism_rules = prism_fit(X_train, y_train, min_rule_coverage, min_rule_accuracy)
y_pred_prism = prism_predict(X_test, prism_rules)
print("\nPRISM Algorithm Results:")
print(f"Accuracy: {accuracy_score(y_test, y_pred_prism):.4f}")
print(classification_report(y_test, y_pred_prism))
plot_confusion_matrix(y_test, y_pred_prism, title="PRISM Confusion Matrix")
plot_rule_coverage(y_test, y_pred_prism, title="PRISM Rule Coverage")