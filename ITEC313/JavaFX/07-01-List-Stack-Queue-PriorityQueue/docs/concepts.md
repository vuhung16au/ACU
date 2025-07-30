# JavaFX List Stack Queue PriorityQueue Demo - Concepts

## Overview

This JavaFX application demonstrates various data structures and algorithms from the Java Collections Framework, including Lists, Stacks, Queues, and Priority Queues.

## Core Concepts

### 1. Lists (ArrayList vs LinkedList)

**ArrayList:**
- Uses a dynamically resizable array
- Efficient for random access (O(1))
- Inefficient for insertions/deletions in the middle (O(n))
- Good for frequent access and infrequent modifications

**LinkedList:**
- Uses a doubly-linked list structure
- Efficient for insertions/deletions anywhere (O(1) for known position)
- Inefficient for random access (O(n))
- Good for frequent modifications

### 2. Comparators and Sorting

**Comparable Interface:**
- Provides natural ordering for objects
- Implemented by the class itself
- Used for default sorting behavior

**Comparator Interface:**
- Provides custom comparison logic
- Can be implemented separately from the class
- Allows multiple sorting strategies for the same class

### 3. Stacks

**LIFO (Last-In, First-Out) Data Structure:**
- Elements are added and removed from the top
- Common operations: push, pop, peek
- Used for expression evaluation, undo operations, etc.

### 4. Queues

**FIFO (First-In, First-Out) Data Structure:**
- Elements are added at the end and removed from the front
- Common operations: offer, poll, peek
- Used for task scheduling, breadth-first search, etc.

### 5. Priority Queues

**Specialized Queue with Priority Ordering:**
- Elements are ordered by priority (not insertion order)
- Highest priority element is removed first
- Can use natural ordering or custom comparator
- Used for task scheduling, event processing, etc.

## Implementation Examples

### TestArrayAndLinkedList
Demonstrates the difference between ArrayList and LinkedList operations:
- Adding elements at different positions
- Forward and backward iteration
- Performance characteristics

### GeometricObjectComparator
Shows how to implement custom comparison logic:
- Comparator interface implementation
- Area-based sorting of geometric objects
- Integration with Collections.sort()

### SortStringByLength
Demonstrates custom string sorting:
- Length-based comparison
- Anonymous comparator implementation
- Array sorting with custom comparator

### SortStringIgnoreCase
Shows case-insensitive string sorting:
- Lambda expression for comparison
- Case-insensitive string comparison
- List sorting with lambda comparator

### MultipleBounceBall
JavaFX animation using collections:
- ArrayList for managing multiple balls
- Dynamic addition/removal of elements
- Real-time animation with collections

### PriorityQueueDemo
Demonstrates priority queue operations:
- Natural ordering (alphabetical)
- Reverse ordering using comparator
- Priority-based element removal

### EvaluateExpression
Stack-based expression evaluation:
- Two-stack algorithm for arithmetic expressions
- Operator precedence handling
- Parentheses support

## Design Patterns

### 1. Strategy Pattern
Used in sorting with different comparators, allowing runtime selection of comparison strategy.

### 2. Observer Pattern
JavaFX properties and bindings demonstrate observer pattern for reactive UI updates.

### 3. Factory Pattern
Collection creation and management demonstrate factory-like patterns.

## Performance Considerations

### Time Complexity
- ArrayList: O(1) access, O(n) insertion/deletion
- LinkedList: O(n) access, O(1) insertion/deletion
- Stack/Queue: O(1) push/pop/offer/poll
- PriorityQueue: O(log n) insertion, O(1) peek, O(log n) removal

### Space Complexity
- All collections: O(n) space for n elements
- Additional overhead varies by implementation

## Best Practices

1. **Choose the Right Collection:**
   - Use ArrayList for random access
   - Use LinkedList for frequent modifications
   - Use Stack for LIFO operations
   - Use Queue for FIFO operations
   - Use PriorityQueue for priority-based operations

2. **Comparator Design:**
   - Ensure consistency (a.compareTo(b) should be opposite of b.compareTo(a))
   - Handle null values appropriately
   - Consider performance for large datasets

3. **JavaFX Integration:**
   - Use ObservableList for reactive UI updates
   - Bind properties for automatic UI updates
   - Handle UI updates on JavaFX Application Thread

## Common Pitfalls

1. **Concurrent Modification:**
   - Don't modify collections while iterating
   - Use Iterator.remove() for safe removal

2. **Memory Management:**
   - Clear references when no longer needed
   - Be aware of collection growth patterns

3. **Thread Safety:**
   - Most collections are not thread-safe
   - Use synchronized collections or concurrent collections when needed 