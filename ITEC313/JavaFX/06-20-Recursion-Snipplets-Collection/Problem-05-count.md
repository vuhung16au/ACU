# Problem 05: Character Occurrence Counter

## Problem Statement

Write a recursive method that finds the number of occurrences of a specified letter in a string using the following method header:
```java
public static int count(String str, char a)
```

For example, `count("Welcome", 'e')` returns 2. Write a test program that prompts the user to enter a string and a character, and displays the number of occurrences for the character in the string.

## Solution

```java
public static int count(String str, char a) {
    // Base case: if string is empty, return 0
    if (str == null || str.length() == 0) {
        return 0;
    }
    
    // Check if first character matches
    int count = (str.charAt(0) == a) ? 1 : 0;
    
    // Recursive case: count occurrences in the rest of the string
    return count + count(str.substring(1), a);
}

// Alternative approach using index
public static int count(String str, char a, int index) {
    // Base case: if index is out of bounds
    if (index >= str.length()) {
        return 0;
    }
    
    // Check if current character matches
    int currentCount = (str.charAt(index) == a) ? 1 : 0;
    
    // Recursive case: count occurrences in the rest of the string
    return currentCount + count(str, a, index + 1);
}
```

## Explanation

The algorithm uses a divide-and-conquer approach to count character occurrences:

1. **Base case**: Return 0 if string is empty or index is out of bounds
2. **Recursive case**:
   - Check if the first character (or character at current index) matches the target
   - If it matches, add 1 to the count; otherwise, add 0
   - Recursively count occurrences in the remaining substring
   - Return the sum of current count and recursive result

**Key insight**: Each recursive call processes one character and delegates the rest to the next recursive call, building up the total count as the recursion unwinds.

**Example walkthrough for count("Welcome", 'e'):**
- `count("Welcome", 'e')` → 'W' ≠ 'e' → 0 + count("elcome", 'e')
- `count("elcome", 'e')` → 'e' = 'e' → 1 + count("lcome", 'e')  
- `count("lcome", 'e')` → 'l' ≠ 'e' → 0 + count("come", 'e')
- `count("come", 'e')` → 'c' ≠ 'e' → 0 + count("ome", 'e')
- `count("ome", 'e')` → 'o' ≠ 'e' → 0 + count("me", 'e')
- `count("me", 'e')` → 'm' ≠ 'e' → 0 + count("e", 'e')
- `count("e", 'e')` → 'e' = 'e' → 1 + count("", 'e')
- `count("", 'e')` → base case → 0
- Result: 0 + 1 + 0 + 0 + 0 + 0 + 1 + 0 = 2
