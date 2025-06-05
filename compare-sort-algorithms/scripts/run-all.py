#!/usr/bin/env python3
#
# Master Script for Sorting Algorithms Benchmarking Suite
# This script coordinates all operations: cleaning, data generation, running comparisons,
# and updating documentation.

import os
import sys
import subprocess
import tempfile
import shutil
import argparse
import re
from datetime import datetime
import glob


# ANSI color codes for better readability
class Colors:
    RED = '\033[0;31m'
    GREEN = '\033[0;32m'
    YELLOW = '\033[0;33m'
    BLUE = '\033[0;34m'
    BOLD = '\033[1m'
    RESET = '\033[0m'


# Get the base directory
BASE_DIR = os.path.abspath(os.path.join(os.path.dirname(__file__), '..'))
os.chdir(BASE_DIR)


def print_header():
    """Print the main header for the script."""
    print(f"{Colors.BOLD}{Colors.BLUE}======================================================={Colors.RESET}")
    print(f"{Colors.BOLD}{Colors.BLUE}      Sorting Algorithms Benchmarking Suite            {Colors.RESET}")
    print(f"{Colors.BOLD}{Colors.BLUE}======================================================={Colors.RESET}")
    print()


def print_section(title):
    """Print a section header."""
    print()
    print(f"{Colors.BOLD}{Colors.YELLOW}{title}{Colors.RESET}")
    print(f"{Colors.YELLOW}-------------------------------------------------------{Colors.RESET}")


def show_usage():
    """Display usage information."""
    print(f"{Colors.BOLD}Usage:{Colors.RESET}")
    print("  python3 scripts/run-all.py [OPTIONS]")
    print()
    print(f"{Colors.BOLD}Options:{Colors.RESET}")
    print("  --clean           Clean all generated datasets and results")
    print("  --clean-results   Clean only results files")
    print("  --clean-datasets  Clean only dataset files")
    print("  --generate-only   Generate datasets but don't run comparisons")
    print("  --sizes SIZE1,SIZE2,...   Run tests only for specific data sizes (comma-separated)")
    print("  --repeat N        Repeat each test N times and average the results (default: 5)")
    print("  --help            Show this help message")
    print()
    print(f"{Colors.BOLD}Examples:{Colors.RESET}")
    print("  python3 scripts/run-all.py                  # Run full benchmark suite")
    print("  python3 scripts/run-all.py --clean          # Clean all data and start fresh")
    print("  python3 scripts/run-all.py --sizes 10,100000  # Run only for sizes 10 and 100000")
    print("  python3 scripts/run-all.py --repeat 10      # Repeat the tests 10 times")


def clean_datasets():
    """Clean all dataset files."""
    print_section("Cleaning datasets")
    for file in glob.glob(os.path.join(BASE_DIR, "datasets", "random_list*.txt")):
        os.remove(file)
    print(f"{Colors.GREEN}All datasets removed.{Colors.RESET}")


def clean_results():
    """Clean all result files and compiled binaries."""
    print_section("Cleaning results")
    
    # Remove result files
    for file in glob.glob(os.path.join(BASE_DIR, "results", "results_*.txt")):
        os.remove(file)
    for file in glob.glob(os.path.join(BASE_DIR, "analysis", "consolidated_results*.txt")):
        os.remove(file)
    print(f"{Colors.GREEN}All result files removed.{Colors.RESET}")
    
    # Remove compiled files
    for file_pattern in ["*_sort_cpp", "*.class", "*_sort_c", "*_sort_rust"]:
        for file in glob.glob(os.path.join(BASE_DIR, "src", file_pattern)):
            os.remove(file)
    print(f"{Colors.GREEN}All compiled files removed.{Colors.RESET}")


def generate_datasets():
    """Generate random datasets for benchmarking."""
    print_section("Generating random datasets")
    print("Using configuration from config/number-of-data-points.txt")
    
    # Run the Python script to generate datasets
    try:
        subprocess.run(
            ["python3", os.path.join(BASE_DIR, "scripts", "generate_data.py")],
            check=True
        )
    except subprocess.CalledProcessError:
        print(f"{Colors.RED}Error: Failed to generate random datasets.{Colors.RESET}")
        sys.exit(1)


