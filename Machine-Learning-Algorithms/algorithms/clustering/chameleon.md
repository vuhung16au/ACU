# CHAMELEON (Hierarchical Clustering Using Dynamic Modeling)

## 1. Overview
CHAMELEON is a hierarchical clustering algorithm that uses dynamic modeling to determine the similarity between pairs of clusters. It can find clusters of arbitrary shapes and is particularly effective for data with varying densities and shapes.

### Type of Learning
- Unsupervised Learning
- Hierarchical Clustering
- Dynamic Modeling-based Clustering

### Key Characteristics
- Uses dynamic modeling for cluster similarity
- Can find clusters of arbitrary shapes
- Handles varying densities
- Two-phase clustering approach
- Graph-based partitioning

### When to Use
- When clusters have varying densities
- When clusters have arbitrary shapes
- For hierarchical clustering
- When traditional methods fail
- For complex data structures

## 2. Historical Context
- Developed by Karypis et al. in 1999
- Extension of traditional hierarchical clustering
- Designed to overcome limitations of existing methods
- Still used in various applications

## 3. Technical Details

### Mathematical Foundation

#### Graph Construction
1. **K-Nearest Neighbor Graph:**
   For each point $x_i$, connect to its $k$ nearest neighbors:
   $$
   E = \{(x_i, x_j) | x_j \in \text{KNN}(x_i)\}
   $$
   where $\text{KNN}(x_i)$ is the set of $k$ nearest neighbors of $x_i$.

2. **Edge Weight:**
   For edge $(x_i, x_j)$:
   $$
   w_{ij} = \frac{1}{d(x_i, x_j)}
   $$
   where $d(x_i, x_j)$ is the distance between points.

#### Dynamic Modeling
1. **Relative Interconnectivity:**
   Between clusters $C_i$ and $C_j$:
   $$
   RI(C_i, C_j) = \frac{|EC_{C_i,C_j}|}{\frac{1}{2}(|EC_{C_i}| + |EC_{C_j}|)}
   $$
   where:
   - $|EC_{C_i,C_j}|$ = sum of edge weights between clusters
   - $|EC_{C_i}|$ = sum of edge weights within cluster $C_i$

2. **Relative Closeness:**
   Between clusters $C_i$ and $C_j$:
   $$
   RC(C_i, C_j) = \frac{\bar{S}_{EC_{C_i,C_j}}}{\frac{|C_i|}{|C_i|+|C_j|}\bar{S}_{EC_{C_i}} + \frac{|C_j|}{|C_i|+|C_j|}\bar{S}_{EC_{C_j}}}
   $$
   where:
   - $\bar{S}_{EC_{C_i,C_j}}$ = average weight of edges between clusters
   - $\bar{S}_{EC_{C_i}}$ = average weight of edges within cluster $C_i$

#### Cluster Merging
1. **Similarity Function:**
   For clusters $C_i$ and $C_j$:
   $$
   S(C_i, C_j) = RI(C_i, C_j) \cdot RC(C_i, C_j)^\alpha
   $$
   where $\alpha$ is a parameter to control the importance of relative closeness.

2. **Merging Criteria:**
   Merge clusters if:
   $$
   S(C_i, C_j) > \tau
   $$
   where $\tau$ is the similarity threshold.

#### Graph Partitioning
1. **Initial Partitioning:**
   - Use multilevel graph partitioning
   - Minimize edge cut
   - Balance partition sizes

2. **Refinement:**
   - Iteratively improve partitions
   - Minimize objective function:
   $$
   \text{minimize } \sum_{e \in E_{cut}} w_e
   $$
   where $E_{cut}$ is the set of edges between partitions.

### Training Process

#### Algorithm Steps
1. Graph partitioning
2. Dynamic modeling
3. Hierarchical merging
4. Cluster refinement

#### Key Parameters
- K-nearest neighbors (k)
- Minimum cluster size
- Interconnectivity threshold
- Closeness threshold

## 4. Performance Analysis

### Time Complexity
- **Graph Construction:**
  - KNN computation: $O(n^2 \cdot d)$
  - Edge weight calculation: $O(n \cdot k \cdot d)$
  where:
  - $n$ = number of samples
  - $d$ = number of features
  - $k$ = number of nearest neighbors

- **Clustering:**
  - Graph partitioning: $O(n \log n)$
  - Dynamic modeling: $O(n \cdot k)$
  - Hierarchical merging: $O(n \log n)$
  - Total: $O(n^2 \cdot d + n \log n)$

### Space Complexity
- **Training:**
  - Data points: $O(n \cdot d)$
  - KNN graph: $O(n \cdot k)$
  - Edge weights: $O(n \cdot k)$
  - Cluster assignments: $O(n)$
  - Similarity matrix: $O(n^2)$

- **Prediction:**
  - KNN graph: $O(n \cdot k)$
  - Cluster assignments: $O(n)$

### Computational Requirements
- Memory intensive for large datasets
- Computationally expensive
- Parallelizable operations:
  - KNN computation
  - Graph partitioning
  - Similarity calculations
- Cache-friendly access patterns
- Suitable for medium-sized datasets

### Scalability Analysis
- Training time scales with:
  - Dataset size
  - Feature dimensionality
  - Number of neighbors
  - Graph structure
- Memory usage scales with:
  - Dataset size
  - KNN graph size
  - Similarity matrix
- Parallelization efficiency:
  - KNN computation
  - Graph operations
  - Similarity calculations

## 5. Practical Applications
- Image segmentation
- Document clustering
- Market segmentation
- Pattern recognition
- Bioinformatics
- Social network analysis

## 6. Advantages and Limitations

### Advantages
- Can find clusters of arbitrary shapes
- Handles varying densities
- More accurate than traditional methods
- Provides hierarchical structure
- Robust to noise

### Limitations
- Computationally expensive
- Memory intensive
- Sensitive to parameters
- May not scale well
- Requires careful tuning

## 7. Implementation Guidelines

### Prerequisites
- NumPy
- SciPy
- NetworkX
- Matplotlib
- Scikit-learn

### Data Requirements
- Numerical features
- Standardization recommended
- No missing values
- Appropriate distance metric

### Best Practices
- Standardize features
- Choose appropriate k value
- Tune thresholds carefully
- Consider computational resources
- Use optimization techniques

## 8. Python Implementation
See `chameleon.py` for complete implementation. 