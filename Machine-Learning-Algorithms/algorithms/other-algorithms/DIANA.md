# DIANA (DIvisive ANAlysis)

## Overview
DIANA is a hierarchical clustering algorithm that uses a top-down approach. It starts with all data points in one cluster and recursively splits clusters until a stopping criterion is met.

## Key Features
- Top-down hierarchical clustering
- Creates a dendrogram
- Handles various distance metrics
- No need to specify number of clusters
- Can handle different data types

## Algorithm Steps
1. Initialization:
   - All data points in one cluster
   - Compute distance matrix
2. Iterative Splitting:
   - Find most dissimilar point
   - Create new cluster
   - Reassign points
3. Stopping:
   - Continue until each point is a cluster
   - Or until stopping criterion met

## Advantages
- No need to specify number of clusters
- Creates interpretable hierarchy
- Handles different data types
- Can handle large datasets
- Provides cluster relationships

## Limitations
- Computationally expensive
- Sensitive to noise
- Order-dependent
- Memory intensive
- Cannot undo splits

## Use Cases
- Document clustering
- Image segmentation
- Market segmentation
- Taxonomy creation
- Pattern recognition

## Implementation Considerations
- Distance metric choice
- Splitting criterion
- Memory management
- Stopping criteria
- Cluster visualization 