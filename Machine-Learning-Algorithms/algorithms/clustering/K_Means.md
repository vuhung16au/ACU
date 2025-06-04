# K-Means Clustering

## 1. Overview
K-Means is an iterative clustering algorithm that partitions a dataset into K distinct, non-overlapping clusters. It aims to minimize the within-cluster sum of squares (WCSS) by assigning each data point to the nearest cluster centroid.

### Type of Learning
- Unsupervised Learning
- Partitional Clustering
- Distance-based Clustering

### Key Characteristics
- Centroid-based
- Iterative optimization
- Hard clustering
- Spherical clusters
- Scalable
- Simple implementation

### When to Use
- When you know the number of clusters
- When clusters are roughly spherical
- When you need fast clustering
- When you have large datasets
- When you want interpretable results
- When you need a baseline clustering method

## 2. Historical Context
- First proposed by Stuart Lloyd in 1957
- Published by James MacQueen in 1967
- One of the most widely used clustering algorithms
- Foundation for many modern clustering methods

## 3. Technical Details

### Mathematical Foundation

#### Objective Function
The algorithm minimizes the within-cluster sum of squares (WCSS):
$$
J = \sum_{i=1}^k \sum_{x \in C_i} \|x - \mu_i\|^2
$$
where:
- $k$ is the number of clusters
- $C_i$ is the set of points in cluster $i$
- $\mu_i$ is the centroid of cluster $i$
- $\|x - \mu_i\|$ is the Euclidean distance

#### Centroid Update
For each cluster $i$, the centroid is updated as:
$$
\mu_i = \frac{1}{|C_i|} \sum_{x \in C_i} x
$$

#### Assignment Step
Each point $x$ is assigned to the nearest centroid:
$$
C_i = \{x : \|x - \mu_i\| \leq \|x - \mu_j\| \text{ for all } j \neq i\}
$$

#### Convergence
The algorithm converges when either:
1. Centroids no longer move significantly:
$$
\|\mu_i^{t+1} - \mu_i^t\| < \epsilon
$$
2. Maximum iterations reached
3. No points change clusters

### Training Process
1. Initialize k centroids randomly
2. Repeat until convergence:
   - Assign points to nearest centroids
   - Update centroids
3. Return final clusters

### Key Parameters
- Number of clusters (k)
- Initialization method
- Maximum iterations
- Convergence threshold
- Distance metric
- Random seed

## 4. Performance Analysis

### Time Complexity
- **Initialization:**
  - Random: $O(k)$
  - k-means++: $O(n \times k)$
  where $n$ is number of samples

- **Each Iteration:**
  - Assignment: $O(n \times k \times d)$
  - Update: $O(n \times d)$
  where $d$ is number of features

- **Total:**
  - $O(n \times k \times d \times i)$
  where $i$ is number of iterations

### Space Complexity
- **Training:**
  - Data storage: $O(n \times d)$
  - Centroids: $O(k \times d)$
  - Cluster assignments: $O(n)$

- **Prediction:**
  - $O(k \times d)$ for storing centroids
  - $O(d)$ for new point

### Computational Requirements
- Memory efficient
- Parallelizable
- Benefits from feature scaling
- Sensitive to initialization
- Can be accelerated with mini-batches

### Scalability Analysis
- Training time scales with:
  - Dataset size
  - Number of clusters
  - Number of features
  - Number of iterations
- Memory usage scales with:
  - Dataset size
  - Number of clusters
  - Feature dimensionality

## 5. Practical Applications
- Customer segmentation
- Image compression
- Document clustering
- Market basket analysis
- Anomaly detection
- Data preprocessing
- Feature learning

## 6. Advantages and Limitations

### Advantages
- Simple to implement
- Scales well to large datasets
- Guarantees convergence
- Works well with spherical clusters
- Easy to interpret
- Fast prediction

### Limitations
- Requires number of clusters
- Sensitive to initialization
- Assumes spherical clusters
- Can converge to local optima
- Sensitive to outliers
- Not suitable for non-convex clusters

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
- Numerical features
- Sufficient data points

### Best Practices
- Feature scaling
- Multiple initializations
- Elbow method for k
- Silhouette analysis
- Proper evaluation metrics
- Cluster visualization

## 8. Python Implementation
See `K_Means.py` for complete implementation. 