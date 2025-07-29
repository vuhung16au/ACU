/**
 * Example1.java
 * 
 * This program demonstrates example in Java:
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

public class Example1 {
    
    /**
     * Simple Node class for demonstration
     * In Python, you might use: class Node: def __init__(self, data): self.data = data; self.next = None
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
     * Simple LinkedList implementation
     * Focuses on basic operations for learning
     */
    static class SimpleLinkedList {
        private Node head;
        
        public SimpleLinkedList() {
            this.head = null;
        }
        
        /**
         * Add element to the end
         * Similar to Python's append()
         */
        public void append(int data) {
            Node newNode = new Node(data);
            
            if (head == null) {
                head = newNode;
                return;
            }
            
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        
        /**
         * Print all elements
         * Similar to Python's print(list)
         */
        public void printList() {
            Node current = head;
            System.out.print("List: ");
            while (current != null) {
                System.out.print(current.data + " ");
                current = current.next;
            }
            System.out.println();
        }
        
        /**
         * Count elements
         * Similar to Python's len(list)
         */
        public int count() {
            int count = 0;
            Node current = head;
            while (current != null) {
                count++;
                current = current.next;
            }
            return count;
        }
        
        /**
         * Find maximum value
         * Similar to Python's max(list)
         */
        public int findMax() {
            if (head == null) {
                throw new IllegalStateException("List is empty");
            }
            
            int max = head.data;
            Node current = head.next;
            
            while (current != null) {
                if (current.data > max) {
                    max = current.data;
                }
                current = current.next;
            }
            
            return max;
        }
        
        /**
         * Check if element exists
         * Similar to Python's element in list
         */
        public boolean contains(int data) {
            Node current = head;
            while (current != null) {
                if (current.data == data) {
                    return true;
                }
                current = current.next;
            }
            return false;
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Example 1: Basic LinkedList Operations ===\n");
        
        // Create a new linked list
        SimpleLinkedList list = new SimpleLinkedList();
        System.out.println("Created empty linked list");
        
        // Add elements (similar to Python's append)
        System.out.println("\nAdding elements...");
        list.append(10);
        list.append(20);
        list.append(30);
        list.append(40);
        list.append(50);
        
        // Print the list
        list.printList();
        
        // Count elements
        System.out.println("Number of elements: " + list.count());
        
        // Find maximum
        System.out.println("Maximum value: " + list.findMax());
        
        // Check if elements exist
        System.out.println("Contains 30: " + list.contains(30));
        System.out.println("Contains 100: " + list.contains(100));
        
        // Demonstrate traversal
        System.out.println("\n--- Traversal Demo ---");
        System.out.println("Traversing the list manually:");
        
        // This is how you'd traverse in Python:
        // current = head
        // while current is not None:
        //     print(current.data)
        //     current = current.next
        
        // In Java:
        Node current = list.head; // Note: head would be private in real implementation
        int position = 0;
        while (current != null) {
            System.out.println("Position " + position + ": " + current.data);
            current = current.next;
            position++;
        }
        
        System.out.println("\n=== Key Differences from Python ===");
        System.out.println("1. Java requires explicit Node class");
        System.out.println("2. Java uses null instead of None");
        System.out.println("3. Java requires explicit type declarations");
        System.out.println("4. Java uses while loops for traversal (same as Python)");
        System.out.println("5. Java uses .next instead of .next (same concept)");
        
        System.out.println("\n=== Performance Notes ===");
        System.out.println("- Traversal: O(n) - same as Python");
        System.out.println("- Append: O(n) - same as Python");
        System.out.println("- Search: O(n) - same as Python");
        System.out.println("- Memory: Each node has overhead (Java) vs Python's dynamic typing");
    }
} 