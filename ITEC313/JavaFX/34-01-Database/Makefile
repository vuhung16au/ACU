# Java compiler and runtime
JAVAC=javac
JAVA=java
SRC_DIR=src/main/java/com/acu/javafx/database
BIN_DIR=target/classes
JAVA_FILES=$(SRC_DIR)/Employee.java \
           $(SRC_DIR)/DatabaseManager.java \
           $(SRC_DIR)/EmployeeDatabaseApp.java

CLASSES=Employee DatabaseManager EmployeeDatabaseApp

# JavaFX setup
JAVAFX_MODULES=javafx.controls,javafx.fxml,javafx.graphics,javafx.base
JAVAFX_PATH=$(shell mvn dependency:build-classpath -Dmdep.outputFile=cp.txt > /dev/null 2>&1 && cat cp.txt)

.PHONY: all clean build run run_EmployeeDatabaseApp

all: build

build:
	@mkdir -p $(BIN_DIR)
	$(JAVAC) -d $(BIN_DIR) -cp $(JAVAFX_PATH) $(JAVA_FILES)

clean:
	rm -rf $(BIN_DIR) cp.txt employee.sqlite

# Run the main JavaFX application
run: run_EmployeeDatabaseApp

run_EmployeeDatabaseApp: build
	$(JAVA) --module-path $(JAVAFX_PATH) --add-modules $(JAVAFX_MODULES) -cp $(BIN_DIR) com.acu.javafx.database.EmployeeDatabaseApp

# Database operations
init-db: build
	$(JAVA) -cp $(BIN_DIR):$(JAVAFX_PATH) com.acu.javafx.database.DatabaseManager

# Clean database
clean-db:
	rm -f employee.sqlite

# Show database info
db-info:
	@echo "Database file: employee.sqlite"
	@if [ -f employee.sqlite ]; then \
		echo "Database exists"; \
		ls -la employee.sqlite; \
	else \
		echo "Database does not exist"; \
	fi

# Help target
help:
	@echo "Available targets:"
	@echo "  build              - Compile the Java source files"
	@echo "  clean              - Remove compiled classes and database"
	@echo "  run                - Run the JavaFX application"
	@echo "  run_EmployeeDatabaseApp - Run the main application"
	@echo "  init-db            - Initialize database (deprecated, use run instead)"
	@echo "  clean-db           - Remove database file"
	@echo "  db-info            - Show database file information"
	@echo "  help               - Show this help message"
