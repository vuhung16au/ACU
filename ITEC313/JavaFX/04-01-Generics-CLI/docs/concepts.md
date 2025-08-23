# Java Generics Concepts

This document explains the key concepts of Java generics demonstrated in the CLI application.

## What are Generics?

Generics provide **type parameterization** in Java. They allow you to define classes, interfaces, and methods with generic types that are replaced with concrete types at compile time.

## Key Benefits

### Type Safety
- **Compile-time error detection** instead of runtime
- **No explicit casting** required
- **Prevents ClassCastException**

### Code Reuse
- **Single implementation** works with multiple types
- **DRY principle** - write once, use with many types
- **Maintainability** - changes benefit all instantiations

### Performance
- **No runtime type checking** overhead
- **Direct access** without casting
- **Efficient collections**

## Generic Classes

```java
public class GenericStack<E> {
    private ArrayList<E> list = new ArrayList<>();
    
    public void push(E element) { ... }
    public E pop() { ... }
}

// Usage
GenericStack<String> stringStack = new GenericStack<>();
GenericStack<Integer> intStack = new GenericStack<>();
```

## Wildcards

### Unbounded Wildcard (?)
- Represents any type
- Can only read as Object
- Cannot add elements (except null)

```java
public static void printList(List<?> list) {
    for (Object item : list) {
        System.out.println(item);
    }
}
```

### Upper Bounded Wildcard (? extends T)
- Represents T or any subtype
- Allows reading from collection
- Cannot add elements

```java
public static double sum(List<? extends Number> list) {
    double sum = 0.0;
    for (Number number : list) {
        sum += number.doubleValue();
    }
    return sum;
}
```

### Lower Bounded Wildcard (? super T)
- Represents T or any supertype
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

Java generics use **type erasure**:
- Generic type information removed at compile time
- All instances share same runtime class file
- Generic information unavailable at runtime

```java
// Compile time
ArrayList<String> list = new ArrayList<>();

// Runtime (after erasure)
ArrayList list = new ArrayList();
String item = (String) list.get(0); // Cast added by compiler
```

## Restrictions

### Cannot Create Instance of Generic Type
```java
E element = new E(); // Compile error
```

### Cannot Create Generic Arrays
```java
E[] array = new E[10]; // Compile error
```

### Generic Type Parameters Not Allowed in Static Context
```java
public static T getInstance() { ... } // Compile error
```

### Exception Classes Cannot Be Generic
```java
public class GenericException<T> extends Exception { ... } // Compile error
```

## Generic Methods

```java
public static <T> void swap(T[] array, int i, int j) {
    T temp = array[i];
    array[i] = array[j];
    array[j] = temp;
}
```

## Bounded Types

```java
public class NumberStack<E extends Number> {
    // E must be Number or subclass
    // Can call Number methods on E
}
```

## Best Practices

### Use Generics for Type Safety
```java
ArrayList<String> list = new ArrayList<>(); // Good
ArrayList list = new ArrayList(); // Avoid raw types
```

### Use Wildcards for Flexibility
```java
public static double sum(List<? extends Number> list) // Good
public static double sum(List<Number> list) // Less flexible
```

### Follow PECS Principle
- **Producer Extends, Consumer Super**
- Use `? extends T` when reading only
- Use `? super T` when writing only

### Avoid Raw Types
- Always use generic types with type arguments
- Raw types exist only for backward compatibility

## Real-World Applications

### Collections Framework
```java
List<String> names = new ArrayList<>();
Map<String, Integer> scores = new HashMap<>();
Set<Integer> numbers = new HashSet<>();
```

### Custom Data Structures
```java
public class BinaryTree<T extends Comparable<T>> {
    // Works with any comparable type
}
```

### Mathematical Operations
```java
public abstract class GenericMatrix<E extends Number> {
    // Matrix operations for any numeric type
}
```

## Summary

Generics provide:
- **Type safety** at compile time
- **Code reuse** across different types
- **Performance** benefits
- **Flexibility** through wildcards
- **Maintainability** through single implementations 