# Architecture Documentation

This document provides a detailed overview of the JavaFX Sorting Algorithm Visualizer architecture, including design patterns, component relationships, and technical implementation details.

## System Overview

The application follows a layered architecture pattern with clear separation of concerns:

```
┌─────────────────────────────────────┐
│           Presentation Layer        │
│        (JavaFX UI Components)      │
├─────────────────────────────────────┤
│           Business Logic Layer      │
│        (Sorting Algorithms)        │
├─────────────────────────────────────┤
│           Data Layer               │
│        (Array Management)          │
└─────────────────────────────────────┘
```

## Component Architecture

### 1. Presentation Layer

#### SortingDemo.java
**Primary Component**: Main JavaFX application class

**Responsibilities**:
- Application lifecycle management
- UI component creation and layout
- User interaction handling
- Animation coordination
- State management

**Key Methods**:
```java
public class SortingDemo extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Application initialization
    }
    
    private VBox createMainLayout() {
        // Main UI layout creation
    }
    
    private HBox createControls() {
        // Control panel creation
    }
    
    private VBox createArrayDisplay() {
        // Array visualization creation
    }
}
```

**Design Patterns Used**:
- **MVC Pattern**: Separates UI logic from business logic
- **Observer Pattern**: Event-driven UI updates
- **Factory Pattern**: UI component creation

#### UI Component Hierarchy
```
SortingDemo
├── VBox (Main Layout)
│   ├── Text (Title)
│   ├── HBox (Controls)
│   │   ├── ComboBox (Algorithm Selection)
│   │   ├── Slider (Speed Control)
│   │   ├── Button (Sort)
│   │   ├── Button (Reset)
│   │   └── Button (Generate New)
│   ├── VBox (Array Display)
│   │   └── HBox (Bar Chart)
│   │       ├── VBox (Bar + Label)
│   │       └── ...
│   └── Label (Status)
```

### 2. Business Logic Layer

#### Algorithm Classes
Each sorting algorithm is implemented as a separate class with static methods:

**InsertionSort.java**
```java
public class InsertionSort {
    public static void insertionSort(int[] list) {
        // O(n²) implementation
    }
}
```

**BubbleSort.java**
```java
public class BubbleSort {
    public static void bubbleSort(int[] list) {
        // O(n²) implementation
    }
}
```

**MergeSort.java**
```java
public class MergeSort {
    public static void mergeSort(int[] list) {
        // O(n log n) implementation
    }
    
    private static void merge(int[] list1, int[] list2, int[] temp) {
        // Merge helper method
    }
}
```

**QuickSort.java**
```java
public class QuickSort {
    public static void quickSort(int[] list) {
        // O(n log n) average implementation
    }
    
    private static int partition(int[] list, int first, int last) {
        // Partition helper method
    }
}
```

**HeapSort.java**
```java
public class HeapSort {
    public static <E> void heapSort(E[] list) {
        // O(n log n) implementation
    }
}
```

**RadixSort.java**
```java
public class RadixSort {
    public static void radixSort(int[] list) {
        // O(d(n+k)) implementation
    }
    
    private static void countingSort(int[] list, int exp) {
        // Counting sort helper method
    }
}
```

#### Supporting Data Structures

**Heap.java**
```java
public class Heap<E> {
    private java.util.ArrayList<E> list;
    private Comparator<? super E> c;
    
    public void add(E newObject) {
        // Heap insertion
    }
    
    public E remove() {
        // Heap removal
    }
}
```

**Design Patterns Used**:
- **Strategy Pattern**: Different sorting algorithms
- **Template Method Pattern**: Common algorithm structure
- **Factory Pattern**: Algorithm selection

### 3. Data Layer

#### Array Management
The application manages array state through several mechanisms:

**State Variables**:
```java
private int[] array;           // Current array state
private int[] originalArray;   // Original unsorted array
private Rectangle[] bars;       // Visual representation
```

**Array Operations**:
- **Generation**: Random array creation
- **Copying**: Preserving original state
- **Updating**: Animation state changes
- **Reset**: Restoring original state

## Design Patterns

### 1. Model-View-Controller (MVC)

**Model**: Array data and sorting algorithms
**View**: JavaFX UI components
**Controller**: SortingDemo class

```java
// Model: Array data
private int[] array;

// View: UI components
private Rectangle[] bars;
private Label statusLabel;

// Controller: User interactions
private void startSorting() {
    // Handle user action
}
```

### 2. Observer Pattern

**Event-driven UI updates**:
```java
Platform.runLater(() -> {
    // Update UI components
    for (int i = 0; i < bars.length; i++) {
        bars[i].setHeight(array[i] * 3);
    }
});
```

### 3. Strategy Pattern

**Algorithm selection**:
```java
switch (selectedAlgorithm) {
    case "Insertion Sort":
        startInsertionSort();
        break;
    case "Bubble Sort":
        startBubbleSort();
        break;
    // ... other algorithms
}
```

### 4. Factory Pattern

**UI component creation**:
```java
private HBox createControls() {
    HBox controls = new HBox(15);
    // Create and configure controls
    return controls;
}
```

## Animation Framework

### 1. Timeline-based Animation

**Core Animation Structure**:
```java
Timeline animation = new Timeline(new KeyFrame(
    Duration.millis(1000 / speedSlider.getValue()),
    event -> {
        // Animation step execution
        if (currentStep < stepCount) {
            // Update array state
            // Update visual representation
            currentStep += 2;
        } else {
            // Animation complete
            animation.stop();
            enableControls();
        }
    }
));
```

