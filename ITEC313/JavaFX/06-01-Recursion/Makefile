# Makefile for 06-01-Recursion JavaFX Project

# Variables
JAVA_SRC_DIR = src/main/java
JAVA_CLASS_DIR = target/classes
MAIN_CLASS = com.acu.javafx.recursion.RecursionDemo

# Define Java source files explicitly
JAVA_SOURCES = $(JAVA_SRC_DIR)/com/acu/javafx/recursion/ComputeFactorial.java \
               $(JAVA_SRC_DIR)/com/acu/javafx/recursion/ComputeFactorialTailRecursion.java \
               $(JAVA_SRC_DIR)/com/acu/javafx/recursion/ComputeFibonacci.java \
               $(JAVA_SRC_DIR)/com/acu/javafx/recursion/DirectorySize.java \
               $(JAVA_SRC_DIR)/com/acu/javafx/recursion/RecursiveBinarySearch.java \
               $(JAVA_SRC_DIR)/com/acu/javafx/recursion/RecursiveSelectionSort.java \
               $(JAVA_SRC_DIR)/com/acu/javafx/recursion/TowerOfHanoi.java

# Individual class names for running
CLASS_NAMES = com.acu.javafx.recursion.ComputeFactorial \
              com.acu.javafx.recursion.ComputeFactorialTailRecursion \
              com.acu.javafx.recursion.ComputeFibonacci \
              com.acu.javafx.recursion.DirectorySize \
              com.acu.javafx.recursion.RecursiveBinarySearch \
              com.acu.javafx.recursion.RecursiveSelectionSort \
              com.acu.javafx.recursion.TowerOfHanoi

# Default target
all: build

# Build target - compile all Java files (excluding RecursionDemo.java)
build: $(JAVA_CLASS_DIR)
	@echo "Building Java files..."
	@mkdir -p $(JAVA_CLASS_DIR)
	javac -d $(JAVA_CLASS_DIR) -cp "$(JAVA_CLASS_DIR)" $(JAVA_SOURCES)
	@echo "Build completed successfully!"

# Clean target - remove compiled classes
clean:
	@echo "Cleaning compiled files..."
	rm -rf $(JAVA_CLASS_DIR)
	@echo "Clean completed!"

# Create target directory if it doesn't exist
$(JAVA_CLASS_DIR):
	mkdir -p $(JAVA_CLASS_DIR)

# Run target - execute all compiled Java sources
run: build
	@echo "Running all Java sources..."
	@for class_name in $(CLASS_NAMES); do \
		echo "Running $$class_name..."; \
		java -cp $(JAVA_CLASS_DIR) $$class_name; \
		echo ""; \
	done
	@echo "All Java sources executed successfully!"

# Individual run targets for each Java file
run-factorial: build
	@echo "Running ComputeFactorial..."
	java -cp $(JAVA_CLASS_DIR) com.acu.javafx.recursion.ComputeFactorial

run-factorial-tail: build
	@echo "Running ComputeFactorialTailRecursion..."
	java -cp $(JAVA_CLASS_DIR) com.acu.javafx.recursion.ComputeFactorialTailRecursion

run-fibonacci: build
	@echo "Running ComputeFibonacci..."
	java -cp $(JAVA_CLASS_DIR) com.acu.javafx.recursion.ComputeFibonacci

run-directory-size: build
	@echo "Running DirectorySize..."
	java -cp $(JAVA_CLASS_DIR) com.acu.javafx.recursion.DirectorySize

run-binary-search: build
	@echo "Running RecursiveBinarySearch..."
	java -cp $(JAVA_CLASS_DIR) com.acu.javafx.recursion.RecursiveBinarySearch

run-selection-sort: build
	@echo "Running RecursiveSelectionSort..."
	java -cp $(JAVA_CLASS_DIR) com.acu.javafx.recursion.RecursiveSelectionSort

run-tower-of-hanoi: build
	@echo "Running TowerOfHanoi..."
	java -cp $(JAVA_CLASS_DIR) com.acu.javafx.recursion.TowerOfHanoi

# Help target
help:
	@echo "Available targets:"
	@echo "  build              - Compile all Java source files"
	@echo "  run                - Run all compiled Java source files"
	@echo "  run-factorial      - Run ComputeFactorial only"
	@echo "  run-factorial-tail - Run ComputeFactorialTailRecursion only"
	@echo "  run-fibonacci      - Run ComputeFibonacci only"
	@echo "  run-directory-size - Run DirectorySize only"
	@echo "  run-binary-search  - Run RecursiveBinarySearch only"
	@echo "  run-selection-sort - Run RecursiveSelectionSort only"
	@echo "  run-tower-of-hanoi - Run TowerOfHanoi only"
	@echo "  clean              - Remove all compiled class files"
	@echo "  help               - Show this help message"

.PHONY: all build run clean help run-factorial run-factorial-tail run-fibonacci run-directory-size run-binary-search run-selection-sort run-tower-of-hanoi
