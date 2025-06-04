## Ensemble Combination Methods Implementation
# This notebook demonstrates three different ensemble combination methods:
# 1. Class-based Multiple Classifier (CMC)
# 2. Dynamic Classifier Selection (DCS)
# 3. Adaptive Classifier Combination (ACC)

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from typing import List, Union, Optional, Tuple, Dict
from sklearn.base import BaseEstimator, ClassifierMixin, clone
from sklearn.metrics import (
    accuracy_score, confusion_matrix, classification_report,
    roc_curve, auc
)
from sklearn.neighbors import KNeighborsClassifier
from sklearn.datasets import make_classification
from sklearn.model_selection import train_test_split
from sklearn.tree import DecisionTreeClassifier
from sklearn.svm import SVC
from sklearn.ensemble import RandomForestClassifier
import os

# Set random seed for reproducibility
np.random.seed(2220)

# Create output directory for visuals
output_dir = 'algorithms/ensemble_methods'
os.makedirs(output_dir, exist_ok=True)

## 2. Class-based Multiple Classifier (CMC) Implementation
def create_cmc(
    base_estimators: List[BaseEstimator],
    n_classes: int
):
    """
    Create a Class-based Multiple Classifier (CMC) ensemble.
    
    Hyperparameters:
    - base_estimators: List of base estimators to use. Each estimator should
      implement fit and predict methods. For best results, use diverse estimators
      with different strengths.
    - n_classes: Number of classes in the classification problem. This determines
      how many binary classifiers will be trained for each base estimator.
    
    Args:
        base_estimators: List of base estimators
        n_classes: Number of classes
        
    Returns:
        dict: CMC model with fit and predict methods
    """
    # Initialize model
    model = {
        'base_estimators': base_estimators,
        'n_classes': n_classes,
        'class_estimators': {}
    }
    
    # Initialize estimators for each class
    for i in range(n_classes):
        model['class_estimators'][i] = [clone(est) for est in base_estimators]
    
    def fit(X, y):
        """Train the CMC ensemble."""
        print("\nTraining CMC ensemble:")
        # Train estimators for each class
        for class_idx in range(model['n_classes']):
            print(f"\nTraining classifiers for class {class_idx + 1}/{model['n_classes']}")
            # Create binary labels for current class
            y_binary = (y == class_idx).astype(int)
            
            # Train each estimator
            for i, estimator in enumerate(model['class_estimators'][class_idx]):
                print(f"  Training estimator {i + 1}/{len(model['class_estimators'][class_idx])}")
                estimator.fit(X, y_binary)
        
        return model
    
    def predict(X):
        """Make predictions using the model."""
        n_samples = X.shape[0]
        class_scores = np.zeros((n_samples, model['n_classes']))
        
        # Get predictions from each class's estimators
        for class_idx in range(model['n_classes']):
            for estimator in model['class_estimators'][class_idx]:
                if hasattr(estimator, 'predict_proba'):
                    scores = estimator.predict_proba(X)[:, 1]
                else:
                    scores = estimator.predict(X)
                class_scores[:, class_idx] += scores
        
        # Average scores for each class
        class_scores /= len(model['base_estimators'])
        
        # Return class with highest score
        return np.argmax(class_scores, axis=1)
    
    # Add methods to model
    model['fit'] = fit
    model['predict'] = predict
    
    return model

