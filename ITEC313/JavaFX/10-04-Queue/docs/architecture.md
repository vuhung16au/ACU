# Queue Demo - Architecture and Design Patterns

## System Architecture Overview

The JavaFX Queue Demo application follows a layered architecture pattern with clear separation of concerns, enabling maintainability, testability, and extensibility.

## Architecture Layers

### 1. Presentation Layer (JavaFX UI)
**Components:**
- `QueueDemo.java` - Main application class
- `styles.css` - UI styling
- JavaFX UI components (TabPane, BorderPane, etc.)

**Responsibilities:**
- User interface rendering
- Event handling and user interaction
- Visual representation of data structures
- Output display and logging

**Design Patterns:**
- **Observer Pattern**: JavaFX event handling
- **MVC Pattern**: View component
- **Builder Pattern**: UI component construction

### 2. Business Logic Layer (Data Structures)
**Components:**
- `GenericQueue.java` - Queue implementation
- `GenericStack.java` - Stack implementation
- `Heap.java` - Heap data structure
- `MyPriorityQueue.java` - Priority queue implementation
- `Patient.java` - Domain model for priority queue

**Responsibilities:**
- Data structure implementations
- Algorithm execution
- Business logic encapsulation
- Data manipulation and retrieval

**Design Patterns:**
- **Template Method Pattern**: Generic implementations
- **Strategy Pattern**: Different data structure behaviors
- **Factory Pattern**: Object creation for data structures

### 3. Test Integration Layer
**Components:**
- `TestStackQueue.java` - Original test implementation
- `TestPriorityQueue.java` - Original test implementation

**Responsibilities:**
- Preserved original test logic
- System output capture
- Test execution coordination

## Detailed Component Architecture

### Main Application (`QueueDemo.java`)

```java
public class QueueDemo extends Application {
    // Data structure instances
    private GenericQueue<String> queue;
    private GenericStack<String> stack;
    private MyPriorityQueue<Patient> priorityQueue;
    
    // UI components
    private VBox queueDisplay;
    private VBox stackDisplay;
    private VBox priorityQueueDisplay;
    private TextArea outputArea;
}
```

**Architecture Features:**
- **Singleton Pattern**: Single application instance
- **Observer Pattern**: Event-driven UI updates
- **Command Pattern**: Button actions as commands

### Data Structure Implementations

#### GenericQueue<E>
```java
public class GenericQueue<E> {
    private java.util.LinkedList<E> list = new java.util.LinkedList<E>();
    
    public void enqueue(E e) { list.addLast(e); }
    public E dequeue() { return list.removeFirst(); }
    public int getSize() { return list.size(); }
}
```

**Design Patterns:**
- **Adapter Pattern**: Wraps LinkedList functionality
- **Generic Type Pattern**: Type-safe implementation
- **Composition Pattern**: Uses LinkedList internally

#### GenericStack<E>
```java
public class GenericStack<E> {
    private java.util.ArrayList<E> list = new java.util.ArrayList<>();
    
    public void push(E o) { list.add(o); }
    public E pop() { 
        E o = list.get(getSize() - 1);
        list.remove(getSize() - 1);
        return o;
    }
}
```

**Design Patterns:**
- **Adapter Pattern**: Wraps ArrayList functionality
- **LIFO Pattern**: Last-in-first-out behavior
- **Generic Type Pattern**: Type-safe implementation

#### Heap<E> and MyPriorityQueue<E>
```java
public class Heap<E> {
    private java.util.ArrayList<E> list = new java.util.ArrayList<>();
    private java.util.Comparator<? super E> c;
    
    public void add(E newObject) { /* heapify logic */ }
    public E remove() { /* heapify logic */ }
}

public class MyPriorityQueue<E> {
    private Heap<E> heap;
    
    public void enqueue(E newObject) { heap.add(newObject); }
    public E dequeue() { return heap.remove(); }
}
```

**Design Patterns:**
- **Composition Pattern**: PriorityQueue uses Heap
- **Strategy Pattern**: Comparator-based ordering
- **Template Method Pattern**: Generic heap operations

## Cross-Platform Architecture

### Maven Configuration Strategy

```xml
<profiles>
    <profile>
        <id>mac</id>
        <activation>
            <os><family>mac</family></os>
        </activation>
        <dependencies>
            <!-- Platform-specific JavaFX dependencies -->
        </dependencies>
    </profile>
    <!-- Similar profiles for win and linux -->
</profiles>
```

**Architecture Benefits:**
- **Automatic Platform Detection**: Maven profiles
- **Platform-Specific Dependencies**: JavaFX classifiers
- **Unified Build Process**: Single command execution

### Build Script Architecture

#### Unix/Linux/macOS (`run.sh`)
```bash
#!/bin/bash
# Environment detection and validation
# Maven build execution
# Application launch
```

#### Windows (`run.bat`)
```cmd
@echo off
REM Environment detection and validation
REM Maven build execution
REM Application launch
```

**Architecture Features:**
- **Platform Abstraction**: Unified interface across platforms
- **Error Handling**: Graceful failure management
- **Environment Validation**: Prerequisite checking

