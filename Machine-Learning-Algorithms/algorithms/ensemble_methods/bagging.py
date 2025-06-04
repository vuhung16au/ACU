## Bagging Ensemble Implementation
# This notebook demonstrates both bagging and pasting ensemble methods.
# Bagging (Bootstrap Aggregating) combines multiple models trained on different
# bootstrap samples of the training data, while pasting uses random subsets
# without replacement.

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from typing import List, Union, Optional
from sklearn.base import BaseEstimator, ClassifierMixin, RegressorMixin, clone
from sklearn.utils import resample
from sklearn.metrics import (
    accuracy_score, confusion_matrix, classification_report,
    roc_curve, auc, mean_squared_error, r2_score
)
from sklearn.datasets import make_classification, make_regression
from sklearn.tree import DecisionTreeClassifier, DecisionTreeRegressor
from sklearn.model_selection import train_test_split
import os

# Set random seed for reproducibility
np.random.seed(2220)

# Create output directory for visuals
output_dir = 'algorithms/ensemble_methods'
os.makedirs(output_dir, exist_ok=True)

## 2. Bagging Ensemble Implementation
def create_bagging_ensemble(base_estimator, n_estimators=10, max_samples=1.0, bootstrap=True):
    """
    Create a bagging ensemble model.
    
    Hyperparameters:
    - base_estimator: Base model to use. Should implement fit and predict methods.
      For classification, should also implement predict_proba.
    - n_estimators (int): Number of models in the ensemble. More models generally
      lead to better performance but increase computation time.
    - max_samples (float): Proportion of samples to use for each model.
      Lower values increase diversity but may reduce individual model performance.
    - bootstrap (bool): Whether to use bootstrap sampling (True for bagging,
      False for pasting). Bootstrap sampling can help reduce variance but may
      increase bias.
    
    Args:
        base_estimator: Base model to use
        n_estimators (int): Number of models
        max_samples (float): Proportion of samples to use
        bootstrap (bool): Whether to use bootstrap sampling
        
    Returns:
        dict: Ensemble model with fit and predict methods
    """
    # Initialize ensemble
    ensemble = {
        'base_estimator': base_estimator,
        'n_estimators': n_estimators,
        'max_samples': max_samples,
        'bootstrap': bootstrap,
        'estimators': [],
        'is_classification': isinstance(base_estimator, ClassifierMixin)
    }
    
    def fit(X, y):
        """Train the bagging ensemble."""
        print("\nTraining Bagging Ensemble:")
        n_samples = X.shape[0]
        n_samples_bootstrap = int(n_samples * ensemble['max_samples'])
        
        # Train estimators
        for i in range(ensemble['n_estimators']):
            print(f"Training estimator {i + 1}/{ensemble['n_estimators']}")
            # Create and train estimator
            estimator = clone(ensemble['base_estimator'])
            
            # Sample data
            if ensemble['bootstrap']:
                # Bagging: Sample with replacement
                indices = np.random.choice(
                    n_samples,
                    size=n_samples_bootstrap,
                    replace=True
                )
            else:
                # Pasting: Sample without replacement
                indices = np.random.choice(
                    n_samples,
                    size=n_samples_bootstrap,
                    replace=False
                )
            
            X_sample = X[indices]
            y_sample = y[indices]
            
            # Train estimator
            estimator.fit(X_sample, y_sample)
            ensemble['estimators'].append(estimator)
        
        return ensemble
    
    def predict(X):
        """Make predictions using the ensemble."""
        if ensemble['is_classification']:
            return _predict_classification(X)
        else:
            return _predict_regression(X)
    
    def _predict_classification(X):
        """Make classification predictions."""
        # Get predictions from each estimator
        predictions = np.array([estimator.predict(X) for estimator in ensemble['estimators']])
        
        # Use majority voting
        return np.apply_along_axis(
            lambda x: np.argmax(np.bincount(x)),
            axis=0,
            arr=predictions
        )
    
    def _predict_regression(X):
        """Make regression predictions."""
        # Get predictions from each estimator
        predictions = np.array([estimator.predict(X) for estimator in ensemble['estimators']])
        
        # Average predictions
        return np.mean(predictions, axis=0)
    
    def predict_proba(X):
        """Predict class probabilities (for classification only)."""
        if not ensemble['is_classification']:
            raise ValueError("predict_proba is only available for classification")
        
        # Get probabilities from each estimator
        probas = np.array([estimator.predict_proba(X) for estimator in ensemble['estimators']])
        
        # Average probabilities
        return np.mean(probas, axis=0)
    
    # Add methods to ensemble
    ensemble['fit'] = fit
    ensemble['predict'] = predict
    ensemble['predict_proba'] = predict_proba
    
    return ensemble

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
    filename = f'{output_dir}/bagging-confusion_matrix.png'
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
    filename = f'{output_dir}/bagging-roc_curves.png'
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
    filename = f'{output_dir}/bagging-regression_results.png'
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

