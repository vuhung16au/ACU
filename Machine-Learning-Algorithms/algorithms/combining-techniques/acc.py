## Adaptive Classifier Combination (ACC) Implementation
## This notebook demonstrates the implementation of Adaptive Classifier Combination,
## an ensemble method that adaptively combines predictions from multiple base classifiers
## by learning optimal combination weights for different regions of the feature space.

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
from sklearn.base import BaseEstimator, ClassifierMixin
from sklearn.utils.validation import check_X_y, check_array, check_is_fitted
from sklearn.cluster import KMeans
from typing import List, Any, Tuple
from sklearn.datasets import make_classification

## 2. Set Random Seed
np.random.seed(2220)

## 3. Core Functions
def initialize_regions(X: np.ndarray, n_regions: int) -> np.ndarray:
    """
    Initialize region centers using k-means clustering.
    
    Parameters:
    -----------
    X : array-like of shape (n_samples, n_features)
        Training data
    n_regions : int
        Number of regions to divide the feature space into
        
    Returns:
    --------
    region_centers : array-like of shape (n_regions, n_features)
        Centers of each region
    """
    kmeans = KMeans(n_clusters=n_regions, random_state=2220)
    kmeans.fit(X)
    return kmeans.cluster_centers_

def get_region_weights(X: np.ndarray, region_centers: np.ndarray) -> np.ndarray:
    """
    Calculate weights for each region based on distance to region centers.
    
    Parameters:
    -----------
    X : array-like of shape (n_samples, n_features)
        Samples
    region_centers : array-like of shape (n_regions, n_features)
        Centers of each region
        
    Returns:
    --------
    weights : array-like of shape (n_samples, n_regions)
        Region weights for each sample
    """
    n_samples = X.shape[0]
    n_regions = region_centers.shape[0]
    weights = np.zeros((n_samples, n_regions))
    
    for i in range(n_samples):
        # Calculate distances to all region centers
        distances = np.linalg.norm(region_centers - X[i], axis=1)
        # Convert distances to weights using softmax
        weights[i] = np.exp(-distances) / np.sum(np.exp(-distances))
        
    return weights

def learn_region_weights(X: np.ndarray, y: np.ndarray, base_classifiers: List[Any], 
                        region_centers: np.ndarray) -> np.ndarray:
    """
    Learn optimal classifier weights for each region.
    
    Parameters:
    -----------
    X : array-like of shape (n_samples, n_features)
        Training data
    y : array-like of shape (n_samples,)
        Training labels
    base_classifiers : List[Any]
        List of trained base classifier instances
    region_centers : array-like of shape (n_regions, n_features)
        Centers of each region
        
    Returns:
    --------
    region_weights : array-like of shape (n_regions, n_classifiers)
        Weights for each classifier in each region
    """
    n_regions = region_centers.shape[0]
    n_classifiers = len(base_classifiers)
    region_weights = np.zeros((n_regions, n_classifiers))
    
    # Get predictions from all classifiers
    predictions = np.array([clf.predict(X) for clf in base_classifiers])
    
    # For each region
    for r in range(n_regions):
        # Get region weights for all samples
        region_weights_samples = get_region_weights(X, region_centers)[:, r]
        
        # Calculate weighted accuracy for each classifier
        for c in range(n_classifiers):
            correct_predictions = (predictions[c] == y)
            weighted_accuracy = np.sum(region_weights_samples * correct_predictions) / np.sum(region_weights_samples)
            region_weights[r, c] = weighted_accuracy
            
        # Normalize weights for this region
        region_weights[r] = region_weights[r] / np.sum(region_weights[r])
    
    return region_weights

def fit_acc(X: np.ndarray, y: np.ndarray, base_classifiers: List[Any], 
           n_regions: int = 5) -> Tuple[List[Any], np.ndarray, np.ndarray, np.ndarray]:
    """
    Fit the ACC ensemble.
    
    Parameters:
    -----------
    X : array-like of shape (n_samples, n_features)
        Training data
    y : array-like of shape (n_samples,)
        Target values
    base_classifiers : List[Any]
        List of base classifier instances
    n_regions : int, default=5
        Number of regions to divide the feature space into
        
    Returns:
    --------
    tuple : (base_classifiers, region_centers, region_weights, classes)
        Trained base classifiers, region centers, region weights, and unique classes
    """
    X, y = check_X_y(X, y)
    classes = np.unique(y)
    
    # Train base classifiers
    for clf in base_classifiers:
        clf.fit(X, y)
        
    # Initialize regions
    region_centers = initialize_regions(X, n_regions)
    
    # Learn region weights
    region_weights = learn_region_weights(X, y, base_classifiers, region_centers)
    
    return base_classifiers, region_centers, region_weights, classes

