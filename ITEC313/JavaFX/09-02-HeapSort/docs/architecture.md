# Heap Sort Architecture and Design Patterns

## System Architecture

### High-Level Architecture
```
┌─────────────────────────────────────────────────────────────┐
│                    JavaFX Application                      │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────┐ │
│  │   UI Layer      │  │  Business Logic │  │  Data Layer │ │
│  │                 │  │                 │  │             │ │
│  │ • HeapSortDemo  │  │ • Heap          │  │ • ArrayList │ │
│  │ • Canvas        │  │ • HeapSort      │  │ • Generics  │ │
│  │ • Controls      │  │ • Algorithms    │  │ • Comparators│ │
│  └─────────────────┘  └─────────────────┘  └─────────────┘ │
└─────────────────────────────────────────────────────────────┘
```

### Package Structure
```
com.acu.javafx.heapsort/
├── HeapSortDemo.java    # Main application class
├── Heap.java           # Heap data structure
└── HeapSort.java       # Sorting algorithm
```

## Design Patterns

### 1. Model-View-Controller (MVC) Pattern

#### Model (Data Layer)
```java
public class Heap<E> {
    private ArrayList<E> list;
    private Comparator<? super E> c;
    
    // Data operations
    public void add(E element);
    public E remove();
    public boolean isEmpty();
}
```

**Responsibilities:**
- Data storage and management
- Business logic implementation
- State maintenance

#### View (UI Layer)
```java
public class HeapSortDemo extends Application {
    private Canvas canvas;
    private GraphicsContext gc;
    
    // UI rendering
    private void drawHeapTree();
    private void updateDisplay();
}
```

**Responsibilities:**
- Visual representation
- User interaction handling
- Display updates

#### Controller (Business Logic)
```java
// Event handlers in HeapSortDemo
private void insertValue();
private void removeRoot();
private void performHeapSort();
```

**Responsibilities:**
- User input processing
- Business logic coordination
- State transitions

### 2. Strategy Pattern

#### Comparator Strategy
```java
// Default strategy
this.c = (e1, e2) -> ((Comparable<E>)e1).compareTo(e2);

// Custom strategy
public Heap(Comparator<E> c) {
    this.c = c;
}
```

**Benefits:**
- Flexible comparison logic
- Runtime strategy selection
- Extensible design

### 3. Template Method Pattern

#### Heap Operations Template
```java
public void add(E newObject) {
    // Template: Add and bubble up
    list.add(newObject);
    int currentIndex = list.size() - 1;
    
    while (currentIndex > 0) {
        // Specific implementation varies by heap type
        if (c.compare(list.get(currentIndex), list.get(parentIndex)) > 0) {
            swap(currentIndex, parentIndex);
        } else {
            break;
        }
        currentIndex = parentIndex;
    }
}
```

### 4. Observer Pattern

#### UI Updates
```java
private void updateDisplay() {
    // Notify UI of state changes
    drawHeapTree();
    updateStatusLabel();
    logOperations();
}
```

## Component Design

### Heap Data Structure

#### Class Design
```java
public class Heap<E> {
    // Core data
    private ArrayList<E> list;
    private Comparator<? super E> c;
    
    // Constructors
    public Heap();
    public Heap(Comparator<E> c);
    public Heap(E[] objects);
    
    // Core operations
    public void add(E newObject);
    public E remove();
    public int getSize();
    public boolean isEmpty();
    
    // Utility methods
    public ArrayList<E> getList();
}
```

#### Design Principles Applied
- **Single Responsibility**: Each method has one clear purpose
- **Open/Closed**: Extensible through generics and comparators
- **Dependency Inversion**: Depends on abstractions (Comparator)
- **Interface Segregation**: Clean, focused interface

### Heap Sort Algorithm

#### Static Utility Design
```java
public class HeapSort {
    // Generic sort methods
    public static <E> void heapSort(E[] list);
    public static <E> void heapSort(E[] list, Comparator<E> c);
    
    // Test method
    public static void main(String[] args);
}
```

#### Design Benefits
- **Utility Pattern**: Static methods for algorithm operations
- **Generic Implementation**: Type-safe for any comparable type
- **Separation of Concerns**: Algorithm separate from data structure
- **Testability**: Independent testing of sorting logic

### JavaFX Application

#### Application Structure
```java
public class HeapSortDemo extends Application {
    // UI Components
    private Canvas canvas;
    private GraphicsContext gc;
    private TextArea outputArea;
    private Button insertButton;
    // ... other UI components
    
    // Data Components
    private Heap<Integer> heap;
    private List<Integer> originalList;
    private List<Integer> sortedList;
    
    // Lifecycle methods
    @Override
    public void start(Stage primaryStage);
    public static void main(String[] args);
}
```

#### UI Design Patterns
- **Composite Pattern**: Complex UI hierarchy
- **Command Pattern**: Button actions as commands
- **Observer Pattern**: UI updates on data changes
- **Factory Pattern**: UI component creation

## Data Flow Architecture

### 1. User Input Flow
```
User Action → Event Handler → Business Logic → Data Update → UI Refresh
```