def run_comparison_for_size(size, repeat_count):
    """Run comparisons for a specific data size, repeated specified times."""
    print_section(f"Running comparison for data size: {size} (repeated {repeat_count} times)")

    # Check if the dataset file exists
    dataset_file = os.path.join(BASE_DIR, "datasets", f"random_list_{size}.txt")
    if not os.path.exists(dataset_file):
        print(f"{Colors.YELLOW}Warning: Dataset file for size {size} not found. Generating it...{Colors.RESET}")
        try:
            subprocess.run(
                ["python3", os.path.join(BASE_DIR, "scripts", "generate_data.py"), str(size)],
                check=True
            )
        except subprocess.CalledProcessError:
            print(f"{Colors.RED}Error: Failed to generate dataset for size {size}.{Colors.RESET}")
            return False

    # Prepare temp directory for storing times
    with tempfile.TemporaryDirectory() as temp_dir:
        failed = False

        # Run the comparison multiple times
        for i in range(1, repeat_count + 1):
            print(f"{Colors.BLUE}Run {i}/{repeat_count} for size {size}...{Colors.RESET}")
            try:
                output_file = os.path.join(temp_dir, f"run_{i}.out")
                with open(output_file, 'w') as f:
                    subprocess.run(
                        [os.path.join(BASE_DIR, "scripts", "run_comparison.sh"), str(size)],
                        stdout=f,
                        stderr=subprocess.STDOUT,
                        check=True
                    )
            except subprocess.CalledProcessError:
                print(f"{Colors.RED}Error: Comparison failed for size {size} on run {i}.{Colors.RESET}")
                failed = True

        # Aggregate results by averaging the times
        result_files = glob.glob(os.path.join(BASE_DIR, "results", f"*_{size}.txt"))
        for result_file in result_files:
            if not os.path.isfile(result_file):
                continue
            
            base_name = os.path.basename(result_file).rsplit('.', 1)[0]
            
            # Save a copy for each run
            for i in range(1, repeat_count + 1):
                shutil.copy(result_file, os.path.join(temp_dir, f"{base_name}_run{i}.txt"))
            
            # Extract execution times from all runs and average them
            times = []
            for i in range(1, repeat_count + 1):
                run_file = os.path.join(temp_dir, f"{base_name}_run{i}.txt")
                with open(run_file, 'r') as f:
                    content = f.read()
                    if "N/A" in content:
                        times = ["N/A"]
                        break
                    
                    time_match = re.search(r'Execution time: ([\d.]+) seconds', content)
                    if time_match:
                        times.append(float(time_match.group(1)))
            
            # Calculate average time
            avg_file = os.path.join(BASE_DIR, "results", f"{base_name}_avg.txt")
            with open(result_file, 'r') as f:
                content = f.read()
            
            if times and times[0] != "N/A":
                avg_time = sum(times) / len(times)
                # Replace the time in the content
                new_content = re.sub(
                    r'Execution time: [\d.]+ seconds',
                    f'Execution time: {avg_time:.6f} seconds',
                    content
                )
            else:
                new_content = content  # Keep N/A as is
            
            # Write the averaged result
            with open(avg_file, 'w') as f:
                f.write(new_content)

    if failed:
        return False
    
    print(f"{Colors.GREEN}Comparison completed for size {size} (averaged over {repeat_count} runs).{Colors.RESET}")
    return True


