# Makefile for Machine Learning Algorithms Project

# Default Python interpreter
PYTHON := python3

# Find all Python files in algorithms directory
PY_FILES := $(shell find algorithms -name "*.py")

# Find all PNG files in algorithms directory
PNG_FILES := $(shell find algorithms -name "*.png")

.PHONY: all clean model-name run

# Run all Python files in algorithms directory
all:
	@echo "Running all Python files..."
	@for file in $(PY_FILES); do \
		echo "Running $$file..."; \
		$(PYTHON) $$file; \
	done

# Run using run-all-py-files.py
run-all:
	@echo "Running all Python files using run-all-py-files.py..."
	@$(PYTHON) run-all-py-files.py

# Clean all PNG files, TSV files, and other generated files
clean:
	@echo "Cleaning generated files..."
	@find algorithms -name "*.png" -type f -delete
	@find algorithms -name "*.tsv" -type f -delete
	@find . -name "catboost_training.json" -type f -delete
	@find . -name "events.out.tfevents*" -type f -delete
	@echo "Cleaned all generated files."

# Run all Python files in a specific model directory
model-name:
	@if [ -z "$(model-name)" ]; then \
		echo "Error: model-name not specified. Usage: make model-name=model_name"; \
		exit 1; \
	fi
	@echo "Running Python files in algorithms/$(model-name)..."
	@if [ -d "algorithms/$(model-name)" ]; then \
		for file in algorithms/$(model-name)/*.py; do \
			if [ -f "$$file" ]; then \
				echo "Running $$file..."; \
				$(PYTHON) $$file; \
			fi \
		done; \
	else \
		echo "Error: Directory algorithms/$(model-name) does not exist."; \
		exit 1; \
	fi 