## Gradient Descent Neural Network Implementation
# This notebook demonstrates a simple neural network trained with gradient descent for classification.

## 1. Import Required Libraries
import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score, classification_report, confusion_matrix
import seaborn as sns
import matplotlib.pyplot as plt
import os
import tensorflow as tf
from tensorflow import keras
from tensorflow.keras import layers

# Set random seed for reproducibility
np.random.seed(2220)

# Create output directory for visuals
output_dir = 'algorithms/classification/gradient_descent'
os.makedirs(output_dir, exist_ok=True)

## 2. Helper Functions for Gradient Descent Network

def sigmoid(x):
    """
    Sigmoid activation function.
    """
    return 1 / (1 + np.exp(-np.clip(x, -500, 500)))

def sigmoid_derivative(x):
    """
    Derivative of sigmoid function.
    """
    return x * (1 - x)

def initialize_weights(n_features, n_classes, hidden_layer_sizes, random_state=None):
    """
    Initialize network weights and biases.
    """
    if random_state is not None:
        np.random.seed(random_state)
    weights = []
    biases = []
    # Input layer to first hidden layer
    weights.append(np.random.randn(n_features, hidden_layer_sizes[0]) * 0.01)
    biases.append(np.zeros(hidden_layer_sizes[0]))
    # Hidden layers
    for i in range(len(hidden_layer_sizes) - 1):
        weights.append(np.random.randn(hidden_layer_sizes[i], hidden_layer_sizes[i + 1]) * 0.01)
        biases.append(np.zeros(hidden_layer_sizes[i + 1]))
    # Last hidden layer to output layer
    weights.append(np.random.randn(hidden_layer_sizes[-1], n_classes) * 0.01)
    biases.append(np.zeros(n_classes))
    return weights, biases

def forward_pass(X, weights, biases, hidden_layer_sizes):
    """
    Perform forward pass through the network.
    """
    activations = [X]
    z_values = []
    # Hidden layers
    for i in range(len(hidden_layer_sizes)):
        z = np.dot(activations[-1], weights[i]) + biases[i]
        z_values.append(z)
        activations.append(sigmoid(z))
    # Output layer
    z = np.dot(activations[-1], weights[-1]) + biases[-1]
    z_values.append(z)
    activations.append(sigmoid(z))
    return activations, z_values

def backward_pass(activations, z_values, y_onehot, weights, hidden_layer_sizes):
    """
    Perform backward pass to compute gradients.
    """
    m = y_onehot.shape[0]
    deltas = []
    # Output layer delta
    delta = activations[-1] - y_onehot
    deltas.append(delta)
    # Hidden layers delta
    for i in range(len(hidden_layer_sizes) - 1, -1, -1):
        delta = np.dot(deltas[0], weights[i + 1].T) * sigmoid_derivative(activations[i + 1])
        deltas.insert(0, delta)
    # Compute gradients
    weight_gradients = []
    bias_gradients = []
    for i in range(len(weights)):
        weight_gradients.append(np.dot(activations[i].T, deltas[i]) / m)
        bias_gradients.append(np.mean(deltas[i], axis=0))
    return weight_gradients, bias_gradients

def update_weights(weights, biases, weight_gradients, bias_gradients, velocity_w, velocity_b, learning_rate, momentum):
    """
    Update weights using gradient descent with momentum.
    """
    for i in range(len(weights)):
        velocity_w[i] = momentum * velocity_w[i] - learning_rate * weight_gradients[i]
        velocity_b[i] = momentum * velocity_b[i] - learning_rate * bias_gradients[i]
        weights[i] += velocity_w[i]
        biases[i] += velocity_b[i]
    return weights, biases, velocity_w, velocity_b

def fit_gradient_descent(X, y, hidden_layer_sizes=(100,), learning_rate=0.01, max_iter=1000, batch_size=32, momentum=0.9, random_state=None):
    """
    Fit neural network using gradient descent.
    """
    n_classes = len(np.unique(y))
    weights, biases = initialize_weights(X.shape[1], n_classes, hidden_layer_sizes, random_state)
    velocity_w = [np.zeros_like(w) for w in weights]
    velocity_b = [np.zeros_like(b) for b in biases]
    # Convert labels to one-hot encoding
    y_onehot_full = np.zeros((len(y), n_classes))
    y_onehot_full[np.arange(len(y)), y] = 1
    prev_error = float('inf')
    for i in range(max_iter):
        indices = np.random.permutation(len(X))
        X_shuffled = X[indices]
        y_shuffled = y_onehot_full[indices]
        for j in range(0, len(X), batch_size):
            X_batch = X_shuffled[j:j + batch_size]
            y_batch = y_shuffled[j:j + batch_size]
            activations, z_values = forward_pass(X_batch, weights, biases, hidden_layer_sizes)
            weight_gradients, bias_gradients = backward_pass(activations, z_values, y_batch, weights, hidden_layer_sizes)
            weights, biases, velocity_w, velocity_b = update_weights(weights, biases, weight_gradients, bias_gradients, velocity_w, velocity_b, learning_rate, momentum)
        if i % 100 == 0:
            y_pred = predict_gradient_descent(X, weights, biases, hidden_layer_sizes)
            error = np.mean(y_pred != y)
            if abs(prev_error - error) < 1e-6:
                break
            prev_error = error
    return weights, biases, i + 1

def predict_proba_gradient_descent(X, weights, biases, hidden_layer_sizes):
    """
    Predict class probabilities using the trained network.
    """
    activations, _ = forward_pass(X, weights, biases, hidden_layer_sizes)
    return activations[-1]

def predict_gradient_descent(X, weights, biases, hidden_layer_sizes):
    """
    Predict class labels using the trained network.
    """
    proba = predict_proba_gradient_descent(X, weights, biases, hidden_layer_sizes)
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
    plt.savefig(f'{output_dir}/gradient_descent-confusion-matrix.png')
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

# Hyperparameters
hidden_layer_sizes = (16,)
learning_rate = 0.05
max_iter = 500
batch_size = 32
momentum = 0.9

# Build Keras model
model = keras.Sequential()
model.add(layers.Input(shape=(n_features,)))
model.add(layers.Dense(hidden_layer_sizes[0], activation='sigmoid'))
model.add(layers.Dense(1, activation='sigmoid'))

optimizer = keras.optimizers.SGD(learning_rate=learning_rate, momentum=momentum)
model.compile(optimizer=optimizer, loss='binary_crossentropy', metrics=['accuracy'])

# Train model
history = model.fit(
    X_train, y_train,
    epochs=max_iter,
    batch_size=batch_size,
    verbose=0
)

# Predict and evaluate
proba = model.predict(X_test)
y_pred = (proba > 0.5).astype(int).flatten()
accuracy = accuracy_score(y_test, y_pred)
print(f"Accuracy: {accuracy:.4f}")
print("\nClassification Report:")
print(classification_report(y_test, y_pred))

# Plot confusion matrix
plot_confusion_matrix(y_test, y_pred, title="Keras Gradient Descent Confusion Matrix") 