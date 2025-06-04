#!/usr/bin/env python3
# -*- coding: utf-8 -*-

## Polynomial Regression Implementation
## This notebook demonstrates both custom and scikit-learn implementations of Polynomial Regression.

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
from sklearn.preprocessing import PolynomialFeatures
from sklearn.linear_model import LinearRegression
from sklearn.model_selection import train_test_split, cross_val_score
from sklearn.metrics import mean_squared_error, r2_score
from sklearn.preprocessing import StandardScaler
import matplotlib.pyplot as plt
import os

## 2. Set Random Seed
np.random.seed(2220)  # Set random seed for reproducibility

## 3. Data Generation Function
def generate_nonlinear_data():
    """
    Generate sample data with non-linear relationship.
    
    Returns:
        tuple: (X, y) features and target
    """
    X = 6 * np.random.rand(100, 1) - 3
    y = 0.5 * X**2 + X + 2 + np.random.randn(100, 1)
    return X, y

## 4. Polynomial Regression Implementation
def fit_polynomial_regression(X, y, degree=2, learning_rate=0.01, n_iterations=1000, regularization=0):
    """
    Train polynomial regression model using gradient descent.
    
    Hyperparameters:
    - degree (int): Degree of the polynomial. Higher degrees allow for more complex curves
                   but may lead to overfitting.
    - learning_rate (float): Step size for gradient descent. Controls how quickly the model
                            converges. Too large may cause divergence, too small may be slow.
    - n_iterations (int): Number of training iterations. More iterations may improve accuracy
                         but increase computation time.
    - regularization (float): L2 regularization parameter. Helps prevent overfitting by
                             penalizing large weights.
    
    Args:
        X (numpy.ndarray): Training features
        y (numpy.ndarray): Target values
        degree (int): Degree of the polynomial
        learning_rate (float): Step size for gradient descent
        n_iterations (int): Number of iterations for training
        regularization (float): L2 regularization parameter
        
    Returns:
        tuple: (weights, bias, cost_history) trained model parameters and training history
    """
    # Generate polynomial features
    poly = PolynomialFeatures(degree=degree)
    X_poly = poly.fit_transform(X)
    n_samples, n_features = X_poly.shape
    
    # Flatten y to 1D for broadcasting
    y = y.flatten()
    
    # Initialize parameters
    weights = np.zeros(n_features)
    bias = 0
    cost_history = []
    
    # Gradient descent
    for _ in range(n_iterations):
        # Forward pass
        y_predicted = np.dot(X_poly, weights) + bias
        
        # Compute gradients
        dw = (1/n_samples) * np.dot(X_poly.T, (y_predicted - y))
        db = (1/n_samples) * np.sum(y_predicted - y)
        
        # Add regularization
        dw += (regularization/n_samples) * weights
        
        # Update parameters
        weights -= learning_rate * dw
        bias -= learning_rate * db
        
        # Compute cost
        cost = (1/(2*n_samples)) * np.sum((y_predicted - y)**2)
        cost += (regularization/(2*n_samples)) * np.sum(weights**2)
        cost_history.append(cost)
    
    return weights, bias, cost_history, poly

def predict_polynomial(X, weights, bias, poly):
    """
    Make predictions using the trained model.
    
    Args:
        X (numpy.ndarray): Features to predict
        weights (numpy.ndarray): Trained weights
        bias (float): Trained bias
        poly (PolynomialFeatures): Fitted polynomial feature transformer
        
    Returns:
        numpy.ndarray: Predicted values
    """
    X_poly = poly.transform(X)
    return np.dot(X_poly, weights) + bias

