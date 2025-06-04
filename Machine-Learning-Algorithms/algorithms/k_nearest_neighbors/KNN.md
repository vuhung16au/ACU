# k-Nearest Neighbors (KNN)

## 1. Overview
k-Nearest Neighbors (KNN) is a simple yet powerful supervised learning algorithm used for both classification and regression tasks. It makes predictions based on the k closest training examples in the feature space, following the principle that similar things exist in close proximity.

### Type of Learning
- Supervised Learning
- Instance-based Learning
- Lazy Learning (no explicit training phase)

### Key Characteristics
- Non-parametric algorithm
- No assumptions about data distribution
- Simple to understand and implement
- Versatile (classification and regression)
- Memory-based learning

### When to Use
- Small to medium-sized datasets
- When data is not linearly separable
- When interpretability is important
- When feature relationships are complex
- When data is not noisy
- When computational resources are available

## 2. Historical Context
- One of the oldest pattern recognition algorithms
- First described in 1951 by Evelyn Fix and Joseph Hodges
- Gained popularity in the 1960s
- Became fundamental in machine learning
- Still widely used in various applications

## 3. Technical Details

### Mathematical Foundation

K-Nearest Neighbors is based on the principle of local approximation and distance-based similarity:

#### Distance Metrics
- **Euclidean Distance:**
  $$
  d(x, y) = \sqrt{\sum_{j=1}^d (x_j - y_j)^2}
  $$

- **Manhattan Distance:**
  $$
  d(x, y) = \sum_{j=1}^d |x_j - y_j|
  $$

- **Minkowski Distance:**
  $$
  d(x, y) = \left(\sum_{j=1}^d |x_j - y_j|^p\right)^{1/p}
  $$
  where $p \geq 1$ is the order parameter.

- **Cosine Similarity:**
  $$
  \text{sim}(x, y) = \frac{x \cdot y}{\|x\| \|y\|}
  $$
  $$
  d(x, y) = 1 - \text{sim}(x, y)
  $$

#### Weighted KNN
For a query point $x$, the weighted prediction is:
- **Classification:**
  $$
  \hat{y} = \arg\max_c \sum_{i=1}^k w_i \mathbb{I}(y_i = c)
  $$
- **Regression:**
  $$
  \hat{y} = \frac{\sum_{i=1}^k w_i y_i}{\sum_{i=1}^k w_i}
  $$
where weights $w_i$ can be:
- **Uniform:** $w_i = 1$
- **Distance-based:** $w_i = \frac{1}{d(x, x_i)}$
- **Gaussian:** $w_i = \exp(-\frac{d(x, x_i)^2}{2\sigma^2})$

#### KD-Tree Construction
For a dataset $X \in \mathbb{R}^{n \times d}$:
1. Choose dimension with maximum variance
2. Find median value
3. Split data into left and right subtrees
4. Recursively build subtrees

#### Ball-Tree Construction
For a dataset $X \in \mathbb{R}^{n \times d}$:
1. Find centroid $c = \frac{1}{n}\sum_{i=1}^n x_i$
2. Find radius $r = \max_i \|x_i - c\|$
3. Split data into two clusters
4. Recursively build subtrees

### Training Process
1. Store all training data
2. No explicit training phase
3. Compute distances during prediction
4. Find k nearest neighbors
5. Make prediction based on neighbors

### Key Parameters
- Number of neighbors (k)
- Distance metric
- Weight function
- Algorithm for finding neighbors
- Leaf size (for tree-based search)

## 4. Performance Analysis

### Time Complexity
- **Training:**
  - Basic KNN: $O(1)$ (just storing data)
  - KD-tree construction: $O(n \log n \cdot d)$
  - Ball-tree construction: $O(n \log n \cdot d)$

- **Prediction:**
  - Basic KNN: $O(n \cdot d)$ per query
  - KD-tree search: $O(\log n \cdot d)$ per query (for low dimensions)
  - Ball-tree search: $O(\log n \cdot d)$ per query
  where:
  - $n$ = number of training samples
  - $d$ = number of features

### Space Complexity
- **Training:**
  - Basic KNN: $O(n \cdot d)$
  - KD-tree: $O(n \cdot d)$
  - Ball-tree: $O(n \cdot d)$

- **Prediction:**
  - Basic KNN: $O(k \cdot d)$ for storing neighbors
  - KD-tree: $O(\log n \cdot d)$ for search path
  - Ball-tree: $O(\log n \cdot d)$ for search path

### Computational Requirements
- Memory efficient with tree structures
- Fast prediction with optimized search
- Benefits from dimensionality reduction
- Parallel processing capability
- Cache-friendly access patterns
- GPU acceleration possible

### Scalability Analysis
- Training time scales with:
  - Dataset size
  - Feature dimensionality
  - Tree construction method
- Memory usage scales with:
  - Dataset size
  - Feature dimensionality
  - Tree structure
- Parallelization efficiency:
  - Query-level parallelism
  - Distance computation
  - Tree traversal

## 5. Practical Applications
- Image classification
- Pattern recognition
- Recommendation systems
- Anomaly detection
- Text categorization
- Medical diagnosis
- Credit scoring

## 6. Advantages and Limitations

### Advantages
- Simple to understand and implement
- No training phase
- Adapts to new data easily
- Works well with non-linear data
- No assumptions about data distribution

### Limitations
- Computationally expensive for large datasets
- Sensitive to feature scaling
- Memory intensive
- Sensitive to irrelevant features
- Requires careful k selection

## 7. Comparison with Similar Algorithms

### vs SVM
- **KNN**: Instance-based, local learning
- **SVM**: Model-based, global learning
- **Use Case**: Choose based on data size and complexity

### vs Decision Trees
- **KNN**: Non-parametric, distance-based
- **Decision Trees**: Parametric, rule-based
- **Use Case**: Choose based on interpretability needs

### vs Logistic Regression
- **KNN**: Non-linear, instance-based
- **Logistic Regression**: Linear, model-based
- **Use Case**: Choose based on data linearity

### vs Random Forests
- **KNN**: Single model, local
- **Random Forests**: Ensemble, global
- **Use Case**: Choose based on data size

### vs Neural Networks
- **KNN**: Simple, interpretable
- **Neural Networks**: Complex, black-box
- **Use Case**: Choose based on complexity needs

## 8. Implementation Guidelines

### Prerequisites
- NumPy
- Pandas
- Scikit-learn
- Matplotlib (for visualization)

### Data Requirements
- Numerical features
- Scaled features
- No missing values
- Balanced classes (for classification)
- Relevant features

### Best Practices
- Feature scaling
- Cross-validation for k selection
- Feature selection/engineering
- Distance metric selection
- Handling missing values
- Proper evaluation metrics

## 9. Python Implementation
See `KNN.py` for complete implementation. 