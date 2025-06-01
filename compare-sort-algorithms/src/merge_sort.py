#!/usr/bin/env python3
"""
Merge Sort implementation in Python with performance measurement.
"""
import time
import sys
import os
import argparse

def merge_sort(arr):
    """
    Merge Sort implementation.
    Divide the array in half, sort each half, then merge the halves.
    """
    if len(arr) > 1:
        # Finding the mid of the array
        mid = len(arr) // 2
        
        # Dividing the array elements into 2 halves
        L = arr[:mid]
        R = arr[mid:]
        
        # Sorting the first half
        merge_sort(L)
        
        # Sorting the second half
        merge_sort(R)
        
        i = j = k = 0
        
        # Copy data to temp arrays L[] and R[]
        while i < len(L) and j < len(R):
            if L[i] <= R[j]:
                arr[k] = L[i]
                i += 1
            else:
                arr[k] = R[j]
                j += 1
            k += 1
        
        # Checking if any element was left
        while i < len(L):
            arr[k] = L[i]
            i += 1
            k += 1
        
        while j < len(R):
            arr[k] = R[j]
            j += 1
            k += 1

def read_numbers_from_file(filename="random_list.txt"):
    """Read numbers from file and return as list."""
    numbers = []
    try:
        with open(filename, 'r') as f:
            for line in f:
                try:
                    numbers.append(int(line.strip()))
                except ValueError:
                    # Skip lines that cannot be converted to integers
                    continue
        return numbers
    except FileNotFoundError:
        print(f"Error: {filename} not found. Please run generate_data.py first.")
        sys.exit(1)

def read_data_points_config(config_file="number-of-data-points.txt"):
    """Read data point sizes from configuration file."""
    try:
        with open(config_file, 'r') as f:
            return [int(line.strip()) for line in f if line.strip() and line.strip().isdigit()]
    except FileNotFoundError:
        print(f"Error: {config_file} not found. Using default size.")
        return [100000]

def parse_arguments():
    """Parse command line arguments."""
    parser = argparse.ArgumentParser(description='Python Merge Sort Performance Test')
    parser.add_argument('-c', '--config', default="config/number-of-data-points.txt",
                      help='Path to configuration file with data sizes')
    parser.add_argument('-d', '--datafile', default=None,
                      help='Path to input data file (overrides size-based file selection)')
    parser.add_argument('-r', '--results', default=None,
                      help='Path to save results')
    return parser.parse_args()

def main():
    print("Python Merge Sort Performance Test")
    print("=" * 40)
    
    # Parse command line arguments
    args = parse_arguments()
    
    # Read data point sizes from configuration file
    data_sizes = read_data_points_config(args.config)
    print(f"Configuration loaded: Testing sizes {data_sizes}")
    
    # Use provided data file or determine based on size
    if args.datafile:
        input_file = args.datafile
        # Extract the size from the file name if possible
        try:
            file_base = os.path.basename(input_file)
            if file_base.startswith("random_list_") and file_base.endswith(".txt"):
                size = int(file_base.replace("random_list_", "").replace(".txt", ""))
            else:
                size = data_sizes[0]  # Default to first size in config
        except:
            size = data_sizes[0]  # Default to first size in config
            
        print(f"\n--- Testing with dataset: {input_file} ---")
        
        # Read data from file
        print("Reading data from file...")
        data = read_numbers_from_file(input_file)
        print(f"Data size: {len(data)} integers")
    else:
        # Use the first size from the configuration
        size = data_sizes[0]
        print(f"\n--- Testing with {size:,} data points ---")
        
        # Get filename based on size
        filename = f"random_list_{size}.txt"
        print(f"Using dataset: {filename}")
        
        # Read data from file
        print("Reading data from file...")
        data = read_numbers_from_file(filename)
        print(f"Data size: {len(data)} integers")
    
    # Create a copy for sorting (to preserve original)
    data_copy = data.copy()
    
    # Measure sorting time
    print("Starting Merge Sort...")
    start_time = time.perf_counter()
    merge_sort(data_copy)
    end_time = time.perf_counter()
    
    execution_time = end_time - start_time
    
    # Verify the array is sorted
    is_sorted = all(data_copy[i] <= data_copy[i + 1] for i in range(len(data_copy) - 1))
    
    # Results
    print(f"Sorting completed: {'SUCCESS' if is_sorted else 'FAILED'}")
    print(f"Execution time: {execution_time:.6f} seconds")
    print(f"Elements per second: {len(data) / execution_time:.0f}")
    
    # Save results with size in filename
    if args.results:
        result_filename = args.results
    else:
        result_filename = f"results_python_{size}.txt"
        # Make sure results directory exists if using default path
        os.makedirs('results', exist_ok=True)
        result_filename = os.path.join('results', result_filename)
    
    with open(result_filename, "w") as f:
        f.write(f"Python Merge Sort Results\n")
        f.write(f"Data size: {len(data)}\n")
        f.write(f"Execution time: {execution_time:.6f} seconds\n")
        f.write(f"Elements per second: {len(data) / execution_time:.0f}\n")
        f.write(f"Sorted correctly: {is_sorted}\n")
    
    print(f"Results saved to {result_filename}")

if __name__ == "__main__":
    main()
