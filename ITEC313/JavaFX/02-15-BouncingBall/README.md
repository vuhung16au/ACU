# JavaFX Bouncing Ball Demo

A JavaFX application demonstrating a bouncing ball animation with interactive controls. The ball moves within the boundaries of a window and bounces off the edges, while users can control the animation speed and pause/resume functionality.

## Features

- **Smooth Animation**: 50ms update intervals for fluid ball movement
- **Interactive Controls**: Mouse and keyboard controls for user interaction
- **Boundary Detection**: Intelligent collision detection with window edges
- **Speed Control**: Dynamic animation speed adjustment
- **Cross-Platform**: Runs on macOS, Windows, and Linux

## Controls

- **Mouse Press**: Pause the ball animation
- **Mouse Release**: Resume the ball animation
- **Up Arrow**: Increase animation speed
- **Down Arrow**: Decrease animation speed
- **Close Window**: Exit the application

## Screenshots

The application displays a green ball bouncing within a 250x150 pixel window. The ball moves diagonally and bounces off the window boundaries.

## Technical Requirements

### Development Environment

- **Java**: OpenJDK 24 or later
- **Maven**: 3.9.x or later
- **JavaFX**: Version 21
- **Target Platforms**: macOS (Intel/Apple Silicon), Windows (x86_64/ARM64), Linux (x86_64/ARM64)

### Runtime Requirements

- **Java Runtime**: OpenJDK 24 or later
- **JavaFX Runtime**: Automatically managed by Maven dependencies

## Quick Start

### Prerequisites

1. Install Java 24 or later
2. Install Maven 3.9.x or later
3. Ensure your system supports JavaFX

### Running the Application

#### On macOS/Linux:

```bash
# Make the script executable (first time only)
chmod +x run.sh

# Run the application
./run.sh
```

#### On Windows:

```cmd
# Run the application
run.bat
```

#### Using Maven directly:

```bash
# Clean and compile
mvn clean compile

# Run the application
mvn javafx:run
```

### Building from Source

```bash
# Clone the repository (if applicable)
git clone <repository-url>
cd 02-15-BouncingBall

# Build the project
mvn clean package

# Run the application
mvn javafx:run
```

## Project Structure

```
02-15-BouncingBall/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/acu/javafx/bouncingball/
│   │   │   │   ├── BounceBallControl.java    # Main application class
│   │   │   │   └── BallPane.java             # Ball animation component
│   │   │   └── module-info.java              # Java module definition
│   │   └── resources/                         # Application resources
│   └── test/
│       └── java/                             # Unit tests
├── docs/
│   ├── concepts.md                           # Main concepts and design decisions
│   └── architecture.md                       # Detailed architecture documentation
├── pom.xml                                   # Maven build configuration
├── run.sh                                    # Unix/Linux/macOS execution script
├── run.bat                                   # Windows execution script
└── README.md                                 # This file
```

## Architecture

The application follows a modular design with clear separation of concerns:

- **BounceBallControl**: Main application class handling UI setup and event coordination
- **BallPane**: Animation component managing ball movement and boundary detection
- **JavaFX Framework**: Provides animation, graphics, and event handling capabilities

### Key Components

1. **Animation System**: Uses JavaFX Timeline for smooth 50ms interval updates
2. **Event Handling**: Mouse and keyboard event management for user interaction
3. **Boundary Detection**: Collision detection and velocity reversal logic
4. **Speed Control**: Dynamic animation rate adjustment with minimum protection

## Development

### Code Quality

- **JavaDoc Documentation**: Comprehensive code documentation
- **Clean Architecture**: Separation of concerns and modular design
- **Error Handling**: Robust boundary condition management
- **Cross-Platform**: Platform-specific dependency management

### Build System

The project uses Maven with the following key features:

- **Platform Detection**: Automatic detection of target platform architecture
- **JavaFX Dependencies**: Platform-specific classifiers for different architectures
- **Java 24 Compatibility**: Modern Java features and performance
- **Executable JAR**: Maven shade plugin for standalone distribution

### Testing

```bash
# Run unit tests
mvn test

# Run with coverage
mvn jacoco:report
```

## Platform Compatibility

### Supported Platforms

| Platform | Architecture | Status |
|----------|--------------|--------|
| macOS | Apple Silicon (ARM64) | ✅ Supported |
| macOS | Intel (x86_64) | ✅ Supported |
| Windows | x86_64 | ✅ Supported |
| Windows | ARM64 | ✅ Supported |
| Linux | x86_64 | ✅ Supported |
| Linux | ARM64 | ✅ Supported |

### Platform-Specific Notes

- **macOS**: Optimized for both Intel and Apple Silicon processors
- **Windows**: Compatible with Windows 10/11 on x86_64 and ARM64
- **Linux**: Tested on Ubuntu, CentOS, and other major distributions

## Troubleshooting

### Common Issues

1. **JavaFX Not Found**: Ensure JavaFX dependencies are properly resolved
   ```bash
   mvn dependency:resolve
   ```

2. **Platform Detection Issues**: Verify os-maven-plugin is working
   ```bash
   mvn help:evaluate -Dexpression=os.detected.classifier
   ```

3. **Animation Performance**: Adjust animation intervals in BallPane.java if needed

4. **Keyboard Controls Not Working**: Ensure the window has focus
   - Click on the window to give it focus
   - The application automatically requests focus on startup

### Debug Mode

```bash
# Run with debug output
mvn javafx:run -Djavafx.verbose=true
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Ensure all tests pass
6. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- **JavaFX Team**: For the excellent JavaFX framework
- **Maven Community**: For the robust build system
- **OpenJDK Team**: For the modern Java platform

## References

- [JavaFX Documentation](https://openjfx.io/)
- [Maven Documentation](https://maven.apache.org/)
- [JavaFX Animation Tutorial](https://docs.oracle.com/javase/8/javafx/api/javafx/animation/package-summary.html)

## Version History

- **v1.0.0**: Initial release with basic bouncing ball functionality
  - Smooth animation with boundary detection
  - Mouse and keyboard controls
  - Cross-platform compatibility
  - Comprehensive documentation