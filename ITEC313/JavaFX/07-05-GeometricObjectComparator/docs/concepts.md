# Geometric Object Comparator - Concepts

## Overview

This project demonstrates the implementation and use of a custom comparator for geometric objects. The application showcases object-oriented programming principles, JavaFX UI development, and sorting algorithms.

## Key Concepts

### 1. Comparator Pattern

The `GeometricObjectComparator` implements the `Comparator<GeometricObject>` interface, which is a functional interface that defines a comparison function for objects.

**Key Points:**
- Compares objects based on their area
- Returns positive value if first object has larger area
- Returns negative value if first object has smaller area
- Returns zero if areas are equal

**Implementation:**
```java
public int compare(GeometricObject o1, GeometricObject o2) {
    return o1.getArea() > o2.getArea() ? 
        1 : o1.getArea() == o2.getArea() ? 0 : -1;
}
```

### 2. Inheritance Hierarchy

The project demonstrates a classic inheritance hierarchy:

```
GeometricObject (abstract)
├── Circle
└── Rectangle
```

**Design Principles:**
- **Abstraction**: `GeometricObject` defines common interface
- **Inheritance**: `Circle` and `Rectangle` extend base class
- **Polymorphism**: Objects can be treated as `GeometricObject` instances

### 3. Abstract Classes

`GeometricObject` is an abstract class that:
- Cannot be instantiated directly
- Provides common functionality (color, filled status, date)
- Defines abstract methods that must be implemented by subclasses
- Serves as a template for concrete geometric objects

### 4. JavaFX UI Components

The application demonstrates various JavaFX UI patterns:

**Layout Management:**
- `VBox`: Vertical layout for main components
- `HBox`: Horizontal layout for buttons
- `BorderPane`: Complex layouts (if needed)

**Controls:**
- `Button`: User interaction triggers
- `Label`: Text display
- `TextArea`: Multi-line text output
- `Shape` classes: Visual representation

**Styling:**
- CSS-like styling with `setStyle()`
- Color management with `Color` class
- Font configuration with `Font` class

### 5. Collections and Sorting

The application uses Java Collections Framework:

**Key Collections:**
- `ArrayList<GeometricObject>`: Stores geometric objects
- `Collections.sort()`: Sorts using custom comparator

**Sorting Process:**
1. Create comparator instance
2. Apply sorting algorithm
3. Update UI to reflect new order

### 6. Event Handling

JavaFX event handling demonstrates:
- **Lambda expressions**: Modern event handler syntax
- **Event types**: Action events for button clicks
- **UI updates**: Synchronizing data with visual representation

### 7. Object Visualization

The application renders geometric objects as visual shapes:

**Visual Mapping:**
- `Circle` objects → `javafx.scene.shape.Circle`
- `Rectangle` objects → `javafx.scene.shape.Rectangle`
- Properties mapped to visual attributes (color, size)

**Scaling:**
- Mathematical dimensions scaled for visual display
- Maintains proportional relationships

## Design Patterns

### 1. Strategy Pattern
The comparator implements the Strategy pattern, allowing different sorting strategies to be applied to the same collection.

### 2. Observer Pattern
JavaFX uses the Observer pattern internally for event handling and UI updates.

### 3. Factory Pattern
The application creates objects dynamically based on user input, demonstrating factory-like behavior.

## Mathematical Concepts

### Area Calculations
- **Circle**: A = πr²
- **Rectangle**: A = width × height

### Perimeter Calculations
- **Circle**: P = 2πr
- **Rectangle**: P = 2(width + height)

## Educational Value

This project demonstrates:
1. **Object-Oriented Programming**: Inheritance, abstraction, polymorphism
2. **JavaFX Development**: Modern UI development with Java
3. **Algorithm Implementation**: Custom sorting with comparators
4. **Software Design**: Clean separation of concerns
5. **Cross-Platform Development**: Maven-based build system

## Best Practices Demonstrated

1. **Separation of Concerns**: UI logic separated from business logic
2. **Clean Code**: Well-documented, readable code
3. **Error Handling**: Graceful handling of edge cases
4. **Cross-Platform Compatibility**: Maven configuration for multiple platforms
5. **Documentation**: Comprehensive documentation and comments 