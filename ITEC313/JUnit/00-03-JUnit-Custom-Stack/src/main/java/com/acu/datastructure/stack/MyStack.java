package com.acu.datastructure.stack;

import java.util.EmptyStackException;

/**
 * A custom implementation of a Stack data structure.
 * This class provides basic stack operations: push, pop, peek, isEmpty, and size.
 * 
 * @param <E> the type of elements stored in the stack
 */
public class MyStack<E> {
    
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;
    private int size;
    
    /**
     * Constructs an empty stack with default capacity.
     */
    public MyStack() {
        this(DEFAULT_CAPACITY);
    }
    
    /**
     * Constructs an empty stack with the specified initial capacity.
     * 
     * @param initialCapacity the initial capacity of the stack
     * @throws IllegalArgumentException if initialCapacity is negative
     */
    public MyStack(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Initial capacity cannot be negative");
        }
        this.elements = new Object[initialCapacity];
        this.size = 0;
    }
    
    /**
     * Pushes an element onto the top of the stack.
     * 
     * @param element the element to push
     * @return the element that was pushed
     */
    public E push(E element) {
        ensureCapacity();
        elements[size++] = element;
        return element;
    }
    
    /**
     * Removes and returns the element at the top of the stack.
     * 
     * @return the element at the top of the stack
     * @throws EmptyStackException if the stack is empty
     */
    @SuppressWarnings("unchecked")
    public E pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        E element = (E) elements[--size];
        elements[size] = null; // Help garbage collection
        return element;
    }
    
    /**
     * Returns the element at the top of the stack without removing it.
     * 
     * @return the element at the top of the stack
     * @throws EmptyStackException if the stack is empty
     */
    @SuppressWarnings("unchecked")
    public E peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return (E) elements[size - 1];
    }
    
    /**
     * Tests if the stack is empty.
     * 
     * @return true if the stack is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Returns the number of elements in the stack.
     * 
     * @return the number of elements in the stack
     */
    public int size() {
        return size;
    }
    
    /**
     * Ensures that the stack has enough capacity to hold additional elements.
     * If the current capacity is insufficient, the capacity is doubled.
     */
    private void ensureCapacity() {
        if (size == elements.length) {
            Object[] newElements = new Object[elements.length * 2];
            System.arraycopy(elements, 0, newElements, 0, size);
            elements = newElements;
        }
    }
    
    /**
     * Removes all elements from the stack.
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }
}
