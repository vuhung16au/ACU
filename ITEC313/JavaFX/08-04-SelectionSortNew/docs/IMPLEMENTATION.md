# JavaFX Implementation Guide

## Project Architecture

The Selection Sort Visualizer is built using JavaFX with a clean, modular architecture that separates concerns and provides a maintainable codebase.

### Class Structure

#### Launcher.java
- Entry point for the application
- Handles JavaFX application initialization
- Provides proper separation between main method and JavaFX Application class

#### SelectionSortApp.java
- Main application class extending JavaFX Application
- Contains the complete visualization logic
- Manages UI components and animation timing
- Implements the selection sort algorithm with visual feedback

#### BarElement (Inner Class)
- Represents individual array elements as visual components
- Encapsulates both the visual representation (Rectangle) and data (value)
- Handles position updates and color changes

## JavaFX Components Used

### Layout Components
- **BorderPane**: Main application layout container
- **VBox**: Vertical arrangement of UI controls
- **HBox**: Horizontal arrangement of buttons and controls
- **Pane**: Container for the visualization area

### Control Components
- **Button**: Interactive controls for user actions
- **Slider**: Speed control for animation timing
- **Label**: Status information and titles

### Visual Components
- **Rectangle**: Represents array elements as bars
- **Text**: Displays values on top of bars

## Animation System

### PauseTransition
The application uses JavaFX's `PauseTransition` class to create timed delays between algorithm steps:

```java
PauseTransition pause = new PauseTransition(Duration.millis(animationSpeed));
pause.setOnFinished(e -> {
    // Next animation step
});
pause.play();
```

### Color Animation
Elements change colors to represent different states:
- **CURRENT_COLOR** (Dark Blue): Current element being processed
- **COMPARING_COLOR** (Red): Element being compared
- **MINIMUM_COLOR** (Orange): Current minimum element found
- **SORTED_COLOR** (Light Green): Elements in their final sorted position
- **DEFAULT_COLOR** (Sky Blue): Unsorted elements

## Event Handling

### Button Events
- **Generate New Array**: Triggers `generateNewArray()` method
- **Start Selection Sort**: Triggers `startSelectionSort()` method

### Slider Events
- **Speed Control**: Updates animation speed in real-time using property listeners

## State Management

### Animation State
```java
private boolean isAnimating = false;
```
Prevents user interaction during sorting process and manages UI state.

### UI State Management
- Buttons are disabled during animation to prevent conflicts
- Status label provides real-time feedback about current operations
- Visual elements maintain consistent state throughout the animation

## Memory Management

### Efficient Resource Usage
- BarElement objects are reused when possible
- Visual nodes are properly managed within the scene graph
- Animation timers are cleaned up after completion

### Garbage Collection Considerations
- References to animation objects are properly cleared
- Event handlers are designed to avoid memory leaks

## Cross-Platform Compatibility

### Maven Profiles
The `pom.xml` includes platform-specific profiles for:
- **macOS Apple Silicon** (aarch64)
- **macOS Intel** (x86_64)
- **Windows** (all architectures)
- **Linux** (generic)

### JavaFX Module System
Proper module path configuration ensures compatibility across different Java installations:

```bash
java --module-path "$JAVAFX_PATH" \
     --add-modules javafx.controls,javafx.fxml \
     -cp "target/classes" \
     com.acu.javafx.selectionsort.Launcher
```

## Error Handling

### Graceful Degradation
- Animation continues even if individual steps encounter issues
- UI remains responsive through proper thread management
- Error states are handled without crashing the application

### Input Validation
- Array generation handles edge cases
- Animation speed validation ensures reasonable performance
- Button states prevent invalid operations

## Performance Considerations

### Animation Optimization
- Rectangle objects are reused rather than recreated
- Color changes use efficient JavaFX property updates
- Animation timing is optimized for smooth visual experience

### Memory Efficiency
- Constant space complexity maintained (O(1) additional memory)
- Visual elements scaled appropriately for performance
- Garbage collection friendly object lifecycle management

## Styling and CSS

### External Stylesheet
The application uses an external CSS file (`styles.css`) for consistent styling:
- Button appearance and hover effects
- Color scheme management
- Layout spacing and padding
- Font selections and sizing

### Dynamic Styling
Some styles are applied programmatically for:
- Animation color changes
- State-dependent appearances
- Platform-specific adjustments

## Testing Considerations

### Manual Testing
- Test with different array sizes
- Verify animation timing at various speeds
- Check platform compatibility
- Validate button state management

### Edge Cases
- Empty arrays (handled by bounds checking)
- Single-element arrays (trivial case)
- Already sorted arrays (demonstrates algorithm behavior)
- Reverse-sorted arrays (worst-case scenario)

## Deployment Options

### Standalone JAR
```bash
mvn clean package
java -jar target/selectionsort-1.0.0.jar
```

### Platform-Specific Packaging
- Use jpackage for native installers
- Include JavaFX runtime for self-contained applications
- Create platform-specific distribution packages

## Educational Integration

### Learning Objectives
- Visual understanding of selection sort algorithm
- Observation of O(n²) time complexity in action
- Understanding of in-place sorting concepts
- Recognition of algorithm stability characteristics

### Interactive Features
- User-controlled array generation
- Adjustable animation speed for different learning paces
- Clear visual feedback for each algorithm step
- Status messages explaining current operations

This implementation serves as both a functional sorting visualizer and a reference for JavaFX application development best practices.
