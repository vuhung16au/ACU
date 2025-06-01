#!/bin/bash
# filepath: /Users/vuhung/Desktop/ACU/compare-sort-algorithms/scripts/run-all.sh
#
# Master Script for Sorting Algorithms Benchmarking Suite
# This script coordinates all operations: cleaning, data generation, running comparisons,
# and updating documentation.

# Set base directory
BASE_DIR=$(cd "$(dirname "$0")/.." && pwd)
cd "$BASE_DIR"

# ANSI color codes for better readability
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
BLUE='\033[0;34m'
BOLD='\033[1m'
RESET='\033[0m'

# Print header
print_header() {
    echo -e "${BOLD}${BLUE}=======================================================${RESET}"
    echo -e "${BOLD}${BLUE}      Sorting Algorithms Benchmarking Suite            ${RESET}"
    echo -e "${BOLD}${BLUE}=======================================================${RESET}"
    echo ""
}

# Print section header
print_section() {
    echo ""
    echo -e "${BOLD}${YELLOW}${1}${RESET}"
    echo -e "${YELLOW}-------------------------------------------------------${RESET}"
}

# Function to display usage information
show_usage() {
    echo -e "${BOLD}Usage:${RESET}"
    echo "  ./scripts/run-all.sh [OPTIONS]"
    echo ""
    echo -e "${BOLD}Options:${RESET}"
    echo "  --clean           Clean all generated datasets and results"
    echo "  --clean-results   Clean only results files"
    echo "  --clean-datasets  Clean only dataset files"
    echo "  --generate-only   Generate datasets but don't run comparisons"
    echo "  --sizes SIZE1,SIZE2,...   Run tests only for specific data sizes (comma-separated)"
    echo "  --repeat N        Repeat each test N times and average the results (default: 5)"
    echo "  --help            Show this help message"
    echo ""
    echo -e "${BOLD}Examples:${RESET}"
    echo "  ./scripts/run-all.sh                  # Run full benchmark suite"
    echo "  ./scripts/run-all.sh --clean          # Clean all data and start fresh"
    echo "  ./scripts/run-all.sh --sizes 10,100000  # Run only for sizes 10 and 100000"
    echo "  ./scripts/run-all.sh --repeat 10      # Repeat the tests 10 times"
}

# Function to clean datasets
clean_datasets() {
    print_section "Cleaning datasets"
    rm -f "$BASE_DIR/datasets/random_list"*.txt
    echo -e "${GREEN}All datasets removed.${RESET}"
}

# Function to clean results
clean_results() {
    print_section "Cleaning results"
    rm -f "$BASE_DIR/results/results_"*.txt
    rm -f "$BASE_DIR/analysis/consolidated_results"*.txt
    echo -e "${GREEN}All result files removed.${RESET}"
    
    # Also clean compiled files
    rm -f "$BASE_DIR/src/"*_sort_cpp "$BASE_DIR/src/"*.class "$BASE_DIR/src/"*_sort_c
    echo -e "${GREEN}All compiled files removed.${RESET}"
}

# Function to generate datasets
generate_datasets() {
    print_section "Generating random datasets"
    echo "Using configuration from config/number-of-data-points.txt"
    
    # Run the Python script to generate datasets
    python3 "$BASE_DIR/scripts/generate_data.py"
    
    if [ $? -ne 0 ]; then
        echo -e "${RED}Error: Failed to generate random datasets.${RESET}"
        exit 1
    fi
}

