# PAM (Partitioning Around Medoids)

## Overview
PAM (Partitioning Around Medoids) is a clustering algorithm that is similar to K-means but uses actual data points (medoids) as cluster centers instead of means. It is more robust to outliers and can work with any distance metric.

## Algorithm Description

### Key Concepts
1. **Medoid**: An actual data point that represents the center of a cluster
2. **Cost Function**: Sum of distances from each point to its medoid
3. **Swap Operation**: Replacing a medoid with a non-medoid to improve clustering

### Algorithm Steps
1. Randomly select k objects as initial medoids
2. Assign each object to the nearest medoid
3. For each medoid m and non-medoid o:
   - Compute the cost of swapping m and o
   - If cost decreases, perform the swap
4. Repeat step 3 until no improvement

### Mathematical Formulation

#### Cost Function
For a set of clusters $C = \{C_1, C_2, ..., C_k\}$ and medoids $M = \{m_1, m_2, ..., m_k\}$, the total cost is defined as:

$$
\text{cost}(C,M) = \sum_{i=1}^k \sum_{x \in C_i} d(x, m_i)
$$

where $d(x,m_i)$ is the distance between point $x$ and medoid $m_i$.

#### Objective Function
The goal is to minimize the total cost:

$$
\min_{C,M} \sum_{i=1}^k \sum_{x \in C_i} d(x, m_i)
$$

subject to:
- Each point belongs to exactly one cluster
- Each cluster has exactly one medoid
- Medoids must be actual data points

#### Swap Operation
For a current medoid $m_i$ and a non-medoid point $o$, the cost change $\Delta$ after swapping is:

$$
\Delta = \sum_{x \in C_i} \min(d(x,o), d(x,m_j)) - \sum_{x \in C_i} d(x,m_i)
$$

where $m_j$ is the second closest medoid to point $x$.

#### Algorithm Convergence
The algorithm converges when:
$$
\Delta \geq 0 \quad \forall m_i \in M, \forall o \notin M
$$

#### Time Complexity Analysis
For each iteration:
- Computing distances: $O(n \times d)$
- Finding nearest medoids: $O(n \times k)$
- Evaluating swaps: $O(k \times (n-k) \times n)$

Total complexity: $O(k(n-k)^2 \times i)$
where:
- $k$ = number of clusters
- $n$ = number of objects
- $d$ = number of features
- $i$ = number of iterations

#### Space Complexity Analysis
- Distance matrix: $O(n^2)$
- Cluster assignments: $O(n)$
- Medoid indices: $O(k)$
- Temporary storage: $O(n)$

Total space complexity: $O(n^2)$

## Variants

### 1. CLARA (Clustering LARge Applications)
- Uses sampling to handle large datasets
- Applies PAM to multiple samples
- Returns best clustering found
- More efficient for large datasets
- May miss optimal solution

### 2. CLARANS (Clustering Large Applications based on RANdomized Search)
- Combines PAM and CLARA
- Uses randomized search
- More efficient than PAM
- Better quality than CLARA
- Can handle large datasets

## Advantages
- More robust to outliers than K-means
- Works with any distance metric
- Produces interpretable centers
- Can handle categorical data
- No assumptions about data distribution

## Limitations
- Computationally expensive
- Sensitive to initial medoid selection
- May converge to local optima
- Memory intensive for large datasets
- Requires number of clusters (k)

## Time Complexity
- PAM: O(k(n-k)²*i)
  - k: number of clusters
  - n: number of objects
  - i: number of iterations
- CLARA: O(ks² + k(n-k))
  - s: sample size
- CLARANS: O(n²)

## Space Complexity
- PAM: O(n²)
- CLARA: O(s²)
- CLARANS: O(n)

## Use Cases
- Customer segmentation
- Document clustering
- Image segmentation
- Market basket analysis
- Bioinformatics

## Best Practices
1. Choose appropriate k value
2. Select suitable distance metric
3. Handle outliers appropriately
4. Use multiple initializations
5. Consider data preprocessing

## Comparison with Other Methods
- More robust than K-means
- More flexible than hierarchical clustering
- More interpretable than some methods
- Better handling of outliers
- More computationally expensive

## Implementation Considerations
1. **Distance Metrics**
   - Euclidean distance
   - Manhattan distance
   - Cosine similarity
   - Custom metrics

2. **Initialization Methods**
   - Random selection
   - K-means++ like
   - Farthest-first
   - Build phase

3. **Optimization Strategies**
   - Greedy search
   - Local search
   - Random search
   - Hybrid approaches

## Example Applications
1. **Customer Segmentation**
   - Group similar customers
   - Identify customer profiles
   - Target marketing

2. **Document Clustering**
   - Group similar documents
   - Topic modeling
   - Text mining

3. **Image Processing**
   - Group similar pixels
   - Image segmentation
   - Object detection

4. **Market Analysis**
   - Group similar products
   - Identify patterns
   - Cross-selling

## Extensions and Variants
1. **Fuzzy PAM**
   - Soft clustering
   - Membership degrees
   - More flexible assignments

2. **Weighted PAM**
   - Feature weighting
   - Importance scores
   - Custom distances

3. **Incremental PAM**
   - Online learning
   - Dynamic updates
   - Streaming data

4. **Parallel PAM**
   - Distributed computing
   - GPU acceleration
   - MapReduce implementation 