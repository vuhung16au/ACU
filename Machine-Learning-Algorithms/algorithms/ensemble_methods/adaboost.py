## AdaBoost Implementation
# This notebook demonstrates the AdaBoost (Adaptive Boosting) algorithm for both
# classification and regression tasks. AdaBoost combines multiple weak learners
# into a strong learner by iteratively focusing on misclassified samples.

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from typing import List, Union, Optional, Tuple
from sklearn.base import BaseEstimator, ClassifierMixin, RegressorMixin, clone
from sklearn.tree import DecisionTreeClassifier, DecisionTreeRegressor
from sklearn.metrics import (
    accuracy_score, confusion_matrix, classification_report,
    roc_curve, auc, mean_squared_error, r2_score
)
from sklearn.datasets import make_classification, make_regression
from sklearn.model_selection import train_test_split
import os

# Set random seed for reproducibility
np.random.seed(2220)

# Create output directory for visuals
output_dir = 'algorithms/ensemble_methods'
os.makedirs(output_dir, exist_ok=True)

## 2. AdaBoost Implementation
def create_adaboost(
    base_estimator=None,
    n_estimators=50,
    learning_rate=1.0
):
    """
    Create an AdaBoost model.
    
    Hyperparameters:
    - base_estimator: Base model to use. If None, a decision stump (tree with
      max_depth=1) is used. Should implement fit and predict methods.
    - n_estimators (int): Maximum number of estimators at which boosting is
      terminated. More estimators generally lead to better performance but
      increase computation time and risk of overfitting.
    - learning_rate (float): Learning rate shrinks the contribution of each
      classifier. Lower values require more estimators but can help prevent
      overfitting.
    
    Args:
        base_estimator: Base model to use
        n_estimators (int): Maximum number of estimators
        learning_rate (float): Learning rate
        
    Returns:
        dict: AdaBoost model with fit and predict methods
    """
    # Initialize model
    model = {
        'base_estimator': base_estimator,
        'n_estimators': n_estimators,
        'learning_rate': learning_rate,
        'estimators': [],
        'estimator_weights': [],
        'estimator_errors': [],
        'is_classification': None
    }
    
    def _get_base_estimator():
        """Get base estimator."""
        if model['base_estimator'] is None:
            if model['is_classification']:
                return DecisionTreeClassifier(max_depth=1, random_state=2220)
            else:
                return DecisionTreeRegressor(max_depth=1, random_state=2220)
        return clone(model['base_estimator'])
    
    def fit(X, y):
        """Train the AdaBoost model."""
        # Determine if classification or regression
        model['is_classification'] = isinstance(model['base_estimator'], ClassifierMixin)
        
        # Initialize sample weights
        n_samples = X.shape[0]
        sample_weights = np.ones(n_samples) / n_samples
        
        # Train estimators
        for i in range(model['n_estimators']):
            # Train base estimator
            estimator = _get_base_estimator()
            estimator.fit(X, y, sample_weight=sample_weights)
            
            # Make predictions
            if model['is_classification']:
                y_pred = estimator.predict(X)
                incorrect = y_pred != y
                # Calculate error and estimator weight (classification)
                error = np.sum(sample_weights * incorrect) / np.sum(sample_weights)
                estimator_weight = model['learning_rate'] * 0.5 * np.log((1 - error) / max(error, 1e-10))
                # Update sample weights
                sample_weights *= np.exp(estimator_weight * incorrect)
                sample_weights /= np.sum(sample_weights)
            else:
                y_pred = estimator.predict(X)
                # AdaBoost.R2 loss: normalized absolute error
                abs_err = np.abs(y_pred - y)
                max_err = abs_err.max()
                if max_err == 0:
                    # Perfect fit
                    error = 0.0
                    estimator_weight = 1.0
                    model['estimators'].append(estimator)
                    model['estimator_weights'].append(estimator_weight)
                    model['estimator_errors'].append(error)
                    print(f"Perfect fit achieved at iteration {i + 1}")
                    break
                loss = abs_err / max_err
                error = np.sum(sample_weights * loss)
                # Avoid error >= 0.5 (skip this estimator)
                error = min(error, 0.999)
                beta = error / (1 - error)
                estimator_weight = model['learning_rate'] * np.log(1.0 / (beta + 1e-10))
                # Update sample weights
                sample_weights *= np.power(beta, (1 - loss))
                sample_weights /= np.sum(sample_weights)
            
            # Store estimator and weight
            model['estimators'].append(estimator)
            model['estimator_weights'].append(estimator_weight)
            model['estimator_errors'].append(error)
            
            # Print progress
            if (i + 1) % 10 == 0:
                print(f"Iteration {i + 1}, Error: {error:.4f}, Weight: {estimator_weight:.4f}")
            
            # Early stopping if perfect fit (classification only)
            if model['is_classification'] and error == 0:
                print(f"Perfect fit achieved at iteration {i + 1}")
                break
        
        return model
    
    def predict(X):
        """Make predictions using the model."""
        if model['is_classification']:
            return _predict_classification(X)
        else:
            return _predict_regression(X)
    
    def _predict_classification(X):
        """Make classification predictions."""
        n_samples = X.shape[0]
        n_classes = len(np.unique(model['estimators'][0].classes_))
        
        # Get predictions from each estimator
        predictions = np.zeros((n_samples, n_classes))
        for estimator, weight in zip(model['estimators'], model['estimator_weights']):
            pred = estimator.predict_proba(X)
            predictions += weight * pred
        
        return np.argmax(predictions, axis=1)
    
    def _predict_regression(X):
        """Make regression predictions."""
        predictions = np.zeros(X.shape[0])
        for estimator, weight in zip(model['estimators'], model['estimator_weights']):
            predictions += weight * estimator.predict(X)
        return predictions
    
    def predict_proba(X):
        """Predict class probabilities (for classification only)."""
        if not model['is_classification']:
            raise ValueError("predict_proba is only available for classification")
        
        n_samples = X.shape[0]
        n_classes = len(np.unique(model['estimators'][0].classes_))
        
        # Get predictions from each estimator
        predictions = np.zeros((n_samples, n_classes))
        for estimator, weight in zip(model['estimators'], model['estimator_weights']):
            pred = estimator.predict_proba(X)
            predictions += weight * pred
        
        # Normalize probabilities
        predictions /= np.sum(predictions, axis=1, keepdims=True)
        return predictions
    
    # Add methods to model
    model['fit'] = fit
    model['predict'] = predict
    model['predict_proba'] = predict_proba
    
    return model

