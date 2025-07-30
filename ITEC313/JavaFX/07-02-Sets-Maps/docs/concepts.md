# Concepts Documentation

## Java Collections Framework Overview

The JavaFX Sets and Maps Demo demonstrates key concepts from the Java Collections Framework, specifically focusing on Sets and Maps. These data structures provide different ways to store and access data efficiently.

## Sets Concepts

### What are Sets?

A Set is a collection that contains no duplicate elements. Sets are used when you need to store unique elements and don't care about their order (unless using LinkedHashSet or TreeSet).

### Set Implementations

#### 1. HashSet
- **Purpose**: Stores unique elements in no particular order
- **Performance**: O(1) average time for add, remove, and contains operations
- **Use Case**: When you need fast access and don't care about order
- **Example**: Storing unique usernames, IP addresses, or any unique identifiers

#### 2. LinkedHashSet
- **Purpose**: Maintains insertion order while ensuring uniqueness
- **Performance**: Slightly slower than HashSet due to maintaining order
- **Use Case**: When you need both uniqueness and insertion order
- **Example**: Tracking user login history, maintaining order of operations

#### 3. TreeSet
- **Purpose**: Stores elements in sorted order
- **Performance**: O(log n) for add, remove, and contains operations
- **Use Case**: When you need elements in sorted order
- **Example**: Maintaining a sorted list of names, scores, or any comparable objects

### Set Methods Demonstrated

- **add()**: Adds an element to the set
- **contains()**: Checks if an element exists in the set
- **remove()**: Removes an element from the set
- **size()**: Returns the number of elements
- **clear()**: Removes all elements

### TreeSet-Specific Methods

- **first()**: Returns the first (lowest) element
- **last()**: Returns the last (highest) element
- **headSet()**: Returns elements less than the specified element
- **tailSet()**: Returns elements greater than or equal to the specified element
- **lower()**: Returns the greatest element less than the given element
- **higher()**: Returns the least element greater than the given element
- **floor()**: Returns the greatest element less than or equal to the given element
- **ceiling()**: Returns the least element greater than or equal to the given element

## Maps Concepts

### What are Maps?

A Map is a collection that maps keys to values. Each key can map to at most one value. Maps are used when you need to associate data with unique identifiers.

### Map Implementations

#### 1. HashMap
- **Purpose**: Stores key-value pairs in no particular order
- **Performance**: O(1) average time for get, put, and remove operations
- **Use Case**: When you need fast access and don't care about order
- **Example**: Storing user data with username as key, caching data

#### 2. TreeMap
- **Purpose**: Stores key-value pairs in sorted order by keys
- **Performance**: O(log n) for get, put, and remove operations
- **Use Case**: When you need keys in sorted order
- **Example**: Maintaining sorted data, range queries

#### 3. LinkedHashMap
- **Purpose**: Maintains insertion order or access order
- **Performance**: Similar to HashMap with slight overhead for order maintenance
- **Use Case**: When you need both fast access and order preservation
- **Example**: LRU (Least Recently Used) cache implementation

### Map Methods Demonstrated

- **put()**: Associates a value with a key
- **get()**: Retrieves the value associated with a key
- **containsKey()**: Checks if a key exists in the map
- **containsValue()**: Checks if a value exists in the map
- **remove()**: Removes a key-value pair
- **size()**: Returns the number of key-value pairs
- **clear()**: Removes all key-value pairs

## Comparator Concepts

### What are Comparators?

A Comparator is an interface that defines a comparison function for ordering objects. It's used when you need custom ordering that differs from the natural ordering of objects.

### Comparator Implementation

The `GeometricObjectComparator` demonstrates:
- **Custom ordering**: Orders geometric objects by area instead of natural ordering
- **Flexibility**: Can be used with any collection that supports ordering
- **Reusability**: Can be applied to different collections

## Performance Analysis

### Collection Performance Comparison

The performance test demonstrates the trade-offs between different collection types:

#### HashSet vs ArrayList
- **HashSet**: O(1) average for contains operations
- **ArrayList**: O(n) for contains operations
- **Trade-off**: HashSet uses more memory but provides faster lookups

#### TreeSet vs HashSet
- **TreeSet**: O(log n) for all operations, but maintains sorted order
- **HashSet**: O(1) average for operations, but no order guarantee
- **Trade-off**: TreeSet provides ordering at the cost of performance

## Practical Applications

### Sets in Real-World Scenarios

1. **No-Fly Lists**: Storing unique names of people not permitted to board aircraft
2. **User Sessions**: Tracking active user sessions
3. **Unique Identifiers**: Storing unique IDs, emails, or usernames
4. **Data Deduplication**: Removing duplicate records from datasets

### Maps in Real-World Scenarios

1. **User Profiles**: Mapping usernames to user profile data
2. **Caching**: Storing frequently accessed data with keys
3. **Configuration**: Mapping configuration keys to values
4. **Word Frequency**: Counting occurrences of words in text

## Best Practices

### Choosing the Right Collection

1. **Use HashSet when**:
   - You need fast access
   - Order doesn't matter
   - Memory usage is not critical

2. **Use LinkedHashSet when**:
   - You need both uniqueness and insertion order
   - Performance is important but order matters

3. **Use TreeSet when**:
   - You need sorted elements
   - You need range operations
   - Performance is less critical than ordering

4. **Use HashMap when**:
   - You need fast key-value access
   - Order doesn't matter
   - Memory usage is not critical

5. **Use TreeMap when**:
   - You need sorted keys
   - You need range queries
   - Performance is less critical than ordering

6. **Use LinkedHashMap when**:
   - You need both fast access and order preservation
   - You're implementing LRU cache

### Memory Considerations

- **HashSet/HashMap**: Uses more memory due to hash table overhead
- **TreeSet/TreeMap**: Uses more memory due to tree structure overhead
- **LinkedHashSet/LinkedHashMap**: Uses more memory due to linked list overhead

### Thread Safety

- **None of these collections are thread-safe by default**
- **Use Collections.synchronizedSet() or Collections.synchronizedMap() for thread safety**
- **Consider ConcurrentHashMap for concurrent access**

## Common Pitfalls

1. **Mutating objects in Sets**: Objects used as set elements should be immutable or their hash codes shouldn't change
2. **Using mutable keys in Maps**: Keys should be immutable or their hash codes shouldn't change
3. **Ignoring equals() and hashCode()**: These methods must be properly implemented for correct behavior
4. **Not considering thread safety**: Collections are not thread-safe by default

## Advanced Concepts

### Custom Comparators

Creating custom comparators allows for flexible ordering:
```java
Comparator<GeometricObject> areaComparator = (o1, o2) -> 
    Double.compare(o1.getArea(), o2.getArea());
```

### Lambda Expressions

Modern Java allows concise comparator creation:
```java
Set<String> sortedSet = new TreeSet<>((s1, s2) -> s1.length() - s2.length());
```

### Stream Operations

Sets and Maps work well with Java 8+ streams:
```java
set.stream().filter(s -> s.length() > 5).forEach(System.out::println);
map.entrySet().stream()
    .filter(entry -> entry.getValue() > 10)
    .forEach(entry -> System.out.println(entry.getKey()));
``` 