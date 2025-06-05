#!/usr/bin/env python3

import os
import json
import glob
import re
from datetime import datetime
from pathlib import Path

def parse_result_file(file_path):
    """Parse a single result file and extract relevant information."""
    results = []
    current_size = None
    
    with open(file_path, 'r') as f:
        for line in f:
            # Try to match dataset size
            size_match = re.search(r'Dataset size: (\d+)', line)
            if size_match:
                current_size = int(size_match.group(1))
                continue
                
            # Try to match language and algorithm results
            result_match = re.search(r'(\w+)\s+(\w+)\s+sort:\s+([\d.]+)\s+seconds', line)
            if result_match and current_size:
                language, algorithm, time = result_match.groups()
                results.append({
                    'dataset_size': current_size,
                    'language': language.lower(),
                    'algorithm': algorithm.lower(),
                    'time': float(time)
                })
    
    return results

def main():
    # Create results directory if it doesn't exist
    results_dir = Path('results')
    results_dir.mkdir(exist_ok=True)
    
    # Get all analysis result files
    analysis_files = glob.glob('analysis/consolidated_results_*.txt')
    
    if not analysis_files:
        print("No analysis result files found!")
        return
    
    # Parse all result files
    all_results = []
    for file_path in analysis_files:
        results = parse_result_file(file_path)
        all_results.extend(results)
    
    # Sort results by dataset size, language, and algorithm
    all_results.sort(key=lambda x: (x['dataset_size'], x['language'], x['algorithm']))
    
    # Generate timestamp for filename
    timestamp = datetime.now().strftime('%Y%m%d-%H%M%S')
    output_file = results_dir / f'results-{timestamp}.json'
    
    # Write results to JSON file
    with open(output_file, 'w') as f:
        json.dump(all_results, f, indent=2)
    
    print(f"Results written to {output_file}")

if __name__ == '__main__':
    main() 