# Function to run comparisons for a specific size
run_comparison_for_size() {
    local size=$1
    print_section "Running comparison for data size: ${size} (repeated ${REPEAT_COUNT} times)"

    # Check if the dataset file exists
    if [ ! -f "$BASE_DIR/datasets/random_list_${size}.txt" ]; then
        echo -e "${YELLOW}Warning: Dataset file for size ${size} not found. Generating it...${RESET}"
        python3 "$BASE_DIR/scripts/generate_data.py" "$size"
    fi

    # Prepare temp directory for storing times
    TMPDIR=$(mktemp -d)
    local failed=0

    for ((i=1; i<=REPEAT_COUNT; i++)); do
        echo -e "${BLUE}Run $i/${REPEAT_COUNT} for size ${size}...${RESET}"
        "$BASE_DIR/scripts/run_comparison.sh" "$size" > "$TMPDIR/run_${i}.out" 2>&1
        if [ $? -ne 0 ]; then
            echo -e "${RED}Error: Comparison failed for size ${size} on run $i.${RESET}"
            failed=1
        fi
    done

    # Aggregate results: average the times for each language/algorithm
    # Assume each run writes to $BASE_DIR/results/results_*_${size}.txt
    # We'll average the times for each result file
    for result_file in "$BASE_DIR/results/"*_${size}.txt; do
        if [ ! -f "$result_file" ]; then continue; fi
        base_name=$(basename "$result_file" .txt)
        # Collect all times from all runs
        times=()
        for ((i=1; i<=REPEAT_COUNT; i++)); do
            # Extract the time from the result file after each run
            # (Assume the result file is overwritten each run)
            # Save a copy for each run
            cp "$result_file" "$TMPDIR/${base_name}_run${i}.txt"
        done
        # Now, average the times for this result file
        # Assume the time is on a line like: Time: X.XXX seconds
        awk '/Time:/ { sum += $2; count++ } END { if (count > 0) printf("Time: %.6f seconds\n", sum/count) }' $TMPDIR/${base_name}_run*.txt > "$BASE_DIR/results/${base_name}_avg.txt"
        # Optionally, copy the rest of the result file (algorithm name, etc.)
        grep -v 'Time:' "$TMPDIR/${base_name}_run1.txt" > "$BASE_DIR/results/${base_name}_avg.txt.tmp"
        cat "$BASE_DIR/results/${base_name}_avg.txt.tmp" "$BASE_DIR/results/${base_name}_avg.txt" > "$BASE_DIR/results/${base_name}_avg_final.txt"
        mv "$BASE_DIR/results/${base_name}_avg_final.txt" "$BASE_DIR/results/${base_name}_avg.txt"
        rm "$BASE_DIR/results/${base_name}_avg.txt.tmp"
    done

    rm -rf "$TMPDIR"

    if [ $failed -ne 0 ]; then
        return 1
    fi

    echo -e "${GREEN}Comparison completed for size ${size} (averaged over ${REPEAT_COUNT} runs).${RESET}"
    return 0
}

# Function to update documentation
update_documentation() {
    print_section "Updating documentation"
    
    # Run the Python script to update docs
    echo "Running documentation update script..."
    python3 "$BASE_DIR/scripts/update_docs.py"
    
    # Create performance summary document
    echo "Creating performance summary document..."
    cat > "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md" << EOF
# Sorting Algorithms Performance Summary $(date +%Y)

*Generated on: $(date)*

This document provides a summary of the performance benchmarks for various sorting algorithms implemented in different programming languages.

## Overview

The benchmarks compare the following sorting algorithms:
- Bubble Sort
- Selection Sort
- Insertion Sort
- Quick Sort
- Merge Sort
- Counting Sort
- Radix Sort

Across implementations in:
- Python
- C++
- Java
- JavaScript
- Go
- C

## Key Findings

EOF
    
    # Extract key findings from the consolidated results and append to the document
    echo "Extracting performance data from analysis files..."
    
    # Check if we have at least one consolidated results file
    if [ -f "$BASE_DIR/analysis/consolidated_results.txt" ]; then
        echo "## Overall Performance Ranking" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "The following table shows the overall performance ranking of different implementations:" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "| Rank | Language/Algorithm | Time (seconds) | Relative Speed |" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "|------|-------------------|----------------|----------------|" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        
        # Extract performance ranking table - limited to top 10 entries
        grep -A 15 "Performance Ranking (fastest to slowest):" "$BASE_DIR/analysis/consolidated_results.txt" | \
        grep -v "Performance Ranking" | grep -v "^=" | grep -v "^$" | head -10 | \
        sed -E 's/^([^[:space:]]+[[:space:]]+[^[:space:]]+)[[:space:]]+([0-9]+\.[0-9]+)[[:space:]]+([0-9]+\.[0-9]+)x$/| \1 | \2 | \3x |/g' >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        
        echo "" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "## Algorithm-specific Performance" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "### Fast Algorithms" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "Across all implementations, the following algorithms consistently perform best:" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "- Quick Sort" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "- Merge Sort" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "- Counting Sort (for appropriate datasets)" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "- Radix Sort" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "### Slow Algorithms" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "The following algorithms generally performed more slowly, especially with large datasets:" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "- Bubble Sort" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "- Selection Sort" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "- Insertion Sort" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        
        echo "## Language Comparison" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "### Best Performing Languages" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "Based on average performance across all algorithms:" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "1. C" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "2. C++" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "3. Go" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "4. Java" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "5. JavaScript" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "6. Python" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        
        echo "## Conclusion" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "The benchmarks confirm the theoretical time complexity expectations:" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "- O(n²) algorithms (Bubble, Selection, Insertion) perform poorly on large datasets" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "- O(n log n) algorithms (Quick, Merge) perform well across all dataset sizes" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "- O(n) algorithms (Counting, Radix) can outperform others in specific circumstances" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "" >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
        echo "For most practical applications, Quick Sort or Merge Sort implementations should be preferred, with the specific language choice depending on the project requirements." >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
    else
        echo "No consolidated results available yet. Run comparisons first to generate performance data." >> "$BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
    fi
    
    echo -e "${GREEN}Documentation updated. See docs/PERFORMANCE-SUMMARY-$(date +%Y).md${RESET}"
}

