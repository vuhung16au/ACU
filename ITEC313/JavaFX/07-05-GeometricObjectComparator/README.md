# Geometric Object Comparator Demo

A JavaFX application that demonstrates the use of `GeometricObjectComparator` to sort geometric objects by their area.

## Overview

This application showcases the implementation of a custom comparator for geometric objects, allowing users to:
- Create and visualize circles and rectangles
- Sort geometric objects by their area
- View detailed information about each object
- Interact with a modern JavaFX interface

## Features

- **Interactive UI**: Modern JavaFX interface with buttons and visual elements
- **Dynamic Object Creation**: Add random circles and rectangles with varying properties
- **Visual Representation**: See shapes rendered with colors and properties
- **Sorting Functionality**: Sort objects by area using the custom comparator
- **Detailed Information**: View area, perimeter, and other properties for each object

## Technical Specifications

### Development Environment
- **Java Version**: OpenJDK 24
- **JavaFX Version**: 21
- **Maven Version**: 3.9.x or later
- **Target Platform**: Cross-platform (macOS, Windows, Linux)

### Architecture
- **Package**: `com.acu.javafx.geometricobjectcomparator`
- **Main Class**: `GeometricObjectComparatorDemo`
- **Launcher**: `Launcher` (for Maven compatibility)

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/acu/javafx/geometricobjectcomparator/
│   │       ├── GeometricObject.java          # Abstract base class
│   │       ├── Circle.java                   # Circle implementation
│   │       ├── Rectangle.java                # Rectangle implementation
│   │       ├── GeometricObjectComparator.java # Custom comparator
│   │       ├── GeometricObjectComparatorDemo.java # Main JavaFX app
│   │       └── Launcher.java                 # Application launcher
│   └── resources/
│       └── (CSS files, images, etc.)
├── test/
│   └── java/
│       └── (unit tests)
pom.xml                                # Maven build configuration
run.sh                                 # Unix/Linux/macOS execution script
run.bat                                # Windows batch execution script
run_direct.sh                          # Direct Java execution script
README.md                              # Project documentation
```

## Build and Run Instructions

### Prerequisites

1. **Java 24**: Install OpenJDK 24 or later
2. **Maven 3.9+**: Install Apache Maven
3. **JavaFX**: Dependencies are managed by Maven

### Running the Application

#### Option 1: Using Maven (Recommended)
```bash
# Unix/Linux/macOS
./run.sh

# Windows
run.bat
```

#### Option 2: Direct Maven Commands
```bash
# Compile the project
mvn clean compile

# Run the application
mvn javafx:run
```

#### Option 3: Direct Java Execution
```bash
# First compile with Maven
mvn clean compile

# Then run directly with Java
./run_direct.sh
```

### Building for Distribution

```bash
# Create executable JAR
mvn clean package

# The JAR will be created in target/geometricobjectcomparator-1.0.0.jar
```

## Usage

1. **Launch the Application**: Run the application using one of the methods above
2. **Add Objects**: Click "Add Circle" or "Add Rectangle" to create new geometric objects
3. **Sort Objects**: Click "Sort by Area" to sort all objects by their area (ascending)
4. **Clear All**: Click "Clear All" to remove all objects and start fresh
5. **View Information**: The output area shows detailed information about each object

## Key Components

### GeometricObject (Abstract Class)
- Base class for all geometric objects
- Contains common properties: color, filled status, creation date
- Defines abstract methods: `getArea()` and `getPerimeter()`

### Circle and Rectangle
- Concrete implementations of `GeometricObject`
- Provide specific area and perimeter calculations
- Support color and fill properties

### GeometricObjectComparator
- Implements `Comparator<GeometricObject>`
- Compares objects based on their area
- Used for sorting collections of geometric objects

### GeometricObjectComparatorDemo
- Main JavaFX application class
- Provides interactive UI for object manipulation
- Demonstrates the comparator functionality

## Cross-Platform Compatibility

The project is designed to work on multiple platforms:

- **macOS**: Intel (x86_64) and Apple Silicon (ARM64)
- **Windows**: x86_64 and ARM64
- **Linux**: x86_64 and ARM64

The Maven configuration automatically detects the platform and includes the appropriate JavaFX dependencies.

## Dependencies

- **JavaFX Controls**: UI components (buttons, labels, etc.)
- **JavaFX Graphics**: Rendering and visualization
- **JavaFX Base**: Core JavaFX functionality
- **JavaFX FXML**: FXML support (if needed for future enhancements)

## Development Notes

- The application uses JavaFX for the user interface
- Geometric objects are rendered as visual shapes with colors
- The comparator demonstrates sorting by area in ascending order
- Random object generation provides variety for testing

## Troubleshooting

### Common Issues

1. **JavaFX not found**: Ensure JavaFX dependencies are properly downloaded by Maven
2. **Compilation errors**: Verify Java 24 is installed and in PATH
3. **Runtime errors**: Check that all dependencies are resolved

### Platform-Specific Notes

- **macOS**: Works on both Intel and Apple Silicon Macs
- **Windows**: Tested on x86_64, ARM64 support included
- **Linux**: Compatible with major distributions

## License

This project is part of the ITEC313 JavaFX course materials.

## Contributing

This is an educational project demonstrating JavaFX and comparator concepts. 