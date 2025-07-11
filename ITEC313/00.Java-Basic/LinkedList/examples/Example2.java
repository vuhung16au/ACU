/**
 * Example 2: Advanced LinkedList Operations
 * 
 * This example demonstrates advanced linked list operations:
 * - Insertion at specific positions
 * - Deletion of elements
 * - Searching and finding elements
 * - Working with multiple linked lists
 * 
 * Python Comparison:
 * - Python lists have built-in insert() and remove() methods
 * - Java requires manual implementation of these operations
 * - Java's explicit memory management vs Python's garbage collection
 */

import java.util.*;

public class Example2 {
    
    /**
     * Node class for the linked list
     * Similar to Python: class Node: def __init__(self, data): self.data = data; self.next = None
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
     * Advanced LinkedList implementation
     * Demonstrates insertion, deletion, and searching
     */
    static class AdvancedLinkedList {
        private Node head;
        private int size;
        
        public AdvancedLinkedList() {
            this.head = null;
            this.size = 0;
        }
        
        /**
         * Insert at beginning
         * Similar to Python's list.insert(0, item)
         */
        public void insertAtBeginning(int data) {
            Node newNode = new Node(data);
            newNode.next = head;
            head = newNode;
            size++;
        }
        
        /**
         * Insert at specific position
         * Similar to Python's list.insert(index, item)
         */
        public void insertAt(int position, int data) {
            if (position < 0 || position > size) {
                throw new IndexOutOfBoundsException("Position out of bounds");
            }
            
            if (position == 0) {
                insertAtBeginning(data);
                return;
            }
            
            Node newNode = new Node(data);
            Node current = head;
            
            // Move to position - 1
            for (int i = 0; i < position - 1; i++) {
                current = current.next;
            }
            
            newNode.next = current.next;
            current.next = newNode;
            size++;
        }
        
        /**
         * Delete first occurrence of element
         * Similar to Python's list.remove(item)
         */
        public boolean delete(int data) {
            if (head == null) {
                return false;
            }
            
            // If head contains the data
            if (head.data == data) {
                head = head.next;
                size--;
                return true;
            }
            
            // Search for the element
            Node current = head;
            while (current.next != null) {
                if (current.next.data == data) {
                    current.next = current.next.next;
                    size--;
                    return true;
                }
                current = current.next;
            }
            
            return false;
        }
        
        /**
         * Delete at specific position
         * Similar to Python's del list[index]
         */
        public int deleteAt(int position) {
            if (position < 0 || position >= size) {
                throw new IndexOutOfBoundsException("Position out of bounds");
            }
            
            if (position == 0) {
                int data = head.data;
                head = head.next;
                size--;
                return data;
            }
            
            Node current = head;
            for (int i = 0; i < position - 1; i++) {
                current = current.next;
            }
            
            int data = current.next.data;
            current.next = current.next.next;
            size--;
            return data;
        }
        
        /**
         * Find position of element
         * Similar to Python's list.index(item)
         */
        public int indexOf(int data) {
            Node current = head;
            int position = 0;
            
            while (current != null) {
                if (current.data == data) {
                    return position;
                }
                current = current.next;
                position++;
            }
            
            return -1; // Not found
        }
        
        /**
         * Get element at position
         * Similar to Python's list[index]
         */
        public int get(int position) {
            if (position < 0 || position >= size) {
                throw new IndexOutOfBoundsException("Position out of bounds");
            }
            
            Node current = head;
            for (int i = 0; i < position; i++) {
                current = current.next;
            }
            
            return current.data;
        }
        
        /**
         * Print the list
         * Similar to Python's print(list)
         */
        public void printList() {
            Node current = head;
            System.out.print("List: ");
            while (current != null) {
                System.out.print(current.data + " ");
                current = current.next;
            }
            System.out.println("(Size: " + size + ")");
        }
        
        /**
         * Get size
         * Similar to Python's len(list)
         */
        public int size() {
            return size;
        }
        
        /**
         * Check if empty
         * Similar to Python's len(list) == 0
         */
        public boolean isEmpty() {
            return head == null;
        }
        
