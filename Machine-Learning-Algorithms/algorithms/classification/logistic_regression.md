# Logistic Regression

## Overview
Logistic Regression is a statistical method used for binary classification problems. Despite its name, it's a classification algorithm that uses a logistic function to model the probability of a binary outcome.

## Algorithm Description

### Key Concepts
1. **Logistic Function**: σ(z) = 1 / (1 + e^(-z))
2. **Decision Boundary**: Linear boundary in feature space
3. **Probability Estimation**: Outputs probability between 0 and 1

### Algorithm Steps
1. Initialize model parameters
2. Compute sigmoid function
3. Calculate cost function
4. Update parameters using gradient descent
5. Make predictions using decision boundary

### Mathematical Formulation

#### Logistic Function
The sigmoid function transforms linear output to probability:

$$P(y=1|x) = \sigma(w^T x + b) = \frac{1}{1 + e^{-(w^T x + b)}}$$

where:
- $w$ is the weight vector
- $x$ is the feature vector
- $b$ is the bias term
- $\sigma(z)$ is the sigmoid function: $\sigma(z) = \frac{1}{1 + e^{-z}}$

#### Cost Function
Binary cross-entropy loss for m training examples:

$$J(w,b) = -\frac{1}{m}\sum_{i=1}^{m}[y_i \log(h(x_i)) + (1-y_i)\log(1-h(x_i))]$$

where:
- $h(x_i) = \sigma(w^T x_i + b)$ is the predicted probability
- $y_i$ is the true label (0 or 1)
- $m$ is the number of training examples

#### Gradient Descent
The gradients of the cost function with respect to parameters:

$$\frac{\partial J}{\partial w} = \frac{1}{m}X^T(h(X) - y)$$
$$\frac{\partial J}{\partial b} = \frac{1}{m}\sum_{i=1}^{m}(h(x_i) - y_i)$$

Parameter updates:
$$w = w - \alpha \frac{\partial J}{\partial w}$$
$$b = b - \alpha \frac{\partial J}{\partial b}$$

where $\alpha$ is the learning rate.

#### Regularization
L2 regularization (Ridge):

$$J_{reg}(w,b) = J(w,b) + \frac{\lambda}{2m}\sum_{j=1}^{n}w_j^2$$

L1 regularization (Lasso):

$$J_{reg}(w,b) = J(w,b) + \frac{\lambda}{m}\sum_{j=1}^{n}|w_j|$$

where $\lambda$ is the regularization parameter.

#### Hessian Matrix
The second derivative matrix (Hessian) for Newton's method:

$$H = \frac{1}{m}X^T S X$$

where $S$ is a diagonal matrix with elements $h(x_i)(1-h(x_i))$.

## Advantages
- Simple and interpretable
- Fast training and prediction
- Works well with linear decision boundaries
- Provides probability estimates
- Easy to implement

## Limitations
- Assumes linear decision boundary
- May underfit complex patterns
- Sensitive to outliers
- Requires feature scaling
- Assumes independent features

## Time Complexity
- Training: O(n * p * i) where n is samples, p is features, i is iterations
- Prediction: O(p)

## Space Complexity
- O(p) for storing coefficients

## Use Cases
- Binary classification
- Probability estimation
- Risk assessment
- Medical diagnosis
- Credit scoring

## Best Practices
1. Check for linearity
2. Handle outliers
3. Scale features
4. Check multicollinearity
5. Validate assumptions

## Implementation Considerations
1. **Feature Engineering**
   - Polynomial features
   - Interaction terms
   - Feature scaling

2. **Model Selection**
   - Regularization
   - Cross-validation
   - Feature selection

3. **Evaluation Metrics**
   - Accuracy
   - Precision
   - Recall
   - F1-score
   - ROC-AUC

## Extensions and Variants
1. **Multinomial Logistic Regression**
   - Multiple classes
   - Softmax function
   - One-vs-Rest approach

2. **Regularized Logistic Regression**
   - L1 regularization (Lasso)
   - L2 regularization (Ridge)
   - Elastic Net

3. **Stochastic Gradient Descent**
   - Mini-batch updates
   - Online learning
   - Faster convergence

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
   - Per iteration:
     - Forward pass: $O(mn)$
     - Backward pass: $O(mn)$
     - Parameter updates: $O(n)$
   - Total for $i$ iterations: $O(imn)$
   
   For batch gradient descent:
   - Full dataset processing: $O(mn)$ per iteration
   - Total complexity: $O(imn)$

   For stochastic gradient descent:
   - Single sample processing: $O(n)$ per iteration
   - Total complexity: $O(in)$

2. **Prediction Phase**
   - Single prediction: $O(n)$
   - Batch prediction of $k$ samples: $O(kn)$

### Space Complexity

1. **Model Storage**
   - Weight vector: $O(n)$
   - Bias term: $O(1)$
   - Total model storage: $O(n)$

2. **Training Storage**
   - Feature matrix $X$: $O(mn)$
   - Labels $y$: $O(m)$
   - Gradients: $O(n)$
   - Total training storage: $O(mn)$

### Computational Considerations

1. **Numerical Stability**
   - Log-sum-exp trick for numerical stability
   - Gradient clipping to prevent exploding gradients
   - Feature scaling for better convergence

2. **Memory Usage**
   - Efficient for large datasets with SGD
   - Mini-batch processing possible
   - Sparse feature representations supported

3. **Optimization Methods**
   - Gradient Descent: $O(imn)$ time, $O(n)$ space
   - Newton's Method: $O(in^3)$ time, $O(n^2)$ space
   - L-BFGS: $O(in^2)$ time, $O(n^2)$ space

4. **Convergence Properties**
   - Linear convergence rate for gradient descent
   - Quadratic convergence for Newton's method
   - Superlinear convergence for L-BFGS 