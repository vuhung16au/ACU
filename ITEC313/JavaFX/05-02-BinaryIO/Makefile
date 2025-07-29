# Makefile for BinaryIO JavaFX Project
# Targets: build, run, clean

# Java compiler
JAVAC = javac

# Java runtime
JAVA = java

# Source directory
SRC_DIR = src/main/java

# Class directory
CLASS_DIR = target/classes

# Package base
PACKAGE_BASE = com.acu.javafx.binaryio

# Java files to compile and run
JAVA_FILES = \
	Copy.java \
	TestDataStream.java \
	TestFileStream.java \
	TestObjectInputStream.java \
	TestObjectOutputStream.java \
	TestObjectStreamForArray.java \
	TestRandomAccessFile.java

# Full paths to Java source files
SOURCES = $(addprefix $(SRC_DIR)/com/acu/javafx/binaryio/, $(JAVA_FILES))

# Class files (without .java extension)
CLASS_FILES = $(JAVA_FILES:.java=.class)

# Full paths to class files
CLASS_PATHS = $(addprefix $(CLASS_DIR)/com/acu/javafx/binaryio/, $(CLASS_FILES))

# Default target
all: build

# Build all Java files
build: $(CLASS_PATHS)

# Compile all Java files at once
$(CLASS_PATHS): $(SOURCES)
	@echo "Compiling all Java files..."
	@mkdir -p $(CLASS_DIR)
	$(JAVAC) -d $(CLASS_DIR) -cp $(SRC_DIR) $(SOURCES)

# Run all files at once
run: build
	@echo "Running all BinaryIO demo files..."
	@echo "=================================="
	@echo "1. Running Copy.java..."
	@rm -f copied-target.txt
	@$(JAVA) -cp $(CLASS_DIR) $(PACKAGE_BASE).Copy source.txt copied-target.txt
	@echo ""
	@echo "2. Running TestDataStream.java..."
	@$(JAVA) -cp $(CLASS_DIR) $(PACKAGE_BASE).TestDataStream
	@echo ""
	@echo "3. Running TestFileStream.java..."
	@$(JAVA) -cp $(CLASS_DIR) $(PACKAGE_BASE).TestFileStream
	@echo ""
	@echo "4. Running TestObjectInputStream.java..."
	@$(JAVA) -cp $(CLASS_DIR) $(PACKAGE_BASE).TestObjectInputStream
	@echo ""
	@echo "5. Running TestObjectOutputStream.java..."
	@$(JAVA) -cp $(CLASS_DIR) $(PACKAGE_BASE).TestObjectOutputStream
	@echo ""
	@echo "6. Running TestObjectStreamForArray.java..."
	@$(JAVA) -cp $(CLASS_DIR) $(PACKAGE_BASE).TestObjectStreamForArray
	@echo ""
	@echo "7. Running TestRandomAccessFile.java..."
	@$(JAVA) -cp $(CLASS_DIR) $(PACKAGE_BASE).TestRandomAccessFile
	@echo ""
	@echo "=================================="
	@echo "All BinaryIO demos completed!"

# Run individual files
run-copy: build
	@echo "Running Copy.java..."
	@rm -f copied-target.txt
	@$(JAVA) -cp $(CLASS_DIR) $(PACKAGE_BASE).Copy source.txt copied-target.txt

run-testdatastream: build
	@echo "Running TestDataStream.java..."
	@$(JAVA) -cp $(CLASS_DIR) $(PACKAGE_BASE).TestDataStream

run-testfilestream: build
	@echo "Running TestFileStream.java..."
	@$(JAVA) -cp $(CLASS_DIR) $(PACKAGE_BASE).TestFileStream

run-testobjectinputstream: build
	@echo "Running TestObjectInputStream.java..."
	@$(JAVA) -cp $(CLASS_DIR) $(PACKAGE_BASE).TestObjectInputStream

run-testobjectoutputstream: build
	@echo "Running TestObjectOutputStream.java..."
	@$(JAVA) -cp $(CLASS_DIR) $(PACKAGE_BASE).TestObjectOutputStream

run-testobjectstreamforarray: build
	@echo "Running TestObjectStreamForArray.java..."
	@$(JAVA) -cp $(CLASS_DIR) $(PACKAGE_BASE).TestObjectStreamForArray

run-testrandomaccessfile: build
	@echo "Running TestRandomAccessFile.java..."
	@$(JAVA) -cp $(CLASS_DIR) $(PACKAGE_BASE).TestRandomAccessFile

# Clean compiled files and generated files
clean:
	@echo "Cleaning compiled files and generated files..."
	@rm -rf $(CLASS_DIR)
	@rm -f copied-target.txt
	@echo "Clean complete!"

# Show help
help:
	@echo "Available targets:"
	@echo "  build                    - Compile all Java files"
	@echo "  run                      - Build and run all files at once"
	@echo "  run-copy                 - Run Copy.java only"
	@echo "  run-testdatastream       - Run TestDataStream.java only"
	@echo "  run-testfilestream       - Run TestFileStream.java only"
	@echo "  run-testobjectinputstream - Run TestObjectInputStream.java only"
	@echo "  run-testobjectoutputstream - Run TestObjectOutputStream.java only"
	@echo "  run-testobjectstreamforarray - Run TestObjectStreamForArray.java only"
	@echo "  run-testrandomaccessfile - Run TestRandomAccessFile.java only"
	@echo "  clean                    - Remove compiled class files"
	@echo "  help                     - Show this help message"

.PHONY: all build run clean help run-copy run-testdatastream run-testfilestream run-testobjectinputstream run-testobjectoutputstream run-testobjectstreamforarray run-testrandomaccessfile
