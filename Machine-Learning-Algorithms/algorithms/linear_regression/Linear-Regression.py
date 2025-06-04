#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
# Linear Regression Implementation

This notebook demonstrates the implementation and usage of Linear Regression for regression tasks.
We'll use both scikit-learn's implementation and a custom implementation to understand the algorithm better.

## Table of Contents
1. Import Required Libraries
2. Data Generation and Preprocessing
3. Custom Linear Regression Implementation
4. Model Training and Evaluation
5. Visualization
"""

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from sklearn.linear_model import LinearRegression as SklearnLinearRegression
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_squared_error, r2_score
from sklearn.preprocessing import StandardScaler
import os

# Set random seed for reproducibility
np.random.seed(2220)

# Create output directory for visuals
output_dir = 'algorithms/linear_regression'
os.makedirs(output_dir, exist_ok=True)

## 2. Data Generation and Preprocessing
def load_sample_data(n_samples=100, noise_level=1.0):
    """
    Generate sample data for demonstration.
    
    Parameters:
    -----------
    n_samples : int, default=100
        Number of samples to generate
    noise_level : float, default=1.0
        Level of noise to add to the data
        
    Returns:
    --------
    X : numpy.ndarray
        Generated features
    y : numpy.ndarray
        Generated target values
    """
    X = 2 * np.random.rand(n_samples, 1)
    y = 4 + 3 * X + noise_level * np.random.randn(n_samples, 1)
    return X, y

## 3. Custom Linear Regression Implementation
def custom_linear_regression_fit(X, y, learning_rate=0.01, n_iterations=1000):
    """
    Train a linear regression model using gradient descent.
    
    Hyperparameters:
    ---------------
    learning_rate : float, default=0.01
        Step size for gradient descent. Controls how quickly the model learns.
        Too large values may cause overshooting, too small values may lead to slow convergence.
    n_iterations : int, default=1000
        Number of training iterations. More iterations can lead to better
        convergence but may cause overfitting if too high.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Training features
    y : numpy.ndarray
        Target values
        
    Returns:
    --------
    tuple
        (weights, bias, cost_history) trained parameters and training history
    """
    n_samples, n_features = X.shape
    
    # Flatten y to ensure 1D
    y = y.flatten()
    # Initialize parameters
    weights = np.zeros(n_features)
    bias = 0
    cost_history = []
    
    # Gradient descent
    for _ in range(n_iterations):
        # Forward pass
        y_predicted = np.dot(X, weights) + bias
        
        # Compute gradients
        dw = (1/n_samples) * np.dot(X.T, (y_predicted - y))
        db = (1/n_samples) * np.sum(y_predicted - y)
        
        # Update parameters
        weights -= learning_rate * dw
        bias -= learning_rate * db
        
        # Compute cost
        cost = (1/(2*n_samples)) * np.sum((y_predicted - y)**2)
        cost_history.append(cost)
    
    return weights, bias, cost_history

def custom_linear_regression_predict(X, weights, bias):
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
        
    Returns:
    --------
    numpy.ndarray
        Predicted values
    """
    return np.dot(X, weights) + bias

## 4. Model Training and Evaluation
# Generate and prepare data
X, y = load_sample_data()
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.2, random_state=2220
)

# Scale features
scaler = StandardScaler()
X_train_scaled = scaler.fit_transform(X_train)
X_test_scaled = scaler.transform(X_test)

# Train custom model
weights, bias, cost_history = custom_linear_regression_fit(
    X_train_scaled, y_train,
    learning_rate=0.01,  # Small learning rate for stable convergence
    n_iterations=1000    # Sufficient iterations for convergence
)
custom_predictions = custom_linear_regression_predict(X_test_scaled, weights, bias)

# Train scikit-learn model
sklearn_model = SklearnLinearRegression()
sklearn_model.fit(X_train_scaled, y_train)
sklearn_predictions = sklearn_model.predict(X_test_scaled)

## 5. Model Evaluation
# Print evaluation metrics
print("\nCustom Implementation Results:")
print(f"MSE: {mean_squared_error(y_test, custom_predictions):.4f}")
print(f"R2 Score: {r2_score(y_test, custom_predictions):.4f}")

print("\nScikit-learn Implementation Results:")
print(f"MSE: {mean_squared_error(y_test, sklearn_predictions):.4f}")
print(f"R2 Score: {r2_score(y_test, sklearn_predictions):.4f}")

## 6. Visualization
def plot_regression_results(X, y, y_pred, title):
    """
    Plot the regression line and data points using seaborn and save to PNG file.
    
    Parameters:
    -----------
    X : numpy.ndarray
        Features
    y : numpy.ndarray
        Actual values
    y_pred : numpy.ndarray
        Predicted values
    title : str
        Plot title
    """
    # Create a DataFrame for seaborn
    df = pd.DataFrame({
        'X': X.flatten(),
        'Actual': y.flatten(),
        'Predicted': y_pred.flatten()
    })
    
    # Create the plot
    plt.figure(figsize=(10, 6))
    sns.scatterplot(data=df, x='X', y='Actual', label='Data points')
    sns.lineplot(data=df, x='X', y='Predicted', color='red', label='Regression line')
    plt.title(title)
    plt.xlabel('Feature')
    plt.ylabel('Target')
    
    # Save plot
    filename = f'{output_dir}/linear_regression-{title.lower().replace(" ", "_")}.png'
    plt.savefig(filename)
    plt.close()

def plot_cost_history(cost_history, title):
    """
    Plot the cost history during training using seaborn and save to PNG file.
    
    Parameters:
    -----------
    cost_history : list
        List of cost values
    title : str
        Plot title
    """
    plt.figure(figsize=(10, 6))
    sns.lineplot(data=pd.DataFrame({'Cost': cost_history}))
    plt.title(title)
    plt.xlabel('Iteration')
    plt.ylabel('Cost')
    
    # Save plot
    filename = f'{output_dir}/linear_regression-{title.lower().replace(" ", "_")}.png'
    plt.savefig(filename)
    plt.close()

# Create visualizations
plot_regression_results(X_test, y_test, custom_predictions, "Custom Implementation")
plot_regression_results(X_test, y_test, sklearn_predictions, "Scikit-learn Implementation")
plot_cost_history(cost_history, "Cost History During Training") 