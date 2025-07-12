

Objectives

- To explore the relationship between interfaces and classes in the Java Collections Framework hierarchy (§20.2).
- To use the common methods defined in the Collection interface for operating collections (§20.2).
- To use the Iterator interface to traverse the elements in a collection (§20.3).
- To use a for-each loop to traverse the elements in a collection (§20.3).
- To explore how and when to use ArrayList or LinkedList to store elements (§20.4).
- To compare elements using the Comparable interface and the Comparator interface (§20.5).
- To use the static utility methods in the Collections class for sorting, searching, shuffling lists, and finding the largest and smallest element in collections (§20.6).
- To develop a multiple bouncing balls application using ArrayList (§20.7).
- To distinguish between Vector and ArrayList and to use the Stack class for creating stacks (§20.8).
- To explore the relationships among Collection, Queue, LinkedList, and PriorityQueue and to create priority queues using the PriorityQueue class (§20.9).
- To use stacks to write a program to evaluate expressions (§20.10).

Lecture 7.1 focuses on **Lists, Stacks, Queues, and Priority Queues**, providing an in-depth look at these fundamental data structures within the Java Collections Framework.

The primary **objectives** of this lecture are to enable understanding of:
*   The relationship between interfaces and classes in the Java Collections Framework hierarchy.
*   Common methods within the `Collection` interface.
*   How to traverse collection elements using the `Iterator` interface and for-each loops.
*   When to use `ArrayList` versus `LinkedList` for storing elements.
*   Comparing elements using the `Comparable` and `Comparator` interfaces.
*   Utilizing static utility methods in the `Collections` class for operations like sorting, searching, and shuffling lists.
*   Distinguishing between `Vector` and `ArrayList`, and using the `Stack` class.
*   Understanding the relationships among `Collection`, `Queue`, `LinkedList`, and `PriorityQueue`.

**Core Concepts:**

*   **Data Structure Definition**: A data structure is defined as a collection of data organized in a specific fashion, supporting operations for accessing and manipulating that data.
*   **Java Collections Framework**: This framework provides container objects, known as **collections**, which hold groups of objects (elements). The framework supports three main types of collections: **lists, sets, and maps**. The `Collection` interface serves as the root interface for manipulating these objects. `Set` and `List` are designated as subinterfaces of `Collection`.

*   **Lists (`List` Interface)**:
    *   A list stores elements in a **sequential order** and allows users to specify where elements are stored and accessed by index.
    *   Common operations include retrieving, inserting, deleting, finding size, checking for element presence, and determining if the list is empty.
    *   **Implementation Choices**: Lists can be implemented in two primary ways:
        *   **Using arrays (`MyArrayList`/`ArrayList`)**: This involves using a dynamically created array. If capacity is exceeded, a new larger array is created, and elements are copied over. `ArrayList` is efficient for random access (using an index) and adding elements to the end. However, adding or removing elements at arbitrary positions can be inefficient due to the need to shift many elements.
        *   **Using linked structures (`MyLinkedList`/`LinkedList`)**: A linked structure consists of nodes, each holding an element and a link to the next node. `LinkedList` is more efficient for adding or removing elements anywhere in the list because it only requires re-linking pointers, rather than shifting elements. However, random access (e.g., `get(index)`) is inefficient as it requires traversing from the beginning or end of the list.
    *   The choice between `ArrayList` and `LinkedList` depends on the application's specific needs regarding random access versus insertion/deletion frequency.

*   **Comparing Elements (`Comparable` and `Comparator`)**:
    *   The `Comparable` interface provides a natural ordering for objects.
    *   The `Comparator` interface allows for **custom comparison logic** when objects don't have a natural ordering or when a different ordering is desired.

*   **The `Collections` Class**: This utility class provides various **static methods** for operations on collections and maps, including sorting, searching, shuffling lists, and finding min/max elements. It can also create synchronized or read-only collection classes.

*   **Stacks (`Stack` Class)**:
    *   A stack is a **Last-In, First-Out (LIFO)** data structure, where elements are accessed, inserted, and deleted only from one end, known as the **top**.
    *   The `Stack` class extends `Vector` and includes methods like `push()` (add to top), `pop()` (remove from top), and `peek()` (view top element).
    *   Implementing a stack using an `ArrayList` is generally more efficient for insertion and deletion at the end.

*   **Queues (`Queue` Interface and `PriorityQueue` Class)**:
    *   A queue represents a waiting list and is a **First-In, First-Out (FIFO)** data structure, where elements are inserted at the end (tail) and removed from the beginning (head).
    *   A `LinkedList` is often used to implement a `Queue` efficiently, particularly for deletions at the beginning.
    *   A **Priority Queue** is a specialized queue where elements are assigned priorities, and the element with the **highest priority is removed first**. This results in a "largest-in, first-out" behavior.

The lecture also touches upon a case study involving **evaluating expressions using stacks**.

Sample code: 

- https://liveexample.pearsoncmg.com/html/TestArrayAndLinkedList.html
- https://liveexample.pearsoncmg.com/html/GeometricObjectComparator.html
- https://liveexample.pearsoncmg.com/html/GeometricObjectComparator.html
- https://liveexample.pearsoncmg.com/html/SortStringByLength.html
- https://liveexample.pearsoncmg.com/html/SortStringIgnoreCase.html
- https://liveexample.pearsoncmg.com/html/MultipleBounceBall.html
- https://liveexample.pearsoncmg.com/html/PriorityQueueDemo.html
- https://liveexample.pearsoncmg.com/html/EvaluateExpression.html