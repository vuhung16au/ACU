## Python Iterators Tutorial for Beginners

## What is an iterator?
# An iterator is an object that contains a countable number of values.
# You can use an iterator to go through all the values, one by one.

## Using iter() and next()
fruits = ["apple", "banana", "cherry"]
fruits_iterator = iter(fruits)  # Get an iterator from the list

print(next(fruits_iterator))  # Output: apple
print(next(fruits_iterator))  # Output: banana
print(next(fruits_iterator))  # Output: cherry
# If you call next() again, it will raise StopIteration

## Looping through an iterable (like a list)
# Python automatically uses iterators in for loops
for fruit in fruits:
    print("Fruit from for loop:", fruit)

## Creating your own iterator class
class CountUpTo:
    """Iterator that counts from 1 up to a given number."""
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

# Use the custom iterator
counter = CountUpTo(3)
for number in counter:
    print("Counting:", number)

## Summary
# - An iterator is an object with __iter__() and __next__() methods
# - Use iter() to get an iterator, and next() to get the next value
# - for loops use iterators automatically
# - You can create your own iterator classes