#### Example: Insert Operation
```java
// 1. User clicks "Insert" button
insertButton.setOnAction(e -> insertValue());

// 2. Event handler processes input
private void insertValue() {
    int value = Integer.parseInt(inputField.getText());
    heap.add(value);  // Business logic
    updateDisplay();   // UI refresh
}
```

### 2. Data Update Flow
```
Data Change → State Update → Visualization Update → Status Update
```

#### Example: Heap Modification
```java
// 1. Data change
heap.add(value);

// 2. State update
originalList.add(value);

// 3. Visualization update
updateDisplay();

// 4. Status update
statusLabel.setText("Heap size: " + heap.getSize());
```

### 3. Rendering Flow
```
Heap Data → Tree Traversal → Canvas Drawing → Visual Output
```

#### Example: Tree Drawing
```java
private void drawHeapTree() {
    List<Integer> heapList = heap.getList();
    drawNode(startX, startY, 0, heapList, CANVAS_WIDTH / 4);
}
```

## Error Handling Architecture

### 1. Input Validation
```java
private void insertValue() {
    try {
        String input = inputField.getText().trim();
        if (input.isEmpty()) {
            showAlert("Please enter a value");
            return;
        }
        
        int value = Integer.parseInt(input);
        // Process valid input
    } catch (NumberFormatException e) {
        showAlert("Please enter a valid integer");
    }
}
```

### 2. State Validation
```java
private void removeRoot() {
    if (heap.isEmpty()) {
        showAlert("Heap is empty");
        return;
    }
    // Process removal
}
```

### 3. Error Presentation
```java
private void showAlert(String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Information");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}
```

## Performance Architecture

### 1. Memory Management
- **ArrayList**: Efficient dynamic array for heap storage
- **Generic Types**: Type safety without boxing overhead
- **Canvas Rendering**: Hardware-accelerated graphics
- **Event Handling**: Non-blocking UI updates

### 2. Algorithm Optimization
- **In-place Sorting**: O(1) space complexity
- **Efficient Heap Operations**: O(log n) insert/remove
- **Lazy Evaluation**: Only redraw when necessary
- **Batch Updates**: Group multiple operations

### 3. UI Performance
- **Canvas-based Rendering**: Direct drawing for performance
- **Event-driven Updates**: Only update when needed
- **Efficient Layout**: Minimal layout calculations
- **Memory-efficient Logging**: Scrollable text area

## Cross-Platform Architecture

### 1. Platform Detection
```xml
<profiles>
    <profile>
        <id>mac</id>
        <activation>
            <os>
                <family>mac</family>
            </os>
        </activation>
    </profile>
    <!-- Windows and Linux profiles -->
</profiles>
```

### 2. Build System
- **Maven-based**: Cross-platform dependency management
- **Profile-based**: Platform-specific configurations
- **Script-based**: Platform-specific execution scripts
- **JAR Packaging**: Portable executable creation

### 3. Runtime Adaptation
- **JavaFX Modules**: Platform-specific optimizations
- **Native Libraries**: Automatic platform detection
- **UI Scaling**: Responsive design for different screen sizes
- **Font Rendering**: Platform-appropriate text rendering

## Testing Architecture

### 1. Unit Testing Structure
```java
// Test heap operations
@Test
public void testHeapInsert() {
    Heap<Integer> heap = new Heap<>();
    heap.add(5);
    assertEquals(1, heap.getSize());
    assertEquals(Integer.valueOf(5), heap.remove());
}

// Test sorting algorithm
@Test
public void testHeapSort() {
    Integer[] array = {3, 1, 4, 1, 5};
    HeapSort.heapSort(array);
    assertArrayEquals(new Integer[]{1, 1, 3, 4, 5}, array);
}
```

### 2. Integration Testing
- **UI Integration**: Test user interactions
- **Data Flow**: Test complete operation sequences
- **Error Handling**: Test error scenarios
- **Performance**: Test with large datasets

### 3. Platform Testing
- **Cross-platform Build**: Test on all target platforms
- **Dependency Resolution**: Test Maven profile activation
- **Script Execution**: Test build scripts
- **Runtime Compatibility**: Test JavaFX module loading

## Deployment Architecture

### 1. Build Process
```bash
# Clean and compile
mvn clean compile

# Run application
mvn javafx:run

# Package as JAR
mvn clean package
```

### 2. Distribution
- **Executable JAR**: Self-contained application
- **Platform Scripts**: Easy execution on target platforms
- **Documentation**: Comprehensive usage instructions
- **Dependencies**: Maven-managed dependencies

### 3. Runtime Requirements
- **Java 24+**: Runtime environment
- **JavaFX 21**: UI framework
- **Maven 3.9+**: Build tool (optional for end users)
- **Platform Support**: macOS, Windows, Linux

## Security Architecture

### 1. Input Validation
- **Type Safety**: Generic type constraints
- **Range Validation**: Integer bounds checking
- **Null Safety**: Null pointer prevention
- **Exception Handling**: Graceful error recovery

### 2. Resource Management
- **Memory Management**: Efficient object lifecycle
- **File System**: Safe resource access
- **Network**: No external network dependencies
- **UI Security**: Safe event handling

### 3. Code Quality
- **Static Analysis**: Compile-time error detection
- **Type Safety**: Generic type constraints
- **Exception Safety**: Proper exception handling
- **Resource Safety**: Automatic resource cleanup 