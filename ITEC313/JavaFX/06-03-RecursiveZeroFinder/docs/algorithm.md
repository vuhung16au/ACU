# Recursive Zero Finder Algorithm

## Overview

The Recursive Zero Finder implements the **bisection method** (also known as binary search) to find the root of a function f(x) within a specified interval [l, r]. The algorithm is guaranteed to converge if the function has opposite signs at the endpoints (f(l)f(r) ≤ 0).

## Mathematical Foundation

### Intermediate Value Theorem

The algorithm relies on the **Intermediate Value Theorem**:
- If f(x) is continuous on [a, b]
- And f(a) and f(b) have opposite signs
- Then there exists at least one c ∈ (a, b) such that f(c) = 0

### Bisection Method

The bisection method repeatedly halves the search interval:
1. Start with interval [l, r] where f(l)f(r) ≤ 0
2. Calculate midpoint: m = (l + r) / 2
3. Evaluate f(m)
4. If |f(m)| ≤ ε, return m (found zero within tolerance)
5. If f(l)f(m) < 0, zero is in [l, m]
6. Otherwise, zero is in [m, r]
7. Repeat with the new interval

## Algorithm Implementation

### Recursive Approach

```java
public static double findZero(Function<Double, Double> f, double l, double r, double e) {
    // Input validation
    if (l >= r) throw new IllegalArgumentException("Invalid bounds");
    if (e <= 0) throw new IllegalArgumentException("Invalid tolerance");
    
    // Check for sign change
    double fL = f.apply(l);
    double fR = f.apply(r);
    if (fL * fR > 0) throw new IllegalArgumentException("No zero guaranteed");
    
    // Check if bounds are already zeros
    if (Math.abs(fL) <= e) return l;
    if (Math.abs(fR) <= e) return r;
    
    // Recursive bisection
    return findZeroRecursive(f, l, r, e);
}

private static double findZeroRecursive(Function<Double, Double> f, double l, double r, double e) {
    double mid = (l + r) / 2.0;
    double fMid = f.apply(mid);
    
    // Check convergence
    if (Math.abs(fMid) <= e) return mid;
    
    // Prevent infinite recursion
    if (Math.abs(r - l) < 1e-15) return mid;
    
    // Choose subinterval
    double fL = f.apply(l);
    if (fL * fMid < 0) {
        return findZeroRecursive(f, l, mid, e);
    } else {
        return findZeroRecursive(f, mid, r, e);
    }
}
```

## Complexity Analysis

### Time Complexity
- **O(log((r-l)/ε))**: Each iteration halves the interval
- **Convergence Rate**: Linear convergence (error reduced by factor of 2 each iteration)

### Space Complexity
- **O(log((r-l)/ε))**: Recursion stack depth
- **Memory Usage**: Minimal additional memory per recursive call

### Convergence Guarantee
- **Guaranteed**: If f(l)f(r) ≤ 0 and f is continuous
- **Rate**: Error bound is (r-l)/2^n after n iterations

## Advantages and Disadvantages

### Advantages
1. **Guaranteed Convergence**: Always finds a zero if one exists
2. **Simple Implementation**: Easy to understand and implement
3. **Robust**: Works with any continuous function
4. **Predictable**: Known convergence rate and error bounds

### Disadvantages
1. **Slow Convergence**: Linear convergence rate
2. **Requires Sign Change**: f(l)f(r) ≤ 0 must be satisfied
3. **Recursion Overhead**: Stack space usage
4. **Single Root**: Finds only one root per interval

## Error Analysis

### Absolute Error
After n iterations, the absolute error is bounded by:
```
|error| ≤ (r - l) / 2^n
```

### Relative Error
For well-conditioned problems:
```
|relative_error| ≈ |absolute_error| / |root|
```

### Stopping Criteria
1. **Function Value**: |f(x)| ≤ ε
2. **Interval Size**: |r - l| ≤ ε
3. **Maximum Iterations**: Prevent infinite recursion

## Numerical Considerations

### Floating-Point Precision
- **Double Precision**: 15-17 significant digits
- **Machine Epsilon**: ~2.22 × 10^-16
- **Minimum Interval**: ~1e-15 to prevent infinite recursion

### Rounding Errors
- **Midpoint Calculation**: (l + r) / 2 can cause overflow
- **Function Evaluation**: Accumulated rounding errors
- **Sign Changes**: Near-zero values can cause issues

### Robustness
- **Input Validation**: Check bounds and tolerance
- **Sign Change Verification**: Ensure f(l)f(r) ≤ 0
- **Infinite Recursion Prevention**: Minimum interval size check

## Example Applications

### 1. Quadratic Function
```
f(x) = x² - 4
Interval: [0, 4]
Tolerance: 0.001
Result: x ≈ 2.0
```

### 2. Trigonometric Function
```
f(x) = sin(x)
Interval: [0, π]
Tolerance: 1e-6
Result: x ≈ π
```

### 3. Exponential Function
```
f(x) = e^x - 1
Interval: [-1, 1]
Tolerance: 1e-8
Result: x ≈ 0.0
```

## Comparison with Other Methods

### Newton's Method
- **Faster**: Quadratic convergence
- **Requires Derivative**: f'(x) needed
- **Not Guaranteed**: May not converge

### Secant Method
- **No Derivative**: Uses finite differences
- **Faster than Bisection**: Superlinear convergence
- **Not Guaranteed**: May not converge

### Fixed-Point Iteration
- **Simple**: g(x) = x - f(x)/f'(x)
- **Fast**: If convergent
- **Not Guaranteed**: Convergence depends on g'(x)

## Implementation Tips

### 1. Input Validation
```java
if (l >= r) throw new IllegalArgumentException("Invalid bounds");
if (e <= 0) throw new IllegalArgumentException("Invalid tolerance");
```

### 2. Sign Change Check
```java
if (fL * fR > 0) throw new IllegalArgumentException("No zero guaranteed");
```

### 3. Infinite Recursion Prevention
```java
if (Math.abs(r - l) < 1e-15) return mid;
```

### 4. Function Evaluation
```java
try {
    double fMid = f.apply(mid);
} catch (Exception e) {
    throw new ArithmeticException("Function evaluation failed");
}
```

## Testing Strategy

### Unit Tests
- **Basic Functions**: Linear, quadratic, cubic
- **Edge Cases**: Zero at bounds, very small intervals
- **Error Conditions**: Invalid inputs, no zero in interval
- **Convergence**: Various tolerance levels

### Integration Tests
- **Function Creation**: Expression parsing
- **UI Integration**: Input validation and error handling
- **Performance**: Execution time measurement

### Stress Tests
- **Very Small Tolerances**: 1e-15
- **Wide Intervals**: [0, 1000]
- **Complex Functions**: Trigonometric, exponential

## Conclusion

The recursive bisection method is a reliable and educational approach to root finding. While not the fastest method, it provides guaranteed convergence and is excellent for learning numerical methods and recursive algorithms. The implementation demonstrates important concepts in numerical analysis, error handling, and software engineering.