## 5. Model Evaluation and Visualization Functions
def plot_results(X, y, y_pred, title):
    """
    Plot the regression curve and data points using seaborn and save to PNG file.
    
    Args:
        X (numpy.ndarray): Features
        y (numpy.ndarray): Actual values
        y_pred (numpy.ndarray): Predicted values
        title (str): Plot title
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/polynomial_regression/polynomial_regression', exist_ok=True)
    
    # Create DataFrame for seaborn
    df = pd.DataFrame({
        'X': X.flatten(),
        'Actual': y.flatten(),
        'Predicted': y_pred.flatten()
    })
    
    # Create plot
    plt.figure(figsize=(10, 6))
    sns.scatterplot(data=df, x='X', y='Actual', label='Data points')
    
    # Sort points for smooth curve
    sort_idx = np.argsort(X.flatten())
    sns.lineplot(x=X[sort_idx].flatten(), y=y_pred[sort_idx].flatten(), 
                color='red', label='Regression curve')
    
    plt.title(title)
    plt.legend()
    
    # Save plot
    filename = f'algorithms/polynomial_regression/polynomial_regression/polynomial_regression-{title.lower().replace(" ", "_")}.png'
    plt.savefig(filename)
    plt.close()

def plot_cost_history(cost_history, title):
    """
    Plot the cost history during training using seaborn and save to PNG file.
    
    Args:
        cost_history (list): List of cost values
        title (str): Plot title
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/polynomial_regression/polynomial_regression', exist_ok=True)
    
    plt.figure(figsize=(10, 6))
    sns.lineplot(data=pd.DataFrame({'Cost': cost_history}))
    plt.xlabel('Iteration')
    plt.ylabel('Cost')
    plt.title(title)
    
    # Save plot
    filename = f'algorithms/polynomial_regression/polynomial_regression/polynomial_regression-{title.lower().replace(" ", "_")}.png'
    plt.savefig(filename)
    plt.close()

def find_optimal_degree(X, y, max_degree=10):
    """
    Find the optimal polynomial degree using cross-validation.
    
    Args:
        X (numpy.ndarray): Features
        y (numpy.ndarray): Target values
        max_degree (int): Maximum degree to try
        
    Returns:
        int: Optimal degree
    """
    degrees = range(1, max_degree + 1)
    scores = []
    
    for degree in degrees:
        poly = PolynomialFeatures(degree=degree)
        X_poly = poly.fit_transform(X)
        model = LinearRegression()
        score = np.mean(cross_val_score(model, X_poly, y, cv=5, scoring='r2'))
        scores.append(score)
    
    optimal_degree = degrees[np.argmax(scores)]
    
    # Plot results
    plt.figure(figsize=(10, 6))
    sns.lineplot(x=degrees, y=scores, marker='o')
    plt.xlabel('Polynomial Degree')
    plt.ylabel('R2 Score')
    plt.title('Cross-validation Score vs. Polynomial Degree')
    
    # Save plot
    filename = 'algorithms/polynomial_regression/polynomial_regression/polynomial_regression-optimal_degree.png'
    plt.savefig(filename)
    plt.close()
    
    return optimal_degree

## 6. Main Execution
# Generate sample data
X, y = generate_nonlinear_data()

# Find optimal degree
optimal_degree = find_optimal_degree(X, y)
print(f"\nOptimal polynomial degree: {optimal_degree}")

# Split data
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.2, random_state=2220
)

# Scale features
scaler = StandardScaler()
X_train_scaled = scaler.fit_transform(X_train)
X_test_scaled = scaler.transform(X_test)

# Custom implementation
weights, bias, cost_history, poly = fit_polynomial_regression(
    X_train_scaled, y_train,
    degree=optimal_degree,
    learning_rate=0.01,
    n_iterations=1000,
    regularization=0.1
)
custom_predictions = predict_polynomial(X_test_scaled, weights, bias, poly)

# Scikit-learn implementation
poly = PolynomialFeatures(degree=optimal_degree)
X_train_poly = poly.fit_transform(X_train_scaled)
X_test_poly = poly.transform(X_test_scaled)

sklearn_model = LinearRegression()
sklearn_model.fit(X_train_poly, y_train)
sklearn_predictions = sklearn_model.predict(X_test_poly)

# Evaluate models
print("\nCustom Implementation Results:")
print(f"MSE: {mean_squared_error(y_test, custom_predictions):.4f}")
print(f"R2 Score: {r2_score(y_test, custom_predictions):.4f}")

print("\nScikit-learn Implementation Results:")
print(f"MSE: {mean_squared_error(y_test, sklearn_predictions):.4f}")
print(f"R2 Score: {r2_score(y_test, sklearn_predictions):.4f}")

# Plot results
plot_results(X_test, y_test, custom_predictions, "Custom Implementation")
plot_results(X_test, y_test, sklearn_predictions, "Scikit-learn Implementation")
plot_cost_history(cost_history, "Cost History During Training")