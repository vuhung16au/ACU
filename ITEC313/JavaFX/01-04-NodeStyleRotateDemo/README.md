# JavaFX Node Style and Rotate Demo

A comprehensive JavaFX application demonstrating common node properties and methods, specifically focusing on CSS styling and node rotation. This project showcases both basic and advanced implementations with cross-platform compatibility.

## Features

### Core Demonstrations
- **CSS Styling**: Dynamic styling of JavaFX nodes using CSS properties
- **Node Rotation**: Interactive rotation of UI components
- **Multiple Implementations**: Both simple and enhanced demo versions
- **Cross-Platform Support**: Builds and runs on macOS, Windows, and Linux

### Enhanced Demo Features
- Interactive rotation controls with slider
- Styled buttons with hover effects
- Styled shapes (Rectangle and Circle) with gradients
- Real-time rotation updates
- Modern, responsive UI design

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


## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/example/
│   │       ├── Launcher.java                    # Application entry point
│   │       ├── NodeStyleRotateDemo.java         # Enhanced demo application
│   │       └── SimpleNodeStyleRotateDemo.java   # Basic implementation
│   └── resources/
├── test/
│   └── java/
pom.xml                                          # Maven build configuration
.gitignore                                       # Git ignore rules
README.md                                        # Project documentation
run.sh                                           # Unix/Linux/macOS execution script
run.bat                                          # Windows execution script
run_direct.sh                                    # Direct Java execution script
```

## Technical Specifications

### Development Environment

- **Target Platform**: macOS Silicon (ARM64) - primary development environment
- **Java Version**: OpenJDK 24
- **Maven Version**: 3.9.x or later
- **JavaFX Version**: 21

### Cross-Platform Compatibility

The project builds and runs on:

- **macOS**: Intel (x86_64) and Apple Silicon (ARM64)
- **Windows**: x86_64 and ARM64
- **Linux**: x86_64 and ARM64

## Prerequisites

### Required Software

1. **Java Development Kit (JDK) 24 or later**
   - Download from [OpenJDK](https://openjdk.org/) or [Oracle](https://www.oracle.com/java/technologies/downloads/)
   - Ensure `JAVA_HOME` environment variable is set

2. **Apache Maven 3.9.x or later**
   - Download from [Maven Official Site](https://maven.apache.org/download.cgi)
   - Ensure `mvn` command is available in PATH

3. **Git** (optional, for cloning)
   - Download from [Git Official Site](https://git-scm.com/downloads)

### Platform-Specific Setup

#### macOS

```bash
# Using Homebrew
brew install openjdk@24 maven

# Set JAVA_HOME (add to ~/.zshrc or ~/.bash_profile)
export JAVA_HOME=$(/usr/libexec/java_home -v 24)
```

#### Windows

1. Download and install OpenJDK 24
2. Download and install Maven
3. Set environment variables:
   - `JAVA_HOME`: Path to JDK installation
   - `MAVEN_HOME`: Path to Maven installation
   - Add `%JAVA_HOME%\bin` and `%MAVEN_HOME%\bin` to PATH

#### Linux

```bash
# Ubuntu/Debian
sudo apt update
sudo apt install openjdk-24-jdk maven

# Fedora/RHEL
sudo dnf install java-24-openjdk-devel maven

# Set JAVA_HOME (add to ~/.bashrc)
export JAVA_HOME=/usr/lib/jvm/java-24-openjdk
```

## Building and Running

### Quick Start (Recommended)

#### macOS/Linux

```bash
# Make script executable
chmod +x run.sh

# Build and run
./run.sh
```

#### Windows

```batch
# Run the batch script
run.bat
```

### Manual Build Process

#### 1. Clean and Compile

```bash
mvn clean compile
```

#### 2. Run with Maven

```bash
# Run enhanced demo (default)
mvn javafx:run

# Run simple demo
mvn javafx:run -Djavafx.mainClass=com.example.SimpleNodeStyleRotateDemo
```

#### 3. Direct Java Execution (Advanced)

```bash
# Make script executable (Unix/Linux/macOS only)
chmod +x run_direct.sh

# Run directly
./run_direct.sh
```

### Creating Executable JAR

```bash
# Package the application
mvn clean package

