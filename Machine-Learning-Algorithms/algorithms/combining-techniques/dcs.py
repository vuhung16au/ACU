## Dynamic Classifier Selection (DCS) Implementation
## This notebook demonstrates the implementation of Dynamic Classifier Selection,
## an ensemble method that dynamically selects the most appropriate classifier
## for each test instance based on local accuracy in the neighborhood of
## the test instance.

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
from sklearn.base import BaseEstimator, ClassifierMixin
from sklearn.utils.validation import check_X_y, check_array
from sklearn.neighbors import KNeighborsClassifier
from sklearn.metrics import confusion_matrix, classification_report
from typing import List, Any, Tuple
from sklearn.datasets import make_classification

## 2. Set Random Seed
np.random.seed(2220)

## 3. Core Functions
def get_local_accuracy(X: np.ndarray, y: np.ndarray, X_test: np.ndarray, 
                      base_classifiers: List[Any], knn: KNeighborsClassifier) -> np.ndarray:
    """
    Calculate local accuracy for each classifier in the neighborhood of test instances.
    
    Parameters:
    -----------
    X : array-like of shape (n_samples, n_features)
        Training data
    y : array-like of shape (n_samples,)
        Training labels
    X_test : array-like of shape (n_test_samples, n_features)
        Test instances
    base_classifiers : List[Any]
        List of trained base classifier instances
    knn : KNeighborsClassifier
        Trained KNN classifier for finding neighbors
        
    Returns:
    --------
    local_accuracies : array-like of shape (n_test_samples, n_classifiers)
        Local accuracy scores for each classifier
    """
    n_test = X_test.shape[0]
    n_classifiers = len(base_classifiers)
    local_accuracies = np.zeros((n_test, n_classifiers))
    
    # Get predictions for all training instances
    train_predictions = np.array([clf.predict(X) for clf in base_classifiers])
    
    # For each test instance
    for i in range(n_test):
        # Find k-nearest neighbors
        distances, indices = knn.kneighbors(X_test[i:i+1])
        
        # Calculate local accuracy for each classifier
        for j, clf_pred in enumerate(train_predictions):
            local_accuracies[i, j] = np.mean(clf_pred[indices[0]] == y[indices[0]])
            
    return local_accuracies

def fit_dcs(X: np.ndarray, y: np.ndarray, base_classifiers: List[Any], 
           k_neighbors: int = 5) -> Tuple[List[Any], KNeighborsClassifier, np.ndarray]:
    """
    Fit the DCS ensemble.
    
    Parameters:
    -----------
    X : array-like of shape (n_samples, n_features)
        Training data
    y : array-like of shape (n_samples,)
        Target values
    base_classifiers : List[Any]
        List of base classifier instances
    k_neighbors : int, default=5
        Number of neighbors to consider for local accuracy estimation
        
    Returns:
    --------
    tuple : (base_classifiers, knn, classes)
        Trained base classifiers, trained KNN classifier, and unique classes
    """
    X, y = check_X_y(X, y)
    classes = np.unique(y)
    
    # Train base classifiers
    for clf in base_classifiers:
        clf.fit(X, y)
        
    # Train KNN for finding neighbors
    knn = KNeighborsClassifier(n_neighbors=k_neighbors)
    knn.fit(X, y)
    
    return base_classifiers, knn, classes

def predict_dcs(X: np.ndarray, base_classifiers: List[Any], knn: KNeighborsClassifier,
               classes: np.ndarray) -> np.ndarray:
    """
    Predict class labels for samples in X.
    
    Parameters:
    -----------
    X : array-like of shape (n_samples, n_features)
        Samples
    base_classifiers : List[Any]
        List of trained base classifier instances
    knn : KNeighborsClassifier
        Trained KNN classifier for finding neighbors
    classes : array-like
        Unique class labels
        
    Returns:
    --------
    y_pred : array-like of shape (n_samples,)
        Predicted class labels
    """
    X = check_array(X)
    
    # Get local accuracies for each test instance
    local_accuracies = get_local_accuracy(knn._fit_X, knn._y, X, base_classifiers, knn)
    
    # Select best classifier for each test instance
    best_classifier_indices = np.argmax(local_accuracies, axis=1)
    
    # Get predictions from selected classifiers
    predictions = np.zeros(X.shape[0], dtype=int)
    for i, clf_idx in enumerate(best_classifier_indices):
        predictions[i] = base_classifiers[clf_idx].predict(X[i:i+1])[0]
        
    return predictions

