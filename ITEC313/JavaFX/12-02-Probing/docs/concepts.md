# Hash Table Probing Techniques - Concepts and Design

## Overview

This document explains the main concepts and design decisions behind the three hash table collision resolution techniques implemented in this JavaFX application.

## Hash Table Fundamentals

### What is a Hash Table?
A hash table is a data structure that stores key-value pairs and provides average O(1) time complexity for insertions, deletions, and searches. It uses a hash function to map keys to array indices.

### Hash Function
All implementations use the same simple hash function:
```java
public int hash(Integer hashCode) {
    return hashCode % this.capacity;
}
```

This function distributes elements evenly across the table when the hash codes are well-distributed.

### Collision Resolution
When two different keys hash to the same index, a collision occurs. The three techniques implemented here handle collisions differently:

## 1. Linear Probing

### Concept
Linear probing resolves collisions by sequentially searching for the next available slot in the hash table.

### Algorithm
- **Insert**: If slot `h(k)` is occupied, try `h(k) + 1`, then `h(k) + 2`, and so on
- **Search**: Start at `h(k)` and follow the same sequence until finding the key or an empty slot
- **Delete**: Mark deleted positions with a special value (`Integer.MIN_VALUE`) to maintain search integrity

### Formula
```
h(k, i) = (h(k) + i) mod m
```
where:
- `h(k)` is the initial hash value
- `i` is the probe number (0, 1, 2, ...)
- `m` is the table size

### Advantages
- Simple to implement
- Good cache performance (sequential memory access)
- Predictable behavior

### Disadvantages
- Primary clustering: consecutive occupied slots form clusters
- Performance degrades as load factor increases
- Can lead to long probe sequences

### Implementation Details
```java
// Insert
int i = hash(e);
while (this.table[i] != null && this.table[i] != Integer.MIN_VALUE) {
    i = (i + 1) % this.table.length;
}
this.table[i] = e;

// Search
int i = hash(e);
int k = i;
while (this.table[i] != null) {
    if (this.table[i] != Integer.MIN_VALUE && this.table[i].equals(e)) {
        return true;
    }
    i = (i + 1) % this.table.length;
    if (i == k) return false; // Wrapped around
}
```

## 2. Quadratic Probing

### Concept
Quadratic probing uses a quadratic function to determine the next probe position, reducing clustering compared to linear probing.

### Algorithm
- **Insert**: If slot `h(k)` is occupied, try `h(k) + 1²`, then `h(k) + 2²`, and so on
- **Search**: Follow the same quadratic sequence
- **Delete**: Same marking approach as linear probing

### Formula
```
h(k, i) = (h(k) + i²) mod m
```
where:
- `h(k)` is the initial hash value
- `i` is the probe number (1, 2, 3, ...)
- `m` is the table size

### Advantages
- Reduces primary clustering
- Better distribution than linear probing
- Still maintains good cache performance

### Disadvantages
- Secondary clustering: keys with same initial hash follow same probe sequence
- More complex than linear probing
- Can still have performance issues at high load factors

### Implementation Details
```java
// Insert
int k = hash(e);
int j = 1;
int i = k;
while (this.table[i] != null && this.table[i] != Integer.MIN_VALUE) {
    i = Math.abs((k + j * j) % this.table.length);
    j++;
}
this.table[i] = e;

// Search
int k = hash(e);
int starting = k;
int i = k;
int j = 1;
while (this.table[i] != null) {
    if (this.table[i] != Integer.MIN_VALUE && this.table[i].equals(e)) {
        return true;
    }
    i = Math.abs((k + j * j) % this.table.length);
    if (i == starting) return false; // Wrapped around
    j++;
}
```

## 3. Separate Chaining

### Concept
Separate chaining resolves collisions by storing multiple elements in the same bucket using a linked list.

### Algorithm
- **Insert**: Add to the linked list at the hash index
- **Search**: Traverse the linked list at the hash index
- **Delete**: Remove from the linked list

### Advantages
- No clustering issues
- Simple deletion (no special markers needed)
- Predictable performance
- Can handle any number of collisions

### Disadvantages
- Extra memory overhead for linked list nodes
- Poor cache performance (random memory access)
- Can degenerate to O(n) if many collisions

### Implementation Details
```java
// Insert
int bucketIndex = hash(e);
if (this.table[bucketIndex] == null) {
    this.table[bucketIndex] = new LinkedList<>();
}
this.table[bucketIndex].add(e);

// Search
int bucketIndex = hash(e);
if (this.table[bucketIndex] != null) {
    LinkedList<Integer> bucket = this.table[bucketIndex];
    for (int i = 0; i < bucket.getSize(); i++) {
        if (e.equals(bucket.get(i))) {
            return true;
        }
    }
}
return false;
```

## Load Factor and Rehashing

### Load Factor
The load factor is the ratio of occupied slots to total capacity:
```
load_factor = size / capacity
```

### Rehashing Strategy
When the load factor exceeds the threshold:
1. Double the capacity
2. Rehash all existing elements
3. Maintain the same collision resolution strategy

### Implementation
```java
public void rehash() {
    List<Integer> list = setToList(); // Copy to a list
    this.capacity <<= 1; // Double capacity      
    this.table = new Integer[this.capacity]; // Create a new hash table
    this.size = 0; // Reset size 

    for (Integer element : list) {
        this.add(element); // Add from the old table to the new table
    }
}
```

## Performance Comparison

### Time Complexity
- **Average Case**: All techniques provide O(1) average time for insertions, deletions, and searches
- **Worst Case**: All techniques can degenerate to O(n) in pathological cases

### Space Complexity
- **Linear/Quadratic Probing**: O(n) where n is the number of elements
- **Separate Chaining**: O(n + m) where m is the table size

### Load Factor Recommendations
- **Linear Probing**: Keep below 0.7 for good performance
- **Quadratic Probing**: Keep below 0.8 for good performance
- **Separate Chaining**: Can handle higher load factors (0.9+)

## Design Decisions

### 1. Integer.MIN_VALUE as Deletion Marker
For linear and quadratic probing, we use `Integer.MIN_VALUE` to mark deleted positions. This allows searches to continue past deleted elements while distinguishing them from null (empty) positions.

### 2. Generic LinkedList Implementation
The separate chaining implementation uses a generic `LinkedList<E>` class that can be reused for other data structures.

### 3. Consistent Interface
All three implementations provide the same public interface:
- `add(E element)`
- `remove(E element)`
- `contains(E element)`
- `clear()`
- `size()`
- `isEmpty()`

### 4. Visualization Strategy
The application provides real-time visualization of:
- Hash table contents
- Load factor monitoring
- Capacity and size tracking
- Side-by-side comparison

This allows users to observe how each technique handles the same data differently.

## Educational Value

This implementation serves as an educational tool to:
1. **Compare Techniques**: See how different approaches handle the same data
2. **Understand Trade-offs**: Observe the pros and cons of each method
3. **Visualize Algorithms**: Watch collision resolution in real-time
4. **Experiment**: Test different load factors and data sets

The side-by-side visualization makes it easy to see how the same sequence of operations produces different hash table layouts depending on the collision resolution strategy used. 