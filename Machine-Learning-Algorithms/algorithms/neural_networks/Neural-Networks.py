#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
Neural Network-Based Models Implementation
This notebook demonstrates implementations of Perceptron and Multilayer Perceptron (MLP).

Table of Contents:
1. Import Required Libraries
2. Data Generation and Preprocessing
3. Perceptron Implementation
4. Multilayer Perceptron Implementation
5. Model Training and Evaluation
6. Visualization
7. Results and Analysis
"""

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.neural_network import MLPClassifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import (
    accuracy_score, confusion_matrix, classification_report,
    mean_squared_error, precision_score, recall_score, f1_score, 
    roc_curve, auc
)
from sklearn.preprocessing import StandardScaler
import os

# Set random seed for reproducibility
np.random.seed(2220)

## 2. Data Generation and Preprocessing
def generate_data(n_samples=1000, n_features=2, class_separation=1.0):
    """
    Generate sample data for classification.
    
    Parameters:
    -----------
    n_samples : int, default=1000
        Number of samples to generate
    n_features : int, default=2
        Number of features
    class_separation : float, default=1.0
        Separation between classes
        
    Returns:
    --------
    tuple
        X : numpy.ndarray
            Features array of shape (n_samples, n_features)
        y : numpy.ndarray
            Target array of shape (n_samples,)
    """
    # Generate features
    X = np.random.randn(n_samples, n_features)
    
    # Generate target with non-linear boundary
    y = np.zeros(n_samples)
    y[X[:, 0]**2 + X[:, 1]**2 > class_separation] = 1
    
    return X, y

## 3. Perceptron Implementation
def perceptron_activation(x):
    """
    Step activation function.
    
    Parameters:
    -----------
    x : float
        Input value
        
    Returns:
    --------
    int
        1 if x >= 0, -1 otherwise
    """
    return np.where(x >= 0, 1, -1)

def train_perceptron(X, y, learning_rate=0.01, max_iter=1000, random_state=None):
    """
    Train a Perceptron model.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Training features
    y : numpy.ndarray
        Target values
    learning_rate : float, default=0.01
        Learning rate for weight updates
    max_iter : int, default=1000
        Maximum number of iterations
    random_state : int, default=None
        Random seed
        
    Returns:
    --------
    tuple
        weights : numpy.ndarray
            Trained weights
        bias : float
            Trained bias
        errors : list
            List of errors per iteration
    """
    if random_state is not None:
        np.random.seed(random_state)
        
    n_samples, n_features = X.shape
    
    # Initialize weights and bias
    weights = np.random.randn(n_features)
    bias = 0
    errors = []
    
    # Convert labels to -1 and 1
    y = np.where(y == 0, -1, 1)
    
    # Training loop
    for _ in range(max_iter):
        iteration_errors = 0
        for i in range(n_samples):
            # Compute prediction
            prediction = perceptron_activation(np.dot(X[i], weights) + bias)
            
            # Update weights if prediction is wrong
            if prediction != y[i]:
                weights += learning_rate * y[i] * X[i]
                bias += learning_rate * y[i]
                iteration_errors += 1
        
        errors.append(iteration_errors)
        
        # Stop if no errors
        if iteration_errors == 0:
            break
            
    return weights, bias, errors

def predict_perceptron(X, weights, bias):
    """
    Make predictions using a trained Perceptron model.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Features to predict
    weights : numpy.ndarray
        Trained weights
    bias : float
        Trained bias
        
    Returns:
    --------
    numpy.ndarray
        Predicted classes
    """
    return perceptron_activation(np.dot(X, weights) + bias)

## 4. Multilayer Perceptron Implementation
def sigmoid(x):
    """
    Sigmoid activation function.
    
    Parameters:
    -----------
    x : numpy.ndarray
        Input values
        
    Returns:
    --------
    numpy.ndarray
        Sigmoid of input values
    """
    return 1 / (1 + np.exp(-np.clip(x, -500, 500)))

def sigmoid_derivative(x):
    """
    Derivative of sigmoid function.
    
    Parameters:
    -----------
    x : numpy.ndarray
        Input values
        
    Returns:
    --------
    numpy.ndarray
        Derivative of sigmoid
    """
    return x * (1 - x)

def initialize_mlp_weights(n_features, hidden_layer_sizes, n_classes):
    """
    Initialize weights and biases for all layers.
    
    Parameters:
    -----------
    n_features : int
        Number of input features
    hidden_layer_sizes : tuple
        Number of neurons in each hidden layer
    n_classes : int
        Number of output classes
        
    Returns:
    --------
    tuple
        weights : list
            List of weight matrices
        biases : list
            List of bias vectors
    """
    layer_sizes = [n_features] + list(hidden_layer_sizes) + [n_classes]
    weights = []
    biases = []
    
    for i in range(len(layer_sizes) - 1):
        # He initialization
        weights.append(
            np.random.randn(layer_sizes[i], layer_sizes[i + 1]) * np.sqrt(2.0 / layer_sizes[i])
        )
        biases.append(np.zeros(layer_sizes[i + 1]))
        
    return weights, biases

def forward_pass(X, weights, biases):
    """
    Perform forward pass through the network.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Input features
    weights : list
        List of weight matrices
    biases : list
        List of bias vectors
        
    Returns:
    --------
    tuple
        activations : list
            Layer activations
        pre_activations : list
            Layer pre-activations
    """
    activations = [X]
    pre_activations = []
    
    for i in range(len(weights)):
        pre_activation = np.dot(activations[-1], weights[i]) + biases[i]
        pre_activations.append(pre_activation)
        
        if i == len(weights) - 1:
            # Output layer: softmax
            exp_scores = np.exp(pre_activation - np.max(pre_activation, axis=1, keepdims=True))
            activation = exp_scores / np.sum(exp_scores, axis=1, keepdims=True)
        else:
            # Hidden layers: sigmoid
            activation = sigmoid(pre_activation)
        
        activations.append(activation)
    
    return activations, pre_activations

def backward_pass(activations, pre_activations, weights, biases, y):
    """
    Perform backward pass through the network.
    
    Parameters:
    -----------
    activations : list
        Layer activations
    pre_activations : list
        Layer pre-activations
    weights : list
        List of weight matrices
    biases : list
        List of bias vectors
    y : numpy.ndarray
        Target values
        
    Returns:
    --------
    tuple
        weight_gradients : list
            Weight gradients for each layer
        bias_gradients : list
            Bias gradients for each layer
    """
    n_samples = y.shape[0]
    weight_gradients = [np.zeros_like(w) for w in weights]
    bias_gradients = [np.zeros_like(b) for b in biases]
    
    # Output layer error
    error = activations[-1] - y
    
    # Backpropagate error
    for i in reversed(range(len(weights))):
        # Compute gradients
        weight_gradients[i] = np.dot(activations[i].T, error) / n_samples
        bias_gradients[i] = np.sum(error, axis=0) / n_samples
        
        if i > 0:
            # Propagate error to previous layer
            error = np.dot(error, weights[i].T) * sigmoid_derivative(activations[i])
    
    return weight_gradients, bias_gradients

def train_mlp(X, y, hidden_layer_sizes=(100,), learning_rate=0.01,
             max_iter=1000, batch_size=32, random_state=None):
    """
    Train an MLP model.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Training features
    y : numpy.ndarray
        Target values
    hidden_layer_sizes : tuple, default=(100,)
        Number of neurons in each hidden layer
    learning_rate : float, default=0.01
        Learning rate for weight updates
    max_iter : int, default=1000
        Maximum number of iterations
    batch_size : int, default=32
        Size of mini-batches
    random_state : int, default=None
        Random seed
        
    Returns:
    --------
    tuple
        weights : list
            Trained weights
        biases : list
            Trained biases
        losses : list
            Training losses
    """
    if random_state is not None:
        np.random.seed(random_state)
        
    n_samples, n_features = X.shape
    n_classes = len(np.unique(y))
    
    # Convert y to integers for one-hot encoding
    y = y.astype(int)
    
    # One-hot encode target values
    y_onehot = np.zeros((n_samples, n_classes))
    y_onehot[np.arange(n_samples), y] = 1
    
    # Initialize weights
    weights, biases = initialize_mlp_weights(n_features, hidden_layer_sizes, n_classes)
    losses = []
    
    # Training loop
    for _ in range(max_iter):
        # Mini-batch training
        indices = np.random.permutation(n_samples)
        for start_idx in range(0, n_samples, batch_size):
            batch_indices = indices[start_idx:start_idx + batch_size]
            X_batch = X[batch_indices]
            y_batch = y_onehot[batch_indices]
            
            # Forward pass
            activations, pre_activations = forward_pass(X_batch, weights, biases)
            
            # Backward pass
            weight_gradients, bias_gradients = backward_pass(
                activations, pre_activations, weights, biases, y_batch
            )
            
            # Update weights and biases
            for i in range(len(weights)):
                weights[i] -= learning_rate * weight_gradients[i]
                biases[i] -= learning_rate * bias_gradients[i]
        
        # Compute loss
        activations, _ = forward_pass(X, weights, biases)
        loss = -np.sum(y_onehot * np.log(activations[-1] + 1e-15)) / n_samples
        losses.append(loss)
        
    return weights, biases, losses

def predict_mlp(X, weights, biases):
    """
    Make predictions using a trained MLP model.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Features to predict
    weights : list
        Trained weights
    biases : list
        Trained biases
        
    Returns:
    --------
    numpy.ndarray
        Predicted classes
    """
    activations, _ = forward_pass(X, weights, biases)
    return np.argmax(activations[-1], axis=1)

def predict_proba_mlp(X, weights, biases):
    """
    Predict class probabilities using a trained MLP model.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Features to predict
    weights : list
        Trained weights
    biases : list
        Trained biases
        
    Returns:
    --------
    numpy.ndarray
        Class probabilities
    """
    activations, _ = forward_pass(X, weights, biases)
    return activations[-1]

## 5. Model Training and Evaluation
# Generate and prepare data
X, y = generate_data(n_samples=1000, n_features=2, class_separation=1.0)
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.2, random_state=2220
)

# Scale features
scaler = StandardScaler()
X_train_scaled = scaler.fit_transform(X_train)
X_test_scaled = scaler.transform(X_test)

# Train custom MLP
weights, biases, losses = train_mlp(
    X_train_scaled, y_train,
    hidden_layer_sizes=(100, 50),  # Two hidden layers
    learning_rate=0.01,            # Small learning rate for stable convergence
    max_iter=1000,                 # Sufficient iterations for convergence
    batch_size=32,                 # Standard batch size
    random_state=2220             # For reproducibility
)
custom_predictions = predict_mlp(X_test_scaled, weights, biases)
custom_predictions_proba = predict_proba_mlp(X_test_scaled, weights, biases)[:, 1]

# Train scikit-learn MLP
sklearn_mlp = MLPClassifier(
    hidden_layer_sizes=(100, 50),  # Two hidden layers
    learning_rate_init=0.01,       # Small learning rate
    max_iter=1000,                 # Maximum iterations
    batch_size=32,                 # Batch size
    random_state=2220             # For reproducibility
)
sklearn_mlp.fit(X_train_scaled, y_train)
sklearn_predictions = sklearn_mlp.predict(X_test_scaled)
sklearn_predictions_proba = sklearn_mlp.predict_proba(X_test_scaled)[:, 1]

## 6. Visualization
def plot_decision_boundary(model, X, y, title):
    """
    Plot decision boundary and data points using seaborn and save to PNG file.
    
    Parameters:
    -----------
    model : object
        Trained model
    X : numpy.ndarray
        Features
    y : numpy.ndarray
        Target values
    title : str
        Plot title
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/neural_networks/neural_networks', exist_ok=True)
    
    # Create mesh grid
    h = 0.02  # Step size
    x_min, x_max = X[:, 0].min() - 1, X[:, 0].max() + 1
    y_min, y_max = X[:, 1].min() - 1, X[:, 1].max() + 1
    xx, yy = np.meshgrid(np.arange(x_min, x_max, h),
                        np.arange(y_min, y_max, h))
    
    # Predict for each point in mesh grid
    if hasattr(model, 'predict_proba'):
        Z = model.predict_proba(np.c_[xx.ravel(), yy.ravel()])[:, 1]
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
    filename = f'algorithms/neural_networks/neural_networks/neural_networks-{title.lower().replace(" ", "_")}.png'
    plt.savefig(filename)
    plt.close()

