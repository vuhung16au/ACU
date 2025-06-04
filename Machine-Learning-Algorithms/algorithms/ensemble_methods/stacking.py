## Stacking Ensemble Implementation
# This notebook demonstrates the implementation of a stacking ensemble method using sklearn's StackingClassifier.
# Stacking combines multiple base models by training a meta-model to learn
# how to best combine their predictions.

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from typing import List, Union, Optional
from sklearn.base import BaseEstimator, ClassifierMixin, RegressorMixin
from sklearn.model_selection import KFold, train_test_split
from sklearn.metrics import (
    accuracy_score, confusion_matrix, classification_report,
    roc_curve, auc
)
from sklearn.datasets import make_classification
from sklearn.linear_model import LogisticRegression
from sklearn.tree import DecisionTreeClassifier
from sklearn.svm import SVC
from sklearn.ensemble import StackingClassifier
import os

# Set random seed for reproducibility
np.random.seed(2220)

# Create output directory for visuals
output_dir = 'algorithms/ensemble_methods'
os.makedirs(output_dir, exist_ok=True)

## 2. Stacking Ensemble Implementation (using sklearn)

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
    filename = f'{output_dir}/stacking-confusion_matrix.png'
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
    filename = f'{output_dir}/stacking-roc_curves.png'
    plt.savefig(filename)
    plt.close()

## 4. Model Training and Evaluation
print("\nStacking Ensemble Example:")
print("------------------------")

# Generate sample data
X, y = make_classification(
    n_samples=1000,
    n_features=20,
    n_informative=15,
    n_redundant=5,
    n_classes=3,
    random_state=2220
)

# Split data
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.2, random_state=2220
)

# Create base models as (name, estimator) tuples
base_models = [
    ("decision_tree", DecisionTreeClassifier(max_depth=3, random_state=2220)),
    ("svc", SVC(probability=True, random_state=2220)),
    ("logreg", LogisticRegression(random_state=2220))
]

# Create meta-model
meta_model = LogisticRegression(random_state=2220)

# Create and train stacking ensemble using sklearn's StackingClassifier
print("\nCreating and training stacking ensemble (sklearn):")
stacking = StackingClassifier(
    estimators=base_models,
    final_estimator=meta_model,
    cv=5,
    passthrough=False,
    stack_method="predict_proba"
)
stacking.fit(X_train, y_train)

# Make predictions
print("\nMaking predictions...")
stacking_pred = stacking.predict(X_test)
stacking_proba = stacking.predict_proba(X_test)

# Evaluate stacking ensemble
print("\nStacking Ensemble Results:")
print(f"Accuracy: {accuracy_score(y_test, stacking_pred):.3f}")
print("\nClassification Report:")
print(classification_report(y_test, stacking_pred))

# Plot results
plot_confusion_matrix(y_test, stacking_pred, "Stacking Confusion Matrix")
plot_roc_curves(y_test, stacking_proba, "Stacking ROC Curves")

# Compare with individual models
print("\nIndividual Model Results:")
for i, (name, model) in enumerate(base_models):
    model.fit(X_train, y_train)
    y_pred = model.predict(X_test)
    print(f"\nBase Model {i + 1} ({name}):")
    print(f"Accuracy: {accuracy_score(y_test, y_pred):.3f}")
    print("\nClassification Report:")
    print(classification_report(y_test, y_pred)) 