# Problem 01: Decimal to Base Conversion

## Problem Statement

Write a recursive method `public static void dec2b(double x, int b, int n)` that displays x, where 0 ≤ x < 1 in base b with at most n digits after the decimal point.

**Examples:**
- `dec2b(0.625, 2, 10)` should return `0.101`
- `dec2b(0.625, 3, 10)` should return `0.1212121212`

## Solution

```java
public static void dec2b(double x, int b, int n) {
    // Base case: if n is 0 or x is 0, we're done
    if (n == 0 || x == 0) {
        return;
    }
    
    // Multiply by base to get the next digit
    double product = x * b;
    int digit = (int) product;  // Get the integer part (next digit)
    
    // Print the digit
    System.out.print(digit);
    
    // Recursive case: continue with fractional part
    // Subtract the digit to get the fractional part
    double fractionalPart = product - digit;
    dec2b(fractionalPart, b, n - 1);
}
```

## Explanation

The algorithm works by repeatedly multiplying the decimal number by the target base:
1. **Base case**: Stop when we've reached the maximum digits (n=0) or when x becomes 0
2. **Recursive case**: 
   - Multiply x by base b to shift digits
   - Extract the integer part as the next digit
   - Print the digit
   - Continue recursively with the fractional part
   - Decrement n to track remaining digits

**Example walkthrough for dec2b(0.625, 2, 10):**
- 0.625 × 2 = 1.25 → digit = 1, fractional = 0.25
- 0.25 × 2 = 0.5 → digit = 0, fractional = 0.5  
- 0.5 × 2 = 1.0 → digit = 1, fractional = 0.0
- Result: 0.101
