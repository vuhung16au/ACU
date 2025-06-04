# CACTUS (CAtegorical ClusTering Using Summaries)

## 1. Overview
CACTUS is a specialized clustering algorithm designed specifically for categorical data. It uses attribute value summaries to identify clusters in categorical datasets, making it particularly effective for handling non-numeric data types.

### Type of Learning
- Unsupervised Learning
- Categorical Clustering
- Non-parametric Clustering

### Key Characteristics
- Categorical data handling
- Attribute value summaries
- No distance metrics needed
- Automatic cluster detection
- Noise handling
- Scalable to large datasets

### When to Use
- When dealing with categorical data
- When traditional distance-based methods fail
- When data has many attributes
- When clusters are defined by attribute patterns
- When you need interpretable results
- When dealing with mixed data types

## 2. Technical Details

### Mathematical Foundation

CACTUS uses attribute value summaries to identify clusters. The algorithm works in three phases:

#### 1. Summary Phase
For each attribute $A_i$ and value $v_{ij}$:
$$
\text{Support}(v_{ij}) = \frac{|\{x \in D | x.A_i = v_{ij}\}|}{|D|}
$$

#### 2. Cluster Generation Phase
A cluster $C$ is a set of attribute-value pairs that satisfy:
$$
\text{Support}(C) = \frac{|\{x \in D | \forall (A_i, v_{ij}) \in C, x.A_i = v_{ij}\}|}{|D|} \geq \theta
$$
where $\theta$ is the minimum support threshold.

#### 3. Cluster Refinement Phase
Clusters are refined using:
$$
\text{Confidence}(C_1 \rightarrow C_2) = \frac{\text{Support}(C_1 \cup C_2)}{\text{Support}(C_1)}
$$

### Key Parameters
- Minimum support threshold
- Confidence threshold
- Maximum cluster size
- Attribute selection criteria
- Noise threshold

## 3. Performance Analysis

### Time Complexity
- **Summary Phase:** $O(n \times m)$
- **Cluster Generation:** $O(2^m)$
- **Refinement Phase:** $O(k \times m)$
where:
- $n$ = number of records
- $m$ = number of attributes
- $k$ = number of initial clusters

### Space Complexity
- **Summary Storage:** $O(m \times v)$
- **Cluster Storage:** $O(k \times m)$
where $v$ is the average number of values per attribute

### Computational Requirements
- Memory for attribute summaries
- Storage for candidate clusters
- Efficient set operations
- Pattern matching capabilities

## 4. Advantages and Limitations

### Advantages
- No distance metrics needed
- Handles categorical data naturally
- Automatic cluster detection
- Interpretable results
- Scalable to large datasets
- Noise handling

### Limitations
- Limited to categorical data
- Sensitive to parameter settings
- May miss complex patterns
- Memory intensive
- Computationally expensive
- Attribute dependency issues

## 5. Implementation Guidelines

### Prerequisites
- NumPy
- Pandas
- Efficient set operations
- Pattern matching libraries

### Data Requirements
- Categorical attributes
- Clean data
- Consistent encoding
- Missing value handling
- Attribute selection

### Best Practices
- Parameter tuning
- Attribute preprocessing
- Memory management
- Pattern validation
- Result interpretation
- Performance optimization

## 6. Python Implementation
See `cactus.py` for complete implementation. 