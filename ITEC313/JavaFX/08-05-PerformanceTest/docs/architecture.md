# Performance Test Application - Architecture

## System Architecture

### Overview

The application follows a layered architecture pattern with clear separation of concerns:

```
┌─────────────────────────────────────┐
│           JavaFX UI Layer           │
│  (PerformanceTestApp, EnhancedApp) │
└─────────────────────────────────────┘
┌─────────────────────────────────────┐
│         Business Logic Layer        │
│  (AlgorithmPerformanceTest)        │
└─────────────────────────────────────┘
┌─────────────────────────────────────┐
│         Core Algorithm Layer        │
│      (PerformanceTest)             │
└─────────────────────────────────────┘
┌─────────────────────────────────────┐
│         Infrastructure Layer        │
│      (Maven, JavaFX Runtime)      │
└─────────────────────────────────────┘
```

## Component Architecture

### 1. UI Components

#### PerformanceTestApp
- **Purpose**: Basic JavaFX application with simple performance testing
- **Features**: 
  - Text output area
  - Progress bar
  - Simple button controls
  - Background thread execution

#### EnhancedPerformanceTestApp
- **Purpose**: Advanced application with algorithm comparison and visualization
- **Features**:
  - Line chart for performance visualization
  - Split pane layout
  - Multiple test types
  - Real-time chart updates

### 2. Business Logic Components

#### AlgorithmPerformanceTest
- **Purpose**: Encapsulates all algorithm testing logic
- **Responsibilities**:
  - Algorithm implementations
  - Performance measurement
  - Test data generation
  - Result formatting

#### PerformanceTest
- **Purpose**: Original simple performance test implementation
- **Responsibilities**:
  - Basic loop performance testing
  - Console output formatting

### 3. Infrastructure Components

#### Maven Configuration (pom.xml)
- **Purpose**: Build system and dependency management
- **Features**:
  - Cross-platform JavaFX dependencies
  - Platform detection
  - JavaFX Maven plugin
  - Shade plugin for executable JARs

#### Build Scripts
- **run.sh**: Unix/Linux/macOS execution
- **run.bat**: Windows execution
- **run_direct.sh**: Direct Java execution

## Design Patterns

### 1. Model-View-Controller (MVC)

```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│    View     │    │ Controller  │    │   Model     │
│ (JavaFX UI) │◄──►│ (Event      │◄──►│ (Algorithm  │
│             │    │  Handlers)  │    │  Logic)     │
└─────────────┘    └─────────────┘    └─────────────┘
```

- **View**: JavaFX Scene components
- **Controller**: Event handlers and UI logic
- **Model**: Algorithm implementations and test logic

### 2. Command Pattern

Each test type is encapsulated as a separate command:

```java
// Example: Algorithm comparison command
private void runAlgorithmComparison() {
    executor.submit(() -> {
        // Execute algorithm tests
        // Update UI via Platform.runLater()
    });
}
```

### 3. Observer Pattern

UI components observe test progress:

```java
Platform.runLater(() -> {
    progressBar.setProgress(progress);
    outputArea.appendText(result);
});
```

## Concurrency Model

### Threading Strategy

```
┌─────────────────────────────────────┐
│        JavaFX Application Thread    │
│  (UI Updates, Event Handling)      │
└─────────────────────────────────────┘
                    │
                    ▼
┌─────────────────────────────────────┐
│        Background Worker Thread     │
│  (Performance Test Execution)      │
└─────────────────────────────────────┘
```

### Thread Safety

- **UI Updates**: All UI modifications go through `Platform.runLater()`
- **Shared State**: Minimal shared state between threads
- **Executor Service**: Managed thread pool for background tasks

## Data Flow

### 1. Test Execution Flow

```
User Click → Event Handler → Background Thread → 
Algorithm Execution → Result Collection → 
UI Update (Platform.runLater) → Display Results
```

### 2. Chart Data Flow

```
Algorithm Test → Performance Data → 
Chart Series Update → Line Chart Rendering
```

## Error Handling

### 1. Exception Handling Strategy

```java
try {
    // Execute performance tests
} catch (Exception e) {
    Platform.runLater(() -> {
        outputArea.appendText("Error: " + e.getMessage());
        // Reset UI state
    });
}
```

### 2. Resource Management

- **Executor Service**: Proper shutdown on application close
- **Memory Management**: Large arrays created and discarded properly
- **UI Resources**: Automatic cleanup by JavaFX

## Performance Considerations

### 1. Memory Management

- **Array Generation**: Arrays created for each test, not reused
- **Chart Data**: Chart series accumulate data over time
- **String Concatenation**: StringBuilder for large output

### 2. UI Responsiveness

- **Background Processing**: All heavy computation in background threads
- **Progress Updates**: Regular progress updates to keep UI responsive
- **Non-blocking Operations**: UI never blocks during testing

### 3. Algorithm Optimization

- **Efficient Implementations**: Standard algorithm implementations
- **Appropriate Data Structures**: Arrays for random access
- **Minimal Object Creation**: Reuse objects where possible

## Configuration Management

### 1. Platform Detection

```xml
<plugin>
    <groupId>kr.motd.maven</groupId>
    <artifactId>os-maven-plugin</artifactId>
    <version>1.7.2</version>
    <executions>
        <execution>
            <phase>initialize</phase>
            <goals>
                <goal>detect</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

### 2. JavaFX Module Configuration

```xml
<configuration>
    <mainClass>com.acu.javafx.performancetest.PerformanceTestApp</mainClass>
    <options>
        <option>--add-opens</option>
        <option>javafx.graphics/javafx.scene=ALL-UNNAMED</option>
    </options>
</configuration>
```

## Testing Strategy

### 1. Unit Testing

- **Algorithm Tests**: Individual algorithm correctness
- **Performance Tests**: Timing accuracy
- **UI Tests**: Component behavior

### 2. Integration Testing

- **End-to-End**: Complete test execution flow
- **Cross-Platform**: Build and run on different platforms
- **Memory Tests**: Large dataset handling

## Deployment Architecture

### 1. Build Process

```
Source Code → Maven Compile → JavaFX Plugin → 
Executable JAR → Platform-Specific Distribution
```

### 2. Runtime Dependencies

- **JavaFX Runtime**: Platform-specific native libraries
- **JVM**: Java 24 or later
- **System Resources**: Sufficient memory for large tests

## Security Considerations

### 1. Input Validation

- **Test Parameters**: Validate input sizes
- **Memory Limits**: Prevent excessive memory usage
- **Thread Safety**: Proper synchronization

### 2. Resource Limits

- **Memory Monitoring**: Track memory usage during tests
- **Timeout Handling**: Prevent infinite loops
- **Graceful Degradation**: Handle resource exhaustion

## Monitoring and Observability

### 1. Progress Tracking

- **Visual Progress**: Progress bars and status labels
- **Real-time Updates**: Live result display
- **Error Reporting**: Clear error messages

### 2. Performance Metrics

- **Execution Time**: Precise timing measurements
- **Memory Usage**: Optional memory tracking
- **Algorithm Complexity**: Visual complexity demonstration 