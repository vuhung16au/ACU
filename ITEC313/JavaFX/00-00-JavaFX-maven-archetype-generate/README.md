

# JavaFX Hello World Maven Project

This repository demonstrates how to create and build a simple "Hello World" JavaFX application using Maven. It follows the official [OpenJFX Maven guide](https://openjfx.io/openjfx-docs/#maven) and uses the latest stable JavaFX and plugin versions as of August 2025.

## Project Structure

- `sample/` — Maven JavaFX project generated from archetype
- `images/` — Screenshots and images

## How to Build a JavaFX Hello World Application

### 1. Generate the Project Using Maven Archetype

Run the following command in your terminal:

```sh
mvn archetype:generate \
        -DarchetypeGroupId=org.openjfx \
        -DarchetypeArtifactId=javafx-archetype-simple \
        -DarchetypeVersion=0.0.6 \
        -DgroupId=org.openjfx \
        -DartifactId=sample \
        -Dversion=1.0.0 \
        -Djavafx-version=24.0.2
```

This will create a new folder called `sample` with a basic JavaFX application.

### 2. Build and Run the Application

Navigate to the generated project directory and run:

```sh
cd sample
mvn clean javafx:run
```

You should see a simple JavaFX window appear. See the screenshot below:

![JavaFX Sample](images/JavaFX-maven-archetype-generate.png)

## Explanation of `pom.xml`

The `pom.xml` file in the generated project includes the following important sections:

### [JavaFX](https://openjfx.io/) Dependency

```
<dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>24.0.2</version>
</dependency>
```
This adds the JavaFX controls library to your project.
`24.0.2` is the latest stable version as of 9 August 2025.

### [JavaFX Maven Plugin](https://mvnrepository.com/artifact/org.openjfx/javafx-maven-plugin)

```
<plugin>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-maven-plugin</artifactId>
        <version>0.0.8</version>
</plugin>
```
This plugin allows you to run JavaFX applications using `mvn javafx:run`.
`0.0.8` is the latest stable version as of 9 August 2025.

### [Maven Compiler Plugin](https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-compiler-plugin)

```
<plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.14.0</version>
</plugin>
```
This ensures your code is compiled with the latest stable Maven compiler plugin.
`3.14.0` is the latest stable version as of 9 August 2025.

---

For more information, visit the [OpenJFX website](https://openjfx.io/index.html).