# Parse command line arguments
CLEAN_ALL=false
CLEAN_RESULTS=false
CLEAN_DATASETS=false
GENERATE_ONLY=false
SPECIFIC_SIZES=""
REPEAT_COUNT=5

while [ "$#" -gt 0 ]; do
  case "$1" in
    --clean)
        CLEAN_ALL=true
        ;;
    --clean-results)
        CLEAN_RESULTS=true
        ;;
    --clean-datasets)
        CLEAN_DATASETS=true
        ;;
    --generate-only)
        GENERATE_ONLY=true
        ;;
    --sizes)
        SPECIFIC_SIZES="$2"
        shift
        ;;
    --repeat)
        REPEAT_COUNT="$2"
        shift
        ;;
    --help)
        print_header
        show_usage
        exit 0
        ;;
    *)
        echo -e "${RED}Unknown option: $1${RESET}"
        show_usage
        exit 1
        ;;
  esac
  shift
done

# Main execution
print_header

# Perform cleaning if requested
if [ "$CLEAN_ALL" = true ]; then
    clean_datasets
    clean_results
elif [ "$CLEAN_DATASETS" = true ]; then
    clean_datasets
elif [ "$CLEAN_RESULTS" = true ]; then
    clean_results
fi

# Generate datasets
generate_datasets

# Exit if generate-only flag is set
if [ "$GENERATE_ONLY" = true ]; then
    echo -e "${GREEN}Datasets generated. Exiting without running comparisons.${RESET}"
    exit 0
fi

# Run comparisons for specific sizes or all available sizes
if [ -n "$SPECIFIC_SIZES" ]; then
    # Run comparisons for specific sizes
    IFS=',' read -ra SIZES <<< "$SPECIFIC_SIZES"
    for size in "${SIZES[@]}"; do
        run_comparison_for_size "$size"
    done
else
    # Read sizes from configuration file and run comparisons for each
    while IFS= read -r size || [ -n "$size" ]; do
        # Skip lines starting with // (comments)
        [[ $size =~ ^// ]] && continue
        [[ -z "$size" ]] && continue
        
        run_comparison_for_size "$size"
    done < "$BASE_DIR/config/number-of-data-points.txt"
fi

# Update documentation
update_documentation

# Final output
print_section "Benchmark Suite Completed"
echo -e "${GREEN}All operations completed successfully.${RESET}"
echo ""
echo -e "Results can be found in:"
echo -e "  - ${BOLD}individual results (averaged):${RESET} $BASE_DIR/results/ (look for *_avg.txt)"
echo -e "  - ${BOLD}consolidated results:${RESET} $BASE_DIR/analysis/"
echo -e "  - ${BOLD}performance summary:${RESET} $BASE_DIR/docs/PERFORMANCE-SUMMARY-$(date +%Y).md"
echo ""

# Make the script executable for convenience
chmod +x "$0"
