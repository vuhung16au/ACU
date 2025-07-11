# GNU Makefile for HelloWorld Java Program
# This Makefile provides targets to compile, run, and clean the HelloWorld Java application
# 
# Author: ITEC313 Student
# Date: July 11, 2025
# Course: ITEC313 - Object-Oriented Programming

# ==============================================================================
# VARIABLES
# ==============================================================================

# Java compiler command
JAVAC = javac

# Java runtime command  
JAVA = java

# Source file name (without extension)
MAIN_CLASS = HelloWorld

# Source file with extension
SOURCE_FILE = $(MAIN_CLASS).java

# Compiled class file
CLASS_FILE = $(MAIN_CLASS).class

# Compiler flags
# -cp .: Sets classpath to current directory
# -Xlint: Enable recommended warnings
JAVAC_FLAGS = -cp . -Xlint

# Runtime flags
JAVA_FLAGS = -cp .

# ==============================================================================
# PHONY TARGETS
# ==============================================================================

# Declare phony targets (targets that don't create files with their names)
.PHONY: all compile run clean help

# ==============================================================================
# DEFAULT TARGET
# ==============================================================================

# Default target when 'make' is run without arguments
# This will compile the program
all: compile

# ==============================================================================
# COMPILATION TARGET
# ==============================================================================

# Target to compile the Java source file into bytecode
# This target depends on the source file and will only run if the source
# is newer than the compiled class file (or if class file doesn't exist)
compile: $(CLASS_FILE)

$(CLASS_FILE): $(SOURCE_FILE)
	@echo "Compiling $(SOURCE_FILE)..."
	$(JAVAC) $(JAVAC_FLAGS) $(SOURCE_FILE)
	@echo "Compilation successful! Generated $(CLASS_FILE)"

# ==============================================================================
# EXECUTION TARGET
# ==============================================================================

# Target to run the compiled Java program
# This target depends on the compile target to ensure the program is compiled first
run: compile
	@echo "Running $(MAIN_CLASS)..."
	@echo "=====================================Output====================================="
	$(JAVA) $(JAVA_FLAGS) $(MAIN_CLASS)
	@echo "=============================================================================="

# ==============================================================================
# CLEANUP TARGET
# ==============================================================================

# Target to clean up compiled files
# Removes all .class files in the current directory
clean:
	@echo "Cleaning up compiled files..."
	rm -f *.class
	@echo "Cleanup complete!"

# ==============================================================================
# UTILITY TARGETS
# ==============================================================================

# Target to display help information
help:
	@echo "HelloWorld Java Project Makefile"
	@echo "================================="
	@echo ""
	@echo "Available targets:"
	@echo "  all      - Default target, compiles the program (same as 'compile')"
	@echo "  compile  - Compile the Java source file to bytecode"
	@echo "  run      - Compile and run the HelloWorld program"
	@echo "  clean    - Remove all compiled .class files"
	@echo "  help     - Display this help message"
	@echo ""
	@echo "Usage examples:"
	@echo "  make           # Compile the program"
	@echo "  make compile   # Explicitly compile the program"
	@echo "  make run       # Compile and run the program"
	@echo "  make clean     # Clean up compiled files"
	@echo "  make help      # Show this help"
	@echo ""
	@echo "Requirements:"
	@echo "  - Java Development Kit (JDK) must be installed"
	@echo "  - javac and java commands must be in your PATH"

# Target to check if Java is properly installed
check-java:
	@echo "Checking Java installation..."
	@which javac > /dev/null || (echo "ERROR: javac not found. Please install JDK." && exit 1)
	@which java > /dev/null || (echo "ERROR: java not found. Please install JDK." && exit 1)
	@echo "Java compiler version:"
	@javac -version
	@echo "Java runtime version:"
	@java -version
	@echo "Java installation check complete!"

# ==============================================================================
# ADDITIONAL TARGETS
# ==============================================================================

# Target to display the source code
show-source:
	@echo "Source code of $(SOURCE_FILE):"
	@echo "==============================="
	@cat $(SOURCE_FILE)

# Target to compile with verbose output
compile-verbose: $(SOURCE_FILE)
	@echo "Compiling $(SOURCE_FILE) with verbose output..."
	$(JAVAC) $(JAVAC_FLAGS) -verbose $(SOURCE_FILE)

# Target to run with debugging information
debug: compile
	@echo "Running $(MAIN_CLASS) with debugging information..."
	$(JAVA) $(JAVA_FLAGS) -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000 $(MAIN_CLASS)
