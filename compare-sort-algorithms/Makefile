# Makefile for Sorting Algorithm Comparison Project

.PHONY: all build run analyze visualize docs clean venv venv-clean data scripts check-php-files

# Directories
SRC_DIR := src
SCRIPTS_DIR := scripts
RESULTS_DIR := results
ANALYSIS_DIR := analysis
DOCS_DIR := docs
DATASETS_DIR := datasets

# PHP executable path
PHP := /opt/homebrew/bin/php

# Executables (add more as needed)
C_BUBBLE := $(SRC_DIR)/bubble_sort_c
CPP_BUBBLE := $(SRC_DIR)/bubble_sort_cpp
JAVA_BUBBLE := $(SRC_DIR)/BubbleSort.class
RUST_BUBBLE := $(SRC_DIR)/bubble_sort_rust
RUST_INSERTION := $(SRC_DIR)/insertion_sort_rust
RUST_SELECTION := $(SRC_DIR)/selection_sort_rust
RUST_MERGE := $(SRC_DIR)/merge_sort_rust
RUST_QUICK := $(SRC_DIR)/quick_sort_rust
RUST_COUNTING := $(SRC_DIR)/counting_sort_rust
RUST_RADIX := $(SRC_DIR)/radix_sort_rust
CSHARP_BUBBLE := $(SRC_DIR)/bubble_sort_cs
CSHARP_INSERTION := $(SRC_DIR)/insertion_sort_cs
CSHARP_SELECTION := $(SRC_DIR)/selection_sort_cs
CSHARP_MERGE := $(SRC_DIR)/merge_sort_cs
CSHARP_QUICK := $(SRC_DIR)/quick_sort_cs
CSHARP_COUNTING := $(SRC_DIR)/counting_sort_cs
CSHARP_RADIX := $(SRC_DIR)/radix_sort_cs

# PHP files (no build needed, just check existence)
PHP_FILES := $(SRC_DIR)/bubble_sort.php \
             $(SRC_DIR)/insertion_sort.php \
             $(SRC_DIR)/selection_sort.php \
             $(SRC_DIR)/merge_sort.php \
             $(SRC_DIR)/quick_sort.php \
             $(SRC_DIR)/counting_sort.php \
             $(SRC_DIR)/radix_sort.php

# Main targets
all: scripts

# Check PHP version
check-php:
	@echo "Checking PHP version..."
	@$(PHP) -v
	@echo "PHP version check completed."

# Check PHP files exist and are executable
check-php-files: $(PHP_FILES)
	@echo "Checking PHP files..."
	@for file in $(PHP_FILES); do \
		if [ ! -f $$file ]; then \
			echo "Error: $$file does not exist"; \
			exit 1; \
		fi; \
		chmod +x $$file; \
	done
	@echo "PHP files check completed."

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
build: $(C_BUBBLE) $(CPP_BUBBLE) $(JAVA_BUBBLE) $(RUST_BUBBLE) $(RUST_INSERTION) $(RUST_SELECTION) $(RUST_MERGE) $(RUST_QUICK) $(RUST_COUNTING) $(RUST_RADIX) $(CSHARP_BUBBLE) $(CSHARP_INSERTION) $(CSHARP_SELECTION) $(CSHARP_MERGE) $(CSHARP_QUICK) $(CSHARP_COUNTING) $(CSHARP_RADIX)

$(C_BUBBLE): $(SRC_DIR)/bubble_sort.c
	gcc -O2 -o $@ $<

$(CPP_BUBBLE): $(SRC_DIR)/bubble_sort.cpp
	g++ -O2 -std=c++17 -o $@ $<

$(JAVA_BUBBLE): $(SRC_DIR)/BubbleSort.java
	javac $<

# Rust compilation rules
$(RUST_BUBBLE): $(SRC_DIR)/bubble_sort.rs
	rustc -O -o $@ $<

$(RUST_INSERTION): $(SRC_DIR)/insertion_sort.rs
	rustc -O -o $@ $<

$(RUST_SELECTION): $(SRC_DIR)/selection_sort.rs
	rustc -O -o $@ $<

$(RUST_MERGE): $(SRC_DIR)/merge_sort.rs
	rustc -O -o $@ $<

$(RUST_QUICK): $(SRC_DIR)/quick_sort.rs
	rustc -O -o $@ $<

$(RUST_COUNTING): $(SRC_DIR)/counting_sort.rs
	rustc -O -o $@ $<

$(RUST_RADIX): $(SRC_DIR)/radix_sort.rs
	rustc -O -o $@ $<

# C# compilation rules
$(CSHARP_BUBBLE) $(CSHARP_INSERTION) $(CSHARP_SELECTION) $(CSHARP_MERGE) $(CSHARP_QUICK) $(CSHARP_COUNTING) $(CSHARP_RADIX):
	dotnet build src/SortAlgorithmsCs/SortAlgorithmsCs.csproj -c Release

# Run performance comparisons
run: data build
	python3 $(SCRIPTS_DIR)/run_comparison.py

# Analyze results
analyse: analyze
analyze: run
	mkdir -p $(ANALYSIS_DIR)
	python3 $(SCRIPTS_DIR)/analyze_results.py

# Generate JSON results from analysis
generate-json-results: 
	python3 $(SCRIPTS_DIR)/generate-json-results.py

# Visualize results
visualise: visualize
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
	rm -f $(SRC_DIR)/*_c $(SRC_DIR)/*_cpp $(SRC_DIR)/*.class
	rm -f $(SRC_DIR)/*_rust
	rm -f $(RESULTS_DIR)/*.txt $(RESULTS_DIR)/*.md $(ANALYSIS_DIR)/*.txt
	rm -f $(DATASETS_DIR)/*.txt
	rm -f images/*.png
	rm -rf $(SRC_DIR)/bin $(SRC_DIR)/obj $(SRC_DIR)/SortAlgorithmsCs/bin $(SRC_DIR)/SortAlgorithmsCs/obj

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


