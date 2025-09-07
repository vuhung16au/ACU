# Problem 06: Array Permutations

## Problem Statement

Write a recursive method that displays all permutations of a given array of integers.

**Sample run:**
```
Enter the array size: 3
[1, 2, 3]
[1, 3, 2]
[2, 1, 3]
[2, 3, 1]
[3, 1, 2]
[3, 2, 1]
```

## Solution

```java
public static void printPermutations(int[] arr) {
    printPermutationsHelper(arr, 0);
}

private static void printPermutationsHelper(int[] arr, int start) {
    // Base case: if we've reached the end of the array
    if (start == arr.length) {
        // Print the current permutation
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
        return;
    }
    
    // Recursive case: try each element at the current position
    for (int i = start; i < arr.length; i++) {
        // Swap current element with element at position 'start'
        swap(arr, start, i);
        
        // Recursively generate permutations for the rest
        printPermutationsHelper(arr, start + 1);
        
        // Backtrack: restore original order
        swap(arr, start, i);
    }
}

// Helper method to swap two elements in the array
private static void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
}
```

## Explanation

The algorithm uses backtracking to generate all permutations:

1. **Base case**: When `start` equals array length, we have a complete permutation to print
2. **Recursive case**:
   - For each position from `start` to end of array
   - Swap the current element with the element at position `start`
   - Recursively generate permutations for positions `start + 1` onwards
   - Backtrack by swapping back to restore original order

**Key insight**: The algorithm fixes one element at a time and recursively generates permutations for the remaining elements. The backtracking ensures we explore all possible arrangements.

**Example walkthrough for [1, 2, 3]:**
- Fix 1 at position 0: generate permutations of [2, 3] → [1,2,3], [1,3,2]
- Fix 2 at position 0: generate permutations of [1, 3] → [2,1,3], [2,3,1]  
- Fix 3 at position 0: generate permutations of [1, 2] → [3,1,2], [3,2,1]

**Time complexity**: O(n!) where n is the array size, since there are n! permutations of n elements.
