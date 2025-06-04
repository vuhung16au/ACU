# Hierarchical Clustering

## 1. Overview
Hierarchical Clustering is a family of clustering algorithms that build a hierarchy of clusters either by successively merging smaller clusters (agglomerative) or by successively splitting larger clusters (divisive). The result is often visualized as a dendrogram, which shows the nested grouping of patterns and the distances at which clusters are merged or split.

### Type of Learning
- Unsupervised Learning
- Hierarchical Clustering

### Key Characteristics
- Builds a tree-like hierarchy of clusters
- Agglomerative (bottom-up) and divisive (top-down) approaches
- Does not require the number of clusters in advance (can cut dendrogram at any level)
- Supports various linkage criteria (single, complete, average, ward)
- Produces a dendrogram for visualization
- Can handle non-globular clusters

### When to Use
- Data with hierarchical structure
- Exploratory data analysis
- Gene expression analysis
- Document or text clustering
- When the number of clusters is unknown

## 2. Historical Context
- Early work in numerical taxonomy (Sokal & Sneath, 1960s)
- Agglomerative methods widely used in biology, linguistics, and social sciences
- Divisive methods less common but useful for large datasets
- Foundation for advanced hierarchical and hybrid clustering methods

## 3. Technical Details

### Mathematical Foundation

#### Distance Metrics
- **Euclidean Distance:**
  $$
  d(x, y) = \sqrt{\sum_{i=1}^d (x_i - y_i)^2}
  $$

- **Manhattan Distance:**
  $$
  d(x, y) = \sum_{i=1}^d |x_i - y_i|
  $$

- **Cosine Distance:**
  $$
  d(x, y) = 1 - \frac{x \cdot y}{\|x\| \|y\|}
  $$

#### Linkage Criteria
- **Single Linkage:**
  $$
  d(C_1, C_2) = \min_{x \in C_1, y \in C_2} d(x, y)
  $$

- **Complete Linkage:**
  $$
  d(C_1, C_2) = \max_{x \in C_1, y \in C_2} d(x, y)
  $$

- **Average Linkage:**
  $$
  d(C_1, C_2) = \frac{1}{|C_1| |C_2|} \sum_{x \in C_1} \sum_{y \in C_2} d(x, y)
  $$

- **Ward's Method:**
  $$
  d(C_1, C_2) = \frac{|C_1| |C_2|}{|C_1| + |C_2|} \|\mu_{C_1} - \mu_{C_2}\|^2
  $$
  where $\mu_{C_i}$ is the centroid of cluster $C_i$

#### Agglomerative Algorithm
1. Initialize distance matrix $D$ where $D_{ij} = d(x_i, x_j)$
2. For each iteration $t$:
   - Find closest clusters $(C_i, C_j)$
   - Merge them into new cluster $C_k$
   - Update distance matrix:
     $$
     D_{kl} = f(D_{il}, D_{jl}, D_{ij})
     $$
     where $f$ depends on linkage method

#### Divisive Algorithm
1. Start with all points in one cluster
2. For each cluster $C$:
   - Find optimal split $(C_1, C_2)$ that maximizes:
     $$
     \Delta(C_1, C_2) = \text{within}(C) - (\text{within}(C_1) + \text{within}(C_2))
     $$
   - Split cluster if $\Delta(C_1, C_2) > \theta$

### Training Process

## 4. Performance Analysis

### Time Complexity
- **Training:**
  - Agglomerative:
    - Naive: $O(n^3)$
    - With priority queue: $O(n^2 \log n)$
    - With nearest-neighbor chain: $O(n^2)$
  - Divisive:
    - Naive: $O(2^n)$
    - With bisecting K-means: $O(n \log n \cdot k)$
  where:
  - $n$ = number of samples
  - $k$ = number of clusters

- **Prediction:**
  - $O(\log n)$ per point with tree traversal
  - $O(n)$ for all points

### Space Complexity
- **Training:**
  - Distance matrix: $O(n^2)$
  - Cluster hierarchy: $O(n)$
  - Priority queue: $O(n^2)$
  - Linkage matrix: $O(n-1 \times 4)$

- **Prediction:**
  - Cluster hierarchy: $O(n)$
  - Cluster assignments: $O(n)$

### Computational Requirements
- Memory intensive for large datasets
- Efficient tree traversal
- Parallelizable operations:
  - Distance computations
  - Cluster merging/splitting
  - Matrix updates
- Cache-friendly access patterns
- GPU acceleration possible

### Scalability Analysis
- Training time scales with:
  - Dataset size
  - Linkage method
  - Implementation strategy
  - Distance metric
- Memory usage scales with:
  - Dataset size
  - Distance matrix storage
  - Tree structure
- Parallelization efficiency:
  - Distance computation
  - Cluster operations
  - Matrix updates

## 5. Practical Applications
- Gene expression and bioinformatics
- Document and text clustering
- Market segmentation
- Image segmentation
- Social network analysis
- Exploratory data analysis

## 6. Advantages and Limitations

### Advantages
- No need to specify number of clusters in advance
- Produces interpretable dendrogram
- Can capture nested and non-globular clusters
- Flexible linkage and distance metrics
- Good for exploratory analysis

### Limitations
- Computationally expensive for large datasets
- Sensitive to noise and outliers
- Merging/splitting is irreversible (greedy)
- Choice of linkage and metric affects results
- Difficult to scale to big data

## 7. Comparison with Similar Algorithms

### vs K-Means
- **Hierarchical**: Nested clusters, slower
- **K-Means**: Flat clustering, faster
- **Use Case**: Choose based on hierarchy needs

### vs DBSCAN
- **Hierarchical**: Distance-based, nested
- **DBSCAN**: Density-based, flat
- **Use Case**: Choose based on cluster definition

### vs Agglomerative
- **Hierarchical**: General term
- **Agglomerative**: Bottom-up approach
- **Use Case**: Choose based on direction

### vs Divisive
- **Hierarchical**: General term
- **Divisive**: Top-down approach
- **Use Case**: Choose based on direction

### vs Spectral Clustering
- **Hierarchical**: Distance-based
- **Spectral**: Graph-based
- **Use Case**: Choose based on data structure

## 8. Implementation Guidelines

### Prerequisites
- NumPy
- Pandas
- Scikit-learn
- Scipy
- Matplotlib
- Seaborn

### Data Requirements
- Continuous or categorical features (with appropriate distance)
- Proper scaling recommended
- No missing values

### Best Practices
- Standardize features
- Try different linkage methods
- Visualize dendrogram
- Use cophenetic correlation to assess cluster quality
- Cut dendrogram at different levels to explore clusterings

## 9. Python Implementation
See `Hierarchical-Clustering.py` for complete implementation. 