# Makefile for Binary Search Algorithm Demonstrations

# Java Source Files
JAVA_SRC_DIR = src/main/java/com/acu/javafx/binarysearch
JAVA_SRC_FILES = $(shell find $(JAVA_SRC_DIR) -name "*.java")
JAVA_CONSOLE_SRC_FILES = $(shell find $(JAVA_SRC_DIR) -name "*.java" ! -name "BinarySearchApp.java")

# Java Class Files
JAVA_CLASS_FILES = $(JAVA_SRC_FILES:.java=.class)
JAVA_CONSOLE_CLASS_FILES = $(JAVA_CONSOLE_SRC_FILES:.java=.class)

# Java Main Classes (with full package names)
GEEKS_CLASS = com.acu.javafx.binarysearch.Geeks
GEEKS_ITERATIVE_CLASS = com.acu.javafx.binarysearch.GeeksIterativeBinarySearch
GEEKS_RECURSIVE_CLASS = com.acu.javafx.binarysearch.GeeksRecursiveBinarySearch
GEEKS_INBUILD_CLASS = com.acu.javafx.binarysearch.GeeksInBuildBinarySearch
GEEKS_COLLECTIONS_CLASS = com.acu.javafx.binarysearch.GeeksJavaCollectionsBinarySearch
BINARY_SEARCH_TEST_CLASS = com.acu.javafx.binarysearch.BinarySearchTest
BINARY_SEARCH_APP_CLASS = com.acu.javafx.binarysearch.BinarySearchApp

# Java Classpath
JAVA_CLASSPATH = .

# JavaFX Module Path (for JavaFX applications)
JAVAFX_MODULE_PATH = --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml

# Targets 
# - build: build all Java class files
# - run-geeks: run the original Geeks binary search demo
# - run-iterative: run the iterative binary search demo
# - run-recursive: run the recursive binary search demo
# - run-inbuild: run the in-build Arrays.binarySearch demo
# - run-collections: run the Collections.binarySearch demo
# - run-test: run the comprehensive test suite
# - run-app: run the JavaFX binary search application
# - run-all: run all console applications
# - clean: clean all Java class files
# - help: show the help message

.PHONY: build build-all run-geeks run-iterative run-recursive run-inbuild run-collections run-test run-app run-all clean help

# Build target for console applications only
build:
	javac -cp $(JAVA_CLASSPATH) -d . $(JAVA_CONSOLE_SRC_FILES)

# Build target for all applications (including JavaFX)
build-all:
	javac -cp $(JAVA_CLASSPATH) -d . $(JAVA_SRC_FILES)

# Run targets for individual classes
run-geeks: build
	@echo "=== Running Geeks Binary Search Demo ==="
	java -cp $(JAVA_CLASSPATH) $(GEEKS_CLASS)
	@echo ""

run-iterative: build
	@echo "=== Running Iterative Binary Search Demo ==="
	java -cp $(JAVA_CLASSPATH) $(GEEKS_ITERATIVE_CLASS)
	@echo ""

run-recursive: build
	@echo "=== Running Recursive Binary Search Demo ==="
	java -cp $(JAVA_CLASSPATH) $(GEEKS_RECURSIVE_CLASS)
	@echo ""

run-inbuild: build
	@echo "=== Running In-Build Arrays.binarySearch Demo ==="
	java -cp $(JAVA_CLASSPATH) $(GEEKS_INBUILD_CLASS)
	@echo ""

run-collections: build
	@echo "=== Running Collections.binarySearch Demo ==="
	java -cp $(JAVA_CLASSPATH) $(GEEKS_COLLECTIONS_CLASS)
	@echo ""

run-test: build
	@echo "=== Running Binary Search Test Suite ==="
	java -cp $(JAVA_CLASSPATH) $(BINARY_SEARCH_TEST_CLASS)
	@echo ""

# Run JavaFX app target (requires JavaFX SDK)
run-app: build-all
	@echo "=== Running JavaFX Binary Search Application ==="
	@echo "Note: This requires JavaFX SDK to be installed and configured"
	@echo "If you have JavaFX SDK installed, update JAVAFX_MODULE_PATH in Makefile"
	@echo "Current command would be:"
	@echo "java $(JAVAFX_MODULE_PATH) -cp $(JAVA_CLASSPATH) $(BINARY_SEARCH_APP_CLASS)"
	@echo ""
	@echo "For now, running without JavaFX module path (may fail):"
	java -cp $(JAVA_CLASSPATH) $(BINARY_SEARCH_APP_CLASS)

# Run all console applications
run-all: run-geeks run-iterative run-recursive run-inbuild run-collections run-test

# Clean target
clean:
	rm -f $(JAVA_CLASS_FILES)
	rm -f $(JAVA_CONSOLE_CLASS_FILES)

# Help target
help:
	@echo "Available targets:"
	@echo "  build           - Compile console Java applications (excludes JavaFX)"
	@echo "  build-all       - Compile all Java applications (including JavaFX)"
	@echo "  run-geeks       - Run the original Geeks binary search demo"
	@echo "  run-iterative   - Run the iterative binary search demo"
	@echo "  run-recursive   - Run the recursive binary search demo"
	@echo "  run-inbuild     - Run the in-build Arrays.binarySearch demo"
	@echo "  run-collections - Run the Collections.binarySearch demo"
	@echo "  run-test        - Run the comprehensive test suite"
	@echo "  run-app         - Run the JavaFX binary search application"
	@echo "  run-all         - Run all console applications"
	@echo "  clean           - Remove all compiled class files"
	@echo "  help            - Show this help message"
	@echo ""
	@echo "Note: For JavaFX applications, you may need to install JavaFX SDK"
	@echo "and update the JAVAFX_MODULE_PATH variable in this Makefile."