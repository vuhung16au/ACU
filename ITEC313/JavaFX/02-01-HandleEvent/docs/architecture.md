# HandleEvent Demo - Architecture and Design

## Overview

This document provides a detailed explanation of the architecture and design patterns used in the HandleEvent JavaFX application.

## Application Architecture

### 1. High-Level Architecture

```
HandleEvent Application
├── Launcher.java (Entry Point)
├── HandleEvent.java (Main Application)
├── UI Components
│   ├── TabPane (Event Categories)
│   ├── Interactive Elements (Shapes, Text Fields)
│   ├── Event Log (TextArea)
│   └── Control Elements (Buttons, Labels)
└── Event Handlers
    ├── Mouse Event Handlers
    ├── Keyboard Event Handlers
    ├── Touch Event Handlers
    ├── Drag & Drop Event Handlers
    ├── Wheel Event Handlers
    └── Focus Event Handlers
```

### 2. Class Responsibilities

#### Launcher Class
**Purpose**: Application entry point

**Responsibilities**:
- Initialize JavaFX application
- Launch the main application class
- Handle command-line arguments

**Design Pattern**: Facade Pattern

```java
public class Launcher {
    public static void main(String[] args) {
        Application.launch(HandleEvent.class, args);
    }
}
```

#### HandleEvent Class
**Purpose**: Main application controller

**Responsibilities**:
- Create the main window and UI components
- Set up event handlers for all interactive elements
- Manage event logging and counter updates
- Handle application lifecycle

**Design Pattern**: Application Controller Pattern

**Key Methods**:
- `start(Stage primaryStage)`: Initialize the application
- `createUI()`: Build the user interface
- `setupEventHandlers()`: Register all event handlers
- `logEvent(String, String)`: Log events with details
- `updateCounter(String, int)`: Update event counters

## UI Architecture

### 1. Layout Hierarchy

```
VBox (mainContainer)
├── Text (title)
├── TabPane (tabPane)
│   ├── Tab (Mouse Events)
│   │   └── VBox
│   │       ├── Text (description)
│   │       ├── Pane (circlePane)
│   │       │   └── Circle (mouseCircle)
│   │       └── Label (counter)
│   ├── Tab (Keyboard Events)
│   │   └── VBox
│   │       ├── Text (description)
│   │       ├── Pane (rectPane)
│   │       │   └── Rectangle (keyboardRect)
│   │       └── Label (counter)
│   ├── Tab (Touch Events)
│   ├── Tab (Drag & Drop)
│   ├── Tab (Wheel Events)
│   └── Tab (Focus Events)
├── TextArea (eventLog)
└── Button (clearButton)
```

### 2. Component Design

#### Interactive Elements
Each tab contains interactive elements designed to demonstrate specific event types:

**Mouse Events Tab**:
- `Circle mouseCircle`: Draggable circle for mouse interactions
- Event handlers: click, press, release, drag, move, enter, exit

**Keyboard Events Tab**:
- `Rectangle keyboardRect`: Focusable rectangle for keyboard input
- Event handlers: key press, release, typed
- Color changes based on key presses (R=Red, G=Green, B=Blue)

**Touch Events Tab**:
- `Rectangle touchRect`: Touch-sensitive rectangle
- Event handlers: touch press, release, move, stationary
- Visual feedback for touch interactions

**Drag & Drop Tab**:
- `Rectangle dragRect`: Draggable rectangle
- Event handlers: drag detected, drag over, drag enter/exit, drag dropped
- Complete drag and drop implementation

**Wheel Events Tab**:
- `Rectangle wheelRect`: Scroll-sensitive rectangle
- Event handlers: scroll
- Scaling transformation based on wheel movement

**Focus Events Tab**:
- `TextField focusField`: Text field for focus demonstration
- Event handlers: focus gained/lost
- Visual feedback for focus state

### 3. Event Logging System

**Components**:
- `TextArea eventLog`: Displays event information
- `Button clearButton`: Clears the event log
- Counter labels: Track event counts for each category

**Features**:
- Timestamp generation for each event
- Detailed event information extraction
- Auto-scrolling to show latest events
- Clear functionality to reset logging

## Event Handling Architecture

### 1. Event Handler Registration Pattern

```java
// Mouse event handlers
mouseCircle.setOnMouseClicked(e -> {
    mouseEventCount++;
    logEvent("Mouse Click", String.format("Position: (%.1f, %.1f), Button: %s", 
        e.getX(), e.getY(), e.getButton()));
    updateCounter("mouseCounter", mouseEventCount);
});
```

**Benefits**:
- Lambda expressions for concise code
- Consistent event handling pattern
- Easy to understand and maintain
- Real-time event processing

### 2. Event Information Extraction

**Common Patterns**:
```java
// Coordinate extraction
String coords = String.format("Position: (%.1f, %.1f)", e.getX(), e.getY());

// Key information
String keyInfo = String.format("Key: %s, Code: %s", e.getText(), e.getCode());

// Touch information
String touchInfo = String.format("Touch Points: %d", e.getTouchCount());

// Drag information
String dragInfo = String.format("Transfer Mode: %s", e.getTransferMode());
```

### 3. Visual Feedback System

