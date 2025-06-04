# %% [markdown]
# # Radial Basis Function (RBF) Network Implementation
# 
# This notebook implements a Radial Basis Function (RBF) Network for classification tasks.
# RBF networks are a type of neural network that uses radial basis functions as activation functions.
# They are particularly useful for function approximation and classification problems.

# %% [markdown]
# ## Import Required Libraries

# %%
import numpy as np
from sklearn.cluster import KMeans
import seaborn as sns
import pandas as pd
from sklearn.datasets import make_classification
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score, confusion_matrix, classification_report
import matplotlib.pyplot as plt

# Set random seed for reproducibility
np.random.seed(2220)

# %% [markdown]
# ## Helper Functions

# %%
def rbf(X, centers, gamma):
    """
    Calculate the RBF activation for input data.
    
    Parameters:
    -----------
    X : array-like, shape (n_samples, n_features)
        Input data
    centers : array-like, shape (n_centers, n_features)
        RBF centers
    gamma : float
        Width parameter of the RBF function (controls the spread of the radial basis function)
    
    Returns:
    --------
    array-like, shape (n_samples, n_centers)
        RBF activations
    """
    distances = np.zeros((X.shape[0], centers.shape[0]))
    for i in range(centers.shape[0]):
        distances[:, i] = np.sum((X - centers[i])**2, axis=1)
    return np.exp(-gamma * distances)

def initialize_centers(X, n_centers, random_state=None):
    """
    Initialize RBF centers using K-means clustering.
    
    Parameters:
    -----------
    X : array-like, shape (n_samples, n_features)
        Input data
    n_centers : int
        Number of RBF centers to use
    random_state : int, optional
        Random seed for reproducibility
    
    Returns:
    --------
    array-like, shape (n_centers, n_features)
        Initialized RBF centers
    """
    kmeans = KMeans(n_clusters=n_centers, random_state=random_state)
    kmeans.fit(X)
    return kmeans.cluster_centers_

def initialize_weights(n_centers, n_classes, random_state=None):
    """
    Initialize weights and biases for the output layer.
    
    Parameters:
    -----------
    n_centers : int
        Number of RBF centers
    n_classes : int
        Number of output classes
    random_state : int, optional
        Random seed for reproducibility
    
    Returns:
    --------
    tuple
        (weights, biases) where weights shape is (n_centers, n_classes)
        and biases shape is (n_classes,)
    """
    if random_state is not None:
        np.random.seed(random_state)
    weights = np.random.randn(n_centers, n_classes) * 0.01
    biases = np.zeros(n_classes)
    return weights, biases

def forward_pass(X, centers, weights, biases, gamma):
    """
    Perform forward pass through the RBF network.
    
    Parameters:
    -----------
    X : array-like, shape (n_samples, n_features)
        Input data
    centers : array-like, shape (n_centers, n_features)
        RBF centers
    weights : array-like, shape (n_centers, n_classes)
        Output layer weights
    biases : array-like, shape (n_classes,)
        Output layer biases
    gamma : float
        Width parameter of the RBF function
    
    Returns:
    --------
    array-like, shape (n_samples, n_classes)
        Network output probabilities
    """
    rbf_activations = rbf(X, centers, gamma)
    output = np.dot(rbf_activations, weights) + biases
    exp_output = np.exp(output - np.max(output, axis=1, keepdims=True))
    return exp_output / np.sum(exp_output, axis=1, keepdims=True)

# %% [markdown]
# ## Main RBF Network Functions

# %%
def fit_rbf_network(X, y, n_centers=10, gamma=1.0, learning_rate=0.01, max_iter=1000, random_state=None):
    """
    Train the RBF network.
    
    Parameters:
    -----------
    X : array-like, shape (n_samples, n_features)
        Training data
    y : array-like, shape (n_samples,)
        Target labels
    n_centers : int, default=10
        Number of RBF centers to use
    gamma : float, default=1.0
        Width parameter of the RBF function
    learning_rate : float, default=0.01
        Learning rate for gradient descent
    max_iter : int, default=1000
        Maximum number of training iterations
    random_state : int, optional
        Random seed for reproducibility
    
    Returns:
    --------
    tuple
        (centers, weights, biases, n_iter) where:
        - centers: RBF centers
        - weights: Output layer weights
        - biases: Output layer biases
        - n_iter: Number of iterations performed
    """
    n_classes = len(np.unique(y))
    y_onehot = np.zeros((len(y), n_classes))
    y_onehot[np.arange(len(y)), y] = 1
    
    centers = initialize_centers(X, n_centers, random_state)
    weights, biases = initialize_weights(n_centers, n_classes, random_state)
    
    n_iter = 0
    for i in range(max_iter):
        predictions = forward_pass(X, centers, weights, biases, gamma)
        error = predictions - y_onehot
        rbf_activations = rbf(X, centers, gamma)
        
        weights -= learning_rate * np.dot(rbf_activations.T, error)
        biases -= learning_rate * np.mean(error, axis=0)
        
        n_iter += 1
        if i % 100 == 0:
            if np.mean(np.argmax(predictions, axis=1) == y) > 0.99:
                break
    
    return centers, weights, biases, n_iter

