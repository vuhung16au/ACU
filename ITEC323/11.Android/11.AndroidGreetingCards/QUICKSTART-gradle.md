# Gradle CLI Quick Start

## Prerequisites
- **Java Development Kit (JDK) 17** (required for this project)
  - This project requires Java 17 due to Kotlin compiler compatibility
  - If you have Java 25+ as default, you'll encounter build errors
- Android SDK (set `ANDROID_HOME` environment variable)

## Java Version Setup

### Check Current Java Version
```bash
java -version
```

### Set Java 17 (if not default)

**macOS/Linux:**
```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
```

**Or add to your shell profile** (~/.zshrc, ~/.bashrc):
```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
export PATH=$JAVA_HOME/bin:$PATH
```

## Common Commands

### Build the Project
```bash
./gradlew build
```

### Assemble APK
```bash
# Debug variant
./gradlew assembleDebug

# Release variant
./gradlew assembleRelease
```

### Clean Build
```bash
./gradlew clean build
```

### Install on Connected Device
```bash
./gradlew installDebug
```

### Run Tests
```bash
./gradlew test
```

### List All Tasks
```bash
./gradlew tasks
```

## Output Location
Built APKs are located in:
```
app/build/outputs/apk/debug/
app/build/outputs/apk/release/
```

## Troubleshooting

### Java Version Issues
If you see `java.lang.IllegalArgumentException: 25` or similar:
```bash
# Temporarily set Java 17
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
./gradlew build
```

### Build Information
- Use `./gradlew --info build` for detailed output
- Use `./gradlew --stacktrace build` for error stack traces
- Use `./gradlew clean` to remove build artifacts
