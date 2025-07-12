Create a JavaFX application that demonstrates the use 

Common Properties and Methods for Nodes 
style: set a JavaFX CSS style
rotate: Rotate a node

Refer to sample code below for a basic implementation.

```java

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

public class NodeStyleRotateDemo extends Application {
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    // Create a scene and place a button in the scene
    StackPane pane = new StackPane();
    Button btOK = new Button("OK");
    btOK.setStyle("-fx-border-color: blue;");
    pane.getChildren().add(btOK);    
    
    pane.setRotate(45); // Rotate pane 45 degrees
    pane.setStyle(
      "-fx-border-color: red; -fx-background-color: lightgray;");
    
    Scene scene = new Scene(pane, 200, 250);
    primaryStage.setTitle("NodeStyleRotateDemo"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
  }
  
  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
```
ref. https://liveexample.pearsoncmg.com/html/NodeStyleRotateDemo.html


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
- **`run_direct.sh`**: Direct Java execution without Maven (optional)

### Project Structure

```text
src/
├── main/
│   ├── java/
│   │   └── com/example/
│   │       ├── Launcher.java          # Application entry point
│   │       ├── ShapesDemo.java        # Main application class
│   │       └── SimpleShapesDemo.java  # Alternative simple version
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

- Efficient rendering
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
4. `docs/concepts.md`: Main concepts and design decisions
5. `docs/architecture.md`: Detailed architecture and design patterns used

## Additional Notes

### Platform-Specific Considerations

- Handle different JavaFX runtime distributions across platforms
- Ensure proper native library loading for all target architectures
- Test build and execution on different operating systems

## Success Criteria

- Application builds successfully on all target platforms
- All specified shapes and controls are properly implemented and functional
- Cross-platform compatibility is verified
- Code is well-structured and documented
- Build scripts work correctly on respective platforms