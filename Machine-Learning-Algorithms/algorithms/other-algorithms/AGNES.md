# AGNES (AGglomerative NESting)

## Overview
AGNES is a hierarchical clustering algorithm that uses a bottom-up approach to build a cluster hierarchy. It starts with individual data points and progressively merges the closest clusters until a stopping criterion is met.

## Key Features
- Bottom-up hierarchical clustering
- Creates a dendrogram
- Handles various distance metrics
- No need to specify number of clusters
- Can handle different data types

## Algorithm Steps
1. Initialization:
   - Each data point is a cluster
   - Compute distance matrix
2. Iterative Merging:
   - Find closest pair of clusters
   - Merge the pair
   - Update distance matrix
3. Stopping:
   - Continue until one cluster remains
   - Or until stopping criterion met

## Advantages
- No need to specify number of clusters
- Creates interpretable hierarchy
- Handles different data types
- Robust to noise
- Provides cluster relationships

## Limitations
- Computationally expensive
- Sensitive to noise
- Cannot handle large datasets
- Memory intensive
- Cannot undo merges

## Use Cases
- Document clustering
- Image segmentation
- Market segmentation
- Taxonomy creation
- Pattern recognition

## Implementation Considerations
- Distance metric choice
- Linkage method
- Memory management
- Stopping criteria
- Cluster visualization 