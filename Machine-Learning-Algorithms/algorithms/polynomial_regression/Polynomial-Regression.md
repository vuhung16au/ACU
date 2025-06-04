# Polynomial Regression

## 1. Overview
Polynomial Regression is an extension of Linear Regression that models the relationship between the independent variable x and the dependent variable y as an nth degree polynomial. It allows for modeling non-linear relationships while maintaining the benefits of linear regression.

### Type of Learning
- Supervised Learning
- Regression Task

### Key Characteristics
- Extends linear regression to model non-linear relationships
- Uses polynomial features of the original features
- Maintains interpretability through polynomial coefficients
- Can capture complex patterns in data

### When to Use
- When data shows non-linear patterns
- When simple linear regression underfits
- When domain knowledge suggests polynomial relationships
- For modeling growth rates and acceleration

## 2. Historical Context
- Developed as an extension of linear regression
- First formalized in the early 20th century
- Became popular with the rise of computational statistics
- Evolved with the development of regularization techniques

## 3. Technical Details

### Mathematical Foundation

Polynomial Regression extends linear regression by modeling the relationship between the independent variable $x$ and the dependent variable $y$ as an $n$th-degree polynomial:

$$
y = \beta_0 + \beta_1 x + \beta_2 x^2 + \dots + \beta_n x^n + \epsilon
$$

where:
- $y$ is the dependent variable
- $x$ is the independent variable
- $\beta_0, \beta_1, \dots, \beta_n$ are the coefficients
- $n$ is the degree of the polynomial
- $\epsilon$ is the error term

#### Feature Expansion
The original feature $x$ is expanded to $[x, x^2, \dots, x^n]$ to allow the model to fit non-linear relationships.

#### Objective Function
The coefficients are estimated by minimizing the sum of squared errors:

$$
J(\beta) = \sum_{i=1}^m (y_i - \hat{y}_i)^2
$$

where $\hat{y}_i$ is the predicted value for sample $i$.

#### Matrix Formulation
Let $\mathbf{X}$ be the design matrix with polynomial features, then:

$$
\mathbf{y} = \mathbf{X}\boldsymbol{\beta} + \boldsymbol{\epsilon}
$$

The solution is:

$$
\boldsymbol{\beta} = (\mathbf{X}^T \mathbf{X})^{-1} \mathbf{X}^T \mathbf{y}
$$

#### Regularization
- **Ridge (L2):**
  $$
  J(\beta) = \sum_{i=1}^m (y_i - \hat{y}_i)^2 + \lambda \sum_{j=1}^n \beta_j^2
  $$
- **Lasso (L1):**
  $$
  J(\beta) = \sum_{i=1}^m (y_i - \hat{y}_i)^2 + \lambda \sum_{j=1}^n |\beta_j|
  $$

### Training Process
1. Generate polynomial features
2. Initialize coefficients
3. Calculate predictions
4. Compute cost function (Mean Squared Error)
5. Update coefficients using gradient descent
6. Repeat until convergence

### Key Parameters
- Polynomial degree
- Learning rate
- Number of iterations
- Regularization strength (if using Ridge/Lasso)
- Convergence criteria

## 4. Performance Analysis

#### Time Complexity
- **Feature Generation:** $O(m d)$, where $d$ is the polynomial degree
- **Closed-form Solution:** $O(m d^2 + d^3)$
- **Gradient Descent:** $O(m d i)$, where $i$ is the number of iterations

#### Space Complexity
- $O(m d)$ for the expanded feature matrix
- $O(d)$ for the coefficients

#### Scalability
- High-degree polynomials increase both time and space complexity
- Regularization is important to prevent overfitting with large $d$

### Computational Requirements
- Memory usage increases with polynomial degree
- Can be computationally intensive for high degrees
- Benefits from feature scaling

## 5. Practical Applications
- Economic growth modeling
- Population growth prediction
- Physics and engineering simulations
- Biological growth patterns
- Market trend analysis

## 6. Advantages and Limitations

### Advantages
- Can capture non-linear relationships
- More flexible than linear regression
- Can model complex patterns
- Provides better fit for curved data
- Can be extended to higher degrees

### Limitations
- Can overfit with high degrees
- More complex than linear regression
- Sensitive to outliers
- Requires careful degree selection
- Computationally more expensive

## 7. Comparison with Similar Algorithms

### vs Linear Regression
- **Polynomial Regression**: Can model non-linear relationships
- **Linear Regression**: Only models linear relationships
- **Use Case**: Choose polynomial when data shows curved patterns

### vs Spline Regression
- **Polynomial Regression**: Single polynomial for entire range
- **Spline Regression**: Piecewise polynomials, more flexible
- **Use Case**: Choose spline when data has different patterns in different regions

### vs Kernel Regression
- **Polynomial Regression**: Global polynomial fit
- **Kernel Regression**: Local weighted averaging
- **Use Case**: Choose kernel when data has varying density

### vs Decision Trees
- **Polynomial Regression**: Continuous, smooth predictions
- **Decision Trees**: Piecewise constant predictions
- **Use Case**: Choose based on whether smoothness is important

### vs Neural Networks
- **Polynomial Regression**: Explicit polynomial form
- **Neural Networks**: Implicit non-linear mapping
- **Use Case**: Choose neural networks for very complex patterns

## 8. Implementation Guidelines

### Prerequisites
- NumPy
- Pandas
- Scikit-learn
- Matplotlib (for visualization)

### Data Requirements
- Continuous target variable
- Numerical features
- No missing values
- Normalized/standardized features
- Appropriate polynomial degree selection

### Best Practices
- Start with low-degree polynomials
- Use cross-validation to select optimal degree
- Apply regularization to prevent overfitting
- Scale features before polynomial expansion
- Monitor model complexity

## 9. Python Implementation
See `Polynomial-Regression.py` for complete implementation. 