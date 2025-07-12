Create a JavaFX application that demonstrates the use of various UI controls, satisfying the objectives outlined in the chapter. The application should include:

Objectives

Understand the JavaFX framework and its components to create graphical user interfaces (GUIs) in Java applications. This chapter covers the following objectives:

• To create graphical user interfaces with various user-interface controls (§§16.2–16.11).
• To create a label with text and graphic using the Label class and explore properties in the abstract Labeled class (§16.2).
• To create a button with text and graphic using the Button class and set a handler using the setOnAction method in the abstract ButtonBase class (§16.3).
• To create a check box using the CheckBox class (§16.4).
• To create a radio button using the RadioButton class and group radio buttons using a ToggleGroup (§16.5).
• To enter data using the TextField class and password using the PasswordField class (§16.6).
• To enter data in multiple lines using the TextArea class (§16.7).
• To select a single item using ComboBox (§16.8).
• To select a single or multiple items using ListView (§16.9).
• To select a range of values using ScrollBar (§16.10).
• To select a range of values using Slider and explore differences between ScrollBar and Slider (§16.11).
• To develop a tic-tac-toe game (§16.12).
• To view and play video and audio using the Media, MediaPlayer, and MediaView (§16.13).
• To develop a case study for showing the national flag and play anthem (§16.14).

Fetch the Examples below to see implementations of the objectives:
- https://liveexample.pearsoncmg.com/html/LabelWithGraphic.html
- https://liveexample.pearsoncmg.com/html/ButtonDemo.html
- https://liveexample.pearsoncmg.com/html/CheckBoxDemo.html
- https://liveexample.pearsoncmg.com/html/RadioButtonDemo.html
- https://liveexample.pearsoncmg.com/html/TextFieldDemo.html
- https://liveexample.pearsoncmg.com/html/DescriptionPane.html
- https://liveexample.pearsoncmg.com/html/TextAreaDemo.html
- https://liveexample.pearsoncmg.com/html/ComboBoxDemo.html
- https://liveexample.pearsoncmg.com/html/ListViewDemo.html
- https://liveexample.pearsoncmg.com/html/ScrollBarDemo.html
- https://liveexample.pearsoncmg.com/html/SliderDemo.html
- https://liveexample.pearsoncmg.com/html/MediaDemo.html

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
docs/concepts.md               # Main concepts and design decisions
docs/architecture.md           # Detailed architecture and design patterns used
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