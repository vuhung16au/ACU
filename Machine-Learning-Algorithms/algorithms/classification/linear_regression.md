# Linear Regression

## Overview
Linear Regression is a fundamental statistical method used to model the relationship between a dependent variable and one or more independent variables. It assumes a linear relationship between the input variables and the target variable.

## Algorithm Description

### Key Concepts
1. **Linear Model**: y = β₀ + β₁x₁ + β₂x₂ + ... + βₙxₙ + ε
2. **Ordinary Least Squares (OLS)**: Method to estimate parameters
3. **Residuals**: Differences between predicted and actual values

### Algorithm Steps
1. Initialize model parameters
2. Calculate coefficients using OLS
3. Make predictions using the linear equation
4. Evaluate model performance

### Mathematical Formulation

#### Linear Model
For n features, the model is expressed as:

$$y = \beta_0 + \sum_{i=1}^{n} \beta_i x_i + \epsilon$$

where:
- $y$ is the target variable
- $x_i$ are the features
- $\beta_i$ are the coefficients
- $\epsilon$ is the error term (assumed to be normally distributed with mean 0 and constant variance $\sigma^2$)

#### Cost Function
The objective is to minimize the sum of squared residuals (RSS):

$$RSS = \sum_{i=1}^{m} (y_i - \hat{y}_i)^2 = \sum_{i=1}^{m} (y_i - (\beta_0 + \sum_{j=1}^{n} \beta_j x_{ij}))^2$$

where:
- $m$ is the number of training examples
- $y_i$ is the actual value
- $\hat{y}_i$ is the predicted value
- $x_{ij}$ is the j-th feature of the i-th example

#### OLS Solution
The optimal coefficients are found by solving the normal equation:

$$\beta = (X^T X)^{-1} X^T y$$

where:
- $X$ is the feature matrix (including a column of ones for the intercept)
- $y$ is the target vector
- $\beta$ is the coefficient vector

#### Matrix Form
The linear model can be written in matrix form as:

$$y = X\beta + \epsilon$$

The solution minimizes the squared error:

$$\min_{\beta} \|y - X\beta\|^2$$

#### Statistical Properties
1. **Unbiasedness**: The OLS estimator is unbiased:
   $$E[\hat{\beta}] = \beta$$

2. **Variance**: The covariance matrix of the coefficients is:
   $$Var(\hat{\beta}) = \sigma^2(X^T X)^{-1}$$

3. **Gauss-Markov Theorem**: Under the classical assumptions, OLS is the Best Linear Unbiased Estimator (BLUE)

## Advantages
- Simple and interpretable
- Fast training and prediction
- Works well with linear relationships
- Provides statistical significance
- Easy to implement

## Limitations
- Assumes linear relationship
- Sensitive to outliers
- Requires feature scaling
- May underfit complex patterns
- Assumes independent features

## Time Complexity
- Training: O(n²p) where n is number of samples and p is number of features
- Prediction: O(p)

## Space Complexity
- O(p) for storing coefficients

## Use Cases
- Price prediction
- Trend analysis
- Risk assessment
- Sales forecasting
- Scientific research

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
   - R-squared
   - Mean Squared Error
   - Root Mean Squared Error
   - Mean Absolute Error

## Extensions and Variants
1. **Ridge Regression**
   - L2 regularization
   - Handles multicollinearity
   - Prevents overfitting

2. **Lasso Regression**
   - L1 regularization
   - Feature selection
   - Sparse solutions

3. **Elastic Net**
   - Combines L1 and L2
   - Better than pure Lasso
   - More stable than Ridge

## Evaluation Metrics
1. **R-squared**
   - Proportion of variance explained
   - Range: 0 to 1
   - Higher is better

2. **Mean Squared Error (MSE)**
   - Average squared error
   - Penalizes large errors
   - Not in original units

3. **Root Mean Squared Error (RMSE)**
   - Square root of MSE
   - In original units
   - More interpretable

4. **Mean Absolute Error (MAE)**
   - Average absolute error
   - Robust to outliers
   - In original units

## Performance Analysis

### Time Complexity

1. **Training Phase**
   - Matrix multiplication $X^T X$: $O(mn^2)$
   - Matrix inversion $(X^T X)^{-1}$: $O(n^3)$
   - Final multiplication: $O(mn)$
   - Total training complexity: $O(mn^2 + n^3)$
   
   For $m >> n$ (common case), this simplifies to $O(mn^2)$

2. **Prediction Phase**
   - Single prediction: $O(n)$
   - Batch prediction of $k$ samples: $O(kn)$

### Space Complexity

1. **Model Storage**
   - Coefficients vector: $O(n)$
   - Additional statistics (optional): $O(n^2)$
   - Total model storage: $O(n^2)$

2. **Training Storage**
   - Feature matrix $X$: $O(mn)$
   - Target vector $y$: $O(m)$
   - Intermediate matrices: $O(n^2)$
   - Total training storage: $O(mn + n^2)$

### Computational Considerations

1. **Numerical Stability**
   - Condition number of $X^T X$ affects stability
   - Regularization can improve stability
   - Feature scaling recommended

2. **Memory Usage**
   - Can be memory-intensive for large datasets
   - Sparse matrix representations possible
   - Online learning variants available

3. **Parallelization**
   - Matrix operations can be parallelized
   - Distributed computing possible
   - GPU acceleration available 