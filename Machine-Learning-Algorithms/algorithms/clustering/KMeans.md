# K-Means Clustering

## 1. Overview
K-Means is a popular unsupervised clustering algorithm that partitions data into K distinct, non-overlapping clusters based on feature similarity. It iteratively assigns each data point to the nearest cluster centroid and updates centroids to minimize within-cluster variance.

### Type of Learning
- Unsupervised Learning
- Clustering

### Key Characteristics
- Partitional clustering
- Centroid-based
- Iterative refinement
- Requires number of clusters (K) as input
- Sensitive to initialization
- Fast and scalable
- Works with continuous features

### When to Use
- Data segmentation
- Market basket analysis
- Image compression
- Anomaly detection
- Preprocessing for other algorithms

## 2. Historical Context
- Introduced by Stuart Lloyd in 1957 (Lloyd's algorithm)
- Popularized by MacQueen in 1967
- Widely used in data mining and pattern recognition
- Foundation for many advanced clustering methods

## 3. Technical Details

### Mathematical Foundation

#### Objective Function
The K-Means algorithm aims to minimize the within-cluster sum of squares (WCSS):
$$
\min_{S} \sum_{i=1}^{k} \sum_{x \in S_i} \|x - \mu_i\|^2
$$
where:
- $S = \{S_1, S_2, ..., S_k\}$ is the set of clusters
- $\mu_i$ is the centroid of cluster $S_i$
- $x$ is a data point
- $\|\cdot\|$ is the Euclidean norm

#### Centroid Calculation
For each cluster $S_i$, the centroid $\mu_i$ is computed as:
$$
\mu_i = \frac{1}{|S_i|} \sum_{x \in S_i} x
$$

#### Assignment Step
Each point $x$ is assigned to the nearest centroid:
$$
S_i = \{x : \|x - \mu_i\| \leq \|x - \mu_j\|, \forall j \neq i\}
$$

#### K-Means++ Initialization
1. Choose first centroid $\mu_1$ randomly
2. For each remaining centroid $\mu_i$:
   - Compute distances $D(x)$ to nearest existing centroid
   - Choose $\mu_i$ with probability proportional to $D(x)^2$

#### Convergence
The algorithm converges when either:
1. No points change clusters:
   $$
   S_i^{(t+1)} = S_i^{(t)}, \forall i
   $$
2. Centroids stop moving:
   $$
   \|\mu_i^{(t+1)} - \mu_i^{(t)}\| < \epsilon, \forall i
   $$
   where $\epsilon$ is a small tolerance value

### Training Process
1. Initialize K centroids (randomly or using K-Means++).
2. Assign each data point to the nearest centroid.
3. Update centroids as the mean of assigned points.
4. Repeat steps 2-3 until convergence.

#### Key Parameters
- Number of clusters (K)
- Initialization method (random, K-Means++)
- Maximum iterations
- Tolerance for convergence
- Distance metric (usually Euclidean)

## 4. Performance Analysis

### Time Complexity
- **Training:**
  - Basic K-Means: $O(n \cdot k \cdot i \cdot d)$
  - K-Means++ initialization: $O(n \cdot k \cdot d)$
  where:
  - $n$ = number of samples
  - $k$ = number of clusters
  - $i$ = number of iterations
  - $d$ = number of features

- **Prediction:**
  - $O(k \cdot d)$ per point
  - $O(n \cdot k \cdot d)$ for all points

### Space Complexity
- **Training:**
  - Data points: $O(n \cdot d)$
  - Centroids: $O(k \cdot d)$
  - Cluster assignments: $O(n)$
  - Distance matrix: $O(n \cdot k)$

- **Prediction:**
  - Centroids: $O(k \cdot d)$
  - Cluster assignments: $O(n)$

### Computational Requirements
- Memory efficient for large datasets
- Fast convergence in practice
- Parallelizable operations:
  - Distance computations
  - Centroid updates
  - Point assignments
- GPU acceleration possible
- Cache-friendly access patterns

### Scalability Analysis
- Training time scales with:
  - Dataset size
  - Number of clusters
  - Feature dimensionality
  - Convergence rate
- Memory usage scales with:
  - Dataset size
  - Number of clusters
  - Feature dimensionality
- Parallelization efficiency:
  - Batch processing
  - Mini-batch updates
  - Distributed computing

## 5. Practical Applications
- Customer segmentation
- Image compression and quantization
- Document clustering
- Anomaly detection
- Pattern recognition
- Preprocessing for supervised learning

## 6. Advantages and Limitations

### Advantages
- Simple to understand and implement
- Scales well to large datasets
- Guarantees convergence
- Works well with spherical clusters
- Fast and efficient

### Limitations
- Requires number of clusters
- Sensitive to initialization
- Assumes spherical clusters
- Sensitive to outliers
- May converge to local optima

## 7. Comparison with Similar Algorithms

### vs DBSCAN
- **K-Means**: Requires k, spherical clusters
- **DBSCAN**: Density-based, arbitrary shapes
- **Use Case**: Choose based on cluster shape

### vs Hierarchical Clustering
- **K-Means**: Flat clustering, fast
- **Hierarchical**: Nested clusters, slower
- **Use Case**: Choose based on hierarchy needs

### vs Gaussian Mixture
- **K-Means**: Hard clustering, spherical
- **GMM**: Soft clustering, elliptical
- **Use Case**: Choose based on cluster shape

### vs Mean Shift
- **K-Means**: Requires k, fixed clusters
- **Mean Shift**: Automatic k, adaptive
- **Use Case**: Choose based on k knowledge

### vs Spectral Clustering
- **K-Means**: Euclidean distance
- **Spectral**: Graph-based, non-linear
- **Use Case**: Choose based on data structure

## 8. Implementation Guidelines

### Prerequisites
- NumPy
- Pandas
- Scikit-learn
- Matplotlib
- Seaborn

### Data Requirements
- Continuous features
- Proper scaling (standardization recommended)
- No missing values
- Outlier handling recommended

### Best Practices
- Use K-Means++ for initialization
- Run multiple times with different seeds
- Use the elbow method or silhouette score to select K
- Standardize features
- Analyze cluster quality
- Visualize results

## 9. Python Implementation
See `KMeans.py` for complete implementation. 