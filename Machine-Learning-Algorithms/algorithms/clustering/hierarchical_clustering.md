# Hierarchical Clustering

## 1. Overview
Hierarchical Clustering is a method of cluster analysis that builds a hierarchy of clusters. It can be performed in two ways:
- Agglomerative (bottom-up): Each observation starts in its own cluster, and pairs of clusters are merged as one moves up the hierarchy
- Divisive (top-down): All observations start in one cluster, and splits are performed recursively as one moves down the hierarchy

### Type of Learning
- Unsupervised Learning
- Hierarchical Clustering
- Distance-based Clustering

### Key Characteristics
- Creates a tree-like structure (dendrogram)
- No need to specify number of clusters a priori
- Can handle non-spherical clusters
- Computationally intensive for large datasets
- Sensitive to noise and outliers

### When to Use
- When hierarchical relationships are important
- When number of clusters is unknown
- When data has hierarchical structure
- For small to medium-sized datasets
- When visualization of cluster hierarchy is needed

## 2. Historical Context
- Developed in the 1960s
- One of the oldest clustering methods
- Still widely used in various fields
- Basis for many modern clustering algorithms

## 3. Technical Details

### Mathematical Foundation
- Based on distance metrics between points and clusters
- Uses linkage criteria to determine cluster distances
- Builds a dendrogram showing cluster relationships

#### Linkage Methods
1. Single Linkage (Nearest Neighbor)
   - Minimum distance between clusters
   - Can lead to chaining effect
   - Good for non-elliptical shapes

2. Complete Linkage (Farthest Neighbor)
   - Maximum distance between clusters
   - More compact clusters
   - Less sensitive to noise

3. Average Linkage
   - Average distance between clusters
   - Compromise between single and complete
   - Less sensitive to outliers

4. Ward's Method
   - Minimizes within-cluster variance
   - Tends to create equal-sized clusters
   - Most popular for many applications

#### Algorithm Steps (Agglomerative)
1. Start with n clusters (one per point)
2. Compute distance matrix between clusters
3. Find closest pair of clusters
4. Merge the pair into a new cluster
5. Update distance matrix
6. Repeat steps 3-5 until one cluster remains

## 4. Performance Analysis

### Time Complexity
- O(n²) for basic implementation
- O(n² log n) for efficient implementations
- Memory complexity: O(n²)

### Computational Requirements
- Computationally expensive for large datasets
- Memory intensive due to distance matrix
- Can be optimized for specific cases

## 5. Practical Applications
- Taxonomy construction
- Document clustering
- Image segmentation
- Market segmentation
- Gene expression analysis
- Social network analysis

## 6. Advantages and Limitations

### Advantages
- No need to specify number of clusters
- Produces hierarchical structure
- Can handle non-spherical clusters
- Visualizable through dendrogram
- Works with any distance metric

### Limitations
- Computationally expensive
- Sensitive to noise and outliers
- Cannot handle very large datasets
- Results depend on linkage method
- Once a merge is made, it cannot be undone

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
- Distance metric appropriate for data

### Best Practices
- Standardize features
- Choose appropriate linkage method
- Use dendrogram to determine number of clusters
- Consider computational limitations
- Validate results with domain knowledge

## 8. Python Implementation
See `hierarchical_clustering.py` for complete implementation. 