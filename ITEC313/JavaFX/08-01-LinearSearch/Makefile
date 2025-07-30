# Makefile for LinearSearch JavaFX Project
# Author: ACU JavaFX Team
# Description: Build and run targets for the LinearSearch application

# Project configuration
PROJECT_NAME = linearsearch
MAIN_CLASS = com.acu.javafx.linearsearch.LinearSearchApp
GEEKS_CLASS = com.acu.javafx.linearsearch.Geeks
JAVA_VERSION = 24
JAVAFX_VERSION = 21

# Directories
SRC_DIR = src/main/java
TARGET_DIR = target
CLASSES_DIR = $(TARGET_DIR)/classes
JAR_FILE = $(TARGET_DIR)/$(PROJECT_NAME)-1.0.0.jar
FAT_JAR_FILE = $(TARGET_DIR)/$(PROJECT_NAME)-1.0.0-fat.jar

# Java compiler and runtime
JAVAC = javac
JAVA = java
JAR = jar

# JavaFX modules (for runtime)
JAVAFX_MODULES = javafx.controls,javafx.fxml,javafx.graphics

# Source files
JAVA_SOURCES = $(shell find $(SRC_DIR) -name "*.java")
CLASS_FILES = $(JAVA_SOURCES:$(SRC_DIR)/%.java=$(CLASSES_DIR)/%.class)

# Default target
.PHONY: all
all: compile

# Compile Java source files (requires JavaFX modules)
.PHONY: compile
compile: $(CLASS_FILES)

$(CLASSES_DIR)/%.class: $(SRC_DIR)/%.java
	@echo "Compiling $<..."
	@mkdir -p $(dir $@)
	$(JAVAC) --module-path "$(shell find ~/.m2/repository/org/openjfx -name "*.jar" | head -1 | xargs dirname)" \
		--add-modules $(JAVAFX_MODULES) \
		-cp "$(SRC_DIR)" -d $(CLASSES_DIR) $<

# Build JAR file
.PHONY: build
build: compile
	@echo "Building JAR file..."
	@mkdir -p $(TARGET_DIR)
	$(JAR) cf $(JAR_FILE) -C $(CLASSES_DIR) .

# Build fat JAR with dependencies (requires Maven)
.PHONY: fat-jar
fat-jar:
	@echo "Building fat JAR with dependencies..."
	mvn clean package

# Run the main application
.PHONY: run
run: compile
	@echo "Running LinearSearch application..."
	$(JAVA) --module-path "$(shell find ~/.m2/repository/org/openjfx -name "*.jar" | head -1 | xargs dirname)" \
		--add-modules $(JAVAFX_MODULES) \
		-cp $(CLASSES_DIR) $(MAIN_CLASS)

# Run with Maven (recommended for JavaFX)
.PHONY: run-maven
run-maven:
	@echo "Running with Maven..."
	mvn javafx:run

# Compile only the Geeks class (no JavaFX dependencies)
.PHONY: compile-geeks
compile-geeks:
	@echo "Compiling Geeks class..."
	@mkdir -p $(CLASSES_DIR)
	$(JAVAC) -cp "$(SRC_DIR)" -d $(CLASSES_DIR) $(SRC_DIR)/com/acu/javafx/linearsearch/Geeks.java

# Run the Geeks class specifically
.PHONY: run-geeks
run-geeks: compile-geeks
	@echo "Running Geeks linear search demo..."
	$(JAVA) -cp $(CLASSES_DIR) $(GEEKS_CLASS)

# Clean build artifacts
.PHONY: clean
clean:
	@echo "Cleaning build artifacts..."
	rm -rf $(TARGET_DIR)
	rm -rf $(CLASSES_DIR)
	find . -name "*.class" -delete

# Clean Maven artifacts
.PHONY: clean-maven
clean-maven:
	@echo "Cleaning Maven artifacts..."
	mvn clean

# Full clean (both Make and Maven)
.PHONY: clean-all
clean-all: clean clean-maven

# Install dependencies (Maven)
.PHONY: install
install:
	@echo "Installing dependencies..."
	mvn dependency:resolve

# Test compilation
.PHONY: test-compile
test-compile: compile
	@echo "Testing compilation..."
	@echo "✓ All Java files compiled successfully"

# Show project info
.PHONY: info
info:
	@echo "=== LinearSearch Project Info ==="
	@echo "Project: $(PROJECT_NAME)"
	@echo "Main Class: $(MAIN_CLASS)"
	@echo "Java Version: $(JAVA_VERSION)"
	@echo "JavaFX Version: $(JAVAFX_VERSION)"
	@echo "Source Directory: $(SRC_DIR)"
	@echo "Target Directory: $(TARGET_DIR)"
	@echo "Java Sources:"
	@for file in $(JAVA_SOURCES); do echo "  - $$file"; done

# Help target
.PHONY: help
help:
	@echo "=== LinearSearch Makefile Help ==="
	@echo "Available targets:"
	@echo "  all          - Default target, compiles the project"
	@echo "  compile      - Compile Java source files (with JavaFX)"
	@echo "  compile-geeks- Compile only Geeks class (no JavaFX)"
	@echo "  build        - Build JAR file"
	@echo "  fat-jar      - Build fat JAR with dependencies (Maven)"
	@echo "  run          - Run the main application"
	@echo "  run-maven    - Run with Maven (recommended for JavaFX)"
	@echo "  run-geeks    - Run the Geeks linear search demo"
	@echo "  clean        - Clean build artifacts"
	@echo "  clean-maven  - Clean Maven artifacts"
	@echo "  clean-all    - Full clean (both Make and Maven)"
	@echo "  install      - Install dependencies (Maven)"
	@echo "  test-compile - Test compilation"
	@echo "  info         - Show project information"
	@echo "  help         - Show this help message"

# Dependencies
$(CLASS_FILES): $(JAVA_SOURCES)

# Ensure target directory exists
$(CLASSES_DIR):
	mkdir -p $(CLASSES_DIR)
