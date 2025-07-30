# JavaFX Probing Application - Architecture and Design Patterns

## Overview

This document describes the detailed architecture and design patterns used in the JavaFX application that demonstrates hash table probing techniques.

## System Architecture

### High-Level Architecture
```
┌─────────────────────────────────────────────────────────────┐
│                    JavaFX Application                      │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐      │
│  │   Linear    │  │  Quadratic  │  │   Separate  │      │
│  │  Probing    │  │  Probing    │  │  Chaining   │      │
│  │ Hash Table  │  │ Hash Table  │  │ Hash Table  │      │
│  └─────────────┘  └─────────────┘  └─────────────┘      │
├─────────────────────────────────────────────────────────────┤
│                    LinkedList<E>                          │
│                 (Supporting Class)                        │
├─────────────────────────────────────────────────────────────┤
│                    JavaFX Framework                       │
│                 (UI Components)                           │
├─────────────────────────────────────────────────────────────┤
│                    Java Platform                          │
│                 (JVM, Core Libraries)                    │
└─────────────────────────────────────────────────────────────┘
```

## Design Patterns

### 1. Model-View-Controller (MVC) Pattern

#### Model Layer
The hash table implementations serve as the model layer:

```java
// Model classes
HashSetUsingLinearProbing    // Linear probing implementation
HashSetUsingQuadraticProbing // Quadratic probing implementation
MyHashSet                    // Separate chaining implementation
LinkedList<E>                // Supporting data structure
```

#### View Layer
The JavaFX UI components form the view layer:

```java
// View components
TextArea linearProbingArea      // Linear probing visualization
TextArea quadraticProbingArea   // Quadratic probing visualization
TextArea separateChainingArea   // Separate chaining visualization
TextField inputField            // User input
Button addButton, removeButton, // User controls
      searchButton, clearButton
Label statusLabel               // Status display
```

#### Controller Layer
The `ProbingDemo` class acts as the controller:

```java
// Controller methods
private void addElement()       // Handle add operations
private void removeElement()    // Handle remove operations
private void searchElement()    // Handle search operations
private void clearAll()         // Handle clear operations
private void setLoadFactor()    // Handle configuration changes
```

### 2. Strategy Pattern

The application implements the Strategy pattern for different collision resolution techniques:

```java
// Strategy interface (implicit)
interface HashTableStrategy {
    boolean add(Integer element);
    boolean remove(Integer element);
    boolean contains(Integer element);
    void clear();
    int size();
    boolean isEmpty();
}

// Concrete strategies
class HashSetUsingLinearProbing implements HashTableStrategy
class HashSetUsingQuadraticProbing implements HashTableStrategy
class MyHashSet implements HashTableStrategy
```

### 3. Observer Pattern

The application uses the Observer pattern for real-time updates:

```java
// Observable: Hash tables
// Observers: Visualization areas

// When hash table state changes:
updateLinearProbingVisualization();
updateQuadraticProbingVisualization();
updateSeparateChainingVisualization();
```

### 4. Factory Pattern

The application uses a simple factory pattern for creating hash table instances:

```java
// Factory method in ProbingDemo
private void initializeHashTables() {
    linearProbing = new HashSetUsingLinearProbing();
    quadraticProbing = new HashSetUsingQuadraticProbing();
    separateChaining = new MyHashSet();
}
```

## Class Design

### Core Classes

#### 1. ProbingDemo (Main Application)
```java
public class ProbingDemo extends Application {
    // Hash table instances
    private HashSetUsingLinearProbing linearProbing;
    private HashSetUsingQuadraticProbing quadraticProbing;
    private MyHashSet separateChaining;
    
    // UI components
    private TextArea linearProbingArea;
    private TextArea quadraticProbingArea;
    private TextArea separateChainingArea;
    private TextField inputField;
    private Label statusLabel;
    
    // Main methods
    public void start(Stage primaryStage)
    private HBox createInputControls()
    private HBox createVisualizationAreas()
    private void updateAllVisualizations()
}
```

**Responsibilities:**
- Initialize the JavaFX application
- Create and manage UI components
- Handle user interactions
- Coordinate between hash tables and visualizations
- Provide real-time updates

#### 2. HashSetUsingLinearProbing
```java
public class HashSetUsingLinearProbing {
    private int capacity;
    private double loadFactorThreshold;
    private int size;
    private Integer[] table;
    
    // Core operations
    public boolean add(Integer e)
    public boolean remove(Integer e)
    public boolean contains(Integer e)
    public void clear()
    
    // Configuration
    public void setLoadFactorThreshold(double factor)
    public void setCapacity(int capacity)
    
    // Visualization support
    public Integer[] getTable()
    public double getCurrentLoadFactor()
}
```

**Responsibilities:**
- Implement linear probing collision resolution
- Manage hash table state
- Handle rehashing when load factor exceeds threshold
- Provide data for visualization

