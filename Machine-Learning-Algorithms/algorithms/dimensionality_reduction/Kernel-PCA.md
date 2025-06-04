# Kernel Principal Component Analysis (Kernel PCA)

## 1. Overview
Kernel PCA is a nonlinear extension of Principal Component Analysis (PCA) that uses the kernel trick to project data into a higher-dimensional feature space, enabling the extraction of nonlinear principal components. It is widely used for nonlinear dimensionality reduction, feature extraction, and visualization of complex data structures.

### Type of Learning
- Unsupervised Learning
- Nonlinear Dimensionality Reduction
- Feature Extraction

### Key Characteristics
- Nonlinear transformation via kernel trick
- Captures complex, nonlinear relationships
- Supports various kernel functions (RBF, polynomial, sigmoid, linear)
- Unsupervised (no labels required)
- Sensitive to kernel parameters and scaling

### When to Use
- Data with nonlinear structure
- Visualization of complex manifolds
- Preprocessing for nonlinear algorithms
- Feature extraction for classification/clustering

## 2. Historical Context
- Introduced by Bernhard Schölkopf, Alexander Smola, and Klaus-Robert Müller in 1998
- Extended the classical PCA to nonlinear settings using kernel methods
- Foundation for many kernel-based learning algorithms

## 3. Technical Details

### Mathematical Foundation

#### Kernel Trick and Feature Space
Given input space $\mathcal{X}$ and feature space $\mathcal{F}$, the kernel function $K$ satisfies:
$$
K(x,y) = \langle \phi(x), \phi(y) \rangle_{\mathcal{F}}
$$
where $\phi: \mathcal{X} \rightarrow \mathcal{F}$ is the feature map.

#### Kernel Matrix Construction
For dataset $X = \{x_1, ..., x_n\}$, the kernel matrix $K$ is:
$$
K_{ij} = K(x_i, x_j)
$$

#### Centering in Feature Space
The centered kernel matrix $\tilde{K}$ is computed as:
$$
\tilde{K} = K - 1_n K - K 1_n + 1_n K 1_n
$$
where $1_n$ is an $n \times n$ matrix with all entries $1/n$.

#### Eigenvalue Problem
The principal components are found by solving:
$$
\tilde{K} \alpha = n \lambda \alpha
$$
where:
- $\alpha$ are the eigenvectors
- $\lambda$ are the eigenvalues
- $n$ is the number of samples

#### Projection
For a new point $x$, its projection onto the $k$-th principal component is:
$$
\langle \phi(x), v_k \rangle = \sum_{i=1}^n \alpha_{ki} K(x, x_i)
$$
where $v_k$ is the $k$-th eigenvector in feature space.

#### Kernel Functions

1. **Linear Kernel:**
   $$
   K(x,y) = \langle x, y \rangle
   ```

2. **Polynomial Kernel:**
   $$
   K(x,y) = (\gamma \langle x, y \rangle + r)^d
   ```
   where:
   - $\gamma$ is the kernel coefficient
   - $r$ is the constant term
   - $d$ is the polynomial degree

3. **RBF (Gaussian) Kernel:**
   $$
   K(x,y) = \exp(-\gamma \|x - y\|^2)
   ```
   where $\gamma$ is the kernel coefficient.

4. **Sigmoid Kernel:**
   $$
   K(x,y) = \tanh(\gamma \langle x, y \rangle + r)
   ```
   where:
   - $\gamma$ is the kernel coefficient
   - $r$ is the constant term

#### Algorithm Steps with Mathematical Formulation

1. **Kernel Matrix Computation:**
   $$
   K_{ij} = K(x_i, x_j) \text{ for } i,j = 1,...,n
   ```

2. **Centering:**
   $$
   \tilde{K} = (I - \frac{1}{n} 1_n 1_n^T) K (I - \frac{1}{n} 1_n 1_n^T)
   ```

3. **Eigendecomposition:**
   $$
   \tilde{K} \alpha_k = \lambda_k \alpha_k \text{ for } k = 1,...,n
   ```

4. **Normalization:**
   $$
   \alpha_k = \frac{\alpha_k}{\sqrt{\lambda_k}}
   ```

5. **Projection:**
   For new point $x$:
   $$
   y_k = \sum_{i=1}^n \alpha_{ki} K(x, x_i)
   ```

#### Time Complexity Analysis
- Kernel matrix computation: $O(n^2 d)$
  - $n$ = number of samples
  - $d$ = input dimension
- Centering: $O(n^2)$
- Eigendecomposition: $O(n^3)$
- Projection of new points: $O(n d)$
- Total training complexity: $O(n^2 d + n^3)$
- Total prediction complexity: $O(n d)$

#### Space Complexity Analysis
- Kernel matrix storage: $O(n^2)$
- Eigenvectors storage: $O(n k)$
  - $k$ = number of components
- Total space complexity: $O(n^2 + n k)$

## 4. Performance Analysis

### Time Complexity
- O(n^2 d) for kernel matrix computation (n = samples, d = features)
- O(n^3) for eigendecomposition

### Space Complexity
- O(n^2) for kernel matrix

### Computational Requirements
- Suitable for small to medium datasets
- Memory intensive for large n

## 5. Practical Applications
- Nonlinear data visualization
- Manifold learning
- Preprocessing for kernel-based classifiers
- Image denoising and compression
- Bioinformatics and genomics

## 6. Advantages and Limitations

### Advantages
- Captures nonlinear relationships
- Flexible with choice of kernel
- Enables visualization of complex data
- Can improve performance of downstream tasks

### Limitations
- Computationally expensive for large datasets
- Sensitive to kernel and parameter selection
- No direct explained variance (unlike linear PCA)
- Harder to interpret principal components
- Not suitable for very large n

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
- Tune kernel and parameters (e.g., gamma for RBF)
- Visualize projections
- Compare with linear PCA
- Use for exploratory analysis and feature engineering

## 8. Python Implementation
See `Kernel-PCA.py` for complete implementation. 