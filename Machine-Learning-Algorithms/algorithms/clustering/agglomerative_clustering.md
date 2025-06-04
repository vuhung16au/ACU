# Agglomerative Clustering Algorithms

## Overview
Agglomerative clustering is a hierarchical clustering method that builds clusters by merging smaller clusters into larger ones. This document covers various agglomerative clustering techniques.

## Types of Agglomerative Clustering

### 1. Single Link Technique (Nearest Neighbor)
- **Description**: Uses the minimum distance between any two points in different clusters
- **Advantages**:
  - Can handle non-elliptical shapes
  - Works well with elongated clusters
- **Limitations**:
  - Sensitive to noise and outliers
  - Can lead to chaining effect
- **Time Complexity**: O(n²)
- **Space Complexity**: O(n²)

### 2. Complete Link Algorithm (Farthest Neighbor)
- **Description**: Uses the maximum distance between any two points in different clusters
- **Advantages**:
  - More compact clusters
  - Less sensitive to noise
- **Limitations**:
  - Tends to break large clusters
  - Can't handle non-globular shapes well
- **Time Complexity**: O(n²)
- **Space Complexity**: O(n²)

### 3. Average Link
- **Description**: Uses the average distance between all pairs of points in different clusters
- **Advantages**:
  - More balanced approach
  - Less sensitive to outliers than single link
- **Limitations**:
  - Computationally more expensive
  - May not work well with very different sized clusters
- **Time Complexity**: O(n²)
- **Space Complexity**: O(n²)

### 4. AGNES (AGglomerative NESting)
- **Description**: A general framework for agglomerative clustering
- **Advantages**:
  - Flexible with different linkage methods
  - Produces a complete hierarchy
- **Limitations**:
  - Can't undo previous steps
  - Sensitive to the order of data points
- **Time Complexity**: O(n²)
- **Space Complexity**: O(n²)

## Implementation Details

### Distance Metrics
- Euclidean distance
- Manhattan distance
- Cosine similarity
- Correlation distance

### Linkage Methods
1. **Single Linkage**
   ```python
   d(C1, C2) = min{d(x,y) | x ∈ C1, y ∈ C2}
   ```

2. **Complete Linkage**
   ```python
   d(C1, C2) = max{d(x,y) | x ∈ C1, y ∈ C2}
   ```

3. **Average Linkage**
   ```python
   d(C1, C2) = (1/|C1|*|C2|) * Σ d(x,y) for x ∈ C1, y ∈ C2
   ```

## Use Cases
- Document clustering
- Image segmentation
- Market segmentation
- Social network analysis
- Bioinformatics

## Best Practices
1. Choose appropriate distance metric
2. Consider data preprocessing
3. Handle outliers appropriately
4. Select suitable linkage method
5. Determine optimal number of clusters

## Comparison with Other Methods
- More flexible than K-means
- Better at handling non-globular clusters
- More computationally expensive
- Produces hierarchical structure 