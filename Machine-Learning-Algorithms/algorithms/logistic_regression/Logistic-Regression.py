#!/usr/bin/env python3
# -*- coding: utf-8 -*-

# Logistic Regression Implementation
# This notebook demonstrates both custom and scikit-learn implementations of Logistic Regression.

# Table of Contents:
# 1. Import Required Libraries
# 2. Data Generation and Preprocessing
# 3. Custom Logistic Regression Implementation
# 4. Model Training and Evaluation
# 5. Visualization
# 6. Results and Analysis

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.linear_model import LogisticRegression as SklearnLogisticRegression
from sklearn.model_selection import train_test_split, cross_val_score
from sklearn.metrics import (
    accuracy_score, precision_score, recall_score, f1_score,
    roc_curve, auc, confusion_matrix, classification_report
)
from sklearn.preprocessing import StandardScaler
import os

# Set random seed for reproducibility
np.random.seed(2220)

# Create output directory for visuals
output_dir = 'algorithms/logistic_regression'
os.makedirs(output_dir, exist_ok=True)

## 2. Data Generation and Preprocessing
def generate_classification_data(n_samples=1000, class_separation=2.0):
    """
    Generate sample data for binary classification.
    
    Parameters:
    -----------
    n_samples : int, default=1000
        Number of samples to generate
    class_separation : float, default=2.0
        Separation between class centers
        
    Returns:
    --------
    tuple
        X : numpy.ndarray
            Features array of shape (n_samples, 2)
        y : numpy.ndarray
            Target array of shape (n_samples,)
    """
    # Generate two classes
    X1 = np.random.randn(n_samples//2, 2) + np.array([class_separation, class_separation])
    X2 = np.random.randn(n_samples//2, 2) + np.array([-class_separation, -class_separation])
    X = np.vstack([X1, X2])
    y = np.hstack([np.ones(n_samples//2), np.zeros(n_samples//2)])
    
    return X, y

## 3. Custom Logistic Regression Implementation
def sigmoid(z):
    """
    Compute sigmoid function.
    
    Parameters:
    -----------
    z : numpy.ndarray
        Input values
        
    Returns:
    --------
    numpy.ndarray
        Sigmoid values between 0 and 1
    """
    return 1 / (1 + np.exp(-np.clip(z, -500, 500)))

def compute_cost(X, y, weights, bias, regularization=0, class_weight=None):
    """
    Compute binary cross-entropy cost with optional regularization and class weights.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Features array
    y : numpy.ndarray
        Target values
    weights : numpy.ndarray
        Model weights
    bias : float
        Model bias
    regularization : float, default=0
        L2 regularization parameter
    class_weight : dict, default=None
        Class weights for imbalanced data
        
    Returns:
    --------
    float
        Cost value
    """
    n_samples = X.shape[0]
    z = np.dot(X, weights) + bias
    predictions = sigmoid(z)
    
    # Apply class weights if provided
    if class_weight is not None:
        weights_array = np.ones_like(y)
        for class_label, weight in class_weight.items():
            weights_array[y == class_label] = weight
    else:
        weights_array = np.ones_like(y)
    
    # Compute cost with weights
    cost = -np.sum(weights_array * (y * np.log(predictions + 1e-15) + 
                                  (1 - y) * np.log(1 - predictions + 1e-15))) / n_samples
    
    # Add regularization
    if regularization > 0:
        cost += (regularization / (2 * n_samples)) * np.sum(weights**2)
    
    return cost

def custom_logistic_regression_fit(X, y, learning_rate=0.01, n_iterations=1000, 
                                 regularization=0, class_weight=None):
    """
    Train a logistic regression model using gradient descent.
    
    Hyperparameters:
    ---------------
    learning_rate : float, default=0.01
        Step size for gradient descent. Controls how quickly the model learns.
        - Too large values may cause overshooting
        - Too small values may lead to slow convergence
    n_iterations : int, default=1000
        Number of training iterations
        - More iterations can lead to better convergence
        - May cause overfitting if too high
    regularization : float, default=0
        L2 regularization parameter
        - Helps prevent overfitting by penalizing large weights
        - Higher values increase regularization strength
    class_weight : dict, default=None
        Class weights for handling imbalanced data
        - Weights are applied to the cost function
        - Gives more importance to minority classes
    
    Parameters:
    -----------
    X : numpy.ndarray
        Training features
    y : numpy.ndarray
        Target values
    learning_rate : float
        Step size for gradient descent
    n_iterations : int
        Number of iterations for training
    regularization : float
        L2 regularization parameter
    class_weight : dict
        Class weights for imbalanced data
        
    Returns:
    --------
    tuple
        weights : numpy.ndarray
            Trained weights
        bias : float
            Trained bias
        cost_history : list
            Training cost history
    """
    n_samples, n_features = X.shape
    
    # Initialize parameters
    weights = np.zeros(n_features)
    bias = 0
    cost_history = []
    
    # Gradient descent
    for _ in range(n_iterations):
        # Forward pass
        z = np.dot(X, weights) + bias
        predictions = sigmoid(z)
        
        # Compute gradients
        dw = np.dot(X.T, (predictions - y)) / n_samples
        db = np.sum(predictions - y) / n_samples
        
        # Add regularization
        if regularization > 0:
            dw += (regularization / n_samples) * weights
        
        # Update parameters
        weights -= learning_rate * dw
        bias -= learning_rate * db
        
        # Compute cost
        cost = compute_cost(X, y, weights, bias, regularization, class_weight)
        cost_history.append(cost)
    
    return weights, bias, cost_history

def custom_logistic_regression_predict_proba(X, weights, bias):
    """
    Predict class probabilities.
    
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
        Predicted probabilities
    """
    z = np.dot(X, weights) + bias
    return sigmoid(z)

def custom_logistic_regression_predict(X, weights, bias, threshold=0.5):
    """
    Make predictions using the trained model.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Features to predict
    weights : numpy.ndarray
        Trained weights
    bias : float
        Trained bias
    threshold : float, default=0.5
        Classification threshold
        
    Returns:
    --------
    numpy.ndarray
        Predicted classes
    """
    return (custom_logistic_regression_predict_proba(X, weights, bias) >= threshold).astype(int)

## 4. Model Training and Evaluation
# Generate and prepare data
X, y = generate_classification_data(n_samples=1000, class_separation=2.0)
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.2, random_state=2220
)

# Scale features
scaler = StandardScaler()
X_train_scaled = scaler.fit_transform(X_train)
X_test_scaled = scaler.transform(X_test)

# Train custom model
weights, bias, cost_history = custom_logistic_regression_fit(
    X_train_scaled, y_train,
    learning_rate=0.01,    # Small learning rate for stable convergence
    n_iterations=1000,     # Sufficient iterations for convergence
    regularization=0.1,    # Moderate L2 regularization
    class_weight={0: 1.2, 1: 0.8}  # Slight class weight adjustment
)

# Get predictions
custom_predictions = custom_logistic_regression_predict(X_test_scaled, weights, bias)
custom_predictions_proba = custom_logistic_regression_predict_proba(X_test_scaled, weights, bias)

# Train scikit-learn model
sklearn_model = SklearnLogisticRegression(
    C=1.0,              # Inverse of regularization strength
    max_iter=1000,      # Maximum number of iterations
    class_weight='balanced'  # Automatically handle class weights
)
sklearn_model.fit(X_train_scaled, y_train)
sklearn_predictions = sklearn_model.predict(X_test_scaled)
sklearn_predictions_proba = sklearn_model.predict_proba(X_test_scaled)[:, 1]

## 5. Visualization
def plot_decision_boundary(X, y, weights, bias, title):
    """
    Plot the decision boundary and data points using seaborn and save to PNG file.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Features
    y : numpy.ndarray
        Target values
    weights : numpy.ndarray
        Trained weights
    bias : float
        Trained bias
    title : str
        Plot title
    """
    h = 0.02  # Step size
    x_min, x_max = X[:, 0].min() - 1, X[:, 0].max() + 1
    y_min, y_max = X[:, 1].min() - 1, X[:, 1].max() + 1
    xx, yy = np.meshgrid(np.arange(x_min, x_max, h),
                        np.arange(y_min, y_max, h))
    
    # Predict for each point in the mesh
    Z = custom_logistic_regression_predict_proba(
        np.c_[xx.ravel(), yy.ravel()], weights, bias
    )
    Z = Z.reshape(xx.shape)
    
    # Create plot
    plt.figure(figsize=(10, 8))
    sns.set_style("whitegrid")
    
    # Plot decision boundary
    plt.contourf(xx, yy, Z, alpha=0.4, cmap='RdYlBu')
    
    # Plot data points
    sns.scatterplot(x=X[:, 0], y=X[:, 1], hue=y, palette='RdYlBu', alpha=0.8)
    
    plt.title(title, fontsize=14)
    plt.xlabel('Feature 1', fontsize=12)
    plt.ylabel('Feature 2', fontsize=12)
    
    # Save plot
    filename = f'{output_dir}/logistic_regression-decision_boundary.png'
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
    fpr, tpr, _ = roc_curve(y_true, y_pred_proba)
    roc_auc = auc(fpr, tpr)
    
    plt.figure(figsize=(8, 6))
    sns.set_style("whitegrid")
    
    plt.plot(fpr, tpr, color='darkorange', lw=2, label=f'ROC curve (AUC = {roc_auc:.2f})')
    plt.plot([0, 1], [0, 1], color='navy', lw=2, linestyle='--')
    
    plt.xlim([0.0, 1.0])
    plt.ylim([0.0, 1.05])
    plt.xlabel('False Positive Rate', fontsize=12)
    plt.ylabel('True Positive Rate', fontsize=12)
    plt.title(title, fontsize=14)
    plt.legend(loc="lower right")
    
    # Save plot
    filename = f'{output_dir}/logistic_regression-roc_curve.png'
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
    cm = confusion_matrix(y_true, y_pred)
    
    plt.figure(figsize=(8, 6))
    sns.set_style("whitegrid")
    
    sns.heatmap(cm, annot=True, fmt='d', cmap='Blues',
                xticklabels=['Class 0', 'Class 1'],
                yticklabels=['Class 0', 'Class 1'])
    
    plt.xlabel('Predicted', fontsize=12)
    plt.ylabel('True', fontsize=12)
    plt.title(title, fontsize=14)
    
    # Save plot
    filename = f'{output_dir}/logistic_regression-confusion_matrix.png'
    plt.savefig(filename)
    plt.close()

# Plot results
plot_decision_boundary(X_test_scaled, y_test, weights, bias, 
                      'Custom Logistic Regression Decision Boundary')
plot_roc_curve(y_test, custom_predictions_proba, 
              'Custom Logistic Regression ROC Curve')
plot_confusion_matrix(y_test, custom_predictions, 
                    'Custom Logistic Regression Confusion Matrix')

## 6. Results and Analysis
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

# Plot cost history
plt.figure(figsize=(10, 6))
sns.set_style("whitegrid")
plt.plot(cost_history)
plt.title('Training Cost History', fontsize=14)
plt.xlabel('Iteration', fontsize=12)
plt.ylabel('Cost', fontsize=12)

# Save plot
filename = f'{output_dir}/logistic_regression-cost_history.png'
plt.savefig(filename)
plt.close() 