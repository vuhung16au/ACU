# Makefile for Sorting Algorithm Comparison Project

.PHONY: all build run analyze visualize docs clean

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
all: build run analyze visualize docs

# Example build rules (extend for all algorithms/languages)
build: $(C_BUBBLE) $(CPP_BUBBLE) $(JAVA_BUBBLE)

$(C_BUBBLE): $(SRC_DIR)/bubble_sort.c
	gcc -O2 -o $@ $<

$(CPP_BUBBLE): $(SRC_DIR)/bubble_sort.cpp
	g++ -O2 -std=c++17 -o $@ $<

$(JAVA_BUBBLE): $(SRC_DIR)/BubbleSort.java
	javac $<

# Run all benchmarks (replace with your actual script/command)
run:
	bash $(SCRIPTS_DIR)/run_comparison.sh

# Analyze results
analyze:
	python3 $(SCRIPTS_DIR)/analyze_results.py

# Visualize results
visualize:
	python3 $(SCRIPTS_DIR)/visualize_results.py

# Generate datasets
.PHONY: generate-data
generate-data:
	python3 $(SCRIPTS_DIR)/generate_data.py

# Update documentation
.PHONY: docs
docs:
	python3 $(SCRIPTS_DIR)/update_docs.py

# Run all scripts in sequence: generate data, run, analyze, visualize, docs
.PHONY: all-scripts
all-scripts: generate-data run analyze visualize docs

# Run main shell script (run-all.sh)
.PHONY: shell
shell:
	bash $(SCRIPTS_DIR)/run-all.sh

# Run performance comparison (run_comparison.sh)
.PHONY: comparison
comparison:
	bash $(SCRIPTS_DIR)/run_comparison.sh

# Run multi-size comparison (run_multi_size_comparison.sh)
.PHONY: multi-size-comparison
multi-size-comparison:
	bash $(SCRIPTS_DIR)/run_multi_size_comparison.sh

# Run complete study (run_complete_study.sh)
.PHONY: complete-study
complete-study:
	bash $(SCRIPTS_DIR)/run_complete_study.sh

# Set up Python virtual environment and install requirements for visualization
.PHONY: venv
venv:
	python3 -m venv venv
	source venv/bin/activate && pip install -r requirements.txt

# Clean build artifacts, results, analysis, and documentation images
clean:
	rm -f $(C_BUBBLE) $(CPP_BUBBLE) $(SRC_DIR)/*.class
	rm -f $(RESULTS_DIR)/*.txt $(ANALYSIS_DIR)/*.txt $(DOCS_DIR)/*.png
	rm -f $(DATASETS_DIR)/*.txt

# Add more rules for other algorithms/languages as needed
# You can also add dataset generation, aggregation, etc.
