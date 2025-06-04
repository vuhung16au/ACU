#!/usr/bin/env python3
# -*- coding: utf-8 -*-

## Support Vector Machines (SVM) Implementation
## This notebook demonstrates both custom and scikit-learn implementations of SVM.
## SVM is a powerful supervised learning algorithm used for classification and regression tasks.
## It finds the optimal hyperplane that maximizes the margin between classes.

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from sklearn.svm import SVC
from sklearn.model_selection import train_test_split, GridSearchCV
from sklearn.metrics import (
    accuracy_score, confusion_matrix, classification_report,
    roc_curve, auc
)
from sklearn.preprocessing import StandardScaler
from cvxopt import matrix, solvers
import os

## 2. Set Random Seed
np.random.seed(2220)

## 3. Data Generation
def generate_data(n_samples: int = 100) -> tuple:
    """
    Generate sample data for binary classification.
    
    Args:
        n_samples: Number of samples to generate
        
    Returns:
        Tuple of (X, y) features and target
    """
    # Generate two classes
    X1 = np.random.randn(n_samples//2, 2) + np.array([2, 2])
    X2 = np.random.randn(n_samples//2, 2) + np.array([-2, -2])
    X = np.vstack([X1, X2])
    y = np.hstack([np.ones(n_samples//2), -np.ones(n_samples//2)])
    
    return X, y

## 4. Kernel Functions
def kernel_function(
    x1: np.ndarray,
    x2: np.ndarray,
    kernel: str = 'linear',
    gamma: float = 'scale',
    degree: int = 3,
    coef0: float = 0.0
) -> float:
    """
    Compute kernel function between two points.
    
    Hyperparameters:
    - kernel (str): Type of kernel function ('linear', 'poly', 'rbf', 'sigmoid').
      Different kernels can capture different types of decision boundaries.
    - gamma (float): Kernel coefficient for 'rbf', 'poly', 'sigmoid' kernels.
      Controls the influence of individual training samples.
    - degree (int): Degree of polynomial kernel. Higher degrees can capture more
      complex decision boundaries but may lead to overfitting.
    - coef0 (float): Independent term in kernel function. Used in polynomial and
      sigmoid kernels.
    
    Args:
        x1: First point
        x2: Second point
        kernel: Type of kernel
        gamma: Kernel coefficient
        degree: Degree of polynomial kernel
        coef0: Independent term in kernel function
        
    Returns:
        Kernel value
    """
    if kernel == 'linear':
        return np.dot(x1, x2)
    elif kernel == 'poly':
        return (gamma * np.dot(x1, x2) + coef0) ** degree
    elif kernel == 'rbf':
        return np.exp(-gamma * np.sum((x1 - x2) ** 2))
    elif kernel == 'sigmoid':
        return np.tanh(gamma * np.dot(x1, x2) + coef0)
    else:
        raise ValueError(f"Unknown kernel: {kernel}")

def compute_kernel_matrix(
    X: np.ndarray,
    kernel: str = 'linear',
    gamma: float = 'scale',
    degree: int = 3,
    coef0: float = 0.0
) -> np.ndarray:
    """
    Compute kernel matrix for training data.
    
    Args:
        X: Training features
        kernel: Type of kernel
        gamma: Kernel coefficient
        degree: Degree of polynomial kernel
        coef0: Independent term in kernel function
        
    Returns:
        Kernel matrix
    """
    n_samples = X.shape[0]
    K = np.zeros((n_samples, n_samples))
    for i in range(n_samples):
        for j in range(n_samples):
            K[i, j] = kernel_function(X[i], X[j], kernel, gamma, degree, coef0)
    return K

## 5. SVM Implementation
def fit_svm(
    X: np.ndarray,
    y: np.ndarray,
    kernel: str = 'linear',
    C: float = 1.0,
    gamma: float = 'scale',
    degree: int = 3,
    coef0: float = 0.0
) -> tuple:
    """
    Train an SVM model using quadratic programming.
    
    Hyperparameters:
    - C (float): Regularization parameter. Controls the trade-off between having
      a large margin and ensuring that points lie on the correct side of the margin.
      Higher values of C lead to a smaller margin but better classification of
      training points.
    
    Args:
        X: Training features
        y: Target values (-1 or 1)
        kernel: Type of kernel
        C: Regularization parameter
        gamma: Kernel coefficient
        degree: Degree of polynomial kernel
        coef0: Independent term in kernel function
        
    Returns:
        Tuple of (alpha, support_vectors, support_vector_labels, b) trained parameters
    """
    n_samples, n_features = X.shape

    # Compute kernel matrix
    K = compute_kernel_matrix(X, kernel, gamma, degree, coef0)

    # Set up quadratic programming problem
    P = matrix(np.outer(y, y) * K)
    q = matrix(-np.ones(n_samples))
    G = matrix(np.vstack((-np.eye(n_samples), np.eye(n_samples))))
    h = matrix(np.hstack((np.zeros(n_samples), C * np.ones(n_samples))))
    A = matrix(y.astype(float), (1, n_samples))
    b = matrix(0.0)

    # Solve quadratic programming problem
    solvers.options['show_progress'] = False
    solution = solvers.qp(P, q, G, h, A, b)
    alpha = np.array(solution['x']).flatten()

    # Find support vectors
    support_vector_indices = alpha > 1e-5
    support_vectors = X[support_vector_indices]
    support_vector_labels = y[support_vector_indices]
    alpha = alpha[support_vector_indices]

    # Compute bias
    b = 0
    for i in range(len(alpha)):
        b += support_vector_labels[i]
        b -= np.sum(alpha * support_vector_labels * K[support_vector_indices][i, support_vector_indices])
    b /= len(alpha)

    return alpha, support_vectors, support_vector_labels, b

def predict_svm(
    X: np.ndarray,
    alpha: np.ndarray,
    support_vectors: np.ndarray,
    support_vector_labels: np.ndarray,
    b: float,
    kernel: str = 'linear',
    gamma: float = 'scale',
    degree: int = 3,
    coef0: float = 0.0
) -> np.ndarray:
    """
    Make predictions using the trained model.
    
    Args:
        X: Features to predict
        alpha: Lagrange multipliers
        support_vectors: Support vectors
        support_vector_labels: Labels of support vectors
        b: Bias term
        kernel: Type of kernel
        gamma: Kernel coefficient
        degree: Degree of polynomial kernel
        coef0: Independent term in kernel function
        
    Returns:
        Predicted classes (-1 or 1)
    """
    y_pred = np.zeros(len(X))
    for i in range(len(X)):
        s = 0
        for a, sv_y, sv in zip(alpha, support_vector_labels, support_vectors):
            s += a * sv_y * kernel_function(X[i], sv, kernel, gamma, degree, coef0)
        y_pred[i] = s + b
    return np.sign(y_pred)

## 6. Visualization Functions
def plot_decision_boundary(
    X: np.ndarray,
    y: np.ndarray,
    alpha: np.ndarray,
    support_vectors: np.ndarray,
    support_vector_labels: np.ndarray,
    b: float,
    kernel: str = 'linear',
    gamma: float = 'scale',
    degree: int = 3,
    coef0: float = 0.0,
    title: str = "Decision Boundary"
):
    """
    Plot the decision boundary and data points using seaborn and save to PNG file.
    
    Args:
        X: Features
        y: Target values
        alpha: Lagrange multipliers
        support_vectors: Support vectors
        support_vector_labels: Labels of support vectors
        b: Bias term
        kernel: Type of kernel
        gamma: Kernel coefficient
        degree: Degree of polynomial kernel
        coef0: Independent term in kernel function
        title: Plot title
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/support_vector_machines/svm', exist_ok=True)
    
    plt.figure(figsize=(10, 8))
    
    # Create mesh grid
    x_min, x_max = X[:, 0].min() - 1, X[:, 0].max() + 1
    y_min, y_max = X[:, 1].min() - 1, X[:, 1].max() + 1
    xx, yy = np.meshgrid(np.arange(x_min, x_max, 0.02),
                        np.arange(y_min, y_max, 0.02))
    
    # Predict for each point in mesh grid
    Z = predict_svm(np.c_[xx.ravel(), yy.ravel()],
                   alpha, support_vectors, support_vector_labels, b,
                   kernel, gamma, degree, coef0)
    Z = Z.reshape(xx.shape)
    
    # Plot decision boundary
    plt.contourf(xx, yy, Z, alpha=0.4)
    
    # Plot data points
    plt.scatter(X[:, 0], X[:, 1], c=y, alpha=0.8)
    
    # Plot support vectors
    plt.scatter(support_vectors[:, 0], support_vectors[:, 1],
               c=support_vector_labels, edgecolors='k', linewidths=2,
               s=100, label='Support Vectors')
    
    plt.title(title)
    plt.xlabel('Feature 1')
    plt.ylabel('Feature 2')
    plt.legend()
    
    # Save plot
    filename = f'algorithms/support_vector_machines/svm/svm-{title.lower().replace(" ", "_")}.png'
    plt.savefig(filename)
    plt.close()

def plot_confusion_matrix(y_true: np.ndarray, y_pred: np.ndarray, title: str = "Confusion Matrix"):
    """
    Plot confusion matrix using seaborn and save to PNG file.
    
    Args:
        y_true: True labels
        y_pred: Predicted labels
        title: Plot title
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/support_vector_machines/svm', exist_ok=True)
    
    # Compute confusion matrix
    cm = confusion_matrix(y_true, y_pred)
    
    # Create plot
    plt.figure(figsize=(8, 6))
    sns.heatmap(cm, annot=True, fmt='d', cmap='Blues')
    plt.title(title)
    plt.xlabel('Predicted')
    plt.ylabel('True')
    
    # Save plot
    filename = f'algorithms/support_vector_machines/svm/svm-{title.lower().replace(" ", "_")}.png'
    plt.savefig(filename)
    plt.close()

def plot_roc_curve(y_true: np.ndarray, y_pred_proba: np.ndarray, title: str = "ROC Curve"):
    """
    Plot ROC curve using seaborn and save to PNG file.
    
    Args:
        y_true: True labels
        y_pred_proba: Predicted probabilities
        title: Plot title
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/support_vector_machines/svm', exist_ok=True)
    
    # Compute ROC curve
    fpr, tpr, _ = roc_curve(y_true, y_pred_proba[:, 1])
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
    filename = f'algorithms/support_vector_machines/svm/svm-{title.lower().replace(" ", "_")}.png'
    plt.savefig(filename)
    plt.close()

## 7. Main Execution
# Generate and prepare data
X, y = generate_data()

# Split data
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.2, random_state=2220
)

# Scale features
scaler = StandardScaler()
X_train_scaled = scaler.fit_transform(X_train)
X_test_scaled = scaler.transform(X_test)

# Train custom SVM
alpha, support_vectors, support_vector_labels, b = fit_svm(
    X_train_scaled,
    y_train,
    kernel='rbf',
    C=1.0,
    gamma=0.1
)

# Train scikit-learn SVM
sklearn_svm = SVC(
    kernel='rbf',
    C=1.0,
    gamma=0.1,
    random_state=2220
)
sklearn_svm.fit(X_train_scaled, y_train)

# Make predictions
y_pred_custom = predict_svm(
    X_test_scaled,
    alpha,
    support_vectors,
    support_vector_labels,
    b,
    kernel='rbf',
    gamma=0.1
)
y_pred_sklearn = sklearn_svm.predict(X_test_scaled)

# Print results
print("\nCustom SVM Results:")
print(f"Accuracy: {accuracy_score(y_test, y_pred_custom):.4f}")
print("\nClassification Report:")
print(classification_report(y_test, y_pred_custom))

print("\nScikit-learn SVM Results:")
print(f"Accuracy: {accuracy_score(y_test, y_pred_sklearn):.4f}")
print("\nClassification Report:")
print(classification_report(y_test, y_pred_sklearn))

# Plot results
plot_decision_boundary(
    X_train_scaled,
    y_train,
    alpha,
    support_vectors,
    support_vector_labels,
    b,
    kernel='rbf',
    gamma=0.1,
    title="Custom SVM Decision Boundary"
)
plot_confusion_matrix(y_test, y_pred_custom, "Custom SVM Confusion Matrix")