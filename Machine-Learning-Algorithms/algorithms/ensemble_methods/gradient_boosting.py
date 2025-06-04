## Gradient Boosting Implementation
# This notebook demonstrates the Gradient Boosting algorithm for both classification
# and regression tasks using sklearn's ensemble module.

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from typing import List, Union, Optional, Tuple, Callable
from sklearn.base import BaseEstimator, ClassifierMixin, RegressorMixin, clone
from sklearn.tree import DecisionTreeRegressor
from sklearn.metrics import (
    accuracy_score, confusion_matrix, classification_report,
    roc_curve, auc, mean_squared_error, r2_score
)
from sklearn.datasets import make_classification, make_regression
from sklearn.model_selection import train_test_split
from sklearn.ensemble import GradientBoostingClassifier, GradientBoostingRegressor
import os

# Set random seed for reproducibility
np.random.seed(2220)

# Create output directory for visuals
output_dir = 'algorithms/ensemble_methods'
os.makedirs(output_dir, exist_ok=True)

## 2. Visualization Functions
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
    filename = f'{output_dir}/gradient_boosting-confusion_matrix.png'
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
    filename = f'{output_dir}/gradient_boosting-roc_curves.png'
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
    filename = f'{output_dir}/gradient_boosting-regression_results.png'
    plt.savefig(filename)
    plt.close()

## 3. Classification Example
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

# Create and train Gradient Boosting classifier using sklearn
print("\nTraining Gradient Boosting Classifier:")
gb_clf = GradientBoostingClassifier(
    n_estimators=100,    # Number of boosting stages
    learning_rate=0.1,   # Learning rate
    max_depth=3,         # Maximum depth of trees
    min_samples_split=2, # Minimum samples required to split
    subsample=0.8,       # Fraction of samples to use
    random_state=2220
)
gb_clf.fit(X_train_clf, y_train_clf)

# Make predictions
print("\nMaking predictions...")
gb_pred_clf = gb_clf.predict(X_test_clf)
gb_prob_clf = gb_clf.predict_proba(X_test_clf)

# Print evaluation metrics
print("\nGradient Boosting Classifier Results:")
print(f"Accuracy: {accuracy_score(y_test_clf, gb_pred_clf):.3f}")
print("\nClassification Report:")
print(classification_report(y_test_clf, gb_pred_clf))

# Plot results
plot_confusion_matrix(y_test_clf, gb_pred_clf, "Gradient Boosting Confusion Matrix")
plot_roc_curves(y_test_clf, gb_prob_clf, "Gradient Boosting ROC Curves")

## 4. Regression Example
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

# Create and train Gradient Boosting regressor using sklearn
print("\nTraining Gradient Boosting Regressor:")
gb_reg = GradientBoostingRegressor(
    n_estimators=100,    # Number of boosting stages
    learning_rate=0.1,   # Learning rate
    max_depth=3,         # Maximum depth of trees
    min_samples_split=2, # Minimum samples required to split
    subsample=0.8,       # Fraction of samples to use
    random_state=2220
)
gb_reg.fit(X_train_reg, y_train_reg)

# Make predictions
print("\nMaking predictions...")
gb_pred_reg = gb_reg.predict(X_test_reg)

# Print evaluation metrics
print("\nGradient Boosting Regressor Results:")
print(f"MSE: {mean_squared_error(y_test_reg, gb_pred_reg):.3f}")
print(f"R2 Score: {r2_score(y_test_reg, gb_pred_reg):.3f}")

# Plot results
plot_regression_results(y_test_reg, gb_pred_reg, "Gradient Boosting Regression Results") 