        /**
         * Convert to array
         * Similar to Python's list(list)
         */
        public int[] toArray() {
            int[] array = new int[size];
            Node current = head;
            int index = 0;
            
            while (current != null) {
                array[index++] = current.data;
                current = current.next;
            }
            
            return array;
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Example 2: Advanced LinkedList Operations ===\n");
        
        // Create a new linked list
        AdvancedLinkedList list = new AdvancedLinkedList();
        System.out.println("Created empty linked list");
        
        // Insert at beginning
        System.out.println("\n--- Inserting at Beginning ---");
        list.insertAtBeginning(30);
        list.insertAtBeginning(20);
        list.insertAtBeginning(10);
        list.printList();
        
        // Insert at specific positions
        System.out.println("\n--- Inserting at Specific Positions ---");
        list.insertAt(1, 15);  // Insert 15 at position 1
        list.insertAt(3, 25);  // Insert 25 at position 3
        list.printList();
        
        // Find elements
        System.out.println("\n--- Finding Elements ---");
        System.out.println("Index of 15: " + list.indexOf(15));
        System.out.println("Index of 25: " + list.indexOf(25));
        System.out.println("Index of 100: " + list.indexOf(100)); // Not found
        
        // Get elements at positions
        System.out.println("\n--- Getting Elements at Positions ---");
        System.out.println("Element at position 0: " + list.get(0));
        System.out.println("Element at position 2: " + list.get(2));
        System.out.println("Element at position 4: " + list.get(4));
        
        // Delete elements
        System.out.println("\n--- Deleting Elements ---");
        System.out.println("Deleting 15...");
        list.delete(15);
        list.printList();
        
        System.out.println("Deleting at position 1...");
        int deleted = list.deleteAt(1);
        System.out.println("Deleted element: " + deleted);
        list.printList();
        
        // Demonstrate array conversion
        System.out.println("\n--- Converting to Array ---");
        int[] array = list.toArray();
        System.out.print("Array: ");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
        
        // Working with multiple lists
        System.out.println("\n--- Multiple Lists Demo ---");
        AdvancedLinkedList list1 = new AdvancedLinkedList();
        AdvancedLinkedList list2 = new AdvancedLinkedList();
        
        // Populate first list
        list1.insertAtBeginning(5);
        list1.insertAtBeginning(3);
        list1.insertAtBeginning(1);
        
        // Populate second list
        list2.insertAtBeginning(6);
        list2.insertAtBeginning(4);
        list2.insertAtBeginning(2);
        
        System.out.println("List 1:");
        list1.printList();
        System.out.println("List 2:");
        list2.printList();
        
        // Compare sizes
        System.out.println("List 1 size: " + list1.size());
        System.out.println("List 2 size: " + list2.size());
        
        System.out.println("\n=== Performance Comparison ===");
        System.out.println("Operation          | LinkedList | ArrayList | Python List");
        System.out.println("-------------------|------------|-----------|------------");
        System.out.println("Insert at start    | O(1)       | O(n)      | O(n)");
        System.out.println("Insert at end      | O(n)       | O(1)      | O(1)");
        System.out.println("Insert at middle   | O(n)       | O(n)      | O(n)");
        System.out.println("Delete at start    | O(1)       | O(n)      | O(n)");
        System.out.println("Delete at end      | O(n)       | O(1)      | O(1)");
        System.out.println("Access by index    | O(n)       | O(1)      | O(1)");
        System.out.println("Search by value    | O(n)       | O(n)      | O(n)");
        
        System.out.println("\n=== Memory Usage ===");
        System.out.println("LinkedList: Each node has data + next pointer overhead");
        System.out.println("ArrayList: Contiguous memory allocation");
        System.out.println("Python List: Dynamic array with growth factor");
        
        System.out.println("\n=== Use Cases ===");
        System.out.println("LinkedList: Frequent insertions/deletions at beginning");
        System.out.println("ArrayList: Random access, known size");
        System.out.println("Python List: General purpose, most common choice");
    }
} 