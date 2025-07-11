# String-Algorithms - Concepts

## Overview

This module covers essential string algorithms and manipulation techniques in Java, with a focus on differences from Python.

## Key Concepts

### Main Learning Points

1. **String Immutability**: Java strings are immutable, meaning their values cannot be changed after creation. For modifications, use StringBuilder or StringBuffer.
2. **StringBuilder vs StringBuffer**: StringBuilder is faster but not thread-safe; StringBuffer is thread-safe but slower. Use StringBuilder for most single-threaded applications.
3. **Common Algorithms**: Learn to reverse strings, check for palindromes, search for substrings, replace characters, split and join strings, and more.

### Best Practices

- Use StringBuilder for repeated string modifications.
- Always use .equals() for string comparison, not ==.
- Handle null strings to avoid NullPointerException.
- Comment code thoroughly, especially where Java differs from Python.

### Common Pitfalls

- Using == instead of .equals() for string comparison.
- Forgetting that Java strings are immutable.
- Inefficient string concatenation in loops (use StringBuilder).
- Assuming Java has Python-like slicing (it does not).

## Practical Examples

- Reversing a string
- Checking for palindromes
- Finding substrings
- Replacing characters
- Splitting and joining strings

## Further Reading

- [Oracle Java Documentation](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html)
- [Java Language Specification](https://docs.oracle.com/javase/specs/jls/se8/html/)
- [Best Practices Guides](https://www.oracle.com/java/technologies/javase/codeconventions-contents.html)
