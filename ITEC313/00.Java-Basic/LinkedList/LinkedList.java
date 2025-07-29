/**
 * LinkedList.java
 * 
 * This program demonstrates LinkedList usage in Java:
 * - LinkedList creation and operations
 * - Node-based data structure
 * - LinkedList vs ArrayList comparison
 * - LinkedList performance characteristics
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
import java.util.*;

public class LinkedList {
    
    /**
     * Node class for the linked list
     * In Python, you'd typically use a tuple or class for this
     */
    static class Node {
        int data;           // The data stored in this node
        Node next;          // Reference to the next node (null if last)
        
        // Constructor - similar to Python's __init__
        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }
    
    // Head of the linked list (similar to Python's self.head)
    private Node head;
    private int size;
    
    /**
     * Constructor - initializes an empty linked list
     * In Python: def __init__(self): self.head = None
     */
    public LinkedList() {
        this.head = null;
        this.size = 0;
    }
    
    /**
     * Adds an element to the end of the linked list
     * Similar to Python's append() method
     * 
     * @param data The data to add
     */
    public void add(int data) {
        Node newNode = new Node(data);
        
        // If list is empty, make new node the head
        if (head == null) {
            head = newNode;
        } else {
            // Traverse to the last node
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            // Add new node at the end
            current.next = newNode;
        }
        size++;
    }
    
    /**
     * Adds an element at a specific position
     * Similar to Python's insert() method
     * 
     * @param position The position to insert at (0-based)
     * @param data The data to insert
     */
    public void addAt(int position, int data) {
        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException("Position out of bounds");
        }
        
        Node newNode = new Node(data);
        
        // Insert at beginning
        if (position == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            // Find the node before the insertion point
            Node current = head;
            for (int i = 0; i < position - 1; i++) {
                current = current.next;
            }
            // Insert the new node
            newNode.next = current.next;
            current.next = newNode;
        }
        size++;
    }
    
    /**
     * Removes the first occurrence of the specified element
     * Similar to Python's remove() method
     * 
     * @param data The data to remove
     * @return true if element was found and removed, false otherwise
     */
    public boolean remove(int data) {
        if (head == null) {
            return false;
        }
        
        // If head contains the data to remove
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
     * Gets the element at the specified position
     * Similar to Python's list[index]
     * 
     * @param index The position to get (0-based)
     * @return The data at the specified position
     */
    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }
    
    /**
     * Returns the size of the linked list
     * Similar to Python's len() function
     * 
     * @return The number of elements in the list
     */
    public int size() {
        return size;
    }
    
    /**
     * Checks if the linked list is empty
     * Similar to Python's len(list) == 0
     * 
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return head == null;
    }
    
    /**
     * Converts the linked list to a string representation
     * Similar to Python's str() or __str__ method
     * 
     * @return String representation of the linked list
     */
    @Override
    public String toString() {
        if (head == null) {
            return "[]";
        }
        
        StringBuilder result = new StringBuilder("[");
        Node current = head;
        
        while (current != null) {
            result.append(current.data);
            if (current.next != null) {
                result.append(" -> ");
            }
            current = current.next;
        }
        
        result.append("]");
        return result.toString();
    }
    
    /**
     * Reverses the linked list in place
     * Similar to Python's list.reverse()
     */
    public void reverse() {
        Node prev = null;
        Node current = head;
        Node next = null;
        
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        
        head = prev;
    }
    
    /**
     * Finds the middle element of the linked list
     * Uses the two-pointer technique (fast and slow pointers)
     * 
     * @return The middle element, or -1 if list is empty
     */
    public int findMiddle() {
        if (head == null) {
            return -1;
        }
        
        Node slow = head;
        Node fast = head;
        
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return slow.data;
    }
    
    /**
     * Checks if the linked list contains a cycle
     * Uses Floyd's Cycle Finding Algorithm
     * 
     * @return true if cycle exists, false otherwise
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
    
    /**
     * Demonstrates linked list operations and compares with ArrayList
     */
    public static void main(String[] args) {
        System.out.println("=== LinkedList Operations Demo ===\n");
        
        // Create and populate linked list
        LinkedList linkedList = new LinkedList();
        System.out.println("Creating linked list...");
        
        // Add elements (similar to Python's append)
        linkedList.add(10);
        linkedList.add(20);
        linkedList.add(30);
        linkedList.add(40);
        linkedList.add(50);
        
        System.out.println("Linked List: " + linkedList);
        System.out.println("Size: " + linkedList.size());
        
        // Insert at specific position
        System.out.println("\n--- Inserting at position 2 ---");
        linkedList.addAt(2, 25);
        System.out.println("After inserting 25 at position 2: " + linkedList);
        
        // Get element at specific position
        System.out.println("\n--- Getting element at position 3 ---");
        System.out.println("Element at position 3: " + linkedList.get(3));
        
        // Remove element
        System.out.println("\n--- Removing element 30 ---");
        linkedList.remove(30);
        System.out.println("After removing 30: " + linkedList);
        
        // Find middle element
        System.out.println("\n--- Finding middle element ---");
        System.out.println("Middle element: " + linkedList.findMiddle());
        
        // Reverse the list
        System.out.println("\n--- Reversing the list ---");
        linkedList.reverse();
        System.out.println("After reversing: " + linkedList);
        
        // Compare with ArrayList
        System.out.println("\n=== ArrayList Comparison ===\n");
        
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(10);
        arrayList.add(20);
        arrayList.add(30);
        arrayList.add(40);
        arrayList.add(50);
        
        System.out.println("ArrayList: " + arrayList);
        System.out.println("ArrayList size: " + arrayList.size());
        
        // Performance comparison
        System.out.println("\n--- Performance Comparison ---");
        System.out.println("LinkedList vs ArrayList:");
        System.out.println("- LinkedList: O(1) insertion/deletion at ends, O(n) random access");
        System.out.println("- ArrayList: O(1) random access, O(n) insertion/deletion");
        
        // Memory usage comparison
        System.out.println("\n--- Memory Usage ---");
        System.out.println("LinkedList: Each element has data + next pointer overhead");
        System.out.println("ArrayList: Contiguous memory allocation, more cache-friendly");
        
        // Use cases
        System.out.println("\n--- Use Cases ---");
        System.out.println("LinkedList: Frequent insertions/deletions, unknown size");
        System.out.println("ArrayList: Random access, known size, better cache performance");
        
        // Demonstrate cycle detection
        System.out.println("\n=== Cycle Detection Demo ===");
        LinkedList cycleList = new LinkedList();
        cycleList.add(1);
        cycleList.add(2);
        cycleList.add(3);
        cycleList.add(4);
        
        System.out.println("List without cycle: " + cycleList);
        System.out.println("Has cycle: " + cycleList.hasCycle());
        
        // Create a cycle (for demonstration - in real code you'd need to modify Node class)
        System.out.println("\nNote: Creating cycles requires modifying the Node class");
        System.out.println("In practice, cycles are usually created by linking nodes incorrectly");
        
        System.out.println("\n=== Summary ===");
        System.out.println("Linked Lists are fundamental data structures that:");
        System.out.println("1. Provide efficient insertion/deletion at any position");
        System.out.println("2. Use dynamic memory allocation");
        System.out.println("3. Are the foundation for more complex data structures");
        System.out.println("4. Are essential for understanding memory management");
    }
} 