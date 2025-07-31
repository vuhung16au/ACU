# Selection Sort Algorithm Documentation

## Overview

Selection Sort is a simple comparison-based sorting algorithm. It works by repeatedly finding the minimum element from the unsorted portion of the array and placing it at the beginning of the sorted portion.

## How It Works

### Step-by-Step Process

1. **Initialization**: Start with the first element of the array
2. **Find Minimum**: Search through the remaining unsorted elements to find the minimum value
3. **Swap**: Exchange the minimum element with the first unsorted element
4. **Advance**: Move the boundary between sorted and unsorted portions one position forward
5. **Repeat**: Continue until the entire array is sorted

### Visual Example

Consider the array: `[64, 25, 12, 22, 11]`

**Pass 1**: Find minimum in `[64, 25, 12, 22, 11]` → minimum is `11`
- Swap `64` and `11` → `[11, 25, 12, 22, 64]`

**Pass 2**: Find minimum in `[25, 12, 22, 64]` → minimum is `12`
- Swap `25` and `12` → `[11, 12, 25, 22, 64]`

**Pass 3**: Find minimum in `[25, 22, 64]` → minimum is `22`
- Swap `25` and `22` → `[11, 12, 22, 25, 64]`

**Pass 4**: Find minimum in `[25, 64]` → minimum is `25`
- No swap needed → `[11, 12, 22, 25, 64]`

**Result**: Sorted array `[11, 12, 22, 25, 64]`

## Algorithm Characteristics

### Time Complexity
- **Best Case**: O(n²) - Even if the array is already sorted, the algorithm still performs all comparisons
- **Average Case**: O(n²) - For randomly ordered arrays
- **Worst Case**: O(n²) - When the array is sorted in reverse order

### Space Complexity
- **O(1)** - Selection sort is an in-place sorting algorithm that uses only a constant amount of additional memory

### Stability
- **Not Stable** - Selection sort is not stable as it may change the relative order of equal elements during swapping

### Adaptivity
- **Not Adaptive** - The algorithm doesn't adapt to the existing order in the data; it always performs the same number of comparisons

## Advantages

1. **Simple Implementation**: Easy to understand and code
2. **In-Place Sorting**: Uses only O(1) extra memory
3. **Minimum Number of Swaps**: Performs at most n-1 swaps (good for scenarios where writing to memory is costly)
4. **Consistent Performance**: Always O(n²) regardless of input order

## Disadvantages

1. **Poor Time Complexity**: O(n²) makes it inefficient for large datasets
2. **Not Stable**: May change the relative order of equal elements
3. **Not Adaptive**: Doesn't take advantage of existing order in the data
4. **More Comparisons**: Makes O(n²) comparisons even if the array is already sorted

## When to Use Selection Sort

Selection Sort is suitable for:
- Small datasets (typically n < 50)
- Educational purposes to understand sorting concepts
- Situations where memory is limited (in-place sorting)
- Cases where the cost of swapping is much higher than comparison
- Embedded systems with memory constraints

## Comparison with Other Sorting Algorithms

| Algorithm | Time Complexity | Space Complexity | Stable | Adaptive |
|-----------|----------------|------------------|---------|----------|
| Selection Sort | O(n²) | O(1) | No | No |
| Bubble Sort | O(n²) | O(1) | Yes | Yes |
| Insertion Sort | O(n²) | O(1) | Yes | Yes |
| Merge Sort | O(n log n) | O(n) | Yes | No |
| Quick Sort | O(n log n) | O(log n) | No | No |

## Pseudocode

```
SelectionSort(array):
    n = length of array
    
    for i = 0 to n-2:
        min_index = i
        
        for j = i+1 to n-1:
            if array[j] < array[min_index]:
                min_index = j
        
        if min_index ≠ i:
            swap array[i] and array[min_index]
    
    return array
```

## Implementation Notes for JavaFX Version

The JavaFX implementation includes several enhancements for educational visualization:

1. **Color Coding**: Different colors represent different states of elements
2. **Animation Timing**: Configurable delays to control visualization speed
3. **Step-by-Step Display**: Each comparison and swap is shown individually
4. **Interactive Controls**: Users can generate new arrays and control the process

This visual approach helps students understand the algorithm's behavior and identify its characteristics in real-time.