### 2. Animation Strategies

#### Step-by-Step Animation
Used for simple algorithms (Insertion Sort, Bubble Sort):

1. **Pre-calculation**: Generate all steps
2. **Sequential execution**: Animate each step
3. **State tracking**: Track current step

#### Instant Completion
Used for complex algorithms (Merge Sort, Quick Sort, Heap Sort, Radix Sort):

1. **Immediate execution**: Run algorithm
2. **Result display**: Show final sorted array
3. **No intermediate steps**: Skip animation

### 3. Thread Safety

**UI Updates**:
```java
Platform.runLater(() -> {
    // Thread-safe UI updates
});
```

**Animation Control**:
```java
if (animation != null) {
    animation.stop();
}
```

## Error Handling

### 1. Graceful Degradation

**Algorithm Failures**:
```java
try {
    // Execute sorting algorithm
    algorithm.sort(array);
} catch (Exception e) {
    statusLabel.setText("Error: " + e.getMessage());
    enableControls();
}
```

**UI Errors**:
```java
Platform.runLater(() -> {
    try {
        // UI update
    } catch (Exception e) {
        // Fallback behavior
    }
});
```

### 2. Input Validation

**Array Size Limits**:
```java
private int[] generateRandomArray(int size) {
    if (size < 1 || size > 50) {
        size = 15; // Default size
    }
    // Generate array
}
```

**Speed Control Bounds**:
```java
speedSlider = new Slider(0.1, 2.0, 1.0);
```

## Performance Considerations

### 1. Memory Management

**Array Operations**:
- **Copying**: `Arrays.copyOf()` for state preservation
- **Cleanup**: Automatic garbage collection
- **Minimal allocation**: Reuse objects where possible

**Animation Objects**:
- **Timeline cleanup**: Proper stop() calls
- **Event handler removal**: Prevent memory leaks
- **Component disposal**: Clear references

### 2. UI Performance

**Rendering Optimization**:
- **Batch updates**: Update multiple components at once
- **Efficient layouts**: Use appropriate container types
- **Minimal redraws**: Only update changed components

**Animation Performance**:
- **Configurable frame rate**: Speed slider control
- **Smooth transitions**: Timeline-based animation
- **Thread safety**: Platform.runLater() usage

### 3. Algorithm Efficiency

**Educational Focus**:
- **Clarity over optimization**: Readable implementations
- **Standard algorithms**: Well-known approaches
- **Benchmarking**: Not primary concern

## Cross-Platform Compatibility

### 1. Maven Profiles

**Platform Detection**:
```xml
<profile>
    <id>mac</id>
    <activation>
        <os><family>mac</family></os>
    </activation>
    <dependencies>
        <!-- macOS-specific dependencies -->
    </dependencies>
</profile>
```

**Benefits**:
- Automatic platform detection
- Correct native libraries
- No manual configuration

### 2. Build Scripts

**Platform-Specific Execution**:
- **run.sh**: Unix/Linux/macOS
- **run.bat**: Windows

**Features**:
- Dependency checking
- Error handling
- User-friendly messages

## Testing Strategy

### 1. Unit Testing

**Algorithm Testing**:
```java
@Test
public void testInsertionSort() {
    int[] array = {3, 1, 4, 1, 5};
    InsertionSort.insertionSort(array);
    assertArrayEquals(new int[]{1, 1, 3, 4, 5}, array);
}
```

**Edge Cases**:
- Empty arrays
- Single element arrays
- Already sorted arrays
- Reverse sorted arrays

### 2. Integration Testing

**UI Testing**:
- Button interactions
- Algorithm selection
- Animation behavior
- Error handling

### 3. Performance Testing

**Algorithm Benchmarking**:
- Time complexity verification
- Memory usage analysis
- Animation smoothness

## Deployment Architecture

### 1. Build Process

**Maven Build**:
```bash
mvn clean compile
mvn javafx:run
```

**JAR Creation**:
```bash
mvn package
```

### 2. Distribution

**Cross-Platform JAR**:
- Single JAR file
- Platform-specific dependencies
- Self-contained execution

**Platform-Specific Scripts**:
- Automatic dependency detection
- Error handling
- User guidance

## Security Considerations

### 1. Input Validation

**Array Generation**:
- Size limits
- Value ranges
- Type safety

**User Input**:
- Algorithm selection validation
- Speed control bounds
- Button state management

### 2. Resource Management

**Memory Usage**:
- Array size limits
- Animation cleanup
- UI component disposal

**File System**:
- No file I/O required
- Temporary data in memory only

## Future Architecture Enhancements

### 1. Modular Design

**Plugin Architecture**:
- Algorithm plugin system
- UI component plugins
- Animation plugin framework

### 2. Microservices Approach

**Service Separation**:
- Algorithm service
- UI service
- Animation service
- Configuration service

### 3. Cloud Integration

**Remote Features**:
- Algorithm comparison
- Performance benchmarking
- User analytics

## Conclusion

The JavaFX Sorting Algorithm Visualizer employs a well-structured, layered architecture that promotes maintainability, extensibility, and cross-platform compatibility. The use of established design patterns ensures code clarity and facilitates future enhancements.

The modular approach allows for easy addition of new algorithms and UI features while maintaining the educational focus of the application. The cross-platform build system ensures consistent behavior across different operating systems. 