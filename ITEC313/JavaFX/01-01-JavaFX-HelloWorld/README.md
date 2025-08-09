# JavaFX Hello World Example

[How to](https://code.visualstudio.com/docs/java/java-gui) build and run a simple JavaFX application that displays "Hello, World!" using Maven using VS Code.

## Maven Commands

### Clean, Compile and Run

To clean, compile and run the JavaFX application:

```bash
# Clean previous builds
mvn clean

# Compile the application
mvn compile

# Run the JavaFX application
mvn javafx:run
```

Or run all steps in one command:

```bash
mvn clean compile javafx:run
```

### Run the JAR File Directly

After building the project with `mvn clean package`, you can run the generated JAR file directly:

```bash
# Navigate to the target directory
cd target

# Run the JAR file with JavaFX modules
java --module-path lib --add-modules javafx.controls,javafx.fxml -jar JavaFX-HelloWorld-1.0.jar
```

Note: The JAR file includes a proper manifest with the main class, so it can be executed directly. However, since it's a JavaFX application, you need to specify the module path and required JavaFX modules.

### Package and Create Standalone Application

To create (or recreate) the standalone macOS `.app` bundle you now have two easy options:

Option 1 (recommended - handles cleanup automatically):
 
```bash
./jpackage.sh
```

Option 2 (manual commands):
 
```bash
mvn clean package
open target/dist/JavaFX-HelloWorld.app
```

What happens during packaging:
 
* Cleans previous build output
* Compiles sources
* Packages the runnable JAR (with manifest main class)
* Copies JavaFX runtime dependencies into `target/lib`
* Runs `jpackage` (bound to the `package` phase) to build the app image at `target/dist/JavaFX-HelloWorld.app`

If you invoke only the jpackage goal directly (e.g. `mvn jpackage:jpackage`) you must ensure the app image directory does not already exist, or run `mvn clean package` instead.

## Screenshot of JavaFX Hello World Application

![JavaFX Hello World](images/JavaFX-HelloWorld.png)
![Maven JavaFX Build](images/maven-javafx-build.png)

