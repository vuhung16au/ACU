# Hilbert Curve Algorithm Documentation

## Overview

The Hilbert curve is a space-filling curve that visits every point in a square grid exactly once. This document explains the algorithm implementation used in this project.

## Mathematical Background

### Definition

A Hilbert curve of order n is a continuous curve that fills a 2^n × 2^n grid, visiting each cell exactly once. The curve is constructed recursively by combining four smaller Hilbert curves of order n-1.

### Properties

- **Space-filling**: Visits every point in the grid
- **Locality-preserving**: Nearby points in the curve are close in space
- **Self-similar**: Higher orders contain patterns from lower orders
- **Deterministic**: Same input always produces the same curve

## Algorithm Implementation

### Recursive Approach

The algorithm uses a recursive divide-and-conquer approach:

1. **Base Case**: For order 0, we have a single point
2. **Recursive Case**: For order n, we divide the space into 4 quadrants and recursively generate curves for each quadrant
3. **Connection**: Connect the quadrants with specific patterns

### Core Algorithm

```java
public static List<Point> generateHilbertCurve(int order, double width, double height) {
    // Validate input
    if (order < 1 || order > 6) {
        throw new IllegalArgumentException("Order must be between 1 and 6");
    }
    
    List<Point> points = new ArrayList<>();
    int gridSize = (int) Math.pow(2, order);
    
    // Calculate step size
    double stepX = width / gridSize;
    double stepY = height / gridSize;
    
    // Generate curve recursively
    generateHilbertCurveRecursive(order, 0, 0, 0, 0, 0, points, stepX, stepY);
    
    return points;
}
```

### Recursive Generation

The recursive method handles four different directions:

- **Direction 0 (Right)**: Moving horizontally to the right
- **Direction 1 (Down)**: Moving vertically downward
- **Direction 2 (Left)**: Moving horizontally to the left
- **Direction 3 (Up)**: Moving vertically upward

Each direction has a specific pattern for connecting the four quadrants.

### Alternative Implementation

The project also includes an alternative implementation using the Hilbert index-to-coordinate mapping:

```java
public static List<Point> generateHilbertCurveAlternative(int order, double width, double height) {
    List<Point> points = new ArrayList<>();
    int gridSize = (int) Math.pow(2, order);
    int totalPoints = gridSize * gridSize;
    
    for (int i = 0; i < totalPoints; i++) {
        int[] coords = hilbertIndexToXY(i, order);
        points.add(new Point(coords[0] * stepX + stepX / 2, coords[1] * stepY + stepY / 2));
    }
    
    return points;
}
```

## Complexity Analysis

### Time Complexity

- **Generation**: O(4^n) where n is the order
- **Point Count**: 2^(2n) points for order n
- **Space Complexity**: O(2^(2n)) for storing all points

### Performance Characteristics

| Order | Points | Time Complexity | Memory Usage |
|-------|--------|----------------|--------------|
| 1 | 4 | O(1) | Minimal |
| 2 | 16 | O(16) | Low |
| 3 | 64 | O(64) | Low |
| 4 | 256 | O(256) | Medium |
| 5 | 1,024 | O(1,024) | Medium |
| 6 | 4,096 | O(4,096) | High |

## Validation

### Curve Validation

The algorithm includes validation to ensure curve properties:

```java
public static boolean validateHilbertCurve(List<Point> points, int order) {
    int expectedCount = getPointCount(order);
    if (points.size() != expectedCount) {
        return false;
    }
    
    // Check that all points are unique
    return points.size() == points.stream().distinct().count();
}
```

### Validation Checks

1. **Point Count**: Verify correct number of points
2. **Uniqueness**: Ensure no duplicate points
3. **Bounds**: Check points are within drawing area
4. **Continuity**: Verify consecutive points are close

## Examples

### Order 1 (4 points)
```
┌─┐
│ │
└─┘
```

### Order 2 (16 points)
```
┌─┬─┐
│ │ │
├─┼─┤
│ │ │
└─┴─┘
```

### Order 3 (64 points)
More complex pattern with 8×8 grid.

## Implementation Details

### Coordinate System

- **Origin**: Top-left corner (0,0)
- **X-axis**: Increases to the right
- **Y-axis**: Increases downward
- **Grid**: 2^n × 2^n cells
- **Points**: Centered in each cell

### Step Size Calculation

```java
double stepX = width / gridSize;
double stepY = height / gridSize;
```

This ensures the curve fits within the drawing area while maintaining proper proportions.

### Point Generation

Points are generated in order, creating a continuous path through the grid. Each point represents the center of a grid cell.

## Testing

### Test Coverage

The algorithm is thoroughly tested with:

1. **All Orders**: Tests for orders 1-6
2. **Point Count**: Verification of correct point counts
3. **Validation**: Curve property validation
4. **Edge Cases**: Invalid inputs and boundary conditions
5. **Performance**: Different canvas sizes and orders

### Test Cases

- Valid order range (1-6)
- Invalid order range (0, 7, negative)
- Point count verification
- Curve validation
- Alternative implementation comparison
- Different canvas sizes

