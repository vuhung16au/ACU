# JavaFX Course Projects

## Overview

This repository contains a comprehensive collection of JavaFX-based projects designed to teach and demonstrate core concepts in Java programming, data structures, algorithms, and user interface development. Each sub-folder is a self-contained module focusing on a specific topic, ranging from basic JavaFX usage to advanced data structures and algorithms, with interactive GUIs and practical exercises.

## Sub-folder Overview

Each sub-folder follows the naming convention `0x-0x-TopicName` and contains:
- Source code (Java, JavaFX)
- Documentation (README.md, Prompt.md, PROJECT_SUMMARY.md, etc.)
- Images and resources
- Build scripts (Maven, shell/batch scripts)

### Example Subfolders

- `01-01-JavaFX-HelloWorld`: Basic JavaFX application structure and setup.
- `01-03-Panes.UI.Controls.Shapes`: Demonstrates JavaFX panes, UI controls, and shape drawing.
- `07-02-Sets-Maps`: Interactive demos of Java Sets and Maps, including HashSet, TreeSet, HashMap, and TreeMap.
- `12-01-AVL-Trees`: AVL tree implementation and visualization, including rotations and balancing.
- `12-02-Probing`: Visualization of hash table collision resolution (linear, quadratic, chaining).
- `12-03-MapHash`: Custom Map and HashSet implementations with JavaFX GUI.

(See each subfolder's README.md for detailed objectives and features.)

## Target Audience

- University students in Computer Science, Software Engineering, or related fields
- Self-learners interested in Java, JavaFX, and data structures
- Instructors seeking modular, hands-on teaching materials
- Anyone preparing for technical interviews or practical Java development

## How to Use This Repo to Learn

1. **Start with Basics**: Begin with the `01-01-JavaFX-HelloWorld` and other `01-xx` folders to learn JavaFX fundamentals.
2. **Progress to UI and Controls**: Explore `01-03-Panes.UI.Controls.Shapes` and similar modules for UI components.
3. **Study Data Structures**: Move to `07-xx` and `12-xx` folders for in-depth data structure and algorithm demos.
4. **Follow the Order**: The folder numbering suggests a recommended learning path, from basic to advanced.
5. **Time Required**: Each module typically takes 1-3 hours, depending on complexity and prior experience. The full course can be completed in 2-4 weeks with regular study.
6. **Hands-on Practice**: Run the JavaFX applications, modify the code.

## Exercises for Learners

- Implement missing features or enhancements described in each Prompt.md
- Modify UI layouts and add new controls
- Extend data structures with new methods (e.g., add balancing to BST, new hash functions)
- Analyze and compare performance of different algorithms
- Complete the exercises and questions in each subfolder's documentation

## Folder Structure

```
JavaFX/
├── 01-01-JavaFX-HelloWorld/
├── 01-02-JavaFX.Button/
├── ...
├── 07-02-Sets-Maps/
├── 12-01-AVL-Trees/
├── 12-02-Probing/
├── 12-03-MapHash/
├── ...
├── LICENSE.md
├── README.md
├── INSTALL-DEV-ENV.md
└── TODO.md
```

- Each `0x-0x-TopicName/` folder contains:
  - `src/` (Java source code)
  - `docs/` (documentation)
  - `images/` (optional, for screenshots/diagrams)
  - `pom.xml` (Maven build file)
  - `run.sh`, `run.bat` (platform scripts)
  - `README.md`, `Prompt.md`, `PROJECT_SUMMARY.md` (learning materials)

## Getting Started

See [INSTALL-DEV-ENV.md](INSTALL-DEV-ENV.md) for instructions on setting up your development environment (Java, Maven, JavaFX) on macOS, Linux, and Windows.

## Maven Build Targets and JAR Packaging

This project uses Maven for build management. Each subfolder contains its own `pom.xml` file with specific configurations for JavaFX applications.

### Core Maven Build Targets

#### 1. **clean**
Removes all build artifacts from the `target/` directory.
```bash
mvn clean
```
- Deletes compiled classes, JAR files, and other generated files
- Useful before rebuilding to ensure a clean state

#### 2. **compile**
Compiles the Java source code into bytecode.
```bash
mvn compile
```
- Compiles all Java files in `src/main/java/`
- Creates `.class` files in `target/classes/`
- Downloads and manages dependencies automatically

#### 3. **package**
Creates a JAR file with all dependencies and resources.
```bash
mvn package
```
- Compiles the code (if not already compiled)
- Runs tests (if configured)
- Creates a JAR file in `target/` directory
- Copies runtime dependencies to `target/lib/`

### Building Individual Projects

Navigate to any subfolder and run Maven commands:

```bash
# Navigate to a specific project
cd 01-01-JavaFX-HelloWorld

# Clean previous builds
mvn clean

# Compile the project
mvn compile

# Package into JAR
mvn package
```

### Running JavaFX Applications

#### Method 1: Using Maven JavaFX Plugin
```bash
# Run directly with Maven (recommended for development)
mvn clean javafx:run
```

#### Method 2: Running JAR Files
After packaging, you can run the JAR file:

```bash
# Navigate to the project directory
cd 01-01-JavaFX-HelloWorld

# Package the application
mvn clean package

# Run the JAR file
java -jar target/JavaFX-HelloWorld-1.0.jar
```

**Note**: JavaFX applications require additional JVM arguments to run properly. The JAR files are configured with the necessary manifest entries, but you may need to specify the module path:

```bash
java --module-path target/lib --add-modules javafx.controls,javafx.fxml -jar target/JavaFX-HelloWorld-1.0.jar
```

#### Method 3: Using Platform Scripts
Each project includes platform-specific run scripts:
- **Linux/macOS**: `run.sh`
- **Windows**: `run.bat`

```bash
# Make script executable (Linux/macOS)
chmod +x run.sh

# Run the application
./run.sh
```

### Building All Projects

To build all projects from the root directory:

```bash
# Clean all projects
mvn clean

# Compile all projects
mvn compile

# Package all projects
mvn package
```

### JAR File Structure

After packaging, each project's `target/` directory contains:
- `project-name-version.jar` - Main application JAR
- `lib/` - Directory containing all runtime dependencies
- `classes/` - Compiled bytecode
- `generated-sources/` - Any generated source files

### Troubleshooting

1. **Module Path Issues**: Ensure JavaFX modules are available in the module path
2. **Version Conflicts**: Check that Java and JavaFX versions are compatible
3. **Dependencies**: Run `mvn dependency:resolve` to verify all dependencies are available
4. **Clean Build**: If experiencing issues, try `mvn clean package` for a fresh build

### Advanced Maven Commands

```bash
# Skip tests during build
mvn package -DskipTests

# Run with specific Java version
mvn package -Dmaven.compiler.source=21 -Dmaven.compiler.target=21

# Install to local Maven repository
mvn install

# Generate dependency tree
mvn dependency:tree
```

---

**Happy Learning!** 