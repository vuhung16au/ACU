# Problem 04: Reverse String Display

## Problem Statement

Write a recursive method that displays a string reversely on the console using the following header:
```java
public static void reverseDisplay(String value)
```

For example, `reverseDisplay("abcd")` displays `dcba`. Write a test program that prompts the user to enter a string and displays its reversal.

## Solution

```java
public static void reverseDisplay(String value) {
    // Base case: if string is empty or has only one character
    if (value == null || value.length() <= 1) {
        if (value != null) {
            System.out.print(value);
        }
        return;
    }
    
    // Recursive case: 
    // 1. First, recursively print the substring (excluding first character)
    // 2. Then, print the first character
    reverseDisplay(value.substring(1));
    System.out.print(value.charAt(0));
}

// Alternative approach using index
public static void reverseDisplay(String value, int index) {
    // Base case: if index is out of bounds
    if (index >= value.length()) {
        return;
    }
    
    // Recursive case: print character at current index after recursive call
    reverseDisplay(value, index + 1);
    System.out.print(value.charAt(index));
}
```

## Explanation

The algorithm works by deferring the printing of characters until after the recursive calls complete:

1. **Base case**: Stop when string is empty or has one character
2. **Recursive case**:
   - Make recursive call with substring (excluding first character)
   - After the recursive call returns, print the first character
   - This creates a "last in, first out" effect

**Key insight**: The recursive call happens BEFORE printing, which means the first character is printed LAST, creating the reverse effect.

**Example walkthrough for "abcd":**
- `reverseDisplay("abcd")` → calls `reverseDisplay("bcd")` then prints 'a'
- `reverseDisplay("bcd")` → calls `reverseDisplay("cd")` then prints 'b'  
- `reverseDisplay("cd")` → calls `reverseDisplay("d")` then prints 'c'
- `reverseDisplay("d")` → prints 'd' (base case)
- Result: 'd' then 'c' then 'b' then 'a' = "dcba"

**Alternative approach** uses an index parameter to traverse the string from end to beginning, printing characters as the recursion unwinds.
