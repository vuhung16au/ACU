# ID3 (Iterative Dichotomiser 3)

## Overview
ID3 is one of the earliest and most influential decision tree algorithms. It uses information gain as the splitting criterion and is specifically designed for categorical features. The algorithm recursively builds a decision tree by selecting the feature that provides the highest information gain at each step.

## Algorithm Details

### Time Complexity
- Training: O(n * p * log(n)), where n is the number of samples and p is the number of features
- Prediction: O(log(n))

### Space Complexity
- O(n * p) for storing the tree structure

### Key Components

1. **Node Structure**
   - Feature name
   - Dictionary of child nodes
   - Value (for leaf nodes)

2. **Splitting Criterion**
   - Information Gain using Entropy
   - No support for continuous features
   - No support for missing values

3. **Stopping Criteria**
   - Maximum tree depth
   - Pure node (all samples belong to same class)
   - No more features to split on

### Algorithm Steps

1. **Initialization**
   - Start with the root node containing all training data
   - Set maximum depth

2. **Recursive Splitting**
   - For each node:
     - Calculate information gain for each feature
     - Select feature with highest gain
     - Create child nodes for each feature value
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

2. **`_calculate_information_gain(X, y, feature)`**
   - Computes information gain for a feature
   - Compares parent and child node entropies

3. **`_best_feature(X, y, features)`**
   - Finds optimal feature to split on
   - Maximizes information gain

4. **`_build_tree(X, y, features, depth)`**
   - Recursively constructs decision tree
   - Implements stopping criteria
   - Creates child nodes

### Usage Example

```python
from id3 import ID3

# Initialize model
id3 = ID3(max_depth=5)

# Define feature names
feature_names = ['outlook', 'temperature', 'humidity', 'wind']

# Train model
id3.fit(X_train, y_train, feature_names)

# Make predictions
predictions = id3.predict(X_test)

# Calculate accuracy
accuracy = id3.score(X_test, y_test)
```

## Advantages

1. **Simplicity**
   - Easy to understand and implement
   - Clear decision rules

2. **Efficiency**
   - Fast training and prediction
   - Works well with categorical data

3. **Interpretability**
   - Produces human-readable rules
   - Easy to visualize

## Limitations

1. **Feature Types**
   - Only works with categorical features
   - Cannot handle continuous features
   - No support for missing values

2. **Overfitting**
   - Can create overly complex trees
   - Requires pruning or depth limits

3. **Bias**
   - Tends to favor features with more levels
   - May create imbalanced trees

## Best Practices

1. **Data Preparation**
   - Convert continuous features to categorical
   - Handle missing values before training
   - Ensure balanced class distribution

2. **Hyperparameter Tuning**
   - Adjust max_depth
   - Consider pruning techniques
   - Monitor tree complexity

3. **Validation**
   - Use cross-validation
   - Check for overfitting
   - Evaluate feature importance

## Applications

1. **Classification**
   - Medical diagnosis
   - Credit scoring
   - Customer segmentation

2. **Rule Generation**
   - Business rules
   - Expert systems
   - Decision support

3. **Feature Selection**
   - Identify important features
   - Understand relationships
   - Reduce dimensionality

## Comparison with Other Algorithms

1. **vs C4.5**
   - C4.5 handles continuous features
   - C4.5 supports missing values
   - C4.5 uses gain ratio instead of information gain

2. **vs CART**
   - CART handles both classification and regression
   - CART uses Gini impurity
   - CART creates binary trees

3. **vs Random Forest**
   - Random Forest is an ensemble method
   - Random Forest reduces overfitting
   - Random Forest is more robust 