# Run the JAR (requires JavaFX in module path)
java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -jar target/node-style-rotate-demo-1.0.0.jar
```

## Application Demonstrations

### Simple Demo (`SimpleNodeStyleRotateDemo`)

- Basic button with blue border styling
- StackPane with red border and light gray background
- 45-degree rotation applied to the entire pane
- Demonstrates the original sample code implementation

### Enhanced Demo (`NodeStyleRotateDemo`)

- **Styled Button Section**:
  - Gradient background with rounded corners
  - Hover effects with scaling
  - Click animation with rotation
  
- **Styled Shapes Section**:
  - Rectangle with gradient fill and rounded corners
  - Circle with radial gradient
  - Custom stroke styling
  
- **Interactive Rotation Section**:
  - Slider control for dynamic rotation (0-360 degrees)
  - Real-time rotation updates
  - Multiple elements rotating at different rates

## Key JavaFX Concepts Demonstrated

### 1. CSS Styling

```java
// Basic styling
node.setStyle("-fx-border-color: blue;");

// Advanced styling with gradients
node.setStyle(
    "-fx-background-color: linear-gradient(#ff5400, #be1d00);" +
    "-fx-background-radius: 30;" +
    "-fx-border-color: #333;" +
    "-fx-border-width: 2;"
);
```

### 2. Node Rotation

```java
// Basic rotation
node.setRotate(45); // Rotate 45 degrees

// Dynamic rotation
slider.valueProperty().addListener((obs, old, newVal) -> {
    node.setRotate(newVal.doubleValue());
});
```

### 3. Event Handling

```java
// Mouse events
button.setOnMouseEntered(e -> /* hover effect */);
button.setOnMouseExited(e -> /* restore normal */);
button.setOnAction(e -> /* click action */);
```

### 4. Property Binding

```java
// Bind slider value to rotation
rotationSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
    rotatingPane.setRotate(newValue.doubleValue());
});
```

## Troubleshooting

### Common Issues

#### 1. "JavaFX runtime components are missing"

**Solution**: Ensure JavaFX dependencies are properly configured in `pom.xml` and platform classifier is correct.

#### 2. Build fails with "package does not exist"

**Solution**: 

```bash
mvn clean
mvn compile
```

#### 3. Application doesn't start on macOS

**Solution**: Check Java version and ensure you're using the correct architecture:

```bash
java --version
uname -m  # Check architecture (arm64 or x86_64)
```

#### 4. Maven dependencies not downloading

**Solution**:

```bash
# Clear Maven cache and re-download
rm -rf ~/.m2/repository/org/openjfx
mvn clean compile
```

### Platform-Specific Issues

#### macOS

- **Issue**: Application doesn't launch
- **Solution**: Ensure you have the correct JavaFX classifier (mac or mac-aarch64)

#### Windows

- **Issue**: Script execution blocked
- **Solution**: Enable script execution in PowerShell:

  ```powershell
  Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
  ```

#### Linux

- **Issue**: Missing graphics libraries
- **Solution**: Install required graphics libraries:

  ```bash
  sudo apt install libgtk-3-dev libxss1 libasound2-dev
  ```

## Development Notes

### Maven Profiles

The project uses Maven profiles for automatic platform detection:

- `mac-x86_64`: macOS Intel
- `mac-aarch64`: macOS Apple Silicon
- `win-x86_64`: Windows x64
- `win-aarch64`: Windows ARM64
- `linux-x86_64`: Linux x64
- `linux-aarch64`: Linux ARM64

### Architecture Detection

Platform detection is automatic based on:

- Operating system family (`os.family`)
- System architecture (`os.arch`)

### Customization

To modify the application:

1. Edit source files in `src/main/java/com/example/`
2. Rebuild with `mvn clean compile`
3. Run with `mvn javafx:run`

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test on multiple platforms
5. Submit a pull request

## License

This project is developed for educational purposes as part of ITEC313 coursework.

## Support

For technical support or questions:

1. Check the troubleshooting section above
2. Ensure all prerequisites are installed correctly
3. Verify your Java and Maven versions
4. Check platform-specific requirements

## Screenshots

![JavaFX Node Style Rotate Demo](images/JavaFX-NodeStyle-Rotate-Demo.png)

---

**Note**: This application demonstrates JavaFX node styling and rotation capabilities across multiple platforms. The enhanced version provides interactive controls for better understanding of the concepts.