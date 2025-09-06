# Recursion and Anagram Concepts

## Table of Contents
1. [What is Recursion?](#what-is-recursion)
2. [Anagram Definition](#anagram-definition)
3. [Recursive Algorithm Design](#recursive-algorithm-design)
4. [Implementation Details](#implementation-details)
5. [Algorithm Analysis](#algorithm-analysis)
6. [Alternative Approaches](#alternative-approaches)
7. [Common Pitfalls](#common-pitfalls)

## What is Recursion?

Recursion is a programming technique where a function calls itself to solve a problem. It's based on the principle of breaking down a complex problem into smaller, similar subproblems.

### Key Components of Recursion

1. **Base Case**: The condition that stops the recursion
2. **Recursive Case**: The part that calls the function with a smaller problem
3. **Recursive Call**: The function calling itself with modified parameters

### Example: Factorial
```java
public int factorial(int n) {
    // Base case
    if (n <= 1) {
        return 1;
    }
    // Recursive case
    return n * factorial(n - 1);
}
```

## Anagram Definition

An anagram is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.

### Examples
- `listen` ↔ `silent`
- `evil` ↔ `vile`
- `cinema` ↔ `iceman`
- `debit card` ↔ `bad credit`

### Properties
- Same length
- Same character frequency
- Different letter arrangement
- Case insensitive (typically)
- Spaces ignored (typically)

## Recursive Algorithm Design

### Problem Analysis
To check if two words are anagrams:
1. Normalize both words (remove spaces, lowercase)
2. Check if lengths are equal
3. Verify all characters from word1 exist in word2

### Recursive Approach
```java
public boolean areAnagramsRecursive(String word1, String word2) {
    // Base case: if word1 is empty, all characters matched
    if (word1.isEmpty()) {
        return true;
    }
    
    // Get first character of word1
    char firstChar = word1.charAt(0);
    
    // Find character in word2
    int index = word2.indexOf(firstChar);
    
    // If not found, not anagrams
    if (index == -1) {
        return false;
    }
    
    // Remove matched character from both words
    String remaining1 = word1.substring(1);
    String remaining2 = word2.substring(0, index) + word2.substring(index + 1);
    
    // Recursively check remaining characters
    return areAnagramsRecursive(remaining1, remaining2);
}
```

### Algorithm Steps
1. **Base Case**: Empty word1 means all characters matched
2. **Character Matching**: Find first character of word1 in word2
3. **Character Removal**: Remove matched character from both words
4. **Recursive Call**: Check remaining characters

## Implementation Details

### Word Normalization
```java
public String normalizeWord(String word) {
    if (word == null) return "";
    return word.replaceAll("\\s+", "").toLowerCase();
}
```

### Character Frequency Approach
```java
public boolean areAnagramsWithFrequency(String word1, String word2) {
    String norm1 = normalizeWord(word1);
    String norm2 = normalizeWord(word2);
    
    if (norm1.length() != norm2.length()) {
        return false;
    }
    
    Map<Character, Integer> freq1 = countCharactersRecursive(norm1, new HashMap<>());
    Map<Character, Integer> freq2 = countCharactersRecursive(norm2, new HashMap<>());
    
    return freq1.equals(freq2);
}
```

### Recursive Character Counting
```java
private Map<Character, Integer> countCharactersRecursive(String word, Map<Character, Integer> map) {
    if (word.isEmpty()) {
        return map;
    }
    
    char firstChar = word.charAt(0);
    map.put(firstChar, map.getOrDefault(firstChar, 0) + 1);
    
    return countCharactersRecursive(word.substring(1), map);
}
```

## Algorithm Analysis

### Time Complexity

#### Recursive Character Matching
- **Best Case**: O(n) - when characters are in same order
- **Average Case**: O(n²) - due to indexOf operation
- **Worst Case**: O(n²) - when characters are in reverse order

#### Frequency Counting
- **Time Complexity**: O(n) - single pass through each word
- **Space Complexity**: O(n) - for character frequency maps

### Space Complexity
- **Recursive Call Stack**: O(n) - maximum depth equals word length
- **String Operations**: O(n) - substring operations create new strings

### Performance Comparison
| Method | Time | Space | Pros | Cons |
|--------|------|-------|------|------|
| Recursive Matching | O(n²) | O(n) | Simple logic | Slower for long words |
| Frequency Counting | O(n) | O(n) | Faster | More memory usage |

## Alternative Approaches

### 1. Sorting Approach
```java
public boolean areAnagramsSorting(String word1, String word2) {
    String norm1 = normalizeWord(word1);
    String norm2 = normalizeWord(word2);
    
    if (norm1.length() != norm2.length()) {
        return false;
    }
    
    char[] chars1 = norm1.toCharArray();
    char[] chars2 = norm2.toCharArray();
    
    Arrays.sort(chars1);
    Arrays.sort(chars2);
    
    return Arrays.equals(chars1, chars2);
}
```

### 2. Array Counting
```java
public boolean areAnagramsArray(String word1, String word2) {
    String norm1 = normalizeWord(word1);
    String norm2 = normalizeWord(word2);
    
    if (norm1.length() != norm2.length()) {
        return false;
    }
    
    int[] count = new int[26]; // Assuming lowercase letters only
    
    for (char c : norm1.toCharArray()) {
        count[c - 'a']++;
    }
    
    for (char c : norm2.toCharArray()) {
        count[c - 'a']--;
    }
    
    for (int i : count) {
        if (i != 0) return false;
    }
    
    return true;
}
```

## Common Pitfalls

### 1. Missing Base Case
```java
// WRONG - No base case, infinite recursion
public boolean badRecursion(String word1, String word2) {
    return areAnagramsRecursive(word1.substring(1), word2);
}
```

### 2. Incorrect Recursive Call
```java
// WRONG - Not reducing problem size
public boolean badRecursion(String word1, String word2) {
    if (word1.isEmpty()) return true;
    return areAnagramsRecursive(word1, word2); // Same parameters!
}
```

### 3. Case Sensitivity Issues
```java
// WRONG - Case sensitive comparison
public boolean badAnagram(String word1, String word2) {
    return word1.equals(word2); // Won't work for "Listen" vs "SILENT"
}
```

### 4. Space Handling
```java
// WRONG - Not handling spaces
public boolean badAnagram(String word1, String word2) {
    return word1.length() == word2.length(); // "debit card" vs "badcredit"
}
```

## Best Practices

### 1. Always Define Base Cases
- Make sure recursion terminates
- Handle edge cases (empty strings, null inputs)

### 2. Reduce Problem Size
- Each recursive call should work on a smaller problem
- Ensure progress toward the base case

### 3. Handle Edge Cases
- Null inputs
- Empty strings
- Single characters
- Different lengths

### 4. Consider Performance
- Choose appropriate algorithm for use case
- Consider space vs time trade-offs
- Profile for actual performance

### 5. Write Tests
- Test base cases
- Test edge cases
- Test normal cases
- Test performance with large inputs

## Educational Value

### Learning Objectives
1. **Understand Recursion**: Base cases, recursive cases, call stack
2. **Algorithm Design**: Breaking down problems into smaller parts
3. **String Processing**: Character manipulation, normalization
4. **Performance Analysis**: Time and space complexity
5. **Testing**: Edge cases, boundary conditions

### Recursion Benefits
- **Elegant Solutions**: Often more readable than iterative approaches
- **Natural Problem Modeling**: Mirrors mathematical definitions
- **Divide and Conquer**: Breaks complex problems into simpler ones

### When to Use Recursion
- **Tree/Graph Traversal**: Natural recursive structures
- **Mathematical Problems**: Factorial, Fibonacci, etc.
- **String Processing**: Pattern matching, parsing
- **Divide and Conquer**: Sorting, searching algorithms

### When NOT to Use Recursion
- **Deep Recursion**: Risk of stack overflow
- **Performance Critical**: Iterative solutions often faster
- **Simple Iterative Solutions**: Don't overcomplicate
- **Memory Constrained**: Call stack uses memory
