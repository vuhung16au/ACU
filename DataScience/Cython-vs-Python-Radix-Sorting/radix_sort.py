#!/usr/bin/env python3
"""
Python implementation of radix sort algorithm.
"""


def counting_sort(arr, exp):
    """
    A function to do counting sort of arr[] according to
    the digit represented by exp.
    """
    n = len(arr)
    output = [0] * n  # output array
    count = [0] * 10  # initialize count array as 0
    
    # Store count of occurrences in count[]
    for i in range(n):
        index = (arr[i] // exp) % 10
        count[index] += 1
    
    # Change count[i] so that count[i] now contains actual
    # position of this digit in output[]
    for i in range(1, 10):
        count[i] += count[i - 1]
    
    # Build the output array
    for i in range(n - 1, -1, -1):
        index = (arr[i] // exp) % 10
        output[count[index] - 1] = arr[i]
        count[index] -= 1
    
    # Copy the output array to arr[], so that arr[] now
    # contains sorted numbers according to current digit
    for i in range(n):
        arr[i] = output[i]


def radix_sort(arr):
    """
    The main function to that sorts arr[] of size n using
    Radix Sort.
    """
    # Find the maximum number to know number of digits
    max_num = max(arr)
    
    # Do counting sort for every digit. Note that instead
    # of passing digit number, exp is passed. exp is 10^i
    # where i is current digit number
    exp = 1
    while max_num // exp > 0:
        counting_sort(arr, exp)
        exp *= 10
    
    return arr


def load_list_from_file(filename):
    """Load a list of numbers from a file."""
    numbers = []
    with open(filename, 'r') as f:
        for line in f:
            numbers.append(int(line.strip()))
    return numbers


if __name__ == "__main__":
    import sys
    import time
    
    if len(sys.argv) != 2:
        print("Usage: python radix_sort.py <filename>")
        sys.exit(1)
    
    filename = sys.argv[1]
    
    # Load the list from file
    print(f"Loading list from {filename}...")
    arr = load_list_from_file(filename)
    print(f"Loaded {len(arr)} numbers")
    
    # Sort the list
    print("Sorting with Python radix sort...")
    start_time = time.time()
    sorted_arr = radix_sort(arr.copy())
    end_time = time.time()
    
    print(f"Python radix sort completed in {end_time - start_time:.4f} seconds")
    print(f"First 10 elements: {sorted_arr[:10]}")
    print(f"Last 10 elements: {sorted_arr[-10:]}")
