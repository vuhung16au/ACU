# CURE (Clustering Using Representatives)

## 1. Overview
CURE is a hierarchical clustering algorithm that uses multiple representative points per cluster to handle non-spherical shapes. It shrinks the representative points towards the cluster center to handle outliers and can find clusters of arbitrary shapes.

### Type of Learning
- Unsupervised Learning
- Hierarchical Clustering
- Representative-based Clustering

### Key Characteristics
- Uses multiple representative points
- Can find non-spherical clusters
- Handles outliers
- Memory efficient
- Works with large datasets

### When to Use
- When clusters have non-spherical shapes
- When data contains outliers
- For large datasets
- When memory is limited
- For hierarchical clustering

## 2. Historical Context
- Developed by Guha et al. in 1998
- Extension of traditional hierarchical clustering
- Designed for large datasets
- Still used in various applications

## 3. Technical Details

### Mathematical Foundation

#### Representative Points
For a cluster $C$ with $n$ points:
1. **Selection of Representatives:**
   - Choose $c$ well-scattered points
   - Points should be far from each other
   - Points should be far from cluster center

2. **Shrinking Process:**
   For each representative point $p$:
   $$
   p_{new} = p + \alpha \cdot (\mu - p)
   $$
   where:
   - $\alpha$ = shrinking factor (0 < $\alpha$ < 1)
   - $\mu$ = cluster centroid
   - $p$ = original representative point

#### Cluster Distance
Between clusters $C_1$ and $C_2$:
$$
d(C_1, C_2) = \min_{p_1 \in R_1, p_2 \in R_2} d(p_1, p_2)
$$
where:
- $R_1, R_2$ = sets of representative points
- $d(p_1, p_2)$ = distance between points

#### Hierarchical Merging
1. **Initial Clusters:**
   - Each point is a cluster
   - Select representatives
   - Compute distances

2. **Merging Process:**
   - Find closest pair of clusters
   - Merge clusters
   - Update representatives
   - Recompute distances

#### Sampling Strategy
For dataset of size $N$:
1. **Random Sampling:**
   - Sample size $s = \min(N, \text{sample\_size})$
   - Sample points uniformly
   - Maintain density distribution

2. **Partitioning:**
   - Divide sample into $p$ partitions
   - Each partition size $\approx s/p$
   - Process partitions independently

### Training Process

#### Algorithm Steps
1. Random sampling of data
2. Partitioning of sample
3. Pre-clustering of partitions
4. Labeling of data
5. Shrinking of representatives

#### Key Parameters
- Number of representative points (c)
- Shrinking factor (α)
- Number of clusters (k)
- Sample size

## 4. Performance Analysis

### Time Complexity
- **Sampling and Partitioning:**
  - Random sampling: $O(n)$
  - Partition creation: $O(n \log n)$
  where $n$ = number of samples

- **Clustering:**
  - Basic implementation: $O(n^2 \cdot d)$
  - With optimizations: $O(n \log n \cdot d)$
  where:
  - $n$ = number of samples
  - $d$ = number of features

- **Representative Updates:**
  - Per merge: $O(c \cdot d)$
  - Total: $O(n \cdot c \cdot d)$
  where $c$ = number of representatives

### Space Complexity
- **Training:**
  - Sample data: $O(n \cdot d)$
  - Representative points: $O(k \cdot c \cdot d)$
  - Distance matrix: $O(n^2)$
  - Cluster assignments: $O(n)$
  where:
  - $k$ = number of clusters
  - $c$ = representatives per cluster

- **Prediction:**
  - Representative points: $O(k \cdot c \cdot d)$
  - Cluster assignments: $O(n)$

### Computational Requirements
- Memory efficient with sampling
- Fast distance computations
- Parallelizable operations:
  - Distance calculations
  - Representative updates
  - Cluster merging
- Cache-friendly access patterns
- Suitable for distributed computing

### Scalability Analysis
- Training time scales with:
  - Dataset size
  - Feature dimensionality
  - Number of representatives
  - Sampling ratio
- Memory usage scales with:
  - Sample size
  - Number of clusters
  - Representatives per cluster
- Parallelization efficiency:
  - Partition-level parallelism
  - Distance computation
  - Representative updates

## 5. Practical Applications
- Image segmentation
- Document clustering
- Market segmentation
- Pattern recognition
- Bioinformatics
- Social network analysis

## 6. Advantages and Limitations

### Advantages
- Can find non-spherical clusters
- Handles outliers well
- Memory efficient
- Works with large datasets
- Provides hierarchical structure

### Limitations
- Sensitive to parameter selection
- May miss some clusters
- Requires careful tuning
- Results depend on sampling
- May need refinement step

## 7. Implementation Guidelines

### Prerequisites
- NumPy
- SciPy
- Matplotlib
- Scikit-learn

### Data Requirements
- Numerical features
- Standardization recommended
- No missing values
- Appropriate distance metric

### Best Practices
- Standardize features
- Choose appropriate number of representatives
- Tune shrinking factor
- Consider sampling strategy
- Use refinement if needed

## 8. Python Implementation
See `cure.py` for complete implementation. 