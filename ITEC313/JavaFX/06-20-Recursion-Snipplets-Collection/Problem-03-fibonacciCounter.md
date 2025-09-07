# Problem 03: Fibonacci Call Counter

## Problem Statement

Given the iterative Fibonacci code:
```java
f0 = 0; // For fib(0)
f1 = 1; // For fib(1)
for (int i = 1; i <= n; i++) {
    currentFib = f0 + f1;
    f0 = f1;
    f1 = currentFib;
}
// After the loop, currentFib is fib(n)
```

Modify the code above so that the program finds the number of times the fib method is called. (Hint: Use a static variable and increment it every time the method is called.)

## Solution

```java
public class FibonacciCounter {
    // Static variable to count method calls
    private static int callCount = 0;
    
    // Recursive Fibonacci method with call counter
    public static long fib(int n) {
        callCount++; // Increment counter on each method call
        
        // Base cases
        if (n == 0) return 0;
        if (n == 1) return 1;
        
        // Recursive case
        return fib(n - 1) + fib(n - 2);
    }
    
    // Method to get and reset the call count
    public static int getCallCount() {
        int count = callCount;
        callCount = 0; // Reset for next calculation
        return count;
    }
    
    // Test method
    public static void main(String[] args) {
        int n = 5;
        long result = fib(n);
        int calls = getCallCount();
        
        System.out.println("fib(" + n + ") = " + result);
        System.out.println("Number of method calls: " + calls);
    }
}
```

## Explanation

The solution demonstrates how to track recursive method calls:

1. **Static counter**: `callCount` tracks total method invocations
2. **Increment on entry**: Every time `fib()` is called, increment the counter
3. **Base cases**: Return known values for n=0 and n=1
4. **Recursive case**: Call `fib(n-1)` and `fib(n-2)`, each incrementing the counter
5. **Reset mechanism**: `getCallCount()` returns current count and resets for next calculation

**Key insight**: The recursive approach has exponential time complexity O(2^n) because each call generates two more calls, leading to many redundant calculations.

**Example for fib(5):**
- Total calls: 15 (much more than the iterative approach's 5 iterations)
- This demonstrates why memoization or iterative approaches are preferred for Fibonacci