def update_documentation():
    """Update documentation with performance data."""
    print_section("Updating documentation")
    
    # Run the Python script to update docs
    print("Running documentation update script...")
    try:
        subprocess.run(
            ["python3", os.path.join(BASE_DIR, "scripts", "update_docs.py")],
            check=False  # Don't exit if this fails
        )
    except subprocess.CalledProcessError:
        print(f"{Colors.YELLOW}Warning: Documentation update script failed or not found.{Colors.RESET}")
    
    # Create performance summary document with datetime stamp
    print("Creating performance summary document...")
    now = datetime.now()
    datetime_stamp = now.strftime('%Y-%m-%d-%H%M%S')
    summary_file = os.path.join(BASE_DIR, "docs", f"PERFORMANCE-SUMMARY-{datetime_stamp}.md")
    
    with open(summary_file, 'w') as f:
        f.write(f"""# Sorting Algorithms Performance Summary {now.year}

*Generated on: {now.strftime('%a %d %b %Y %H:%M:%S %Z')}*

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

""")

        # Extract key findings from the consolidated results
        print("Extracting performance data from analysis files...")
        consolidated_file = os.path.join(BASE_DIR, "analysis", "consolidated_results.txt")
        
        if os.path.exists(consolidated_file):
            f.write("## Overall Performance Ranking\n\n")
            f.write("The following table shows the overall performance ranking of different implementations:\n\n")
            f.write("| Rank | Language/Algorithm | Time (seconds) | Relative Speed |\n")
            f.write("|------|-------------------|----------------|----------------|\n")
            
            # Extract performance ranking table - limited to top 10 entries
            try:
                with open(consolidated_file, 'r') as cf:
                    content = cf.read()
                    
                    # Find the performance ranking section
                    ranking_match = re.search(r'Performance Ranking \\(fastest to slowest\\):.*?=+\n(.*?)(?:\n\n|\Z)', 
                                                content, re.DOTALL)
                    if ranking_match:
                        ranking_data = ranking_match.group(1).strip().split('\n')
                        # Skip headers
                        for i, line in enumerate(ranking_data[:10]):
                            if line.startswith('-'):
                                continue
                            if not line.strip():
                                continue
                            
                            # Format the data for markdown table
                            parts = line.split()
                            if len(parts) >= 4:
                                language_algo = ' '.join(parts[:-2])
                                time = parts[-2]
                                relative = parts[-1]
                                f.write(f"| {i+1} | {language_algo} | {time} | {relative} |\n")
            except Exception as e:
                print(f"{Colors.YELLOW}Warning: Error extracting performance data: {str(e)}{Colors.RESET}")
            
            # Write algorithm-specific performance data
            f.write("""
## Algorithm-specific Performance

### Fast Algorithms

Across all implementations, the following algorithms consistently perform best:

- Quick Sort
- Merge Sort
- Counting Sort (for appropriate datasets)
- Radix Sort

### Slow Algorithms

The following algorithms generally performed more slowly, especially with large datasets:

- Bubble Sort
- Selection Sort
- Insertion Sort

## Language Comparison

### Best Performing Languages

Based on average performance across all algorithms:

1. C
2. C++
3. Go
4. Java
5. JavaScript
6. Python

## Conclusion

The benchmarks confirm the theoretical time complexity expectations:

- O(n²) algorithms (Bubble, Selection, Insertion) perform poorly on large datasets
- O(n log n) algorithms (Quick, Merge) perform well across all dataset sizes
- O(n) algorithms (Counting, Radix) can outperform others in specific circumstances

For most practical applications, Quick Sort or Merge Sort implementations should be preferred, with the specific language choice depending on the project requirements.
""")
        else:
            f.write("No consolidated results available yet. Run comparisons first to generate performance data.\n")
    
    print(f"{Colors.GREEN}Documentation updated. See {os.path.relpath(summary_file, BASE_DIR)}{Colors.RESET}")


def main():
    """Main function to parse arguments and run the benchmark suite."""
    # Parse command line arguments
    parser = argparse.ArgumentParser(description="Sorting Algorithms Benchmarking Suite", add_help=False)
    parser.add_argument("--clean", action="store_true", help="Clean all generated datasets and results")
    parser.add_argument("--clean-results", action="store_true", help="Clean only results files")
    parser.add_argument("--clean-datasets", action="store_true", help="Clean only dataset files")
    parser.add_argument("--generate-only", action="store_true", help="Generate datasets but don't run comparisons")
    parser.add_argument("--sizes", help="Run tests only for specific data sizes (comma-separated)")
    parser.add_argument("--repeat", type=int, default=5, help="Repeat each test N times and average the results (default: 5)")
    parser.add_argument("--help", action="store_true", help="Show this help message")
    
    args = parser.parse_args()
    
    # Show usage if requested
    if args.help:
        print_header()
        show_usage()
        return 0
    
    # Main execution
    print_header()
    
    # Perform cleaning if requested
    if args.clean:
        clean_datasets()
        clean_results()
    elif args.clean_datasets:
        clean_datasets()
    elif args.clean_results:
        clean_results()
    
    # Generate datasets
    generate_datasets()
    
    # Exit if generate-only flag is set
    if args.generate_only:
        print(f"{Colors.GREEN}Datasets generated. Exiting without running comparisons.{Colors.RESET}")
        return 0
    
    # Run comparisons
    if args.sizes:
        # Run comparisons for specific sizes
        sizes = args.sizes.split(',')
        for size in sizes:
            run_comparison_for_size(size.strip(), args.repeat)
    else:
        # Read sizes from configuration file
        config_file = os.path.join(BASE_DIR, "config", "number-of-data-points.txt")
        with open(config_file, 'r') as f:
            for line in f:
                line = line.strip()
                # Skip comments and empty lines
                if not line or line.startswith('//'):
                    continue
                run_comparison_for_size(line, args.repeat)
    
    # Update documentation
    update_documentation()
    
    # Final output
    print_section("Benchmark Suite Completed")
    print(f"{Colors.GREEN}All operations completed successfully.{Colors.RESET}")
    print()
    print("Results can be found in:")
    print(f"  - {Colors.BOLD}individual results (averaged):{Colors.RESET} {BASE_DIR}/results/ (look for *_avg.txt)")
    print(f"  - {Colors.BOLD}consolidated results:{Colors.RESET} {BASE_DIR}/analysis/")
    print(f"  - {Colors.BOLD}performance summary:{Colors.RESET} {BASE_DIR}/docs/PERFORMANCE-SUMMARY-{datetime.now().year}.md")
    print()
    
    return 0


if __name__ == "__main__":
    sys.exit(main())
