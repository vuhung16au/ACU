# Perceptron

## Overview
The Perceptron is one of the simplest forms of artificial neural networks, consisting of a single neuron with adjustable weights and a bias. It's a binary classifier that learns to separate linearly separable data by adjusting its weights through a simple learning rule.

## Algorithm Description

### Key Concepts
1. **Single Neuron**: Basic computational unit
2. **Step Function**: Binary activation function
3. **Weight Updates**: Learning through error correction
4. **Linear Decision Boundary**: Separates classes with a hyperplane

### Algorithm Steps
1. Initialize weights and bias
2. For each training example:
   - Compute prediction
   - Update weights if prediction is wrong
3. Repeat until convergence or max iterations
4. Make predictions using learned weights

### Mathematical Formulation

#### Neuron Output
For input x and weights w:
```
y = step(w^T x + b)
```
where step(z) = 1 if z ≥ 0, -1 otherwise

#### Weight Update Rule
When prediction is wrong:
```
w_new = w_old + η * y * x
b_new = b_old + η * y
```
where η is the learning rate

## Advantages
- Simple to understand and implement
- Fast training for linearly separable data
- Online learning capability
- Memory efficient
- Guaranteed convergence for linearly separable data

## Limitations
- Only works for linearly separable data
- No probability estimates
- Sensitive to learning rate
- May not converge for non-separable data
- Single layer limitation

## Time Complexity
- Training: O(n * p * i) where n is samples, p is features, i is iterations
- Prediction: O(p)

## Space Complexity
- O(p) for storing weights

## Use Cases
- Binary classification
- Linear separation problems
- Educational purposes
- Simple pattern recognition
- Feature learning

## Best Practices
1. Scale input features
2. Choose appropriate learning rate
3. Set reasonable max iterations
4. Handle non-separable data
5. Monitor convergence

## Implementation Considerations
1. **Initialization**
   - Random weights
   - Zero initialization
   - Custom initialization

2. **Learning Rate**
   - Fixed rate
   - Adaptive rate
   - Decaying rate

3. **Stopping Criteria**
   - No errors
   - Max iterations
   - Error threshold

## Extensions and Variants
1. **Pocket Algorithm**
   - Stores best weights
   - Handles non-separable data
   - More robust

2. **Voted Perceptron**
   - Multiple weight vectors
   - Weighted voting
   - Better generalization

3. **Kernel Perceptron**
   - Non-linear decision boundary
   - Kernel trick
   - More powerful

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

5. **Decision Boundary**
   - Margin width
   - Separation quality
   - Generalization ability 