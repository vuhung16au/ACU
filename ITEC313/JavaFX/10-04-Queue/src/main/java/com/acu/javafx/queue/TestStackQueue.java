package com.acu.javafx.queue;

public class TestStackQueue {
  public static void main(String[] args) {
    // Create a stack
    GenericStack<String> stack = new GenericStack<>();

    // Add elements to the stack
    stack.push("Tom"); // Push Tom to the stack
    System.out.println("(1) " + stack);

    stack.push("Chandra"); // Push Chandra to the the stack
    System.out.println("(2) " + stack);

    stack.push("Kim"); // Push it to the stack
    stack.push("Liam"); // Push Liam to the stack
    System.out.println("(3) " + stack);

    // Remove elements from the stack
    System.out.println("(4) " + stack.pop());
    System.out.println("(5) " + stack.pop());
    System.out.println("(6) " + stack);

    // Create a queue
    GenericQueue<String> queue = new GenericQueue<>();

    // Add elements to the queue
    queue.enqueue("Tom"); // Add Tom to the queue
    System.out.println("(7) " + queue);

    queue.enqueue("Chandra"); // Add Chandra to the queue
    System.out.println("(8) " + queue);

    queue.enqueue("Kim"); // Add Kim to the queue
    queue.enqueue("Liam"); // Add Liam to the queue
    System.out.println("(9) " + queue);

    // Remove elements from the queue
    System.out.println("(10) " + queue.dequeue());
    System.out.println("(11) " + queue.dequeue());
    System.out.println("(12) " + queue);
  }
} 