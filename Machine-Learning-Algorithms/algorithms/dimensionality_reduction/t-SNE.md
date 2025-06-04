# t-SNE (t-Distributed Stochastic Neighbor Embedding)

### Mathematical Foundation

#### High-Dimensional Space
1. **Pairwise Similarities:**
   For points $x_i$ and $x_j$ in high-dimensional space:
   $$
   p_{j|i} = \frac{\exp(-\|x_i - x_j\|^2/2\sigma_i^2)}{\sum_{k \neq i} \exp(-\|x_i - x_k\|^2/2\sigma_i^2)}
   $$
   where:
   - $\sigma_i$ = bandwidth parameter for point $i$
   - $\|x_i - x_j\|$ = Euclidean distance

2. **Joint Probabilities:**
   $$
   p_{ij} = \frac{p_{j|i} + p_{i|j}}{2n}
   $$
   where $n$ = number of points

#### Low-Dimensional Space
1. **Student's t-Distribution:**
   For points $y_i$ and $y_j$ in low-dimensional space:
   $$
   q_{ij} = \frac{(1 + \|y_i - y_j\|^2)^{-1}}{\sum_{k \neq l} (1 + \|y_k - y_l\|^2)^{-1}}
   $$

2. **Kullback-Leibler Divergence:**
   The cost function to minimize:
   $$
   C = \sum_i \sum_j p_{ij} \log \frac{p_{ij}}{q_{ij}}
   $$

#### Gradient Descent
1. **Gradient Calculation:**
   $$
   \frac{\partial C}{\partial y_i} = 4 \sum_j (p_{ij} - q_{ij})(y_i - y_j)(1 + \|y_i - y_j\|^2)^{-1}
   $$

2. **Update Rule:**
   $$
   y_i^{(t+1)} = y_i^{(t)} - \eta \frac{\partial C}{\partial y_i} + \alpha(t)(y_i^{(t)} - y_i^{(t-1)})
   $$
   where:
   - $\eta$ = learning rate
   - $\alpha(t)$ = momentum at iteration $t$

#### Perplexity
The perplexity parameter controls the bandwidth $\sigma_i$:
$$
\text{Perp}(P_i) = 2^{-\sum_j p_{j|i} \log_2 p_{j|i}}
$$
where $P_i$ is the probability distribution over all points given point $i$.

### Training Process

## 4. Performance Analysis

### Time Complexity
- **Pairwise Distance Computation:**
  - $O(n^2 \cdot d)$
  where:
  - $n$ = number of samples
  - $d$ = number of features

- **Similarity Matrix:**
  - Computation: $O(n^2)$
  - Storage: $O(n^2)$

- **Gradient Descent:**
  - Per iteration: $O(n^2 \cdot k)$
  - Total: $O(n^2 \cdot k \cdot T)$
  where:
  - $k$ = number of output dimensions
  - $T$ = number of iterations

### Space Complexity
- **Training:**
  - Data matrix: $O(n \cdot d)$
  - Similarity matrix: $O(n^2)$
  - Gradient matrix: $O(n \cdot k)$
  - Momentum matrix: $O(n \cdot k)$

- **Prediction:**
  - Embedding matrix: $O(n \cdot k)$
  - Similarity matrix: $O(n^2)$

### Computational Requirements
- Memory intensive for large datasets
- Computationally expensive
- Parallelizable operations:
  - Distance computations
  - Gradient calculations
  - Matrix operations
- GPU acceleration beneficial
- Batch processing possible

### Scalability Analysis
- Training time scales with:
  - Dataset size (quadratic)
  - Feature dimensionality
  - Number of iterations
  - Output dimensions
- Memory usage scales with:
  - Dataset size (quadratic)
  - Similarity matrix size
  - Output dimensions
- Parallelization efficiency:
  - Distance computations
  - Gradient updates
  - Matrix operations

## 6. Advantages and Limitations

### Advantages
- Preserves local structure
- Handles non-linear relationships
- Good for visualization
- Can reveal clusters
- Works well with high-dimensional data

### Limitations
- Computationally expensive
- Non-deterministic
- Sensitive to perplexity
- Doesn't preserve global structure
- Can't be used for new data

## 7. Comparison with Similar Algorithms

### vs PCA
- **t-SNE**: Local structure, non-linear
- **PCA**: Global structure, linear
- **Use Case**: Choose based on structure focus

### vs UMAP
- **t-SNE**: Local structure, slower
- **UMAP**: Global and local, faster
- **Use Case**: Choose based on speed needs

### vs MDS
- **t-SNE**: Local structure, non-linear
- **MDS**: Global structure, linear
- **Use Case**: Choose based on structure focus

### vs Isomap
- **t-SNE**: Local structure, stochastic
- **Isomap**: Global structure, deterministic
- **Use Case**: Choose based on determinism

### vs LLE
- **t-SNE**: Local structure, probabilistic
- **LLE**: Local structure, deterministic
- **Use Case**: Choose based on approach

## 8. Implementation Guidelines 