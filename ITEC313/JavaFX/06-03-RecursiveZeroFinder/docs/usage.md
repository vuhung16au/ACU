# Usage Guide - Recursive Zero Finder

## Getting Started

### Launching the Application

1. **Using Run Scripts** (Recommended):
   ```bash
   # Unix/Linux/macOS
   ./run.sh
   
   # Windows
   run.bat
   ```

2. **Using Maven**:
   ```bash
   mvn clean compile
   mvn javafx:run
   ```

3. **Using JAR**:
   ```bash
   mvn clean package
   java -jar target/recursive-zero-finder-1.0.0.jar
   ```

### Application Interface

The application provides a clean, intuitive interface with:
- **Function Input Field**: Enter mathematical expressions
- **Bounds Input**: Specify search interval [l, r]
- **Tolerance Input**: Set precision (ε)
- **Action Buttons**: Find Zero, Clear, Exit
- **Results Area**: Display results and verification

## Input Guidelines

### Function Expressions

The application supports basic mathematical expressions:

#### Supported Formats
- **Arithmetic**: `x + 2`, `x - 3`, `x * 2`, `x / 2`
- **Powers**: `x*x`, `x^2` (simplified)
- **Combinations**: `x*x - 4`, `x^2 - 4`

#### Examples
```
x*x - 4          # Quadratic: x² - 4
x - 3            # Linear: x - 3
x*x*x - 8        # Cubic: x³ - 8
x*x + x - 6      # Quadratic: x² + x - 6
```

#### Limitations
- **Simple Parser**: Current implementation uses basic expression parsing
- **No Complex Functions**: sin, cos, log, etc. not supported
- **No Parentheses**: Complex grouping not supported
- **Single Variable**: Only 'x' is supported

### Bounds and Tolerance

#### Bounds (l, r)
- **Left Bound (l)**: Lower limit of search interval
- **Right Bound (r)**: Upper limit of search interval
- **Requirement**: l < r
- **Sign Change**: f(l)f(r) ≤ 0 (opposite signs or zero)

#### Tolerance (ε)
- **Precision**: Maximum allowed |f(x)| at the zero
- **Range**: Typically 0.001 to 1e-15
- **Balance**: Smaller = more precise, larger = faster

## Step-by-Step Usage

### Example 1: Finding Square Root of 4

1. **Launch Application**: Run using preferred method
2. **Enter Function**: `x*x - 4`
3. **Set Bounds**: Left = `0`, Right = `4`
4. **Set Tolerance**: `0.001`
5. **Click "Find Zero"**
6. **View Results**: Should find x ≈ 2.0

### Example 2: Finding Root of Linear Function

1. **Enter Function**: `x - 3`
2. **Set Bounds**: Left = `0`, Right = `5`
3. **Set Tolerance**: `0.0001`
4. **Click "Find Zero"**
5. **View Results**: Should find x = 3.0

### Example 3: Finding Cube Root of 8

1. **Enter Function**: `x*x*x - 8`
2. **Set Bounds**: Left = `0`, Right = `3`
3. **Set Tolerance**: `0.001`
4. **Click "Find Zero"**
5. **View Results**: Should find x = 2.0

## Understanding Results

### Result Display

The results area shows:
```
=== Zero Finding Results ===
Function: f(x) = x*x - 4
Search interval: [0.000000, 4.000000]
Tolerance: 0.001000
Found zero: x = 2.00000000
Function value at zero: f(2.00000000) = 0.00000000
Execution time: 0.123 ms

=== Verification ===
|f(2.00000000)| = 0.00000000
Tolerance check: 0.00000000 <= 0.001000 ? ✓ PASS
```

### Key Information
- **Found Zero**: The computed root
- **Function Value**: f(x) at the found zero
- **Execution Time**: Algorithm performance
- **Verification**: Confirms |f(x)| ≤ ε

## Common Use Cases

### 1. Educational Examples

#### Quadratic Equations
```
x² - 4 = 0          → x*x - 4
x² - 5x + 6 = 0     → x*x - 5*x + 6
x² + 2x - 3 = 0     → x*x + 2*x - 3
```

#### Linear Equations
```
x - 5 = 0           → x - 5
2x - 7 = 0          → 2*x - 7
x + 3 = 0           → x + 3
```

