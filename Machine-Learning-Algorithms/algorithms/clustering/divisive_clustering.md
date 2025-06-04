# Divisive Clustering (DIANA)

## Overview
Divisive Clustering, specifically DIANA (DIvisive ANAlysis), is a hierarchical clustering method that works in a top-down manner. Unlike agglomerative clustering, it starts with all data points in one cluster and recursively splits clusters until the desired number of clusters is reached.

## DIANA Algorithm

### Description
DIANA (DIvisive ANAlysis) is a divisive hierarchical clustering algorithm that:
1. Starts with all objects in one cluster
2. Iteratively splits the cluster with the largest diameter
3. Continues until the desired number of clusters is reached

### Algorithm Steps
1. Start with all objects in one cluster
2. For each cluster:
   - Find the object with the largest average dissimilarity to other objects
   - Move this object to a new cluster
   - Move objects that are more similar to the new cluster than to the original cluster
3. Repeat step 2 until the desired number of clusters is reached

### Mathematical Formulation
For a cluster C, the diameter is defined as:
```
diameter(C) = max{d(x,y) | x,y ∈ C}
```
where d(x,y) is the distance between objects x and y.

## Advantages
- Produces a complete hierarchy
- Can handle non-globular clusters
- More flexible than agglomerative methods
- Better at identifying outliers

## Limitations
- Computationally expensive (O(n²))
- Sensitive to the order of data points
- Can't undo previous splits
- Memory intensive for large datasets

## Time Complexity
- O(n²) for each split
- O(k*n²) for k clusters

## Space Complexity
- O(n²) for distance matrix
- O(n) for cluster assignments

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
4. Select suitable splitting criterion
5. Determine optimal number of clusters

## Comparison with Other Methods
- More flexible than K-means
- Better at handling non-globular clusters
- More computationally expensive than agglomerative methods
- Produces hierarchical structure
- More sensitive to noise than agglomerative methods

## Implementation Considerations
1. Distance Metric Selection
   - Euclidean distance
   - Manhattan distance
   - Cosine similarity
   - Correlation distance

2. Splitting Criterion
   - Maximum diameter
   - Maximum average distance
   - Maximum variance

3. Stopping Criteria
   - Number of clusters
   - Minimum cluster size
   - Maximum diameter
   - Minimum improvement

## Example Applications
1. **Document Clustering**
   - Group similar documents
   - Identify document hierarchies
   - Topic modeling

2. **Image Segmentation**
   - Group similar pixels
   - Identify regions
   - Object detection

3. **Market Segmentation**
   - Group similar customers
   - Identify market segments
   - Customer behavior analysis

4. **Social Network Analysis**
   - Group similar users
   - Identify communities
   - Network structure analysis 