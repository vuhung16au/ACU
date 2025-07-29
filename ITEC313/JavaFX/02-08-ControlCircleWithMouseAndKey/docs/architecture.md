# ControlCircleWithMouseAndKey - Architecture and Design Patterns

## Architecture Overview

The application follows a layered architecture pattern with clear separation of concerns:

```
┌─────────────────────────────────────────────────────────────┐
│                    Presentation Layer                       │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────┐ │
│  │   BorderPane    │  │      HBox       │  │   Buttons   │ │
│  └─────────────────┘  └─────────────────┘  └─────────────┘ │
└─────────────────────────────────────────────────────────────┘
┌─────────────────────────────────────────────────────────────┐
│                     Business Logic Layer                    │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────┐ │
│  │ControlCircleWith│  │   Event Handlers│  │CirclePane   │ │
│  │MouseAndKey      │  │                 │  │             │ │
│  └─────────────────┘  └─────────────────┘  └─────────────┘ │
└─────────────────────────────────────────────────────────────┘
┌─────────────────────────────────────────────────────────────┐
│                      Data Layer                            │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────┐ │
│  │    Circle       │  │   Properties    │  │ Constraints │ │
│  └─────────────────┘  └─────────────────┘  └─────────────┘ │
└─────────────────────────────────────────────────────────────┘
```

## Design Patterns

### 1. Model-View-Controller (MVC) Pattern

**Model**: `CirclePane` manages the circle state and behavior
**View**: JavaFX Scene with BorderPane and HBox layout
**Controller**: Event handlers in `ControlCircleWithMouseAndKey`

### 2. Observer Pattern

The application implements the Observer pattern through JavaFX's event system:

```java
// Subject: CirclePane
// Observers: Event handlers
circlePane.setOnMouseClicked(e -> {
    // Observer logic
});
```

### 3. Strategy Pattern

Different input methods (buttons, mouse, keyboard) use the same strategy for circle manipulation:

```java
// Common strategy interface
public void enlarge();
public void shrink();
```

### 4. Factory Pattern

The application uses JavaFX's built-in factory patterns for UI components:

```java
Button btEnlarge = new Button("Enlarge");
HBox hBox = new HBox();
BorderPane borderPane = new BorderPane();
```

## Component Architecture

### 1. Main Application Class

**File**: `ControlCircleWithMouseAndKey.java`
**Responsibilities**:
- Application lifecycle management
- UI component creation and assembly
- Event handler registration
- Scene and stage management

**Key Methods**:
- `start(Stage)`: Application entry point
- `setupUI(Stage)`: UI component creation
- `setupEventHandlers()`: Event binding

### 2. Custom Pane Component

**File**: `CirclePane.java`
**Responsibilities**:
- Circle rendering and management
- Size constraint enforcement
- Centering and positioning logic
- State management

**Key Methods**:
- `enlarge()`: Increase circle radius
- `shrink()`: Decrease circle radius
- `centerCircle()`: Position circle in center
- `setRadius(double)`: Direct radius setting

### 3. Launcher Class

**File**: `Launcher.java`
**Responsibilities**:
- Alternative entry point for IDEs
- Delegation to main application class

## Event Architecture

### Event Flow Diagram

```
User Action
    ↓
Event Creation (JavaFX)
    ↓
Event Handler Selection
    ↓
Handler Execution
    ↓
Circle State Update
    ↓
Visual Redraw
    ↓
User Feedback
```

### Event Handler Types

1. **Button Event Handlers**
   - Type: `EventHandler<ActionEvent>`
   - Implementation: Lambda expressions
   - Trigger: Button clicks

2. **Mouse Event Handlers**
   - Type: `EventHandler<MouseEvent>`
   - Implementation: Anonymous handlers
   - Trigger: Mouse clicks (PRIMARY/SECONDARY)

3. **Keyboard Event Handlers**
   - Type: `EventHandler<KeyEvent>`
   - Implementation: Anonymous handlers
   - Trigger: Key presses (UP/DOWN arrows)

## Module System Architecture

### Module Declaration

```java
module com.acu.javafx.controlcirclewithmouseandkey {
    requires javafx.controls;
    requires javafx.fxml;
    
    exports com.acu.javafx.controlcirclewithmouseandkey;
}
```

### Module Dependencies

- **javafx.controls**: UI components (Button, HBox, BorderPane)
- **javafx.fxml**: FXML support (for future extensibility)
- **Internal**: Exports the main package for external access

## Build Architecture

### Maven Configuration

The project uses Maven with cross-platform configuration:

1. **Platform Detection**: OS Maven plugin for architecture-specific dependencies
2. **JavaFX Plugin**: For running JavaFX applications
3. **Shade Plugin**: For creating executable JARs
4. **Compiler Plugin**: Java 24 configuration

### Build Scripts

- **run.sh**: Unix/Linux/macOS execution
- **run.bat**: Windows execution
- **Cross-platform**: Automatic platform detection

## Error Handling Architecture

### Input Validation

1. **Size Constraints**: Enforced in `CirclePane`
2. **Event Validation**: Proper event type checking
3. **Focus Management**: Ensures keyboard events work

### Exception Handling

- **Runtime Exceptions**: Handled by JavaFX framework
- **Build Errors**: Maven provides detailed error messages
- **Platform Issues**: Scripts check for required tools

## Performance Considerations

### Memory Management

- **Object Reuse**: CirclePane reuses Circle object
- **Event Handler Efficiency**: Lambda expressions for minimal overhead
- **Layout Optimization**: Efficient BorderPane layout

### Rendering Performance

- **Minimal Redraws**: Only redraw when circle size changes
- **Efficient Positioning**: Simple center calculation
- **Responsive UI**: Immediate visual feedback

## Security Considerations

### Input Sanitization

- **Event Validation**: Proper event type checking
- **Size Constraints**: Prevents excessive resource usage
- **Module Isolation**: Proper module boundaries

### Platform Security

- **Sandboxed Execution**: JavaFX security model
- **Resource Limits**: Constrained circle size
- **Cross-Platform Safety**: Consistent behavior across platforms

## Testing Architecture

### Unit Testing Structure

```
src/test/java/com/acu/javafx/controlcirclewithmouseandkey/
├── ControlCircleWithMouseAndKeyTest.java
├── CirclePaneTest.java
└── EventHandlerTest.java
```

### Test Categories

1. **Unit Tests**: Individual component testing
2. **Integration Tests**: Event flow testing
3. **UI Tests**: Visual behavior verification

## Deployment Architecture

### JAR Creation

- **Maven Shade Plugin**: Creates executable JAR
- **Module Path**: Proper module system support
- **Dependencies**: All dependencies included

### Platform Distribution

- **Cross-Platform**: Single JAR works on all platforms
- **Native Libraries**: Automatic platform detection
- **Runtime Requirements**: Java 24+ with JavaFX 21 