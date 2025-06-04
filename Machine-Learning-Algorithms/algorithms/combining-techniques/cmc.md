# Combination of Multiple Classifiers (CMC)

## Overview
The Combination of Multiple Classifiers (CMC) is an ensemble learning method that combines predictions from multiple base classifiers using a weighted voting scheme. The weights are determined based on each classifier's performance on a validation set.

## Key Features
- Weighted voting based on classifier performance
- Validation set for weight calculation
- Compatible with any base classifier
- Handles both classification and probability prediction

## Algorithm Details

### Training Process
1. Split the training data into training and validation sets
2. Train each base classifier on the training set
3. Evaluate each classifier on the validation set
4. Calculate weights based on validation performance
5. Normalize weights to sum to 1

### Prediction Process
1. Get predictions from all base classifiers
2. Apply weights to each classifier's predictions
3. Combine weighted predictions using voting
4. Return the class with highest weighted vote

## Implementation Details

### Parameters
- `base_classifiers`: List of base classifier instances
- `validation_split`: Proportion of training data to use for validation (default: 0.2)

### Methods
- `fit(X, y)`: Train the ensemble
- `predict(X)`: Predict class labels
- `predict_proba(X)`: Predict class probabilities

## Use Cases
- When different classifiers have varying performance on different parts of the data
- When you want to leverage the strengths of multiple classifiers
- When you need a robust ensemble that adapts to classifier performance

## Advantages
- Simple and intuitive approach
- No assumptions about classifier independence
- Can improve overall performance by combining diverse classifiers
- Easy to implement and understand

## Limitations
- Requires a validation set, reducing training data
- May not perform well if base classifiers are highly correlated
- Weights are static after training
- Sensitive to validation set selection

## Example Usage
```python
from sklearn.ensemble import RandomForestClassifier
from sklearn.svm import SVC
from sklearn.neighbors import KNeighborsClassifier
from cmc import CombinationOfMultipleClassifiers

# Create base classifiers
classifiers = [
    RandomForestClassifier(),
    SVC(probability=True),
    KNeighborsClassifier()
]

# Create and train CMC ensemble
cmc = CombinationOfMultipleClassifiers(classifiers)
cmc.fit(X_train, y_train)

# Make predictions
predictions = cmc.predict(X_test)
probabilities = cmc.predict_proba(X_test)
```

## References
1. Kittler, J., et al. (1998). On combining classifiers. IEEE Transactions on Pattern Analysis and Machine Intelligence, 20(3), 226-239.
2. Kuncheva, L. I. (2004). Combining pattern classifiers: methods and algorithms. John Wiley & Sons. 