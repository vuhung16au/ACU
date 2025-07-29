/**
 * Advanced.java
 * 
 * This program demonstrates advanced in Java:
 * - Core concepts and principles
 * - Implementation techniques
 * - Best practices and patterns
 * - Practical examples and usage
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
import java.util.*;

public class Advanced {
    
    /**
     * Node class for doubly linked list
     * In Python: class Node: def __init__(self, data): self.data = data; self.next = None; self.prev = None
     */
    static class DoublyNode {
        int data;
        DoublyNode next;
        DoublyNode prev;
        
        DoublyNode(int data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }
    
    /**
     * Doubly Linked List implementation
     * Provides O(1) deletion and bidirectional traversal
     */
    static class DoublyLinkedList {
        private DoublyNode head;
        private DoublyNode tail;
        private int size;
        
        public DoublyLinkedList() {
            this.head = null;
            this.tail = null;
            this.size = 0;
        }
        
        /**
         * Add element to end
         * O(1) operation due to tail pointer
         */
        public void append(int data) {
            DoublyNode newNode = new DoublyNode(data);
            
            if (head == null) {
                head = tail = newNode;
            } else {
                newNode.prev = tail;
                tail.next = newNode;
                tail = newNode;
            }
            size++;
        }
        
        /**
         * Add element to beginning
         * O(1) operation
         */
        public void prepend(int data) {
            DoublyNode newNode = new DoublyNode(data);
            
            if (head == null) {
                head = tail = newNode;
            } else {
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            }
            size++;
        }
        
        /**
         * Delete element by value
         * O(n) search but O(1) deletion once found
         */
        public boolean delete(int data) {
            DoublyNode current = head;
            
            while (current != null) {
                if (current.data == data) {
                    // If it's the head
                    if (current == head) {
                        head = head.next;
                        if (head != null) {
                            head.prev = null;
                        } else {
                            tail = null;
                        }
                    }
                    // If it's the tail
                    else if (current == tail) {
                        tail = tail.prev;
                        tail.next = null;
                    }
                    // If it's in the middle
                    else {
                        current.prev.next = current.next;
                        current.next.prev = current.prev;
                    }
                    size--;
                    return true;
                }
                current = current.next;
            }
            return false;
        }
        
        /**
         * Print list forward
         */
        public void printForward() {
            DoublyNode current = head;
            System.out.print("Forward: ");
            while (current != null) {
                System.out.print(current.data + " ");
                current = current.next;
            }
            System.out.println();
        }
        
        /**
         * Print list backward
         */
        public void printBackward() {
            DoublyNode current = tail;
            System.out.print("Backward: ");
            while (current != null) {
                System.out.print(current.data + " ");
                current = current.prev;
            }
            System.out.println();
        }
        
        public int size() {
            return size;
        }
    }
    
    /**
     * Circular Linked List implementation
     * Last node points to first node
     */
    static class CircularLinkedList {
        private Node head;
        private int size;
        
        public CircularLinkedList() {
            this.head = null;
            this.size = 0;
        }
        
        /**
         * Add element to end
         * Creates circular reference
         */
        public void append(int data) {
            Node newNode = new Node(data);
            
            if (head == null) {
                head = newNode;
                newNode.next = head; // Point to itself
            } else {
                Node current = head;
                while (current.next != head) {
                    current = current.next;
                }
                current.next = newNode;
                newNode.next = head;
            }
            size++;
        }
        
        /**
         * Print circular list
         * Stops when we reach head again
         */
        public void printList() {
            if (head == null) {
                System.out.println("Empty list");
                return;
            }
            
            Node current = head;
            System.out.print("Circular: ");
            do {
                System.out.print(current.data + " ");
                current = current.next;
            } while (current != head);
            System.out.println();
        }
        
        /**
         * Check if list has cycle
         * Uses Floyd's algorithm
         */
        public boolean hasCycle() {
            if (head == null || head.next == null) {
                return false;
            }
            
            Node slow = head;
            Node fast = head;
            
            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
                
                if (slow == fast) {
                    return true;
                }
            }
            
            return false;
        }
        
        public int size() {
            return size;
        }
    }
    
    /**
     * Simple Node for circular list
     */
    static class Node {
        int data;
        Node next;
        
        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }
    
    /**
     * Merge two sorted linked lists
     * Similar to Python's sorted(list1 + list2)
     */
    public static Node mergeSortedLists(Node list1, Node list2) {
        // Dummy head for result
        Node dummy = new Node(0);
        Node current = dummy;
        
        while (list1 != null && list2 != null) {
            if (list1.data <= list2.data) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }
        
        // Attach remaining elements
        if (list1 != null) {
            current.next = list1;
        }
        if (list2 != null) {
            current.next = list2;
        }
        
        return dummy.next;
    }
    
    /**
     * Sort linked list using merge sort
     * Similar to Python's list.sort()
     */
    public static Node mergeSort(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        
        // Find middle
        Node slow = head;
        Node fast = head.next;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // Split into two halves
        Node secondHalf = slow.next;
        slow.next = null;
        
        // Recursively sort
        Node firstHalf = mergeSort(head);
        Node sortedSecondHalf = mergeSort(secondHalf);
        
        // Merge sorted halves
        return mergeSortedLists(firstHalf, sortedSecondHalf);
    }
    
    /**
     * Print linked list
     */
    public static void printList(Node head) {
        Node current = head;
        System.out.print("List: ");
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }
    
    /**
     * Create linked list from array
     */
    public static Node createList(int[] arr) {
        if (arr.length == 0) return null;
        
        Node head = new Node(arr[0]);
        Node current = head;
        
        for (int i = 1; i < arr.length; i++) {
            current.next = new Node(arr[i]);
            current = current.next;
        }
        
        return head;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Advanced: Complex LinkedList Operations ===\n");
        
        // Doubly Linked List Demo
        System.out.println("--- Doubly Linked List Demo ---");
        DoublyLinkedList dll = new DoublyLinkedList();
        
        dll.append(10);
        dll.append(20);
        dll.append(30);
        dll.prepend(5);
        dll.prepend(1);
        
        dll.printForward();
        dll.printBackward();
        
        System.out.println("Deleting 20...");
        dll.delete(20);
        dll.printForward();
        dll.printBackward();
        
        // Circular Linked List Demo
        System.out.println("\n--- Circular Linked List Demo ---");
        CircularLinkedList cll = new CircularLinkedList();
        
        cll.append(1);
        cll.append(2);
        cll.append(3);
        cll.append(4);
        
        cll.printList();
        System.out.println("Has cycle: " + cll.hasCycle());
        System.out.println("Size: " + cll.size());
        
        // Merge Sort Demo
        System.out.println("\n--- Merge Sort Demo ---");
        int[] arr1 = {4, 2, 1, 3};
        int[] arr2 = {8, 6, 5, 7};
        
        Node list1 = createList(arr1);
        Node list2 = createList(arr2);
        
        System.out.println("List 1:");
        printList(list1);
        System.out.println("List 2:");
        printList(list2);
        
        Node merged = mergeSortedLists(list1, list2);
        System.out.println("Merged:");
        printList(merged);
        
        // Sort Demo
        System.out.println("\n--- Sort Demo ---");
        int[] unsorted = {64, 34, 25, 12, 22, 11, 90};
        Node unsortedList = createList(unsorted);
        
        System.out.println("Unsorted:");
        printList(unsortedList);
        
        Node sortedList = mergeSort(unsortedList);
        System.out.println("Sorted:");
        printList(sortedList);
        
        // Performance Analysis
        System.out.println("\n=== Performance Analysis ===");
        System.out.println("Data Structure    | Insert | Delete | Search | Memory");
        System.out.println("------------------|--------|--------|--------|--------");
        System.out.println("Singly Linked     | O(1)*  | O(n)   | O(n)   | Low");
        System.out.println("Doubly Linked     | O(1)*  | O(1)*  | O(n)   | Medium");
        System.out.println("Circular Linked   | O(1)*  | O(n)   | O(n)   | Low");
        System.out.println("ArrayList         | O(n)   | O(n)   | O(1)   | High");
        System.out.println("Python List       | O(n)   | O(n)   | O(1)   | High");
        System.out.println("* At beginning/end");
        
        System.out.println("\n=== Memory Comparison ===");
        System.out.println("Singly Linked: data + next pointer");
        System.out.println("Doubly Linked: data + next + prev pointers");
        System.out.println("Circular Linked: data + next pointer (circular)");
        System.out.println("ArrayList: contiguous memory allocation");
        System.out.println("Python List: dynamic array with growth factor");
        
        System.out.println("\n=== Use Cases ===");
        System.out.println("Singly Linked: Simple lists, stacks, queues");
        System.out.println("Doubly Linked: Deques, undo/redo systems");
        System.out.println("Circular Linked: Round-robin scheduling, games");
        System.out.println("ArrayList: Random access, known size");
        System.out.println("Python List: General purpose, most common");
        
        System.out.println("\n=== Algorithm Complexity ===");
        System.out.println("Merge Sort: O(n log n) time, O(log n) space");
        System.out.println("Cycle Detection: O(n) time, O(1) space");
        System.out.println("List Reversal: O(n) time, O(1) space");
        System.out.println("Middle Element: O(n) time, O(1) space");
        
        System.out.println("\n=== Key Takeaways ===");
        System.out.println("1. Linked lists excel at insertions/deletions");
        System.out.println("2. Doubly linked lists provide bidirectional access");
        System.out.println("3. Circular lists are useful for cyclic data");
        System.out.println("4. Merge sort is efficient for linked lists");
        System.out.println("5. Memory overhead vs. performance trade-offs");
    }
} 