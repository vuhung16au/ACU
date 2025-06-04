# Bagging (Bootstrap Aggregating)

## 1. Overview
Bagging is an ensemble learning method that combines multiple models trained on different subsets of the training data. It's particularly effective for reducing variance and improving the stability of machine learning algorithms.

### Type of Learning
- Ensemble Learning
- Supervised Learning
- Parallel Ensemble
- Bootstrap-based Method

### Key Characteristics
- Bootstrap sampling
- Parallel training
- Model aggregation
- Variance reduction
- Improved stability

### When to Use
- High variance models
- Complex datasets
- When stability is important
- Parallel processing available
- Large datasets

## 2. Historical Context
- Developed by Leo Breiman in 1996
- Based on bootstrap sampling
- Evolved into Random Forest
- Widely used in practice
- Still actively researched

## 3. Technical Details

### Mathematical Foundation

Bootstrap Sample:
$$
D_b = \{(x_i, y_i) | i \in \{1,...,n\}, \text{with replacement}\}
$$

Aggregation (Classification):
$$
\hat{y} = \text{majority vote}(\{f_b(x)\}_{b=1}^B)
$$

Aggregation (Regression):
$$
\hat{y} = \frac{1}{B}\sum_{b=1}^B f_b(x)
$$

where:
- $D_b$ is the bootstrap sample
- $f_b$ is the base model
- $B$ is the number of models
- $x$ is the input vector

### Training Process
1. Generate bootstrap samples
2. Train base models
3. Make predictions
4. Aggregate results
5. Evaluate performance

### Key Parameters
- Number of estimators
- Base model type
- Sample size
- Replacement strategy
- Aggregation method
- Random state

## 4. Performance Analysis

### Time Complexity
- Training: O(B × n × m)
- Prediction: O(B × m)

where:
- B = number of models
- n = sample size
- m = model complexity

### Space Complexity
- O(B × m) for models
- O(n) for samples
- O(1) for prediction

### Computational Requirements
- Parallel processing
- Memory for models
- Efficient sampling
- Aggregation capability

## 5. Practical Applications
- Classification tasks
- Regression problems
- Anomaly detection
- Feature selection
- Model stability
- Risk assessment

## 6. Advantages and Limitations

### Advantages
- Reduces variance
- Improves stability
- Handles overfitting
- Parallel processing
- Robust to noise

### Limitations
- May increase bias
- Computationally expensive
- Memory intensive
- May not improve all models
- Requires careful tuning

## 7. Implementation Guidelines

### Prerequisites
- Scikit-learn
- NumPy
- Pandas
- Matplotlib
- Joblib

### Data Requirements
- Sufficient samples
- Balanced classes
- Clean data
- Feature scaling
- No missing values

### Best Practices
- Model selection
- Parameter tuning
- Cross-validation
- Performance monitoring
- Error analysis
- Resource management

## 8. Python Implementation
See `bagging.py` for complete implementation. 