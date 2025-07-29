# JavaFX Text, Font & Color Demo - Concepts

## Overview

This project demonstrates the core JavaFX classes for text rendering, font management, and color manipulation. The application showcases various techniques for creating rich text displays with different fonts, colors, and styling effects.

## Key Concepts

### 1. Text Class (`javafx.scene.text.Text`)

The `Text` class is the fundamental building block for displaying text in JavaFX applications.

**Key Features:**
- Position text at specific coordinates
- Apply fonts and styling
- Set text color and effects
- Support for underline and strikethrough

**Example Usage:**
```java
Text text = new Text(20, 30, "Hello World");
text.setFont(Font.font("Arial", FontWeight.BOLD, 16));
text.setFill(Color.BLUE);
text.setUnderline(true);
```

### 2. Font Class (`javafx.scene.text.Font`)

The `Font` class provides comprehensive font management capabilities.

**Key Features:**
- Font family selection (Arial, Times New Roman, Courier, etc.)
- Font weight control (NORMAL, BOLD, LIGHT, etc.)
- Font posture (REGULAR, ITALIC)
- Font size specification

**Example Usage:**
```java
Font font = Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20);
label.setFont(font);
```

### 3. Color Class (`javafx.scene.paint.Color`)

The `Color` class handles color representation and manipulation.

**Key Features:**
- Predefined colors (RED, BLUE, GREEN, etc.)
- RGB color creation
- Transparency/opacity control
- Color manipulation methods

**Example Usage:**
```java
// Predefined color
Color red = Color.RED;

// Custom RGB color
Color custom = new Color(0.2, 0.6, 0.8, 1.0); // R, G, B, Alpha

// With transparency
Color transparent = new Color(1.0, 0.0, 0.0, 0.5); // 50% transparent red
```

## Application Structure

### Main Application (`TextFontColorDemo`)

The main application provides a tabbed interface with four demonstrations:

1. **Font Demo**: Shows basic font styling with a circle background
2. **Show Text Demo**: Demonstrates various text effects and positioning
3. **Color Demo**: Displays predefined colors and transparency effects
4. **Combined Demo**: Advanced text effects combining multiple techniques

### Individual Demo Classes

#### FontDemo
- Based on the original FontDemo example
- Demonstrates font application to labels
- Shows circle background with styled text overlay

#### ShowText
- Based on the original ShowText example
- Shows text positioning and multiple text elements
- Demonstrates underline and strikethrough effects

## Technical Implementation

### Module System
The application uses Java's module system for proper encapsulation:
```java
module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    exports com.example;
}
```

### Cross-Platform Support
- Maven profiles for different platforms (macOS, Windows, Linux)
- Platform-specific JavaFX dependencies
- Automatic architecture detection

### Build System
- Maven-based build configuration
- JavaFX Maven plugin for execution
- Cross-platform dependency management

## Best Practices

### Font Usage
- Use system fonts for consistency
- Consider font availability across platforms
- Provide fallback fonts for better compatibility

### Color Management
- Use predefined colors for consistency
- Implement proper contrast ratios for accessibility
- Consider color blindness in design choices

### Text Positioning
- Use proper coordinate systems
- Consider text bounds for layout
- Implement responsive text sizing

## Performance Considerations

- Font loading can impact startup time
- Complex text effects may affect rendering performance
- Use appropriate text caching for repeated elements
- Consider hardware acceleration for smooth rendering

## Accessibility

- Ensure sufficient color contrast
- Provide alternative text for decorative elements
- Use semantic markup where possible
- Support screen reader compatibility 