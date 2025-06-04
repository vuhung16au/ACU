# Decision Tree Induction

## Overview
Decision Tree Induction is a fundamental algorithm for constructing decision trees from training data. It uses a top-down, greedy approach to recursively partition the feature space based on the most informative features.

## Algorithm Details

### Time Complexity
- Training: O(n * p * log(n)), where n is the number of samples and p is the number of features
- Prediction: O(log(n))

### Space Complexity
- O(n * p) for storing the tree structure

### Key Components

1. **Node Structure**
   - Feature index
   - Threshold value
   - Left and right child nodes
   - Value (for leaf nodes)

2. **Splitting Criteria**
   - Information Gain using Entropy
   - Gini Impurity (alternative)
   - Variance Reduction (for regression)

3. **Stopping Criteria**
   - Maximum tree depth
   - Minimum samples per node
   - Pure node (all samples belong to same class)
   - No more features to split on

### Algorithm Steps

1. **Initialization**
   - Start with the root node containing all training data
   - Set maximum depth and minimum samples per split

2. **Recursive Splitting**
   - For each node:
     - Calculate best split using information gain
     - Create child nodes based on split
     - Recursively apply to child nodes
     - Stop when stopping criteria met

3. **Leaf Node Assignment**
   - Assign majority class to leaf nodes
   - Store prediction value

## Implementation Details

### Key Methods

1. **`_calculate_entropy(y)`**
   - Calculates entropy of target variable
   - Used for measuring information gain

2. **`_calculate_information_gain(X, y, feature, threshold)`**
   - Computes information gain for potential split
   - Compares parent and child node entropies

3. **`_best_split(X, y)`**
   - Finds optimal feature and threshold
   - Maximizes information gain

4. **`_build_tree(X, y, depth)`**
   - Recursively constructs decision tree
   - Implements stopping criteria
   - Creates child nodes

### Usage Example

```python
from decision_tree_induction import DecisionTreeInduction

# Initialize model
dt = DecisionTreeInduction(max_depth=5, min_samples_split=2)

# Train model
dt.fit(X_train, y_train)

# Make predictions
predictions = dt.predict(X_test)

# Calculate accuracy
accuracy = dt.score(X_test, y_test)
```

## Advantages

1. **Interpretability**
   - Easy to understand and visualize
   - Clear decision rules

2. **Efficiency**
   - Fast training and prediction
   - Works well with large datasets

3. **Flexibility**
   - Handles both classification and regression
   - Works with numerical and categorical features

## Limitations

1. **Overfitting**
   - Can create overly complex trees
   - Requires pruning or depth limits

2. **Instability**
   - Small changes in data can lead to different trees
   - Sensitive to feature selection

3. **Bias**
   - Tends to favor features with more levels
   - May create imbalanced trees

## Best Practices

1. **Preprocessing**
   - Handle missing values
   - Scale numerical features
   - Encode categorical features

2. **Hyperparameter Tuning**
   - Adjust max_depth
   - Set appropriate min_samples_split
   - Consider pruning techniques

3. **Validation**
   - Use cross-validation
   - Monitor tree depth
   - Check for overfitting

## Applications

1. **Classification**
   - Medical diagnosis
   - Credit scoring
   - Customer segmentation

2. **Regression**
   - Price prediction
   - Demand forecasting
   - Risk assessment

3. **Feature Selection**
   - Identify important features
   - Reduce dimensionality
   - Understand relationships 