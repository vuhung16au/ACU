
# Generate the project 

Save your files to the folder: /Users/vuhung/00.Work/02.ACU/github/ITEC313/JavaFX/07-05-GeometricObjectComparator/

Create a JavaFX application that demonstrates the use of 

- https://liveexample.pearsoncmg.com/html/GeometricObjectComparator.html
-> please fetch (e.g using wget, curl)the code from the links above and use the same classnames here.
when you got the code from the links, don't combine the code from the links into one file, keep the code as it is and use the same classnames here.

package name: com.acu.javafx.geometricobjectcomparator

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

## Success Criteria

- All specified controls are properly implemented and functional
- Code is well-structured and documented
- Build scripts work correctly
- The JavaFX can be run 