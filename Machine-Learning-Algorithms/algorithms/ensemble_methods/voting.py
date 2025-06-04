## Voting Ensemble Implementation
# This notebook demonstrates both hard and soft voting ensemble methods.
# Voting ensembles combine multiple classifiers to make predictions based on
# majority voting (hard) or probability averaging (soft).

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from typing import List, Union, Optional, Literal
from sklearn.base import BaseEstimator, ClassifierMixin, clone
from sklearn.preprocessing import LabelEncoder
from sklearn.datasets import make_classification
from sklearn.linear_model import LogisticRegression
from sklearn.tree import DecisionTreeClassifier
from sklearn.svm import SVC
from sklearn.metrics import (
    accuracy_score, confusion_matrix, classification_report,
    roc_curve, auc
)
from sklearn.model_selection import train_test_split
import os

# Set random seed for reproducibility
np.random.seed(2220)

# Create output directory for visuals
output_dir = 'algorithms/ensemble_methods'
os.makedirs(output_dir, exist_ok=True)

## 2. Voting Ensemble Implementation
def create_voting_ensemble(estimators, voting='hard', weights=None):
    """
    Create a voting ensemble classifier.
    
    Hyperparameters:
    - estimators (list): List of (name, estimator) tuples. Each estimator
      should be a classifier that implements fit and predict methods.
    - voting (str): Voting strategy ('hard' or 'soft').
      - 'hard': Uses majority voting for predictions
      - 'soft': Uses probability averaging for predictions
    - weights (list): List of weights for each estimator. If None, equal
      weights are used.
    
    Args:
        estimators (list): List of (name, estimator) tuples
        voting (str): Voting strategy ('hard' or 'soft')
        weights (list): List of weights for each estimator
        
    Returns:
        dict: Ensemble model with fit and predict methods
    """
    # Initialize estimators
    ensemble = {
        'estimators': [clone(estimator) for _, estimator in estimators],
        'voting': voting,
        'weights': weights,
        'label_encoder': LabelEncoder(),
        'classes': None
    }
    
    def fit(X, y):
        """Train the voting ensemble."""
        # Encode labels
        y = ensemble['label_encoder'].fit_transform(y)
        ensemble['classes'] = ensemble['label_encoder'].classes_
        
        # Train estimators
        print("\nTraining base estimators:")
        for i, estimator in enumerate(ensemble['estimators']):
            print(f"Training estimator {i + 1}/{len(ensemble['estimators'])}")
            estimator.fit(X, y)
        
        return ensemble
    
    def predict(X):
        """Make predictions using the ensemble."""
        if ensemble['voting'] == 'hard':
            return _predict_hard(X)
        else:
            return _predict_soft(X)
    
    def predict_proba(X):
        """Predict class probabilities."""
        if ensemble['voting'] == 'hard':
            raise ValueError("predict_proba is not available when voting='hard'")
        
        # Get probabilities from each estimator
        probas = np.array([estimator.predict_proba(X) 
                          for estimator in ensemble['estimators']])
        
        # Apply weights if provided
        if ensemble['weights'] is not None:
            probas = np.average(probas, axis=0, weights=ensemble['weights'])
        else:
            probas = np.mean(probas, axis=0)
        
        return probas
    
    def _predict_hard(X):
        """Predict using hard voting."""
        # Get predictions from each estimator
        predictions = np.array([estimator.predict(X) 
                              for estimator in ensemble['estimators']])
        
        # Apply weights if provided
        if ensemble['weights'] is not None:
            weighted_predictions = np.zeros((len(X), len(ensemble['classes'])))
            for i, pred in enumerate(predictions):
                weighted_predictions[np.arange(len(X)), pred] += ensemble['weights'][i]
            return np.argmax(weighted_predictions, axis=1)
        else:
            # Use majority voting
            return np.apply_along_axis(
                lambda x: np.argmax(np.bincount(x, minlength=len(ensemble['classes']))),
                axis=0,
                arr=predictions
            )
    
    def _predict_soft(X):
        """Predict using soft voting."""
        return np.argmax(predict_proba(X), axis=1)
    
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
    filename = f'{output_dir}/voting-confusion_matrix.png'
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
    filename = f'{output_dir}/voting-roc_curves.png'
    plt.savefig(filename)
    plt.close()

## 4. Model Training and Evaluation
print("\nVoting Ensemble Example:")
print("----------------------")

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

# Create base classifiers
estimators = [
    ('dt', DecisionTreeClassifier(max_depth=3, random_state=2220)),
    ('svc', SVC(probability=True, random_state=2220)),
    ('lr', LogisticRegression(random_state=2220))
]

# Create and train hard voting ensemble
print("\nCreating and training hard voting ensemble:")
hard_voting = create_voting_ensemble(
    estimators=estimators,
    voting='hard'  # Use majority voting
)
hard_voting['fit'](X_train, y_train)

# Create and train soft voting ensemble
print("\nCreating and training soft voting ensemble:")
soft_voting = create_voting_ensemble(
    estimators=estimators,
    voting='soft'  # Use probability averaging
)
soft_voting['fit'](X_train, y_train)

# Make predictions
print("\nMaking predictions:")
hard_pred = hard_voting['predict'](X_test)
soft_pred = soft_voting['predict'](X_test)
soft_proba = soft_voting['predict_proba'](X_test)

# Evaluate ensembles
print("\nHard Voting Ensemble Results:")
print(f"Accuracy: {accuracy_score(y_test, hard_pred):.3f}")
print("\nClassification Report:")
print(classification_report(y_test, hard_pred))

print("\nSoft Voting Ensemble Results:")
print(f"Accuracy: {accuracy_score(y_test, soft_pred):.3f}")
print("\nClassification Report:")
print(classification_report(y_test, soft_pred))

# Plot results
plot_confusion_matrix(y_test, hard_pred, "Hard Voting Confusion Matrix")
plot_confusion_matrix(y_test, soft_pred, "Soft Voting Confusion Matrix")
plot_roc_curves(y_test, soft_proba, "Soft Voting ROC Curves")

# Compare with individual models
print("\nIndividual Model Results:")
for i, (name, model) in enumerate(estimators):
    model.fit(X_train, y_train)
    y_pred = model.predict(X_test)
    print(f"\n{name.upper()} Model:")
    print(f"Accuracy: {accuracy_score(y_test, y_pred):.3f}")
    print("\nClassification Report:")
    print(classification_report(y_test, y_pred)) 