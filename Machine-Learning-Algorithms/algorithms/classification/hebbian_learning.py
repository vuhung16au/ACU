## Hebbian Learning Neural Network Implementation
# This notebook demonstrates the Hebbian learning rule for classification using a simple neural network approach.

## 1. Import Required Libraries
import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score, classification_report, confusion_matrix
import seaborn as sns
import matplotlib.pyplot as plt
import os

# Set random seed for reproducibility
np.random.seed(2220)

# Create output directory for visuals
output_dir = 'algorithms/classification/hebbian_learning'
os.makedirs(output_dir, exist_ok=True)

## 2. Helper Functions for Hebbian Network

def initialize_weights(n_features, n_classes):
    """
    Initialize network weights.
    """
    weights = np.random.randn(n_features, n_classes) * 0.01
    return weights

def hebbian_update(X, y, weights, learning_rate):
    """
    Perform Hebbian learning update.
    """
    y_onehot = np.zeros((len(y), weights.shape[1]))
    y_onehot[np.arange(len(y)), y] = 1
    for i in range(len(X)):
        weights += learning_rate * np.outer(X[i], y_onehot[i])
    return weights

def fit_hebbian(X, y, learning_rate=0.01, max_iter=1000):
    """
    Fit Hebbian network.
    """
    n_classes = len(np.unique(y))
    weights = initialize_weights(X.shape[1], n_classes)
    for i in range(max_iter):
        indices = np.random.permutation(len(X))
        X_shuffled = X[indices]
        y_shuffled = y[indices]
        weights = hebbian_update(X_shuffled, y_shuffled, weights, learning_rate)
        if i % 100 == 0:
            y_pred = predict_hebbian(X, weights)
            if np.mean(y_pred == y) > 0.99:
                break
    return weights, i + 1

def predict_proba_hebbian(X, weights):
    """
    Predict class probabilities using the trained Hebbian network.
    """
    activations = np.dot(X, weights)
    exp_activations = np.exp(activations - np.max(activations, axis=1, keepdims=True))
    return exp_activations / np.sum(exp_activations, axis=1, keepdims=True)

def predict_hebbian(X, weights):
    """
    Predict class labels using the trained Hebbian network.
    """
    proba = predict_proba_hebbian(X, weights)
    return np.argmax(proba, axis=1)

## 3. Visualization Functions
def plot_confusion_matrix(y_true, y_pred, title="Confusion Matrix"):
    """
    Plot confusion matrix using seaborn.
    """
    cm = confusion_matrix(y_true, y_pred)
    plt.figure(figsize=(6, 5))
    sns.heatmap(cm, annot=True, fmt='d', cmap='Blues')
    plt.xlabel('Predicted')
    plt.ylabel('True')
    plt.title(title)
    plt.savefig(f'{output_dir}/hebbian_learning-confusion-matrix.png')
    plt.close()

## 4. Model Training and Evaluation

# Generate synthetic data for demonstration
n_samples = 400
n_features = 4
X = np.random.randn(n_samples, n_features)
y = (X[:, 0] + X[:, 1] > 0).astype(int)

# Split data into train and test sets
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.2, random_state=2220
)

# Hyperparameters explained:
# - learning_rate: Learning rate for weight updates
# - max_iter: Maximum number of iterations
learning_rate = 0.01
max_iter = 1000

# Train Hebbian Network
weights, n_iter = fit_hebbian(X_train, y_train, learning_rate, max_iter)

# Predict and evaluate
y_pred = predict_hebbian(X_test, weights)
accuracy = accuracy_score(y_test, y_pred)
print(f"Accuracy: {accuracy:.4f}")
print("\nClassification Report:")
print(classification_report(y_test, y_pred))

# Plot confusion matrix
plot_confusion_matrix(y_test, y_pred, title="Hebbian Learning Confusion Matrix") 