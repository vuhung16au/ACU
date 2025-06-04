# Clustering Algorithms Comparison

| Algorithm | Time Complexity | Space Complexity | Best For | Limitations | Advantages | Use Cases |
|-----------|----------------|------------------|----------|-------------|------------|-----------|
| K-Means | O(n*k*i) where k is clusters, i is iterations | O(n+k) | - Spherical clusters<br>- Similar-sized clusters<br>- When number of clusters is known<br>- Large datasets | - Requires number of clusters<br>- Sensitive to outliers<br>- Assumes spherical clusters<br>- Local optima issues | - Fast and efficient<br>- Simple to implement<br>- Works well with large datasets<br>- Easy to interpret | - Customer segmentation<br>- Image compression<br>- Document clustering<br>- Market basket analysis |
| DBSCAN (Density-Based Spatial Clustering) | O(n²) | O(n) | - Arbitrary-shaped clusters<br>- When number of clusters is unknown<br>- When handling noise is important<br>- When density varies | - Sensitive to parameters<br>- Struggles with high dimensions<br>- Memory intensive for large datasets<br>- Can't handle varying densities well | - No need to specify clusters<br>- Can find arbitrary shapes<br>- Robust to outliers<br>- Works with noise | - Spatial data analysis<br>- Anomaly detection<br>- Image segmentation<br>- Social network analysis |
| Hierarchical Clustering | O(n²) | O(n²) | - When cluster hierarchy is important<br>- When number of clusters is unknown<br>- Small to medium datasets<br>- When visualization is needed | - Computationally expensive<br>- Memory intensive<br>- Sensitive to noise<br>- Can't handle large datasets | - Creates cluster hierarchy<br>- No need to specify clusters<br>- Good visualization<br>- Works with any distance metric | - Taxonomy creation<br>- Document organization<br>- Gene expression analysis<br>- Market research |

## Common Characteristics
- All are unsupervised learning algorithms
- All group similar data points
- All require distance/similarity measures
- All can handle numerical data
- All are sensitive to parameter choices

## Key Differences
1. **Cluster Shape Assumptions**:
   - K-Means: Spherical clusters
   - DBSCAN: Arbitrary shapes
   - Hierarchical: Can find various shapes

2. **Number of Clusters**:
   - K-Means: Must specify
   - DBSCAN: Automatically determines
   - Hierarchical: Can determine from dendrogram

3. **Noise Handling**:
   - K-Means: Sensitive to noise
   - DBSCAN: Robust to noise
   - Hierarchical: Sensitive to noise

4. **Scalability**:
   - K-Means: Most scalable
   - DBSCAN: Moderately scalable
   - Hierarchical: Least scalable

5. **Parameter Sensitivity**:
   - K-Means: Number of clusters
   - DBSCAN: Epsilon and min_samples
   - Hierarchical: Linkage method and distance metric

6. **Output Type**:
   - K-Means: Flat clusters
   - DBSCAN: Flat clusters with noise points
   - Hierarchical: Nested clusters (dendrogram)

7. **Popular Variants**:
   - K-Means: K-Means++, Mini-Batch K-Means
   - DBSCAN: HDBSCAN, OPTICS
   - Hierarchical: Agglomerative, Divisive

8. **Use Cases**:
   - K-Means: When clusters are well-separated
   - DBSCAN: When clusters have irregular shapes
   - Hierarchical: When cluster relationships matter

9. **Implementation Complexity**:
   - K-Means: Simplest
   - DBSCAN: Moderate
   - Hierarchical: Most complex 