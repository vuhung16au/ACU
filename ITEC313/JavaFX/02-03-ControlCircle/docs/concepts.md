# ControlCircle - Main Concepts and Design Decisions

## Overview

The ControlCircle application demonstrates fundamental JavaFX concepts including:
- Event handling with lambda expressions
- UI component creation and management
- Layout management with multiple windows
- Shape manipulation and visual feedback

## Key Concepts

### 1. Event Handling with Lambda Expressions

The application uses modern Java lambda expressions for event handling:

```java
btEnlarge.setOnAction(e -> {
    circle.setRadius(circle.getRadius() + 2);
});
```

This approach is more concise and readable than traditional anonymous inner classes.

### 2. Multiple Window Management

The application demonstrates how to create and manage multiple windows:
- Primary window containing the circle
- Secondary window containing control buttons

This separation provides a clean UI design and demonstrates JavaFX's multi-window capabilities.

### 3. Shape Manipulation

The circle's radius is dynamically modified based on user interactions:
- Enlarge button increases radius by 2 pixels
- Shrink button decreases radius by 2 pixels (with minimum size protection)

### 4. Layout Management

The application uses different layout containers:
- `Pane`: For the circle display (allows absolute positioning)
- `HBox`: For button layout (horizontal arrangement)

## Design Decisions

### 1. Separation of Concerns

- Circle display and button controls are in separate windows
- Each UI component has a single responsibility
- Event handlers are focused and minimal

### 2. User Experience

- Buttons are clearly labeled and intuitive
- Visual feedback is immediate
- Minimum size protection prevents the circle from disappearing

### 3. Code Organization

- Clean, well-documented code structure
- Consistent naming conventions
- Proper JavaFX application lifecycle management

## Technical Implementation

### Application Structure

1. **Main Application Class**: `ControlCircle` extends `Application`
2. **UI Components**: Circle, Buttons, Layout containers
3. **Event Handlers**: Lambda expressions for button actions
4. **Window Management**: Multiple Stage instances

### Key Methods

- `start(Stage primaryStage)`: Application entry point
- `setOnAction()`: Event handler registration
- `setRadius()`: Circle property modification

## Learning Objectives

This application teaches:
- Basic JavaFX application structure
- Event-driven programming
- UI component interaction
- Multi-window application design
- Lambda expressions in event handling 