## 3. Dynamic Classifier Selection (DCS) Implementation
def create_dcs(
    base_estimators: List[BaseEstimator],
    k_neighbors: int = 5,
    selection_method: str = 'local_accuracy'
):
    """
    Create a Dynamic Classifier Selection (DCS) ensemble.
    
    Hyperparameters:
    - base_estimators: List of base estimators to use. Each estimator should
      implement fit and predict methods. For best results, use diverse estimators
      with different strengths.
    - k_neighbors: Number of neighbors to consider for local accuracy calculation.
      Higher values consider more neighbors but may be less sensitive to local
      patterns.
    - selection_method: Method to select the best classifier ('local_accuracy' or
      'overall_accuracy'). Local accuracy considers performance in the neighborhood
      of each test sample, while overall accuracy uses global performance.
    
    Args:
        base_estimators: List of base estimators
        k_neighbors: Number of neighbors for local accuracy
        selection_method: Selection method
        
    Returns:
        dict: DCS model with fit and predict methods
    """
    # Initialize model
    model = {
        'base_estimators': base_estimators,
        'k_neighbors': k_neighbors,
        'selection_method': selection_method,
        'estimators': [clone(est) for est in base_estimators],
        'knn': KNeighborsClassifier(n_neighbors=k_neighbors),
        'X_train': None,
        'y_train': None,
        'estimator_accuracies': None
    }
    
    def fit(X, y):
        """Train the DCS ensemble."""
        print("\nTraining DCS ensemble:")
        # Store training data
        model['X_train'] = X
        model['y_train'] = y
        
        # Train base estimators
        print("\nTraining base estimators:")
        for i, estimator in enumerate(model['estimators']):
            print(f"Training estimator {i + 1}/{len(model['estimators'])}")
            estimator.fit(X, y)
        
        # Calculate overall accuracies
        print("\nCalculating estimator accuracies...")
        model['estimator_accuracies'] = np.zeros(len(model['estimators']))
        for i, estimator in enumerate(model['estimators']):
            y_pred = estimator.predict(X)
            model['estimator_accuracies'][i] = accuracy_score(y, y_pred)
            print(f"Estimator {i + 1} accuracy: {model['estimator_accuracies'][i]:.4f}")
        
        # Train KNN for local accuracy
        if model['selection_method'] == 'local_accuracy':
            print("\nTraining KNN for local accuracy...")
            model['knn'].fit(X, y)
        
        return model
    
    def predict(X):
        """Make predictions using the model."""
        n_samples = X.shape[0]
        predictions = np.zeros(n_samples)
        
        if model['selection_method'] == 'local_accuracy':
            # Find nearest neighbors
            _, indices = model['knn'].kneighbors(X)
            
            # For each sample
            for i in range(n_samples):
                # Get local accuracy for each estimator
                local_accuracies = np.zeros(len(model['estimators']))
                for j, estimator in enumerate(model['estimators']):
                    y_pred = estimator.predict(model['X_train'][indices[i]])
                    local_accuracies[j] = accuracy_score(
                        model['y_train'][indices[i]],
                        y_pred
                    )
                
                # Select best estimator
                best_estimator_idx = np.argmax(local_accuracies)
                predictions[i] = model['estimators'][best_estimator_idx].predict(X[i:i+1])
        
        else:  # overall_accuracy
            # Select best overall estimator
            best_estimator_idx = np.argmax(model['estimator_accuracies'])
            predictions = model['estimators'][best_estimator_idx].predict(X)
        
        return predictions
    
    # Add methods to model
    model['fit'] = fit
    model['predict'] = predict
    
    return model

## 4. Adaptive Classifier Combination (ACC) Implementation
def create_acc(
    base_estimators: List[BaseEstimator],
    n_neighbors: int = 5
):
    """
    Create an Adaptive Classifier Combination (ACC) ensemble.
    
    Hyperparameters:
    - base_estimators: List of base estimators to use. Each estimator should
      implement fit and predict methods. For best results, use diverse estimators
      with different strengths.
    - n_neighbors: Number of neighbors to consider for local performance
      calculation. Higher values consider more neighbors but may be less
      sensitive to local patterns.
    
    Args:
        base_estimators: List of base estimators
        n_neighbors: Number of neighbors for local performance
        
    Returns:
        dict: ACC model with fit and predict methods
    """
    # Initialize model
    model = {
        'base_estimators': base_estimators,
        'n_neighbors': n_neighbors,
        'estimators': [clone(est) for est in base_estimators],
        'knn': KNeighborsClassifier(n_neighbors=n_neighbors),
        'X_train': None,
        'y_train': None,
        'estimator_weights': None
    }
    
    def fit(X, y):
        """Train the ACC ensemble."""
        print("\nTraining ACC ensemble:")
        # Store training data
        model['X_train'] = X
        model['y_train'] = y
        
        # Train base estimators
        print("\nTraining base estimators:")
        for i, estimator in enumerate(model['estimators']):
            print(f"Training estimator {i + 1}/{len(model['estimators'])}")
            estimator.fit(X, y)
        
        # Train KNN
        print("\nTraining KNN for local performance...")
        model['knn'].fit(X, y)
        
        # Initialize weights
        model['estimator_weights'] = np.ones((len(X), len(model['estimators'])))
        
        # Calculate local weights
        print("\nCalculating local weights...")
        _, indices = model['knn'].kneighbors(X)
        
        for i in range(len(X)):
            # Get local performance for each estimator
            for j, estimator in enumerate(model['estimators']):
                y_pred = estimator.predict(model['X_train'][indices[i]])
                model['estimator_weights'][i, j] = accuracy_score(
                    model['y_train'][indices[i]],
                    y_pred
                )
        
        return model
    
    def predict(X):
        """Make predictions using the model."""
        n_samples = X.shape[0]
        predictions = np.zeros(n_samples)
        
        # Find nearest neighbors
        _, indices = model['knn'].kneighbors(X)
        
        # For each sample
        for i in range(n_samples):
            # Get weighted predictions
            weighted_preds = np.zeros(len(model['estimators']))
            for j, estimator in enumerate(model['estimators']):
                pred = estimator.predict(X[i:i+1])
                weighted_preds[j] = pred * model['estimator_weights'][indices[i][0], j]
            
            # Make final prediction
            predictions[i] = np.round(np.mean(weighted_preds))
        
        return predictions
    
    # Add methods to model
    model['fit'] = fit
    model['predict'] = predict
    
    return model