## Event-Driven Architecture

### JavaFX Event Handling

```java
// Button event handling
enqueueBtn.setOnAction(e -> {
    String value = inputField.getText().trim();
    if (!value.isEmpty()) {
        queue.enqueue(value);
        updateQueueDisplay();
        log("Enqueued: " + value);
    }
});
```

**Architecture Patterns:**
- **Observer Pattern**: Event listeners
- **Lambda Expressions**: Modern Java event handling
- **Command Pattern**: Button actions as commands

### Thread-Safe UI Updates

```java
private void log(String message) {
    Platform.runLater(() -> {
        outputArea.appendText(message + "\n");
        outputArea.setScrollTop(Double.MAX_VALUE);
    });
}
```

**Architecture Benefits:**
- **Thread Safety**: Platform.runLater() for UI updates
- **Responsive UI**: Non-blocking operations
- **Event Queue Management**: Proper event handling

## Resource Management Architecture

### CSS Styling Integration

```java
Scene scene = new Scene(root, 1000, 700);
scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
```

**Architecture Features:**
- **Separation of Concerns**: UI styling separate from logic
- **Resource Loading**: Proper resource path handling
- **Theme Management**: Centralized styling

### Visual Representation Architecture

```java
private void updateQueueDisplay() {
    queueDisplay.getChildren().clear();
    
    // Create temporary structures for visualization
    List<String> elements = new ArrayList<>();
    GenericQueue<String> tempQueue = new GenericQueue<>();
    
    // Preserve original data while creating visualization
    while (queue.getSize() > 0) {
        String element = queue.dequeue();
        elements.add(element);
        tempQueue.enqueue(element);
    }
    
    // Restore original data
    while (tempQueue.getSize() > 0) {
        queue.enqueue(tempQueue.dequeue());
    }
}
```

**Architecture Benefits:**
- **Data Integrity**: Original data preserved
- **Visual Consistency**: Real-time updates
- **Memory Efficiency**: Temporary object management

## Test Integration Architecture

### System Output Capture

```java
private void runStackQueueTest() {
    // Capture System.out
    java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
    java.io.PrintStream originalOut = System.out;
    System.setOut(new java.io.PrintStream(baos));
    
    try {
        TestStackQueue.main(new String[0]);
        String output = baos.toString();
        log(output);
    } finally {
        System.setOut(originalOut);
    }
}
```

**Architecture Features:**
- **Output Redirection**: System.out capture
- **Resource Cleanup**: Proper stream management
- **Test Preservation**: Original test logic unchanged

## Performance Architecture

### Memory Management

**Strategies:**
- **Object Pooling**: Reuse temporary objects
- **Garbage Collection**: Proper cleanup
- **Efficient Algorithms**: O(1) operations where possible

### UI Responsiveness

**Techniques:**
- **Non-blocking Operations**: Platform.runLater()
- **Efficient Updates**: Only redraw when necessary
- **Event Queue Management**: Proper event handling

## Security Architecture

### Input Validation

```java
if (!value.isEmpty()) {
    queue.enqueue(value);
    updateQueueDisplay();
    inputField.clear();
    log("Enqueued: " + value);
}
```

**Security Features:**
- **Input Sanitization**: Trim and validate input
- **Null Safety**: Proper null checking
- **Type Safety**: Generic type constraints

## Extensibility Architecture

### Plugin-Style Data Structure Addition

The architecture supports easy addition of new data structures:

1. **Implement Data Structure**: Create new class implementing standard interface
2. **Add UI Tab**: Create new tab in main application
3. **Add Visual Representation**: Implement display update method
4. **Add Test Integration**: Include test class if available

### Configuration Management

**Maven Profiles**: Platform-specific configurations
**Build Scripts**: Environment-specific execution
**Resource Loading**: Flexible resource management

## Deployment Architecture

### JAR Packaging

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <configuration>
        <transformers>
            <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                <mainClass>com.acu.javafx.queue.QueueDemo</mainClass>
            </transformer>
        </transformers>
    </configuration>
</plugin>
```

**Deployment Features:**
- **Executable JAR**: Self-contained application
- **Dependency Management**: All dependencies included
- **Cross-Platform**: Single JAR for all platforms

## Monitoring and Debugging Architecture

### Logging System

```java
private void log(String message) {
    Platform.runLater(() -> {
        outputArea.appendText(message + "\n");
        outputArea.setScrollTop(Double.MAX_VALUE);
    });
}
```

**Debugging Features:**
- **Real-time Logging**: Immediate feedback
- **UI Integration**: Logs displayed in application
- **Error Handling**: Graceful error management

## Future Architecture Considerations

### Microservices Integration
- **REST API**: Expose data structure operations
- **WebSocket**: Real-time updates
- **Containerization**: Docker support

### Cloud Deployment
- **Container Orchestration**: Kubernetes support
- **Scalability**: Horizontal scaling
- **Monitoring**: Application metrics

### Advanced UI Features
- **Animation Framework**: Smooth transitions
- **3D Visualization**: Advanced graphics
- **Accessibility**: Screen reader support 