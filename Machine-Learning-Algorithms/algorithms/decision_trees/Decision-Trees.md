# Decision Trees

## 1. Overview
Decision Trees are a non-parametric supervised learning method used for both classification and regression tasks. They create a model that predicts the value of a target variable by learning simple decision rules inferred from the data features.

### Type of Learning
- Supervised Learning
- Classification and Regression Tasks
- Rule-based Learning

### Key Characteristics
- Hierarchical tree structure
- Non-parametric approach
- Easy to interpret
- Can handle both numerical and categorical data
- Naturally handles multi-class problems
- Can capture non-linear relationships

### When to Use
- When interpretability is important
- When data has both numerical and categorical features
- When you need to understand feature importance
- When you want to capture non-linear relationships
- When you need a quick baseline model
- When you want to build ensemble methods

## 2. Historical Context
- First introduced in the 1960s
- ID3 algorithm developed by Ross Quinlan in 1986
- C4.5 algorithm (improved version) in 1993
- CART (Classification and Regression Trees) in 1984
- Became fundamental in machine learning
- Basis for many ensemble methods

## 3. Technical Details

### Mathematical Foundation

Decision Trees use recursive binary splitting to partition the feature space:

#### Splitting Criteria
- **Gini Impurity (Classification):**
  $$
  Gini = 1 - \sum_{i=1}^C p_i^2
  $$
  where $p_i$ is the proportion of class $i$ in a node.

- **Entropy (Classification):**
  $$
  Entropy = -\sum_{i=1}^C p_i \log_2(p_i)
  $$

- **Information Gain:**
  $$
  IG = Entropy(parent) - \sum_{j=1}^k \frac{n_j}{n} Entropy(child_j)
  $$
  where:
  - $n_j$ is the number of samples in child $j$
  - $n$ is the total number of samples

- **Variance Reduction (Regression):**
  $$
  \Delta Var = Var(parent) - \sum_{j=1}^k \frac{n_j}{n} Var(child_j)
  $$
  where $Var$ is the variance of target values.

#### Optimal Split Selection
For each feature $j$ and threshold $t$, compute:
$$
\text{Gain}(j, t) = \text{Impurity}(parent) - \sum_{i \in \{L,R\}} \frac{n_i}{n} \text{Impurity}(child_i)
$$
where:
- $L, R$ are left and right child nodes
- $n_i$ is the number of samples in child $i$

The best split $(j^*, t^*)$ maximizes the gain:
$$
(j^*, t^*) = \arg\max_{j,t} \text{Gain}(j, t)
$$

#### Leaf Node Prediction
- **Classification:**
  $$
  \hat{y} = \arg\max_c \sum_{i=1}^n \mathbb{I}(y_i = c)
  $$
- **Regression:**
  $$
  \hat{y} = \frac{1}{n} \sum_{i=1}^n y_i
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
1. Start with all training data at root node
2. Find best split point for each feature
3. Choose feature with highest information gain
4. Create child nodes
5. Repeat recursively until stopping criteria met

### Key Parameters
- Maximum tree depth
- Minimum samples per leaf
- Minimum samples for split
- Splitting criterion
- Maximum features to consider
- Class weights

## 4. Performance Analysis

### Time Complexity
- **Training:**
  - For each node:
    - Feature evaluation: $O(n \cdot d)$
    - Split point search: $O(n \log n)$
  - Total per level: $O(n \cdot d \cdot \log n)$
  - Total training: $O(n \cdot d \cdot \log n \cdot D)$
  where:
  - $n$ = number of samples
  - $d$ = number of features
  - $D$ = maximum tree depth

- **Prediction:**
  - $O(D)$ per sample
  - $O(N \cdot D)$ for $N$ samples

### Space Complexity
- **Training:**
  - Tree structure: $O(2^D \cdot d)$
  - Feature values: $O(n \cdot d)$
  - Split points: $O(2^D)$
  - Total: $O(2^D \cdot d + n \cdot d)$

- **Prediction:**
  - $O(2^D \cdot d)$ for storing the tree

### Computational Requirements
- Memory efficient for balanced trees
- Fast training with efficient splitting
- Efficient prediction phase
- Benefits from feature scaling
- Parallel processing capability
- Cache-friendly access patterns

### Scalability Analysis
- Training time scales with:
  - Dataset size
  - Feature dimensionality
  - Tree depth
- Memory usage scales with:
  - Tree depth (exponential)
  - Feature dimensionality
  - Dataset size
- Parallelization efficiency:
  - Feature-level parallelism
  - Node-level parallelism
  - Sample-level parallelism

## 5. Practical Applications
- Credit scoring
- Medical diagnosis
- Customer churn prediction
- Fraud detection
- Risk assessment
- Quality control
- Marketing campaign optimization

## 6. Advantages and Limitations

### Advantages
- Easy to understand and interpret
- Can handle both numerical and categorical data
- Requires little data preparation
- Can capture non-linear relationships
- Provides feature importance

### Limitations
- Can overfit easily
- Unstable (small changes can lead to different trees)
- Can be biased towards features with more levels
- May create over-complex trees
- Can be sensitive to data imbalance

## 7. Comparison with Similar Algorithms

### vs Random Forests
- **Decision Trees**: Single tree, prone to overfitting
- **Random Forests**: Ensemble of trees, more robust
- **Use Case**: Choose Random Forests for better generalization

### vs SVM
- **Decision Trees**: Rule-based, axis-parallel splits
- **SVM**: Margin-based, can be non-linear
- **Use Case**: Choose based on data structure

### vs Logistic Regression
- **Decision Trees**: Non-linear, rule-based
- **Logistic Regression**: Linear, probabilistic
- **Use Case**: Choose based on interpretability needs

### vs KNN
- **Decision Trees**: Global model, rule-based
- **KNN**: Local model, distance-based
- **Use Case**: Choose based on data size and complexity

### vs Neural Networks
- **Decision Trees**: Interpretable, discrete
- **Neural Networks**: Complex, continuous
- **Use Case**: Choose based on complexity needs

## 8. Implementation Guidelines

### Prerequisites
- NumPy
- Pandas
- Scikit-learn
- Matplotlib (for visualization)
- Graphviz (for tree visualization)

### Data Requirements
- Clean data (handle missing values)
- Encoded categorical variables
- Scaled numerical features
- Balanced classes (for classification)
- Relevant features

### Best Practices
- Feature scaling
- Cross-validation
- Pruning to prevent overfitting
- Feature selection
- Handling missing values
- Proper evaluation metrics

## 9. Python Implementation
See `Decision-Trees.py` for complete implementation. 