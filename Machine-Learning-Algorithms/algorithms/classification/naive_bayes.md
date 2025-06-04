# Naive Bayes Classification

## Overview
Naive Bayes is a probabilistic classification algorithm based on Bayes' theorem with the "naive" assumption of conditional independence between features. It's particularly effective for text classification and other high-dimensional data.

## Algorithm Description

### Key Concepts
1. **Bayes' Theorem**: P(A|B) = P(B|A) * P(A) / P(B)
2. **Conditional Independence**: Features are independent given the class
3. **Prior Probability**: Probability of each class
4. **Likelihood**: Probability of features given class

### Algorithm Steps
1. Calculate prior probabilities for each class
2. Calculate conditional probabilities for each feature
3. Apply Bayes' theorem to compute posterior probabilities
4. Make predictions based on highest probability

### Mathematical Formulation

#### Bayes' Theorem
For a class $C$ and feature vector $X = (x_1, x_2, ..., x_n)$:

$$P(C|X) = \frac{P(X|C) \cdot P(C)}{P(X)}$$

where:
- $P(C|X)$ is the posterior probability
- $P(X|C)$ is the likelihood
- $P(C)$ is the prior probability
- $P(X)$ is the evidence

#### Naive Independence Assumption
Features are conditionally independent given the class:

$$P(X|C) = \prod_{i=1}^{n} P(x_i|C)$$

This leads to the simplified posterior:

$$P(C|X) \propto P(C) \cdot \prod_{i=1}^{n} P(x_i|C)$$

#### Classification Rule
Choose the class with maximum posterior probability:

$$C^* = \arg\max_{C} P(C|X) = \arg\max_{C} P(C) \cdot \prod_{i=1}^{n} P(x_i|C)$$

#### Variants

1. **Multinomial Naive Bayes**
   For text classification with word counts:
   $$P(x_i|C) = \frac{N_{ic} + \alpha}{N_c + \alpha|V|}$$
   where:
   - $N_{ic}$ is count of word $i$ in class $C$
   - $N_c$ is total words in class $C$
   - $\alpha$ is smoothing parameter
   - $|V|$ is vocabulary size

2. **Gaussian Naive Bayes**
   For continuous features:
   $$P(x_i|C) = \frac{1}{\sqrt{2\pi\sigma_{ic}^2}} \exp\left(-\frac{(x_i - \mu_{ic})^2}{2\sigma_{ic}^2}\right)$$
   where:
   - $\mu_{ic}$ is mean of feature $i$ in class $C$
   - $\sigma_{ic}^2$ is variance of feature $i$ in class $C$

3. **Bernoulli Naive Bayes**
   For binary features:
   $$P(x_i|C) = p_{ic}^{x_i}(1-p_{ic})^{1-x_i}$$
   where:
   - $p_{ic}$ is probability of feature $i$ being present in class $C$
   - $x_i \in \{0,1\}$

#### Log-Space Computation
To avoid numerical underflow:

$$\log P(C|X) = \log P(C) + \sum_{i=1}^{n} \log P(x_i|C)$$

## Advantages
- Fast training and prediction
- Works well with high-dimensional data
- Requires little training data
- Handles missing values well
- Simple to implement

## Limitations
- Assumes feature independence
- Sensitive to feature selection
- May perform poorly with correlated features
- Requires feature discretization
- Can be affected by zero probabilities

## Time Complexity
- Training: O(n * p) where n is samples, p is features
- Prediction: O(p * c) where c is number of classes

## Space Complexity
- O(p * c) for storing probabilities

## Use Cases
- Text classification
- Spam detection
- Sentiment analysis
- Document categorization
- Medical diagnosis

## Best Practices
1. Handle zero probabilities
2. Choose appropriate feature representation
3. Apply feature selection
4. Use appropriate smoothing
5. Validate independence assumption

## Implementation Considerations
1. **Feature Engineering**
   - Text preprocessing
   - Feature discretization
   - Feature selection

2. **Model Selection**
   - Laplace smoothing
   - Feature weighting
   - Class balancing

3. **Evaluation Metrics**
   - Accuracy
   - Precision
   - Recall
   - F1-score
   - ROC-AUC

## Extensions and Variants
1. **Multinomial Naive Bayes**
   - Text classification
   - Word counts
   - Document frequency

2. **Gaussian Naive Bayes**
   - Continuous features
   - Normal distribution
   - Mean and variance

3. **Bernoulli Naive Bayes**
   - Binary features
   - Presence/absence
   - Boolean values

## Evaluation Metrics
1. **Accuracy**
   - Overall correctness
   - Simple to understand
   - May be misleading

2. **Precision**
   - True positives / (True positives + False positives)
   - Focus on positive class
   - Important when false positives are costly

3. **Recall**
   - True positives / (True positives + False negatives)
   - Focus on positive class
   - Important when false negatives are costly

4. **F1-score**
   - Harmonic mean of precision and recall
   - Balanced measure
   - Useful for imbalanced classes

5. **ROC-AUC**
   - Area under ROC curve
   - Model discrimination ability
   - Threshold-independent

## Performance Analysis

### Time Complexity

1. **Training Phase**
   - Prior probability calculation: $O(m)$
   - Feature probability calculation: $O(mn)$
   - Total training complexity: $O(mn)$
   
   For specific variants:
   - Multinomial: $O(mn + |V|)$
   - Gaussian: $O(mn)$
   - Bernoulli: $O(mn)$

2. **Prediction Phase**
   - Probability calculation: $O(nc)$
   - Class selection: $O(c)$
   - Total prediction complexity: $O(nc)$
   
   For specific variants:
   - Multinomial: $O(nc + |V|)$
   - Gaussian: $O(nc)$
   - Bernoulli: $O(nc)$

### Space Complexity

1. **Model Storage**
   - Prior probabilities: $O(c)$
   - Feature probabilities: $O(nc)$
   - Total model storage: $O(nc)$
   
   For specific variants:
   - Multinomial: $O(|V|c)$
   - Gaussian: $O(nc)$ (means and variances)
   - Bernoulli: $O(nc)$

2. **Runtime Memory**
   - Feature vector: $O(n)$
   - Probability calculations: $O(c)$
   - Total runtime: $O(n + c)$

### Computational Considerations

1. **Numerical Stability**
   - Log-space computations
   - Laplace smoothing
   - Feature scaling for Gaussian

2. **Memory Management**
   - Sparse representations for text
   - Efficient probability storage
   - Streaming updates possible

3. **Parallelization**
   - Feature probability calculations
   - Class probability calculations
   - Batch predictions

4. **Optimization Techniques**
   - Feature selection: $O(n \log n)$
   - Vocabulary pruning: $O(|V|)$
   - Probability caching: $O(nc)$ 