def plot_confusion_matrix(y_true, y_pred, title):
    """
    Plot confusion matrix using seaborn and save to PNG file.
    
    Parameters:
    -----------
    y_true : numpy.ndarray
        True labels
    y_pred : numpy.ndarray
        Predicted labels
    title : str
        Plot title
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/neural_networks/neural_networks', exist_ok=True)
    
    # Compute confusion matrix
    cm = confusion_matrix(y_true, y_pred)
    
    # Create plot
    plt.figure(figsize=(8, 6))
    sns.heatmap(cm, annot=True, fmt='d', cmap='Blues')
    plt.title(title)
    plt.xlabel('Predicted')
    plt.ylabel('True')
    
    # Save plot
    filename = f'algorithms/neural_networks/neural_networks/neural_networks-{title.lower().replace(" ", "_")}.png'
    plt.savefig(filename)
    plt.close()

def plot_training_history(losses, title):
    """
    Plot training history using seaborn and save to PNG file.
    
    Parameters:
    -----------
    losses : list
        List of training losses
    title : str
        Plot title
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/neural_networks/neural_networks', exist_ok=True)
    
    # Create plot
    plt.figure(figsize=(10, 6))
    plt.plot(losses, label='Training Loss')
    plt.title(title)
    plt.xlabel('Iteration')
    plt.ylabel('Value')
    plt.legend()
    
    # Save plot
    filename = f'algorithms/neural_networks/neural_networks/neural_networks-{title.lower().replace(" ", "_")}.png'
    plt.savefig(filename)
    plt.close()