# Create and train Bagging classifier
print("\nTraining Bagging Classifier:")
bagging_clf = create_bagging_ensemble(
    base_estimator=DecisionTreeClassifier(random_state=2220),
    n_estimators=10,    # Number of models
    max_samples=0.8,    # Proportion of samples to use
    bootstrap=True      # Use bootstrap sampling (bagging)
)
bagging_clf['fit'](X_train_clf, y_train_clf)

# Create and train Pasting classifier
print("\nTraining Pasting Classifier:")
pasting_clf = create_bagging_ensemble(
    base_estimator=DecisionTreeClassifier(random_state=2220),
    n_estimators=10,    # Number of models
    max_samples=0.8,    # Proportion of samples to use
    bootstrap=False     # Don't use bootstrap sampling (pasting)
)
pasting_clf['fit'](X_train_clf, y_train_clf)

# Make predictions
print("\nMaking predictions...")
bagging_pred_clf = bagging_clf['predict'](X_test_clf)
bagging_prob_clf = bagging_clf['predict_proba'](X_test_clf)
pasting_pred_clf = pasting_clf['predict'](X_test_clf)
pasting_prob_clf = pasting_clf['predict_proba'](X_test_clf)

# Print evaluation metrics
print("\nBagging Classifier Results:")
print(f"Accuracy: {accuracy_score(y_test_clf, bagging_pred_clf):.3f}")
print("\nClassification Report:")
print(classification_report(y_test_clf, bagging_pred_clf))

print("\nPasting Classifier Results:")
print(f"Accuracy: {accuracy_score(y_test_clf, pasting_pred_clf):.3f}")
print("\nClassification Report:")
print(classification_report(y_test_clf, pasting_pred_clf))

# Plot results
plot_confusion_matrix(y_test_clf, bagging_pred_clf, "Bagging Confusion Matrix")
plot_confusion_matrix(y_test_clf, pasting_pred_clf, "Pasting Confusion Matrix")
plot_roc_curves(y_test_clf, bagging_prob_clf, "Bagging ROC Curves")
plot_roc_curves(y_test_clf, pasting_prob_clf, "Pasting ROC Curves")

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

# Create and train Bagging regressor
print("\nTraining Bagging Regressor:")
bagging_reg = create_bagging_ensemble(
    base_estimator=DecisionTreeRegressor(random_state=2220),
    n_estimators=10,    # Number of models
    max_samples=0.8,    # Proportion of samples to use
    bootstrap=True      # Use bootstrap sampling (bagging)
)
bagging_reg['fit'](X_train_reg, y_train_reg)

# Create and train Pasting regressor
print("\nTraining Pasting Regressor:")
pasting_reg = create_bagging_ensemble(
    base_estimator=DecisionTreeRegressor(random_state=2220),
    n_estimators=10,    # Number of models
    max_samples=0.8,    # Proportion of samples to use
    bootstrap=False     # Don't use bootstrap sampling (pasting)
)
pasting_reg['fit'](X_train_reg, y_train_reg)

# Make predictions
print("\nMaking predictions...")
bagging_pred_reg = bagging_reg['predict'](X_test_reg)
pasting_pred_reg = pasting_reg['predict'](X_test_reg)

# Print evaluation metrics
print("\nBagging Regressor Results:")
print(f"MSE: {mean_squared_error(y_test_reg, bagging_pred_reg):.3f}")
print(f"R2 Score: {r2_score(y_test_reg, bagging_pred_reg):.3f}")

print("\nPasting Regressor Results:")
print(f"MSE: {mean_squared_error(y_test_reg, pasting_pred_reg):.3f}")
print(f"R2 Score: {r2_score(y_test_reg, pasting_pred_reg):.3f}")

# Plot results
plot_regression_results(y_test_reg, bagging_pred_reg, "Bagging Regression Results")
plot_regression_results(y_test_reg, pasting_pred_reg, "Pasting Regression Results") 