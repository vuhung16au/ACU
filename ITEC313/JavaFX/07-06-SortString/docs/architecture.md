# Architecture and Design Patterns

## System Architecture

### High-Level Architecture

The JavaFX String Sorting Demo follows a layered architecture pattern:

```
┌─────────────────────────────────────┐
│           Presentation Layer        │
│         (JavaFX UI Components)     │
├─────────────────────────────────────┤
│           Business Logic Layer      │
│         (Sorting Algorithms)       │
├─────────────────────────────────────┤
│           Data Layer               │
│         (String Input/Output)      │
└─────────────────────────────────────┘
```

### Component Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    SortStringDemo                          │
│                 (Main Application)                         │
├─────────────────────────────────────────────────────────────┤
│  UI Components    │  Event Handlers    │  Business Logic  │
│  - TextField      │  - Button Actions  │  - Input Parsing │
│  - TextArea       │  - Input Validation│  - Error Handling│
│  - Buttons        │  - Output Display  │  - Sorting Logic │
└─────────────────────────────────────────────────────────────┘
```

## Design Patterns

### 1. Model-View-Controller (MVC)

**Model**: 
- `SortStringByLength.MyComparator`: Encapsulates length-based sorting logic
- `SortStringIgnoreCase`: Encapsulates case-insensitive sorting logic
- String processing and validation logic

**View**: 
- JavaFX Scene with VBox layout
- TextField for input
- TextArea for output display
- Buttons for user interactions

**Controller**: 
- Event handlers in `SortStringDemo`
- Input validation and error handling
- Coordination between Model and View

### 2. Strategy Pattern

The application implements the Strategy pattern for different sorting algorithms:

```java
// Strategy 1: Length-based sorting
Arrays.sort(strings, new SortStringByLength.MyComparator());

// Strategy 2: Case-insensitive sorting
stringList.sort((s1, s2) -> s1.compareToIgnoreCase(s2));
```

### 3. Factory Pattern

The application uses a factory-like approach for creating different sorting strategies based on user selection.

## Class Structure

### Core Classes

#### SortStringDemo
- **Purpose**: Main JavaFX application class
- **Responsibilities**: 
  - UI initialization and layout
  - Event handling
  - Input validation
  - Output formatting
- **Dependencies**: JavaFX framework, sorting utility classes

#### SortStringByLength
- **Purpose**: Demonstrates custom comparator implementation
- **Responsibilities**:
  - Custom comparator for length-based sorting
  - Console-based demonstration
- **Dependencies**: Java Collections framework

#### SortStringIgnoreCase
- **Purpose**: Demonstrates lambda expression sorting
- **Responsibilities**:
  - Case-insensitive sorting logic
  - Console-based demonstration
- **Dependencies**: Java Collections framework

#### Launcher
- **Purpose**: Application entry point
- **Responsibilities**:
  - Delegates to main application class
  - Provides clean separation for build tools
- **Dependencies**: SortStringDemo

### Package Structure

```
com.acu.javafx.sortstring/
├── Launcher.java              # Application entry point
├── SortStringDemo.java        # Main JavaFX application
├── SortStringByLength.java    # Length-based sorting
└── SortStringIgnoreCase.java  # Case-insensitive sorting
```

## Build Architecture

### Maven Configuration

The project uses Maven for build management with the following key components:

#### Dependencies
- **JavaFX Controls**: UI components
- **JavaFX Graphics**: Rendering engine
- **JavaFX Base**: Core functionality
- **JavaFX FXML**: Layout support (for future use)

#### Plugins
- **Maven Compiler Plugin**: Java 24 compilation
- **JavaFX Maven Plugin**: Application execution
- **Maven Shade Plugin**: Executable JAR creation

#### Platform Detection
- **OS Maven Plugin**: Automatic platform detection
- **Cross-platform Dependencies**: Architecture-specific JavaFX modules

### Cross-Platform Support

#### Platform Detection
```xml
<os.detected.classifier>${os.detected.classifier}</os.detected.classifier>
<os.detected.name>${os.detected.name}</os.detected.name>
<os.detected.arch>${os.detected.arch}</os.detected.arch>
```

#### Supported Platforms
- **macOS**: Intel (x86_64) and Apple Silicon (ARM64)
- **Windows**: x86_64 and ARM64
- **Linux**: x86_64 and ARM64

## Runtime Architecture

### JavaFX Application Lifecycle

1. **Application Launch**: `Launcher.main()` → `SortStringDemo.main()`
2. **Stage Initialization**: `Application.start(Stage)`
3. **Scene Creation**: UI component initialization
4. **Event Loop**: User interaction handling
5. **Application Shutdown**: Clean resource disposal

### Memory Management

- **JavaFX Components**: Automatic garbage collection
- **Event Handlers**: Lambda expressions for memory efficiency
- **String Processing**: Immutable string operations
- **UI Updates**: Platform-specific rendering

## Error Handling

### Input Validation
- **Empty Input**: Checks for non-empty strings
- **Format Validation**: Ensures proper comma-separated format
- **Exception Handling**: Try-catch blocks for robust operation

### User Feedback
- **Error Dialogs**: Alert dialogs for user notifications
- **Input Hints**: Placeholder text and descriptions
- **Clear Functionality**: Reset capability for user convenience

## Performance Considerations

### Sorting Algorithms
- **Time Complexity**: O(n log n) for both sorting methods
- **Space Complexity**: O(n) for temporary storage
- **Memory Efficiency**: In-place sorting where possible

### UI Responsiveness
- **Event-Driven Architecture**: Non-blocking UI operations
- **Background Processing**: Sorting operations don't block UI
- **Efficient Updates**: Minimal UI redraws

## Security Considerations

### Input Sanitization
- **String Validation**: Prevents malicious input
- **Length Limits**: Prevents memory exhaustion
- **Character Encoding**: Proper UTF-8 handling

### Resource Management
- **File Access**: No direct file system access
- **Network Access**: No network operations
- **System Resources**: Minimal system resource usage

## Testing Strategy

### Unit Testing
- **Sorting Logic**: Individual algorithm testing
- **Input Validation**: Edge case testing
- **Error Handling**: Exception scenario testing

### Integration Testing
- **UI Integration**: End-to-end user workflow testing
- **Cross-Platform**: Platform-specific behavior testing
- **Build Verification**: Maven build process testing

## Deployment Architecture

### Build Artifacts
- **JAR File**: Executable application package
- **Dependencies**: Platform-specific JavaFX modules
- **Documentation**: Comprehensive project documentation

### Distribution
- **Cross-Platform Scripts**: Platform-specific execution scripts
- **Maven Repository**: Centralized dependency management
- **Documentation**: Self-contained project documentation 