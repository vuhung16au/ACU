
Objectives
- To design common features of lists in an interface and  provide skeleton implementation in an abstract class  (§24.2).
- To design and implement a dynamic list using an array  (§24.3).
- To design and implement a dynamic list using a linked structure (§24.4).
- To design and implement a stack class using an array list and a queue class using a linked list (§24.5).
- To design and implement a priority queue using a heap (§24.6).


Lecture 10, titled "**Implementing Lists, Stacks, Queues, and Priority Queues**," delves into the practical implementation of these fundamental data structures in Java. The lecture explores two primary methods for implementing lists – using arrays and using linked structures – and then builds upon these foundations to implement stacks, queues, and priority queues.

The main **objectives** of this lecture are to:
*   Design common features of lists using an interface and provide a skeleton implementation in an abstract class.
*   Design and implement dynamic lists using both **arrays (MyArrayList)** and **linked structures (MyLinkedList)**.
*   Design and implement a **stack class** (using an array list) and a **queue class** (using a linked list).
*   Design and implement a **priority queue** (using a heap).

### Lists

A **list** is a popular data structure used to store data in sequential order, similar to lists of students, rooms, or books. Common operations on a list include retrieving, inserting, deleting, finding the count of elements, checking for element presence, and determining if the list is empty.

There are two primary ways to implement a list:

1.  **Using Arrays (MyArrayList)**:
    *   An array-based list, such as `MyArrayList`, uses an underlying array to store elements.
    *   Arrays are fixed-size, but a **dynamic list** can be implemented by creating a new, larger array (typically twice the current size) and copying elements when the capacity is exceeded.
    *   Operations like retrieving an element at a specific index (`get(int index)`) or adding an element at the end (`add(Object o)`) are efficient.
    *   However, `add(int index, Object o)` and `remove(int index)` are **inefficient** because they require shifting a potentially large number of elements.
    *   For insertion at a specified index, all subsequent elements must be shifted to the right. For deletion, all subsequent elements must be shifted to the left.
    *   The time complexity for `add(int index, E e)` and `remove(int index)` for `MyArrayList` is **O(n)**.

2.  **Using Linked Structures (MyLinkedList)**:
    *   A linked list, like `MyLinkedList`, consists of dynamically created **nodes**, where each node holds an element and a pointer (`next`) to its next neighbor.
    *   The `head` variable refers to the first node, and the `tail` variable refers to the last node; both are `null` if the list is empty.
    *   Linked lists improve efficiency for adding and removing elements anywhere in the list compared to array lists, as it avoids mass element shifting.
    *   Operations like `addFirst(E e)` and `removeFirst()` are **efficient (O(1))** in a linked list because they only involve updating a few pointers.
    *   Adding an element to the tail (`addLast(E e)`) is also **O(1)** due to the `tail` reference.
    *   However, `get(int index)` and `set(int index, Object o)` are **inefficient (O(n))** because they require traversing the list from the beginning to find the element at a specific index.
    *   **Circular singly linked lists** have the last node's pointer pointing back to the first node.
    *   **Doubly linked lists** contain nodes with two pointers: one to the `next` node and one to the `previous` node, allowing traversal in both directions.
    *   **Circular doubly linked lists** combine both circular and doubly linked properties.

Both `MyArrayList` and `MyLinkedList` share common operations, which can be generalized in an interface (`MyList`) or an abstract class.

### Stacks

A **stack** is a special type of list where elements are accessed, inserted, and deleted only from one end, called the **top**. This adheres to the **Last-In, First-Out (LIFO)** principle.
*   Operations: `push` (add to top), `pop` (remove from top), `peek` (view top element), `empty`, `getSize`.
*   **Implementation:** Using an **array list** to implement a stack is generally more efficient than a linked list because insertions and deletions occur only at the end (top) of the stack, which are efficient operations for array lists.

### Queues

A **queue** represents a waiting list, where elements are inserted at the **end (tail)** and accessed/deleted from the **beginning (head)**. This follows the **First-In, First-Out (FIFO)** principle.
*   Operations: `enqueue` (add to tail), `dequeue` (remove from head), `getSize`.
*   **Implementation:** It is more efficient to implement a queue using a **linked list** because deletions (dequeue) are made at the beginning of the list, which is an efficient operation for linked lists.

### Priority Queues

A **priority queue** is different from a regular queue as elements are assigned **priorities**. When accessing elements, the element with the **highest priority is removed first**, exhibiting a "largest-in, first-out" behavior. This is similar to an emergency room where patients with higher priority are treated first.
*   **Implementation:** Priority queues are typically implemented using a **heap** data structure.

### Design Considerations for Stacks and Queues

When designing stack and queue classes, two approaches are possible:
1.  **Using inheritance:** Extending `ArrayList` for a stack or `LinkedList` for a queue.
2.  **Using composition:** Defining an `ArrayList` as a data field in the stack class, and a `LinkedList` as a data field in the queue class.

The lecture suggests that **composition is generally better** as it allows for defining a completely new stack or queue class without inheriting unnecessary and inappropriate methods from the underlying `ArrayList` or `LinkedList`. This promotes cleaner design and prevents misuse of inherited methods that might violate the LIFO/FIFO principles.

Sample code 

- https://liveexample.pearsoncmg.com/html/MyList.html
- https://liveexample.pearsoncmg.com/dsanimation/ArrayListeBook.html
- https://liveexample.pearsoncmg.com/html/MyArrayList.html
- https://liveexample.pearsoncmg.com/html/TestMyArrayList.html
- https://liveexample.pearsoncmg.com/dsanimation/LinkedListeBook.html
- https://liveexample.pearsoncmg.com/html/MyLinkedList.html
- https://liveexample.pearsoncmg.com/html/TestMyLinkedList.html
- https://liveexample.pearsoncmg.com/dsanimation/StackeBook.html
- http://www.cs.armstrong.edu/liang/animation/web/Queue.html
- https://liveexample.pearsoncmg.com/html/GenericStack.html
- https://liveexample.pearsoncmg.com/html/GenericQueue.html
- https://liveexample.pearsoncmg.com/html/TestStackQueue.html
- https://liveexample.pearsoncmg.com/html/MyPriorityQueue.html
- https://liveexample.pearsoncmg.com/html/TestPriorityQueue.html