#### 3. HashSetUsingQuadraticProbing
```java
public class HashSetUsingQuadraticProbing {
    // Similar structure to HashSetUsingLinearProbing
    // but with quadratic probing algorithm
}
```

**Responsibilities:**
- Implement quadratic probing collision resolution
- Same responsibilities as linear probing but with different algorithm

#### 4. MyHashSet (Separate Chaining)
```java
public class MyHashSet {
    private int capacity;
    private double loadFactorThreshold;
    private int size;
    private LinkedList<Integer>[] table;
    
    // Core operations (same interface)
    // but uses linked lists for collision resolution
}
```

**Responsibilities:**
- Implement separate chaining collision resolution
- Manage linked list buckets
- Handle collisions by adding to linked lists

#### 5. LinkedList<E>
```java
public class LinkedList<E> implements Iterable<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;
    
    // Core operations
    public void add(E e)
    public boolean remove(E e)
    public E get(int index)
    public int getSize()
    public boolean isEmpty()
    
    // Iterator support
    public Iterator<E> iterator()
}
```

**Responsibilities:**
- Provide linked list functionality for separate chaining
- Support generic types
- Implement Iterable interface for enhanced functionality

## UI Architecture

### Layout Structure
```
VBox (root)
├── Label (title)
├── HBox (input controls)
│   ├── Label, TextField, Buttons
│   └── Separator, Load Factor controls
├── HBox (visualization areas)
│   ├── VBox (Linear Probing)
│   │   ├── Label (title)
│   │   └── TextArea (visualization)
│   ├── VBox (Quadratic Probing)
│   │   ├── Label (title)
│   │   └── TextArea (visualization)
│   └── VBox (Separate Chaining)
│       ├── Label (title)
│       └── TextArea (visualization)
└── Label (status)
```

### UI Component Responsibilities

#### Input Controls
- **TextField**: Captures user input for operations
- **Buttons**: Trigger specific operations (Add, Remove, Search, Clear)
- **Load Factor Controls**: Allow configuration of hash table behavior

#### Visualization Areas
- **TextAreas**: Display hash table contents in formatted text
- **Labels**: Provide titles and status information
- **Styling**: Consistent visual appearance across all panels

## Data Flow

### 1. User Input Flow
```
User Input → Controller → Hash Tables → Visualization Update
```

### 2. Operation Flow
```
Button Click → Event Handler → Hash Table Operation → State Change → Visualization Update
```

### 3. Configuration Flow
```
Load Factor Change → Hash Table Reconfiguration → Rehashing → Visualization Update
```

## Error Handling

### Input Validation
```java
try {
    int value = Integer.parseInt(inputField.getText().trim());
    if (value < 0 || value > 99) {
        showAlert("Please enter a number between 0 and 99.");
        return;
    }
    // Process valid input
} catch (NumberFormatException e) {
    showAlert("Please enter a valid integer.");
}
```

### Hash Table Error Handling
```java
public void setLoadFactorThreshold(double factor) {
    if (factor >= 1) {
        throw new IllegalArgumentException("The load factor threshold must be less than 1.0");
    }
    // Process valid factor
}
```

### UI Error Display
```java
private void showAlert(String message) {
    Platform.runLater(() -> {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    });
}
```

## Performance Considerations

### Memory Management
- Hash tables use arrays for efficient access
- Linked lists use dynamic allocation for separate chaining
- UI components are lightweight and reusable

### Update Strategy
- Real-time updates only when necessary
- Batch updates for multiple operations
- Efficient string building for visualizations

### Threading
- JavaFX application runs on the Application Thread
- UI updates are properly synchronized
- Long-running operations are avoided in UI thread

## Extensibility

### Adding New Probing Techniques
1. Create new class implementing the same interface
2. Add visualization area in UI
3. Integrate with controller methods
4. Update documentation

### Adding New Operations
1. Add button to UI
2. Implement controller method
3. Update all hash table instances
4. Refresh visualizations

### Adding New Visualization Types
1. Create new visualization component
2. Implement update method
3. Integrate with existing update cycle
4. Add configuration options if needed

## Testing Strategy

### Unit Testing
- Test each hash table implementation independently
- Test LinkedList functionality
- Test UI component behavior

### Integration Testing
- Test complete operation flows
- Test visualization updates
- Test error handling scenarios

### Performance Testing
- Test with large datasets
- Test load factor boundaries
- Test memory usage patterns

## Deployment Architecture

### Maven Configuration
- Cross-platform dependency management
- Platform-specific JavaFX modules
- Automated build and packaging

### Runtime Requirements
- Java 24 or later
- JavaFX 21
- Platform-specific native libraries

### Distribution
- Executable JAR with dependencies
- Platform-specific scripts
- Comprehensive documentation

This architecture provides a robust, maintainable, and extensible foundation for the hash table probing demonstration application. 