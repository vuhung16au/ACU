# Advanced JavaFX and FXML with CSS Styling

This project demonstrates advanced JavaFX features including CSS styling capabilities for UI components.

## Project Overview

The `StyleSheetDemo` application showcases how to apply CSS styles to JavaFX UI nodes using:
- **Style Classes** (`.plaincircle`, `.circleborder`, `.border`)
- **Style IDs** (`#redcircle`, `#greencircle`)
- **Multiple style classes** on a single node

## Features

### Enhanced UI Design
- **Larger Window**: 600x400 pixel application window for better visibility
- **Split Layout**: Left side shows CSS styling demonstration, right side displays instructions
- **Scrollable Instructions**: Comprehensive guidelines and summaries directly in the application
- **Professional Layout**: Clean, organized interface with proper spacing and typography

### CSS Styling
- **Plain Circle Style**: White fill with black stroke
- **Circle Border Style**: Dashed border effect with custom stroke width
- **Border Style**: Black border for container panes
- **Color-specific Styles**: Red and green circles using ID selectors

### UI Components
- Two separate panes with different circle configurations
- Horizontal layout using HBox with proper spacing
- Multiple circles with various styling combinations
- Instructional text with detailed explanations

## Project Structure

```
31-01-JavaFX-FXML/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── acu/
│       │           └── javafx/
│       │               └── advanced/
│       │                   └── StyleSheetDemo.java
│       └── resources/
│           └── mystyle.css
├── docs/
│   ├── architecture.md
│   └── concepts.md
├── pom.xml
└── README.md
```

## Documentation

For detailed information about this project, please refer to:

- **[docs/architecture.md](docs/architecture.md)** - Technical architecture and design patterns
- **[docs/concepts.md](docs/concepts.md)** - Core concepts and implementation details

## CSS Styles Explained

### Style Classes
- `.plaincircle`: Basic circle style with white fill and black stroke
- `.circleborder`: Creates a dashed border effect
- `.border`: Adds a black border to container elements

### Style IDs
- `#redcircle`: Red fill and stroke for specific circles
- `#greencircle`: Green fill and stroke for specific circles

## Running the Application

### Prerequisites
- Java 24 or higher
- Maven 3.6 or higher
- JavaFX 21

### Build and Run

1. **Compile and run with Maven:**
   ```bash
   mvn clean javafx:run
   ```

2. **Build executable JAR:**
   ```bash
   mvn clean package
   ```

3. **Run the JAR file:**
   ```bash
   java -jar target/advanced-javafx-fxml-1.0.0.jar
   ```

## Expected Output

The application displays a 600x400 window with:

### Left Side - CSS Styling Demonstration
- **Title**: "CSS Styling Demonstration"
- **Top Pane**: Three circles (two plain white circles, one red circle) in a bordered container
- **Bottom Pane**: One green circle with dashed border effect in a bordered container

### Right Side - Instructions & Guidelines
- **Title**: "Instructions & Guidelines"
- **Scrollable Content** including:
  - CSS Integration details
  - Style Classes explanation
  - Style IDs explanation
  - CSS Properties reference
  - Visual Output summary

## Key Learning Points

1. **CSS Integration**: How to load and apply CSS stylesheets in JavaFX
2. **Style Classes**: Using `.getStyleClass().add()` for reusable styles
3. **Style IDs**: Using `.setId()` for unique element styling
4. **Multiple Styles**: Combining multiple style classes on a single node
5. **Resource Loading**: Proper placement of CSS files in the resources directory
6. **UI Design**: Creating professional layouts with proper spacing and typography
7. **User Experience**: Providing instructional content directly in the application

## Technical Details

- **Java Version**: 24
- **JavaFX Version**: 21
- **Maven Plugin**: javafx-maven-plugin 0.0.8
- **Packaging**: Executable JAR with Maven Shade Plugin
- **Window Size**: 600x400 pixels
- **Layout**: HBox with VBox containers for organized content

## CSS Properties Used

- `-fx-fill`: Sets the fill color of shapes
- `-fx-stroke`: Sets the stroke color of shapes
- `-fx-stroke-width`: Sets the stroke width
- `-fx-stroke-dash-array`: Creates dashed line patterns
- `-fx-border-color`: Sets border color for containers
- `-fx-border-width`: Sets border width for containers

## UI Improvements

### Enhanced Layout
- **Larger Window**: Increased from 300x250 to 600x400 for better visibility
- **Split Design**: Left side for demonstration, right side for instructions
- **Proper Spacing**: 20px padding and 15px spacing between elements
- **Typography**: Different font sizes and weights for hierarchy

### Instructional Content
- **Comprehensive Guidelines**: Step-by-step explanations of CSS concepts
- **Visual Summary**: Clear description of what users should see
- **Scrollable Interface**: All content accessible without overwhelming the UI
- **Professional Presentation**: Clean, readable text formatting

This project serves as a foundation for understanding advanced JavaFX styling techniques and can be extended with more complex CSS rules and UI components.
