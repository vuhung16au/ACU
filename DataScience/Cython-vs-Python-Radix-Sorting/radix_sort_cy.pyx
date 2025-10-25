# cython: language_level=3
"""
Cython implementation of radix sort algorithm for performance comparison.
"""

import numpy as np
cimport numpy as cnp
from libc.stdlib cimport malloc, free

ctypedef cnp.int64_t DTYPE_t


cdef void counting_sort_cython(DTYPE_t* arr, int n, int exp):
    """
    Cython implementation of counting sort for radix sort.
    """
    cdef int i, index
    cdef int* count = <int*>malloc(10 * sizeof(int))
    cdef int* output = <int*>malloc(n * sizeof(int))
    
    # Initialize count array
    for i in range(10):
        count[i] = 0
    
    # Store count of occurrences in count[]
    for i in range(n):
        index = (arr[i] // exp) % 10
        count[index] += 1
    
    # Change count[i] so that count[i] now contains actual
    # position of this digit in output[]
    for i in range(1, 10):
        count[i] += count[i - 1]
    
    # Build the output array
    for i in range(n):
        idx = n - 1 - i  # Calculate index to avoid negative range
        index = (arr[idx] // exp) % 10
        output[count[index] - 1] = arr[idx]
        count[index] -= 1
    
    # Copy the output array to arr[]
    for i in range(n):
        arr[i] = output[i]
    
    free(count)
    free(output)


cdef void radix_sort_cython(DTYPE_t* arr, int n):
    """
    Cython implementation of radix sort.
    """
    cdef int i
    cdef DTYPE_t max_num = arr[0]
    cdef int exp = 1
    
    # Find the maximum number
    for i in range(1, n):
        if arr[i] > max_num:
            max_num = arr[i]
    
    # Do counting sort for every digit
    while max_num // exp > 0:
        counting_sort_cython(arr, n, exp)
        exp *= 10


def radix_sort_cython_wrapper(arr):
    """
    Python wrapper for the Cython radix sort function.
    """
    cdef int n = len(arr)
    cdef DTYPE_t* arr_ptr = <DTYPE_t*>malloc(n * sizeof(DTYPE_t))
    
    # Copy data to C array
    for i in range(n):
        arr_ptr[i] = arr[i]
    
    # Sort
    radix_sort_cython(arr_ptr, n)
    
    # Copy back to Python list
    result = [arr_ptr[i] for i in range(n)]
    
    free(arr_ptr)
    return result


def load_list_from_file(filename):
    """Load a list of numbers from a file."""
    numbers = []
    with open(filename, 'r') as f:
        for line in f:
            numbers.append(int(line.strip()))
    return numbers


# For direct execution
if __name__ == "__main__":
    import sys
    import time
    
    if len(sys.argv) != 2:
        print("Usage: python radix_sort_cy.pyx <filename>")
        sys.exit(1)
    
    filename = sys.argv[1]
    
    # Load the list from file
    print(f"Loading list from {filename}...")
    arr = load_list_from_file(filename)
    print(f"Loaded {len(arr)} numbers")
    
    # Sort the list
    print("Sorting with Cython radix sort...")
    start_time = time.time()
    sorted_arr = radix_sort_cython_wrapper(arr.copy())
    end_time = time.time()
    
    print(f"Cython radix sort completed in {end_time - start_time:.4f} seconds")
    print(f"First 10 elements: {sorted_arr[:10]}")
    print(f"Last 10 elements: {sorted_arr[-10:]}")
