# Makefile for Python vs Cython Radix Sort Performance Test

# Python executable in virtual environment
PYTHON = .venv/bin/python
PIP = .venv/bin/pip

# Default target
.PHONY: all
all: setup random random10m build test

# Setup virtual environment and install dependencies
.PHONY: setup
setup:
	@echo "Setting up virtual environment..."
	/opt/homebrew/bin/python3.14 -m venv .venv
	$(PIP) install --upgrade pip
	$(PIP) install -r requirements.txt
	@echo "Virtual environment setup complete!"

# Generate random list with 1,000,000 numbers
.PHONY: random
random:
	@echo "Generating random list with 1,000,000 numbers..."
	$(PYTHON) generate_random_list.py -n 1000000 -o random_list_1000000.txt
	@echo "Generated random_list_1000000.txt"

# Generate random list with 10,000,000 numbers
.PHONY: random10m
random10m:
	@echo "Generating random list with 10,000,000 numbers..."
	$(PYTHON) generate_random_list.py -n 10000000 -o random_list_10000000.txt
	@echo "Generated random_list_10000000.txt"

# Build Cython extension
.PHONY: build
build:
	@echo "Building Cython extension..."
	$(PYTHON) setup.py build_ext --inplace
	@echo "Cython extension built successfully!"

# Run performance tests
.PHONY: test
test: build
	@echo "Running performance tests..."
	$(PYTHON) radix_sort_test.py
	@echo "Performance tests completed!"

# Test with 1,000 elements
.PHONY: test1k
test1k: build
	@echo "Generating 1,000 random numbers..."
	$(PYTHON) generate_random_list.py -n 1000 -o test_1k.txt
	@echo "Running performance test with 1,000 elements..."
	$(PYTHON) -c "import sys; sys.path.append('.'); from radix_sort_test import test_performance; test_performance('test_1k.txt')"
	@echo "Test completed!"

# Test with 10,000 elements
.PHONY: test10k
test10k: build
	@echo "Generating 10,000 random numbers..."
	$(PYTHON) generate_random_list.py -n 10000 -o test_10k.txt
	@echo "Running performance test with 10,000 elements..."
	$(PYTHON) -c "import sys; sys.path.append('.'); from radix_sort_test import test_performance; test_performance('test_10k.txt')"
	@echo "Test completed!"

# Test with 100,000 elements
.PHONY: test100k
test100k: build
	@echo "Generating 100,000 random numbers..."
	$(PYTHON) generate_random_list.py -n 100000 -o test_100k.txt
	@echo "Running performance test with 100,000 elements..."
	$(PYTHON) -c "import sys; sys.path.append('.'); from radix_sort_test import test_performance; test_performance('test_100k.txt')"
	@echo "Test completed!"

# Test with 1,000,000 elements
.PHONY: test1m
test1m: build
	@echo "Generating 1,000,000 random numbers..."
	$(PYTHON) generate_random_list.py -n 1000000 -o test_1m.txt
	@echo "Running performance test with 1,000,000 elements..."
	$(PYTHON) -c "import sys; sys.path.append('.'); from radix_sort_test import test_performance; test_performance('test_1m.txt')"
	@echo "Test completed!"

# Generate performance comparison graph
.PHONY: graph
graph: build
	@echo "Generating performance comparison graph..."
	@echo "This will test performance with 1K, 10K, 100K, 250K, 500K, and 1M elements..."
	$(PYTHON) performance_graph.py
	@echo "Graph generation completed!"
	@echo "Generated files:"
	@echo "  - python_vs_cython.png (performance comparison)"
	@echo "  - cython_speedup.png (speedup visualization)"

# Clean generated files
.PHONY: clean
clean:
	@echo "Cleaning generated files..."
	rm -f random_list_*.txt
	rm -f test_*.txt
	rm -f graph_test_*.txt
	rm -f *.png
	rm -f *.so
	rm -f *.c
	rm -f radix_sort_cy.c
	rm -rf build/
	rm -rf __pycache__/
	rm -rf .venv/
	@echo "Clean completed!"

# Help target
.PHONY: help
help:
	@echo "Available targets:"
	@echo "  setup     - Create virtual environment and install dependencies"
	@echo "  random    - Generate random list with 1,000,000 numbers"
	@echo "  random10m - Generate random list with 10,000,000 numbers"
	@echo "  build     - Build Cython extension"
	@echo "  test      - Run performance tests with existing data files"
	@echo "  test1k    - Test performance with 1,000 elements"
	@echo "  test10k   - Test performance with 10,000 elements"
	@echo "  test100k  - Test performance with 100,000 elements"
	@echo "  test1m    - Test performance with 1,000,000 elements"
	@echo "  graph     - Generate performance comparison graphs"
	@echo "  clean     - Remove all generated files"
	@echo "  help      - Show this help message"
