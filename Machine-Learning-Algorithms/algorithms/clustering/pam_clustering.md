# Partitioning Around Medoids (PAM) / K-medoids Clustering

## 1. Overview
PAM (Partitioning Around Medoids) is a clustering algorithm that uses actual data points (medoids) as cluster centers, making it more robust to outliers than K-means. It minimizes the sum of dissimilarities between points in a cluster and the point designated as the center of that cluster.

### Type of Learning
- Unsupervised Learning
- Partitional Clustering
- Distance-based Clustering

### Key Characteristics
- Uses actual data points as cluster centers (medoids)
- More robust to outliers than K-means
- Works with any distance metric
- Computationally more expensive than K-means
- Guarantees convergence

### When to Use
- When data contains outliers
- When cluster centers must be actual data points
- When using non-Euclidean distances
- For small to medium-sized datasets
- When interpretability is important

## 2. Historical Context
- Developed in 1987 by Kaufman and Rousseeuw
- Part of the PAM (Partitioning Around Medoids) algorithm
- Basis for CLARA (Clustering LARge Applications)
- Still widely used in various applications

## 3. Technical Details

### Mathematical Foundation
- Minimizes the sum of absolute distances to medoids
- Uses actual data points as cluster centers
- Iteratively improves cluster assignments

#### Algorithm Steps
1. Initialize: Randomly select k medoids
2. Assignment: Assign each point to nearest medoid
3. Update: For each cluster
   - Consider each point as potential medoid
   - Calculate total cost of swapping
   - If cost decreases, make the swap
4. Repeat steps 2-3 until convergence

#### Key Parameters
- Number of clusters (k)
- Distance metric
- Maximum iterations
- Initial medoid selection method

## 4. Performance Analysis

### Time Complexity
- O(k(n-k)²) per iteration
- Where k = number of clusters
- n = number of samples
- Memory complexity: O(n²)

### Computational Requirements
- More expensive than K-means
- Memory intensive for large datasets
- Can be optimized with CLARA for large datasets

## 5. Practical Applications
- Customer segmentation
- Document clustering
- Image segmentation
- Anomaly detection
- Market basket analysis
- Social network analysis

## 6. Advantages and Limitations

### Advantages
- More robust to outliers than K-means
- Works with any distance metric
- Interpretable results (actual data points as centers)
- Guaranteed convergence
- No assumptions about data distribution

### Limitations
- Computationally expensive
- Sensitive to initial medoid selection
- Not suitable for very large datasets
- Requires number of clusters a priori
- Can get stuck in local optima

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
- Choose appropriate distance metric
- Run multiple times with different initializations
- Use silhouette analysis for cluster validation
- Consider computational limitations

## 8. Python Implementation
See `pam_clustering.py` for complete implementation. 