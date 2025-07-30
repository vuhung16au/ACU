# PriorityQueue Concepts

## Overview

This document explains the key concepts behind the PriorityQueue implementation in this JavaFX demo application.

## PriorityQueue Fundamentals

### What is a PriorityQueue?

A `PriorityQueue` is a specialized queue data structure that orders elements based on their priority rather than the order in which they were added (FIFO). The element with the highest priority is always at the front of the queue.

### Key Characteristics

1. **Priority-based ordering**: Elements are ordered by priority, not insertion order
2. **Heap-based implementation**: Uses a binary heap for efficient operations
3. **Dynamic resizing**: Automatically grows as needed
4. **Thread-safe**: Not thread-safe by default (use `PriorityBlockingQueue` for thread safety)

## Implementation in This Demo

### Two Queue Types

The application demonstrates two different PriorityQueue implementations:

#### 1. Comparable-based Queue
```java
PriorityQueue<String> queue1 = new PriorityQueue<>();
```

**Characteristics:**
- Uses natural ordering of elements
- For `String` objects, this means alphabetical order
- Elements are ordered from smallest to largest
- No custom comparator needed

**Example behavior:**
```
Input: ["Oklahoma", "Indiana", "Georgia", "Texas"]
Output: Georgia Indiana Oklahoma Texas
```

#### 2. Comparator-based Queue
```java
PriorityQueue<String> queue2 = new PriorityQueue<>(4, Collections.reverseOrder());
```

**Characteristics:**
- Uses custom comparator for ordering
- `Collections.reverseOrder()` provides reverse alphabetical ordering
- Elements are ordered from largest to smallest
- Custom ordering behavior

**Example behavior:**
```
Input: ["Oklahoma", "Indiana", "Georgia", "Texas"]
Output: Texas Oklahoma Indiana Georgia
```

## Core Operations

### Adding Elements
```java
queue.offer(element);  // Adds element to queue
```

### Removing Elements
```java
String element = queue.remove();  // Removes and returns highest priority element
```

### Peeking
```java
String element = queue.peek();  // Views highest priority element without removing
```

### Checking Size
```java
int size = queue.size();  // Number of elements in queue
boolean isEmpty = queue.isEmpty();  // Check if queue is empty
```

## PriorityQueue vs Other Collections

| Collection | Ordering | Use Case |
|------------|----------|----------|
| `ArrayList` | Insertion order | General purpose list |
| `LinkedList` | Insertion order | Queue/Stack operations |
| `TreeSet` | Natural/Comparator | Sorted unique elements |
| `PriorityQueue` | Priority-based | Priority-based processing |

## Real-world Applications

### 1. Task Scheduling
```java
PriorityQueue<Task> taskQueue = new PriorityQueue<>();
// Tasks with higher priority are processed first
```

### 2. Event Processing
```java
PriorityQueue<Event> eventQueue = new PriorityQueue<>();
// Events with earlier timestamps are processed first
```

### 3. Network Packet Routing
```java
PriorityQueue<Packet> packetQueue = new PriorityQueue<>();
// High-priority packets are transmitted first
```

### 4. Dijkstra's Algorithm
```java
PriorityQueue<Node> nodeQueue = new PriorityQueue<>();
// Nodes with shortest distance are processed first
```

## Performance Characteristics

### Time Complexity
- **Insertion (offer)**: O(log n)
- **Removal (remove/poll)**: O(log n)
- **Peek**: O(1)
- **Size check**: O(1)

### Space Complexity
- **Storage**: O(n) where n is the number of elements

## Best Practices

### 1. Choose Appropriate Comparator
```java
// For custom objects, implement Comparable
public class Task implements Comparable<Task> {
    private int priority;
    
    @Override
    public int compareTo(Task other) {
        return Integer.compare(this.priority, other.priority);
    }
}
```

### 2. Handle Empty Queue
```java
if (!queue.isEmpty()) {
    String element = queue.remove();
}
```

### 3. Use Appropriate Initial Capacity
```java
// If you know the approximate size, specify it
PriorityQueue<String> queue = new PriorityQueue<>(100);
```

## Common Pitfalls

### 1. Assuming FIFO Order
```java
// WRONG: PriorityQueue doesn't maintain insertion order
PriorityQueue<String> queue = new PriorityQueue<>();
queue.offer("Zebra");
queue.offer("Apple");
// First element removed will be "Apple", not "Zebra"
```

### 2. Not Handling Null Elements
```java
// PriorityQueue doesn't allow null elements
queue.offer(null); // Throws NullPointerException
```

### 3. Inconsistent Comparator
```java
// Ensure comparator is consistent
// compare(a, b) should return the opposite of compare(b, a)
```

## Testing PriorityQueue Behavior

The JavaFX application provides an interactive way to test these concepts:

1. **Add elements** and observe how they're ordered
2. **Compare the two queues** to see different ordering behaviors
3. **Clear and restart** to test with different data sets

This hands-on approach helps solidify understanding of PriorityQueue concepts. 