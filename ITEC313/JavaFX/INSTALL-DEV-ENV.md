# Installing the JavaFX Development Environment

This guide will help you set up Java, Maven, and JavaFX on your system. Follow the instructions for your operating system.

---

## macOS (Intel & Apple Silicon)

### 1. Install Homebrew (if not already installed)
```
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

### 2. Install Java (OpenJDK 24)
```
brew install openjdk@24
```
Add Java to your PATH (if not done automatically):
```
echo 'export PATH="/opt/homebrew/opt/openjdk@24/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

### 3. Install Maven
```
brew install maven
```

### 4. JavaFX (via Maven)
JavaFX dependencies are managed by Maven. No manual installation is required.

---

## Linux (Ubuntu/Debian)

### 1. Install Java (OpenJDK 24)
```
sudo apt update
sudo apt install openjdk-24-jdk
```

### 2. Install Maven
```
sudo apt install maven
```

### 3. JavaFX (via Maven)
JavaFX dependencies are managed by Maven. No manual installation is required.

---

## Linux (RedHat/CentOS/Fedora)

### 1. Install Java (OpenJDK 24)
```
sudo dnf install java-24-openjdk-devel
```

### 2. Install Maven
```
sudo dnf install maven
```

### 3. JavaFX (via Maven)
JavaFX dependencies are managed by Maven. No manual installation is required.

---

## Windows 11

### 1. Install Java (OpenJDK 24)
- Download the OpenJDK 24 installer from [Adoptium](https://adoptium.net/) or [Oracle](https://www.oracle.com/java/technologies/downloads/).
- Run the installer and follow the prompts.
- Add the Java `bin` directory to your `PATH` environment variable if not done automatically.

### 2. Install Maven
- Download Maven from [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)
- Unzip to a folder (e.g., `C:\maven`)
- Add the Maven `bin` directory to your `PATH` environment variable.

### 3. JavaFX (via Maven)
JavaFX dependencies are managed by Maven. No manual installation is required.

---

## Verifying Installation

Check Java version:
```
java -version
```
Check Maven version:
```
mvn -version
```

---

## Building and Running Projects

1. Navigate to a project folder (e.g., `12-03-MapHash`):
   ```
   cd 12-03-MapHash
   ```
2. Build the project:
   ```
   mvn clean compile
   ```
3. Run the JavaFX application:
   ```
   mvn javafx:run
   ```

For platform-specific scripts, use `run.sh` (macOS/Linux) or `run.bat` (Windows).

---

For troubleshooting, see the README.md or the documentation in each project folder. 