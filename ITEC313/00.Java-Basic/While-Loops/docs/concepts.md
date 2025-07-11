# While Loops in Java

## Overview
Java provides two main types of loops for repeated execution: `while` and `do-while`. Both are used to execute a block of code multiple times based on a condition.

## While Loop
The `while` loop checks the condition before executing the loop body. If the condition is false initially, the body may not execute at all.

```java
int count = 0;
while (count < 5) {
    System.out.println(count);
    count++;
}
```

**Python Comparison:**
```python
count = 0
while count < 5:
    print(count)
    count += 1
```
- Java requires type declarations and braces `{}`; Python uses indentation.

## Do-While Loop
The `do-while` loop executes the body at least once, then checks the condition.

```java
int count = 0;
do {
    System.out.println(count);
    count++;
} while (count < 5);
```

**Python Equivalent:**
Python does not have a direct `do-while` loop, but similar behavior can be achieved:
```python
count = 0
while True:
    print(count)
    count += 1
    if count >= 5:
        break
```

## Infinite Loops
Both `while` and `do-while` can create infinite loops:
```java
while (true) {
    // Infinite loop
}
```

## Best Practices
- Always ensure loop conditions will eventually become false unless an infinite loop is intended.
- Use `break` to exit loops early if needed.
- Avoid modifying the loop variable in multiple places to prevent logic errors.

## Common Pitfalls
- Forgetting to update the loop variable (causes infinite loops)
- Off-by-one errors in loop conditions

## Summary Table
| Feature         | Java `while` | Java `do-while` | Python `while` |
|-----------------|--------------|-----------------|---------------|
| Pre-check       | Yes          | No              | Yes           |
| Post-check      | No           | Yes             | No            |
| Executes at least once | No     | Yes             | No            |

---
This document is intended for students transitioning from Python to Java, highlighting similarities and differences in loop constructs.
