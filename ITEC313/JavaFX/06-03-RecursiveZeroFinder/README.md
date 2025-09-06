# Recursive Zero Finder - JavaFX Application

## Overview

This JavaFX application demonstrates the implementation of a recursive zero finder using the bisection method (binary search). The application finds the root of a function f(x) between two bounds l and r, where f(l)f(r) ≤ 0, using a recursive approach.

## Features

### 🎯 Core Functionality

1. **Recursive Zero Finding** - Implements the bisection method recursively
2. **Interactive GUI** - Modern JavaFX interface with intuitive controls
3. **Function Input** - Support for mathematical expressions (e.g., x^2 - 4, x*x - 4)
4. **Parameter Configuration** - Input fields for bounds and tolerance
5. **Real-time Results** - Live display of zero finding results and verification
6. **Error Handling** - Comprehensive error reporting and validation

### 🚀 Key Features

- **Educational Interface**: Perfect for learning recursive algorithms and numerical methods
- **Cross-platform**: Works on Windows, macOS, and Linux
- **Comprehensive Testing**: Full JUnit test suite with edge cases
- **Performance Monitoring**: Execution time measurement
- **Input Validation**: Robust error handling for invalid inputs
- **Clear Documentation**: Well-commented code for educational purposes

## Project Structure

```
06-03-RecursiveZeroFinder/
├── src/main/java/com/acu/javafx/zerofinder/
│   ├── RecursiveZeroFinder.java        # Core zero finding algorithm
│   └── ZeroFinderDemo.java             # JavaFX application
├── src/test/java/com/acu/javafx/zerofinder/
│   └── RecursiveZeroFinderTest.java    # JUnit test suite
├── docs/
│   ├── algorithm.md                    # Algorithm explanation
│   └── usage.md                        # Usage guide
├── images/                             # Screenshots and diagrams
├── pom.xml                            # Maven configuration
├── run.sh                             # Unix/Linux/macOS run script
├── run.bat                            # Windows run script
├── run-jar.sh                         # JAR run script
└── README.md                          # This file
```

## Quick Start

### Prerequisites

- **Java**: OpenJDK 24 or later
- **Maven**: 3.9.x or later
- **JavaFX**: 24 (included in dependencies)

### Running the Application

#### Option 1: Using Run Scripts (Recommended)
```bash
# Unix/Linux/macOS
./run.sh

# Windows
run.bat
```

#### Option 2: Direct Maven Commands
```bash
# Compile and run
mvn clean compile
mvn javafx:run

# Or run tests first
mvn test
mvn javafx:run
```

#### Option 3: Build and Run JAR
```bash
# Build executable JAR
mvn clean package

# Run from JAR
java -jar target/recursive-zero-finder-1.0.0.jar
```

## Usage Guide

### Getting Started

1. **Launch the Application**: Run using one of the methods above
2. **Enter Function**: Input a mathematical expression (e.g., `x*x - 4`)
3. **Set Bounds**: Specify the search interval [l, r]
4. **Set Tolerance**: Define the precision (e.g., 0.001)
5. **Find Zero**: Click "Find Zero" to compute the result
6. **View Results**: Check the results area for the zero and verification

### Example Usage

**Example 1: Quadratic Function**
- Function: `x*x - 4`
- Left bound: `0`
- Right bound: `4`
- Tolerance: `0.001`
- Expected result: `x ≈ 2.0`

**Example 2: Linear Function**
- Function: `x - 3`
- Left bound: `0`
- Right bound: `5`
- Tolerance: `0.0001`
- Expected result: `x = 3.0`

**Example 3: Cubic Function**
- Function: `x*x*x - 8`
- Left bound: `0`
- Right bound: `3`
- Tolerance: `0.001`
- Expected result: `x = 2.0`

### Supported Function Expressions

The application supports basic mathematical expressions:
- **Arithmetic**: `x + 2`, `x - 3`, `x * 2`, `x / 2`
- **Powers**: `x*x`, `x^2` (simplified)
- **Combinations**: `x*x - 4`, `x^2 - 4`

*Note: The current implementation uses a simplified expression parser. For complex expressions, consider using a proper mathematical expression library.*

## Algorithm Details

### Recursive Bisection Method

The algorithm implements the bisection method recursively:

1. **Input Validation**: Check bounds and tolerance
2. **Sign Check**: Verify f(l)f(r) ≤ 0 (guarantees a zero exists)
3. **Boundary Check**: Return bound if it's already a zero
4. **Recursive Bisection**:
   - Calculate midpoint: `mid = (l + r) / 2`
   - Evaluate function at midpoint: `f(mid)`
   - If `|f(mid)| ≤ ε`, return `mid`
   - If `f(l)f(mid) < 0`, search in `[l, mid]`
   - Otherwise, search in `[mid, r]`

### Complexity Analysis

- **Time Complexity**: O(log((r-l)/ε))
- **Space Complexity**: O(log((r-l)/ε)) (recursion stack)
- **Convergence**: Guaranteed if f(l)f(r) ≤ 0

### Error Handling

The algorithm handles various error conditions:
- Invalid bounds (l ≥ r)
- Non-positive tolerance
- No zero in interval (f(l)f(r) > 0)
- Function evaluation errors
- Infinite recursion prevention

## Testing

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=RecursiveZeroFinderTest

# Run with verbose output
mvn test -Dtest=RecursiveZeroFinderTest -X
```

### Test Coverage

The test suite covers:
- **Basic Functions**: Linear, quadratic, cubic, trigonometric, exponential
- **Edge Cases**: Zero at bounds, very small/large intervals
- **Error Conditions**: Invalid inputs, no zero in interval
- **Convergence**: Various tolerance levels
- **Function Creation**: Expression parsing and evaluation

### Test Examples

```java
// Test quadratic function
Function<Double, Double> f = x -> x * x - 4;
double zero = RecursiveZeroFinder.findZero(f, 0, 4, 1e-6);
assertEquals(2.0, zero, 1e-6);

// Test error handling
assertThrows(IllegalArgumentException.class, () -> {
    RecursiveZeroFinder.findZero(f, 0, 2, 1e-6); // No zero in [0,2]
});
```

## Technical Details

### Dependencies

- **JavaFX 24**: UI framework
- **JUnit 5**: Testing framework
- **Maven**: Build and dependency management

### Build Configuration

The project uses Maven with:
- **Java 24**: Target version
- **Cross-platform JavaFX**: Automatic platform detection
- **Maven plugins**: JavaFX Maven plugin, Surefire plugin, Shade plugin

### Platform Support

- **Windows**: x86_64, ARM64
- **macOS**: x86_64, ARM64
- **Linux**: x86_64, ARM64

## Development

### Building from Source

```bash
# Clone the repository
git clone <repository-url>
cd 06-03-RecursiveZeroFinder

# Build the project
mvn clean package

# Run tests
mvn test

# Run the application
mvn javafx:run
```

### Adding New Features

1. **New Function Types**: Extend the expression parser
2. **Additional Algorithms**: Implement other root-finding methods
3. **Visualization**: Add function plotting capabilities
4. **Export Results**: Save results to file

### Code Structure

- **RecursiveZeroFinder**: Core algorithm implementation
- **ZeroFinderDemo**: JavaFX application and UI
- **RecursiveZeroFinderTest**: Comprehensive test suite



### Debug Mode

Run with debug output:
```bash
mvn javafx:run -Djavafx.debug=true
```


## Screenshots

[06-03-RecursiveZeroFinder.png](images/06-03-RecursiveZeroFinder.png)
