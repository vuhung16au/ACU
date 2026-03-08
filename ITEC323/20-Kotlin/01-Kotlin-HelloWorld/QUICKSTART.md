# Quick Start Guide

This guide will help you build and run the Kotlin Hello World application.

## Prerequisites

Before you begin, ensure you have the following installed:

1. **Java Development Kit (JDK) 11 or higher**
   - Check your Java version:
     ```bash
     java -version
     ```
   - Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)

2. **Gradle** (optional if using wrapper)
   - Check your Gradle version:
     ```bash
     gradle -version
     ```
   - Download from [Gradle.org](https://gradle.org/install/)

## Building the Project

### Option 1: Using Gradle

Navigate to the project directory and run:

```bash
gradle build
```

This will:
- Download dependencies
- Compile the Kotlin source code
- Create a JAR file in `build/libs/`

### Option 2: Using Gradle Wrapper (if configured)

```bash
./gradlew build
```

## Running the Application

### Method 1: Using Gradle Run Task

```bash
gradle run
```

### Method 2: Running the JAR File

After building, run the generated JAR:

```bash
java -jar build/libs/kotlin-hello-world-1.0.0.jar
```

### Method 3: Using Kotlin Compiler Directly

If you have the Kotlin compiler installed:

```bash
kotlinc src/main/kotlin/HelloWorld.kt -include-runtime -d hello.jar
java -jar hello.jar
```

## Expected Output

When you run the application, you should see:

```
Hello, World!
Welcome to Kotlin programming!
```

## Common Issues

### Issue: "gradle: command not found"
**Solution:** Install Gradle or use the Gradle wrapper (`./gradlew`)

### Issue: "JAVA_HOME not set"
**Solution:** Set the JAVA_HOME environment variable to your JDK installation path

```bash
# macOS/Linux
export JAVA_HOME=$(/usr/libexec/java_home)

# Or add to ~/.zshrc or ~/.bashrc
echo 'export JAVA_HOME=$(/usr/libexec/java_home)' >> ~/.zshrc
```

### Issue: Permission denied for gradlew
**Solution:** Make the wrapper executable

```bash
chmod +x gradlew
```

## Cleaning the Build

To remove build artifacts:

```bash
gradle clean
```

## Development Tips

- **VS Code**: Install the "Kotlin Language" extension for better IDE support
- **IntelliJ IDEA**: Open the project folder, and it will automatically detect the Gradle configuration
- **Hot Reload**: Use `gradle run --continuous` for continuous build and run

## Next Steps

- Modify [HelloWorld.kt](src/main/kotlin/HelloWorld.kt) to experiment with Kotlin
- Add more Kotlin files to the `src/main/kotlin/` directory
- Explore Kotlin's features like data classes, coroutines, and extensions
- Add unit tests in `src/test/kotlin/`

For more information, visit the [Kotlin documentation](https://kotlinlang.org/docs/home.html).
