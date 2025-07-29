# ControlCircleWithMouseAndKey - Concepts and Design Decisions

## Overview

This JavaFX application demonstrates interactive circle control using multiple input methods: buttons, mouse clicks, and keyboard events. The application showcases fundamental JavaFX concepts including event handling, layout management, and user interaction.

## Key Concepts

### 1. Event-Driven Programming

The application follows an event-driven programming model where user actions (events) trigger specific responses:

- **Button Events**: Clicking "Enlarge" or "Shrink" buttons
- **Mouse Events**: Left-click to enlarge, right-click to shrink
- **Keyboard Events**: UP arrow to enlarge, DOWN arrow to shrink

### 2. Event Handler Implementation

Three different approaches to event handling are demonstrated:

#### Lambda Expressions
```java
btEnlarge.setOnAction(e -> circlePane.enlarge());
```

#### Anonymous Event Handlers
```java
circlePane.setOnMouseClicked(e -> {
    if (e.getButton() == MouseButton.PRIMARY) {
        circlePane.enlarge();
    }
    else if (e.getButton() == MouseButton.SECONDARY) {
        circlePane.shrink();
    }
});
```

### 3. Layout Management

The application uses `BorderPane` for main layout:
- **Center**: CirclePane containing the interactive circle
- **Bottom**: HBox containing control buttons

### 4. Custom Pane Implementation

`CirclePane` extends `Pane` to create a custom component that:
- Manages a circle with size constraints
- Provides enlarge/shrink functionality
- Handles resizing and centering
- Maintains proper aspect ratios

## Design Decisions

### 1. Separation of Concerns

- **ControlCircleWithMouseAndKey**: Main application logic and UI setup
- **CirclePane**: Circle management and rendering
- **Event Handlers**: Isolated event processing logic

### 2. Size Constraints

The circle has defined limits:
- **MIN_RADIUS**: 5 pixels (prevents circle from disappearing)
- **MAX_RADIUS**: 50 pixels (prevents excessive growth)
- **RADIUS_CHANGE**: 2 pixels per action (smooth scaling)

### 3. Focus Management

The circle pane is made focusable to enable keyboard events:
```java
circlePane.setFocusTraversable(true);
```

### 4. Cross-Platform Compatibility

- Uses JavaFX 21 for modern features
- Implements proper module system with `module-info.java`
- Cross-platform build configuration with Maven

## Event Flow

1. **User Action**: Button click, mouse click, or key press
2. **Event Creation**: JavaFX creates appropriate event object
3. **Event Handler**: Lambda expression or anonymous handler processes event
4. **Circle Modification**: `enlarge()` or `shrink()` method called
5. **Visual Update**: Circle redrawn with new size
6. **Feedback**: User sees immediate visual response

## Learning Objectives

This application demonstrates:

- **Event Handling**: Multiple ways to handle user input
- **Layout Management**: Using BorderPane and HBox
- **Custom Components**: Creating reusable UI components
- **Input Validation**: Constraining circle size within bounds
- **Responsive Design**: Immediate visual feedback to user actions
- **Code Organization**: Clean separation of UI and logic

## Extensibility

The design allows for easy extension:
- Add more keyboard shortcuts
- Implement different mouse button behaviors
- Add animation effects
- Include additional controls (color, position, etc.)
- Support for multiple circles 