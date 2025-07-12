
Objectives

- To store unordered, nonduplicate elements using a set (§21.2). 
- To explore how and when to use HashSet (§21.2.1), 
LinkedHashSet (§21.2.2), or TreeSet (§21.2.3) to store elements.
- To compare performance of sets and lists (§21.3).
- To use sets to develop a program that counts the keywords in a 
Java source file (§21.4).
- To tell the differences between Collection and Map and describe when and how to use HashMap, LinkedHashMap, and TreeMap to 
store values associated with keys (§21.5). 
To use maps to develop a program that counts the occurrence of  the words in a text (§21.6).
- To obtain singleton sets, lists, and maps, and unmodifiable sets, lists, and maps, using the static methods in the Collections class (§21.7).

Lecture 7.2, titled "**Sets and Maps**", delves into two crucial data structures within the Java Collections Framework that provide distinct ways of storing and accessing data: sets and maps.

The **objectives** for this lecture include understanding how to:
*   Store unordered, non-duplicate elements using a set.
*   Choose between `HashSet`, `LinkedHashSet`, and `TreeSet` for element storage.
*   Compare the performance of sets and lists.
*   Utilize sets, for instance, to count keywords in a Java source file.
*   Differentiate between `Collection` and `Map`.
*   Employ `HashMap`, `LinkedHashMap`, and `TreeMap` to store key-value pairs.
*   Apply maps, for example, to count word occurrences in text.
*   Obtain singleton and unmodifiable sets, lists, and maps using static methods from the `Collections` class.

**Motivations for using Sets and Maps**:
The lecture provides practical scenarios to highlight the utility of these data structures. For example, a "No-Fly" list, which contains names of people not permitted to board an aircraft, can be efficiently managed using a **set** because it needs to store unique, unordered names. If, in addition, detailed information (like gender, height, weight, nationality) needs to be stored and retrieved using a person's name as a key, a **map** becomes an efficient data structure for this task.

**Key Concepts:**

1.  **The `Set` Interface**:
    *   The `Set` interface extends the `Collection` interface.
    *   A defining characteristic of a `Set` is that it **contains no duplicate elements**. Implementations of the `Set` interface must ensure that `e1.equals(e2)` is false for any two distinct elements `e1` and `e2` in the set.
    *   The `AbstractSet` class provides partial implementations for the `equals` and `hashCode` methods, and is abstract because `size` and `iterator` methods are not implemented.
    *   **`HashSet`**: This concrete class implements `Set` and is used to store duplicate-free elements efficiently. For optimal performance, objects added to a `HashSet` should properly disperse their hash codes via their `hashCode` method.
    *   **`LinkedHashSet`**: This extends `HashSet` and maintains the **insertion order** of elements.
    *   **`SortedSet` Interface and `TreeSet` Class**: `SortedSet` is a subinterface of `Set` that guarantees elements are stored in a **sorted order**. `TreeSet` is a concrete implementation of `SortedSet`. Elements can be sorted in two ways:
        *   Using the `Comparable` interface for **natural ordering**.
        *   By providing a **`Comparator`** if a custom sorting order is desired or if the elements do not implement `Comparable`.
    *   **Performance**: The lecture suggests comparing the performance of `Set` implementations against `List` implementations.
    *   **Case Study**: Sets are used to develop an application that **counts keywords in a Java source file**.

2.  **The `Map` Interface**:
    *   The `Map` interface **maps keys to elements**. Unlike `List` where indices are integers, keys in a `Map` can be any object.
    *   You retrieve an object from a map using its key, and you must use a key to place an object into the map.
    *   **Concrete `Map` Classes**:
        *   **`HashMap`**: Efficient for **locating a value, inserting a mapping, and deleting a mapping**.
        *   **`TreeMap`**: Implements `SortedMap` and is efficient for **traversing keys in a sorted order**.
        *   **`LinkedHashMap`**: Extends `HashMap` and preserves the **insertion order** of entries, or can be configured to maintain access order (least recently accessed to most recently).
    *   **Case Study**: Maps are used to **count the occurrences of words in a text**, storing word-count pairs and then sorting them by converting to a `TreeMap`.

3.  **Utility Methods**: The `Collections` class provides static methods for creating singleton (containing only one element) and unmodifiable versions of sets, lists, and maps.

Sample code 

- https://liveexample.pearsoncmg.com/html/TestHashSet.html
- https://liveexample.pearsoncmg.com/html/TestLinkedHashSet.html
- https://liveexample.pearsoncmg.com/html/TestTreeSet.html
- https://liveexample.pearsoncmg.com/html/TestTreeSetWithComparator.html
- https://liveexample.pearsoncmg.com/html/SetListPerformanceTest.html
- https://liveexample.pearsoncmg.com/html/CountKeywords.html
- https://liveexample.pearsoncmg.com/html/TestMap.html
- https://liveexample.pearsoncmg.com/html/CountOccurrenceOfWords.html