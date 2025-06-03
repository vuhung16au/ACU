# Python Iterators

## Introduction
This file introduces the concept of iterators in Python. You will learn what an iterator is, how to use built-in iterators, and how to create your own iterator class. Understanding iterators is essential for working with sequences and loops in Python.

## Algorithms Implemented

### 1. Using Built-in Iterators
Iterators allow you to access elements of a collection one at a time.
```python
fruits = ["apple", "banana", "cherry"]
fruits_iterator = iter(fruits)
print(next(fruits_iterator))  # Output: apple
print(next(fruits_iterator))  # Output: banana
print(next(fruits_iterator))  # Output: cherry
```
**Step-by-step:**
1. Create a list of items.
2. Get an iterator using `iter()`.
3. Use `next()` to get each item in order.

### 2. Looping Through an Iterable
Python automatically uses iterators in `for` loops.
```python
for fruit in fruits:
    print("Fruit from for loop:", fruit)
```

### 3. Creating a Custom Iterator Class
You can create your own iterator by defining `__iter__()` and `__next__()` methods.
```python
class CountUpTo:
    def __init__(self, max_number):
        self.current = 1
        self.max_number = max_number
    def __iter__(self):
        return self
    def __next__(self):
        if self.current > self.max_number:
            raise StopIteration
        value = self.current
        self.current += 1
        return value

counter = CountUpTo(3)
for number in counter:
    print("Counting:", number)
```
**Step-by-step:**
1. Define a class with `__iter__()` and `__next__()`.
2. Use the class in a `for` loop to get values one by one.

## Algorithm Complexity (Time & Space)

### What is Time and Space Complexity?
- **Time complexity:** How the number of steps grows as the input size increases.
- **Space complexity:** How much extra memory is needed as the input grows.

### Complexity Analysis
- **Getting next item:** $O(1)$
- **Looping through $n$ items:** $O(n)$

#### Proof & Cases
- Each call to `next()` or each loop iteration takes constant time.

## Important Notes
- Iterators are used behind the scenes in all Python loops.
- Custom iterators let you control how data is produced.
- Always raise `StopIteration` to signal the end of iteration.

## Real-World Applications
- Reading lines from a file one at a time
- Generating sequences (like numbers, dates)
- Processing large datasets without loading everything into memory

## Ideas for Self-Practicing
- Write an iterator that counts down from a number to 1.
- Create an iterator for even numbers up to a limit.
- Try using iterators with different data types (lists, tuples, dictionaries).

## Further Readings & Connections
- [Python Iterators (W3Schools)](https://www.w3schools.com/python/python_iterators.asp)
- [GeeksforGeeks: Iterators in Python](https://www.geeksforgeeks.org/iterators-in-python/)
- Learn about generators (a special kind of iterator) in advanced Python.

---
**Key Terms:**
- **Iterator:** An object that lets you access items one at a time.
- **Iterable:** An object you can get an iterator from (like a list).
- **StopIteration:** Exception that signals the end of iteration. 