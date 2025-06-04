## Combination of Multiple Classifiers (CMC) Implementation
## This notebook demonstrates the implementation of Combination of Multiple Classifiers,
## an ensemble method that combines predictions from multiple base classifiers
## using a weighted voting scheme where weights are determined by classifier
## performance on a validation set.

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
from sklearn.base import BaseEstimator, ClassifierMixin
from sklearn.utils.validation import check_X_y, check_array
from sklearn.metrics import confusion_matrix, classification_report
from typing import List, Dict, Any, Tuple
from sklearn.datasets import make_classification

## 2. Set Random Seed
np.random.seed(2220)

## 3. Core Functions
def split_data(X: np.ndarray, y: np.ndarray, validation_split: float = 0.2) -> Tuple[Tuple[np.ndarray, np.ndarray], Tuple[np.ndarray, np.ndarray]]:
    """
    Split data into training and validation sets.
    
    Parameters:
    -----------
    X : array-like of shape (n_samples, n_features)
        Features
    y : array-like of shape (n_samples,)
        Target values
    validation_split : float, default=0.2
        Proportion of data to use for validation
        
    Returns:
    --------
    tuple : ((X_train, y_train), (X_val, y_val))
        Training and validation data splits
    """
    n_samples = X.shape[0]
    val_size = int(n_samples * validation_split)
    indices = np.random.permutation(n_samples)
    val_indices = indices[:val_size]
    train_indices = indices[val_size:]
    
    X_train, y_train = X[train_indices], y[train_indices]
    X_val, y_val = X[val_indices], y[val_indices]
    
    return (X_train, y_train), (X_val, y_val)

def calculate_weights(X_val: np.ndarray, y_val: np.ndarray, base_classifiers: List[Any]) -> np.ndarray:
    """
    Calculate classifier weights based on validation performance.
    
    Parameters:
    -----------
    X_val : array-like of shape (n_val_samples, n_features)
        Validation features
    y_val : array-like of shape (n_val_samples,)
        Validation target values
    base_classifiers : List[Any]
        List of trained base classifier instances
        
    Returns:
    --------
    weights : array-like of shape (n_classifiers,)
        Normalized weights for each classifier
    """
    weights = []
    for clf in base_classifiers:
        y_pred = clf.predict(X_val)
        accuracy = np.mean(y_pred == y_val)
        weights.append(accuracy)
    
    # Normalize weights
    weights = np.array(weights)
    weights = weights / np.sum(weights)
    return weights

def fit_cmc(X: np.ndarray, y: np.ndarray, base_classifiers: List[Any], 
           validation_split: float = 0.2) -> Tuple[List[Any], np.ndarray, np.ndarray]:
    """
    Fit the CMC ensemble.
    
    Parameters:
    -----------
    X : array-like of shape (n_samples, n_features)
        Training data
    y : array-like of shape (n_samples,)
        Target values
    base_classifiers : List[Any]
        List of base classifier instances
    validation_split : float, default=0.2
        Proportion of training data to use for validation
        
    Returns:
    --------
    tuple : (base_classifiers, classifier_weights, classes)
        Trained base classifiers, classifier weights, and unique classes
    """
    X, y = check_X_y(X, y)
    classes = np.unique(y)
    
    # Split data for training and validation
    (X_train, y_train), (X_val, y_val) = split_data(X, y, validation_split)
    
    # Train base classifiers
    for clf in base_classifiers:
        clf.fit(X_train, y_train)
    
    # Calculate weights based on validation performance
    classifier_weights = calculate_weights(X_val, y_val, base_classifiers)
    
    return base_classifiers, classifier_weights, classes

def predict_cmc(X: np.ndarray, base_classifiers: List[Any], classifier_weights: np.ndarray,
               classes: np.ndarray) -> np.ndarray:
    """
    Predict class labels for samples in X.
    
    Parameters:
    -----------
    X : array-like of shape (n_samples, n_features)
        Samples
    base_classifiers : List[Any]
        List of trained base classifier instances
    classifier_weights : array-like of shape (n_classifiers,)
        Weights for each classifier
    classes : array-like
        Unique class labels
        
    Returns:
    --------
    y_pred : array-like of shape (n_samples,)
        Predicted class labels
    """
    X = check_array(X)
    
    # Get predictions from all classifiers
    predictions = np.array([clf.predict(X) for clf in base_classifiers])
    
    # Weighted voting
    weighted_predictions = np.zeros((X.shape[0], len(classes)))
    for i, clf_pred in enumerate(predictions):
        for j, class_label in enumerate(classes):
            weighted_predictions[:, j] += classifier_weights[i] * (clf_pred == class_label)
    
    return classes[np.argmax(weighted_predictions, axis=1)]

