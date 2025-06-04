# Logistic Regression

## 1. Overview
Logistic Regression is a fundamental supervised learning algorithm used for binary classification problems. Despite its name, it's a classification algorithm that models the probability of a binary outcome using a logistic function (sigmoid function).

### Type of Learning
- Supervised Learning
- Classification Task

### Key Characteristics
- Models probability using sigmoid function
- Provides probabilistic predictions
- Linear decision boundary
- Can be extended to multi-class problems
- Interpretable coefficients

### When to Use
- Binary classification problems
- When probability estimates are needed
- When interpretability is important
- As a baseline model for classification
- When features are linearly separable

## 2. Historical Context
- Developed by David Cox in 1958
- Based on the logistic function by Pierre François Verhulst (1845)
- Became fundamental in statistical analysis
- Evolved to handle multi-class problems and regularization

## 3. Technical Details

### Mathematical Foundation

Logistic Regression models the probability that the dependent variable $y$ belongs to class 1 as a function of the input features $x_1, x_2, \ldots, x_n$:

$$
P(y=1|x) = \sigma(z) = \frac{1}{1 + e^{-z}}
$$
where $z = \beta_0 + \beta_1 x_1 + \dots + \beta_n x_n$

#### Log-Likelihood
The log-likelihood for $m$ samples is:
$$
\ell(\boldsymbol{\beta}) = \sum_{i=1}^m \left[ y_i \log(p_i) + (1 - y_i) \log(1 - p_i) \right]
$$
where $p_i = P(y_i=1|x_i)$

#### Cost Function (Binary Cross-Entropy)
The cost function to minimize is the negative log-likelihood:
$$
J(\boldsymbol{\beta}) = -\frac{1}{m} \sum_{i=1}^m \left[ y_i \log(p_i) + (1 - y_i) \log(1 - p_i) \right]
$$

#### Regularization
- **L2 (Ridge):**
  $$
  J(\boldsymbol{\beta}) = -\frac{1}{m} \sum_{i=1}^m \left[ y_i \log(p_i) + (1 - y_i) \log(1 - p_i) \right] + \lambda \sum_{j=1}^n \beta_j^2
  $$
- **L1 (Lasso):**
  $$
  J(\boldsymbol{\beta}) = -\frac{1}{m} \sum_{i=1}^m \left[ y_i \log(p_i) + (1 - y_i) \log(1 - p_i) \right] + \lambda \sum_{j=1}^n |\beta_j|
  $$

#### Optimization
Parameters are typically estimated using gradient descent or advanced optimizers (e.g., L-BFGS).

### Training Process
1. Initialize coefficients
2. Calculate probabilities using sigmoid function
3. Compute cost function (Binary Cross-Entropy)
4. Update coefficients using gradient descent
5. Repeat until convergence

### Key Parameters
- Learning rate
- Number of iterations
- Regularization strength (L1/L2)
- Convergence criteria
- Class weights (for imbalanced data)

## 4. Performance Analysis

### Time Complexity
- **Gradient Descent:** $O(m n i)$
- **Advanced Solvers (e.g., L-BFGS):** $O(m n^2)$ per iteration

where:
- $m$ = number of samples
- $n$ = number of features
- $i$ = number of iterations

### Space Complexity
- $O(m n)$ for data storage
- $O(n)$ for coefficients

### Computational Requirements
- Memory efficient
- Can be parallelized
- Suitable for large datasets
- Benefits from feature scaling

## 5. Practical Applications
- Medical diagnosis
- Credit scoring
- Fraud detection
- Customer churn prediction
- Spam detection
- Disease prediction

## 6. Advantages and Limitations

### Advantages
- Simple to understand and implement
- Provides probability estimates
- Works well with small datasets
- Can be regularized
- Interpretable results

### Limitations
- Assumes linear decision boundary
- May underfit complex data
- Sensitive to outliers
- Requires feature scaling
- Can't handle non-linear relationships directly

## 7. Comparison with Similar Algorithms

### vs Linear Regression
- **Logistic Regression**: For binary classification
- **Linear Regression**: For continuous target variables
- **Use Case**: Choose based on whether the target is categorical or continuous

### vs SVM
- **Logistic Regression**: Probabilistic, linear boundary
- **SVM**: Maximum margin, can be non-linear with kernels
- **Use Case**: Choose SVM when margin maximization is important

### vs Decision Trees
- **Logistic Regression**: Linear decision boundary
- **Decision Trees**: Non-linear, axis-parallel boundaries
- **Use Case**: Choose decision trees when features have complex interactions

### vs Naive Bayes
- **Logistic Regression**: Discriminative model
- **Naive Bayes**: Generative model
- **Use Case**: Choose based on whether you need probability estimates

### vs Neural Networks
- **Logistic Regression**: Single layer, linear
- **Neural Networks**: Multiple layers, non-linear
- **Use Case**: Choose neural networks for complex patterns

## 8. Implementation Guidelines

### Prerequisites
- NumPy
- Pandas
- Scikit-learn
- Matplotlib (for visualization)

### Data Requirements
- Binary target variable
- Numerical features
- No missing values
- Normalized/standardized features
- Balanced or properly weighted classes

### Best Practices
- Feature scaling
- Handling class imbalance
- Cross-validation
- Regularization
- Feature selection
- Proper evaluation metrics (AUC-ROC, precision, recall)

## 9. Python Implementation
See `Logistic-Regression.py` for complete implementation. 