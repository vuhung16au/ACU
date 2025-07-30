# Architecture Documentation

## Overview

The JavaFX Sets and Maps Demo is a comprehensive application that demonstrates various Java Collections Framework concepts, specifically focusing on Sets and Maps. The application provides a graphical user interface to interact with different implementations of these data structures.

## Architecture Components

### 1. Core Java Classes

#### Sets Demonstrations
- **TestHashSet**: Demonstrates HashSet functionality with string elements
- **TestLinkedHashSet**: Shows LinkedHashSet behavior maintaining insertion order
- **TestTreeSet**: Illustrates TreeSet with natural ordering and navigable set methods
- **TestTreeSetWithComparator**: Shows TreeSet with custom comparator for geometric objects

#### Maps Demonstrations
- **TestMap**: Demonstrates HashMap, TreeMap, and LinkedHashMap functionality
- **CountOccurrenceOfWords**: Shows word counting using TreeMap

#### Supporting Classes
- **GeometricObject**: Abstract base class for geometric shapes
- **Circle**: Concrete implementation of GeometricObject
- **Rectangle**: Concrete implementation of GeometricObject
- **GeometricObjectComparator**: Custom comparator for geometric objects

#### Performance Testing
- **SetListPerformanceTest**: Compares performance of different collection types

#### Utility Classes
- **CountKeywords**: Demonstrates keyword counting in Java source files

### 2. JavaFX Application

#### Main Application Class
- **SetsAndMapsDemo**: The main JavaFX application that provides the GUI

#### UI Components
- **Button Grid**: 8 buttons for different demonstrations
- **Text Area**: Output display for demonstration results
- **File Chooser**: For selecting Java source files
- **Scroll Pane**: For scrolling through output

### 3. Build System

#### Maven Configuration
- **Cross-platform profiles**: Automatic platform detection
- **JavaFX dependencies**: Platform-specific classifiers
- **Maven plugins**: Compiler, JavaFX, and Shade plugins

#### Build Scripts
- **run.sh**: Unix/Linux/macOS execution script
- **run.bat**: Windows batch execution script
- **run_direct.sh**: Direct Java execution without Maven

## Design Patterns

### 1. MVC Pattern
- **Model**: Individual demonstration classes (TestHashSet, TestMap, etc.)
- **View**: JavaFX UI components
- **Controller**: SetsAndMapsDemo class handling user interactions

### 2. Strategy Pattern
- Different collection implementations demonstrate different strategies for data storage
- Custom comparators provide different sorting strategies

### 3. Factory Pattern
- GeometricObject hierarchy demonstrates factory pattern for creating different shapes

## Cross-Platform Compatibility

### Platform Detection
The Maven configuration uses profiles to automatically detect the target platform:
- **macOS**: Uses `mac` classifier
- **Windows**: Uses `win` classifier  
- **Linux**: Uses `linux` classifier

### Architecture Support
- **x86_64**: Intel/AMD 64-bit processors
- **ARM64**: Apple Silicon, ARM64 Windows, ARM64 Linux

## Performance Considerations

### Threading
- Performance tests run in background threads to prevent UI blocking
- Platform.runLater() used for UI updates from background threads

### Memory Management
- Proper cleanup of resources in demonstration methods
- Efficient string handling in word counting examples

## Error Handling

### Exception Management
- Try-catch blocks in all demonstration methods
- Graceful error reporting to UI
- File existence validation for file operations

### User Input Validation
- File chooser with proper extension filters
- Input validation for file operations

## Extensibility

### Adding New Demonstrations
1. Create new demonstration class
2. Add button to UI
3. Implement event handler
4. Update documentation

### Custom Comparators
- GeometricObjectComparator shows how to create custom comparators
- Pattern can be extended for other object types

## Security Considerations

### File Operations
- File chooser restricts to Java files only
- Path validation before file operations
- Exception handling for file access errors

### Input Validation
- Proper string handling to prevent injection attacks
- Validation of file paths and user inputs 