# JavaFX ClockPane Demo

A JavaFX application that demonstrates the use of a custom `ClockPane` component to display an analog clock with digital time.

## Overview

This project implements a JavaFX application that shows an analog clock with hour, minute, and second hands, along with a digital time display. The clock is built using a custom `ClockPane` class that extends `Pane` and draws the clock components using JavaFX shapes and text.

## Features

- **Analog Clock Display**: Shows hour, minute, and second hands with different colors
- **Digital Time Display**: Shows the current time in digital format
- **Responsive Design**: The clock automatically resizes with the window
- **Cross-Platform**: Runs on macOS, Windows, and Linux
- **Real-time Updates**: Displays the current system time

## Clock Components

- **Hour Hand**: Green color, shorter length
- **Minute Hand**: Blue color, medium length  
- **Second Hand**: Red color, longest length
- **Clock Face**: White circle with black border
- **Number Markers**: 12, 3, 6, and 9 o'clock positions

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/acu/javafx/clockpanesdemo/
│   │       ├── Launcher.java          # Application entry point
│   │       ├── DisplayClock.java      # Main application class
│   │       └── ClockPane.java         # Custom clock component
│   └── resources/
├── test/
│   └── java/
pom.xml                                # Maven build configuration
run.sh                                 # Unix/Linux/macOS execution script
run.bat                               # Windows execution script
run_direct.sh                         # Direct Java execution script
README.md                             # This file
```

## Technical Specifications

### Development Environment

- **Java Version**: OpenJDK 24
- **JavaFX Version**: 21
- **Maven Version**: 3.9.x or later
- **Target Platform**: Cross-platform (macOS, Windows, Linux)

### Dependencies

- JavaFX Controls
- JavaFX FXML
- JavaFX Graphics
- JavaFX Base

## Building and Running

### Prerequisites

1. **Java 24**: Install OpenJDK 24 or later
2. **Maven**: Install Apache Maven 3.9.x or later
3. **JavaFX**: The project uses Maven to manage JavaFX dependencies

### Using Maven (Recommended)

#### Unix/Linux/macOS
```bash
chmod +x run.sh
./run.sh
```

#### Windows
```cmd
run.bat
```

### Direct Java Execution

#### Unix/Linux/macOS
```bash
chmod +x run_direct.sh
./run_direct.sh
```

### Manual Maven Commands

```bash
# Clean and compile
mvn clean compile

# Run the application
mvn javafx:run

# Package the application
mvn clean package
```

## Code Examples

### ClockPane Class

The `ClockPane` class extends `Pane` and provides:

```java
// Create a clock with current time
ClockPane clock = new ClockPane();

// Create a clock with specific time
ClockPane clock = new ClockPane(14, 30, 45);

// Set time programmatically
clock.setHour(15);
clock.setMinute(30);
clock.setSecond(45);
```

### DisplayClock Application

The main application creates a window with the clock:

```java
ClockPane clock = new ClockPane();
String timeString = clock.getHour() + ":" + clock.getMinute() + ":" + clock.getSecond();
Label lblCurrentTime = new Label(timeString);

BorderPane pane = new BorderPane();
pane.setCenter(clock);
pane.setBottom(lblCurrentTime);
```

## Architecture

### Design Patterns

- **Custom Component Pattern**: `ClockPane` extends `Pane` to create a reusable clock component
- **MVC Pattern**: Separation of the clock logic (`ClockPane`) from the display logic (`DisplayClock`)
- **Observer Pattern**: The clock automatically repaints when dimensions change

### Key Classes

1. **ClockPane**: Custom JavaFX component that draws an analog clock
2. **DisplayClock**: Main application class that demonstrates the clock
3. **Launcher**: Entry point for the JavaFX application

## Cross-Platform Compatibility

The project is designed to work on multiple platforms:

- **macOS**: Intel (x86_64) and Apple Silicon (ARM64)
- **Windows**: x86_64 and ARM64
- **Linux**: x86_64 and ARM64

The Maven configuration automatically detects the platform and includes the appropriate JavaFX dependencies.

## Troubleshooting

### Common Issues

1. **JavaFX not found**: Ensure you're using the Maven build which includes JavaFX dependencies
2. **Compilation errors**: Make sure you have Java 24 installed
3. **Runtime errors**: Check that all JavaFX modules are available

### Platform-Specific Notes

- **macOS**: JavaFX is included with the JDK on recent versions
- **Windows**: May need to install JavaFX separately
- **Linux**: Use package manager or download JavaFX manually

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test on multiple platforms
5. Submit a pull request

## License

This project is part of the ACU JavaFX learning series.

## References

- [JavaFX Documentation](https://openjfx.io/)
- [Maven JavaFX Plugin](https://openjfx.io/maven-plugin.html)
- [Original ClockPane Example](https://liveexample.pearsoncmg.com/html/ClockPane.html)
- [Original DisplayClock Example](https://liveexample.pearsoncmg.com/html/DisplayClock.html)