def predict_acc(X: np.ndarray, base_classifiers: List[Any], region_centers: np.ndarray,
               region_weights: np.ndarray, classes: np.ndarray) -> np.ndarray:
    """
    Predict class labels for samples in X.
    
    Parameters:
    -----------
    X : array-like of shape (n_samples, n_features)
        Samples
    base_classifiers : List[Any]
        List of trained base classifier instances
    region_centers : array-like of shape (n_regions, n_features)
        Centers of each region
    region_weights : array-like of shape (n_regions, n_classifiers)
        Weights for each classifier in each region
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
    
    # Get region weights for test samples
    region_weights_samples = get_region_weights(X, region_centers)
    
    # Calculate weighted predictions
    weighted_predictions = np.zeros((X.shape[0], len(classes)))
    for i in range(X.shape[0]):
        for r in range(region_centers.shape[0]):
            for c in range(len(base_classifiers)):
                class_idx = np.where(classes == predictions[c, i])[0][0]
                weighted_predictions[i, class_idx] += (
                    region_weights_samples[i, r] * region_weights[r, c]
                )
    
    return classes[np.argmax(weighted_predictions, axis=1)]

def predict_proba_acc(X: np.ndarray, base_classifiers: List[Any], region_centers: np.ndarray,
                     region_weights: np.ndarray, classes: np.ndarray) -> np.ndarray:
    """
    Predict class probabilities for samples in X.
    
    Parameters:
    -----------
    X : array-like of shape (n_samples, n_features)
        Samples
    base_classifiers : List[Any]
        List of trained base classifier instances
    region_centers : array-like of shape (n_regions, n_features)
        Centers of each region
    region_weights : array-like of shape (n_regions, n_classifiers)
        Weights for each classifier in each region
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
    
    # Get region weights for test samples
    region_weights_samples = get_region_weights(X, region_centers)
    
    # Calculate weighted probabilities
    weighted_proba = np.zeros((X.shape[0], len(classes)))
    for i in range(X.shape[0]):
        for r in range(region_centers.shape[0]):
            for c in range(len(base_classifiers)):
                weighted_proba[i] += (
                    region_weights_samples[i, r] * region_weights[r, c] * proba_predictions[c, i]
                )
    
    return weighted_proba

## 4. Visualization Functions
def plot_region_weights(region_weights: np.ndarray, classifier_names: List[str], 
                       title: str = "Region Weights for Each Classifier"):
    """
    Plot the weights assigned to each classifier in different regions.
    
    Parameters:
    -----------
    region_weights : array-like of shape (n_regions, n_classifiers)
        Weights for each classifier in each region
    classifier_names : List[str]
        Names of the classifiers
    title : str, default="Region Weights for Each Classifier"
        Plot title
    """
    # Create DataFrame for plotting
    weights_df = pd.DataFrame(region_weights, 
                            columns=classifier_names,
                            index=[f"Region {i+1}" for i in range(region_weights.shape[0])])
    
    # Plot heatmap
    plt.figure(figsize=(10, 6))
    sns.heatmap(weights_df, annot=True, cmap="YlOrRd", fmt=".2f")
    plt.title(title)
    plt.tight_layout()
    plt.savefig('algorithms/combining-techniques/acc_region_weights.png')
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
    plt.savefig('algorithms/combining-techniques/acc_confusion_matrix.png')
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

# Train ACC ensemble
base_classifiers, region_centers, region_weights, classes = fit_acc(
    X_train, y_train, base_classifiers, n_regions=5
)

# Make predictions
y_pred = predict_acc(X_test, base_classifiers, region_centers, region_weights, classes)

# Plot results
classifier_names = ["Random Forest", "Gradient Boosting", "SVM"]
plot_region_weights(region_weights, classifier_names)
plot_classifier_performance(y_test, y_pred) 