## 3. Visualization Functions
def plot_confusion_matrix(y_true, y_pred, title="Confusion Matrix"):
    """
    Plot confusion matrix using seaborn and save to PNG file.
    
    Args:
        y_true (numpy.ndarray): True labels
        y_pred (numpy.ndarray): Predicted labels
        title (str): Plot title
    """
    cm = confusion_matrix(y_true, y_pred)
    plt.figure(figsize=(8, 6))
    sns.heatmap(cm, annot=True, fmt='d', cmap='Blues')
    plt.title(title)
    plt.xlabel('Predicted')
    plt.ylabel('True')
    filename = f'{output_dir}/adaboost-confusion_matrix.png'
    plt.savefig(filename)
    plt.close()

def plot_roc_curves(y_true, y_pred_proba, title="ROC Curves"):
    """
    Plot ROC curves for each class using seaborn and save to PNG file.
    
    Args:
        y_true (numpy.ndarray): True labels
        y_pred_proba (numpy.ndarray): Predicted probabilities
        title (str): Plot title
    """
    plt.figure(figsize=(8, 6))
    
    # Plot ROC curve for each class
    for i in range(y_pred_proba.shape[1]):
        fpr, tpr, _ = roc_curve(y_true == i, y_pred_proba[:, i])
        roc_auc = auc(fpr, tpr)
        plt.plot(fpr, tpr, label=f'Class {i} (AUC = {roc_auc:.2f})')
    
    plt.plot([0, 1], [0, 1], 'k--')
    plt.xlim([0.0, 1.0])
    plt.ylim([0.0, 1.05])
    plt.xlabel('False Positive Rate')
    plt.ylabel('True Positive Rate')
    plt.title(title)
    plt.legend(loc="lower right")
    filename = f'{output_dir}/adaboost-roc_curves.png'
    plt.savefig(filename)
    plt.close()

