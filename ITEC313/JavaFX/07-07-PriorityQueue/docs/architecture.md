# PriorityQueue JavaFX Architecture

## Overview

This document describes the architecture and design patterns used in the PriorityQueue JavaFX demo application.

## System Architecture

### High-Level Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    JavaFX Application                      │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────┐ │
│  │   UI Layer      │  │  Business Logic │  │  Data Layer │ │
│  │                 │  │                 │  │             │ │
│  │ • Scene         │  │ • PriorityQueue │  │ • String    │ │
│  │ • Controls      │  │ • Comparators   │  │   Elements  │ │
│  │ • Layout        │  │ • Operations    │  │ • Queue     │ │
│  │ • Event Handlers│  │                 │  │   State     │ │
│  └─────────────────┘  └─────────────────┘  └─────────────┘ │
└─────────────────────────────────────────────────────────────┘
```

## Component Design

### 1. Application Entry Point

#### `Launcher.java`
- **Purpose**: Application entry point for JavaFX
- **Responsibility**: Delegates to main application class
- **Design Pattern**: Facade Pattern

```java
public class Launcher {
    public static void main(String[] args) {
        PriorityQueueJavaFXApp.main(args);
    }
}
```

### 2. Main Application Class

#### `PriorityQueueJavaFXApp.java`
- **Purpose**: Main JavaFX application controller
- **Responsibility**: UI management and business logic coordination
- **Design Pattern**: MVC (Model-View-Controller)

**Key Components:**
- **Model**: Two PriorityQueue instances
- **View**: JavaFX Scene with controls
- **Controller**: Event handlers and business logic

### 3. Console Demo Class

#### `PriorityQueueDemo.java`
- **Purpose**: Original console-based demonstration
- **Responsibility**: Standalone PriorityQueue demonstration
- **Design Pattern**: Simple demonstration pattern

## Design Patterns Used

### 1. Model-View-Controller (MVC)

**Model:**
```java
private PriorityQueue<String> queue1;  // Comparable-based
private PriorityQueue<String> queue2;  // Comparator-based
```

**View:**
```java
private TextArea outputArea;
private TextField inputField;
private Button addButton, demoButton1, demoButton2, clearButton;
```

**Controller:**
```java
private void addToQueues() { /* ... */ }
private void demoComparableQueue() { /* ... */ }
private void demoComparatorQueue() { /* ... */ }
private void clearQueues() { /* ... */ }
```

### 2. Observer Pattern

JavaFX uses the Observer pattern internally:
```java
addButton.setOnAction(e -> addToQueues());
demoButton1.setOnAction(e -> demoComparableQueue());
demoButton2.setOnAction(e -> demoComparatorQueue());
clearButton.setOnAction(e -> clearQueues());
```

### 3. Strategy Pattern

Different queue ordering strategies:
```java
// Strategy 1: Natural ordering
PriorityQueue<String> queue1 = new PriorityQueue<>();

// Strategy 2: Custom comparator
PriorityQueue<String> queue2 = new PriorityQueue<>(4, Collections.reverseOrder());
```

## UI Architecture

### Layout Hierarchy

```
VBox (root)
├── Label (title)
├── HBox (input section)
│   ├── Label (input label)
│   ├── TextField (input field)
│   └── Button (add button)
├── HBox (button section)
│   ├── Button (demo comparable)
│   ├── Button (demo comparator)
│   └── Button (clear)
└── TextArea (output)
```

### Responsive Design

- **Flexible Layout**: Uses VBox and HBox for responsive arrangement
- **Dynamic Sizing**: Components adapt to window size
- **Consistent Spacing**: Uniform padding and gaps

## Data Flow

### 1. Input Flow
```
User Input → TextField → Event Handler → Business Logic → Queue Update → UI Update
```

### 2. Output Flow
```
Queue Operation → Business Logic → Output Generation → TextArea Update → User View
```

### 3. Demo Flow
```
Button Click → Event Handler → Queue Processing → Result Display → UI Update
```

## Error Handling

### Input Validation
```java
private void addToQueues() {
    String input = inputField.getText().trim();
    if (!input.isEmpty()) {
        // Process input
    }
}
```

### Queue State Management
```java
private void demoComparableQueue() {
    PriorityQueue<String> tempQueue = new PriorityQueue<>(queue1);
    // Use temporary queue to avoid modifying original
}
```

## Cross-Platform Considerations

### 1. Platform Detection
Maven profiles automatically detect platform:
```xml
<profile>
    <id>mac-aarch64</id>
    <activation>
        <os>
            <family>mac</family>
            <arch>aarch64</arch>
        </os>
    </activation>
</profile>
```

### 2. JavaFX Module Management
```xml
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-controls</artifactId>
    <classifier>${javafx.platform}</classifier>
</dependency>
```

## Build Architecture

### Maven Configuration
- **Multi-platform support**: Automatic platform detection
- **Dependency management**: JavaFX modules with platform-specific classifiers
- **Plugin configuration**: JavaFX Maven plugin for execution

### Build Scripts
- **`run.sh`**: Unix/Linux/macOS execution
- **`run.bat`**: Windows execution
- **`run_direct.sh`**: Direct Java execution (optional)

## Performance Considerations

### 1. Memory Management
- **Temporary queues**: Used for demonstrations to preserve original state
- **Efficient updates**: Minimal UI updates during operations
- **Garbage collection**: Proper cleanup of temporary objects

### 2. UI Responsiveness
- **Event-driven**: Non-blocking UI operations
- **Immediate feedback**: Real-time updates to user actions
- **Error prevention**: Input validation before processing

### 3. Scalability
- **Modular design**: Easy to extend with new features
- **Separation of concerns**: Clear boundaries between components
- **Configurable**: Easy to modify queue behaviors

## Testing Strategy

### 1. Unit Testing
- **Queue operations**: Test individual PriorityQueue methods
- **Business logic**: Test queue manipulation functions
- **Input validation**: Test edge cases and error conditions

### 2. Integration Testing
- **UI integration**: Test user interactions
- **Cross-platform**: Test on different operating systems
- **Build verification**: Test build and deployment process

### 3. User Acceptance Testing
- **Interactive testing**: Manual testing of all features
- **Usability testing**: Verify intuitive user experience
- **Educational value**: Ensure learning objectives are met

## Future Enhancements

### 1. Additional Queue Types
- **Custom comparators**: More complex ordering strategies
- **Object queues**: PriorityQueue with custom objects
- **Performance metrics**: Timing and efficiency measurements

### 2. Advanced UI Features
- **Visual queue representation**: Graphical display of queue state
- **Animation**: Animated queue operations
- **Export functionality**: Save/load queue states

### 3. Educational Features
- **Step-by-step mode**: Detailed operation explanations
- **Algorithm visualization**: Show internal heap structure
- **Performance comparison**: Compare different queue implementations

## Security Considerations

### 1. Input Sanitization
- **String validation**: Prevent malicious input
- **Length limits**: Prevent memory issues
- **Character encoding**: Handle special characters properly

### 2. Resource Management
- **Memory limits**: Prevent excessive memory usage
- **Queue size limits**: Prevent performance degradation
- **Cleanup procedures**: Proper resource disposal

This architecture provides a solid foundation for the PriorityQueue demonstration while maintaining flexibility for future enhancements and cross-platform compatibility. 