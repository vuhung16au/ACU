#!/usr/bin/env python3
# -*- coding: utf-8 -*-

## Softmax Regression Implementation
## This notebook demonstrates the implementation of Softmax Regression,
## a generalization of logistic regression for multi-class classification.

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from sklearn.linear_model import LogisticRegression as SklearnLogisticRegression
from sklearn.model_selection import train_test_split, cross_val_score
from sklearn.metrics import (
    accuracy_score, confusion_matrix, classification_report,
    roc_curve, auc
)
from sklearn.preprocessing import StandardScaler, label_binarize

## 2. Set Random Seed
np.random.seed(2220)

## 3. Core Functions
def softmax(z: np.ndarray) -> np.ndarray:
    """
    Compute softmax function.
    
    Args:
        z: Input values
        
    Returns:
        Softmax probabilities
    """
    # Subtract max for numerical stability
    exp_z = np.exp(z - np.max(z, axis=1, keepdims=True))
    return exp_z / np.sum(exp_z, axis=1, keepdims=True)

def compute_cost(
    X: np.ndarray,
    y_one_hot: np.ndarray,
    weights: np.ndarray,
    bias: np.ndarray,
    regularization: float = 0,
    class_weight: dict = None
) -> float:
    """
    Compute cross-entropy cost.
    
    Hyperparameters:
    - regularization (float): L2 regularization parameter. Higher values
      increase model simplicity but may underfit.
    - class_weight (dict): Class weights for imbalanced data. Higher weights
      for minority classes help balance the model.
    
    Args:
        X: Features
        y_one_hot: One-hot encoded target values
        weights: Model weights
        bias: Model bias
        regularization: L2 regularization parameter
        class_weight: Class weights for imbalanced data
        
    Returns:
        Cost value
    """
    n_samples = X.shape[0]
    z = np.dot(X, weights) + bias
    predictions = softmax(z)
    
    # Apply class weights if provided
    if class_weight is not None:
        weights_array = np.ones_like(y_one_hot)
        for class_label, weight in class_weight.items():
            weights_array[:, class_label] = weight
    else:
        weights_array = np.ones_like(y_one_hot)
    
    # Compute cost with weights
    cost = -np.sum(weights_array * y_one_hot * np.log(predictions + 1e-15)) / n_samples
    
    # Add regularization
    if regularization > 0:
        cost += (regularization / (2 * n_samples)) * np.sum(weights**2)
    
    return cost

def fit_softmax_regression(
    X: np.ndarray,
    y: np.ndarray,
    learning_rate: float = 0.01,
    n_iterations: int = 1000,
    regularization: float = 0,
    class_weight: dict = None
) -> tuple:
    """
    Train Softmax Regression model using gradient descent.
    
    Hyperparameters:
    - learning_rate (float): Step size for gradient descent. Higher values
      make learning faster but may lead to instability.
    - n_iterations (int): Number of training iterations. More iterations
      generally lead to better convergence but require more time.
    - regularization (float): L2 regularization parameter. Higher values
      increase model simplicity but may underfit.
    - class_weight (dict): Class weights for imbalanced data. Higher weights
      for minority classes help balance the model.
    
    Args:
        X: Training features
        y: Target values
        learning_rate: Step size for gradient descent
        n_iterations: Number of iterations for training
        regularization: L2 regularization parameter
        class_weight: Class weights for imbalanced data
        
    Returns:
        Tuple of (weights, bias, cost_history, classes)
    """
    n_samples, n_features = X.shape
    classes = np.unique(y)
    n_classes = len(classes)
    
    # Convert y to one-hot encoding
    y_one_hot = np.zeros((n_samples, n_classes))
    y_one_hot[np.arange(n_samples), y] = 1
    
    # Initialize parameters
    weights = np.zeros((n_features, n_classes))
    bias = np.zeros(n_classes)
    cost_history = []
    
    # Gradient descent
    for _ in range(n_iterations):
        # Forward pass
        z = np.dot(X, weights) + bias
        predictions = softmax(z)
        
        # Compute gradients
        dw = np.dot(X.T, (predictions - y_one_hot)) / n_samples
        db = np.sum(predictions - y_one_hot, axis=0) / n_samples
        
        # Add regularization
        if regularization > 0:
            dw += (regularization / n_samples) * weights
        
        # Update parameters
        weights -= learning_rate * dw
        bias -= learning_rate * db
        
        # Compute cost
        cost = compute_cost(X, y_one_hot, weights, bias, regularization, class_weight)
        cost_history.append(cost)
    
    return weights, bias, cost_history, classes

def predict_proba(
    X: np.ndarray,
    weights: np.ndarray,
    bias: np.ndarray
) -> np.ndarray:
    """
    Predict class probabilities.
    
    Args:
        X: Features to predict
        weights: Model weights
        bias: Model bias
        
    Returns:
        Predicted probabilities for each class
    """
    z = np.dot(X, weights) + bias
    return softmax(z)

def predict(
    X: np.ndarray,
    weights: np.ndarray,
    bias: np.ndarray
) -> np.ndarray:
    """
    Make predictions using the trained model.
    
    Args:
        X: Features to predict
        weights: Model weights
        bias: Model bias
        
    Returns:
        Predicted classes
    """
    return np.argmax(predict_proba(X, weights, bias), axis=1)

