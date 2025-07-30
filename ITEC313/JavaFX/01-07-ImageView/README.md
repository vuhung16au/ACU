# JavaFX Image and ImageView Demo

This project demonstrates the use of JavaFX `Image` and `ImageView` classes, showcasing various image manipulation techniques and interactive controls.

## 📚 Core Concepts

### The Image Class
- **Package**: `javafx.scene.image.Image`
- **Purpose**: Represents an image that can be loaded from various sources
- **Features**: Supports multiple image formats (GIF, JPEG, PNG, etc.)
- **Loading**: Can load images from URLs, files, or input streams

### The ImageView Class
- **Package**: `javafx.scene.image.ImageView`
- **Purpose**: Displays images in the JavaFX scene graph
- **Features**: Provides image manipulation capabilities (scaling, rotation, etc.)
- **Properties**: Supports fit width/height, preserve ratio, rotation, and more

## 🏗️ Project Structure

```text
01-07-ImageView/
├── src/
│   └── main/
│       ├── java/
│       │   ├── module-info.java                    # Module descriptor
│       │   └── com/acu/javafx/imagedemo/
│       │       ├── Launcher.java                   # Application entry point
│       │       ├── ShowImage.java                  # Basic Image/ImageView demo
│       │       └── ImageDemo.java                  # Enhanced interactive demo
│       └── resources/
│           └── image/
│               └── us.gif                          # Sample image file
├── pom.xml                                         # Maven configuration
├── run.sh                                          # Unix/Linux/macOS run script
├── run.bat                                         # Windows run script
├── run_direct.sh                                   # Direct Java execution script
└── README.md                                       # Project documentation
```

## 🚀 Quick Start

### Prerequisites
- **Java**: OpenJDK 24 or later
- **Maven**: 3.9.x or later
- **JavaFX**: 21 (included in dependencies)

### Running the Application

#### Option 1: Using Maven (Recommended)
```bash
# Unix/Linux/macOS
./run.sh

# Windows
run.bat
```

#### Option 2: Direct Maven Commands
```bash
# Build the project
mvn clean compile

# Run the application
mvn javafx:run
```

#### Option 3: Direct Java Execution
```bash
# First build with Maven
mvn clean compile

# Then run directly (Unix/Linux/macOS)
./run_direct.sh
```

## 📖 Application Features

### ShowImage.java
- **Basic Demo**: Simple demonstration of Image and ImageView
- **Multiple Views**: Shows the same image in different configurations
- **Properties**: Demonstrates fit width/height and rotation
- **Based on**: [Pearson ShowImage Example](https://liveexample.pearsoncmg.com/html/ShowImage.html)

### ImageDemo.java
- **Enhanced Demo**: Interactive application with controls
- **Real-time Manipulation**: Scale and rotate images dynamically
- **Multiple Images**: Support for different image sources
- **Error Handling**: Graceful handling of missing images
- **Modern UI**: Clean, responsive interface design

## 🎯 Key Learning Objectives

### Image Loading
```java
// Load image from resources
Image image = new Image("image/us.gif");

// Load image from URL
Image image = new Image("https://example.com/image.jpg");

// Load image from file
Image image = new Image(new FileInputStream("path/to/image.png"));
```

### ImageView Properties
```java
ImageView imageView = new ImageView(image);

// Set size constraints
imageView.setFitWidth(200);
imageView.setFitHeight(150);
imageView.setPreserveRatio(true);

// Apply transformations
imageView.setRotate(90);
imageView.setScaleX(1.5);
imageView.setScaleY(1.5);
```

### Interactive Controls
- **Scale Slider**: Adjust image size in real-time
- **Rotation Slider**: Rotate image from 0° to 360°
- **Image Selector**: Choose from different image sources
- **Reset Button**: Return all controls to default values

## 🔧 Technical Specifications

### Development Environment
- **Target Platform**: macOS Silicon (ARM64) - primary development environment
- **Java Version**: OpenJDK 24
- **Maven Version**: 3.9.x or later
- **JavaFX Version**: 21

### Cross-Platform Compatibility
The project is buildable and runnable on:
- **macOS**: Intel (x86_64) and Apple Silicon (ARM64)
- **Windows**: x86_64 and ARM64
- **Linux**: x86_64 and ARM64

### Build Configuration
- Platform detection properties for automatic architecture detection
- JavaFX dependencies with platform-specific classifiers
- Maven compiler plugin configured for Java 24
- JavaFX Maven plugin for running the application
- Cross-platform dependency management

## 📁 File Descriptions

### Source Files
- **`Launcher.java`**: Application entry point that launches the main demo
- **`ShowImage.java`**: Basic demonstration based on Pearson's example
- **`ImageDemo.java`**: Enhanced interactive demonstration with controls

### Configuration Files
- **`pom.xml`**: Maven build configuration with cross-platform support
- **`module-info.java`**: Java module descriptor for the application

### Build Scripts
- **`run.sh`**: Unix/Linux/macOS execution script with dependency checks
- **`run.bat`**: Windows batch execution script
- **`run_direct.sh`**: Direct Java execution without Maven

## 🎨 UI/UX Design

### Modern Interface
- Clean, responsive design with proper spacing
- Intuitive controls with real-time feedback
- Status indicators for user guidance
- Consistent styling throughout the application

### Interactive Features
- Real-time image manipulation
- Visual feedback for all controls
- Error handling with user-friendly messages
- Responsive layout that adapts to window size

## 🔍 Troubleshooting

### Common Issues

#### Image Not Loading
- Ensure image files are in the correct location (`src/main/resources/image/`)
- Check file permissions and format compatibility
- Verify image file integrity

#### JavaFX Runtime Issues
- Ensure JavaFX dependencies are properly configured
- Check Java version compatibility (Java 24+ recommended)
- Verify platform-specific JavaFX libraries are available

#### Build Errors
- Clean and rebuild: `mvn clean compile`
- Check Maven and Java versions
- Verify all dependencies are available

### Platform-Specific Notes

#### macOS
- JavaFX libraries typically installed via Homebrew
- Default path: `/opt/homebrew/opt/openjfx/libexec/lib`

#### Linux
- JavaFX libraries may need to be installed separately
- Common path: `/usr/share/openjfx/lib`

#### Windows
- JavaFX SDK can be downloaded separately
- Set `JAVAFX_PATH` environment variable if needed

## 📚 Additional Resources

- [JavaFX Image Documentation](https://openjfx.io/javadoc/21/javafx.graphics/javafx/scene/image/Image.html)
- [JavaFX ImageView Documentation](https://openjfx.io/javadoc/21/javafx.graphics/javafx/scene/image/ImageView.html)
- [Pearson ShowImage Example](https://liveexample.pearsoncmg.com/html/ShowImage.html)

## 🤝 Contributing

This project is part of the ITEC313 JavaFX learning series. For questions or improvements, please refer to the course guidelines.

## 📄 License

This project is created for educational purposes as part of the ITEC313 course curriculum.

## Screenshots

![JavaFX Image View Demo](images/01-07-ImageView.png)