**Implementation Strategy**:
- Color changes for state transitions
- Shape transformations for interactions
- Focus indicators for text fields
- Real-time visual updates

**Examples**:
```java
// Mouse enter/exit color changes
mouseCircle.setOnMouseEntered(e -> mouseCircle.setFill(Color.LIGHTGREEN));
mouseCircle.setOnMouseExited(e -> mouseCircle.setFill(Color.LIGHTBLUE));

// Keyboard color changes
if (e.getCode() == KeyCode.R) keyboardRect.setFill(Color.RED);
else if (e.getCode() == KeyCode.G) keyboardRect.setFill(Color.GREEN);

// Focus visual feedback
focusField.focusedProperty().addListener((obs, oldVal, newVal) -> {
    if (newVal) {
        focusField.setStyle("-fx-background-color: lightgreen;");
    } else {
        focusField.setStyle("-fx-background-color: white;");
    }
});
```

## Data Flow Architecture

### 1. Event Processing Flow

```
User Interaction
    ↓
JavaFX Event Generation
    ↓
Event Handler Execution
    ↓
Event Information Extraction
    ↓
Event Logging
    ↓
Counter Update
    ↓
Visual Feedback
    ↓
UI Update
```

### 2. Event Counter Management

**Counter System**:
- Integer counters for each event category
- Real-time counter updates
- Label-based display system
- Reset functionality

**Implementation**:
```java
private int mouseEventCount = 0;
private int keyboardEventCount = 0;
// ... other counters

private void updateCounter(String counterId, int count) {
    // Find and update counter labels
}
```

### 3. Event Logging System

**Logging Features**:
- Timestamp generation
- Event type identification
- Detailed event information
- Auto-scrolling behavior
- Clear functionality

**Implementation**:
```java
private void logEvent(String eventType, String details) {
    String timestamp = java.time.LocalTime.now().toString();
    String logEntry = String.format("[%s] %s: %s%n", timestamp, eventType, details);
    eventLog.appendText(logEntry);
    eventLog.setScrollTop(Double.MAX_VALUE);
}
```

## Cross-Platform Architecture

### 1. Platform Detection

**Maven Configuration**:
- Platform-agnostic JavaFX dependencies
- Automatic platform detection
- Cross-platform build support

**Runtime Considerations**:
- Touch event availability detection
- Platform-specific event behavior
- Graceful degradation for unavailable features

### 2. Build System

**Maven Configuration**:
```xml
<plugin>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-maven-plugin</artifactId>
    <configuration>
        <mainClass>com.acu.javafx.handleeventdemo.Launcher</mainClass>
        <options>
            <option>--add-modules</option>
            <option>javafx.controls,javafx.fxml</option>
        </options>
    </configuration>
</plugin>
```

**Execution Scripts**:
- `run.sh`: Unix/Linux/macOS execution
- `run.bat`: Windows batch execution
- `run_direct.sh`: Direct Java execution

## Error Handling Architecture

### 1. Graceful Degradation

**Touch Event Handling**:
- Check for touch event availability
- Provide fallback for non-touch devices
- Clear indication of touch support

**Platform-Specific Features**:
- Handle platform differences in event behavior
- Provide appropriate fallbacks
- Maintain functionality across platforms

### 2. Event Handler Safety

**Best Practices**:
- Null checks for event objects
- Exception handling in event handlers
- Safe event information extraction
- Proper event consumption when needed

**Implementation**:
```java
node.setOnMouseClicked(e -> {
    try {
        // Event handling code
        logEvent("Mouse Click", details);
    } catch (Exception ex) {
        logEvent("Error", "Failed to process mouse click: " + ex.getMessage());
    }
});
```

## Performance Architecture

### 1. Event Handler Efficiency

**Optimization Strategies**:
- Lightweight event handlers
- Minimal processing in event handlers
- Efficient event information extraction
- Proper event consumption

### 2. UI Responsiveness

**Responsive Design**:
- Non-blocking event handlers
- Immediate visual feedback
- Smooth animations and transitions
- Efficient UI updates

### 3. Memory Management

**Resource Management**:
- Proper cleanup of event handlers
- Avoid memory leaks in long-running applications
- Efficient object creation and disposal
- Minimal memory footprint

## Testing Architecture

### 1. Event Handler Testing

**Testable Components**:
- Event handler registration
- Event information extraction
- Counter management
- Logging functionality

### 2. Integration Testing

**Test Scenarios**:
- Application startup and shutdown
- Tab navigation and switching
- Event logging and clearing
- Counter updates and resets

### 3. Cross-Platform Testing

**Platform Testing**:
- Different operating systems
- Various JavaFX versions
- Touch device compatibility
- Keyboard layout variations

## Deployment Architecture

### 1. Build Artifacts

**Generated Files**:
- `target/javafx-handleevent-demo-1.0.0.jar`: Executable JAR
- `target/classes/`: Compiled classes
- Platform-specific dependencies

### 2. Runtime Requirements

**Dependencies**:
- Java 24 runtime
- JavaFX 21 runtime
- Platform-specific native libraries

### 3. Distribution

**Packaging Options**:
- JAR file with dependencies
- Platform-specific installers
- Docker containerization
- Native application packaging 