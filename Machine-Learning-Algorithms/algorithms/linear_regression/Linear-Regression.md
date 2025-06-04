# Linear Regression

## 1. Overview
Linear Regression is a fundamental supervised learning algorithm used for predicting continuous values. It models the relationship between a dependent variable and one or more independent variables by fitting a linear equation to the observed data.

### Type of Learning
- Supervised Learning
- Regression Task

### Key Characteristics
- Assumes linear relationship between features and target
- Minimizes the sum of squared residuals
- Provides interpretable coefficients
- Can handle both single and multiple features

### When to Use
- Predicting continuous values
- Understanding feature importance
- When relationships are approximately linear
- As a baseline model for comparison

## 2. Historical Context
- First developed by Francis Galton in 1886
- Further developed by Karl Pearson and Ronald Fisher
- Became fundamental in statistical analysis
- Evolved to handle multiple variables and various optimization techniques

## 3. Technical Details

### Mathematical Foundation

Linear Regression models the relationship between a dependent variable $y$ and one or more independent variables $x_1, x_2, \ldots, x_n$ by fitting a linear equation to observed data:

$$
y = \beta_0 + \beta_1 x_1 + \beta_2 x_2 + \dots + \beta_n x_n + \epsilon
$$

where:
- $y$ is the dependent variable
- $x_i$ are the independent variables (features)
- $\beta_0$ is the intercept
- $\beta_i$ are the coefficients (weights)
- $\epsilon$ is the error term (noise)

#### Objective Function
The goal is to find the coefficients $\beta$ that minimize the sum of squared errors (residuals):

$$
J(\beta) = \sum_{i=1}^m (y_i - \hat{y}_i)^2 = \sum_{i=1}^m \left(y_i - (\beta_0 + \beta_1 x_{i1} + \dots + \beta_n x_{in})\right)^2
$$

where $m$ is the number of samples.

#### Matrix Formulation
In matrix notation:

$$
\mathbf{y} = \mathbf{X}\boldsymbol{\beta} + \boldsymbol{\epsilon}
$$

where:
- $\mathbf{y}$ is the $m \times 1$ vector of targets
- $\mathbf{X}$ is the $m \times (n+1)$ design matrix (with a column of ones for the intercept)
- $\boldsymbol{\beta}$ is the $(n+1) \times 1$ vector of coefficients
- $\boldsymbol{\epsilon}$ is the error vector

#### Solution (Normal Equation)
The optimal coefficients (without regularization) are given by:

$$
\boldsymbol{\beta} = (\mathbf{X}^T \mathbf{X})^{-1} \mathbf{X}^T \mathbf{y}
$$

#### Regularization (Ridge/Lasso)
- **Ridge Regression (L2):**
  $$
  J(\beta) = \sum_{i=1}^m (y_i - \hat{y}_i)^2 + \lambda \sum_{j=1}^n \beta_j^2
  $$
- **Lasso Regression (L1):**
  $$
  J(\beta) = \sum_{i=1}^m (y_i - \hat{y}_i)^2 + \lambda \sum_{j=1}^n |\beta_j|
  $$

where $\lambda$ is the regularization strength.

### Training Process
1. Initialize coefficients
2. Calculate predictions
3. Compute cost function (Mean Squared Error)
4. Update coefficients using gradient descent
5. Repeat until convergence

### Key Parameters
- Learning rate
- Number of iterations
- Regularization strength (if using Ridge/Lasso)
- Convergence criteria

## 4. Performance Analysis

#### Time Complexity
- **Closed-form (Normal Equation):**
  - Matrix multiplication: $O(m n^2)$
  - Matrix inversion: $O(n^3)$
  - Total: $O(m n^2 + n^3)$
- **Gradient Descent:**
  - Each iteration: $O(m n)$
  - For $i$ iterations: $O(m n i)$

where:
- $m$ = number of samples
- $n$ = number of features
- $i$ = number of iterations

#### Space Complexity
- $O(m n)$ for storing the data matrix $\mathbf{X}$
- $O(n)$ for storing the coefficients $\boldsymbol{\beta}$

#### Scalability
- Closed-form solution is efficient for small $n$, but not suitable for very high-dimensional data due to matrix inversion.
- Gradient descent scales better for large $n$ and $m$.

### Computational Requirements
- Memory efficient
- Can be parallelized
- Suitable for large datasets

## 5. Practical Applications
- House price prediction
- Sales forecasting
- Risk assessment
- Medical diagnosis
- Economic modeling

## 6. Advantages and Limitations

### Advantages
- Simple to understand and implement
- Computationally efficient
- Provides interpretable results
- Works well with linear relationships
- Can be extended to polynomial regression

### Limitations
- Assumes linear relationship
- Sensitive to outliers
- May underfit complex data
- Requires feature scaling
- Can't handle non-linear relationships

## 7. Comparison with Similar Algorithms

### vs Polynomial Regression
- **Linear Regression**: Assumes linear relationship between features and target
- **Polynomial Regression**: Can capture non-linear relationships by adding polynomial terms
- **Use Case**: Choose polynomial when data shows curved patterns

### vs Ridge Regression
- **Linear Regression**: No regularization, may overfit
- **Ridge Regression**: L2 regularization, better for multicollinearity
- **Use Case**: Choose Ridge when features are correlated

### vs Lasso Regression
- **Linear Regression**: No feature selection
- **Lasso Regression**: L1 regularization, performs feature selection
- **Use Case**: Choose Lasso when feature selection is important

### vs Elastic Net
- **Linear Regression**: No regularization
- **Elastic Net**: Combines L1 and L2 regularization
- **Use Case**: Choose Elastic Net when both feature selection and multicollinearity are concerns

### vs Logistic Regression
- **Linear Regression**: For continuous target variables
- **Logistic Regression**: For binary classification
- **Use Case**: Choose based on whether the target is continuous or categorical

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

### Best Practices
- Feature scaling
- Handling outliers
- Cross-validation
- Regularization when needed
- Feature selection

## 9. Python Implementation
See `Linear-Regression.py` for complete implementation. 