# Loop Control Concepts

## Overview

Loop control statements in Java allow you to control the flow of loop execution. These statements provide mechanisms to exit loops early, skip iterations, or control nested loops more precisely.

## Core Concepts

### 1. Break Statement

The `break` statement exits the innermost loop containing it.

**Syntax:**
```java
break;
```

**Example:**
```java
for (int i = 1; i <= 10; i++) {
    if (i == 5) {
        break; // Exit loop when i equals 5
    }
    System.out.println(i);
}
// Output: 1, 2, 3, 4
```

**Python Equivalent:**
```python
for i in range(1, 11):
    if i == 5:
        break
    print(i)
```

### 2. Continue Statement

The `continue` statement skips the current iteration and continues with the next.

**Syntax:**
```java
continue;
```

**Example:**
```java
for (int i = 1; i <= 10; i++) {
    if (i % 2 == 0) {
        continue; // Skip even numbers
    }
    System.out.println(i);
}
// Output: 1, 3, 5, 7, 9
```

**Python Equivalent:**
```python
for i in range(1, 11):
    if i % 2 == 0:
        continue
    print(i)
```

### 3. Labeled Statements

Java supports labeled `break` and `continue` statements, which are not available in Python.

**Syntax:**
```java
labelName: for (...) {
    // loop body
    break labelName;    // or continue labelName;
}
```

**Example - Labeled Break:**
```java
outerLoop: for (int i = 1; i <= 3; i++) {
    for (int j = 1; j <= 3; j++) {
        if (i == 2 && j == 2) {
            break outerLoop; // Exit outer loop
        }
        System.out.println("i=" + i + ", j=" + j);
    }
}
```

**Example - Labeled Continue:**
```java
outerLoop: for (int i = 1; i <= 3; i++) {
    for (int j = 1; j <= 3; j++) {
        if (i == 2 && j == 2) {
            continue outerLoop; // Continue outer loop
        }
        System.out.println("i=" + i + ", j=" + j);
    }
}
```

## Advanced Concepts

### 1. Nested Loop Control

When using nested loops, `break` and `continue` affect only the innermost loop by default.

**Default Behavior:**
```java
for (int i = 1; i <= 3; i++) {
    for (int j = 1; j <= 3; j++) {
        if (j == 2) {
            break; // Only breaks inner loop
        }
        System.out.println("i=" + i + ", j=" + j);
    }
}
```

**With Labels:**
```java
outerLoop: for (int i = 1; i <= 3; i++) {
    for (int j = 1; j <= 3; j++) {
        if (j == 2) {
            break outerLoop; // Breaks outer loop
        }
        System.out.println("i=" + i + ", j=" + j);
    }
}
```

### 2. Performance Considerations

Loop control statements can significantly improve algorithm performance:

**Without Break (Inefficient):**
```java
boolean found = false;
for (int i = 0; i < array.length; i++) {
    if (array[i] == target) {
        found = true;
    }
    // Continues checking even after finding target
}
```

**With Break (Efficient):**
```java
for (int i = 0; i < array.length; i++) {
    if (array[i] == target) {
        break; // Exit immediately when found
    }
}
```

### 3. Common Patterns

#### Early Exit Pattern
```java
for (int i = 0; i < data.length; i++) {
    if (data[i] == null || data[i].isEmpty()) {
        continue; // Skip invalid data
    }
    // Process valid data
}
```

#### Search Pattern
```java
boolean found = false;
for (int i = 0; i < array.length; i++) {
    if (array[i] == target) {
        found = true;
        break; // Exit when found
    }
}
```

#### Validation Pattern
```java
for (String item : items) {
    if (!isValid(item)) {
        continue; // Skip invalid items
    }
    processItem(item);
}
```

## Python vs Java Comparison

### Similarities
- Basic `break` and `continue` work identically
- Both languages support early loop termination
- Both support iteration skipping

### Key Differences

| Feature | Java | Python |
|---------|------|--------|
| Basic break/continue | ✅ | ✅ |
| Labeled statements | ✅ | ❌ |
| Nested loop control | Advanced | Limited |
| Loop labels | Required | Not available |

### Python Workarounds

**For Labeled Break:**
```python
# Python equivalent using flag
found = False
for i in range(1, 4):
    for j in range(1, 4):
        if i == 2 and j == 2:
            found = True
            break
    if found:
        break
```

**For Labeled Continue:**
```python
# Python equivalent using flag
for i in range(1, 4):
    skip_rest = False
    for j in range(1, 4):
        if i == 2 and j == 2:
            skip_rest = True
            break
    if skip_rest:
        continue
```

## Best Practices

1. **Use break for early termination**: Improves performance
2. **Use continue for filtering**: Keeps code clean
3. **Use labels sparingly**: Only when necessary for complex nested loops
4. **Document complex logic**: Explain why loop control is needed
5. **Consider readability**: Sometimes explicit conditions are clearer than loop control

## Common Mistakes

1. **Forgetting semicolons**: Java requires semicolons after statements
2. **Incorrect label syntax**: Labels must end with colon
3. **Using break in switch**: Different syntax than loop break
4. **Overusing labels**: Can make code harder to understand
5. **Ignoring scope**: Labels must be in scope when used

## Practical Applications

- **Data validation**: Skip invalid entries
- **Search algorithms**: Exit when target found
- **Matrix operations**: Control nested iterations
- **File processing**: Skip unwanted lines
- **Performance optimization**: Early termination
