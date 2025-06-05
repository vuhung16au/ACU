#!/usr/bin/env python3

import os
import json
import glob
import re
from datetime import datetime
from pathlib import Path

def parse_consolidated_result_file(file_path):
    """Parse a consolidated result file and extract relevant information for all languages, including C#."""
    results = []
    data_size = None
    with open(file_path, 'r') as f:
        content = f.read()
        # Extract data size
        size_match = re.search(r'Data Size: ([\d,]+) integers', content)
        if size_match:
            data_size = int(size_match.group(1).replace(',', ''))
        # Find all language/algorithm headers
        lang_algo_header = re.compile(r'^(\w[\w#\+]+) \(([^)]+)\):', re.MULTILINE)
        exec_time_line = re.compile(r'Average execution time: ([\d\.]+) seconds')
        for match in lang_algo_header.finditer(content):
            lang, algo = match.group(1), match.group(2)
            start_pos = match.end()
            time_match = exec_time_line.search(content[start_pos:])
            if time_match and data_size:
                exec_time = float(time_match.group(1))
                results.append({
                    'dataset_size': data_size,
                    'language': lang.lower(),
                    'algorithm': algo.lower().replace(' sort', ''),
                    'time': exec_time
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
        results = parse_consolidated_result_file(file_path)
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