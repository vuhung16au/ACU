# Problem 02: Random Sorted Array

## Problem Statement

Write a recursive method `public static void randomFillSortedArray(int[] x, int l, int r, int a, int b)` that fills the array x between l and r with random values between a and b such that x is sorted.

**Sample runs:**
```
Enter the array size: 10
Enter the limits: 0 1000
[235, 280, 382, 428, 458, 462, 484, 495, 536, 850]

Enter the array size: 10
Enter the limits: 0 9
[0, 0, 3, 3, 4, 6, 6, 8, 8, 9]
```

## Solution

```java
public static void randomFillSortedArray(int[] x, int l, int r, int a, int b) {
    // Base case: if left index >= right index, we're done
    if (l >= r) {
        return;
    }
    
    // Generate random value between a and b
    int randomValue = a + (int)(Math.random() * (b - a + 1));
    
    // Fill current position with random value
    x[l] = randomValue;
    
    // Recursive case: fill remaining positions
    // Update the lower bound to maintain sorted order
    randomFillSortedArray(x, l + 1, r, randomValue, b);
}
```

## Explanation

The algorithm ensures sorted order by maintaining the constraint that each subsequent element must be >= the previous element:

1. **Base case**: Stop when left index >= right index (no more positions to fill)
2. **Recursive case**:
   - Generate a random value between a and b
   - Place it at position l
   - Recursively fill the rest, but update the lower bound to the current value
   - This ensures each new element is >= the previous one

**Key insight**: By updating the lower bound `a` to `randomValue` in the recursive call, we guarantee that all subsequent elements will be >= the current element, maintaining sorted order.

**Example walkthrough for array size 3, limits [0, 9]:**
- Position 0: random value 3, update bounds to [3, 9]
- Position 1: random value 6, update bounds to [6, 9]  
- Position 2: random value 8, update bounds to [8, 9]
- Result: [3, 6, 8] (sorted)
