# Cross-Platform JavaFX Build Guide

This project has been configured to work across different operating systems and architectures automatically. The `pom.xml` uses Maven profiles to detect your platform and select the appropriate JavaFX dependencies.

## Supported Platforms

The project automatically detects and supports:

### Windows
- **All Windows architectures**: Uses `win` classifier

### Linux  
- **Linux x64** (Intel/AMD 64-bit): Uses `linux` classifier

### macOS
- **Mac Apple Silicon** (ARM64/aarch64): Uses `mac-aarch64` classifier
- **Mac Intel** (x86_64): Uses `mac` classifier  
- **Other Mac architectures**: Falls back to `mac` classifier

## How It Works

The `pom.xml` contains platform-specific profiles that automatically activate based on your operating system and architecture:

```xml
<profiles>
    <!-- macOS Apple Silicon (ARM64) -->
    <profile>
        <id>mac-aarch64</id>
        <activation>
            <os>
                <family>mac</family>
                <arch>aarch64</arch>
            </os>
        </activation>
        <properties>
            <javafx.platform>mac-aarch64</javafx.platform>
        </properties>
    </profile>
    <!-- ... other profiles ... -->
</profiles>
```

## Building the Project

### Prerequisites

- Java 17 or higher
- Maven 3.6.0 or higher

### Build Commands

1. **Check active profiles** (see which platform was detected):

   ```bash
   mvn help:active-profiles
   ```

2. **Clean and compile**:

   ```bash
   mvn clean compile
   ```

3. **Run the application**:

   ```bash
   mvn clean javafx:run
   ```

4. **Package the application**:

   ```bash
   mvn clean package
   ```

## Manual Platform Override

If automatic detection fails or you want to target a specific platform, you can manually specify the platform:

```bash
# For Windows
mvn clean compile -Djavafx.platform=win

# For Linux x64
mvn clean compile -Djavafx.platform=linux

# For Mac Intel
mvn clean compile -Djavafx.platform=mac

# For Mac Apple Silicon
mvn clean compile -Djavafx.platform=mac-aarch64
```

## Troubleshooting

### If you get dependency resolution errors

1. Check that your platform is correctly detected with `mvn help:active-profiles`
2. Try manually specifying the platform with `-Djavafx.platform=<platform>`
3. Ensure you have Java 17+ and Maven 3.6.0+
4. Clear JavaFX cache if experiencing architecture conflicts: `rm -rf ~/.openjfx/cache`

### Available JavaFX Platform Identifiers

- `win` - Windows (all architectures)
- `linux` - Linux x64
- `mac` - macOS Intel/Universal
- `mac-aarch64` - macOS Apple Silicon

## Development Notes

- The project uses JavaFX 21.0.2 for maximum compatibility
- All JavaFX modules (controls, fxml, base, graphics) are included
- The Maven Shade plugin creates a fat JAR that includes all dependencies
- Platform detection happens automatically during the Maven lifecycle
- Architecture-specific native libraries are correctly resolved

## Testing on Different Platforms

To verify cross-platform compatibility:

1. **Check the effective POM** to see resolved dependencies:

   ```bash
   mvn help:effective-pom
   ```

2. **List dependencies** to verify correct platform JARs are selected:

   ```bash
   mvn dependency:list | grep javafx
   ```

3. **Analyze dependencies** to see the dependency tree:

   ```bash
   mvn dependency:tree
   ```

## Common Issues and Solutions

### Architecture Mismatch (macOS)
If you see errors about incompatible architectures (x86_64 vs arm64), clear the JavaFX cache:
```bash
rm -rf ~/.openjfx/cache
mvn clean compile
```

### Profile Detection Issues
If the wrong profile is detected, you can force a specific platform:
```bash
mvn clean javafx:run -Djavafx.platform=mac-aarch64
```
