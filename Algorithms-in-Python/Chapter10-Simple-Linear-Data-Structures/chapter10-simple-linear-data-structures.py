# Chapter 10: Some Simple Linear Data Structures
# This tutorial introduces stacks, queues, and linked lists for beginners with simple, well-commented examples.

# 1. Stack (LIFO: Last In, First Out)
# You can use a Python list as a stack.

stack = []  # Create an empty stack
stack.append(10)  # Push 10 onto the stack
stack.append(20)  # Push 20 onto the stack
stack.append(30)  # Push 30 onto the stack
print("Stack after pushes:", stack)

popped_item = stack.pop()  # Pop the top item (30)
print("Popped item:", popped_item)
print("Stack after pop:", stack)

# 2. Queue (FIFO: First In, First Out)
# You can use collections.deque for an efficient queue.
from collections import deque

queue = deque()  # Create an empty queue
queue.append(1)  # Enqueue 1
queue.append(2)  # Enqueue 2
queue.append(3)  # Enqueue 3
print("Queue after enqueues:", list(queue))

dequeued_item = queue.popleft()  # Dequeue the front item (1)
print("Dequeued item:", dequeued_item)
print("Queue after dequeue:", list(queue))

# 3. Singly Linked List
# A linked list is a sequence of nodes, each containing data and a reference to the next node.

class Node:
    def __init__(self, data):
        self.data = data  # Store the data
        self.next = None  # Reference to the next node

class SinglyLinkedList:
    def __init__(self):
        self.head = None  # Start with an empty list

    def append(self, data):
        """Add a new node with the given data to the end of the list."""
        new_node = Node(data)
        if not self.head:
            self.head = new_node
        else:
            current = self.head
            while current.next:
                current = current.next
            current.next = new_node

    def print_list(self):
        """Print all elements in the linked list."""
        current = self.head
        while current:
            print(current.data, end=" -> ")
            current = current.next
        print("None")

# Create a linked list and add some elements
linked_list = SinglyLinkedList()
linked_list.append(100)
linked_list.append(200)
linked_list.append(300)
print("Linked list contents:")
linked_list.print_list()

# 4. Key Points
# - Stack: Use for undo features, backtracking, etc.
# - Queue: Use for task scheduling, breadth-first search, etc.
# - Linked List: Useful for dynamic memory allocation, easy insertions/deletions.

# End of Chapter 10: Simple Linear Data Structures tutorial
