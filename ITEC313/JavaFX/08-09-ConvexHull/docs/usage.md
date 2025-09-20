# Convex Hull Demo - Usage Guide

## Getting Started

### Launching the Application

1. **Using Maven** (Recommended):
   ```bash
   cd 08-09-ConvexHull
   mvn clean javafx:run
   ```

2. **Using JAR file**:
   ```bash
   mvn clean package
   java -jar target/convex-hull-demo-1.0.jar
   ```

### Interface Overview

The application window is divided into three main sections:

- **Left Panel**: Controls and instructions
- **Center**: Interactive canvas for points and hull
- **Right Panel**: Status and hull information

## Basic Usage

### Adding Points

#### Method 1: Click to Add
1. Click anywhere on the white canvas
2. A black dot will appear at the clicked location
3. Continue clicking to add more points

#### Method 2: Random Points
1. Enter the number of points in the "Number of points" field
2. Click "Add Random Points" button
3. Random points will be generated on the canvas

#### Method 3: Single Random Point
1. Click "Add Point" button
2. A single random point will be added

### Finding the Convex Hull

1. Ensure you have at least 3 points on the canvas
2. Click "Find Convex Hull" button
3. The convex hull will be displayed as red lines connecting the hull vertices
4. Hull vertices will be highlighted in red
5. Hull information will be displayed in the status panel

### Managing Points

#### Removing Points
- Click "Remove Point" to remove the last added point
- Continue clicking to remove more points

#### Clearing All
- Click "Clear All" to remove all points and hull
- The canvas will be reset to its initial state

## Understanding the Display

### Visual Elements

#### Points
- **Black dots**: All points on the canvas
- **Red dots**: Points that are part of the convex hull
- **Size**: Points are drawn as small circles for visibility

#### Convex Hull
- **Red lines**: Edges of the convex hull
- **Thickness**: Hull edges are drawn with thicker lines
- **Color**: Red to distinguish from points

#### Canvas
- **White background**: Clean canvas for point visualization
- **Black border**: Canvas boundaries
- **Size**: 600x400 pixels for optimal viewing

### Status Information

#### Status Label
- Shows current operation status
- Displays error messages if any
- Updates in real-time

#### Hull Information
- **Points**: Total number of points on canvas
- **Convex Hull**: Number of hull vertices
- **Area**: Area of the convex hull
- **Perimeter**: Perimeter of the convex hull

## Advanced Usage

### Testing Different Scenarios

#### Simple Shapes
1. Add points to form a square
2. Find the convex hull
3. Observe that the hull matches the square

#### Complex Shapes
1. Add points in a random pattern
2. Find the convex hull
3. Notice how interior points are excluded

#### Edge Cases
1. Try with exactly 3 points (triangle)
2. Try with collinear points
3. Observe how the algorithm handles these cases

### Educational Exercises

#### Exercise 1: Basic Understanding
1. Add 4 points to form a square
2. Add a point inside the square
3. Find the convex hull
4. Notice that the interior point is not part of the hull

#### Exercise 2: Algorithm Behavior
1. Add points in a line
2. Find the convex hull
3. Observe that only the endpoints are included

#### Exercise 3: Performance
1. Add many random points (50+)
2. Find the convex hull
3. Observe the algorithm's efficiency

## Troubleshooting

### Common Issues

#### "Need at least 3 points" Error
- **Cause**: Trying to find hull with insufficient points
- **Solution**: Add more points before finding the hull

#### "Invalid number" Error
- **Cause**: Invalid input in the number field
- **Solution**: Enter a valid positive integer

#### Application Won't Start
- **Cause**: JavaFX dependencies not found
- **Solution**: Ensure JavaFX is properly installed and configured

#### Points Not Appearing
- **Cause**: Clicking outside the canvas area
- **Solution**: Click within the white canvas area

### Performance Issues

#### Slow Response with Many Points
- **Cause**: Large number of points (100+)
- **Solution**: Use fewer points for better performance

#### Memory Issues
- **Cause**: Too many points in memory
- **Solution**: Clear points periodically or restart the application

### Getting Help

#### Error Messages
- Read the status label for error descriptions
- Check the console output for detailed error information

#### Algorithm Issues
- Refer to the algorithm documentation
- Check the test cases for expected behavior

## Tips and Best Practices

### Efficient Usage

#### Point Management
- Use "Add Random Points" for quick testing
- Clear points when switching between different scenarios
- Use "Remove Point" to fine-tune point placement

#### Visualization
- Use different point distributions to understand the algorithm
- Try regular shapes (squares, triangles) first
- Experiment with irregular patterns

#### Learning
- Start with simple cases (3-5 points)
- Gradually increase complexity
- Observe how the hull changes with different point sets

### Educational Value

#### Understanding Concepts
- **Convex Hull**: The smallest convex polygon containing all points
- **Graham Scan**: The algorithm used to find the hull
- **Computational Geometry**: The field of study this belongs to

#### Algorithm Analysis
- **Time Complexity**: O(n log n) for sorting
- **Space Complexity**: O(n) for storing points
- **Efficiency**: Compare with other algorithms

## Conclusion

The Convex Hull Demo provides an interactive way to explore computational geometry concepts. By experimenting with different point distributions and observing the algorithm's behavior, users can gain a deeper understanding of convex hulls and their applications in computer science and mathematics.