def predict_proba_cmc(X: np.ndarray, base_classifiers: List[Any], classifier_weights: np.ndarray,
                     classes: np.ndarray) -> np.ndarray:
    """
    Predict class probabilities for samples in X.
    
    Parameters:
    -----------
    X : array-like of shape (n_samples, n_features)
        Samples
    base_classifiers : List[Any]
        List of trained base classifier instances
    classifier_weights : array-like of shape (n_classifiers,)
        Weights for each classifier
    classes : array-like
        Unique class labels
        
    Returns:
    --------
    probabilities : array-like of shape (n_samples, n_classes)
        Class probabilities
    """
    X = check_array(X)
    
    # Get probability predictions from all classifiers
    proba_predictions = np.array([clf.predict_proba(X) for clf in base_classifiers])
    
    # Weighted average of probabilities
    weighted_proba = np.zeros((X.shape[0], len(classes)))
    for i, clf_proba in enumerate(proba_predictions):
        weighted_proba += classifier_weights[i] * clf_proba
        
    return weighted_proba

## 4. Visualization Functions
def plot_classifier_weights(classifier_weights: np.ndarray, classifier_names: List[str],
                          title: str = "Classifier Weights"):
    """
    Plot the weights assigned to each classifier.
    
    Parameters:
    -----------
    classifier_weights : array-like of shape (n_classifiers,)
        Weights for each classifier
    classifier_names : List[str]
        Names of the classifiers
    title : str, default="Classifier Weights"
        Plot title
    """
    # Create DataFrame for plotting
    weights_df = pd.DataFrame({
        'Classifier': classifier_names,
        'Weight': classifier_weights
    })
    
    # Plot bar chart
    plt.figure(figsize=(10, 6))
    sns.barplot(data=weights_df, x='Classifier', y='Weight')
    plt.title(title)
    plt.xlabel("Classifier")
    plt.ylabel("Weight")
    plt.xticks(rotation=45)
    plt.tight_layout()
    plt.savefig('algorithms/combining-techniques/cmc_classifier_weights.png')
    plt.close()

def plot_classifier_performance(y_true: np.ndarray, y_pred: np.ndarray,
                              title: str = "Classifier Performance"):
    """
    Plot confusion matrix and classification metrics.
    
    Parameters:
    -----------
    y_true : array-like of shape (n_samples,)
        True labels
    y_pred : array-like of shape (n_samples,)
        Predicted labels
    title : str, default="Classifier Performance"
        Plot title
    """
    # Calculate confusion matrix
    cm = confusion_matrix(y_true, y_pred)
    
    # Plot confusion matrix
    plt.figure(figsize=(8, 6))
    sns.heatmap(cm, annot=True, fmt="d", cmap="Blues")
    plt.title(f"{title} - Confusion Matrix")
    plt.xlabel("Predicted")
    plt.ylabel("True")
    plt.tight_layout()
    plt.savefig('algorithms/combining-techniques/cmc_confusion_matrix.png')
    plt.close()
    
    # Print classification report
    print("\nClassification Report:")
    print(classification_report(y_true, y_pred))

## 5. Example Usage
# Generate sample data
X, y = make_classification(n_samples=1000, n_features=20, n_classes=3,
                         n_informative=15, random_state=2220)

# Split data
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=2220)

# Initialize base classifiers
base_classifiers = [
    RandomForestClassifier(
        n_estimators=100,  # Number of trees in the forest
        max_depth=5,       # Maximum depth of each tree
        min_samples_split=2,  # Minimum samples required to split a node
        min_samples_leaf=1,   # Minimum samples required at leaf node
        max_features='sqrt',  # Number of features to consider for best split
        n_jobs=-1,           # Use all available cores
        random_state=2220    # For reproducibility
    ),
    GradientBoostingClassifier(
        n_estimators=100,    # Number of boosting stages
        learning_rate=0.1,   # Step size shrinkage
        max_depth=3,         # Maximum depth of each tree
        min_samples_split=2, # Minimum samples required to split a node
        min_samples_leaf=1,  # Minimum samples required at leaf node
        subsample=0.8,       # Fraction of samples used for fitting
        random_state=2220    # For reproducibility
    ),
    SVC(
        C=1.0,              # Regularization parameter
        kernel='rbf',       # Kernel type
        gamma='scale',      # Kernel coefficient
        probability=True,   # Enable probability estimates
        random_state=2220   # For reproducibility
    )
]

# Train CMC ensemble
base_classifiers, classifier_weights, classes = fit_cmc(
    X_train, y_train, base_classifiers, validation_split=0.2
)

# Make predictions
y_pred = predict_cmc(X_test, base_classifiers, classifier_weights, classes)

# Plot results
classifier_names = ["Random Forest", "Gradient Boosting", "SVM"]
plot_classifier_weights(classifier_weights, classifier_names)
plot_classifier_performance(y_test, y_pred) 