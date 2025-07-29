# JavaFX PathTransition Demo

A JavaFX application demonstrating PathTransition animations with interactive controls.

## Overview

This application showcases JavaFX's `PathTransition` animation capabilities by displaying a rectangle moving along a circular path. Users can control the animation using play, pause, stop, and reverse buttons.

**Live Example**: [PathTransition Demo](https://liveexample.pearsoncmg.com/html/PathTransitionDemo.html)

## Features

- **Smooth Animation**: Rectangle moves along a circular path with smooth interpolation
- **Interactive Controls**: Play, pause, stop, and reverse buttons
- **Visual Feedback**: Dashed path visualization and hover effects
- **Cross-Platform**: Runs on macOS, Windows, and Linux
- **Modern UI**: Clean, responsive interface with styled buttons

## Screenshots

The application displays:
- A circular dashed path (blue)
- A red rectangle moving along the path
- Control buttons with hover effects
- Clean, modern interface design

## Technical Specifications

### Development Environment

- **Java Version**: OpenJDK 24
- **JavaFX Version**: 21
- **Maven Version**: 3.9.x or later
- **Target Platform**: Cross-platform (macOS, Windows, Linux)

### Supported Platforms

- **macOS**: Intel (x86_64) and Apple Silicon (ARM64)
- **Windows**: x86_64 and ARM64
- **Linux**: x86_64 and ARM64

## Quick Start

### Prerequisites

1. **Java 24**: Install OpenJDK 24 or later
2. **Maven**: Install Maven 3.9.x or later
3. **JavaFX**: Included via Maven dependencies

### Running the Application

#### Option 1: Using Maven (Recommended)

**macOS/Linux:**
```bash
chmod +x run.sh
./run.sh
```

**Windows:**
```cmd
run.bat
```

#### Option 2: Direct Maven Commands

```bash
# Clean and compile
mvn clean compile

# Run the application
mvn javafx:run
```

#### Option 3: Direct Java Execution

```bash
chmod +x run_direct.sh
./run_direct.sh
```

### Building the Project

```bash
# Clean and compile
mvn clean compile

# Create executable JAR
mvn clean package

# Run the JAR file
java -jar target/pathtransitiondemo-1.0.0.jar
```

## Project Structure

```
02-10-PathTransitionDemo/
├── src/
│   └── main/
│       ├── java/
│       │   ├── com/acu/javafx/pathtransitiondemo/
│       │   │   └── PathTransitionDemo.java
│       │   └── module-info.java
│       └── resources/
├── docs/
│   ├── architecture.md
│   └── concepts.md
├── pom.xml
├── run.sh
├── run.bat
├── run_direct.sh
├── .gitignore
└── README.md
```

## Key Components

### Main Application Class

**`PathTransitionDemo.java`**: The main JavaFX application class that:
- Sets up the UI layout
- Creates the animation area
- Configures the PathTransition
- Handles user interactions

### Animation Configuration

```java
pathTransition = new PathTransition();
pathTransition.setDuration(Duration.seconds(4));
pathTransition.setPath(path);
pathTransition.setNode(rectangle);
pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
pathTransition.setCycleCount(Timeline.INDEFINITE);
pathTransition.setAutoReverse(false);
```

### UI Components

- **Animation Area**: White container with bordered path
- **Path**: Dashed blue circle (100px radius)
- **Moving Object**: Red rectangle (20x20px)
- **Controls**: Styled buttons with hover effects

## Animation Controls

| Button | Action | Description |
|--------|--------|-------------|
| **Play** | Start/Pause | Toggles animation playback |
| **Pause** | Pause | Pauses the animation |
| **Stop** | Stop | Stops and resets animation |
| **Reverse** | Toggle Reverse | Enables/disables auto-reverse |

## Build Configuration

### Maven Configuration

The `pom.xml` includes:
- JavaFX dependencies with platform-specific classifiers
- Maven compiler plugin for Java 24
- JavaFX Maven plugin for execution
- Maven Shade plugin for JAR creation

### Cross-Platform Support

Platform-specific JavaFX modules are automatically included:
- `mac-aarch64` for Apple Silicon
- `mac` for Intel macOS
- `win` for Windows
- `linux` for Linux

## Development

### Code Quality

- **JavaDoc**: Comprehensive documentation
- **Clean Code**: Well-structured, readable code
- **Error Handling**: Graceful error handling
- **Resource Management**: Proper cleanup in `stop()` method

### Design Patterns

- **MVC Pattern**: Separated concerns (Model: Animation, View: UI, Controller: Events)
- **Builder Pattern**: UI component creation
- **Strategy Pattern**: Animation behavior encapsulation
- **Observer Pattern**: Event handling

## Testing

### Unit Testing

```bash
# Run unit tests
mvn test
```

### Manual Testing

1. **Animation Controls**: Test all button interactions
2. **Visual Feedback**: Verify hover effects
3. **Performance**: Check smooth animation at 60 FPS
4. **Cross-Platform**: Test on different operating systems

## Troubleshooting

### Common Issues

**JavaFX Modules Not Found**
```bash
# Ensure JavaFX is properly installed
java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml
```

**Maven Build Failures**
```bash
# Clean and rebuild
mvn clean compile
```

**Platform-Specific Issues**
- Check JavaFX platform classifiers in `pom.xml`
- Verify Java version compatibility
- Ensure proper module path configuration

### Debug Mode

```bash
# Run with debug output
mvn javafx:run -Djavafx.debug=true
```

## Performance Considerations

- **Hardware Acceleration**: Enabled by default
- **Smooth Animation**: 60 FPS target
- **Memory Management**: Proper cleanup on application stop
- **UI Responsiveness**: Non-blocking animation updates

## Security

- **Module Isolation**: Limited module exports
- **Sandboxed Execution**: No file system or network access
- **Self-Contained**: No external dependencies at runtime

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is part of the ACU JavaFX learning materials.

## Documentation

- **[Concepts](docs/concepts.md)**: Main concepts and design decisions
- **[Architecture](docs/architecture.md)**: Detailed architecture and design patterns

## Related Projects

- [JavaFX Hello World](../01-01-JavaFX-HelloWorld/)
- [JavaFX Button Demo](../01-02-JavaFX.Button/)
- [JavaFX Animation Demos](../02-11-FlagRisingAnimation/)

## Support

For issues and questions:
1. Check the troubleshooting section
2. Review the documentation
3. Test on different platforms
4. Verify Java and Maven versions