## 4. Data Generation
def generate_multiclass_data(n_samples: int = 1000, n_classes: int = 3) -> tuple:
    """
    Generate sample data for multi-class classification.
    
    Args:
        n_samples: Number of samples to generate
        n_classes: Number of classes
        
    Returns:
        Tuple of (X, y) features and target
    """
    # Generate data for each class
    X = []
    y = []
    
    for i in range(n_classes):
        # Generate points in different regions
        angle = 2 * np.pi * i / n_classes
        center = np.array([np.cos(angle), np.sin(angle)]) * 2
        X_class = np.random.randn(n_samples//n_classes, 2) + center
        X.append(X_class)
        y.extend([i] * (n_samples//n_classes))
    
    return np.vstack(X), np.array(y)

## 5. Visualization Functions
def plot_decision_boundary(
    X: np.ndarray,
    y: np.ndarray,
    weights: np.ndarray,
    bias: np.ndarray,
    title: str
):
    """
    Plot the decision boundary and data points.
    
    Args:
        X: Features
        y: Target values
        weights: Model weights
        bias: Model bias
        title: Plot title
    """
    h = 0.02  # Step size
    x_min, x_max = X[:, 0].min() - 1, X[:, 0].max() + 1
    y_min, y_max = X[:, 1].min() - 1, X[:, 1].max() + 1
    xx, yy = np.meshgrid(np.arange(x_min, x_max, h),
                        np.arange(y_min, y_max, h))
    
    # Predict for each point in the mesh
    Z = predict(np.c_[xx.ravel(), yy.ravel()], weights, bias)
    Z = Z.reshape(xx.shape)
    
    # Create figure
    plt.figure(figsize=(10, 6))
    
    # Plot decision boundary
    sns.scatterplot(x=X[:, 0], y=X[:, 1], hue=y, palette='deep')
    plt.contourf(xx, yy, Z, alpha=0.4)
    
    plt.xlabel('Feature 1')
    plt.ylabel('Feature 2')
    plt.title(title)
    plt.savefig('algorithms/softmax_regression/softmax_regression-decision-boundary.png')
    plt.close()

def plot_confusion_matrix(y_true: np.ndarray, y_pred: np.ndarray, title: str):
    """
    Plot confusion matrix.
    
    Args:
        y_true: True labels
        y_pred: Predicted labels
        title: Plot title
    """
    cm = confusion_matrix(y_true, y_pred)
    plt.figure(figsize=(8, 6))
    sns.heatmap(cm, annot=True, fmt='d', cmap='Blues')
    plt.xlabel('Predicted')
    plt.ylabel('True')
    plt.title(title)
    plt.savefig('algorithms/softmax_regression/softmax_regression-confusion-matrix.png')
    plt.close()

def plot_cost_history(cost_history: list, title: str):
    """
    Plot the cost history during training.
    
    Args:
        cost_history: List of cost values
        title: Plot title
    """
    plt.figure(figsize=(10, 6))
    sns.lineplot(data=pd.DataFrame({'Iteration': range(len(cost_history)),
                                   'Cost': cost_history}),
                 x='Iteration', y='Cost')
    plt.title(title)
    plt.savefig('algorithms/softmax_regression/softmax_regression-cost-history.png')
    plt.close()

## 6. Main Execution
# Generate data
X, y = generate_multiclass_data()

# Split data
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.2, random_state=2220
)

# Scale features
scaler = StandardScaler()
X_train_scaled = scaler.fit_transform(X_train)
X_test_scaled = scaler.transform(X_test)

# Train custom model
weights, bias, cost_history, classes = fit_softmax_regression(
    X_train_scaled,
    y_train,
    learning_rate=0.01,
    n_iterations=1000,
    regularization=0.1
)

# Train scikit-learn model
sklearn_model = SklearnLogisticRegression(
    multi_class='multinomial',
    solver='lbfgs',
    C=1/0.1,  # Inverse of regularization strength
    max_iter=1000,
    random_state=2220
)
sklearn_model.fit(X_train_scaled, y_train)

# Make predictions
y_pred_custom = predict(X_test_scaled, weights, bias)
y_pred_sklearn = sklearn_model.predict(X_test_scaled)

# Print results
print("\nCustom Model Results:")
print(f"Accuracy: {accuracy_score(y_test, y_pred_custom):.4f}")
print("\nClassification Report:")
print(classification_report(y_test, y_pred_custom))

print("\nScikit-learn Model Results:")
print(f"Accuracy: {accuracy_score(y_test, y_pred_sklearn):.4f}")
print("\nClassification Report:")
print(classification_report(y_test, y_pred_sklearn))

# Plot results
plot_decision_boundary(X_train_scaled, y_train, weights, bias,
                      "Custom Softmax Regression Decision Boundary")
plot_confusion_matrix(y_test, y_pred_custom,
                     "Custom Softmax Regression Confusion Matrix")
plot_cost_history(cost_history, "Custom Softmax Regression Cost History")