def predict_proba_rbf_network(X, centers, weights, biases, gamma):
    """
    Predict class probabilities.
    
    Parameters:
    -----------
    X : array-like, shape (n_samples, n_features)
        Input data
    centers : array-like, shape (n_centers, n_features)
        RBF centers
    weights : array-like, shape (n_centers, n_classes)
        Output layer weights
    biases : array-like, shape (n_classes,)
        Output layer biases
    gamma : float
        Width parameter of the RBF function
    
    Returns:
    --------
    array-like, shape (n_samples, n_classes)
        Predicted class probabilities
    """
    return forward_pass(X, centers, weights, biases, gamma)

def predict_rbf_network(X, centers, weights, biases, gamma):
    """
    Predict class labels.
    
    Parameters:
    -----------
    X : array-like, shape (n_samples, n_features)
        Input data
    centers : array-like, shape (n_centers, n_features)
        RBF centers
    weights : array-like, shape (n_centers, n_classes)
        Output layer weights
    biases : array-like, shape (n_classes,)
        Output layer biases
    gamma : float
        Width parameter of the RBF function
    
    Returns:
    --------
    array-like, shape (n_samples,)
        Predicted class labels
    """
    return np.argmax(predict_proba_rbf_network(X, centers, weights, biases, gamma), axis=1)

# %% [markdown]
# ## Generate and Prepare Data

# %%
# Generate synthetic data
X, y = make_classification(
    n_samples=1000,
    n_features=2,
    n_classes=2,  # Changed from 3 to 2 classes
    n_informative=2,
    n_redundant=0,
    n_clusters_per_class=1,  # Added this parameter
    random_state=2220
)

# Split the data
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.2, random_state=2220
)

# %% [markdown]
# ## Train and Evaluate the Model

# %%
# Train the RBF network
centers, weights, biases, n_iter = fit_rbf_network(
    X_train, y_train,
    n_centers=10,  # Number of RBF centers
    gamma=1.0,     # Width parameter of the RBF function
    learning_rate=0.01,  # Learning rate for gradient descent
    max_iter=1000,  # Maximum number of training iterations
    random_state=2220
)

# Make predictions
y_pred = predict_rbf_network(X_test, centers, weights, biases, gamma=1.0)

# Calculate accuracy
accuracy = accuracy_score(y_test, y_pred)
print(f"Accuracy: {accuracy:.4f}")

# Generate classification report
print("\nClassification Report:")
print(classification_report(y_test, y_pred))

# %% [markdown]
# ## Visualize Results

# %%
# Create a mesh grid for decision boundary
x_min, x_max = X[:, 0].min() - 1, X[:, 0].max() + 1
y_min, y_max = X[:, 1].min() - 1, X[:, 1].max() + 1
xx, yy = np.meshgrid(np.arange(x_min, x_max, 0.1),
                     np.arange(y_min, y_max, 0.1))

# Predict for mesh grid points
Z = predict_rbf_network(np.c_[xx.ravel(), yy.ravel()], centers, weights, biases, gamma=1.0)
Z = Z.reshape(xx.shape)

# Plot decision boundary and data points
plt.figure(figsize=(10, 8))
sns.set_style("whitegrid")
plt.contourf(xx, yy, Z, alpha=0.4)
sns.scatterplot(x=X[:, 0], y=X[:, 1], hue=y, palette='deep', alpha=0.8)
plt.title('RBF Network Decision Boundary')
plt.xlabel('Feature 1')
plt.ylabel('Feature 2')
plt.savefig('algorithms/classification/rbf_decision_boundary.png', dpi=300, bbox_inches='tight')

# Plot confusion matrix
plt.figure(figsize=(8, 6))
cm = confusion_matrix(y_test, y_pred)
sns.heatmap(cm, annot=True, fmt='d', cmap='Blues')
plt.title('Confusion Matrix')
plt.xlabel('Predicted')
plt.ylabel('True')
plt.savefig('algorithms/classification/rbf_confusion_matrix.png', dpi=300, bbox_inches='tight')