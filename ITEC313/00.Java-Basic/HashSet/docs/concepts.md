# HashSet - Core Concepts

## Overview

HashSet is a collection class in Java that implements the Set interface. It stores unique elements and provides O(1) average time complexity for basic operations like add, remove, and contains.

## Key Concepts

### 1. Uniqueness
- HashSet guarantees that no duplicate elements are stored
- When you try to add a duplicate element, it's simply ignored
- This is different from ArrayList which allows duplicates

### 2. Hash-Based Storage
- Elements are stored using hash table data structure
- Provides O(1) average time complexity for basic operations
- Order of elements is not guaranteed (unlike LinkedHashSet)

### 3. Null Values
- HashSet can contain at most one null element
- Adding multiple null values will only store one

### 4. Custom Objects
- When storing custom objects, you MUST override equals() and hashCode()
- Without proper equals() and hashCode(), HashSet won't work correctly
- This is crucial for determining uniqueness

## Important Methods

### Basic Operations
- `add(E element)` - Adds element if not already present
- `remove(Object obj)` - Removes specified element
- `contains(Object obj)` - Returns true if element exists
- `size()` - Returns number of elements
- `isEmpty()` - Returns true if set is empty
- `clear()` - Removes all elements

### Set Operations
- `addAll(Collection c)` - Union operation
- `retainAll(Collection c)` - Intersection operation
- `removeAll(Collection c)` - Difference operation

### Iteration
- Enhanced for loop (recommended)
- Iterator
- forEach with lambda (Java 8+)
- Stream API (Java 8+)

## Performance Characteristics

### Time Complexity
- **Add**: O(1) average, O(n) worst case
- **Remove**: O(1) average, O(n) worst case
- **Contains**: O(1) average, O(n) worst case
- **Size**: O(1)

### Space Complexity
- O(n) where n is the number of elements

## Best Practices

### 1. Use for Uniqueness
- Perfect for removing duplicates from collections
- Use when you need to track unique items

### 2. Custom Objects
```java
// Always override equals() and hashCode()
@Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    MyClass other = (MyClass) obj;
    return Objects.equals(field1, other.field1) && 
           Objects.equals(field2, other.field2);
}

@Override
public int hashCode() {
    return Objects.hash(field1, field2);
}
```

### 3. Initial Capacity
- Set initial capacity if you know the approximate size
- Helps avoid resizing operations

### 4. Iteration
- Use enhanced for loop for simple iteration
- Use Iterator when you need to remove elements during iteration

## Common Pitfalls

### 1. Forgetting equals() and hashCode()
- Most common mistake when using custom objects
- Results in incorrect behavior for contains() and remove()

### 2. Assuming Order
- HashSet doesn't guarantee any specific order
- Use LinkedHashSet if you need insertion order
- Use TreeSet if you need sorted order

### 3. Mutable Objects
- Avoid storing mutable objects that change after being added
- Can cause the object to become "lost" in the set

### 4. Performance with Large Sets
- While O(1) average, performance can degrade with poor hashCode()
- Ensure hashCode() distributes elements evenly

## Python Comparisons

### Creating Sets
```python
# Python
my_set = set()
my_set = {1, 2, 3}
```
```java
// Java
HashSet<Integer> mySet = new HashSet<>();
HashSet<Integer> mySet = new HashSet<>(Arrays.asList(1, 2, 3));
```

### Adding Elements
```python
# Python
my_set.add(item)
```
```java
// Java
mySet.add(item);
```

### Checking Membership
```python
# Python
if item in my_set:
    print("Found")
```
```java
// Java
if (mySet.contains(item)) {
    System.out.println("Found");
}
```

### Set Operations
```python
# Python
union = setA | setB
intersection = setA & setB
difference = setA - setB
```
```java
// Java
HashSet<Integer> union = new HashSet<>(setA);
union.addAll(setB);

HashSet<Integer> intersection = new HashSet<>(setA);
intersection.retainAll(setB);

HashSet<Integer> difference = new HashSet<>(setA);
difference.removeAll(setB);
```

## Real-World Applications

### 1. Duplicate Removal
- Remove duplicate emails from mailing list
- Remove duplicate user IDs from database results

### 2. Membership Testing
- Check if user has permission to access resource
- Track visited URLs in web crawler

### 3. Set Operations
- Find common friends between two users
- Calculate unique visitors to website

### 4. Caching
- Store unique session IDs
- Track unique IP addresses

## Further Reading

- [Oracle Java HashSet Documentation](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/HashSet.html)
- [Java Collections Framework](https://docs.oracle.com/javase/tutorial/collections/)
- [Hash Table Data Structure](https://en.wikipedia.org/wiki/Hash_table)
- [Java equals() and hashCode() Contract](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#equals-java.lang.Object-)