def plot_regression_results(y_true, y_pred, title="Regression Results"):
    """
    Plot regression results using seaborn and save to PNG file.
    
    Args:
        y_true (numpy.ndarray): True values
        y_pred (numpy.ndarray): Predicted values
        title (str): Plot title
    """
    plt.figure(figsize=(8, 6))
    sns.scatterplot(x=y_true, y=y_pred)
    plt.plot([y_true.min(), y_true.max()], [y_true.min(), y_true.max()], 'r--')
    plt.title(title)
    plt.xlabel('True Values')
    plt.ylabel('Predictions')
    filename = f'{output_dir}/adaboost-regression_results.png'
    plt.savefig(filename)
    plt.close()

## 4. Classification Example
print("\nClassification Example:")
print("----------------------")

# Generate classification data
X_clf, y_clf = make_classification(
    n_samples=1000,
    n_features=20,
    n_informative=15,
    n_redundant=5,
    n_classes=3,
    random_state=2220
)

# Split data
X_train_clf, X_test_clf, y_train_clf, y_test_clf = train_test_split(
    X_clf, y_clf, test_size=0.2, random_state=2220
)

# Create and train AdaBoost classifier
print("\nCreating and training AdaBoost classifier:")
adaboost_clf = create_adaboost(
    base_estimator=DecisionTreeClassifier(max_depth=1, random_state=2220),
    n_estimators=50,    # Number of weak learners
    learning_rate=1.0   # Learning rate
)
adaboost_clf['fit'](X_train_clf, y_train_clf)

# Make predictions
print("\nMaking predictions...")
adaboost_pred_clf = adaboost_clf['predict'](X_test_clf)
adaboost_proba_clf = adaboost_clf['predict_proba'](X_test_clf)

# Evaluate classifier
print("\nAdaBoost Classifier Results:")
print(f"Accuracy: {accuracy_score(y_test_clf, adaboost_pred_clf):.3f}")
print("\nClassification Report:")
print(classification_report(y_test_clf, adaboost_pred_clf))

# Plot results
plot_confusion_matrix(y_test_clf, adaboost_pred_clf, "AdaBoost Confusion Matrix")
plot_roc_curves(y_test_clf, adaboost_proba_clf, "AdaBoost ROC Curves")

## 5. Regression Example
print("\nRegression Example:")
print("------------------")

# Generate regression data
X_reg, y_reg = make_regression(
    n_samples=1000,
    n_features=20,
    n_informative=15,
    noise=0.1,
    random_state=2220
)

# Split data
X_train_reg, X_test_reg, y_train_reg, y_test_reg = train_test_split(
    X_reg, y_reg, test_size=0.2, random_state=2220
)

# Create and train AdaBoost regressor
print("\nCreating and training AdaBoost regressor:")
adaboost_reg = create_adaboost(
    base_estimator=DecisionTreeRegressor(max_depth=1, random_state=2220),
    n_estimators=50,    # Number of weak learners
    learning_rate=1.0   # Learning rate
)
adaboost_reg['fit'](X_train_reg, y_train_reg)

# Make predictions
print("\nMaking predictions...")
adaboost_pred_reg = adaboost_reg['predict'](X_test_reg)

# Evaluate regressor
print("\nAdaBoost Regressor Results:")
print(f"MSE: {mean_squared_error(y_test_reg, adaboost_pred_reg):.3f}")
print(f"R2 Score: {r2_score(y_test_reg, adaboost_pred_reg):.3f}")

# Plot results
plot_regression_results(y_test_reg, adaboost_pred_reg, "AdaBoost Regression Results") 