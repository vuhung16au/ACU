# Java Generics Concepts

This document explains the key concepts of Java generics demonstrated in the JavaFX Generics Demo application.

## What are Generics?

Generics provide the capability to **parameterize types** in Java. This means you can define classes, interfaces, and methods with generic types that can be replaced with concrete types by the compiler.

## Key Benefits of Generics

### 1. Type Safety
- **Compile-time error detection**: Errors are caught during compilation rather than at runtime
- **No explicit casting required**: When retrieving elements from generic collections
- **Prevents ClassCastException**: Eliminates runtime type errors

### 2. Code Reuse
- **Single implementation**: One implementation works with multiple types
- **DRY principle**: Don't Repeat Yourself - write once, use with many types
- **Maintainability**: Changes to the generic code benefit all type instantiations

### 3. Performance
- **No runtime type checking**: Eliminates overhead of runtime type verification
- **Direct access**: No need for casting operations
- **Efficient collections**: Optimized for the specific type

## Generic Classes and Interfaces

### Generic Class Declaration
```java
public class GenericStack<E> {
    private ArrayList<E> list = new ArrayList<>();
    
    public void push(E element) { ... }
    public E pop() { ... }
}
```

### Usage Examples
```java
GenericStack<String> stringStack = new GenericStack<>();
GenericStack<Integer> intStack = new GenericStack<>();
```

## Wildcards

Wildcards provide flexibility in generic type parameters and are essential for generic programming.

### 1. Unbounded Wildcard (?)
- Represents any type
- Most restrictive wildcard
- Can only read as Object
- Cannot add elements (except null)

```java
public static void printList(List<?> list) {
    for (Object item : list) {
        System.out.println(item);
    }
}
```

### 2. Upper Bounded Wildcard (? extends T)
- Represents T or any subtype of T
- Allows reading from the collection
- Cannot add elements (don't know exact type)

```java
public static double sum(List<? extends Number> list) {
    double sum = 0.0;
    for (Number number : list) {
        sum += number.doubleValue();
    }
    return sum;
}
```

### 3. Lower Bounded Wildcard (? super T)
- Represents T or any supertype of T
- Allows adding elements of type T
- Can only read as Object

```java
public static void addNumbers(List<? super Integer> list, Integer... numbers) {
    for (Integer number : numbers) {
        list.add(number);
    }
}
```

## Type Erasure

Java generics are implemented using **type erasure**:
- Generic type information is removed at compile time
- All instances of a generic class share the same runtime class file
- Generic information is not available at runtime

### Example
```java
// At compile time
ArrayList<String> list = new ArrayList<>();

// At runtime (after type erasure)
ArrayList list = new ArrayList();
String item = (String) list.get(0); // Explicit cast added by compiler
```

## Restrictions on Generics

Due to type erasure, several restrictions apply:

### 1. Cannot Create Instance of Generic Type
```java
// This is NOT allowed
E element = new E(); // Compile error
```

### 2. Cannot Create Generic Arrays
```java
// This is NOT allowed
E[] array = new E[10]; // Compile error
```

### 3. Generic Type Parameters Not Allowed in Static Context
```java
public class GenericClass<T> {
    // This is NOT allowed
    public static T getInstance() { ... } // Compile error
}
```

### 4. Exception Classes Cannot Be Generic
```java
// This is NOT allowed
public class GenericException<T> extends Exception { ... } // Compile error
```

## Generic Methods

Methods can also be declared with generic types:

```java
public static <T> void swap(T[] array, int i, int j) {
    T temp = array[i];
    array[i] = array[j];
    array[j] = temp;
}
```

## Bounded Generic Types

You can restrict the types that can be used for a generic parameter:

```java
public class NumberStack<E extends Number> {
    // E must be Number or a subclass of Number
    // This allows calling Number methods on E
}
```

## Raw Types

Raw types are generic types used without type arguments (for backward compatibility):

```java
ArrayList list = new ArrayList(); // Raw type
// Equivalent to ArrayList<Object>
```

**Warning**: Raw types lose type safety and should be avoided in new code.

## Best Practices

### 1. Use Generics for Type Safety
```java
// Good
ArrayList<String> list = new ArrayList<>();

// Avoid
ArrayList list = new ArrayList(); // Raw type
```

### 2. Use Wildcards for Flexibility
```java
// Good - accepts any List of Number or subtype
public static double sum(List<? extends Number> list)

// Less flexible - only accepts List<Number>
public static double sum(List<Number> list)
```

### 3. Follow PECS Principle
- **Producer Extends, Consumer Super**
- Use `? extends T` when you only read from the collection
- Use `? super T` when you only write to the collection

### 4. Avoid Raw Types
- Always use generic types with type arguments
- Raw types exist only for backward compatibility

## Real-World Applications

### 1. Collections Framework
All Java collections use generics for type safety:
```java
List<String> names = new ArrayList<>();
Map<String, Integer> scores = new HashMap<>();
Set<Integer> numbers = new HashSet<>();
```

### 2. Custom Data Structures
```java
public class BinaryTree<T extends Comparable<T>> {
    // Tree implementation that works with any comparable type
}
```

### 3. Utility Methods
```java
public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
    // Generic filter method
}
```

### 4. Mathematical Operations
```java
public abstract class GenericMatrix<E extends Number> {
    // Matrix operations that work with any numeric type
}
```

## Summary

Generics provide:
- **Type safety** at compile time
- **Code reuse** across different types
- **Performance** benefits through elimination of casting
- **Flexibility** through wildcards
- **Maintainability** through single implementations

Understanding generics is essential for modern Java development and is used extensively in the Java Collections Framework and many other libraries. 