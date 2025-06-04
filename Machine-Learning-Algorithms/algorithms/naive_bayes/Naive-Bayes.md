# Naive Bayes Classification

## 1. Overview
Naive Bayes is a probabilistic classification algorithm based on Bayes' theorem with the "naive" assumption of conditional independence between features. It is particularly effective for text classification and other high-dimensional data problems.

### Type of Learning
- Supervised Learning
- Probabilistic Classification
- Generative Model

### Key Characteristics
- Probabilistic approach
- Feature independence assumption
- Fast training and prediction
- Works well with high dimensions
- Handles missing data
- Incremental learning
- Multi-class support

### When to Use
- Text classification
- Spam filtering
- Sentiment analysis
- Document categorization
- Medical diagnosis
- Weather prediction
- Real-time prediction

## 2. Historical Context
- Based on Bayes' theorem (1763)
- First used in 1960s
- Popularized in 1990s
- Still widely used in NLP
- Modern variants developed
- Integration with deep learning

## 3. Technical Details

### Mathematical Foundation

#### Bayes' Theorem
The probability of class $C$ given features $X = (x_1, x_2, ..., x_n)$:
$$
P(C|X) = \frac{P(X|C)P(C)}{P(X)}
$$
where:
- $P(C|X)$ is the posterior probability
- $P(X|C)$ is the likelihood
- $P(C)$ is the prior probability
- $P(X)$ is the evidence

#### Naive Independence Assumption
Assume features are conditionally independent given the class:
$$
P(X|C) = \prod_{i=1}^n P(x_i|C)
$$

#### Classification Rule
Choose the class $C^*$ that maximizes the posterior:
$$
C^* = \arg\max_C P(C) \prod_{i=1}^n P(x_i|C)
$$

#### Variants
- **Gaussian Naive Bayes:**
  - For continuous features, assume $x_i|C \sim \mathcal{N}(\mu_{Ci}, \sigma_{Ci}^2)$
  - Likelihood:
    $$
    P(x_i|C) = \frac{1}{\sqrt{2\pi\sigma_{Ci}^2}} \exp\left(-\frac{(x_i-\mu_{Ci})^2}{2\sigma_{Ci}^2}\right)
    $$
- **Multinomial Naive Bayes:**
  - For count features (e.g., word counts):
    $$
    P(X|C) = \frac{(\sum_j x_j)!}{\prod_j x_j!} \prod_{i=1}^n P(x_i|C)^{x_i}
    $$
- **Bernoulli Naive Bayes:**
  - For binary features:
    $$
    P(X|C) = \prod_{i=1}^n P(x_i=1|C)^{x_i} P(x_i=0|C)^{1-x_i}
    $$

#### Smoothing (Laplace)
To avoid zero probabilities:
$$
P(x_i|C) = \frac{N_{x_i,C} + \alpha}{N_C + \alpha d}
$$
where:
- $N_{x_i,C}$ is the count of feature $x_i$ in class $C$
- $N_C$ is the total count for class $C$
- $d$ is the number of possible feature values
- $\alpha$ is the smoothing parameter

### Training Process
1. Calculate prior probabilities
2. Estimate likelihoods
3. Compute feature probabilities
4. Apply smoothing
5. Store parameters

### Key Parameters
- Prior probabilities
- Feature probabilities
- Smoothing parameter
- Feature selection
- Class weights
- Threshold
- Variance threshold

## 4. Performance Analysis

### Time Complexity
- **Training:**
  $$
  O(N \times d)
  $$
  where $N$ is the number of samples and $d$ is the number of features.
- **Prediction:**
  $$
  O(d \times K)
  $$
  where $K$ is the number of classes.

### Space Complexity
- **Parameter Storage:**
  $$
  O(d \times K)
  $$
  where $d$ is the number of features and $K$ is the number of classes.
- Scales with feature count
- Memory for probabilities

### Computational Requirements
- Low computational cost
- Memory efficient
- Parallel processing possible
- Incremental updates
- Real-time prediction
- Scalable to large datasets

## 5. Practical Applications
- Text classification
- Email filtering
- Document categorization
- Sentiment analysis
- Medical diagnosis
- Weather forecasting
- Fraud detection

## 6. Advantages and Limitations

### Advantages
- Fast training and prediction
- Works well with high-dimensional data
- Requires little training data
- Handles missing values well
- Probabilistic predictions

### Limitations
- Assumes feature independence
- Sensitive to feature scaling
- May perform poorly with correlated features
- Requires careful feature selection
- Can be affected by zero probabilities

## 7. Comparison with Similar Algorithms

### vs Logistic Regression
- **Naive Bayes**: Generative model, assumes independence
- **Logistic Regression**: Discriminative model, no independence assumption
- **Use Case**: Choose based on data characteristics

### vs Decision Trees
- **Naive Bayes**: Probabilistic, global model
- **Decision Trees**: Rule-based, local model
- **Use Case**: Choose based on interpretability needs

### vs SVM
- **Naive Bayes**: Probabilistic, fast training
- **SVM**: Margin-based, slower training
- **Use Case**: Choose based on training time requirements

### vs Random Forests
- **Naive Bayes**: Simple, fast
- **Random Forests**: Complex, more accurate
- **Use Case**: Choose based on performance needs

### vs Neural Networks
- **Naive Bayes**: Interpretable, probabilistic
- **Neural Networks**: Complex, black-box
- **Use Case**: Choose based on complexity requirements

## 8. Implementation Guidelines

### Prerequisites
- NumPy
- Pandas
- Scikit-learn
- Matplotlib
- Seaborn

### Data Requirements
- Clean data
- Relevant features
- Balanced classes
- Proper encoding
- Missing value handling
- Feature scaling
- Categorical encoding

### Best Practices
- Feature selection
- Smoothing techniques
- Cross-validation
- Probability calibration
- Feature scaling
- Class balancing
- Model evaluation
- Error analysis

## 9. Python Implementation
See `Naive-Bayes.py` for complete implementation. 