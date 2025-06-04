# K-Nearest Neighbors (KNN)

## Overview
K-Nearest Neighbors (KNN) is a simple, instance-based learning algorithm that classifies new instances based on the majority class of their k nearest neighbors in the feature space. It's a non-parametric method that makes predictions based on the similarity of new instances to training instances.

## Algorithm Description

### Key Concepts
1. **Instance-Based Learning**: Learning by storing training instances
2. **Distance Metric**: Measure of similarity between instances
3. **K Value**: Number of nearest neighbors to consider
4. **Voting**: Majority class among k neighbors

### Algorithm Steps
1. Store all training instances
2. Compute distance between new instance and all training instances
3. Select k nearest neighbors
4. Assign class based on majority vote
5. (Optional) Weight votes by distance

### Mathematical Formulation

#### Distance Metrics
Common distance metrics in $\mathbb{R}^n$:

1. **Euclidean Distance**:
   $$d_E(x,y) = \sqrt{\sum_{i=1}^{n}(x_i - y_i)^2}$$

2. **Manhattan Distance**:
   $$d_M(x,y) = \sum_{i=1}^{n}|x_i - y_i|$$

3. **Minkowski Distance** (generalization):
   $$d_p(x,y) = (\sum_{i=1}^{n}|x_i - y_i|^p)^{1/p}$$
   where $p \geq 1$ (p=1: Manhattan, p=2: Euclidean)

4. **Cosine Similarity**:
   $$d_C(x,y) = 1 - \frac{x \cdot y}{||x|| \cdot ||y||} = 1 - \frac{\sum_{i=1}^{n}x_i y_i}{\sqrt{\sum_{i=1}^{n}x_i^2} \cdot \sqrt{\sum_{i=1}^{n}y_i^2}}$$

#### Weighted Voting
For a test instance $x$, the predicted class $c$ is:

$$c = \arg\max_{c \in C} \sum_{i=1}^{k} w_i \cdot \mathbb{I}(y_i = c)$$

where:
- $C$ is the set of possible classes
- $w_i$ is the weight of the i-th neighbor
- $\mathbb{I}(y_i = c)$ is the indicator function
- $y_i$ is the class of the i-th neighbor

Common weighting schemes:

1. **Uniform Weights**:
   $$w_i = \frac{1}{k}$$

2. **Distance-Based Weights**:
   $$w_i = \frac{1}{d(x, x_i) + \epsilon}$$
   where $\epsilon$ is a small constant to prevent division by zero

3. **Gaussian Weights**:
   $$w_i = \exp(-\frac{d(x, x_i)^2}{2\sigma^2})$$
   where $\sigma$ is a bandwidth parameter

#### Probability Estimation
The probability of class $c$ for instance $x$:

$$P(c|x) = \frac{\sum_{i=1}^{k} w_i \cdot \mathbb{I}(y_i = c)}{\sum_{i=1}^{k} w_i}$$

## Advantages
- Simple to understand and implement
- No training phase
- Naturally handles multi-class problems
- Can be used for both classification and regression
- Adapts to new data easily

## Limitations
- Computationally expensive for large datasets
- Sensitive to irrelevant features
- Requires feature scaling
- Memory intensive
- Sensitive to k value

## Time Complexity
- Training: O(1) - just storing data
- Prediction: O(n * p) where n is samples, p is features

## Space Complexity
- O(n * p) for storing training data

## Use Cases
- Pattern recognition
- Image classification
- Recommendation systems
- Anomaly detection
- Medical diagnosis

## Best Practices
1. Choose appropriate k value
2. Scale features
3. Select relevant features
4. Choose appropriate distance metric
5. Handle missing values

## Implementation Considerations
1. **Distance Metrics**
   - Euclidean distance
   - Manhattan distance
   - Cosine similarity
   - Custom metrics

2. **Weighting Schemes**
   - Uniform weights
   - Distance-based weights
   - Custom weights

3. **Neighbor Selection**
   - Fixed k
   - Adaptive k
   - Radius-based

## Extensions and Variants
1. **Weighted KNN**
   - Distance-based weights
   - Feature importance
   - Custom weighting

2. **Adaptive KNN**
   - Variable k
   - Local k selection
   - Dynamic k

3. **Approximate KNN**
   - KD-trees
   - Ball trees
   - Locality-sensitive hashing

## Evaluation Metrics
1. **Accuracy**
   - Overall correctness
   - Simple to understand
   - May be misleading

2. **Precision**
   - True positives / (True positives + False positives)
   - Focus on positive class
   - Important when false positives are costly

3. **Recall**
   - True positives / (True positives + False negatives)
   - Focus on positive class
   - Important when false negatives are costly

4. **F1-score**
   - Harmonic mean of precision and recall
   - Balanced measure
   - Useful for imbalanced classes

5. **ROC-AUC**
   - Area under ROC curve
   - Model discrimination ability
   - Threshold-independent

## Performance Analysis

### Time Complexity

1. **Training Phase**
   - Data storage: $O(1)$
   - No actual training computation

2. **Prediction Phase**
   - Distance computation: $O(n)$ per training instance
   - Finding k nearest neighbors: $O(m \log k)$ using heap
   - Total for m training instances: $O(mn + m \log k)$
   
   For optimized implementations:
   - KD-tree: $O(\log m)$ average case
   - Ball tree: $O(\log m)$ average case
   - LSH: $O(1)$ average case

### Space Complexity

1. **Model Storage**
   - Training data: $O(mn)$
   - Additional structures:
     - KD-tree: $O(mn)$
     - Ball tree: $O(mn)$
     - LSH: $O(m)$

2. **Runtime Memory**
   - Distance computations: $O(m)$
   - Neighbor storage: $O(k)$
   - Total: $O(m + k)$

### Computational Considerations

1. **Distance Computation**
   - Euclidean: $O(n)$ per pair
   - Manhattan: $O(n)$ per pair
   - Cosine: $O(n)$ per pair
   - Minkowski: $O(n)$ per pair

2. **Neighbor Search Optimization**
   - KD-tree construction: $O(mn \log m)$
   - Ball tree construction: $O(mn \log m)$
   - LSH construction: $O(m)$

3. **Parallelization**
   - Distance computations: Embarrassingly parallel
   - Neighbor search: Can be parallelized
   - Voting: Can be parallelized

4. **Memory Management**
   - Batch processing possible
   - Streaming updates supported
   - Sparse representations for high dimensions 