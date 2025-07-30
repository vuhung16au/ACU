# Prime Numbers Algorithms - Architecture and Design Patterns

## Overview

This document describes the detailed architecture and design patterns used in the Prime Numbers Algorithms Demo application.

## System Architecture

### 1. High-Level Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    JavaFX Application                      │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐      │
│  │   UI Layer  │  │  Business   │  │  Algorithm  │      │
│  │             │  │   Logic     │  │   Layer     │      │
│  └─────────────┘  └─────────────┘  └─────────────┘      │
├─────────────────────────────────────────────────────────────┤
│                    Maven Build System                     │
├─────────────────────────────────────────────────────────────┤
│                    Java Runtime Environment               │
└─────────────────────────────────────────────────────────────┘
```

### 2. Component Architecture

#### UI Layer (JavaFX)
- **PrimeNumbersDemo**: Main application class extending `Application`
- **Scene Graph**: Hierarchical structure of UI components
- **Event Handling**: Lambda expressions for user interactions
- **Layout Management**: VBox, HBox, ScrollPane containers

#### Business Logic Layer
- **Algorithm Selection**: Dropdown-based algorithm choice
- **Input Validation**: User input processing and validation
- **Output Management**: Console output capture and display
- **Error Handling**: Exception management and user feedback

#### Algorithm Layer
- **PrimeNumber**: Basic trial division algorithm
- **PrimeNumbers**: Optimized trial division with √n bound
- **EfficientPrimeNumbers**: List-based optimization
- **SieveOfEratosthenes**: Classic sieve algorithm

## Design Patterns

### 1. MVC (Model-View-Controller) Pattern

#### Model
- **Algorithm Classes**: Encapsulate prime number logic
- **Data Structures**: Arrays, Lists for algorithm state
- **Business Rules**: Prime number testing logic

#### View
- **JavaFX Components**: Labels, Buttons, TextArea, ComboBox
- **Layout Containers**: VBox, HBox, ScrollPane
- **Styling**: Fonts, colors, spacing

#### Controller
- **Event Handlers**: Button click handlers, input validation
- **Algorithm Execution**: Orchestrates algorithm runs
- **Output Management**: Captures and displays results

### 2. Strategy Pattern

The application uses the Strategy pattern for algorithm selection:

```java
// Strategy interface (implicit)
public interface PrimeAlgorithm {
    void execute(String input);
}

// Concrete strategies
public class PrimeNumber implements PrimeAlgorithm {
    public void execute(String input) {
        // Basic trial division implementation
    }
}

public class SieveOfEratosthenes implements PrimeAlgorithm {
    public void execute(String input) {
        // Sieve implementation
    }
}

// Context (PrimeNumbersDemo)
private void runAlgorithm() {
    String selectedAlgorithm = algorithmComboBox.getValue();
    // Strategy selection and execution
}
```

### 3. Template Method Pattern

The algorithm execution follows a template method pattern:

```java
private void runAlgorithm() {
    // 1. Setup phase
    String selectedAlgorithm = algorithmComboBox.getValue();
    String input = inputField.getText().trim();
    
    // 2. Output capture setup
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(baos);
    PrintStream old = System.out;
    System.setOut(ps);

    try {
        // 3. Algorithm execution (varies by strategy)
        switch (selectedAlgorithm) {
            case "PrimeNumber - First 50 primes":
                PrimeNumber.main(new String[]{});
                break;
            // ... other cases
        }
    } catch (Exception e) {
        // 4. Error handling
        appendOutput("Error running algorithm: " + e.getMessage() + "\n");
    } finally {
        // 5. Cleanup phase
        System.out.flush();
        System.setOut(old);
    }
    
    // 6. Output processing
    String output = baos.toString();
    appendOutput("=== " + selectedAlgorithm + " ===\n");
    appendOutput(output);
}
```

### 4. Observer Pattern

The UI components observe user actions:

```java
// Observer registration
runButton.setOnAction(e -> runAlgorithm());
clearButton.setOnAction(e -> clearOutput());

// Observer implementation
private void runAlgorithm() {
    // Handle algorithm execution
}

private void clearOutput() {
    // Handle output clearing
}
```

## Class Structure

### 1. Main Application Class

```java
public class PrimeNumbersDemo extends Application {
    // UI Components
    private TextArea outputArea;
    private TextField inputField;
    private ComboBox<String> algorithmComboBox;
    private Button runButton;
    private Button clearButton;

    // Lifecycle methods
    @Override
    public void start(Stage primaryStage) {
        // UI initialization
    }

    // Event handlers
    private void runAlgorithm() {
        // Algorithm execution logic
    }