## 5. Visualization Functions
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
    filename = f'{output_dir}/combination-confusion_matrix.png'
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
    filename = f'{output_dir}/combination-roc_curves.png'
    plt.savefig(filename)
    plt.close()

## 6. Model Training and Evaluation
print("\nEnsemble Combination Methods Example:")
print("-----------------------------------")

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

# Create base models
base_models = [
    DecisionTreeClassifier(max_depth=3, random_state=2220),
    SVC(probability=True, random_state=2220),
    RandomForestClassifier(n_estimators=100, random_state=2220)
]

# Create and train CMC ensemble
print("\nCreating and training CMC ensemble:")
cmc = create_cmc(
    base_estimators=base_models,
    n_classes=3
)
cmc['fit'](X_train, y_train)

# Create and train DCS ensemble
print("\nCreating and training DCS ensemble:")
dcs = create_dcs(
    base_estimators=base_models,
    k_neighbors=5,
    selection_method='local_accuracy'
)
dcs['fit'](X_train, y_train)

# Create and train ACC ensemble
print("\nCreating and training ACC ensemble:")
acc = create_acc(
    base_estimators=base_models,
    n_neighbors=5
)
acc['fit'](X_train, y_train)

# Make predictions
print("\nMaking predictions...")
cmc_pred = cmc['predict'](X_test)
dcs_pred = dcs['predict'](X_test)
acc_pred = acc['predict'](X_test)

# Evaluate ensembles
print("\nCMC Ensemble Results:")
print(f"Accuracy: {accuracy_score(y_test, cmc_pred):.3f}")
print("\nClassification Report:")
print(classification_report(y_test, cmc_pred))

print("\nDCS Ensemble Results:")
print(f"Accuracy: {accuracy_score(y_test, dcs_pred):.3f}")
print("\nClassification Report:")
print(classification_report(y_test, dcs_pred))

print("\nACC Ensemble Results:")
print(f"Accuracy: {accuracy_score(y_test, acc_pred):.3f}")
print("\nClassification Report:")
print(classification_report(y_test, acc_pred))

# Plot results
plot_confusion_matrix(y_test, cmc_pred, "CMC Confusion Matrix")
plot_confusion_matrix(y_test, dcs_pred, "DCS Confusion Matrix")
plot_confusion_matrix(y_test, acc_pred, "ACC Confusion Matrix")

# Compare with individual models
print("\nIndividual Model Results:")
for i, model in enumerate(base_models):
    model.fit(X_train, y_train)
    y_pred = model.predict(X_test)
    print(f"\nBase Model {i + 1}:")
    print(f"Accuracy: {accuracy_score(y_test, y_pred):.3f}")
    print("\nClassification Report:")
    print(classification_report(y_test, y_pred)) 