# ArrayList Implementation Concepts

## Overview

This project demonstrates the implementation of a custom ArrayList data structure using JavaFX for visualization and interaction. The implementation includes the core ArrayList functionality with a modern GUI interface.

## Key Concepts

### 1. MyList Interface

The `MyList<E>` interface extends `Collection<E>` and defines the core operations for a list:

- **add(int index, E e)**: Add element at specific index
- **get(int index)**: Retrieve element at index
- **indexOf(Object e)**: Find first occurrence of element
- **lastIndexOf(E e)**: Find last occurrence of element
- **remove(int index)**: Remove element at index
- **set(int index, E e)**: Replace element at index

### 2. MyArrayList Implementation

The `MyArrayList<E>` class implements the `MyList<E>` interface with:

- **Dynamic Array**: Uses an internal array that grows as needed
- **Initial Capacity**: Starts with 16 elements
- **Growth Strategy**: Doubles size + 1 when capacity is reached
- **Generic Type**: Supports any object type with type safety

### 3. Core Operations

#### Adding Elements
- **add(E e)**: Adds to end of list
- **add(int index, E e)**: Inserts at specific position, shifting elements right

#### Removing Elements
- **remove(Object e)**: Removes first occurrence by value
- **remove(int index)**: Removes element at index, shifting elements left

#### Accessing Elements
- **get(int index)**: Returns element at index
- **set(int index, E e)**: Replaces element at index

#### Utility Operations
- **size()**: Returns number of elements
- **clear()**: Removes all elements
- **contains(Object e)**: Checks if element exists
- **isEmpty()**: Checks if list is empty

### 4. Performance Characteristics

- **Time Complexity**:
  - Access by index: O(1)
  - Search by value: O(n)
  - Insert/Remove at end: O(1) amortized
  - Insert/Remove at beginning: O(n)
  - Insert/Remove at middle: O(n)

- **Space Complexity**: O(n) where n is the number of elements

### 5. JavaFX Integration

The application provides:
- **Interactive GUI**: Modern interface for testing ArrayList operations
- **Real-time Feedback**: Immediate visual feedback for all operations
- **Error Handling**: Proper validation and error messages
- **Cross-platform**: Works on Windows, macOS, and Linux

## Design Patterns

1. **Template Method Pattern**: Interface defines contract, implementation provides specific behavior
2. **Iterator Pattern**: Custom iterator for traversing elements
3. **MVC Pattern**: Separation of data (ArrayList), view (JavaFX), and control (event handlers)

## Learning Objectives

- Understanding dynamic array implementation
- Learning generic programming in Java
- Exploring JavaFX application development
- Practicing data structure visualization
- Understanding time/space complexity analysis 