## AdaBoost Implementation
# This notebook demonstrates both custom and scikit-learn implementations of AdaBoost.

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import os
from sklearn.ensemble import AdaBoostClassifier
from sklearn.tree import DecisionTreeClassifier
from sklearn.model_selection import train_test_split, cross_val_score
from sklearn.metrics import (
    accuracy_score, confusion_matrix, classification_report,
    roc_curve, auc
)
from sklearn.preprocessing import StandardScaler

# Set random seed for reproducibility
np.random.seed(2220)

# Create output directory for visuals
output_dir = 'algorithms/adaboost'
os.makedirs(output_dir, exist_ok=True)

## 2. Helper Functions for AdaBoost Implementation

def build_weak_learner(max_depth=1, random_state=None):
    """
    Create a new weak learner (decision stump).
    
    Args:
        max_depth (int): Maximum depth of the decision tree
        random_state (int): Random seed for reproducibility
        
    Returns:
        DecisionTreeClassifier: A decision tree with specified max_depth
    """
    return DecisionTreeClassifier(
        max_depth=max_depth,
        random_state=random_state
    )

def fit_adaboost(X, y, n_estimators=50, learning_rate=1.0, max_depth=1, random_state=None):
    """
    Train an AdaBoost model.
    
    Args:
        X (numpy.ndarray): Training features
        y (numpy.ndarray): Target values
        n_estimators (int): Number of weak learners
        learning_rate (float): Learning rate shrinks the contribution of each classifier
        max_depth (int): Maximum depth of the decision trees
        random_state (int): Random seed for reproducibility
        
    Returns:
        tuple: (estimators, estimator_weights, estimator_errors, sample_weights)
    """
    n_samples = X.shape[0]
    
    # Initialize sample weights
    sample_weights = np.ones(n_samples) / n_samples
    estimators = []
    estimator_weights = []
    estimator_errors = []
    
    # Build weak learners
    for _ in range(n_estimators):
        # Train weak learner
        estimator = build_weak_learner(max_depth, random_state)
        estimator.fit(X, y, sample_weight=sample_weights)
        
        # Make predictions
        y_pred = estimator.predict(X)
        
        # Calculate weighted error
        incorrect = y_pred != y
        error = np.sum(sample_weights * incorrect) / np.sum(sample_weights)
        
        # Skip if perfect fit
        if error == 0:
            estimators.append(estimator)
            estimator_weights.append(1.0)
            estimator_errors.append(0.0)
            break
        
        # Calculate estimator weight
        estimator_weight = learning_rate * 0.5 * np.log((1 - error) / error)
        
        # Update sample weights
        sample_weights *= np.exp(estimator_weight * incorrect)
        sample_weights /= np.sum(sample_weights)
        
        # Store results
        estimators.append(estimator)
        estimator_weights.append(estimator_weight)
        estimator_errors.append(error)
    
    return estimators, estimator_weights, estimator_errors, sample_weights

def predict_proba_adaboost(X, estimators, estimator_weights):
    """
    Predict class probabilities using trained AdaBoost model.
    
    Args:
        X (numpy.ndarray): Features to predict
        estimators (list): List of trained estimators
        estimator_weights (list): List of estimator weights
        
    Returns:
        numpy.ndarray: Class probabilities
    """
    # Get predictions from all estimators
    predictions = np.array([estimator.predict(X) for estimator in estimators])
    
    # Calculate weighted sum
    weighted_sum = np.sum(
        predictions * np.array(estimator_weights)[:, np.newaxis],
        axis=0
    )
    
    # Convert to probabilities
    proba = 1 / (1 + np.exp(-2 * weighted_sum))
    return np.column_stack((1 - proba, proba))

def predict_adaboost(X, estimators, estimator_weights):
    """
    Make predictions using the trained AdaBoost model.
    
    Args:
        X (numpy.ndarray): Features to predict
        estimators (list): List of trained estimators
        estimator_weights (list): List of estimator weights
        
    Returns:
        numpy.ndarray: Predicted classes
    """
    proba = predict_proba_adaboost(X, estimators, estimator_weights)
    return np.argmax(proba, axis=1)

## 3. Data Generation and Visualization Functions

