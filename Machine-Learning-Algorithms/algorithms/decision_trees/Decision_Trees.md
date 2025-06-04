# Decision Trees

## 1. Overview
Decision Trees are hierarchical tree structures that recursively partition the feature space to make predictions. They use a series of binary decisions to classify instances or predict continuous values.

### Type of Learning
- Supervised Learning
- Tree-based Learning
- Classification and Regression Tasks

### Key Characteristics
- Hierarchical structure
- Recursive partitioning
- Feature selection
- Interpretable rules
- Non-linear relationships
- Handles mixed data types

### When to Use
- When you need interpretable results
- When you have mixed data types
- When you want to capture non-linear relationships
- When you need feature importance
- When you want to handle missing values
- When you need a baseline model

## 2. Historical Context
- First introduced by Ross Quinlan in 1986 (ID3 algorithm)
- C4.5 algorithm developed in 1993
- CART (Classification and Regression Trees) developed by Breiman et al. in 1984
- Foundation for many ensemble methods

## 3. Technical Details

### Mathematical Foundation

#### Information Gain
For a dataset $D$ with $m$ classes, the entropy is:
$$
H(D) = -\sum_{i=1}^m p_i \log_2(p_i)
$$
where $p_i$ is the proportion of class $i$ in $D$.

For a feature $A$ with $v$ possible values, the information gain is:
$$
IG(D,A) = H(D) - \sum_{j=1}^v \frac{|D_j|}{|D|} H(D_j)
$$
where $D_j$ is the subset of $D$ where feature $A$ has value $j$.

#### Gini Impurity
For classification tasks, Gini impurity is:
$$
Gini(D) = 1 - \sum_{i=1}^m p_i^2
$$

For a split on feature $A$:
$$
Gini_{split}(D,A) = \sum_{j=1}^v \frac{|D_j|}{|D|} Gini(D_j)
$$

#### Variance Reduction
For regression tasks, the variance of a node is:
$$
Var(D) = \frac{1}{|D|} \sum_{i=1}^{|D|} (y_i - \bar{y})^2
$$

The variance reduction for a split is:
$$
Var_{reduction}(D,A) = Var(D) - \sum_{j=1}^v \frac{|D_j|}{|D|} Var(D_j)
$$

#### Pruning
Cost complexity pruning minimizes:
$$
R_\alpha(T) = R(T) + \alpha|T|
$$
where:
- $R(T)$ is the misclassification rate
- $|T|$ is the number of leaf nodes
- $\alpha$ is the complexity parameter

### Training Process
1. Start with root node containing all data
2. For each node:
   - Find best split using impurity measure
   - Create child nodes
   - Recursively repeat until stopping criteria
3. Apply pruning if needed

### Key Parameters
- Maximum depth
- Minimum samples per leaf
- Minimum samples for split
- Impurity measure
- Pruning parameters
- Feature selection criteria

## 4. Performance Analysis

### Time Complexity
- **Training:**
  - For each node:
    - Feature evaluation: $O(n \times d)$
    - Split point calculation: $O(n \log n)$
  - Total training: $O(n \times d \times h \times \log n)$
  where:
  - $n$ = number of samples
  - $d$ = number of features
  - $h$ = tree height

- **Prediction:**
  - $O(h)$ per sample
  - $O(n \times h)$ for $n$ samples

### Space Complexity
- **Training:**
  - $O(n \times d)$ for data storage
  - $O(2^h)$ for tree structure
  where $h$ is the maximum tree height

- **Prediction:**
  - $O(h)$ for storing path
  - $O(1)$ additional space

### Computational Requirements
- Memory efficient
- Fast prediction
- Parallelizable training
- Benefits from feature scaling
- Can handle missing values

### Scalability Analysis
- Training time scales with:
  - Dataset size
  - Number of features
  - Tree depth
- Memory usage scales with:
  - Dataset size
  - Tree complexity
- Prediction time scales with:
  - Tree depth

## 5. Practical Applications
- Credit scoring
- Medical diagnosis
- Customer segmentation
- Fraud detection
- Risk assessment
- Quality control
- Process optimization

## 6. Advantages and Limitations

### Advantages
- Easy to understand and interpret
- Can handle both numerical and categorical data
- Non-parametric
- Feature importance
- Handles missing values
- Fast prediction

### Limitations
- Can overfit
- Unstable to small changes
- Biased towards features with more levels
- Can create complex trees
- May not capture linear relationships well
- Can be memory intensive for deep trees

## 7. Implementation Guidelines

### Prerequisites
- NumPy
- Pandas
- Scikit-learn
- Matplotlib (for visualization)

### Data Requirements
- Clean data
- Relevant features
- Proper encoding
- Handled missing values
- Balanced classes (for classification)
- Sufficient training data

### Best Practices
- Cross-validation
- Feature scaling
- Pruning
- Feature selection
- Proper evaluation metrics
- Tree visualization

## 8. Python Implementation
See `Decision_Trees.py` for complete implementation. 