def predict_proba_dcs(X: np.ndarray, base_classifiers: List[Any], knn: KNeighborsClassifier,
                     classes: np.ndarray) -> np.ndarray:
    """
    Predict class probabilities for samples in X.
    
    Parameters:
    -----------
    X : array-like of shape (n_samples, n_features)
        Samples
    base_classifiers : List[Any]
        List of trained base classifier instances
    knn : KNeighborsClassifier
        Trained KNN classifier for finding neighbors
    classes : array-like
        Unique class labels
        
    Returns:
    --------
    probabilities : array-like of shape (n_samples, n_classes)
        Class probabilities
    """
    X = check_array(X)
    
    # Get local accuracies for each test instance
    local_accuracies = get_local_accuracy(knn._fit_X, knn._y, X, base_classifiers, knn)
    
    # Select best classifier for each test instance
    best_classifier_indices = np.argmax(local_accuracies, axis=1)
    
    # Get probability predictions from selected classifiers
    probabilities = np.zeros((X.shape[0], len(classes)))
    for i, clf_idx in enumerate(best_classifier_indices):
        probabilities[i] = base_classifiers[clf_idx].predict_proba(X[i:i+1])[0]
        
    return probabilities

## 4. Visualization Functions
def plot_local_accuracies(local_accuracies: np.ndarray, classifier_names: List[str],
                         title: str = "Local Accuracies for Each Classifier"):
    """
    Plot the local accuracies of each classifier for test instances.
    
    Parameters:
    -----------
    local_accuracies : array-like of shape (n_test_samples, n_classifiers)
        Local accuracy scores for each classifier
    classifier_names : List[str]
        Names of the classifiers
    title : str, default="Local Accuracies for Each Classifier"
        Plot title
    """
    # Create DataFrame for plotting
    acc_df = pd.DataFrame(local_accuracies, columns=classifier_names)
    
    # Plot boxplot
    plt.figure(figsize=(10, 6))
    sns.boxplot(data=acc_df)
    plt.title(title)
    plt.xlabel("Classifier")
    plt.ylabel("Local Accuracy")
    plt.xticks(rotation=45)
    plt.tight_layout()
    plt.savefig('algorithms/combining-techniques/dcs_local_accuracies.png')
    plt.close()

def plot_classifier_selection(best_classifier_indices: np.ndarray, classifier_names: List[str],
                            title: str = "Classifier Selection Distribution"):
    """
    Plot the distribution of selected classifiers.
    
    Parameters:
    -----------
    best_classifier_indices : array-like of shape (n_samples,)
        Indices of selected classifiers for each test instance
    classifier_names : List[str]
        Names of the classifiers
    title : str, default="Classifier Selection Distribution"
        Plot title
    """
    # Count selections for each classifier
    selection_counts = np.bincount(best_classifier_indices, minlength=len(classifier_names))
    
    # Create DataFrame for plotting
    selection_df = pd.DataFrame({
        'Classifier': classifier_names,
        'Count': selection_counts
    })
    
    # Plot bar chart
    plt.figure(figsize=(10, 6))
    sns.barplot(data=selection_df, x='Classifier', y='Count')
    plt.title(title)
    plt.xlabel("Classifier")
    plt.ylabel("Number of Selections")
    plt.xticks(rotation=45)
    plt.tight_layout()
    plt.savefig('algorithms/combining-techniques/dcs_classifier_selection.png')
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
    plt.savefig('algorithms/combining-techniques/dcs_confusion_matrix.png')
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

# Train DCS ensemble
base_classifiers, knn, classes = fit_dcs(
    X_train, y_train, base_classifiers, k_neighbors=5
)

# Make predictions
y_pred = predict_dcs(X_test, base_classifiers, knn, classes)

# Get local accuracies for visualization
local_accuracies = get_local_accuracy(knn._fit_X, knn._y, X_test, base_classifiers, knn)
best_classifier_indices = np.argmax(local_accuracies, axis=1)

# Plot results
classifier_names = ["Random Forest", "Gradient Boosting", "SVM"]
plot_local_accuracies(local_accuracies, classifier_names)
plot_classifier_selection(best_classifier_indices, classifier_names)
plot_classifier_performance(y_test, y_pred) 