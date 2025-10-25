#!/usr/bin/env python3
"""
Performance testing script for Python vs Cython radix sort implementation.
"""

import time
import sys
import os
from radix_sort import radix_sort, load_list_from_file
from radix_sort_cy import radix_sort_cython_wrapper


def test_performance(filename):
    """Test performance of both Python and Cython implementations."""
    print(f"Testing performance with file: {filename}")
    print("=" * 60)
    
    # Load the data
    print("Loading data...")
    arr = load_list_from_file(filename)
    print(f"Loaded {len(arr)} numbers")
    print(f"Range: {min(arr)} to {max(arr)}")
    print()
    
    # Test Python implementation
    print("Testing Python radix sort...")
    python_arr = arr.copy()
    start_time = time.time()
    python_result = radix_sort(python_arr)
    python_time = time.time() - start_time
    print(f"Python radix sort completed in {python_time:.4f} seconds")
    print(f"First 10 elements: {python_result[:10]}")
    print(f"Last 10 elements: {python_result[-10:]}")
    print()
    
    # Test Cython implementation
    print("Testing Cython radix sort...")
    cython_arr = arr.copy()
    start_time = time.time()
    cython_result = radix_sort_cython_wrapper(cython_arr)
    cython_time = time.time() - start_time
    print(f"Cython radix sort completed in {cython_time:.4f} seconds")
    print(f"First 10 elements: {cython_result[:10]}")
    print(f"Last 10 elements: {cython_result[-10:]}")
    print()
    
    # Verify results are the same
    if python_result == cython_result:
        print("✓ Results match between Python and Cython implementations")
    else:
        print("✗ Results do not match between Python and Cython implementations")
        print("This indicates a bug in one of the implementations!")
    print()
    
    # Performance comparison
    if python_time > 0 and cython_time > 0:
        speedup = python_time / cython_time
        print("Performance Comparison:")
        print(f"Python time:  {python_time:.4f} seconds")
        print(f"Cython time:  {cython_time:.4f} seconds")
        print(f"Speedup:      {speedup:.2f}x")
        if speedup > 1:
            print(f"Cython is {speedup:.2f}x faster than Python")
        else:
            print(f"Python is {1/speedup:.2f}x faster than Cython")
    else:
        print("Could not calculate speedup due to zero execution time")
    
    print("=" * 60)
    return python_time, cython_time


def main():
    """Main function to run performance tests."""
    print("Python vs Cython Radix Sort Performance Test")
    print("=" * 60)
    
    # Test with different file sizes if they exist
    test_files = [
        "random_list_1000000.txt",
        "random_list_10000000.txt"
    ]
    
    results = {}
    
    for filename in test_files:
        if os.path.exists(filename):
            print(f"\nTesting with {filename}...")
            python_time, cython_time = test_performance(filename)
            results[filename] = {
                'python_time': python_time,
                'cython_time': cython_time,
                'speedup': python_time / cython_time if cython_time > 0 else 0
            }
        else:
            print(f"File {filename} not found. Skipping...")
    
    # Summary
    if results:
        print("\n" + "=" * 60)
        print("PERFORMANCE SUMMARY")
        print("=" * 60)
        for filename, result in results.items():
            print(f"\n{filename}:")
            print(f"  Python:  {result['python_time']:.4f}s")
            print(f"  Cython:  {result['cython_time']:.4f}s")
            print(f"  Speedup: {result['speedup']:.2f}x")
    else:
        print("\nNo test files found. Please generate test data first.")
        print("Run: make random or make random10m")


if __name__ == "__main__":
    main()
