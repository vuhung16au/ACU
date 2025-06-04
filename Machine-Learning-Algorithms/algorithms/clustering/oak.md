# OAK (Online Adaptive Clustering)

## 1. Overview
OAK is an online clustering algorithm that adapts to changing data distributions over time. It maintains a dynamic set of clusters that can grow, merge, or split based on incoming data, making it suitable for streaming data and real-time applications.

### Type of Learning
- Unsupervised Learning
- Online Learning
- Adaptive Clustering

### Key Characteristics
- Online processing
- Adaptive clustering
- Dynamic cluster management
- Real-time updates
- Memory efficient
- Streaming support

### When to Use
- When dealing with streaming data
- When data distribution changes
- When real-time clustering is needed
- When memory is limited
- When clusters evolve over time
- When online learning is required

## 2. Technical Details

### Mathematical Foundation

OAK uses a dynamic approach to maintain and update clusters:

#### 1. Cluster Representation
Each cluster $C$ is represented by:
$$
C = (n, \mu, \Sigma, t)
$$
where:
- $n$ = number of points
- $\mu$ = mean vector
- $\Sigma$ = covariance matrix
- $t$ = last update time

#### 2. Cluster Update
For a new point $x$, the cluster update rules are:
$$
n_{new} = n + 1
$$
$$
\mu_{new} = \mu + \frac{x - \mu}{n_{new}}
$$
$$
\Sigma_{new} = \frac{n}{n_{new}}\Sigma + \frac{1}{n_{new}}(x - \mu)(x - \mu)^T
$$

#### 3. Cluster Management
Clusters are managed using:
- Distance threshold: $\theta_d$
- Time threshold: $\theta_t$
- Merge threshold: $\theta_m$
- Split threshold: $\theta_s$

### Key Parameters
- Distance threshold
- Time threshold
- Merge threshold
- Split threshold
- Memory limit
- Update frequency

## 3. Performance Analysis

### Time Complexity
- **Point Processing:** $O(d)$
- **Cluster Update:** $O(d^2)$
- **Cluster Management:** $O(k \times d^2)$
where:
- $d$ = dimensionality
- $k$ = number of clusters

### Space Complexity
- **Cluster Storage:** $O(k \times d^2)$
- **Temporary:** $O(d^2)$
- **Index Structure:** $O(k)$

### Computational Requirements
- Memory for cluster storage
- Efficient update operations
- Management overhead
- Time tracking
- Quality monitoring

## 4. Advantages and Limitations

### Advantages
- Online processing
- Adaptive clustering
- Memory efficient
- Real-time updates
- Streaming support
- Dynamic management

### Limitations
- Parameter sensitivity
- Update overhead
- Quality trade-offs
- Memory management
- Time tracking
- Split/merge decisions

## 5. Implementation Guidelines

### Prerequisites
- NumPy
- Efficient data structures
- Memory management
- Time tracking
- Quality metrics

### Data Requirements
- Clean data
- Consistent scaling
- Missing value handling
- Feature selection
- Dimensionality reduction

### Best Practices
- Parameter tuning
- Memory monitoring
- Quality assessment
- Update strategy
- Time management
- Performance optimization

## 6. Python Implementation
See `oak.py` for complete implementation. 