# ROCK (RObust Clustering using linKs)

## 1. Overview
ROCK is a hierarchical clustering algorithm specifically designed for categorical data. It uses the concept of links between data points to measure similarity, making it particularly effective for handling categorical attributes and identifying clusters with arbitrary shapes.

### Type of Learning
- Unsupervised Learning
- Hierarchical Clustering
- Categorical Clustering

### Key Characteristics
- Link-based similarity
- Categorical data handling
- Hierarchical structure
- Noise resistance
- Arbitrary cluster shapes
- Memory efficient

### When to Use
- When dealing with categorical data
- When clusters have arbitrary shapes
- When traditional distance metrics fail
- When you need hierarchical results
- When data has many attributes
- When noise is present

## 2. Technical Details

### Mathematical Foundation

ROCK uses a link-based approach to measure similarity between points. The algorithm works in three main phases:

#### 1. Link Computation
For points $x_i$ and $x_j$, the number of common neighbors is:
$$
\text{link}(x_i, x_j) = |\{x_k | x_k \in N(x_i) \cap N(x_j)\}|
$$
where $N(x)$ is the neighborhood of point $x$.

#### 2. Goodness Measure
The goodness measure for merging clusters $C_i$ and $C_j$ is:
$$
g(C_i, C_j) = \frac{\sum_{x_i \in C_i, x_j \in C_j} \text{link}(x_i, x_j)}{(|C_i| + |C_j|)^{1+2f(\theta)} - |C_i|^{1+2f(\theta)} - |C_j|^{1+2f(\theta)}}
$$
where $f(\theta)$ is a function of the similarity threshold $\theta$.

#### 3. Cluster Merging
Clusters are merged based on the goodness measure until the desired number of clusters is reached.

### Key Parameters
- Similarity threshold
- Number of clusters
- Neighborhood size
- Link threshold
- Merge criteria
- Stopping condition

## 3. Performance Analysis

### Time Complexity
- **Link Computation:** $O(n^2 \times m)$
- **Goodness Calculation:** $O(n^2)$
- **Cluster Merging:** $O(n \log n)$
where:
- $n$ = number of points
- $m$ = number of attributes

### Space Complexity
- **Link Storage:** $O(n^2)$
- **Cluster Storage:** $O(n)$
- **Temporary:** $O(n)$

### Computational Requirements
- Memory for link matrix
- Efficient set operations
- Neighborhood computation
- Merge operations
- Priority queue

## 4. Advantages and Limitations

### Advantages
- Handles categorical data
- Arbitrary cluster shapes
- Noise resistance
- Hierarchical structure
- Memory efficient
- Link-based similarity

### Limitations
- Parameter sensitivity
- Computational cost
- Memory requirements
- Scalability issues
- Link computation overhead
- Merge criteria selection

## 5. Implementation Guidelines

### Prerequisites
- NumPy
- Efficient set operations
- Priority queue
- Sparse matrix support

### Data Requirements
- Categorical attributes
- Clean data
- Consistent encoding
- Missing value handling
- Attribute selection

### Best Practices
- Parameter tuning
- Memory management
- Link optimization
- Merge strategy
- Result validation
- Performance monitoring

## 6. Python Implementation
See `rock.py` for complete implementation. 