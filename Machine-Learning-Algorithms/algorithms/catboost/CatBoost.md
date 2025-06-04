# CatBoost (Categorical Boosting)

## 1. Overview
CatBoost is a gradient boosting framework that handles categorical features natively and uses ordered boosting to prevent target leakage. It is designed to provide high performance while requiring minimal parameter tuning.

### Type of Learning
- Supervised Learning
- Ensemble Learning
- Classification and Regression Tasks

### Key Characteristics
- Native categorical feature handling
- Ordered boosting
- Symmetric tree structure
- Oblivious decision trees
- Target-based encoding
- Built-in overfitting detection
- GPU acceleration

### When to Use
- When you have categorical features
- When you need minimal parameter tuning
- When you want to prevent target leakage
- When you need high accuracy
- When you have imbalanced data
- When you need feature importance
- When you want GPU acceleration

## 2. Historical Context
- Developed by Yandex in 2017
- Open-sourced in 2018
- Winner of multiple Kaggle competitions
- Popular in production environments
- Inspired by gradient boosting
- Focus on categorical features

## 3. Technical Details

### Mathematical Foundation

CatBoost combines gradient boosting with specialized handling of categorical features and ordered boosting:

#### Objective Function
For a tree ensemble with $K$ trees:
$$
\mathcal{L}(\phi) = \sum_{i=1}^n l(y_i, \hat{y}_i) + \sum_{k=1}^K \Omega(f_k)
$$
where:
- $l$ is the loss function
- $\Omega$ is the regularization term
- $f_k$ is the $k$-th tree

#### Target-based Encoding
For categorical feature $x$ with value $v$, the encoding is:
$$
\text{enc}(v) = \frac{\sum_{j=1}^n \mathbb{I}(x_j = v) \cdot y_j + a \cdot P}{\sum_{j=1}^n \mathbb{I}(x_j = v) + a}
$$
where:
- $a$ is the prior weight
- $P$ is the prior probability
- $\mathbb{I}$ is the indicator function

#### Ordered Boosting
For each iteration $t$, the gradient is computed using only the first $t$ samples:
$$
g_i^t = -\left[\frac{\partial l(y_i, F(x_i))}{\partial F(x_i)}\right]_{F(x)=F_{t-1}(x)}
$$
where $F_{t-1}$ is trained on samples $1$ to $t-1$.

#### Symmetric Tree Structure
Each tree is built using oblivious decision trees, where all nodes at the same level use the same feature:
$$
f_k(x) = \sum_{l=1}^L w_l \cdot \mathbb{I}(x_{j_l} \leq t_l)
$$
where:
- $L$ is the number of leaves
- $w_l$ is the leaf weight
- $j_l$ is the feature index
- $t_l$ is the threshold

#### Leaf Value Calculation
For leaf $l$, the optimal weight is:
$$
w_l^* = -\frac{\sum_{i \in I_l} g_i}{\sum_{i \in I_l} h_i + \lambda}
$$
where:
- $I_l$ is the instance set of leaf $l$
- $g_i$ is the gradient
- $h_i$ is the Hessian
- $\lambda$ is the L2 regularization parameter

### Training Process
1. Initialize model
2. For each iteration:
   - Process categorical features
   - Build symmetric tree
   - Calculate leaf values
   - Update predictions
3. Apply regularization

### Key Parameters
- Learning rate
- Depth
- L2 regularization
- Random strength
- Border count
- Feature border type
- Leaf estimation method

## 4. Performance Analysis

### Time Complexity
- **Training:**
  - For each tree:
    - Categorical encoding: $O(n \cdot d_c)$
    - Tree building: $O(n \log n \cdot d)$
    - Leaf value calculation: $O(n)$
  - Total training: $O(K(n \cdot d_c + n \log n \cdot d))$
  where:
  - $K$ = number of trees
  - $n$ = number of samples
  - $d$ = number of features
  - $d_c$ = number of categorical features

- **Prediction:**
  - $O(K \log n)$ per sample
  - $O(N K \log n)$ for $N$ samples

### Space Complexity
- **Training:**
  - Tree structure: $O(K \cdot 2^D \cdot d)$
  - Categorical encodings: $O(d_c \cdot n_{cats})$
  - Gradient storage: $O(n)$
  where:
  - $D$ is the maximum tree depth
  - $n_{cats}$ is the number of unique categories

- **Prediction:**
  - $O(K \cdot 2^D \cdot d)$ for storing the ensemble

### Computational Requirements
- Memory efficient with symmetric trees
- Fast training with ordered boosting
- Efficient prediction with oblivious trees
- GPU acceleration support
- Parallel processing capability
- Benefits from feature scaling

### Scalability Analysis
- Training time scales with:
  - Number of trees
  - Dataset size
  - Feature dimensionality
  - Number of categories
- Memory usage scales with:
  - Number of trees
  - Tree depth
  - Feature dimensionality
  - Number of categories
- Parallelization efficiency:
  - Tree-level parallelism
  - Feature-level parallelism
  - Category-level parallelism

## 5. Practical Applications
- Click-through rate prediction
- Customer churn prediction
- Credit risk assessment
- Fraud detection
- Stock market prediction
- Search ranking
- Anomaly detection

## 6. Advantages and Limitations

### Advantages
- Native categorical handling
- Minimal parameter tuning
- Prevents target leakage
- High accuracy
- GPU support
- Built-in overfitting detection
- Feature importance
- Fast training

### Limitations
- Memory intensive
- Can be slow with many categories
- Requires careful feature engineering
- Complex implementation
- Sensitive to data quality
- Requires expertise

## 7. Implementation Guidelines

### Prerequisites
- NumPy
- Pandas
- CatBoost
- Scikit-learn
- Matplotlib (for visualization)

### Data Requirements
- Clean data
- Properly encoded categorical variables
- Scaled numerical features
- Relevant features
- Sufficient training data
- Proper validation set

### Best Practices
- Feature engineering
- Parameter tuning
- Cross-validation
- Early stopping
- Regularization
- Feature importance analysis
- Model interpretation
- Proper evaluation metrics

## 8. Python Implementation
See `CatBoost.py` for complete implementation. 