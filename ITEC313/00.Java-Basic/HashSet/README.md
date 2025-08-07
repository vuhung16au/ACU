# HashSet

Unique elements, set operations, and hash-based storage

## 📋 Overview

This project demonstrates the usage of HashSet in Java, which is equivalent to Python's set data structure. HashSet provides O(1) average time complexity for add, remove, and contains operations, making it ideal for scenarios requiring unique elements and fast membership testing.

HashSet is part of Java's Collections Framework and implements the Set interface. It stores elements using a hash table data structure, ensuring no duplicates are stored and providing efficient lookup operations.

## 📁 Files in this Directory

```
HashSet/
├── HashSetDemo.java        # Main demonstration program
├── Makefile               # Build automation
├── README.md              # This documentation
├── examples/              # Additional example files
│   ├── Example1.java     # Basic HashSet operations
│   ├── Example2.java     # Set operations (union, intersection, etc.)
│   └── Advanced.java     # Custom objects and performance analysis
├── data/                  # Sample data files
│   ├── input.txt         # Sample input data for demonstrations
│   └── sample.csv        # Structured CSV data for examples
├── docs/                  # Additional documentation
│   └── concepts.md       # Detailed concept explanations
└── tests/                 # Test files (removed per requirements)
```

## 🛠 Building and Running

```bash
# Compile the program
make compile

# Run the main demonstration
make run

# Run individual examples
javac examples/Example1.java && java -cp examples Example1
javac examples/Example2.java && java -cp examples Example2
javac examples/Advanced.java && java -cp examples Advanced

# Clean compiled files
make clean

# Show available make targets
make help
```

## 📚 Learning Objectives

This project teaches:

1. **HashSet Fundamentals**
   - Understanding hash-based storage
   - Unique element guarantee
   - O(1) average time complexity

2. **Basic Operations**
   - Adding and removing elements
   - Checking membership with contains()
   - Iterating through HashSet elements

3. **Set Operations**
   - Union (addAll)
   - Intersection (retainAll)
   - Difference (removeAll)
   - Symmetric difference

4. **Custom Objects**
   - Importance of equals() and hashCode()
   - Proper implementation for HashSet compatibility
   - Common pitfalls and solutions

5. **Performance Analysis**
   - Time complexity understanding
   - Performance measurement techniques
   - Real-world optimization considerations

6. **Real-world Applications**
   - Duplicate removal from collections
   - Membership testing scenarios
   - Data processing workflows

## 🎯 Key Takeaways

### Core Concepts
- **Uniqueness**: HashSet guarantees no duplicate elements
- **Hash-based**: Uses hash table for O(1) average operations
- **No Order**: Elements are not stored in any specific order
- **Null Support**: Can contain at most one null element

### Performance Characteristics
- **Add/Remove/Contains**: O(1) average time complexity
- **Space**: O(n) where n is number of elements
- **Iteration**: O(n) time complexity

### Best Practices
- Always override equals() and hashCode() for custom objects
- Use for scenarios requiring uniqueness
- Consider initial capacity for large sets
- Choose appropriate iteration method

## 🔍 Important Concepts

### 1. Hash Table Implementation
HashSet uses a hash table data structure where:
- Elements are stored based on their hash code
- Collisions are handled using linked lists or trees
- Provides O(1) average time for basic operations

### 2. Uniqueness Guarantee
- No two equal elements can exist in the same HashSet
- Equality is determined by equals() method
- Duplicate additions are simply ignored

### 3. equals() and hashCode() Contract
When using custom objects:
- equals() defines when two objects are considered equal
- hashCode() must be consistent with equals()
- Both methods must be overridden together

### 4. Set Operations
- **Union**: Combines all elements from both sets
- **Intersection**: Keeps only elements present in both sets
- **Difference**: Removes elements present in the other set
- **Symmetric Difference**: Elements in exactly one set

## 🔍 Code Examples

### Basic HashSet Creation
```java
// Creating empty HashSet
HashSet<String> colors = new HashSet<>();

// Creating HashSet from collection
HashSet<Integer> numbers = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
```

### Adding and Checking Elements
```java
// Adding elements
colors.add("red");
colors.add("green");
colors.add("red"); // Duplicate - ignored

// Checking membership
if (colors.contains("red")) {
    System.out.println("Red is in the set");
}
```

### Set Operations
```java
// Union
HashSet<Integer> union = new HashSet<>(setA);
union.addAll(setB);

// Intersection
HashSet<Integer> intersection = new HashSet<>(setA);
intersection.retainAll(setB);

// Difference
HashSet<Integer> difference = new HashSet<>(setA);
difference.removeAll(setB);
```

### Custom Objects
```java
// Must override equals() and hashCode()
class Person {
    private String name;
    private int age;
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return age == person.age && Objects.equals(name, person.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
```

## 📝 Notes for Python Developers

### Key Differences from Python Sets

1. **Import Required**
   ```python
   # Python - built-in type
   my_set = set()
   ```
   ```java
   // Java - must import
   import java.util.HashSet;
   HashSet<String> mySet = new HashSet<>();
   ```

2. **Adding Elements**
   ```python
   # Python
   my_set.add(item)
   ```
   ```java
   // Java
   mySet.add(item);
   ```

3. **Membership Testing**
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

4. **Set Operations**
   ```python
   # Python - operator syntax
   union = setA | setB
   intersection = setA & setB
   difference = setA - setB
   ```
   ```java
   // Java - method calls
   HashSet<Integer> union = new HashSet<>(setA);
   union.addAll(setB);
   
   HashSet<Integer> intersection = new HashSet<>(setA);
   intersection.retainAll(setB);
   
   HashSet<Integer> difference = new HashSet<>(setA);
   difference.removeAll(setB);
   ```

5. **No Set Comprehensions**
   ```python
   # Python - set comprehension
   squares = {x*x for x in range(10)}
   ```
   ```java
   // Java - must use loops
   HashSet<Integer> squares = new HashSet<>();
   for (int x = 0; x < 10; x++) {
       squares.add(x * x);
   }
   ```

6. **Custom Objects**
   ```python
   # Python - implement __eq__ and __hash__
   class Person:
       def __eq__(self, other):
           return self.name == other.name and self.age == other.age
       
       def __hash__(self):
           return hash((self.name, self.age))
   ```
   ```java
   // Java - override equals() and hashCode()
   class Person {
       @Override
       public boolean equals(Object obj) {
           // Implementation
       }
       
       @Override
       public int hashCode() {
           return Objects.hash(name, age);
       }
   }
   ```

### Performance Considerations
- Both Python sets and Java HashSet provide O(1) average time complexity
- Java HashSet may have slightly different performance characteristics due to JVM optimizations
- Python sets are generally more memory-efficient for small collections

---

**Course**: ITEC313 - Object-Oriented Programming  
**Institution**: XYZ (XYZ)  
**Date**: July 11, 2025