def generate_data(n_samples=1000, n_features=10):
    """
    Generate sample data for classification.
    
    Args:
        n_samples (int): Number of samples to generate
        n_features (int): Number of features to generate
        
    Returns:
        tuple: (X, y) features and target
    """
    # Generate features
    X = np.random.randn(n_samples, n_features)
    
    # Generate target with non-linear relationship
    y = np.zeros(n_samples)
    y[X[:, 0] + X[:, 1]**2 + np.sin(X[:, 2]) > 0] = 1
    
    # Add some noise
    y += np.random.normal(0, 0.1, n_samples)
    y = (y > 0.5).astype(int)
    
    return X, y

def plot_sample_weights(weights, title):
    """
    Plot sample weights distribution using seaborn.
    
    Args:
        weights (numpy.ndarray): Sample weights
        title (str): Plot title
    """
    plt.figure(figsize=(10, 6))
    sns.histplot(weights, bins=50)
    plt.xlabel('Sample Weight')
    plt.ylabel('Frequency')
    plt.title(title)
    plt.savefig(f'{output_dir}/adaboost-sample-weights.png')
    plt.close()

def plot_estimator_weights(weights, errors, title):
    """
    Plot estimator weights and errors using seaborn.
    
    Args:
        weights (list): Estimator weights
        errors (list): Estimator errors
        title (str): Plot title
    """
    plt.figure(figsize=(12, 6))
    
    plt.subplot(1, 2, 1)
    sns.lineplot(x=range(len(weights)), y=weights, label='Weight')
    plt.xlabel('Estimator')
    plt.ylabel('Weight')
    plt.title('Estimator Weights')
    
    plt.subplot(1, 2, 2)
    sns.lineplot(x=range(len(errors)), y=errors, label='Error')
    plt.xlabel('Estimator')
    plt.ylabel('Error')
    plt.title('Estimator Errors')
    
    plt.suptitle(title)
    plt.tight_layout()
    plt.savefig(f'{output_dir}/adaboost-estimator-weights.png')
    plt.close()

def plot_confusion_matrix(y_true, y_pred, title):
    """
    Plot confusion matrix using seaborn.
    
    Args:
        y_true (numpy.ndarray): True labels
        y_pred (numpy.ndarray): Predicted labels
        title (str): Plot title
    """
    plt.figure(figsize=(8, 6))
    cm = confusion_matrix(y_true, y_pred)
    sns.heatmap(cm, annot=True, fmt='d', cmap='Blues')
    plt.xlabel('Predicted')
    plt.ylabel('True')
    plt.title(title)
    plt.savefig(f'{output_dir}/adaboost-confusion-matrix.png')
    plt.close()

def plot_roc_curve(y_true, y_pred_proba, title):
    """
    Plot ROC curve using seaborn.
    
    Args:
        y_true (numpy.ndarray): True labels
        y_pred_proba (numpy.ndarray): Predicted probabilities
        title (str): Plot title
    """
    plt.figure(figsize=(8, 6))
    fpr, tpr, _ = roc_curve(y_true, y_pred_proba[:, 1])
    roc_auc = auc(fpr, tpr)
    
    plt.plot(fpr, tpr, color='darkorange', lw=2,
             label=f'ROC curve (AUC = {roc_auc:.2f})')
    plt.plot([0, 1], [0, 1], color='navy', lw=2, linestyle='--')
    plt.xlim([0.0, 1.0])
    plt.ylim([0.0, 1.05])
    plt.xlabel('False Positive Rate')
    plt.ylabel('True Positive Rate')
    plt.title(title)
    plt.legend(loc="lower right")
    plt.savefig(f'{output_dir}/adaboost-roc-curve.png')
    plt.close()

## 4. Model Training and Evaluation

# Run code sequentially
# Generate and prepare data
X, y = generate_data(n_samples=1000, n_features=10)
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Scale features
scaler = StandardScaler()
X_train_scaled = scaler.fit_transform(X_train)
X_test_scaled = scaler.transform(X_test)

# Train scikit-learn AdaBoost
print("\nTraining scikit-learn AdaBoost...")
clf = AdaBoostClassifier(
    estimator=DecisionTreeClassifier(max_depth=1),
    n_estimators=50,
    learning_rate=1.0,
    random_state=42
)
clf.fit(X_train_scaled, y_train)

# Make predictions
y_pred = clf.predict(X_test_scaled)
y_pred_proba = clf.predict_proba(X_test_scaled)

# Print results
print(f"AdaBoost Accuracy: {accuracy_score(y_test, y_pred):.3f}")
print("\nClassification Report:")
print(classification_report(y_test, y_pred))

# Plot results
plot_confusion_matrix(y_test, y_pred, "Confusion Matrix")
plot_roc_curve(y_test, y_pred_proba, "ROC Curve")