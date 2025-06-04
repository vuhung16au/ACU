# Random Forest

## 1. Overview
Random Forest is an ensemble learning method that constructs multiple decision trees and outputs the class that is the mode of the classes (classification) or mean prediction (regression) of the individual trees. It uses bagging and feature randomness to create an uncorrelated forest of trees.

### Type of Learning
- Supervised Learning
- Ensemble Learning
- Classification and Regression Tasks

### Key Characteristics
- Ensemble of trees
- Bagging
- Feature randomness
- Parallel training
- Robust to overfitting
- Feature importance

### When to Use
- When you need high accuracy
- When you want to avoid overfitting
- When you need feature importance
- When you have high-dimensional data
- When you want parallel training
- When you need a robust model

## 2. Historical Context
- Developed by Leo Breiman in 2001
- Extension of bagging and random subspace method
- One of the most successful ensemble methods
- Foundation for many modern tree-based methods

## 3. Technical Details

### Mathematical Foundation

#### Bagging
For a dataset $D$ with $n$ samples, create $B$ bootstrap samples:
$$
D_b = \{(x_i, y_i)\}_{i=1}^n \text{ where } (x_i, y_i) \sim D
$$

#### Feature Selection
For each tree, select $m$ features randomly:
$$
m = \lfloor \sqrt{d} \rfloor \text{ for classification}
$$
$$
m = \lfloor d/3 \rfloor \text{ for regression}
$$
where $d$ is the total number of features.

#### Tree Construction
For each tree $t$:
1. Sample with replacement from training data
2. Select random feature subset
3. Find best split using:
   - Gini impurity for classification:
   $$
   Gini(D) = 1 - \sum_{i=1}^c p_i^2
   $$
   - Variance reduction for regression:
   $$
   Var(D) = \frac{1}{|D|} \sum_{i=1}^{|D|} (y_i - \bar{y})^2
   $$

#### Ensemble Prediction
For classification:
$$
\hat{y} = \text{mode}\{h_t(x)\}_{t=1}^B
$$

For regression:
$$
\hat{y} = \frac{1}{B} \sum_{t=1}^B h_t(x)
$$

#### Feature Importance
For feature $j$:
$$
Importance(j) = \frac{1}{B} \sum_{t=1}^B \sum_{node \in t} \Delta I(node, j)
$$
where $\Delta I(node, j)$ is the impurity decrease at node $node$ due to feature $j$.

### Training Process
1. Create bootstrap samples
2. For each sample:
   - Select random feature subset
   - Grow decision tree
   - Store tree in forest
3. Return ensemble of trees

### Key Parameters
- Number of trees
- Maximum tree depth
- Minimum samples per leaf
- Feature subset size
- Bootstrap sample size
- Random seed

## 4. Performance Analysis

### Time Complexity
- **Training:**
  - For each tree:
    - Bootstrap sampling: $O(n)$
    - Tree construction: $O(n \times d \times h \times \log n)$
  - Total training: $O(B \times n \times d \times h \times \log n)$
  where:
  - $B$ = number of trees
  - $n$ = number of samples
  - $d$ = number of features
  - $h$ = tree height

- **Prediction:**
  - $O(B \times h)$ per sample
  - $O(n \times B \times h)$ for $n$ samples

### Space Complexity
- **Training:**
  - $O(n \times d)$ for data storage
  - $O(B \times 2^h)$ for tree structures
  where $h$ is the maximum tree height

- **Prediction:**
  - $O(B \times h)$ for storing paths
  - $O(1)$ additional space

### Computational Requirements
- Memory intensive
- Highly parallelizable
- Benefits from feature scaling
- Can handle missing values
- Efficient prediction
- Scalable to large datasets

### Scalability Analysis
- Training time scales with:
  - Number of trees
  - Dataset size
  - Number of features
  - Tree depth
- Memory usage scales with:
  - Number of trees
  - Tree complexity
  - Dataset size
- Prediction time scales with:
  - Number of trees
  - Tree depth

## 5. Practical Applications
- Credit scoring
- Medical diagnosis
- Customer churn prediction
- Fraud detection
- Stock market prediction
- Quality control
- Process optimization

## 6. Advantages and Limitations

### Advantages
- High accuracy
- Robust to overfitting
- Handles high dimensions
- Provides feature importance
- Parallel training
- Works with mixed data types

### Limitations
- Memory intensive
- Can be slow for large datasets
- Less interpretable than single trees
- May need many trees
- Sensitive to parameter tuning
- Can be biased towards categorical variables

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
- Sufficient training data
- Balanced classes (for classification)

### Best Practices
- Cross-validation
- Feature scaling
- Parameter tuning
- Feature selection
- Proper evaluation metrics
- Tree visualization

## 8. Python Implementation
See `Random_Forest.py` for complete implementation. 