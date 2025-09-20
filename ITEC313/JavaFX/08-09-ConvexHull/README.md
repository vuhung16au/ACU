# Convex Hull Algorithm Demo - JavaFX Application

## Overview

This JavaFX application demonstrates the Convex Hull algorithm using the Graham Scan method. The convex hull is the smallest convex polygon that contains all the given points. This application provides an interactive way to explore and understand the convex hull concept through a modern, user-friendly interface.

## Features

### 🎯 Core Functionality

1. **Interactive Point Addition** - Click on the canvas to add points
2. **Random Point Generation** - Generate random points for quick testing
3. **Convex Hull Calculation** - Find the convex hull using Graham Scan algorithm
4. **Visual Display** - See points and hull edges with different colors
5. **Hull Information** - Display area, perimeter, and vertex count

### 🚀 Key Features

- **Interactive GUI**: Modern JavaFX interface with intuitive controls
- **Real-time Visualization**: Live display of points and convex hull
- **Algorithm Information**: Shows hull area, perimeter, and vertex count
- **Error Handling**: Comprehensive error reporting and validation
- **Cross-platform**: Works on Windows, macOS, and Linux
- **Educational**: Perfect for learning convex hull concepts

## Project Structure

```
08-09-ConvexHull/
├── src/main/java/com/acu/javafx/convexhull/
│   ├── ConvexHullDemo.java              # Main JavaFX application
│   ├── ConvexHullAlgorithm.java         # Graham Scan algorithm implementation
│   └── Point.java                       # Point class for 2D coordinates
├── src/test/java/com/acu/javafx/convexhull/
│   ├── ConvexHullAlgorithmTest.java     # Algorithm tests
│   └── PointTest.java                   # Point class tests
├── docs/
│   ├── algorithm.md                     # Algorithm explanation
│   └── usage.md                         # Usage guide
├── images/                              # Screenshots and diagrams
├── pom.xml                             # Maven configuration
└── README.md                           # This file
```

## Quick Start

### Prerequisites

- **Java**: OpenJDK 24 or later
- **Maven**: 3.9.x or later
- **JavaFX**: 24 (included in dependencies)

### Running the Application

#### Option 1: Using Maven (Recommended)
```bash
# Navigate to project directory
cd 08-09-ConvexHull

# Run the application
mvn clean javafx:run
```

#### Option 2: Build and Run
```bash
# Build the project
mvn clean package

# Run the application
java -jar target/convex-hull-demo-1.0.jar
```

#### Option 3: Run Tests
```bash
# Run all tests
mvn test

# Run specific test
mvn test -Dtest=ConvexHullAlgorithmTest
```

## Usage Guide

### Getting Started

1. **Launch the Application**: Run the application using one of the methods above
2. **Add Points**: Click on the canvas to add points, or use "Add Random Points"
3. **Find Convex Hull**: Click "Find Convex Hull" to see the result
4. **Explore**: Use other controls to add/remove points and clear the canvas

### Understanding the Interface

#### Controls Panel
- **Number of points**: Input field for random point generation
- **Find Convex Hull**: Calculates and displays the convex hull
- **Add Random Points**: Generates random points for testing
- **Add Point**: Adds a single random point
- **Remove Point**: Removes the last added point
- **Clear All**: Clears all points and hull

#### Status Panel
- **Status**: Shows current operation status
- **Hull Information**: Displays points count, hull vertices, area, and perimeter

### Algorithm Explanation

The application uses the **Graham Scan algorithm** to find the convex hull:

1. **Find Bottom Point**: Locate the bottom-most (and leftmost in case of tie) point
2. **Sort by Polar Angle**: Sort all other points by their polar angle with respect to the bottom point
3. **Graham Scan**: Process points in order, removing points that make right turns
4. **Result**: The remaining points form the convex hull

#### Key Concepts

- **Convex Hull**: The smallest convex polygon containing all points
- **Graham Scan**: Efficient O(n log n) algorithm for finding convex hull
- **Polar Angle**: Angle between the reference point and each point
- **Cross Product**: Used to determine turn direction (left/right)

## Technical Details

### Algorithm Complexity

- **Time Complexity**: O(n log n) - dominated by sorting
- **Space Complexity**: O(n) - for storing points and hull
- **Sorting**: Points are sorted by polar angle
- **Scanning**: Linear scan through sorted points

### Key Classes

#### Point Class
- Represents a 2D point with x, y coordinates
- Includes distance calculation and cross product methods
- Supports equality and hashing for collections

#### ConvexHullAlgorithm Class
- Implements the Graham Scan algorithm
- Provides utility methods for hull analysis
- Handles edge cases and error conditions

#### ConvexHullDemo Class
- Main JavaFX application
- Handles user interactions and visualization
- Manages the application state

### Testing

The project includes comprehensive JUnit tests:

- **PointTest**: Tests Point class functionality
- **ConvexHullAlgorithmTest**: Tests algorithm correctness
- **Edge Cases**: Handles insufficient points, duplicates, collinear points
- **Validation**: Ensures proper error handling

## Development

### Building from Source

```bash
# Clone the repository
git clone <repository-url>
cd 08-09-ConvexHull

# Build the project
mvn clean package

# Run tests
mvn test

# Run the application
mvn javafx:run
```

### Adding New Features

1. Create new classes in the `convexhull` package
2. Implement the feature logic
3. Add corresponding tests
4. Update documentation

### Project Configuration

The project uses Maven with:
- **Java 24**: Target version
- **JavaFX 24**: UI framework
- **Cross-platform support**: Automatic platform detection
- **JUnit 5**: Testing framework

## Troubleshooting

### Common Issues

#### 1. JavaFX Not Found
**Solution**: Ensure JavaFX dependencies are properly configured in `pom.xml`

#### 2. Insufficient Points Error
**Solution**: Add at least 3 points before finding the convex hull

#### 3. Out of Memory
**Solution**: Increase JVM heap size: `java -Xmx2g -jar target/convex-hull-demo-1.0.jar`

#### 4. Platform-Specific Issues
**Solution**: Use the appropriate Maven profile for your platform

### Debug Mode

Run with debug output:
```bash
mvn javafx:run -Djavafx.debug=true
```

## Algorithm Details

### Graham Scan Algorithm

The Graham Scan is a method for finding the convex hull of a finite set of points in the plane:

1. **Find the bottom-most point** (and leftmost in case of tie)
2. **Sort points by polar angle** with respect to the bottom point
3. **Process points in order**:
   - Add point to hull
   - Remove points that make right turns
   - Continue until all points processed

### Cross Product

The cross product is used to determine turn direction:
- **Positive**: Counter-clockwise turn
- **Negative**: Clockwise turn  
- **Zero**: Collinear points

### Time Complexity Analysis

- **Sorting**: O(n log n) - sorting points by polar angle
- **Scanning**: O(n) - linear scan through sorted points
- **Total**: O(n log n)

## Screenshots

The application provides a clean, intuitive interface for exploring convex hull concepts with real-time visualization and interactive controls.

## License

This project is part of the JavaFX Course Projects collection and follows the same licensing terms.
