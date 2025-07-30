# Map and Hash Set Architecture

## System Architecture Overview

This document describes the detailed architecture, design patterns, and technical implementation of the Map and Hash Set demonstration project.

## High-Level Architecture

### 1. Layered Architecture

```
┌─────────────────────────────────────┐
│           Presentation Layer        │
│         (JavaFX Application)       │
├─────────────────────────────────────┤
│           Business Logic Layer      │
│         (Data Structure Logic)     │
├─────────────────────────────────────┤
│           Data Structure Layer      │
│         (HashMap, HashSet)         │
├─────────────────────────────────────┤
│           Foundation Layer          │
│         (Java Collections API)     │
└─────────────────────────────────────┘
```

### 2. Component Architecture

```
MapHashDemo (JavaFX Application)
├── UI Components
│   ├── TabPane (Main Container)
│   ├── HashMap Tab
│   ├── HashSet Tab
│   └── Test Results Tab
├── Data Structures
│   ├── MyHashMap<String, String>
│   └── MyHashSet<String>
└── Event Handlers
    ├── HashMap Operations
    ├── HashSet Operations
    └── Test Execution
```

## Design Patterns

### 1. Model-View-Controller (MVC)

#### Model
- **Data Structures**: `MyHashMap`, `MyHashSet`
- **Business Logic**: Hash operations, collision resolution
- **State Management**: Current map/set state

#### View
- **JavaFX UI**: TabPane, TextFields, Buttons, TextAreas
- **CSS Styling**: Modern, responsive design
- **User Interaction**: Input validation, feedback

#### Controller
- **Event Handlers**: Button click handlers
- **Data Binding**: UI updates based on model changes
- **Input Processing**: Text field validation and processing

### 2. Strategy Pattern

#### Hash Function Strategy
```java
// Different hash function implementations
private int hash(int hashCode) {
    return supplementalHash(hashCode) & (capacity - 1);
}

private static int supplementalHash(int h) {
    h ^= (h >>> 20) ^ (h >>> 12);
    return h ^ (h >>> 7) ^ (h >>> 4);
}
```

#### Collision Resolution Strategy
```java
// Separate chaining implementation
LinkedList<Entry<K,V>>[] table;
```

### 3. Iterator Pattern

#### MyHashSet Iterator
```java
private class MyHashSetIterator implements Iterator<E> {
    private ArrayList<E> list;
    private int current = 0;
    
    public boolean hasNext() { return current < list.size(); }
    public E next() { return list.get(current++); }
}
```

### 4. Factory Pattern

#### Data Structure Creation
```java
// Factory methods for creating data structures
MyHashMap<String, String> hashMap = new MyHashMap<>();
MyHashSet<String> hashSet = new MyHashSet<>();
```

## Package Structure

### 1. Main Package: `com.acu.javafx.maphash`

```
com.acu.javafx.maphash/
├── interfaces/
│   ├── MyMap.java          # Map interface
│   └── MySet.java          # Set interface
├── implementations/
│   ├── MyHashMap.java      # HashMap implementation
│   └── MyHashSet.java      # HashSet implementation
├── tests/
│   ├── TestMyHashMap.java  # HashMap test class
│   └── TestMyHashSet.java  # HashSet test class
├── ui/
│   └── MapHashDemo.java    # Main JavaFX application
└── resources/
    └── styles.css          # CSS styling
```

### 2. Dependencies

#### External Dependencies
- **JavaFX**: UI framework
- **Java Collections**: Standard library interfaces
- **Maven**: Build system

#### Internal Dependencies
- **MyMap → MyHashMap**: Interface implementation
- **MySet → MyHashSet**: Interface implementation
- **MapHashDemo → Data Structures**: UI uses data structures

## Data Flow Architecture

### 1. User Interaction Flow

```
User Input → Event Handler → Business Logic → Data Structure → UI Update
     ↓              ↓              ↓              ↓              ↓
Text Field → Button Click → Validation → Hash Operation → Display Result
```

### 2. Data Structure Operations

#### HashMap Operations
```
put(key, value) → hash(key) → find bucket → add/update entry → rehash if needed
get(key) → hash(key) → find bucket → search list → return value
remove(key) → hash(key) → find bucket → remove entry → update size
```

#### HashSet Operations
```
add(element) → hash(element) → find bucket → add if not exists → rehash if needed
contains(element) → hash(element) → find bucket → search list → return boolean
remove(element) → hash(element) → find bucket → remove entry → update size
```

## Memory Management Architecture

### 1. Hash Table Structure

```
Hash Table Array
├── Bucket 0: LinkedList<Entry<K,V>>
├── Bucket 1: LinkedList<Entry<K,V>>
├── Bucket 2: LinkedList<Entry<K,V>>
└── ...
    └── Bucket N: LinkedList<Entry<K,V>>
```

### 2. Memory Allocation Strategy

#### Initial Allocation
- **Default Capacity**: 4 (power of 2)
- **Load Factor**: 0.75
- **Memory Overhead**: Array + LinkedList nodes

#### Dynamic Resizing
- **Trigger**: Load factor > threshold
- **New Capacity**: Current capacity * 2
- **Rehashing**: All entries rehashed and redistributed

#### Cleanup Operations
- **Clear**: Reset size, clear all buckets
- **Remove**: Remove specific entries, update size
- **Garbage Collection**: Automatic cleanup of removed entries

## Performance Architecture

### 1. Time Complexity Analysis

#### Average Case (Good Hash Distribution)
```
Operation    | Time Complexity
-------------|----------------
put()        | O(1)
get()        | O(1)
remove()     | O(1)
contains()   | O(1)
```

#### Worst Case (Poor Hash Distribution)
```
Operation    | Time Complexity
-------------|----------------
All Operations | O(n)
```

