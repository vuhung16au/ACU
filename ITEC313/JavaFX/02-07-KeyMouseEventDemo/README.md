# JavaFX Key and Mouse Event Demo

A JavaFX application demonstrating key and mouse event handling with interactive text elements.

## Overview

This project demonstrates JavaFX event handling through two main components:

1. **MouseEventDemo**: A draggable text element that responds to mouse drag events
2. **KeyEventDemo**: A keyboard-controllable text element that can be moved with arrow keys and edited by typing

## Features

### Mouse Event Demo
- **Draggable Text**: Click and drag the text "Programming is fun" to move it around
- **Real-time Position Updates**: Text position updates smoothly as you drag
- **Visual Feedback**: Styled text with clear visual appearance

### Key Event Demo
- **Arrow Key Navigation**: Use arrow keys to move the text around
- **Text Editing**: Type any character to change the text content
- **Focus Management**: Text automatically receives keyboard focus
- **Visual Styling**: Distinct styling to differentiate from mouse demo

## Arrow Key Issue Resolution

The original implementation had an issue where arrow keys would toggle between tabs instead of moving the text in the Key Event Demo. This has been completely resolved by using separate windows for each demo, eliminating any TabPane interference.

## Technical Specifications

### Development Environment
- **Java Version**: OpenJDK 24
- **JavaFX Version**: 21
- **Maven Version**: 3.9.x or later
- **Target Platform**: Cross-platform (macOS, Windows, Linux)

### Cross-Platform Compatibility
- **macOS**: Intel (x86_64) and Apple Silicon (ARM64)
- **Windows**: x86_64 and ARM64
- **Linux**: x86_64 and ARM64

## Project Structure

```
02-07-KeyMouseEventDemo/
├── src/
│   └── main/
│       └── java/
│           ├── module-info.java
│           └── com/acu/javafx/keymouseeventdemo/
│               ├── Launcher.java                    # Main application (separate windows)
│               ├── MouseEventDemo.java              # Mouse event demonstration
│               └── KeyEventDemo.java                # Keyboard event demonstration
├── docs/
│   ├── architecture.md                              # Architecture documentation
│   └── concepts.md                                 # Concepts and design decisions
├── pom.xml                                         # Maven build configuration
├── run.sh                                          # Unix/Linux/macOS run script
├── run.bat                                         # Windows run script
└── README.md                                       # This file
```

## Quick Start

### Prerequisites
- Java 24 or later
- Maven 3.9.x or later

### Running the Application

```bash
# On Unix/Linux/macOS:
./run.sh

# On Windows:
run.bat

# Using Maven directly:
mvn clean javafx:run
```

## Usage Instructions

### Mouse Event Demo
1. Click "Open Mouse Event Demo" button
2. Click and drag the text "Programming is fun" to move it around
3. The text will follow your mouse cursor as you drag

### Key Event Demo
1. Click "Open Key Event Demo" button
2. The text "A" will automatically receive keyboard focus
3. Use arrow keys to move the text:
   - ↑ Down arrow: Move text down
   - ↑ Up arrow: Move text up
   - ← Left arrow: Move text left
   - → Right arrow: Move text right
4. Type any character to change the text content

## Build and Development

### Building the Project
```bash
mvn clean compile
```

### Creating Executable JAR
```bash
mvn clean package
```

### Running Tests
```bash
mvn test
```

## Architecture

### Event-Driven Design
The application uses JavaFX's event system with lambda expressions for efficient event handling:

```java
// Mouse event handling
text.setOnMouseDragged(e -> {
    text.setX(e.getX());
    text.setY(e.getY());
});

// Keyboard event handling
text.setOnKeyPressed(e -> {
    switch (e.getCode()) {
        case DOWN: text.setY(text.getY() + 10); break;
        case UP: text.setY(text.getY() - 10); break;
        // ... more cases
    }
});
```

### Component-Based Architecture
- Each demo is a separate component extending `Pane`
- Modular design allows easy extension and modification
- Clear separation of concerns between different event types

### Separate Windows Design
The application uses separate windows for each demo to completely eliminate any potential conflicts:

1. **Launcher Window**: Provides buttons to open each demo
2. **Mouse Event Window**: Dedicated window for mouse interactions
3. **Key Event Window**: Dedicated window for keyboard interactions

This approach ensures:
- No interference between different event types
- Clean separation of concerns
- Better user experience with focused demos
- No arrow key conflicts

## Cross-Platform Features

### Platform Detection
Maven profiles automatically detect the target platform and configure appropriate JavaFX dependencies.

### Build Scripts
- **run.sh**: Unix/Linux/macOS execution script with error checking
- **run.bat**: Windows batch execution script with error checking

## Performance Considerations

### Event Handling
- Efficient lambda-based event handlers
- Minimal object creation during event processing
- Proper focus management for keyboard input

### Rendering
- Optimized text rendering
- Smooth drag operations
- Responsive UI updates

## Documentation

### Architecture Documentation
See `docs/architecture.md` for detailed architecture information.

### Concepts Documentation
See `docs/concepts.md` for design decisions and concepts.

## Examples

The application is based on examples from:
- [MouseEventDemo](https://liveexample.pearsoncmg.com/html/MouseEventDemo.html)
- [KeyEventDemo](https://liveexample.pearsoncmg.com/html/KeyEventDemo.html)

## Troubleshooting

### Common Issues

#### JavaFX Runtime Not Found
**Solution**: Ensure you have JavaFX dependencies in your classpath. The Maven configuration handles this automatically.

#### Keyboard Events Not Working
**Solution**: Make sure the text element has focus. The application automatically requests focus for the keyboard demo.

#### Build Failures
**Solution**: 
1. Ensure Java 24+ is installed
2. Ensure Maven 3.9+ is installed
3. Run `mvn clean` before building

### Platform-Specific Issues

#### macOS
- Ensure you have the latest JavaFX runtime
- Use the provided run.sh script for best compatibility

#### Windows
- Ensure PATH includes Java and Maven
- Use the provided run.bat script for best compatibility

#### Linux
- Install OpenJDK 24+ and Maven
- Use the provided run.sh script

## Contributing

### Development Guidelines
1. Follow Java coding conventions
2. Add comprehensive JavaDoc comments
3. Test on multiple platforms
4. Update documentation for any changes

### Adding New Demos
1. Create a new class extending `Pane`
2. Implement the demo functionality
3. Add a new tab in `Launcher.java`
4. Update documentation

## License

This project is part of the ACU JavaFX learning materials.

## Version History

- **1.0.0**: Initial release with MouseEventDemo and KeyEventDemo