    private void clearOutput() {
        // Output clearing logic
    }
}
```

### 2. Algorithm Classes

Each algorithm class follows a consistent structure:

```java
public class AlgorithmName {
    public static void main(String[] args) {
        // Algorithm implementation
        // Console output
    }
}
```

## Data Flow

### 1. User Input Flow

```
User Input → TextField → Validation → Algorithm Selection → Execution
```

### 2. Algorithm Execution Flow

```
Algorithm Selection → Input Validation → Output Capture → Algorithm Run → Result Display
```

### 3. Output Display Flow

```
Console Output → ByteArrayOutputStream → TextArea → ScrollPane → User View
```

## Error Handling Strategy

### 1. Input Validation

```java
if (input.isEmpty()) {
    appendOutput("Please enter a number for this algorithm.\n");
    return;
}
```

### 2. Exception Handling

```java
try {
    // Algorithm execution
} catch (Exception e) {
    appendOutput("Error running algorithm: " + e.getMessage() + "\n");
} finally {
    // Resource cleanup
}
```

### 3. Resource Management

```java
// Stream management
ByteArrayOutputStream baos = new ByteArrayOutputStream();
PrintStream ps = new PrintStream(baos);
PrintStream old = System.out;
System.setOut(ps);

try {
    // Algorithm execution
} finally {
    System.out.flush();
    System.setOut(old);
}
```

## Cross-Platform Considerations

### 1. Maven Configuration

#### Platform Detection
```xml
<profiles>
    <profile>
        <id>mac</id>
        <activation>
            <os>
                <family>mac</family>
            </os>
        </activation>
        <properties>
            <javafx.platform>mac</javafx.platform>
        </properties>
    </profile>
    <!-- Windows and Linux profiles -->
</profiles>
```

#### JavaFX Dependencies
```xml
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-controls</artifactId>
    <version>${javafx.version}</version>
</dependency>
```

### 2. Build Scripts

#### Unix/Linux/macOS
```bash
#!/bin/bash
# Platform detection and execution
if [[ "$OSTYPE" == "darwin"* ]]; then
    # macOS specific configuration
fi
```

#### Windows
```batch
@echo off
REM Windows-specific execution
```

## Performance Architecture

### 1. Memory Management

#### Stream Management
- **ByteArrayOutputStream**: Efficient in-memory output capture
- **PrintStream Redirection**: Minimal overhead
- **Automatic Cleanup**: Proper resource disposal

#### UI Performance
- **Non-blocking Operations**: Algorithm execution doesn't freeze UI
- **Efficient Layout**: Minimal layout recalculations
- **Memory-Efficient Components**: Appropriate component sizing

### 2. Algorithm Performance

#### Complexity Analysis
- **PrimeNumber**: O(n²) - Educational demonstration
- **PrimeNumbers**: O(n√n) - Optimized trial division
- **EfficientPrimeNumbers**: O(n log log n) - List-based optimization
- **SieveOfEratosthenes**: O(n log log n) - Most efficient for large ranges

#### Memory Usage
- **PrimeNumber**: O(1) - Constant space
- **PrimeNumbers**: O(1) - Constant space
- **EfficientPrimeNumbers**: O(π(n)) - Space proportional to number of primes
- **SieveOfEratosthenes**: O(n) - Linear space

## Security Considerations

### 1. Input Validation
- **Number Range**: Validate input within reasonable bounds
- **Type Safety**: Ensure numeric input
- **Resource Limits**: Prevent excessive memory usage

### 2. Resource Protection
- **Stream Management**: Proper cleanup of I/O streams
- **Memory Limits**: Prevent memory exhaustion
- **Exception Handling**: Graceful error recovery

## Testing Strategy

### 1. Unit Testing
- **Algorithm Testing**: Individual algorithm correctness
- **UI Component Testing**: Component behavior validation
- **Integration Testing**: End-to-end functionality

### 2. Performance Testing
- **Algorithm Benchmarking**: Performance comparison
- **Memory Usage Testing**: Resource consumption analysis
- **Cross-Platform Testing**: Platform-specific behavior

## Deployment Architecture

### 1. Maven Build
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.4.1</version>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>shade</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

### 2. Executable JAR
- **Fat JAR**: Includes all dependencies
- **Main Class**: Specified in manifest
- **Cross-Platform**: Works on all supported platforms

## Future Architecture Enhancements

### 1. Modular Design
- **Java Modules**: Use Java 9+ module system
- **Plugin Architecture**: Extensible algorithm support
- **Service Layer**: Abstract algorithm execution

### 2. Advanced UI
- **FXML**: Separate UI definition from logic
- **CSS Styling**: Customizable appearance
- **Responsive Design**: Adaptive to different screen sizes

### 3. Performance Monitoring
- **Metrics Collection**: Algorithm performance tracking
- **Visualization**: Real-time performance charts
- **Profiling**: Detailed performance analysis

## Conclusion

The architecture successfully demonstrates:
1. **Separation of Concerns**: Clear separation between UI, business logic, and algorithms
2. **Design Pattern Usage**: Appropriate use of MVC, Strategy, and Template Method patterns
3. **Cross-Platform Compatibility**: Robust support for multiple operating systems
4. **Maintainability**: Clean, well-structured code that's easy to extend
5. **Educational Value**: Clear demonstration of software architecture principles

The design prioritizes simplicity, maintainability, and educational value while providing a robust, cross-platform application. 