#### Cubic Equations
```
x³ - 8 = 0          → x*x*x - 8
x³ - 27 = 0         → x*x*x - 27
x³ - x = 0          → x*x*x - x
```

### 2. Numerical Analysis

#### Testing Convergence
- **Small Tolerance**: Test algorithm precision
- **Large Tolerance**: Test algorithm speed
- **Wide Intervals**: Test algorithm robustness

#### Comparing Methods
- **Bisection**: Guaranteed convergence
- **Newton's**: Faster but not guaranteed
- **Secant**: No derivative needed

### 3. Function Analysis

#### Finding Roots
- **Single Root**: Use appropriate interval
- **Multiple Roots**: Find each separately
- **No Roots**: Check sign change requirement

#### Verification
- **Substitute**: Check f(x) = 0
- **Graph**: Visualize function behavior
- **Analytical**: Compare with known solutions

## Error Handling

### Common Errors

#### 1. "No zero guaranteed in interval"
**Cause**: f(l)f(r) > 0 (same signs)
**Solution**: Choose interval with sign change

#### 2. "Left bound must be less than right bound"
**Cause**: l ≥ r
**Solution**: Ensure l < r

#### 3. "Tolerance must be positive"
**Cause**: ε ≤ 0
**Solution**: Use positive tolerance

#### 4. "Cannot evaluate function"
**Cause**: Invalid expression
**Solution**: Check function syntax

### Error Prevention

#### Input Validation
- **Check Bounds**: Ensure l < r
- **Check Tolerance**: Ensure ε > 0
- **Check Function**: Test expression syntax
- **Check Sign Change**: Verify f(l)f(r) ≤ 0

#### Best Practices
- **Start Simple**: Use basic functions first
- **Test Bounds**: Verify sign change
- **Reasonable Tolerance**: Balance precision and speed
- **Clear Results**: Check verification output

## Advanced Usage

### Performance Optimization

#### Tolerance Selection
- **High Precision**: Use small ε (1e-10)
- **Fast Results**: Use larger ε (0.001)
- **Balanced**: Use moderate ε (1e-6)

#### Interval Selection
- **Narrow Intervals**: Faster convergence
- **Wide Intervals**: More robust
- **Optimal**: Just wide enough to contain root

### Function Design

#### Well-Conditioned Functions
- **Smooth**: Continuous derivatives
- **Monotonic**: Single root per interval
- **Bounded**: Reasonable function values

#### Avoiding Issues
- **Multiple Roots**: Use narrow intervals
- **Near-Zero Slopes**: Use smaller tolerance
- **Discontinuities**: Avoid function breaks

## Troubleshooting

### Application Issues

#### 1. Application Won't Start
**Check**: Java version (requires Java 24+)
**Check**: Maven installation
**Check**: JavaFX dependencies

#### 2. Compilation Errors
**Check**: Java version compatibility
**Check**: Maven configuration
**Check**: Dependencies

#### 3. Runtime Errors
**Check**: Function expression syntax
**Check**: Input validation
**Check**: Error messages

### Algorithm Issues

#### 1. No Convergence
**Check**: Sign change requirement
**Check**: Function continuity
**Check**: Interval bounds

#### 2. Wrong Results
**Check**: Function expression
**Check**: Interval selection
**Check**: Tolerance setting

#### 3. Performance Issues
**Check**: Tolerance size
**Check**: Interval width
**Check**: Function complexity

## Tips and Tricks

### Efficient Usage
1. **Start with Examples**: Use provided examples first
2. **Test Simple Functions**: Begin with linear functions
3. **Verify Results**: Always check the verification output
4. **Use Appropriate Tolerance**: Balance precision and speed

### Educational Value
1. **Understand Algorithm**: Study the bisection method
2. **Compare Methods**: Try different root-finding approaches
3. **Analyze Convergence**: Observe how tolerance affects results
4. **Explore Functions**: Test various mathematical functions

### Best Practices
1. **Input Validation**: Always check inputs before running
2. **Error Handling**: Read and understand error messages
3. **Result Verification**: Confirm results make sense
4. **Documentation**: Keep notes of interesting examples

## Conclusion

The Recursive Zero Finder provides an excellent platform for learning numerical methods and recursive algorithms. By following this usage guide, you can effectively use the application for educational purposes, numerical analysis, and function exploration. Remember to start with simple examples and gradually work toward more complex problems.
