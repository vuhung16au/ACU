# Eight Queens - Architecture and Design Patterns

## Architecture Overview

The Eight Queens application follows a clean, modular architecture that separates concerns between the algorithmic logic and the user interface. The design emphasizes maintainability, extensibility, and cross-platform compatibility.

## System Architecture

### High-Level Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    JavaFX Application                      │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────────┐    ┌─────────────────────────────────┐ │
│  │   UI Layer      │    │        Algorithm Layer         │ │
│  │                 │    │                                 │ │
│  │ • GridPane      │◄──►│ • Backtracking Algorithm       │ │
│  │ • Labels        │    │ • Constraint Validation        │ │
│  │ • Styling       │    │ • Solution Search              │ │
│  │ • Event Handling│    │ • Data Structures              │ │
│  └─────────────────┘    └─────────────────────────────────┘ │
├─────────────────────────────────────────────────────────────┤
│                    JavaFX Framework                       │
├─────────────────────────────────────────────────────────────┤
│                    Java Platform                          │
└─────────────────────────────────────────────────────────────┘
```

### Component Architecture

#### 1. Application Entry Point
```java
public class EightQueens extends Application
```
- **Responsibility**: Main application class and JavaFX lifecycle management
- **Pattern**: Template Method Pattern (Application.start())
- **Dependencies**: JavaFX Application framework

#### 2. Algorithm Module
```java
private boolean search()
private int findPosition(int k)
public boolean isValid(int row, int column)
```
- **Responsibility**: Core backtracking algorithm implementation
- **Pattern**: Strategy Pattern (algorithm implementation)
- **Dependencies**: None (pure algorithmic logic)

#### 3. UI Module
```java
GridPane chessBoard
Label[][] labels
```
- **Responsibility**: Visual representation and user interaction
- **Pattern**: Composite Pattern (GridPane contains Labels)
- **Dependencies**: JavaFX UI components

## Design Patterns

### 1. Template Method Pattern

The `Application` class provides a template for JavaFX applications:

```java
public class EightQueens extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Custom initialization logic
    }
}
```

**Benefits:**
- Standardized application lifecycle
- Consistent startup behavior
- Platform-specific optimizations handled by framework

### 2. Strategy Pattern

The backtracking algorithm implements a search strategy:

```java
private boolean search() {
    // Algorithm strategy implementation
}
```

**Benefits:**
- Encapsulated algorithm logic
- Easy to swap different search strategies
- Testable in isolation

### 3. Composite Pattern

The UI uses composite pattern for the chess board:

```java
GridPane chessBoard = new GridPane();
Label[][] labels = new Label[SIZE][SIZE];
```

**Benefits:**
- Hierarchical UI structure
- Consistent behavior across components
- Simplified event handling

### 4. Data Access Object (DAO) Pattern

The queen positions are managed through a simple DAO:

```java
private int[] queens = {-1, -1, -1, -1, -1, -1, -1, -1};
```

**Benefits:**
- Centralized data access
- Encapsulated data structure
- Easy to modify representation

## Module Dependencies

### Dependency Graph

```
EightQueens (Main Class)
├── JavaFX Application Framework
├── Algorithm Module
│   ├── search()
│   ├── findPosition()
│   └── isValid()
├── UI Module
│   ├── GridPane
│   ├── Labels
│   └── Styling
└── Data Module
    └── queens[] array
```

### Dependency Management

#### Maven Dependencies
```xml
<dependencies>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>21</version>
    </dependency>
    <!-- Additional JavaFX modules -->
</dependencies>
```

#### Platform Dependencies
- **JavaFX Runtime**: Platform-specific native libraries
- **Java Platform**: JVM and core libraries
- **Maven**: Build and dependency management

## Data Flow Architecture

### 1. Initialization Flow

```
Application Start
    ↓
Initialize queens[] array
    ↓
Call search() algorithm
    ↓
Find solution
    ↓
Create UI components
    ↓
Display solution
```

### 2. Algorithm Flow

```
search() method
    ↓
Initialize k = 0
    ↓
while (k >= 0 && k < SIZE)
    ↓
findPosition(k)
    ↓
if (position found)
    queens[k] = position
    k++
else
    queens[k] = -1
    k--
    ↓
Return solution found
```

### 3. UI Rendering Flow

```
Create GridPane
    ↓
Create 8x8 Label array
    ↓
Apply styling to each Label
    ↓
Place queens on board
    ↓
Create Scene with GridPane
    ↓
