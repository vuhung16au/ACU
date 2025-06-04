# t-Distributed Stochastic Neighbor Embedding (t-SNE)

## 1. Overview
t-SNE is a nonlinear dimensionality reduction technique particularly well-suited for the visualization of high-dimensional datasets. It models each high-dimensional object by a two- or three-dimensional point in such a way that similar objects are modeled by nearby points and dissimilar objects are modeled by distant points with high probability.

### Type of Learning
- Unsupervised Learning
- Nonlinear Dimensionality Reduction
- Manifold Learning
- Visualization

### Key Characteristics
- Preserves local structure (neighbor relationships)
- Nonlinear mapping
- Probabilistic neighbor embedding
- Sensitive to perplexity and learning rate
- Unsupervised (no labels required)
- Primarily for visualization (2D/3D)

### When to Use
- Visualization of high-dimensional data
- Exploration of clusters and structure
- Preprocessing for clustering/labeling
- Manifold learning

## 2. Historical Context
- Introduced by Laurens van der Maaten and Geoffrey Hinton in 2008
- Improved upon Stochastic Neighbor Embedding (SNE)
- Widely adopted for data visualization in machine learning and bioinformatics

## 3. Technical Details

### Mathematical Foundation

#### High-Dimensional Space
For a dataset $X = \{x_1, x_2, ..., x_n\}$ in high-dimensional space, the conditional probability $p_{j|i}$ that $x_i$ would pick $x_j$ as its neighbor is:

$$
p_{j|i} = \frac{\exp(-\|x_i - x_j\|^2/2\sigma_i^2)}{\sum_{k \neq i} \exp(-\|x_i - x_k\|^2/2\sigma_i^2)}
$$

where:
- $\sigma_i$ is the variance of the Gaussian centered at $x_i$
- $\|x_i - x_j\|^2$ is the squared Euclidean distance

The joint probabilities $p_{ij}$ are symmetrized:
$$
p_{ij} = \frac{p_{j|i} + p_{i|j}}{2n}
$$

#### Low-Dimensional Space
In the low-dimensional space, the joint probabilities $q_{ij}$ are computed using a Student-t distribution:

$$
q_{ij} = \frac{(1 + \|y_i - y_j\|^2)^{-1}}{\sum_{k \neq l} (1 + \|y_k - y_l\|^2)^{-1}}
$$

where $y_i$ and $y_j$ are the low-dimensional representations of $x_i$ and $x_j$.

#### Cost Function
The algorithm minimizes the Kullback-Leibler divergence between the two distributions:

$$
C = KL(P\|Q) = \sum_{i \neq j} p_{ij} \log \frac{p_{ij}}{q_{ij}}
$$

#### Gradient Descent
The gradient of the cost function with respect to the low-dimensional points is:

$$
\frac{\partial C}{\partial y_i} = 4 \sum_j (p_{ij} - q_{ij})(y_i - y_j)(1 + \|y_i - y_j\|^2)^{-1}
$$

The update rule for gradient descent is:
$$
y_i^{(t+1)} = y_i^{(t)} + \eta \frac{\partial C}{\partial y_i} + \alpha(t)(y_i^{(t)} - y_i^{(t-1)})
$$

where:
- $\eta$ is the learning rate
- $\alpha(t)$ is the momentum at iteration $t$

#### Perplexity
The perplexity of the conditional distribution $P_i$ is:
$$
\text{Perp}(P_i) = 2^{H(P_i)}
$$

where $H(P_i)$ is the Shannon entropy:
$$
H(P_i) = -\sum_j p_{j|i} \log_2 p_{j|i}
```

#### Time Complexity Analysis
- Pairwise distance computation: $O(n^2 \times d)$
  - $n$ = number of samples
  - $d$ = number of features
- Probability matrix computation: $O(n^2)$
- Gradient computation: $O(n^2)$ per iteration
- Total complexity: $O(n^2 \times d + n^2 \times i)$
  - $i$ = number of iterations

#### Space Complexity Analysis
- Distance matrix: $O(n^2)$
- Probability matrices: $O(n^2)$
- Gradient matrix: $O(n \times d')$
  - $d'$ = target dimensionality
- Total space complexity: $O(n^2)$

### Key Parameters
- Perplexity (controls balance between local and global aspects)
- Learning rate
- Number of iterations
- Number of components (usually 2 or 3)
- Initialization (random or PCA)

#### Algorithm Steps
1. Compute pairwise similarities in high-dimensional space (conditional probabilities)
2. Initialize low-dimensional map (random or PCA)
3. Compute pairwise similarities in low-dimensional space (Student-t distribution)
4. Minimize KL divergence using gradient descent
5. Return low-dimensional embedding

## 4. Performance Analysis

### Time Complexity
- O(n^2) for exact t-SNE (n = samples)
- O(n log n) for Barnes-Hut t-SNE (approximate, large n)

### Space Complexity
- O(n^2) for distance and probability matrices

### Computational Requirements
- Computationally intensive for large datasets
- Sensitive to parameter selection
- Not suitable for very large n without approximation

## 5. Practical Applications
- Visualization of high-dimensional data
- Cluster analysis
- Single-cell genomics
- Image and text data exploration
- Anomaly detection

## 6. Advantages and Limitations

### Advantages
- Excellent for visualizing complex, high-dimensional data
- Preserves local structure
- Reveals clusters and substructure
- Widely used in exploratory data analysis

### Limitations
- Computationally expensive for large datasets
- Sensitive to perplexity and learning rate
- Not suitable for feature extraction or downstream modeling
- No direct mapping for new data (non-parametric)
- Results can vary between runs

## 7. Implementation Guidelines

### Prerequisites
- NumPy
- Scikit-learn
- Matplotlib
- Seaborn

### Data Requirements
- Continuous, numerical features
- Standardization recommended
- No missing values

### Best Practices
- Standardize features
- Tune perplexity and learning rate
- Run multiple times to check stability
- Use for visualization, not feature extraction
- Compare with other manifold learning methods

## 8. Python Implementation
See `tSNE.py` for complete implementation. 