### 2. Space Complexity Analysis

#### Storage Requirements
```
Component           | Space Complexity
-------------------|------------------
Hash Table Array   | O(capacity)
LinkedList Nodes   | O(n)
Entry Objects      | O(n)
Total              | O(n)
```

### 3. Load Factor Impact

#### Performance vs Load Factor
```
Load Factor | Collision Rate | Performance | Memory Usage
------------|----------------|-------------|-------------
0.25        | Low           | Excellent   | High
0.50        | Medium        | Good        | Medium
0.75        | High          | Acceptable  | Low
0.90        | Very High     | Poor        | Very Low
```

## Cross-Platform Architecture

### 1. Platform Detection

#### Maven Profiles
```xml
<!-- macOS Intel -->
<profile>
    <id>mac</id>
    <activation>
        <os>
            <family>mac</family>
        </os>
    </activation>
</profile>

<!-- macOS ARM64 -->
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

### 2. JavaFX Module Loading

#### Platform-Specific Dependencies
```xml
<!-- Platform-specific JavaFX modules -->
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-graphics</artifactId>
    <classifier>win</classifier>
</dependency>
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-graphics</artifactId>
    <classifier>mac</classifier>
</dependency>
```

### 3. Build Scripts

#### Cross-Platform Execution
```bash
# Unix/Linux/macOS
#!/bin/bash
mvn javafx:run

# Windows
@echo off
call mvn javafx:run
```

## Security Architecture

### 1. Input Validation

#### Text Field Validation
```java
private void validateInput(String input) {
    if (input == null || input.trim().isEmpty()) {
        throw new IllegalArgumentException("Input cannot be null or empty");
    }
}
```

#### Hash Function Security
- **Deterministic**: Same input always produces same output
- **Uniform Distribution**: Minimizes clustering
- **Fast Computation**: Efficient for large datasets

### 2. Error Handling

#### Exception Handling Strategy
```java
try {
    // Operation
} catch (Exception e) {
    // Log error and provide user feedback
    outputArea.appendText("Error: " + e.getMessage() + "\n");
}
```

## Testing Architecture

### 1. Test Strategy

#### Unit Testing
- **Individual Methods**: Test each operation in isolation
- **Edge Cases**: Null values, empty collections
- **Boundary Conditions**: Capacity limits, load factor thresholds

#### Integration Testing
- **End-to-End**: Complete workflow testing
- **Performance**: Load factor and rehashing validation
- **Memory**: Memory leak detection

#### User Interface Testing
- **Interactive Testing**: JavaFX interface validation
- **User Scenarios**: Common usage patterns
- **Error Handling**: Invalid input handling

### 2. Test Structure

```
Test Classes
├── TestMyHashMap.java
│   ├── testPut()
│   ├── testGet()
│   ├── testRemove()
│   └── testClear()
└── TestMyHashSet.java
    ├── testAdd()
    ├── testContains()
    ├── testRemove()
    └── testIterator()
```

## Deployment Architecture

### 1. Build Process

#### Maven Build Pipeline
```
Source Code → Compile → Test → Package → Deploy
     ↓           ↓        ↓       ↓        ↓
Java Files → Bytecode → Tests → JAR → Distribution
```

#### Build Artifacts
- **Executable JAR**: `target/maphash-1.0.0.jar`
- **Source JAR**: `target/maphash-1.0.0-sources.jar`
- **Documentation**: `target/site/`

### 2. Distribution Strategy

#### Cross-Platform Distribution
- **Source Code**: Git repository
- **Binary Distribution**: Maven Central (future)
- **Documentation**: GitHub Pages (future)

#### Platform-Specific Considerations
- **macOS**: Universal binary support
- **Windows**: Installer package (future)
- **Linux**: Package manager integration (future)

## Monitoring and Logging Architecture

### 1. Application Monitoring

#### Performance Metrics
- **Operation Timing**: Measure operation performance
- **Memory Usage**: Track memory consumption
- **Load Factor**: Monitor hash table efficiency

#### User Interaction Tracking
- **Operation Counts**: Track user operations
- **Error Rates**: Monitor error frequencies
- **Usage Patterns**: Analyze user behavior

### 2. Logging Strategy

#### Log Levels
- **DEBUG**: Detailed operation information
- **INFO**: General application state
- **WARN**: Potential issues
- **ERROR**: Error conditions

#### Log Output
```java
// Example logging
logger.debug("Adding entry: [" + key + ", " + value + "]");
logger.info("HashMap size: " + size);
logger.warn("Load factor threshold approaching: " + loadFactor);
```

## Future Architecture Enhancements

### 1. Microservices Architecture

#### Service Decomposition
- **Data Structure Service**: Core hash table operations
- **UI Service**: JavaFX application
- **Testing Service**: Automated test execution
- **Monitoring Service**: Performance metrics collection

### 2. Cloud-Native Architecture

#### Containerization
- **Docker**: Application containerization
- **Kubernetes**: Orchestration and scaling
- **Service Mesh**: Inter-service communication

### 3. Event-Driven Architecture

#### Event Sourcing
- **Operation Events**: Record all data structure operations
- **State Replay**: Reconstruct state from events
- **Audit Trail**: Complete operation history

## Conclusion

The architecture of the Map and Hash Set demonstration project emphasizes:

- **Modularity**: Clear separation of concerns
- **Extensibility**: Easy to add new features
- **Maintainability**: Clean, well-documented code
- **Performance**: Efficient algorithms and data structures
- **Cross-Platform**: Works on multiple platforms
- **Testability**: Comprehensive testing strategy

This architecture provides a solid foundation for both educational purposes and practical applications, while maintaining the flexibility to evolve and adapt to future requirements. 