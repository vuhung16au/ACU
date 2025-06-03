# Chapter 10: Simple Linear Data Structures

## Introduction
This chapter introduces simple linear data structures such as stacks, queues, and linked lists. You will learn how these structures work, how to implement them, and their importance in computer science.

## Algorithms Implemented

### 1. Stack (LIFO)
A stack is a data structure where the last element added is the first one removed (Last-In, First-Out).
```python
stack = []
stack.append(1)  # Push
stack.append(2)
stack.append(3)
print(stack.pop())  # Pop (removes 3)
```
**Step-by-step:**
1. Add elements to the stack with `append()`.
2. Remove the top element with `pop()`.

### 2. Queue (FIFO)
A queue is a data structure where the first element added is the first one removed (First-In, First-Out).
```python
from collections import deque
queue = deque()
queue.append(1)  # Enqueue
queue.append(2)
queue.append(3)
print(queue.popleft())  # Dequeue (removes 1)
```
**Step-by-step:**
1. Add elements to the queue with `append()`.
2. Remove the front element with `popleft()`.

### 3. Singly Linked List (Conceptual)
A linked list is a sequence of nodes, each containing data and a reference to the next node.
```python
class Node:
    def __init__(self, data):
        self.data = data
        self.next = None

# Create nodes
node1 = Node(1)
node2 = Node(2)
node3 = Node(3)
node1.next = node2
node2.next = node3
```
**Step-by-step:**
1. Create nodes with data.
2. Link each node to the next.

## Algorithm Complexity (Time & Space)

### What is Time and Space Complexity?
- **Time complexity:** How the number of steps grows as the input size increases.
- **Space complexity:** How much extra memory is needed as the input grows.

### Complexity Analysis
- **Stack/Queue operations:** $O(1)$ for push/pop and enqueue/dequeue
- **Linked list traversal:** $O(n)$

#### Proof & Cases
- Stack and queue operations do not depend on the number of elements.
- Linked list traversal must visit each node.

## Important Notes
- Stacks are used for undo features, function calls, and parsing.
- Queues are used for scheduling, buffering, and breadth-first search.
- Linked lists are flexible but slower to access by index than arrays.

## Real-World Applications
- Stacks: Undo in editors, browser history
- Queues: Print jobs, customer service lines
- Linked lists: Music playlists, navigation systems

## Ideas for Self-Practicing
- Implement a stack using a linked list.
- Write a queue that supports checking if it is empty.
- Try reversing a linked list.

## Further Readings & Connections
- [Khan Academy: Stacks and Queues](https://www.khanacademy.org/computing/computer-science/algorithms/stacks-and-queues)
- [GeeksforGeeks: Linked List](https://www.geeksforgeeks.org/data-structures/linked-list/)
- Learn about more advanced data structures like trees (see Chapter 12) and graphs (see Chapter 15).

---
**Key Terms:**
- **Stack:** Last-In, First-Out (LIFO) data structure.
- **Queue:** First-In, First-Out (FIFO) data structure.
- **Linked List:** Sequence of nodes linked together. 