def plot_roc_curve(y_true, y_pred_proba, title):
    """
    Plot ROC curve using seaborn and save to PNG file.
    
    Parameters:
    -----------
    y_true : numpy.ndarray
        True labels
    y_pred_proba : numpy.ndarray
        Predicted probabilities
    title : str
        Plot title
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/neural_networks/neural_networks', exist_ok=True)
    
    # Compute ROC curve
    fpr, tpr, _ = roc_curve(y_true, y_pred_proba)
    roc_auc = auc(fpr, tpr)
    
    # Create plot
    plt.figure(figsize=(8, 6))
    plt.plot(fpr, tpr, color='darkorange', lw=2,
             label=f'ROC curve (AUC = {roc_auc:.2f})')
    plt.plot([0, 1], [0, 1], color='navy', lw=2, linestyle='--')
    plt.xlim([0.0, 1.0])
    plt.ylim([0.0, 1.05])
    plt.xlabel('False Positive Rate')
    plt.ylabel('True Positive Rate')
    plt.title(title)
    plt.legend(loc="lower right")
    
    # Save plot
    filename = f'algorithms/neural_networks/neural_networks/neural_networks-{title.lower().replace(" ", "_")}.png'
    plt.savefig(filename)
    plt.close()

# Plot results
plot_decision_boundary(sklearn_mlp, X_test_scaled, y_test, 
                      'Custom MLP Decision Boundary')
plot_confusion_matrix(y_test, custom_predictions, 
                    'Custom MLP Confusion Matrix')
plot_training_history(losses, 'Custom MLP Training History')

## 7. Results and Analysis
# Print evaluation metrics
print("\nCustom Implementation Results:")
print(f"Accuracy: {accuracy_score(y_test, custom_predictions):.4f}")
print(f"Precision: {precision_score(y_test, custom_predictions):.4f}")
print(f"Recall: {recall_score(y_test, custom_predictions):.4f}")
print(f"F1 Score: {f1_score(y_test, custom_predictions):.4f}")

print("\nScikit-learn Implementation Results:")
print(f"Accuracy: {accuracy_score(y_test, sklearn_predictions):.4f}")
print(f"Precision: {precision_score(y_test, sklearn_predictions):.4f}")
print(f"Recall: {recall_score(y_test, sklearn_predictions):.4f}")
print(f"F1 Score: {f1_score(y_test, sklearn_predictions):.4f}")

# Print classification report
print("\nCustom Implementation Classification Report:")
print(classification_report(y_test, custom_predictions))

print("\nScikit-learn Implementation Classification Report:")
print(classification_report(y_test, sklearn_predictions)) 