# Installation Guide - 2048 Game

This guide explains how to set up and run the 2048 JavaFX game on your system.

## Prerequisites

### Required Software

1. **Java Development Kit (JDK) 24 or higher**
   - Download from: https://adoptium.net/
   - Verify installation: `java -version`

2. **Apache Maven 3.6 or higher**
   - Download from: https://maven.apache.org/download.cgi
   - Verify installation: `mvn -version`

3. **JavaFX 24**
   - Included with the project dependencies
   - No separate installation required

### System Requirements

- **Operating System**: Windows, macOS, or Linux
- **Memory**: Minimum 512MB RAM
- **Disk Space**: 50MB for the application

## Installation Steps

### 1. Clone or Download the Project

```bash
# If using Git
git clone <repository-url>
cd 60-02-2048-game

# Or download and extract the ZIP file
```

### 2. Verify Java Installation

```bash
java -version
# Should show Java 24 or higher
```

### 3. Verify Maven Installation

```bash
mvn -version
# Should show Maven 3.6 or higher
```

### 4. Build the Project

```bash
# Navigate to the project directory
cd 60-02-2048-game

# Clean and compile
mvn clean compile

# Run tests
mvn test
```

### 5. Run the Application

```bash
# Using Maven (recommended)
mvn javafx:run

# Or create executable JAR
mvn clean package
java -jar target/game2048-1.0.0.jar
```

## Platform-Specific Instructions

### Windows

1. Install JDK 24 from Adoptium
2. Install Maven from Apache website
3. Add Java and Maven to your PATH environment variable
4. Open Command Prompt or PowerShell
5. Navigate to project directory and run commands

### macOS

1. Install JDK 24 using Homebrew:
   ```bash
   brew install openjdk@24
   ```

2. Install Maven using Homebrew:
   ```bash
   brew install maven
   ```

3. Open Terminal and navigate to project directory

### Linux (Ubuntu/Debian)

1. Install JDK 24:
   ```bash
   sudo apt update
   sudo apt install openjdk-24-jdk
   ```

2. Install Maven:
   ```bash
   sudo apt install maven
   ```

3. Open Terminal and navigate to project directory

## Troubleshooting

### Common Issues

1. **Java Version Error**
   - Ensure you have Java 24 or higher
   - Check JAVA_HOME environment variable

2. **Maven Not Found**
   - Verify Maven is installed and in PATH
   - Try running `mvn -version` to test

3. **JavaFX Module Issues**
   - The project uses JavaFX 24 with platform-specific dependencies
   - Maven should automatically download the correct JavaFX modules

4. **Build Failures**
   - Clean the project: `mvn clean`
   - Update dependencies: `mvn dependency:resolve`
   - Check internet connection for dependency downloads

### Getting Help

If you encounter issues:

1. Check the error messages carefully
2. Verify all prerequisites are installed
3. Ensure you're in the correct project directory
4. Try cleaning and rebuilding: `mvn clean compile`

## Verification

After successful installation, you should be able to:

1. ✅ Run `mvn clean compile` without errors
2. ✅ Run `mvn test` and see all tests pass
3. ✅ Run `mvn javafx:run` and see the game window open
4. ✅ Use arrow keys to play the game
5. ✅ See ACU brand colors in the interface

## Next Steps

Once installation is complete:

1. Read the main README.md for game instructions
2. Explore the source code to understand the implementation
3. Run the unit tests to see the game logic in action
4. Try modifying the code to add new features

## Support

For technical support or questions about the installation:

1. Check the troubleshooting section above
2. Review the project documentation
3. Contact the course instructor
4. Check the project repository for updates
