# Makefile for Sorting Algorithm Comparison Project

.PHONY: all build run analyze visualize docs clean venv venv-clean data scripts

# Directories
SRC_DIR := src
SCRIPTS_DIR := scripts
RESULTS_DIR := results
ANALYSIS_DIR := analysis
DOCS_DIR := docs
DATASETS_DIR := datasets

# Executables (add more as needed)
C_BUBBLE := $(SRC_DIR)/bubble_sort_c
CPP_BUBBLE := $(SRC_DIR)/bubble_sort_cpp
JAVA_BUBBLE := $(SRC_DIR)/BubbleSort.class

# Main targets
all: scripts

# Set up Python virtual environment and install requirements
venv:
	python3 -m venv .venv
	. .venv/bin/activate && pip install -r requirements.txt

# Clean Python virtual environment
venv-clean:
	rm -rf .venv

# Generate test datasets
data: venv
	python3 $(SCRIPTS_DIR)/generate_data.py

# Build sorting algorithm implementations
build: $(C_BUBBLE) $(CPP_BUBBLE) $(JAVA_BUBBLE)

$(C_BUBBLE): $(SRC_DIR)/bubble_sort.c
	gcc -O2 -o $@ $<

$(CPP_BUBBLE): $(SRC_DIR)/bubble_sort.cpp
	g++ -O2 -std=c++17 -o $@ $<

$(JAVA_BUBBLE): $(SRC_DIR)/BubbleSort.java
	javac $<

# Run performance comparisons
run: data build
	python3 $(SCRIPTS_DIR)/run_comparison.py

# Analyze results
analyze: run
	mkdir -p $(ANALYSIS_DIR)
	python3 $(SCRIPTS_DIR)/analyze_results.py

# Generate JSON results from analysis
generate-json-results: 
	python3 $(SCRIPTS_DIR)/generate-json-results.py

# Visualize results
visualize: analyze
	mkdir -p $(DOCS_DIR)
	python3 $(SCRIPTS_DIR)/visualize_results.py

# Update documentation
docs: visualize
	python3 $(SCRIPTS_DIR)/update_docs.py

# Run all scripts in sequence with proper dependencies
scripts: venv data build run analyze visualize docs

# Clean build artifacts, results, analysis, and documentation images
clean:
	rm -f $(C_BUBBLE) $(CPP_BUBBLE) $(SRC_DIR)/*.class
	rm -f $(RESULTS_DIR)/*.txt $(RESULTS_DIR)/*.md $(ANALYSIS_DIR)/*.txt $(DOCS_DIR)/*.png
	rm -f $(DATASETS_DIR)/*.txt
	rm -f images/*.png


# Additional targets for specific comparisons
.PHONY: comparison multi-size-comparison complete-study

# Run performance comparison
comparison: run

# Run multi-size comparison
multi-size-comparison: data build
	bash $(SCRIPTS_DIR)/run_multi_size_comparison.sh

# Run complete study
complete-study: data build
	bash $(SCRIPTS_DIR)/run_complete_study.sh
