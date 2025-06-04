# Naive Bayes

## 1. Overview
Naive Bayes is a probabilistic classifier based on Bayes' theorem with strong independence assumptions between features. Despite its simplicity, it often performs well in practice and is particularly effective for text classification and spam filtering.

### Type of Learning
- Supervised Learning
- Probabilistic Classification
- Generative Model

### Key Characteristics
- Probabilistic approach
- Feature independence
- Fast training and prediction
- Works well with high dimensions
- Handles missing data
- Incremental learning

### When to Use
- When you have high-dimensional data
- When you need fast training and prediction
- When you want probabilistic outputs
- When you have limited training data
- When you need incremental learning
- When you want a simple baseline model

## 2. Historical Context
- Based on Bayes' theorem (1763)
- First used in text classification
- Popularized in spam filtering
- Foundation for many probabilistic models
- Still widely used in practice

## 3. Technical Details

### Mathematical Foundation

#### Bayes' Theorem
The fundamental equation:
$$
P(y|X) = \frac{P(X|y)P(y)}{P(X)}
$$
where:
- $P(y|X)$ is the posterior probability
- $P(X|y)$ is the likelihood
- $P(y)$ is the prior probability
- $P(X)$ is the evidence

#### Naive Assumption
Features are conditionally independent given the class:
$$
P(X|y) = \prod_{i=1}^n P(x_i|y)
$$

#### Classification Rule
For a new instance $X$, predict class $y$ that maximizes:
$$
\hat{y} = \text{argmax}_y P(y)\prod_{i=1}^n P(x_i|y)
$$

#### Different Variants

1. Gaussian Naive Bayes:
$$
P(x_i|y) = \frac{1}{\sqrt{2\pi\sigma_y^2}} \exp\left(-\frac{(x_i - \mu_y)^2}{2\sigma_y^2}\right)
$$

2. Multinomial Naive Bayes:
$$
P(x_i|y) = \frac{N_{yi} + \alpha}{N_y + \alpha n}
$$
where:
- $N_{yi}$ is count of feature $i$ in class $y$
- $N_y$ is total count of features in class $y$
- $\alpha$ is smoothing parameter
- $n$ is number of features

3. Bernoulli Naive Bayes:
$$
P(x_i|y) = P(i|y)x_i + (1 - P(i|y))(1 - x_i)
$$

#### Log Probability
To avoid numerical underflow:
$$
\log P(y|X) \propto \log P(y) + \sum_{i=1}^n \log P(x_i|y)
$$

### Training Process
1. Calculate class priors $P(y)$
2. Estimate feature probabilities $P(x_i|y)$
3. Apply smoothing if needed
4. Store parameters for prediction

### Key Parameters
- Prior probabilities
- Smoothing parameter
- Feature distribution type
- Class weights
- Threshold for binary features
- Missing value handling

## 4. Performance Analysis

### Time Complexity
- **Training:**
  - $O(n \times d)$ where:
    - $n$ = number of samples
    - $d$ = number of features

- **Prediction:**
  - $O(d)$ per sample
  - $O(n \times d)$ for $n$ samples

### Space Complexity
- **Training:**
  - $O(c \times d)$ where:
    - $c$ = number of classes
    - $d$ = number of features

- **Prediction:**
  - $O(c \times d)$ for storing parameters
  - $O(1)$ additional space

### Computational Requirements
- Memory efficient
- Fast training and prediction
- Benefits from feature scaling
- Can handle streaming data
- Parallelizable
- Works well with sparse data

### Scalability Analysis
- Training time scales linearly with:
  - Dataset size
  - Number of features
- Memory usage scales with:
  - Number of classes
  - Number of features
- Prediction time scales with:
  - Number of features

## 5. Practical Applications
- Text classification
- Spam filtering
- Sentiment analysis
- Document categorization
- Medical diagnosis
- Weather prediction
- Customer behavior analysis

## 6. Advantages and Limitations

### Advantages
- Fast training and prediction
- Works well with high dimensions
- Handles missing data
- Probabilistic outputs
- Incremental learning
- Memory efficient

### Limitations
- Assumes feature independence
- Sensitive to feature selection
- Can be outperformed by more complex models
- Requires feature discretization for some variants
- May need smoothing for rare events
- Can be affected by irrelevant features

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
- Appropriate feature distribution

### Best Practices
- Feature selection
- Proper smoothing
- Cross-validation
- Feature scaling
- Proper evaluation metrics
- Probability calibration

## 8. Python Implementation
See `Naive_Bayes.py` for complete implementation. 