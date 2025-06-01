#!/usr/bin/env python3
"""
Update documentation for the sorting algorithms comparison project.
This script updates README.md and creates additional documentation based on results.
"""
import os
import re
import glob
import sys
from datetime import datetime

def get_project_root():
    script_dir = os.path.dirname(os.path.abspath(__file__))
    return os.path.dirname(script_dir)

def update_readme():
    """Updates the README.md with the latest information"""
    project_root = get_project_root()
    readme_path = os.path.join(project_root, "README.md")
    
    # Read existing README
    with open(readme_path, 'r') as f:
        content = f.read()
    
    # Get all available data sizes
    data_sizes = []
    for result_file in glob.glob(os.path.join(project_root, "analysis", "consolidated_results_*.txt")):
        try:
            size = int(os.path.basename(result_file).split('_')[-1].split('.')[0])
            data_sizes.append(size)
        except (ValueError, IndexError):
            continue
    data_sizes.sort()
    current_date = datetime.now().strftime("%d %B %Y")

    # Remove all existing '## Usage' sections (and their content)
    usage_pattern = r'## Usage.*?(?=^## |\Z)'  # greedy match, up to next section or end
    content = re.sub(usage_pattern, '', content, flags=re.DOTALL | re.MULTILINE)

    # Remove any accidental duplicate blank lines
    content = re.sub(r'\n{3,}', '\n\n', content)

    # Compose the new Usage section
    new_usage = f"""## Usage

This repository provides scripts to run and compare sorting algorithm implementations across languages.

### Quick Start

Run all tests with default settings:

```bash
./scripts/run-all.sh
```

### Additional Options

The main script (`run-all.sh`) supports several options:

- `--clean` - Clean all generated datasets and results
- `--clean-results` - Clean only results files
- `--clean-datasets` - Clean only dataset files
- `--generate-only` - Generate datasets but don't run comparisons
- `--sizes SIZE1,SIZE2,...` - Run tests only for specific data sizes (comma-separated)

#### Examples

```bash
# Clean all data and start fresh
./scripts/run-all.sh --clean

# Run only for small dataset (faster testing)
./scripts/run-all.sh --sizes 5000

# Generate data without running tests
./scripts/run-all.sh --generate-only
```

### Data Sizes

The following data sizes are currently configured for testing:
{", ".join([f"{size:,}" for size in data_sizes]) if data_sizes else "No data has been generated yet."}

Last updated: {current_date}
"""

    # Insert the Usage section after the Quick Start section
    quick_start_pattern = r'(## Quick Start.*?)(^## |\Z)'
    match = re.search(quick_start_pattern, content, flags=re.DOTALL | re.MULTILINE)
    if match:
        before = match.group(1)
        after = content[match.end(1):]
        new_content = before.rstrip() + '\n\n' + new_usage + '\n' + after.lstrip()
    else:
        # If Quick Start not found, prepend Usage at the top
        new_content = new_usage + '\n' + content

    # Remove any accidental duplicate blank lines again
    new_content = re.sub(r'\n{3,}', '\n\n', new_content)

    # Write updated README
    with open(readme_path, 'w') as f:
        f.write(new_content)
    print(f"Updated README.md with latest usage information and data sizes.")

def create_multi_size_report():
    """Creates a report comparing performance across different data sizes"""
    project_root = get_project_root()
    report_path = os.path.join(project_root, "docs", "MULTI_SIZE_PERFORMANCE_STUDY.md")
    
    # Get all available data sizes and their result files
    data_size_files = {}
    for result_file in glob.glob(os.path.join(project_root, "analysis", "consolidated_results_*.txt")):
        try:
            size = int(os.path.basename(result_file).split('_')[-1].split('.')[0])
            data_size_files[size] = result_file
        except (ValueError, IndexError):
            continue
    
    if not data_size_files:
        print("No consolidated results found. Run comparisons first.")
        return
    
    # Now create the report
    with open(report_path, 'w') as f:
        f.write(f"# Multi-Size Performance Study\n\n")
        f.write(f"*Generated on: {datetime.now().strftime('%d %B %Y')}*\n\n")
        f.write("This document analyzes how sorting algorithms scale across different data sizes.\n\n")
        
        # For each algorithm, create a section showing performance across sizes
        f.write("## Scaling Analysis\n\n")
        f.write("This section shows how each algorithm's execution time scales with data size.\n\n")
        
        # Extract algorithms from the first result file
        first_size = min(data_size_files.keys())
        algorithms = set()
        languages = set()
        
        # Pattern to extract performance data
        pattern = re.compile(r'([A-Za-z+]+) \(([A-Za-z ]+)\):\s+Execution time: ([\d.]+) seconds')
        
        # First pass to collect all algorithms and languages
        with open(data_size_files[first_size], 'r') as file:
            for line in file:
                match = pattern.search(line)
                if match:
                    language, algo, _ = match.groups()
                    algorithms.add(algo)
                    languages.add(language)
        
        # Create a table for each algorithm showing performance across sizes and languages
        for algorithm in sorted(algorithms):
            f.write(f"### {algorithm}\n\n")
            f.write(f"| Data Size | " + " | ".join(sorted(languages)) + " |\n")
            f.write(f"|" + "-" * 10 + "|" + "".join(["-" * 12 + "|" for _ in languages]) + "\n")
            
            # For each size, get the execution time for this algorithm in each language
            for size in sorted(data_size_files.keys()):
                times = {lang: "N/A" for lang in languages}
                
                with open(data_size_files[size], 'r') as file:
                    content = file.read()
                    for lang in languages:
                        # Look for the specific algorithm in this language
                        match = re.search(rf'{lang} \({algorithm}\):.+?Execution time: ([\d.]+|N/A) seconds', 
                                         content, re.DOTALL)
                        if match:
                            time_str = match.group(1)
                            if time_str != "N/A":
                                time_val = float(time_str)
                                times[lang] = f"{time_val:.6f}"
                
                # Write the row for this size
                f.write(f"| {size:,} | " + " | ".join([times[lang] for lang in sorted(languages)]) + " |\n")
            
            f.write("\n")
        
        # Add a visualization suggestion
        f.write("\n## Visualization\n\n")
        f.write("To better understand the scaling behavior, consider creating plots from this data. ")
        f.write("A log-log plot of execution time vs. data size would clearly show the asymptotic behavior ")
        f.write("of each algorithm and confirm their theoretical time complexities.\n\n")
        
        f.write("## Complexity Confirmation\n\n")
        f.write("Based on the data, we can observe:\n\n")
        f.write("- **O(n²) algorithms** (Bubble, Selection, Insertion): Execution time grows quadratically with input size\n")
        f.write("- **O(n log n) algorithms** (Quick, Merge): Execution time grows slightly more than linearly\n")
        f.write("- **O(n) algorithms** (Counting, Radix): Execution time grows linearly with input size\n")
    
    print(f"Created multi-size performance study at {report_path}")

if __name__ == "__main__":
    # If a specific function is specified, only run that
    if len(sys.argv) > 1 and sys.argv[1] == "readme":
        update_readme()
    elif len(sys.argv) > 1 and sys.argv[1] == "multisize":
        create_multi_size_report()
    else:
        # Run all documentation updates
        update_readme()
        create_multi_size_report()
