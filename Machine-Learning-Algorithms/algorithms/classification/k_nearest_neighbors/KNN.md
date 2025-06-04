# K-Nearest Neighbors (KNN)

## 1. Overview
K-Nearest Neighbors is a simple yet powerful instance-based learning algorithm that makes predictions based on the majority class or average value of its k nearest neighbors in the feature space.

### Type of Learning
- Supervised Learning
- Instance-based Learning
- Classification and Regression Tasks

### Key Characteristics
- Non-parametric
- Lazy learning
- Memory-based
- Distance-based
- Simple implementation
- No training phase

### When to Use
- When you need a simple baseline model
- When you have a small to medium dataset
- When you want interpretable results
- When you need to handle both classification and regression
- When you want to avoid assumptions about data distribution

## 2. Historical Context
- One of the oldest machine learning algorithms
- First proposed by Evelyn Fix and Joseph Hodges in 1951
- Later refined by Thomas Cover and Peter Hart in 1967
- Foundation for many modern instance-based learning methods

## 3. Technical Details

### Mathematical Foundation

#### Distance Metrics
The algorithm relies on distance metrics to find nearest neighbors. Common metrics include:

1. Euclidean Distance:
$$
d(x,y) = \sqrt{\sum_{i=1}^n (x_i - y_i)^2}
$$

2. Manhattan Distance:
$$
d(x,y) = \sum_{i=1}^n |x_i - y_i|
$$

3. Minkowski Distance:
$$
d(x,y) = \left(\sum_{i=1}^n |x_i - y_i|^p\right)^{1/p}
$$

#### Classification
For a new instance $x$, the predicted class $\hat{y}$ is determined by:
$$
\hat{y} = \text{argmax}_{c \in C} \sum_{i=1}^k \mathbb{I}(y_i = c)
$$
where:
- $C$ is the set of possible classes
- $y_i$ is the class of the $i$-th nearest neighbor
- $\mathbb{I}$ is the indicator function

#### Regression
For regression tasks, the predicted value $\hat{y}$ is the average of the k nearest neighbors:
$$
\hat{y} = \frac{1}{k} \sum_{i=1}^k y_i
$$

#### Weighted KNN
We can assign weights based on distance:
$$
\hat{y} = \frac{\sum_{i=1}^k w_i y_i}{\sum_{i=1}^k w_i}
$$
where weights can be calculated as:
$$
w_i = \frac{1}{d(x, x_i)^p}
$$
for some power $p$.

### Training Process
1. Store all training instances
2. For each test instance:
   - Calculate distances to all training instances
   - Find k nearest neighbors
   - Make prediction based on neighbors

### Key Parameters
- Number of neighbors (k)
- Distance metric
- Weighting scheme
- Feature scaling method

## 4. Performance Analysis

### Time Complexity
- **Training:**
  - $O(1)$ - only storing the data
  - Space: $O(n \times d)$ where $n$ is number of samples and $d$ is number of features

- **Prediction:**
  - Distance calculation: $O(d)$ per training instance
  - Finding k nearest neighbors: $O(n \log k)$ using a max-heap
  - Total prediction time: $O(n \times d + n \log k)$
  - For $m$ test instances: $O(m \times n \times d + m \times n \log k)$

### Space Complexity
- **Training:**
  - $O(n \times d)$ for storing training data
  - $O(1)$ additional space

- **Prediction:**
  - $O(k)$ for storing k nearest neighbors
  - $O(d)$ for storing the test instance

### Computational Requirements
- Memory intensive for large datasets
- Computationally expensive during prediction
- Benefits from dimensionality reduction
- Can be optimized with data structures like k-d trees

### Scalability Analysis
- Training time is constant
- Prediction time scales linearly with:
  - Number of training instances
  - Number of features
  - Number of test instances
- Memory usage scales with:
  - Dataset size
  - Feature dimensionality

## 5. Practical Applications
- Pattern recognition
- Image classification
- Recommendation systems
- Anomaly detection
- Medical diagnosis
- Credit scoring
- Handwriting recognition

## 6. Advantages and Limitations

### Advantages
- Simple to understand and implement
- No training phase
- Naturally handles multi-class problems
- Can be used for both classification and regression
- No assumptions about data distribution
- Easy to update with new data

### Limitations
- Computationally expensive for large datasets
- Sensitive to irrelevant features
- Requires feature scaling
- Memory intensive
- Sensitive to the choice of k
- Can be affected by the curse of dimensionality

## 7. Implementation Guidelines

### Prerequisites
- NumPy
- Pandas
- Scikit-learn
- Matplotlib (for visualization)

### Data Requirements
- Clean data
- Relevant features
- Proper scaling
- No missing values
- Balanced classes (for classification)
- Sufficient training data

### Best Practices
- Cross-validation for k selection
- Feature scaling
- Feature selection
- Distance metric selection
- Weighted voting
- Proper evaluation metrics

## 8. Python Implementation
See `KNN.py` for complete implementation. 