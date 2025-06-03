# Chapter 11: Priority Queue

## Introduction
This chapter introduces the priority queue, a data structure where each element has a priority and elements with higher priority are served before those with lower priority. You will learn how to use priority queues in Python using the `heapq` module.

## Algorithms Implemented

### 1. Min-Heap as a Priority Queue
A min-heap is a binary tree where the smallest element is always at the root. In Python, the `heapq` module provides min-heap functionality.
```python
import heapq
priority_queue = []
heapq.heappush(priority_queue, 4)
heapq.heappush(priority_queue, 1)
heapq.heappush(priority_queue, 3)
heapq.heappush(priority_queue, 2)
while priority_queue:
    next_item = heapq.heappop(priority_queue)
    print("Popped item with highest priority:", next_item)
```
**Step-by-step:**
1. Add elements to the heap with `heappush()`.
2. Remove the smallest element with `heappop()`.

### 2. Priority Queue with Tuples
You can store (priority, value) pairs to associate data with a priority.
```python
priority_queue_with_tasks = []
heapq.heappush(priority_queue_with_tasks, (2, "do homework"))
heapq.heappush(priority_queue_with_tasks, (1, "eat lunch"))
heapq.heappush(priority_queue_with_tasks, (3, "play games"))
while priority_queue_with_tasks:
    priority, task = heapq.heappop(priority_queue_with_tasks)
    print(f"Task: '{task}' with priority {priority}")
```
**Step-by-step:**
1. Add (priority, value) pairs to the heap.
2. Remove and process tasks in order of priority.

## Algorithm Complexity (Time & Space)

### What is Time and Space Complexity?
- **Time complexity:** How the number of steps grows as the input size increases.
- **Space complexity:** How much extra memory is needed as the input grows.

### Complexity Analysis
- **heappush/heappop:** $O(\log n)$ per operation
- **Building a heap:** $O(n)$

#### Proof & Cases
- Each push/pop operation may require moving up or down the tree, which takes $O(\log n)$ steps.

## Important Notes
- Priority queues are useful for scheduling and simulation.
- The default heapq is a min-heap; for max-heap, invert the values.
- Tuples allow you to store both priority and data.

## Real-World Applications
- Task scheduling (operating systems, printers)
- Dijkstra's shortest path algorithm
- Event-driven simulation

## Ideas for Self-Practicing
- Implement a max-heap using heapq.
- Write a priority queue for hospital patients (higher severity = higher priority).
- Try using a priority queue to sort a list.

## Further Readings & Connections
- [GeeksforGeeks: Priority Queue](https://www.geeksforgeeks.org/priority-queue-set-1-introduction/)
- Learn about graphs and shortest path algorithms (see Chapter 15).

---
**Key Terms:**
- **Priority Queue:** Data structure where each element has a priority.
- **Heap:** A special tree-based structure for efficient priority queue operations.
- **Min-Heap:** The smallest element is always at the root. 