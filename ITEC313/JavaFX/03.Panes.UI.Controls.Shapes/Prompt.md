# JavaFX Panes, UI Controls, and Shapes Demo Application

## Project Overview

Create a comprehensive JavaFX application that demonstrates the fundamental concepts of JavaFX includes the following {features}: 

{features}
- panes, 
- UI controls, 
- shapes. 
{/features}

This project serves as an educational example showcasing how to build cross-platform JavaFX applications with proper build configuration.

## Application Requirements

### Core Features

The application must demonstrate the following JavaFX components:

#### 1. Shapes Display

- **Circle**: Display a circle with customizable properties (radius, fill color, stroke)
- **Rectangle**: Display a rectangle with customizable dimensions and styling
- **Line**: Display lines with different stroke properties and angles
- **General Shape**: Demonstrate additional shape types (polygon, ellipse, etc.)

#### 2. UI Controls

- **Buttons**: Interactive buttons to control shape properties
- **Sliders**: For adjusting shape dimensions and properties
- **Color Pickers**: To change shape colors dynamically
- **Text Fields**: For inputting custom values
- **Labels**: To display information about current settings

#### 3. Layout Panes

- **BorderPane**: Main application layout
- **HBox/VBox**: For organizing controls
- **GridPane**: For structured layout of controls
- **StackPane**: For layering shapes

#### 4. Interactive Features

- Click events to modify shapes
- Real-time property updates
- Shape animation (optional enhancement)
- Responsive layout design

## Technical Specifications

### Development Environment

- **Target Platform**: macOS Silicon (ARM64) - primary development environment
- **Java Version**: OpenJDK 24
- **Maven Version**: 3.9.x or later
- **JavaFX Version**: 21

### Cross-Platform Compatibility

The project must be buildable and runnable on:

- **macOS**: Intel (x86_64) and Apple Silicon (ARM64)
- **Windows**: x86_64 and ARM64
- **Linux**: x86_64 and ARM64

### Build Configuration Requirements

#### Maven Configuration (`pom.xml`)

- Platform detection properties for automatic architecture detection
- JavaFX dependencies with platform-specific classifiers
- Maven compiler plugin configured for Java 24
- JavaFX Maven plugin for running the application
- Cross-platform dependency management

#### Build Scripts

- **`run.sh`**: Unix/Linux/macOS execution script
- **`run.bat`**: Windows batch execution script

### Project Structure

```text
src/
├── main/
│   ├── java/
│   │   └── com/example/
│   │       ├── Launcher.java          # Application entry point
│   │       ├── XYZ.java        # Main application class
│   │       └── XYZ.java  # Alternative simple version
│   └── resources/
│       └── (CSS files, images, etc.)
├── test/
│   └── java/
│       └── (unit tests)
pom.xml                                # Maven build configuration
.gitignore                            # Git ignore rules
README.md                             # Project documentation
```

## Implementation Guidelines

### Code Quality Requirements

- Clean, well-documented code with JavaDoc comments
- Proper separation of concerns
- Error handling for user interactions
- Responsive UI design principles

### UI/UX Design

- Modern, clean interface design
- Intuitive user interactions
- Proper spacing and layout
- Consistent styling throughout the application

### Performance Considerations

- Efficient shape rendering
- Smooth animations (if implemented)
- Proper memory management
- Responsive user interface

## Deliverables

### Code Files

1. **Java Source Files**: Complete JavaFX application implementation
2. **Maven Configuration**: Cross-platform `pom.xml` with platform detection
3. **Build Scripts**: Platform-specific execution scripts
4. **Git Configuration**: Appropriate `.gitignore` file

### Documentation

1. **README.md**: Comprehensive project documentation
2. **Build Instructions**: Step-by-step build and run instructions
3. **Architecture Notes**: Platform compatibility documentation

## Additional Notes

### Platform-Specific Considerations

- Handle different JavaFX runtime distributions across platforms
- Ensure proper native library loading for all target architectures
- Test build and execution on different operating systems

### Future Enhancements (Optional)

- 3D shapes demonstration
- Advanced animations and transitions
- CSS styling examples
- FXML-based UI design
- Custom shape creation tools

## Success Criteria

- Application builds successfully on all target platforms
- All specified shapes and controls are properly implemented and functional
- Cross-platform compatibility is verified
- Code is well-structured and documented
- Build scripts work correctly on respective platforms