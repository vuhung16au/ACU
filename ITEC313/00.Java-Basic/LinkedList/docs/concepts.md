# LinkedList - Concepts

## Overview

Linked lists are fundamental data structures that store elements in a linear sequence, where each element (node) contains data and a reference to the next element. Unlike arrays, linked lists don't require contiguous memory allocation.

## Key Concepts

### 1. Node Structure
- **Data**: The actual value stored in the node
- **Next Pointer**: Reference to the next node in the sequence
- **Null Terminator**: Last node points to null (end of list)

### 2. Types of Linked Lists
- **Singly Linked List**: Each node has data and next pointer
- **Doubly Linked List**: Each node has data, next, and previous pointers
- **Circular Linked List**: Last node points back to first node

### 3. Basic Operations
- **Insertion**: Add elements at beginning, end, or specific position
- **Deletion**: Remove elements by value or position
- **Traversal**: Visit all elements sequentially
- **Search**: Find elements by value or position

### 4. Advanced Operations
- **Reversal**: Change direction of all pointers
- **Cycle Detection**: Detect if list has circular references
- **Merge**: Combine two sorted lists
- **Sort**: Arrange elements in order

## Performance Characteristics

### Time Complexity
- **Insertion at beginning**: O(1)
- **Insertion at end**: O(n) for singly linked, O(1) for doubly linked
- **Deletion**: O(n) for singly linked, O(1) for doubly linked
- **Search by value**: O(n)
- **Access by index**: O(n)

### Space Complexity
- **Storage**: O(n) for n elements
- **Overhead**: Each node has data + pointer(s)

## Comparison with Other Data Structures

### vs ArrayList
- **LinkedList**: Better for frequent insertions/deletions
- **ArrayList**: Better for random access and known size

### vs Python List
- **Python List**: Dynamic array, O(1) random access
- **Java LinkedList**: True linked list, O(n) random access

## Best Practices

### Implementation
- Always check for null pointers
- Maintain size counter for efficiency
- Use dummy nodes for edge cases
- Handle empty list scenarios

### Memory Management
- Java handles garbage collection automatically
- Be aware of memory overhead per node
- Consider using tail pointer for O(1) append

### Error Handling
- Validate indices before access
- Check for null before dereferencing
- Handle edge cases (empty list, single element)

## Common Pitfalls

### 1. Null Pointer Exceptions
- Always check if head is null before traversal
- Validate pointers before dereferencing

### 2. Infinite Loops
- Ensure proper termination conditions
- Check for cycles in circular lists

### 3. Memory Leaks
- Properly update pointers during deletion
- Java garbage collection handles most cases

### 4. Off-by-One Errors
- Be careful with position calculations
- Validate bounds before operations

## Python to Java Differences

### 1. No Built-in Linked Lists in Python
```python
# Python - use list or implement manually
class Node:
    def __init__(self, data):
        self.data = data
        self.next = None
```

```java
// Java - explicit Node class
class Node {
    int data;
    Node next;
    Node(int data) {
        this.data = data;
        this.next = null;
    }
}
```

### 2. Memory Management
- **Python**: Automatic garbage collection
- **Java**: Automatic garbage collection but explicit null handling

### 3. Type Safety
- **Python**: Dynamic typing
- **Java**: Static typing with generics

### 4. Collections
- **Python**: `collections.deque` provides some linked list functionality
- **Java**: `java.util.LinkedList` provides full implementation

## Use Cases

### When to Use Linked Lists
1. **Frequent insertions/deletions**: O(1) at beginning/end
2. **Unknown size**: Dynamic memory allocation
3. **Stack/Queue implementations**: Natural fit
4. **Memory constraints**: No contiguous memory requirement

### When NOT to Use Linked Lists
1. **Random access**: O(n) vs O(1) for arrays
2. **Cache performance**: Poor locality of reference
3. **Memory overhead**: Extra pointer storage
4. **Known size**: Arrays are more efficient

## Algorithm Examples

### 1. Cycle Detection (Floyd's Algorithm)
```java
public boolean hasCycle() {
    if (head == null || head.next == null) return false;
    
    Node slow = head;
    Node fast = head;
    
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) return true;
    }
    return false;
}
```

### 2. List Reversal
```java
public void reverse() {
    Node prev = null;
    Node current = head;
    Node next = null;
    
    while (current != null) {
        next = current.next;
        current.next = prev;
        prev = current;
        current = next;
    }
    head = prev;
}
```

### 3. Merge Two Sorted Lists
```java
public Node merge(Node list1, Node list2) {
    Node dummy = new Node(0);
    Node current = dummy;
    
    while (list1 != null && list2 != null) {
        if (list1.data <= list2.data) {
            current.next = list1;
            list1 = list1.next;
        } else {
            current.next = list2;
            list2 = list2.next;
        }
        current = current.next;
    }
    
    current.next = (list1 != null) ? list1 : list2;
    return dummy.next;
}
```

## Further Reading

### Java Documentation
- [LinkedList Class](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/LinkedList.html)
- [Collections Framework](https://docs.oracle.com/javase/tutorial/collections/)

### Algorithms
- [Linked List Algorithms](https://en.wikipedia.org/wiki/Linked_list)
- [Cycle Detection](https://en.wikipedia.org/wiki/Cycle_detection)

### Best Practices
- [Java Collections Best Practices](https://docs.oracle.com/javase/tutorial/collections/implementations/index.html)
- [Memory Management in Java](https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-2.html#jvms-2.5)
