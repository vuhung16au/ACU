# Chapter 11: Priority Queue
# This tutorial introduces priority queues for beginners, using heaps in Python.

import heapq  # Import the heapq module for heap operations

# 1. What is a Priority Queue?
# A priority queue is a data structure where each element has a priority.
# Elements with higher priority are served before elements with lower priority.
# In Python, the heapq module implements a min-heap by default.

# 2. Example: Using a Min-Heap as a Priority Queue

# Create an empty priority queue (min-heap)
priority_queue = []

# Add elements with priorities (the lower the value, the higher the priority)
heapq.heappush(priority_queue, 4)  # Add 4
heapq.heappush(priority_queue, 1)  # Add 1
heapq.heappush(priority_queue, 3)  # Add 3
heapq.heappush(priority_queue, 2)  # Add 2

print("Priority queue (min-heap) after pushes:", priority_queue)

# Remove elements in priority order (smallest first)
while priority_queue:
    next_item = heapq.heappop(priority_queue)
    print("Popped item with highest priority:", next_item)

# 3. Example: Priority Queue with Tuples (priority, value)
# Useful when you want to associate data with a priority.

priority_queue_with_tasks = []
heapq.heappush(priority_queue_with_tasks, (2, "do homework"))
heapq.heappush(priority_queue_with_tasks, (1, "eat lunch"))
heapq.heappush(priority_queue_with_tasks, (3, "play games"))

print("\nPriority queue with tasks:", priority_queue_with_tasks)

while priority_queue_with_tasks:
    priority, task = heapq.heappop(priority_queue_with_tasks)
    print(f"Task: '{task}' with priority {priority}")

# 4. Key Points
# - Priority queues are useful for scheduling, simulations, and algorithms like Dijkstra's shortest path.
# - The heapq module provides an efficient way to implement a priority queue in Python.
# - By default, heapq is a min-heap (smallest value has highest priority).
# - For a max-heap, you can insert negative priorities or use custom classes.

# End of Chapter 11: Priority Queue tutorial
