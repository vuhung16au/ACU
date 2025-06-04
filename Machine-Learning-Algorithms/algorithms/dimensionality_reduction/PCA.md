# Principal Component Analysis (PCA)

## 1. Overview
Principal Component Analysis (PCA) is a linear dimensionality reduction technique that transforms data into a new coordinate system such that the greatest variance by any projection of the data comes to lie on the first coordinate (the first principal component), the second greatest variance on the second coordinate, and so on. PCA is widely used for feature reduction, visualization, noise filtering, and data compression.

### Type of Learning
- Unsupervised Learning
- Dimensionality Reduction
- Feature Extraction

### Key Characteristics
- Linear transformation
- Orthogonal principal components
- Maximizes variance
- Reduces dimensionality
- Unsupervised (no labels required)
- Sensitive to scaling

### When to Use
- High-dimensional data
- Visualization (2D/3D projection)
- Noise reduction
- Feature selection/extraction
- Preprocessing for other algorithms

## 2. Historical Context
- Introduced by Karl Pearson in 1901
- Developed independently by Harold Hotelling in 1933
- Foundation for many modern dimensionality reduction techniques
- Widely used in statistics, machine learning, and signal processing

## 3. Technical Details

### Mathematical Foundation

#### Data Preprocessing
1. **Standardization:**
   For each feature $j$:
   $$
   x_{ij}' = \frac{x_{ij} - \mu_j}{\sigma_j}
   $$
   where:
   - $\mu_j$ = mean of feature $j$
   - $\sigma_j$ = standard deviation of feature $j$

#### Covariance Matrix
For standardized data $X$:
$$
\Sigma = \frac{1}{n-1} X^T X
$$
where:
- $n$ = number of samples
- $X$ = $n \times d$ data matrix
- $\Sigma$ = $d \times d$ covariance matrix

#### Eigendecomposition
The covariance matrix can be decomposed as:
$$
\Sigma = V \Lambda V^T
$$
where:
- $V$ = matrix of eigenvectors
- $\Lambda$ = diagonal matrix of eigenvalues
- Columns of $V$ are principal components
- Eigenvalues represent variance along each component

#### Principal Components
1. **Component Calculation:**
   The $i$-th principal component is:
   $$
   PC_i = X v_i
   $$
   where $v_i$ is the $i$-th eigenvector.

2. **Variance Explained:**
   The proportion of variance explained by the $i$-th component:
   $$
   \text{Explained Variance}_i = \frac{\lambda_i}{\sum_{j=1}^d \lambda_j}
   $$
   where $\lambda_i$ is the $i$-th eigenvalue.

#### Dimensionality Reduction
1. **Projection:**
   To reduce to $k$ dimensions:
   $$
   X_{reduced} = X V_k
   $$
   where $V_k$ contains the first $k$ eigenvectors.

2. **Reconstruction:**
   The original data can be approximated as:
   $$
   X_{approx} = X_{reduced} V_k^T
   $$

#### Optimization Objective
PCA maximizes the variance of the projected data:
$$
\max_{v} \text{Var}(Xv) = \max_{v} v^T \Sigma v
$$
subject to:
- $v^T v = 1$ (unit length)
- $v^T v_i = 0$ for all previous components $v_i$

### Training Process

#### Algorithm Steps
1. Standardize the data (zero mean, unit variance)
2. Compute the covariance matrix
3. Compute eigenvalues and eigenvectors (or use SVD)
4. Sort eigenvectors by descending eigenvalues
5. Select top k eigenvectors (principal components)
6. Project data onto the principal components

#### Key Parameters
- Number of components (k)
- Whether to whiten components
- SVD solver (for large datasets)

## 4. Performance Analysis

### Time Complexity
- **Data Preprocessing:**
  - Standardization: $O(n \cdot d)$
  where:
  - $n$ = number of samples
  - $d$ = number of features

- **Covariance Matrix:**
  - Computation: $O(n \cdot d^2)$
  - Storage: $O(d^2)$

- **Eigendecomposition:**
  - Full decomposition: $O(d^3)$
  - Truncated SVD: $O(n \cdot d \cdot k)$
  where $k$ = number of components

- **Projection:**
  - $O(n \cdot d \cdot k)$

### Space Complexity
- **Training:**
  - Data matrix: $O(n \cdot d)$
  - Covariance matrix: $O(d^2)$
  - Eigenvectors: $O(d \cdot k)$
  - Eigenvalues: $O(d)$

- **Prediction:**
  - Projection matrix: $O(d \cdot k)$
  - Mean vector: $O(d)$
  - Standard deviation vector: $O(d)$

### Computational Requirements
- Memory efficient for moderate dimensions
- Computationally intensive for high dimensions
- Parallelizable operations:
  - Covariance computation
  - Matrix multiplication
  - SVD decomposition
- Cache-friendly access patterns
- GPU acceleration possible

### Scalability Analysis
- Training time scales with:
  - Dataset size
  - Feature dimensionality
  - Number of components
  - Algorithm choice (full vs. truncated)
- Memory usage scales with:
  - Dataset size
  - Feature dimensionality
  - Covariance matrix size
- Parallelization efficiency:
  - Matrix operations
  - SVD computation
  - Data projection

## 5. Practical Applications
- Data visualization (2D/3D plots)
- Noise reduction
- Image compression
- Genomics and bioinformatics
- Finance and risk analysis
- Preprocessing for clustering/classification

## 6. Advantages and Limitations

### Advantages
- Reduces dimensionality while preserving variance
- Removes multicollinearity
- Helps visualize high-dimensional data
- Computationally efficient
- Provides interpretable components

### Limitations
- Linear transformation only
- Sensitive to scaling
- May lose important information
- Assumes orthogonal components
- Can't handle missing values

## 7. Comparison with Similar Algorithms

### vs Kernel PCA
- **PCA**: Linear transformation
- **Kernel PCA**: Non-linear transformation
- **Use Case**: Choose based on data linearity

### vs t-SNE
- **PCA**: Global structure, fast
- **t-SNE**: Local structure, slower
- **Use Case**: Choose based on structure focus

### vs UMAP
- **PCA**: Linear, preserves variance
- **UMAP**: Non-linear, preserves topology
- **Use Case**: Choose based on data structure

### vs LDA
- **PCA**: Unsupervised, variance-based
- **LDA**: Supervised, class separation
- **Use Case**: Choose based on task type

### vs ICA
- **PCA**: Orthogonal components
- **ICA**: Independent components
- **Use Case**: Choose based on component independence

## 8. Implementation Guidelines

### Prerequisites
- NumPy
- Pandas
- Scikit-learn
- Matplotlib
- Seaborn

### Data Requirements
- Continuous, numerical features
- Standardization recommended
- No missing values

### Best Practices
- Always standardize features
- Analyze explained variance ratio
- Visualize principal components
- Use scree plot to select number of components
- Interpret results with caution

## 9. Python Implementation
See `PCA.py` for complete implementation. 