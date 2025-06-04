# Adaptive Classifier Combination (ACC)

## Overview
Adaptive Classifier Combination (ACC) is an ensemble learning method that adaptively combines predictions from multiple base classifiers by learning optimal combination weights for different regions of the feature space. It uses clustering to divide the feature space into regions and learns region-specific weights for each classifier.

## Key Features
- Region-based weight learning
- K-means clustering for feature space partitioning
- Softmax-based region weight calculation
- Adaptive combination of classifier predictions

## Algorithm Details

### Mathematical Foundation

#### Region Definition
For a feature space $\mathcal{X} \in \mathbb{R}^d$, we partition it into $K$ regions using K-means clustering:

$$
\mathcal{X} = \bigcup_{k=1}^K R_k
$$

where each region $R_k$ is defined by its centroid $\mu_k$ and radius $r_k$.

#### Region Weight Calculation
For a test instance $x$, the weight of region $k$ is calculated using softmax:

$$
w_k(x) = \frac{\exp(-\beta \|x - \mu_k\|^2)}{\sum_{j=1}^K \exp(-\beta \|x - \mu_j\|^2)}
$$

where:
- $\beta$ is a temperature parameter
- $\|x - \mu_k\|^2$ is the squared Euclidean distance

#### Classifier Weight Learning
For each region $R_k$, we learn classifier weights $\alpha_{k,c}$ for each classifier $c$ by minimizing:

$$
\min_{\alpha_{k,c}} \sum_{i \in R_k} w_k(x_i) \sum_{c=1}^C \alpha_{k,c} \mathbb{I}(h_c(x_i) \neq y_i)
$$

subject to:
$$
\sum_{c=1}^C \alpha_{k,c} = 1, \quad \alpha_{k,c} \geq 0
$$

where:
- $h_c(x_i)$ is the prediction of classifier $c$ for instance $i$
- $y_i$ is the true label
- $\mathbb{I}$ is the indicator function

#### Final Prediction
For a test instance $x$, the combined prediction is:

$$
\hat{y}(x) = \text{argmax}_{y \in \mathcal{Y}} \sum_{k=1}^K w_k(x) \sum_{c=1}^C \alpha_{k,c} \mathbb{I}(h_c(x) = y)
$$

#### Time Complexity Analysis
- K-means clustering: $O(n \times d \times K \times i)$
  - $n$ = number of samples
  - $d$ = number of features
  - $K$ = number of regions
  - $i$ = number of iterations
- Weight learning per region: $O(n_k \times C)$
  - $n_k$ = number of samples in region $k$
  - $C$ = number of classifiers
- Prediction: $O(K \times C)$ per instance

#### Space Complexity Analysis
- Region centroids: $O(K \times d)$
- Classifier weights: $O(K \times C)$
- Base classifiers: $O(C \times M)$
  - $M$ = average memory per classifier

### Training Process
1. Train all base classifiers on the training data
2. Use K-means clustering to divide the feature space into regions
3. For each region:
   - Calculate region weights for training samples
   - Learn optimal classifier weights based on weighted accuracy
   - Normalize weights for each region

### Prediction Process
1. For each test instance:
   - Calculate weights for each region based on distance to region centers
   - Get predictions from all base classifiers
   - Combine predictions using region-specific weights
   - Return the class with highest weighted vote

## Implementation Details

### Parameters
- `base_classifiers`: List of base classifier instances
- `n_regions`: Number of regions to divide the feature space into (default: 5)

### Methods
- `fit(X, y)`: Train the ensemble
- `predict(X)`: Predict class labels
- `predict_proba(X)`: Predict class probabilities

## Use Cases
- When different classifiers perform better in different regions of the feature space
- When you want to leverage the strengths of multiple classifiers in a structured way
- When you need an adaptive ensemble that considers feature space structure
- When dealing with complex, non-uniform data distributions

## Advantages
- Adapts to feature space structure
- Can handle complex decision boundaries
- More structured than simple ensemble methods
- Can improve performance in heterogeneous data spaces
- Provides region-specific classifier combination

## Limitations
- Computationally expensive due to clustering and weight learning
- Sensitive to the number of regions
- May be affected by poor clustering results
- Requires careful tuning of region parameters

## Example Usage
```python
from sklearn.ensemble import RandomForestClassifier
from sklearn.svm import SVC
from sklearn.neighbors import KNeighborsClassifier
from acc import AdaptiveClassifierCombination

# Create base classifiers
classifiers = [
    RandomForestClassifier(),
    SVC(probability=True),
    KNeighborsClassifier()
]

# Create and train ACC ensemble
acc = AdaptiveClassifierCombination(classifiers, n_regions=5)
acc.fit(X_train, y_train)

# Make predictions
predictions = acc.predict(X_test)
probabilities = acc.predict_proba(X_test)
```

## References
1. Kuncheva, L. I., & Rodríguez, J. J. (2014). A weighted voting framework for classifiers ensembles. Knowledge and Information Systems, 38(2), 259-275.
2. Rodríguez, J. J., & Kuncheva, L. I. (2006). Combining multiple classifiers with dynamic weighted voting. In International Workshop on Multiple Classifier Systems (pp. 126-135). Springer, Berlin, Heidelberg. 