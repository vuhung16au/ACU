## Decision Tree Induction from Scratch (Functional, Jupyter-Notebook-Ready)

## 1. Import Required Libraries
import numpy as np
from collections import Counter
import seaborn as sns
import matplotlib.pyplot as plt

## 2. Decision Tree Node as Dictionary (No OOP)
def create_node(feature=None, threshold=None, left=None, right=None, value=None):
    """
    Create a node for the decision tree as a dictionary.
    Args:
        feature: Index of the feature to split on
        threshold: Threshold value for the split
        left: Left child node
        right: Right child node
        value: Value if the node is a leaf
    Returns:
        dict representing the node
    """
    return {
        'feature': feature,
        'threshold': threshold,
        'left': left,
        'right': right,
        'value': value
    }

## 3. Hyperparameters for the Decision Tree
# max_depth: Maximum depth of the tree (prevents overfitting)
# min_samples_split: Minimum number of samples required to split a node
max_depth = 5  # Maximum depth of the tree
min_samples_split = 2  # Minimum samples to split a node
root = None  # Global variable for the root node

## 4. Entropy Calculation
def _calculate_entropy(y: np.ndarray) -> float:
    """
    Calculate entropy of a target variable.
    Args:
        y: Target labels
    Returns:
        Entropy value
    """
    hist = np.bincount(y.astype(int))
    ps = hist / len(y)
    return -np.sum([p * np.log2(p) for p in ps if p > 0])

## 5. Information Gain Calculation
def _calculate_information_gain(X: np.ndarray, y: np.ndarray, feature: int, threshold: float) -> float:
    """
    Calculate information gain for a split.
    Args:
        X: Feature matrix
        y: Target labels
        feature: Feature index to split on
        threshold: Threshold value for the split
    Returns:
        Information gain value
    """
    parent_entropy = _calculate_entropy(y)
    left_mask = X[:, feature] <= threshold
    right_mask = ~left_mask
    if len(y[left_mask]) == 0 or len(y[right_mask]) == 0:
        return 0
    n = len(y)
    left_entropy = _calculate_entropy(y[left_mask])
    right_entropy = _calculate_entropy(y[right_mask])
    child_entropy = (len(y[left_mask]) / n) * left_entropy + (len(y[right_mask]) / n) * right_entropy
    return parent_entropy - child_entropy

## 6. Find the Best Split
def _best_split(X: np.ndarray, y: np.ndarray):
    """
    Find the best feature and threshold to split on.
    Args:
        X: Feature matrix
        y: Target labels
    Returns:
        Tuple of (best_feature, best_threshold)
    """
    best_gain = -1
    best_feature = None
    best_threshold = None
    n_features = X.shape[1]
    for feature in range(n_features):
        thresholds = np.unique(X[:, feature])
        for threshold in thresholds:
            gain = _calculate_information_gain(X, y, feature, threshold)
            if gain > best_gain:
                best_gain = gain
                best_feature = feature
                best_threshold = threshold
    return best_feature, best_threshold

## 7. Recursively Build the Tree
def _build_tree(X: np.ndarray, y: np.ndarray, depth: int = 0):
    """
    Recursively build the decision tree.
    Args:
        X: Feature matrix
        y: Target labels
        depth: Current depth of the tree
    Returns:
        Root node of the tree (as a dictionary)
    """
    n_samples, n_features = X.shape
    n_labels = len(np.unique(y))
    # Stopping criteria
    if (max_depth is not None and depth >= max_depth) or \
       n_samples < min_samples_split or \
       n_labels == 1:
        leaf_value = _most_common_label(y)
        return create_node(value=leaf_value)
    best_feature, best_threshold = _best_split(X, y)
    if best_feature is None:
        leaf_value = _most_common_label(y)
        return create_node(value=leaf_value)
    left_mask = X[:, best_feature] <= best_threshold
    right_mask = ~left_mask
    left_child = _build_tree(X[left_mask], y[left_mask], depth + 1)
    right_child = _build_tree(X[right_mask], y[right_mask], depth + 1)
    return create_node(best_feature, best_threshold, left_child, right_child)

## 8. Find the Most Common Label
def _most_common_label(y: np.ndarray) -> int:
    """
    Return the most common label in a set of labels.
    Args:
        y: Target labels
    Returns:
        Most common label
    """
    return Counter(y).most_common(1)[0][0]

## 9. Fit the Decision Tree
def fit(X: np.ndarray, y: np.ndarray) -> None:
    """
    Train the decision tree.
    Args:
        X: Training data
        y: Target values
    """
    global root
    root = _build_tree(X, y)

## 10. Traverse the Tree for Prediction
def _traverse_tree(x: np.ndarray, node) -> int:
    """
    Traverse the tree to make a prediction for a single sample.
    Args:
        x: Single input sample
        node: Current node (dictionary)
    Returns:
        Predicted label
    """
    if node['value'] is not None:
        return node['value']
    if x[node['feature']] <= node['threshold']:
        return _traverse_tree(x, node['left'])
    return _traverse_tree(x, node['right'])

## 11. Predict Labels for a Dataset
def predict(X: np.ndarray) -> np.ndarray:
    """
    Make predictions for input data.
    Args:
        X: Input data
    Returns:
        Predicted labels
    """
    return np.array([_traverse_tree(x, root) for x in X])

## 12. Calculate Accuracy Score
def score(X: np.ndarray, y: np.ndarray) -> float:
    """
    Calculate accuracy score.
    Args:
        X: Test data
        y: True labels
    Returns:
        Accuracy score
    """
    predictions = predict(X)
    return np.mean(predictions == y)

## 13. Example Usage: Iris Dataset

## Set random seed for reproducibility
np.random.seed(2220)

## Load Iris dataset
def load_iris_data():
    """
    Load the Iris dataset and return features and labels.
    Returns:
        X: Features
        y: Labels
        feature_names: List of feature names
    """
    from sklearn.datasets import load_iris
    iris = load_iris()
    return iris.data, iris.target, iris.feature_names

X, y, feature_names = load_iris_data()

## Split the data into training and test sets
from sklearn.model_selection import train_test_split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=2220)

## Train the decision tree
fit(X_train, y_train)

## Make predictions on the test set
preds = predict(X_test)

## Calculate and print accuracy
acc = score(X_test, y_test)
print(f"Test accuracy: {acc:.2f}")

## 14. Visualize Feature Importance with Seaborn
# Feature importance is estimated by information gain for each feature
feature_importance = np.zeros(X.shape[1])
for i in range(X.shape[1]):
    feature_importance[i] = _calculate_information_gain(X_train, y_train, i, np.mean(X_train[:, i]))

## Plot feature importance using seaborn
plt.figure(figsize=(10, 6))
sns.barplot(x=feature_names, y=feature_importance)
plt.title('Feature Importance (Information Gain)')
plt.xlabel('Feature')
plt.ylabel('Importance')
plt.xticks(rotation=45)
plt.tight_layout()
plt.savefig('algorithms/decision_trees/decision_tree_induction-feature_importance.png')
# plt.show()  # Not used, as per requirements