Display Stage
```

## Cross-Platform Architecture

### Platform Detection

The Maven configuration includes platform detection:

```xml
<extensions>
    <extension>
        <groupId>kr.motd.maven</groupId>
        <artifactId>os-maven-plugin</artifactId>
        <version>1.7.2</version>
    </extension>
</extensions>
```

### Platform-Specific Considerations

#### macOS
- **Apple Silicon**: ARM64 native libraries
- **Intel**: x86_64 native libraries
- **JavaFX**: Bundled with recent JDK distributions

#### Windows
- **x86_64**: Standard Intel architecture
- **ARM64**: Windows on ARM support
- **JavaFX**: May need separate installation

#### Linux
- **x86_64**: Standard Intel architecture
- **ARM64**: ARM server and desktop support
- **JavaFX**: OpenJFX package installation

## Build Architecture

### Maven Build Process

```
Source Code
    ↓
Maven Compiler Plugin (Java 24)
    ↓
Compiled Classes
    ↓
JavaFX Maven Plugin
    ↓
Executable Application
```

### Build Scripts Architecture

#### Unix/Linux/macOS Scripts
```bash
run.sh          # Maven-based execution
run_direct.sh   # Direct Java execution
```

#### Windows Scripts
```cmd
run.bat         # Maven-based execution
```

### Build Configuration

#### Maven Plugins
1. **Compiler Plugin**: Java 24 compilation
2. **JavaFX Plugin**: Application execution
3. **Shade Plugin**: Executable JAR creation

#### Platform Detection
- **OS Detection**: Automatic platform identification
- **Architecture Detection**: ARM64 vs x86_64
- **Dependency Resolution**: Platform-specific libraries

## Error Handling Architecture

### Exception Handling Strategy

#### Algorithm Errors
```java
// No explicit exception handling needed
// Algorithm guarantees termination
```

#### UI Errors
```java
// JavaFX handles most UI exceptions
// Graceful degradation for missing resources
```

#### Build Errors
```bash
# Script-level error checking
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven not found"
    exit 1
fi
```

## Performance Architecture

### Memory Management

#### Algorithm Memory
- **Fixed Size**: 8 integers for queen positions
- **Stack Space**: Minimal recursion depth
- **No Dynamic Allocation**: Predictable memory usage

#### UI Memory
- **Grid Components**: 64 Label objects
- **Image Resources**: Minimal (text-based queens)
- **Event Handlers**: None (static display)

### Execution Performance

#### Algorithm Performance
- **Time Complexity**: O(8!) worst case, but much better average
- **Space Complexity**: O(1) for data structures
- **Early Termination**: Backtracking stops on invalid paths

#### UI Performance
- **Rendering**: Efficient JavaFX rendering
- **Responsiveness**: Immediate solution display
- **Memory**: Minimal UI memory footprint

## Security Architecture

### Code Security
- **No External Dependencies**: Self-contained algorithm
- **No Network Access**: Local execution only
- **No File I/O**: No external file access

### Platform Security
- **Java Security Manager**: Standard Java security
- **JavaFX Security**: Sandboxed UI components
- **Maven Security**: Verified dependency sources

## Testing Architecture

### Unit Testing Strategy
- **Algorithm Testing**: Pure function testing
- **UI Testing**: Component testing
- **Integration Testing**: End-to-end testing

### Test Structure
```
src/
├── main/
│   └── java/
│       └── com/acu/javafx/eightqueens/
│           └── EightQueens.java
└── test/
    └── java/
        └── com/acu/javafx/eightqueens/
            ├── EightQueensTest.java
            ├── AlgorithmTest.java
            └── UITest.java
```

## Deployment Architecture

### JAR Packaging
```xml
<maven-shade-plugin>
    <configuration>
        <transformers>
            <transformer>
                <mainClass>com.acu.javafx.eightqueens.EightQueens</mainClass>
            </transformer>
        </transformers>
    </configuration>
</maven-shade-plugin>
```

### Distribution Strategy
- **Source Distribution**: Maven project
- **Binary Distribution**: Executable JAR
- **Cross-Platform**: Platform-specific scripts

## Future Architecture Considerations

### Scalability
- **N-Queens Extension**: Configurable board size
- **Multiple Solutions**: Solution enumeration
- **Interactive Mode**: User-driven placement

### Maintainability
- **Modular Design**: Separated concerns
- **Clean Code**: Well-documented implementation
- **Extensible Architecture**: Easy to extend

### Performance Optimization
- **Algorithm Optimization**: More efficient search strategies
- **UI Optimization**: Responsive design improvements
- **Memory Optimization**: Reduced memory footprint

This architecture provides a solid foundation for the Eight Queens application while maintaining flexibility for future enhancements and cross-platform compatibility. 